import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Cop4331SharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {AgmCoreModule} from '@agm/core';

@NgModule({
    imports: [
        Cop4331SharedModule,
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyAV-TB_B7OrFHvsesiRaJbGXD5BsxdJ6TU'
        }),
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Cop4331HomeModule {}
