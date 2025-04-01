import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { WikiService } from '../../wiki.service';
import { EpisodeModel } from '../episode.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-episode',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './add-episode.component.html',
})
export class AddEpisodeComponent {
  form = new FormGroup({
    title: new FormControl(""),
    airDate: new FormControl(""),
    code: new FormControl(""),
  });
  private wikiService = inject(WikiService);
  private router = inject(Router);

  goBack() {
    this.router.navigate(['/wiki/episodes']);
  }

  isInvalid(key: string): boolean {
    const control = this.form.get(key);
    return !!(control && control.touched && control.invalid);
    // In TypeScript !! - mean double negation
    // undefined, null -> false
    // {}, "some string" -> true
  }

  addEpisode() {
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

    const episode = {
      title: this.form.value.title!,
      airDate: this.form.value.airDate!,
      episode: this.form.value.code!,
    }

    this.wikiService.sendData<EpisodeModel>(episode, "http://localhost:8081/api/episodes")
      .subscribe({
        complete: () => this.goBack(),
        error: err => console.error("Błąd podczas dodawania epizodu", err)
      });
  }
}
