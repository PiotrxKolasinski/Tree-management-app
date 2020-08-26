FROM node:alpine AS node
WORKDIR /app
COPY . .
RUN npm ci && npm run build --prod

FROM nginx:alpine
COPY --from=node /app/dist/tree-management-web /usr/share/nginx/html
