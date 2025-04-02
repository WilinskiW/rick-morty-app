import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AccountComponent } from './account/account.component';
import { isAuthenticated } from './auth.guard';

export const authRoutes = [
  {
    path: "auth/login",
    component: LoginComponent
  },
  {
    path: "auth/register",
    component: RegisterComponent,
  },
  {
    path: "auth/account",
    component: AccountComponent,
    canMatch: [isAuthenticated]
  }
]
