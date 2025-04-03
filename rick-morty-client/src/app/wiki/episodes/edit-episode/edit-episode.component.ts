import { Component, inject, input, OnInit } from '@angular/core';
import { CharacterEditTableComponent } from '../../characters/character-edit-table/character-edit-table.component';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CharacterModel } from '../../characters/character.model';
import { WikiService } from '../../wiki.service';
import { FormService } from '../../../form.service';
import { EpisodeModel } from '../episode.model';

@Component({
  selector: 'app-edit-episode',
  imports: [
    CharacterEditTableComponent,
    ReactiveFormsModule
  ],
  templateUrl: './edit-episode.component.html',
})
export class EditEpisodeComponent implements OnInit {
  editForm = new FormGroup({
    title: new FormControl(""),
    airDate: new FormControl(""),
    episode: new FormControl(""),
    characters: new FormControl<CharacterModel[]>([])
  });

  id = input.required<string>();
  episode: EpisodeModel | undefined;
  restOfCharacters: CharacterModel[] = [];
  private wikiService = inject(WikiService);
  private formService = inject(FormService);

  ngOnInit() {
    this.wikiService.fetchData<EpisodeModel>(`http://localhost:8081/api/episodes/${this.id()}`)
      .subscribe(data => {
        this.episode = data;

        this.editForm.patchValue({
          title: data.title,
          airDate: data.airDate,
          episode: data.episode,
        });
      });

    this.wikiService.fetchData<CharacterModel[]>(`http://localhost:8081/api/characters/${this.id()}/notInEpisode`)
      .subscribe(data => this.restOfCharacters = data)
  }

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.editForm, key);
  }

  goBack() {
    this.wikiService.navigateTo("episodes", this.id());
  }

  editOnSubmit() {
    if (this.editForm.invalid) {
      this.formService.markAllControlsAsTouched(this.editForm);
      return;
    }

    const episode = {
      title: this.editForm.value.title!,
      airDate: this.editForm.value.airDate!,
      episode: this.editForm.value.episode!,
      characters: this.editForm.value.characters || [],
    }

    this.wikiService.putData<EpisodeModel>(episode, `http://localhost:8081/api/episodes/${this.id()}`)
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas edycji epizodu", err)
      });
  }
}
