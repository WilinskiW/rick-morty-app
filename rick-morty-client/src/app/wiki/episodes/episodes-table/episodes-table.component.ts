import { Component, inject, signal } from '@angular/core';
import { WikiService } from '../../wiki.service';
import { EpisodeModel } from '../episode.model';
import { ContentTableComponent } from '../../../shared/content-table/content-table.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-episodes-locations',
  imports: [
    ContentTableComponent,
    RouterLink
  ],
  templateUrl: './episodes-table.component.html',
  styleUrl: './episodes-table.component.css'
})
export class EpisodesTableComponent {
  episodes: EpisodeModel[] = [];
  isFetching = signal(false);
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<EpisodeModel[]>("http://localhost:8081/api/episodes")
      .subscribe({
        next: (episodes) => this.episodes = episodes,
        complete: () => {
          this.isFetching.set(true);
        }
      });
  }

  protected readonly String = String;
}
