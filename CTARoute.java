//Author: Ruoshan Chen   Apr20,2017 
//CTARoute class describes what a cta route contains and sets up some specified methods for dealing with
//certain problems.

package cta;

import java.util.ArrayList;

public class CTARoute {
 
    private String name;
    private ArrayList<CTAStation> stops;
     
    public CTARoute() {
        name = "Green";
        stops = new ArrayList<CTAStation>();
        
    }
     
    public CTARoute(String name) {
        this.name = name;
        stops = new ArrayList<CTAStation>();
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public ArrayList<CTAStation> getStops() {
        return stops;
    }
 
    public void setStops(ArrayList<CTAStation> stops) {
        this.stops = stops;
    }
     
    public boolean equals(CTARoute r) {
        if (!name.equals(r.getName())) {
            return false;
        }
         
        ArrayList<CTAStation> other = r.getStops();
        if (stops.size() != other.size()) {
            return false;
        }
         
        for (int i=0; i<stops.size(); i++) {
            if (!stops.get(i).equals(other.get(i))) {
                return false;
            }
        }
         
        return true;
    }
     
    public String toString() {
        String result = name;
         
        for (CTAStation station : stops) {
            result = result + " " + station.getName();
        }
         
        return result;
    }
     
    //add a new station to a cta route
    public void addStation(CTAStation station) {
        stops.add(station);
    }
     
    //delete a existing station from a cta route
    public void removeStation(CTAStation station) {
        for (CTAStation s : stops) {
            if (s.equals(station)) {
                stops.remove(s);
                return;
            }
        }
    }
     
    //insert a new station into a specific position in a cta route
    public void insertStation(int index, CTAStation station) {
        stops.add(index, station);
    }
     
    //find a specified station name in a cta route otherwise return null
    public CTAStation lookupStation(String name) {
        for (CTAStation s : stops) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
         
        return null;
    }
     
    //find a specified station name in a cta route otherwise return a negative 
    public int findStation(String name) {
        for (int i=0; i<stops.size(); i++) {
            if (stops.get(i).getName().equals(name)) {
                return i;
            }
        }
         
        return -1;
    }
    
    
    //find the nearest station to a location by providing a latitude and a longitude 
    public CTAStation nearestStation(double lat, double lng) {
        CTAStation closest = stops.get(0);
        double cDistance = closest.calcDistance(lat, lng);
         
        for (CTAStation s : stops) {
            double nDistance = s.calcDistance(lat, lng);
            if (nDistance < cDistance){
                closest = s;
            }
        }
         
        return closest;
    }
     
    
    //find the nearest station to a location by providing a GeoLocation variable 
    public CTAStation nearestStation(GeoLocation g) {
        CTAStation closest = stops.get(0);
        double cDistance = closest.calcDistance(g);
         
        for (CTAStation s : stops) {
            double nDistance = s.calcDistance(g);
            if (nDistance < cDistance){
                closest = s;
            }
        }
         
        return closest;
    }
    
    //calculate the number of the stops between two stations on the same route
    public int numStops(int i, CTAStation s, CTAStation d){
    	int[] a=s.getIndex();
    	int[] b=d.getIndex();
    	int stops=Math.abs(a[i]-b[i]);
    	return stops;
    }
    
    public CTAStation get(int index) {
        return stops.get(index);
    }
     
    public int size() {
        return stops.size();
    }

}
