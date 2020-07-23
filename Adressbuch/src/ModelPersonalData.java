
public class ModelPersonalData 
{
	private int Kundennummer;
	private String name;
	private String vorname;
	private String Strasse;
	private int Hausnr;
	private String Ort;
	private int PLZ;
	private long Handy;
	
	public ModelPersonalData(int Kundennr,String name,String vorname,String Strasse,int Hausnr,String Ort,int PLZ,long Handy)
	{
	  	 this.Kundennummer=Kundennr;
		 this.name=name;
		 this.vorname=vorname;
		 this.Strasse=Strasse;
		 this.Hausnr=Hausnr;
		 this.Ort=Ort;
		 this.PLZ=PLZ;
		 this.Handy=Handy;
	}
	
	public ModelPersonalData(int num)
	{
	  	this.Kundennummer=num;
	}
	
	/**
	 * 
	 * The get and set methods.
	 */
	
	public int getSchluessel(){return Kundennummer;}
	public String getname() { return name; }
	public String getvorname() { return vorname; }
	public String getStrasse() { return Strasse; }
	public int getHausnr(){return Hausnr;}
	public String getOrt(){return Ort;}
	public int getplz(){return PLZ;}
	public long getHandy(){return Handy;}
	
	public void setSchluessel(int knr) { this.Kundennummer = knr; }
	public void setname(String name) { this.name = name; }
	public void setvorname(String vorname) { this.vorname =vorname; }
	public void setStrasse(String strasse){this.Strasse=strasse;}
	public void setHausnr(int Hausnr) {this.Hausnr = Hausnr;}
	public void setort(String Ort) { this.Ort = Ort; }
	public void setplz(int plz) { this.PLZ=plz; }
	public void sethandy(long handy){this.Handy=handy;}
	
}
