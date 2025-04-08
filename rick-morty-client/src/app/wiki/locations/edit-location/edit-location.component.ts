import { Component, input, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LocationModel } from '../location.model';
import { CharacterModel } from '../../characters/character.model';
import { CharacterEditTableComponent } from '../../../shared/components/character-edit-table/character-edit-table.component';
import { FormWiki } from '../../../shared/abstract/formWiki';

@Component({
  selector: 'app-edit-location',
  imports: [
    ReactiveFormsModule,
    CharacterEditTableComponent
  ],
  templateUrl: './edit-location.component.html',
})
export class EditLocationComponent extends FormWiki implements OnInit{
  form = new FormGroup({
    name: new FormControl(""),
    type: new FormControl(""),
    dimension: new FormControl(""),
    residents: new FormControl<CharacterModel[]>([])
  });

  id = input.required<string>();
  location: LocationModel | undefined;
  restOfCharacters: CharacterModel[] = [];

  ngOnInit() {
    this.wikiService.fetchData<LocationModel>(`http://localhost:8081/api/locations/${this.id()}`)
      .subscribe(data => {
        this.location = data;

        this.form.patchValue({
          name: data.name,
          type: data.type,
          dimension: data.dimension,
        });
      });

    this.wikiService.fetchData<CharacterModel[]>(`http://localhost:8081/api/characters/${this.id()}/notInLocation`)
      .subscribe(data => this.restOfCharacters = data)
  }

  submit() {
    if (this.form.invalid) {
      this.formService.markAllControlsAsTouched(this.form);
      return;
    }

    const location = {
      name: this.form.value.name!,
      type: this.form.value.type!,
      dimension: this.form.value.dimension! || "Unknown",
      residents: this.form.value.residents || [],
    }

    this.wikiService.putData<LocationModel>(location, `http://localhost:8081/api/locations/${this.id()}`)
      .subscribe({
        complete: () => this.goBack(["locations", this.id()]),
        error: err => console.error("Błąd podczas dodawania lokacji", err)
      });
  }
}
