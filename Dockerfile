# 1. 베이스 이미지 선택 (Java 17 버전을 사용합니다)
FROM openjdk:17-jdk-slim

# 2. JAR 파일이 저장될 경로를 변수로 지정
ARG JAR_FILE_PATH=build/libs/*.jar

# 3. 위에서 지정한 경로의 JAR 파일을 Docker 이미지 안으로 복사
#    이때 app.jar 라는 이름으로 복사합니다.
COPY ${JAR_FILE_PATH} app.jar

# 4. 컨테이너가 시작될 때 실행할 명령어를 지정
#    "java -jar /app.jar" 명령어로 우리 프로젝트를 실행합니다.
ENTRYPOINT ["java","-jar","/app.jar"]