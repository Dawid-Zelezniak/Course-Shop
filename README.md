 

[PL] Aplikacja symuluje system do zarządzania kursami.Umożlwia utworzenie użytkownika typu Student lub CourseAuthor.Student posiada tylko jedną rolę - ROLE_STUDENT i może przeglądać oraz kupować kursy.Autor kursu ma natomiast dwie role – ROLE_STUDENT oraz ROLE_TEACHER. Może on przeglądać i kupować kursy utworzone przez innych autorów a także dodawać nowe kursy i je modyfikować.Po załadowaniu kontekstu aplikacji  tworzony jest CourseAuthor z dodatkową rolą ROLE_ADMIN.Admin ma dostęp do tego co CourseAuthor a dodatkowo może wyświetlić informacje o wszystkich studentach i autorach przechowywanych w bazie danych. 

 

[ENG] The application simulates a course management system.It allows the creation of users: Student and CourseAuthor.A Student has only one role – ROLE_STUDENT and can browse and purchase courses.On the other hand, a CourseAuthor has two roles – ROLE_STUDENT and ROLE_TEACHER.They can browse and purchase courses created by other authors,as well as add new courses and modify them.Upon loading the application context , a CourseAuthor is created with and additional role of ROLE_ADMIN.An admin has access to everything a CourseAuthor does,and additionally can display information about all students and authors stored in the database. 

Technologies I used : 
* Spring Boot v3.2.3 
* Java 17 
* RabbitMQ 
* Stripe 
* MYSQL 
* Thymeleaf 
VIDEO PRESENTATION ~ 10 min [PL] : https://youtu.be/923i1-52OQs
