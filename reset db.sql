alter table if exists character_episode drop foreign key if exists FKm6hmio03dhcqs9vkvfm76f3vf;
alter table if exists character_episode drop foreign key if exists FKr6idg38482wdp7n8jxesptqyu;
alter table if exists characters drop foreign key if exists FK92utx2lllqdkqalurreq5h43r;
alter table if exists characters drop foreign key if exists FKd3pgsseclg08j4x78hvt0yf1n;
alter table if exists user_favorites drop foreign key if exists FK91hvh1gui6m5qt5agtmo8ojks;
alter table if exists user_roles drop foreign key if exists FKhfh9dx7w3ubf1co1vdev94g3f;
drop table if exists character_episode;
drop table if exists characters;
drop table if exists episodes;
drop table if exists locations;
drop table if exists user_favorites;
drop table if exists user_roles;
drop table if exists users;

create table character_episode (character_id integer not null, episode_id integer not null, primary key (character_id, episode_id)) engine=InnoDB;
create table characters (id integer not null auto_increment, location_id integer, origin_id integer, source_id integer, created datetime(6), gender varchar(255), image varchar(255), name varchar(255), species varchar(255), status varchar(255), type varchar(255), primary key (id)) engine=InnoDB;
create table episodes (id integer not null auto_increment, source_id integer, created datetime(6), air_date varchar(255), episode varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table locations (id integer not null auto_increment, source_id integer, created datetime(6), dimension varchar(255), name varchar(255), type varchar(255), primary key (id)) engine=InnoDB;
create table user_favorites (character_id integer, id integer not null auto_increment, username varchar(255), primary key (id)) engine=InnoDB;
create table user_roles (user_id bigint not null, role varchar(255)) engine=InnoDB;
create table users (id bigint not null auto_increment, password varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB;
alter table if exists characters add constraint UK2cdrn845gk3iddssbdvh797sr unique (source_id);
alter table if exists users add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
alter table if exists character_episode add constraint FKm6hmio03dhcqs9vkvfm76f3vf foreign key (character_id) references characters (id);
alter table if exists character_episode add constraint FKr6idg38482wdp7n8jxesptqyu foreign key (episode_id) references episodes (id);
alter table if exists characters add constraint FK92utx2lllqdkqalurreq5h43r foreign key (location_id) references locations (id);
alter table if exists characters add constraint FKd3pgsseclg08j4x78hvt0yf1n foreign key (origin_id) references locations (id);
alter table if exists user_favorites add constraint FK91hvh1gui6m5qt5agtmo8ojks foreign key (character_id) references characters (id);
alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id);
