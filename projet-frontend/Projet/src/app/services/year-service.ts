import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { ApiService } from './api-service';
import { BehaviorSubject, Observable } from 'rxjs';
import { Year } from '../components/shared/types/year.type';

@Injectable({
  providedIn: 'root',
})
export class YearService {
  private _currentYearId: number | undefined;

  private selectedYearSubject = new BehaviorSubject<Year | null>(null);
  selectedYear$ = this.selectedYearSubject.asObservable();

  private readonly isBrowser!: boolean;

  // on application init, we need to set the current year from local storage
  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private _api: ApiService
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    if (this.isBrowser) {
      this._currentYearId = parseInt(
        localStorage.getItem('currentYearId') || ''
      );
    }
  }

  getAllYears(): Observable<Year[]> {
    return this._api.allYears();
  }

  get currentYearId(): number {
    if (this._currentYearId === undefined) {
      throw new Error('Current year is not set');
    } else {
      return this._currentYearId;
    }
  }

  set currentYearId(yearId: number) {
    if (this.isBrowser) {
      localStorage.setItem('currentYearId', yearId.toString());
    }
    this._currentYearId = yearId;
  }

  setSelectedYear(year: Year | null): void {
    this.selectedYearSubject.next(year);
  }

  getSelectedYearId(): number | null {
    return this._currentYearId || null;
  }
}
