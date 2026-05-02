package view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.ExpenseTrackerModel;
import model.Transaction;

public class DataPanelView extends JPanel {
	private JTable transactionsTable;
	private JButton addTransactionBtn;
	private JTextField amountField;
	private JTextField categoryField;
	private DefaultTableModel transactionsModel;

	public DataPanelView() {
		super();
		
	    this.transactionsModel = new DefaultTableModel();
	    this.transactionsModel.addColumn("Serial");
	    this.transactionsModel.addColumn("Amount");
	    this.transactionsModel.addColumn("Category");
	    this.transactionsModel.addColumn("Date");
	    
	    addTransactionBtn = new JButton("Add Transaction");

	    // Create UI components
	    JLabel amountLabel = new JLabel("Amount:");
	    amountField = new JTextField(10);
	    // Improvement 1: Associate the label with its field so screen readers
	    // announce the label text when the field receives focus.
	    amountLabel.setLabelFor(amountField);
	    amountField.getAccessibleContext().setAccessibleName("Amount");
	    amountField.getAccessibleContext().setAccessibleDescription(
	        "Enter the transaction amount. Must be greater than 0 and at most 1000.");
	    amountField.setToolTipText("Enter the transaction amount (0 < amount <= 1000)");
	    
	    JLabel categoryLabel = new JLabel("Category:");
	    categoryField = new JTextField(10);
	    // Improvement 1: Associate the label with its field so screen readers
	    // announce the label text when the field receives focus.
	    categoryLabel.setLabelFor(categoryField);
	    categoryField.getAccessibleContext().setAccessibleName("Category");
	    categoryField.getAccessibleContext().setAccessibleDescription(
	        "Enter the transaction category. Valid values: food, travel, bills, entertainment, other.");
	    categoryField.setToolTipText("Valid categories: food, travel, bills, entertainment, other");

	    // Improvement 2: Add accessible name, description, and tooltip to the button
	    // so screen readers announce its purpose.
	    addTransactionBtn.getAccessibleContext().setAccessibleName("Add Transaction");
	    addTransactionBtn.getAccessibleContext().setAccessibleDescription(
	        "Click to add a new transaction with the entered amount and category.");
	    addTransactionBtn.setToolTipText("Add a new transaction");

	    transactionsTable = new JTable(transactionsModel);
	    transactionsTable.setDefaultEditor(Object.class, null);
	    transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    // Improvement 2: Add accessible name and description to the table
	    // so screen readers can announce the table's purpose and navigate its cells.
	    transactionsTable.getAccessibleContext().setAccessibleName("Transactions");
	    transactionsTable.getAccessibleContext().setAccessibleDescription(
	        "Table of all transactions. Each row shows a serial number, amount, category, and date. " +
	        "Select a row, then use Edit > Delete to remove a transaction.");
	    
	    setLayout(new BorderLayout());
	    JPanel addTransactionPanel = new JPanel();
	    JPanel inputPanel = new JPanel();
	    inputPanel.add(amountLabel);
	    inputPanel.add(amountField);
	    inputPanel.add(categoryLabel); 
	    inputPanel.add(categoryField);
	    inputPanel.add(addTransactionBtn);
	    addTransactionPanel.add(inputPanel);
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(addTransactionBtn);
	    addTransactionPanel.add(buttonPanel);
	  
	    // Add panels to frame
	    add(addTransactionPanel, BorderLayout.NORTH);
	    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
	}
 	
	public JTable getTransactionsTable() {
		return transactionsTable;
	}

	public double getAmount() {
		if(amountField.getText().isEmpty()) {
			return 0;
		}else {
			double amount = Double.parseDouble(amountField.getText());
			return amount;
		}
	}
	
	public void setAmount(String amount) {
		// For testing purposes
		amountField.setText(amount);
	}

	public String getCategory() {
		return categoryField.getText();
	}
	
	public void setCategory(String category) {
		// For testing purposes
		categoryField.setText(category);
	}

	public JButton getAddTransactionBtn() {
		return addTransactionBtn;
	}
	
	public DefaultTableModel getTableModel() {
		return transactionsModel;
	}
	
	public int getSelectedTransactionID() {
		return this.transactionsTable.getSelectedRow();
	}
	
	public int getTransactionsTableRowCount() {
		// For testing purposes
		return this.transactionsTable.getRowCount();
	}
	  
	public Object getTransactionsTableValueAt(int row, int col) {
		// For testing purposes
		return this.transactionsModel.getValueAt(row, col);
	}
	
	public void refreshTable(ExpenseTrackerModel model) {
		List<Transaction> transactions = model.getTransactions();
		
		// model.setRowCount(0);
		transactionsModel.setRowCount(0);
		int rowNum = transactionsModel.getRowCount();

		// Add rows from transactions list
		for(Transaction t : transactions) {
			transactionsModel.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});

		}
		Object[] totalRow = {"Total", null, null, model.computeTransactionsTotalCost()};
		transactionsModel.addRow(totalRow);

		// Fire table update
		transactionsTable.updateUI();
	}  
}
