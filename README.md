# gnomes-demo
<p align="center">
<img src="https://cloud.githubusercontent.com/assets/803893/8378310/5e7474de-1be6-11e5-816c-0b7abf8d384f.png"/>
</p>

This a simple microservice(ish) application consisted of three microservices 

* gnomes-data: REST data endpoint that generates random data points (Eureka)
* aggregation: Aggregate data from data endpoint, does in a reactive manner using rx-java (Eureka, Hystrix)
* api: An [API-Gateway](http://microservices.io/patterns/apigateway.html) that serves the datapoint using charts (Eureka,Zuul)

## Building
`gradle build`

## Creating services
You will need a circuit breaker and a service registry to run this apps:

`cf cs p-circuit-breaker-dashboard standard hystrix`
`cf cs p-service-registry standard registry`

## Pushing the apps

You can push all apps at once using the parent manifest file. There's a manifest on each microservice as well to push individually

`cf push -f manifest.mf`

# How to demo this

### data-services project

You can demo how simple it is to bind to a service registry:

```java
@SpringBootApplication
@EnableDiscoveryClient
@ServiceScan
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DataApplication.class);
        app.run(args);
    }


}

```

If you curl the enpoint of this application: 

`curl http://$cfdomain/gnomes/data`

```json
{
data: 19.878341758728503
}
```

Outputs random data to simulate a data service

### aggregation project

This project aggregates the results from the data project, it simulates two calls protected by hystrix.

If you curl the endpoint:

`curl http://$cfdomain/aggregation/aggregate`

```json
{
profit: 116.68136286795357,
gnomes: 15.65027543673633
}
```

The clients:

```java
@Component
public class GnomesClient {

    @Autowired
    private RestTemplate template;

    private Double lastValue = 0.0;

   @HystrixCommand(fallbackMethod = "defaultValue",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public Observable<Map<String,Double>> gnomesData(){

       return new ObservableResult<Map<String, Double>>() {
           @Override
           public Map<String, Double> invoke() {
               Map<String,Double> response = template.getForObject("http://gnomes-data/data",Map.class, Collections.singletonMap("base",10));
               Double data = response.get("data");
               lastValue = data;
               return Collections.singletonMap("gnomes",data);
           }
       };


    }

    public Map<String,Double> defaultValue(){
        return Collections.singletonMap("gnomes",lastValue);
    }

}

```

