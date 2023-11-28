import { useLocation, useNavigate } from "react-router-dom";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import Card from "react-bootstrap/Card";
import CardHeader from "react-bootstrap/esm/CardHeader";
import CardFooter from "react-bootstrap/esm/CardFooter";
import CardBody from "react-bootstrap/esm/CardBody";
import Button from "react-bootstrap/esm/Button";
import { useEffect, useState } from "react";
import axios from "axios";
import moment from "moment";
import { useAtom } from "jotai";
import { IsLogin } from "../data/atom";
import { useCookies } from "react-cookie";

export const VocaBoardDetail = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [vocaBoardData, setVocaBoardData] = useState({});
  const [cookies, ,] = useCookies("userId");

  useEffect(() => {
    if (location.state) {
      let isUpdate = new Set(JSON.parse(sessionStorage.getItem("isUpdate")));

      let updateCount = true;

      if (Boolean(sessionStorage.getItem("isUpdate"))) {
        updateCount = !isUpdate.has(location.state.vocaBoardId);
      }

      axios
        .get(
          process.env.PUBLIC_URL +
            `/api/voca_board/${
              Boolean(cookies.userId) ? "user_" : ""
            }voca_board_detail?id=${
              location.state.vocaBoardId
            }&updateCount=${updateCount}`
        )
        .then((res) => {
          isUpdate.add(location.state.vocaBoardId);
          sessionStorage.setItem("isUpdate", JSON.stringify([...isUpdate]));
          setVocaBoardData(res.data.result);
        });
    }
  }, []);

  const onClickTrade = () => {
    if (cookies.userId) {
      axios
        .post(process.env.PUBLIC_URL + "/api/voca_board/trade_voca", {
          vocaBoardId: vocaBoardData.id,
        })
        .then((res) => {
          if (res.data.code === "0000") {
            alert(
              "단어장 거래가 완료되었습니다. \n" +
                `남은 포인트: ${res.data.result}`
            );
            navigate("/voca/list");
          } else {
            alert(res.data.msg);
          }
        })
        .catch((e) => {
          alert("시스템 오류");
        });
    } else {
      alert("로그인 후 이용이 가능합니다.");
      navigate("/login");
    }
  };

  return (
    <>
      <Header />
      <div className="py-5">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              {vocaBoardData.id && (
                <>
                  <Card>
                    <CardHeader>
                      <Card.Title>{vocaBoardData.title}</Card.Title>
                      <Card.Subtitle className="float-right">
                        <span className="float-right">
                          {moment(vocaBoardData.regDt).format("YYYY-MM-DD")}{" "}
                        </span>
                        <br />
                        <span className="float-right">
                          {vocaBoardData.point} Point
                        </span>
                        <br />
                        <span className="float-right">
                          조회수: {vocaBoardData.count} 구매수:{" "}
                          {vocaBoardData.buycount}
                        </span>
                      </Card.Subtitle>
                    </CardHeader>
                    <CardBody style={{ minHeight: "500px" }}>
                      <Card>
                        <CardBody>
                          <Card.Subtitle>VocaInfo</Card.Subtitle>
                          <div style={{ marginTop: "10px" }}>
                            <Card.Text>
                              {vocaBoardData.voca.vocaName} ({" "}
                              {vocaBoardData.voca.country} ) <br />
                              {moment(vocaBoardData.voca.regDt).format(
                                "YYYY-MM-DD"
                              )}
                            </Card.Text>
                          </div>
                        </CardBody>
                      </Card>
                      <Card.Text>{vocaBoardData.board}</Card.Text>
                    </CardBody>
                    <CardFooter>
                      <div
                        className="float-right"
                        style={{
                          justifyContent: "space-between",
                        }}
                      >
                        {vocaBoardData.own && (
                          <Button style={{ fontSize: "14px" }} variant="info">
                            수정
                          </Button>
                        )}
                        {!vocaBoardData?.haveVoca && (
                          <Button
                            style={{ fontSize: "14px", marginLeft: "20px" }}
                            variant="success"
                            onClick={onClickTrade}
                          >
                            구매
                          </Button>
                        )}

                        <Button
                          style={{ marginLeft: "20px", fontSize: "14px" }}
                          onClick={() => navigate(-1)}
                        >
                          목록
                        </Button>
                      </div>
                    </CardFooter>
                  </Card>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
