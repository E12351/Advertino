FROM java:8
ENV http_proxy 'http://10.0.21.124:8084/'
ENV https_proxy 'http://10.0.21.124:8084/'
ENV no_proxy 'localhost,52.221.154.229'
RUN apt-get update -qqy && apt-get -qy install apt-utils net-tools
RUN curl -sL https://deb.nodesource.com/setup_8.x | bash -
RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add -
RUN echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list
RUN apt-get -qy install nodejs
RUN npm install nodemon -g

RUN npm install forever -g
RUN npm install forever-monitor

RUN rm -rf /var/lib/apt/lists/*

WORKDIR /app
ADD Java_Backend/build/libs/bms-0.0.1-SNAPSHOT.jar app.jar
COPY config /app/config
COPY models /app/models
COPY public /app/public
COPY routes /app/routes
COPY Services /app/Services
COPY views /app/views
COPY app.js /app/app.js
COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json
COPY test.js /app/test.js
RUN npm install
#ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.config.location=/app/config/application.properties -jar /app/app.jar & forever start /app/app.js production" ]
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.config.location=/app/config/application.properties -jar /app/app.jar" ]

