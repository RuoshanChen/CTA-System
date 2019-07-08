//Author: Ruoshan Chen   Apr20,2017 
//CTAStation class is inherited from GeoLocation class and it describes what a cta station contains 
//and sets up some basic methods.

package cta;

public class CTAStation extends GeoLocation {
	private String name;
    private String location;
    private int[] index;
    private boolean wheelchair;
     
    public CTAStation() {
        name = "unknown";
        location = "unknown";
        wheelchair = false;
        index = new int[1];
    }
 
    public CTAStation(String name, double latitude, double longitude, String location, boolean wheelchair, int[] i) {
        super(latitude, longitude);
        this.name = name;
        this.location = location;
        this.wheelchair = wheelchair;
        index = i;
    }
     
    public void setName(String n) {
        name = n;
    }
     
    public void setLocation(String l) {
        location = l;
    }
     
    public void setWheelchair(boolean w) {
        wheelchair = w;
    }
     
    public int[] getIndex() {
        return index;
    }
 
    public void setIndex(int[] index) {
        this.index = index;
    }
 
    public String getName() {
        return name;
    }
     
    public String getLocation() {
        return location;
    }
     
    public boolean isWheelchair() {
        return wheelchair;
    }
     
    public String toString() {
        return name + " at " + super.toString();
    }
     
    public String toStringFull() {
        return name + " at " + super.toString() + " " + (wheelchair?"with":"without") + " wheelchair access.";
    }
     
    public boolean equals(CTAStation s) {
        if (!super.equals(s)) {
            return false;
        } else {
            return name.equals(s.getName()) && location.equals(s.getLocation()) && wheelchair == s.isWheelchair();
        }
    }
}
