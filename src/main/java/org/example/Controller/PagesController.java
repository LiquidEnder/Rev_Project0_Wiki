package org.example.Controller;

import org.example.repository.entities.PagesEntity;
import org.example.service.PagesService;
import org.example.service.model.Page;
import org.example.util.InputHandler;

import java.util.List;
import java.util.Optional;

public class PagesController {
	private final PagesService pagesService = new PagesService();

	//can be set on Construction or login
	private boolean isAdmin;

	public PagesController(){isAdmin=false;}

	public PagesController(boolean admin) {
		isAdmin = admin;
	}

	private void printMenu(){
		System.out.println("=== PAGE VIEWING MENU ===");
		System.out.println("1. Search page by title");
		System.out.println("2. Search page by id");
		System.out.println("3. Get All Pages");
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
				//case 1 -> addPage();
				case 1 -> SearchPageByTitle();
				case 2 -> searchPageById();
				case 3 -> getAllPages();
				case 0 -> {
					System.out.println("Leaving Page Services");
					running = false;
				}
				default -> System.out.println("Invalid choice");
			}
		}
	}

	private void SearchPageByTitle() {
		// What can the user provide?
		String title = InputHandler.getStringInput("What is the title?");
		Optional<Page> page = pagesService.getModelByTitle(title);
		if(page.isPresent()){
			System.out.println(page.get());
		}else{
			System.out.println("Page not found");
		}
	}

	private void searchPageById() {
		// What can the user provide?
		Integer pageId = InputHandler.getIntInput("What is the page id?");
		Optional<Page> page = pagesService.getModelById(pageId);
		if(page.isPresent()){
			System.out.println(page.get());
		}else{
			System.out.println("Page not found");
		}
	}

	private void getAllPages() {
		List<Page> pages = pagesService.getAllModels();
		for(Page page : pages){
			System.out.println(page);
		}
	}
}
