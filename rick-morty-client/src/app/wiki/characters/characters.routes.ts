import { ActivatedRouteSnapshot, ResolveFn, Route } from '@angular/router';
import { CharactersTableComponent } from './characters-table/characters-table.component';
import { CharacterDetailComponent } from './character-detail/character-detail.component';
import { AddCharacterComponent } from './add-character/add-character.component';
import { EditCharacterComponent } from './edit-characters/edit-character.component';
import { isAdmin, isModeratorOrAdmin } from '../../auth/auth.guard';
import { inject } from '@angular/core';
import { WikiService } from '../wiki.service';
import { CharacterModel } from './character.model';

const resolveData: ResolveFn<any> = (activatedRoute: ActivatedRouteSnapshot) => {
  const wikiService = inject(WikiService);
  const pageNumber = Number(activatedRoute.queryParamMap.get("page")) || 1;

  return wikiService.fetchDataWithPages<CharacterModel>(
    "http://localhost:8081/api/characters", pageNumber
  );
};

export const charactersRoutes: Route[] = [
  {
    path: "characters",
    component: CharactersTableComponent,
    runGuardsAndResolvers: "paramsOrQueryParamsChange",
    resolve: {
      characters: resolveData
    }
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
