package polaczenie;

import java.util.Iterator;

public class NasluchiwaniePolaczenia extends Thread {
	
	private Serwer serwer;
	private int liczbaGraczy = 0;
	private int liczbaDolaczonychGraczy = 0;
	private boolean mozliwoscDolaczeniaDoGry = false;
	private int liczbaGotowychGraczy = 0;
	private static final int LICZBA_GRACZY = 2;
	
	public NasluchiwaniePolaczenia(Serwer serwer) {
		this.serwer = serwer;
	}

	public void wyslijWiadomosc(Polaczenie polaczenie, Wydarzenie wydarzenie) {
		polaczenie.wysijWiadomosc(wydarzenie.wyslijWiadomosc());
	}

	public void wyslijWiadomoscDoWszystkich(Wydarzenie wydarzenie) {
		Iterator<Polaczenie> i = serwer.getPolaczenia().iterator();
		while (i.hasNext()) {
			Polaczenie polaczenie = (Polaczenie) i.next();
			if (polaczenie.isAlive()) {
				wyslijWiadomosc(polaczenie, wydarzenie);
			}
		}
	}

	public Wydarzenie odbierzWiadomosc(Polaczenie polaczenie) {
		if (polaczenie.kolejkaWiadomosci.isEmpty()) {
			return null;
		} else {
			Wydarzenie wydarzenie = new Wydarzenie((String) polaczenie.kolejkaWiadomosci.getFirst());
			polaczenie.kolejkaWiadomosci.removeFirst();
			return wydarzenie;
		}
	}

	public boolean idGracza(String nazwa) {
		Iterator<Polaczenie> i = serwer.getPolaczenia().iterator();
		while (i.hasNext()) {
			Polaczenie polaczenie = (Polaczenie) i.next();
			if (polaczenie.getNazwa().compareTo(nazwa) == 0)
				return false;
		}
		return true;
	}
	
	private void mozliwoscDolaczenieDoGry(boolean bool) {
		mozliwoscDolaczeniaDoGry = bool;
		Wydarzenie wydarzenieWyj;
		if (mozliwoscDolaczeniaDoGry) {
			wydarzenieWyj = new Wydarzenie(Wydarzenie.MOZLIWOSC_DOLACZENIA_DO_GRY);
		} else {
			wydarzenieWyj = new Wydarzenie(Wydarzenie.NIE_MOZNA_DOLACZYC_DO_GRY);
		}
		wyslijWiadomoscDoWszystkich(wydarzenieWyj);
	}
	
	private void startGry() {
		Wydarzenie wydarzenieWyj;
		liczbaGotowychGraczy = 0;
		wydarzenieWyj = new Wydarzenie(Wydarzenie.START_GRY);
		int tmp = 1;
		for (Polaczenie polaczenie : serwer.getPolaczenia()) {
			if (polaczenie.isAlive() && polaczenie.getDolaczony()) {
				wydarzenieWyj.setWiadomosc(Integer.toString(tmp));
				wyslijWiadomosc(polaczenie, wydarzenieWyj);
				tmp++;
			}
		}
	}
	
	public void run() {
		while (serwer.czyUruchomiony()) {
			for (int i = serwer.getPolaczenia().size() - 1; i >= 0; --i) {
				Polaczenie polaczenie = (Polaczenie) serwer.getPolaczenia().get(i);
				if (!polaczenie.isAlive()) {
					if (polaczenie.getNazwa() != "") {
						Wydarzenie wydarzenieWyj;
						wydarzenieWyj = new Wydarzenie(Wydarzenie.WYJSCIE_GRACZA);
						wyslijWiadomoscDoWszystkich(wydarzenieWyj);
						liczbaGraczy--;
						liczbaDolaczonychGraczy = 0;
					}
					polaczenie.close();
					serwer.getPolaczenia().remove(polaczenie);
				} else {
					Wydarzenie wydarzenie;
					while ((wydarzenie = odbierzWiadomosc(polaczenie)) != null) {
						switch (wydarzenie.getTypZdarzenia()) {
						case Wydarzenie.KLIENT_WIADOMOSC_WYSLANIE:
							if (wydarzenie.getIdGracza() != "") {
								Wydarzenie wydarzenieWyj;
								wydarzenieWyj = new Wydarzenie(Wydarzenie.WIADOMOSC_DO_WSZYSTKICH_SERWER, wydarzenie.getWiadomosc());
								wydarzenieWyj.setIdGracza(wydarzenie.getIdGracza());
								wyslijWiadomoscDoWszystkich(wydarzenieWyj);
							}
							break;
						case Wydarzenie.PROBA_LOGOWANIA_KLIENTA:
							if (wydarzenie.getIdGracza() != "") {
								if (liczbaGraczy == LICZBA_GRACZY) {
									Wydarzenie wydarzenieWyj;
									wydarzenieWyj = new Wydarzenie(Wydarzenie.BLAD_LOGOWANIA, "2 graczy w grze, nie mozna dolaczyc!");
									wyslijWiadomosc(polaczenie, wydarzenieWyj);
									wydarzenieWyj = new Wydarzenie(Wydarzenie.OGRANICZENIE_GRACZY);
									wyslijWiadomosc(polaczenie, wydarzenieWyj);
								} else if (idGracza(wydarzenie.getIdGracza())) {
									polaczenie.setNazwa(wydarzenie.getIdGracza());
									Wydarzenie wydarzenieWyj;
									wydarzenieWyj = new Wydarzenie(Wydarzenie.DOLACZENIE, wydarzenie.getIdGracza());
									wyslijWiadomoscDoWszystkich(wydarzenieWyj);
									liczbaGraczy++;
									if (liczbaGraczy == LICZBA_GRACZY) {
										mozliwoscDolaczenieDoGry(true);
									}
								} else {
									Wydarzenie wydarzenieWyj;
									wydarzenieWyj = new Wydarzenie(Wydarzenie.BLAD_LOGOWANIA,"U¿ytkownik \"" + wydarzenie.getIdGracza() + "\" ju¿ istnieje");
									wyslijWiadomosc(polaczenie, wydarzenieWyj);
									wydarzenieWyj = new Wydarzenie(Wydarzenie.BLAD_ID_GRACZA);
									wyslijWiadomosc(polaczenie, wydarzenieWyj);
								}
							}
							break;
						case Wydarzenie.PROBA_DOLACZENIA_KLIENTA_DO_GRY:
							if (polaczenie.getNazwa() != "") {
								if (liczbaGraczy != LICZBA_GRACZY) {
									Wydarzenie wydarzenieWyj;
									wydarzenieWyj = new Wydarzenie(Wydarzenie.KLIENT_NIE_DOLACZYL_DO_GRY);
									wyslijWiadomosc(polaczenie, wydarzenieWyj);
								} else {
									polaczenie.setDolaczony(true);
									Wydarzenie wydarzenieWyj;
									wydarzenieWyj = new Wydarzenie(Wydarzenie.KLIENT_DOLACZYL_DO_GRY);
									wyslijWiadomosc(polaczenie, wydarzenieWyj);
									wydarzenieWyj = new Wydarzenie(Wydarzenie.DOLACZNIE_DO_GRY, wydarzenie.getIdGracza());
									wyslijWiadomoscDoWszystkich(wydarzenieWyj);
									liczbaDolaczonychGraczy++;
									if (liczbaDolaczonychGraczy == LICZBA_GRACZY) {
										startGry();
									}
								}
							}
							break;
						case Wydarzenie.KLIENT_GOTOWY:
							if (polaczenie.getNazwa() != "") {
								liczbaGotowychGraczy++;
								if (liczbaGotowychGraczy == LICZBA_GRACZY) {
									Wydarzenie wydarzenieWyj;
									wydarzenieWyj = new Wydarzenie(Wydarzenie.WSZYSCY_GOTOWI_DO_GRY);
									wyslijWiadomoscDoWszystkich(wydarzenieWyj);
								}
							}
							break;
						case Wydarzenie.RUCH_KLIENT:
							if (wydarzenie.getIdGracza() != "") {
								Wydarzenie wydarzenieWyj;
								wydarzenieWyj = new Wydarzenie(Wydarzenie.WYSLANIE_RUCHU_SERWER, wydarzenie.getWiadomosc());
								wydarzenieWyj.setIdGracza(wydarzenie.getIdGracza());
								wyslijWiadomoscDoWszystkich(wydarzenieWyj);
							}
							break;
						case Wydarzenie.RUCH_WYNIK_KLIENT:
							if (wydarzenie.getIdGracza() != "") {
								Wydarzenie wydarzenieWyj;
								wydarzenieWyj = new Wydarzenie(Wydarzenie.WYNIK_RUCHU_WSZYSCY,wydarzenie.getWiadomosc());
								wydarzenieWyj.setIdGracza(wydarzenie.getIdGracza());
								wyslijWiadomoscDoWszystkich(wydarzenieWyj);
							}
							break;

						case Wydarzenie.ZAKONCZENIE_GRY:
								liczbaDolaczonychGraczy = 0;
							break;
						}
					}
				}
			}
			try {
				Thread.sleep(50);
			} catch (Exception ex) {
			}
		}
	}
}