import { useNavigate } from "react-router-dom";
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

export const VocaBoardReceipt = () => {
  const navigate = useNavigate();
  const [receiptList, setReceiptList] = useState([]);
  const [cookies, ,] = useCookies("userId");
  const [filter, setFilter] = useState({
    page: 1,
  });
  const [pagination, setPagination] = useAtom(PaginationData);

  useEffect(() => {
    axios
      .get(
        process.env.PUBLIC_URL +
          `/api/receipt//receipt_list?page=${filter.page}`
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
      .catch((e) => {
        alert("시스템 오류");
      });
  }, [filter]);

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
            <div className="col-md-12">
              <div className="table-responsive">
                <table className="table table-bordered">
                  <thead className="thead-dark">
                    <tr>
                      <th style={{ width: "70px", textAlign: "center" }}>
                        No.
                      </th>
                      <th style={{ textAlign: "center" }}>단어장 이름</th>
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
                      <th style={{ width: "75px" }}></th>
                    </tr>
                  </thead>
                  <tbody>
                    {receiptList[0] &&
                      receiptList.map((item, index) => (
                        <>
                          <tr>
                            <td style={{ textAlign: "center" }}>{index + 1}</td>
                            <td>{item.vocaName}</td>
                            <td>{item.toUser}</td>
                            <td>{item.fromUser}</td>
                            <td style={{ textAlign: "right" }}>{item.point}</td>
                            <td style={{ textAlign: "right" }}>
                              {item.buyCount}
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
                          </tr>
                        </>
                      ))}
                  </tbody>
                </table>
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
