import "./App.css";
import "./wireframe.css";
import { Routes, Route } from "react-router-dom";
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
import { VocaBoardReceipt } from "./component/vocaBoard/vocaBoardReceipt";
import { ChargePoint } from "./component/point/chargePoint";

const App = () => {
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
        <Route path="/vocaboard/receipt" element={<VocaBoardReceipt />} />
        <Route path="/point/charge" element={<ChargePoint />} />
      </Routes>
    </div>
  );
};

export default App;
