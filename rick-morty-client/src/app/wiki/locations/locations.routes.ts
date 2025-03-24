import { LocationsTableComponent } from './locations-table/locations-table.component';
import { LocationDetailComponent } from './location-detail/location-detail.component';

export const locationsRoutes = [
  {
    path: "locations",
    component: LocationsTableComponent,
  },
  {
    path: "locations/:id",
    component: LocationDetailComponent,
  }
]
