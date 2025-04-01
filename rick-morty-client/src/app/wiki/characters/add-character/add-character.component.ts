import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { LocationSummaryModel } from '../../locations/locationSummary.model';
import { CharacterModel } from '../character.model';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-character',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './add-character.component.html',
})
export class AddCharacterComponent implements OnInit{
  form = new FormGroup({
    name: new FormControl(""),
    status: new FormControl("Unknown"),
    species: new FormControl(""),
    type: new FormControl(""),
    gender: new FormControl("Unknown"),
    origin: new FormControl("Unknown"),
    current: new FormControl("Unknown"),
    imageUrl: new FormControl("")
  })
  locations: LocationSummaryModel[] = [];
  private wikiService = inject(WikiService);
  private siteLocation = inject(Location);


  ngOnInit() {
    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => this.locations = locations
      });
  }

  addCharacter() {
    const origin = this.locations.find(loc => loc.name === this.form.value.origin);
    const current = this.locations.find(loc => loc.name === this.form.value.current);

    const character = {
      name: this.form.value.name!,
      status: this.form.value.status!,
      species: this.form.value.species!,
      type: this.form.value.type!,
      gender: this.form.value.gender!,
      origin: origin || { id: 0, name: 'Unknown', type: '', dimension: 'Unknown'},
      currentLocation: current || { id: 0, name: 'Unknown', type: '', dimension: "Unknown" },
      imageUrl: this.form.value.imageUrl!
    };

    this.wikiService.sendData<CharacterModel>(character, "http://localhost:8081/api/characters")
      .subscribe({
        complete: () => this.siteLocation.back(),
        error: err => console.error("Błąd podczas dodawania postaci", err)
      });
  }

}
