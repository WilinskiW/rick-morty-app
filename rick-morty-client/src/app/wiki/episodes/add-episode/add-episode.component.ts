import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { EpisodeModel } from '../episode.model';
import { FormService } from '../../form.service';

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
  private formService = inject(FormService);
  private wikiService = inject(WikiService);

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.form, key);
  }

  addEpisode() {
    if (this.form.invalid) {
      this.formService.markAllControlsAsTouched(this.form);
      return;
    }

    const episode = {
      title: this.form.value.title!,
      airDate: this.form.value.airDate!,
      episode: this.form.value.code!,
    }

    this.wikiService.sendData<EpisodeModel>(episode, "http://localhost:8081/api/episodes")
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas dodawania epizodu", err)
      });
  }

  goBack() {
    this.formService.navigateTo("episodes")
  }
}
