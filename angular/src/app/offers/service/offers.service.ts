import {Observable} from 'rxjs';
import {Offer} from '../model/offer.model';

export interface OffersService {

    getById(offerId: number): Observable<Offer>

    getAll(): Observable<Offer[]>

    save(offer: Offer): Observable<Offer>

    update(offer: Offer): Observable<void>

    remove(offerId: number): Observable<void>

    upload(file: File, type: string): Observable<string>

}
