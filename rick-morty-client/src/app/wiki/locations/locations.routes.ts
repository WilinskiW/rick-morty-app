import { LocationsTableComponent } from './locations-table/locations-table.component';
import { LocationDetailComponent } from './location-detail/location-detail.component';
import { AddLocationComponent } from './add-location/add-location.component';
import { EditLocationComponent } from './edit-location/edit-location.component';

export const locationsRoutes = [
  {
    path: "locations",
    component: LocationsTableComponent,
  },
  {
    path: "locations/add",
    component: AddLocationComponent,
  },
  {
    path: "locations/:id",
    component: LocationDetailComponent,
  },
  {
    path: "locations/:id/edit",
    component: EditLocationComponent,
  }
]
