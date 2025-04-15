import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Scanner;

public class Airport implements Comparator<Airport> {
    //Stores all the existing airports
    public static Hashtable<String,Airport> allAirports=new Hashtable<String,Airport>();

    //number of existing airports for task 1, used as a UNID for array locations
    public static int airportCount=0;

    //number of existing airports for task 2(including park versions of each airport), used as a UNID for array locations
    public static int airportCountT2=0;

    //code of the airport
    private String airportCode;

    //name of the airfield
    private String airfieldName;

    //is it the park version of the airport
    private boolean parkType=false;

    private Double latitude;

    private Double longitude;
    private int parkingCost;

    //list of neighbours for task 1
    private ArrayList<Connection> neigh;

    //list of neighbours for task 2, including park versions
    private ArrayList<Connection> neighT2;

    //location in task 1 graph arrays
    private int arrLoc;

    //location in task 2 graph arrays
    private int arrLocT2;

    //constructor for the airports
    public Airport(String airportCode, String airfieldName, Double latitude, Double longitude, int parkingCost) {
        this.setAirportCode(airportCode);
        this.setAirfieldName(airfieldName);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setParkingCost(parkingCost);
        this.setNeigh(new ArrayList<Connection>());
        this.setNeighT2(new ArrayList<Connection>());
        arrLoc=airportCount;
        airportCount++;
        arrLocT2=airportCountT2;
        airportCountT2++;
    }

    //constructor for the park versions of the airports
    public Airport(String airportCode, String airfieldName, Double latitude, Double longitude, int parkingCost, boolean parkType) {
        this.setAirportCode(airportCode);
        this.setAirfieldName(airfieldName);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setParkingCost(parkingCost);
        this.setNeigh(new ArrayList<Connection>());
        this.setNeighT2(new ArrayList<Connection>());
        this.parkType=true;
        arrLocT2=airportCountT2;
        airportCountT2++;
    }

    //default constructor
    public Airport() {

    }

    //method to read the airport data and create objects
    public static void readAirports(Scanner airportSc) {
        airportSc.nextLine();

        while(airportSc.hasNextLine()){
            String[] lineReader=airportSc.nextLine().split(",");
            String airportCode=lineReader[0];
            String airportPark=airportCode+"PARK";
            //System.out.println(airportCode);
            //System.out.println(airportPark);
            String airfieldName=lineReader[1];

            //System.out.println(airfieldName);
            Double latitude= Double.valueOf(lineReader[2]);
            //System.out.println(latitude);
            Double longitude=Double.valueOf(lineReader[3]);
            //System.out.println(longitude);
            int parkingCost= Integer.parseInt(lineReader[4]);
            //System.out.println(parkingCost);
            allAirports.put(airportCode, new Airport(airportCode,airfieldName,latitude,longitude,parkingCost));
            allAirports.put(airportPark, new Airport(airportPark,airfieldName,latitude,longitude,parkingCost,true));

            allAirports.get(airportCode).neighT2.add(new Connection(allAirports.get(airportPark),0));
            //allAirports.get(airportPark).neighT2.add(new Connection(allAirports.get(airportCode),0));
            allAirports.get(airportPark).neighT2.add(new Connection(allAirports.get(airportPark),0));
        }
    }

    //method to read the directions data and create neighbor lists
    public static void readDirections(Scanner directionsSc) {
        directionsSc.nextLine();

        while(directionsSc.hasNextLine()){
            String[] lineReader=directionsSc.nextLine().split(",");
            String from=lineReader[0];
            String to=lineReader[1];
            String fromPark=from+"PARK";
            double dist=Mission.calculateDistance(allAirports.get(from), allAirports.get(to));
            allAirports.get(from).neigh.add(new Connection(allAirports.get(to),dist));
            allAirports.get(from).neighT2.add(new Connection(allAirports.get(to),dist));
            allAirports.get(fromPark).neighT2.add(new Connection(allAirports.get(to),dist));
            }

    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportCode='" + getAirportCode() + '\'' +
                ", airfieldName='" + getAirfieldName() + '\'' +
                ", latitude=" + getLatitude() +
                ", longitude=" + getLongitude() +
                ", parkingCost=" + getParkingCost() +
                '}';
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirfieldName() {
        return airfieldName;
    }

    public void setAirfieldName(String airfieldName) {
        this.airfieldName = airfieldName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(int parkingCost) {
        this.parkingCost = parkingCost;
    }

    @Override
    public int compare(Airport o1, Airport o2) {
        double cost1=AirportGraph.activeMission.getCost()[o1.getArrLoc()];
        double cost2=AirportGraph.activeMission.getCost()[o2.getArrLoc()];

        return Double.compare(cost1, cost2);

    }

    public ArrayList<Connection> getNeigh() {
        return neigh;
    }

    public void setNeigh(ArrayList<Connection> neigh) {
        this.neigh = neigh;
    }

    public int getArrLoc() {
        return arrLoc;
    }

    public void setArrLoc(int arrLoc) {
        this.arrLoc = arrLoc;
    }

    public boolean isParkType() {
        return parkType;
    }

    public void setParkType(boolean parkType) {
        this.parkType = parkType;
    }

    public ArrayList<Connection> getNeighT2() {
        return neighT2;
    }

    public void setNeighT2(ArrayList<Connection> neighT2) {
        this.neighT2 = neighT2;
    }

    public int getArrLocT2() {
        return arrLocT2;
    }

    public void setArrLocT2(int arrLocT2) {
        this.arrLocT2 = arrLocT2;
    }
}
