FROM postgres:13

RUN apt-get update

# pgcrypto extension
RUN apt-get install postgresql-contrib -y

# init db
COPY init-db.sql /docker-entrypoint-initdb.d/
