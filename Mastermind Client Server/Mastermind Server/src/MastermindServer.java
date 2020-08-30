import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Tarik
 * @version 1.0.0 erstellt neues Mastermind-Spiel und UI
 * @see Aufgabenbeschreibung
 */
public class MastermindServer {

	private final int maxAnzahlVersuche = 10;
	private final Spielbrett spielbrett = new Spielbrett();
	private final int port = 12004;
	ServerSocket socket;
	Socket clientListener;

	/**
	 * Erstellt eine neue Mastermind UI
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		new MastermindServer();
	}

	/**
	 * UI manager für das Spiel Mastermind und startet ein neues Spiel
	 * 
	 * @category UI
	 * @since 1.0.0
	 * @author Tarik
	 * @throws IOException          if an {@code IOException} occures in
	 *                              {@link Spieler#rateZahl() rateZahl}
	 * @throws InterruptedException
	 */
	public MastermindServer() throws IOException, InterruptedException {
		socket = new ServerSocket(port);
		System.out.println("Warte auf Verbindung...");
		while (true) {
			Socket listener = socket.accept();
			System.out.println("Test");
			Thread thread = new Thread(new Runnable() {				
				public void run() {
					try {
						Socket tcpListener = listener;
						tcpListener.setKeepAlive(true);
						System.out.println("Verbindung eingegangen");
						PrintStream myStream = new PrintStream(System.out) {
							@Override
							public void println(String message) {
								// System.out.println(message);
								DataOutputStream outToClient;
								try {
									outToClient = new DataOutputStream(tcpListener.getOutputStream());
									outToClient.writeBytes(message + "\r\n");
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void print(String message) {
								println(message);
							}
						};
						//System.setOut(myStream);
						myStream.println(
								"++++++++++++++++++++++++++++++++++MASTERMIND++++++++++++++++++++++++++++++++++");
						myStream.println(
								"+                 Bitte nur vierstellige Zahlen eingeben!!!                  +");
						myStream.println(
								"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
						myStream.println("");
						neuesSpiel(tcpListener, myStream);
						myStream.println(
								"*************************** Danke und Tschuess!!! ****************************");
						tcpListener.close();
					} catch (Exception ex) {

					}
				}
			});
			thread.start();
		}

	}

	/**
	 * setzt die im Spielbrett gespeicherten Versuche zurück und lässt vom
	 * Aufgabensteller eine Geheimzahl erzeugen. Der Spieler wird zur Eingabe der
	 * Ratezahl aufgefordert. Diese wird dann zur Bewertung an den Aufgabensteller
	 * weitergegeben. Resultat der Bewertung ist ein Objekt der Klasse Versuch.
	 * Anschließend wird das Versuchsobjekt dem Spielbrett zum Protokollieren
	 * übergeben und alle im Spielbrett gespeicherten Versuche werden angezeigt.
	 * Das Spiel wird beendet, wenn die Anzahl der im aktuellen Versuch
	 * gespeicherten direkten Treffer gleich vier ist oder die vorgegebene Anzahl
	 * der Versuche erreicht wurde.
	 * 
	 * @see Aufgabenstellung, Methodenhinweise
	 * @author Tarik, Nico
	 * @version 1.0.0
	 * @throws IOException if an {@code IOException} occures in
	 *                     {@link Spieler#rateZahl() rateZahl}
	 */
	public void neuesSpiel(Socket tcpListener, PrintStream myStream) throws IOException {
		Aufgabensteller aufgabensteller = new Aufgabensteller();
		Spieler spieler = new Spieler();

		this.spielbrett.ruecksetzenVersuche();
		aufgabensteller.erzeugeGeheimzahl();

		// Spielschleife, solange Spiel nicht beendet wird
		while (true) {
			int anzahlVersuche = this.spielbrett.getAnzahlVersuche();

			// Der Spieler gibt eine Zahl ein und diese wird bewertet
			myStream.println(String.format("Sie haben noch %d Versuch(e): ", maxAnzahlVersuche - anzahlVersuche));
			// Netzwerk
			BufferedReader bf = new BufferedReader(new InputStreamReader(tcpListener.getInputStream()));
			myStream.println("GIVE ME NUMBER");
			String input = bf.readLine();
			myStream.println(input);
			Versuch versuch = aufgabensteller.bewerteRatezahl(Integer.parseInt(input));
			spielbrett.protokolliereVersuch(versuch);
			anzahlVersuche++;
			// Wenn Treffer erzielt
			if (versuch.getDirekteTreffer() == 4) {
				myStream.println(String.format(
						"Volltreffer!!! Sie haben die Zahl im %d. Versuch erraten.\r\nMöchtest Sie noch mal spielen (1=JA - 2=NEIN)?\r\n",
						anzahlVersuche));
				// Nochmalspielen? 1=JA 2=NEIN
				// Netzwerk
				bf = new BufferedReader(new InputStreamReader(tcpListener.getInputStream()));
				myStream.println("GIVE ME NUMBER");
				input = bf.readLine();
				myStream.println(input);
				int antwort = Integer.parseInt(input);
				// System.out.println("GIVE ME NUMBER");
				// inFromClient = new DataInputStream(tcpListener.getInputStream());
				// int antwort = Integer.parseInt(inFromClient.readUTF());
				if (antwort == 1) {
					this.spielbrett.ruecksetzenVersuche();
					aufgabensteller.erzeugeGeheimzahl();
				} else if (antwort == 2) {
					return;
				} else {
					myStream.println("Layer-8-Fehler: Ung�ltige Eingabe");
					return;
				}
			}
			// Wenn kein Treffer
			else {
				// Alle Versuche ausgeben
				myStream.println(this.spielbrett.liefereVersuche());
				// Wenn keine Leben mehr
				if (maxAnzahlVersuche - anzahlVersuche == 0) {
					myStream.print(String.format(
							"Looser!!! Du hast verloren, was ein Pech. Die Zahl war: %d. \r\n\r\nM�chtest Sie noch mal spielen (1=JA - 2=NEIN)?\r\n",
							aufgabensteller.getGeheimzahl()));
					// Nochmalspielen? 1=JA 2=NEIN

					// Netzwerk
					bf = new BufferedReader(new InputStreamReader(tcpListener.getInputStream()));
					myStream.println("GIVE ME NUMBER");
					input = bf.readLine();
					myStream.println(input);
					int antwort = Integer.parseInt(input);
					if (antwort == 1) {
						this.spielbrett.ruecksetzenVersuche();
						aufgabensteller.erzeugeGeheimzahl();
					} else if (antwort == 2) {
						return;
					} else {
						myStream.println("Layer-8-Fehler: Ung�ltige Eingabe");
						return;
					}
				}
			}

		}

	}
}
