import { useContext, useEffect, useState, useCallback } from "react";
import { Container, Form, Button, Spinner, Alert, Card } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";


const ChamDiem = () => {
    const { khoaLuanId } = useParams();
    const [user] = useContext(MyUserContext);
    const navigate = useNavigate();

    const [tieuChis, setTieuChis] = useState([]);
    const [diemSo, setDiemSo] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [loadingMessage, setLoadingMessage] = useState("Đang khởi tạo...");

    const loadData = useCallback(async () => {
        if (!user || !khoaLuanId) return;

        try {
            setLoading(true);
            setError(null);
            setSuccess(null);
            setLoadingMessage("Đang xác thực quyền chấm điểm...");
            let thanhVienId = null;
            try {
                
                const thanhVienIdRes = await authApis().get(endpoints['get-thanhvien-id'], {
                    params: {
                        giangVienId: user.id,
                        khoaLuanId: khoaLuanId
                    }
                });
                thanhVienId = thanhVienIdRes.data.thanhVienId;
            } catch (err) {
                console.error("Lỗi khi lấy ID thành viên:", err);
                if (err.response && (err.response.status === 404 || err.response.status === 400)) {
                    throw new Error("Bạn không phải là thành viên trong hội đồng của khóa luận này.");
                }
                throw new Error("Lỗi khi xác thực quyền chấm điểm.");
            }

            if (!thanhVienId) {
                throw new Error("Không thể xác định quyền chấm điểm của bạn cho khóa luận này.");
            }

           
            setLoadingMessage("Đang tải phiếu điểm...");
            const diemDaChamEndpoint = endpoints['diem-da-cham']
                .replace('{thanhVienId}', thanhVienId)
                .replace('{khoaLuanId}', khoaLuanId);

            const [diemRes, tieuChiRes] = await Promise.all([
                authApis().get(diemDaChamEndpoint),
                authApis().get(endpoints['tieuchis'])
            ]);

            setTieuChis(tieuChiRes.data);

            if (diemRes.data && diemRes.data.length > 0) {
                const diemDaCo = diemRes.data.reduce((acc, diem) => {
                    if (diem.tieuChiId && diem.tieuChiId.id) {
                        acc[diem.tieuChiId.id] = diem.diem;
                    }
                    return acc;
                }, {});
                setDiemSo(diemDaCo);
            }

        } catch (ex) {
            console.error("Lỗi trong quá trình tải dữ liệu:", ex);
            setError(ex.message || "Đã có lỗi không xác định xảy ra. Vui lòng thử lại.");
        } finally {
            setLoading(false);
        }
    }, [khoaLuanId, user]);

    useEffect(() => {
        loadData();
    }, [loadData]);

    const handleDiemChange = (tieuChiId, value, diemToiDa) => {
        const diem = parseFloat(value);
        if (value === '') {
            setDiemSo(prev => ({ ...prev, [tieuChiId]: '' }));
            return;
        }
        if (!isNaN(diem) && diem >= 0 && diem <= diemToiDa) {
            setDiemSo(prev => ({ ...prev, [tieuChiId]: diem }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (Object.values(diemSo).length < tieuChis.length || Object.values(diemSo).some(d => d === '' || d === null)) {
            setError("Vui lòng chấm điểm cho tất cả các tiêu chí.");
            return;
        }

        setLoading(true);
        setError(null);
        setSuccess(null);

        try {
            const endpoint = endpoints['cham-diem'];
            await authApis().post(endpoint, diemSo, {
                params: {
                    giangVienId: user.id,
                    khoaLuanId: khoaLuanId
                }
            });

            setSuccess("Chấm điểm thành công! Đang chuyển hướng...");
            setTimeout(() => {
                navigate(-1);
            }, 3000);

        } catch (ex) {
            console.error("Lỗi khi nộp điểm:", ex);
            setError(ex.response?.data?.message || "Đã có lỗi xảy ra khi nộp điểm.");
        } finally {
            setLoading(false);
        }
    };

    
    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" variant="primary" />
                <p className="mt-2">{loadingMessage}</p>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <h1 className="text-center text-primary mb-4">PHIẾU CHẤM ĐIỂM KHÓA LUẬN</h1>
            <Card className="shadow-sm">
                <Card.Header as="h5" className="bg-primary text-white">
                    Mã khóa luận: {khoaLuanId}
                </Card.Header>
                <Card.Body>
                    {error && <Alert variant="danger">{error}</Alert>}
                    {success && <Alert variant="success">{success}</Alert>}

                    
                    {!error || (error && tieuChis.length > 0) ? (
                        <Form onSubmit={handleSubmit}>
                            {tieuChis.map(tc => (
                                <Form.Group key={tc.id} className="mb-4 p-3 border rounded" controlId={`tieu-chi-${tc.id}`}>
                                    <div className="row align-items-center">
                                        <div className="col-md-8">
                                            <Form.Label className="fw-bold fs-5 mb-1">
                                                {tc.tenTc}
                                            </Form.Label>
                                            <p className="text-muted mb-1">{tc.noiDung}</p>
                                            <span className="text-info">(Điểm tối đa: {tc.diemToiDa})</span>
                                        </div>
                                        <div className="col-md-4 mt-2 mt-md-0">
                                            <Form.Control 
                                                type="number"
                                                min="0"
                                                max={tc.diemToiDa}
                                                step="0.1"
                                                value={diemSo[tc.id] ?? ''}
                                                onChange={e => handleDiemChange(tc.id, e.target.value, tc.diemToiDa)}
                                                required
                                                disabled={loading || success}
                                                className="text-center fs-5"
                                            />
                                        </div>
                                    </div>
                                </Form.Group>
                            ))}
                            
                            <div className="text-center mt-4">
                                <Button variant="primary" type="submit" disabled={loading || success} size="lg">
                                    {loading && <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />}
                                    {loading ? ' Đang nộp...' : 'Hoàn tất chấm điểm'}
                                </Button>
                            </div>
                        </Form>
                    ) : null}
                </Card.Body>
            </Card>
        </Container>
    );
};

export default ChamDiem;
