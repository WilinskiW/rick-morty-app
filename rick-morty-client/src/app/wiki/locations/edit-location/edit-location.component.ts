import { Component, inject, input, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormService } from '../../form.service';
import { WikiService } from '../../wiki.service';
import { AuthService } from '../../../auth/auth.service';
import { LocationModel } from '../location.model';
import { CharacterModel } from '../../characters/character.model';
import { CharacterEditTableComponent } from '../../../shared/character-edit-table/character-edit-table.component';

@Component({
  selector: 'app-edit-location',
  imports: [
    ReactiveFormsModule,
    CharacterEditTableComponent
  ],
  templateUrl: './edit-location.component.html',
})
export class EditLocationComponent implements OnInit{
  editForm = new FormGroup({
    name: new FormControl(""),
    type: new FormControl(""),
    dimension: new FormControl(""),
    residents: new FormControl<CharacterModel[]>([])
  });

  id = input.required<string>();
  location: LocationModel | undefined;
  restOfCharacters: CharacterModel[] = [];
  user$ = inject(AuthService).user$;
  private wikiService = inject(WikiService);
  private formService = inject(FormService);

  ngOnInit() {
    this.wikiService.fetchData<LocationModel>(`http://localhost:8081/api/locations/${this.id()}`)
      .subscribe(data => {
        this.location = data;

        this.editForm.patchValue({
          name: data.name,
          type: data.type,
          dimension: data.dimension,
          residents: data.residents
        });
      });

    this.wikiService.fetchData<CharacterModel[]>(`http://localhost:8081/api/characters/${this.id()}/notInLocation`)
      .subscribe(data => this.restOfCharacters = data)
  }

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.editForm, key);
  }

  goBack() {
    this.wikiService.navigateTo("locations", this.id());
  }

  editOnSubmit() {
    if (this.editForm.invalid) {
      this.formService.markAllControlsAsTouched(this.editForm);
      return;
    }

    const location = {
      name: this.editForm.value.name!,
      type: this.editForm.value.type!,
      dimension: this.editForm.value.dimension! || "Unknown",
      residents: this.editForm.value.residents || [],
    }

    this.wikiService.putData<LocationModel>(location, `http://localhost:8081/api/locations/${this.id()}`)
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas dodawania lokacji", err)
      });
  }
}
