alter table customer
  drop constraint nn_customer_full_name;
alter table customer
  drop constraint nn_customer_shipping_address;
alter table customer
  add constraint ch_nn_customer_full_name
    check(full_name is not null or app_user_name is null)
;
alter table customer
  add constraint ch_nn_customer_shipping_address
    check(shipping_address is not null or app_user_name is null)
;
