package org.example;

import org.example.Controller.AccountsController;
import org.example.Controller.EditsController;
import org.example.Controller.PagesController;
import org.example.service.AccountsService;

import org.example.repository.DAO.AccountsDAO;

import org.example.repository.entities.AccountsEntity;

import org.example.service.model.Account;
import org.example.util.ConnectionHandler;
import org.example.util.InputHandler;

import java.sql.SQLException;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
	public static void main(String[] args) throws SQLException {
		AccountsController accountController = new AccountsController();
		PagesController pageController = new PagesController();
		EditsController editController = new EditsController();
		//handle login
		int userID = accountController.login();
		editController.setCurrent_user(userID);


		System.out.println("=== WIKI MANAGEMENT SYSTEM ===");

		boolean running = true;
		while(running){
			printMenu();
			int choice = InputHandler.getIntInput("Make a choice: ");
			switch(choice){
				case 1 -> accountController.handleInput();
				case 2 -> pageController.handleInput();
				case 3 -> editController.handleInput();
				case 0 -> {
					System.out.println("Goodbye!");
					running = false;
				}
			}
		}
	}


	private static void printMenu(){
		System.out.println("=== MAIN MENU ===");
		System.out.println("1. Accounts Services");
		System.out.println("2. Page Viewing Services");
		System.out.println("3. Page Creation and Editing Services");
		System.out.println("0. Exit");
	}

}
