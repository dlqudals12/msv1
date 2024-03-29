import { Link, useLocation, useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import Dropdown from "react-bootstrap/Dropdown";
import axios from "axios";
import { useAtom } from "jotai";
import { IsLogin } from "../data/atom";
import { useEffect } from "react";

export const Header = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [cookies, ,] = useCookies("userId");
  const [loginBool, setLoginBool] = useAtom(IsLogin);

  useEffect(() => {
    setLoginBool(Boolean(cookies.userId));
  }, []);

  const logoutUser = () => {
    axios.post(process.env.PUBLIC_URL + "/api/user/logout").then((res) => {
      setLoginBool(false);
      navigate("/");
      sessionStorage.removeItem("isUpdate");
    });
  };

  return (
    <>
      <div className="d-flex flex-column flex-md-row align-items-center p-3 mb-3 box-shadow border-bottom">
        <a className="my-0 mr-md-auto font-weight-normal" href="/">
          Home
        </a>
        <nav className="my-2 my-md-0 mr-md-3">
          <div className="btn-group">
            <div className="dropdown show">
              <Dropdown>
                <Dropdown.Toggle
                  className="btn dropdown-toggle"
                  to="#"
                  role="button"
                  id="dropdownMenuLink"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  Point
                </Dropdown.Toggle>

                <Dropdown.Menu
                  className="dropdown-menu"
                  aria-labelledby="dropdownMenuLink"
                >
                  {/* <Dropdown.Item
                    className="dropdown-item"
                    href="/point/exchange"
                  >
                    환전
                  </Dropdown.Item>
                  <Dropdown.Item
                    className="dropdown-item"
                    href="/point/charge/list"
                  >
                    환전 내역
                  </Dropdown.Item> */}
                  <Dropdown.Item
                    className="dropdown-item"
                    href="/point/exchange"
                  >
                    환전
                  </Dropdown.Item>
                  <Dropdown.Item
                    className="dropdown-item"
                    href="/point/exchange/list"
                  >
                    환전 리스트
                  </Dropdown.Item>
                  <Dropdown.Item className="dropdown-item" href="/point/charge">
                    포인트 충전
                  </Dropdown.Item>
                  <Dropdown.Item
                    className="dropdown-item"
                    href="/point/receipt"
                  >
                    포인트 거래내역
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </div>

            <div className="dropdown show" style={{ marginLeft: "15px" }}>
              <Dropdown>
                <Dropdown.Toggle
                  className="btn dropdown-toggle"
                  to="#"
                  role="button"
                  id="dropdownMenuLink"
                  data-toggle="dropdown"
                  aria-haspopup="true"
                  aria-expanded="false"
                >
                  Voca
                </Dropdown.Toggle>
                <Dropdown.Menu
                  className="dropdown-menu"
                  aria-labelledby="dropdownMenuLink"
                >
                  <Dropdown.Item className="dropdown-item" href="/voca/new">
                    Voca 등록
                  </Dropdown.Item>
                  <Dropdown.Item className="dropdown-item" href="/voca/list">
                    My Voca
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </div>
            <Dropdown className="dropdown show" style={{ marginLeft: "15px" }}>
              <Dropdown.Toggle
                className="btn dropdown-toggle"
                to="#"
                role="button"
                id="dropdownMenuLink"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                Voca 거래공간
              </Dropdown.Toggle>
              <Dropdown.Menu
                className="dropdown-menu"
                aria-labelledby="dropdownMenuLink"
              >
                <Dropdown.Item className="dropdown-item" href="/vocaboard/list">
                  Voca 거래소
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </div>
        </nav>
        {loginBool ? (
          <>
            <Link to="/userInfo" className="btn btn-outline-info">
              {cookies.userId} 정보
            </Link>
            <Link
              to=""
              className="btn btn-outline-info"
              onClick={logoutUser}
              style={{ marginLeft: "15px" }}
            >
              Logout
            </Link>
          </>
        ) : (
          <>
            <Link className="btn btn-outline-info" to="/signup">
              Sign up
            </Link>
            <Link
              className="btn btn-outline-info"
              to="/login"
              style={{ marginLeft: "15px" }}
            >
              Login
            </Link>
          </>
        )}
      </div>
    </>
  );
};
