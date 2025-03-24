import { Component, inject, input, OnInit } from '@angular/core';
import { WikiService } from '../../wiki.service';
import { LocationModel } from '../location.model';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-location-detail',
  imports: [
    TitleCasePipe
  ],
  templateUrl: './location-detail.component.html',
  styleUrl: './location-detail.component.css'
})
export class LocationDetailComponent implements OnInit{
  id = input.required<string>();
  location: LocationModel | undefined;
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<LocationModel>(`http://localhost:8081/api/locations/${this.id()}`)
      .subscribe({
        next: (data) => {
          this.location = data;
        },
        complete: () => {
          console.log(this.location);
        }
      });
  }
}
