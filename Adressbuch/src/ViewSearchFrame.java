import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;



/**
 * This Class ist the frame, where you can search any data in database.
 * @author yusuf
 *
 */
public class ViewSearchFrame 
{
	static JFrame suchenframe;
	static JTextField suchenfield;
	static JButton suchbutton;
	static JButton  suchabbruchbutton;
	
	public ViewSearchFrame()
	{
		initial();
	}
	
	KeyListener tfKeyListener = new KeyAdapter()
	{
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				suchbutton.doClick();
		
			}
		}
		
		
	};
	
	public void initial()
	{
		JPanel suchpanel;
		JLabel suchenlabel;
		suchenframe=new JFrame();
		suchenframe.setBounds(100, 100, 438, 130);
		suchenframe.setLocationRelativeTo(null);
		suchenframe.setVisible(true);
		suchenframe.setVisible(true);
		suchpanel=new JPanel();
		suchpanel.setLayout(null);
		
		suchenlabel=new JLabel("    Tippen Sie Ihr Suchwort ein:"); 
		suchenlabel.setBounds(1,17,210,25);
		suchpanel.add(suchenlabel);
	
		suchenfield=new JTextField();
		suchenfield.setBounds(215,17,150,25);
		suchenfield.addKeyListener(tfKeyListener);
		javax.swing.border.Border border = BorderFactory.createEtchedBorder();
		suchenfield.setBorder(border);
		suchpanel.add(suchenfield);
		
	
		suchbutton = new JButton(new ImageIcon(getClass().getResource("images/find.gif")));
		suchbutton.setBounds(235,49,55,30);
		suchpanel.add(suchbutton);
		
		suchabbruchbutton=new JButton(new ImageIcon(getClass().getResource("images/Abbruch.gif")));
		suchabbruchbutton.setBounds(285,49,55,30);
		suchabbruchbutton.setToolTipText("Abbrechen");
		suchpanel.add(suchabbruchbutton);
		
		suchenframe.add(suchpanel);
	}
}
