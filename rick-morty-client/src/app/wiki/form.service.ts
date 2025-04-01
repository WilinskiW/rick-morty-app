import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormService {
  isInvalid(form: FormGroup, key: string): boolean {
    const control = form.get(key);
    return !!(control && control.touched && control.invalid);
    // In TypeScript !! - mean double negation
    // undefined, null -> false
    // {}, "some string" -> true
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
