package com.example.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

//@FeignClient(name = "currency-exchange",url = "localhost:8000") //without load balancing

@FeignClient(name = "currency-exchange")//with load balancing
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retreiveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);

}
