import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * "Spieler" class
 * @author Nico
 * @version 1.0.0
 */
public class Spieler {
   
    /**
     * Constructor of "Spieler" class
     * @author Nico
     * @version 1.0.0
     */
    public Spieler () {}
    
    /**
     * @author Nico
     * @version 1.0.0
     * @return the number that was entered
     * @throws IOException
     */
    public int rateZahl() throws IOException  
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) 
        {
            String input = br.readLine();
            try
            {
                int zahl = Integer.parseInt(input);
                if (zahl > -1 && zahl < 10000)
                    return zahl;
            }
            catch (NumberFormatException e) {}
            System.out.println("Bitte eine vierstellige Zahl eingeben!!! :(");
        }
    }
}