[![Project status](https://badgen.net/badge/project%20status/stable%20%26%20actively%20maintaned?color=green)](https://github.com/homecentr/docker-dockerd-exporter/graphs/commit-activity) [![](https://badgen.net/github/label-issues/homecentr/docker-dockerd-exporter/bug?label=open%20bugs&color=green)](https://github.com/homecentr/docker-dockerd-exporter/labels/bug) [![](https://badgen.net/github/release/homecentr/docker-dockerd-exporter)](https://hub.docker.com/repository/docker/homecentr/dockerd-exporter)
[![](https://badgen.net/docker/pulls/homecentr/dockerd-exporter)](https://hub.docker.com/repository/docker/homecentr/dockerd-exporter) 
[![](https://badgen.net/docker/size/homecentr/dockerd-exporter)](https://hub.docker.com/repository/docker/homecentr/dockerd-exporter)

![CI/CD on master](https://github.com/homecentr/docker-dockerd-exporter/workflows/CI/CD%20on%20master/badge.svg)
![Regular Docker image vulnerability scan](https://github.com/homecentr/docker-dockerd-exporter/workflows/Regular%20Docker%20image%20vulnerability%20scan/badge.svg)


# HomeCentr - dockerd-exporter
This image is based on the original [dockerd-exporter] by [Stefan Prodan](https://github.com/stefanprodan). Docker daemon exposes natively metrics in Prometheus compatible format. In Swarm environment, Prometheus would be only able to reach the metrics of the node it is running on. This container allows exposing these metrics into an overlay network. This way Prometheus can just scrape the containers.

## Usage

```yml
version: "3.7"
services:
  dockerd-exporter:
    build: .
    image: homecentr/dockerd-exporter
```

## Exposing metrics on Docker daemon

Please note that the metrics are currently not exposed by default. It requires enabling experimental features as described in the [Docker docs](https://docs.docker.com/config/daemon/prometheus/).

## Environment variables

| Name | Default value | Description |
|------|---------------|-------------|
| PUID | 7077 | UID of the user dockerd-exporter should be running as. The user does NOT require access to Docker socket. |
| PGID | 7077 | GID of the user dockerd-exporter should be running as. The group does NOT require access to Docker socket. |
| DOCKERD_HOST | host.docker.internal | Hostname pointing to the node hosting Docker daemon with exposed metrics. `host.docker.internal` points to the node hosting the container and works correctly also in Docker Swarm. |
| DOCKERD_PORT | 9323 | Port on which the metrics are exposed. 9323 is the value used by Docker docs so the image uses it as default. |

> Please note that the container must be connected to a network for the `host.docker.internal` record to be resolvable via Docker DNS.  

## Exposed ports

| Port | Protocol | Description |
|------|------|-------------|
| 9323 | TCP | HTTP endpoint with the metrics in Prometheus compatible format. |

## Volumes

This container does not expose any volumes.

## Security
The container is regularly scanned for vulnerabilities and updated. Further info can be found in the [Security tab](https://github.com/homecentr/docker-dockerd-exporter/security).

### Container user
The container supports privilege drop. Even though the container starts as root, it will use the permissions only to perform the initial set up. The dockerd-exporter process runs as UID/GID provided in the PUID and PGID environment variables.

:warning: Do not change the container user directly using the `user` Docker compose property or using the `--user` argument. This would break the privilege drop logic.