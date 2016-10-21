--liquibase formatted sql
--changeset V1_001_artist_album_track

create table sprayground.artists
(
  "id" uuid not null primary key,
  "first" text,
  "last" text,
  "group_name" text
);

alter table sprayground.artists owner to deploydb;

grant select, insert, update, delete on sprayground.artists to sprayground_rw;

create table sprayground.albums
(
  "id" uuid not null primary key,
  "artist_id" uuid references sprayground.artists(id),
  "title" text,
  "release_date" date not null
);

alter table sprayground.albums owner to deploydb;

grant select, insert, update, delete on sprayground.albums to sprayground_rw;

create table sprayground.tracks
(
  "id" uuid not null primary key,
  "album_id" uuid references sprayground.albums(id),
  "name" text,
  "length" int
);

alter table sprayground.tracks owner to deploydb;

grant select, insert, update, delete on sprayground.tracks to sprayground_rw;

--rollback drop table if exists sprayground.tracks
--rollback drop table if exists sprayground.albums
--rollback drop table if exists sprayground.artists