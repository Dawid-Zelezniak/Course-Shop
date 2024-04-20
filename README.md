 

[PL] Aplikacja jest systemem do zarządzania kursami, w którym mogą rejestrować się użytkownicy tacy jak autor lub student. Autor może tworzyć nowe kursy, modyfikować istniejące, a także przeglądać kursy innych autorów i je kupować. Student może jedynie przeglądać dostępne kursy lub je nabyć. W aplikacji dostępny jest również administrator, który ma uprawnienia takie jak autor, lecz dodatkowo ma wgląd w informacje o użytkownikach (utworzone kursy, kupione kursy, złożone zamówienia). Po udanej transakcji użytkownik otrzymuje wiadomość e-mail z informacją o zakupionym kursie, która jest wysyłana w sposób asynchoroniczny z głównego serwisu za pośrednictwem RabbitMQ do serwisu wysyłającego wiadomości e-mail co w przypadku dużego obciążenia nie będzie powodowało widocznego spowolnienia działania aplikacji gdyż cała logika odpowiedzialna za wysyłanie wiadomości znajduje sie w osobnym serwisie. Płatności realizowane są poprzez API udostępnione przez Stripe, dzięki czemu wzrasta bezpieczeństwo użytkowników,ponieważ nie przechowuje ani nie przetwarzam ich danych w obrębie mojej aplikacji.Proces płatności polegający na wywoływaniu API Stripe został napisany z użyciem JavaScript lecz sam nie napisałem kodu JS tylko znalazłem poodbne rozwiązanie w internecie i przerobiłem je tak aby spełniało moje wymaganinia.
Aplikacja będzie aktualizowana oferując nowe funkcjonalności dla użytkowników jak również wydajniejszy i czytelniejszy kod.W kolejnych aktualizacjach zostaną dodane testy jednostkowe oraz integracyjne , a także możliwość wysłania wiadomości email do wszystkich użytkowników aplikacji z poziomu administratora oraz możliwość zakupu kilku kursów na raz zamiast jednego.


[EN] The application is a course management system where users such as authors or students can register. Authors can create new courses, modify existing ones, and browse courses from other authors, as well as purchase them. Students can only browse available courses or purchase them. The application also includes an administrator, who has permissions similar to authors but additionally has access to user information (created courses, purchased courses, placed orders). After a successful transaction, the user receives an email notification about the purchased course, which is sent asynchronously from the main service via RabbitMQ to the email service. This ensures smooth operation even under heavy load, as the entire logic responsible for sending messages is located in a separate service. Payments are processed through the API provided by Stripe, enhancing user security, as it does not store or process their data within my application. The payment process, involving the invocation of the Stripe API, was implemented using JavaScript, although I did not write the JS code myself but rather adapted a similar solution found online to meet my requirements.
The application will be updated to offer new functionalities for users as well as more efficient and readable code. In subsequent updates, unit and integration tests will be added, along with the ability to send email messages to all application users from the administrator's interface, and the option to purchase multiple courses at once instead of just one.

Technologies I used : 
* Spring Boot v3.2.3 
* Java 17 
* RabbitMQ 
* Stripe 
* MYSQL 
  
VIDEO PRESENTATION ~ 10 min [PL] : https://youtu.be/923i1-52OQs
