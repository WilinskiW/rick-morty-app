import { Component } from '@angular/core';
import { TitleCasePipe } from "@angular/common";
import { CardFooterComponent } from './card-footer/card-footer.component';

@Component({
  selector: 'app-detail-card',
  imports: [
    TitleCasePipe,
    CardFooterComponent
  ],
  templateUrl: './detail-card.component.html',
  styleUrl: './detail-card.component.css'
})
export class DetailCardComponent {

}
