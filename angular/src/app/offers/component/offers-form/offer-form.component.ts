import {Component, EventEmitter, Inject, Input, Output} from '@angular/core';
import {Offer} from "../../model/offer.model";
import {Image} from "../../model/image.model";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {OffersService} from "../../service/offers.service";
import {Observable} from "rxjs";

@Component({
    selector: 'app-offer-form',
    templateUrl: './offer-form.component.html',
    styleUrls: ['./offer-form.component.css']
})
export class OfferFormComponent {

    @Input()
    offer: Offer;
    @Output()
    save = new EventEmitter();
    @Output()
    cancel = new EventEmitter();
    isEnabled = true;
    isProcessing = false;

    constructor(@Inject('OffersService') private offersService: OffersService, private route: ActivatedRoute, router: Router) {
        router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.offer = this.route.snapshot.data.offer;
                this.isEnabled = false;
            }
        });
    }

    saveOffer(offerForm) {
        if (offerForm.valid) {
            this.save.emit(this.offer);
        }
    }

    uploadImage(file: File) {
        this.offersService.upload(file, "img")
            .subscribe(url => console.log(url)); //this.offer.images.push(new Image(url)), ex => console.log(ex)));
    }

    private subscribe(observable: Observable<any>) {
        this.isProcessing = true;
        observable.subscribe(() => this.refresh());
    }

    refresh() {
        this.isProcessing = false;
    }

}
