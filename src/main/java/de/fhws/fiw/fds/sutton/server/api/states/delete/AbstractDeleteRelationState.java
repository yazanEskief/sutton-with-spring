package de.fhws.fiw.fds.sutton.server.api.states.delete;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The AbstractDeleteRelationState extends the {@link AbstractDeleteState} class and defines the required properties
 * and methods to delete relations between main resources and their related sub-resources.
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractDeleteRelationState.AbstractDeleteRelationStateBuilder}</p>
 *
 * @see AbstractDeleteState
 */
public abstract class AbstractDeleteRelationState<T extends AbstractModel, R> extends AbstractDeleteState<T, R> {

    /**
     * id {@link Long} of the main resource
     */
    protected long primaryId;

    public AbstractDeleteRelationState(final AbstractDeleteRelationStateBuilder<R> builder) {
        super(builder);
        this.primaryId = builder.parentId;
    }

    public static abstract class AbstractDeleteRelationStateBuilder<R> extends AbstractDeleteStateBuilder<R> {
        /**
         * id {@link Long} of the main resource
         */
        protected long parentId;

        public AbstractDeleteRelationStateBuilder<R> setParentId(final long parentId) {
            this.parentId = parentId;
            return this;
        }
    }

}
