import { Component, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from "@angular/router";

@Component({
  selector: 'app-nav-link',
  imports: [
    RouterLinkActive,
    RouterLink
  ],
  templateUrl: './nav-link.component.html',
  standalone: true,
  styleUrl: './nav-link.component.css'
})
export class NavLinkComponent {
  name =  input.required<string>();
  activeSection = input.required<string>();

  get prepareRouteLink() {
    return `${this.name().toLowerCase()}`;
  }
}
