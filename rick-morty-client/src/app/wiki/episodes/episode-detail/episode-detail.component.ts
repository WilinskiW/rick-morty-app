import { Component, inject, input, OnInit } from '@angular/core';
import { EpisodeModel } from '../episode.model';
import { WikiService } from '../../wiki.service';
import { RouterLink } from '@angular/router';
import { DetailCardComponent } from '../../../shared/detail-card/detail-card.component';

@Component({
  selector: 'app-episode-detail',
  imports: [
    RouterLink,
    DetailCardComponent
  ],
  templateUrl: './episode-detail.component.html',
  styleUrl: './episode-detail.component.css'
})
export class EpisodeDetailComponent implements OnInit {
  id = input.required<string>();
  episode: EpisodeModel | undefined;
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<EpisodeModel>(`http://localhost:8081/api/episodes/${this.id()}`)
      .subscribe({
        next: (data) => {
          this.episode = data;
        },
        complete: () => {
          console.log(this.episode);
        }
      });
  }
}
