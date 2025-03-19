import { Component, computed, inject, input } from '@angular/core';
import { ContentTableService } from './content-table.service';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'app-content-table',
  imports: [
    NgForOf
  ],
  templateUrl: './content-table.component.html',
  styleUrl: './content-table.component.css'
})
export class ContentTableComponent {
  private contentTableService = inject(ContentTableService);
  name = input.required<string>();
  tableHeaders = computed(() => this.contentTableService.tableHeaders(this.name()))

  numSequence(n: number): Array<number> { // Tylko na fazę testów
    return Array(n);
  }
}
