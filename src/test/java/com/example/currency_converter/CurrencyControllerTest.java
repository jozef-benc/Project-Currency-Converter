package com.example.currency_converter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Testy pre načítanie všetkých mien (GET endpoint)
    @Test
    public void testGetAllCurrencies() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(17))  // Overujeme počet mien
                .andExpect(jsonPath("$[0].currencyCode").value("EUR"))
                .andExpect(jsonPath("$[1].currencyCode").value("USD"));
    }

    // Testy pre konverziu (POST endpoint)
    @Test
    public void testConvertUSDToCZK() throws Exception {
        mockMvc.perform(post("/api/currencies/convert")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "CZK")
                        .param("amount", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2263.96));  // Očakávaný zaokrúhlený výsledok
    }

    @Test
    public void testConvertEURToJPY() throws Exception {
        mockMvc.perform(post("/api/currencies/convert")
                        .param("fromCurrency", "EUR")
                        .param("toCurrency", "JPY")
                        .param("amount", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7900.0));  // Očakávaný výsledok
    }

    // Testy pre chybové stavy
    @Test
    public void testConvertInvalidCurrency() throws Exception {
        mockMvc.perform(post("/api/currencies/convert")
                        .param("fromCurrency", "XXX")  // Neexistujúca mena
                        .param("toCurrency", "CZK")
                        .param("amount", "100"))
                .andExpect(status().isBadRequest())  // Očakávame kód 400
                .andExpect(content().string("Invalid from currency"));  // Očakávame text odpovede
    }

    @Test
    public void testConvertZeroAmount() throws Exception {
        mockMvc.perform(post("/api/currencies/convert")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "CZK")
                        .param("amount", "0"))
                .andExpect(jsonPath("$").value(0.00));
    }

    // Testy pre konverziu malých/veľkých čísiel
    @Test
    public void testConvertLargeAmount() throws Exception {
        mockMvc.perform(post("/api/currencies/convert")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "JPY")
                        .param("amount", "1000000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(142342342342.34));  // Očakávaný zaokrúhlený výsledok
    }

    // Testy priamo na CurrencyRepository
    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testFindAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        assertEquals(17, currencies.size());  // Počet mien
    }

    @Test
    public void testFindCurrencyByCode() {
        Currency currency = currencyRepository.findById("USD").orElse(null);
        assertNotNull(currency);
        assertEquals("USD", currency.getCurrencyCode());
        assertEquals(1.11, currency.getRate());
    }

    // Test pre nulový kurz
    @Test
    public void testConvertCurrencyWithZeroRate() throws Exception {
        // Najskôr uložíme menu s nulovým kurzom do databázy pre testovanie
        currencyRepository.save(new Currency("TST", 0.0));  // Príklad pre testovaciu menu

        mockMvc.perform(post("/api/currencies/convert")
                        .param("fromCurrency", "TST")
                        .param("toCurrency", "CZK")
                        .param("amount", "100"))
                .andExpect(status().isBadRequest())  // Očakávame kód 400
                .andExpect(content().string("Invalid rate for currency: TST"));  // Očakávame správu o chybe

        // Odstránime menu "TST" po teste
        currencyRepository.deleteById("TST");
    }
}
