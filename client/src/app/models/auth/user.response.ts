import {Role} from './role.model';

export interface UserResponse {
  token: string;
  role: Role;
}
