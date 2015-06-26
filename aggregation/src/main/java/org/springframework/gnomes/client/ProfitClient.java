package org.springframework.gnomes.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.Collections;
import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
@Component
public class ProfitClient {
    @Autowired
    private RestTemplate template;

    private Double lastValue = 0.0;

    @HystrixCommand(fallbackMethod = "defaultValue",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public Observable<Map<String,Double>> profitData(){
        return new ObservableResult<Map<String, Double>>() {
            @Override
            public Map<String, Double> invoke() {
                Map<String,Double> response = template.getForObject("http://gnomes-data/data?base={base}",Map.class, 100);
                Double data = response.get("data");
                lastValue = data;
                return Collections.singletonMap("profit",data);
            }
        };
    }

    public Map<String,Double> defaultValue(){
        return Collections.singletonMap("profit",lastValue);
    }
}
