<H2>Интеграция с внешними системами (Kafka)</H2>

<H3>Цель практической работы</H3>
Закрепить знания, полученные по следующим темам:

* Настройка Kafka в приложениях.
* Отправка сообщений в Kafka.
* Принятие и обработка сообщений от Kafka.

<H3>Что нужно сделать</H3>
Создайте два небольших приложения.

* Первое приложение — order-service. Оно будет иметь эндпоинт, на который приходит POST-запрос с сущностью Order. Сущность Order состоит из двух полей: String product и Integer quantity. Эндпоинт принимает сущность и отправляет в Kafka событие OrderEvent (которое также состоит из полей product и quantity). Событие отправляется в топик order-topic.

Ещё это приложение должно иметь KafkaListener, который будет слушать события по топику order-status-topic. Этот слушатель должен вывести в консоль информацию о событии.

* Второе приложение — order-status-service — не имеет эндпоинтов. Оно состоит из KafkaListener, который слушает топик order-topic. 
Когда в слушатель приходит событие, происходит отправка другого события в топик order-status-service. Это событие состоит из полей String status и Instat date. В поле status записывается произвольная строка (например, CREATED или PROCESS), в поле date — текущая дата.

---

<H3>Как запустить:</H3>

1. Запустите докер контейнеры в папке docker: <b>docker-compose up</b> (должен быть запущен docker daemon)
2. В папке Module_6_order-service выполните команду: <b>./gradlew build</b>
3. Запустите первое приложение: <b>java -jar .\build\libs\Module_6_order-service.jar</b>
4. В папке Module_6_order-status-service выполните команду: <b>./gradlew build</b>
5. Запустите второе приложение: <b>java -jar .\build\libs\Module_6_order-status-service.jar</b>
6. Отправьте POST запрос на эндпоинт: http://localhost:8080/kafka/send  
С телом, например:

{
"product": "hat",
"quantity": 2
}
