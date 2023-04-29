#!/bin/bash

if [[ "$1" == "-h" || "$1" == "--help" ]]; then
    cat << EndOfMessage
Usage: ${0##*/} [<image-version>]
Arguments:
  <image-version>  The image tag to use. Defaults to '1.0.0'
EndOfMessage
    exit 1
fi

VERSION="${1:-1.0.0}"
DOCKER_REGISTRY=${DOCKER_REGISTRY:-springboot}
DOCKER_IMAGE=${DOCKER_IMAGE:-payroll-api}
portNum=${portNum:-8080}


docker run -d -p $portNum:$portNum --name $DOCKER_IMAGE $DOCKER_REGISTRY/$DOCKER_IMAGE:$VERSION
