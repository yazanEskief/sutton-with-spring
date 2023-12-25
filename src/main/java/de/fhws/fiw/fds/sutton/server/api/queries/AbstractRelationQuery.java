package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The AbstractRelationQuery extends the functionality of the {@link AbstractQuery} in order to fetch associated
 * data from the database
 */
public abstract class AbstractRelationQuery<T extends AbstractModel, R> extends AbstractQuery<T, R> {

    protected long primaryId;

    protected AbstractRelationQuery(final long primaryId) {
        this.primaryId = primaryId;
    }

    public AbstractRelationQuery<T, R> setPagingBehavior(final PagingBehavior<T, R> pagingBehavior) {
        super.setPagingBehavior(pagingBehavior);
        return this;
    }

}
