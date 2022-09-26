
create table client
(
    id   serial not null primary key,
    name varchar(50)
);

create table address
(
    street varchar(50),
    client_id bigint not null references client(id)
);

create table phone
(
    number varchar(50),
    client_id bigint not null references client(id)
);
