import { useContext, useEffect, useState } from "react";
import { Container, ListGroup, Spinner, Button, Card } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const KhoaLuanList = () => {
    const { hoiDongId } = useParams();
    const [user,] = useContext(MyUserContext);
    const [khoaLuans, setKhoaLuans] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const loadKhoaLuans = async () => {
            try {
                let endpoint = endpoints['khoaluans-hoidong'].replace('{hoiDongId}', hoiDongId);
                let res = await authApis().get(endpoint);
                setKhoaLuans(res.data);
                setError(null);
            } catch (ex) {
                console.error(ex);
                setError("Không thể tải danh sách khóa luận.");
                setKhoaLuans([]);
            } finally {
                setLoading(false);
            }
        };

        setLoading(true);
        loadKhoaLuans();
    }, [hoiDongId]);

    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" variant="info" />
                <p>Đang tải danh sách khóa luận...</p>
            </Container>
        );
    }

    if (error) {
        return <Container className="text-center mt-5"><p className="text-danger">{error}</p></Container>
    }

    return (
        <Container className="mt-4">
            <h1 className="text-center text-info mb-4">DANH SÁCH KHÓA LUẬN CẦN CHẤM</h1>
            
            {khoaLuans && khoaLuans.length > 0 ? (
                <ListGroup>
                    {khoaLuans.map(kl => (
                        <ListGroup.Item key={kl.id} className="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 className="mb-1">{kl.chuDe}</h5>
                                <p className="mb-1 text-muted">
                                    Sinh viên thực hiện: {kl.sinhVienId ? `${kl.sinhVienId.user.fullname}` : 'Chưa có thông tin'}
                                </p>
                            </div>
                            <Link to={`/khoaluan/${kl.id}/chamdiem`}>
                                <Button variant="outline-info">Chấm điểm</Button>
                            </Link>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            ) : (
                <Card className="text-center p-4">
                    <Card.Body>
                        <Card.Title>Không có khóa luận nào</Card.Title>
                        <Card.Text>
                            Hiện tại không có khóa luận nào được phân công trong hội đồng này.
                        </Card.Text>
                        <Link to="/">
                            <Button variant="secondary">Quay về Trang chủ</Button>
                        </Link>
                    </Card.Body>
                </Card>
            )}
        </Container>
    );
};

export default KhoaLuanList;
