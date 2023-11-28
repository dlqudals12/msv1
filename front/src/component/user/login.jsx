import { useEffect, useState } from "react";
import { Footer } from "../common/footer";
import { Header } from "../common/header";
import { regUtil } from "../../util/regUtil";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import { ValidationMsg } from "../common/validationMsg";
import { useCookies } from "react-cookie";

const defaultValidation = {
  nullLogin: false,
  login: false,
  nullPassword: false,
  password: false,
};

export const Login = () => {
  const navigate = useNavigate();
  const [loginData, setLoginData] = useState({
    loginId: "",
    password: "",
  });
  const [validation, setValidation] = useState(defaultValidation);
  const [cookies, ,] = useCookies("userId");

  useEffect(() => {
    if (cookies.userId) navigate("/");
  }, []);

  const onClickLogin = () => {
    const valid = {
      nullLogin: !Boolean(loginData.loginId),
      login: !regUtil("loginId").test(loginData.loginId),
      nullPassword: !Boolean(loginData.password),
      password: !regUtil("password").test(loginData.password),
    };

    setValidation(valid);

    if (!Object.values(valid).includes(true)) {
      axios
        .post(process.env.PUBLIC_URL + "/api/user/login", loginData)
        .then((res) => {
          if (res.data.code === "0000") {
            navigate("/");
          } else {
            alert(res.data.msg);
          }
        })
        .catch((e) => {
          alert("서버 오류입니다.");
        });
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
          minHeight: "830px",
        }}
      >
        <div className="container" style={{ marginTop: "130px" }}>
          <div className="row">
            <div className="mx-auto col-md-6 col-10 bg-white p-5">
              <h1 className="mb-4">Log in</h1>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Id"
                  value={loginData.loginId}
                  onChange={(e) => {
                    setLoginData({ ...loginData, loginId: e.target.value });
                  }}
                />
                {ValidationMsg(
                  validation.nullLogin || validation.login,
                  validation.nullLogin
                    ? "아이디를 입력해 주세요"
                    : "아이디 형식이 맞지 않습니다."
                )}
              </div>
              <div className="form-group mb-3">
                <input
                  type="password"
                  className="form-control"
                  placeholder="Password"
                  value={loginData.password}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      onClickLogin();
                    }
                  }}
                  onChange={(e) => {
                    setLoginData({ ...loginData, password: e.target.value });
                  }}
                />
                {ValidationMsg(
                  validation.nullPassword || validation.password,
                  validation.nullLogin
                    ? "비밀번호를 입력해 주세요"
                    : "비밀번호 형식이 맞지 않습니다."
                )}

                <small className="form-text text-muted text-right">
                  <a href="#"> Recover password</a>
                </small>
              </div>
              <button
                type="button"
                onClick={onClickLogin}
                className="btn btn-primary"
              >
                Login
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
