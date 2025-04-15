/*
This class is used to calculate task 2 routes
pathFinder and pathSearch methods calculates the route
printT2Route prints the route to the outputfile
 */
import java.io.PrintWriter;
import java.util.*;

public class T2Graph {

    //static variable, stores the active graph
    public static T2Graph activeGraph;

    //stores the most efficient path
    List<Airport> bestPath;

    //stores the cost of the most efficient path
    double currentMin;

    //constructor
    public T2Graph( )
    {
        this.currentMin=Integer.MAX_VALUE;
        this.bestPath=new ArrayList<>();
        activeGraph=this;
    }

    //method to calculate the most efficient path
    public void pathFinder(String departure, String target, Long timeOrigin, Long deadline, PrintWriter output2){
        Airport depA=Airport.allAirports.get(departure);
        Airport targetA=Airport.allAirports.get(target);

        //System.out.println(depA.getAirportCode()+" "+depA.getNeighT2().size());

        //limits the maxPath according to the size of neighbours to increase the time efficiency
        int maxPath=12;

        if(depA.getNeighT2().size()>=20)
            maxPath = 6;

        else if(depA.getNeighT2().size()>=17)
            maxPath = 7;

        else if(depA.getNeighT2().size()>=10)
            maxPath = 8;

        List<Airport> activePath=new ArrayList<>();
        Set<Airport> visited=new HashSet<>();

        //calls the method to check neighbours and calculate the path
        pathSearch(depA,targetA,activePath,visited, timeOrigin,  deadline, maxPath,0);

        //calls the method to print the route
        printT2Route(output2);
    }

    //method to print the route
    private void printT2Route(PrintWriter output2) {
        if(bestPath.isEmpty()) {
            output2.println("No possible solution.");
            return;
        }

        for(Airport e: bestPath){
            String codeStr;
            if(e.isParkType())
                codeStr="PARK";
            else
                codeStr=e.getAirportCode();

            output2.printf("%s ",codeStr);
        }

        output2.printf("%.5f%n", currentMin);
    }

    //the method to check neighbours and calculate the path
    private void pathSearch(Airport depA, Airport targetA, List<Airport> activePath, Set<Airport> visited, Long timeOrigin, Long deadline, int maxPath, double cost) {

        activePath.add(depA);

        if(!depA.isParkType())
            visited.add(depA);

        if(Objects.equals(depA.getAirportCode(), targetA.getAirportCode())){
            this.bestPath=new ArrayList<>(activePath);

            if(cost<currentMin) {
                currentMin = cost;
            }

        }

        else if(activePath.size()<maxPath){

            for(Connection neigh: depA.getNeighT2()){//for loop starts
                double distance=Mission.calculateDistance(depA, neigh.getAirport());
                int duration;
                Long nextTime= (long) 0.0;

                if(neigh.getAirport().isParkType()) {
                    duration = 6;
                    nextTime= (long) (6*3600);
                }

                else
                    duration=Mission.flightDuration(distance);

                Long durationInMillis= ((long) duration *3600);

                double tempCost=Integer.MAX_VALUE;

                if(timeOrigin+durationInMillis<=deadline) {
                    if(neigh.getAirport().isParkType())
                        tempCost=depA.getParkingCost();

                    else
                        tempCost = Mission.flightCost(depA, neigh.getAirport(), timeOrigin, (timeOrigin + durationInMillis));
                }

                if(!visited.contains(neigh.getAirport()) && (timeOrigin+durationInMillis+nextTime<=deadline) && tempCost+cost<=currentMin) {
                    pathSearch(neigh.getAirport(), targetA, activePath, visited, timeOrigin + durationInMillis, deadline, maxPath,cost+tempCost);
                }
            }//for loop ends
        }

        activePath.remove(activePath.size()-1);

        if(!depA.isParkType())
            visited.remove(depA);
    }

}
