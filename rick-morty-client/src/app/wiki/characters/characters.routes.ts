import { Route } from '@angular/router';
import { CharactersTableComponent } from './characters-table/characters-table.component';
import { CharacterDetailComponent } from './character-detail/character-detail.component';
import { AddCharacterComponent } from './add-character/add-character.component';

export const charactersRoutes: Route[] = [
  {
    path: "characters",
    component: CharactersTableComponent,
  },
  {
    path: "characters/add",
    component: AddCharacterComponent
  },
  {
    path: "characters/:id",
    component: CharacterDetailComponent
  },
]
