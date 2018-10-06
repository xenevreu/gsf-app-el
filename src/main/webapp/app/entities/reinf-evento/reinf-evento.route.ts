import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReinfEvento } from 'app/shared/model/reinf-evento.model';
import { ReinfEventoService } from './reinf-evento.service';
import { ReinfEventoComponent } from './reinf-evento.component';
import { ReinfEventoDetailComponent } from './reinf-evento-detail.component';
import { ReinfEventoUpdateComponent } from './reinf-evento-update.component';
import { ReinfEventoDeletePopupComponent } from './reinf-evento-delete-dialog.component';
import { IReinfEvento } from 'app/shared/model/reinf-evento.model';

@Injectable({ providedIn: 'root' })
export class ReinfEventoResolve implements Resolve<IReinfEvento> {
    constructor(private service: ReinfEventoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reinfEvento: HttpResponse<ReinfEvento>) => reinfEvento.body));
        }
        return of(new ReinfEvento());
    }
}

export const reinfEventoRoute: Routes = [
    {
        path: 'reinf-evento',
        component: ReinfEventoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfEvento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-evento/:id/view',
        component: ReinfEventoDetailComponent,
        resolve: {
            reinfEvento: ReinfEventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfEvento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-evento/new',
        component: ReinfEventoUpdateComponent,
        resolve: {
            reinfEvento: ReinfEventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfEvento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reinf-evento/:id/edit',
        component: ReinfEventoUpdateComponent,
        resolve: {
            reinfEvento: ReinfEventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfEvento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reinfEventoPopupRoute: Routes = [
    {
        path: 'reinf-evento/:id/delete',
        component: ReinfEventoDeletePopupComponent,
        resolve: {
            reinfEvento: ReinfEventoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gsfappelApp.reinfEvento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
