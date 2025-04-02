import { EpisodesTableComponent } from './episodes-table/episodes-table.component';
import { EpisodeDetailComponent } from './episode-detail/episode-detail.component';
import { AddEpisodeComponent } from './add-episode/add-episode.component';
import { EditEpisodeComponent } from './edit-episode/edit-episode.component';
import { isAdmin, isModeratorOrAdmin } from '../../auth/auth.guard';

export const episodesRoutes = [
  {
    path: "episodes",
    component: EpisodesTableComponent,
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
