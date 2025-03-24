import { CharacterDetailComponent } from './characters/character-detail/character-detail.component';
import { CharactersTableComponent } from './characters/characters-table/character-table-data/characters-table.component';
import { LocationsTableComponent } from './locations/location-table/locations-table.component';
import { EpisodesLocationsComponent } from './episodes/episodes-locations/episodes-locations.component';
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
    component: EpisodesLocationsComponent,
  },
  {
    path: "episodes/:id",
    component: EpisodeDetailComponent,
  },
]
