import { LocationsTableComponent } from './locations-table/locations-table.component';
import { LocationDetailComponent } from './location-detail/location-detail.component';
import { AddLocationComponent } from './add-location/add-location.component';
import { EditLocationComponent } from './edit-location/edit-location.component';
import { isAdmin, isModeratorOrAdmin } from '../../auth/auth.guard';
import { ActivatedRouteSnapshot, ResolveFn, RunGuardsAndResolvers } from '@angular/router';
import { inject } from '@angular/core';
import { WikiService } from '../wiki.service';
import { LocationModel } from './location.model';

const resolveData: ResolveFn<any> = (activatedRoute: ActivatedRouteSnapshot) => {
  const wikiService = inject(WikiService);
  const pageNumber = Number(activatedRoute.queryParamMap.get("page")) || 1;

  return wikiService.fetchDataWithPages<LocationModel>(
    "http://localhost:8081/api/locations", pageNumber
  );
};

export const locationsRoutes = [
  {
    path: "locations",
    component: LocationsTableComponent,
    runGuardsAndResolvers: "paramsOrQueryParamsChange" as RunGuardsAndResolvers,
    resolve: {
      locations: resolveData
    },
  },
  {
    path: "locations/add",
    component: AddLocationComponent,
    canMatch: [isAdmin]
  },
  {
    path: "locations/:id",
    component: LocationDetailComponent,
  },
  {
    path: "locations/:id/edit",
    component: EditLocationComponent,
    canMatch: [isModeratorOrAdmin]
  }
]
