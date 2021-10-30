package com.tusker.currencyconversionmicroservice.service;

import com.tusker.currencyconversionmicroservice.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//@FeignClient(name="currency-exchange", url="localhost:8000")
@FeignClient("currency-exchange")
public interface CurrencyExchangeProxy {

    @RequestMapping(method = RequestMethod.GET, value="currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(
        @PathVariable String from,
        @PathVariable String to
    );
}
