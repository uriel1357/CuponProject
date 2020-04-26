create table COMPANIES(id integer, email varchar(100), password varchar(100), name varchar(100));
create table CUSTOMERS(id integer, email varchar(100), password varchar(100), firsr_name varchar(100), last_name varchar(100));
create table CATEGORIES(id integer, name varchar(100));
create table CUPONS(id integer, company_id integer, category_id integer, title varchar(100), description varchar(100), start_date Date, end_date Date,amount integer,  price double, image varchar(100));
create table CUSTOMERS_VS_CUPONS(customer_id integer, cupon_id integer);
insert into COMPANIES (id , email , password , name ) values (1, "email@gmail.com", "pass", "comp");
select count(*) from COMPANIES where  email="email@gmail.com" and password="pass";

-- insert into Test(id, title) values(1, "Hello");
-- select * from Test;
-- Your code here!

