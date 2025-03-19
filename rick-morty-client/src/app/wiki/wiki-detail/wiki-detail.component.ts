import { Component, input } from '@angular/core';
import { CharacterComponent } from './character/character.component';
import { LocationComponent } from './location/location.component';
import { EpisodeComponent } from './episode/episode.component';

@Component({
  selector: 'app-wiki-detail',
  imports: [
    CharacterComponent,
    LocationComponent,
    EpisodeComponent,
    ],
  templateUrl: './wiki-detail.component.html',
  styleUrl: './wiki-detail.component.css'
})
export class WikiDetailComponent {
  section = input.required<string>();
  dataId = input.required<number>();
}
