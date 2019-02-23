package polaczenie;

import java.net.Socket;

public class Klient {
	
	private int port;
	private String host;
	private Polaczenie polaczenie = null;
	private Socket gniazdo;
	private String idGracza;
	
	public Klient(String nazwa, String host, int port) {
		this.idGracza = nazwa;
		this.port = port;
		this.host = host;
	}
	
	public String getIdGracza() {
		return idGracza;
	}
	
	public boolean polaczenieDostepne() {
		return (polaczenie != null && polaczenie.isAlive());
	}
	
	public void wyslijWiadomosc(Wydarzenie wydarzenie) { 
		polaczenie.wysijWiadomosc(wydarzenie.wyslijWiadomosc()); //wyslij wiadomosc o tworzeniu klienta
	}

	public Wydarzenie odbierzWiadomosc() { 
		if (polaczenie.kolejkaWiadomosci.isEmpty()) { //jesli kolejka wiadomosci jest pusta zwroc null
			return null;
		} else {
			Wydarzenie wydarzenie = new Wydarzenie((String) polaczenie.kolejkaWiadomosci.getFirst()); //wez pierwsza
			polaczenie.kolejkaWiadomosci.removeFirst(); //usun pierwsza
			return wydarzenie;
		}
	}
	
	public boolean start() {
		try {
			gniazdo = new Socket(host, port);
		} catch (Exception ex) {
			return false;
		}
		polaczenie = new Polaczenie(gniazdo);
		polaczenie.start();
		return true;
	}

	public void stop() {
		if (polaczenie != null)
			polaczenie.close();
	}
}