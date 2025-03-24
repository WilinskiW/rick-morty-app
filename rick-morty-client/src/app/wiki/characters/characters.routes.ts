import { Route } from '@angular/router';
import { CharactersTableComponent } from './characters-table/characters-table.component';
import { CharacterDetailComponent } from './character-detail/character-detail.component';

export const charactersRoutes: Route[] = [
  {
    path: "characters",
    component: CharactersTableComponent,
  },
  {
    path: "characters/:id",
    component: CharacterDetailComponent
  },
]
