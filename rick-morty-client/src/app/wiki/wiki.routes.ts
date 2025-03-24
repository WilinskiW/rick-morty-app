import { CharacterDetailComponent } from './characters/character-detail/character-detail.component';
import { NoPhotoDetailComponent } from '../shared/no-photo-detail/no-photo-detail.component';
import {
  CharactersTableComponent
} from './characters/characters-table/character-table-data/characters-table.component';
import { LocationsTableComponent } from './locations/location-table/locations-table.component';
import { EpisodesLocationsComponent } from './episodes/episodes-locations/episodes-locations.component';

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
    component: NoPhotoDetailComponent,
    data: {
      infoFields: ["Type:", "Dimension:", "Residents:"]
    }
  },
  {
    path: "episodes",
    component: EpisodesLocationsComponent,
  },
  {
    path: "episodes/:id",
    component: NoPhotoDetailComponent,
    data: {
      infoFields: ["Episode code:", "Air date:", "Characters:"]
    }
  },
]
