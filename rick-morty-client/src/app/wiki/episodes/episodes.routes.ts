import { EpisodesTableComponent } from './episodes-table/episodes-table.component';
import { EpisodeDetailComponent } from './episode-detail/episode-detail.component';
import { AddEpisodeComponent } from './add-episode/add-episode.component';

export const episodesRoutes = [
  {
    path: "episodes",
    component: EpisodesTableComponent,
  },
  {
    path: "episodes/add",
    component: AddEpisodeComponent,
  },
  {
    path: "episodes/:id",
    component: EpisodeDetailComponent,
  },
]
