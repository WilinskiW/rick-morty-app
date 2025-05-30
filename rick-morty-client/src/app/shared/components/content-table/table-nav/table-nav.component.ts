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
  previous = computed(() => this.data()!.first ? this.data()!.number + 1 : (this.data()!.number + 1) - 1);
  current = computed(() => this.data()!.number + 1);
  next = computed(() => this.data()!.last ? this.data()!.number + 1 : this.data()!.number + 2)
  last = computed(() => this.data!()!.totalPages)
}

// Page = 0       + 1 -> Obiekt PageModel (Indeks przesuwany o +1 dla UX)
// Previous = 0   + 1
// Current = 0    + 1
// Next = 0       + 2

// Page = 1       + 1
// Previous = 1
// Current = 1    + 1
// Next = 1       + 2

// Page = 2       + 1
// Previous = 2
// Current = 2    + 1
// Next = 2       + 2

// W dół
