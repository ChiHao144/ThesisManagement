import { useContext, useEffect, useState } from "react";
import { Card, Button, Container, Row, Col, Spinner } from "react-bootstrap";
import { Link } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const Home = () => {
    const [user,] = useContext(MyUserContext);
    const [hoiDongs, setHoiDongs] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadHoiDongs = async () => {
            if (!user) return; 

            try {
                let endpoint = endpoints['hoidongs-giangvien'].replace('{giangVienId}', user.id);
                
                let res = await authApis().get(endpoint);
                setHoiDongs(res.data);
                setError(null);
            } catch (ex) {
                console.error(ex);
                setError("Không thể tải danh sách hội đồng.");
                setHoiDongs([]);
            } finally {
                setLoading(false);
            }
        };

        setLoading(true);
        loadHoiDongs();
    }, [user]);

    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" variant="primary" />
                <p>Đang tải dữ liệu...</p>
            </Container>
        );
    }

    if (error) {
         return <Container className="text-center mt-5"><p className="text-danger">{error}</p></Container>
    }

    return (
        <Container className="mt-4">
            <h1 className="text-center text-primary mb-4">DANH SÁCH HỘI ĐỒNG</h1>
            
            {hoiDongs && hoiDongs.length > 0 ? (
                <Row>
                    {hoiDongs.map(hd => (
                        <Col md={6} lg={4} key={hd.id} className="mb-4">
                            <Card className="h-100 shadow-sm">
                                <Card.Body>
                                    {/* CẬP NHẬT: Hiển thị noiDungBaoVe thay vì tenHoiDong */}
                                    <Card.Title>{hd.noiDungBaoVe || `Hội đồng #${hd.id}`}</Card.Title>
                                    <Card.Subtitle className="mb-2 text-muted">
                                        {/* CẬP NHẬT: Hiển thị ngayBaoVe thay vì ngayTao */}
                                        Ngày bảo vệ: {hd.ngayBaoVe ? new Date(hd.ngayBaoVe).toLocaleDateString() : 'Chưa có'}
                                    </Card.Subtitle>
                                    <Card.Text>
                                        {/* CẬP NHẬT: Hiển thị trạng thái dựa trên trường daKhoa */}
                                        Trạng thái: 
                                        <span className={hd.daKhoa ? "fw-bold text-danger" : "fw-bold text-success"}>
                                            {hd.daKhoa ? " Đã khóa" : " Đang hoạt động"}
                                        </span>
                                    </Card.Text>
                                    {/* Nút vào chấm điểm sẽ bị vô hiệu hóa nếu hội đồng đã khóa */}
                                    <Link to={`/hoidong/${hd.id}/khoaluan`}>
                                        <Button variant="outline-primary" disabled={hd.daKhoa}>Vào chấm điểm</Button>
                                    </Link>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            ) : (
                <p className="text-center text-muted">Bạn chưa được phân công vào hội đồng nào.</p>
            )}
        </Container>
    );
};

export default Home;
