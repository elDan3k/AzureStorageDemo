import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {OffersService} from './offers.service';
import {Offer} from '../model/offer.model';
import {Api} from '../../api';
import {RequestOptions} from "@angular/http";

@Injectable()
export class HttpOffersService implements OffersService {

    constructor(private httpClient: HttpClient, private api: Api) {
    }

    getById(offerId: number): Observable<Offer> {
        return this.httpClient.get<Offer>(`${this.api.offers}/${offerId}`);
    }

    getAll(): Observable<Offer[]> {
        return this.httpClient.get<Offer[]>(this.api.offers);
    }

    save(offer: Offer): Observable<Offer> {
        return this.httpClient.post<Offer>(this.api.offers, offer);
    }

    update(offer: Offer): Observable<void> {
        return this.httpClient.put<void>(`${this.api.offers}/${offer.id}`, offer);
    }

    remove(offerId: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.api.offers}/${offerId}`);
    }

    upload(file: File, type: string): Observable<string> {

        let formData = new FormData();
        formData.append("file", file);
        formData.append("type", type);

        let headers = new HttpHeaders();
        headers.append("Content-Type", "multipart/form-data").append("Accept", "application/json");
        headers.append("responseType", "text");

        let params = new HttpParams();
        //params.append("type", type);


        let options = {
            headers
        };

        return this.httpClient.post<string>(`${this.api.files}`, formData, options);
        //headers: new HttpHeaders().append("Content-Type", "multipart/form-data").append("Accept", "application/json").append("Data", formData)});
        //params: new HttpParams().append("type", type)})
    }
}
