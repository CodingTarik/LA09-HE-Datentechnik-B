import java.io.IOException;

/**
 * @author Tarik
 * @version 1.0.0 erstellt neues Mastermind-Spiel und UI
 * @see Aufgabenbeschreibung
 */
public class MastermindUI {

    private final int maxAnzahlVersuche = 10;
    private final Spielbrett spielbrett = new Spielbrett();
    /**
     * Erstellt eine neue Mastermind UI
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new MastermindUI();
    }
    /**
     * UI manager für das Spiel Mastermind und startet ein neues Spiel
     * @category UI
     * @since 1.0.0
     * @author Tarik
     * @throws IOException if an {@code IOException} occures in {@link Spieler#rateZahl() rateZahl}
     */
    public MastermindUI() throws IOException
    {
        System.out.println("++++++++++++++++++++++++++++++++++MASTERMIND++++++++++++++++++++++++++++++++++");
        System.out.println("+                 Bitte nur vierstellige Zahlen eingeben!!!                  +");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("");
        neuesSpiel();
        System.out.println("*************************** Danke und Tschuess!!! ****************************");
    }

    /**
     * setzt die im Spielbrett gespeicherten Versuche zurück und lässt vom
     * Aufgabensteller eine Geheimzahl erzeugen. Der Spieler wird zur Eingabe der
     * Ratezahl aufgefordert. Diese wird dann zur Bewertung an den Aufgabensteller
     * weitergegeben. Resultat der Bewertung ist ein Objekt der Klasse Versuch.
     * Anschließend wird das Versuchsobjekt dem Spielbrett zum Protokollieren
     * übergeben und alle im Spielbrett gespeicherten Versuche werden angezeigt. Das
     * Spiel wird beendet, wenn die Anzahl der im aktuellen Versuch gespeicherten
     * direkten Treffer gleich vier ist oder die vorgegebene Anzahl der Versuche
     * erreicht wurde.
     * 
     * @see Aufgabenstellung, Methodenhinweise
     * @author Tarik, Nico
     * @version 1.0.0
     * @throws IOException if an {@code IOException} occures in {@link Spieler#rateZahl() rateZahl}
     */
    public void neuesSpiel() throws IOException
    {
        Aufgabensteller aufgabensteller = new Aufgabensteller();
        Spieler spieler = new Spieler();         
        
        this.spielbrett.ruecksetzenVersuche();
        aufgabensteller.erzeugeGeheimzahl();
        
        // Spielschleife, solange Spiel nicht beendet wird
        while(true)
        {   
            int anzahlVersuche = this.spielbrett.getAnzahlVersuche();
            
            // Der Spieler gibt eine Zahl ein und diese wird bewertet
            System.out.print(String.format("Sie haben noch %d Versuch(e): ", maxAnzahlVersuche - anzahlVersuche));
            Versuch versuch = aufgabensteller.bewerteRatezahl(spieler.rateZahl());
            System.out.println();
            spielbrett.protokolliereVersuch(versuch);
            anzahlVersuche++;
            // Wenn Treffer erzielt
            if(versuch.getDirekteTreffer() == 4)
            {
                System.out.print(String.format("Volltreffer!!! Sie haben die Zahl im %d. Versuch erraten.\r\nMöchtest Sie noch mal spielen (1=JA - 2=NEIN)?\r\n", anzahlVersuche));
                // Nochmalspielen? 1=JA 2=NEIN
                int antwort = spieler.rateZahl();
                if(antwort == 1)
                {                    
                    this.spielbrett.ruecksetzenVersuche();
                    aufgabensteller.erzeugeGeheimzahl();
                }
                else if(antwort == 2)
                {
                    return;
                }    
                else
                {
                    System.out.println("Layer-8-Fehler: Ungültige Eingabe");
                    return;
                }
            }
            // Wenn kein Treffer
            else
            {
                // Alle Versuche ausgeben
                System.out.println(this.spielbrett.liefereVersuche());
                // Wenn keine Leben mehr
                if(maxAnzahlVersuche - anzahlVersuche == 0)
                {
                    System.out.print(String.format("Looser!!! Du hast verloren, was ein Pech. Die Zahl war: %d. \r\n\r\nMöchtest Sie noch mal spielen (1=JA - 2=NEIN)?\r\n", aufgabensteller.getGeheimzahl()));  
                    // Nochmalspielen? 1=JA 2=NEIN  
                    int antwort = spieler.rateZahl();
                    if(antwort == 1)
                    {
                        this.spielbrett.ruecksetzenVersuche();
                        aufgabensteller.erzeugeGeheimzahl();
                    }
                    else if(antwort == 2)
                    {
                        return;
                    }    
                    else
                    {
                        System.out.println("Layer-8-Fehler: Ungültige Eingabe");
                        return;
                    }
                }
            }
             
        }  
        
    }
}
