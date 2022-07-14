# Pokedex-Api

The Pokedex api powers all Pokedex. A Pokédex is a tool that a Pokémon Trainer uses to get information on Pokémon.

# API Documentation

API documentation, examples and test cases can all be found in the Postman file in the repository. 

Install Postman details [here](https://www.postman.com/downloads/)

Import the Postman collection to load up the request documentation, testing, and examples.

## Testing
Testing is done currently through postman as an integration suite. Running the application locally, you can run the collection of requests currently saved and a suite of tests will as part of that to ensure data correctness and responses.

# Local Development
Guide to setting up local development, dependencies and tools. If you want to do docker development you may skip installation of SBT/JDK/SDK.

## Dependencies

### SBT / JDK / SDK
The Pokedex api is built using sbt. Installation details found [here](https://www.scala-sbt.org/download.html). If you're using homebrew you can execute:

```brew install sbt```

The Pokedex api also requires a [JDK](https://www.oracle.com/java/technologies/downloads/) and scala [SDK](https://www.scala-lang.org/download/all.html) to be installed. The versions of these are dependant on development tools but also the SDK is hard choosen in the Dependencies file. Below are the recommended versions to install.
 
```
JDK version: 18+
SDK version: 2.12.7
```

### Docker

Docker development is also supported by this project. 

Installation instructions can be found [here](https://docs.docker.com/get-docker/)

Docker Compose in this project is used to launch a docker image with a yaml configuration file for local configuration and use the production Dockerfile.

Install docker compose details [here](https://docs.docker.com/compose/install/) or use homebrew with:

```brew install docker-compose```

## Getting started

### SBT
The project can simply be started on localhost:3010 by executing from the project directory:

```sbt core-api-server/run```

### Docker

To build the image and run it:

```docker-compose -f docker-compose.full.yml up```


