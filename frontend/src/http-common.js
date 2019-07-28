import axios from 'axios';

let username = '';
let headers = {};

export const HTTP = (otherHeader) => {
    return axios.create({
        baseURL: 'http://localhost:8090/api/',
        headers: otherHeader || headers
    })
}

export const SetHeader = (param) => {
    headers = param
}

export const SetUser = (param) => {
    username = param
}