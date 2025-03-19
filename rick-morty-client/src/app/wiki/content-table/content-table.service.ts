import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ContentTableService {
  private TABLE_HEADERS = new Map<string, string[]>([
    ["Characters", ["ID", "Name", "Status", "Type", "Gender", "Origin", "Last Seen", "Details"]],
    ["Locations", ["ID", "Name", "Type", "Dimension", "Details"]],
    ["Episodes", ["ID", "Name", "Air date", "Episode", "Number of characters", "Details"]]
  ]);

  tableHeaders(location: string): string[] {
    return this.TABLE_HEADERS.get(location) || [];
  }
}
