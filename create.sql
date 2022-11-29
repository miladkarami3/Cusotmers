create table customer (id bigint not null, birth_date datetime, email varchar(255), lastname varchar(255), name varchar(255), phone_number varchar(255), time_zone_id varchar(255), primary key (id)) engine=InnoDB;
create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table purchase (id bigint not null, date date, price bigint, product_name varchar(255), customer_id bigint, primary key (id)) engine=InnoDB;
alter table purchase add constraint FK2pehe23hwdcyql94c531rbf70 foreign key (customer_id) references customer (id);
