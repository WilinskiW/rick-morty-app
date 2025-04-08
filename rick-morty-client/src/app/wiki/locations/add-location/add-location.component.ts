import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CharacterModel } from '../../characters/character.model';
import { LocationModel } from '../location.model';
import { FormWiki } from '../../../shared/abstract/formWiki';

@Component({
  selector: 'app-add-location',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-location.component.html',
})
export class AddLocationComponent extends FormWiki implements OnInit {
  form = new FormGroup({
    name: new FormControl(""),
    type: new FormControl(""),
    dimension: new FormControl(""),
    residents: new FormControl("")
  });
  characters: CharacterModel[] = [];

  ngOnInit() {
    this.wikiService.fetchData<CharacterModel[]>("http://localhost:8081/api/characters")
      .subscribe({
        next: (characters) => this.characters = characters
      });
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
      residents: this.form.value.residents || []
    }

    this.wikiService.sendData<LocationModel>(location, "http://localhost:8081/api/locations")
      .subscribe({
        complete: () => this.goBack(["locations"]),
        error: err => console.error("Błąd podczas edytowania lokacji", err)
      });
  }
}
