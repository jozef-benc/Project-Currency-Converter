package com.example.currency_converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    // Endpoint na získanie všetkých mien a ich kurzov
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    // Endpoint na konverziu z jednej meny na inú
    @PostMapping("/convert")
    public double convertCurrency(@RequestParam String fromCurrency,
                                  @RequestParam String toCurrency,
                                  @RequestParam double amount) {
        Currency from = currencyRepository.findById(fromCurrency)
                .orElseThrow(() -> new IllegalArgumentException("Invalid from currency"));
        Currency to = currencyRepository.findById(toCurrency)
                .orElseThrow(() -> new IllegalArgumentException("Invalid to currency"));

        // Kontrola, či sú hodnoty 'rate' nenulové
        if (from.getRate() == 0.0) {
            throw new IllegalArgumentException("Invalid rate for currency: " + fromCurrency);
        }
        if (to.getRate() == 0.0) {
            throw new IllegalArgumentException("Invalid rate for currency: " + toCurrency);
        }

        // Výpočet výsledku
        double result = (amount / from.getRate()) * to.getRate();

        // Zaokrúhlenie výsledku na 2 desatinné miesta
        return Math.round(result * 100.0) / 100.0;
    }

    // Spracovanie výnimky IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}