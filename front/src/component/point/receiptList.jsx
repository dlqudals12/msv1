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

export const ReceiptList = () => {
  const navigate = useNavigate();
  const [receiptList, setReceiptList] = useState([]);
  const [cookies, ,] = useCookies("userId");
  const [filter, setFilter] = useState({
    page: 1,
    receiptType: "",
  });
  const [pagination, setPagination] = useAtom(PaginationData);

  useEffect(() => {
    axios
      .get(
        process.env.PUBLIC_URL +
          `/api/receipt/receipt_list?page=${filter.page}&receiptType=${filter.receiptType}`
      )
      .then((res) => {
        if (res.data.code === "0000") {
          const result = res.data.result;
          setReceiptList(result?.content);
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

  console.log("토스(2Dv9ZPM7zXLkKEypNArWda0JLX".length);

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
                  value={filter.receiptType}
                  onChange={(e) => {
                    setFilter({
                      ...filter,
                      receiptType: e.target.value,
                      page: 1,
                    });
                  }}
                >
                  <option value={""}>전체</option>
                  <option value={"CHARGE"}>포인트</option>
                  <option value={"DEALVOCA"}>단어장</option>
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
                          거래 품목
                        </th>
                        <th style={{ textAlign: "center" }}>판매자</th>
                        <th style={{ textAlign: "center" }}>구매자</th>
                        <th style={{ width: "100px", textAlign: "center" }}>
                          포인트
                        </th>
                        <th style={{ width: "75px", textAlign: "center" }}>
                          거래수
                        </th>
                        <th style={{ width: "150px", textAlign: "center" }}>
                          거래일
                        </th>
                        <th style={{ width: "75px", textAlign: "center" }}>
                          상태
                        </th>
                        <th style={{ width: "75px", textAlign: "center" }}>
                          타입
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      {receiptList[0] &&
                        receiptList.map((item, index) => (
                          <>
                            <tr>
                              <td style={{ textAlign: "center" }}>
                                {index + 1}
                              </td>
                              <td
                                style={{
                                  width: "300px",
                                  display: "block",
                                  whiteSpace: "nowrap",
                                  overflow: "hidden",
                                  textOverflow: "ellipsis",
                                }}
                                data-tooltip-id={"vocaName" + index}
                                data-tooltip-content={item.vocaName}
                              >
                                <span
                                  style={{ cursor: "pointer" }}
                                  onClick={() => {
                                    navigator.clipboard.writeText(
                                      item.vocaName
                                    );
                                  }}
                                >
                                  {item.vocaName
                                    ? item.vocaName
                                    : "포인트 충전"}
                                </span>
                                {item.vocaName.length > 28 && (
                                                                <Tooltip id={"vocaName" + index} place="top" />
                                                              )}
                              </td>
                              <td>
                                {item.toUser ? item.toUser : "관리자(ADMIN)"}
                              </td>
                              <td>
                                {item.fromUser
                                  ? item.fromUser
                                  : "관리자(ADMIN)"}
                              </td>
                              <td style={{ textAlign: "right" }}>
                                {item.point}
                              </td>
                              <td style={{ textAlign: "right" }}>
                                {item.buyCount === 0 ? 1 : item.buyCount}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {moment(item.regDt).format("YYYY-MM-DD HH:mm")}
                              </td>
                              <td
                                style={{
                                  textAlign: "center",
                                  color:
                                    item.status === "판매"
                                      ? "#E03D3D"
                                      : "#93bf85",
                                }}
                              >
                                {item.status}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {item.receiptType === "CHARGE" ? (
                                  <Coin
                                    style={{ width: "30px", height: "20px" }}
                                  />
                                ) : (
                                  <JournalCheck
                                    style={{ width: "30px", height: "20px" }}
                                  />
                                )}
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
                    onClick={() => navigate("/point/charge")}
                  >
                    포인트 충전
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
