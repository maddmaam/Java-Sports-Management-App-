package main.GUI;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;
import main.Purchaseables.InteractiveItem;

import javax.swing.JOptionPane;

/**
 * Provides a simple static method which opens a dialog allowing the user to choose which of an Athlete's stats to modify; for use with InteractiveItems.
*/

public class StatChoiceDialog {

	/**
	 * Constructor. Does nothing.
	*/
	public StatChoiceDialog(InteractiveItem i, PlayerTeam p, Athlete a) {

	}

	/**
	 * Static method; opens a dialog prompting the player to choose ATK, DEF, or HP. Returns the selection as a String.
	 * @return 		String containing "Atk", "Def", "HP", or "Cancel" if the player closed the window without choosing.
	*/
	public static String chooseStat() {
	Object[] options = {"ATK", "DEF", "HP"};
	String message = "Choose a stat to modify:";
	int choice = JOptionPane.showOptionDialog(null, message, "Use Item", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	if (choice == JOptionPane.YES_OPTION) {
		return "Atk";
	} else if (choice == JOptionPane.NO_OPTION) {
		return "Def";
	} else if (choice == JOptionPane.CANCEL_OPTION) {
		return "HP";
	} else {
		return "Cancel";
	}
	}

}