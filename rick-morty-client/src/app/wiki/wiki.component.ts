import { Component, input } from '@angular/core';
import { NavbarComponent } from './navbar/navbar.component';
import { ContentTableComponent } from './content-table/content-table.component';
import { TitleCasePipe } from '@angular/common';


@Component({
  selector: 'app-wiki',
  imports: [
    ContentTableComponent,
    TitleCasePipe,
    NavbarComponent,
  ],
  templateUrl: './wiki.component.html',
  styleUrl: './wiki.component.css'
})
export class WikiComponent {
  section = input.required<string>();
}
