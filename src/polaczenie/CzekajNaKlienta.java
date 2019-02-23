package polaczenie;

import java.io.IOException;
import java.net.Socket;

public class CzekajNaKlienta extends Thread {
	
	private Serwer serwer;

	public CzekajNaKlienta(Serwer serwer) {
		this.serwer = serwer;
	}

	public void run() {
		while (serwer.czyUruchomiony()) {  //czekaj na ¿¹danie po³¹czenia klienta
			Socket gniazdoKlienta;
			try {
				gniazdoKlienta = serwer.gniazdoSerwera.accept(); //accept - ods³uchuje po³¹czenie, które ma byæ wykonane w tym gniezdzie i akceptuje je
				Polaczenie polaczenie = new Polaczenie(gniazdoKlienta); //// Poczekaj na ¿¹danie po³¹czenia klienta ,nowe polaczenie z gniazdem klienta
				serwer.getPolaczenia().add(polaczenie); 
				polaczenie.start();
			} catch (IOException e) {
			}
		}
	}
}