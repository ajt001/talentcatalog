import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SearchResults} from "../model/search-results";
import {SavedSearch} from "../model/saved-search";

@Injectable({
  providedIn: 'root'
})
export class SavedSearchService {

  private apiUrl: string = environment.apiUrl + '/saved-search';

  constructor(private http: HttpClient) { }

  search(request): Observable<SearchResults<SavedSearch>> {
    return this.http.post<SearchResults<SavedSearch>>(`${this.apiUrl}/search`, request);
  }

  load(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}/load`);
  }

  get(id: number): Observable<SavedSearch> {
    return this.http.get<SavedSearch>(`${this.apiUrl}/${id}`);
  }

  create(savedSearch: SavedSearch): Observable<SavedSearch>  {
    return this.http.post<SavedSearch>(`${this.apiUrl}`, savedSearch);
  }

  update(savedSearch: SavedSearch): Observable<SavedSearch>  {
    return this.http.put<SavedSearch>(`${this.apiUrl}/${savedSearch.id}`, savedSearch);
  }

  delete(id: number): Observable<boolean>  {
    return this.http.delete<boolean>(`${this.apiUrl}/${id}`);
  }

}
