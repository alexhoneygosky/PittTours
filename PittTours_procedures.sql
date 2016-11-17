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