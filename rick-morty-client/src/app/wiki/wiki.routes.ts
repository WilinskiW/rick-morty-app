import { ContentTableComponent } from '../shared/content-table/content-table.component';
import { CharacterDetailComponent } from './characters/character-detail/character-detail.component';
import { NoPhotoDetailComponent } from '../shared/no-photo-detail/no-photo-detail.component';
import {
  CharactersTableComponent
} from './characters/characters-table/character-table-data/characters-table.component';

export const wikiRoutes = [
  {
    path: "characters",
    component: CharactersTableComponent,
    data: {
      section: "Characters"
    }
  },
  {
    path: "characters/:id",
    component: CharacterDetailComponent
  },
  {
    path: "locations",
    component: ContentTableComponent,
    data: {
      section: "Locations",
      headers: ["ID", "Name", "Type", "Dimension", "Details"]
    }
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
    component: ContentTableComponent,
    data: {
      section: "Episodes",
      headers: ["ID", "Name", "Air date", "Episode", "Number of characters", "Details"]
    }
  },
  {
    path: "episodes/:id",
    component: NoPhotoDetailComponent,
    data: {
      infoFields: ["Episode code:", "Air date:", "Characters:"]
    }
  },
]
