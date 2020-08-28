import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.CharBuffer;

/**
 * "Spieler" class
 * @author Nico
 * @version 1.0.0
 */
public class Spieler {
   
    private Socket socket;
    private DataOutputStream outToServer;
    private InputStreamReader inFromServer;

    
    /**
     * Constructor of "Spieler" class
     * @author Nico
     * @version 1.0.0
     */
    public Spieler (String adresse, int port) throws IOException 
    {
        this.socket = new Socket(adresse, port);
        this.socket.setKeepAlive(true);
        this.outToServer = new DataOutputStream(socket.getOutputStream());
        this.inFromServer = new InputStreamReader(socket.getInputStream());

        while (true)
        {
            BufferedReader bf = new BufferedReader(this.inFromServer);
            String read = "";
            while(true) { 
            	read = bf.readLine();
            	String s = read;
            	 
            	if(s.equals("GIVE ME NUMBER"))
            	{
            		int rateZahl = rateZahl();
            		this.outToServer.writeBytes(Integer.toString(rateZahl)+"\r\n");
            	}
            	else
            	{
            		System.out.println(s);
            	}
            }
        }
    }
    
    public static void main (String[] args) throws IOException
    {
        //TODO check args
    	String adresse = args[0];
        /*final String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
         
        if(!adresse.matches(PATTERN)){
            throw new IllegalArgumentException("first argument not a valid ip");
        }*/

        int port = Integer.parseInt(args[1]);


        Spieler spieler = new Spieler(adresse, port);
    }

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