import { CanMatchFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';
import { map, of } from 'rxjs';

export const isAuthenticated: CanMatchFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return of(true);
  }

  return authService.fetchCurrentUser().pipe(
    map(user => {
      if (user) return true;
      router.navigate(['/auth/login']);
      return false;
    })
  );
};


export const isAdmin: CanMatchFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.fetchCurrentUser().pipe(
    map(user => {
      const isAdmin = user?.roles.includes("ADMIN");
      if (isAdmin) return true;
      router.navigate(['/unauthorized']);
      return false;
    })
  );
};

export const isModeratorOrAdmin: CanMatchFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.fetchCurrentUser().pipe(
    map(user => {
      const hasRole = user?.roles.includes("ADMIN") || user?.roles.includes("MODERATOR");
      if (hasRole) return true;
      router.navigate(['/unauthorized']);
      return false;
    })
  );
};
