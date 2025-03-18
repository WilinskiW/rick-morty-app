import {Component, computed, input} from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-section-tab',
  imports: [
    RouterLink
  ],
  templateUrl: './section-tab.component.html',
  styleUrl: './section-tab.component.css'
})
export class SectionTabComponent {
  sectionName = input.required<string>();
  imageUrl = computed<string>(() => `${this.sectionName().toLowerCase()}.jpeg`);

  get getBackgroundImg(): string {
    return `background-image: url(/images/${this.imageUrl()})`
  }
}
