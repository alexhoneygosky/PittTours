--CS 1555 Pitt Tours Triggers and Views

--Ariella Hanna(ard71) and Alex Honeygosky(ach53)

--View to get all flights and their prices
CREATE OR REPLACE VIEW ALLFLIGHTS
AS
SELECT p.airline_id, f.flight_number, p.high_price, p.low_price, f.departure_city, f.arrival_city
FROM PRICE p, FLIGHT f
WHERE p.airline_id = f.airline_id;

--View to get the amount of seats taken on each flight currently
CREATE OR REPLACE VIEW FLIGHTSEATS
AS
SELECT rd.Flight_Number, COUNT(rd.Flight_Number) AS Taken_Seats, f.Plane_Type
FROM RESERVATION_DETAIL rd, FLIGHT f
WHERE rd.Flight_Number = f.Flight_Number
GROUP BY rd.Flight_Number, f.Plane_Type;

--View to get the capacity of each plane
CREATE OR REPLACE VIEW PLANECAPACITY
AS
SELECT f.Flight_Number, f.Plane_Type, p.Plane_Capacity, f.Departure_City, f.Arrival_City, f.Weekly_Schedule
FROM PLANE p, FLIGHT f
WHERE p.Plane_Type = f.Plane_Type;

--View to get all of the details of all of the flights for all reservations
CREATE OR REPLACE VIEW RESERVATIONSPLUSFLIGHTS
AS
SELECT rd.Reservation_Number, rd.Flight_Number, rd.Flight_Date, rd.Leg, f.Airline_Id, f.Plane_Type, f.Departure_City, f.Arrival_City, f.Departure_Time, f.Arrival_Time, f.Weekly_Schedule
FROM RESERVATION_DETAIL rd, FLIGHT f
WHERE rd.Flight_Number = f.Flight_Number;

--Trigger for adjust tickets
CREATE OR REPLACE TRIGGER adjustTicket
BEFORE UPDATE OF High_Price, Low_Price
ON PRICE
REFERENCES NEW AS new OLD AS old
FOR EACH ROW
DECLARE
flightNumber VARCHAR2(3);
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
	IF (:new.High_Price <> :old.High_Price) THEN
		SELECT Flight_Number INTO flightNumber 
		FROM ALLFLIGHTS af
		WHERE :new.High_Price <> af.High_Price AND af.Departure_City = :new.Departure_City AND af.Arrival_City = :new.Arrival_City;
		
		UPDATE RESERVATION
		SET Cost = Cost - :old.High_Price + :new.High_Price
		WHERE Reservation_Number IN 
		(
			SELECT Reservation_Number
			FROM RESERVATION_DETAIL
			WHERE Flight_Number = flightNumber
		) AND Ticketed = 'N';		
	ELSE
		SELECT Flight_Number INTO flightNumber 
		FROM ALLFLIGHTS af
		WHERE :new.Low_Price <> af.Low_Price AND af.Departure_City = :new.Departure_City AND af.Arrival_City = :new.Arrival_City;	

		UPDATE RESERVATION
		SET Cost = Cost - :old.Low_Price + :new.Low_Price
		WHERE Reservation_Number IN 
		(
			SELECT Reservation_Number
			FROM RESERVATION_DETAIL
			WHERE Flight_Number = flightNumber
		);		
	END IF;
END;
/

--Trigger for plane upgrades
CREATE OR REPLACE TRIGGER planeUpgrade
BEFORE INSERT
ON RESERVATION
FOR EACH ROW
DECLARE currentPlaneSeatsTaken INT;
flightNumber VARCHAR2(3);
flightDate DATE;
planeType VARCHAR(4);
totalPlaneSeats INT;
maxPlaneSeats INT;
ownerId VARCHAR2(5);
planeLessPassengers VARCHAR2(4);
biggestPlaneSeats INT;
newPlane CHAR(4);
weeklySchedule VARCHAR2(7);
BEGIN
	SELECT DISTINCT Flight_Number INTO flightNumber
	FROM RESERVATION_DETAIL
	WHERE Reservation_Number = :new.Reservation_Number;
	
	SELECT Plane_Type, Taken_Seats INTO planeType, currentPlaneSeatsTaken
	FROM FLIGHTSEATS
	WHERE Flight_Number = flightNumber;
	
	SELECT Plane_Capacity, Weekly_Schedule INTO totalPlaneSeats, weeklySchedule
	FROM PLANECAPACITY
	WHERE Plane_Type = planeType AND Flight_Number = flightNumber;
	
	IF (currentPlaneSeatsTaken = totalPlaneSeats) THEN
		SELECT MAX(Plane_Capacity) INTO biggestPlaneSeats
		FROM PLANECAPACITY
		WHERE Plane_Type = planeType AND Weekly_Schedule = weeklySchedule;
	
		SELECT Plane_Type INTO newPlane
		FROM PLANECAPACITY
		WHERE Plane_Capacity = biggestPlaneSeats AND Plane_Type = planeType AND Weekly_Schedule = weeklySchedule;
		
		UPDATE FLIGHT
		SET Plane_Type = newPlane
		WHERE Flight_Number = flightNumber;
	END IF;
END;
/

--Trigger for canceling reservations, however plane switch based on amount of seats taken on a flight not implemented
CREATE OR REPLACE TRIGGER cancelReservation
AFTER UPDATE
ON OUR_DATE
FOR EACH ROW
DECLARE currentDate DATE;
BEGIN
	SELECT C_Date INTO currentDate
	From OUR_DATE;
	
	DELETE FROM RESERVATION
	WHERE Ticketed = 'N' AND Reservation_Number IN 
	(
		SELECT Reservation_Number
		FROM RESERVATIONSPLUSFLIGHTS rpf
		WHERE currentDate - TO_DATE(TO_CHAR(rpf.Flight_Date, 'DD-MM-YY') || ' ' || rpf.Departure_Time, 'DD-MM-YY HH24:MI') <= '12'
	);
	
END;
/