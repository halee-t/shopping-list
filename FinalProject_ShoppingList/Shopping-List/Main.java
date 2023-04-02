/*
  CSI 2300 Final Project
  Partners: Robert Elle and Halee Tisler
*/

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//imports for GUI
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Main extends JFrame implements ActionListener{

  //declare objects and arraylist
  Budget budget = new Budget();
  GroceryList grocery = new GroceryList();
  ArrayList<String> groceryList = new ArrayList<String>();

  //GUI declarations
  private JLabel inputLabel;
  private JLabel listLabel;
  private JLabel budgetLabel;
  private JLabel costLabel;
  private JTextArea outputArea;
  private JTextField inputField;
  private JTextField budgetField;
  private JTextField costField;
  private JButton addButton;
  private JButton removeButton;
  private JButton viewButton;
  private JButton clearButton;

  Main(){

  JTextField outputField = null;
  GridBagConstraints layoutConst = null;

  setTitle("Shopping List");
  inputLabel = new JLabel("Input: ");
  listLabel = new JLabel("Shopping List: ");
  budgetLabel = new JLabel("Budget: $");
  costLabel = new JLabel("Cost: $");
  outputArea = new JTextArea(10, 15);
  inputField = new JTextField(5);
  budgetField = new JTextField(5);
  costField = new JTextField(5);
  addButton = new JButton("Add Item");
  removeButton = new JButton("Remove Item");
  viewButton = new JButton ("View List");
  clearButton = new JButton("Clear");

  //input field should be able to edit
  inputField.setEditable(true);

  //output area should not be able to edit
  outputArea.setEditable(false);

  //Action listeners for the buttons
  addButton.addActionListener(this);
  removeButton.addActionListener(this);
  viewButton.addActionListener(this);
  clearButton.addActionListener(this);

  //cost field should not be able to edit, initialize an output
  costField.setEditable(false);
  costField.setText(Double.toString(budget.getCurrentCost()));

  //budget field should be able to edit
  budgetField.setEditable(true);

  //gridbag layout
  setLayout(new GridBagLayout());

  //budget label layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.anchor = GridBagConstraints.LINE_END;
  layoutConst.gridx = 0;
  layoutConst.gridy = 3;
  add(budgetLabel, layoutConst);

  //budget field layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.fill = GridBagConstraints.HORIZONTAL;
  layoutConst.gridx = 1;
  layoutConst.gridy = 3;
  add(budgetField, layoutConst);

  //cost label layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.anchor = GridBagConstraints.LINE_END;
  layoutConst.gridx = 2;
  layoutConst.gridy = 3;
  add(costLabel, layoutConst);

  //cost field layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.fill = GridBagConstraints.HORIZONTAL;
  layoutConst.gridx = 3;
  layoutConst.gridy = 3;
  add(costField, layoutConst);

  //input label layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.anchor = GridBagConstraints.LINE_END;
  layoutConst.gridx = 4;
  layoutConst.gridy = 3;
  add(inputLabel, layoutConst);

  //input field layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.fill = GridBagConstraints.HORIZONTAL;
  layoutConst.gridx = 5;
  layoutConst.gridy = 3;
  add(inputField, layoutConst);

  //list label layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.anchor = GridBagConstraints.LINE_END;
  layoutConst.gridx = 2;
  layoutConst.gridy = 0;
  add(listLabel, layoutConst);

  //output area layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5,5,5,5);
  layoutConst.fill = GridBagConstraints.HORIZONTAL;
  layoutConst.gridx = 2;
  layoutConst.gridy = 1;
  layoutConst.gridwidth = 2;
  add(outputArea, layoutConst);

  //add button layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5, 5, 5, 5);
  layoutConst.fill = GridBagConstraints.BOTH;
  layoutConst.gridx = 1;
  layoutConst.gridy = 2;
  add(addButton, layoutConst);

  //remove button layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5, 5, 5, 5);
  layoutConst.fill = GridBagConstraints.BOTH;
  layoutConst.gridx = 2;
  layoutConst.gridy = 2;
  add(removeButton, layoutConst);

  //view button layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5, 5, 5, 5);
  layoutConst.fill = GridBagConstraints.BOTH;
  layoutConst.gridx = 3;
  layoutConst.gridy = 2;
  add(viewButton, layoutConst);

  //clear button layout
  layoutConst = new GridBagConstraints();
  layoutConst.insets = new Insets(5, 5, 5, 5);
  layoutConst.fill = GridBagConstraints.BOTH;
  layoutConst.gridx = 4;
  layoutConst.gridy = 2;
  add(clearButton, layoutConst);

  }

  
  @Override
   public void actionPerformed(ActionEvent event){

    //detects which button was pressed
    JButton sourceEvent = (JButton) event.getSource();

    String inputUserBudget;

    Scanner scan = new Scanner(System.in);
    FileOutputStream fileStream = null;
    PrintWriter outFS = null;

    //need a try catch for IOException
    try {
      fileStream = new FileOutputStream("List.txt", true);
      outFS = new PrintWriter(fileStream);

      //when the add button is pressed, call the addItem method from grocery
      if (sourceEvent == addButton) {
        inputUserBudget = budgetField.getText();
        double userBudget = Double.parseDouble(inputUserBudget);
        grocery.addItem(groceryList, userBudget, inputField, costField);   
      }

      //when the remove button is pressed, call the removeItem method from grocery
      else if (sourceEvent == removeButton) {
        grocery.removeItem(groceryList, outFS, inputField, costField);
      }

      //when the view button is pressed, call the printList method from grocery
      else if (sourceEvent == viewButton) {
        grocery.printList(outputArea);
      }

      //when the clear button is pressed, call the clearList method from grocery
      else if (sourceEvent == clearButton) {
        grocery.clearList(costField);
      }
    }

    catch (IOException e) {}
     
   }



  public static void main(String[] args) throws IOException {
    Main myFrame = new Main();

    //stop the program when the window is closed
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myFrame.pack();
    myFrame.setVisible(true);
    
    //Declaration for input/output files
    Scanner scan = new Scanner(System.in);
    FileOutputStream fileStream = null;
    PrintWriter outFS = null;

    fileStream = new FileOutputStream("List.txt", true);
    outFS = new PrintWriter(fileStream);

    //Close the file
    outFS.close();
  }

}
