# Project Currency Converter
### Vypracované zadanie pre pozíciu Java Backend Developer
--------------------

### Autor:
Jozef Benc \
24.09.2024

--------------------
### Setup:
IntelliJ IDEA Community Edition 2024.2.2 \
JDK 17.0.11 \
start.spring.io

--------------------
### Znenie zadania:
Cieľom zadania je vypracovať aplikáciu ktorá bude poskytovať REST rozhranie pre konverziu bankových kurzov. \
Výsledkom má byť spustiteľná aplikácia postavená na framework Spring Boot a Jave 17 s prevolatelným REST rozhraním s dvomi endpointami, ktoré na komunikáciu využívajú formát JSON. \ 
Aplikácia musí byt spustiteľná a REST rozhrania prevolatelne (napr cez aplikaciu Postman/SoapUI). \
Aplikácia musí obsahovať endpoint \
·         pre načítanie zoznamu aktuálnych kurzov, uložené budú v db - dáta budu iniciované pri štarte aplikácie \
·         pre konverziu kurzov - na vstupe bude kurz z akého chcem previezť + suma  a  výstupný kurz na aký chcem prepočítať \

Databázu je možné riesiť in memory - H2. \
Takisto je potrebné pokryť vhodnú časť kódu unit testami za pomoci JUnit 5. \
Ak zvládnete, tak aj aplikáciu skontajnerizovať pomocou Docker.