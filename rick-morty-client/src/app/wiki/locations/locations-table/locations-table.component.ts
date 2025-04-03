import { Component, inject, OnInit, signal } from '@angular/core';
import { ContentTableComponent } from '../../../shared/content-table/content-table.component';
import { WikiService } from '../../wiki.service';
import { LocationSummaryModel } from '../locationSummary.model';
import { RouterLink } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { ConnectionErrorComponent } from '../../../shared/connection-error/connection-error.component';

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
export class LocationsTableComponent implements OnInit{
  locations: LocationSummaryModel[] = [];
  isFetching = signal(true);
  isError = signal(false);
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => {
          this.locations = locations
          this.isFetching.set(false);
          console.log(this.isFetching())
        },
        error: () => this.isError.set(true)
      });
  }

  protected readonly String = String;
}
