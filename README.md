# leaderboardRecord-server


## 개발환경
Java 17  
Sprint Boot 3.3.2  
MariaDB 11.2.2


## 실행 전 설정

```./src/main/resources/application-sample.yaml```를 복사해 동일한 경로에 
```application.yaml``` 생성


## DB Migration

```./src/main.resources/db``` 폴더에 DB 스키마 관련 sql 파일 이용  
사용중인 db(maria 11.2) 미지원 등의 이유로 migration 툴은 이용하지 않음