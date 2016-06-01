use `computer-database-db`;
drop table if exists user;

create table user (
  id                        bigint not null auto_increment,
  login                     varchar(255) not null,
  password                  varchar(255) not null,
  role                      varchar(255) not null,
  constraint pk_user primary key (id));
