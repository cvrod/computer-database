package com.excilys.cdb.CLI;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.cdb.DAO.CompanyDAO;
import com.excilys.cdb.DAO.ComputerDAO;
import com.excilys.cdb.DAO.GenericDAO;
import com.excilys.cdb.exception.UnknowTypeException;

public class CLI {

	public static final int MAX_CHOICE = 9;
	static Scanner sc = null;
	static int choice = -1;
	static GenericDAO queries = null;
	static ComputerDAO computerDAO = null;
	static CompanyDAO companyDAO = null;

	public static void showMenu() {
		System.out.println("\n *****Main Menu*****");
		System.out.println("1/ List All Computer");
		System.out.println("2/ List All Companies");
		System.out.println("3/ Get Computer Detail");
		System.out.println("9/ Quit");
	}

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

	public static void makeRequest(int choice) {
		StringBuffer resultStr = null;
		try {
			switch (choice) {
			case 1: // List all Computer
				System.out.println("\n--> Computer List : \n");
				resultStr = queries.listAll(GenericDAO.COMPUTER_TABLE);
				System.out.println(resultStr);
				break;
			case 2: // List all Companies
				System.out.println("\n--> Companies List : \n");
				resultStr = queries.listAll(GenericDAO.COMPANY_TABLE);
				System.out.println(resultStr);
				break;
			case 3: //Getting computer detail
				System.out.println("\n--> Getting computer detail :");
				int id = getValidId();
				resultStr = computerDAO.getComputerDetail(id);
				System.out.println(resultStr);
				break;
			default:
				break;
			}
		} catch (UnknowTypeException e) {
			System.out.println("Table inconnue !");
		}
	}

	public static void main(String[] args) {
		queries = new GenericDAO();
		computerDAO = new ComputerDAO();
		companyDAO = new CompanyDAO();
		
		boolean isFinished = false;
		while (!isFinished) {

			showMenu();
			choice = getChoice();
			System.out.println("Choice : " + choice);

			if (choice == 9) {
				System.out.println("Leaving app....");
				isFinished = true;
			} else {
				makeRequest(choice);
			}

		}
	}
}
