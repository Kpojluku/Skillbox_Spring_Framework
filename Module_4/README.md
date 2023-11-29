<H2>RestAPI с базой данных</H2>

<H3>Цель практической работы</H3>
Закрепить знания, полученные по следующим темам:

* Разработка REST API с использованием Spring MVC.
* Валидация входящих запросов от клиентов.
* AOP.
* Создание слоя приложения для работы с базой данных с использованием Spring Boot Data JPA.
* Написание спецификаций для фильтрации новостей при запросе.
* Маппинг сущностей с использованием MapStruct.


<H3>Что нужно сделать</H3>
Реализуйте REST API для новостного сервиса. API должен предоставлять возможность:

* создавать пользователей и управлять ими,
* создавать категории новостей и управлять ими,
* создавать новости и управлять ими,
* создавать комментарии для новостей и управлять ими.

<H4>Описание логики работы сервиса</H4>
* Редактирование и удаление новости разрешается только тому пользователю, который её создал. Реализуйте эту логику через AOP.  
* Редактирование и удаление комментария к новости разрешается только тому пользователю, который его создал. Реализуйте эту логику через AOP.
* Возврат списка сущностей (findAll) должен происходить только с помощью пагинации. Исключение — список комментариев. В API список комментариев должен возвращаться только для конкретной новости.
* При возврате списка новостей ответ не должен содержать списка комментариев к каждой новости. Вместо этого убедитесь, что каждый объект из списка содержит значение, отображающее количество комментариев.
* Если возвращается одна новость (findById), она должна содержать все комментарии к ней.
* Используйте MapStruct для преобразования тела запроса в сущность, с помощью которой можно работать с базой данных (например, сохранить преобразованную сущность). Все тела ответа необходимо преобразовывать в DTO.
* Реализуйте фильтрацию новостей по категориям и авторам.
* Настройте корректную обработку ошибок, которые могут произойти в приложении. В случае возникновения ошибки необходимо отдавать клиенту читаемый результат.
* Реализуйте валидацию входящих данных от клиента.
____

<H3>Как запустить:</H3>


1. Запустите докер контейнер в папке docker: <b>docker-compose up</b> (должен быть запущен docker daemon и не занят порт 5432)
2. Выполните команду: <b>./gradlew build</b>
3. Запустите приложение: <b>java -jar .\build\libs\Module_3.jar</b>

---

<H3>Endpoints</H3>
Можно посмотреть по адресу:  
http://localhost:8080/swagger-ui/index.html