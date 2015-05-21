package frontend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import operationsClient.OperationsClient;

public class AplicationGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField inputField;
	private JTextArea outputField;
	final static int GAP = 10;

	public AplicationGUI() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		initializePanel();
	}

	private void initializePanel() {
		JPanel mainPanel = new JPanel() {
			private static final long serialVersionUID = -8416461308505432509L;

			@Override
			public Dimension getMaximumSize() {
				Dimension pref = getPreferredSize();
				return new Dimension(Integer.MAX_VALUE, pref.height);
			}
		};
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(createEntryFields());
		mainPanel.add(createButtons());
		add(mainPanel);
	}

	private Component createButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JButton button = new JButton("Run command");
		button.addActionListener(this);
		button.setActionCommand("add command");
		panel.add(button);

		button = new JButton("Clear all");
		button.addActionListener(this);
		button.setActionCommand("clear all");
		panel.add(button);

		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, AplicationGUI.GAP - 5, AplicationGUI.GAP - 5));
		return panel;
	}

	private Component createEntryFields() {
	    JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		String[] labelStrings = { "Input: ", "Output: " };
		JLabel[] labels = new JLabel[labelStrings.length];
		JComponent[] fields = new JComponent[labelStrings.length];
		int fieldNum = 0;

		// Create the text field and set it up.
		inputField = new JTextField();
		inputField.setColumns(20);
		inputField.setSize(100, 300);
		inputField.setText("ping -n 3 google.com");
		// inputField.setHorizontalAlignment(JTextField.LEFT);
		fields[fieldNum++] = inputField;

		outputField = new JTextArea(7, 30);
		// outputField.setLineWrap(false);
		fields[fieldNum++] = outputField;

		for (int i = 0; i < labelStrings.length; i++) {
			labels[i] = new JLabel(labelStrings[i], SwingConstants.TRAILING);
			labels[i].setLabelFor(fields[i]);
			panel.add(labels[i]);
			panel.add(fields[i]);
		}
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("clear all".equals(e.getActionCommand())) {
			inputField.setText("");
			outputField.setText("");
		}
		if ("add command".equals(e.getActionCommand())) {
			// test if value is not empty
			String commandText = inputField.getText();
			if (!commandText.isEmpty()) {

				OperationsClient operationsClient = new OperationsClient(commandText);
				operationsClient.start();
				try {
					// put thread to sleep for 5 seconds
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String response = operationsClient.getResponse();
				System.out.println("Client response:" + response);
				if (response != null) {
					outputField.setText(response);
				}
			}
		}
	}

	private static void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("Proiect PRC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new AplicationGUI();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
		frame.setLocation(300, 300);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AplicationGUI.createAndShowGUI();
			}
		});
	}
	
	
}
