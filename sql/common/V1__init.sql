create table customer (
  id                      int identity(1, 1),
  full_name               nvarchar(100),
  email                   nvarchar(100),
  phone                   nvarchar(100),
  shipping_address        nvarchar(200),
  anonymous_session_id    nvarchar(100),
  constraint pk_customer
    primary key(id),
  constraint nn_customer_full_name
    check(full_name is not null),
  constraint nn_customer_shipping_address
    check(shipping_address is not null)
);
--
create table goods_item (
  id                      int identity(1, 1),
  name                    nvarchar(200),
  description             nvarchar(4000),
  price                   numeric(18,2),
  constraint pk_goods_item
    primary key(id),
  constraint nn_goods_item_name
    check(name is not null)
);
--
create table goods_category (
  id                      int identity(1, 1),
  name                    nvarchar(100),
  constraint pk_goods_category
    primary key(id),
  constraint nn_goods_category_name
    check(name is not null),
  constraint uk_goods_category
    unique(name)
);
--
create table goods_category_link (
  goods_item_id           int,
  goods_category_id       int,
  constraint pk_goods_category_link
    primary key(goods_item_id, goods_category_id),
  constraint fk_goods_category_link_item
    foreign key(goods_item_id)
    references goods_item(id),
  constraint fk_goods_category_link_category
    foreign key(goods_category_id)
    references goods_category(id)
);
--
create table order_head (
  id                      int identity(1, 1),
  order_number            nvarchar(30),
  customer_id             int,
  created                 datetime2,
  is_basket               int,
  constraint pk_order_head
    primary key(id),
  constraint fk_order_head_customer_id
    foreign key(customer_id)
    references customer(id),
  constraint nn_order_head_customer_id
    check(customer_id is not null),
  constraint nn_order_head_created
    check(created is not null),
  constraint nn_order_head_is_basket
    check(is_basket is not null),
  constraint ch_order_head_is_basket
    check(is_basket in (0, 1))
);
--
create table order_detail (
  id                      int identity,
  order_head_id           int,
  goods_item_id           int,
  quantity                number(18,0),
  constraint pk_order_detail
    primary key (id),
  constraint fk_order_detail_order_head_id
    foreign key(order_head_id)
    references order_head(id),
  constraint fk_order_detail_goods_item_id
    foreign key(goods_item_id)
    references goods_item(id),
  constraint nn_order_detail_quantity
    check(quantity is not null),
  constraint ch_order_detail_quantity
    check(quantity > 0)
);
--
