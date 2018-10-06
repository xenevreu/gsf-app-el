import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReinfItemControle } from 'app/shared/model/reinf-item-controle.model';
import { ReinfItemControleService } from './reinf-item-controle.service';
import { ReinfItemControleComponent } from './reinf-item-controle.component';
import { ReinfItemControleDetailComponent } from './reinf-item-controle-detail.component';
import { ReinfItemControleUpdateComponent } from './reinf-item-controle-update.component';
import { ReinfItemControleDeletePopupComponent } from './reinf-item-controle-delete-dialog.component';
import { IReinfItemControle } from 'app/shared/model/reinf-item-controle.model';

@Injectable({ providedIn: 'root' })
export class ReinfItemControleResolve implements Resolve<IReinfItemControle> {
    constructor(private service: ReinfItemControleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reinfItemControle: HttpResponse<ReinfItemControle>) => reinfItemControle.body));
        }
        return of(new ReinfItemControle());
    }
}

export const reinfItemControleRoute: Routes = [
    {
        path: 'reinf-item-controle',
        component: ReinfItemControleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfItemControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-item-controle/:id/view',
        component: ReinfItemControleDetailComponent,
        resolve: {
            reinfItemControle: ReinfItemControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfItemControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-item-controle/new',
        component: ReinfItemControleUpdateComponent,
        resolve: {
            reinfItemControle: ReinfItemControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfItemControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-item-controle/:id/edit',
        component: ReinfItemControleUpdateComponent,
        resolve: {
            reinfItemControle: ReinfItemControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfItemControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reinfItemControlePopupRoute: Routes = [
    {
        path: 'reinf-item-controle/:id/delete',
        component: ReinfItemControleDeletePopupComponent,
        resolve: {
            reinfItemControle: ReinfItemControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfItemControle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
