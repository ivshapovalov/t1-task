--liquibase formatted sql

--changeset Ivan:3
alter table if exists products add constraint product_category_constraint foreign key (category_id) references categories;
alter table if exists reviews add constraint review_product_constraint foreign key (product_id) references products;

--rollback
alter table if exists products drop constraint if exists product_category_constraint;
alter table if exists reviews drop constraint if exists review_product_constraint;