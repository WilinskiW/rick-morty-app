import { Component, inject } from '@angular/core';
import { AsyncPipe, Location } from '@angular/common';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-card-footer',
  templateUrl: './card-footer.component.html',
  imports: [
    AsyncPipe
  ],
  styleUrl: './card-footer.component.css'
})
export class CardFooterComponent {
  private location = inject(Location);
  user$ = inject(AuthService).user$;

  goBack() {
    this.location.back();
  }
}
