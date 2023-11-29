import { useEffect, useState } from "react";
import Button from "react-bootstrap/esm/Button";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import axios from "axios";
import { useNavigate, useSearchParams } from "react-router-dom";
import { loadTossPayments } from "@tosspayments/payment-sdk";
import { v4 as uuidv4 } from "uuid";
import { useCookies } from "react-cookie";

const clientKey = "test_ck_7DLJOpm5QrlJLlY4dxo3PNdxbWnY";

export const ChargePoint = () => {
  const navigate = useNavigate();
  const [searchParam, setSearchParam] = useSearchParams();
  const [chargeData, setChargeData] = useState({
    chargeType: "",
    point: 0,
  });
  const [userWirte, setUserRight] = useState(false);
  const [cookies, ,] = useCookies("userId");

  useEffect(() => {
    if (searchParam.get("payStatus") === "success") {
      axios
        .get(
          "https://api.tosspayments.com/v1/payments/" +
            searchParam.get("paymentKey"),
          {
            headers: {
              Authorization:
                "Basic dGVzdF9za19PRVA1OUx5Ylo4QjA2NW1rRTFXVjZHWW83cFJlOg==",
            },
          }
        )
        .then((ress) => {
          if (ress.status === 200) {
            if (
              ress.data.orderId === searchParam.get("orderId") &&
              ress.data.totalAmount === Number(searchParam.get("point"))
            ) {
              axios
                .post(process.env.PUBLIC_URL + "/api/receipt/charge_point", {
                  point: Number(searchParam.get("point")),
                })
                .then((res) => {
                  if (res.data.code === "0000") {
                    alert(
                      (searchParam.get("chargeType") === "TOSS"
                        ? "토스페이 "
                        : "") +
                        "결제에 성공하였습니다. \n 남은 금액: " +
                        res.data.result
                    );
                    searchParam.delete("payStatus");
                    searchParam.delete("chargeType");
                    searchParam.delete("point");
                    searchParam.delete("orderId");
                    setSearchParam(searchParam);
                  } else {
                    alert(res.data.msg);
                  }
                })
                .catch((e) => {
                  alert("시스템 오류");
                });
            } else {
              alert("결제 정보가 맞지 않습니다.");
              navigate("/");
            }
          } else {
            alert("결제 정보가 존재하지 않습니다.");
            navigate("/");
          }
        });
    } else if (searchParam.get("payStatus") === "fail") {
      alert(
        searchParam.get("chargeType") === "TOSS"
          ? "토스페이 "
          : "" + "결제에 실패하였습니다."
      );
      navigate("/point/charge");
    }
  }, []);

  const buttonTest = () => {
    axios
      .get(
        "https://api.tosspayments.com/v1/payments/jvX2KBP9QADpexMgkW36xPvw4nyWXJrGbR5ozO06yLYlaEJ7",
        {
          headers: {
            Authorization:
              "Basic dGVzdF9za19PRVA1OUx5Ylo4QjA2NW1rRTFXVjZHWW83cFJlOg==",
          },
        }
      )
      .then((res) => {
        console.log(res);
      });
  };

  const onClickCharge = async () => {
    const valid = {
      chargeData: !Boolean(chargeData.chargeType),
      point: chargeData.point < 1,
    };

    if (!Object.values(valid).includes(true)) {
      const uuid = uuidv4().replaceAll("-", "");
      const tossPayments = await loadTossPayments(clientKey);
      tossPayments.requestPayment("카드", {
        amount: chargeData.point,
        orderId: uuid,
        orderName: "포인트 충전",
        customerName: cookies.userId,
        successUrl: `http://localhost:3000/point/charge?payStatus=success&point=${chargeData.point}&chargeType=${chargeData.chargeType}&orderId=${uuid}`,
        failUrl: `http://localhost:3000/point/charge?payStatus=fail&chargeType=${chargeData.chargeType}`,
        validHours: 24,
        cashReceipt: {
          type: "소득공제",
        },
      });
    } else {
      alert(
        valid.chargeData
          ? "결제 타입을 선택해주세요."
          : "포인트는 1이상이어야 합니다."
      );
    }
    /* axios
        .post(process.env.PUBLIC_URL + "/api/receipt/charge_point", chargeData)
        .then((res) => {
          if (res.data.code === "0000") {
            alert("충전에 성공했습니다.");
            navigate("/point/list");
          } else {
            alert(res.data.msg);
          }
        })
        .catch((e) => {
          alert("시스템 오류");
        });
    } else {
      alert(
        valid.chargeData
          ? "결제 타입을 선택해주세요."
          : "포인트는 1이상이어야 합니다."
      );
    } */
  };

  return (
    <>
      <Header />
      <div className="py-5">
        <div className="container">
          <Card className="mt-4">
            <Card.Header
              style={{
                textAlign: "center",
                fontSize: "27px",
                fontFamily: "NotoSansKRBold",
              }}
            >
              포인트 충전
            </Card.Header>
            <Card.Body>
              <Card.Subtitle>결제 방식을 선택하세요</Card.Subtitle>
              <Card.Text className="mt-2">
                <Button
                  style={{ backgroundColor: "#0D6EFD", color: "#FFFFFF" }}
                  active={chargeData.chargeType === "TOSS"}
                  onClick={() => {
                    setChargeData({
                      ...chargeData,
                      chargeType:
                        chargeData.chargeType === "TOSS" ? "" : "TOSS",
                    });
                  }}
                >
                  TOSS
                </Button>
              </Card.Text>
              <Card.Subtitle className="mt-3">
                결제 금액을 선택하세요.
              </Card.Subtitle>
              <Card.Text className="mt-2">
                <Form.Select
                  style={{ width: "150px", height: "33px" }}
                  value={userWirte ? -1 : chargeData.point}
                  onChange={(e) => {
                    if (Number(e.target.value) < 0) {
                      setUserRight(true);
                    } else {
                      setUserRight(false);
                      setChargeData({
                        ...chargeData,
                        point: Number(e.target.value),
                      });
                    }
                  }}
                >
                  <option value={0}>0</option>
                  <option value={5000}>5000</option>
                  <option value={10000}>10000</option>
                  <option value={20000}>20000</option>
                  <option value={30000}>30000</option>
                  <option value={40000}>40000</option>
                  <option value={50000}>50000</option>
                  <option value={10000}>100000</option>
                  <option value={-1}>직접입력</option>
                </Form.Select>
                <input
                  type="number"
                  className="ml-2"
                  style={{ width: "180px", height: "33px" }}
                  value={userWirte ? chargeData.point : ""}
                  disabled={!userWirte}
                  onChange={(e) => {
                    if (Number(e.target.value) > 0) {
                      setChargeData({
                        ...chargeData,
                        point: Number(e.target.value),
                      });
                    }
                  }}
                />
              </Card.Text>
              <Card.Text className="mt-4 mb-0">
                결제 금액: {chargeData.point}
              </Card.Text>
              <Card.Text className="mt-0 mb-0">
                충전 포인트: {chargeData.point}
              </Card.Text>
              <Card.Text className="mt-0">
                결제 방법: {chargeData.chargeType}
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <Button
                className="float-right"
                style={{ padding: "6px 12px 6px 12px" }}
                variant="success"
                onClick={onClickCharge}
              >
                충전
              </Button>
              <Button onClick={buttonTest}>TEST</Button>
            </Card.Footer>
          </Card>
        </div>
      </div>
      <Footer />
    </>
  );
};
