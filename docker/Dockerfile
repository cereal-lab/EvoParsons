# Building Stage
FROM openjdk:8-alpine as Builder
ARG branch
WORKDIR /app/
RUN set -xe \
    && apk add --no-cache wget bash subversion sqlite-dev \
    && wget https://raw.githubusercontent.com/cereal-lab/EvoParsons/${branch}/scripts/download-server --content-disposition --no-check-certificate \
    && chmod a+x ./download-server \
    && BRANCH=${branch} ./download-server \
    && chmod a+x ./scripts/build-server \
    && ./scripts/build-server

FROM openjdk:8-jre-alpine
WORKDIR /app/
COPY --from=Builder /app/bin/*.jar /app/
COPY --from=Builder /app/scripts/evoparsons-server /app/
#COPY --from=Builder /app/Programs /data/Programs/
#COPY --from=Builder /app/Transforms /data/Transforms/
RUN chmod a+x /app/evoparsons-server
ENTRYPOINT [ "/app/evoparsons-server" ]