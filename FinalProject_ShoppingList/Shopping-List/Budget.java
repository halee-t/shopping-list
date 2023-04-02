import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Budget {

    JFrame myFrame = new JFrame();
  
    private double price;
    private double cost;

    Scanner scan = new Scanner(System.in);
    FileInputStream fileByteStream = null;
    Scanner inFS = null;
  
    public double getPrice(String userItem) throws IOException{
      //Open the StoreData.txt to read
      fileByteStream = new FileInputStream("StoreData.txt");
      inFS = new Scanner(fileByteStream);

      //Find the grocery item in our StoreData file to get the price
      while (inFS.hasNext()) {
        String listItem = inFS.next();
        double listPrice = inFS.nextDouble();

        if (userItem.equalsIgnoreCase(listItem)) {
          price = listPrice;
        }
  
      }

      //Close the file
      fileByteStream.close();

      return price;
    }

    public void setCurrentCostAdd(double price, double budget) {
        //increase the cost by price of item
        cost += price;

        //check to see if we are within budget
        if (cost > budget) {
          JOptionPane.showMessageDialog(myFrame, "You are over budget!");
        }

    }

    public void setCurrentCostRemove(double price) {
      //decrease the cost by price of item
      cost = cost - price;
      
    }

    public double getCurrentCost() {
      return cost;
    }
}