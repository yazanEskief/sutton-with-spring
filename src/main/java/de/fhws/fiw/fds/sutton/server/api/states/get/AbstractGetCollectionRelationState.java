package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractGetCollectionRelationState extends the {@link AbstractGetCollectionState} with the required
 * functionality to request a collection of sub-resources related to a specific main resource from the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * * {@link AbstractGetCollectionRelationState.AbstractGetCollectionRelationStateBuilder}</p>
 *
 * @see AbstractGetCollectionState
 */
public abstract class AbstractGetCollectionRelationState<T extends AbstractModel, R> extends AbstractGetCollectionState<T, R> {

    /**
     * id {@link Long} of the main resource
     */
    protected long primaryId;

    /**
     * The query {@link AbstractRelationQuery} to be used to fetch sub-resources associated with a primary resource
     * from the database
     */
    protected AbstractRelationQuery<T, R> query;

    public AbstractGetCollectionRelationState(final AbstractGetCollectionRelationStateBuilder<T, R> builder) {
        super(builder);
        this.primaryId = builder.parentId;
        this.query = builder.query;
        super.query = this.query;
    }

    public static abstract class AbstractGetCollectionRelationStateBuilder<T extends AbstractModel, R>
            extends AbstractGetCollectionStateBuilder<T, R> {
        protected long parentId;

        protected AbstractRelationQuery<T, R> query;

        public AbstractGetCollectionRelationStateBuilder<T, R> setParentId(final long parentId) {
            this.parentId = parentId;
            return this;
        }

        public AbstractGetCollectionRelationStateBuilder<T, R> setQuery(final AbstractRelationQuery<T, R> query) {
            this.query = query;
            return this;
        }
    }
}
