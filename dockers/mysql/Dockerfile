FROM mysql:5.5

ENV MYSQL_DATABASE computer-database-db
ENV MYSQL_ROOT_PASSWORD root

COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
COPY 3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql