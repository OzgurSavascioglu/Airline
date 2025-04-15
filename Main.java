import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        if (args.length != 6) {
            System.exit(1);
        }

        //create strings to store input and output file names
        String airportsFile = args[0];
        String directionsFile = args[1];
        String weatherFile = args[2];
        String missionsFile = args[3];
        String task1File = args[4];
        String task2File = args[5];

        // scanner object is required to read the contents of the file
        Scanner airportSc = new Scanner(new File(airportsFile));
        Scanner directionsSc = new Scanner(new File(directionsFile));
        Scanner weatherSc = new Scanner(new File(weatherFile));
        Scanner missionsSc = new Scanner(new File(missionsFile));

        //printwrites objects to print the outputfiles
        PrintWriter output1 = new PrintWriter(task1File);
        PrintWriter output2 = new PrintWriter(task2File);

        //read the input files
        Airport.readAirports(airportSc);
        Airport.readDirections(directionsSc);
        Airfield.readWeather(weatherSc);
        Mission.readMissions(missionsSc);

        //creates the outputs for the tasks
        Mission.startTask1(output1);
        Mission.startTask2(output2);

        //closing the input scanners and output printwriters
        airportSc.close();
        directionsSc.close();
        weatherSc.close();
        missionsSc.close();
        output1.close();
        output2.close();
    }
}