alter table customer
  add app_user_name varchar(50);
alter table customer
  add constraint fk_customer_app_user_name
    foreign key(app_user_name)
    references app_user(name);
commit;
