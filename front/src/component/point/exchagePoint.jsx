import { useEffect, useState } from "react";
import { Header } from "../common/header";
import { Footer } from "../common/footer";
import { useNavigate } from "react-router-dom";
import {
  newExchangeDefaultValidation,
  newVocaDefaultValidation,
} from "../../util/validationUtil";
import axios from "axios";
import { ValidationMsg } from "../common/validationMsg";
import Button from "react-bootstrap/esm/Button";

export const ExchangePoint = () => {
  const navigate = useNavigate();
  const [exchangeData, setExchangeData] = useState({
    bank: "",
    bankNum: "",
    name: "",
    money: "",
  });
  const [exchangeList, setExchangeList] = useState([]);
  const [validation, setValidation] = useState(newExchangeDefaultValidation);
  const [pointNow, setPointNow] = useState(0);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    axios
      .get(process.env.PUBLIC_URL + "/api/user/user_info")
      .then((res) => {
        setPointNow(res.data.result.point);
      })
      .catch((e) => {});
  }, [refresh]);

  const onChangeExchangeData = (e) => {
    setExchangeData({ ...exchangeData, [e.target.name]: e.target.value });
  };

  const onClickNewExchange = () => {
    const valid = {
      money: !Boolean(exchangeData.money) || exchangeData.money > pointNow,
      bank: !Boolean(exchangeData.bank) || exchangeData.bank.length < 2,
      bankNum:
        !Boolean(exchangeData.bankNum) || exchangeData.bankNum.length < 8,
      name: !Boolean(exchangeData.name),
    };

    setValidation(valid);

    if (!Object.values(valid).includes(true)) {
      axios
        .post(process.env.PUBLIC_URL + "/api/exchange/save", exchangeData)
        .then((res) => {
          if (res.data.code === "0000") {
            alert("등록 완료하였습니다.");
            navigate("/point/exchange/list");
          } else {
            alert("res.data.code");
          }
        })
        .catch((e) => {
          alert("시스템 오류");
        });
    }
  };

  return (
    <>
      <Header />
      <div className="py-5 text-center">
        <div className="container">
          <div className="row">
            <div className="mx-auto col-lg-6 col-10">
              <h1>환전</h1>
              <div className="form-group">
                <label for="bank" style={{ float: "left" }}>
                  은행명 <span style={{ color: "red" }}>*</span>
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="bank"
                  name="bank"
                  placeholder="bank"
                  value={exchangeData.bank}
                  onChange={onChangeExchangeData}
                />
                {validation.bank &&
                  ValidationMsg(validation.bank, "은행명을 확인해주세요.")}
                <label
                  for="bankNum"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  계좌 번호 <span style={{ color: "red" }}>*</span>
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="bankNum"
                  name="bankNum"
                  placeholder="bankNum"
                  value={exchangeData.bankNum}
                  onChange={onChangeExchangeData}
                />
                {validation.bankNum &&
                  ValidationMsg(validation.bankNum, "계좌번호를 확인해주세요.")}
                <label for="name" style={{ float: "left", marginTop: "15px" }}>
                  입금자명 <span style={{ color: "red" }}>*</span>
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  placeholder="name"
                  value={exchangeData.name}
                  onChange={onChangeExchangeData}
                />
                {validation.name &&
                  ValidationMsg(validation.name, "입금자명을 확인해주세요.")}
                <label for="money" style={{ float: "left", marginTop: "15px" }}>
                  금액 <span style={{ color: "red" }}>*</span>
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="money"
                  name="money"
                  placeholder="money"
                  value={exchangeData.money}
                  onChange={onChangeExchangeData}
                />
                {validation.money &&
                  ValidationMsg(validation.money, "금액을 확인해주세요.")}
              </div>
              <Button
                type="button"
                classNameName="btn btn-primary"
                onClick={onClickNewExchange}
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
