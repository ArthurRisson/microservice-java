package br.edu.atitus.currencyservice.clients;

import java.util.List;
import java.util.Map;

public record BCBResponse(
        List<Map<String, Object>> value) {
}
