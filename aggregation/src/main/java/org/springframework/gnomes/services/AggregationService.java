package org.springframework.gnomes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gnomes.client.GnomesClient;
import org.springframework.gnomes.client.ProfitClient;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
@Component
public class AggregationService {

    @Autowired
    private GnomesClient gnomesClient;
    @Autowired
    private ProfitClient profitClient;

    public Observable<Map<String,Double>> aggregate(){
        return Observable.zip(gnomesClient.gnomesData(), profitClient.profitData(), (d1,d2) -> {
            Map<String,Double> zip = new HashMap<String, Double>();
            zip.putAll(d1);
            zip.putAll(d2);
            return zip;
        });
    }
}
