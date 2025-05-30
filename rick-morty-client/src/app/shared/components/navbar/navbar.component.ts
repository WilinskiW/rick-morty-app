import { Component, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NavLinkComponent } from './nav-link/nav-link.component';
import { AuthService } from '../../../auth/auth.service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [
    NavLinkComponent,
    RouterLink,
    AsyncPipe
  ],
  templateUrl: './navbar.component.html',
  standalone: true,
})
export class NavbarComponent{
  section = input.required<string>();
  user$ = inject(AuthService).user$;

  get sectionSingular(): string {
    // section name in singular form
    return this.section().slice(0, -1);
  }
}
