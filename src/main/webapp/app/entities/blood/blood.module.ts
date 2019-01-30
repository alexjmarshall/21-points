import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TwentyOnePointsSharedModule } from 'app/shared';
import { TwentyOnePointsAdminModule } from 'app/admin/admin.module';
import {
    BloodComponent,
    BloodDetailComponent,
    BloodUpdateComponent,
    BloodDeletePopupComponent,
    BloodDeleteDialogComponent,
    bloodRoute,
    bloodPopupRoute
} from './';

const ENTITY_STATES = [...bloodRoute, ...bloodPopupRoute];

@NgModule({
    imports: [TwentyOnePointsSharedModule, TwentyOnePointsAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BloodComponent, BloodDetailComponent, BloodUpdateComponent, BloodDeleteDialogComponent, BloodDeletePopupComponent],
    entryComponents: [BloodComponent, BloodUpdateComponent, BloodDeleteDialogComponent, BloodDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TwentyOnePointsBloodModule {}
