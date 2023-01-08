set schema PUBLIC;

create table "cyberstar"
(
    "cyberstar_id"             BIGINT auto_increment,
    "login_id"        CHARACTER VARYING(10) not null,
    "name"        CHARACTER VARYING(20) not null,
    constraint cyberstarS_PK
        primary key ("cyberstar_id")
);

create table "relationship"
(
    "cyberstar_id"   BIGINT not null,
    "follower_count"  BIGINT default 0,
    "following_count" BIGINT default 0,
    "friend_count"    BIGINT default 0,
    "version"   BIGINT not null default 0,
    constraint relationship_PK
        primary key ("cyberstar_id"),
    constraint relationship_cyberstar_id_FK
        foreign key ("cyberstar_id") references "cyberstar"
);

create table "friend"
(
    "id"          BIGINT auto_increment,
    "cyberstar_id" BIGINT not null,
    "friend_id"    BIGINT not null,
    constraint FRIEND_PK
        primary key ("id"),
    constraint FRIEND_cyberstar_id_FK
        foreign key ("cyberstar_id") references "cyberstar",
    constraint FRIEND_cyberstar_id_FK_2
        foreign key ("friend_id") references "cyberstar"
);

create table "subscribe"
(
    "id"          BIGINT auto_increment,
    "cyberstar_id" BIGINT not null,
    "subscribe_id"    BIGINT not null,
    constraint SUBSCRIBE_PK
        primary key ("id"),
    constraint SUBSCRIBE_CYBERSTAR_ID_FK
        foreign key ("cyberstar_id") references "cyberstar",
    constraint SUBSCRIBE_CYBERSTAR_ID_FK_2
        foreign key ("subscribe_id") references "cyberstar",
    constraint SUBSCRIBE_UNIQUE
        unique ("cyberstar_id", "subscribe_id")
);

