import { CanMatchFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const isAuthenticated: CanMatchFn = () => {
  if (inject(AuthService).isAuthenticated()) {
    return true;
  }

  const router = inject(Router);
  router.navigate(['/auth/login']);
  return false;
}

export const isAdmin: CanMatchFn = () => {
  if (inject(AuthService).isAdmin()) {
    return true;
  }

  const router = inject(Router);
  router.navigate(['/unauthorized']);
  return false;
}

export const isModeratorOrAdmin: CanMatchFn = () => {
  const authService = inject(AuthService);
  if (authService.isAdmin() || authService.isModerator()) {
    return true;
  }

  const router = inject(Router);
  router.navigate(['/unauthorized']);
  return false;
}
