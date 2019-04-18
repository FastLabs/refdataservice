package org.flabs.refdata.currency.service;

import io.reactivex.Single;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import io.vertx.servicediscovery.spi.ServiceType;
import io.vertx.servicediscovery.types.AbstractServiceReference;
import org.flabs.refdata.currency.model.CurrencyPair;

import java.util.List;

public interface CurrencyReferenceDataService extends ServiceType {
    String PROVIDER_ADDRESS = "ref-data.currency-pair";
    String SERVICE_NAME = "ref-data-service";

    static Record createRecord() {
        return new Record().setType(SERVICE_NAME).setName(SERVICE_NAME);
    }

    Single<List<CurrencyPair>> getCurrencyPairs();

    @Override
    default String name() {
        return SERVICE_NAME;
    }

    @Override
    default ServiceReference get(Vertx vertx, io.vertx.servicediscovery.ServiceDiscovery discovery, Record record, JsonObject configuration) {
        return new CurrencyReferenceDataServiceReference(vertx, discovery, record);
    }


    class CurrencyReferenceDataServiceReference extends AbstractServiceReference<CurrencyReferenceDataService> {
        /**
         * Creates a new instance of {@link AbstractServiceReference}.
         *
         * @param vertx     the vert.x instance
         * @param discovery
         * @param record    the service record
         */
        public CurrencyReferenceDataServiceReference(Vertx vertx, ServiceDiscovery discovery, Record record) {
            super(vertx, discovery, record);
        }

        @Override
        protected CurrencyReferenceDataService retrieve() {
            return new CurrencyReferenceDataServiceImpl(vertx.eventBus());
        }
    }
}
