import { Component, inject } from '@angular/core';
import { AuthService } from '../auth.service';
import { AsyncPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-account',
  imports: [
    AsyncPipe,
    FormsModule
  ],
  templateUrl: './account.component.html',
})
export class AccountComponent {
  private authService = inject(AuthService);
  user$ = this.authService.user$;

  onSubmit() {
    this.authService.logout();
  }
}
