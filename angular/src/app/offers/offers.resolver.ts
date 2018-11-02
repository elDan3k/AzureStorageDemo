import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Offer} from "./model/offer.model";
import {Observable} from "rxjs";
import {OffersService} from "./service/offers.service";
import {Inject} from "@angular/core";

export class OffersResolver implements Resolve<Offer> {

  constructor(@Inject('OffersService') private offersService: OffersService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Offer> {
    return this.offersService.getById(+route.paramMap.get('id'));
  }

}
