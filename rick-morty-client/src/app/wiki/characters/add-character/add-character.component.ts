import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { LocationSummaryModel } from '../../locations/locationSummary.model';
import { CharacterModel } from '../character.model';
import { FormService } from '../../../form.service';

@Component({
  selector: 'app-add-character',
  imports: [
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-character.component.html',
})
export class AddCharacterComponent implements OnInit {
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
  private formService = inject(FormService);
  private wikiService = inject(WikiService);


  ngOnInit() {
    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => this.locations = locations
      });
  }

  goBack() {
    this.wikiService.navigateTo("characters");
  }

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.form, key);
  }

  addCharacter() {
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
          complete: () => this.goBack(),
          error: err => console.error("Błąd podczas dodawania postaci", err)
        });
  }
}
