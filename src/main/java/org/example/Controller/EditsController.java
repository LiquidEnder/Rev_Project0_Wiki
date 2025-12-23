package org.example.Controller;

import org.example.repository.entities.EditsEntity;
import org.example.repository.entities.PagesEntity;
import org.example.service.AccountsService;
import org.example.service.PagesService;
import org.example.service.model.Account;
import org.example.service.model.Edit;
import org.example.util.InputHandler;
import org.example.service.EditsService;
import org.example.service.model.Page;

import java.util.List;
import java.util.Optional;

public class EditsController {
	private int current_user;
	private final EditsService editsService = new EditsService();
	private final PagesService pageService = new PagesService();
	private final AccountsService accountService = new AccountsService();

	public EditsController() {}

	public EditsController(int current_user) {
		this.current_user = current_user;
	}

	public void handleInput(){
		boolean running = true;
		while(running){
			printMenu();
			int choice = InputHandler.getIntInput("Make a choice!");
			switch(choice){
				case 1 -> addPage();
				case 2 -> addEdit();
//                case 2 -> getEmployeeById();
//                case 3 -> getEmployeeByName();
				case 3 -> getAllEdits();
				case 4 -> getAllEditsByAccount();
//                case 6 -> getAllEmployeesByLocation();
				case 0 -> {
					System.out.println("Leaving Employee Services");
					running = false; }
				default -> System.out.println("Invalid choice");
			}
		}
	}

	private void getAllEditsByAccount() {
		String username = InputHandler.getStringInput("What is the Account username?");
		Optional<Account> account = accountService.getModelByUsername(username);
		if(account.isPresent()){

			List<Edit> edits = editsService.getAllModelsByAccount(account.get());
			for(Edit edit : edits){
				System.out.println(edit);
			}
		}else{
			System.out.println("Invalid username");
		}
	}

	private void getAllEdits() {
		List<Edit> edits = editsService.getAllModels();
		for(Edit edit : edits){
			System.out.println(edit);
		}
	}

	private void addEdit() {
		// What do we expect from the user?
		// Page title
		String title = InputHandler.getStringInput("What is the Title of the page?");
		// page content
		String content = InputHandler.getStringInput("What is the new content you wish to input?");

		Optional<Page> page = pageService.getModelByTitle(title);
		if (page.isPresent()){
			EditsEntity editEntity = new EditsEntity();
			editEntity.setContent(content);
			editEntity.setPageID(page.get().getId());
			editEntity.setAccountID(current_user);

			Integer newEditId = editsService.createEntity(editEntity);

			if(newEditId == -1){
				System.out.println("Unable to Edit");
			}else{
				PagesEntity pagesEntity = pageService.getEntityById(page.get().getId()).get();
				pagesEntity.setContent(content);
				pageService.updateEntity(pagesEntity);
				System.out.println("page has been edited: " + newEditId);
			}
		}else{
			System.out.println("Page Title is invalid, please create page first");
		}
	}



	private void printMenu(){
		System.out.println("=== PAGE CREATION AND EDITS SERVICES ===");
		System.out.println("1. Create Page");
		System.out.println("2. Add Edit");
		System.out.println("3. Get All Edits");
		System.out.println("4. Get All Edits by Account");
		System.out.println("0. Exit");

	}

	public int getCurrent_user() {
		return current_user;
	}

	public void setCurrent_user(int current_user) {
		this.current_user = current_user;
	}

	private void addPage(){
		// WHat is needed?
		String pageName = InputHandler.getStringInput("What is the new Page title?");
		String content = InputHandler.getStringInput("What is the starting content?");
		PagesEntity pageEntity = new PagesEntity();
		pageEntity.setTitle(pageName);
		pageEntity.setContent(content);
		Integer newPageID = pageService.createEntity(pageEntity);

		if (newPageID == -1){
			System.out.println("Invalid Page Title");
		}else{
			pageEntity.setId(newPageID);
			addEditForNewPage(pageEntity);
			System.out.println("New Page Created: " + newPageID);
		}
	}

	private void addEditForNewPage(PagesEntity newPage) {
		// What do we expect from the user?
		// Page title
		String title = newPage.getTitle();
		// Department Name
		String content = newPage.getContent();

		EditsEntity editEntity = new EditsEntity();
		editEntity.setContent(newPage.getContent());
		editEntity.setPageID(newPage.getId());
		editEntity.setAccountID(current_user);

		Integer newEditId = editsService.createEntity(editEntity);
		if(newEditId == -1){
			System.out.println("Unable to place Edit for page creation");
		}
	}
}
