# Tracking friends location
Spring back-end for Tracking friends location Android app

## Getting Started

### Prerequisites
* docker
* mongodb

*To build from source:*
* java 8
* gradle

### Build from source

#### Using gradle:
```
gradle clean
gradle build
```

#### Using gradle script:
Linux or Mac:
```
chmod +x gradlew
./gradlew clean build
```
Windows:
```
gradlew.bat clean build
```

### Run with Docker

#### Build Docker container:
```
docker build . -t trackingfriendsservice
```

#### Run container:
```
docker run -p 8080:8080 --name trackingfriendsservice --link mongodb
```

## Deployment
```
docker-compose up
docker-compose down
```
Remove everyting:
```
docker-compose down --rmi local -v --remove-orphans
```

**If you are only interested in running the service, check out [this repository](https://github.com/sskender/trackingfriends-server).**

## License

This project is licensed under the GNU License - see the [LICENSE](LICENSE) file for details.

