package polaczenie;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Polaczenie extends Thread {
	
	private BufferedReader zapis;
	private PrintWriter odczyt;
	private String nazwa = "";
	public LinkedList<String> kolejkaWiadomosci;
	private boolean dolaczony = false;
	private Socket gniazdo;

	public Polaczenie(Socket gniazdo) {
		try {
			zapis = new BufferedReader(new InputStreamReader(gniazdo.getInputStream()));
			odczyt = new PrintWriter(gniazdo.getOutputStream(), true);
		} catch (IOException ex) {
		}
		kolejkaWiadomosci = new LinkedList<String>();
		this.gniazdo = gniazdo;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public void setDolaczony(boolean b) {
		dolaczony = b;
	}
	
	public String getNazwa() {
		return nazwa;
	}

	public boolean getDolaczony() {
		return dolaczony;
	}
	
	public void wysijWiadomosc(String string) {
		odczyt.println(string);
	}

	public void run() {
		String string;
		try {
			while ((string = zapis.readLine()) != null) {
				kolejkaWiadomosci.add(string);
			}
			odczyt.close();
			zapis.close();
		} catch (IOException ex) {
		}
		try {
			gniazdo.close();
		} catch (Exception ex) {
		}
	}

	public void close() {
		try {
			gniazdo.close();
		} catch (Exception ex) {
		}
	}
}