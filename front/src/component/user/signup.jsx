import { useState } from "react";
import { Header } from "./../common/header";
import { Footer } from "./../common/footer";
import { signupDefaultValidation } from "../../util/validationUtil";
import { ValidationMsg } from "../common/validationMsg";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { regUtil } from "../../util/regUtil";

export const Signup = () => {
  const navigate = useNavigate();
  const [signupData, setSignupData] = useState({
    name: "",
    email: "",
    phone: "",
    loginId: "",
    password: "",
    checkPassword: "",
    role: "USER",
  });
  const [validaiton, setValidation] = useState(signupDefaultValidation);

  const onChangeSignupData = (e) => {
    setSignupData({ ...signupData, [e.target.name]: e.target.value });
  };

  const msg = (field, type) => {
    return type === "null"
      ? field + " 입력해주세요."
      : field + "의 형식에 맞지 않습니다.";
  };

  const onClickSignup = () => {
    const valid = {
      nullName: !Boolean(signupData.name),
      name: signupData.name < 2,
      nullEmail: !Boolean(signupData.email),
      email: !regUtil("email").test(signupData.email),
      nullPhone: !Boolean(signupData.phone),
      phone: !regUtil("phone").test(signupData.phone),
      nullLoginId: !Boolean(signupData.loginId),
      loginId: !regUtil("loginId").test(signupData.loginId),
      nullPassword: !Boolean(signupData.password),
      password: !regUtil("password").test(signupData.password),
      checkPassword: signupData.checkPassword !== signupData.password,
    };

    setValidation(valid);

    if (!Object.values(valid).includes(true)) {
      axios
        .post(process.env.PUBLIC_URL + "/api/user/save_user", signupData)
        .then((res) => {
          if (res.data.code === "0000") {
            alert("회원가입이 완료되었습니다.");
            navigate("/login");
          } else {
            alert(res.data.msg);
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
              <h1>Signup</h1>
              <div className="form-group">
                <label for="name" style={{ float: "left" }}>
                  Name
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  placeholder="Name"
                  value={signupData.name}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullName || validaiton.name,
                  validaiton.nullName
                    ? msg("이름을", "null")
                    : msg("이름", "not")
                )}
                <label
                  for="userId"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Id
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="userId"
                  name="loginId"
                  placeholder="Id"
                  value={signupData.loginId}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullLoginId || validaiton.loginId,
                  validaiton.nullLoginId
                    ? msg("아이디를", "null")
                    : msg("아이디", "not")
                )}
                <label
                  for="userPw"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Password
                </label>{" "}
                <input
                  type="password"
                  className="form-control"
                  id="userPw"
                  name="password"
                  placeholder="Password"
                  value={signupData.password}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullPassword || validaiton.password,
                  validaiton.nullPassword
                    ? msg("비밀번호를", "null")
                    : msg("비밀번호", "not")
                )}
                <label
                  for="userPwCh"
                  style={{ float: "left", marginTop: "15px" }}
                >
                  Password Check
                </label>{" "}
                <input
                  type="password"
                  className="form-control"
                  id="userPwCh"
                  name="checkPassword"
                  placeholder="Password Check"
                  value={signupData.checkPassword}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.checkPassword,
                  "비밀번호와 일치하지 않습니다."
                )}
                <label for="email" style={{ float: "left", marginTop: "15px" }}>
                  Email
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="email"
                  name="email"
                  placeholder="email"
                  value={signupData.email}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullEmail || validaiton.email,
                  validaiton.nullEmail
                    ? msg("이메일을", "null")
                    : msg("이메일", "not")
                )}
                <label for="phone" style={{ float: "left", marginTop: "15px" }}>
                  Phone
                </label>{" "}
                <input
                  type="number"
                  className="form-control"
                  id="phone"
                  name="phone"
                  placeholder="phone"
                  value={signupData.phone}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullPhone || validaiton.phone,
                  validaiton.nullPhone
                    ? msg("휴대폰 번호를", "null")
                    : msg("휴대폰 번호", "not")
                )}
              </div>
              <button
                type="button"
                classNameName="btn btn-primary"
                onClick={onClickSignup}
              >
                Sign in
              </button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
