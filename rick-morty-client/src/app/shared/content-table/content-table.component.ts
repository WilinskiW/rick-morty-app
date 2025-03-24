import { Component, DestroyRef, inject, input, OnInit, signal } from '@angular/core';
import { NgForOf } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CharacterModel } from '../../wiki/characters/character.model';
import { WikiService } from '../../wiki/wiki.service';


@Component({
  selector: 'app-content-table',
  imports: [
    NgForOf,
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './content-table.component.html',
  standalone: true,
  styleUrl: './content-table.component.css'
})
export class ContentTableComponent implements OnInit {
  section = input.required<string>();
  data = signal<CharacterModel[]>([]);
  isFetching = signal(false);
  private wikiService = inject(WikiService);
  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    this.isFetching.set(true);
    const subscription = this.wikiService.fetchData<CharacterModel[]>("http://localhost:8081/api/characters")
      .subscribe({
        next: responseData => {
          this.data.set(responseData);
        },
        complete: () => {
          console.log(this.data())
          this.isFetching.set(false);
        }
      });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }
}
