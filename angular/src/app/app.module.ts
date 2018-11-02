import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {OffersModule} from './offers/offers.module';
import {Api} from './api';
import {routerModule} from './app.routing';
import {HttpClient} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        OffersModule,
        routerModule
    ],
    providers: [
        Api
    ],
    bootstrap: [AppComponent]
})
export class AppModule {

}

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
