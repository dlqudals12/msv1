import "./App.css";
import "./wireframe.css";
import { Routes, Route } from "react-router-dom";
import { Main } from "./component/main/main";
import { Login } from "./component/user/login";
import { Signup } from "./component/user/signup";
import { NewVoca } from "./component/voca/newVoca";
import { VocaDetail } from "./component/voca/vocaDetail";
import { VocaList } from "./component/voca/vocaList";

const App = () => {
  return (
    <div>
      <Routes>
        <Route path={"/"} element={<Main />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/voca/new" element={<NewVoca />} />
        <Route path="/voca/detail" element={<VocaDetail />} />
        <Route path="/voca/list" element={<VocaList />} />
      </Routes>
    </div>
  );
};

export default App;
