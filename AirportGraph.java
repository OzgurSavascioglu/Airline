/*This class is used to calculate Task 1 missions
dijkstraT1 and t1_Neighbours methods calculate the most efficient route
printRoute method print the route to the output file
 */

import java.io.PrintWriter;
import java.util.*;

public class AirportGraph {
    //stores the active mission
    public static AirportGraph activeMission;
    // stores the cost for the most efficient route for each airport
    private double[] cost;
    //stores the most efficient route source node for each airport
    private String[] from;
    //stores the aiprorts with final calculations for the cost
    private HashSet<String> settled;
    //this queue is used in Dijstra algorithm calculation for the most efficient path
    private PriorityQueue<Airport> pQueue;
    // Number of vertices
    private final int vertices;

    //constructor
    public AirportGraph( )
    {
        this.vertices = Airport.airportCount;
        cost = new double[vertices];
        from = new String[vertices];
        settled = new HashSet<>();
        pQueue = new PriorityQueue<>(vertices, new Airport());
        activeMission=this;
    }

    /*
    Dijkstra algorithm to find the most efficient route
     */
    public void dijkstraT1(String departure, String target, Long timeOrigin, PrintWriter output1)
    {
        Airport depA=Airport.allAirports.get(departure);

        for (int i = 0; i < vertices; i++) {
            cost[i] = Integer.MAX_VALUE;
            from[i] = null;
        }

        pQueue.add(depA);
        cost[depA.getArrLoc()] = 0.0;

        while (settled.size() != vertices) {
            if (pQueue.isEmpty())
                return;

            String fromCode = pQueue.remove().getAirportCode();

            if (settled.contains(fromCode))
                continue;

            settled.add(fromCode);
            t1_Neighbours(fromCode,timeOrigin);

            if(Objects.equals(fromCode, target)){
                printRoute(target, output1);
                return;
            }
        }
    }

    /*
    this method calculates the cost for neighbours of the airport to find the most efficient step
     */
    private void t1_Neighbours(String airportCode,Long timeOrigin) {
        double edgeCost = -1;
        double newCost = -1;
        Airport comeFrom=Airport.allAirports.get(airportCode);
        for (int i = 0; i < comeFrom.getNeigh().size(); i++) {
            Connection current = comeFrom.getNeigh().get(i);

            if (!settled.contains(current.getAirport().getAirportCode())) {

                edgeCost = Mission.task1Cost(comeFrom,current.getAirport(), timeOrigin);
                newCost = cost[comeFrom.getArrLoc()] + edgeCost;

                if (newCost < cost[current.getAirport().getArrLoc()]) {
                    cost[current.getAirport().getArrLoc()] = newCost;
                    from[current.getAirport().getArrLoc()] = airportCode;
                }

                pQueue.add(current.getAirport());
            }
        }
    }

    private void printRoute(String code, PrintWriter output1) {
        Stack<Airport> route=new Stack<>();
        Airport temp=Airport.allAirports.get(code);

        while(from[Airport.allAirports.get(code).getArrLoc()] != null) {
            route.push(Airport.allAirports.get(code));
            code=from[Airport.allAirports.get(code).getArrLoc()];
        }
        route.push(Airport.allAirports.get(code));

        while(!route.isEmpty()){
            String codeStr=route.pop().getAirportCode();
            //System.out.printf("%s ",codeStr);
            output1.printf("%s ",codeStr);
        }

        //System.out.printf("%.5f%n", cost[temp.getArrLoc()]);
        output1.printf("%.5f%n", cost[temp.getArrLoc()]);
    }

    public double[] getCost() {
        return cost;
    }

    public void setCost(double[] cost) {
        this.cost = cost;
    }
}
