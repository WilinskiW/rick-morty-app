import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { AuthService } from '../../../../auth/auth.service';
import { WikiService } from '../../../../wiki/wiki.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-card-footer',
  templateUrl: './card-footer.component.html',
  imports: [
    AsyncPipe
  ],
})
export class CardFooterComponent {
  private activeRouter = inject(ActivatedRoute);
  private wikiService = inject(WikiService);
  user$ = inject(AuthService).user$;

  goBack() {
    const segments = this.activeRouter.snapshot.url;
    segments.pop()
    this.wikiService.navigateTo(segments[0].path);
  }

  confirmDelete() {
    if (confirm(`Are you sure want to delete this element?`)) {
      this.wikiService.deleteData(window.location.href).subscribe({
        complete: () => this.goBack()
      });
    }
  }

  goToEdit() {
    this.wikiService.navigateTo(...this.activeRouter.snapshot.url.toString().split(","), "edit");
  }
}
