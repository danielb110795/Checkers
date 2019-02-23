package warcaby;  

import java.awt.Color; 
import java.awt.Font;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.JOptionPane;

import polaczenie.Klient;
import polaczenie.Wydarzenie;
import polaczenie.Serwer;

public class Warcaby extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel Panel = null;	
	private JButton graczVsGracz = null;
	private JButton graczVsKomputer = null;
	private JButton start = null;
	private JButton polacz = null;
	private JButton poziomLatwy = null;
	private JButton poziomTrudny = null;
	private JButton wroc = null;
	private JButton zapisz = null;
	private JButton wczytaj = null;
	private JButton rozpocznijGre = null;
	private JLabel napisWarcaby = null;
	private JLabel napisPoziom = null;
	private JLabel obramowaniePionowoPrawo = null;
	private JLabel obramowaniePionowoLewo = null;
	private JLabel obramowaniePoziomoGora = null;
	private JLabel obramowaniePoziomoDol = null;
	private JLabel napisChat = null;
	private JScrollPane poleInformacyjneGraczVsKomputer = null;
	private JScrollPane poleDoWyswietlaniaWiadomosci = null;
	private JScrollPane poleInformacyjneGraczVsGracz = null;
	private JTextField poleDoWpisaniaAdresu = null;
	private JTextField poleDoPisaniaWiadomosci = null;
	public JTextArea statusGryZKomputerem = null;
	private JTextArea statusGryZGraczem = null;
	private JRadioButton przyciskKlient = null;
	private JRadioButton przyciskSerwer = null;
	

	private Klient client = null;
	private JTextArea czatOdbierz = null;
	private boolean clientStarted = false;
	private Serwer server = null; // status serwera
	private int port;
	private boolean token = false;
	
	private GraczVsGracz planszaGraczVsGracz = null;
	private PoziomLatwy planszaPoziomLatwy = null;
	private PoziomTrudny planszaPoziomTrudny = null;
	Date aktualnaData = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	String dateString = dateFormat.format(aktualnaData);
	private static Warcaby instance = null;
	int poziom; //1 - latwy, 2 - trudny

	public static Warcaby getInstance() { //zwraca instancje
		return instance;
	}
	
	public Warcaby() {
		super();
		Plik.zapis("ruchy.txt", "Aplikacja zosta³a w³¹czona" + dateString);
		instance = this;
		inicjalizacja();
		port = Konfiguracja.getInstance().getPort();
		poleDoWpisaniaAdresu.setText(Konfiguracja.getInstance().getHost());
	}

	public boolean hasToken() { //zwraca token
		return token;
	}

	public void setToken(boolean b) { //ustawia token na true badz false
		token = b;
	}
	
	private void inicjalizacja() {
		this.setSize(650, 700);
		this.setLocation(200, 5);
		this.setContentPane(getPanel());
		this.setResizable(false);
		this.setTitle("Warcaby");
		
		this.addWindowListener(new java.awt.event.WindowAdapter() { // nas³uchiwanie interfejsu do odbierania zdarzeñ okiennych. 
			public void windowClosing(java.awt.event.WindowEvent e) { // Wywo³ywane, gdy u¿ytkownik próbuje zamkn¹æ okno z menu systemowego okna.
				Konfiguracja.getInstance().setHost(poleDoWpisaniaAdresu.getText());
				Konfiguracja.getInstance().zapisz(); //zapisanie do ppoleliki ini aktualnej konfiguracji
			}
		});
		
		new Thread() { //aalokacja nowego watku
			@Override
			public void run() {
				while (true) { // jesli ten watek jest uruchomiony
					if (client != null && client.polaczenieDostepne()) {
						obslugaPolaczenia(); //funkcja obsugujaca wiadomosci
					} else if (clientStarted && client != null) {
						client.stop();
						client = null;
						zerwanePolaczenie(); //funckja oblugujaca gdy polaczenie zostanie zerwane
					}
					try {
						Thread.sleep(20); // usypia watek na 20 ms
					} catch (InterruptedException ex) {
					}
				}
			}
		}.start(); //powoduje wyknywanie watku
	}

	
	private JPanel getPanel() {
		if (Panel == null) {
			Panel = new JPanel();
			Panel.setLayout(null);
			Panel.add(getGraczVsGracz(), null);
			Panel.add(getGraczVsKomputer(), null);
			Panel.add(getNapisWarcaby(), null);
			Panel.add(getNapisPoziom(), null);
			Panel.add(getObramowaniePionowoPrawo(), null);
			Panel.add(getObramowaniePionowoLewo(), null);
			Panel.add(getObramowaniePoziomoGora(), null);
			Panel.add(getObramowaniePoziomoDol(), null);
			Panel.add(getPoziomLatwy(), null);
			Panel.add(getPoziomTrudny(), null);
			Panel.add(getWroc(), null);
			Panel.add(getZapisz(), null);
			Panel.add(getWczytaj(), null);
			Panel.add(getNapisChat(), null);
			Panel.add(getpoleDoPisaniaWiadomosci(), null);
			Panel.add(getPoleInformacyjneGraczVsKomputer(), null);
			Panel.add(getpoleDoWyswietlaniaWiadomosci(), null);
			Panel.add(getPoleInformacyjneGraczVsGracz(), null);
			Panel.add(getPrzyciskKlient(), null);
			Panel.add(getPlanszaGraczVsGracz(), null);
			Panel.add(getPlanszaPoziomTrudny(), null);
			Panel.add(getPlanszaPoziomLatwy(), null);
			Panel.add(getPrzyciskSerwer(), null);
			Panel.add(getStart(), null);
			Panel.add(getPolacz(), null);
			Panel.add(getPoleDoWpisaniaAdresu(), null);
			Panel.add(getRozpocznijGre(), null);
			ButtonGroup grupa = new ButtonGroup();
			grupa.add(przyciskKlient);
			grupa.add(przyciskSerwer);	
		}
		
		return Panel;
	}

	
	private JLabel getNapisWarcaby() {
		if (napisWarcaby == null) {
			napisWarcaby = new JLabel();
			// setLayout(null);
			Font font = new Font("Helvetica", Font.BOLD, 80);
			napisWarcaby.setFont(font);
			napisWarcaby.setForeground(Color.RED);
			napisWarcaby.setBounds(new Rectangle(120, 60, 600, 70));
			napisWarcaby.setText("WARCABY");
		}
		
		return napisWarcaby;
	}

	
	private JLabel getNapisPoziom() {
		if (napisPoziom == null) {
			napisPoziom = new JLabel();
			Font font = new Font("Helvetica", Font.BOLD, 80);
			napisPoziom.setFont(font);
			napisPoziom.setForeground(Color.RED);
			napisPoziom.setBounds(new Rectangle(165, 80, 500, 70));
			napisPoziom.setText("POZIOM");
			napisPoziom.setVisible(false);
		}
		
		return napisPoziom;
	}

	private JLabel getObramowaniePionowoPrawo() {
		if (obramowaniePionowoPrawo == null) {
			obramowaniePionowoPrawo = new JLabel(new ImageIcon("ObramowaniePionowo.jpg"));
			obramowaniePionowoPrawo.setBounds(new Rectangle(527, 70, 30, 467));
			obramowaniePionowoPrawo.setVisible(false);
		}
		
		return obramowaniePionowoPrawo;
	}
	
	private JLabel getObramowaniePionowoLewo() {
		if (obramowaniePionowoLewo == null) {
			obramowaniePionowoLewo = new JLabel(new ImageIcon("ObramowaniePionowo.jpg"));
			obramowaniePionowoLewo.setBounds(new Rectangle(90, 70, 30, 467));
			obramowaniePionowoLewo.setVisible(false);	
		}	
		return obramowaniePionowoLewo;
	}
	
	private JLabel getObramowaniePoziomoGora() {
		if (obramowaniePoziomoGora == null) {
			obramowaniePoziomoGora = new JLabel(new ImageIcon("ObramowaniePoziomo.jpg"));
			obramowaniePoziomoGora.setBounds(new Rectangle(120, 70, 407, 30));
			obramowaniePoziomoGora.setVisible(false);	
		}	
		return obramowaniePoziomoGora;
	}
	
	private JLabel getObramowaniePoziomoDol() {
		if (obramowaniePoziomoDol == null) {
			obramowaniePoziomoDol = new JLabel(new ImageIcon("ObramowaniePoziomo.jpg"));
			obramowaniePoziomoDol.setBounds(new Rectangle(120, 507, 407, 30));
			obramowaniePoziomoDol.setVisible(false);	
		}	
		return obramowaniePoziomoDol;
	}
	
	private JButton getGraczVsGracz() {
		if (graczVsGracz == null) {
			graczVsGracz = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 25);
			graczVsGracz.setFont(font);
			graczVsGracz.setBackground(Color.RED);
			graczVsGracz.setLocation(170, 340);
			graczVsGracz.setSize(300, 80);
			graczVsGracz.setText("Gracz vs Gracz");
			graczVsGracz.setEnabled(true);
			graczVsGracz.setVisible(true);
			graczVsGracz.addActionListener(new java.awt.event.ActionListener() {	
				public void actionPerformed(java.awt.event.ActionEvent e) {
					graczVsGracz.setVisible(false);
					graczVsKomputer.setVisible(false);
					napisWarcaby.setVisible(false);
					napisPoziom.setVisible(false);
					poziomLatwy.setVisible(false);
					poziomTrudny.setVisible(false);
					Font font = new Font("Helvetica", Font.BOLD, 12);
					wroc.setFont(font);
					wroc.setLocation(178, 510);
					wroc.setSize(130, 25);
					wroc.setBackground(null);
					wroc.setEnabled(true);
					wroc.setVisible(true);
					zapisz.setVisible(false);
					napisChat.setVisible(true);
					poleDoPisaniaWiadomosci.setVisible(true);
					poleDoWyswietlaniaWiadomosci.setVisible(true);
					poleInformacyjneGraczVsGracz.setVisible(true);
					przyciskKlient.setVisible(true);
					przyciskSerwer.setVisible(true);
					start.setVisible(true);
					rozpocznijGre.setVisible(true);
					wczytaj.setVisible(false);
					planszaGraczVsGracz.setVisible(true);
					obramowaniePionowoLewo.setVisible(true);
					obramowaniePionowoPrawo.setVisible(true);
					obramowaniePoziomoDol.setVisible(true);
					obramowaniePoziomoGora.setVisible(true);
					obramowaniePionowoPrawo.setBounds(new Rectangle(527, 20, 30, 467));
					obramowaniePionowoLewo.setBounds(new Rectangle(90, 20, 30, 467));
					obramowaniePoziomoGora.setBounds(new Rectangle(120, 20, 407, 30));
					obramowaniePoziomoDol.setBounds(new Rectangle(120, 457, 407, 30));
					zmienStatusGryZGraczem("Stwórz serwer lub po³¹cz siê jako klient gdy serwer ju¿ istnieje.");
					Plik.zapis("ruchy.txt", "Klikniêto gracz vs gracz.");
				}
			});
		}
		
		return graczVsGracz;
	}

	
	private JButton getGraczVsKomputer() {
		if (graczVsKomputer == null) {
			graczVsKomputer = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 25);
			graczVsKomputer.setFont(font);
			graczVsKomputer.setBackground(Color.RED);
			graczVsKomputer.setLocation(170, 210);
			graczVsKomputer.setSize(300, 80);
			graczVsKomputer.setText("Gracz vs Komputer");
			graczVsKomputer.setEnabled(true);
			graczVsKomputer.setVisible(true);
			graczVsKomputer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Font fontwroc = new Font("Helvetica", Font.BOLD, 25);
					wroc.setFont(fontwroc);
					wroc.setBackground(Color.RED);
					wroc.setLocation(170, 490);
					wroc.setSize(300, 80);
					wroc.setEnabled(true);
					wroc.setVisible(true);
					napisWarcaby.setVisible(false);
					graczVsGracz.setVisible(false);
					graczVsKomputer.setVisible(false);
					napisPoziom.setVisible(true);
					poziomTrudny.setVisible(true);
					poziomLatwy.setVisible(true);
					wczytaj.setVisible(false);
				}
			});
		}
		
		return graczVsKomputer;
	}

	
	private JButton getPoziomLatwy() {
		if (poziomLatwy == null) {
			poziomLatwy = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 25);
			poziomLatwy.setFont(font);
			poziomLatwy.setBackground(Color.RED);
			poziomLatwy.setLocation(170, 250);
			poziomLatwy.setSize(300, 80);
			poziomLatwy.setText("£atwy");
			poziomLatwy.setEnabled(true);
			poziomLatwy.setVisible(false);
			poziomLatwy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Font fontwroc = new Font("Helvetica", Font.BOLD, 15);
					wroc.setFont(fontwroc);
					wroc.setSize(150, 50);
					wroc.setBackground(null);
					wroc.setLocation(470, 600);
					wroc.setVisible(true);
					napisPoziom.setVisible(false);
					poziomTrudny.setVisible(false);
					poziomLatwy.setVisible(false);
					zapisz.setVisible(true);
					poleInformacyjneGraczVsKomputer.setVisible(true);
					planszaPoziomLatwy.setVisible(true);
					obramowaniePionowoPrawo.setBounds(new Rectangle(527, 70, 30, 467));
					obramowaniePionowoLewo.setBounds(new Rectangle(90, 70, 30, 467));
					obramowaniePoziomoGora.setBounds(new Rectangle(120, 70, 407, 30));
					obramowaniePoziomoDol.setBounds(new Rectangle(120, 507, 407, 30));
					obramowaniePionowoLewo.setVisible(true);
					obramowaniePionowoPrawo.setVisible(true);
					obramowaniePoziomoDol.setVisible(true);
					obramowaniePoziomoGora.setVisible(true);
					Plik.zapis("ruchy.txt", "Grasz na poziomie ³atwym");
					zmienStatusGryZKomputerem("Grasz w Warcaby na poziomie ³atwym. Twój ruch.");
					poziom = 1;
				}
			});
		}
		
		return poziomLatwy;
	}

	
	private JButton getPoziomTrudny() {
		if (poziomTrudny == null) {
			poziomTrudny = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 25);
			poziomTrudny.setFont(font);
			poziomTrudny.setBackground(Color.RED);
			poziomTrudny.setLocation(170, 370);
			poziomTrudny.setSize(300, 80);
			poziomTrudny.setText("Trudny");
			poziomTrudny.setEnabled(true);
			poziomTrudny.setVisible(false);
			poziomTrudny.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Font fontwroc = new Font("Helvetica", Font.BOLD, 15);
					wroc.setFont(fontwroc);
					wroc.setSize(150, 50);
					wroc.setLocation(470, 600);
					wroc.setBackground(null);
					wroc.setVisible(true);
					napisPoziom.setVisible(false);
					poziomTrudny.setVisible(false);
					poziomLatwy.setVisible(false);
					zapisz.setVisible(true);
					poleInformacyjneGraczVsKomputer.setVisible(true);
					planszaPoziomTrudny.setVisible(true);
					obramowaniePionowoLewo.setVisible(true);
					obramowaniePionowoPrawo.setVisible(true);
					obramowaniePoziomoDol.setVisible(true);
					obramowaniePoziomoGora.setVisible(true);
					obramowaniePionowoPrawo.setBounds(new Rectangle(527, 70, 30, 467));
					obramowaniePionowoLewo.setBounds(new Rectangle(90, 70, 30, 467));
					obramowaniePoziomoGora.setBounds(new Rectangle(120, 70, 407, 30));
					obramowaniePoziomoDol.setBounds(new Rectangle(120, 507, 407, 30));
					Plik.zapis("ruchy.txt", "Grasz na poziomie trudnym");
					zmienStatusGryZKomputerem("Grasz w Warcaby na poziomie trudnym. Twój ruch.");
					poziom = 2;
				}
			});
		}
		
		return poziomTrudny;
	}

	
	private JButton getWroc() {
		if (wroc == null) {
			wroc = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 25);
			wroc.setFont(font);
			wroc.setBackground(Color.RED);
			wroc.setText("Wróæ");
			wroc.setEnabled(true);
			wroc.setVisible(false);
			wroc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					napisWarcaby.setVisible(true);
					graczVsGracz.setVisible(true);
					graczVsKomputer.setVisible(true);
					napisPoziom.setVisible(false);
					poziomTrudny.setVisible(false);
					poziomLatwy.setVisible(false);
					wroc.setVisible(false);
					zapisz.setVisible(false);
					poleInformacyjneGraczVsKomputer.setVisible(false);
					poleInformacyjneGraczVsGracz.setVisible(false);
					napisChat.setVisible(false);
					poleDoPisaniaWiadomosci.setVisible(false);
					poleDoWyswietlaniaWiadomosci.setVisible(false);
					poleInformacyjneGraczVsGracz.setVisible(false);
					przyciskKlient.setVisible(false);
					przyciskSerwer.setVisible(false);
					start.setVisible(false);
					rozpocznijGre.setVisible(false);
					wczytaj.setVisible(true);
					poleDoWpisaniaAdresu.setVisible(false);
					polacz.setVisible(false);
					planszaGraczVsGracz.setVisible(false);
					planszaPoziomTrudny.setVisible(false);
					planszaPoziomLatwy.setVisible(false);
					planszaPoziomLatwy.ustawieniaPoczatkowe();
					planszaPoziomTrudny.ustawieniaPoczatkowe();
					obramowaniePionowoLewo.setVisible(false);
					obramowaniePionowoPrawo.setVisible(false);
					obramowaniePoziomoDol.setVisible(false);
					obramowaniePoziomoGora.setVisible(false);
					planszaGraczVsGracz.ustawieniaPoczatkowe();
					repaint();
					Plik.zapis("ruchy.txt", "Klikniêto wróæ, przerwano grê");
					zmienStatusGryZGraczem("Stwórz serwer lub po³¹cz siê jako klient gdy serwer ju¿ istnieje.");
				}
			});
		}	
		return wroc;
	}

	
	private JButton getZapisz() {
		if (zapisz == null) {
			zapisz = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 15);
			zapisz.setFont(font);
			//zapisz.setBackground(Color.RED);
			zapisz.setSize(150, 50);
			zapisz.setLocation(25, 600);
			zapisz.setText("Zapisz");
			zapisz.setEnabled(true);
			zapisz.setVisible(false);
			zapisz.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				if (poziom == 1)
				{
					try {
						Plik.zapiszStanGry(planszaPoziomLatwy.plansza, poziom);
						JOptionPane.showMessageDialog(null, "Zapisano stan gry");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
				if (poziom == 2)
				{
					try {
						Plik.zapiszStanGry(planszaPoziomTrudny.plansza, poziom);
						JOptionPane.showMessageDialog(null, "Zapisano stan gry");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
					
				}
			});
		}
		
		return zapisz;
	}

	
	private JButton getWczytaj() {
		if (wczytaj == null) {
			wczytaj = new JButton();
			Font font = new Font("Helvetica", Font.BOLD, 25);
			wczytaj.setFont(font);
			wczytaj.setBackground(Color.RED);
			wczytaj.setLocation(170, 470);
			wczytaj.setSize(300, 80);
			wczytaj.setText("Wczytaj");
			wczytaj.setEnabled(true);
			wczytaj.setVisible(true);
			wczytaj.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						poziom = Plik.odczytajStanGry(planszaPoziomLatwy.plansza);
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
					if (poziom == 1)
					{
					try {
						poziom = Plik.odczytajStanGry(planszaPoziomLatwy.plansza);
						repaint();
						JOptionPane.showMessageDialog(null, "Wczytano stan gry");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					}
					
					if (poziom == 2)
					{
					try {
						poziom = Plik.odczytajStanGry(planszaPoziomTrudny.plansza);
						repaint();
						JOptionPane.showMessageDialog(null, "Wczytano stan gry");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					}
					
					if (poziom == 1)
					{
						Font fontwroc = new Font("Helvetica", Font.BOLD, 15);
						wroc.setFont(fontwroc);
						wroc.setSize(150, 50);
						wroc.setBackground(null);
						wroc.setLocation(470, 600);
						wroc.setVisible(true);
						napisWarcaby.setVisible(false);
						graczVsGracz.setVisible(false);
						graczVsKomputer.setVisible(false);
						wczytaj.setVisible(false);
						zapisz.setVisible(true);
						poleInformacyjneGraczVsKomputer.setVisible(true);
						planszaPoziomLatwy.setVisible(true);
						obramowaniePionowoPrawo.setBounds(new Rectangle(527, 70, 30, 467));
						obramowaniePionowoLewo.setBounds(new Rectangle(90, 70, 30, 467));
						obramowaniePoziomoGora.setBounds(new Rectangle(120, 70, 407, 30));
						obramowaniePoziomoDol.setBounds(new Rectangle(120, 507, 407, 30));
						obramowaniePionowoLewo.setVisible(true);
						obramowaniePionowoPrawo.setVisible(true);
						obramowaniePoziomoDol.setVisible(true);
						obramowaniePoziomoGora.setVisible(true);
						Plik.zapis("ruchy.txt", "Wczytano grê na poziomie ³atwym.");
						zmienStatusGryZKomputerem("Wczytano grê na poziomie ³atwym. Twój ruch.");
						poziom = 1;
					}
					
					if (poziom == 2)
					{
						Font fontwroc = new Font("Helvetica", Font.BOLD, 15);
						wroc.setFont(fontwroc);
						wroc.setSize(150, 50);
						wroc.setLocation(470, 600);
						wroc.setBackground(null);
						wroc.setVisible(true);
						napisWarcaby.setVisible(false);
						graczVsGracz.setVisible(false);
						graczVsKomputer.setVisible(false);
						wczytaj.setVisible(false);
						zapisz.setVisible(true);
						poleInformacyjneGraczVsKomputer.setVisible(true);
						planszaPoziomTrudny.setVisible(true);
						obramowaniePionowoLewo.setVisible(true);
						obramowaniePionowoPrawo.setVisible(true);
						obramowaniePoziomoDol.setVisible(true);
						obramowaniePoziomoGora.setVisible(true);
						obramowaniePionowoPrawo.setBounds(new Rectangle(527, 70, 30, 467));
						obramowaniePionowoLewo.setBounds(new Rectangle(90, 70, 30, 467));
						obramowaniePoziomoGora.setBounds(new Rectangle(120, 70, 407, 30));
						obramowaniePoziomoDol.setBounds(new Rectangle(120, 507, 407, 30));
						Plik.zapis("ruchy.txt", "Wczytano gre na poziomie trudnym.");
						zmienStatusGryZKomputerem("Wczytano gre na poziomie trudnym. Twój ruch.");
						poziom = 2;
					}
					
				}
			});
		}
		
		return wczytaj;
	}
	
	
	public void zmienStatusGryZKomputerem(String wiadomosc) {
		Color color;
		color = new Color(255, 255, 196);
		statusGryZKomputerem.setBackground(color);
		statusGryZKomputerem.setText("");
		statusGryZKomputerem.append(wiadomosc);
	}

	
	public JTextArea getStatusGryZKomputerem() {
		if (statusGryZKomputerem == null) {
			statusGryZKomputerem = new JTextArea();
			statusGryZKomputerem.setEnabled(true);
			statusGryZKomputerem.setEditable(false);
			statusGryZKomputerem.setLineWrap(true);
			statusGryZKomputerem.setWrapStyleWord(true);
			statusGryZKomputerem.setFont(new Font("Dialog", Font.BOLD, 12));
			zmienStatusGryZKomputerem("Grasz w Warcaby na poziomie ³atwym. Twój ruch.");
		}	
		return statusGryZKomputerem;
	}

	
	private JScrollPane getPoleInformacyjneGraczVsKomputer() {
		if (poleInformacyjneGraczVsKomputer == null) {
			poleInformacyjneGraczVsKomputer = new JScrollPane();
			poleInformacyjneGraczVsKomputer.setLocation(223, 600);
			poleInformacyjneGraczVsKomputer.setEnabled(true);
			poleInformacyjneGraczVsKomputer.setVisible(false);
			poleInformacyjneGraczVsKomputer.setViewportView(getStatusGryZKomputerem());
			poleInformacyjneGraczVsKomputer.setSize(200, 50);
		}
		
		return poleInformacyjneGraczVsKomputer;
	}

	
	private JLabel getNapisChat() {
		if (napisChat == null) {
			napisChat = new JLabel();
			napisChat.setBounds(new Rectangle(330, 510, 72, 16));
			napisChat.setText("Czat:");
			napisChat.setVisible(false);
		}
		
		return napisChat;
	}

	
	private JTextField getpoleDoPisaniaWiadomosci() {
		if (poleDoPisaniaWiadomosci == null) {
			poleDoPisaniaWiadomosci = new JTextField();
			poleDoPisaniaWiadomosci.setLocation(330, 540);
			poleDoPisaniaWiadomosci.setEnabled(false);
			poleDoPisaniaWiadomosci.setSize(300, 20);
			poleDoPisaniaWiadomosci.setVisible(false);
			poleDoPisaniaWiadomosci.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!poleDoPisaniaWiadomosci.getText().trim().isEmpty()) {
						Wydarzenie ge = new Wydarzenie(Wydarzenie.KLIENT_WIADOMOSC_WYSLANIE);
						ge.setWiadomosc(poleDoPisaniaWiadomosci.getText().trim());
						sendMessage(ge);
						poleDoPisaniaWiadomosci.setText("");
					}
				}
			});
		}
		
		return poleDoPisaniaWiadomosci;
	}

	
	private JScrollPane getpoleDoWyswietlaniaWiadomosci() {
		if (poleDoWyswietlaniaWiadomosci == null) {
			poleDoWyswietlaniaWiadomosci = new JScrollPane();
			poleDoWyswietlaniaWiadomosci.setLocation(330, 570);	
			poleDoWyswietlaniaWiadomosci.setViewportView(getCzatOdbierz()); 
			poleDoWyswietlaniaWiadomosci.setSize(300, 83);
			poleDoWyswietlaniaWiadomosci.setVisible(false);
		}
		
		return poleDoWyswietlaniaWiadomosci;
	}

	
	private JScrollPane getPoleInformacyjneGraczVsGracz() {
		if (poleInformacyjneGraczVsGracz == null) {
			poleInformacyjneGraczVsGracz = new JScrollPane();
			poleInformacyjneGraczVsGracz.setLocation(20, 545);
			poleInformacyjneGraczVsGracz.setEnabled(true);
			poleInformacyjneGraczVsGracz.setViewportView(getStatusGryZGraczem());
			poleInformacyjneGraczVsGracz.setSize(290, 70);
			poleInformacyjneGraczVsGracz.setVisible(false);
		}
		
		return poleInformacyjneGraczVsGracz;
	}

	
	private JTextArea getStatusGryZGraczem() {
		if (statusGryZGraczem == null) {
			statusGryZGraczem = new JTextArea();
			statusGryZGraczem.setEnabled(true);
			statusGryZGraczem.setEditable(false);
			statusGryZGraczem.setLineWrap(true);
			statusGryZGraczem.setWrapStyleWord(true);
			statusGryZGraczem.setFont(new Font("Dialog", Font.BOLD, 12));
			zmienStatusGryZGraczem("Stwórz serwer lub po³¹cz siê jako klient gdy serwer ju¿ istnieje.");
		}
		
		return statusGryZGraczem;
	}

	
	private void zmienStatusGryZGraczem(String wiadomosc) {
		Color color;
		color = new Color(255, 255, 196);
		statusGryZGraczem.setBackground(color);
		statusGryZGraczem.setText("");
		statusGryZGraczem.append(wiadomosc);
	}


	private JRadioButton getPrzyciskKlient() {
		if (przyciskKlient == null) {
			przyciskKlient = new JRadioButton();
			przyciskKlient.setLocation(20, 620);
			przyciskKlient.setText("klient");
			przyciskKlient.setSize(70, 20);
			przyciskKlient.setVisible(false);
			przyciskKlient.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					start.setVisible(false);
					polacz.setVisible(true);
					poleDoWpisaniaAdresu.setVisible(true);
				}
			});
		}
		
		return przyciskKlient;
	}


	private JRadioButton getPrzyciskSerwer() {
		if (przyciskSerwer == null) {
			przyciskSerwer = new JRadioButton();
			przyciskSerwer.setLocation(20, 640);
			przyciskSerwer.setSelected(true);
			przyciskSerwer.setText("serwer");
			przyciskSerwer.setVisible(false);
			przyciskSerwer.setSize(70, 20);
			przyciskSerwer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					start.setVisible(true);
					polacz.setVisible(false);
					poleDoWpisaniaAdresu.setVisible(false);
				}
			});
		}
		
		return przyciskSerwer;
	}


	private JButton getStart() {
		if (start == null) {
			start = new JButton();
			start.setBounds(new Rectangle(120, 628, 80, 25));
			start.setText("Start");
			start.setVisible(false);
			start.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					start.setEnabled(false);
					if (server == null || !server.czyUruchomiony()) {
						przyciskSerwer.setEnabled(false);
						przyciskKlient.setEnabled(false);
						String host = "localhost";
						server = new Serwer(port);
						if (server.start()) {
							client = new Klient(getID(), host, port);
							if (client.start()) {
								Wydarzenie ge = new Wydarzenie(Wydarzenie.PROBA_LOGOWANIA_KLIENTA);
								sendMessage(ge);
							}
							zmienStatusGryZGraczem("Serwer pomyœlnie uruchomiony!");
							poleDoPisaniaWiadomosci.setEnabled(true);
							wroc.setEnabled(false);
							start.setText("Stop");
						} else {
							przyciskSerwer.setEnabled(true);
							przyciskKlient.setEnabled(true);
							zmienStatusGryZGraczem("Nie uda³o sie uruchoniæ serwera!\n");
						}
					} else {
						zmienStatusGryZGraczem("Serwer zatrzymany!\n");
						if (server != null)
							server.stop();
						server = null;
						ustawOdNowa();
					}
					start.setEnabled(true);
					
					
				}
			});
		}
		
		return start;
	}

	
	private JButton getPolacz() {
		if (polacz == null) {
			polacz = new JButton();
			polacz.setBounds(new Rectangle(230, 630, 80, 25));
			polacz.setText("Po³¹cz");
			polacz.setVisible(false);
			polacz.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					polacz.setEnabled(false);
					poleDoWpisaniaAdresu.setEnabled(false);
					if (client == null || !client.polaczenieDostepne()) {
						przyciskSerwer.setEnabled(false);
						przyciskKlient.setEnabled(false);
						String host = poleDoWpisaniaAdresu.getText().trim();
						client = new Klient(getID(), host, port);
						if (client.start()) {
							Wydarzenie ge = new Wydarzenie(Wydarzenie.PROBA_LOGOWANIA_KLIENTA);
							sendMessage(ge);
							zmienStatusGryZGraczem("Po³¹czono z serwerem!\nNaœcisnij 'Rozpocznij grê' gdy jesteœ gotowy.");
							poleDoPisaniaWiadomosci.setEnabled(true);
							polacz.setText("Roz³¹cz");
							wroc.setEnabled(false);
							clientStarted = true;
						} else {
							clientStarted = false;
							przyciskSerwer.setEnabled(true);
							przyciskKlient.setEnabled(true);
							poleDoWpisaniaAdresu.setEnabled(true);
							zmienStatusGryZGraczem("Nie uda³o sie po³¹czyæ z serwerem!\n");
						}
					} else {
						zmienStatusGryZGraczem("Po³¹czenie przerwane!\n");
						if (client != null) {
							client.stop();
							clientStarted = false;
						}
						client = null;
						ustawOdNowa();
					}
					polacz.setEnabled(true);	
				}
			});
		}
		
		return polacz;
	}

	
	private JTextField getPoleDoWpisaniaAdresu() {
		if (poleDoWpisaniaAdresu == null) {
			poleDoWpisaniaAdresu = new JTextField();
			poleDoWpisaniaAdresu.setLocation(100, 630);
			poleDoWpisaniaAdresu.setSize(120, 25);
			poleDoWpisaniaAdresu.setText("localhost");
			poleDoWpisaniaAdresu.setVisible(false);
		}
		
		return poleDoWpisaniaAdresu;
	}


	private JButton getRozpocznijGre() {
		if (rozpocznijGre == null) {
			rozpocznijGre = new JButton();
			rozpocznijGre.setLocation(20, 510);
			rozpocznijGre.setEnabled(false);
			rozpocznijGre.setText("Rozpocznij grê");
			rozpocznijGre.setVisible(false);
			rozpocznijGre.setSize(130, 25);
			rozpocznijGre.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {	
					rozpocznijGre.setEnabled(false);
					planszaGraczVsGracz.ustawieniaPoczatkowe();
					repaint();
					Wydarzenie ge = new Wydarzenie(Wydarzenie.PROBA_DOLACZENIA_KLIENTA_DO_GRY);
					sendMessage(ge);
				}
			});
		}
		
		return rozpocznijGre;
	}


	
/* Do po³¹czenie klient serwer*/
// Oprócz tego poni¿ej zmiany w przycisku Start, Po³¹cz
	private String getID() {
		return (przyciskSerwer.isSelected()) ? "ID_SERVER" : "ID_CLIENT";
	}
	
	private JTextArea getCzatOdbierz() {
		if (czatOdbierz == null) {
			czatOdbierz = new JTextArea();
			czatOdbierz.setEnabled(true);
			czatOdbierz.setLineWrap(true);
			czatOdbierz.setWrapStyleWord(true);
			czatOdbierz.setEditable(false);
		}
		return czatOdbierz;
	}
	
	private void obslugaPolaczenia() {
		Wydarzenie ge;
		while (client != null && client.polaczenieDostepne() && (ge = client.odbierzWiadomosc()) != null) {
			switch (ge.getTypZdarzenia()) {
			case Wydarzenie.WIADOMOSC_DO_WSZYSTKICH_SERWER:
				if (getID().compareTo(ge.getIdGracza()) == 0) {
					czatOdbierz.append("TY > ");
				} else {
					czatOdbierz.append("PRZECIWNIK > ");
				}
				czatOdbierz.append(ge.getWiadomosc() + "\n");
				czatOdbierz.setCaretPosition(czatOdbierz.getText().length());
				break;
				
			case Wydarzenie.DOLACZENIE:
				if (getID().compareTo(ge.getWiadomosc()) != 0) {
					zmienStatusGryZGraczem("Po³¹czonych jest 2 graczy.\nNaciœnij 'Rozpocznij grê'.");
				}
				break;
				
			case Wydarzenie.MOZLIWOSC_DOLACZENIA_DO_GRY:
				rozpocznijGre.setEnabled(true);
				break; 
				
			case Wydarzenie.DOLACZNIE_DO_GRY:
				if (getID().compareTo(ge.getWiadomosc()) == 0) {
					zmienStatusGryZGraczem("Oczekiwanie a¿ przeciwnik bêdzie gotowy do gry...");
				} else if (rozpocznijGre.isEnabled()) {
					if (wroc.isEnabled()) {
						zmienStatusGryZGraczem("Przeciwnik gotowy do gry.\nNaciœnij 'Rozpocznij grê'.");
					} else {
						zmienStatusGryZGraczem("Przeciwnik gotowy do gry.\nNaciœnij 'Rozpocznij grê'.");
					}
				}
				break;
			
			case Wydarzenie.START_GRY:
				if (przyciskSerwer.isSelected()) {
					zmienStatusGryZGraczem("Gra siê rozpocze³a.\nTwój ruch!");
					setToken(true);
				} else {
					zmienStatusGryZGraczem("Gra siê rozpocze³a.\nRuch ma Twój przeciwnik!");
				}
				break;
			
			case Wydarzenie.WYSLANIE_RUCHU_SERWER:
				if (getID().compareTo(ge.getIdGracza()) != 0) {
					String s = ge.getWiadomosc();
					int idx1 = s.indexOf('|');
					int idx2 = s.indexOf('|', idx1+1);
					int idx3 = s.indexOf('|', idx2+1);
					int idx4 = s.indexOf('|', idx3+1);
					int idx5 = s.indexOf('|', idx4+1);
					int idx6 = s.indexOf('|', idx5+1);
					int idx7 = s.indexOf('|', idx6+1);
					int idx8 = s.indexOf('|', idx7+1);
					int idx9 = s.indexOf('|', idx8+1);
					int idx10 = s.indexOf('|', idx9+1);
					int idx11 = s.indexOf('|', idx10+1);
					int idx12 = s.indexOf('|', idx11+1);
					int idx13 = s.indexOf('|', idx12+1);
					int idx14 = s.indexOf('|', idx13+1);
					int idx15 = s.indexOf('|', idx14+1);
					int idx16 = s.indexOf('|', idx15+1);
					int idx17 = s.indexOf('|', idx16+1);
					int idx18 = s.indexOf('|', idx17+1);
					int idx19 = s.indexOf('|', idx18+1);
					int idx20 = s.indexOf('|', idx19+1);
					int idx21 = s.indexOf('|', idx20+1);
					int idx22 = s.indexOf('|', idx21+1);
					int idx23 = s.indexOf('|', idx22+1);
					int idx24 = s.indexOf('|', idx23+1);
					int idx25 = s.indexOf('|', idx24+1);
					int idx26 = s.indexOf('|', idx25+1);
					int idx27 = s.indexOf('|', idx26+1);
					int idx28 = s.indexOf('|', idx27+1);
					int idx29 = s.indexOf('|', idx28+1);
					int idx30 = s.indexOf('|', idx29+1);
					int idx31 = s.indexOf('|', idx30+1);
					int idx32 = s.indexOf('|', idx31+1);
					int idx33 = s.indexOf('|', idx32+1);
					
					String p01 = s.substring(0, idx1);
					String p03 = s.substring(idx1 + 1, idx2);
					String p05 = s.substring(idx2 + 1, idx3);
					String p07 = s.substring(idx3 + 1, idx4);
					String p10 = s.substring(idx4 + 1, idx5);
					String p12 = s.substring(idx5 + 1, idx6);
					String p14 = s.substring(idx6 + 1, idx7);
					String p16 = s.substring(idx7 + 1, idx8);
					String p21 = s.substring(idx8 + 1, idx9);
					String p23 = s.substring(idx9 + 1, idx10);
					String p25 = s.substring(idx10 + 1, idx11);
					String p27 = s.substring(idx11 + 1, idx12);
					String p30 = s.substring(idx12 + 1, idx13);
					String p32 = s.substring(idx13 + 1, idx14);
					String p34 = s.substring(idx14 + 1, idx15);
					String p36 = s.substring(idx15 + 1, idx16);
					String p41 = s.substring(idx16 + 1, idx17);
					String p43 = s.substring(idx17 + 1, idx18);
					String p45 = s.substring(idx18 + 1, idx19);
					String p47 = s.substring(idx19 + 1, idx20);
					String p50 = s.substring(idx20 + 1, idx21);
					String p52 = s.substring(idx21 + 1, idx22);
					String p54 = s.substring(idx22 + 1, idx23);
					String p56 = s.substring(idx23 + 1, idx24);
					String p61 = s.substring(idx24 + 1, idx25);
					String p63 = s.substring(idx25 + 1, idx26);
					String p65 = s.substring(idx26 + 1, idx27);
					String p67 = s.substring(idx27 + 1, idx28);
					String p70 = s.substring(idx28 + 1, idx29);
					String p72 = s.substring(idx29 + 1, idx30);
					String p74 = s.substring(idx30 + 1, idx31);
					String p76 = s.substring(idx31 + 1, idx32);
					String wynik = s.substring(idx32 + 1, idx33);
					String pionki = s.substring(idx33 + 1);

					try {
						int B1 = Integer.parseInt(p01);
						int D1 = Integer.parseInt(p03);
						int F1 = Integer.parseInt(p05);
						int H1 = Integer.parseInt(p07);
						
						int A2 = Integer.parseInt(p10);
						int C2 = Integer.parseInt(p12);
						int E2 = Integer.parseInt(p14);
						int G2 = Integer.parseInt(p16);
						
						int B3 = Integer.parseInt(p21);
						int D3 = Integer.parseInt(p23);
						int F3 = Integer.parseInt(p25);
						int H3 = Integer.parseInt(p27);
						
						int A4 = Integer.parseInt(p30);
						int C4 = Integer.parseInt(p32);
						int E4 = Integer.parseInt(p34);
						int G4 = Integer.parseInt(p36);
						
						int B5 = Integer.parseInt(p41);
						int D5 = Integer.parseInt(p43);
						int F5 = Integer.parseInt(p45);
						int H5 = Integer.parseInt(p47);
						
						int A6 = Integer.parseInt(p50);
						int C6 = Integer.parseInt(p52);
						int E6 = Integer.parseInt(p54);
						int G6 = Integer.parseInt(p56);
						
						int B7 = Integer.parseInt(p61);
						int D7 = Integer.parseInt(p63);
						int F7 = Integer.parseInt(p65);
						int H7 = Integer.parseInt(p67);
						
						int A8 = Integer.parseInt(p70);
						int C8 = Integer.parseInt(p72);
						int E8 = Integer.parseInt(p74);
						int G8 = Integer.parseInt(p76);
						
						int wynikk = Integer.parseInt(wynik);
						
						int pionkiIlosc = Integer.parseInt(pionki);
						
						WynikRuchu w;
						if(wynikk == 1)
							 w = WynikRuchu.ZWYKLY_RUCH; 
						else 
							 w = WynikRuchu.BICIE; 
						
						Wydarzenie geOut = new Wydarzenie(Wydarzenie.RUCH_WYNIK_KLIENT);
						geOut.setWiadomosc(B1 + "|" + D1 + "|" + F1 + "|" + H1 + "|" +
									   	 A2 + "|" + C2 + "|" + E2 + "|" + G2 + "|" +
									   	 B3 + "|" + D3 + "|" + F3 + "|" + H3 + "|" +
									   	 A4 + "|" + C4 + "|" + E4 + "|" + G4 + "|" +
										 B5 + "|" + D5 + "|" + F5 + "|" + H5 + "|" +
										 A6 + "|" + C6 + "|" + E6 + "|" + G6 + "|" +
										 B7 + "|" + D7 + "|" + F7 + "|" + H7 + "|" +
										 A8 + "|" + C8 + "|" + E8 + "|" + G8 + "|" + w.ordinal() + "|" + pionkiIlosc);
						sendMessage(geOut);
					} catch (NumberFormatException ex) {
					}

				}
				break;

			case Wydarzenie.WYNIK_RUCHU_WSZYSCY: {
				String s = ge.getWiadomosc();
				int idx1 = s.indexOf('|');
				int idx2 = s.indexOf('|', idx1 + 1);
				int idx3 = s.indexOf('|', idx2 + 1);
				int idx4 = s.indexOf('|', idx3 + 1);
				int idx5 = s.indexOf('|', idx4 + 1);
				int idx6 = s.indexOf('|', idx5 + 1);
				int idx7 = s.indexOf('|', idx6 + 1);
				int idx8 = s.indexOf('|', idx7 + 1);
				int idx9 = s.indexOf('|', idx8 + 1);
				int idx10 = s.indexOf('|', idx9 + 1);
				int idx11 = s.indexOf('|', idx10 + 1);
				int idx12 = s.indexOf('|', idx11 + 1);
				int idx13 = s.indexOf('|', idx12 + 1);
				int idx14 = s.indexOf('|', idx13 + 1);
				int idx15 = s.indexOf('|', idx14 + 1);
				int idx16 = s.indexOf('|', idx15 + 1);
				int idx17 = s.indexOf('|', idx16 + 1);
				int idx18 = s.indexOf('|', idx17 + 1);
				int idx19 = s.indexOf('|', idx18 + 1);
				int idx20 = s.indexOf('|', idx19 + 1);
				int idx21 = s.indexOf('|', idx20 + 1);
				int idx22 = s.indexOf('|', idx21 + 1);
				int idx23 = s.indexOf('|', idx22 + 1);
				int idx24 = s.indexOf('|', idx23 + 1);
				int idx25 = s.indexOf('|', idx24 + 1);
				int idx26 = s.indexOf('|', idx25 + 1);
				int idx27 = s.indexOf('|', idx26 + 1);
				int idx28 = s.indexOf('|', idx27 + 1);
				int idx29 = s.indexOf('|', idx28 + 1);
				int idx30 = s.indexOf('|', idx29 + 1);
				int idx31 = s.indexOf('|', idx30 + 1);
				int idx32 = s.indexOf('|', idx31 + 1);
				int idx33 = s.indexOf('|', idx32 + 1);
				
				String p01 = s.substring(0, idx1);
				String p03 = s.substring(idx1 + 1, idx2);
				String p05 = s.substring(idx2 + 1, idx3);
				String p07 = s.substring(idx3 + 1, idx4);
				String p10 = s.substring(idx4 + 1, idx5);
				String p12 = s.substring(idx5 + 1, idx6);
				String p14 = s.substring(idx6 + 1, idx7);
				String p16 = s.substring(idx7 + 1, idx8);
				String p21 = s.substring(idx8 + 1, idx9);
				String p23 = s.substring(idx9 + 1, idx10);
				String p25 = s.substring(idx10 + 1, idx11);
				String p27 = s.substring(idx11 + 1, idx12);
				String p30 = s.substring(idx12 + 1, idx13);
				String p32 = s.substring(idx13 + 1, idx14);
				String p34 = s.substring(idx14 + 1, idx15);
				String p36 = s.substring(idx15 + 1, idx16);
				String p41 = s.substring(idx16 + 1, idx17);
				String p43 = s.substring(idx17 + 1, idx18);
				String p45 = s.substring(idx18 + 1, idx19);
				String p47 = s.substring(idx19 + 1, idx20);
				String p50 = s.substring(idx20 + 1, idx21);
				String p52 = s.substring(idx21 + 1, idx22);
				String p54 = s.substring(idx22 + 1, idx23);
				String p56 = s.substring(idx23 + 1, idx24);
				String p61 = s.substring(idx24 + 1, idx25);
				String p63 = s.substring(idx25 + 1, idx26);
				String p65 = s.substring(idx26 + 1, idx27);
				String p67 = s.substring(idx27 + 1, idx28);
				String p70 = s.substring(idx28 + 1, idx29);
				String p72 = s.substring(idx29 + 1, idx30);
				String p74 = s.substring(idx30 + 1, idx31);
				String p76 = s.substring(idx31 + 1, idx32);
				String wynikRuchu = s.substring(idx32 + 1, idx33);
				String pionkii = s.substring(idx33 + 1);

				try {

					int B1 = Integer.parseInt(p01);
					int D1 = Integer.parseInt(p03);
					int F1 = Integer.parseInt(p05);
					int H1 = Integer.parseInt(p07);
					
					int A2 = Integer.parseInt(p10);
					int C2 = Integer.parseInt(p12);
					int E2 = Integer.parseInt(p14);
					int G2 = Integer.parseInt(p16);
					
					int B3 = Integer.parseInt(p21);
					int D3 = Integer.parseInt(p23);
					int F3 = Integer.parseInt(p25);
					int H3 = Integer.parseInt(p27);
					
					int A4 = Integer.parseInt(p30);
					int C4 = Integer.parseInt(p32);
					int E4 = Integer.parseInt(p34);
					int G4 = Integer.parseInt(p36);
					
					int B5 = Integer.parseInt(p41);
					int D5 = Integer.parseInt(p43);
					int F5 = Integer.parseInt(p45);
					int H5 = Integer.parseInt(p47);
					
					int A6 = Integer.parseInt(p50);
					int C6 = Integer.parseInt(p52);
					int E6 = Integer.parseInt(p54);
					int G6 = Integer.parseInt(p56);
					
					int B7 = Integer.parseInt(p61);
					int D7 = Integer.parseInt(p63);
					int F7 = Integer.parseInt(p65);
					int H7 = Integer.parseInt(p67);
					
					int A8 = Integer.parseInt(p70);
					int C8 = Integer.parseInt(p72);
					int E8 = Integer.parseInt(p74);
					int G8 = Integer.parseInt(p76);
					
					int n = Integer.parseInt(wynikRuchu);
					
					int pio = Integer.parseInt(pionkii);
					
					WynikRuchu w = WynikRuchu.values()[n];

					if (przyciskKlient.isSelected()) {
						planszaGraczVsGracz.ruch = 2;
					}
					if (przyciskSerwer.isSelected()) {
						planszaGraczVsGracz.ruch = 1;
					}
					
					planszaGraczVsGracz.plansza[0][1] = B1;
					planszaGraczVsGracz.plansza[0][3] = D1;
					planszaGraczVsGracz.plansza[0][5] = F1;
					planszaGraczVsGracz.plansza[0][7] = H1;
					
					planszaGraczVsGracz.plansza[1][0] = A2;
					planszaGraczVsGracz.plansza[1][2] = C2;
					planszaGraczVsGracz.plansza[1][4] = E2;
					planszaGraczVsGracz.plansza[1][6] = G2;
					
					planszaGraczVsGracz.plansza[2][1] = B3;
					planszaGraczVsGracz.plansza[2][3] = D3;
					planszaGraczVsGracz.plansza[2][5] = F3;
					planszaGraczVsGracz.plansza[2][7] = H3;
					
					planszaGraczVsGracz.plansza[3][0] = A4;
					planszaGraczVsGracz.plansza[3][2] = C4;
					planszaGraczVsGracz.plansza[3][4] = E4;
					planszaGraczVsGracz.plansza[3][6] = G4;
					
					planszaGraczVsGracz.plansza[4][1] = B5;
					planszaGraczVsGracz.plansza[4][3] = D5;
					planszaGraczVsGracz.plansza[4][5] = F5;
					planszaGraczVsGracz.plansza[4][7] = H5;
					
					planszaGraczVsGracz.plansza[5][0] = A6;
					planszaGraczVsGracz.plansza[5][2] = C6;
					planszaGraczVsGracz.plansza[5][4] = E6;
					planszaGraczVsGracz.plansza[5][6] = G6;
					
					planszaGraczVsGracz.plansza[6][1] = B7;
					planszaGraczVsGracz.plansza[6][3] = D7;
					planszaGraczVsGracz.plansza[6][5] = F7;
					planszaGraczVsGracz.plansza[6][7] = H7;
	
					planszaGraczVsGracz.plansza[7][0] = A8;
					planszaGraczVsGracz.plansza[7][2] = C8;
					planszaGraczVsGracz.plansza[7][4] = E8;
					planszaGraczVsGracz.plansza[7][6] = G8;
					
					repaint();

					if (w == WynikRuchu.ZWYKLY_RUCH) {
						if (getID().compareTo(ge.getIdGracza()) != 0) {
							zmienStatusGryZGraczem("Ruch przeciwnika.");
						} else {
							zmienStatusGryZGraczem("Twoj ruch.");
							setToken(true);
						}
					}
					else
					{
						if (getID().compareTo(ge.getIdGracza()) != 0) {
							zmienStatusGryZGraczem("Zbi³eœ pionek przeciwnika. Ruch przeciwnika.");
							if (pio == 0) {
								zmienStatusGryZGraczem("Wygra³eœ. Mo¿esz zagraæ jeszcze raz, kliknij 'Kolejna gra'.");
								
								Wydarzenie geOut = new Wydarzenie(Wydarzenie.ZAKONCZENIE_GRY); //ustawienie 0 klientow gotowych do gry
								sendMessage(geOut);
								rozpocznijGre.setText("Kolejna gra");
								rozpocznijGre.setEnabled(true);
								JOptionPane.showMessageDialog(this, "Wygra³eœ!");
							}
								
						} else {
							zmienStatusGryZGraczem("Przeciwnik zbi³ Ci pionek. Twoj ruch.");
							setToken(true);
							if (pio == 0) {
								zmienStatusGryZGraczem("Przegra³eœ. Mo¿esz zagraæ jeszcze raz, kliknij 'Kolejna gra'.");					
								rozpocznijGre.setText("Kolejna gra");
								rozpocznijGre.setEnabled(true);
								JOptionPane.showMessageDialog(this, "Przegra³eœ!");
							}
						}
					}
					
					
				} catch (NumberFormatException ex) {
				}
			}
				break;
				
			case Wydarzenie.WYJSCIE_GRACZA:
				zerwanePolaczenie();
				break;
				
			case Wydarzenie.OGRANICZENIE_GRACZY:
				if (client != null)
					client.stop();
				client = null;
				ustawOdNowa();
				zmienStatusGryZGraczem("Nie mo¿na po³¹czyæ z serwerem!\nBrak wolnych miejsc!");
				break;
			}
		}
	}
	
	private void ustawOdNowa() {
		rozpocznijGre.setText("Rozpocznij grê");
		start.setText("Start");
		polacz.setText("Po³¹cz");
		poleDoPisaniaWiadomosci.setEnabled(false);
		rozpocznijGre.setEnabled(false);
		przyciskSerwer.setEnabled(true);
		przyciskKlient.setEnabled(true);
		poleDoWpisaniaAdresu.setEnabled(true);
		wroc.setEnabled(true);

	}
	
	private void zerwanePolaczenie() {
		if (przyciskKlient.isSelected()) {
			clientStarted = false;
			ustawOdNowa();
			polacz.setEnabled(true);
			planszaGraczVsGracz.ustawieniaPoczatkowe();
			repaint();
		} 
		else
		{
			planszaGraczVsGracz.ustawieniaPoczatkowe();
			repaint();
		}
		
		zmienStatusGryZGraczem("Po³¹czenie zosta³o przerwane! Ktoœ wyszed³ z gry.");
	}
	
	
	public boolean sendMessage(Wydarzenie ge) {
		if (client != null && client.polaczenieDostepne()) {
			ge.setIdGracza(getID());
			client.wyslijWiadomosc(ge);
			return true;
		} else {
			return false;
		}
	}
	
	//............................PLANSZA.........................
	
	private GraczVsGracz getPlanszaGraczVsGracz() {
		if (planszaGraczVsGracz == null) {
			planszaGraczVsGracz = new GraczVsGracz();
			planszaGraczVsGracz.setLocation(120,50);
			planszaGraczVsGracz.setVisible(false);
		}
		return planszaGraczVsGracz;
	}
	
	private PoziomLatwy getPlanszaPoziomLatwy() { //poziomy
		if (planszaPoziomLatwy == null) {
			planszaPoziomLatwy = new PoziomLatwy();
			planszaPoziomLatwy.setLocation(120,100);
			planszaPoziomLatwy.setVisible(false);
		}
		return planszaPoziomLatwy;
	}
	
	private PoziomTrudny getPlanszaPoziomTrudny() {
		if (planszaPoziomTrudny == null) {
			planszaPoziomTrudny = new PoziomTrudny();
			planszaPoziomTrudny.setLocation(120,100);
			planszaPoziomTrudny.setVisible(false);
		}
		return planszaPoziomTrudny;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // invokeLater - wykonywanie asynchronicznie
			public void run() {
				Warcaby warcaby = new Warcaby();
				warcaby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				warcaby.setVisible(true);
			}
		});
	}
}