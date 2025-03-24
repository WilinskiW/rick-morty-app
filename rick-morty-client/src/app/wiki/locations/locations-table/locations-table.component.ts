import { Component, inject, OnInit, signal } from '@angular/core';
import { ContentTableComponent } from '../../../shared/content-table/content-table.component';
import { TitleCasePipe } from '@angular/common';
import { WikiService } from '../../wiki.service';
import { LocationSummaryModel } from '../locationSummary.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-location-table',
  imports: [
    ContentTableComponent,
    TitleCasePipe,
    RouterLink
  ],
  templateUrl: './locations-table.component.html',
  styleUrl: './locations-table.component.css'
})
export class LocationsTableComponent implements OnInit{
  locations: LocationSummaryModel[] = [];
  isFetching = signal(false);
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<LocationSummaryModel[]>("http://localhost:8081/api/locations")
      .subscribe({
        next: (locations) => this.locations = locations,
        complete: () => {
          this.isFetching.set(true);
        }
      });
  }

  protected readonly String = String;
}
