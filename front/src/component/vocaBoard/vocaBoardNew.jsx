import { Header } from "../common/header";
import { Footer } from "../common/footer";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { ValidationMsg } from "../common/validationMsg";
import Button from "react-bootstrap/esm/Button";
import { newVocaBoardDefaultValidation } from "../../util/validationUtil";
import axios from "axios";

export const VocaBoardNew = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [vocaInfo, setVocaInfo] = useState({});
  const [vocaBoardData, setVocaBoardData] = useState({
    title: "",
    board: "",
    point: 0,
  });
  const [validaiton, setValidation] = useState(newVocaBoardDefaultValidation);

  useEffect(() => {
    if (location.state) {
      setVocaInfo(location.state);
    } else {
      navigate("/");
    }
  });

  const onClickSave = () => {
    const valid = {
      nullTitle: !Boolean(vocaBoardData.title),
      point: vocaBoardData.point < 1,
      nullDescription: !Boolean(vocaBoardData.board),
    };

    setValidation(valid);

    if (!Object.values(valid).includes(true)) {
      axios
        .post(process.env.PUBLIC_URL + "/api/voca_board/save_voca_board", {
          ...vocaBoardData,
          vocaId: vocaInfo.vocaId,
        })
        .then((res) => {
          if (res.data.code === "0000") {
            alert("거래소에 등록하었습니다.");
            navigate("/vocaboard/list");
          } else {
            alert(res.data.msg);
          }
        })
        .catch(() => {
          alert("시스템 오류");
        });
    }
  };

  const onChangeValue = (e) => {
    const isNum =
      (typeof vocaBoardData[e.target.name]).toUpperCase() === "NUMBER";

    setVocaBoardData({
      ...vocaBoardData,
      [e.target.name]: isNum ? Number(e.target.value) : e.target.value,
    });
  };

  return (
    <>
      <Header />
      <div className="py-5 text-center">
        <div className="container">
          <div className="row">
            <div className="mx-auto col-lg-6 col-10">
              <h1>거래소 등록</h1>
              <div className="form-group">
                <label for="" style={{ float: "left" }}>
                  단어장 이름
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="vocaName"
                  name="vocaName"
                  value={vocaInfo?.vocaName}
                  disabled
                />
                <label for="title" style={{ float: "left", marginTop: "15px" }}>
                  제목
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="title"
                  name="title"
                  placeholder="제목"
                  value={vocaBoardData.title}
                  onChange={onChangeValue}
                />
                {ValidationMsg(validaiton.nullTitle, "제목을 입력해주세요.")}
                <label for="point" style={{ float: "left", marginTop: "15px" }}>
                  판매 포인트
                </label>{" "}
                <input
                  type="number"
                  className="form-control"
                  id="point"
                  name="point"
                  value={vocaBoardData.point}
                  onChange={onChangeValue}
                />
                {ValidationMsg(
                  validaiton.point,
                  "판매 포인트는 1이상으로 입력해 주세요."
                )}
                <label for="board" style={{ float: "left", marginTop: "15px" }}>
                  설명
                </label>{" "}
                <textarea
                  type="text"
                  className="form-control"
                  id="board"
                  name="board"
                  placeholder="설명"
                  value={vocaBoardData.board}
                  onChange={onChangeValue}
                />
                {ValidationMsg(
                  validaiton.nullDescription,
                  "설명을 입력해주세요."
                )}
                <Button
                  className="btn btn-primary"
                  style={{ marginTop: "30px" }}
                  onClick={onClickSave}
                >
                  New
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
