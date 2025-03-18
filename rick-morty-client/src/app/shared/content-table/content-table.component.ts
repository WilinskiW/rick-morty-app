import { Component, inject, input } from '@angular/core';
import { ContentTableService } from './content-table.service';

@Component({
  selector: 'app-content-table',
  imports: [],
  templateUrl: './content-table.component.html',
  styleUrl: './content-table.component.css'
})
export class ContentTableComponent {
  name = input.required<string>();
  private contentTableService = inject(ContentTableService);

  get tableHeaders(): string[] {
    return this.contentTableService.tableHeaders(this.name());
  }
}
