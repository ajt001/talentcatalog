import {Injectable} from '@angular/core';
import {Candidate} from '../model/candidate';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {SearchResults} from '../model/search-results';
import {map} from "rxjs/operators";
import {SavedSearchRunRequest} from "../model/saved-search";

@Injectable({providedIn: 'root'})
export class CandidateService {

  private apiUrl = environment.apiUrl + '/candidate';

  constructor(private http:HttpClient) {}

  runSavedSearch(runRequest: SavedSearchRunRequest): Observable<SearchResults<Candidate>> {
    return this.http.post<SearchResults<Candidate>>(`${this.apiUrl}/runsavedsearch`, runRequest);
  }

  search(request): Observable<SearchResults<Candidate>> {
    return this.http.post<SearchResults<Candidate>>(`${this.apiUrl}/search`, request);
  }

  findByCandidateEmail(request): Observable<SearchResults<Candidate>> {
    return this.http.post<SearchResults<Candidate>>(`${this.apiUrl}/findbyemail`, request);
  }

  findByCandidateNumberOrName(request): Observable<SearchResults<Candidate>> {
    return this.http.post<SearchResults<Candidate>>(`${this.apiUrl}/findbynumberorname`, request);
  }

  findByCandidatePhone(request): Observable<SearchResults<Candidate>> {
    return this.http.post<SearchResults<Candidate>>(`${this.apiUrl}/findbyphone`, request);
  }

  get(id: number): Observable<Candidate> {
    return this.http.get<Candidate>(`${this.apiUrl}/${id}`);
  }

  create(details): Observable<Candidate>  {
    return this.http.post<Candidate>(`${this.apiUrl}`, details);
  }

  updateLinks(id: number, details): Observable<Candidate>  {
    return this.http.put<Candidate>(`${this.apiUrl}/${id}/links`, details);
  }

  updateStatus(id: number, details): Observable<Candidate>  {
    return this.http.put<Candidate>(`${this.apiUrl}/${id}/status`, details);
  }

  update(id: number, details): Observable<Candidate>  {
    return this.http.put<Candidate>(`${this.apiUrl}/${id}`, details);
  }

  delete(id: number): Observable<boolean>  {
    return this.http.delete<boolean>(`${this.apiUrl}/${id}`);
  }

  //todo Implement
  exportFromSavedSearch(request: SavedSearchRunRequest, size: number) {
    return this.http.post(`${this.apiUrl}/exportsearch/csv`, request, {responseType: 'blob'});
  }

  export(request) {
    return this.http.post(`${this.apiUrl}/export/csv`, request, {responseType: 'blob'});
  }

  downloadCv(candidateId: number) {
    return this.http.get(`${this.apiUrl}/${candidateId}/cv.pdf`, {responseType: 'blob'}).pipe(map(res => {
      return new Blob([res], { type: 'application/pdf', });
    }));
  }


}
