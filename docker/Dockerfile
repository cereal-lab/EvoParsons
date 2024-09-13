# Building Stage
FROM openjdk:12-alpine AS builder
ARG branch
WORKDIR /app/
RUN set -xe \
    && apk add --no-cache wget bash git sqlite-dev \
    && wget https://raw.githubusercontent.com/cereal-lab/EvoParsons/${branch}/scripts/download-server --content-disposition --no-check-certificate \
    && chmod a+x ./download-server \
    && BRANCH=${branch} ./download-server \
    && chmod a+x ./scripts/build-server \
    && ./scripts/build-server

FROM openjdk:12-alpine
WORKDIR /app/
COPY --from=builder /app/bin/*.jar /app/
COPY --from=builder /app/scripts/evoparsons-server /app/
#COPY --from=builder /app/Programs /data/Programs/
#COPY --from=builder /app/Transforms /data/Transforms/
RUN chmod a+x /app/evoparsons-server
ENTRYPOINT [ "/app/evoparsons-server" ]