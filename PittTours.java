import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import oracle.jdbc.driver.OracleDriver;

public class PittTours {
    

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
	public static boolean loadAirline(Statement statement, String file){
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
	
	//Testing method for load schedule that doesn't take input
	public static boolean loadSchedule(Statement statement, String file){
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
	
	// Testing method for load price that doesn't take input
	public static boolean loadPrice(Statement statement, String file){
		ResultSet rs;
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
	
	//Testing method for change price that doesn't take input
	public static boolean changePrice(Statement statement, String d_city, String a_city, String a_id, int high, int low){
		ResultSet resultSet;
		String procedure = new String("EXEC change_price ('" + d_city + "','" + a_city + "','" + a_id + "',"
		+ high + "," + low + " );");
		//Update the table with new prices
		try{
			statement.execute(procedure);	
			String confirmPriceChange = new String("SELECT * FROM PRICE WHERE DEPARTURE_CITY = '" 
			+ d_city + "' AND ARRIVAL_CITY = '"+a_city+"';");
            resultSet = statement.executeQuery(confirmPriceChange);
            if(resultSet.next() == true) {
            	System.out.println("Price successfully updated.");
            }
            return true;
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();	
            return false;	
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
	
	//Testing method for loadPlane that doesn't take input
	public static boolean loadPlane(Statement statement, String file){
		ResultSet rs;
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
	
	//Testing method for getManifest that doesn't take input
	public static boolean getManifest(Statement statement, String date, String flightNum){
		ResultSet rs;
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
		try{
			rs = statement.executeQuery(query);
			while(rs.next()){
				String salutation = rs.getString("C.salutation");
				String f_name = rs.getString("C.first_name");
				String l_name = rs.getString("C.last_name");
				System.out.println(salutation + ". " + f_name + " " +l_name);
			}
			return true;
		}
		catch(SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();	
            return false;
		}
	}
	
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
    
    //Testing method for adding new customer
    public static boolean addNewCustomer(Statement statement, String title, String fname, String lname, String street, String city,
    	String state, String phone, String email, String ccNumber, String ccExpDate) {
        ResultSet resultSet;

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
            return true;         
        } catch (SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();
            return false;
        } catch (NullPointerException npe) {
            System.out.println("Null pointer.");
            return false;
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
    
    //Testing method for showCustomerInfo that does not require input
    public static boolean showCustomerInfo(Statement statement, String fname, String lname) {
        ResultSet resultSet;
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
            return true;                                  
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();
            return false;
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

	//Testing method for findFlightPriceBetweenCities that does not require input
	public static boolean findFlightPriceBetweenCities(Statement statement, String cityOne, String cityTwo) {
        ResultSet resultSet;
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
            return true;
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();    
            return false;        
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

	//Testing method for findAllRoutesBetweenCities that does not require input
	public static boolean findAllRoutesBetweenCities(Statement statement, String cityOne, String cityTwo) {
        ResultSet resultSet;

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
            return true;          
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();  
            return false;          
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

	//Testing method for findAirlineRoutesBetweenCities that does not require input
	public static boolean findAirlineRoutesBetweenCities(Statement statement, String cityOne, String cityTwo, String airline) {
        ResultSet resultSet;

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
       		return true;    
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();  
            return false;           
        }
    }
    
    public static void findAllRoutesBetweenCitiesWithAvailableSeats(Scanner s, Statement statement, Statement statementTwo) {
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

            if(dayOfWeek == 1) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE 'S______'";
            }
            else if(dayOfWeek == 2) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '_M_____'";            
            }
            else if(dayOfWeek == 3) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '__T____'";               
            }
            else if(dayOfWeek == 4) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '___W___'";               
            }
            else if(dayOfWeek == 5) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '____T__'";               
            }
            else if(dayOfWeek == 6) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '_____F_'";               
            }
            else if(dayOfWeek == 7) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '______S'";               
            }

            try {
                resultSet = statement.executeQuery(findAllFlightsOnDate);

                System.out.println("Flight Number | Dep. City | Arr. City | Dep. Time | Arr. Time");

                while(resultSet.next() != false) {
                    String fNum = resultSet.getString("flight_number");
                    String depCity = resultSet.getString("departure_city");
                    String arrCity = resultSet.getString("arrival_city");
                    String depTime = resultSet.getString("departure_time");
                    String arrTime = resultSet.getString("arrival_time");
                    int capacity = Integer.parseInt(resultSet.getString("plane_capacity"));                    
                    
                    String findAllSeatsTakenPerFlightQuery = "SELECT rd.flight_number, COUNT(rd.flight_number) AS taken_seats FROM RESERVATION_DETAIL rd WHERE rd.flight_number = '" + resultSet.getString("flight_number") + "' GROUP BY rd.flight_number";

                    seatsTakenResultSet = statementTwo.executeQuery(findAllSeatsTakenPerFlightQuery);

                    while(seatsTakenResultSet.next() != false) {
                        if(fNum.equals(seatsTakenResultSet.getString("flight_number")) && Integer.parseInt(seatsTakenResultSet.getString("taken_seats")) < capacity) {
                            System.out.println("     " + fNum + "           " + depCity + "          " + arrCity + "       " + depTime + "        " + arrTime);
                        }
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

	//Testing method for findAllRoutesBetweenCitiesWithAvailableSeats that does not require input
	public static boolean findAllRoutesBetweenCitiesWithAvailableSeats(Statement statement, Statement statementTwo, String cityOne,
		String cityTwo, String stringDate) {
        ResultSet resultSet, seatsTakenResultSet;


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

            if(dayOfWeek == 1) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE 'S______'";
            }
            else if(dayOfWeek == 2) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '_M_____'";            
            }
            else if(dayOfWeek == 3) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '__T____'";               
            }
            else if(dayOfWeek == 4) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '___W___'";               
            }
            else if(dayOfWeek == 5) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '____T__'";               
            }
            else if(dayOfWeek == 6) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '_____F_'";               
            }
            else if(dayOfWeek == 7) {
                findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                    + " AND f.weekly_schedule LIKE '______S'";               
            }

            try {
                resultSet = statement.executeQuery(findAllFlightsOnDate);

                System.out.println("Flight Number | Dep. City | Arr. City | Dep. Time | Arr. Time");

                while(resultSet.next() != false) {
                    String fNum = resultSet.getString("flight_number");
                    String depCity = resultSet.getString("departure_city");
                    String arrCity = resultSet.getString("arrival_city");
                    String depTime = resultSet.getString("departure_time");
                    String arrTime = resultSet.getString("arrival_time");
                    int capacity = Integer.parseInt(resultSet.getString("plane_capacity"));                    
                    
                    String findAllSeatsTakenPerFlightQuery = "SELECT rd.flight_number, COUNT(rd.flight_number) AS taken_seats FROM RESERVATION_DETAIL rd WHERE rd.flight_number = '" + resultSet.getString("flight_number") + "' GROUP BY rd.flight_number";

                    seatsTakenResultSet = statementTwo.executeQuery(findAllSeatsTakenPerFlightQuery);

                    while(seatsTakenResultSet.next() != false) {
                        if(fNum.equals(seatsTakenResultSet.getString("flight_number")) && Integer.parseInt(seatsTakenResultSet.getString("taken_seats")) < capacity) {
                            System.out.println("     " + fNum + "           " + depCity + "          " + arrCity + "       " + depTime + "        " + arrTime);
                        }
                    }                
                }
                return true;
            }  catch(SQLException sqle) {
                System.out.println("Result set failed");
                System.out.println(sqle.toString());
                sqle.printStackTrace();  
                return false;           
            }            
        } catch(ParseException pe) {
            System.out.println("Parse exception.");
            System.exit(1);
            return false;
        }
    }
    
    public static void findAirlineRoutesBetweenCitiesWithAvailableSeats(Scanner s, Statement statement, Statement statementTwo) {
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

                if(dayOfWeek == 1) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE 'S______'";
                }
                else if(dayOfWeek == 2) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '_M_____'";            
                }
                else if(dayOfWeek == 3) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '__T____'";               
                }
                else if(dayOfWeek == 4) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '___W___'";               
                }
                else if(dayOfWeek == 5) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '____T__'";               
                }
                else if(dayOfWeek == 6) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '_____F_'";               
                }
                else if(dayOfWeek == 7) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '______S'";               
                }

                try {
                    resultSet = statement.executeQuery(findAllFlightsOnDate);

                    System.out.println("Airline Id | Flight Number | Dep. City | Arr. City | Dep. Time | Arr. Time");                    

                    while(resultSet.next() != false) {
                        String fNum = resultSet.getString("flight_number");
                        String depCity = resultSet.getString("departure_city");
                        String arrCity = resultSet.getString("arrival_city");
                        String depTime = resultSet.getString("departure_time");
                        String arrTime = resultSet.getString("arrival_time");
                        int capacity = Integer.parseInt(resultSet.getString("plane_capacity"));  

                        String findAllSeatsTakenPerFlightQuery = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL rd GROUP BY rd.flight_number";

                        seatsTakenResultSet = statementTwo.executeQuery(findAllSeatsTakenPerFlightQuery);

                        while(seatsTakenResultSet.next() != false) {
                            if(fNum.equals(seatsTakenResultSet.getString("flight_number")) && Integer.parseInt(seatsTakenResultSet.getString("taken_seats")) < capacity) {
                                System.out.println("     " + airlineId + "           " + fNum + "           " + depCity + "          " + arrCity + "       " + depTime + "        " + arrTime);
                            }
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
    
    //Testing method for findAirlineRoutesBetweenCitiesWithAvailableSeats that does not require input
    public static boolean findAirlineRoutesBetweenCitiesWithAvailableSeats(Statement statement, Statement statementTwo, String cityOne,
    	String cityTwo, String airline, String stringDate) {
        ResultSet resultSet, seatsTakenResultSet;

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

                if(dayOfWeek == 1) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE 'S______'";
                }
                else if(dayOfWeek == 2) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '_M_____'";            
                }
                else if(dayOfWeek == 3) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '__T____'";               
                }
                else if(dayOfWeek == 4) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '___W___'";               
                }
                else if(dayOfWeek == 5) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '____T__'";               
                }
                else if(dayOfWeek == 6) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '_____F_'";               
                }
                else if(dayOfWeek == 7) {
                    findAllFlightsOnDate = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type AND f.airline_id = p.owner_id WHERE f.departure_city = '" + cityOne + "' AND f.arrival_city = '" + cityTwo + "'"
                        + " AND f.airline_id = '" + airlineId + "' AND f.weekly_schedule LIKE '______S'";               
                }

                try {
                    resultSet = statement.executeQuery(findAllFlightsOnDate);

                    System.out.println("Airline Id | Flight Number | Dep. City | Arr. City | Dep. Time | Arr. Time");                    

                    while(resultSet.next() != false) {
                        String fNum = resultSet.getString("flight_number");
                        String depCity = resultSet.getString("departure_city");
                        String arrCity = resultSet.getString("arrival_city");
                        String depTime = resultSet.getString("departure_time");
                        String arrTime = resultSet.getString("arrival_time");
                        int capacity = Integer.parseInt(resultSet.getString("plane_capacity"));  

                        String findAllSeatsTakenPerFlightQuery = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL rd GROUP BY rd.flight_number";

                        seatsTakenResultSet = statementTwo.executeQuery(findAllSeatsTakenPerFlightQuery);

                        while(seatsTakenResultSet.next() != false) {
                            if(fNum.equals(seatsTakenResultSet.getString("flight_number")) && Integer.parseInt(seatsTakenResultSet.getString("taken_seats")) < capacity) {
                                System.out.println("     " + airlineId + "           " + fNum + "           " + depCity + "          " + arrCity + "       " + depTime + "        " + arrTime);
                            }
                        }  
                        return true;              
                    }
                }  catch(SQLException sqle) {
                    System.out.println("Result set failed");
                    System.out.println(sqle.toString());
                    sqle.printStackTrace(); 
                    return false;            
                }            
            } catch(ParseException pe) {
                System.out.println("Parse exception.");
                System.exit(1);
                return false;
            }
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();   
            return false;            
        }
        return false;
    }

    public static void addReservation(Scanner s, Statement statement) {
        ResultSet resultSet, resultSetTwo;
        System.out.println("---------------");
        String[] flights = new String[4];
        String[] depDates = new String[4];
        int tripLegs = 4;
        int index = 0;

        int allGoodReservations = 0;

        while(tripLegs != 0) {
            System.out.print("Flight Number: ");
            flights[index] = s.next();
            if(flights[index].equals("0")) {
                flights[index] = null;
                break;
            }
            System.out.print("Departure Date: ");
            depDates[index] = s.next();

            tripLegs--;
            index++;
        }

        System.out.print("Customer Id: ");
        String custId = s.next();
        System.out.print("Credit Card Number: ");
        String ccn = s.next();

        String startCity = "";
        String endCity = "";
        int[] canReserveFlight = new int[4];

        int totalCost = 0;

        for(int i = 0; i < flights.length; i++) {
            if(flights[i] == null) {
                break;
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                
                try {
                    Date date = formatter.parse(depDates[i]);

                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                    if(dayOfWeek == 1) {
                        try {
                            String determineFlightExistsSunday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE 'S______'";

                            ResultSet flightOnSunday = statement.executeQuery(determineFlightExistsSunday);

                            if(flightOnSunday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnSunday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnSunday.getString("arrival_city");
                                }

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);

                                int takenSeats = 0; 
                                int totalSeats = 0;

                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);

                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }
                    }

                    else if(dayOfWeek == 2) {
                        try {
                            String determineFlightExistsMonday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '_M_____'";

                            ResultSet flightOnMonday = statement.executeQuery(determineFlightExistsMonday);

                            if(flightOnMonday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnMonday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnMonday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }                    
                    }

                    else if(dayOfWeek == 3) {
                        try {
                            String determineFlightExistsTuesday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '__T____'";

                            ResultSet flightOnTuesday = statement.executeQuery(determineFlightExistsTuesday);

                            if(flightOnTuesday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnTuesday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnTuesday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }                    
                    }

                    else if(dayOfWeek == 4) {
                        try {
                            String determineFlightExistsWednesday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '___W___'";

                            ResultSet flightOnWednesday = statement.executeQuery(determineFlightExistsWednesday);

                            if(flightOnWednesday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnWednesday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnWednesday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }                    
                    }

                    else if(dayOfWeek == 5) {
                        try {
                            String determineFlightExistsThursday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '____T__'";

                            ResultSet flightOnThursday = statement.executeQuery(determineFlightExistsThursday);

                            if(flightOnThursday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnThursday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnThursday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }                    
                    } 

                    else if(dayOfWeek == 6) {
                        try {
                            String determineFlightExistsFriday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '_____F_'";

                            ResultSet flightOnFriday = statement.executeQuery(determineFlightExistsFriday);

                            if(flightOnFriday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnFriday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnFriday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }                    
                    }

                    else if(dayOfWeek == 7) {
                        try {
                            String determineFlightExistsSaturday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '______S'";

                            ResultSet flightOnSaturday = statement.executeQuery(determineFlightExistsSaturday);

                            if(flightOnSaturday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnSaturday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnSaturday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1])) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }                            
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();                         
                        }                    
                    }                                                                                                                     
                } catch(ParseException pe) {
                    System.out.println("Parse exception.");
                    System.exit(1);
                }
            }
        }

        if(flights[0] != null) {
            try {
                String getAllReservationsQuery = "SELECT COUNT(reservation_number) AS total_reservations FROM RESERVATION";

                ResultSet allReservationsResults = statement.executeQuery(getAllReservationsQuery);

                allReservationsResults.next();

                int totalReservations = Integer.parseInt(allReservationsResults.getString("total_reservations"));

                int newReservation = totalReservations + 1;


                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                Date date = new Date();
                String today = dateFormat.format(date);

                System.out.println(startCity);
                System.out.println(endCity);

                String addNewReservation = "INSERT INTO RESERVATION VALUES('" + newReservation + "', '" + custId + "', " + totalCost + ", '" + ccn + "', To_Date('" + today + "', 'MM-dd-yyyy'), 'N', '" + startCity + "', '" + endCity + "')";

                statement.executeUpdate(addNewReservation);

                System.out.println("Your reservation number is: " + newReservation);

                for(int y = 0; y < canReserveFlight.length; y++) {
                    if(flights[y] == null) {
                        break;
                    }

                    String addNewResDetail = "INSERT INTO RESERVATION_DETAIL VALUES('" + newReservation + "', '" + flights[y] + "', To_Date('" + depDates[y] + "', 'MM-dd-yyyy'), " + y + ")";
                    statement.executeUpdate(addNewResDetail);
                }
            } catch(SQLException sqle) {
                System.out.println("Result set failed");
                System.out.println(sqle.toString());
                sqle.printStackTrace();                 
            }
        }
    }
    
    //Testing method for addReservation that does not require input
    public static boolean addReservation(Statement statement, String[] flights, String[] depDates, String custId,
    	String ccn) {
        ResultSet resultSet, resultSetTwo;
        System.out.println("---------------");
        int tripLegs = 4;
        int index = 0;

        int allGoodReservations = 0;

        while(tripLegs != 0) {
            if(flights[index].equals("0")) {
                flights[index] = null;
                break;
            }
            tripLegs--;
            index++;
        }

        String startCity = "";
        String endCity = "";
        int[] canReserveFlight = new int[4];

        int totalCost = 0;

        for(int i = 0; i < flights.length; i++) {
            if(flights[i] == null) {
                break;
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                
                try {
                    Date date = formatter.parse(depDates[i]);

                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                    if(dayOfWeek == 1) {
                        try {
                            String determineFlightExistsSunday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE 'S______'";

                            ResultSet flightOnSunday = statement.executeQuery(determineFlightExistsSunday);

                            if(flightOnSunday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnSunday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnSunday.getString("arrival_city");
                                }

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);

                                int takenSeats = 0; 
                                int totalSeats = 0;

                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);

                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();  
                            return false;                       
                        }
                    }

                    else if(dayOfWeek == 2) {
                        try {
                            String determineFlightExistsMonday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '_M_____'";

                            ResultSet flightOnMonday = statement.executeQuery(determineFlightExistsMonday);

                            if(flightOnMonday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnMonday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnMonday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                               
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();   
                            return false;                      
                        }                    
                    }

                    else if(dayOfWeek == 3) {
                        try {
                            String determineFlightExistsTuesday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '__T____'";

                            ResultSet flightOnTuesday = statement.executeQuery(determineFlightExistsTuesday);

                            if(flightOnTuesday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnTuesday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnTuesday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();    
                            return false;                     
                        }                    
                    }

                    else if(dayOfWeek == 4) {
                        try {
                            String determineFlightExistsWednesday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '___W___'";

                            ResultSet flightOnWednesday = statement.executeQuery(determineFlightExistsWednesday);

                            if(flightOnWednesday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnWednesday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnWednesday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                              
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();  
                            return false;                       
                        }                    
                    }

                    else if(dayOfWeek == 5) {
                        try {
                            String determineFlightExistsThursday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '____T__'";

                            ResultSet flightOnThursday = statement.executeQuery(determineFlightExistsThursday);

                            if(flightOnThursday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnThursday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnThursday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                                
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();  
                            return false;                       
                        }                    
                    } 

                    else if(dayOfWeek == 6) {
                        try {
                            String determineFlightExistsFriday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '_____F_'";

                            ResultSet flightOnFriday = statement.executeQuery(determineFlightExistsFriday);

                            if(flightOnFriday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnFriday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnFriday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1]) || depDates[i+1] == null) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                               
                            }
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace(); 
                            return false;                        
                        }                    
                    }

                    else if(dayOfWeek == 7) {
                        try {
                            String determineFlightExistsSaturday = "SELECT * FROM FLIGHT WHERE flight_number = '" + flights[i] + "' AND weekly_schedule LIKE '______S'";

                            ResultSet flightOnSaturday = statement.executeQuery(determineFlightExistsSaturday);

                            if(flightOnSaturday.next() == true) {
                                if(i == 0) {
                                    startCity = flightOnSaturday.getString("departure_city");
                                }

                                if(flights[i + 1] == null || i == 3) {
                                    endCity = flightOnSaturday.getString("arrival_city");
                                }                            

                                String findTakenSeats = "SELECT flight_number, COUNT(flight_number) AS taken_seats FROM RESERVATION_DETAIL WHERE flight_number = '" 
                                + flights[i] + "' AND flight_date = To_Date('" + depDates[i] + "', 'MM-dd-yyyy') GROUP BY flight_number";

                                ResultSet flightCurrentCapacity = statement.executeQuery(findTakenSeats);
                                
                                int takenSeats = 0;
                                int totalSeats = 0;
                                
                                if(flightCurrentCapacity.next() == true) {
                                    takenSeats = Integer.parseInt(flightCurrentCapacity.getString("taken_seats"));
                                }

                                String findTotalSeats = "SELECT * FROM FLIGHT f JOIN PLANE p ON f.plane_type = p.plane_type WHERE f.flight_number = '" + flights[i] + "'";
                                
                                ResultSet flightTotalCapacity = statement.executeQuery(findTotalSeats);
                                
                                if(flightTotalCapacity.next() == true) {
                                    totalSeats = Integer.parseInt(flightTotalCapacity.getString("plane_capacity"));
                                }

                                if(takenSeats < totalSeats) {
                                    canReserveFlight[i] = 1;

                                    String airlineId = flightTotalCapacity.getString("airline_id");

                                    String getAirlinePriceQuery = "SELECT * FROM PRICE WHERE airline_id = '" + airlineId + "'";

                                    ResultSet airline = statement.executeQuery(getAirlinePriceQuery);
                                    airline.next();

                                    if(depDates[i].equals(depDates[i+1])) {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("high_price"));
                                    }

                                    else {
                                        totalCost = totalCost + Integer.parseInt(airline.getString("low_price"));
                                    }                                    
                                }

                                else {
                                    System.out.println("Sorry, this flight is full!");
                                    System.exit(0);
                                }                               
                            }                            
                        } catch(SQLException sqle) {
                            System.out.println("Result set failed");
                            System.out.println(sqle.toString());
                            sqle.printStackTrace();    
                            return false;                     
                        }                    
                    }                                                                                                                     
                } catch(ParseException pe) {
                    System.out.println("Parse exception.");
                    System.exit(1);
                }
            }
        }

        if(flights[0] != null) {
            try {
                String getAllReservationsQuery = "SELECT COUNT(reservation_number) AS total_reservations FROM RESERVATION";

                ResultSet allReservationsResults = statement.executeQuery(getAllReservationsQuery);

                allReservationsResults.next();

                int totalReservations = Integer.parseInt(allReservationsResults.getString("total_reservations"));

                int newReservation = totalReservations + 1;


                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                Date date = new Date();
                String today = dateFormat.format(date);

                System.out.println(startCity);
                System.out.println(endCity);

                String addNewReservation = "INSERT INTO RESERVATION VALUES('" + newReservation + "', '" + custId + "', " + totalCost + ", '" + ccn + "', To_Date('" + today + "', 'MM-dd-yyyy'), 'N', '" + startCity + "', '" + endCity + "')";

                statement.executeUpdate(addNewReservation);

                System.out.println("Your reservation number is: " + newReservation);

                for(int y = 0; y < canReserveFlight.length; y++) {
                    if(flights[y] == null) {
                        break;
                    }

                    String addNewResDetail = "INSERT INTO RESERVATION_DETAIL VALUES('" + newReservation + "', '" + flights[y] + "', To_Date('" + depDates[y] + "', 'MM-dd-yyyy'), " + y + ")";
                    statement.executeUpdate(addNewResDetail);
                }
                return true;
            } catch(SQLException sqle) {
                System.out.println("Result set failed");
                System.out.println(sqle.toString());
                sqle.printStackTrace();   
                return false;              
            }
        }
        return false;
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
    
    //Testing method for showReservationInfo that does not require input
    public static boolean showReservationInfo(Statement statement, String reservationNum) {
        ResultSet resultSet; 

        String findReservationQuery = "SELECT * FROM RESERVATION_DETAIL WHERE reservation_number = '" + reservationNum + "'";

        try {
            resultSet = statement.executeQuery(findReservationQuery);

            System.out.println("Reservation # | Flight # | Flight Date | Leg");
            while(resultSet.next() != false) {
                System.out.println("\t" + resultSet.getString("reservation_number") + "         " + resultSet.getString("flight_number") + "        " 
                    + resultSet.getString("flight_date").substring(0,10) + "     " + resultSet.getString("leg"));
            }
        	return true;
        } catch(SQLException sqle) {
            System.out.println("Result set failed");
            System.out.println(sqle.toString());
            sqle.printStackTrace();    
            return false;          
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
    
    //Testing method for buyTicket that does not require input
    public static boolean buyTicket(Statement statement, String reservationNum) {
        ResultSet resultSet;

        String updateReservation = "UPDATE RESERVATION SET ticketed = 'Y' WHERE reservation_number = '" + reservationNum + "'";

        try {
            statement.executeUpdate(updateReservation);
            System.out.println("Ticketing complete!");
            return true;
        } catch(SQLException sqle) {
            System.out.println("Ticket update failed due to possible nonexistence of reservation");
            System.out.println(sqle.toString());
            sqle.printStackTrace();   
            return false;          
        }
    }

    public static void main (String[] args) {
        Connection connection;
        Statement statement = null;
        Statement statementTwo = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass"; 
        
            connection = DriverManager.getConnection(url, "ach53", "drumline617+"); 

            statement = connection.createStatement();
            statementTwo = connection.createStatement();
        } catch(Exception Ex) {
            System.out.println("Error connecting to database.  Machine Error: " +
            Ex.toString());
            Ex.printStackTrace();
        }
		if (args.length >= 1 && args[0] == "-d"){
			driver(statement, statementTwo);
		}
		else if (args.length >= 1 && args[0] == "-b"){
			benchmark(statement, statementTwo);
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
                    findAllRoutesBetweenCitiesWithAvailableSeats(s, statement, statementTwo);
                    break;
                case 7:
                    findAirlineRoutesBetweenCitiesWithAvailableSeats(s, statement, statementTwo);
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
    public static void driver(Statement statement, Statement statementTwo){
    	//These are all test values that can be changed
    	String airlines = new String("airlinesTest.csv");
    	String planes = new String("planesTest.csv");
    	String prices = new String("pricesTest.csv");
    	String schedule = new String ("scheduleTest.csv");
    	String date = new String("12-12-2016");
    	String f_num =  new String("011");
    	String d_city = new String ("PIT");
    	String a_city = new String("JFK");
    	String a_id = new String("1");
    	int high = 250;
    	int low = 100;
    	String title = new String("Mr");
    	String fname = new String("John");
    	String lname = new String("Doe");
    	String street = new String("1st St");
    	String city = new String("Pittsburgh");
    	String state = new String("PA");
    	String phone = new String("4125551234");
    	String email = new String("johndoe@email.com");
    	String ccNumber = new String("1234567887654321");
    	String ccExpDate = new String("09-18");
    	String cityOne = new String("JFK");
    	String cityTwo = new String("PIT");
    	String airline = new String("1");
    	String stringDate = new String("12-12-2016");
    	String[] flights = new String[] {"153"};
    	String[] depDates = new String[]{"12-12-2016"};
    	String custId = new String("1");
    	String ccn = new String("1234567887654321");
    	String reservationNum = new String("1");
    	
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
    	if (loadSchedule(statement, schedule))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading schedule info");
    	
    	System.out.println("5. Load price information from file");
    	if (loadPrice(statement, prices))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading price info");
    		
    	System.out.println("6. Change price");
    	if (changePrice(statement, d_city, a_city, a_id, high, low))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error with loading plane info");
    		
    	System.out.println("7. Get manifest");
    	if (getManifest(statement, date, f_num))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error getting the manifest");
    		
    	System.out.println("Testing Customer Options");
    	
    	System.out.println("1. Add customer");
    	if (addNewCustomer(statement, title, fname, lname, street, city,
    	state, phone, email, ccNumber, ccExpDate))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error adding a new customer.");
    		
    	System.out.println("2. Show customer info");
    	if (showCustomerInfo(statement, fname, lname))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error showing customer info.");
    		
    	System.out.println("3. Find flight price between two cities");
    	if (findFlightPriceBetweenCities(statement, cityOne, cityTwo))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error finding the price between cities.");
    		
    	System.out.println("4. Find all routes between two cities");
    	if (findAllRoutesBetweenCities(statement, cityOne, cityTwo))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error finding the routes between cities.");
    		
    	System.out.println("5. Find all routes between two cities on one airline");
    	if (findAirlineRoutesBetweenCities(statement, cityOne, cityTwo, airline))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error finding the airline routes between cities.");
    		
    	System.out.println("6. Find all routes between cities with available seats");
    	if (findAllRoutesBetweenCitiesWithAvailableSeats(statement, statementTwo, cityOne,
		cityTwo, stringDate))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error finding the routes between two cities with available seats.");
    		
    	System.out.println("7. Find all routes between cities with available seats on a given airline");
    	if (findAirlineRoutesBetweenCitiesWithAvailableSeats(statement, statementTwo, cityOne,
    	cityTwo, airline, stringDate))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error finding the routes between two cities with available seats for a given airline.");
    		
    	System.out.println("8. Add reservation");
    	if (addReservation(statement, flights, depDates, custId, ccn))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error adding the reservation");
    		
    	System.out.println("9. Show reservation info");
    	if (showReservationInfo(statement, reservationNum))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error showing reservation info");
    		
    	System.out.println("10. Buy ticket");
    	if (buyTicket(statement, reservationNum))
    		System.out.println("Successful");
    	else
    		System.out.println("There is an error buying a ticket");
    	
    	System.exit(0);
    	
    }
    
    public static void  benchmark(Statement statement, Statement statementTwo){
    	System.exit(0);
    }
}