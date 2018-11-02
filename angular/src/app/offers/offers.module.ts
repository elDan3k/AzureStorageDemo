import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OfferCardComponent} from './component/offer-card/offer-card.component';
import {OfferFormComponent} from './component/offers-form/offer-form.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {OffersPanelComponent} from './component/offers-panel/offers-panel.component';
import {HttpOffersService} from './service/http-offers.service';
import {HttpClientModule} from '@angular/common/http';
import {routerModule} from './offers.routing';
import offersData from './model/offer.data';
import {OffersResolver} from './offers.resolver';
import {TranslateModule} from '@ngx-translate/core';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        routerModule,
        TranslateModule
    ],
    declarations: [
        OfferCardComponent,
        OfferFormComponent,
        OffersPanelComponent
    ],
    providers: [
        {
            provide: 'OffersService',
            useClass: HttpOffersService
        },
        {
            provide: 'OffersData',
            useValue: offersData
        },
        OffersResolver
    ],
    exports: [
        OffersPanelComponent
    ]
})
export class OffersModule {
}
