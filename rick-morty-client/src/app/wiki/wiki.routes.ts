import { charactersRoutes } from './characters/characters.routes';
import { locationsRoutes } from './locations/locations.routes';
import { episodesRoutes } from './episodes/episodes.routes';

export const wikiRoutes = [
  ...charactersRoutes,
  ...locationsRoutes,
  ...episodesRoutes
]
