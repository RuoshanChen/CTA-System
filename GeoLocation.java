//Author: Ruoshan Chen   Apr20,2017 
//GeoLocation Class is for getting the latitude and longitude of a specific location 

package cta;

public class GeoLocation {
	protected double latitude;
    protected double longitude;
     
    public GeoLocation() {
    	
        latitude = 0;
        longitude = 0;
    }
     
    public GeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
     
    public void setLatitude(double lat) {
        latitude = lat;
    }
     
    public void setLongitude(double lng) {
        longitude = lng;
    }
     
    public double getLatitude() {
        return latitude;
    }
     
    public double getLongitude() {
        return longitude;
    }
     
    public String toString() {
        return "[" + latitude + ", " + longitude + "]";
    }
     
    public boolean equals(GeoLocation g) {
        return (Math.abs(latitude - g.getLatitude())<0.001 && Math.abs(longitude - g.getLongitude())<0.001);
    }
    
    //calculate the distance from one location to another using GeoLocation as variable 
    public double calcDistance(GeoLocation g) {
        return calcDistance(g.getLatitude(), g.getLongitude());
    }
  
    //calculate the distance from one location to another using latitude(double) and longitude(double) as variable 
    public double calcDistance(double lat, double lng) {
        return Math.sqrt(Math.pow(latitude-lat, 2) + Math.pow(longitude - lng, 2));
    }
 
}
