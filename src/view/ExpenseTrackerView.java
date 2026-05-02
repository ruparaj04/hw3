package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.ExpenseTrackerModel;
import model.Transaction;

import java.awt.*;
import java.util.List; 

/**
 * Provides a visualization of a list of transactions along with
 * the ability to add/remove transactions.
 * 
 * NOTE: Represents the View in the MVC architecture pattern.
 */
public class ExpenseTrackerView extends JFrame {

  private ExpenseTrackerModel model;
  
  private JTabbedPane tabbedPanel;
  private DataPanelView dataPanelView;
  private AnalysisPanelView analysisPanelView;
  
  private JMenuItem fileOpenFileMenuItem;
  private JMenuItem fileSaveAsMenuItem;
  private JMenuItem editDeleteMenuItem;

  public JTabbedPane getTabbedPanel() {
	  return this.tabbedPanel;
  }
  
  public DataPanelView getDataPanelView() {
	  return this.dataPanelView;
  }
  
  public AnalysisPanelView getAnalysisPanelView() {
	  return this.analysisPanelView;
  }
  
  public JMenuItem getOpenFileMenuItem() {
	  return fileOpenFileMenuItem;
  }
  
  public JMenuItem getSaveAsMenuItem() {
	  return fileSaveAsMenuItem;
  }
  
  public JMenuItem getDeleteMenuItem() {
	  return editDeleteMenuItem;
  }

  public ExpenseTrackerView(ExpenseTrackerModel model) {
    setTitle("Expense Tracker"); // Set title
 
    this.model = model;
    
    // Create the top menu bar
    JMenuBar topMenuBar = new JMenuBar();
    topMenuBar.getAccessibleContext().setAccessibleName("Main Menu");

    JMenu fileMenu = new JMenu("File");
    fileMenu.getAccessibleContext().setAccessibleName("File");
    fileMenu.getAccessibleContext().setAccessibleDescription("File operations: open or save a transaction list.");
    topMenuBar.add(fileMenu);

    this.fileOpenFileMenuItem = new JMenuItem("Open File...");
    // Improvement 2: Add accessible name and description to menu items so
    // screen readers announce their purpose when navigated to.
    this.fileOpenFileMenuItem.getAccessibleContext().setAccessibleName("Open File");
    this.fileOpenFileMenuItem.getAccessibleContext().setAccessibleDescription(
        "Open a CSV file and load its transactions into the expense tracker.");
    this.fileOpenFileMenuItem.setToolTipText("Open a CSV file to load transactions");
    fileMenu.add(this.fileOpenFileMenuItem);

    this.fileSaveAsMenuItem = new JMenuItem("Save As...");
    this.fileSaveAsMenuItem.getAccessibleContext().setAccessibleName("Save As");
    this.fileSaveAsMenuItem.getAccessibleContext().setAccessibleDescription(
        "Save the current transaction list to a CSV file.");
    this.fileSaveAsMenuItem.setToolTipText("Save the transaction list to a CSV file");
    fileMenu.add(this.fileSaveAsMenuItem);

    JMenu editMenu = new JMenu("Edit");
    editMenu.getAccessibleContext().setAccessibleName("Edit");
    editMenu.getAccessibleContext().setAccessibleDescription("Edit operations: delete a selected transaction.");
    topMenuBar.add(editMenu);

    this.editDeleteMenuItem = new JMenuItem("Delete");
    this.editDeleteMenuItem.getAccessibleContext().setAccessibleName("Delete");
    this.editDeleteMenuItem.getAccessibleContext().setAccessibleDescription(
        "Delete the currently selected transaction from the list.");
    this.editDeleteMenuItem.setToolTipText("Delete the selected transaction");
    editMenu.add(editDeleteMenuItem);
    setJMenuBar(topMenuBar);
  
    // Layout components
    tabbedPanel = new JTabbedPane();
    // Improvement 2: Add accessible name and description to the tabbed pane
    // so screen readers announce which tab is active when the user switches tabs.
    tabbedPanel.getAccessibleContext().setAccessibleName("Expense Tracker Panels");
    tabbedPanel.getAccessibleContext().setAccessibleDescription(
        "Switch between the Data tab (add/remove transactions) and the Analysis tab (view charts).");
    dataPanelView = new DataPanelView();
    analysisPanelView = new AnalysisPanelView();
    tabbedPanel.add("Data", dataPanelView);
    tabbedPanel.add("Analyis", analysisPanelView);
    add(tabbedPanel);
  
    // Set frame properties
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    refresh();
    setVisible(true);
  
  }
  
  public String showFileChooser(boolean isOpenFile) {
	JFileChooser chooser = new JFileChooser();
	int result = -1;
	if (isOpenFile) {
		result = chooser.showOpenDialog(this);
	}
	else {
		result = chooser.showSaveDialog(this);
	}
	if (result == JFileChooser.APPROVE_OPTION) {
	  String path = chooser.getSelectedFile().getAbsolutePath();
	  if (!path.toLowerCase().endsWith(".csv")) {
	    path = path + ".csv";
	  }
	  return path;
	}
	return null;
  }
  
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  } 

  public void refresh() {  
    // Pass to views
    dataPanelView.refreshTable(model);
    analysisPanelView.setVisible(model);
  }

  // Other view methods
}
