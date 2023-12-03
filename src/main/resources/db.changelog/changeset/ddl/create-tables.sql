
create table if not exists conditions
(
    id   bigserial primary key,
    text varchar(255)
);

create table if not exists currents
(
    id           bigserial        primary key,
    humidity     double precision not null,
    last_updated timestamp(6)     unique,
    pressure_mb  double precision,
    temp_c       double precision,
    wind_mph     double precision,
    condition_id bigint unique,
    constraint fk_condition_current foreign key (condition_id) references conditions (id)
);

create table if not exists locations
(
    id         bigserial        primary key,
    country    varchar(255),
    lat        double precision not null,
    local_time timestamp(6),
    lon        double precision not null,
    name       varchar(255),
    region     varchar(255)
);

create table if not exists weathers
(
    id          bigserial primary key,
    current_id  bigint unique,
    constraint fk_current_weather foreign key (current_id) references currents (id),
    location_id bigint unique,
    constraint fk_location_weather foreign key (location_id) references locations (id)
);

