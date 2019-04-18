package org.flabs.common.service;

import io.vertx.core.eventbus.EventBus;

public abstract class AbstractDataService {
    protected EventBus eventBus;

    public AbstractDataService(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
