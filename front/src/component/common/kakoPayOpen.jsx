import { kakaoAxios } from "../../util/axios";
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

export const KakaoPayOpen = () => {
  const [urlParams, setUrlParams] = useSearchParams();

  useEffect(() => {
    if (!urlParams.get("payStatus")) {
      const payData = window.opener.payKakao();
      kakaoAxios
        .post("/ready", {
          cid: "TCSUBSCRIP",
          partner_order_id: payData.orderId,
          partner_user_id: "msv",
          item_name: "포인트 결제",
          quantity: 1,
          total_amount: payData.point,
          tax_free_amount: 0,
          approval_url: `http://localhost:3000/point/charge/kakaoPay?payStatus=success&point=${payData.point}&chargeType=KAKAO&orderId=${payData.orderId}`,
          cancel_url: `http://localhost:3000/point/charge/kakaoPay?payStatus=cancel&point=${payData.point}&chargeType=KAKAO&orderId=${payData.orderId}`,
          fail_url: `http://localhost:3000/point/charge/kakaoPay?payStatus=fail&point=${payData.point}&chargeType=KAKAO&orderId=${payData.orderId}`,
        })
        .then((res) => {
          if (res.status === 200) {
            localStorage.setItem("tid", res.data.tid);
            window.location.replace(res.data.next_redirect_pc_url);
          } else {
            alert("결제 실페");
            window.close();
          }
        })
        .catch((e) => {
          alert("결제 실페");
          window.close();
        });
    } else {
      if (urlParams.get("payStatus") === "success") {
        kakaoAxios
          .post("/approve", {
            cid: "TCSUBSCRIP",
            tid: localStorage.getItem("tid"),
            partner_order_id: urlParams.get("orderId"),
            partner_user_id: "msv",
            pg_token: urlParams.get("pg_token"),
          })
          .then((res) => {
            if (res.status === 200) {
              window.opener.payKakaoEnd(
                Number(urlParams.get("point")),
                urlParams.get("orderId"),
                localStorage.getItem("tid")
              );
              localStorage.removeItem("tid");
              window.close();
            } else {
              alert("결제 실페");
              window.close();
            }
          })
          .catch((e) => {
            alert("결제 실페");
            window.close();
          });
      } else {
        alert("결제 실페");
        window.close();
      }
    }
  }, []);

  return <></>;
};
