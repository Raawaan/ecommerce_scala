
 drop table if exists customer;
 drop table if exists item;
 drop table if exists `order`;
 drop table if exists ordered_items;
 drop schema if exists ecommerce;
 create schema ecommerce;
use ecommerce;
create table ecommerce.customer
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table item
(
    id          int auto_increment
        primary key,
    name        varchar(200) null,
    price       int          null,
    isAvailable tinyint(1)   null
);

create table `order`
(
    id          int auto_increment
        primary key,
    customer_id int not null,
    constraint order_customer_id_fk
        foreign key (customer_id) references customer (id)
);

create table ordered_items
(
    id       int auto_increment
        primary key,
    order_id int not null,
    item_id  int null,
    constraint ordered_items_item_id_fk
        foreign key (item_id) references item (id),
    constraint ordered_items_order_id_fk
        foreign key (order_id) references `order` (id)
);


insert into customer (id, name)
values (1,'Rawan');

insert into item (id, name, price, isAvailable)
values (1,'pizza',200,1);
