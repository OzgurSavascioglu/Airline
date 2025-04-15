/*
This class is used to store the missions
readMissions method is used to read the input file and create the Mission objects
startTask1 and startTask2 methods initiate the task specific calculations calculations
flightDuration method calculates the duration oif the flight
flightCost method calculates the cost of the flight for task 2
task1Cost method calculates the cost of the flight for task 1
calculateDistance method calculates the distance between two airports
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Mission {
    //stores the earth radius
    public static final double EARTH_RADIUS=6371.00;
    //stores all existing missions
    public static ArrayList<Mission> allMissions=new ArrayList<>();
    //stores the airplane model
    private static String model;
    //stores the origin airport code
    private String origin;
    //stores the destination airport code
    private String destination;
    //stores the timeOrigin of the mission
    private Long timeOr;
    //stores the mission deadline
    private Long deadline;

    //constructor
    public Mission(String origin, String destination, Long timeOr, Long deadline) {

        this.origin = origin;
        this.destination = destination;
        this.timeOr = timeOr;
        this.deadline = deadline;
    }

    //readMissions method is used to read the input file and create the Mission objects
    public static void readMissions(Scanner missionsSc) {
        model=missionsSc.nextLine();

        while(missionsSc.hasNextLine()){
            String[] lineReader=missionsSc.nextLine().split(" ");

            String origin=lineReader[0];
            String destination=lineReader[1];
            Long timeOr= Long.valueOf((lineReader[2]));
            Long deadline= Long.valueOf((lineReader[3]));
            allMissions.add(new Mission(origin,destination,timeOr,deadline));
        }
    }

    //startTask1 method initiates the Task1 calculations
    public static void startTask1(PrintWriter output1){
        for(Mission e:allMissions){
            AirportGraph missionGraph=new AirportGraph();
            missionGraph.dijkstraT1(e.origin,e.destination,e.timeOr,output1);
        }
    }

    //startTask2 method initiates the Task2 calculations
    public static void startTask2(PrintWriter output2){
        for(Mission e:allMissions){
            T2Graph task2Graph=new T2Graph();
            task2Graph.pathFinder(e.origin,e.destination,e.timeOr, e.deadline, output2);
        }
    }

    /*
    flightDuration method calculates the duration oif the flight by calling
    the relevant calculation method for the airplane model
     */
    public static int flightDuration(Double distance){
        int duration = switch (Mission.model) {
            case "Carreidas 160" -> cDuration(distance);
            case "Orion III" -> oDuration(distance);
            case "Skyfleet S570" -> sDuration(distance);
            case "T-16 Skyhopper" -> tDuration(distance);
            default -> duration=-1;
        };
        return duration;
    }

    /*
    calculates the flight duration for the T-16 Skyhopper
     */
    private static int tDuration(Double distance) {
        int duration=6;
        if(distance>5000)
            duration=18;
        else if(distance>2500)
            duration=12;
        return duration;
    }

    /*
    calculates the flight duration for the Skyfleet S570
     */
    private static int sDuration(Double distance) {
        int duration=6;
        if(distance>1000)
            duration=18;
        else if(distance>500)
            duration=12;
        return duration;
    }

    /*
    calculates the flight duration for the Orion III
     */
    private static int oDuration(Double distance) {
        int duration=6;
        if(distance>3000)
            duration=18;
        else if(distance>1500)
            duration=12;
        return duration;
    }

    /*
    calculates the flight duration for the Carreidas 160
     */
    private static int cDuration(Double distance) {
        int duration=6;
        if(distance>350)
            duration=18;
        else if(distance>175)
            duration=12;
        return duration;
    }

    //flightCost method calculates the cost of the flight for task 2
    public static double flightCost(Airport origin, Airport destination, Long departure, Long landing){

        Integer dWeather = Airfield.allAirfields.get(origin.getAirfieldName()).getTimeCodeTable().get(departure);
        Integer lWeather = Airfield.allAirfields.get(destination.getAirfieldName()).getTimeCodeTable().get(landing);
        double distance=calculateDistance(origin, destination);
        Airfield.multiplier(dWeather);
        Airfield.multiplier(lWeather);
        return 300*Airfield.multiplier(dWeather)*Airfield.multiplier(lWeather)+distance;
    }

    //task1Cost method calculates the cost of the flight for task 1
    public static double task1Cost(Airport origin, Airport destination, Long departure){
        Integer dWeather = Airfield.allAirfields.get(origin.getAirfieldName()).getTimeCodeTable().get(departure);
        Integer lWeather = Airfield.allAirfields.get(destination.getAirfieldName()).getTimeCodeTable().get(departure);
        double distance=calculateDistance(origin, destination);

        return 300*Airfield.multiplier(dWeather)*Airfield.multiplier(lWeather)+distance;
    }

    //calculateDistance method calculates the distance between two airports
    public static double calculateDistance(Airport origin, Airport destination){
        double lat1=Math.toRadians(origin.getLatitude());
        double lat2=Math.toRadians(destination.getLatitude());
        double lon1=Math.toRadians(origin.getLongitude());
        double lon2=Math.toRadians(destination.getLongitude());
        double inside=Math.sqrt(Math.pow(Math.sin((lat2-lat1)/2),2)+Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin((lon2-lon1)/2),2));
        return 2*EARTH_RADIUS*Math.asin(inside);
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        Mission.model = model;
    }

    public Long getTimeOr() {
        return timeOr;
    }

    public void setTimeOr(Long timeOr) {
        this.timeOr = timeOr;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "model='" + model + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", timeOr=" + timeOr +
                ", deadline=" + deadline +
                '}';
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
