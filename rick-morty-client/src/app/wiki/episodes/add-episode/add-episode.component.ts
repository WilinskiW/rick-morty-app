import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EpisodeModel } from '../episode.model';
import '../episodes.validators';
import { cantBeInTheFuture, mustBeCode } from '../episodes.validators';
import { FormWiki } from '../../../shared/abstract/formWiki';

@Component({
  selector: 'app-add-episode',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-episode.component.html',
})
export class AddEpisodeComponent extends FormWiki{
  form = new FormGroup({
    title: new FormControl("", {
      validators: [Validators.required]
    }),
    airDate: new FormControl("", {
      validators: [Validators.required, cantBeInTheFuture]
    }),
    episode: new FormControl("", {
      validators: [Validators.required, mustBeCode]
    }),
  });

  submit(): void {
    if (this.form.invalid) {
      this.formService.markAllControlsAsTouched(this.form);
      return;
    }

    const episode = {
      title: this.form.value.title!,
      airDate: this.form.value.airDate!,
      episode: this.form.value.episode!,
    }

    this.wikiService.sendData<EpisodeModel>(episode, "http://localhost:8081/api/episodes")
      .subscribe({
        complete: () => this.goBack(["episodes"]),
        error: err => console.error("Błąd podczas dodawania epizodu", err)
      });
  }
}
