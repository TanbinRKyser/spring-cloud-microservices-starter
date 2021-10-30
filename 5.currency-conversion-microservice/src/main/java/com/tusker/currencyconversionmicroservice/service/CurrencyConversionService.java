package com.tusker.currencyconversionmicroservice.service;

import com.tusker.currencyconversionmicroservice.model.CurrencyConversion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class CurrencyConversionService {

    private final CurrencyExchangeProxy currencyExchangeProxy;

    public CurrencyConversionService( CurrencyExchangeProxy currencyExchangeProxy ) {
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    public CurrencyConversion convertCurrency( String from, String to, BigDecimal amount ) {

        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put( "from", from);
        uriVariables.put( "to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariables );

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion( currencyConversion.getId(),
                from, to, amount,
                currencyConversion.getConversionMultiple(),
                amount.multiply( currencyConversion.getConversionMultiple() ),
                currencyConversion.getEnvironment()
        );
    }

    public CurrencyConversion convertCurrencyUsingFeign( String from, String to, BigDecimal amount ) {

        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue( from, to);

        return new CurrencyConversion( currencyConversion.getId(),
                from, to, amount,
                currencyConversion.getConversionMultiple(),
                amount.multiply( currencyConversion.getConversionMultiple() ),
                currencyConversion.getEnvironment() + " " + "feign"
        );
    }
}
