package warcaby;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Random;

import javax.swing.JPanel;

/*class Pionek
{
	public int w;
	public int k;
	public int poprzednieW;
	public int poprzednieK;
	
	public void setW(int w)
	{
		this.w = w;
	}
	public void setK(int k)
	{
		this.k = k;
	}
	public void setPoprzednieW(int poprzednieW)
	{
		this.poprzednieW = poprzednieW;
	}
	public void setPoprzednieK(int poprzednieK)
	{
		this.poprzednieK = poprzednieK;
	}
	public int getW()
	{
		return w;
	}
	public int getK()
	{
		return k;
	}
	public int getPoprzednieW()
	{
		return poprzednieW;
	}
	public int getPoprzednieK()
	{
		return poprzednieK;
	}
}*/

public class PoziomLatwy extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;

	public int[][] plansza =   {{ 0, 2, 0, 2, 0, 2, 0, 2 }, 
								{ 2, 0, 2, 0, 2, 0, 2, 0 }, 
								{ 0, 2, 0, 2, 0, 2, 0, 2 },
								{ 3, 0, 3, 0, 3, 0, 3, 0 }, 
								{ 0, 3, 0, 3, 0, 3, 0, 3 }, 
								{ 1, 0, 1, 0, 1, 0, 1, 0 },
								{ 0, 1, 0, 1, 0, 1, 0, 1 }, 
								{ 1, 0, 1, 0, 1, 0, 1, 0 }}; 
	
	/*public int[][] plansza =   {{ 0, 3, 0, 3, 0, 3, 0, 2 }, 
								{ 1, 0, 3, 0, 2, 0, 2, 0 }, 
								{ 0, 3, 0, 2, 0, 3, 0, 2 },
								{ 2, 0, 3, 0, 2, 0, 2, 0 }, 
								{ 0, 3, 0, 1, 0, 3, 0, 1 }, 
								{ 3, 0, 1, 0, 3, 0, 3, 0 },
								{ 0, 1, 0, 3, 0, 3, 0, 1 }, 
								{ 3, 0, 3, 0, 3, 0, 1, 0 }};*/


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
	private int ruch = 1;
	private int poprzednieW;
	private int poprzednieK;
	private int wZbitego[] = new int[4];
	private int kZbitego[] = new int[4];
	private boolean bicieDamki = false;
	private boolean jestRuch = false;
	//List<Pionek> listaPionkow = new LinkedList<Pionek>();
	
	private int tabW[] = new int[64];
	private int tabK[] = new int[64];
	private int tabPoprzednieW[] = new int[64];
	private int tabPoprzednieK[] = new int[64];
	private int tabZbitegoW[] = new int[64];
	private int tabZbitegoK[] = new int[64];
	private int nBic = -1;
	private boolean pierwszy = true;
	

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
		zerujMacierz(bicie);
		zerujMacierz(podswietlanie);
	}
	
	public PoziomLatwy() {
		Dimension rozmiar = new Dimension(407, 407);
		setSize(rozmiar);
		setMinimumSize(rozmiar); //ustawia minimalny rozmiar
		setMaximumSize(rozmiar); //ustawia maksymalny rozmiar
		setPreferredSize(rozmiar); //ustawia preferowany rozmiar
		addMouseListener(this);
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

					if (podswietlanie[i][j] == 2)
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
	public void wykonajRuchKomputera()
	{
		if(ruch == 2)
			if(wykryjBicie(-1,-1) == false)
			{
				if(jestRuch == false)
				{
					if(sprawdzRuch(2, 5) == false) {
						Warcaby.getInstance().zmienStatusGryZKomputerem("Gratulacje, wygra³eœ! Pionki zosta³y ustawione od nowa. Mo¿esz zagraæ jeszcze raz.");
						Plik.zapis("ruchy.txt", "Wygra³eœ z komputerem.");
						ustawieniaPoczatkowe();
						repaint();
					}
				}
				
				podswietl2();
			}
			else
				podswietlBicie2();

		repaint();
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
						sprPodswietl2(i, j);
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
					System.out.println("1");
				}
			}
			else if(w < 2 && k > 5)
			{
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
					System.out.println("2");
				}
			}
			else if(w < 2 && k > 1 && k < 6)
			{
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
					System.out.println("3");
				}
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
					System.out.println("4");
				}
			}
			else if(w > 1 && w < 6 && k < 2)
			{
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3)&& plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
					System.out.println("5");
				}
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
					System.out.println("6");
				}
			}
			else if(w > 1 && w < 6 && k > 5)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
					System.out.println("7");
				}
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
					System.out.println("8");
				}
			}
			else if(w > 1 && w < 6 && k > 1 && k < 6)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
					System.out.println("9");
				}
				if((plansza[w+1][k-1] == przeciwnik || plansza[w+1][k-1] == przeciwnik+3) && plansza[w+2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 8;
					wykrytoBicie = true;
					System.out.println("10");
				}
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3) && plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
					System.out.println("11" + w + k);
				}
				if((plansza[w+1][k+1] == przeciwnik || plansza[w+1][k+1] == przeciwnik+3) && plansza[w+2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 4;
					wykrytoBicie = true;
					System.out.println("12");
				}
			}
			else if(w > 5 && k < 2)
			{
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3) && plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
					System.out.println("13");
				}
			}
			else if(w > 5 && k > 5)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
					System.out.println("14");
				}
			}
			else if(w > 5 && k > 1 && k < 6)
			{
				if((plansza[w-1][k-1] == przeciwnik || plansza[w-1][k-1] == przeciwnik+3) && plansza[w-2][k-2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 1;
					wykrytoBicie = true;
					System.out.println("15");
				}
				if((plansza[w-1][k+1] == przeciwnik || plansza[w-1][k+1] == przeciwnik+3) && plansza[w-2][k+2] == 3)
				{
					bicie[w][k] = bicie[w][k] + 2;
					wykrytoBicie = true;
					System.out.println("16");
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
		if(i > -1)
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
        if (wykrytoBicie == true)
        	Warcaby.getInstance().zmienStatusGryZKomputerem("Musisz zbiæ pionek przeciwnika.");
		return wykrytoBicie;
	}
	public void sprPodswietl2(int w, int k)
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
			Warcaby.getInstance().zmienStatusGryZKomputerem("Czerwony siê ruszy³ z pola "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]+ ". Twój ruch.");
			jestRuch = false;
			ruch = 1;
			
			
		}
		//Warcaby.getInstance().zmienStatusGryZKomputerem("Czerwony siê ruszy³ z pola "+pola[poprzednieW][poprzednieK]+" na "+ pola[tmp1][tmp2]);
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
			System.out.println("Ruch BIA£E - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Plik.zapis("ruchy.txt", "Ruch BIA£E - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			jestRuch = false;
			ruch = 2;
			wykonajRuchKomputera();
		}
	}
	
	public void podswietl2() //ruch komputera
	{
		int n = -1;
		for (int w = 0; w < 8; w++) {
			for (int k = 0; k < 8; k++) {
				if(plansza[w][k] == 2)
				{
					zerujMacierz(podswietlanie);
					if(k < 7)
					{
						if(plansza[w+1][k+1] == 3)
						{
							n++;
							tabW[n] = (w+1);
							tabK[n] = k+1;
							tabPoprzednieW[n] = w;
							tabPoprzednieK[n] = k;
						}
					}
					if(k > 0)
					{
						if(plansza[w+1][k-1] == 3)
						{
							n++;
							tabW[n] = w+1;
							tabK[n] = k-1;
							tabPoprzednieW[n] = w;
							tabPoprzednieK[n] = k;
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
							n++;
							tabW[n] = wi;
							tabK[n] = kol;
							tabPoprzednieW[n] = w;
							tabPoprzednieK[n] = k;
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
							n++;
							tabW[n] = wi;
							tabK[n] = kol;
							tabPoprzednieW[n] = w;
							tabPoprzednieK[n] = k;	
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
							n++;
							tabW[n] = wi;
							tabK[n] = kol;
							tabPoprzednieW[n] = w;
							tabPoprzednieK[n] = k;	
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
							n++;
							tabW[n] = wi;
							tabK[n] = kol;
							tabPoprzednieW[n] = w;
							tabPoprzednieK[n] = k;
						}
						else
							break;
					}
					
				}
			}
		}
		if(n > -1)
		{
			Random r = new Random(); 
			int a = r.nextInt(n+1); // deklaracja i definicja zmiennej
	        if(tabW[a] != 7 && plansza[tabPoprzednieW[a]][tabPoprzednieK[a]] == 2)
	        	plansza[tabW[a]][tabK[a]] = 2;
	        else
	        	plansza[tabW[a]][tabK[a]] = 5;
	        
	        plansza[tabPoprzednieW[a]][tabPoprzednieK[a]] = 3;
	        
	        System.out.println("Ruch CZERWONE - Pionek z "+pola[tabPoprzednieW[a]][tabPoprzednieK[a]]+" na "+pola[tabW[a]][tabK[a]]);
	        Plik.zapis("ruchy.txt", "Ruch CZERWONE - Pionek z "+pola[tabPoprzednieW[a]][tabPoprzednieK[a]]+" na "+pola[tabW[a]][tabK[a]]);
	        podswietlanie[tabPoprzednieW[a]][tabPoprzednieK[a]] = 2;
	        podswietlanie[tabW[a]][tabK[a]] = 4;
	        Warcaby.getInstance().zmienStatusGryZKomputerem("Czerwony siê ruszy³ z pola "+pola[tabPoprzednieW[a]][tabPoprzednieK[a]]+" na "+pola[tabW[a]][tabK[a]]+ ". Twój ruch.");
			jestRuch = false;
			ruch = 1;
		}
		else
		{
			System.out.println("Wygra³eœ");	//wygrywa gracz (bia³e)
			Warcaby.getInstance().zmienStatusGryZKomputerem("Gratulacje, wygra³eœ! Pionki zosta³y ustawione od nowa. Mo¿esz zagraæ jeszcze raz.");
			Plik.zapis("ruchy.txt", "Wygra³eœ z komputerem.");
			ustawieniaPoczatkowe();
			repaint();
		}
	}
	
	public void podswietlBicie1(int w, int k)
	{
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
			System.out.println("Bicie BIA£E - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			Plik.zapis("ruchy.txt", "Bicie BIA£E - Pionek z "+pola[poprzednieW][poprzednieK]+" na "+pola[w][k]);
			repaint();
			bicieDamki = false;
			zerujMacierz(bicie);
			zerujMacierz(podswietlanie);
			System.out.println("Sprawdzam bicie dla w = "+w+" k = "+k); //do usuniecia
			if(wykryjBicie(w,k) == false)
			{
				System.out.println("Nie wykryto bicia dla w = "+w+" k = "+k); //do usuniecia
				if(w == 0)
					plansza[w][k] = 4;
				
				if(sprawdzMacierz(plansza, 2, 5) == false) {
					System.out.println("Koniec gry, wygra³y bia³e!!!");
					Warcaby.getInstance().zmienStatusGryZKomputerem("Gratulacje, wygra³eœ! Pionki zosta³y ustawione od nowa. Mo¿esz zagraæ jeszcze raz.");
					Plik.zapis("ruchy.txt", "Wygra³eœ z komputerem.");
					ustawieniaPoczatkowe();
					repaint();
				}
				ruch = 2;
				wykonajRuchKomputera();
			}
			else
			{
				System.out.println("Wykryto bicia dla w = "+w+" k = "+k); //do usuniecia
				podswietlBicie1(w,k);
			}
				
		}
	
	}
	public void podswietlBicie2()
	{
		for (int w = 0; w < 8; w++) {
			for (int k = 0; k < 8; k++) {
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
						pierwszy = true;
						while(wi > 0 && kol > 0)
						{
							wi--;
							kol--;
							if(moznaPodswietlac == true && plansza[wi][kol] != 3)
								break;
							if(moznaPodswietlac == true)
							{
								nBic++;
								tabW[nBic] = wi;
								tabK[nBic] = kol;
								tabPoprzednieW[nBic] = w;
								tabPoprzednieK[nBic] = k;
								
								if(pierwszy == false)
									tabZbitegoW[nBic] = tabZbitegoW[nBic - 1];
								pierwszy = false;
							}
							if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
							{
								nBic++;
								tabZbitegoW[nBic] = wi;
								tabZbitegoK[nBic] = kol;
								nBic--;
								moznaPodswietlac = true;
							}
						}	
					}
					if(bicie[w][k] == 2 || bicie[w][k] == 3 || bicie[w][k] == 6 || bicie[w][k] == 10 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 14 || bicie[w][k] == 15)
					{
						wi = w;
						kol = k;
						moznaPodswietlac = false;
						pierwszy = true;
						while(wi > 0 && kol < 7)
						{
							wi--;
							kol++;
							if(moznaPodswietlac == true && plansza[wi][kol] != 3)
								break;
							if(moznaPodswietlac == true)
							{
								nBic++;
								tabW[nBic] = wi;
								tabK[nBic] = kol;
								tabPoprzednieW[nBic] = w;
								tabPoprzednieK[nBic] = k;
								if(pierwszy == false)
									tabZbitegoW[nBic] = tabZbitegoW[nBic - 1];
								pierwszy = false;
							}
							if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
							{
								nBic++;
								tabZbitegoW[nBic] = wi;
								tabZbitegoK[nBic] = kol;
								nBic--;
								moznaPodswietlac = true;
							}
						}	
					}
					if(bicie[w][k] == 4 || bicie[w][k] == 5 || bicie[w][k] == 6 || bicie[w][k] == 12 || bicie[w][k] == 7 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
					{
						wi = w;
						kol = k;
						moznaPodswietlac = false;
						pierwszy = true;
						while(wi < 7 && kol < 7)
						{
							wi++;
							kol++;
							if(moznaPodswietlac == true && plansza[wi][kol] != 3)
								break;
							if(moznaPodswietlac == true)
							{
								nBic++;
								tabW[nBic] = wi;
								tabK[nBic] = kol;
								tabPoprzednieW[nBic] = w;
								tabPoprzednieK[nBic] = k;
								if(pierwszy == false)
									tabZbitegoW[nBic] = tabZbitegoW[nBic - 1];
								pierwszy = false;
							}
							if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
							{
								nBic++;
								tabZbitegoW[nBic] = wi;
								tabZbitegoK[nBic] = kol;
								nBic--;
								moznaPodswietlac = true;
							}
						}	
					}
					if(bicie[w][k] == 8 || bicie[w][k] == 9 || bicie[w][k] == 10 || bicie[w][k] == 12 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
					{
						wi = w;
						kol = k;
						moznaPodswietlac = false;
						pierwszy = true;
						while(wi < 7 && kol > 0)
						{
							wi++;
							kol--;
							if(moznaPodswietlac == true && plansza[wi][kol] != 3)
								break;
							if(moznaPodswietlac == true)
							{
								nBic++;
								tabW[nBic] = wi;
								tabK[nBic] = kol;
								tabPoprzednieW[nBic] = w;
								tabPoprzednieK[nBic] = k;
								if(pierwszy == false)
									tabZbitegoW[nBic] = tabZbitegoW[nBic - 1];
								pierwszy = false;
							}
							if(plansza[wi][kol] == 1 || plansza[wi][kol] == 4)
							{
								nBic++;
								tabZbitegoW[nBic] = wi;
								tabZbitegoK[nBic] = kol;
								nBic--;
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
						nBic++;
						tabW[nBic] = w-2;
						tabK[nBic] = k-2;
						tabPoprzednieW[nBic] = w;
						tabPoprzednieK[nBic] = k;
						tabZbitegoW[nBic] = w-1;
						tabZbitegoK[nBic] = k-1;
					}
					if(bicie[w][k] == 2 || bicie[w][k] == 3 || bicie[w][k] == 6 || bicie[w][k] == 10 || bicie[w][k] == 7 || bicie[w][k] == 11 || bicie[w][k] == 14 || bicie[w][k] == 15)
					{
						nBic++;
						tabW[nBic] = w-2;
						tabK[nBic] = k+2;
						tabPoprzednieW[nBic] = w;
						tabPoprzednieK[nBic] = k;
						tabZbitegoW[nBic] = w-1;
						tabZbitegoK[nBic] = k+1;
					}
					if(bicie[w][k] == 4 || bicie[w][k] == 5 || bicie[w][k] == 6 || bicie[w][k] == 12 || bicie[w][k] == 7 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
					{
						nBic++;
						tabW[nBic] = w+2;
						tabK[nBic] = k+2;
						tabPoprzednieW[nBic] = w;
						tabPoprzednieK[nBic] = k;
						tabZbitegoW[nBic] = w+1;
						tabZbitegoK[nBic] = k+1;
					}
					if(bicie[w][k] == 8 || bicie[w][k] == 9 || bicie[w][k] == 10 || bicie[w][k] == 12 || bicie[w][k] == 11 || bicie[w][k] == 13 || bicie[w][k] == 14 || bicie[w][k] == 15)
					{
						
						nBic++;
						tabW[nBic] = w+2;
						tabK[nBic] = k-2;
						tabPoprzednieW[nBic] = w;
						tabPoprzednieK[nBic] = k;
						tabZbitegoW[nBic] = w+1;
						tabZbitegoK[nBic] = k-1; 
					}
				}
			}
		}
		repaint();
		if(nBic > -1)
		{
			Random r = new Random(); 
			int a = r.nextInt(nBic+1); // deklaracja i definicja zmiennej
			zerujMacierz(podswietlanie);
			plansza[tabZbitegoW[a]][tabZbitegoK[a]] = 3;
			if(plansza[tabPoprzednieW[a]][tabPoprzednieK[a]] == 2)
				plansza[tabW[a]][tabK[a]] = 2;
			else
				plansza[tabW[a]][tabK[a]] = 5;
			
			plansza[tabPoprzednieW[a]][tabPoprzednieK[a]] = 3;
			System.out.println("Bicie CZERWONE - Pionek z "+pola[tabPoprzednieW[a]][tabPoprzednieK[a]]+" na "+pola[tabW[a]][tabK[a]]);
			Plik.zapis("ruchy.txt", "Bicie CZERWONE - Pionek z "+pola[tabPoprzednieW[a]][tabPoprzednieK[a]]+" na "+pola[tabW[a]][tabK[a]]);
			Warcaby.getInstance().zmienStatusGryZKomputerem("Przeciwnik zbi³ Ci pionka. Ruszy³ siê z pola "+pola[tabPoprzednieW[a]][tabPoprzednieK[a]]+" na "+pola[tabW[a]][tabK[a]]+". Twój ruch.");
			podswietlanie[tabPoprzednieW[a]][tabPoprzednieK[a]] = 2;
	        podswietlanie[tabW[a]][tabK[a]] = 4;
			repaint();
			bicieDamki = false;
			nBic = -1;
			zerujMacierz(bicie);
			zerujMacierz(podswietlanie);
			if(wykryjBicie(tabW[a],tabK[a]) == false)
			{
				if(tabW[a] == 7)
					plansza[tabW[a]][tabK[a]] = 5;
				if(sprawdzMacierz(plansza, 1, 4) == false) {
					System.out.println("Koniec gry, wygra³y czerwone!!!");
					Warcaby.getInstance().zmienStatusGryZKomputerem("Niestety, przegra³eœ! Pionki zosta³y ustawione od nowa. Mo¿esz zagraæ jeszcze raz.");
					Plik.zapis("ruchy.txt", "Przegra³eœ z komputerem.");
					ustawieniaPoczatkowe();
					repaint();
				}
				ruch = 1;
			}
			else
				podswietlBicie2(); 
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		wybierzPole(e.getX(), e.getY());
		zerujMacierz(bicie);
		if (ruch == 1)
		{
			if(wykryjBicie(-1,-1) == false)
			{
				if(jestRuch == false)
				{
					if(sprawdzRuch(1, 4) == false) {
						Warcaby.getInstance().zmienStatusGryZKomputerem("Niestety, przegra³eœ! Pionki zosta³y ustawione od nowa. Mo¿esz zagraæ jeszcze raz.");
						Plik.zapis("ruchy.txt", "Przegra³eœ z komputerem.");
					    ustawieniaPoczatkowe();
						repaint();
					}
				}
				
				podswietl1(wiersz, kolumna);
			}			
			else
			{
				podswietlBicie1(wiersz, kolumna);
			}
		}
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
