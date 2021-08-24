/* Name: Leslie Recendiz
Course: CNT 4714 – Spring 2021
Assignment title: Project 1 – Event-driven Enterprise Simulation 
Date: Sunday January 31, 2021
*/

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.*;
import java.util.*;

public class Store extends JFrame {

    private ArrayList<Items> inventory;
    private Order order = new Order();

    // create Text Fields for GUI
    private JTextField jtfNumItems = new JTextField();
    private JTextField jtfItemsID = new JTextField();
    private JTextField jtfQuantity = new JTextField();
    private JTextField jtfItemInfo = new JTextField();
    private JTextField jtfTotalItems = new JTextField();

    // create Buttons for GUI
    private JButton jbtProcessItem = new JButton("Process Item #1");// need to update item #

    private JButton jbtConfirmItem = new JButton("Confirm Item #1");// need to update item #
    private JButton jbtViewOrder = new JButton("View Order");
    private JButton jbtFinishOrder = new JButton("Finish Order");
    private JButton jbtNewOrder = new JButton("New Order");
    private JButton jbtExit = new JButton("Exit");

    // create Jlabels for GUI
    JLabel jlbSubtotal = new JLabel("Order Subtotal for 0 item(s):");
    JLabel jlbItemsID = new JLabel("Enter Items ID for Item #1:");
    JLabel jlbQuantity = new JLabel("Enter Quantitiy for Item #1:");
    JLabel jlbItemInfo = new JLabel("Item #1 Info:");

   
    public Store() throws FileNotFoundException {
        this.getItemsFromFile();
        
        JPanel p1 = new JPanel(new GridLayout(5, 2));
        //jframe color background
        p1.setBackground(Color.PINK);
        p1.setForeground(Color.GRAY);
        p1.add(new JLabel("Enter number of items in this order:"));
        p1.add(jtfNumItems);
        p1.add(jlbItemsID);
        p1.add(jtfItemsID);
        p1.add(jlbQuantity);
        p1.add(jtfQuantity);
        p1.add(jlbItemInfo);
        p1.add(jtfItemInfo);
        p1.add(jlbSubtotal);
        p1.add(jtfTotalItems);


        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2.add(jbtProcessItem);
        p2.add(jbtConfirmItem);
        p2.add(jbtViewOrder);
        p2.add(jbtFinishOrder);
        p2.add(jbtNewOrder);
        p2.add(jbtExit);


        // deactivate buttons
        this.jbtConfirmItem.setEnabled(false);
        this.jbtViewOrder.setEnabled(false);
        this.jbtFinishOrder.setEnabled(false);

        // deactivate textfields
        this.jtfTotalItems.setEnabled(false);
        this.jtfItemInfo.setEnabled(false);
        // add the panels to the frame
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.SOUTH);

        // actionlisteners for all buttons
        jbtProcessItem.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int numItemsInOrder = Integer.parseInt(jtfNumItems.getText());
                String ItemsID = jtfItemsID.getText();
                int quantityOfItem = Integer.parseInt(jtfQuantity.getText());

                // set maxNumofItems for new order based on input
                if (order.getMaxNumItems() == -1 && numItemsInOrder > 0) {
                    order.setMaxNumItems(numItemsInOrder);
                    jtfNumItems.setEnabled(false);
                }
                // search for Items
                int ItemsIndex = linearSearch(ItemsID);
                // found Items
                if (ItemsIndex != -1) {
                	Items foundItems = inventory.get(ItemsIndex);
                    
                	//check if it's available or not
                	if(foundItems.isActive().equals(" false")) {
                		//if not print alert
                		JOptionPane.showMessageDialog(null, "Sorry...that item is out of stock, please try another item");
                	}else {
                	// if available, give this item info to order to print
                    order.setItemInfo(foundItems.getItemsID() + "", foundItems.getName(), foundItems.getPrice() + "",
                            quantityOfItem + "", order.getDiscountPercentage(quantityOfItem) + "",
                            order.getTotalDiscount(quantityOfItem, foundItems.getPrice()) + "");
                    String ItemsInfo = foundItems.getItemsID() + foundItems.getName() + " $" + foundItems.getPrice()
                            + " " + quantityOfItem + " " + order.getDiscountPercentage(quantityOfItem) + "% "
                            + order.getTotalDiscount(quantityOfItem, foundItems.getPrice()); // need to get discound
                    jtfItemInfo.setText(ItemsInfo);
                    jbtConfirmItem.setEnabled(true);
                    jbtProcessItem.setEnabled(false);
                    order.setOrderSubtotal(quantityOfItem, foundItems.getPrice());
                    jtfItemInfo.setEnabled(false);
                    jtfTotalItems.setEnabled(false);
                	}
                }
                // Items not found display alert
                else {
                    JOptionPane.showMessageDialog(null, "Items ID " + ItemsID + " not in file.");
                }
            }

        });

        // action on each button
        jbtConfirmItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numOfItemsInOrder = Integer.parseInt(jtfNumItems.getText());
                int ItemsID = Integer.parseInt(jtfItemsID.getText());
                int quantityOfItem = Integer.parseInt(jtfQuantity.getText());

                if (numOfItemsInOrder > order.getMaxNumItems())
                    System.out.println("went over qantity");

                // increment currentNumofItems
                order.setCurrentNumItems(quantityOfItem);
                // update subtotal items
                order.setTotalItems(order.getTotalItems() + 1);

                JOptionPane.showMessageDialog(null, "Item #" + order.getTotalItems() + " accepted");

                // prepare transaction.txt line
                order.prepareTransaction();

                // add item to viewOrder
                order.addToViewOrder(jtfItemInfo.getText());

                // enable buttons
                jbtProcessItem.setEnabled(true);
                jbtViewOrder.setEnabled(true);
                jbtFinishOrder.setEnabled(true);
                jbtConfirmItem.setEnabled(false);
                jtfNumItems.setEnabled(false);

                // update button text
                jbtProcessItem.setText("Process Item #" + (order.getTotalItems() + 1));
                jbtConfirmItem.setText("Confirm Item #" + (order.getTotalItems() + 1));

                // update textFields
                jtfItemsID.setText("");
                jtfQuantity.setText("");
                jtfTotalItems.setText("$" + new DecimalFormat("#0.00").format(order.getOrderSubtotal()));

                // update labels
                jlbSubtotal.setText("Order subtotal for " + order.getCurrentNumItems() + " item(s)");
                jlbItemsID.setText("Enter Items ID for Item #" + (order.getTotalItems() + 1) + ":");
                jlbQuantity.setText("Enter quantity for Item #" + (order.getTotalItems() + 1) + ":");
               
                if (order.getCurrentNumItems() < order.getMaxNumItems())
                    jlbItemInfo.setText("Item #" + (order.getTotalItems() + 1) + " info:");

                // final item order
                if (order.getCurrentNumItems() >= order.getMaxNumItems()) {
                    jlbItemsID.setVisible(false);
                    jlbQuantity.setVisible(false);
                    jbtProcessItem.setEnabled(false);
                    jbtConfirmItem.setEnabled(false);
                }
            }

        });

        jbtViewOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, order.getViewOrder());
            }
        });

        jbtFinishOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // write items to transactions.txt
                try {
                    order.printTransactions();
                    JOptionPane.showMessageDialog(null, order.getFinishOrder());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Store.super.dispose(); // dispose frame
            }
        });

        jbtNewOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Store.super.dispose(); // dispose frame
                // run main
                try {
                    Store.main(null);
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        jbtExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Store.super.dispose(); // dispose frame
            }
        });

    }

    public int linearSearch(String ItemsID) {
        for (int i = 0; i < this.inventory.size(); i++) {
            Items currentItems = inventory.get(i);
            if (currentItems.getItemsID().equals(ItemsID) == true)
                return i;
        }
        return -1;
    }

    public void getItemsFromFile() throws FileNotFoundException {
        // create load all Itemss in an arraylist
        this.inventory = new ArrayList<Items>();
        File file = new File("src/inventory.txt");
        Scanner sc = new Scanner(file);

        // scan in inventory into arraylist
        while (sc.hasNextLine()) {
            // grab next inventory line and parse into 3 strings
            String Items = sc.nextLine();
            String[] ItemsInfo = Items.split(",");

            // create a Items and set fields
            Items currentItems = new Items();
            currentItems.setItemsID(ItemsInfo[0]);

            currentItems.setName(ItemsInfo[1]);

            currentItems.setPrice(Double.parseDouble(ItemsInfo[3]));
            
            currentItems.setActive(ItemsInfo[2]);

            // add Items to inventory arraylist
            inventory.add(currentItems);
        }

        // close stream
        sc.close();
        // testing
        for (int i = 0; i < inventory.size(); i++) {
            Items current = inventory.get(i);
            }
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Store frame = new Store();
        frame.pack(); // fit windows for screen
//        frame.getContentPane().setBackground(Color.BLUE);
        frame.setTitle("Nile Dot Com");
        frame.setLocationRelativeTo(null); // center windows
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close window
        frame.setVisible(true); // display window

        
    }
}