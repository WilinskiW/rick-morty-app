import { Component } from '@angular/core';
import { LocationSummaryModel } from '../locationSummary.model';
import { RouterLink } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { ContentTableComponent } from '../../../shared/components/content-table/content-table.component';
import { ConnectionErrorComponent } from '../../../shared/components/connection-error/connection-error.component';
import { DataTableClass } from '../../../shared/abstract/dataTable.class';

@Component({
  selector: 'app-location-table',
  imports: [
    ContentTableComponent,
    RouterLink,
    TitleCasePipe,
    ConnectionErrorComponent
  ],
  templateUrl: './locations-table.component.html',
})
export class LocationsTableComponent extends DataTableClass<LocationSummaryModel> {
  protected url: string = "http://localhost:8081/api/locations";
}
