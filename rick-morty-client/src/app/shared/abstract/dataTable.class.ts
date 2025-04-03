import { inject, OnInit, signal } from '@angular/core';
import { WikiService } from '../../wiki/wiki.service';

export abstract class DataTableClass<T> implements OnInit {
  data: T[] = [];
  isFetching = signal(true);
  isError = signal(false);
  private wikiService = inject(WikiService);

  protected abstract url: string;

  ngOnInit() {
    this.wikiService.fetchData<T[]>(this.url)
      .subscribe({
        next: (data) => {
          this.data = data
          this.isFetching.set(false);
        },
        error: () => {
          this.isFetching.set(false);
          this.isError.set(true);
        }
      });
  }

  protected readonly String = String;
}
