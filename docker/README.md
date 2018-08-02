
# Readme

## Requirements
	OS requirements : Ubuntu 16.04 and higher [Docker CE is supported on Ubuntu on x86_64, armhf, s390x (IBM Z), and ppc64le (IBM Power) architectures.]

## Content of this directory

### Dockerfile

As before, this `Dockerfile` contains 2 stages, one is building stage, and another one is runtime stage.

However, this version will not use any local Java source code, it will fetch the source code from the remote repository directly.

There is a section of all the default values of environment variables for EA and Broker:

```Dockerfile
ENV BROKER_HOSTNAME   TESTNAME
ENV BROKER_PORT       99999
ENV EA_HOSTNAME       localhost
ENV EA_PORT           1223
ENV DATA_LOCATION     /data/
```

Those environment variables can be override by runtime, which is exactly what the `run.sh` and `docker-compose.yml` did.

### docker-compose.yml

As EA and Broker will always be paired together, the two services has been merged into one.

The `BROKER_HOSTNAME` and `BROKER_PORT` will be passed into the container, so the EA and Broker can load the setting on its start. The `alias` is also use `BROKER_HOSTNAME` environment variable this time.

### supervisord.conf

We use `supervisor` to run multiple services in a single container, this file is a configuration file for `supervisor`.

### run.sh

This is a helper script for provisioning the server environment and runing the service. In the next section, we will discuss more details of the commands it supports.

## Commands of `run.sh`

### host_setup
`host_setup` is required when the docker envirnment and docker-compose environment 

```bash
$ ./run.sh host_setup
```

The `host_setup` command is for preparing the server environment. It includes 3 parts, installing all the necessary softwares for running Docker and the services, installing Docker and Docker Compose, allow current user have permission to run `docker` command, and then fetch all the files required for building the Docker image for the services from the remote repository.

### build
`NOTE:` We need to run this command for every time to run the service on different ports or hostname.

Using `build` command we initially download all the required files for lauching the docker container, network and serives (ie Dockerfile, docker-coompose.yml and supervisord.conf)

This command will build the Docker image for the service. It requires the current directory contains the `Dockerfile`, `docker-compose.yml` and `supervisord.conf`, so make sure they exist before run this command, and run `provision` or `provision_docker` command if they do not exist. This command requires 2 agurguments, 'port' and 'Broker_Hostname'


The `port` and `Broker_Hostname` are for making changes to `BrokerCommunicator.java` and `config.property.broker` so the communication between services can take place.

```bash
$ ./run.sh build 1235 localHost
```

### up
`NOTE:` The port and hostname has to be same when creating the image through build command. 

The `up` command will start the service with given settings. It requires `docker-compose.yml` in the current directory, and it requires 4 arguments, `name`, `port`, `Broker_Hostname` and `data directory` location.

The `name` is for the service. The `port` is for the Broker listening port. The data directory is for EA and Broker storing their data. As the `BROKER_HOSTNAME` will be passed to the container, we can specify the Broker server name by given the environment variable the name you want. All of them should be different for different services if you run multiple services on a same machine.

```bash
$ ./run.sh up class1 1235 localHost ./class1
```

### down

The `down` command is for stoping and removing the service (including the network) with given name. 
`Note:` The data directory shared on host machine will not be removed.

```bash
$ ./run.sh down class1
```

### logs

The `logs` command is for checking the service logs with given name. Using this we can check if services on the container has been launched succesfully.

```bash
$ ./run.sh logs class1
```
