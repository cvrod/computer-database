package com.excilys.cdb.CLI;

import java.sql.ResultSet;
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
	 * 
	 * @param choice
	 */
	public static void makeRequest(int choice) {
		int updateRes = -1;
		ResultSet rs = null;
		ArrayList<Company> companyList = null;
		ArrayList<Computer> computerList = null;
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
			case 4:
				System.out.println("\n--> Delete Computer : ");
				id = getValidId();
				updateRes = computerDAO.deleteComputer(id);
				if (updateRes == 1) {
					System.out.println("Delete Sucess !");
				} else {
					System.out.println("Error occur during Delete !");
				}
				break;
			default:
				break;
			}
		} catch (UnknowTypeException e) {
			System.out.println("Table inconnue !");
		}
	}

	private static void printComputerDetail(ArrayList<Computer> computerList) {
		for (Computer c : computerList) {
			System.out.println(c.toString());
		}
	}

	private static void printComputer(ArrayList<Computer> computerList) {
		for (Computer c : computerList) {
			System.out.println("ID : " + c.getId() + ", Name : " + c.getName());
		}
	}

	private static void printCompany(ArrayList<Company> companyList) {
		for (Company c : companyList) {
			System.out.println(c.toString());
		}
	}

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
