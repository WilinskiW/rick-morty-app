import { inject, Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class FormService {
  private router = inject(Router);

  isInvalid(form: FormGroup, key: string): boolean {
    const control = form.get(key);
    return !!(control && control.touched && control.invalid);
    // In TypeScript !! - mean double negation
    // undefined, null -> false
    // {}, "some string" -> true
  }

  navigateTo(location: string) {
    this.router.navigate(['/wiki/', location]);
  }

  markAllControlsAsTouched(form: FormGroup) {
    Object.keys(form.controls).forEach(controlName => {
      const control = form.get(controlName);
      if (control) {
        control.markAsTouched();
      }
    });
  }
}
