package org.springframework.gnomes;

import org.junit.Test;
import rx.Observable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Vinicius Carvalho
 */
public class AggregationTest {

    Random random = new Random();



    @Test
    public void aggregateTest() throws Exception {
        Object result = Observable.zip(getData("gnomes"),getData("profit"), (d1,d2) -> {
            Map<String,Double> zip = new HashMap<String, Double>();
            zip.putAll(d1);
            zip.putAll(d2);
            return zip;
        }).toBlocking().first();
        System.out.println(result);
    }


    private Observable<Map<String,Double>> getData(String key){
        return Observable.just(Collections.singletonMap(key,random.nextDouble()));
    }

}
