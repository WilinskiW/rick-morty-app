import { Component, DestroyRef } from '@angular/core';
import { NavbarComponent } from '../shared/components/navbar/navbar.component';
import { ActivatedRoute, NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter, map } from 'rxjs';


@Component({
  selector: 'app-wiki',
  imports: [
    NavbarComponent,
    RouterOutlet,
  ],
  templateUrl: './wiki.component.html',
  standalone: true,
})
export class WikiComponent {
  section = "";

  constructor(private route: ActivatedRoute, private router: Router, private onDestroy: DestroyRef) {
    const sub = this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.route.firstChild?.snapshot?.url[0]?.path || "unknown")
      )
      .subscribe(section => {
        this.section = section || 'Unknown';
      });

    this.onDestroy.onDestroy(() => sub.unsubscribe());
  }
}
