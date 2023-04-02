import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GroceryList{

  JFrame frame = new JFrame();
  
  //Declare
  Budget budget = new Budget();
  Scanner scan = new Scanner(System.in);
  FileInputStream fileByteStream = null;
  Scanner inFS = null;
  FileOutputStream fileStream = null;
  PrintWriter outFS = null;
  
  public void addItem(ArrayList<String> list, double BUDGET, JTextField inputField, JTextField costField) throws IOException{

    //open list.txt to write on, setting it to true allows it to save between runs
    fileStream = new FileOutputStream("List.txt", true);
    outFS = new PrintWriter(fileStream);

    //open StoreData.txt to read
    fileByteStream = new FileInputStream("StoreData.txt");
    inFS = new Scanner(fileByteStream);

    //Declare variables
    String userAddItem;
    String shoppingListItem;
    Double costOfItem;
    int matches = 0;                //this is used to see if the inputted item matches an item in StoreData.txt  

    //get the item from input
    userAddItem = inputField.getText();

    //read through StoreData.txt to find the matching item and price
    while (inFS.hasNext()){
      shoppingListItem = inFS.next();
      costOfItem = inFS.nextDouble();

      if (userAddItem.equalsIgnoreCase(shoppingListItem)) {
        ++matches;                  //a match has been found, the item is in inventory
        list.add(userAddItem);      //add item to the arrayList

        Double priceOfItem = budget.getPrice(userAddItem);        //call the getPrice method from budget
        budget.setCurrentCostAdd(priceOfItem,BUDGET);             //call the setCurrentCostAdd method from budget

        costField.setText(Double.toString(budget.getCurrentCost()));        //output the current cost

        outFS.printf("%s\n", userAddItem);        //write the item to list.txt
    
        outFS.close(); 
      }
        
      else { }

    }

    //if no matches were found, the item is not in inventory, send an error message
    if (matches == 0) {
        JOptionPane.showMessageDialog(frame, "This item is not in our inventory :(");
        return;
    }

  }

  public void removeItem(ArrayList<String> list, PrintWriter OUTFS, JTextField inputField, JTextField costField) throws IOException{

    //open list.txt to read
    fileByteStream = new FileInputStream("List.txt");
    inFS = new Scanner(fileByteStream);

    String userRemoveItem;

    //get the item they would like to remove from input
    userRemoveItem = inputField.getText();

    Double priceOfItem = budget.getPrice(userRemoveItem);    //call getPrice method from budget
    budget.setCurrentCostRemove(priceOfItem);                //call setCurrentCostRemove method from budget

    costField.setText(Double.toString(budget.getCurrentCost()));    //output the current cost


    String shoppingListItem;
    final int size = list.size();

    for (int j = 0; j < size; j++) {
      
      shoppingListItem = inFS.nextLine();

      if (userRemoveItem.equalsIgnoreCase(shoppingListItem)) {
        
        //remove item from arraylist
        list.remove(userRemoveItem);
      }

    }

    //we need to clear the list first, then reprint it without the item that was removed
    clearList(costField);
    
    for (int i = 0; i < list.size(); i++) {
      String word = list.get(i);
      OUTFS.printf("%s\n", word);
    }
      
    fileByteStream.close();
    OUTFS.close();
  }

  public void printList(JTextArea outputArea) throws IOException{

    //clear the outputArea to avoid duplicates
    outputArea.setText("");

    //open list.txt to read
    fileByteStream = new FileInputStream("List.txt");
    inFS = new Scanner(fileByteStream);

    //read the file and print each line to outputArea, append allows us to add it to the bottom
    while (inFS.hasNext()) {
      String line = inFS.nextLine();
      
      outputArea.append(line + "\n");
    }

    fileByteStream.close();
  }


  public void clearList(JTextField costField) throws IOException{
    //open list.txt to write, setting it to false clears the list
    fileStream = new FileOutputStream("List.txt", false);
    outFS = new PrintWriter(fileStream);

    //reset the cost
    costField.setText("0.00");

    outFS.printf("");
  }

}