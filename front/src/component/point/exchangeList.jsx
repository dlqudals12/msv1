import { Link, useNavigate } from "react-router-dom";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import { PaginationCommon } from "../common/paginationCommon";
import { PaginationData } from "../data/atom";
import { useAtom } from "jotai";
import { useEffect, useState } from "react";
import Button from "react-bootstrap/esm/Button";
import axios from "axios";
import { useCookies } from "react-cookie";
import moment from "moment";
import { Coin } from "react-bootstrap-icons";
import { JournalCheck } from "react-bootstrap-icons";
import Form from "react-bootstrap/Form";
import { Tooltip } from "react-tooltip";

export const ExchangeList = () => {
  const navigate = useNavigate();
  const [exchangeList, setExchangeList] = useState([]);
  const [cookies, ,] = useCookies("userId");
  const [filter, setFilter] = useState({
    page: 1,
    status: "",
  });
  const [pagination, setPagination] = useAtom(PaginationData);

  useEffect(() => {
    axios
      .get(
        process.env.PUBLIC_URL +
          `/api/exchange/exchange_user_list?page=${filter.page}&status=${filter.status}`
      )
      .then((res) => {
        if (res.data.code === "0000") {
          const result = res.data.result;
          setExchangeList(result?.content);
          setPagination({
            total: result.totalElements,
            maxPage: result.totalPages,
            start: Math.floor(filter.page / 10) * 10 + 1,
          });
        } else {
          alert(res.data.msg);
        }
      })
      .catch((e) => {});
  }, [filter]);

  const statusVal = (status) => {
    switch (status) {
      case "PROCESSING":
        return <span style={{ color: "#0D6EFD" }}>처리중</span>;
      case "COMPLETED":
        return <span style={{ color: "#218838" }}>완료</span>;
      case "CANCEL":
        return <span style={{ color: "red" }}>처리 거부</span>;
      case "REVOKE":
        return <span style={{ color: "red" }}>취소</span>;
    }
  };

  return (
    <>
      <Header />
      <div
        className="py-5 text-center"
        style={{
          backgroundImage: `url('https://static.pingendo.com/cover-bubble-dark.svg')`,
          backgroundSize: "cover",
        }}
      >
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-1">거래내역</h1>
            </div>
          </div>
        </div>
      </div>
      <div className="py-5" draggable="true">
        <div className="container">
          <div className="row" style={{ width: "100%" }}>
            <div className="col-md-15">
              <div className="float-right mb-2">
                <Form.Select
                  style={{ width: "160px", height: "35px" }}
                  value={filter.status}
                  onChange={(e) => {
                    setFilter({
                      ...filter,
                      status: e.target.value,
                      page: 1,
                    });
                  }}
                >
                  <option value={""}>전체</option>
                  <option value={"PROCESSING"}>처리중</option>
                  <option value={"COMPLETED"}>완료</option>
                  <option value={"CANCEL"}>처리 거부</option>
                  <option value={"REVOKE"}>취소</option>
                </Form.Select>
              </div>
              <div className="table-responsive">
                <div style={{ minHeight: "610px" }}>
                  <table className="table table-bordered">
                    <thead className="thead-dark">
                      <tr>
                        <th style={{ width: "70px", textAlign: "center" }}>
                          No.
                        </th>
                        <th style={{ textAlign: "center", width: "300px" }}>
                          계좌번호
                        </th>
                        <th style={{ width: "100px", textAlign: "center" }}>
                          은행명
                        </th>
                        <th style={{ width: "100px", textAlign: "center" }}>
                          입금자명
                        </th>
                        <th style={{ width: "150px", textAlign: "center" }}>
                          금액
                        </th>
                        <th style={{ textAlign: "center" }}>거래일</th>
                        <th style={{ width: "100px", textAlign: "center" }}>
                          상태
                        </th>
                        <th style={{ width: "100px" }}></th>
                      </tr>
                    </thead>
                    <tbody>
                      {exchangeList[0] &&
                        exchangeList.map((item, index) => (
                          <>
                            <tr>
                              <td style={{ textAlign: "center" }}>
                                {index + 1}
                              </td>
                              <td>{item.bankNum}</td>
                              <td style={{ textAlign: "center" }}>
                                {item.bank}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {item.name}
                              </td>
                              <td style={{ textAlign: "right" }}>
                                {item.money}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {moment(item.regDt).format("YYYY-MM-DD HH:mm")}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {statusVal(item.status)}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                <Button
                                  variant="danger"
                                  style={{
                                    fontSize: "12px",
                                    padding: "3px 8px 3px 8px",
                                  }}
                                  disabled={item.status !== "PROCESSING"}
                                >
                                  삭제
                                </Button>
                              </td>
                            </tr>
                          </>
                        ))}
                    </tbody>
                  </table>
                </div>

                <div
                  style={{
                    display: "flex",
                    justifyContent: "flex-end",
                    fontSize: "15px",
                  }}
                >
                  <Button
                    style={{
                      padding: "5px 10px 5px 10px",
                    }}
                    variant="success"
                    onClick={() => navigate("/point/exchange")}
                  >
                    환전
                  </Button>
                </div>
                <PaginationCommon
                  page={filter.page}
                  setPage={(data) => {
                    setFilter({ ...filter, page: data });
                  }}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
