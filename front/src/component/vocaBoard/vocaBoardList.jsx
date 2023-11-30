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

export const VocaBoardList = () => {
  const navigate = useNavigate();
  const [vocaBoardList, setVocaBoardList] = useState([]);
  const [cookies, ,] = useCookies("userId");
  const [keyword, setKeyWord] = useState("");
  const [filter, setFilter] = useState({
    title: "",
    page: 1,
    loginId: "",
  });
  const [, setPagination] = useAtom(PaginationData);

  useEffect(() => {
    axios
      .get(
        process.env.PUBLIC_URL +
          `/api/voca_board/list_voca_board?title=${filter.title}&page=${filter.page}&loginId=${filter.loginId}`
      )
      .then((res) => {
        if (res.data.code === "0000") {
          const result = res.data.result;
          setVocaBoardList(result?.content);
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
              <h1 className="display-1">거래소</h1>
            </div>
          </div>
        </div>
      </div>
      <div className="py-5" draggable="true">
        <div className="container">
          <div
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "space-between",
            }}
          >
            <label style={{ fontSize: "14px", margin: "0", height: "30px" }}>
              {cookies.userId && (
                <>
                  내 단어장
                  <input
                    type="checkbox"
                    value={Boolean(filter.loginId)}
                    style={{ marginLeft: "10px" }}
                    onChange={(e) => {
                      setFilter({
                        ...filter,
                        loginId: e.target.checked ? cookies.userId : "",
                        page: 1,
                      });
                    }}
                  />
                </>
              )}
            </label>
            <label
              className="float-right"
              style={{ marginRight: "30px", height: "30px" }}
            >
              <input
                type="text"
                value={keyword}
                onKeyDown={(e) => {
                  if (e.key === "Enter")
                    setFilter({ ...filter, title: keyword, page: 1 });
                }}
                onChange={(e) => {
                  setKeyWord(e.target.value);
                }}
              />
              <Button
                style={{
                  marginLeft: "15px",
                  padding: "4px 10px 4px 10px",
                  fontSize: "14px",
                }}
                onClick={() => {
                  setFilter({ ...filter, title: keyword, page: 1 });
                }}
              >
                검색
              </Button>
            </label>
          </div>
          <div className="row" style={{ width: "100%" }}>
            <div className="col-md-12">
              <div className="table-responsive">
                <table className="table table-bordered">
                  <thead className="thead-dark">
                    <tr>
                      <th style={{ width: "70px", textAlign: "center" }}>
                        No.
                      </th>
                      <th>제목</th>
                      <th>포인트</th>
                      <th style={{ width: "150px", textAlign: "center" }}>
                        조회수
                      </th>
                      <th style={{ width: "150px", textAlign: "center" }}>
                        판매수
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {vocaBoardList[0] &&
                      vocaBoardList.map((item, index) => (
                        <>
                          <tr
                            style={{ cursor: "pointer" }}
                            onClick={() => {
                              navigate("/vocaboard/detail", {
                                state: {
                                  vocaBoardId: item.vocaBoardId,
                                },
                              });
                            }}
                          >
                            <td>{index + 1}</td>
                            <td>{item.title}</td>
                            <td>{item.point}</td>
                            <td>{item.viewCount}</td>
                            <td>{item.buyCount}</td>
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
