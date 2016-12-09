--CS 1555 Pitt Tours Procedures and Functions

--Ariella Hanna(ard71) and Alex Honeygosky(ach53)
--Erase the database
create or replace procedure erase_db
as
BEGIN
	delete from Airline;
	delete from Plane;
	delete from Flight;
	delete from Price;
	delete from Customer;
	delete from Reservation;
	delete from Reservation_detail;
	delete from Our_date;
END;
/

--Add an airline
create or replace procedure add_airline(id IN varchar2, name IN varchar2, 
abbreviation IN varchar2, year IN int)
as
BEGIN
	insert into Airline values(id, name, abbreviation, year);
END;
/
SHOW ERRORS;

--Add a flight
create or replace procedure add_flight(f_num IN varchar2, airline IN varchar2, 
plane IN varchar2, departure_city IN varchar2, arrival_city IN varchar2, departure_time IN varchar2,
arrival_time IN varchar2, schedule IN varchar2)
as
BEGIN
	insert into Flight values(f_num, airline, plane, departure_city, arrival_city, departure_time,
	arrival_time, schedule);
END;
/
SHOW ERRORS;

--Add pricing information
create or replace procedure add_price(depart IN varchar2, arrival IN varchar2, airline IN varchar2,
high IN number, low IN number)
as
BEGIN
	insert into Price values(depart, arrival, airline, high, low);
END;
/
SHOW ERRORS;

--Change price
create or replace procedure change_price(depart IN varchar2, arrival IN varchar2, airline IN varchar2,
high IN number, low IN number)
as
BEGIN
	update Price
	set high_price = high, low_price = low
	where departure_city = depart and arrival_city = arrival and airline_id = airline;
END;
/
SHOW ERRORS;

--Add a new plane
create or replace procedure add_plane(plane_type IN varchar2, manufacture IN varchar2, capacity IN number,
last_service IN date, year IN number, id IN varchar2)
as
BEGIN
	insert into Plane values(plane_type, manufacture, capacity, last_service, year, id);
END;
/
SHOW ERRORS;

--Get manifest
create or replace procedure get_manifest(f_num IN varchar2, f_date IN date)
as
Cursor c_names is 
	select C.salutation, C.first_name, C.last_name
	from Customer C
	join
	(select Res.cid
	from (select R.cid, Rd.flight_date, Rd.flight_number
	from Reservation R
	join
	Reservation_detail Rd
	on R.reservation_number = Rd.reservation_number) Res
	where (Res.flight_number = f_num and Res.flight_date = f_date)) RR
	on C.cid = RR.cid;
BEGIN
	for item in c_names loop
		dbms_output.put_line(item.salutation||' '||item.first_name||' '||item.last_name);
	end loop;
END;
/
SHOW ERRORS;

--Add new customer
create or replace function new_Customer(Salutation IN VARCHAR2, FName IN VARCHAR2, LName IN VARCHAR2,
CreditCard IN VARCHAR2, CreditCardExp IN VARCHAR2, Street IN VARCHAR2, City IN VARCHAR2, State IN VARCHAR2,
Phone IN VARCHAR2, Email IN VARCHAR2, Miles IN VARCHAR2)
RETURN INT
IS
returnVal INT;
BEGIN
	SELECT COUNT(CID) INTO returnVal
	FROM CUSTOMER;

	INSERT INTO CUSTOMER VALUES(returnVal+1, Salutation, FName, LName, CreditCard, CreditCardExp, Street, City, State, Phone, Email, Miles);
	
	RETURN (returnVal);
END;
/
SHOW ERRORS;