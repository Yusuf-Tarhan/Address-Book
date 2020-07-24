

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.lang.Thread;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class ViewMainFrame extends JFrame implements  MouseListener
{

	private static final long serialVersionUID = 1L;
	static  JFrame frame;
	JFrame savenewdataframe;
	static JTable list;
	static Object [][] objektdaten;
	static Object [] spalten;
	static JScrollPane scroll;
	JTextField tf[];
	static JPanel kundenpan,MainPanel;
	static JLabel [] lab;
	JButton ButtonSave,ButtonAbbruch;
	static DefaultTableModel defTableModel;
	static JButton ButtonDelete;
	static JButton ButtonUpdate;
	static JButton ButtonSearch;
	static JButton ButtonBack;
	static JButton ButtonExport;
	static JButton ButtonExport2;
	static JButton ButtonImport;
	static JButton ButtonDeleteAll;
	JButton  ButtonNeu;
	 
	 Date d;
	 Calendar calendar;
	 JLabel labuhr;
	
	 static int switchupdatenumber;
	
	
	 Container cp= getContentPane();
	 static ViewMainFrame gui;
	 static ModelPersonalData kunde;
	 static int suchwort2;
	 static JButton printbutton;
	static DefaultTableModel foundlistmodel;
	static JTable foundlisttable;
	static JScrollPane newscroll;

	
	
	
		/**
	 * By opening this application will be showed all data sets in table.
	 */
	public void showtable()
	  {
		ModelPersonalData [] daten;
		int KundenAnzahl;

		KundenAnzahl=ModelDBQuery.getSchlüssel();
		daten= new ModelPersonalData[KundenAnzahl];
		
		 	 ModelDBQuery.StartAbfrage();
		 	 
		for(int i=0; i < daten.length; i++)
		{
		    daten[i] = ModelDBQuery.getNext();
		 }
		 	 
		objektdaten=new Object[KundenAnzahl][8];
		for(int i = 0; i <KundenAnzahl; i++)
		{
					
			objektdaten[i][0]=daten[i].getSchluessel()+"";
			objektdaten[i][1]=daten[i].getname();
			objektdaten[i][2]=daten[i].getvorname()+"";
			objektdaten[i][3]=daten[i].getStrasse()+"";
			objektdaten[i][4]=daten[i].getHausnr()+"";
			objektdaten[i][5]=daten[i].getOrt()+"";
			objektdaten[i][6]=daten[i].getplz()+"";
			objektdaten[i][7]=daten[i].getHandy()+"";
		}
		 	 
		 	 
		spalten=new Object[8];
		spalten[0]="Kundennummer";
		spalten[1]="Name";
		spalten[2]="Vorname";
		spalten[3]="Strasse";
		spalten[4]="Hausnummer";
		spalten[5]="Ort";
		spalten[6]="PLZ";
		spalten[7]="Telefonnummer";
	
		defTableModel = new DefaultTableModel(objektdaten,spalten);
		list=new JTable(defTableModel);
		list.setRowHeight(30);
		list.addMouseListener(this);
		scroll = new JScrollPane(list);
		scroll.setBounds( 20, 100, 780, 300 ); 
		MainPanel.add(scroll);
		switchupdatenumber=1;
		if(list.getRowCount()>0)
		{
			list.setRowSelectionInterval(0, 0);
		}
		
		if(list.getRowCount()>0)
		{
			ButtonDelete.setEnabled(true);
			ButtonUpdate.setEnabled(true);
			ButtonExport2.setEnabled(true);
			ButtonExport.setEnabled(true);
			printbutton.setEnabled(true);
			ButtonSearch.setEnabled(true);
			ButtonDeleteAll.setEnabled(true);

		}
		
	  }
	
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public ViewMainFrame() 
	{
		initialize();
	}

	/**
	 * This is the first frame that will be showed by opening the application
	 */
	private void initialize() 
	{
	
		MainPanel=new JPanel();
		MainPanel.setLayout(null);
		
		
		
		ButtonExport=new JButton(new ImageIcon(getClass().getResource("images/save2.png")));
		ButtonExport.setToolTipText("Speichern unter ");
		ButtonExport.setBounds(21, 1, 50, 30);
		ButtonExport.setEnabled(false);
		MainPanel.add(ButtonExport);
		
		ButtonExport2=new JButton(new ImageIcon(getClass().getResource("images/save3.png")));
		ButtonExport2.setToolTipText("Speichern Sie die Liste. ");
		ButtonExport2.setEnabled(false);
		ButtonExport2.setBounds(72, 1, 50, 30);
		MainPanel.add(ButtonExport2);
		
		ButtonImport=new JButton(new ImageIcon(getClass().getResource("images/Open.png")));
		ButtonImport.setToolTipText("Laden Sie eine Liste hoch. ");
		ButtonImport.setBounds(123, 1, 50, 30);
		MainPanel.add(ButtonImport);
		
		ButtonDeleteAll=new JButton(new ImageIcon(getClass().getResource("images/remove.png")));
		ButtonDeleteAll.setToolTipText("Löschen Sie die ganze Adressenliste ");
		ButtonDeleteAll.setBounds(174, 1, 50, 30);
		ButtonDeleteAll.setEnabled(false);
		MainPanel.add(ButtonDeleteAll);

		
		ButtonNeu=new JButton(new ImageIcon(getClass().getResource("images/new2.png")));
		ButtonNeu.setToolTipText("Fügen Sie bitte neue Adresse hinzu");
		ButtonNeu.setBounds(20, 52, 145, 37);
		MainPanel.add(ButtonNeu);
		
		ButtonDelete = new JButton(new ImageIcon(getClass().getResource("images/delete3.png")));
		ButtonDelete.setToolTipText("Löschen Sie die selektierte Zeile ");
		ButtonDelete.setEnabled(false);		
		ButtonDelete.setBounds(175, 52, 145, 37);
		MainPanel.add(ButtonDelete);

		ButtonUpdate = new JButton(new ImageIcon(getClass().getResource("images/edit.png")));
		ButtonUpdate.setToolTipText("Bearbeiten Sie die selektierte Zeile");
		ButtonUpdate.setEnabled(false);		
		ButtonUpdate.setBounds(330, 52, 145, 37);
		MainPanel.add(ButtonUpdate);

		
		ButtonSearch = new JButton(new ImageIcon(getClass().getResource("images/find2.png")));
		ButtonSearch.setBounds(485, 52, 145, 37);
		ButtonSearch.setEnabled(false);
		MainPanel.add(ButtonSearch);

		
		printbutton = new JButton(new ImageIcon(getClass().getResource("images/print2.png")));
		printbutton.setBounds(640, 52, 145, 37);
		printbutton.setEnabled(false);
		MainPanel.add(printbutton);
		
		ButtonBack = new JButton(new ImageIcon(getClass().getResource("images/zurück.png")));
		ButtonBack.setBounds(20, 420, 105, 45);
		ButtonBack.setToolTipText("Zurück zur Liste ");
		MainPanel.add(ButtonBack);
		ButtonBack.setEnabled(false);
		
		
		labuhr=new JLabel();
		Thread thr=new Thread()
		{
			public void run()
			{
				
				while(true)
				{
					try 
					{
						d=new Date();
						
						calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
						calendar.setTime(d); 
						int	hour=calendar.get(Calendar.HOUR_OF_DAY);		
						int minute=calendar.get(Calendar.MINUTE);
						int second=calendar.get(Calendar.SECOND);
						String hour2="";
						String minute2="";
						String second2="";
						if(hour<10){hour2="0"+hour;}
						if(hour>9){hour2=""+hour;}
						if(minute<10){minute2="0"+minute;}
						if(minute>9){minute2=""+minute;}
						if(second<10){second2="0"+second;}
						if(second>9){second2=""+second;}

						labuhr.setText(hour2+":"+ minute2+":"+second2);						
						labuhr.setFont(new java.awt.Font("Arial", 1, 30));
						labuhr.setBounds(550, 420, 145, 37);
						sleep(1000);
					}
					catch (InterruptedException e) 
					{
						
						
					}
					
					
				}
			}
		};
		MainPanel.add(labuhr);	

		thr.start();
		cp.add(MainPanel);
		showtable();
		
		
	}
	
	
}
	
