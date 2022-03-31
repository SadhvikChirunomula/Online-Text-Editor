## Online Text Editor

This is a simple springboot application which will run a text-editor in your desired browser on localhost:8080.

### Features

* Users can login/register
* You can save your files
* Format css to your files

----

### Language Used

* HTML, CSS, JS For Frontend
* Java Spring Boot for Backend

### How to run 

First clone the docker image
```
docker pull online-text-editor
```

Now, run it using below command
```
docker run -p 8080:8080 online-text-editor
```

### Docker commands Used

```
docker build -t online-text-editor .

docker run -p 8080:8080 online-text-editor

docker rm -vf $(docker ps -a)

docker rmi -f $(docker images -aq)

```
----


