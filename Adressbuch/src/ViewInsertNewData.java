import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ViewInsertNewData
{
	static JFrame savenewdataframe;
	
	static JTextField tf[];
	JPanel kundenpan;
	static JLabel [] lab;
	static JButton ButtonSave;
	JButton ButtonAbbruch;
	public static String name;
	static  String vorname;
	static String strasse;
	static int hausnr;
	static String ort;
	static int plz;
	static long handy;
	
	
	public ViewInsertNewData()
	{
		GuiNewData();
	}
	
	/**
	 * This Class create a new frame, where we are specify a new data to insert in to database..
	 */
	public void GuiNewData()
	{
		savenewdataframe = new JFrame();
		savenewdataframe.setBounds(100, 100, 838, 302);
		savenewdataframe.setVisible(true);
		savenewdataframe.setLocationRelativeTo(null);

		lab=new JLabel[8];
		tf=new JTextField[7];
		kundenpan=new JPanel();
		kundenpan.setLayout(null);
		
		lab[0]=new JLabel("Name:");
		lab[0].setBounds(5,37,130,25);
		lab[0].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[0]);
		tf[0]=new JTextField();
		tf[0].setBounds(135,37,650,25);
		tf[0].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[0]);
		
		lab[1]=new JLabel("Vorname:");
		lab[1].setBounds(5,64,110,25);
		lab[1].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[1]);
		tf[1]=new JTextField();
		tf[1].setBounds(135,64,650,25);
		tf[1].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[1]);
		
		lab[2]=new JLabel("Strasse:");
		lab[2].setBounds(5,91,110,25);
		lab[2].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[2]);
		tf[2]=new JTextField();
		tf[2].setBounds(135,91,450,25);
		tf[2].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[2]);
		lab[3]=new JLabel("HausNr:");
		lab[3].setBounds(600,91,60,25);
		lab[3].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[3]);
		tf[3]=new JTextField();
		tf[3].setBounds(660,91,125,25);
		tf[3].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[3]);
		
		lab[4]=new JLabel("Ort:");
		lab[4].setBounds(5,118,110,25);
		lab[4].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[4]);
		tf[4]=new JTextField();
		tf[4].setBounds(135,118,450,25);
		tf[4].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[4]);
		lab[5]=new JLabel("PLZ:");
		lab[5].setBounds(600,118,60,25);
		lab[5].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[5]);
		tf[5]=new JTextField();
		tf[5].setBounds(660,118,125,25);
		tf[5].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[5]);
		
		lab[6]=new JLabel("Handy:");
		lab[6].setBounds(5,145,130,25);
		lab[6].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(lab[6]);
		tf[6]=new JTextField();
		tf[6].setBounds(135,145,650,25);
		tf[6].setFont(new java.awt.Font("Arial", 1, 12));
		kundenpan.add(tf[6]);
		
		
		ButtonSave=new JButton(new ImageIcon(getClass().getResource("images/save2.png")));
		ButtonSave.setToolTipText("Speichern");
		ButtonSave.setBounds(135,190,120,30);
		kundenpan.add(ButtonSave);
		
		ButtonAbbruch=new JButton(new ImageIcon(getClass().getResource("images/Abbruch2.png")));
		ButtonAbbruch.setToolTipText("Abbrechen");
		ButtonAbbruch.setBounds(265,190,120,30);
		ButtonAbbruch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				savenewdataframe.setVisible(false);
			}
		});
		kundenpan.add(ButtonAbbruch);
		
		savenewdataframe.setContentPane(kundenpan);
	

	}
}

