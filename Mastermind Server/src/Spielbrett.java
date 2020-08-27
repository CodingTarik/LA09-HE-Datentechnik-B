import java.util.ArrayList;
import java.util.List;
/**
 * @author Nico & Tarik
 * Verwaltet ein Spielbrett vom Spiel Mastermind
 */
public class Spielbrett {
    private int anzahlVersuche = 0;
    private List<Versuch> versuchsListe = new ArrayList<>();
    
    public Spielbrett() {  }
    
    public int getAnzahlVersuche()
    {
        return anzahlVersuche;
    }
    /**
     *  bekommt als Argument ein Objekt der Klasse Versuch
     * übergeben und speichert dieses. 
     * @author Tarik
     */
    public void protokolliereVersuch(Versuch versuch)
    {
        anzahlVersuche++;       
        versuchsListe.add(versuch);     
    }

    /**
     * Gibt die Versuche in der Form VersuchNr | Ratezahl | direkte Treffer | indirekte Treffer zurück
     * @author Nico
     */
    public String liefereVersuche ()
    {
        String result = "VersuchNr  Ratezahl  direkte  indirekte Treffer\n";
        int i = 0;
        for (Versuch v : this.versuchsListe)
        {
            int ratezahl = v.getRateZahl();
            String rateString = String.valueOf(ratezahl);
            if(rateString.length() == 3)
            {
                rateString = "0" + rateString;
            }
            /* Wir haben keine Tabs mit \t wie in der Aufgabe verlangt verwendet, weil die Formatierung dadurch sehr schlecht war
             * d.h. haben wir Leerzeichen verwendet */
            result += "    " + (++i) + "        " + rateString + "       " + v.getDirekteTreffer() + "         " + v.getIndirekteTreffer() + "\n";
        }
        return result;
    }

    /**
     * Setzt alle Versuche zurück
     * @author Nico
     */
    public void ruecksetzenVersuche ()
    {
        this.versuchsListe.clear();
        this.anzahlVersuche = 0;
    }
}