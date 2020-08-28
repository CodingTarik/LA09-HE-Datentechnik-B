/**
 * @author Tarik
 * @version 1.0.0
 * @see UML
 */
public class Versuch
{
    private int ratezahl;
    private int direkteTreffer;
    private int indirekteTreffer;

    public Versuch(int ratezahl, int direkteTreffer, int indirekteTreffer)
    {
        this.ratezahl = ratezahl;
        this.direkteTreffer = direkteTreffer;
        this.indirekteTreffer = indirekteTreffer;
    }

    public int getRateZahl () 
    {
        return this.ratezahl;
    }

    public int getDirekteTreffer () 
    {
        return this.direkteTreffer;
    }

    public int getIndirekteTreffer () 
    {
        return this.indirekteTreffer;
    }

}