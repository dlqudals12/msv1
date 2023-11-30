import { useState } from "react";
import { Header } from "../common/header";
import { Footer } from "../common/footer";
import { useNavigate } from "react-router-dom";
import { newVocaDefaultValidation } from "../../util/validationUtil";
import axios from "axios";
import { ValidationMsg } from "../common/validationMsg";
import Button from "react-bootstrap/esm/Button";

export const NewVoca = () => {
  const navigate = useNavigate();
  const [vocaData, setVocaData] = useState({
    vocaName: "",
    country: "",
    column1: "",
    column2: "",
    column3: "",
    column4: "",
  });
  const [validation, setValidation] = useState(newVocaDefaultValidation);

  const onChangeVocaData = (e) => {
    setVocaData({ ...vocaData, [e.target.name]: e.target.value });
  };

  const onClickNewVoca = () => {
    const valid = {
      nullVocaName: !Boolean(vocaData.vocaName),
      nullCountry: !Boolean(vocaData.country),
      nullColumn1: !Boolean(vocaData.column1),
      nullColumn2: !Boolean(vocaData.column2),
      nullColumn4: !Boolean(vocaData.column3) && Boolean(vocaData.column4),
    };

    setValidation(valid);

    if (!Object.values(valid).includes(true)) {
      axios
        .post(process.env.PUBLIC_URL + "/api/voca/save_voca", vocaData)
        .then((res) => {
          if (res.data.code === "0000") {
            alert("단어장 등록이 완료되었습니다.");
            navigate("/voca/detail", {
              state: {
                vocaId: res.data.result,
                isOwn: true,
              },
            });
          } else {
            alert(res.data.msg);
          }
        })
        .catch((e) => {
          alert("시스템 오류");
        });
    }
  };

  const msg = (msg) => {
    return msg + "입력해 주세요.";
  };

  return (
    <>
      <Header />
      <div className="py-5 text-center">
        <div className="container">
          <div className="row">
            <div className="mx-auto col-lg-6 col-10">
              <h1>Voca 등록</h1>
              <div className="form-group">
                <label for="vocaName" style={{ float: "left" }}>
                  VocaName <span style={{ color: "red" }}>*</span>
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="vocaName"
                  name="vocaName"
                  placeholder="VocaName"
                  value={vocaData.vocaName}
                  onChange={onChangeVocaData}
                />
                {validation.nullVocaName &&
                  ValidationMsg(validation.nullVocaName, msg("단어명을"))}
                <label
                  for="country"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Country <span style={{ color: "red" }}>*</span>
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="country"
                  name="country"
                  placeholder="Country"
                  value={vocaData.country}
                  onChange={onChangeVocaData}
                />
                {validation.nullCountry &&
                  ValidationMsg(validation.nullCountry, msg("국가명을"))}
                <label
                  for="column1"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  First Column <span style={{ color: "red" }}>*</span>
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="column1"
                  name="column1"
                  placeholder="First Column"
                  value={vocaData.column1}
                  onChange={onChangeVocaData}
                />
                {validation.nullColumn1 &&
                  ValidationMsg(validation.nullColumn1, msg("첫 번째 컬럼을"))}
                <label
                  for="column2"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Second Column <span style={{ color: "red" }}>*</span>
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="column2"
                  name="column2"
                  placeholder="Second Column"
                  value={vocaData.column2}
                  onChange={onChangeVocaData}
                />
                {validation.nullColumn2 &&
                  ValidationMsg(validation.nullColumn2, msg("두 번째 컬럼을"))}
                <label
                  for="column3"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Third Column
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="column3"
                  name="column3"
                  placeholder="Third Column"
                  value={vocaData.column3}
                  onChange={onChangeVocaData}
                />
                <label
                  for="phocolumn4ne"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Firth Column
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="column4"
                  name="column4"
                  placeholder="Firth Column"
                  value={vocaData.column4}
                  onChange={onChangeVocaData}
                />
                {validation.nullColumn4 &&
                  ValidationMsg(
                    validation.nullColumn4,
                    msg("세 번째 컬럼을 입력 후 입력해 주세요")
                  )}
              </div>
              <Button
                type="button"
                classNameName="btn btn-primary"
                onClick={onClickNewVoca}
              >
                등록
              </Button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
