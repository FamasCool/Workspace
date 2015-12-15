package com.qty.webstartcalculator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WebStartCalculator {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				CalculatorFrame frame = new CalculatorFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

/**
 * A frame with a calculator panel and a menu to load and save the calculator history.
 */
class CalculatorFrame extends JFrame {
	
	public CalculatorFrame() {
		setTitle();
		panel = new CalculatorPanel();
		add(panel);
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem openItem = fileMenu.add("Open");
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				open();
			}
		});
		
		JMenuItem saveItem = fileMenu.add("Save");
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				save();
			}
		});
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		pack();
	}
	
	/**
	 * Gets the title from the persistent store or asks the user for the title if there is no prior entry.
	 */
	public void setTitle() {
		try {
			String title = null;
			
			BasicService basic = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
			URL codeBase = basic.getCodeBase();
			
			PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");
			URL key = new URL(codeBase, "title");
			
			try {
				FileContents contents = service.get(key);
				InputStream in = contents.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				title = reader.readLine();
			} catch (FileNotFoundException e) {
				title = JOptionPane.showInputDialog("Please supply a frame title: ");
				if (title == null) {
					return;
				}
				
				service.create(key, 100);
				FileContents contents = service.get(key);
				OutputStream out = contents.getOutputStream(true);
				PrintStream printOut = new PrintStream(out);
				printOut.print(title);
			}
			setTitle(title);
		} catch (UnavailableServiceException e) {
			JOptionPane.showMessageDialog(this, e);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, e);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}
	
	/**
	 * Opens a history file and updates the display.
	 */
	public void open() {
		try {
			FileOpenService service = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
			FileContents contents = service.openFileDialog(".", new String[] {"txt"});
			
			JOptionPane.showMessageDialog(this, contents.getName());
			if (contents != null) {
				InputStream in = contents.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = reader.readLine()) != null) {
					panel.display.setText(panel.display.getText() + line);
					panel.display.setText(panel.display.getText() + "\n");
				}
			}
		} catch (UnavailableServiceException e) {
			JOptionPane.showMessageDialog(this, e);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}
	
	/**
	 * Saves the calculator history to a file.
	 */
	public void save() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream printOut = new PrintStream(out);
			printOut.print(getTitle());
			InputStream data = new ByteArrayInputStream(out.toByteArray());
			FileSaveService service = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
			service.saveFileDialog(".", new String[] {"txt" }, data, "calc.txt");
		} catch (UnavailableServiceException e) {
			JOptionPane.showMessageDialog(this, e);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}
	
	private CalculatorPanel panel;
}

/**
 * A panel with calculator buttons and a result display.
 */
class CalculatorPanel extends JPanel {
	
	public CalculatorPanel() {
		setLayout(new BorderLayout());
		
		result = 0;
		lastCommand = "=";
		start = true;
		
		// add the display
		
		display = new JButton("0");
		display.setEnabled(false);
		add(display, BorderLayout.NORTH);
		
		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();
		
		// add the buttons in a 4 x 4 grid
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4));
		
		addButton("7", insert);
		addButton("8", insert);
		addButton("9", insert);
		addButton("/", command);
		
		addButton("4", insert);
		addButton("5", insert);
		addButton("6", insert);
		addButton("*", command);
		
		addButton("1", insert);
		addButton("2", insert);
		addButton("3", insert);
		addButton("-", command);
		
		addButton("0", insert);
		addButton(".", insert);
		addButton("=", command);
		addButton("+", command);
		
		add(panel, BorderLayout.CENTER);
	}
	
	/**
	 * Adds a button to the center panel.
	 * @param label the button label
	 * @param listener the button listener
	 */
	private void addButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.addActionListener(listener);
		panel.add(button);
	}
	
	/**
	 * This action insertActon implements ActionListener {
	 */
	private class InsertAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String input = event.getActionCommand();
			if (start) {
				display.setText("");
				start = false;
			}
			display.setText(display.getText() + input);
		}
	}
	
	/**
	 * This action executes the command that the button action string denotes.
	 */
	private class CommandAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			
			if (start) {
				if (command.equals("-")) {
					display.setText(command);
					start = false;
				} else {
					lastCommand = command;
				}
			} else {
				calculate(Double.parseDouble(display.getText()));
				lastCommand = command;
				start = true;
			}
		}
	}
	
	/**
	 * Carries out the pending calculation.
	 * @param x the value to be accumulated with the prior result.
	 */
	public void calculate(double x) {
		if (lastCommand.equals("+")) {
			result += x;
		} else if (lastCommand.equals("-")) {
			result -= x;
		} else if (lastCommand.equals("*")) {
			result *= x;
		} else if (lastCommand.equals("/")) {
			result /= x;
		} else if (lastCommand.equals("=")) {
			result = x;
		}
		display.setText("" + result);
	}
	
	protected JButton display;
	private JPanel panel;
	private double result;
	private String lastCommand;
	private boolean start;
}