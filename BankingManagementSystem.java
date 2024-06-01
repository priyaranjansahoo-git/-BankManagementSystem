import java.sql.*; 
import java.io.*;
import java.util.Scanner;

public class BankingManagementSystem {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner =  new Scanner(System.in);
        
        try {
             
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
             
            String conUrl = "jdbc:oracle:thin:@localhost:1521";
            conn = DriverManager.getConnection(conUrl, "SYSTEM", "silu");
            stmt = conn.createStatement();
            
            boolean keepRunning = true;
            
            while (keepRunning) {
                System.out.println("\n***** Banking Management System *****");
                System.out.println("1. Show Customer Records");
                System.out.println("2. Add Customer Record");
                System.out.println("3. Delete Customer Record");
                System.out.println("4. Update Customer Information");
                System.out.println("5. Show Account Details of a Customer");
                System.out.println("6. Show Loan Details of a Customer");
                System.out.println("7. Deposit Money to an Account");
                System.out.println("8. Withdraw Money from an Account");
                System.out.println("9. Exit");
                System.out.println("Enter your choice (1-9):");
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        showCustomerRecords(stmt);
                        break;
                    case 2:
                        addCustomerRecord(stmt, scanner);
                        break;
                    case 3:
                        deleteCustomerRecord(stmt, scanner);
                        break;
                    case 4:
                        updateCustomerInformation(stmt, scanner);
                        break;
                    case 5:
                        showAccountDetails(stmt, scanner);
                        break;
                    case 6:
                        showLoanDetails(stmt, scanner);
                        break;
                    case 7:
                        depositMoney(stmt, scanner);
                        break;
                    case 8:
                        withdrawMoney(stmt, scanner);
                        break;
                    case 9:
                        keepRunning = false;
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error closing resources: " + ex.getMessage());
            }
        }
    }

    private static void showCustomerRecords(Statement stmt) {
        try {
            String query = "SELECT * FROM Customers";
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\nCustomer Records:");
            while (rs.next()) {
                System.out.println("Customer No: " + rs.getString("cust_no"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Phone No: " + rs.getString("phoneno"));
                System.out.println("City: " + rs.getString("city"));
                System.out.println("---------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void addCustomerRecord(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nAdd Customer Record");
            System.out.print("Enter Customer No: ");
            String custNo = scanner.next();
            
            System.out.print("Enter Name: ");
            String name = scanner.next();
            
            System.out.print("Enter Phone No: ");
            String phoneNo = scanner.next();
            
            System.out.print("Enter City: ");
            String city = scanner.next();
            
            String insertSQL = String.format(
                "INSERT INTO Customers (cust_no, name, phoneno, city) VALUES ('%s', '%s', '%s', '%s')",
                custNo, name, phoneNo, city
            );
            
            stmt.executeUpdate(insertSQL);
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
    }

    private static void deleteCustomerRecord(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nDelete Customer Record");
            System.out.print("Enter Customer No to Delete: ");
            String custNo = scanner.next();
            
            String deleteSQL = String.format("DELETE FROM Customers WHERE cust_no = '%s'", custNo);
            stmt.executeUpdate(deleteSQL);
            
            System.out.println("Customer deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
    }

    private static void updateCustomerInformation(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nUpdate Customer Information");
            System.out.print("Enter Customer No: ");
            String custNo = scanner.next();
            
            System.out.println("Select field to update:");
            System.out.println("1. Name");
            System.out.println("2. Phone No");
            System.out.println("3. City");
            
            int fieldChoice = scanner.nextInt();
            String fieldToUpdate = null;
            String newValue = null;
            
            switch (fieldChoice) {
                case 1:
                    fieldToUpdate = "name";
                    System.out.print("Enter new Name: ");
                    newValue = scanner.next();
                    break;
                case 2:
                    fieldToUpdate = "phoneno";
                    System.out.print("Enter new Phone No: ");
                    newValue = scanner.next();
                    break;
                case 3:
                    fieldToUpdate = "city";
                    System.out.print("Enter new City: ");
                    newValue = scanner.next();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            
            String updateSQL = String.format(
                "UPDATE Customers SET %s = '%s' WHERE cust_no = '%s'", 
                fieldToUpdate, newValue, custNo
            );
            
            stmt.executeUpdate(updateSQL);
            System.out.println("Customer information updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating customer information: " + e.getMessage());
        }
    }

    private static void showAccountDetails(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nShow Account Details");
            System.out.print("Enter Customer No: ");
            String custNo = scanner.next();
            
            String query = String.format("SELECT * FROM Accounts WHERE cust_no = '%s'", custNo);
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\nAccount Details:");
            while (rs.next()) {
                System.out.println("Account No: " + rs.getString("account_no"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Balance: " + rs.getFloat("balance"));
                System.out.println("Branch Code: " + rs.getString("branch_code"));
                System.out.println("---------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void showLoanDetails(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nShow Loan Details");
            System.out.print("Enter Customer No: ");
            String custNo = scanner.next();
            
            String query = String.format("SELECT * FROM Loans WHERE cust_no = '%s'", custNo);
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\nLoan Details:");
            while (rs.next()) {
                System.out.println("Loan No: " + rs.getString("loan_no"));
                System.out.println("Loan Amount: " + rs.getFloat("loan_amount"));
                System.out.println("Branch Code: " + rs.getString("branch_code"));
                System.out.println("---------------------");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void depositMoney(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nDeposit Money");
            System.out.print("Enter Account No: ");
            String accountNo = scanner.next();
            
            System.out.print("Enter Deposit Amount: ");
            float depositAmount = scanner.nextFloat();
            
            
            String query = String.format("SELECT balance FROM Accounts WHERE account_no = '%s'", accountNo);
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                float currentBalance = rs.getFloat("balance");
                float newBalance = currentBalance + depositAmount;
                
                
                String updateSQL = String.format(
                    "UPDATE Accounts SET balance = %.2f WHERE account_no = '%s'", 
                    newBalance, accountNo
                );
                
                stmt.executeUpdate(updateSQL);
                System.out.println("Deposit successful. New balance: " + newBalance);
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void withdrawMoney(Statement stmt, Scanner scanner) {
        try {
            System.out.println("\nWithdraw Money");
            System.out.print("Enter Account No: ");
            String accountNo = scanner.next();
            
            System.out.print("Enter Withdraw Amount: ");
            float withdrawAmount = scanner.nextFloat();
            
           
            String query = String.format("SELECT balance FROM Accounts WHERE account_no = '%s'", accountNo);
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                float currentBalance = rs.getFloat("balance");
                
                if (currentBalance >= withdrawAmount) {
                    float newBalance = currentBalance - withdrawAmount;
                    
                    
                    String updateSQL = String.format(
                        "UPDATE Accounts SET balance = %.2f WHERE account_no = '%s'", 
                        newBalance, accountNo
                    );
                    
                    stmt.executeUpdate(updateSQL);
                    System.out.println("Withdrawal successful. New balance: " + newBalance);
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
