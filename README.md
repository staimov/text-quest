[![My Skills](https://skillicons.dev/icons?i=java,spring,html,bootstrap,docker&theme=light)](https://skillicons.dev)
# text-quest
### Text quest game

JavaRush Module 3 final mini-project:

https://javarush.com/quests/lectures/jru.module3.lecture04

## Feel free to test the project deployed on render.com

https://text-quest-l7tx.onrender.com

(It may take some time at the first start)

## Screenshot

![screenshot](./src/main/resources/static/images/screenshot1.png?raw=true)

## To build the project
```
mvn clean install
```

## To run the project
```
java -jar target/text-quest-1.0.0.jar
```

or

```
mvn spring-boot:run
```

## To deploy and run the project on Heroku

```
heroku create
```

```
git push heroku main
```

```
heroku open
```

## To deploy and run the project in Docker

Use Dockerfile to build an image and run it:

```
docker build --tag=text-quest:latest .
```

```
docker run -it -p8888:8080 text-quest:latest
```

Оr use Maven to build an image and run it:

```
mvn spring-boot:build-image
```

```
docker run -it -p8888:8080 text-quest:1.0.0
```

## Java Classes Diagram

![screenshot](./src/main/resources/static/images/classes.png?raw=true)

## Spring Beans Dependencies Diagram

![screenshot](./src/main/resources/static/images/beans.png?raw=true)


## Attributions

Vectors and icons by <a href="https://goodstuffnononsense.com/hand-drawn-icons/space-icons/?ref=svgrepo.com" target="_blank">Good Stuff No Nonsense</a> in CC Attribution License via <a href="https://www.svgrepo.com/" target="_blank">SVG Repo</a>
