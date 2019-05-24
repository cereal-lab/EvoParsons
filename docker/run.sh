#!/bin/bash

branch="Mark3"

docker_host_setup(){

  ##SET UP THE REPOSITORY
  sudo apt-get update 
  sudo apt-get install -y --no-install-recommends \
      apt-transport-https \
      ca-certificates \
      curl \
      software-properties-common

  sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

  sudo add-apt-repository \
     "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
     $(lsb_release -cs) \
     stable"

## Installing docker package on docker host
  sudo apt-get update
  sudo apt-get install docker-ce -y

## Add Docker daemon configuration
  cat <<EOF | sudo tee /etc/docker/daemon.json
{
  "icc": false,
  "disable-legacy-registry": true,
  "userland-proxy": false,
  "live-restore": true
}
EOF

  # Post docker installation steps
  # Start docker service
  sudo systemctl enable docker
  sudo systemctl start docker
  # Add current user to docker group
  sudo usermod -aG docker $USER
  # verify docker is working
  docker version
  docker info

  # Installing docker-compose package on docker host
  sudo apt-get update
  sudo apt-get install docker-compose -y
  
  # verify docker-compose is working
  docker-compose version
}

#Red Hat, CentOS
docker_host_setup_rh(){

  ##SET UP THE REPOSITORY
  wget https://download.docker.com/linux/centos/7/x86_64/stable/Packages/docker-ce-18.03.1.ce-1.el7.centos.x86_64.rpm --content-disposition --no-check-certificate
  sudo yum localinstall ./docker-ce-18.03.1.ce-1.el7.centos.x86_64.rpm
## Add Docker daemon configuration
  cat <<EOF | sudo tee /etc/docker/daemon.json
{
  "icc": false,
  "disable-legacy-registry": true,
  "userland-proxy": false,
  "live-restore": true
}
EOF

  # Post docker installation steps
  # Start docker service
  sudo systemctl enable docker
  sudo systemctl start docker
  # Add current user to docker group
  #here we should create group
  sudo groupadd docker
  #TODO:logout and login problem
  sudo usermod -aG docker $USER
  # verify docker is working
  docker version
  docker info

  # Installing docker-compose package on docker host
  sudo yum install docker-compose
  
  # verify docker-compose is working
  docker-compose version
}

docker_container_build() {
    
    # Download the config files for the docker container
    TargetRepository="https://raw.githubusercontent.com/cereal-lab/EvoParsons/$branch/docker/"
    for TargetFile in "Dockerfile"
    do
	    curl -fsSL ${TargetRepository}${TargetFile} -o ${TargetFile}
    done
    
    docker build --no-cache -t evoparsons_server --build-arg branch=$branch -f Dockerfile .
}


docker_container_run() {
  if [[ "$#" -ne 2 ]]; then
    echo "Usage:"
    echo "    $0 up <name> <configUrl>"
    echo "    Example 1: $0 up class1 https://raw.githubusercontent.com/cereal-lab/EvoParsons/$branch/ev.json"
    echo "    Example 2: $0 up class2 file:///home/dvitel/usf/sw/evotutoring/trunk/ev.json"
    echo "    Note: if file:// is provided, path will be converted relative to container"
    echo ""
    return 1;
  fi 

  local name="$(echo ${1} | tr ' ' '-')"
  local config=${2}
  local configContent="$(curl $config)"  
  if [[ "$?" -ne 0 ]]; then
    echo "Cannot open url of provided config ${config}"
    return 1
  fi
  local dbName="${name}" 
  local dbPort="$(grep -Eo "\"dbPort\"\s*:\s*\"([1-9][0-9]*)\"" <<< "$configContent" | cut -d: -f2 | sort -u | xargs)"
  configContent=$(echo "$configContent" | sed -e "s \[\[BRANCH\]\] ${branch} g")
  if [ "$?" -ne 0 ] || [ -z "$dbPort" ]; then
    echo "Cannot find dbPort in ${config}"
  else 
    echo "DB port was found: $dbPort"     
    local cs="mongodb://evoRoot:8ndhMVk4Bt%40b123@db:27017/$dbName?authSource=admin"
    configContent=$(echo "$configContent" | sed -e "s \"db\"\s*:\s*\".*\" \"db\":\"$cs\" g")
  fi    
  local existingMongo="$(docker ps --format 'table {{.Names}} {{.Ports}}' | grep ":$dbPort->" | cut -d ' ' -f1)"
  if [ -z "$existingMongo" ]; then           
    existingMongo="m-$dbPort"
    docker network create -d bridge $existingMongo
    mkdir -p /EvoParsons/mongo/$dbName     
    docker run --name $existingMongo -p 0.0.0.0:$dbPort:27017/tcp \
      --restart=always -e MONGO_INITDB_ROOT_USERNAME=evoRoot \
      -e MONGO_INITDB_ROOT_PASSWORD=8ndhMVk4Bt@b123 \
      --network=$existingMongo --network-alias=db \
      -v /EvoParsons/mongo/$existingMongo:/data/db -d mongo:4.1-bionic   
  else 
    echo "DB port $dbPort is occupied by $existingMongo - assuming by other mongodb instance!!!!!"  
  fi
  local hosts="$(grep -Eo "\"hostname\"\s*:\s*\"(\S*)\"" <<< "$configContent" | cut -d: -f2 | sort -u | xargs)"
  if [ "$?" -ne 0 ] || [ -z "$hosts" ]; then
    echo "Cannot find host in ${config}"
    return 1
  else 
    echo "Host was found: $hosts"    
  fi    
  local ports="$(grep -Eo "\"port\"\s*:\s*\"(\S*)\"" <<< "$configContent" | cut -d: -f2 | sort -u | xargs)"
  if [ "$?" -ne 0 ] || [ -z "$ports" ]; then
    echo "Cannot find port in ${config}"
    return 1
  else 
    echo "Port was found: $ports"    
  fi    
  local data="$(pwd)/${1}"
  mkdir -p "$data"
  echo "$configContent"  
  echo "$configContent" > "$data/ev.json"
  config="/app/DATA.out/ev.json"
  echo "Config was remapped to $config"
  
  echo "Starting services for $name ..."
  echo "Data folder: $data"
  
  #export BROKER_PORT=$port
  #export BROKER_HOSTNAME=$host
  #export DATA_DIR=$data
  #export BROKER_CONFIG=$config

#   local composeConfig="./$name.yml"
# cat << EOF > $composeConfig
# version: '2'
# services:
#   EvoParsonsServer:
#     image: evoparsons_server
#     restart: always
#     ports:
# EOF
# for port in $ports
# do
# cat << EOF >> $composeConfig
#       - "${port}:${port}"
# EOF
# done
# # if -n "$dbPort" then 
# # cat << EOF >> $composeConfig
# #       - "${dbPort}:${dbPort}"
# # EOF
# # fi
# cat << EOF >> $composeConfig
#     environment:
#       - "BROKER_CONFIG=${BROKER_CONFIG}"
#     volumes:
#       - "${DATA_DIR}:/app/DATA.out"
#     networks:
#       default:
#         aliases:
# EOF
# for host in $hosts
# do
# cat << EOF >> $composeConfig
#           - ${host}
# EOF
# done
  local mappedPorts=()
  for port in $ports; do
    mappedPorts+=("-p 0.0.0.0:$port:$port/tcp")
  done
  echo "ports are ${mappedPorts[@]}"
  #docker-compose -f $composeConfig -p $name up -d 
  docker run --name $name ${mappedPorts[@]} \
    --restart=always -e BROKER_CONFIG=${config} \
    --network=$existingMongo --network-alias=$name \
    -v ${data}:/app/DATA.out -d evoparsons_server

  #--network=container:$existingMongo \

}



docker_container_down() {

  if [ "$#" -ne  1 ]; then
    echo "Usage:"
    echo "    $0 down <name>"
    return 1
  fi
  p=_EvoParsonsServer_1
  local name=${1}
  echo "Shutting down ..."
  docker stop $name$p
  echo "Container Removed"
}

docker_container_logs(){

  if [ "$#" -ne  1 ]; then
    echo "Usage:"
    echo "    $0 logs <name>"
    return 1
  fi
  p=_EvoParsonsServer_1
  local name=${1}
  shift
  echo "Logs for $name$p..."
  docker logs $name$p
}


# script starts here
command=$1
shift

case "$command" in
    build) docker_container_build $@ ;;
    up) docker_container_run $@ ;;
    down) docker_container_down $@ ;;
    logs) docker_container_logs $@ ;;
    host_setup) docker_host_setup $@ ;;
    *)        echo "Usage: <build|up|down|logs|host_setup>" ;;
esac
