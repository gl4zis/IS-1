enum Protocol {
  HTTP = 'http',
  HTTPS = 'https',
}

const API_PROTOCOL = Protocol.HTTP;
const SERVER_HOST = 'localhost';
const SERVER_PORT = 8183;
const API_PATH = '/api';

function createApiUrl(): string {
  return `${API_PROTOCOL}://${SERVER_HOST}:${SERVER_PORT}${API_PATH}`;
}

export const environment = {
  api: createApiUrl(),
};
