--CS 1555 Pitt Tours Project
--Ariella Hanna and Alex Honeygosky
--ard71 and ach53

drop table Airline cascade constraints;
drop table Flight cascade constraints;
drop table Plane cascade constraints;
drop table Price cascade constraints;
drop table Customer cascade constraints;
drop table Reservation cascade constraints;
drop table Reservation_detail cascade constraints;
drop table Our_date cascade constraints;

--airline_name and airline_abbreviation are both alternate keys
create table Airline(
	airline_id varchar2(5),
	airline_name varchar2(50) not null unique,
	airline_abbreviation varchar2(10)not null unique,
	year_founded int,
	constraint airline_pk primary key(airline_id)	
);

create table Plane(
	plane_type char(4),
	manufacture varchar2(10),
	plane_capacity int,
	last_service date,
	year int,
	owner_id varchar2(5),
	constraint plane_pk primary key (plane_type,owner_id),
	constraint plane_fk foreign key (owner_id) references Airline(airline_id) on delete cascade
);

create table Flight(
	flight_number varchar2(3),
	airline_id varchar2(5),
	plane_type char(4),
	departure_city varchar2(3) not null,
	arrival_city varchar2(3) not null,
	departure_time varchar2(4) not null,
	arrival_time varchar2(4) not null,
	weekly_schedule varchar2(7),
	constraint flight_pk primary key(flight_number),
	constraint flight_fk1 foreign key (plane_type,airline_id) references Plane(plane_type,owner_id) on delete cascade,
	constraint flight_fk2 foreign key (airline_id) references Airline(airline_id) on delete cascade,
	constraint flight_chk check(departure_city != arrival_city)
);

create table Price(
	departure_city varchar2(3),
	arrival_city varchar2(3),
	airline_id varchar2(5),
	high_price int not null,
	low_price int not null,
	constraint price_pk primary key(departure_city,arrival_city),
	constraint price_fk foreign key(airline_id) references Airline(airline_id) on delete cascade,
	constraint price_chk check(high_price >= low_price)
);

--credit number is not unique because a each member of a family may have an account and use
--the same credit card
create table Customer(
	cid varchar2(9),
	salutation varchar2(3),
	first_name varchar2(30) not null,
	last_name varchar2(30) not null,
	credit_card_num varchar2(16) not null,
	credit_card_expire date not null,
	street varchar2(30),
	city varchar2(30),
	state varchar2(2),
	phone varchar2(10),
	email varchar2(30),
	frequent_miles varchar2(5),
	constraint customer_pk primary key(cid),
	constraint check_salutation check (salutation = 'Mrs' or salutation = 'Mr' or salutation = 'Ms')
);

create table Reservation(
	reservation_number varchar2(5),
	cid varchar2(9),
	cost int,
	credit_card_num varchar2(16) not null,
	reservation_date date not null,
	ticketed varchar2(1) not null,
	start_city varchar2(3) not null,
	end_city varchar2(3) not null,
	constraint reservation_pk primary key(reservation_number),
	constraint customer_fk foreign key(cid) references Customer(cid) on delete cascade,
	constraint check_ticketed check (ticketed = 'Y' or ticketed = 'N')
);


create table Reservation_detail(
	reservation_number varchar2(5),
	flight_number varchar2(3),
	flight_date date not null,
	leg int,
	constraint reservation_detail_pk primary key(reservation_number,leg),
	constraint reservation_detail_fk1 foreign key(reservation_number) references Reservation(reservation_number)on delete cascade,
	constraint reservation_detail_fk2 foreign key(flight_number) references Flight(flight_number) on delete cascade
);

create table Our_date(
	c_date date,
	constraint our_date_pk primary key(c_date)
);
