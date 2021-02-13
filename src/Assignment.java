import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Assignment {

	// Global Variables
	String userID_global = "";
	String userPin_global = "";
	String accBalance_global = "";

	public Assignment() throws IOException {

		// call these methods
		welcome();
		login();
	}

	public void logout() {

		System.out.println("Thanks for choosing us. Good bye!");
		System.exit(0);

	}

	public void checkStock() throws IOException {
		FileInputStream stream = new FileInputStream("stockPrice.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);
		String linha = br.readLine();
		while (linha != null) {
			System.out.println(linha);
			linha = br.readLine();
		}
		anotherTransaction();
	}

	public void anotherTransaction() {
		System.out.println("Would you like to make another transaction? Type a number:");
		System.out.println("1. Yes");
		System.out.println("2. No");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";

		try {

			input = br.readLine();

			if (input.equals("1") || input.equals("y") || input.equals("Y")) {
				menu();
				menuSelect();
			}
			if (input.equals("2") || input.equals("n") || input.equals("N")) {
				logout();

			} else {
				System.out.println("You didn't enter a valid number");
				System.out.println("Try again");
				anotherTransaction();
			}

		} catch (Exception e) {
		}

	}

	public void changePassword() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String newPassword = "";
		/// ASK USER TO TYPE THE NEW PIN

		try {

			boolean valid = false;

			do {
				System.out.println("Type in the new PIN Number that you would like to use: (4 digits only)");
				newPassword = br.readLine();
				if (newPassword.matches("[0-9999]+")) {
					valid = true;
				} else {
					valid = false;
					System.out.println("Invalid Input. Type in only 4 digts.");

				}

			} while (valid == false);
		} catch (Exception e) {
		}

		// ASK ONE MORE TIME TO VERIFY THE PIN

		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		String newPassword2 = "";

		try {

			boolean valid = false;

			do {
				System.out.println("Please type it one more time: ");
				newPassword2 = br2.readLine();

				if (newPassword2.equals(newPassword)) {
					userPin_global = String.valueOf(newPassword2);
					valid = true;
					System.out.println("Your PIN Number has been changed to " + userPin_global);
					changeFile(accBalance_global, userPin_global);

				} else {
					valid = false;
					System.out.println("Your first entrance must match the second. Try again");
					changePassword();
				}

			} while (valid == false);
		} catch (Exception e) {
		}

		anotherTransaction();
	}

	public void withdrawMoney() {

		System.out.println("How Much?");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = ""; // move the input variable to the top
		// so we can see it after the catch

		try {

			boolean valid = false;

			do {
				System.out.println("Please enter a number: 20, 50, 100");
				input = br.readLine();

				if (input.contains("20") || input.contains("40") || input.contains("60") || input.contains("80")
						|| input.contains("50") || input.contains("100") || input.contains("200")) {
					valid = true;
				} else {
					valid = false;
					System.out.println("Sorry. Only 20, 50 or 100 notes available");
				}
			} while (valid == false);
		} catch (Exception e) {
			System.out.println("Error reading input");
		}

		//

		if (Double.parseDouble(accBalance_global) > Double.parseDouble(input)) {

			Double answer = Double.parseDouble(accBalance_global) - Double.parseDouble(input);
			accBalance_global = String.valueOf(answer);

			System.out.println("New balance: " + accBalance_global);
			changeFile(accBalance_global, userPin_global);

		} else {
			System.out.println("Sorry, you don't have enough money in your account.");

		}

		anotherTransaction();
	}

	private void changeFile(String balance, String password) {
		BufferedWriter writer = null;

		try {
			FileWriter fstream = new FileWriter(userID_global + ".txt", false);
			writer = new BufferedWriter(fstream);
			writer.write(userID_global);
			writer.newLine();
			writer.write(password);
			writer.newLine();
			writer.write(balance);
			writer.close();

		} catch (IOException ex) {
			System.out.println("Error");
		}

	}

	public void checkAccountBalance() {
		System.out.println("Account Balance is: " + accBalance_global);

		anotherTransaction();

	}

	public void menuSelect() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = ""; // move the input variable to the top
		// so we can see it later after the catch

		try {

			boolean valid = false;

			do {
				System.out.println("What would you like to do?");
				input = br.readLine();

				if (input.matches("[1-5]+")) {
					valid = true;
				} else {
					valid = false;
					System.out.println("Invalid input. Type a number between 1 and 5");
				}

			} while (valid == false);

		} catch (Exception e) {
			System.out.println("Error reading input");
		}

		// send user off
		if (input.equals("1")) {
			checkAccountBalance();
		}
		if (input.equals("2")) {
			withdrawMoney();

		}
		if (input.equals("3")) {
			changePassword();
		}
		if (input.equals("4")) {
			try {
				checkStock();
			} catch (Exception e) {
			}
		}
		if (input.equals("5")) {
			logout();
		} else {
			System.out.println("You didn't enter a valid number. Try again!");
			menuSelect();
		}
	}

	public void menu() {

		System.out.println("Please select an ATM Transaction");
		System.out.println("Press [1] to check current Account Balance");
		System.out.println("Press [2] to Withdraw Money");
		System.out.println("Press [3] to Change Password");
		System.out.println("Press [4] to Check the lastest Stock Prices for the Bank");
		System.out.println("Press [5] to Exit");

	}

	public void login() throws IOException {

		// ask user for their ID

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// Create variable first (changes scope)
		String idNumber = "";
		try {

			boolean valid = false;

			do {
				System.out.println("Please enter your ID: ");
				idNumber = br.readLine();
				if (idNumber.matches("[0-9]+")) {
					valid = true;
				} else {
					valid = false;
				}

			} while (valid == false);
		} catch (Exception e) {
		}

		// GET THE PIN

		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		String pinNumber = ""; // move the input variable to the top
		// so we can see it later after the catch
		try {

			boolean valid2 = false;

			do {
				System.out.println("Please enter your PIN: ");
				pinNumber = br2.readLine();

				if (pinNumber.matches("[0-9999]+")) {
					valid2 = true;

				} else {
					valid2 = false;

				}

			} while (valid2 == false);

		} catch (Exception e) {
			System.out.println("Error Reading Input");
		}

		/////////////////////////
		// Reading in the File///
		///////////////////////
		try {
			BufferedReader br3 = new BufferedReader(new FileReader(idNumber + ".txt"));

			String userID = br3.readLine();
			String userPIN = br3.readLine();
			String accBalance = br3.readLine();

			// Set the global variables
			userID_global = userID;
			userPin_global = userPIN;
			accBalance_global = accBalance;

			if (userID.equals(idNumber) && userPIN.equals(pinNumber)) {
				// GOES OK
				menu();
				menuSelect();
			} else {
				System.out.println("Wrong ID or Password. Try again!");
				login();
			}

		} catch (FileNotFoundException fnfex) {
			System.out.println("Invalid ID, Try again");
		}
		login();

	}

	public void welcome() {

		System.out.println("Welcome to Justo's Bank ATM Machine");

	}

	public static void main(String[] args) throws IOException {
		new Assignment();

	}

}