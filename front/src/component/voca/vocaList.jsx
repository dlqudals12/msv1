import { useNavigate } from "react-router-dom";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import { useEffect, useState } from "react";
import { CheckLg } from "react-bootstrap-icons";
import axios from "axios";
import Pagination from "react-bootstrap/Pagination";

export const VocaList = () => {
  const navigate = useNavigate();
  const [vocaList, setVocaList] = useState([]);
  const [filter, setFilter] = useState({
    vocaName: "",
    page: 1,
  });
  const [pagination, setPagination] = useState({
    total: 0,
    maxPage: 0,
    start: 1,
  });

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
          });
        }
      });
  }, []);

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
              <h1 className="display-1">VocaList</h1>
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
                      <th>Vocaname</th>
                      <th>Country</th>
                      <th style={{ width: "150px", textAlign: "center" }}>
                        Owner
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    {vocaList[0] &&
                      vocaList.map((item, index) => (
                        <>
                          <tr
                            style={{ cursor: "pointer" }}
                            onClick={() => {
                              navigate("/voca/detail", {
                                state: {
                                  vocaId: item.vocaId,
                                  isOwn: item.own,
                                },
                              });
                            }}
                          >
                            <td style={{ textAlign: "center" }}>{index + 1}</td>
                            <td>{item.vocaName}</td>
                            <td>{item.country}</td>
                            <td style={{ textAlign: "center" }}>
                              {item.own ? (
                                <>
                                  <CheckLg />
                                </>
                              ) : (
                                <></>
                              )}
                            </td>
                          </tr>
                        </>
                      ))}
                  </tbody>
                </table>
                <Pagination>
                  <Pagination.Prev />
                  {pagination.total !== 0 ? (
                    Array.from(
                      { length: 10 },
                      (_, i) => i + pagination.start
                    ).map((item) => (
                      <>
                        <Pagination.Item
                          active={item === filter.page}
                          activeLabel=""
                        >
                          {item}
                        </Pagination.Item>
                      </>
                    ))
                  ) : (
                    <Pagination.Item>1</Pagination.Item>
                  )}
                  <Pagination.Next />
                </Pagination>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
