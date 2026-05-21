package br.edu.atitus.currencyservice.clients;

import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BCBClientFallback implements BCBClient {

    @Override
    public BCBResponse getRate(String moeda, String dataCotacao, String format) {
        return new BCBResponse(Collections.emptyList());
    }
}
