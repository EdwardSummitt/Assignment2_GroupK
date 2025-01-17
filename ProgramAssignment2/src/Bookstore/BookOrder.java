package Bookstore;

import java.io.File;
import java.util.Scanner;
import java.util.InputMismatchException;

// NOTE TO SELF: doesn't actually save the changes made into the csv, im not sure if he wants us to do that though

public class BookOrder {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Step 1: Assume that the CSV file is in the same folder as the Java files
        Scanner inputScanner = new Scanner(System.in);
        String filePath = "orders.csv";

        // Step 2: Read data from the user-provided file path if the csv could not be found and insert into the tree
        boolean validFile = false;
        while (!validFile) {
            try {
                Scanner fileScanner = new Scanner(new File(filePath));

                // Skip the first line (header)
                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }

                // Read and process each line
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(","); // Assuming the CSV is comma-separated

                    int orderID = Integer.parseInt(parts[0].trim());
                    String bookName = parts[1].trim();

                    tree.insert(orderID, bookName);
                }
                fileScanner.close();
                validFile = true; // File was read successfully, exit loop
            } catch (Exception e) {
                System.out.println("The csv file could not be found: " + e.getMessage());
                System.out.print("Please place orders.csv directly in the root directory of your project "
                		+ "(the main project folder, not inside src or any package) and rerun the program."
                		+ "\nIf the above does not work, please enter a valid file path for orders.csv (without quotes): ");
                filePath = inputScanner.nextLine();
            }
        }

        // Step 3: User menu
        while (true) {
            System.out.println("\n--- Bookstore Order Management ---");
            System.out.println("1. Add new order");
            System.out.println("2. Fulfill order (remove)");
            System.out.println("3. Print in-order list of book names");
            System.out.println("4. Find book by order ID");
            System.out.println("5. Find oldest order");
            System.out.println("6. Find latest order");
            System.out.println("7. Exit");
            System.out.println("----------------------------------");

            int choice = -1;
            try {
                choice = inputScanner.nextInt();
                inputScanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                inputScanner.nextLine(); // Consume invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    // Add new order
                    try {
                        System.out.print("Enter Order ID: ");
                        int newOrderID = inputScanner.nextInt();
                        inputScanner.nextLine(); // Consume newline

                        System.out.print("Enter Book Name: ");
                        String newBookName = inputScanner.nextLine();

                        tree.insert(newOrderID, newBookName);
                        System.out.println("Order added successfully.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Order ID should be an integer. Please try again.");
                        inputScanner.nextLine(); // Consume invalid input
                    }
                    break;

                case 2:
                    // Fulfill order (remove order)
                    try {
                        System.out.print("Enter Order ID to remove: ");
                        int removeOrderID = inputScanner.nextInt();
                        inputScanner.nextLine(); // Consume newline
                        
                        String bookName = tree.search(removeOrderID);
                        if (bookName != null) {
                            System.out.println("Order found with ID: " + removeOrderID + ", Book: " + bookName);
                            tree.delete(removeOrderID);
                            System.out.println("Order removed successfully.");
                        } else {
                            System.out.println("Order not found.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Order ID should be an integer. Please try again.");
                        inputScanner.nextLine(); // Consume invalid input
                    }
                    break;

                case 3:
                    // Print in-order traversal
                    System.out.println("In-order list of books:");
                    tree.inOrderTraversal();
                    break;

                case 4:
                    // Find book by order ID
                    try {
                        System.out.print("Enter Order ID to find: ");
                        int searchOrderID = inputScanner.nextInt();
                        inputScanner.nextLine(); // Consume newline

                        String bookName = tree.search(searchOrderID);
                        if (bookName != null) {
                            System.out.println("Order found with ID: " + searchOrderID + ", Book: " + bookName);
                        } else {
                            System.out.println("Order not found.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Order ID should be an integer. Please try again.");
                        inputScanner.nextLine(); // Consume invalid input
                    }
                    break;

                case 5:
                    // Find oldest order (minimum order ID)
                    AVLTree.Node minOrder = tree.findMinOrder();
                    if (minOrder != null) {
                        System.out.println("Oldest order - OrderID: " + minOrder.orderID + ", Book: " + minOrder.bookName);
                    } else {
                        System.out.println("No orders found.");
                    }
                    break;

                case 6:
                    // Find latest order (maximum order ID)
                    AVLTree.Node maxOrder = tree.findMaxOrder();
                    if (maxOrder != null) {
                        System.out.println("Latest order - OrderID: " + maxOrder.orderID + ", Book: " + maxOrder.bookName);
                    } else {
                        System.out.println("No orders found.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting the program.");
                    inputScanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            // Print number of nodes
            int nodeCount = tree.getNodeCount();
            System.out.println("Number of nodes: " + nodeCount);
            // Print tree height
            int treeHeight = tree.getHeight();
            System.out.println("Tree height: " + treeHeight);
        }
    }
}
