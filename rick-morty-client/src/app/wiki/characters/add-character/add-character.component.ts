import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { LocationSummaryModel } from '../../locations/locationSummary.model';
import { CharacterModel } from '../character.model';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-add-character',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink
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
  private wikiService = inject(WikiService);
  private router = inject(Router);


  ngOnInit() {
    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => this.locations = locations
      });
  }

  goBack() {
    this.router.navigate(['/wiki/characters']);
  }

  isInvalid(key: string): boolean {
    const control = this.form.get(key);
    return !!(control && control.touched && control.invalid);
    // In TypeScript !! - mean double negation
    // undefined, null -> false
    // {}, "some string" -> true
  }

  addCharacter() {
    if (this.form.invalid) {
      //Mark all controls as touched
      Object.keys(this.form.controls).forEach(controlName => {
        const control = this.form.get(controlName);
        if (control) {
          control.markAsTouched();
        }

      });
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
