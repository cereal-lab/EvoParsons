# Building Stage
FROM openjdk:8-alpine as Builder

WORKDIR /app/

RUN set -xe \
    && apk add --no-cache wget bash subversion \
    && wget https://sourceforge.net/p/evotutoring/code/HEAD/tree/trunk/scripts/download-server?format=raw --content-disposition --no-check-certificate \
    && chmod a+x ./download-server \
    && ./download-server

RUN ls -al      

RUN set -xe \
  && chmod a+x ./scripts/build-server \  
  && ./scripts/build-server

# Runtime Stage
FROM openjdk:8-jre-alpine

# We use supervisord to run multiple programs in a single container.
RUN apk add --no-cache supervisor bash

WORKDIR /app/

# Get what we built during the building process
COPY --from=Builder /app/docker/supervisord.conf /etc/
COPY --from=Builder /app/bin/*.jar /app/
COPY --from=Builder /app/scripts/evoparsons-server /app/
COPY --from=Builder /app/ParsonsLibraries /app/ParsonsLibraries

RUN chmod a+x /app/evoparsons-server

# Specify where we are going to store the data
VOLUME [ "/data" ]

CMD [ "/usr/bin/supervisord"]
