package main.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;
import main.Purchaseables.HealInjury;
import main.Purchaseables.Item;

import java.awt.GridBagLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import java.awt.Font;

/**
 * GUI Class for Item use; extends JDialog.
*/
public class UseItemGUI extends JDialog {
	/**Current UseItem Conent Panel */
	private final JPanel contentPanel = new JPanel();
	/**Instance of the PlayerTeam Logic class */
	PlayerTeam player;
	/**String of the Players Athletes Descriptions */
	String[] descriptionStrings;
	/**List of the Players Athlete to use Item on */
	JList athleteList;

	/**
	 * Create the JDialog which asks the player to use the item
	 * @param playerTeam The current PlayerTeam logic class instance
	 * @param itemSelected The current int of the Item which was selected, and will be used 
	 */
	public UseItemGUI(PlayerTeam playerTeam, Item itemSelected) {
		player = playerTeam;
		descriptionStrings = new String[5];
		for (int i = 0; i < player.getAllTeam().length; i++){
			descriptionStrings[i] = player.getTeamMember(i).getDescription();
		}
		setBounds(100, 100, 750, 200);
		setMinimumSize(new Dimension(750,200));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblConfirm = new JLabel("Which athlete would you like to use Item on?");
			GridBagConstraints gbc_lblConfirm = new GridBagConstraints();
			gbc_lblConfirm.insets = new Insets(0, 0, 5, 0);
			gbc_lblConfirm.gridx = 0;
			gbc_lblConfirm.gridy = 0;
			contentPanel.add(lblConfirm, gbc_lblConfirm);
		}
		{
			athleteList = new JList();
			athleteList.setFont(new Font("Dialog", Font.BOLD, 12));
			athleteList.setVisibleRowCount(5);
			athleteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			athleteList.setModel(new AbstractListModel() {
				String[] values = descriptionStrings;
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 0;
			gbc_list.gridy = 1;
			contentPanel.add(athleteList, gbc_list);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Use Item");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(athleteList.getSelectedIndex() >= 0){
							Athlete selectedAthlete = player.getTeamMember(athleteList.getSelectedIndex());
							String useMessage = itemSelected.use(selectedAthlete, playerTeam);
							JOptionPane.showMessageDialog(null, useMessage, "Use Item", JOptionPane.PLAIN_MESSAGE);
							dispose();
						}
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	}

}
