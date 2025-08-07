import { useContext } from "react";
import { Alert, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";

const Home = () => {
    const [user] = useContext(MyUserContext);  // lấy thông tin user đã đăng nhập
    const nav = useNavigate();

    return (
        <>
            <h1>WELCOME THESIS MANAGEMENT</h1>

            
        </>
    );
}

export default Home;
