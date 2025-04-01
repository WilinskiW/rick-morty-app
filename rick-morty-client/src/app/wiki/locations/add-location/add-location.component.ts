import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { CharacterModel } from '../../characters/character.model';
import { LocationModel } from '../location.model';
import { FormService } from '../../form.service';

@Component({
  selector: 'app-add-location',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-location.component.html',
})
export class AddLocationComponent implements OnInit {
  form = new FormGroup({
    name: new FormControl(""),
    type: new FormControl(""),
    dimension: new FormControl(""),
    residents: new FormControl("")
  });
  characters: CharacterModel[] = [];
  private formService = inject(FormService);
  private wikiService = inject(WikiService);


  ngOnInit() {
    this.wikiService.fetchData<CharacterModel[]>("http://localhost:8081/api/characters")
      .subscribe({
        next: (characters) => this.characters = characters
      });
  }

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.form, key);
  }

  addLocation() {
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

    this.wikiService.sendData<LocationModel>(location, "http://localhost:8081/api/locations")
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas dodawania lokacji", err)
      });
  }

  goBack() {
    this.formService.navigateTo("locations");
  }
}
