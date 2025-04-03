import { Component, input} from '@angular/core';

@Component({
  selector: 'app-content-table',
  templateUrl: './content-table.component.html',
  standalone: true,
})
export class ContentTableComponent {
  section = input.required<string>();
}
