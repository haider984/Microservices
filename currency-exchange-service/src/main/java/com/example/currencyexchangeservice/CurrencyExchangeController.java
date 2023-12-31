package com.example.currencyexchangeservice;

import brave.Span;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private Logger logger= LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private CurrencyExhangeRepository repository;

    @Autowired
    private Environment environment;

    @Autowired
    private Tracer tracer;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retreiveExchangeValue(@PathVariable String from, @PathVariable String to){

//        CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));

        Span span = tracer.currentSpan();

        logger.info("retreiveExchangeValue called with {} to {}. Trace ID: {}", from, to, span.context().traceIdString());

        //logger.info("retreiveExchangeValue  called with {} to {}", from,to);

        CurrencyExchange currencyExchange=repository.findByFromAndTo(from,to);
        if (currencyExchange==null)
            throw new RuntimeException("Unable to find data for "+from+"to"+to);

        String port=environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;

    }
}
