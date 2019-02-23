package warcaby;

import java.io.*;

public class Konfiguracja {
	
	private static Konfiguracja instance = null; //instancja
	private String host = "localhost"; //nazwa hostu do polaczenia
	private int port = 4545; //port 
	private String nazwaPliku = "warcaby.ini"; //nazwa pliku konfiguracyjnego

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public static Konfiguracja getInstance() {
		if (instance == null) 
			instance = new Konfiguracja(); //je�li instancja nie istnieje stw�rz j�
		return instance;
	}
	
	public void zapisz() {
		try {
			PrintWriter zapis;
			zapis = new PrintWriter(new BufferedWriter(new FileWriter(nazwaPliku)));
			zapis.print("host=");
			zapis.println(getHost());
			zapis.print("port=");
			zapis.println(getPort());
			zapis.close();
		} catch (IOException e) {
		}
	}
}