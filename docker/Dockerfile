# Building Stage
FROM openjdk:8-alpine as Builder
ARG branch

WORKDIR /app/

RUN set -xe \
    && apk add --no-cache wget bash subversion \
    && wget https://raw.githubusercontent.com/cereal-lab/EvoParsons/${branch}/scripts/download-server --content-disposition --no-check-certificate \
    && chmod a+x ./download-server \
    && BRANCH=${branch} ./download-server

RUN ls -al      

RUN set -xe \
  && chmod a+x ./scripts/build-server \  
  && ./scripts/build-server

# Runtime Stage
FROM openjdk:8-jre-alpine

# We use supervisord to run multiple programs in a single container.
RUN apk add --no-cache supervisor bash subversion

WORKDIR /app/

# Get what we built during the building process
COPY --from=Builder /app/docker/supervisord.conf /etc/
COPY --from=Builder /app/bin/*.jar /app/
COPY --from=Builder /app/scripts/evoparsons-server /app/

RUN chmod a+x /app/evoparsons-server

# Specify where we are going to store the data
VOLUME [ "/data" ]

CMD [ "/usr/bin/supervisord"]
