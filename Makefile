# Makefile for building and publishing Docker image

# Version of the application
export VERSION ?= 1.0.0

# Version of Spring Boot to use
export SPRINGBOOT_VERSION ?= 2.5.5

# Stable version of the application
export STABLE_VERSION ?= 1.0.0

# Docker registry to use
export DOCKER_REGISTRY ?= springboot

# Docker image name
export DOCKER_IMAGE ?= payroll-api

# Name of the Docker image
IMAGE_NAME := $(DOCKER_REGISTRY)/$(DOCKER_IMAGE):$(VERSION)

# Build the Docker image
$(DOCKER_IMAGE):
	docker build -t $(DOCKER_REGISTRY)/$@:$(VERSION) -f Dockerfile .


# Push the Docker image to the registry
docker-push:
	docker push $(IMAGE_NAME)

# Tag the Docker image with the stable version
docker-tag:
	docker tag $(IMAGE_NAME) $(DOCKER_REGISTRY)/$(DOCKER_IMAGE):$(STABLE_VERSION)

# Push the stable Docker image to the registry
docker-push-stable: docker-tag
	docker push $(DOCKER_REGISTRY)/$(DOCKER_IMAGE):$(STABLE_VERSION)

# Clean up Docker artifacts
docker-clean:
	docker rmi $(IMAGE_NAME)
	docker rmi $(DOCKER_REGISTRY)/$(DOCKER_IMAGE):$(STABLE_VERSION)
