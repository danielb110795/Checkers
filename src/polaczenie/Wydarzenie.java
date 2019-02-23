package polaczenie;

public class Wydarzenie {
	
	public static final int PROBA_LOGOWANIA_KLIENTA = 1001;
	public static final int BLAD_LOGOWANIA = 1002;
	public static final int DOLACZENIE = 1003;
	public static final int OGRANICZENIE_GRACZY = 1006;
	public static final int BLAD_ID_GRACZA = 1007;
	public static final int MOZLIWOSC_DOLACZENIA_DO_GRY = 1101;
	public static final int NIE_MOZNA_DOLACZYC_DO_GRY = 1102;
	public static final int PROBA_DOLACZENIA_KLIENTA_DO_GRY = 1103;
	public static final int KLIENT_DOLACZYL_DO_GRY = 1104;
	public static final int KLIENT_NIE_DOLACZYL_DO_GRY = 1105;
	public static final int DOLACZNIE_DO_GRY = 1106;
	public static final int START_GRY = 1107;
	public static final int WYJSCIE_GRACZA = 1109;
	public static final int KLIENT_GOTOWY = 1110;
	public static final int WSZYSCY_GOTOWI_DO_GRY = 1111;
	public static final int ZAKONCZENIE_GRY = 1108;
	public static final int KLIENT_WIADOMOSC_WYSLANIE = 1201;
	public static final int WIADOMOSC_DO_WSZYSTKICH_SERWER = 1202;
	public static final int RUCH_KLIENT = 1301;
	public static final int WYSLANIE_RUCHU_SERWER = 1302;
	public static final int RUCH_WYNIK_KLIENT = 1304;
	public static final int WYNIK_RUCHU_WSZYSCY = 1305;	

	private String wiadomosc;
	private String idGracza = "";
	private int typZdarzenia;

	public Wydarzenie(int typ) {
		setTypZdarzenia(typ);
	}

	public Wydarzenie(int typ, String wiadomosc) {
		this(typ);
		this.wiadomosc = wiadomosc;
	}

	public Wydarzenie(String odebranaWiadomosc) {
		String string = odebranaWiadomosc;
		int tmp1 = string.indexOf('|');
		int tmp2 = string.indexOf('|', tmp1 + 1);
		String a = string.substring(0, tmp1);
		String b = string.substring(tmp1 + 1, tmp2);
		String c = string.substring(tmp2 + 1);
		try {
			setTypZdarzenia(Integer.parseInt(a));
		} catch (NumberFormatException ex) {
			setTypZdarzenia(-1);
		}
		setIdGracza(b);
		setWiadomosc(c);
	}
	
	public void setWiadomosc(String wiadomosc) {
		this.wiadomosc = wiadomosc;
	}
	
	public void setTypZdarzenia(int typ) {
		typZdarzenia = typ;
	}

	public void setIdGracza(String id) {
		idGracza = id;
	}
	
	public int getTypZdarzenia() {
		return typZdarzenia;
	}

	public String getWiadomosc() {
		return wiadomosc;
	}

	public String getIdGracza() {
		return idGracza;
	}

	public String wyslijWiadomosc() {
		String wyslanaWiadomosc = typZdarzenia + "|" + idGracza + "|" + getWiadomosc();
		return wyslanaWiadomosc;
	}
}