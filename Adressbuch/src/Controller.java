import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


public class Controller implements ActionListener,KeyListener
{
	//Controllerfunctions modview;
	ViewMainFrame view;
	static int insertcounter, updatecounter;
	ModelPersonalData kunde;
	int searchupdatedeleterow;
	int rowsearch,columnsearch;
	
	public Controller()
	{
		view=new ViewMainFrame();
		view.ButtonNeu.addActionListener(this::clickButtonNeu);
		ViewMainFrame.ButtonUpdate.addActionListener(this::clickButtonUpdate);
		ViewMainFrame.ButtonDelete.addActionListener(this::clickButtonDelete);
		ViewMainFrame.ButtonSearch.addActionListener(this::clickButtonSearch);
		ViewMainFrame.printbutton.addActionListener(this::print);
		ViewMainFrame.ButtonExport.addActionListener(this::export);
		ViewMainFrame.ButtonExport2.addActionListener(this::export2);
		ViewMainFrame.ButtonImport.addActionListener(this::importdata);
	
		
		insertcounter=0;
		updatecounter=0;
		
	}
	
	
	
	public void print(ActionEvent e) 
	{
		 MessageFormat header = new MessageFormat("Kundendaten");
		 MessageFormat footer = new MessageFormat("Seite {0,number,integer}");
		
		
		try 
		{
			ViewMainFrame.list.print(JTable.PrintMode.FIT_WIDTH, header, footer);
		} 
		catch (PrinterException e1) {e1.printStackTrace();}
	}

	public void importdata(ActionEvent e) 
	{
		try {importfile();} 
		catch (IOException e1) {e1.printStackTrace();}
	}

	

	public void export(ActionEvent e) 
	{
		
			try 
			{
				exportfile("");
			} 
			catch (ClassNotFoundException | IOException | SQLException e2) 
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	
	}

	public void export2(ActionEvent e) 
	{
		try 
		{
			exportfile(null);
		} 
		catch (ClassNotFoundException | IOException | SQLException e2) 
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
	}

	
	
	public void clickButtonNeu(ActionEvent e)
	{
		insertcounter=1;
		new ViewInsertNewData();
		ViewInsertNewData.ButtonSave.addActionListener(this::saveorupdate);
	}
	
	public void saveorupdate(ActionEvent e)
	{
		//System.out.println("insertcounter: "+ insertcounter);
		//System.out.println("updatecounter: "+ updatecounter);
	
			if(insertcounter==1) 
			{
				System.out.println("insertcounter");
				insertnewdata();
			}
			
			if(updatecounter==1) 
			{
				System.out.println("updatecounter");

				updatetable();
				
			}
			insertcounter=0;
			updatecounter=0;
	}
	
	public void clickButtonUpdate(ActionEvent e) 
	{
		System.out.println("insertcounter: "+ insertcounter);
		System.out.println("updatecounter: "+ updatecounter);
		updatecounter=1;
		showupdatetable();
		if(ViewMainFrame.list.getSelectedRowCount()!=0)
		{		
			ViewInsertNewData.ButtonSave.addActionListener(this::saveorupdate);
		}
	}
	
	public void clickButtonDelete(ActionEvent e) 
	{
		deletedata();
		ViewMainFrame.ButtonBack.setEnabled(false);
	}
	
	public void clickButtonSearch(ActionEvent e) 
	{
		new ViewSearchFrame();
		ViewSearchFrame.suchbutton.addActionListener(this::searchdata);
		ViewSearchFrame.suchabbruchbutton.addActionListener(this::searchcancelled);
		ViewMainFrame.ButtonBack.addActionListener(this::backtomainlist);
	}
	
	public void searchdata(ActionEvent e)
	{
		ViewSearchFrame.suchenframe.dispose();
		ViewMainFrame.ButtonBack.setEnabled(true);
		search();
		ViewMainFrame.scroll.setVisible(false);
		
	}
	
	public void searchcancelled(ActionEvent e)
	{
		ViewSearchFrame.suchenframe.setVisible(false);
	}
	
	public void backtomainlist(ActionEvent e)
	{
		ViewMainFrame.scroll.setVisible(true);
		ViewMainFrame.ButtonBack.setEnabled(false);
		ViewMainFrame.MainPanel.remove(ViewMainFrame.newscroll);
		ViewSearchFrame.suchenframe.removeAll();
		ViewMainFrame.switchupdatenumber=1;
		ViewMainFrame.foundlistmodel.setRowCount(0);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args)
	{	
		Controller congui=new Controller();
		congui.view.setSize(838,552);
		congui.view.setLocationRelativeTo(null);
		congui.view.setVisible(true);
	}
	
	
	public void insertnewdata()
	{

		//newdata=new ViewInsertNewData();
		ViewInsertNewData.name=ViewInsertNewData.tf[0].getText().trim();
		ViewInsertNewData.vorname=ViewInsertNewData.tf[1].getText().trim();
		ViewInsertNewData.strasse=ViewInsertNewData.tf[2].getText().trim();
	    
	    try{ ViewInsertNewData.hausnr=(Integer.valueOf(ViewInsertNewData.tf[3].getText().trim()).intValue()); }catch(NumberFormatException nfe) {ViewInsertNewData.hausnr = 0;}
	    ViewInsertNewData.ort=ViewInsertNewData.tf[4].getText().trim();
		
	    try{ ViewInsertNewData.plz=(Integer.valueOf(ViewInsertNewData.tf[5].getText().trim()).intValue()); }catch(NumberFormatException nfe) {ViewInsertNewData.plz = 0;}
	    try{ ViewInsertNewData.handy=(Long.valueOf(ViewInsertNewData.tf[6].getText().trim()).longValue()); }catch(NumberFormatException nfe) {ViewInsertNewData.handy = 0;}
	    
	    kunde=new ModelPersonalData(ModelDBQuery.getSchlüssel()+1,ViewInsertNewData.name,ViewInsertNewData.vorname,ViewInsertNewData.strasse,ViewInsertNewData.hausnr,ViewInsertNewData.ort,ViewInsertNewData.plz,ViewInsertNewData.handy);
	    int neu=ModelDBQuery.getSchlüssel();
	    ModelDBQuery.insert(kunde);
	   
	    ViewInsertNewData.savenewdataframe.dispose();
	    ViewMainFrame.defTableModel.insertRow(neu, new Object[]{neu+1,ViewInsertNewData.name,ViewInsertNewData.vorname,ViewInsertNewData.strasse,ViewInsertNewData.hausnr,ViewInsertNewData.ort,ViewInsertNewData.plz,ViewInsertNewData.handy,} );
	    if(ViewMainFrame.list.getRowCount()>0)
		{
			ViewMainFrame.ButtonDelete.setEnabled(true);
			ViewMainFrame.ButtonUpdate.setEnabled(true);
			ViewMainFrame.ButtonExport2.setEnabled(true);
			ViewMainFrame.ButtonExport.setEnabled(true);
			ViewMainFrame.printbutton.setEnabled(true);
			ViewMainFrame.ButtonSearch.setEnabled(true);
		} 
	   
			ViewMainFrame.list.setRowSelectionInterval(0, 0);
		
	}
	
	
	/**
	 * Shows the data which will be updated
	 */
	public void showupdatetable()
	{
		
		if((ViewMainFrame.switchupdatenumber==1 && ViewMainFrame.list.getSelectedRowCount()==0) || (ViewMainFrame.switchupdatenumber==2 && ViewMainFrame.foundlisttable.getSelectedRowCount()==0)  )
		{
			JOptionPane.showMessageDialog(null,"Wählen Sie bitte eine Zeile aus","Titel", JOptionPane.CANCEL_OPTION);
		}
	
		
		if(ViewMainFrame.switchupdatenumber==1 && ViewMainFrame.list.getSelectedRowCount()!=0)
		{
			new ViewInsertNewData();

		 	ViewInsertNewData.tf[0].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 1)));
		 	ViewInsertNewData.tf[1].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 2)));
		 	ViewInsertNewData.tf[2].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 3)));
		 	ViewInsertNewData.tf[3].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 4)));
		 	ViewInsertNewData.tf[4].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 5)));
		 	ViewInsertNewData.tf[5].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 6)));
		 	ViewInsertNewData.tf[6].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(ViewMainFrame.list.getSelectedRow(), 7)));
		}
		if(ViewMainFrame.switchupdatenumber==2 && ViewMainFrame.foundlisttable.getSelectedRowCount()!=0)
		{

			new ViewInsertNewData();

			columnsearch=0;
			rowsearch = ViewMainFrame.foundlisttable.getSelectedRow();
			searchupdatedeleterow=Integer.parseInt(ViewMainFrame.foundlisttable.getValueAt(rowsearch, columnsearch).toString());
			//System.out.println("searchupdatedeleterow="+ searchupdatedeleterow);
		 	ViewInsertNewData.tf[0].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 1)));
		 	ViewInsertNewData.tf[1].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 2)));
		 	ViewInsertNewData.tf[2].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 3)));
		 	ViewInsertNewData.tf[3].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 4)));
		 	ViewInsertNewData.tf[4].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 5)));
		 	ViewInsertNewData.tf[5].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 6)));
		 	ViewInsertNewData.tf[6].setText(String.valueOf(ViewMainFrame.defTableModel.getValueAt(searchupdatedeleterow-1, 7)));
		 	System.out.println("karlarda");
		}
	}
	
	
	public void setdatatable()
	{
		ViewInsertNewData.name=ViewInsertNewData.tf[0].getText();
		ViewInsertNewData.vorname=ViewInsertNewData.tf[1].getText();
		ViewInsertNewData.strasse=ViewInsertNewData.tf[2].getText();
	 	try{ ViewInsertNewData.hausnr=(Integer.valueOf(ViewInsertNewData.tf[3].getText())); }catch(NumberFormatException nfe) {ViewInsertNewData.hausnr = 0;}
	 	ViewInsertNewData.ort=ViewInsertNewData.tf[4].getText();
	    try{ ViewInsertNewData.plz=(Integer.valueOf(ViewInsertNewData.tf[5].getText())); }catch(NumberFormatException nfe) {ViewInsertNewData.plz = 0;}
	    try{ ViewInsertNewData.handy=(Long.valueOf(ViewInsertNewData.tf[6].getText())); }catch(NumberFormatException nfe) {ViewInsertNewData.handy = 0;}
	}
	
	
	/**
	 *  Updates the table.
	 */
	public void updatetable()
	{
	
		if(ViewMainFrame.switchupdatenumber==1)
		{
		 	
			setdatatable();
		    kunde=new ModelPersonalData(ViewMainFrame.list.getSelectedRow()+1,ViewInsertNewData.name,ViewInsertNewData.vorname,ViewInsertNewData.strasse,ViewInsertNewData.hausnr,ViewInsertNewData.ort,ViewInsertNewData.plz,ViewInsertNewData.handy);
			 ModelDBQuery.update(kunde);
			 
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.name, ViewMainFrame.list.getSelectedRow(), 1);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.vorname, ViewMainFrame.list.getSelectedRow(), 2);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.strasse, ViewMainFrame.list.getSelectedRow(), 3);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.hausnr, ViewMainFrame.list.getSelectedRow(), 4);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.ort, ViewMainFrame.list.getSelectedRow(), 5);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.plz, ViewMainFrame.list.getSelectedRow(), 6);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.handy, ViewMainFrame.list.getSelectedRow(), 7);
		}
		
		if(ViewMainFrame.switchupdatenumber==2)
		{
			setdatatable();
			searchupdatedeleterow=Integer.parseInt(ViewMainFrame.foundlisttable.getValueAt(rowsearch,columnsearch).toString());

		    
		    kunde=new ModelPersonalData(searchupdatedeleterow,ViewInsertNewData.name,ViewInsertNewData.vorname,ViewInsertNewData.strasse,ViewInsertNewData.hausnr,ViewInsertNewData.ort,ViewInsertNewData.plz,ViewInsertNewData.handy);
			 ModelDBQuery.update(kunde);
			 
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.name, searchupdatedeleterow-1, 1);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.vorname,searchupdatedeleterow-1, 2);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.strasse, searchupdatedeleterow-1, 3);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.hausnr,searchupdatedeleterow-1, 4);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.ort, searchupdatedeleterow-1, 5);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.plz,searchupdatedeleterow-1, 6);
			 ViewMainFrame.defTableModel.setValueAt(ViewInsertNewData.handy,searchupdatedeleterow-1, 7);
			 
			 ViewMainFrame.MainPanel.remove(ViewMainFrame.newscroll);
			 ViewMainFrame.scroll.setVisible(true);
			 ViewSearchFrame.suchenframe.removeAll();
			
		}
			 ViewInsertNewData.savenewdataframe.dispose();
			 ViewMainFrame.ButtonBack.setEnabled(false);
	}
	
	/**
	 * Deletes the row that is selected.
	 */
	public void deletedata()
	{
		int selectdelete;
		int deletesearchrow;
		int searchcolumn;
		if((ViewMainFrame.switchupdatenumber==1 && ViewMainFrame.list.getSelectedRowCount()==0) || (ViewMainFrame.switchupdatenumber==2 && ViewMainFrame.foundlisttable.getSelectedRowCount()==0)  )
		{
			JOptionPane.showMessageDialog(null,"Wählen Sie bitte eine Zeile aus","Titel", JOptionPane.CANCEL_OPTION);
		}
		
		if(ViewMainFrame.switchupdatenumber==1 && !(ViewMainFrame.list.getSelectedRowCount()==0))
		{

			
	    	int result=JOptionPane.showConfirmDialog(null, "Wollen Sie die Zeile wirklich löschen ?",null, JOptionPane.YES_NO_OPTION);
 			if(result == JOptionPane.YES_OPTION) 
 			{
				selectdelete=ViewMainFrame.list.getSelectedRow()+1;
				ViewMainFrame.defTableModel.removeRow(selectdelete-1);
				for(int i=selectdelete; i<=ViewMainFrame.list.getRowCount(); i++)
				{
					ViewMainFrame.defTableModel.setValueAt(i, i-1, 0);
		
				}
				ModelDBQuery.delete(selectdelete);
				
				for(int i=selectdelete; i<ViewMainFrame.list.getRowCount()+1; i++)
				{
					ModelDBQuery.updatelöschen(i);
				
				}
				
				if(ViewMainFrame.list.getRowCount()==0)
				{
					ViewMainFrame.ButtonDelete.setEnabled(false);
					ViewMainFrame.ButtonUpdate.setEnabled(false);
					ViewMainFrame.ButtonExport2.setEnabled(false);
					ViewMainFrame.ButtonExport.setEnabled(false);
					ViewMainFrame.printbutton.setEnabled(false);
					ViewMainFrame.ButtonSearch.setEnabled(false);
				}
				if(ViewMainFrame.list.getRowCount()>0)
				{
					ViewMainFrame.list.setRowSelectionInterval(0, 0);
				}

 			}
		}
			
		if(ViewMainFrame.switchupdatenumber==2 && !(ViewMainFrame.foundlisttable.getSelectedRowCount()==0))
		{
	    	int result2=JOptionPane.showConfirmDialog(null, "Wollen Sie die Zeile wirklich löschen ?",null, JOptionPane.YES_NO_OPTION);
 			if(result2 == JOptionPane.YES_OPTION) 
 			{
				searchcolumn = 0;
				deletesearchrow = ViewMainFrame.foundlisttable.getSelectedRow();
				searchupdatedeleterow=Integer.parseInt(ViewMainFrame.foundlisttable.getValueAt(deletesearchrow, searchcolumn).toString());
	
				ViewMainFrame.defTableModel.removeRow(searchupdatedeleterow-1);
				for(int i=searchupdatedeleterow; i<=ViewMainFrame.list.getRowCount(); i++)
				{
					ViewMainFrame.defTableModel.setValueAt(i, i-1, 0);
		
				}
	
				ModelDBQuery.delete(searchupdatedeleterow);
				for(int i=searchupdatedeleterow; i<ViewMainFrame.list.getRowCount()+1; i++)
				{
					ModelDBQuery.updatelöschen(i);
					
				}
				ViewMainFrame.MainPanel.remove(ViewMainFrame.newscroll);
				ViewMainFrame.scroll.setVisible(true);
				ViewSearchFrame.suchenframe.removeAll();
				ViewMainFrame.switchupdatenumber=1;
				if(ViewMainFrame.list.getRowCount()>0)
				{
					ViewMainFrame.list.setRowSelectionInterval(0, 0);
				}
				ViewMainFrame.ButtonBack.setEnabled(false);
 			}
		}
		
	}
	

	/**
	 * Search for the word entered in the input field.
	 */
	public void search()
	{
		Object neuspalten[];
		Object [][] suchobjekt;
		int searchcounter,searchcounter2;
		searchcounter=0;
		searchcounter2=0;
		String suchwort=ViewSearchFrame.suchenfield.getText().trim();	
		for(int i=0; i<ViewMainFrame.list.getRowCount(); i++)
		{
			/**
			 * Here is determined the array size of found data. 
			 */
			if(suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 1)) || suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 2)) || suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 3)) ||suchwort.equalsIgnoreCase(ViewMainFrame.defTableModel.getValueAt(i, 4).toString())||suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 5))||suchwort.equalsIgnoreCase(ViewMainFrame.defTableModel.getValueAt(i, 6).toString())||suchwort.equalsIgnoreCase(ViewMainFrame.defTableModel.getValueAt(i, 7).toString()))
			{
			
				searchcounter2=searchcounter2+1;
			}
		}
		
		
		suchobjekt=new Object[searchcounter2][8];

		int counter=0;
		for(int i=0; i<ViewMainFrame.list.getRowCount(); i++)
		{
			if(suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 1)) || suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 2)) || suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 3)) ||suchwort.equalsIgnoreCase(ViewMainFrame.defTableModel.getValueAt(i, 4).toString())||suchwort.equalsIgnoreCase((String)ViewMainFrame.defTableModel.getValueAt(i, 5))||suchwort.equalsIgnoreCase(ViewMainFrame.defTableModel.getValueAt(i, 6).toString())||suchwort.equalsIgnoreCase(ViewMainFrame.defTableModel.getValueAt(i, 7).toString()))

			{
				
				searchcounter=searchcounter+1;
				suchobjekt[searchcounter-1][0]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 0);
				suchobjekt[searchcounter-1][1]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 1);
				suchobjekt[searchcounter-1][2]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 2);
				suchobjekt[searchcounter-1][3]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 3);
				suchobjekt[searchcounter-1][4]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 4);
				suchobjekt[searchcounter-1][5]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 5);
				suchobjekt[searchcounter-1][6]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 6);
				suchobjekt[searchcounter-1][7]=(Object)ViewMainFrame.defTableModel.getValueAt(i, 7);
				counter=counter+1;
			}
			
		}
		
		if(counter==0)
		{
			JOptionPane.showMessageDialog(null,"Kein Treffer","Titel", JOptionPane.CLOSED_OPTION);
		}
		neuspalten=new Object[8];
		neuspalten[0]="Kundennummer";
		neuspalten[1]="Name";
		neuspalten[2]="Vorname";
		neuspalten[3]="Strasse";
		neuspalten[4]="Hausnummer";
		neuspalten[5]="Ort";
		neuspalten[6]="PLZ";
		neuspalten[7]="Telefonnummer";
	
		ViewMainFrame.foundlistmodel=new DefaultTableModel(suchobjekt,neuspalten);
		ViewMainFrame.foundlisttable=new JTable(ViewMainFrame.foundlistmodel);
		
		ViewMainFrame.foundlisttable.setRowHeight(30);
		//foundlisttable.addMouseListener();
		ViewMainFrame.newscroll = new JScrollPane(ViewMainFrame.foundlisttable);
		ViewMainFrame.newscroll.setBounds( 20, 100, 780, 300 ); 
		
		ViewMainFrame.MainPanel.add(ViewMainFrame.newscroll);
		
		ViewMainFrame.switchupdatenumber=2;
		
			
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 *  Saves the list. If the parameter is null, the list is saved in a directory that has already been defined. 
	 *  Otherwise, the list is saved in the location selected by the user.
	 */
	public void exportfile(String param) throws IOException, ClassNotFoundException, SQLException

	{
		
    	try
    	{
    		
    		if(param==null)
    		{
    	    	File filesave=new File("C:\\Users\\yusuf\\eclipse-workspace\\AdressBuch.txt");
    	    	filesave.createNewFile();
    			
	    		FileWriter fw=new FileWriter(filesave.getAbsoluteFile());
	        	BufferedWriter bw=new BufferedWriter(fw);

	        	for(int i=0; i<ViewMainFrame.list.getRowCount(); i++)
	        	{
	        		for(int j=0; j<ViewMainFrame.list.getColumnCount(); j++)
	        		{
	        			if(j==ViewMainFrame.list.getColumnCount()-1)
	        			{
		        			bw.write(ViewMainFrame.list.getValueAt(i, j).toString());
		    
	        			}
	        			else
	        			{
		        			bw.write(ViewMainFrame.list.getValueAt(i, j).toString()+",");
	        				
	        			}
	        			
	        		}
	        		bw.write("\n");
	        	}
	        	bw.close();
	        	fw.close();
	        	JOptionPane.showMessageDialog(null, "Die Daten sind erfolgreich in das Verzeichniss " + filesave.getAbsolutePath()+ " gespeichert.");

    		}
    		
    		else
    		{
    				JFileChooser chooser = new JFileChooser();
    				int status=chooser.showSaveDialog(null);
	    	    	if(status==JFileChooser.APPROVE_OPTION)
	    	    	{
		    	    	File filesave=new File(chooser.getSelectedFile().getAbsolutePath());

		    	    	String phadlänge=filesave.toString();
		    	    	char [] phad=phadlänge.toCharArray();
		    	    	char clonphad[]=new char[phad.length];
		    	    	String controll="txt";
		    	    	String con="";
		    	    	for(int i=0; i<phad.length; i++)
		    	    	{
		    	    		clonphad[i]=phad[i];
		    	    	}
		    	    	con=""+clonphad[phad.length-1]+clonphad[phad.length-2]+clonphad[phad.length-3];
		    	    	System.out.println("con"+con);
		    	    	if(controll.equals(con))
		    	    	{
		    	    		filesave.createNewFile();
			    			
		    	    		FileWriter fw=new FileWriter(filesave.getAbsoluteFile());
		    	        	BufferedWriter bw=new BufferedWriter(fw);
		
		    	        	for(int i=0; i<ViewMainFrame.list.getRowCount(); i++)
		    	        	{
		    	        		for(int j=0; j<ViewMainFrame.list.getColumnCount(); j++)
		    	        		{
		    	        			if(j==ViewMainFrame.list.getColumnCount()-1)
		    	        			{
		    		        			bw.write(ViewMainFrame.list.getValueAt(i, j).toString());
		    		    
		    	        			}
		    	        			else
		    	        			{
		    		        			bw.write(ViewMainFrame.list.getValueAt(i, j).toString()+",");
		    	        				
		    	        			}
		    	        			
		    	        		}
		    	        		bw.write("\n");
		    	        	}
		    	        	bw.close();
		    	        	fw.close();
		    	        	JOptionPane.showMessageDialog(null, "Die Daten sind erfolgreich gespeichert.");	
		    	    	}
		    	    	if(!controll.equals(con))
		        		{
		        			JOptionPane.showMessageDialog(null,"Sie können die Daten nur als txt Format speichern","Titel", JOptionPane.CANCEL_OPTION);
		        		}
	    	    	}
    		}
	    	    
    	}
    	catch (Exception ex) {ex.printStackTrace();} 
    	
    }
	
	/**
	 * 
	 * @throws IOException if can't be loaded a file, then either is the file don't exist or the file is incompatible.
	 */
	public void importfile() throws IOException
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter=new FileNameExtensionFilter("Text Files","txt","text");
		chooser.setFileFilter(filter);
		int status2=chooser.showOpenDialog(null);
    	if(status2==JFileChooser.APPROVE_OPTION)
    	{
			File inputFile=new File(chooser.getSelectedFile().getAbsolutePath());
	
			int i=8;
			String Row;
			Object [] RowData = null;

	        FileReader fr = new FileReader(inputFile);
	        BufferedReader br = new BufferedReader(fr);
	        
			
	        if(i==8)
	        {
	        
		        int SpaltenCount=0;
		        ModelDBQuery.deleteAll();
			    ViewMainFrame.defTableModel.setRowCount(0);
			    while ((Row = br.readLine()) != null)
			    {
			       while(SpaltenCount<8)
			       {
				      RowData = Row.split(",");
				      SpaltenCount++;
				   }
			       ModelDBQuery.insert(new ModelPersonalData(ModelDBQuery.getSchlüssel()+1,RowData[1].toString(),RowData[2].toString(),RowData[3].toString(),Integer.parseInt(RowData[4].toString()),RowData[5].toString(),Integer.parseInt(RowData[6].toString()),Long.parseLong(RowData[7].toString())));
			       ViewMainFrame.defTableModel.insertRow(ModelDBQuery.getSchlüssel()-1, new Object[]{RowData[0],RowData[1],RowData[2],RowData[3],RowData[4],RowData[5],RowData[6],RowData[7],} );
			       SpaltenCount=0;
				   ViewMainFrame.list.setRowSelectionInterval(0, 0);

			     }
			    fr.close();
			    br.close();
			    ViewMainFrame.ButtonDelete.setEnabled(true);
				ViewMainFrame.ButtonUpdate.setEnabled(true);
				ViewMainFrame.ButtonExport2.setEnabled(true);
				ViewMainFrame.ButtonExport.setEnabled(true);
				ViewMainFrame.printbutton.setEnabled(true);
				ViewMainFrame.ButtonSearch.setEnabled(true);
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(null, "Die Datei kann nich geöffnet werden.Sie ist inkompatible. ");
	        }
    	}
	}
	
	
	
	
	
	
	
		
}
