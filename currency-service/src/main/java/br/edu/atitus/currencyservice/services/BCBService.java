package br.edu.atitus.currencyservice.services;

import br.edu.atitus.currencyservice.clients.BCBClient;
import br.edu.atitus.currencyservice.clients.BCBResponse;
import br.edu.atitus.currencyservice.dtos.CurrencyDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BCBService {

    private static final String FIXED_BUSINESS_DATE = "2026-05-20";
    private final BCBClient bcbClient;

    public BCBService(BCBClient bcbClient) {
        this.bcbClient = bcbClient;
    }

    @Cacheable(value = "bcbRates", key = "#source + '-' + #target")
    public CurrencyDTO getConversionRate(String source, String target) {
        String sourceCode = "'" + source.toUpperCase() + "'";
        String dateCode = "'" + FIXED_BUSINESS_DATE + "'";

        BCBResponse response = bcbClient.getRate(sourceCode, dateCode, "json");
        if (response == null || response.value() == null || response.value().isEmpty()) {
            return null;
        }

        Map<String, Object> quote = response.value().get(0);
        if (quote == null) {
            return null;
        }

        Object rateObject = quote.getOrDefault("cotacaoCompra", quote.get("cotacaoVenda"));
        if (rateObject == null) {
            return null;
        }

        Double rate;
        try {
            rate = Double.parseDouble(rateObject.toString());
        } catch (NumberFormatException e) {
            return null;
        }

        String environment = "BCB API - " + quote.getOrDefault("tipoBoletim", "unknown");
        return new CurrencyDTO(source.toUpperCase(), target.toUpperCase(), rate, environment);
    }
}
