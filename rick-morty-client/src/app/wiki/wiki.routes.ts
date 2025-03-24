import { CharacterDetailComponent } from './characters/character-detail/character-detail.component';
import { CharactersTableComponent } from './characters/characters-table/character-table-data/characters-table.component';
import { LocationsTableComponent } from './locations/locations-table/locations-table.component';
import { EpisodesTableComponent } from './episodes/episodes-table/episodes-table.component';
import { EpisodeDetailComponent } from './episodes/episode-detail/episode-detail.component';
import { LocationDetailComponent } from './locations/location-detail/location-detail.component';

export const wikiRoutes = [
  {
    path: "characters",
    component: CharactersTableComponent,
  },
  {
    path: "characters/:id",
    component: CharacterDetailComponent
  },
  {
    path: "locations",
    component: LocationsTableComponent,
  },
  {
    path: "locations/:id",
    component: LocationDetailComponent,
  },
  {
    path: "episodes",
    component: EpisodesTableComponent,
  },
  {
    path: "episodes/:id",
    component: EpisodeDetailComponent,
  },
]
