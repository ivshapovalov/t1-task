create table categories
(
    id   bigserial    not null,
    name varchar(255) not null,
    primary key (id)
);

create table products
(
    price       numeric(38, 2) not null,
    category_id bigint,
    id          bigserial      not null ,
    description varchar(255)   not null,
    name        varchar(255)   not null,
    primary key (id)
);

create table reviews
(
    id         bigserial    not null ,
    product_id bigint,
    message    varchar(255) not null,
    primary key (id)
);

GO
