import { Component, input } from '@angular/core';
import { CharacterModel } from '../../wiki/characters/character.model';

@Component({
  selector: 'app-character-edit-table',
  imports: [],
  templateUrl: './character-edit-table.component.html',
})
export class CharacterEditTableComponent {
  characters = input.required<CharacterModel[] | undefined>();
}
