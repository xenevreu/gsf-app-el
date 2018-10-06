import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReinfControle } from 'app/shared/model/reinf-controle.model';
import { ReinfControleService } from './reinf-controle.service';
import { ReinfControleComponent } from './reinf-controle.component';
import { ReinfControleDetailComponent } from './reinf-controle-detail.component';
import { ReinfControleUpdateComponent } from './reinf-controle-update.component';
import { ReinfControleDeletePopupComponent } from './reinf-controle-delete-dialog.component';
import { IReinfControle } from 'app/shared/model/reinf-controle.model';

@Injectable({ providedIn: 'root' })
export class ReinfControleResolve implements Resolve<IReinfControle> {
    constructor(private service: ReinfControleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reinfControle: HttpResponse<ReinfControle>) => reinfControle.body));
        }
        return of(new ReinfControle());
    }
}

export const reinfControleRoute: Routes = [
    {
        path: 'reinf-controle',
        component: ReinfControleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-controle/:id/view',
        component: ReinfControleDetailComponent,
        resolve: {
            reinfControle: ReinfControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-controle/new',
        component: ReinfControleUpdateComponent,
        resolve: {
            reinfControle: ReinfControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-controle/:id/edit',
        component: ReinfControleUpdateComponent,
        resolve: {
            reinfControle: ReinfControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfControle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reinfControlePopupRoute: Routes = [
    {
        path: 'reinf-controle/:id/delete',
        component: ReinfControleDeletePopupComponent,
        resolve: {
            reinfControle: ReinfControleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfControle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
