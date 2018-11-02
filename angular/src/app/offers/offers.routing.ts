import {Route, RouterModule} from "@angular/router";
import {OffersPanelComponent} from "./component/offers-panel/offers-panel.component";

const routes: Route[] = [
    {
        path: '',
        component: OffersPanelComponent
    },
    {
        path: 'offers/dashboard',
        component: OffersPanelComponent
    }
];

export const routerModule = RouterModule.forChild(routes);
