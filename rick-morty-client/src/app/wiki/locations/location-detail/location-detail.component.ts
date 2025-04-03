import { Component, inject, input, OnInit, signal } from '@angular/core';
import { WikiService } from '../../wiki.service';
import { LocationModel } from '../location.model';
import { TitleCasePipe } from '@angular/common';
import { DetailCardComponent } from '../../../shared/components/detail-card/detail-card.component';

@Component({
  selector: 'app-location-detail',
  imports: [
    TitleCasePipe,
    DetailCardComponent
  ],
  templateUrl: './location-detail.component.html',
})
export class LocationDetailComponent implements OnInit{
  id = input.required<string>();
  isResidentExist = signal(false);
  location: LocationModel | undefined;
  private wikiService = inject(WikiService);

  ngOnInit() {
    this.wikiService.fetchData<LocationModel>(`http://localhost:8081/api/locations/${this.id()}`)
      .subscribe({
        next: (data) => {
          this.location = data;
          this.isResidentExist.set(this.location.residents.length > 0);
        },
        error: () => this.wikiService.goToResourceNotFound()
      });
  }
}
