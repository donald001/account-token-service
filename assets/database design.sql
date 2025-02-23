create table account
(
    id             bigint generated by default as identity,
    created_time   timestamp,
    last_updated   timestamp,
    service_id     varchar(255) not null,
    contract_id    varchar(255),
    fleet_solution varchar(255) not null,
    status         varchar(255) check (status in ('Created', 'Activated', 'Deactivated')),
    primary key (id)
);
create table token
(
    id           bigint generated by default as identity,
    created_time timestamp,
    last_updated timestamp,
    account_id   bigint,
    status       varchar(255) check (status in ('Created', 'Assigned', 'Activated', 'Deactivated')),
    token        varchar(255),
    type         varchar(255) not null check (type in ('RFID', 'EMAID', 'MAC')),
    primary key (id)
);