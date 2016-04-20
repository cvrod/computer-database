package com.excilys.cdb.CLI;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.cdb.DAO.CompanyDAO;
import com.excilys.cdb.DAO.ComputerDAO;
import com.excilys.cdb.DAO.GenericDAO;
import com.excilys.cdb.exception.UnknowTypeException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DBConnect;


/**
 * Command Line Interface implementation Take request from user
 */
public class CLI {

	public static final int MAX_CHOICE = 9;
	static Scanner sc = null;
	static int choice = -1;
	static ComputerDAO computerDAO = null;
	static CompanyDAO companyDAO = null;
	static CompanyMapper companyMapper = null;
	static ComputerMapper computerMapper = null;
	static DBConnect connection = null;

	/**
	 * Printing Menu
	 */
	public static void showMenu() {
		System.out.println("\n *****Main Menu*****");
		System.out.println("1/ List All Computer");
		System.out.println("2/ List All Companies");
		System.out.println("3/ Get Computer Detail");
		System.out.println("4/ Delete Computer");
		System.out.println("5/ Create Computer");
		System.out.println("9/ Quit");
	}

	/**
	 * getting user choice from System.in
	 * 
	 * @return a valid choice
	 */
	public static int getChoice() {
		boolean isValid = false;
		int answer = -1;

		while (!isValid) {
			try {
				sc = new Scanner(System.in);
				answer = sc.nextInt();
			} catch (InputMismatchException e) {

			}
			if (answer >= 1 && answer <= MAX_CHOICE) {
				isValid = true;
			} else {
				System.out.println("Please enter a choice between 1 and " + MAX_CHOICE + " !");
			}
		}
		return answer;
	}

	/**
	 * getting a valid id from System.in
	 * 
	 * @return a valid id (int)
	 */
	public static int getValidId() {
		boolean isValid = false;
		int answer = -1;
		while (!isValid) {
			try {
				System.out.println("\tid ?");
				sc = new Scanner(System.in);
				answer = sc.nextInt();
			} catch (InputMismatchException e) {
			}
			if (answer > 0) {
				isValid = true;
			} else {
				System.out.println("Please enter a Valid ID...");
			}
		}
		return answer;
	}

	/**
	 * Call DAO functions
	 * 
	 * @param choice
	 */
	public static void makeRequest(int choice) {
		int updateRes = -1;
		ResultSet rs = null;
		ArrayList<Company> companyList = null;
		ArrayList<Computer> computerList = null;
		Computer tmpComputer = null;
		try {
			switch (choice) {
			case 1: // List all Computer
				System.out.println("\n--> Computer List : \n");
				rs = computerDAO.listAll(GenericDAO.COMPUTER_TABLE);
				computerList = computerMapper.map(rs);
				printComputer(computerList);
				break;
			case 2: // List all Companies
				System.out.println("\n--> Companies List : \n");
				rs = companyDAO.listAll(GenericDAO.COMPANY_TABLE);
				companyList = companyMapper.map(rs);
				printCompany(companyList);
				break;
			case 3: // Getting computer detail
				System.out.println("\n--> Getting computer detail :");
				int id = getValidId();
				rs = computerDAO.getComputerDetail(id);
				computerList = computerMapper.map(rs);
				printComputerDetail(computerList);
				break;
			case 4: // Delete a computer (by id)
				System.out.println("\n--> Delete Computer : ");
				id = getValidId();
				updateRes = computerDAO.deleteComputer(id);
				if (updateRes == 1) {
					System.out.println("Delete Sucess !");
				} else {
					System.out.println("Error occur during delete !");
				}
				break;
			case 5: //Create a computer
				tmpComputer = getComputerFromCLI();
				int res = computerDAO.addingComputer(tmpComputer);
				if(res == 1){
					System.out.println("Insertion Success !");
				} else {
					System.out.println("Error occur during insertion !");
				}
			default:
				break;
			}
		} catch (UnknowTypeException e) {
			System.out.println("Table inconnue !");
		}
	}

	/**
	 * Getting a Computer Object from CLI
	 * @return A Valid Computer Object ready to go in the database
	 */
	private static Computer getComputerFromCLI() {
		Computer res = null;
		boolean isValid = false;
		String name = null;
		
		while(!isValid){
			System.out.println("Computer Name ?");
			sc = new Scanner(System.in);
			name = sc.nextLine();
			name = name.trim();
			if(!name.equals("")){
				isValid = true;
			}
		}
		System.out.println("Introduction date ?");
		String intro = getDateFromCLI();
		
		try{
			LocalDate.parse(intro);
		}catch(DateTimeParseException e){
			System.out.println("Invalid or null introduction date... Skipping");
			intro = null;
		}
		
		System.out.println("Discontinued date ?");
		String discontinued = getDateFromCLI();
		
		try{
			LocalDate.parse(discontinued);
		}catch(DateTimeParseException e){
			System.out.println("Invalid or null discontinued date... Skipping");
			discontinued = null;
		}
		
		System.out.println("Company ?");
		int idCompany = getValidId();
		ResultSet resCompany = companyDAO.getCompanyById(idCompany);
		ArrayList <Company> companyList = companyMapper.map(resCompany);
		if(companyList.size() == 0){
			res = new Computer(name, intro, discontinued, null);
		}
		else{
			Company c = companyList.get(0);
			res = new Computer(name, intro, discontinued, c);
		}
		return res;
	}

	/**
	 * return a date from command line
	 * @return str containing a valid or invalid date, need to check date after
	 */
	private static String getDateFromCLI() {
		String year, month, day;
		
		System.out.println("\tYear ?");
		year = sc.nextLine();
		System.out.println("\tMonth ?");
		month = sc.nextLine();
		System.out.println("\tDay ?");
		day = sc.nextLine();
		
		return year+"-"+month+"-"+day;
	}

	/**
	 * Print computer detail from a computer list
	 * 
	 * @param computerList
	 *            list to print
	 */
	private static void printComputerDetail(ArrayList<Computer> computerList) {
		if (computerList.size() == 0) {
			System.out.println("Nothing to show !");
		}
		for (Computer c : computerList) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Print computer id & name from a computer list
	 * 
	 * @param computerList
	 *            list to print
	 */
	private static void printComputer(ArrayList<Computer> computerList) {
		if (computerList.size() == 0) {
			System.out.println("Nothing to show !");
		}
		for (Computer c : computerList) {
			System.out.println("ID : " + c.getId() + ", Name : " + c.getName());
		}
	}

	/**
	 * Print company infos from a company list
	 * 
	 * @param companyList
	 *            list to print
	 */
	private static void printCompany(ArrayList<Company> companyList) {
		if (companyList.size() == 0) {
			System.out.println("Nothing to show !");
		}
		for (Company c : companyList) {
			System.out.println(c.toString());
		}
	}

	/**
	 * Initialize DAO, Mapper, Connection and lauching menu main loop
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		computerDAO = new ComputerDAO();
		companyDAO = new CompanyDAO();
		companyMapper = CompanyMapper.getInstance();
		computerMapper = ComputerMapper.getInstance();
		connection = DBConnect.getInstance();
		boolean isFinished = false;
		while (!isFinished) {
			showMenu();
			choice = getChoice();
			System.out.println("// Choice : " + choice + " //");

			if (choice == 9) {
				System.out.println("Leaving app....");
				sc.close();
				isFinished = true;
			} else {
				makeRequest(choice);
			}
		}
	}
}
