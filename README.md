 

[PL] Aplikacja jest systemem do zarządzania kursami, w którym mogą rejestrować się użytkownicy tacy jak autor lub student. Autor może tworzyć nowe kursy, modyfikować istniejące, a także przeglądać kursy innych autorów i je kupować. Student może jedynie przeglądać dostępne kursy lub je nabyć. W aplikacji dostępny jest również administrator, który ma uprawnienia takie jak autor, lecz dodatkowo ma wgląd w informacje o użytkownikach (utworzone kursy, kupione kursy, złożone zamówienia). Po udanej transakcji użytkownik otrzymuje wiadomość e-mail z informacją o zakupionym kursie, która jest wysyłana z głównego serwisu za pośrednictwem RabbitMQ do serwisu wysyłającego wiadomości e-mail. Płatności realizowane są poprzez API udostępnione przez Stripe.
Aplikacja będzie aktualizowana oferując nowe funkcjonalności dla użytkowników jak również wydajniejszy i czytelniejszy kod.


[EN] The application is a course management system where users such as authors or students can register. Authors can create new courses, modify existing ones, and also view courses created by other authors and purchase them. Students can only browse available courses or purchase them. The application also includes an administrator who has author privileges but additionally has access to user information (created courses, purchased courses, placed orders). After a successful transaction, the user receives an email with information about the purchased course, which is sent from the main service via RabbitMQ to the email service. Payments are processed through the API provided by Stripe. The application will be updated to offer new features for users as well as more efficient and readable code.

Technologies I used : 
* Spring Boot v3.2.3 
* Java 17 
* RabbitMQ 
* Stripe 
* MYSQL 

