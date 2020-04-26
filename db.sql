
  drop table coupons.COMPANIES;
  drop table coupons.CUSTOMERS;
  drop table coupons.CATEGORIES;
  drop table coupons.COUPON;
  drop table coupons.CUSTOMERS_VS_CUPONS;




  create table coupons.COMPANIES(id integer, email varchar(100), password varchar(100), name varchar(100));
  create table coupons.CUSTOMERS(id integer, email varchar(100), password varchar(100), firstName varchar(100), lastName varchar(100));
  create table coupons.CATEGORIES(id integer, name varchar(100));
  create table coupons.COUPON(id integer, company_id integer, category_id integer, title varchar(100), description varchar(100), start_date Date, end_date Date,amount integer,  price double, image varchar(100));
  create table coupons.CUSTOMERS_VS_CUPONS(customer_id integer, cupon_id integer);

