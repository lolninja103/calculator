Web-service of funds transfer from card to card.
Must have the following functionality:
1) Login form.
2) Registration of new clients.
Client's attributes (all fields are mandatory):
● FULL NAME
Login
Password

3) Client's personal cabinet
Client's personal cabinet should have a possibility to:
 Add cards and view their balances
 Top up the balance of a card
 Transfer of funds to cards of other clients
 The user must enter the number of the card of the person to whom the money is being transferred.
 After that the system should request confirmation of the transfer and display the full name of the person to whom the money is transferred
 After money transfer the funds should be debited from the incoming card to the outgoing card
 Transfer amount cannot exceed the current balance
 * Viewing of transaction history with a filtering option:
 By amount from/to
 By recipient account
 By date from/to
 ** Displaying of statistics for the selected period (if implemented using DBMS):
 Amount of transfers by beneficiary account for the period
 Receipts to expenses ratio
 Transaction with maximum amount for the period
 Average expenses per day for the period

Transaction of the maximum sum for the period Average expenses of the day for the period
Wishes to realization
1) Application - Spring Boot
2) Data access layer. As a storage - any DBMS (for example, h2, mysql, postgresql, etc.) or text files. For access use jdbc/jooq/hibernate/java.io.* (in case of storing in text files)
3) Web forms using any template engine, like thymeleaf
4) Spring MVC controllers with necessary rests must be implemented to pass data to the web form from the backend
Examples of configuration and structure:
https://www.baeldung.com/spring-boot-start , https://dzone.com/articles/creating-a-web-application-with-spring-boot
Preferred implementation order:
1) Data layer (storage)
2) Services and rests
3) Web-interface. Has the lowest priority, i.e. the main task is to implement REST controllers, which implement all the functions of the application.
4) Tasks of higher complexity

Translated with www.DeepL.com/Translator (free version)
