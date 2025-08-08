import { useState } from "react";
import { Button, Form, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const ChangePassword = () => {
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);
    const nav = useNavigate();

    const [form, setForm] = useState({
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
    });

    const inputs = [
        {
            title: "Mật khẩu cũ",
            field: "oldPassword",
            type: "password"
        },
        {
            title: "Mật khẩu mới",
            field: "newPassword",
            type: "password"
        },
        {
            title: "Xác nhận mật khẩu mới",
            field: "confirmPassword",
            type: "password"
        }
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (form.newPassword !== form.confirmPassword) {
            setError("Mật khẩu mới không khớp!");
            return;
        }

        try {
            setLoading(true);

            let res = await authApis().post(endpoints['change-password'],
                {
                    oldPassword: form.oldPassword,
                    newPassword: form.newPassword
                },
                {
                    headers: {
                        "Content-Type": "application/json"
                    }
                }
            );

            setMessage(res.data.message || "Đổi mật khẩu thành công!");
            setError(null);

            setTimeout(() => nav("/"), 2000);
        } catch (err) {
            setError(err.response?.data?.message || "Đổi mật khẩu thất bại!");
            setMessage(null);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <h2 className="text-center text-primary mt-3">ĐỔI MẬT KHẨU</h2>

            {message && <Alert variant="success">{message}</Alert>}
            {error && <Alert variant="danger">{error}</Alert>}

            <Form onSubmit={handleSubmit}>
                {inputs.map(input => (
                    <Form.Group key={input.field} className="mb-3" controlId={input.field}>
                        <Form.Label>{input.title}</Form.Label>
                        <Form.Control required type={input.type} value={form[input.field]}onChange={e => setForm({ ...form, [input.field]: e.target.value })}placeholder={input.title}/>
                    </Form.Group>
                ))}

                {loading ? (<MySpinner />) : (
                    <Form.Group className="mb-3">
                        <Button variant="primary" type="submit">Đổi mật khẩu</Button>
                    </Form.Group>
                )}
            </Form>
        </>
    );
};

export default ChangePassword;
