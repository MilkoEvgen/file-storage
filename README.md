### Задание:
Необходимо реализовать REST API, которое взаимодействует с файловым 
хранилищем и предоставляет возможность получать доступ к файлам и истории 
загрузок.<br/>
### Сущности:
- **User** -> Integer id, String name, List<Event> events
- **Event** -> Integer id, User user, File file
- **File** -> Integer id, String name, String filePath
### Требования:
1. Все CRUD операции для каждой из сущностей
2. Придерживаться подхода MVC
3. Для сборки проекта использовать Maven
4. Для взаимодействия с БД - Hibernate
5. Для конфигурирования Hibernate - аннотации
6. Инициализация БД должна быт реализована с помощью flyway
7. Взаимодействие с пользователем должно быть реализовано с помощью Postman


**Технологии**: Java, MySQL, Hibernate, HTTP, Servlets, Flyway, Maven, Swagger
___
### Инструкция по запуску проекта:
1. Установить Java и Maven
2. Склонировать проект себе на компьютер `git clone https://github.com/MilkoEvgen/file-storage.git`
3. Перейти в папку проекта и выполнить команду `mvn clean install`
4. Перейти в папку target и выполнить команду `java -jar FileStorage-1.0-SNAPSHOT.jar`
