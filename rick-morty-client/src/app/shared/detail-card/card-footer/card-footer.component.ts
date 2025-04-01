import { Component, inject } from '@angular/core';
import { AsyncPipe, Location } from '@angular/common';
import { AuthService } from '../../../auth/auth.service';
import { WikiService } from '../../../wiki/wiki.service';

@Component({
  selector: 'app-card-footer',
  templateUrl: './card-footer.component.html',
  imports: [
    AsyncPipe
  ],
})
export class CardFooterComponent {
  private location = inject(Location);
  private wikiService = inject(WikiService);
  user$ = inject(AuthService).user$;

  goBack() {
    this.location.back();
  }

  confirmDelete() {
    if(confirm(`Are you sure want to delete this element?`)){
      this.wikiService.deleteData(window.location.href).subscribe({
        complete: () => this.goBack()
      });
    }
  }

  goToEdit() {
    const url = this.location.path() + "/edit";
    this.wikiService.router.navigateByUrl(url);
  }
}
