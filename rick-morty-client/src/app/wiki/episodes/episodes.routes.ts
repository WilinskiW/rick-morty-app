import { EpisodesTableComponent } from './episodes-table/episodes-table.component';
import { EpisodeDetailComponent } from './episode-detail/episode-detail.component';

export const episodesRoutes = [
  {
    path: "episodes",
    component: EpisodesTableComponent,
  },
  {
    path: "episodes/:id",
    component: EpisodeDetailComponent,
  },
]
