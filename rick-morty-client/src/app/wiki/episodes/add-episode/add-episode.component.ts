import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { Location } from '@angular/common';
import { EpisodeModel } from '../episode.model';

@Component({
  selector: 'app-add-episode',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-episode.component.html',
})
export class AddEpisodeComponent {
  form = new FormGroup({
    title: new FormControl(""),
    airDate: new FormControl(""),
    code: new FormControl(""),
  });
  private wikiService = inject(WikiService);
  private siteLocation = inject(Location);


  addEpisode() {
    const episode = {
      title: this.form.value.title!,
      airDate: this.form.value.airDate!,
      episode: this.form.value.code!,
    }

    this.wikiService.sendData<EpisodeModel>(episode, "http://localhost:8081/api/episodes")
      .subscribe({
        complete: () => this.siteLocation.back(),
        error: err => console.error("Błąd podczas dodawania epizodu", err)
      });
  }
}
