import { Component, input, OnInit } from '@angular/core';
import { CharacterEditTableComponent } from '../../../shared/components/character-edit-table/character-edit-table.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CharacterModel } from '../../characters/character.model';
import { EpisodeModel } from '../episode.model';
import { cantBeInTheFuture, mustBeCode } from '../episodes.validators';
import { FormWiki } from '../../../shared/abstract/formWiki';

@Component({
  selector: 'app-edit-episode',
  imports: [
    CharacterEditTableComponent,
    ReactiveFormsModule
  ],
  templateUrl: './edit-episode.component.html',
})
export class EditEpisodeComponent extends FormWiki implements OnInit {
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
    characters: new FormControl<CharacterModel[]>([])
  });
  id = input.required<string>();
  episode: EpisodeModel | undefined;
  restOfCharacters: CharacterModel[] = [];

  ngOnInit() {
    this.wikiService.fetchData<EpisodeModel>(`http://localhost:8081/api/episodes/${this.id()}`)
      .subscribe(data => {
        this.episode = data;

        this.form.patchValue({
          title: data.title,
          airDate: data.airDate,
          episode: data.episode,
        });
      });

    this.wikiService.fetchData<CharacterModel[]>(`http://localhost:8081/api/characters/${this.id()}/notInEpisode`)
      .subscribe(data => this.restOfCharacters = data)
  }

  submit() {
    if (this.form.invalid) {
      this.formService.markAllControlsAsTouched(this.form);
      return;
    }

    const episode = {
      title: this.form.value.title!,
      airDate: this.form.value.airDate!,
      episode: this.form.value.episode!,
      characters: this.form.value.characters || [],
    }

    this.wikiService.putData<EpisodeModel>(episode, `http://localhost:8081/api/episodes/${this.id()}`)
      .subscribe({
        complete: () => this.goBack(["episodes", this.id()]),
        error: err => console.error("Błąd podczas edycji epizodu", err)
      });
  }
}
