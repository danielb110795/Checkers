package warcaby;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Plik {

	/*public static void stworzPlik(String path) {
		try {
			File file = new File(path);
			if (file.createNewFile()) {
				System.out.println("Plik zostal stworzony!");
			} else {
				System.out.println("Plik juz istnieje");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	public static void zapis(String path, String tekst) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(path, true); //true powoduje zapisanie kolejnej zawartoœci po tej co jest a nie jej nadpisanie
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(tekst + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public static void skasujPlik(String path) {
		try {
			File file = new File(path);

			if (file.delete()) {
				System.out.println(file.getName() + " zostal skasowany!");
			} else {
				System.out.println("Operacja kasowania sie nie powiodla.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 */
	
	/* Funkcje do odczytu i zapisu stanu gdy */
	public static void zapiszStanGry(int macierz[][], int poziom) throws FileNotFoundException {
		PrintWriter zapis = new PrintWriter("zapis.txt"); // tworzony obiekt PrintWriter, parametr - nazwa pliku

		zapis.println("Macierz");
		zapis.println(poziom);
		for (int i = 0; i < 8; i++) { //zapisanie w forach aktualnej macierzy do gry
			for (int j = 0; j < 8; j++)
				zapis.print(macierz[i][j] + "\t");
			zapis.println();
		}
		zapis.close();
	}

	public static int odczytajStanGry(int macierz[][]) throws FileNotFoundException {
		Scanner odczyt = new Scanner(new File("zapis.txt")); // tworzony obiekt Scanner
		System.out.println(odczyt.nextLine());
		int poziom = odczyt.nextInt();

		try {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					macierz[i][j] = odczyt.nextInt();
				}
			}
			odczyt.close();
		} catch (Exception e) {
			System.err.println("b³¹d odczytu danych");
			System.exit(0);
		}

		return poziom;
	}
}
