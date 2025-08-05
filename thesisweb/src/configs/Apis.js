import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/ThesisApp/api/';

export const endpoints = {
    'user': '/user',
    'login': '/login',
    'profile': '/secure/profile',
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