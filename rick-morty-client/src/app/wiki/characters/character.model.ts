import { LocationSummaryModel } from '../locations/locationSummary.model';

export interface CharacterModel {
  id?: number,
  name: string,
  status: string,
  species: string,
  type: string,
  gender: string,
  origin: LocationSummaryModel | null
  currentLocation: LocationSummaryModel | null
  imageUrl: string
}
