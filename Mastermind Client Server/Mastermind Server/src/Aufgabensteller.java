/**
 * @author Nico
 * @version 1.0.0
 */
public class Aufgabensteller 
{
	/**
	 * die zufällige Zahl die generiert wird
	 * 
	 * @author Nico
	 * @version 1.0.0
	 */
	private int geheimzahl;
    public int getGeheimzahl()
    {
        return this.geheimzahl;
    }
	/**
	 * @author Nico
	 * @version 1.0.0
	 */
	public Aufgabensteller () {}
	
	/**
	 * erzeugt eine vierstellige Zahl gemäß 
	 * Aufgabenstellung und speichert sie im Attribut geheimzahl. 
	 * 
	 * Die Geheimzahl kann alle Ziffern von 0 bis 9 enthalten, jedoch darf keine Ziffer
	 * zwei- oder mehrmals enthalten sein. 
	 * 
	 * @author Nico
	 * @return nothing to see here
	 */
	public void erzeugeGeheimzahl () 
	{
		int[] zahlen = new int[4];
		for(int i = 0 ; i < 4 ; ++i)
		{
			int zahl;
			boolean rejected;
			do
			{
				rejected = false;
				zahl = (int) (Math.random() * 10);
				for (int j = 0 ; j < i ; ++j)
				{
					if(zahl == zahlen[j]) 
                    {
                        rejected = true;
                    }
				}
			} 
			while(rejected);
			zahlen[i] = zahl;
		}		
		String s = "";
        for (int i : zahlen)
        {
            s += String.valueOf(i);
        }
		this.geheimzahl = Integer.parseInt(s);
	}

	/**
	 * zerlegt eine als Argument übergebene Zahl
	 * in ihre Ziffern und gibt diese 
	 * als Elemente eines Felds zurück.
	 * 
	 * @author Nico
	 * @version 1.0.0
	 * @param zahl the number to disassemble
	 * @return the individual digits of the zahl
	 * @throws IllegalArgumentException if the number is less than 0 or bigger tan 9999 (the number has more than 4 digits)
	 */
	private int[] zerlegeZahl (int zahl) throws IllegalArgumentException
	{
		if (zahl > 9999 || zahl < 0)
			throw new IllegalArgumentException();
	   
		int[] zerlegt = new int[4];
	   
		for(int i = 3 ; i > -1 ; --i)
		{
			zerlegt[i] = zahl % 10;
			zahl /= 10;
		}
		return zerlegt;
	}

	/**
	 * erhält als Argument die vom Spieler eingegebene Ratezahl, ermittelt 
	 * die direkten und indirekten Treffer durch Vergleich der Ziffern von Geheimzahl und Ratezahl
	 * unter Verwendung der Methode zerlegeZahl, speichert die Ratezahl sowie die direkten
	 * und indirekten Treffer in einem Objekt vom Typ Versuch und liefert dieses an den Aufrufer
	 * zurück.
	 * 
	 * @author Tarik, Nico
	 * @version 1.0.0
	 * @param eingabeZahl the number that the player guessed
	 * @return a object of type {@link Versuch} which is useless
	 */
	public Versuch bewerteRatezahl (int eingabeZahl)
	{
        final int[] geheimzahlZerlegt = zerlegeZahl(this.geheimzahl);
		final int[] eingabezahlZerlegt = zerlegeZahl(eingabeZahl);
		int direkteTreffer = 0;
		int indirekteTreffer = 0;
        for(int i = 0; i < geheimzahlZerlegt.length; i++)
        {
            if(geheimzahlZerlegt[i] == eingabezahlZerlegt[i])
            {
                direkteTreffer++;
            }
            for(int x = 0; x < eingabezahlZerlegt.length; x++)
            {
                if(x != i && eingabezahlZerlegt[x] == geheimzahlZerlegt[i])
                {                  
                    indirekteTreffer++;
                }
            }
        }
		return new Versuch(eingabeZahl, direkteTreffer, indirekteTreffer);
	}
}