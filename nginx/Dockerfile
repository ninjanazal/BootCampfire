FROM nginx:latest

RUN rm /etc/nginx/config.d/default.conf

COPY nginx/nginx.config /etc/nginx/config.d/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]