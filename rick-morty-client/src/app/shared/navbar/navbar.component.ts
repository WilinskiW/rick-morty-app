import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NavLinkComponent } from './nav-link/nav-link.component';

@Component({
  selector: 'app-navbar',
  imports: [
    NavLinkComponent,
    RouterLink
  ],
  templateUrl: './navbar.component.html',
  standalone: true,
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  section = input.required<string>();
}
