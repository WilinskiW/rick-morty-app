import { Component, input } from '@angular/core';
import { CharacterModel } from '../character.model';
import { RouterLink } from '@angular/router';
import { TitleCasePipe } from '@angular/common';
import { DataTableClass } from '../../../shared/abstract/dataTable.class';
import { ConnectionErrorComponent } from '../../../shared/components/connection-error/connection-error.component';
import { ContentTableComponent } from '../../../shared/components/content-table/content-table.component';
import { TableNavComponent } from '../../../shared/components/content-table/table-nav/table-nav.component';
import { PageModel } from '../../page.model';

@Component({
  selector: 'app-characters-table',
  imports: [
    ContentTableComponent,
    RouterLink,
    TitleCasePipe,
    ConnectionErrorComponent,
    TableNavComponent
  ],
  templateUrl: './characters-table.component.html',
})
export class CharactersTableComponent extends DataTableClass<CharacterModel>{
  protected url = "http://localhost:8081/api/characters";
  characters = input<PageModel<CharacterModel>>();
}
