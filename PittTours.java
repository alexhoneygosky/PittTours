import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import oracle.jdbc.driver.OracleDriver;

public class PittTours {
    public static void addNewCustomer(Scanner s, Statement statement) {
        ResultSet resultSet;

        Scanner tempScanner = new Scanner(System.in).useDelimiter(" ");

        System.out.println("---------------");
        System.out.print("Title (Mr, Ms, Mrs): ");
        String title = s.next();
        System.out.print("First Name: ");
        String fname = s.next();
        System.out.print("Last Name: ");
        String lname = s.next();
        System.out.print("Street Address: ");
        String street = tempScanner.nextLine();
        System.out.print("City: ");
        String city = s.next();
        System.out.print("State: ");
        String state = s.next();
        System.out.print("Phone Number: ");
        String phone = s.next();
        System.out.print("Email: ");
        String email = s.next();
        System.out.print("Credit Card Number: ");
        String ccNumber = s.next();
        System.out.print("Credit Card Exp. Date: ");
        String ccExpDate = s.next();

        String customerQuery = "SELECT first_name, last_name FROM CUSTOMER WHERE first_name = '" + fname + "' AND last_name = '" + lname + "'";

        try {
            resultSet = statement.executeQuery(customerQuery);

            if(resultSet.next() == true) {
                System.out.println("That customer already exists!");
            }

            else {
                //get number of rows in customer table to calculate cid
                String getAllCustomersQuery = "SELECT * FROM CUSTOMER";

                resultSet = statement.executeQuery(getAllCustomersQuery);

                int numRows = 0;

                while(resultSet.next()) {
                    ++numRows;
                }

                int cidValue = numRows + 1;

                String customerInsert = "INSERT INTO CUSTOMER VALUES('" + cidValue + "', '" + title + "', '" + fname + "', '" + lname + "', '" + ccNumber
                    + "', To_Date('" + ccExpDate + "', 'MM-YY'), '" + street + "', '" + city + "', '" + state + "', '" + phone + "', '" + email + "', '" + "0')";
                statement.executeUpdate(customerInsert);

                String confirmNewCustomerQuery = "SELECT CID FROM CUSTOMER WHERE CID = '" + cidValue + "'";
                resultSet = statement.executeQuery(confirmNewCustomerQuery);
                
                if(resultSet.next() == true) {
                    System.out.println("New customer " + cidValue + " added!");
                }
            }            
        } catch (SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();
        } catch (NullPointerException npe) {
            System.out.println("Null pointer.");
        }
    }

	//Administrator option to erase entire database
	public static boolean eraseDB(Statement statement){
		System.out.println("Erasing database...");
		String procedure = new String("EXEC erase_db;");
		try{
			statement.execute(procedure);
			System.out.println("Finished.");
			return true;
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();
		}
		return false;
	}
	
	//Administrator option to load airline information from a file
	public static void loadAirline(Statement statement){
		ResultSet rs;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the file to load airline information from:");
		String file = s.next();
		String line = null;
		try{
			// Insert into database from file
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				String[] tuple = line.split(",");
				String procedure = new String("EXEC add_airline ('"+ tuple[0] + "','"+tuple[1]
				+ "','" + tuple[2] + "'," + tuple[3] + ");");
				statement.execute(procedure);
			}
			// Display table
			String query = new String("SELECT * FROM AIRLINE;");
			rs = statement.executeQuery(query);
			System.out.println("Airlines");
			System.out.println("Id\tName\tAbbreviation\tYear Founded");
			while (rs.next()){
				String id = rs.getString("airline_id");
				String name = rs.getString("airline_name");
				String abrv = rs.getString("airline_abbreviation");
				int year = rs.getInt("year_founded");
				System.out.println(id+"\t"+name+"\t"+abrv+"\t"+year);
			}
		}
		catch (FileNotFoundException e){
			System.out.println("ERROR: Unable to open file: "+file);
		}
		catch (IOException e){
			System.out.println("ERROR: Unable to read file: "+file);
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();		
		}
	}
	
	//Load airline method for driver
	private boolean loadAirline(Statement statement, String file){
		ResultSet rs;
		String line = null;
		try{
			// Insert into database from file
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				String[] tuple = line.split(",");
				String procedure = new String("EXEC add_airline ('"+ tuple[0] + "','"+tuple[1]
				+ "','" + tuple[2] + "'," + tuple[3] + ");");
				statement.execute(procedure);
			}
			// Display table
			String query = new String("SELECT * FROM AIRLINE;");
			rs = statement.executeQuery(query);
			System.out.println("Airlines");
			System.out.println("Id\tName\tAbbreviation\tYear Founded");
			while (rs.next()){
				String id = rs.getString("airline_id");
				String name = rs.getString("airline_name");
				String abrv = rs.getString("airline_abbreviation");
				int year = rs.getInt("year_founded");
				System.out.println(id+"\t"+name+"\t"+abrv+"\t"+year);
			}
			return true;
		}
		catch (FileNotFoundException e){
			System.out.println("ERROR: Unable to open file: "+file);
			return false;
		}
		catch (IOException e){
			System.out.println("ERROR: Unable to read file: "+file);
			return false;
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();	
            return false;	
		}
	}
	
	//Administrator option to load schedule from a file
	public static void loadSchedule(Statement statement){
		ResultSet rs;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the file to load schedule information from:");
		String file = s.next();
		String line = null;
		try{
			//Insert into db from file
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				String[] tuple = line.split(",");
				String procedure = new String("EXEC add_flight ('"+ tuple[0] + "','"+tuple[1]
				+ "','" + tuple[2] + "','" + tuple[3] + "','" + tuple[4] + "','" + tuple[5]
				+ "','" + tuple[6] + "','" + tuple[7] + "');");
				statement.execute(procedure);
			}
			//Display db
			String query = new String("SELECT * FROM FLIGHT;");
			rs = statement.executeQuery(query);
			System.out.println("Flights");
			System.out.println("Flight Number\tAirline Id\tPlane Type\tDeparture City"+
			"\tArrival City\tDeparture Time\tArrival Time\tWeekly Schedule");
			while (rs.next()){
				String fNum = rs.getString("flight_number");
				String id = rs.getString("airline_id");
				String plane = rs.getString("plane_type");
				String dCity = rs.getString("departure_city");
				String aCity = rs.getString("arrival_city");
				String dTime = rs.getString("departure_time");
				String aTime = rs.getString("arrival_time");
				String schedule = rs.getString("weekly_schedule");

				System.out.println(fNum+"\t"+id+"\t"+plane+"\t"+dCity + "\t" + aCity + "\t"
				+ dTime + "\t" + aTime + "\t" + schedule);
			}
		}
		catch (FileNotFoundException e){
			System.out.println("ERROR: Unable to open file: "+file);
		}
		catch (IOException e){
			System.out.println("ERROR: Unable to read file: "+file);
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();		
		}
	}
	
	private boolean loadSchedule(Statement statement, String file){
		ResultSet rs;
		String line = null;
		try{
			//Insert into db from file
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				String[] tuple = line.split(",");
				String procedure = new String("EXEC add_flight ('"+ tuple[0] + "','"+tuple[1]
				+ "','" + tuple[2] + "','" + tuple[3] + "','" + tuple[4] + "','" + tuple[5]
				+ "','" + tuple[6] + "','" + tuple[7] + "');");
				statement.execute(procedure);
			}
			//Display db
			String query = new String("SELECT * FROM FLIGHT;");
			rs = statement.executeQuery(query);
			System.out.println("Flights");
			System.out.println("Flight Number\tAirline Id\tPlane Type\tDeparture City"+
			"\tArrival City\tDeparture Time\tArrival Time\tWeekly Schedule");
			while (rs.next()){
				String fNum = rs.getString("flight_number");
				String id = rs.getString("airline_id");
				String plane = rs.getString("plane_type");
				String dCity = rs.getString("departure_city");
				String aCity = rs.getString("arrival_city");
				String dTime = rs.getString("departure_time");
				String aTime = rs.getString("arrival_time");
				String schedule = rs.getString("weekly_schedule");

				System.out.println(fNum+"\t"+id+"\t"+plane+"\t"+dCity + "\t" + aCity + "\t"
				+ dTime + "\t" + aTime + "\t" + schedule);
			}
			return true;
		}
		catch (FileNotFoundException e){
			System.out.println("ERROR: Unable to open file: "+file);
			return false;
		}
		catch (IOException e){
			System.out.println("ERROR: Unable to read file: "+file);
			return false;
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();	
            return false;	
		}
	}
	
	//Administrator option to load pricing from a file
	public static void loadPrice(Statement statement){
		ResultSet rs;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the file to load price information from:");
		String file = s.next();
		String line = null;
		try{
			//Insert into db from file
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				String[] tuple = line.split(",");
				String procedure = new String("EXEC add_price ('"+ tuple[0] + "','"+tuple[1]
				+ "','" + tuple[2] + "'," + tuple[3] + "," + tuple[4] + ");");
				statement.execute(procedure);
			}
			// Display db
			String query = new String("SELECT * FROM PRICE;");
			rs = statement.executeQuery(query);
			System.out.println("Prices");
			System.out.println("Departure City\tArrival City\tAirline Id\tHigh Price\tLow Price");
			while (rs.next()){
				String dCity = rs.getString("departure_city");
				String aCity = rs.getString("arrival_city");
				String id = rs.getString("airline_id");
				int high = rs.getInt("high_price");
				int low = rs.getInt("low_price");

				System.out.println(dCity + "\t" + aCity + "\t" + id + "\t" + high + "\t" + low);
			}
		}
		catch (FileNotFoundException e){
			System.out.println("ERROR: Unable to open file: "+file);
		}
		catch (IOException e){
			System.out.println("ERROR: Unable to read file: "+file);
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();		
		}
	}
	
	//Administrator option to change a price
	public static void changePrice(Statement statement){
		ResultSet resultSet;
		Scanner s = new Scanner(System.in);
		System.out.println("Update Price");
		//Get info from user
		System.out.println("Enter departure city: ");
		String depart = s.nextLine();
		System.out.println("\nEnter arrival city: ");
		String arrival = s.nextLine();
		System.out.println("\nEnter airline id: ");
		String airline = s.nextLine();
		System.out.println("\nEnter high price: ");
		int high = s.nextInt();
		System.out.println("\nEnter low price: ");
		int low = s.nextInt();
		String procedure = new String("EXEC change_price ('" + depart + "','" + arrival + "','" + airline + "',"
		+ high + "," + low + " );");
		//Update the table with new prices
		try{
			statement.execute(procedure);	
			String confirmPriceChange = new String("SELECT * FROM PRICE WHERE DEPARTURE_CITY = '" 
			+ depart + "' AND ARRIVAL_CITY = '"+arrival+"';");
            resultSet = statement.executeQuery(confirmPriceChange);
            if(resultSet.next() == true) {
            	System.out.println("Price successfully updated.");
            }
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();		
		}
	}
	
	//Administrator option to load plane information from a file
	public static void loadPlane(Statement statement){
		ResultSet rs;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the file to load plane information from:");
		String file = s.next();
		String line = null;
		try{
			//Insert into db from file
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				String[] tuple = line.split(",");
				String procedure = new String("EXEC add_plane ('"+ tuple[0] + "','"+tuple[1]
				+ "'," + tuple[2] + ",to_date('" + tuple[3] + "','MM-DD-YYYY')," + tuple[4] + ",'" + tuple[5] + "');");
				statement.execute(procedure);
			}
			//Display db
			String query = new String("SELECT * FROM PLANE;");
			rs = statement.executeQuery(query);
			System.out.println("Planes");
			System.out.println("Type\tManufacturer\tCapacity\tLast Service Date\tYear\tOwner Id");
			while (rs.next()){
				String type = rs.getString("plane_type");
				String man = rs.getString("manufacture");
				int capacity = rs.getInt("plane_capacity");
				String service = rs.getString("last_service");
				int year= rs.getInt("year");
				String id = rs.getString("owner_id");

				System.out.println(type + "\t" + man + "\t" + capacity + "\t" + service + "\t" + year + "\t" + id);
			}
		}
		catch (FileNotFoundException e){
			System.out.println("ERROR: Unable to open file: "+file);
		}
		catch (IOException e){
			System.out.println("ERROR: Unable to read file: "+file);
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();		
		}
	}
	
	//Administrator option to get manifest for a specific flight
	public static void getManifest(Statement statement){
		ResultSet rs;
		Scanner s = new Scanner(System.in);
		System.out.println("Get Manifest");
		System.out.println("Enter the date (MM-DD-YYYY):");
		String date = s.next();
		System.out.println("\nEnter the flight number:");
		String flightNum = s.next();
		String query = new String("select C.salutation, C.first_name, C.last_name\n"+
		"from Customer C\n"+
		"join\n" +
		"(select Res.cid\n"+
		"from (select R.cid, Rd.flight_date, Rd.flight_number\n"+
		"from Reservation R\n"+
		"join\n"+
		"Reservation_detail Rd\n"+
		"on R.reservation_number = Rd.reservation_number) Res\n"+
		"where (Res.flight_number = '"+flightNum+ "' and Res.flight_date = to_Date('"+date+"','MM-DD-YYYY'))) RR\n"+
		"on C.cid = RR.cid;");
		System.out.println(query);
		try{
			rs = statement.executeQuery(query);
			while(rs.next()){
				String salutation = rs.getString("salutation");
				String f_name = rs.getString("first_name");
				String l_name = rs.getString("last_name");
				System.out.println(salutation + ". " + f_name + " " +l_name);
			}
		}
		catch(SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();	
		}
	}
	
    public static void showCustomerInfo(Scanner s, Statement statement) {
        ResultSet resultSet;

        System.out.println("---------------");
        System.out.print("Customer First Name: ");
        String fname = s.next();
        System.out.print("Customer Last Name: ");
        String lname = s.next();

        String getCustomerInfoQuery = "SELECT salutation, first_name, last_name, street, city, state, phone, email, frequent_miles FROM CUSTOMER WHERE "
            + "first_name = '" + fname + "' AND last_name = '" + lname + "'";

        try {
            resultSet = statement.executeQuery(getCustomerInfoQuery);

            resultSet.next();
            System.out.println("Customer Information");
            System.out.println("Salutation: " + resultSet.getString("salutation"));
            System.out.println("First Name: " + resultSet.getString("first_name"));
            System.out.println("Last Name: " + resultSet.getString("last_name"));
            System.out.println("Street: " + resultSet.getString("street"));
            System.out.println("City: " + resultSet.getString("city"));
            System.out.println("State: " + resultSet.getString("state"));
            System.out.println("Phone: " + resultSet.getString("phone"));
            System.out.println("Email: " + resultSet.getString("email"));
            System.out.println("Frequent Miles: " + resultSet.getString("frequent_miles"));                                    
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();
        }  
    }

    public static void findFlightPriceBetweenCities(Scanner s, Statement statement) {
        ResultSet resultSet;

        System.out.println("---------------");
        System.out.print("First City (PIT, MCO, JFK, etc.): ");
        String cityOne = s.next();
        System.out.print("Second City (PIT, MCO, JFK, etc.): ");
        String cityTwo = s.next();

        String findFlightPricesBetweenCitiesQuery = "SELECT departure_city, arrival_city, high_price, low_price FROM PRICE WHERE "
        + "(departure_city = '" + cityOne + "' AND arrival_city = '" + cityTwo + "') OR (departure_city = '" + cityTwo + "' AND arrival_city = '" + cityOne 
        + "')";

        try {
            resultSet = statement.executeQuery(findFlightPricesBetweenCitiesQuery);

            System.out.println("Departure City | Arrival City | High Price | Low Price");
            while(resultSet.next() != false) {
                System.out.println("\t" + resultSet.getString("departure_city") + "\t\t" + resultSet.getString("arrival_city") + "\t  " 
                    + resultSet.getString("high_price") + "\t\t" + resultSet.getString("low_price"));
            }

            System.out.println("NOTE: If you take a round trip flight in the same day, the high price is used.\nOtherwise, the low price is used");
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();            
        }
    }

    public static void findAllRoutesBetweenCities(Scanner s, Statement statement) {
        ResultSet resultSet;

        System.out.println("---------------");
        System.out.print("First City (PIT, MCO, JFK, etc.): ");
        String cityOne = s.next();
        System.out.print("Second City (PIT, MCO, JFK, etc.): ");
        String cityTwo = s.next();

        String findAllDirectRoutesBetweenCitiesQuery = "SELECT flight_number, departure_city, arrival_city, departure_time, arrival_time FROM FLIGHT "
            + "WHERE departure_city = '" + cityOne + "' AND arrival_city = '" + cityTwo + "'";
            
        String findAllConnectingRoutesBetweenCitiesQuery = "SELECT flight_number, departure_city, arrival_city, departure_time, arrival_time, weekly_schedule "
            + "FROM FLIGHT WHERE (departure_city = '" + cityOne + "' AND arrival_city = '" + cityTwo + "') OR departure_city = '" + cityTwo + "'";

        try {
            resultSet = statement.executeQuery(findAllDirectRoutesBetweenCitiesQuery);

            System.out.println("Direct Routes");
            System.out.println("Flight Number | Departure City | Arrival City | Departure Time | Arrival Time");
            while(resultSet.next() != false) {
                System.out.println("\t" + resultSet.getString("flight_number") + "\t\t" + resultSet.getString("departure_city") + "\t\t"
                    + resultSet.getString("arrival_city") + "\t\t" + resultSet.getString("departure_time") + "\t\t" + resultSet.getString("arrival_time"));
            }

            resultSet = statement.executeQuery(findAllConnectingRoutesBetweenCitiesQuery);
            System.out.println();
            System.out.println("Connecting Routes (including first leg)");
            System.out.println("Flight Number | Departure City | Arrival City | Departure Time | Arrival Time");

            int firstLegArrivalTime = 0;
            String firstLegDaysOfOperation = "";

            while(resultSet.next() != false) {
                if(resultSet.getString("departure_city").equals(cityOne)) {
                    firstLegArrivalTime = Integer.parseInt(resultSet.getString("arrival_time"));
                    firstLegDaysOfOperation = resultSet.getString("weekly_schedule");

                    System.out.println(firstLegDaysOfOperation);

                    System.out.println("\t" + resultSet.getString("flight_number") + "\t\t" + resultSet.getString("departure_city") + "\t\t"
                        + resultSet.getString("arrival_city") + "\t\t" + resultSet.getString("departure_time") + "\t\t" 
                        + resultSet.getString("arrival_time"));                    
                }
                
                else if(!resultSet.getString("departure_city").equals(cityOne)) {
                    int timeDifference = Integer.parseInt(resultSet.getString("departure_time")) - firstLegArrivalTime;
                    String currentRowDaysOfOperation = resultSet.getString("weekly_schedule");
                    int daysOverlap = 0;

                    for(int i = 0; i < firstLegDaysOfOperation.length(); i++) {
                        if(currentRowDaysOfOperation.charAt(i) == firstLegDaysOfOperation.charAt(i) && firstLegDaysOfOperation.charAt(i) != '-') {
                            daysOverlap++;
                        }
                    }

                    if(timeDifference >= 100 && daysOverlap >= 1) {
                        System.out.println("\t" + resultSet.getString("flight_number") + "\t\t" + resultSet.getString("departure_city") + "\t\t"
                            + resultSet.getString("arrival_city") + "\t\t" + resultSet.getString("departure_time") + "\t\t" 
                            + resultSet.getString("arrival_time"));
                    }
                }
            }            
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();            
        }        
    }

    public static void findAirlineRoutesBetweenCities(Scanner s, Statement statement) {
        ResultSet resultSet;
        Scanner tempScanner = new Scanner(System.in).useDelimiter(" ");

        System.out.println("---------------");
        System.out.print("First City (PIT, MCO, JFK, etc.): ");
        String cityOne = s.next();
        System.out.print("Second City (PIT, MCO, JFK, etc.): ");
        String cityTwo = s.next();
        System.out.print("Airline Name: ");
        String airline = tempScanner.nextLine();

        String findAirlineIdQuery = "SELECT airline_id FROM AIRLINE WHERE airline_name = '" + airline + "'";

        try {
            resultSet = statement.executeQuery(findAirlineIdQuery);

            resultSet.next();
            String airlineId = resultSet.getString("airline_id");

            String findAirlineDirectRoutesBetweenCitiesQuery = "SELECT airline_id, flight_number, departure_city, arrival_city, departure_time, arrival_time FROM FLIGHT "
                + "WHERE departure_city = '" + cityOne + "' AND arrival_city = '" + cityTwo + "' AND airline_id = '" + airlineId + "'";
            
            String findAirlineConnectingRoutesBetweenCitiesQuery = "SELECT airline_id, flight_number, departure_city, arrival_city, departure_time, arrival_time, weekly_schedule "
                + "FROM FLIGHT WHERE ((departure_city = '" + cityOne + "' AND arrival_city = '" + cityTwo + "') OR departure_city = '" + cityTwo + "') AND airline_id = '"
                + airlineId + "'";
            
            resultSet = statement.executeQuery(findAirlineDirectRoutesBetweenCitiesQuery);

            System.out.println("Direct Routes");
            System.out.println("Airline Id | Flight Number | Departure City | Arrival City | Departure Time | Arrival Time");

            while(resultSet.next() != false) {
                System.out.println("\t" + resultSet.getString("airline_id") + "\t\t" + resultSet.getString("flight_number") + "\t\t" 
                    + resultSet.getString("departure_city") + "\t\t" + resultSet.getString("arrival_city") + "\t\t" 
                    + resultSet.getString("departure_time") + "\t\t" + resultSet.getString("arrival_time"));                
            }

            resultSet = statement.executeQuery(findAirlineConnectingRoutesBetweenCitiesQuery);

            System.out.println();
            System.out.println("Connecting Routes (including first leg)");
            System.out.println("Airline Id | Flight Number | Departure City | Arrival City | Departure Time | Arrival Time");

            int firstLegArrivalTime = 0;
            String firstLegDaysOfOperation = "";

            while(resultSet.next() != false) {
                if(resultSet.getString("departure_city").equals(cityOne)) {
                    firstLegArrivalTime = Integer.parseInt(resultSet.getString("arrival_time"));
                    firstLegDaysOfOperation = resultSet.getString("weekly_schedule");

                    System.out.println(firstLegDaysOfOperation);

                    System.out.println("\t" + resultSet.getString("airline_id") + "\t\t" + resultSet.getString("flight_number") + "\t\t" 
                        + resultSet.getString("departure_city") + "\t\t" + resultSet.getString("arrival_city") + "\t\t" 
                        + resultSet.getString("departure_time") + "\t\t" + resultSet.getString("arrival_time"));     
                }
                
                else if(!resultSet.getString("departure_city").equals(cityOne)) {
                    int timeDifference = Integer.parseInt(resultSet.getString("departure_time")) - firstLegArrivalTime;
                    String currentRowDaysOfOperation = resultSet.getString("weekly_schedule");
                    int daysOverlap = 0;

                    for(int i = 0; i < firstLegDaysOfOperation.length(); i++) {
                        if(currentRowDaysOfOperation.charAt(i) == firstLegDaysOfOperation.charAt(i) && firstLegDaysOfOperation.charAt(i) != '-') {
                            daysOverlap++;
                        }
                    }

                    if(timeDifference >= 100 && daysOverlap >= 1) {
                        System.out.println("\t" + resultSet.getString("airline_id") + "\t\t" + resultSet.getString("flight_number") + "\t\t" 
                            + resultSet.getString("departure_city") + "\t\t" + resultSet.getString("arrival_city") + "\t\t" 
                            + resultSet.getString("departure_time") + "\t\t" + resultSet.getString("arrival_time"));     
                    }
                }
            }      
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();             
        }
    }

    //does not work completely
    public static void findAllRoutesBetweenCitiesWithAvailableSeats(Scanner s, Statement statement) {
        ResultSet resultSet, seatsTakenResultSet;

        System.out.println("---------------");
        System.out.print("First City (PIT, MCO, JFK, etc.): ");
        String cityOne = s.next();
        System.out.print("Second City (PIT, MCO, JFK, etc.): ");
        String cityTwo = s.next();
        System.out.print("Date: ");
        String stringDate = s.next();

        SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");

        try {
            Date date = formatter.parse(stringDate);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            String findAllFlightsOnDate = "";
            String flightNum = "";
            int planeCapacity = 0;
            String resFlightNum = "";
            int bookedSeats = 0;

            if(dayOfWeek == 0) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE 'S______'";
            }
            else if(dayOfWeek == 1) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '_M_____'";            
            }
            else if(dayOfWeek == 2) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '__T____'";               
            }
            else if(dayOfWeek == 3) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '___W___'";               
            }
            else if(dayOfWeek == 4) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '____T__'";               
            }
            else if(dayOfWeek == 5) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '_____F_'";               
            }
            else if(dayOfWeek == 6) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '______S'";               
            }

            try {
                resultSet = statement.executeQuery(findAllFlightsOnDate);

                while(resultSet.next() != false) {
                    //System.out.println(resultSet.getString("flight_number") + " " + resultSet.getString("plane_type") + " " + resultSet.getString("plane_capacity") + " " + resultSet.getString("weekly_schedule"));
                    String findAllSeatsTakenPerFlightQuery = "SELECT flight_number, COUNT(flight_number) FROM RESERVATION_DETAIL rd GROUP BY rd.flight_number";

                    seatsTakenResultSet = statement.executeQuery(findAllSeatsTakenPerFlightQuery);

                    while(seatsTakenResultSet.next() != false) {
                        if(resultSet.getString("flight_number").equals(seatsTakenResultSet.getString("flight_number")) && Integer.parseInt(seatsTakenResultSet.getString("taken_seats")) < Integer.parseInt(resultSet.getString("plane_capacity"))) {
                            System.out.println(resultSet.getString("flight_number") + " " + resultSet.getString("departure_city") + " " + resultSet.getString("arrival_city") + " " + resultSet.getString("departure_time") + " " + resultSet.getString("arrival_time"));
                        }

                        //System.out.println(seatsTakenResultSet.getString("flight_number") + " " + seatsTakenResultSet.getString("taken_seats"));
                    }                
                }
            }  catch(SQLException sqle) {
                System.out.println("Result set failed");
                System.out.println(sqle.toString());
                sqle.printStackTrace();             
            }            
        } catch(ParseException pe) {
            System.out.println("Parse exception.");
            System.exit(1);
        }
    }

    //does not work completely
    public static void findAirlineRoutesBetweenCitiesWithAvailableSeats(Scanner s, Statement statement) {
        ResultSet resultSet, seatsTakenResultSet;
        Scanner tempScanner = new Scanner(System.in).useDelimiter(" ");

        System.out.println("---------------");
        System.out.print("First City (PIT, MCO, JFK, etc.): ");
        String cityOne = s.next();
        System.out.print("Second City (PIT, MCO, JFK, etc.): ");
        String cityTwo = s.next();
        System.out.print("Airline: ");
        String airline = tempScanner.nextLine();
        System.out.print("Date: ");
        String stringDate = s.next();

        SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");

        String findAirlineIdQuery = "SELECT airline_id FROM AIRLINE WHERE airline_name = '" + airline + "'";

        try {
            resultSet = statement.executeQuery(findAirlineIdQuery);

            resultSet.next();
            String airlineId = resultSet.getString("airline_id");            

            try {
                Date date = formatter.parse(stringDate);

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);



                String findAllFlightsOnDate = "";
                String flightNum = "";
                int planeCapacity = 0;
                String resFlightNum = "";
                int bookedSeats = 0;

                if(dayOfWeek == 0) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE 'S______'";
                }
                else if(dayOfWeek == 1) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '_M_____'";            
                }
                else if(dayOfWeek == 2) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '__T____'";               
                }
                else if(dayOfWeek == 3) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '___W___'";               
                }
                else if(dayOfWeek == 4) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '____T__'";               
                }
                else if(dayOfWeek == 5) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '_____F_'";               
                }
                else if(dayOfWeek == 6) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '______S'";               
                }

                try {
                    resultSet = statement.executeQuery(findAllFlightsOnDate);

                    while(resultSet.next() != false) {
                        //System.out.println(resultSet.getString("flight_number") + " " + resultSet.getString("plane_type") + " " + resultSet.getString("plane_capacity") + " " + resultSet.getString("weekly_schedule"));
                        String findAllSeatsTakenPerFlightQuery = "SELECT flight_number, COUNT(flight_number) FROM RESERVATION_DETAIL rd GROUP BY rd.flight_number";

                        seatsTakenResultSet = statement.executeQuery(findAllSeatsTakenPerFlightQuery);

                        while(seatsTakenResultSet.next() != false) {
                            if(resultSet.getString("flight_number").equals(seatsTakenResultSet.getString("flight_number")) && Integer.parseInt(seatsTakenResultSet.getString("taken_seats")) < Integer.parseInt(resultSet.getString("plane_capacity"))) {
                                System.out.println(resultSet.getString("flight_number") + " " + resultSet.getString("departure_city") + " " + resultSet.getString("arrival_city") + " " + resultSet.getString("departure_time") + " " + resultSet.getString("arrival_time"));
                            }

                            //System.out.println(seatsTakenResultSet.getString("flight_number") + " " + seatsTakenResultSet.getString("taken_seats"));
                        }                
                    }
                }  catch(SQLException sqle) {
                    System.out.println("Result set failed");
                    System.out.println(sqle.toString());
                    sqle.printStackTrace();             
                }            
            } catch(ParseException pe) {
                System.out.println("Parse exception.");
                System.exit(1);
            }
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();               
        }
    }

    //does not work completely
    public static void addReservation(Scanner s, Statement statement) {
        ResultSet resultSet, resultSetTwo;
        System.out.println("---------------");
        String[] flights = new String[4];
        String[] depDates = new String[4];
        int tripLegs = 4;
        int index = 0;

        int allGoodReservations = 0;

        while(tripLegs != 0 || !s.next().equals(0)) {
            System.out.print("Flight Number: ");
            flights[index] = s.next();
            System.out.print("Departure Date: ");
            depDates[index] = s.next();
        }

        for(int i = 0; i < flights.length; i++) {
            String findBookedSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL GROUP BY flight_number";
            String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type";

            try {
                resultSet = statement.executeQuery(findBookedSeats);
                resultSetTwo = statement.executeQuery(findTotalSeats);

                while(resultSetTwo.next() != false) {
                    while(resultSet.next() != false) {
                        if(resultSet.getString("flight_number").equals(resultSetTwo.getString("flight_number")) && Integer.parseInt(resultSet.getString("taken_seats")) < Integer.parseInt(resultSetTwo.getString("plane_capacity"))) {
                            allGoodReservations++;
                        }
                    }
                }

                if(allGoodReservations == flights.length) {
                    //insert into reservation_detail and reservation 
                }

                else {
                    System.out.println("Parts of this reservation request cannot be fulfilled.");
                }
            } catch(SQLException sqle) {
                System.out.println("Result set failed");
                System.out.println(sqle.toString());
                sqle.printStackTrace();                   
            }
        }
    }

    public static void showReservationInfo(Scanner s, Statement statement) {
        ResultSet resultSet;

        System.out.println("---------------");
        System.out.print("Reservation Number: ");
        String reservationNum = s.next();   

        String findReservationQuery = "SELECT * FROM RESERVATION_DETAIL WHERE reservation_number = '" + reservationNum + "'";

        try {
            resultSet = statement.executeQuery(findReservationQuery);

            System.out.println("Reservation # | Flight # | Flight Date | Leg");
            while(resultSet.next() != false) {
                System.out.println("\t" + resultSet.getString("reservation_number") + "         " + resultSet.getString("flight_number") + "        " 
                    + resultSet.getString("flight_date").substring(0,10) + "     " + resultSet.getString("leg"));
            }
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();              
        }
    }

    public static void buyTicket(Scanner s, Statement statement) {
        ResultSet resultSet;

        System.out.println("---------------");
        System.out.print("Reservation Number: ");
        String reservationNum = s.next();

        String updateReservation = "UPDATE RESERVATION SET ticketed = 'Y' WHERE reservation_number = '" + reservationNum + "'";

        try {
            statement.executeUpdate(updateReservation);
            System.out.println("Ticketing complete!");
        } catch(SQLException sqle) {
            System.out.println("Ticket update failed due to possible nonexistence of reservation");
            System.out.println(sqle.toString());
            sqle.printStackTrace();             
        }
    }

    public static void main (String[] args) {
        Connection connection;
        Statement statement = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass"; 
        
            connection = DriverManager.getConnection(url, "ach53", "drumline617+"); 

            statement = connection.createStatement();
        } catch(Exception Ex) {
            System.out.println("Error connecting to database.  Machine Error: " +
            Ex.toString());
            Ex.printStackTrace();
        }
		if (args[0] = "-d"){
			driver(statement);
		}
		else if (args[0] = "-b"){
			benchmark(statement);
		}
        Scanner s = new Scanner(System.in);
        
        System.out.println("Welcome to PittTours!");
        System.out.println("Choose an option below");
        System.out.println("1) Administrator Interface");
        System.out.println("2) Customer Interface");
        int choice = s.nextInt();
        
        if (choice == 1){
        	System.out.println("---------------");
            System.out.println("1) Erase the database");
            System.out.println("2) Load Airline Information");
            System.out.println("3) Load Schedule Information");
            System.out.println("4) Load Pricing Information");
            System.out.println("5) Change Pricing Information");
            System.out.println("6) Load Plane Information");
            System.out.println("7) Generate Passenger Manifest");
            System.out.println("8) Quit PittTours");
            
            int custInterfaceOption = s.nextInt();
            
            switch(custInterfaceOption){
            	case 1:
            		eraseDB(statement);
            		break;
            	case 2:
            		loadAirline(statement);
            		break;
            	case 3:
            		loadSchedule(statement);
            		break;
            	case 4:
            		loadPrice(statement);
            		break;
            	case 5:
            		changePrice(statement);
            		break;
            	case 6:
            		loadPlane(statement);
            		break;
            	case 7:
            		getManifest(statement);
            		break;
            	case 8:
            		System.out.println("Thank you for using PittTours!");
                    System.exit(0);
            	
            }
        }

        else if(choice == 2) {
            System.out.println("---------------");
            System.out.println("Customer Interface");
            System.out.println("1) Add Customer");
            System.out.println("2) Show Customer Info");
            System.out.println("3) Find Flight Price Between Cities");
            System.out.println("4) Find All Routes Between Cities");
            System.out.println("5) Find Airline Routes Between Cities");
            System.out.println("6) Find Available Seats for All Routes on a Given Day");
            System.out.println("7) Find Available Seats for Airline Routes on a Given Day");
            System.out.println("8) Add Reservation");
            System.out.println("9) Show Reservation Info");
            System.out.println("10) Buy Ticket");
            System.out.println("11) Quit PittTours");

            int custInterfaceOption = s.nextInt();

            switch (custInterfaceOption) {
                case 1:
                    addNewCustomer(s, statement);
                    break;
                case 2:
                    showCustomerInfo(s, statement);
                    break;
                case 3:
                    findFlightPriceBetweenCities(s, statement);
                    break;
                case 4:
                    findAllRoutesBetweenCities(s, statement);
                    break;
                case 5:
                    findAirlineRoutesBetweenCities(s, statement);
                    break;
                case 6:
                    findAllRoutesBetweenCitiesWithAvailableSeats(s, statement);
                    break;
                case 7:
                    findAirlineRoutesBetweenCitiesWithAvailableSeats(s, statement);
                    break;
                case 8:
                    addReservation(s, statement);
                    break;
                case 9:
                    showReservationInfo(s, statement);
                    break;
                case 10:
                    buyTicket(s, statement);
                    break;
                case 11:
                    System.out.println("Thank you for using PittTours!");
                    System.exit(0);
            }
        }
    }
    public static void driver(Statement statement){
    	String airlines = new String("airlinesTest.csv");
    	String planes = new String("planesTest.csv");
    	String prices = new String("pricesTest.csv");
    	String schedule = new String ("scheduleTest.csv");
    	System.out.println("Testing Administrator Options");
    	System.out.println("1. Erase the database");
    	if(eraseDB(statement))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with erasing the database.");
    		
    	System.out.println("2. Load airline information from file");
    	if (loadAirline(statement, airlines))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading airline info");
    		
    	System.out.println("3. Load plane information from file");
    	if (loadPlane(statement, planes))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading plane info");
    		
    	System.out.println("4. Load schedule information from file");
    	if (loadPlane(statement, schedule))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading schedule info");
    	
    	System.out.println("5. Load price information from file");
    	if (loadPlane(statement, prices))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading price info");
    		
    	System.out.println("6. Change price");
    	if (loadPlane(statement, planes))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading plane info");
    		
    	System.out.println("7. Get manifest");
    	if (loadPlane(statement, prices))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading price info");
    	
    	System.exit(0);
    	
    }
}