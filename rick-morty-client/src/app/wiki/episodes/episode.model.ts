import { CharacterModel } from '../characters/character.model';

export interface EpisodeModel {
  id: number,
  title: string,
  date: Date,
  episode: string,
  characters: CharacterModel[]
}
