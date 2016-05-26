package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dao.UnknowTypeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Command Line Interface implementation Take request from user.
 */

public class CLI {

    public static final int MAX_CHOICE = 10;
    public static final int PAGE_OFFSET = 20;
    public static final String COMPUTER_TABLE = "computer";
    public static final String COMPANY_TABLE = "company";
    public static final CLI instance = new CLI();
    static Scanner sc = null;
    static int choice = -1;

    private ComputerService computerService;

    private CompanyService companyService;
    /**
     * Printing Menu.
     */
    public static void showMenu() {
        System.out.println("\n *****Main Menu*****");
        System.out.println("1/ List All Computer");
        System.out.println("2/ List All Companies");
        System.out.println("3/ Get Computer Detail");
        System.out.println("4/ Delete Computer");
        System.out.println("5/ Create Computer");
        System.out.println("6/ Update Computer");
        System.out.println("7/ List All Computer By Page");
        System.out.println("8/ List All Companies By Page");
        System.out.println("9/ Delete Company");
        System.out.println("10/ Quit");
    }

    /**
     * getting user choice from System.in.
     *
     * @return a valid choice
     */
    public static int getChoice() {
        boolean isValid = false;
        int answer = -1;

        while (!isValid) {
            try {
                answer = sc.nextInt();
            } catch (InputMismatchException e) {

            }
            if (answer >= 1 && answer <= MAX_CHOICE) {
                isValid = true;
            } else {
                System.out.println("Please enter a choice between 1 and "
                        + MAX_CHOICE + " !");
            }
        }
        return answer;
    }

    /**
     * getting a valid id from System.in.
     *
     * @return a valid id (int)
     */
    public static int getValidNumber() {
        boolean isValid = false;
        int answer = -1;

        while (!isValid) {
            try {
                sc = new Scanner(System.in);
                answer = sc.nextInt();
            } catch (InputMismatchException e) {
            }
            if (answer > 0) {
                isValid = true;
            } else {
                System.out.println("Please enter a Valid number...");
            }
        }
        return answer;
    }

    /**
     * Call DAO functions.
     *
     * @param choice
     *            user choice
     */
    public static void makeRequest(int choice) {
        int updateRes = -1;
        Computer tmpComputer = null;
        try {
            switch (choice) {
            case 1: // List all Computer
                System.out.println("\n--> Computer List : \n");
                printComputer((ArrayList<Computer>) instance.computerService
                        .listAll());
                break;
            case 2: // List all Companies
                System.out.println("\n--> Companies List : \n");
                printCompany(
                        (ArrayList<Company>) instance.companyService.listAll());
                break;
            case 3: // Getting computer detail
                System.out.println("\n--> Getting computer detail :");
                System.out.println("\tid ?");
                int id = getValidNumber();
                tmpComputer = instance.computerService.get(id);
                if (tmpComputer != null) {
                    System.out.println(tmpComputer.toString());
                }
                break;
            case 4: // Delete a computer (by id)
                System.out.println("\n--> Delete Computer : ");
                System.out.println("\tid ?");
                id = getValidNumber();
                updateRes = instance.computerService.delete(id);
                if (updateRes == 1) {
                    System.out.println("Delete Sucess !");
                } else {
                    System.out.println("Error occur during delete !");
                }
                break;
            case 5: // Create a computer
                tmpComputer = getComputerFromCLI();
                tmpComputer = instance.computerService.add(tmpComputer);
                if (tmpComputer != null) {
                    System.out.println("Insertion Success !");
                } else {
                    System.out.println("Error occur during insertion !");
                }
                break;
            case 6: // Update a computer
                System.out.println("\n--> Update Computer");
                System.out.println("\tid ?");
                id = getValidNumber();
                tmpComputer = getComputerFromCLI();
                updateRes = instance.computerService.update(id, tmpComputer);
                if (updateRes == 1) {
                    System.out.println("Update Success !");
                } else {
                    System.out.println("Problem during update !");
                }
                break;
            case 7: // List All Computer By Page
                System.out.println("All Computer by page");
                printListByPage(COMPUTER_TABLE);
                break;
            case 8: // List All Companies By Page
                System.out.println("All Companies by page");
                printListByPage(COMPANY_TABLE);
                break;
            case 9: // Delete Company
                System.out.println("Delete Company");
                System.out.println("\n--> Delete Company : ");
                System.out.println("\tid ?");
                id = getValidNumber();
                updateRes = instance.companyService.delete(id);
                if (updateRes == 1) {
                    System.out.println("Delete Sucess !");
                } else {
                    System.out.println("Error occur during delete !");
                }
            default:
                break;
            }
        } catch (UnknowTypeException e) {
            System.out.println("Table inconnue !");
        }
    }

    /**
     * Printing List of computer or company by page.
     *
     * @param type
     *            GenericDAO.COMPUTER_TABLE or GenericDAO.COMPANY_TABLE
     * @throws UnknowTypeException
     *             if given table doesnt exist
     */
    private static void printListByPage(String type)
            throws UnknowTypeException {
        ArrayList<Company> companyList = null;
        ArrayList<Computer> computerList = null;
        int start = 0;
        int offset = PAGE_OFFSET;
        boolean isFinished = false;
        boolean hasNext = true;
        while (!isFinished) {
            if (type.equals(COMPUTER_TABLE)) {
                computerList = instance.computerService
                        .listAllByPage(start, offset).getElementList();
                printComputer(computerList);
                if (computerList.size() != 20) {
                    hasNext = false;
                } else {
                    hasNext = true;
                }
            } else if (type.equals(COMPANY_TABLE)) {
                companyList = instance.companyService
                        .listAllByPage(start, offset).getElementList();
                printCompany(companyList);
                if (companyList.size() != 20) {
                    hasNext = false;
                } else {
                    hasNext = true;
                }
            }
            System.out.println("\n1/Prev Page -- 2/Next Page -- 3/Quit");
            int userChoice = getValidNumber();
            if (start != 0 && userChoice == 1) {
                start -= 20;
            } else if (userChoice == 2 && hasNext) {
                start += 20;
            } else if (userChoice == 3) {
                isFinished = true;
            } else {
                System.out.println("Wrong entry");
            }
        }
    }

    /**
     * Getting a Computer Object from CLI.
     *
     * @return A Valid Computer Object ready to go in the database
     */
    private static Computer getComputerFromCLI() {
        Computer res = null;
        boolean isValid = false;
        String name = null;

        while (!isValid) {
            sc = new Scanner(System.in);
            System.out.println("Computer Name ?");
            name = sc.nextLine();
            name = name.trim();
            if (!name.equals("")) {
                isValid = true;
            }
        }
        System.out.println("Introduction date ?");
        String intro = getDateFromCLI();

        try {
            LocalDate.parse(intro);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid or null introduction date... Skipping");
            intro = null;
        }

        System.out.println("Discontinued date ?");
        String discontinued = getDateFromCLI();

        try {
            LocalDate.parse(discontinued);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid or null discontinued date... Skipping");
            discontinued = null;
        }

        System.out.println("Company ?");
        int idCompany = getValidNumber();
        Company c;
        c = instance.companyService.get(idCompany);

        res = new Computer(name, intro, discontinued, c);
        return res;
    }

    /**
     * return a date from command line.
     *
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

        return year + "-" + month + "-" + day;
    }

    /**
     * Print computer id & name from a computer list.
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
     * Print company infos from a company list.
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
     * Initialize DAO, Mapper, Connection and lauching menu main loop.
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        sc.useDelimiter("\\n");
        // Get application context
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        instance.companyService = (CompanyService) applicationContext.getBean("companyService");
        instance.computerService = (ComputerService) applicationContext.getBean("computerService");
        

        boolean isFinished = false;
        while (!isFinished) {
            showMenu();
            choice = getChoice();
            System.out.println("// Choice : " + choice + " //");

            if (choice == 10) {
                System.out.println("Leaving app....");
                sc.close();
                isFinished = true;
            } else {
                makeRequest(choice);
            }
        }
        ((ConfigurableApplicationContext) applicationContext).close();
    }
}
