import { CharacterModel } from '../characters/character.model';

export interface LocationModel {
  id: number,
  name: string,
  type: string,
  dimension: string,
  residents: CharacterModel[]
}
