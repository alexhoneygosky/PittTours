import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import oracle.jdbc.driver.OracleDriver;

public class PittTours {
    public static void newCustomer(Scanner s, Statement statement) {
        ResultSet resultSet;

        System.out.println("---------------");
        System.out.print("Title (Mr, Ms, Mrs): ");
        String title = s.next();
        System.out.print("First Name: ");
        String fname = s.next();
        System.out.print("Last Name: ");
        String lname = s.next();
        System.out.print("Address (Street Number<enter>, Street Name<enter>, Street Type (St, Ln, Blvd, etc.)<enter>: ");
        String streetNum = s.next();
        String streetName = s.next();
        String streetType = s.next();
        String street = streetNum + " " + streetName + " " + streetType;
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
	public static void eraseDB(Statement statement){
		System.out.println("Erasing database...");
		String procedure = new String("EXEC erase_db;");
		try{
			statement.execute(procedure);
			System.out.println("Finished.");
		}
		catch (SQLException sqle){
			System.out.println("SQL Error");
            System.out.println(sqle.toString());
            sqle.printStackTrace();
		}
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
		System.out.println("\nEnter high price: ");
		int high = s.nextInt();
		System.out.println("\nEnter low price: ");
		int low = s.nextInt();
		String procedure = new String("EXEC change_price ('" + depart + "','" + arrival + "',"
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
		Scanner s = new Scanner(System.in);
		System.out.println("Get Manifest");
		System.out.println("Enter the date (MM-DD-YY):");
		String date = s.next();
		System.out.println("\nEnter the flight number:");
		String flightNum = s.next();
		String procedure = new String("EXEC get_manifest('"+flightNum+"',to_date('"+date+"','MM-DD-YYYY'));");
		try{
			statement.execute(procedure);
		}
		catch(SQLException sqle){
			System.out.println("SQL Error");
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
        
            connection = DriverManager.getConnection(url, "ard71", "fl@wl3ss"); 

            statement = connection.createStatement();
        } catch(Exception Ex) {
            System.out.println("Error connecting to database.  Machine Error: " +
            Ex.toString());
            Ex.printStackTrace();
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
                    newCustomer(s, statement);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 10:
                        
                case 11:
                    System.out.println("Thank you for using PittTours!");
                    System.exit(0);
            }
        }
    }
}