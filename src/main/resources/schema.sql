
create table "cyberstar"
(
    "cyberstar_id"             BIGINT auto_increment,
    "login_id"        CHARACTER VARYING(10) not null,
    "name"        CHARACTER VARYING(20) not null,
    "follower_count"  BIGINT default 0,
    "subscribe_count" BIGINT default 0,
    "friend_count"    BIGINT default 0,
    constraint cyberstarS_PK
        primary key ("cyberstar_id")
);

create table "follower"
(
    "id"          BIGINT auto_increment,
    "cyberstar_id" BIGINT not null,
    "follower_id"  BIGINT not null,
    constraint FOLLOWER_PK
        primary key ("id"),
    constraint FOLLOWER_cyberstar__id_FK
        foreign key ("cyberstar_id") references "cyberstar",
    constraint FOLLOWER_cyberstar__id_FK_2
        foreign key ("cyberstar_id") references "cyberstar"
);

create table "friend"
(
    "id"          BIGINT auto_increment,
    "cyberstar_id" BIGINT not null,
    "friend_id"    BIGINT not null,
    constraint FRIEND_PK
        primary key ("id"),
    constraint FRIEND_cyberstar__id_FK
        foreign key ("cyberstar_id") references "cyberstar",
    constraint FRIEND_cyberstar__id_FK_2
        foreign key ("friend_id") references "cyberstar"
);

create table "subscribed"
(
    "id"          BIGINT auto_increment,
    "cyberstar_id" BIGINT not null,
    "subscribed_id"    BIGINT not null,
    constraint SUBSCRIBED_PK
        primary key ("id"),
    constraint SUBSCRIBED_CYBERSTAR__ID_FK
        foreign key ("cyberstar_id") references "cyberstar",
    constraint SUBSCRIBED_CYBERSTAR__ID_FK_2
        foreign key ("subscribed_id") references "cyberstar"
);

