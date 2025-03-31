import { Component, inject, input, OnInit } from '@angular/core';
import { CharacterModel } from '../character.model';
import { WikiService } from '../../wiki.service';
import { TitleCasePipe } from '@angular/common';
import { DetailCardComponent } from '../../../shared/detail-card/detail-card.component';

@Component({
  selector: 'app-character-detail',
  imports: [
    TitleCasePipe,
    DetailCardComponent
  ],
  templateUrl: './character-detail.component.html',
  standalone: true,
})
export class CharacterDetailComponent implements OnInit{
  character : CharacterModel | undefined;
  id = input.required<number>();
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<CharacterModel>(`http://localhost:8081/api/characters/${this.id()}`)
      .subscribe({
        next: (data) => {
          this.character = data;
        }
      })
  }
}
