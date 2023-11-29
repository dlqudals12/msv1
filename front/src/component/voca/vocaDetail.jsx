import { useRef, useState } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import Table from "react-bootstrap/Table";
import { useEffect } from "react";
import axios from "axios";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

export const VocaDetail = () => {
  const focusRef = useRef();
  const navigate = useNavigate();
  const location = useLocation();
  const [refresh, setRefresh] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();
  const [vocaInfo, setVocaInfo] = useState();
  const [vocaWordList, setVocaWordList] = useState([]);
  const [isOwn, setIsOwn] = useState(false);
  const [maxCol, setMaxCol] = useState(0);

  useEffect(() => {
    if (location.state) {
      const vocaId = location.state.vocaId;
      axios
        .get(
          process.env.PUBLIC_URL + "/api/voca/voca_word_list?vocaId=" + vocaId
        )
        .then((res) => {
          const data = res.data.result;

          setVocaInfo(data.voca);

          if (Boolean(data.voca.column4)) {
            setMaxCol(4);
          } else if (Boolean(data.voca.column3)) {
            setMaxCol(3);
          } else if (Boolean(data.voca.column2)) {
            setMaxCol(2);
          }

          if (location.state.isOwn) {
            setVocaWordList([
              ...data.vocaWordList.map((data, index) => {
                return {
                  vocaWordId: Number(data.id),
                  vocaId: Number(vocaId),
                  word1: data.word1,
                  word2: data.word2,
                  word3: data.word3,
                  word4: data.word5,
                  write: false,
                  isNew: false,
                };
              }),
              {
                vocaWordId: null,
                vocaId: Number(vocaId),
                word1: "",
                word2: "",
                word3: "",
                word4: "",
                write: true,
                isNew: true,
              },
            ]);

            setIsOwn(location.state.isOwn);
          } else {
            setVocaWordList(
              data.vocaWordList.map((data, index) => {
                return {
                  vocaWordId: Number(data.id),
                  vocaId: Number(vocaId),
                  word1: data.word1,
                  word2: data.word2,
                  word3: data.word3,
                  word4: data.word5,
                  write: false,
                  isNew: false,
                };
              })
            );
          }
        });
    } else {
      navigate("/");
    }
  }, [refresh]);

  const onChangeWord = (e, index) => {
    setVocaWordList(
      vocaWordList.map((item, indexs) => {
        return index === indexs
          ? {
              ...item,
              [e.target.name]: e.target.value,
            }
          : item;
      })
    );
  };

  const onClickPost = (item, index) => {
    if (item.write) {
      const valid = {
        word1: !Boolean(item.word1),
        word2: !Boolean(item.word2),
        word3: !Boolean(item.word3) && vocaInfo.column3,
        word4: !Boolean(item.word4) && vocaInfo.column4,
      };

      if (!Object.values(valid).includes(true)) {
        axios
          .post(
            process.env.PUBLIC_URL +
              (item.isNew
                ? "/api/voca/save_voca_word"
                : "/api/voca/update_voca_word"),
            item
          )
          .then((res) => {
            if (res.data.code === "0000") {
              //alert(`단어장 요소 ${item.isNew ? "등록" : "수정"}했습니다.`);

              if (item.isNew) {
                setRefresh(!refresh);
              } else {
                setVocaWordList(
                  vocaWordList.map((items) => {
                    return item.vocaWordId === items.vocaWordId
                      ? {
                          ...items,
                          write: false,
                        }
                      : items;
                  })
                );
              }
            } else {
              alert(res.data.msg);
            }
          })
          .catch((e) => {
            alert("시스템 오류");
          });
      } else {
        alert("등록하려는 단어를 확인해 주세요.");
      }
    } else {
      setVocaWordList(
        vocaWordList.map((items) => {
          return item.vocaWordId === items.vocaWordId
            ? {
                ...items,
                write: true,
              }
            : items;
        })
      );
    }
  };

  const deleteVocaWord = (id) => {
    axios
      .delete(process.env.PUBLIC_URL + "/api/voca/delete_voca_word/" + id)
      .then((res) => {
        if ((res.data.code = "0000")) {
          alert("단어 삭제에 성공하였습니다.");
          setRefresh(!refresh);
        } else {
          alert(res.data.msg);
        }
      })
      .catch((e) => {
        alert("시스템 오류");
      });
  };

  return (
    <>
      <Header />
      <div
        className="py-5 text-center"
        style={{
          backgroundImage:
            'url("https://static.pingendo.com/cover-bubble-dark.svg")',
          backgroundSize: "cover",
        }}
      >
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-1">VocaDetail</h1>
            </div>
          </div>
        </div>
      </div>
      <div className="py-5" draggable="true">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <Card
                style={{ marginBottom: "30px", backgroundColor: "#e0e0e0" }}
              >
                <Card.Body>
                  <Card.Text
                    style={{ fontSize: "20px", fontFamily: "NotoSansKRBold" }}
                  >
                    단어장: {vocaInfo?.vocaName}
                  </Card.Text>
                  <Card.Text
                    style={{ fontSize: "20px", fontFamily: "NotoSansKRBold" }}
                  >
                    국가: {vocaInfo?.country}
                  </Card.Text>
                </Card.Body>
              </Card>
              <div className="table-responsive">
                <Table striped bordered hover>
                  <thead>
                    <tr>
                      <th>No</th>
                      <th>{vocaInfo?.column1}</th>
                      <th>{vocaInfo?.column2}</th>
                      {vocaInfo?.column3 && <th>{vocaInfo?.column3}</th>}
                      {vocaInfo?.column4 && <th>{vocaInfo?.column4}</th>}
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {vocaWordList[0] &&
                      vocaWordList.map((item, index) => (
                        <>
                          <tr>
                            <td>{index + 1}</td>
                            <td>
                              {item.write ? (
                                <>
                                  <input
                                    value={item.word1}
                                    name="word1"
                                    autoFocus
                                    onChange={(e) => {
                                      onChangeWord(e, index);
                                    }}
                                  />
                                </>
                              ) : (
                                item.word1
                              )}
                            </td>
                            <td>
                              {item.write ? (
                                <>
                                  <input
                                    value={item.word2}
                                    name="word2"
                                    onChange={(e) => {
                                      onChangeWord(e, index);
                                    }}
                                    onKeyDown={(e) => {
                                      if (e.key === "Tab" && maxCol === 2) {
                                        e.preventDefault();
                                        onClickPost(item, index);
                                      }
                                    }}
                                  />
                                </>
                              ) : (
                                item.word2
                              )}
                            </td>
                            {vocaInfo.column3 && (
                              <td>
                                {item.write ? (
                                  <>
                                    <input
                                      value={item.word3}
                                      name="word3"
                                      onChange={(e) => {
                                        onChangeWord(e, index);
                                      }}
                                      onKeyDown={(e) => {
                                        if (e.key === "Tab" && maxCol === 3) {
                                          e.preventDefault();
                                          onClickPost(item, index);
                                        }
                                      }}
                                    />
                                  </>
                                ) : (
                                  item.word3
                                )}
                              </td>
                            )}
                            {vocaInfo.column4 && (
                              <td>
                                {item.write ? (
                                  <>
                                    <input
                                      value={item.word4}
                                      name="word4"
                                      onChange={(e) => {
                                        onChangeWord(e, index);
                                      }}
                                      onKeyDown={(e) => {
                                        if (e.key === "Tab" && maxCol === 4) {
                                          e.preventDefault();
                                          onClickPost(item, index);
                                        }
                                      }}
                                    />
                                  </>
                                ) : (
                                  item.word4
                                )}
                              </td>
                            )}

                            <td style={{ padding: "10px" }}>
                              {isOwn && (
                                <div
                                  style={{
                                    textAlign: "center",
                                  }}
                                >
                                  <Button
                                    variant="secondary"
                                    style={{
                                      display: "inline-block",
                                      fontSize: "12px",
                                      height: "30px",
                                    }}
                                    onClick={() => onClickPost(item, index)}
                                  >
                                    {item.isNew ? "저장" : "수정"}
                                  </Button>
                                  {!item.isNew && (
                                    <Button
                                      style={{
                                        display: "inline-block",
                                        marginLeft: "10px",
                                        fontSize: "12px",
                                        height: "30px",
                                      }}
                                      onClick={() => {
                                        if (
                                          window.confirm(
                                            "해당 단어장 요소를 삭제 하시겠습니까?"
                                          )
                                        ) {
                                          deleteVocaWord(item.vocaWordId);
                                        }
                                      }}
                                    >
                                      삭제
                                    </Button>
                                  )}
                                </div>
                              )}
                            </td>
                          </tr>
                        </>
                      ))}
                  </tbody>
                </Table>
              </div>
              <div>
                <Button
                  className="float-right"
                  onClick={() => navigate("/voca/list")}
                >
                  목록
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
