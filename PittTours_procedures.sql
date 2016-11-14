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