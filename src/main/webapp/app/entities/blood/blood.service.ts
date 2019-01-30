import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBlood } from 'app/shared/model/blood.model';

type EntityResponseType = HttpResponse<IBlood>;
type EntityArrayResponseType = HttpResponse<IBlood[]>;

@Injectable({ providedIn: 'root' })
export class BloodService {
    public resourceUrl = SERVER_API_URL + 'api/blood';

    constructor(protected http: HttpClient) {}

    create(blood: IBlood): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(blood);
        return this.http
            .post<IBlood>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(blood: IBlood): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(blood);
        return this.http
            .put<IBlood>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBlood>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBlood[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(blood: IBlood): IBlood {
        const copy: IBlood = Object.assign({}, blood, {
            timestamp: blood.timestamp != null && blood.timestamp.isValid() ? blood.timestamp.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((blood: IBlood) => {
                blood.timestamp = blood.timestamp != null ? moment(blood.timestamp) : null;
            });
        }
        return res;
    }
}
