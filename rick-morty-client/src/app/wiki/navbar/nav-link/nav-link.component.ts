import { Component, input } from '@angular/core';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { TitleCasePipe } from "@angular/common";

@Component({
  selector: 'app-nav-link',
  imports: [
    RouterLinkActive,
    TitleCasePipe,
    RouterLink
  ],
  templateUrl: './nav-link.component.html',
  styleUrl: './nav-link.component.css'
})
export class NavLinkComponent {
  name =  input.required<string>();
  activeSection = input.required<string>();

  get prepareRouteLink() {
    return this.name() === this.activeSection() ? '[./]' : `../${this.name().toLowerCase()}`;
  }
}
