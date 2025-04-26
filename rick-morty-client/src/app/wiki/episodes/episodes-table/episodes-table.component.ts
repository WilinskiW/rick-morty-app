import { Component, input } from '@angular/core';
import { EpisodeModel } from '../episode.model';
import { RouterLink } from '@angular/router';
import { ContentTableComponent } from '../../../shared/components/content-table/content-table.component';
import { ConnectionErrorComponent } from '../../../shared/components/connection-error/connection-error.component';
import { DataTableClass } from '../../../shared/abstract/dataTable.class';
import { DatePipe } from '@angular/common';
import { TableNavComponent } from '../../../shared/components/content-table/table-nav/table-nav.component';
import { PageModel } from '../../page.model';

@Component({
  selector: 'app-episodes-locations',
  imports: [
    ContentTableComponent,
    RouterLink,
    ConnectionErrorComponent,
    DatePipe,
    TableNavComponent
  ],
  templateUrl: './episodes-table.component.html',
})
export class EpisodesTableComponent extends DataTableClass<EpisodeModel>{
  protected url: string = "http://localhost:8081/api/episodes";
  episodes = input<PageModel<EpisodeModel>>();
}
