package org.example.Controller;

import org.example.service.AccountsService;
import org.example.service.model.Account;
import org.example.repository.DAO.AccountsDAO;
import org.example.repository.entities.AccountsEntity;
import org.example.util.InputHandler;

import java.util.List;
import java.util.Optional;


public class AccountsController {

	private final AccountsService accountsService = new AccountsService();

	//can be set on Construction or login
	private boolean isAdmin;

	public AccountsController(){isAdmin=false;}

	public AccountsController(boolean admin) {
		isAdmin = admin;
	}

	private void printMenu(){
		System.out.println("=== ACCOUNTS SERVICES MENU ===");
		if(isAdmin) {
			System.out.println("1. Add account");
			System.out.println("2. Search account by username");
			System.out.println("3. Search accounts by id");
			System.out.println("4. Get All accounts");
		}
		System.out.println("0. Exit");
	}


	public void handleInput(){
		boolean running = true;
		while(running){
			printMenu();
			int choice = InputHandler.getIntInput("Enter your choice: ");

			//only admins can mess with accounts.
			if(isAdmin) choice = 0;

			switch(choice){
				case 1 -> addAccount();
				case 2 -> searchAccountByUsername();
				case 3 -> searchAccountById();
				case 4 -> getAllAccounts();
				case 0 -> {
					System.out.println("Leaving Account Services");
					running = false;
				}
				default -> System.out.println("Invalid choice");
			}
		}
	}

	private void addAccount(){
		// WHat is needed?
		String username = InputHandler.getStringInput("What is the new username?");
		String password = InputHandler.getStringInput("What is the password?");
		String password2 = InputHandler.getStringInput("re confirm password.");

		while (!password.equals(password2)) {
			password = InputHandler.getStringInput("ERROR: PASSWORDS DO NOT MATCH, INPUT AGAIN\n" +
					"What is the password?");
			password2 = InputHandler.getStringInput("re confirm password.");
		}

		AccountsEntity accountEntity = new AccountsEntity();
		accountEntity.setUsername(username);
		accountEntity.setPassword(password);
		Integer newAccountId = accountsService.createEntity(accountEntity);

		if (newAccountId == -1){
			System.out.println("Invalid Username");
		}else{
			System.out.println("New Account Created: " + newAccountId);
		}
	}


	private void searchAccountByUsername() {
		// What can the user provide?
		String username = InputHandler.getStringInput("What is the username?");
		Optional<Account> account = accountsService.getModelByUsername(username);
		if(account.isPresent()){
			System.out.println(account.get());
		}else{
			System.out.println("Account not found");
		}
	}

	private void searchAccountById() {
		// What can the user provide?
		Integer accountId = InputHandler.getIntInput("What is the account id?");
		Optional<Account> account = accountsService.getModelById(accountId);
		if(account.isPresent()){
			System.out.println(account.get());
		}else{
			System.out.println("Account not found");
		}
	}

	private void getAllAccounts() {
		List<Account> accounts = accountsService.getAllModels();
		for(Account account : accounts){
			System.out.println(account);
		}
	}


	//SPECIAL FUNCTION. USED TO LOGIN ONLY
	public int login(){
		int id = -1;

		do {
			String username = InputHandler.getStringInput("Username: ");
			String password = InputHandler.getStringInput("Password: ");

			try {
				id = accountsService.Login(username, password);
			}
			catch(RuntimeException e)
			{
				System.out.println(e.getMessage());
			}
		}while(id<0);

		isAdmin= id == 0;

		return id;
	}
}
