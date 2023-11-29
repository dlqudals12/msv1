import { useNavigate } from "react-router-dom";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import { useEffect, useState } from "react";
import { CheckLg } from "react-bootstrap-icons";
import axios from "axios";
import { useAtom } from "jotai";
import { PaginationData } from "../data/atom";
import { PaginationCommon } from "../common/paginationCommon";
import Button from "react-bootstrap/esm/Button";

export const VocaList = () => {
  const navigate = useNavigate();
  const [vocaList, setVocaList] = useState([]);
  const [filter, setFilter] = useState({
    vocaName: "",
    page: 1,
  });
  const [pagination, setPagination] = useAtom(PaginationData);

  useEffect(() => {
    axios
      .get(
        process.env.PUBLIC_URL +
          `/api/voca/voca_list?vocaName=${filter.vocaName}&page=${filter.page}`
      )
      .then((res) => {
        if (res.data.code === "0000") {
          setVocaList(res.data.result?.content);
          setPagination({
            ...pagination,
            total: res.data.result?.totalElements,
            maxPage: res.data.result?.totalPages,
            start: Math.floor(filter.page / 10) * 10 + 1,
          });
        }
      })
      .catch((e) => {});
  }, []);

  const onClickDetail = (item) => {
    navigate("/voca/detail", {
      state: {
        vocaId: item.vocaId,
        isOwn: item.own,
      },
    });
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
              <h1 className="display-1">My Voca</h1>
            </div>
          </div>
        </div>
      </div>
      <div className="py-5" draggable="true">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <div className="table-responsive">
                <table className="table table-bordered">
                  <thead className="thead-dark">
                    <tr>
                      <th style={{ width: "70px", textAlign: "center" }}>
                        No.
                      </th>
                      <th>단어장 이름</th>
                      <th>나라</th>
                      <th style={{ width: "150px", textAlign: "center" }}>
                        소유여부
                      </th>
                      <th style={{ width: "150px", textAlign: "center" }}>
                        판매
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {vocaList[0] &&
                      vocaList.map((item, index) => (
                        <>
                          <tr style={{ cursor: "pointer" }}>
                            <td
                              onClick={() => onClickDetail(item)}
                              style={{ textAlign: "center" }}
                            >
                              {index + 1}
                            </td>
                            <td onClick={() => onClickDetail(item)}>
                              {item.vocaName}
                            </td>
                            <td onClick={() => onClickDetail(item)}>
                              {item.country}
                            </td>
                            <td
                              onClick={() => onClickDetail(item)}
                              style={{ textAlign: "center" }}
                            >
                              {item.own ? (
                                <>
                                  <CheckLg />
                                </>
                              ) : (
                                <></>
                              )}
                            </td>
                            <td
                              style={{ textAlign: "center", cursor: "default" }}
                            >
                              <Button
                                disabled={!item.own || item.sell}
                                variant="primary"
                                style={{
                                  fontSize: "12px",
                                  padding: "4px 8px 4px 8px",
                                }}
                                onClick={() =>
                                  navigate("/vocaboard/new", {
                                    state: {
                                      vocaId: item.vocaId,
                                      vocaName: item.vocaName,
                                    },
                                  })
                                }
                              >
                                판매
                              </Button>
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
