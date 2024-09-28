package main.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.*;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * GUI Class for the startup screen; extends JFrame.
*/
public class StartAppGUI extends JFrame{
	/**The JFrame of the StartAppGUI */
	public JFrame startFrame;

	/**
	 * Create the application. Calls the Initialize method
	 */
	public StartAppGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		startFrame = new JFrame();
		startFrame.setTitle("SENG Soccer Management Sim 2023");
		startFrame.setBounds(100, 100, 450, 300);
		startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		startFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Welcome to SENG Soccer Management Sim 2023!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startFrame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel lblWouldYouLike = new JLabel("Click below to start!!");
		lblWouldYouLike.setHorizontalAlignment(SwingConstants.CENTER);
		startFrame.getContentPane().add(lblWouldYouLike, BorderLayout.CENTER);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetupGUI setupUI = new SetupGUI();
				setupUI.setVisible(true);
				startFrame.setVisible(false);
				startFrame.dispose();
			}
		});
		startFrame.getContentPane().add(btnPlay, BorderLayout.SOUTH);
	}

}
