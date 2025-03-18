import {Component} from '@angular/core';
import {SectionTabComponent} from './section-tab/section-tab.component';

@Component({
  selector: 'app-home',
  imports: [
    SectionTabComponent
  ],
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
}
