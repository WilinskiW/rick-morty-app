import { Component, inject, input, OnChanges, OnInit, signal, SimpleChanges } from '@angular/core';
import { CharacterModel } from '../character.model';
import { AuthService } from '../../../auth/auth.service';
import { AsyncPipe } from '@angular/common';
import { WikiService } from '../../wiki.service';

@Component({
  selector: 'app-character-edit-table',
  imports: [AsyncPipe],
  templateUrl: './character-edit-table.component.html',
})
export class CharacterEditTableComponent implements OnInit, OnChanges{
  id = input.required<number | undefined>();
  section = input.required<string>();
  charactersInput = input.required<CharacterModel[] | undefined>();
  characters = signal<CharacterModel[] | undefined>([]);
  user$ = inject(AuthService).user$;
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.characters.set(this.charactersInput());
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['charactersInput'] && changes['charactersInput'].currentValue) {
      this.characters.set(changes['charactersInput'].currentValue);
    }
  }

  removeCharacter(characterId: number | undefined) {
    this.wikiService
      .deleteData(`http://localhost:8081/api/${this.section()}/${String(this.id())}/remove-character/${characterId}`)
      .subscribe(() => {
        if (this.characters()) {
          this.characters.set(
            this.characters()!.filter((c) => c.id !== characterId)
          );
        }
      });
  }

  goToEdit(id: number | undefined) {
    this.wikiService.navigateTo('characters', String(id), 'edit');
  }
}
