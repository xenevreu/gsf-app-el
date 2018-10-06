import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';
import { ReinfStatusControleService } from './reinf-status-controle.service';
import { ReinfStatusControleComponent } from './reinf-status-controle.component';
import { ReinfStatusControleDetailComponent } from './reinf-status-controle-detail.component';
import { ReinfStatusControleUpdateComponent } from './reinf-status-controle-update.component';
import { ReinfStatusControleDeletePopupComponent } from './reinf-status-controle-delete-dialog.component';
import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';

@Injectable({ providedIn: 'root' })
export class ReinfStatusControleResolve implements Resolve<IReinfStatusControle> {
    constructor(private service: ReinfStatusControleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reinfStatusControle: HttpResponse<ReinfStatusControle>) => reinfStatusControle.body));
        }
        return of(new ReinfStatusControle());
    }
}

export const reinfStatusControleRoute: Routes = [
    {
        path: 'reinf-status-controle',
        component: ReinfStatusControleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfStatusControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-status-controle/:id/view',
        component: ReinfStatusControleDetailComponent,
        resolve: {
            reinfStatusControle: ReinfStatusControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfStatusControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-status-controle/new',
        component: ReinfStatusControleUpdateComponent,
        resolve: {
            reinfStatusControle: ReinfStatusControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfStatusControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-status-controle/:id/edit',
        component: ReinfStatusControleUpdateComponent,
        resolve: {
            reinfStatusControle: ReinfStatusControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfStatusControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reinfStatusControlePopupRoute: Routes = [
    {
        path: 'reinf-status-controle/:id/delete',
        component: ReinfStatusControleDeletePopupComponent,
        resolve: {
            reinfStatusControle: ReinfStatusControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfStatusControle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
