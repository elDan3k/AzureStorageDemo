import {Component, Inject} from '@angular/core';
import {Offer} from '../../model/offer.model';
import {OffersService} from '../../service/offers.service';
import {Observable, Subscription} from 'rxjs';

@Component({
    selector: 'app-offers-panel',
    templateUrl: './offers-panel.component.html',
    styleUrls: ['./offers-panel.component.css']
})
export class OffersPanelComponent {

    offers: Offer[] = [];
    selectedOffer: Offer = null;
    editedOffer: Offer = null;
    isProcessing = false;
    subscription: Subscription;

    constructor(@Inject('OffersService') private offersService: OffersService) {
        this.refresh();
    }

    select(offer: Offer) {
        this.selectedOffer = offer;
        this.editedOffer = Object.assign({}, offer);
    }

    save(offer: Offer) {
        if (offer.id) {
            this.subscribe(this.offersService.update(offer));
        } else {
            this.subscribe(this.offersService.save(offer));
        }
        this.reset();
    }

    private subscribe(observable: Observable<any>) {
        this.isProcessing = true;
        observable.subscribe(() => this.refresh());
    }

    reset() {
        this.selectedOffer = null;
        this.editedOffer = null;
    }

    add() {
        this.editedOffer = new Offer();
    }

    remove() {
        this.subscribe(this.offersService.remove(this.editedOffer.id));
        this.reset();
    }

    refreshOffers(observable: Observable<Offer[]>) {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
        this.subscription = observable.subscribe(
            offersPage => this.offers = offersPage,
            ex => {
                console.log(ex);
                this.isProcessing = false;
            },
            () => this.isProcessing = false
        );
        console.log(this.offers);
    }

    refresh() {
        this.isProcessing = true;
        this.refreshOffers(this.offersService.getAll());
    }

}
