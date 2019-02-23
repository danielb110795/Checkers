package polaczenie;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Serwer {
	
	public NasluchiwaniePolaczenia nasluchiwaniePolaczenia;
	public ServerSocket gniazdoSerwera;
	private CzekajNaKlienta czekajNaKlienta;
	private LinkedList<Polaczenie> polaczenia;
	private boolean czyUruchomiony;
	private int port;

	public Serwer(int port) {
		this.port = port;
		czyUruchomiony = false;
	}

	public boolean czyUruchomiony() {
		return czyUruchomiony;
	}
	
	public int getRozmiarPolaczenia() {
		return polaczenia.size();
	}
	
	public LinkedList<Polaczenie> getPolaczenia() {
		return polaczenia;
	}
	
	public boolean start() {
		try {
			gniazdoSerwera = new ServerSocket(port);
		} catch (Exception ex) {
			return false;
		}
		
		czyUruchomiony = true;
		polaczenia = new LinkedList<Polaczenie>();
		nasluchiwaniePolaczenia = new NasluchiwaniePolaczenia(this);
		nasluchiwaniePolaczenia.start();
		czekajNaKlienta = new CzekajNaKlienta(this);
		czekajNaKlienta.start();
		return true;
	}

	public void stop() {
		czyUruchomiony = false;
		nasluchiwaniePolaczenia.interrupt(); //przerwij
		czekajNaKlienta.interrupt(); //przerwij
		try {
			for (int i = 0; i < polaczenia.size(); i++) {
				//zamyka wszystkie polaczenia
				Polaczenie polaczenie = (Polaczenie) polaczenia.get(i);
				polaczenie.close();
			}
			polaczenia.clear(); //wyczyszczenie listy
			gniazdoSerwera.close();
		} catch (IOException ex) {
		}
	}
}
