import { Component, inject, input, OnInit, signal } from '@angular/core';
import { EpisodeModel } from '../episode.model';
import { WikiService } from '../../wiki.service';
import { DetailCardComponent } from '../../../shared/components/detail-card/detail-card.component';

@Component({
  selector: 'app-episode-detail',
  imports: [
    DetailCardComponent
  ],
  templateUrl: './episode-detail.component.html',
})
export class EpisodeDetailComponent implements OnInit {
  id = input.required<string>();
  isCharacterExist = signal(false);
  episode: EpisodeModel | undefined;
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<EpisodeModel>(`http://localhost:8081/api/episodes/${this.id()}`)
      .subscribe({
        next: (data) => {
          this.episode = data;
          this.isCharacterExist.set(this.episode.characters.length > 0);
        },
        error: () => this.wikiService.goToResourceNotFound()
      });
  }
}
