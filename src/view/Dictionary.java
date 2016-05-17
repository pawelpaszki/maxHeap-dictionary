package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.MaxHeap;
import models.Entry;

/**
 * 
 * @author Pawel Paszki
 * @version 06/04/2016
 * 
 * This class is a controller of the application. It displays all the 
 * required Swing components and takes appropriate action, when an event occurs
 */
public class Dictionary implements ActionListener {

	private MaxHeap<?> maxHeap = new MaxHeap<Object>();
	private JFrame mainWindow;
	private final Color backgroundColor = new Color(248, 236, 194);
	private JPanel controlPanel;
	private JPanel displayResultsPanel;
	private JTextField addSpanishWordTextField;
	private JTextField addEnglishWordTextField;
	private JTextField wordToFindTextField;
	private JTextField pathOfFileToBeSavedTextField;
	private JLabel blankLabel = new JLabel();
	private JTextArea resultsTextArea;
	// root of the app directory
	private final String userDirLocation = System.getProperty("user.dir");
	private final File userDir = new File(userDirLocation);

	public static <T> void main(String... args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dictionary controller = new Dictionary();
					controller.initialise();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * this method initialises the Frame and all its components
	 */
	private void initialise() {
		mainWindow = new JFrame("Spanish - English dictionary");

		mainWindow.setSize(500, 470);
		mainWindow.setLocationRelativeTo(null);// centres the window
		mainWindow.getContentPane().setBackground(backgroundColor);
		mainWindow.setVisible(true); // makes the window visible
		// closes the window, when stop button is pressed
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null); // no layout manager
		mainWindow.setResizable(false);

		// panel, which contains all the buttons and text fields
		controlPanel = createPanel(0, 0, 500, 350);
		controlPanel.setLayout(new GridLayout(13, 1));

		// this panel contains the text area, in which results of 
		// a search / addition / change of the dictionary will be displayed
		displayResultsPanel = createPanel(0, 355, 500, 110);
		displayResultsPanel.setLayout(new GridLayout(1, 1));
		resultsTextArea = new JTextArea();
		resultsTextArea.setBackground(backgroundColor);
		resultsTextArea.setFont(new Font("Arial", Font.ITALIC, 16));
		displayResultsPanel.add(resultsTextArea);

		addSpanishWordTextField = addJTextField(controlPanel);
		addSpanishWordTextField.setToolTipText("Enter spanish word here");
		addEnglishWordTextField = addJTextField(controlPanel);
		addEnglishWordTextField.setToolTipText("Enter english word here");
		addButton(controlPanel, "add words");
		controlPanel.add(blankLabel);
		wordToFindTextField = addJTextField(controlPanel);
		wordToFindTextField.setToolTipText("Enter spanish word to be found");
		addButton(controlPanel, "search");
		blankLabel = new JLabel();
		controlPanel.add(blankLabel);
		pathOfFileToBeSavedTextField = addJTextField(controlPanel);
		pathOfFileToBeSavedTextField.setToolTipText("Enter the name of the file to be saved");
		addButton(controlPanel, "save dictionary");
		blankLabel = new JLabel();
		controlPanel.add(blankLabel);
		addButton(controlPanel, "load dictionary");
		blankLabel = new JLabel();
		controlPanel.add(blankLabel);
		addButton(controlPanel, "clear the dictionary");
		mainWindow.add(controlPanel);
		mainWindow.add(displayResultsPanel);
	}

	/**
	 * Add a button to the button panel.
	 */
	private void addButton(Container panel, String buttonText) {
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		panel.add(button);
	}

	/**
	 * Add a text field to the specified panel.
	 */
	private JTextField addJTextField(Container panel) {
		JTextField textField = new JTextField();
		textField.addActionListener(this);
		textField.setFont(new Font("Arial", Font.BOLD, 16));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textField);
		return textField;
	}

	/**
	 * 
	 * this method creates a panel with following properties:
	 * @param xPos - x position
	 * @param yPos - y position
	 * @param width - width of the panel
	 * @param height - height of the panel
	 * @return
	 */
	private JPanel createPanel(int xPos, int yPos, int width, int height) {
		JPanel panel = new JPanel();
		panel.setBounds(xPos, yPos, width, height);
		panel.setBackground(backgroundColor);
		return panel;
	}

	/**
	 * this method specifies, what to do in an event occurs, ie button is pressed
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		switch (command) {
		case "add words":
			if (addSpanishWordTextField.getText() != null && addEnglishWordTextField.getText() != null)
				maxHeap.add(new Entry<Object>(addSpanishWordTextField.getText(), addEnglishWordTextField.getText()));
			resultsTextArea.setText(
					"spanish word \"" + addSpanishWordTextField.getText() + "\" has been added to the dictionary");
			clearTextFields();
			maxHeap.printEntries();
			break;
		case "load dictionary":
			JFileChooser chooser = new JFileChooser(userDir);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("dat files", "dat");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(mainWindow);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getAbsolutePath();
				maxHeap.loadDictionary(path);
				maxHeap.printEntries();
			}
			clearTextFields();
			break;
		case "save dictionary":
			if (pathOfFileToBeSavedTextField.getText().length() > 0 && maxHeap.getSize() > 0) {
				String filename = pathOfFileToBeSavedTextField.getText();
				maxHeap.saveDictionary(filename);
				resultsTextArea.setText("Dictionary has been saved");
			}
			clearTextFields();
			break;

		case "search":
			String spanishWord = wordToFindTextField.getText();
			String englishWord = maxHeap.getEntry(spanishWord);
			if (englishWord != null) {
				resultsTextArea.setText("Spanish word: " + spanishWord + "\nEnglish translation: " + englishWord
						+ "\nnumber of steps during search: " + maxHeap.getNumberOfStepsInSearch());
				maxHeap.setNumberOfStepsInSearch(0);
			} else {
				resultsTextArea.setText("There is no word: \"" + spanishWord + "\" in the dictionary");
			}
			clearTextFields();
			break;
		case "clear the dictionary":
			maxHeap.clear();
			resultsTextArea.setText("All entries from the dictionary have been removed");
			clearTextFields();
		}
	}

	/**
	 * this method clears all the textfields
	 */
	private void clearTextFields() {
		addSpanishWordTextField.setText("");
		addEnglishWordTextField.setText("");
		wordToFindTextField.setText("");
		pathOfFileToBeSavedTextField.setText("");
	}
}
