package org.flabs.refdata.currency.service;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.flabs.refdata.currency.model.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

public class CurrencyReferenceDataServiceImpl implements CurrencyReferenceDataService {

    private EventBus eventBus;


    public CurrencyReferenceDataServiceImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public CurrencyReferenceDataServiceImpl() {
    }

    @Override
    public Single<List<CurrencyPair>> getCurrencyPairs() {

        return Single.create((SingleOnSubscribe<JsonArray>) emitter -> {
            eventBus.<JsonArray>send(PROVIDER_ADDRESS, new JsonObject(), arr -> {
                if (arr.succeeded()) {
                    final Message<JsonArray> result = arr.result();
                    emitter.onSuccess(result.body());
                } else {
                    emitter.onError(arr.cause());
                }
            });
        })
                .map(arr -> new ArrayList<>());

    }


}
