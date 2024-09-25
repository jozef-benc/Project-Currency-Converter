package com.example.currency_converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) throws Exception {
        // Pridanie kurzov
        currencyRepository.save(new Currency("EUR", 1.0));    // Euro - referenčná mena
        currencyRepository.save(new Currency("USD", 1.11));   // Americký dolár
        currencyRepository.save(new Currency("GBP", 0.87));   // Britská libra
        currencyRepository.save(new Currency("JPY", 158.0));  // Japonský jen
        currencyRepository.save(new Currency("AUD", 1.65));   // Austrálsky dolár
        currencyRepository.save(new Currency("CAD", 1.44));   // Kanadský dolár
        currencyRepository.save(new Currency("CHF", 0.97));   // Švajčiarsky frank
        currencyRepository.save(new Currency("CNY", 7.77));   // Čínsky jüan
        currencyRepository.save(new Currency("INR", 88.56));  // Indická rupia
        currencyRepository.save(new Currency("BRL", 5.58));   // Brazílsky real
        currencyRepository.save(new Currency("ZAR", 20.45));  // Juhoafrický rand
        currencyRepository.save(new Currency("CZK", 25.13));  // Česká koruna
        currencyRepository.save(new Currency("PLN", 4.56));   // Poľský zlotý
        currencyRepository.save(new Currency("HUF", 387.45)); // Maďarský forint
        currencyRepository.save(new Currency("NOK", 11.45));  // Nórska koruna
        currencyRepository.save(new Currency("SEK", 11.30));  // Švédska koruna
        currencyRepository.save(new Currency("DKK", 7.45));   // Dánska koruna
    }
}
