

## Как запустить.


1) предоставить права gradlew:
``chmod +x gradlew``
2) Запустить базу данных PostgreSQL:
   ```docker-compose up -d```
3) Запустить приложение:
```./gradlew clean BootRun```

Приложение запускается на ```localhost:8080```
В дальнейшем добавиться возможность запуска приложение сразу через docker-compose.