import { Component} from '@angular/core';
import { EpisodeModel } from '../episode.model';
import { RouterLink } from '@angular/router';
import { ContentTableComponent } from '../../../shared/components/content-table/content-table.component';
import { ConnectionErrorComponent } from '../../../shared/components/connection-error/connection-error.component';
import { DataTableClass } from '../../../shared/abstract/dataTable.class';

@Component({
  selector: 'app-episodes-locations',
  imports: [
    ContentTableComponent,
    RouterLink,
    ConnectionErrorComponent
  ],
  templateUrl: './episodes-table.component.html',
})
export class EpisodesTableComponent extends DataTableClass<EpisodeModel>{
  protected url: string = "http://localhost:8081/api/episodes";
}
