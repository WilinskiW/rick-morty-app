import { LocationSummaryModel } from '../locations/locationSummary.model';

export interface CharacterModel {
  id: number,
  name: string,
  status: string,
  species: string,
  type: string,
  gender: string,
  origin: LocationSummaryModel
  currentLocation: LocationSummaryModel
  imageUrl: string
}
