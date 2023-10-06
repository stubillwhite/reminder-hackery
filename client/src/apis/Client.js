import axios from 'axios';

const Client = axios.create({
    baseURL: 'http://localhost:8080',

    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

Client.interceptors.response.use(
    response => response,
    error => {
        console.log('ERROR:', error);
        return Promise.reject(error);
    });

export const pingServer = async (ping) => {
    return await Client.post('ping', ping);
};

export const createTask = async (task) => {
    return await Client.post('tasks', task);
};

export const getTasks = async () => {
    return await Client.get('tasks');
};
