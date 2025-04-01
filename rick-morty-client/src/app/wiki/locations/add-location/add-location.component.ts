import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { CharacterModel } from '../../characters/character.model';
import { LocationModel } from '../location.model';
import { Router } from '@angular/router';

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
  private wikiService = inject(WikiService);
  private router = inject(Router);


  ngOnInit() {
    this.wikiService.fetchData<CharacterModel[]>("http://localhost:8081/api/characters")
      .subscribe({
        next: (characters) => this.characters = characters
      });
  }

  isInvalid(key: string): boolean {
    const control = this.form.get(key);
    return !!(control && control.touched && control.invalid);
    // In TypeScript !! - mean double negation
    // undefined, null -> false
    // {}, "some string" -> true
  }

  goBack() {
    this.router.navigate(['/wiki/locations']);
  }

  addLocation() {
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

    const location = {
      name: this.form.value.name!,
      type: this.form.value.type!,
      dimension: this.form.value.dimension!  || "Unknown",
      residents: this.form.value.residents || [],
    }

    this.wikiService.sendData<LocationModel>(location, "http://localhost:8081/api/locations")
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas dodawania lokacji", err)
      });
  }
}
