/*
 * This file is part of the OWLlink API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, derivo GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, derivo GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.owllink.builtin.response;

import org.semanticweb.owlapi.owllink.Request;
import org.semanticweb.owlapi.owllink.Response;

import java.util.*;

/**
 * Author: Olaf Noppens
 * Date: 27.10.2009
 */
public class ResponseMessageImpl implements ResponseMessage {
    private Map<Request<?>, Object> responsesByRequests;
    protected List<Request<?>> requests;
    private boolean hasError = false;

    /**
     * @param requests requests 
     */
    public ResponseMessageImpl(Request<?>... requests) {
        this.requests = Collections.unmodifiableList(Arrays.asList(requests));
        this.responsesByRequests = new HashMap<>();
        for (Request<?> request : requests)
            this.responsesByRequests.put(request, null);
    }

    /**
     * @param <R> response type
     * @param request request 
     * @param response response 
     */
    public <R extends Response> void add(Request<R> request, R response) {
        this.responsesByRequests.put(request, response);
    }

    /**
     * @param response response 
     * @param index index 
     */
    public void add(Response response, int index) {
        this.responsesByRequests.put(this.requests.get(index), response);
    }

    /**
     * @param exception exception 
     * @param index index 
     */
    public void add(OWLlinkErrorResponseException exception, int index) {
        this.responsesByRequests.put(this.requests.get(index), exception);
        this.hasError = true;
    }

    @Override
    public Iterator<Response> iterator() {
        return new Iterator<Response>() {
            Iterator<Request<?>> internal = requests.iterator();

            @Override
            public boolean hasNext() {
                return this.internal.hasNext();
            }

            @Override
            public Response next() {
                Request<?> request = this.internal.next();
                return _getResponse(request);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean hasError() {
        return this.hasError;
    }

    /**
     * @param request request 
     * @param exception exception 
     */
    public void add(Request<?> request, OWLlinkErrorResponseException exception) {
        this.responsesByRequests.put(request, exception);
        this.hasError = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends Response> R getResponse(Request<R> request) throws OWLlinkErrorResponseException {
        return (R) this._getResponse(request);
    }

    protected Response _getResponse(Request<?> request) throws OWLlinkErrorResponseException {
        Object response = this.responsesByRequests.get(request);
        if (response instanceof Response) {
            return (Response) response;
        } else if (response instanceof OWLlinkErrorResponseException) {
            throw (OWLlinkErrorResponseException) response;
        }
        return null;
    }

    @Override
    public boolean hasErrorResponse(Request<?> request) {
        return responsesByRequests.get(request) instanceof OWLlinkErrorResponseException;
    }

    @Override
    public boolean isErrorResponse(int index) {
        return hasErrorResponse(this.requests.get(index));
    }

    @Override
    public OWLlinkErrorResponseException getError(Request<?> request) {
        Object error = this.responsesByRequests.get(request);
        if (error instanceof OWLlinkErrorResponseException)
            return (OWLlinkErrorResponseException) error;
        return null;
    }

    @Override
    public Response get(int index) throws OWLlinkErrorResponseException {
        Object response = this.responsesByRequests.get(this.requests.get(index));
        if (response instanceof Response) {
            return (Response) response;
        } else if (response instanceof OWLlinkErrorResponseException) {
            throw (OWLlinkErrorResponseException) response;
        }
        return null;
    }

    @Override
    public OWLlinkErrorResponseException getError(int index) {
        return this.getError(this.requests.get(index));
    }
}
