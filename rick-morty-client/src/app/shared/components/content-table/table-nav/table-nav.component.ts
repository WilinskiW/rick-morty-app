import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PageModel } from '../../../../wiki/page.model';

@Component({
  selector: 'app-table-nav',
  imports: [
    RouterLink
  ],
  templateUrl: './table-nav.component.html',
})
export class TableNavComponent<T> {
  data = input.required<PageModel<T> | undefined>();
  previous = computed(() => !this.data()!.first ? this.data()!.number - 1 : this.data()!.number);
  current = computed(() => this.data()!.number);
  next = computed(() => !this.data()!.last ? this.data()!.number + 1 : this.data()!.number)
  last = computed(() => this.data!()!.totalPages)

  //todo:
  //  2. Pull danych ze strony i wyświetlanie
  //  3. Wprowadzenie tego do episodes i locations
}
