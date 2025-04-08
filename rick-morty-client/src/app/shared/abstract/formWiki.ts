import { inject, Injectable } from '@angular/core';
import { FormService } from '../../form.service';
import { WikiService } from '../../wiki/wiki.service';
import { FormGroup } from '@angular/forms';

@Injectable({ providedIn: "root"} )
export abstract class FormWiki {
  abstract form: FormGroup;
  protected formService = inject(FormService);
  protected wikiService = inject(WikiService);

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.form, key);
  }

  abstract submit(): void;

  goBack(section: string[]) {
    this.wikiService.navigateTo(...section);
  }
}
