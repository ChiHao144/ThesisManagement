import { useContext } from "react";
import { Button, Container, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";

const Header = () => {
    const [user, dispatch] = useContext(MyUserContext);


    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Link to="/" className="nav-link">Trang chủ</Link>
                            {user === null ? <>
                                <Link to="/login" className="nav-link text-info">ĐĂNG NHẬP</Link>
                            </> : <>
                                <Link to="/" className="nav-link text-info">
                                    <img src={user.avatar} width={30} height={30} className="rounded-circle" alt="avatar" />
                                    <span className="text-info">Chào {user.username}!</span>
                                </Link>
                                <Button variant="danger" onClick={() => dispatch({"type": "logout"})}>ĐĂNG XUẤT</Button>
                            </>}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}
export default Header;