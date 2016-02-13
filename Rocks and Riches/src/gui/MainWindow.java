package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import main.Main;
import main.Move;

import net.miginfocom.swing.MigLayout;

public class MainWindow extends JFrame
{

	private static final long serialVersionUID = 1L;

	JPanel mainpanel = new JPanel();

	JPanel topPanel = new JPanel();
	JLabel mapLabel = new JLabel();

	JPanel movementPanel = new JPanel();
	JButton buttonUp = new JButton("↑");
	JButton buttonLeft = new JButton("←");
	JButton buttonRight = new JButton("→");
	JButton buttonDown = new JButton("↓");

	JPanel actionPanel = new JPanel();
	JButton digButton = new JButton("Dig");
	JButton invButton = new JButton("Inventory");
	
	JPanel systemPanel = new JPanel();
	JButton saveButton = new JButton("Save");
	JButton mapButton = new JButton("Map");
	
	public JTextArea textarea = new JTextArea(24,50);
	JScrollPane scroll = new JScrollPane(textarea);

	ActionListener aMove = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			switch(e.getActionCommand())
			{

			case "↑":
				Move.move(0);
				break;
			case "←":
				Move.move(1);
				break;
			case "↓":
				Move.move(2);
				break;
			case "→":
				Move.move(3);
				break;
			}

		}
	};

	ActionListener aAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand())
			{
			
			case "Dig" : new DigWindow(); break;
			case "Inventory" : new InventoryWindow(); break;
			case "Map" : new MapWindow();
			
			}
			
		}
	};
	
	ActionListener aSave = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(Main.PATH + "/player.dat"));
				os.writeObject(Main.player);
				os.close();
				textarea.append("Save successful.");
			} catch (IOException e1) {
				e1.printStackTrace();
				textarea.append("Save failed.");
			}

		}
	};
	
	public MainWindow()
	{

		super("JarPG");

		textarea.setEditable(false);
		textarea.setLineWrap(true);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		drawPlayer();

		mainpanel.setLayout(new MigLayout());
		topPanel.setLayout(new MigLayout());
		movementPanel.setLayout(new MigLayout());
		actionPanel.setLayout(new MigLayout());
		systemPanel.setLayout(new MigLayout());
		
		topPanel.add(scroll);
		topPanel.add(mapLabel);
		topPanel.setBorder(BorderFactory.createEtchedBorder());

		movementPanel.add(buttonUp,"span 2, align center, wrap");
		movementPanel.add(buttonLeft,"align left");
		movementPanel.add(buttonRight, "align right,wrap");
		movementPanel.add(buttonDown, "span 2, align center");
		movementPanel.setBorder(BorderFactory.createEtchedBorder());
		buttonUp.addActionListener(aMove);
		buttonLeft.addActionListener(aMove);
		buttonDown.addActionListener(aMove);
		buttonRight.addActionListener(aMove);
		
		actionPanel.add(digButton,"aligny top, alignx center, wrap");
		actionPanel.add(invButton, "aligny top, alignx center");
		
		invButton.addActionListener(aAction);
		digButton.addActionListener(aAction);
		actionPanel.setBorder(BorderFactory.createEtchedBorder());		
		
		systemPanel.add(saveButton,"aligny top, alignx center,wrap");
		systemPanel.add(mapButton,"aligny top, alignx center");
		
		saveButton.addActionListener(aSave);
		mapButton.addActionListener(aAction);
		systemPanel.setBorder(BorderFactory.createEtchedBorder());
		
		mainpanel.add(topPanel,"wrap");
		mainpanel.add(movementPanel,"split 2");
		mainpanel.add(actionPanel, "growy, wrap");
		mainpanel.add(systemPanel);		

		add(mainpanel);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


	}

	public void drawPlayer()
	{
		
		BufferedImage mapImg = null;
		
		try {
			mapImg = ImageIO.read(new File(Main.PATH + "/map.bmp"));			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("CATCH");
		}
		
		Graphics2D g =  mapImg.createGraphics();
		g.setColor(new Color(255,0,255));
		g.drawOval(Main.currentx-1, Main.currenty-1, 2, 2);

		ImageIcon mapIcon = new ImageIcon(mapImg);
		mapLabel.setIcon(mapIcon);

	}

}