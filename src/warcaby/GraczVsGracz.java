package warcaby;   


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import polaczenie.Wydarzenie;

public class GraczVsGracz extends JPanel {

	private static final long serialVersionUID = 1L;

	public int[][] plansza =   {{ 0, 2, 0, 2, 0, 2, 0, 2 }, 
								{ 2, 0, 2, 0, 2, 0, 2, 0 }, 
								{ 0, 2, 0, 2, 0, 2, 0, 2 },
								{ 3, 0, 3, 0, 3, 0, 3, 0 }, 
								{ 0, 3, 0, 3, 0, 3, 0, 3 }, 
								{ 1, 0, 1, 0, 1, 0, 1, 0 },
								{ 0, 1, 0, 1, 0, 1, 0, 1 }, 
								{ 1, 0, 1, 0, 1, 0, 1, 0 }};

	public int[][] podswietlanie =  {{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
									{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
									{ 0, 0, 0, 0, 0, 0, 0, 0 },
									{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
									{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
									{ 0, 0, 0, 0, 0, 0, 0, 0 },
									{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
									{ 0, 0, 0, 0, 0, 0, 0, 0 }};
	
	public int[][] bicie =  {{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
							{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
							{ 0, 0, 0, 0, 0, 0, 0, 0 },
							{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
							{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
							{ 0, 0, 0, 0, 0, 0, 0, 0 },
							{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
							{ 0, 0, 0, 0, 0, 0, 0, 0 }};
	public String[][] pola =	{{ "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1" }, 
								{ "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2" }, 
								{ "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3" },
								{ "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4" }, 
								{ "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5" }, 
								{ "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6" },
								{ "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7" }, 
								{ "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8" }};
	
	private int kolumna;
	private int wiersz;
	public int ruch = 1;
	private int poprzednieW;
	private int poprzednieK;
	private int wZbitego[] = new int[4];
	private int kZbitego[] = new int[4];
	private boolean bicieDamki = false;
	private boolean jestRuch = false;
	public int pionkiBiale = 12;
	public int pionkiCzarne = 12;
	
	public GraczVsGracz() {
		Dimension rozmiar = new Dimension(407, 407);
		setSize(rozmiar);
		setMinimumSize(rozmiar); //ustawia minimalny rozmiar
		setMaximumSize(rozmiar); //ustawia maksymalny rozmiar
		setPreferredSize(rozmiar); //ustawia preferowany rozmiar
		addMouseListener(new MouseAdapter() { //obsluga myszki
			@Override
			public void mousePressed(MouseEvent e) { //wywolywane po naciscnieciu myszka w odpowiednie miejsce opisane if
				if (e.getButton() == MouseEvent.BUTTON1 && Warcaby.getInstance().hasToken()) {
					wybierzPole(e.getX(), e.getY());
					zerujMacierz(bicie);
					if (ruch == 1)
					{
						if(wykryjBicie(-1,-1) == false)
						{
							if(jestRuch == false)
							{
								if(sprawdzRuch(1, 4) == false) {
									System.out.println("Koniec gry, wygra造 czerwone!!!");
									Plik.zapis("ruchy.txt", "Koniec gry, wygra造 czerwone!!!");
								}
							}
							
							podswietl1(wiersz, kolumna);
						}			
						else
						{
							podswietlBicie1(wiersz, kolumna);
						}
					}
					else if (ruch == 2)
						if(wykryjBicie(-1,-1) == false)
						{
							if(jestRuch == false)
							{
								if(sprawdzRuch(2, 5) == false) {
									System.out.println("Koniec gry, wygra造 bia貫!!!");
									Plik.zapis("ruchy.txt", "Koniec gry, wygra造 bia貫!!!");
								}
							}
							
							podswietl2(wiersz, kolumna);
						}
						else
							podswietlBicie2(wiersz, kolumna);
					repaint();
				}
			}
		});
	}

	
	
	public void paint(Graphics g) {
		rysujPlansze(g);
	}
	
	public void rysujLinie(Graphics2D g2) {
		for (int j = 0; j < 9; j++) {
			g2.setColor(Color.red);
			g2.fillRect(0, 0, 407, 407);
		}
	}
	
	public void rysujPlansze(Graphics g) {
		Image img = createImage(getSize().width, getSize().height);
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		rysujLinie(g2);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				switch (plansza[i][j]) {
				case 0:
					g2.setColor(Color.white);
					g2.fillRect(j + j * 50, i + i * 50, 50, 50);
					break;
				case 3:
					g2.setColor(Color.black);

					if (podswietlanie[i][j] == 2 || podswietlanie[i][j] == 9 || podswietlanie[i][j] == 10 || podswietlanie[i][j] == 11 || podswietlanie[i][j] == 12)
						g2.setColor(Color.pink);
					if (podswietlanie[i][j] == 1 || podswietlanie[i][j] == 5 || podswietlanie[i][j] == 6 || podswietlanie[i][j] == 7 || podswietlanie[i][j] == 8)
						g2.setColor(Color.yellow);

					g2.fillRect(j +j * 50, i + i * 50, 50, 50);
					break;
				case 2:
					g2.setColor(Color.black);
					g2.fillRect(j +j * 50, i +i * 50, 50, 50);
					g2.setColor(Color.red);

					if (podswietlanie[i][j] == 4)
						g2.setColor(Color.pink);

					g2.fillOval(j + 3 +j * 50, i + 3 +i * 50, 44, 44);

					break;
				case 1:
					g2.setColor(Color.black);
					g2.fillRect(j +j * 50, i +i * 50, 50, 50);
					g2.setColor(Color.LIGHT_GRAY);

					if (podswietlanie[i][j] == 3)
						g2.setColor(Color.yellow);

					g2.fillOval(j + 3 + j * 50, i + 3 +i * 50, 44, 44);

					break;
				case 4:
					g2.setColor(Color.black);
					g2.fillRect(j +j * 50, i + i * 50, 50, 50);
			
					g2.setColor(Color.LIGHT_GRAY);

					if (podswietlanie[i][j] == 3)
						g2.setColor(Color.yellow);

					g2.fillOval(j + 3 + j * 50, i + 3 + i * 50, 44, 44);
					
					g2.setColor(Color.black);
					g2.fillOval(j + 15 + j * 50, i + 15 + i * 50, 20, 20);
					break;
				case 5:
					g2.setColor(Color.black);
					g2.fillRect(j + j * 50, i + i * 50, 50, 50);
					g2.setColor(Color.red);

					if (podswietlanie[i][j] == 4)
						g2.setColor(Color.pink);

					g2.fillOval(j + 3 + j * 50, i + 3 + i * 50, 44, 44);
					g2.setColor(Color.black);
					g2.fillOval(j + 15 + j * 50, i + 15 + i * 50, 20, 20);
					break;
				}
			}
		}
		g.drawImage(img, 0, 0, this);
	}
	
	public void wybierzPole(int x, int y) {
		if (x > 0 && x < 400 && y > 0 && y < 400) {

			wiersz = y / 50;
			kolumna = x / 50;

		}
	}
	
	public void zerujMacierz(int macierz[][])
	{
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				macierz[i][j] = 0;
			}
		}
	}
	public void wypiszMacierz(int macierz[][])
	{
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.println(macierz[i][j]);
			}
		}
	}
	public boolean sprawdzMacierz(int macierz[][], int liczba, int liczba2)
	{
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(macierz[i][j] == liczba || macierz[i][j] == liczba2)
					return true;
			}
		}
		return false;
	}
	public boolean sprawdzRuch(int liczba, int liczba2)
	{
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(plansza[i][j] == liczba || plansza[i][j] == liczba2)
				{
					if(liczba == 1)
						podswietl1(i, j);
					else if(liczba == 2)
						podswietl2(i, j);
					for (int w = 0; w < 8; w++) {
						for (int k = 0; k < 8; k++) {
							if(podswietlanie[w][k] == liczba)
							{
								zerujMacierz(podswietlanie);
								jestRuch = true;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	public boolean sprawdzBicie(int w, int k, int przeciwnik, boolean wykrytoBicie2)
	{
		boolean wykrytoBicie = wykrytoBicie2;
		
		if(plansza[w][k] == ruch+3)
		{
			int wi = w;
			int kol = k;
			while(wi > 1 && kol > 1)
			{
				wi--;
				kol--;
		
				if(plansza[wi][kol] == ruch || plansza[wi][kol] == ruch+3)
					break;
				if(plansza[wi][kol] == przeciwnik || plansza[wi][kol] == przeciwnik + 3)
				{
					if(plansza[wi-1][kol-1] != 3)
						break;
					else
					{
						bicie[w][k] = bicie[w][k] + 1;
						wykrytoBicie = true;
						bicieDamki = true;
						break;
					}
				}
			}
			wi = w;
			kol = k;
			while(wi > 1 && kol < 6)
			{
				
				wi--;
				kol++;
			
				if(plansza[wi][kol] == ruch || plansza[wi][kol] == ruch+3)
					break;
				if(plansza[wi][kol] == przeciwnik || plansza[wi][kol] == przeciwnik + 3)
				{
					if(plansza[wi-1][kol+1] != 3)
						break;
					else
					{
						bicie[w][k] = bicie[w][k] + 2;
						wykrytoBicie = true;
						bicieDamki = true;
						break;
					}
				}
				
			}
			wi = w;
			kol = k;
			while(wi < 6 && kol > 1)
			{
				wi++;
				kol--;
				
				if(plansza[wi][kol] == ruch || plansza[wi][kol] == ruch+3)
					break;
				if(plansza[wi][kol] == przeciwnik || plansza[wi][kol] == przeciwnik + 3)
				{
					if(plansza[wi+1][kol-1] != 3)
						break;
					else
					{
						bicie[w][k] = bicie[w][k] + 8;
						wykrytoBicie = true;
						bicieDamki = true;
						break;
					}
				}
				
			}
			wi = w;
			kol = k;
			while(wi < 6 && kol < 6)
			{
				wi++;
				kol++;
				if(plansza[wi][kol] == ruch || plansza[wi][kol] == ruch+3)
					break;
				if(plansza[wi][kol] == przeciwnik || plansza[wi][kol] == przeciwnik + 3)
				{
					if(plansza[wi+1][kol+1] != 3)
						break;
					else
					{
						bicie[w][k] = bicie[w][k] + 4;
						wykrytoBicie = true;
						bicieDamki = true;
						break;
					}
				}
			}
		}
		if(plansza[w][k] == ruch && bicieDamki == false)
		{
			if(w < 2 && k < 2)
			{
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
				}
			}
			else if(w < 2 && k > 5)
			{
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
				}
			}
			else if(w < 2 && k > 1 && k < 6)
			{
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
				}
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
				}
			}
			else if(w > 1 && w < 6 && k < 2)
			{
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3)&& plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
				}
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
				}
			}
			else if(w > 1 && w < 6 && k > 5)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
				}
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
				}
			}
			else if(w > 1 && w < 6 && k > 1 && k < 6)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
				}
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
				}
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3) && plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
				}
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
				}
			}
			else if(w > 5 && k < 2)
			{
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3) && plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
				}
			}
			else if(w > 5 && k > 5)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
				}
			}
			else if(w > 5 && k > 1 && k < 6)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
				}
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3) && plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
				}
			}
		}
		return wykrytoBicie;
	}
	public boolean wykryjBicie(int i, int j)
	{
		int przeciwnik = 1;
		boolean wykrytoBicie = false;
		
		if(ruch == 1)
			przeciwnik = 2;
		if(i > 0)
		{
			wykrytoBicie = sprawdzBicie(i,j,przeciwnik,wykrytoBicie);
		}
		else
		{
			for (int w = 0; w < 8; w++) {
				for (int k = 0; k < 8; k++) {
					wykrytoBicie = sprawdzBicie(w,k,przeciwnik,wykrytoBicie);
				}
			}
		}
		return wykrytoBicie;
	}
	public void podswietl1(int w, int k)
	{
		if(plansza[w][k] == 1)
		{
			zerujMacierz(podswietlanie);
			if(k < 7)
			{
				if(plansza[w-1][k+1] == 3)
				{
					podswietlanie[w-1][k+1] = 1;
					podswietlanie[w][k] = 3;
					poprzednieW = w;
					poprzednieK = k;
				}
			}
			if(k > 0)
			{
				if(plansza[w-1][k-1] == 3)
				{
					podswietlanie[w-1][k-1] = 1;
					podswietlanie[w][k] = 3;
					poprzednieW = w;
					poprzednieK = k;
				}
			}
		}
		else if(plansza[w][k] == 4)
		{
			zerujMacierz(podswietlanie);
			int wi = w;
			int kol = k;
			
			while(wi > 0 && kol > 0)
			{
				wi--;
				kol--;
	
				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 1;
					podswietlanie[w][k] = 3;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			wi = w;
			kol = k;
			while(wi > 0 && kol < 7)
			{
				wi--;
				kol++;
		
				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 1;
					podswietlanie[w][k] = 3;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			wi = w;
			kol = k;
			while(wi < 7 && kol > 0)
			{
				wi++;
				kol--;
		
				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 1;
					podswietlanie[w][k] = 3;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			wi = w;
			kol = k;
			while(wi < 7 && kol < 7)
			{
				wi++;
				kol++;
				
				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 1;
					podswietlanie[w][k] = 3;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			
		}
		if(podswietlanie[w][k] == 1)
		{
			zerujMacierz(podswietlanie);
			
			if(w != 0 && plansza[poprzednieW][poprzednieK] == 1)
				plansza[w][k] = 1;
			else
				plansza[w][k] = 4;
			
			plansza[poprzednieW][poprzednieK] = 3;
			System.out.println("Ruch BIAΒ - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Plik.zapis("ruchy.txt", "Ruch BIAΒ - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			
			Warcaby.getInstance().setToken(false);
			Wydarzenie ge = new Wydarzenie(Wydarzenie.RUCH_KLIENT); //utworzenie wydarzena strza逝
			ge.setWiadomosc(plansza[0][1] + "|" + plansza[0][3] + "|" + plansza[0][5] + "|" + plansza[0][7] + "|" + 
					  plansza[1][0] + "|" + plansza[1][2] + "|" + plansza[1][4] + "|" + plansza[1][6] + "|" + 
					  plansza[2][1] + "|" + plansza[2][3] + "|" + plansza[2][5] + "|" + plansza[2][7] + "|" + 
					  plansza[3][0] + "|" + plansza[3][2] + "|" + plansza[3][4] + "|" + plansza[3][6] + "|" + 
					  plansza[4][1] + "|" + plansza[4][3] + "|" + plansza[4][5] + "|" + plansza[4][7] + "|" + 
					  plansza[5][0] + "|" + plansza[5][2] + "|" + plansza[5][4] + "|" + plansza[5][6] + "|" + 
					  plansza[6][1] + "|" + plansza[6][3] + "|" + plansza[6][5] + "|" + plansza[6][7] + "|" + 
					  plansza[7][0] + "|" + plansza[7][2] + "|" + plansza[7][4] + "|" + plansza[7][6] + "|" + 1 + "|" + -1); //ustawienie i wyslanie wiadomosci do preciwnego gracza
			Warcaby.getInstance().sendMessage(ge);
			jestRuch = false;
			ruch = 2;
		}
	}
	
	public void podswietl2(int w, int k)
	{
		if(plansza[w][k] == 2)
		{
			zerujMacierz(podswietlanie);
			if(k < 7)
			{
				if(plansza[w+1][k+1] == 3)
				{
					podswietlanie[w+1][k+1] = 2;
					podswietlanie[w][k] = 4;
					poprzednieW = w;
					poprzednieK = k;
				}
			}
			if(k > 0)
			{
				if(plansza[w+1][k-1] == 3)
				{
					podswietlanie[w+1][k-1] = 2;
					podswietlanie[w][k] = 4;
					poprzednieW = w;
					poprzednieK = k;
				}
			}
		}
		else if(plansza[w][k] == 5)
		{
			zerujMacierz(podswietlanie);
			int wi = w;
			int kol = k;
			
			while(wi > 0 && kol > 0)
			{
				wi--;
				kol--;

				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 2;
					podswietlanie[w][k] = 4;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			wi = w;
			kol = k;
			while(wi > 0 && kol < 7)
			{
				wi--;
				kol++;
				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 2;
					podswietlanie[w][k] = 4;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			wi = w;
			kol = k;
			while(wi < 7 && kol > 0)
			{
				wi++;
				kol--;

				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 2;
					podswietlanie[w][k] = 4;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			wi = w;
			kol = k;
			while(wi < 7 && kol < 7)
			{
				wi++;
				kol++;

				if(plansza[wi][kol] == 3)
				{
					podswietlanie[wi][kol] = 2;
					podswietlanie[w][k] = 4;
					poprzednieW = w;
					poprzednieK = k;	
				}
				else
					break;
			}
			
		}
		if(podswietlanie[w][k] == 2)
		{
			zerujMacierz(podswietlanie);
			
			if(w != 7 && plansza[poprzednieW][poprzednieK] == 2)
				plansza[w][k] = 2;
			else
				plansza[w][k] = 5;
			plansza[poprzednieW][poprzednieK] = 3;
			System.out.println("Ruch CZERWONE - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Plik.zapis("ruchy.txt", "Ruch CZERWONE - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Warcaby.getInstance().setToken(false);
			Wydarzenie ge = new Wydarzenie(Wydarzenie.RUCH_KLIENT); //utworzenie wydarzena strza逝
			ge.setWiadomosc(plansza[0][1] + "|" + plansza[0][3] + "|" + plansza[0][5] + "|" + plansza[0][7] + "|" + 
					  plansza[1][0] + "|" + plansza[1][2] + "|" + plansza[1][4] + "|" + plansza[1][6] + "|" + 
					  plansza[2][1] + "|" + plansza[2][3] + "|" + plansza[2][5] + "|" + plansza[2][7] + "|" + 
					  plansza[3][0] + "|" + plansza[3][2] + "|" + plansza[3][4] + "|" + plansza[3][6] + "|" + 
					  plansza[4][1] + "|" + plansza[4][3] + "|" + plansza[4][5] + "|" + plansza[4][7] + "|" + 
					  plansza[5][0] + "|" + plansza[5][2] + "|" + plansza[5][4] + "|" + plansza[5][6] + "|" + 
					  plansza[6][1] + "|" + plansza[6][3] + "|" + plansza[6][5] + "|" + plansza[6][7] + "|" + 
					  plansza[7][0] + "|" + plansza[7][2] + "|" + plansza[7][4] + "|" + plansza[7][6] + "|" + 1 + "|" + -1); //ustawienie i wyslanie wiadomosci do preciwnego gracza
			Warcaby.getInstance().sendMessage(ge);
			jestRuch = false;
			ruch = 1;
		}
	}
	
	public void ustawieniaPoczatkowe() {
		plansza [0][1] = 2;
		plansza [0][3] = 2;
		plansza [0][5] = 2;
		plansza [0][7] = 2;
		plansza [1][0] = 2;
		plansza [1][2] = 2;
		plansza [1][4] = 2;
		plansza [1][6] = 2;
		plansza [2][1] = 2;
		plansza [2][3] = 2;
		plansza [2][5] = 2;
		plansza [2][7] = 2;
		plansza [5][0] = 1;
		plansza [5][2] = 1;
		plansza [5][4] = 1;
		plansza [5][6] = 1;
		plansza [6][1] = 1;
		plansza [6][3] = 1;
		plansza [6][5] = 1;
		plansza [6][7] = 1;
		plansza [7][0] = 1;
		plansza [7][2] = 1;
		plansza [7][4] = 1;
		plansza [7][6] = 1;
		plansza [4][1] = 3;
		plansza [4][3] = 3;
		plansza [4][5] = 3;
		plansza [4][7] = 3;
		plansza [3][0] = 3;
		plansza [3][2] = 3;
		plansza [3][4] = 3;
		plansza [3][6] = 3;
		ruch = 1;
		pionkiBiale = 12;
		pionkiCzarne = 12;
		zerujMacierz(bicie);
		zerujMacierz(podswietlanie);
	}
	public void podswietlBicie1(int w, int k) //tutaj
	{
	/*for(int i=0;i<4;i++)
	{
		wZbitego[i] = -1;
		kZbitego[i] = -1;
	} */
		if(bicie[w][k] > 0 && plansza[w][k] == 4)
		{
			int wi;
			int kol;
			boolean moznaPodswietlac;
			zerujMacierz(podswietlanie);
			if(bicie[w][k] == 1 || bicie[w][k] == 3 || bicie[w][k] == 5 || bicie[w][k] == 9 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				while(wi > 0 && kol > 0)
				{
					wi--;
					kol--;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 5;
					if(plansza[wi][kol] == 2 || plansza[wi][kol] == 5)
					{
						wZbitego[0] = wi;
						kZbitego[0] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
			if(bicie[w][k] == 2 || bicie[w][k] == 3 || bicie[w][k] == 6 || bicie[w][k] == 10 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				while(wi > 0 && kol < 7)
				{
					wi--;
					kol++;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 6;
					if(plansza[wi][kol] == 2 || plansza[wi][kol] == 5)
					{
						wZbitego[1] = wi;
						kZbitego[1] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
			if(bicie[w][k] == 4 || bicie[w][k] == 5 || bicie[w][k] == 6 || bicie[w][k] == 12 || bicie[w][k] == 7 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				while(wi < 7 && kol < 7)
				{
					wi++;
					kol++;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 7;
					if(plansza[wi][kol] == 2 || plansza[wi][kol] == 5)
					{
						wZbitego[2] = wi;
						kZbitego[2] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
			if(bicie[w][k] == 8 || bicie[w][k] == 9 || bicie[w][k] == 10 || bicie[w][k] == 12 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				while(wi < 7 && kol > 0)
				{
					wi++;
					kol--;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 8;
					if(plansza[wi][kol] == 2 || plansza[wi][kol] == 5)
					{
						wZbitego[3] = wi;
						kZbitego[3] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
		}
		else if(bicie[w][k] > 0 && plansza[w][k] == 1)
		{
			zerujMacierz(podswietlanie);
			if(bicie[w][k] == 1 || bicie[w][k] == 3 || bicie[w][k] == 5 || bicie[w][k] == 9 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 15)
			{
				podswietlanie[w-2][k-2] = 5;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[0] = w-1;
				kZbitego[0] = k-1;
			}
			if(bicie[w][k] == 2 || bicie[w][k] == 3 || bicie[w][k] == 6 || bicie[w][k] == 10 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				podswietlanie[w-2][k+2] = 6;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[1] = w-1;
				kZbitego[1] = k+1;
			}
			if(bicie[w][k] == 4 || bicie[w][k] == 5 || bicie[w][k] == 6 || bicie[w][k] == 12 || bicie[w][k] == 7 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				podswietlanie[w+2][k+2] = 7;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[2] = w+1;
				kZbitego[2] = k+1;
			}
			if(bicie[w][k] == 8 || bicie[w][k] == 9 || bicie[w][k] == 10 || bicie[w][k] == 12 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				podswietlanie[w+2][k-2] = 8;
				podswietlanie[w][k] = 3;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[3] = w+1;
				kZbitego[3] = k-1;
			}
		}
		repaint();
		int n = -1;
		if(podswietlanie[w][k] == 5)
			n = 0;
		if(podswietlanie[w][k] == 6)
			n = 1;
		if(podswietlanie[w][k] == 7)
			n = 2;
		if(podswietlanie[w][k] == 8)
			n = 3;
		if(n > -1)
		{
			zerujMacierz(podswietlanie);
			plansza[wZbitego[n]][kZbitego[n]] = 3;
			if(plansza[poprzednieW][poprzednieK] == 1)
				plansza[w][k] = 1;
			else
				plansza[w][k] = 4;
			
			plansza[poprzednieW][poprzednieK] = 3;
			System.out.println("Bicie BIAΒ - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Plik.zapis("ruchy.txt", "Bicie BIAΒ - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			
			repaint();
			bicieDamki = false;
			zerujMacierz(bicie);
			pionkiCzarne--;
			if(wykryjBicie(w,k) == false)
			{
				if(w == 0)
					plansza[w][k] = 4;
				
				if(sprawdzMacierz(plansza, 2, 5) == false) {
					System.out.println("Koniec gry, wygra造 bia貫!!!");
					Plik.zapis("ruchy.txt", "Koniec gry, wygra造 bia貫!!!");
				}
				ruch = 2;
				Warcaby.getInstance().setToken(false);
				
				Wydarzenie ge = new Wydarzenie(Wydarzenie.RUCH_KLIENT); //utworzenie wydarzena strza逝
				ge.setWiadomosc(plansza[0][1] + "|" + plansza[0][3] + "|" + plansza[0][5] + "|" + plansza[0][7] + "|" + 
						  plansza[1][0] + "|" + plansza[1][2] + "|" + plansza[1][4] + "|" + plansza[1][6] + "|" + 
						  plansza[2][1] + "|" + plansza[2][3] + "|" + plansza[2][5] + "|" + plansza[2][7] + "|" + 
						  plansza[3][0] + "|" + plansza[3][2] + "|" + plansza[3][4] + "|" + plansza[3][6] + "|" + 
						  plansza[4][1] + "|" + plansza[4][3] + "|" + plansza[4][5] + "|" + plansza[4][7] + "|" + 
						  plansza[5][0] + "|" + plansza[5][2] + "|" + plansza[5][4] + "|" + plansza[5][6] + "|" + 
						  plansza[6][1] + "|" + plansza[6][3] + "|" + plansza[6][5] + "|" + plansza[6][7] + "|" + 
						  plansza[7][0] + "|" + plansza[7][2] + "|" + plansza[7][4] + "|" + plansza[7][6]  + "|" + 2 + "|" + pionkiCzarne); //ustawienie i wyslanie wiadomosci do preciwnego gracza
				Warcaby.getInstance().sendMessage(ge);
			}
			else
				podswietlBicie1(w,k);
		}
	}
	public void podswietlBicie2(int w, int k)
	{
		if(bicie[w][k] > 0 && plansza[w][k] == 5)
		{
			zerujMacierz(podswietlanie);
			int wi;
			int kol;
			boolean moznaPodswietlac;
			if(bicie[w][k] == 1 || bicie[w][k] == 3 || bicie[w][k] == 5 || bicie[w][k] == 9 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				while(wi > 0 && kol > 0)
				{
					wi--;
					kol--;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 9;
					if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
					{
						wZbitego[0] = wi;
						kZbitego[0] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
			if(bicie[w][k] == 2 || bicie[w][k] == 3 || bicie[w][k] == 6 || bicie[w][k] == 10 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				while(wi > 0 && kol < 7)
				{
					wi--;
					kol++;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 10;
					if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
					{
						wZbitego[1] = wi;
						kZbitego[1] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
			if(bicie[w][k] == 4 || bicie[w][k] == 5 || bicie[w][k] == 6 || bicie[w][k] == 12 || bicie[w][k] == 7 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				while(w < 7 && k < 7)
				{
					wi++;
					kol++;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 11;
					if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
					{
						wZbitego[2] = wi;
						kZbitego[2] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
			if(bicie[w][k] == 8 || bicie[w][k] == 9 || bicie[w][k] == 10 || bicie[w][k] == 12 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				wi = w;
				kol = k;
				moznaPodswietlac = false;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				while(wi < 7 && kol > 0)
				{
					wi++;
					kol--;
					if(moznaPodswietlac == true && plansza[wi][kol] != 3)
						break;
					if(moznaPodswietlac == true)
						podswietlanie[wi][kol] = 12;
					if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
					{
						wZbitego[3] = wi;
						kZbitego[3] = kol;
						moznaPodswietlac = true;
					}
				}	
			}
		}
		else if(bicie[w][k] > 0 && plansza[w][k] == 2)
		{
			zerujMacierz(podswietlanie);
			if(bicie[w][k] == 1 || bicie[w][k] == 3 || bicie[w][k] == 5 || bicie[w][k] == 9 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 15)
			{
				podswietlanie[w-2][k-2] = 9;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[0] = w-1;
				kZbitego[0] = k-1;
			}
			if(bicie[w][k] == 2 || bicie[w][k] == 3 || bicie[w][k] == 6 || bicie[w][k] == 10 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				podswietlanie[w-2][k+2] = 10;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[1] = w-1;
				kZbitego[1] = k+1;
			}
			if(bicie[w][k] == 4 || bicie[w][k] == 5 || bicie[w][k] == 6 || bicie[w][k] == 12 || bicie[w][k] == 7 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				podswietlanie[w+2][k+2] = 11;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[2] = w+1;
				kZbitego[2] = k+1;
			}
			if(bicie[w][k] == 8 || bicie[w][k] == 9 || bicie[w][k] == 10 || bicie[w][k] == 12 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
			{
				podswietlanie[w+2][k-2] = 12;
				podswietlanie[w][k] = 4;
				poprzednieW = w;
				poprzednieK = k;
				wZbitego[3] = w+1;
				kZbitego[3] = k-1;
			}
		}
		repaint();
		int n = -1;
		if(podswietlanie[w][k] == 9)
			n = 0;
		if(podswietlanie[w][k] == 10)
			n = 1;
		if(podswietlanie[w][k] == 11)
			n = 2;
		if(podswietlanie[w][k] == 12)
			n = 3;
		if(n > -1)
		{
			zerujMacierz(podswietlanie);
			plansza[wZbitego[n]][kZbitego[n]] = 3;
			if(plansza[poprzednieW][poprzednieK] == 2)
				plansza[w][k] = 2;
			else
				plansza[w][k] = 5;
			
			plansza[poprzednieW][poprzednieK] = 3;
			System.out.println("Bicie CZERWONE - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Plik.zapis("ruchy.txt", "Bicie CZERWONE - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);

			

			repaint();
			bicieDamki = false;
			zerujMacierz(bicie);
			pionkiBiale--;
			if(wykryjBicie(w,k) == false)
			{
				if(w == 7)
					plansza[w][k] = 5;
				if(sprawdzMacierz(plansza, 1, 4) == false) {
					System.out.println("Koniec gry, wygra造 czerwone!!!");
					Plik.zapis("ruchy.txt", "Koniec gry, wygra造 czerwone!!!");
				}
				ruch = 1;
				Warcaby.getInstance().setToken(false);
				Wydarzenie ge = new Wydarzenie(Wydarzenie.RUCH_KLIENT); //utworzenie wydarzena strza逝
				ge.setWiadomosc(plansza[0][1] + "|" + plansza[0][3] + "|" + plansza[0][5] + "|" + plansza[0][7] + "|" + 
							  plansza[1][0] + "|" + plansza[1][2] + "|" + plansza[1][4] + "|" + plansza[1][6] + "|" + 
							  plansza[2][1] + "|" + plansza[2][3] + "|" + plansza[2][5] + "|" + plansza[2][7] + "|" + 
							  plansza[3][0] + "|" + plansza[3][2] + "|" + plansza[3][4] + "|" + plansza[3][6] + "|" + 
							  plansza[4][1] + "|" + plansza[4][3] + "|" + plansza[4][5] + "|" + plansza[4][7] + "|" + 
							  plansza[5][0] + "|" + plansza[5][2] + "|" + plansza[5][4] + "|" + plansza[5][6] + "|" + 
							  plansza[6][1] + "|" + plansza[6][3] + "|" + plansza[6][5] + "|" + plansza[6][7] + "|" + 
							  plansza[7][0] + "|" + plansza[7][2] + "|" + plansza[7][4] + "|" + plansza[7][6] + "|" + 2 + "|" + pionkiBiale); //ustawienie i wyslanie wiadomosci do preciwnego gracza
				Warcaby.getInstance().sendMessage(ge);	
			}
			else
				podswietlBicie2(w,k);
		}
		
	}
}
