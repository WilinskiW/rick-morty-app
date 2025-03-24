import { Component, input} from '@angular/core';
import { NgForOf } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-content-table',
  imports: [
    NgForOf,
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './content-table.component.html',
  standalone: true,
  styleUrl: './content-table.component.css'
})
export class ContentTableComponent {
  section = input.required<string>();
}
