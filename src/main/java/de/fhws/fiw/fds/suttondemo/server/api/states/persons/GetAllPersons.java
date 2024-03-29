/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.suttondemo.server.api.states.persons;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByFirstAndLastName;

import java.util.Collection;

public class GetAllPersons<R> extends AbstractGetCollectionState<R, Person> {

    public GetAllPersons(final Builder<R> builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {
        QueryByFirstAndLastName<R> theQuery = (QueryByFirstAndLastName<R>) this.query;
        int waitingTime = theQuery.getWaitingTime();

        try {
            Thread.sleep(waitingTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonUri.REL_PATH, PersonRelTypes.CREATE_PERSON, getAcceptRequestHeader());
    }

    public static class Builder<R> extends AbstractGetCollectionStateBuilder<R, Person> {

        @Override
        public AbstractState<R, Collection<Person>> build() {
            return new GetAllPersons<>(this);
        }
    }
}
