import { Component, DestroyRef, inject, input, OnInit } from '@angular/core';
import { NgForOf } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CharacterModel } from '../../wiki/characters/character.model';


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
  headers = input.required<string[]>();
  private httpClient = inject(HttpClient);
  private destroyRef = inject(DestroyRef);

  numSequence(n: number): Array<number> { // Tylko na fazę testów
    return Array(n);
  }

  ngOnInit(): void {
    const subscription = this.httpClient.get<{characters:  CharacterModel[] }>("http://localhost:8081/api/character/1").subscribe({
      next: (responseData) => {
        console.log(responseData);
      }
    });

    this.destroyRef.onDestroy(() => subscription.unsubscribe());
  }
}
