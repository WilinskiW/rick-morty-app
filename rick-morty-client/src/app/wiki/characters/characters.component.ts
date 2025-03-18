import { Component } from '@angular/core';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { ContentTableComponent } from '../../shared/content-table/content-table.component';

@Component({
  selector: 'app-characters',
  imports: [
    NavbarComponent,
    ContentTableComponent
  ],
  templateUrl: './characters.component.html',
  styleUrl: './characters.component.css'
})
export class CharactersComponent {

}
