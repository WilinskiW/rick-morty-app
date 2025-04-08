import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LocationSummaryModel } from '../../locations/locationSummary.model';
import { CharacterModel } from '../character.model';
import { FormWiki } from '../../../shared/abstract/formWiki';

@Component({
  selector: 'app-add-character',
  imports: [
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-character.component.html',
})
export class AddCharacterComponent extends FormWiki implements OnInit {
  form = new FormGroup({
    name: new FormControl("", {
      validators: [Validators.required, Validators.minLength(3)]
    }),
    status: new FormControl("Unknown"),
    species: new FormControl("", {
      validators: [Validators.required]
    }),
    type: new FormControl(""),
    gender: new FormControl("Unknown"),
    origin: new FormControl("Unknown"),
    current: new FormControl("Unknown"),
    imageUrl: new FormControl("")
  })
  locations: LocationSummaryModel[] = [];

  ngOnInit() {
    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => this.locations = locations
      });
  }

  submit() {
    if (this.form.invalid) {
      this.formService.markAllControlsAsTouched(this.form);
      return;
    }

      const origin = this.locations.find(loc => loc.name === this.form.value.origin);
      const current = this.locations.find(loc => loc.name === this.form.value.current);

      const character = {
        name: this.form.value.name!,
        status: this.form.value.status!,
        species: this.form.value.species!,
        type: this.form.value.type!,
        gender: this.form.value.gender!,
        origin: this.form.value.origin! === "Unknown" ? null : origin,
        currentLocation: this.form.value.current! === "Unknown" ? null : current,
        imageUrl: this.form.value.imageUrl!
      };

      this.wikiService.sendData<CharacterModel>(character, "http://localhost:8081/api/characters")
        .subscribe({
          complete: () => this.goBack(["characters"]),
          error: err => console.error("Błąd podczas dodawania postaci", err)
        });
  }
}
