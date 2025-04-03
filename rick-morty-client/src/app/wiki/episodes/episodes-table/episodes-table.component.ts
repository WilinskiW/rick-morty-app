import { Component, inject, OnInit, signal } from '@angular/core';
import { WikiService } from '../../wiki.service';
import { EpisodeModel } from '../episode.model';
import { ContentTableComponent } from '../../../shared/content-table/content-table.component';
import { RouterLink } from '@angular/router';
import { ConnectionErrorComponent } from '../../../shared/connection-error/connection-error.component';

@Component({
  selector: 'app-episodes-locations',
  imports: [
    ContentTableComponent,
    RouterLink,
    ConnectionErrorComponent
  ],
  templateUrl: './episodes-table.component.html',
})
export class EpisodesTableComponent implements OnInit{
  episodes: EpisodeModel[] = [];
  isFetching = signal(true);
  isError = signal(false);
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<EpisodeModel[]>("http://localhost:8081/api/episodes")
      .subscribe({
        next: (episodes) => {
          this.episodes = episodes
          this.isFetching.set(false);
          console.log(this.isFetching())
        },
        error: () => this.isError.set(true)
      });
  }

  protected readonly String = String;
}
