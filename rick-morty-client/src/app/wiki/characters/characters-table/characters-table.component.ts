import { Component, inject, OnInit, signal } from '@angular/core';
import { ContentTableComponent } from '../../../shared/content-table/content-table.component';
import { CharacterModel } from '../character.model';
import { WikiService } from '../../wiki.service';
import { RouterLink } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { ConnectionErrorComponent } from '../../../shared/connection-error/connection-error.component';

@Component({
  selector: 'app-characters-table',
  imports: [
    ContentTableComponent,
    RouterLink,
    TitleCasePipe,
    ConnectionErrorComponent
  ],
  templateUrl: './characters-table.component.html',
})
export class CharactersTableComponent implements OnInit {
  characters: CharacterModel[] = [];
  isFetching = signal(true);
  isError = signal(false);
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<CharacterModel[]>("http://localhost:8081/api/characters")
      .subscribe({
        next: (characters) => {
          this.characters = characters
          this.isFetching.set(false);
          console.log(this.isFetching())
        },
        error: () => this.isError.set(true)
      });
  }

  protected readonly String = String;
}
