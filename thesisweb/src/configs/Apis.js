import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/ThesisApp/api/';

export const endpoints = {
    'user': '/user',
    'login': '/login',
    'profile': '/secure/profile',
    "change-password": "/secure/changepassword",
    'hoidongs-giangvien': '/secure/hoidong/giangvien/{giangVienId}',
    'khoaluans-hoidong': '/secure/khoaluan/hoidong/{hoiDongId}',
    'cham-diem': '/secure/diem/cham', 
    'diem-da-cham': '/secure/diem/thanhvien/{thanhVienId}/khoaluan/{khoaLuanId}', 
    'tieuchis': '/tieuchis',
    'get-thanhvien-id': '/secure/thanhvien-id',
}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load('token')}`
    }
})

export default axios.create({
    baseURL: BASE_URL
})