import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import level.World;
import net.miginfocom.swing.MigLayout;

public class WorldWindow extends JDialog
{

	private static final long serialVersionUID = -6672888944649399921L;

	String[] TEMPERATURES = { "Very Cold", "Cold", "Average", "Hot", "Very Hot" };
	String[] SIZES = { "Small", "Medium", "Large" };
	
	String PATH = "worlds/";
	String PATH_REGIONS;
	
	int worldSize;
	int nContinents;
	boolean poles;
	boolean beaches;
	World world;	
	int nGen;
	int sTemp;
	int mountains;
	int sMountain;
	int nLakes;
	int sLakes;
	int twistiness;
	int nRivers;
			
	JTabbedPane mainpane = new JTabbedPane();
	
	JPanel countpanel = new JPanel();
	JPanel parameterpanel = new JPanel();
	
	JPanel worldpanel = new JPanel();
	
	JLabel imgPane = new JLabel();
	
	JLabel lWorldSize = new JLabel("World size (n*x): ");
	JLabel lContinents = new JLabel("Continent seeds: ");
	JLabel lPoles = new JLabel("Generate poles?");
	JLabel lGen = new JLabel("Generation passes: ");
	JLabel lBeach = new JLabel("Generate beaches?");
	JLabel lTemperature = new JLabel("Temperature: ");
	JLabel lMountains = new JLabel ("Mountains: ");
	JLabel lMountainSize = new JLabel("Mountain size: ");
	JLabel lLakes = new JLabel("Lakes: ");
	JLabel lLakeSize = new JLabel("Lake size: ");
	JLabel lRiverTwistiness = new JLabel("River twistiness: ");
	JLabel lName = new JLabel("World Name: ");
	JLabel lRivers = new JLabel("Rivers: ");
	
	JTextField fWorldSize = new JTextField(4);
	JTextField fContinents = new JTextField(4);
	JTextField fGen = new JTextField(4);
	JTextField fMountains = new JTextField(4);
	JTextField fLakes = new JTextField(4);
	JTextField fName = new JTextField(20);
	JTextField fRivers = new JTextField(4);
	
	JCheckBox cPoles = new JCheckBox();
	JCheckBox cBeach = new JCheckBox();
	
	JComboBox<String> bTemperature = new JComboBox<>(TEMPERATURES);
	JComboBox<String> bMountainSize = new JComboBox<>(SIZES);
	JComboBox<String> bLakeSize = new JComboBox<>(SIZES);
	
	JButton newWorld = new JButton("Generate new");
	JButton useWorld = new JButton("Use this world");
	
	JSlider slTwistiness = new JSlider(0, 18, 12);
	
	JScrollPane mapScroll = new JScrollPane(imgPane);
	
	ActionListener aNewWorld = new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			PATH = "worlds/";
			PATH = PATH + fName.getText();
			
			try {FileUtils.deleteDirectory(new File(PATH));} 
			catch (IOException e1) {e1.printStackTrace();}
			new File(PATH).mkdirs();
			
			worldSize = Integer.parseInt(fWorldSize.getText());
			nContinents = Integer.parseInt(fContinents.getText());
			nGen = Integer.parseInt(fGen.getText()); 
			poles = cPoles.isSelected();
			beaches = cBeach.isSelected();
			sTemp = bTemperature.getSelectedIndex();
			mountains = Integer.parseInt(fMountains.getText());
			sMountain = bMountainSize.getSelectedIndex();
			nLakes = Integer.parseInt(fLakes.getText());
			sLakes = bLakeSize.getSelectedIndex();
			twistiness = slTwistiness.getValue();
			nRivers = Integer.parseInt(fRivers.getText());
			
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			world = new World(PATH,worldSize,nContinents,nGen,sTemp,poles,beaches,mountains,sMountain,nLakes,sLakes,twistiness,nRivers);
			world.imgOut();
						
			try 
			{
				
				BufferedImage img = ImageIO.read(new File(PATH + "/map.bmp"));
				ImageIcon icon = new ImageIcon(img);
				imgPane.setIcon(icon);
				pack();
				
			} 
			catch (IOException e1){e1.printStackTrace();}
			
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
		}
	};
	
	ActionListener aUseWorld = new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			saveWorld();
			setVisible(false);
			
		}
	};
	
	WorldWindow()
	{
		
		setLayout(new MigLayout());
		worldpanel.setLayout(new MigLayout());
		countpanel.setLayout(new MigLayout());
		parameterpanel.setLayout(new MigLayout());
		
		countpanel.add(lWorldSize,"align right, push");
		countpanel.add(fWorldSize,"align left, push, wrap");
		countpanel.add(lContinents,"align right, push");
		countpanel.add(fContinents,"align left, push, wrap");
		countpanel.add(lGen,"align right, push");
		countpanel.add(fGen,"align left, push, wrap");
		countpanel.add(lMountains,"align right, push");
		countpanel.add(fMountains,"align left, push, wrap");
		countpanel.add(lLakes,"align right, push");
		countpanel.add(fLakes,"align left, push, wrap");
		countpanel.add(lRivers,"align right, push");
		countpanel.add(fRivers,"align left, push, wrap");
				
		parameterpanel.add(lTemperature, "align right, push");
		parameterpanel.add(bTemperature, "align left, push, wrap");
		parameterpanel.add(lMountainSize, "align right, push");
		parameterpanel.add(bMountainSize, "align left, push, wrap");
		parameterpanel.add(lLakeSize, "align right, push");
		parameterpanel.add(bLakeSize, "align left, push, wrap");
		parameterpanel.add(lRiverTwistiness, "align right, push");
		parameterpanel.add(slTwistiness, "align left, push, wrap");
		
		parameterpanel.add(lPoles, "align right, push");
		parameterpanel.add(cPoles, "align left, push, wrap");
		parameterpanel.add(lBeach, "align right, push");
		parameterpanel.add(cBeach, "align left, push, wrap");
				
		//Map panel
		worldpanel.add(mapScroll, "span 2, align center, wrap");
		
		mapScroll.setMaximumSize(new Dimension(600,600));
		mapScroll.getVerticalScrollBar().setUnitIncrement(20);
		mapScroll.getHorizontalScrollBar().setUnitIncrement(20);
		
		//set component defaults
		fWorldSize.setText("400");
		fContinents.setText("6");
		fGen.setText("100");
		fMountains.setText("5");
		fLakes.setText("10");
		fRivers.setText("5");
		cBeach.setSelected(true);
		cPoles.setSelected(true);
		bTemperature.setSelectedIndex(2);
		bMountainSize.setSelectedIndex(1);
		bLakeSize.setSelectedIndex(1);
		fName.setText("world");
		
		//add tabs		
		mainpane.addTab("Counts",countpanel);
		mainpane.addTab("Parameters", parameterpanel);
		
		//add panels
		add(lName,"split 2, align center");
		add(fName,"align center, wrap");
		add(mainpane,"align center, wrap, push");
		add(worldpanel,"wrap, align center");
		add(newWorld, "align center, split 2");
		add(useWorld, "align center");
		
		newWorld.addActionListener(aNewWorld);
		useWorld.addActionListener(aUseWorld);
		
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setVisible(true);
		
	}
	
	private void saveWorld()
	{
		
		world.generateJSON();
		
	}
	
	public int[] getSpawn()
	{
		
		return world.getSpawn();
		
	}
	
	public String getPath()
	{
		
		return PATH;
		
	}
		
}
