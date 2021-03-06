package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import items.Item;
import items.SmeltingRecipeSingle;
import main.Smelt;
import net.miginfocom.swing.MigLayout;

import static main.Smelt.singleSmelting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Main.player;

public class SmeltingWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	SmeltingRecipeSingle currentRecipe;

	JPanel mainPane = new JPanel();
	JPanel inventoryList = new JPanel();
	JScrollPane inventoryPane = new JScrollPane(inventoryList);

	JPanel craftingPane = new JPanel();
	JLabel lIn = new JLabel();
	JLabel lHas = new JLabel();
	JButton bSmelt = new JButton("Smelt");

	JButton bExit = new JButton("Close");

	ActionListener aExit = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();

		}
	};

	ActionListener aAdd = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			int inxRp = Integer.parseInt(e.getActionCommand().split(",")[0]);
			int inxReq = Integer.parseInt(e.getActionCommand().split(",")[1]);
			
			currentRecipe = Smelt.singleSmelting.get(inxRp);
			int currentQ = player.inventory.get(inxReq).stacksize;

			lIn.setText(currentRecipe.nQuantity + " " + new Item(currentRecipe.requires).name);
			lHas.setText(Integer.toString(currentQ));
									
			bSmelt.setEnabled(true);
			craftingPane.repaint();
			craftingPane.revalidate();

		}
	};

	ActionListener aSmelt = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			int remain = player.removeItem(new Item(currentRecipe.requires),currentRecipe.nQuantity);
			player.addItem(new Item(currentRecipe.yield), currentRecipe.nYield);			
						
			if(remain < currentRecipe.nQuantity)
			{
								
				currentRecipe = null;
				lIn.setText(null);
				lHas.setText(null);
				bSmelt.setEnabled(false);
				
			}
			else
			{
				lHas.setText(Integer.toString(remain));				
			}
			
			getInventory();
			inventoryList.revalidate();
			inventoryList.repaint();

		}
	};

	public SmeltingWindow()
	{

		mainPane.setLayout(new MigLayout());
		inventoryList.setLayout(new MigLayout());
		craftingPane.setLayout(new MigLayout());

		getInventory();

		craftingPane.add(new JLabel("Requires:"));
		craftingPane.add(lIn,"wrap");
		craftingPane.add(new JLabel("Has:"));
		craftingPane.add(lHas,"wrap");
		craftingPane.add(bSmelt,"span 2, alignx center");
		
		bSmelt.addActionListener(aSmelt);

		mainPane.add(inventoryPane,"grow,push,wrap,height max(256),width min(256)");
		mainPane.add(craftingPane, "wrap,alignx center");
		mainPane.add(bExit,"alignx center");

		bExit.addActionListener(aExit);

		add(mainPane);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void getInventory()
	{

		inventoryList.removeAll();

		for(SmeltingRecipeSingle s : singleSmelting)
		{

			for(Item i : player.inventory)
			{

				if(i.id == s.requires && i.stacksize >= s.nQuantity)
				{

					JButton button = new JButton("Craft");
					String inxRp = Integer.toString(Smelt.singleSmelting.indexOf(s));
					String inxReq = Integer.toString(player.inventory.indexOf(i));
					button.setActionCommand(inxRp + "," + inxReq);
					button.addActionListener(aAdd);

					inventoryList.add(new JLabel(Integer.toString(s.nYield)),"pushx,alignx center");
					inventoryList.add(new JLabel(new Item(s.yield).name),"pushx,alignx center");
					inventoryList.add(button,"wrap, pushx,alignx center");

				}

			}

		}

	}

}
