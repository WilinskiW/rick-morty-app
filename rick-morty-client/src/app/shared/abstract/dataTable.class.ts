import { inject, Injectable, OnInit, signal } from '@angular/core';
import { WikiService } from '../../wiki/wiki.service';
import { PageModel } from '../../wiki/page.model';

@Injectable({
  providedIn: 'root',
})
export abstract class DataTableClass<T> implements OnInit {
  data: PageModel<T> | undefined;
  isFetching = signal(true);
  isError = signal(false);
  private wikiService = inject(WikiService);

  protected abstract url: string;

  ngOnInit() {
    this.wikiService.fetchDataWithPages<T>(this.url, 0)
      .subscribe({
        next: (data) => {
          this.data = data;
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
