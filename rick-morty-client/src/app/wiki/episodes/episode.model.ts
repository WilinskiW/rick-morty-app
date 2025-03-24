import { CharacterModel } from '../characters/character.model';

export interface EpisodeModel {
  id: number,
  title: string,
  airDate: string,
  episode: string,
  characters: CharacterModel[]
}
