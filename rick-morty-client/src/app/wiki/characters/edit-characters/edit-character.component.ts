import { Component, inject, input, OnInit } from '@angular/core';
import { WikiService } from '../../wiki.service';
import { CharacterModel } from '../character.model';
import { LocationSummaryModel } from '../../locations/locationSummary.model';
import { AuthService } from '../../../auth/auth.service';
import { AsyncPipe } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormService } from '../../form.service';

@Component({
  selector: 'app-edit-characters',
  imports: [
    AsyncPipe,
    ReactiveFormsModule
  ],
  templateUrl: './edit-character.component.html',
})
export class EditCharacterComponent implements OnInit {
  editForm = new FormGroup({
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

  id = input.required<string>();
  character: CharacterModel | undefined;
  locations: LocationSummaryModel[] = [];
  user$ = inject(AuthService).user$;
  private wikiService = inject(WikiService);
  private formService = inject(FormService);


  ngOnInit() {
    this.wikiService.fetchData<CharacterModel>(`http://localhost:8081/api/characters/${this.id()}`)
      .subscribe((data) => {
        this.character = data;
        this.editForm.patchValue({
          name: data.name,
          status: data.status,
          species: data.species,
          type: data.type,
          gender: data.gender,
          origin: data.origin?.name || "Unknown",
          current: data.currentLocation?.name || "Unknown",
          imageUrl: data.imageUrl
        });
      });

    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => this.locations = locations
      });
  }

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.editForm, key);
  }


  goBack() {
    this.wikiService.navigateTo("characters", this.id());
  }

  editOnSubmit() {
    if (this.editForm.invalid) {
      this.formService.markAllControlsAsTouched(this.editForm);
      return;
    }

    const origin = this.locations.find(loc => loc.name === this.editForm.value.origin);
    const current = this.locations.find(loc => loc.name === this.editForm.value.current);

    const character = {
      name: this.editForm.value.name!,
      status: this.editForm.value.status!,
      species: this.editForm.value.species!,
      type: this.editForm.value.type!,
      gender: this.editForm.value.gender!,
      origin: this.editForm.value.origin! === "Unknown" ? null : origin,
      currentLocation: this.editForm.value.current! === "Unknown" ? null : current,
      imageUrl: this.editForm.value.imageUrl!
    };

    this.wikiService.patchData<CharacterModel>(character, `http://localhost:8081/api/characters/${this.id()}`)
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas edytowania postaci", err)
      });
  }
}
