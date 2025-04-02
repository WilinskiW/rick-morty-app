import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { WikiComponent } from './wiki/wiki.component';
import { wikiRoutes } from './wiki/wiki.routes';
import { authRoutes } from './auth/auth.routes';
import { NotFoundComponent } from './not-found/not-found.component';

export const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "wiki",
    component: WikiComponent,
    children: [
      {
        path: "",
        pathMatch: "full",
        redirectTo: "characters" // default route for wiki
      },
      ...wikiRoutes
    ]
  },
  ...authRoutes,
  {
    path: "**",
    component: NotFoundComponent
  }
];
