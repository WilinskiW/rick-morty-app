import { Component, inject, OnInit, signal } from '@angular/core';
import { ContentTableComponent } from '../../../../shared/content-table/content-table.component';
import { CharacterModel } from '../../character.model';
import { WikiService } from '../../../wiki.service';
import { RouterLink } from '@angular/router';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-characters-table',
  imports: [
    ContentTableComponent,
    RouterLink,
    TitleCasePipe
  ],
  templateUrl: './characters-table.component.html',
  styleUrl: './characters-table.component.css'
})
export class CharactersTableComponent implements OnInit {
  characters: CharacterModel[] = [];
  isFetching = signal(false);
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<CharacterModel[]>("http://localhost:8081/api/characters")
      .subscribe({
        next: (characters) => this.characters = characters,
        complete: () => {
          this.isFetching.set(true);
        }
      });
  }

  protected readonly TitleCasePipe = TitleCasePipe;
  protected readonly String = String;
}
