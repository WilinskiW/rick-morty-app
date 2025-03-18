import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CharactersComponent } from './wiki/characters/characters.component';

export const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "wiki/characters",
    component: CharactersComponent
  }
];
