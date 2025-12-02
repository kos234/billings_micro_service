FROM node:20 AS build-stage

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
# Собираем проект (создаст папку dist)
RUN npm run build

# ЭТАП 2: Запуск (Production)
FROM nginx:stable-alpine as production-stage

# Копируем собранные файлы из первого этапа в папку Nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Копируем наш конфиг nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]