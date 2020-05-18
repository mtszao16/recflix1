import jwt from 'jsonwebtoken';
import { AUTH_TOKEN } from './constants';

export function getUserId() {
  const authToken = localStorage.getItem(AUTH_TOKEN);
  const decoded = jwt.decode(authToken);

  return decoded.userId;
}
