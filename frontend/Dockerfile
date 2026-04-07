#https://v2.vuejs.org/v2/cookbook/dockerize-vuejs-app
FROM node:lts-alpine
RUN npm install -g http-server
WORKDIR /frontend
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 8080
CMD [ "http-server", "dist" ]
