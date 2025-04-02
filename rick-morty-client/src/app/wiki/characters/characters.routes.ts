import { Route } from '@angular/router';
import { CharactersTableComponent } from './characters-table/characters-table.component';
import { CharacterDetailComponent } from './character-detail/character-detail.component';
import { AddCharacterComponent } from './add-character/add-character.component';
import { EditCharacterComponent } from './edit-characters/edit-character.component';
import { isAdmin, isModeratorOrAdmin } from '../../auth/auth.guard';

export const charactersRoutes: Route[] = [
  {
    path: "characters",
    component: CharactersTableComponent,
  },
  {
    path: "characters/add",
    component: AddCharacterComponent,
    canMatch: [isAdmin]
  },
  {
    path: "characters/:id",
    component: CharacterDetailComponent
  },
  {
    path: "characters/:id/edit",
    component: EditCharacterComponent,
    canMatch: [isModeratorOrAdmin]
  }
]
