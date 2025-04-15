/*
this class is used to store neighbours of the airport, it stores the neighbor airport and distance to the original airport
 */
public class Connection {
    //neighbor airport
    private Airport airport;

    //distance to the original airport
    private double dist;

    //constructor
    public Connection(Airport airport, double dist) {
        this.airport = airport;
        this.dist = dist;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
}
