import "./App.css";
import "./wireframe.css";
import { Routes, Route, useLocation, useNavigate } from "react-router-dom";
import { Main } from "./component/main/main";
import { Login } from "./component/user/login";
import { Signup } from "./component/user/signup";
import { NewVoca } from "./component/voca/newVoca";
import { VocaDetail } from "./component/voca/vocaDetail";
import { VocaList } from "./component/voca/vocaList";
import { UserInfo } from "./component/user/userInfo";
import { VocaBoardNew } from "./component/vocaBoard/vocaBoardNew";
import { VocaBoardList } from "./component/vocaBoard/vocaBoardList";
import { VocaBoardDetail } from "./component/vocaBoard/vocaBoardDetail";
import { ChargePoint } from "./component/point/chargePoint";
import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { ReceiptList } from "./component/point/receiptList";

const includeUrls = [
  "/login",
  "/signup",
  "/",
  "/vocaboard/list",
  "/vocaboard/detail",
];

const App = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [cookies, ,] = useCookies("userId");

  useEffect(() => {
    if (!includeUrls.includes(location.pathname) && !cookies.userId) {
      alert("로그인 후 이용해주세요.");
      navigate("/login");
    }
  }, []);
  return (
    <div>
      <Routes>
        <Route path={"/"} element={<Main />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/userInfo" element={<UserInfo />} />
        <Route path="/voca/new" element={<NewVoca />} />
        <Route path="/voca/detail" element={<VocaDetail />} />
        <Route path="/voca/list" element={<VocaList />} />
        <Route path="/vocaboard/new" element={<VocaBoardNew />} />
        <Route path="/vocaboard/list" element={<VocaBoardList />} />
        <Route path="/vocaboard/detail" element={<VocaBoardDetail />} />
        <Route path="/point/receipt" element={<ReceiptList />} />
        <Route path="/point/charge" element={<ChargePoint />} />
      </Routes>
    </div>
  );
};

export default App;
