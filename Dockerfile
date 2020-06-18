FROM homecentr/base:2.4.0-alpine

ENV DOCKERD_HOST="172.17.0.1"
ENV DOCKERD_PORT="9323"

RUN apk add --no-cache socat=1.7.3.3-r1	

COPY ./fs/ /

EXPOSE 9323