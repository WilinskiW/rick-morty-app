import { Component, input } from '@angular/core';

@Component({
  selector: 'app-no-photo-detail',
  imports: [],
  templateUrl: './no-photo-detail.component.html',
  standalone: true,
  styleUrl: './no-photo-detail.component.css'
})
export class NoPhotoDetailComponent {
  infoFields = input.required<string>();
}
