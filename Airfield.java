/*
This class stores the airfield objects
readWeather method reads the weather inputs and create the airfields
multiplier method calculates the weather multipliers
 */

import java.util.Hashtable;
import java.util.Scanner;

public class Airfield {

    //stores all the existing airfields
    public static Hashtable<String,Airfield> allAirfields=new Hashtable<String,Airfield>();

    //stores the name of the airfield
    private String airfieldName;

    //stores the weather codes for different times
    private Hashtable<Long, Integer> timeCodeTable;

    //constructor
    public Airfield(String airfieldName) {
        this.airfieldName = airfieldName;
        this.timeCodeTable=new Hashtable<Long, Integer>();
    }

    //readWeather method reads the weather inputs and create the airfields
    public static void readWeather(Scanner weatherSc) {
        weatherSc.nextLine();

        while(weatherSc.hasNextLine()){
            String[] lineReader=weatherSc.nextLine().split(",");

            String airfieldName=lineReader[0];
            Long time= Long.valueOf(lineReader[1]);
            Integer weatherCode= Integer.parseInt(lineReader[2]);

            if(!allAirfields.containsKey(airfieldName))
                allAirfields.put(airfieldName, new Airfield(airfieldName));

            allAirfields.get(airfieldName).timeCodeTable.put(time, weatherCode);

        }
    }

    //multiplier method calculates the weather multipliers
    public static double multiplier(int weatherCode){
           int bw=   weatherCode/16;
           int br=   (weatherCode%16)/8;
           int bs=   (weatherCode%8)/4;
           int bh=   (weatherCode%4)/2;
           int bb=   (weatherCode%2);
           return (bw*1.05+(1-bw))*(br*1.05+(1-br))*(bs*1.10+(1-bs))*(bh*1.15+(1-bh))*(bb*1.20+(1-bb));
    }

    public String getAirfieldName() {
        return airfieldName;
    }

    public void setAirfieldName(String airfieldName) {
        this.airfieldName = airfieldName;
    }

    public Hashtable<Long, Integer> getTimeCodeTable() {
        return timeCodeTable;
    }

    public void setTimeCodeTable(Hashtable<Long, Integer> timeCodeTable) {
        this.timeCodeTable = timeCodeTable;
    }

    @Override
    public String toString() {
        return "Airfield{" +
                "airfieldName='" + airfieldName + '\'' +
                ", timeCodeTable=" + timeCodeTable +
                '}';
    }
}
