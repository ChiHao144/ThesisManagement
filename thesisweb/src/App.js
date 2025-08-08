import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import Login from "./components/Login";
import { MyUserContext } from "./configs/Contexts";
import MyUserReducer from "./reducers/MyUserReducer";
import { useReducer } from "react";
import ChangePassword from "./components/ChangePassword";
import KhoaLuanList from "./components/KhoaLuanList";
import ChamDiem from "./components/ChamDiem";

const App = () => {
  let [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <MyUserContext.Provider value={[user, dispatch]}>
      <BrowserRouter>
        <Header />

        <Container>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/change-password" element={<ChangePassword />} />
            <Route path="/hoidong/:hoiDongId/khoaluan" element={<KhoaLuanList />} />
            <Route path="/khoaluan/:khoaLuanId/chamdiem" element={<ChamDiem />} />
          </Routes>
        </Container>

        <Footer />
      </BrowserRouter>
    </MyUserContext.Provider>
  );
}

export default App;