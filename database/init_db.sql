create table customer
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
insert into customer (id, name)
values (2,'Ali');
insert into customer (id, name)
values (3,'Mahmoud');
insert into item (id, name, price, isAvailable)
values (1,'pizza',200,1);
insert into item (id, name, price, isAvailable)
values (2,'burger',20,1);
insert into item (id, name, price, isAvailable)
values (3,'water',2000,1)
;insert into item (id, name, price, isAvailable)
 values (4,'coffee',2,0);

