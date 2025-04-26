import { EpisodesTableComponent } from './episodes-table/episodes-table.component';
import { EpisodeDetailComponent } from './episode-detail/episode-detail.component';
import { AddEpisodeComponent } from './add-episode/add-episode.component';
import { EditEpisodeComponent } from './edit-episode/edit-episode.component';
import { isAdmin, isModeratorOrAdmin } from '../../auth/auth.guard';
import { ActivatedRouteSnapshot, ResolveFn, RunGuardsAndResolvers } from '@angular/router';
import { inject } from '@angular/core';
import { WikiService } from '../wiki.service';
import { EpisodeModel } from './episode.model';

const resolveData: ResolveFn<any> = (activatedRoute: ActivatedRouteSnapshot) => {
  const wikiService = inject(WikiService);
  const pageNumber = Number(activatedRoute.queryParamMap.get("page")) || 1;

  return wikiService.fetchDataWithPages<EpisodeModel>(
    "http://localhost:8081/api/episodes", pageNumber
  );
};

export const episodesRoutes = [
  {
    path: "episodes",
    component: EpisodesTableComponent,
    runGuardsAndResolvers: "paramsOrQueryParamsChange" as RunGuardsAndResolvers,
    resolve: {
      episodes: resolveData
    },
  },
  {
    path: "episodes/add",
    component: AddEpisodeComponent,
    canMatch: [isAdmin]
  },
  {
    path: "episodes/:id",
    component: EpisodeDetailComponent,
  },
  {
    path: "episodes/:id/edit",
    component: EditEpisodeComponent,
    canMatch: [isModeratorOrAdmin]
  },
]
