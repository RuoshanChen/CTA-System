//Author:Ruoshan Chen   Apr22,2017 
//CTAStopApp class contains a main function for this project and some methods for fulfilling 
//the design requirements.


package cta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class CTAStopApp {
     
	//prompt user for enter an integer 
    public static int intPrompt(Scanner input, String prompt) {
        int result = 0;
        String entered = "";
        boolean done = false;
         
        do {
            try {
                System.out.print(prompt);
                entered = input.nextLine();
                result = Integer.parseInt(entered);
                 
                done = true;
            } catch (Exception e) {
                System.out.println("'" + entered + "' is not an integer. You must enter a whole number.");
            }
        } while (!done);
         
        return result;
    }
     
    // prompt user for an integer that is limited from min to max
    public static int intPrompt(Scanner input, String prompt, int min, int max) {
        int result = 0;
        String entered = "";
        boolean done = false;
         
        do {
            try {
                System.out.print(prompt);
                entered = input.nextLine();
                result = Integer.parseInt(entered);
                 
                if (result>=min && result<=max) {
                    done = true;
                } else {
                    System.out.println(result + " needs to be between " + min + " and " + max + ". Please try again.");
                }
                 
            } catch (Exception e) {
                System.out.println("'" + entered + "' is not an integer. You must enter a whole number.");
            }
        } while (!done);
         
        return result;
    }
     
    // prompt user for a double 
    public static double doublePrompt(Scanner input, String prompt) {
        double result = 0.0;
        String entered = "";
        boolean done = false;
         
        do {
            try {
                System.out.print(prompt);
                entered = input.nextLine();
                result = Double.parseDouble(entered);
                 
                done = true;
            } catch (Exception e) {
                System.out.println("'" + entered + "' is not an double. You must enter a real number.");
            }
        } while (!done);
         
        return result;
    }
     
    //prompt user for a double limited from min to max
    public static double doublePrompt(Scanner input, String prompt, double min, double max) {
        double result = 0;
        String entered = "";
        boolean done = false;
         
        do {
            try {
                System.out.print(prompt);
                entered = input.nextLine();
                result = Double.parseDouble(entered);
                 
                if (result>=min && result<=max) {
                    done = true;
                } else {
                    System.out.println(result + " needs to be between " + min + " and " + max + ". Please try again.");
                }
                 
            } catch (Exception e) {
                System.out.println("'" + entered + "' is not an double. You must enter a real number.");
            }
        } while (!done);
         
        return result;
    }
     
    //prompt user for choosing yes or no
    public static boolean yesNoPrompt(Scanner input, String prompt) {
        boolean result = false;
        String entered = "";
        boolean done = false;
         
        do {
            System.out.print(prompt);
            entered = input.nextLine();
             
            if (entered.equalsIgnoreCase("y")||entered.equalsIgnoreCase("yes")) {
                result = true;
                done = true;
            } else if (entered.equalsIgnoreCase("n")||entered.equalsIgnoreCase("no")) {
                result = false;
                done = true;
            } else {
                System.out.println("'" + entered + "' is not a yes or no. You must enter y (yes) or n (no).");
            }
             
        } while (!done);
         
        return result;
    }
     
    //prompt user for confirming their yes/no input
    public static boolean yesNoPromptConfirm(Scanner input, String prompt, String secondaryPrompt) {
        boolean result = false;
        String entered = "";
        boolean done = false;
         
        do {
            System.out.print(prompt);
            entered = input.nextLine();
             
            if (entered.equalsIgnoreCase("y")||entered.equalsIgnoreCase("yes")) {
                result = true;
                done = confirmInput(input, secondaryPrompt);
            } else if (entered.equalsIgnoreCase("n")||entered.equalsIgnoreCase("no")) {
                result = false;
                done = true;
            } else {
                System.out.println("'" + entered + "' is not a yes or no. You must enter y (yes) or n (no).");
            }
             
        } while (!done);
         
        return result;
    }
     
    //prompt user for confirming their input
    public static boolean confirmInput(Scanner input, String prompt) {
        return yesNoPrompt(input, prompt);
    }
     
    
    // read data from CTAStops.csv
    public static CTARoute[] read(String filename) throws IOException{
        File file = new File(filename);
        Scanner input = new Scanner(file);
      
        String[] headers = input.nextLine().split(",");
        int numRoutes = headers.length - 5;
        
        CTARoute[] routes = new CTARoute[numRoutes];
        
        for (int i=0; i<numRoutes; i++) {
            routes[i] = new CTARoute(headers[i+5]);
        }
        ArrayList<CTAStation> rawStops = new ArrayList<CTAStation>();
 
        input.nextLine();
     
        
        while(input.hasNextLine()) {
        	
            String[] data = input.nextLine().split(",");
            CTAStation station = new CTAStation();
            station.setName(data[0]);
            station.setLatitude(Double.parseDouble(data[1]));
            station.setLongitude(Double.parseDouble(data[2]));
            station.setLocation(data[3]);
            station.setWheelchair(Boolean.parseBoolean(data[4]));
             
            int[] i = new int[numRoutes];
            for (int j=0; j<i.length; j++) {
                i[j] = Integer.parseInt(data[j+5]);
            }
             
            station.setIndex(i);
             
            rawStops.add(station);
        }
         
        for (CTAStation station : rawStops) {
            int[] pos = station.getIndex();
            for (int i=0; i<pos.length; i++) {
                if (pos[i] != -1) {
                    boolean added = false;
                    for (int j=0; j<routes[i].size(); j++) {
                        if (pos[i] < routes[i].get(j).getIndex()[i]) {
                            routes[i].insertStation(j, station);
                            added = true;
                            break;
                        }
                    }
                     
                    if (!added) {
                        routes[i].addStation(station);
                    }
                }
            }
        }
         
        input.close();
         
        return routes;
    }
     
    //operate menu choice 
    public static void menu(CTARoute[] routes) {
        Scanner input = new Scanner(System.in);
         
        boolean done = false;
         
        do {
             
            printMenu();
             
            int choice = intPrompt(input, "Enter you choice: ", 1, 8);
             
            switch(choice) {
                case 1:
                    displayStationNames(routes);
                    break;
                case 2:
                	 //add
                    routes = addStation(routes, input);
                    break;
                case 3:
                	//search
                    searchStation(routes, input);
                    break;
                    
                case 4:
                	//delete
                    routes = deleteStation(routes, input);
                    break;

                case 5:
                    //generate route from starting station to destination station on the same route
                	generateRoute(routes,input);
                    break;
                case 6:
                	//find nearest
                    nearestStation(routes, input);
                    break;
                   
                case 7:
                	//say goodbye
                    System.out.println("Goodbye!");
                    done = true;
                    break;
                default:
                    System.out.println("I didn't understand that.");
            }
             
        } while (!done);
         
        input.close();
    }
     
    //print out menu
    public static void printMenu() {
        System.out.println("1. Display the names of all stations");
        System.out.println("2. Add a new station");
        System.out.println("3. Display information for a station with a specific name");
        System.out.println("4. Delete an existing station");
        System.out.println("5. Show the route from starting station to destination station");
        System.out.println("6. Display the nearest station to a location");
        System.out.println("7. Exit");
    }
    
     //display all the station names by routes
    public static void displayStationNames(CTARoute[] routes) {
        System.out.println();
        for (int i=0; i<routes.length; i++) {
            System.out.println(routes[i].toString());
        }
        System.out.println();
    }
     
    //generate the route from start station to destination station (both station are on the SAME ROUTE)
    public static void generateRoute(CTARoute[] routes, Scanner input){
    	
    	System.out.println("Please enter your start station:");
    	String sName=input.nextLine();
    	System.out.println("Please enter your destination station:");
    	String dName=input.nextLine();
    	
    	int route=-1;
    	String color;
    	int stops=-1;
    	boolean flag=false;
    	
    	CTAStation start=null;
    	CTAStation destination=null;
    	
    	//check if the start station exist
    	 for (CTARoute r : routes) {
	            CTAStation found = r.lookupStation(sName);
	            if (found != null) {
		            start=found;
		           }
	        }
    	 if (start==null){
    		 System.out.println("No station found called " + sName);
    	 }
    	
    	//check if the destination station exist
    	 for (CTARoute r : routes) {
	            CTAStation found = r.lookupStation(dName);
	            if (found != null) {
		            destination=found;
		           }
	        }
    	 if (destination==null){
 		 System.out.println("No station found called " + dName);
    	 }
    	
    	 //generate the route from start station to destination station 
    			 for (int i=0;i<routes.length;i++) {
    				 	CTAStation sFound = routes[i].lookupStation(sName);
    		            CTAStation dFound = routes[i].lookupStation(dName);
		                
    		            if (sFound!=null&&dFound!=null) {
    		                
    		                route=i;
    		                
    		                color=decideRoute(route);
    		                
    		                stops=routes[i].numStops(i, sFound, dFound);
    		                
    		                System.out.println("You can take " + color+ " line and pass "+stops+" stops to get there.");
    		                
    		                flag=true;
    		               
    		            }   		            	
    		            }
    			 
    			//check if the start station and destination are on the same route
    			if (start!=null&&destination!=null&&flag==false){
    				System.out.println("They are not on the same route!");
    			}
    		        	
    		        
    			 			 		 
    }
    
    // decide which route the start station and destination station are on
    public static String decideRoute(int i){
    	
    	String route=null;
    	switch (i){
		case 0:
			route= "red";		
		break;
		case 1:
			route= "green";
		break;
		case 2:
			route= "blue";
		break;	
		case 3:
			route= "brown";
		break;
		case 4:
			route= "purple";
		break;
		case 5:
		    route= "pink";
		   
		break;
		case 6:
			route= "orange";
			
		break;
		case 7:
			route= "yellow";
		break;
    	}
		return route;
    }
    
    
    // calculate the nearest station to a specified station
    public static void nearestStation(CTARoute[] routes, Scanner input) {
        System.out.println();
         
        double latitude = doublePrompt(input, "Please enter your latitude: ", -90, 90);
        double longitude = doublePrompt(input, "Please enter your longitude: ", -180, 180);
         
        CTAStation nearest = routes[0].nearestStation(latitude, longitude);
        for (int i=1; i<routes.length; i++) {
            CTAStation other = routes[i].nearestStation(latitude, longitude);
            if (nearest.calcDistance(latitude, longitude)>other.calcDistance(latitude, longitude)){
                nearest = other;
            }
        }
         
        System.out.println("The closest station is " + nearest.toString());
         
        System.out.println();
    }
     
    
    //search a specified station by its name
    public static void searchStation(CTARoute[] routes, Scanner input) {
        System.out.println();
         
        System.out.print("What is the name of the station you wish to search for? ");
        String name = input.nextLine();
        
        CTAStation search = null;
        for (CTARoute route : routes) {
            CTAStation found = route.lookupStation(name);
            if (found!=null) {
                search = found;
            }
        }
        	if (search == null) {
            System.out.println("No station found called " + name);
        } else {
            System.out.println(search.toStringFull());
        }
        
         
        System.out.println();
    }
     
   
    //add station to a specified position in the CTA
    public static CTARoute[] addStation(CTARoute[] routes, Scanner input) {
        System.out.println();
         
        CTAStation add = new CTAStation();
         
        System.out.print("Enter the name of the new station: ");
        String name = input.nextLine();
        add.setName(name);
         
        System.out.print("Enter the location of " + name + ": ");
        add.setLocation(input.nextLine());
         
        add.setWheelchair(yesNoPrompt(input, "Does " + name + " have wheelchair access? (y/n): "));
         
        add.setLatitude(doublePrompt(input, "What is the latitude of " + name + "? ", -90, 90));
        add.setLongitude(doublePrompt(input, "What is the longitude of " + name + "? ", -90, 90));
         
        for (CTARoute route : routes) {
            if (yesNoPrompt(input, "Is " + name + " on the " + route.getName() + " line? (y/n): ")) {
                System.out.print("What is the name of the previous station? (Enter 'None' if there is no station): ");
                String prevName = input.nextLine();
                if (prevName.equalsIgnoreCase("none")) {
                    route.insertStation(0, add);
                } else {
                    System.out.print("What is the name of the following station? (Enter 'None' if there is no station): ");
                    String followName = input.nextLine();
                     
                    int index = route.findStation(prevName);
                    if (index == -1) {
                        if (followName.equalsIgnoreCase("none")) {
                            route.addStation(add);
                        } else {
                            index = route.findStation(followName);
                            if (index == -1) {
                                System.out.println("Neither " + prevName + " nor " + followName + " where found. Moving on...");
                            } else {
                                route.insertStation(index, add);
                            }
                        }
                    } else {
                        route.insertStation(index + 1, add);
                    }
                     
                }
            }
        }
         
        System.out.println();
         
        return routes;
    }
     
    
    //delete an existing station from CTA
    public static CTARoute[] deleteStation(CTARoute[] routes, Scanner input) {
        System.out.println();
         
        System.out.print("What is the name of the station you want to delete? ");
        String name = input.nextLine();
        boolean deleted = false;
         
        for (CTARoute route : routes) {
            CTAStation station = route.lookupStation(name);
            if (station != null && yesNoPromptConfirm(input,
                    "Do you want to remove " + name + " from the " + route.getName() + " line? (y/n): ",
                    "Are you sure you want to delete " + name + " from the " + route.getName() + " line? (y/n): ")) {
                route.removeStation(station);
                deleted = true;
                System.out.println();
            } 
            
        }
         
        if (!deleted) {
            System.out.println("No station called '" + name + "' was found.");
        }
         
        return routes;
    }
    
    //main function
    public static void main(String[] args) {
         
        CTARoute[] routes;
        
         
        try {
            routes = read("CTAStops.csv");
             
            menu(routes);
               
            
        } catch(IOException e) {
            System.out.println("File Not Found");
        }
         
    }

}
