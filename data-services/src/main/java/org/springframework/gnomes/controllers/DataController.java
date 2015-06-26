package org.springframework.gnomes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Vinicius Carvalho
 */
@RequestMapping("/data")
@Controller
public class DataController {

    private Random random = new Random();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Double> getData(@RequestParam(value="base",defaultValue = "10") Integer base){

        return Collections.singletonMap("data",(random.nextDouble()*base)+base);
    }

}
