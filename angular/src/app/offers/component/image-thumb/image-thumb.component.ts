import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Offer} from '../../model/offer.model';

@Component({
    selector: 'app-offer-card',
    templateUrl: './offer-card.component.html',
    styleUrls: ['./offer-card.component.css']
})
export class OfferCardComponent {

    @Input()
    offer: Offer;
    @Input()
    isSelected = false;
    @Output()
    offerSelected = new EventEmitter();

    select() {
        this.offerSelected.emit(this.offer);
    }

    getPhoto() {
        if (this.offer.images.length > 0) {
            return this.offer.images[0].url;
        } else {
            return "http://placehold.it/318x320";
        }
    }

}
