package org.springframework.gnomes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gnomes.services.AggregationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

import java.util.Map;

/**
 * @author Vinicius Carvalho
 */
@Controller
@RequestMapping("/aggregate")
public class AggregatorController {

    @Autowired
    private AggregationService service;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DeferredResult<Map<String,Double>> data(){
        Observable<Map<String,Double>> data = service.aggregate();

        DeferredResult<Map<String,Double>> result = new DeferredResult<>();



        data.subscribe(map -> {
            result.setResult(map);
        });

        return result;

    }
}
