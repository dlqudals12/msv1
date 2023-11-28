import { useEffect, useState } from "react";
import { Header } from "./../common/header";
import { Footer } from "./../common/footer";
import { signupDefaultValidation } from "../../util/validationUtil";
import { ValidationMsg } from "../common/validationMsg";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { regUtil } from "../../util/regUtil";
import Button from "react-bootstrap/esm/Button";

export const UserInfo = () => {
  const navigate = useNavigate();
  const [userInfo, setUserInfo] = useState({
    name: "",
    email: "",
    phone: "",
    loginId: "",
    password: "",
    checkPassword: "",
    point: 0,
  });
  const [validaiton, setValidation] = useState(signupDefaultValidation);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    axios
      .get(process.env.PUBLIC_URL + "/api/user/user_info")
      .then((res) => {
        const data = res.data.result;

        if (res.data.code === "0000") {
          setUserInfo({
            name: data.name,
            email: data.email,
            phone: data.phone,
            loginId: data.loginId,
            password: "",
            checkPassword: "",
            point: data.point,
          });
        } else {
          alert(res.data.msg);
        }
      })
      .catch((e) => {
        alert("시스템 오류");
      });
  }, [refresh]);

  const onChangeSignupData = (e) => {
    setUserInfo({ ...userInfo, [e.target.name]: e.target.value });
  };

  const msg = (field, type) => {
    return type === "null"
      ? field + " 입력해주세요."
      : field + "의 형식에 맞지 않습니다.";
  };

  const onClickUpdateUser = (type) => {
    const valid =
      type === "password"
        ? {
            ...validaiton,
            nullPassword: !Boolean(userInfo.password),
            password: !regUtil("password").test(userInfo.password),
            checkPassword: userInfo.checkPassword !== userInfo.password,
          }
        : {
            ...validaiton,
            nullName: !Boolean(userInfo.name),
            name: userInfo.name < 2,
            nullEmail: !Boolean(userInfo.email),
            email: !regUtil("email").test(userInfo.email),
            nullPhone: !Boolean(userInfo.phone),
            phone: !regUtil("phone").test(userInfo.phone),
          };

    setValidation(valid);

    if (!Object.values(valid).includes(true)) {
      axios
        .post(process.env.PUBLIC_URL + "/api/user/update_user", {
          ...userInfo,
          type: type,
        })
        .then((res) => {
          if (res.data.code === "0000") {
            alert(
              (type === "password" ? "비밀번호" : "회원정보") +
                " 변경이 완료되었습니다."
            );
            setRefresh(!refresh);
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
              <h1>회원정보</h1>
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
                  value={userInfo.name}
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
                  disabled
                  value={userInfo.loginId}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullLoginId || validaiton.loginId,
                  validaiton.nullLoginId
                    ? msg("아이디를", "null")
                    : msg("아이디", "not")
                )}
                <label for="point" style={{ float: "left", marginTop: "15px" }}>
                  Point
                </label>{" "}
                <input
                  type="number"
                  className="form-control"
                  disabled
                  id="point"
                  name="point"
                  placeholder="point"
                  value={userInfo.point}
                />
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
                  value={userInfo.password}
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
                <label
                  style={{
                    position: "relative",
                    width: "100%",
                    marginBottom: "0",
                  }}
                >
                  <input
                    type="password"
                    className="form-control"
                    id="userPwCh"
                    name="checkPassword"
                    placeholder="Password Check"
                    value={userInfo.checkPassword}
                    onChange={onChangeSignupData}
                  />
                  <Button
                    style={{
                      position: "absolute",
                      top: "5px",
                      right: "5px",
                      fontSize: "12px",
                      padding: "4px",
                    }}
                    onClick={() => onClickUpdateUser("password")}
                  >
                    비밀번호 변경{" "}
                  </Button>
                  {ValidationMsg(
                    validaiton.checkPassword,
                    "비밀번호와 일치하지 않습니다."
                  )}
                </label>
                <label for="email" style={{ float: "left", marginTop: "15px" }}>
                  Email
                </label>{" "}
                <input
                  type="text"
                  className="form-control"
                  id="email"
                  name="email"
                  placeholder="email"
                  value={userInfo.email}
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
                  value={userInfo.phone}
                  onChange={onChangeSignupData}
                />
                {ValidationMsg(
                  validaiton.nullPhone || validaiton.phone,
                  validaiton.nullPhone
                    ? msg("휴대폰 번호를", "null")
                    : msg("휴대폰 번호", "not")
                )}
              </div>
              <Button
                classNameName="btn btn-primary"
                onClick={() => {
                  onClickUpdateUser("common");
                }}
              >
                변경
              </Button>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
