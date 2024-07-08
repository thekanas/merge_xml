Для запуска необходимо отредактировать конфигурацию:

1. Ставим галочку Modify options - Ovveride configuration properties

![image](https://github.com/South12309/merge_xml/assets/26357330/ac0b8ea1-32bb-40e3-a8cc-6987442ff928)


2. Открывается таблица в которой мы заполняем параметры подключения к нашей БД

![image](https://github.com/South12309/merge_xml/assets/26357330/57edeba8-ede1-43eb-af94-5752bf7c7004)

3. Запускаем приложение

Для проверки работы приложения необходимо выполнить шаги:

1. Скопировать абсолютный путь до директории с тестовыми фикстурами, например, /home/user/project/merge_xml/src/test/java/com/vpolosov/trainee/merge_xml/test_fixtures/Ok
2. В Postman отправить POST запрос на адрес localhost:8080/xml, в Body формат raw Text указать скопированный путь до тестовых фикстур
3. При успешном ответе получим: Total.xml was created!
