package org.example.service;

import org.example.repository.entities.EditsEntity;
import org.example.service.model.Account;
import org.example.service.model.Edit;
import org.example.service.Interface.ServiceInterface;
import org.example.repository.DAO.EditsDAO;
import org.example.service.model.Page;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditsService implements ServiceInterface<EditsEntity, Edit>{

	private EditsDAO editsDAO = new EditsDAO();
	private AccountsService accountsService = new AccountsService();
	private PagesService pagesService = new PagesService();

	@Override
	public Integer createEntity(EditsEntity entity) {
		try{
			Integer newId = editsDAO.create(entity);
			return newId;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Optional<EditsEntity> getEntityById(Integer id) {
		try{
			Optional<EditsEntity> edit = editsDAO.findById(id);
			if(edit.isEmpty()){
				throw new RuntimeException("Edit not found");
			}
			return edit;
		}catch(SQLException | RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public List<EditsEntity> getAllEntities() {

		try{
			List<EditsEntity> editEntities = editsDAO.findAll();
			return editEntities;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public EditsEntity updateEntity(EditsEntity newEntity) {
		return null;
	}

	@Override
	public boolean deleteEntity(Integer id) {
		return false;
	}

	@Override
	public Optional<Edit> convertEntityToModel(EditsEntity entity) {
		try{
			Optional<Account> account = accountsService.getModelById(entity.getAccountID());

			if(account.isEmpty()){
				throw new RuntimeException("Invalid account id");
			}

			Optional<Page> page = pagesService.getModelById(entity.getPageID());

			if(page.isEmpty()){
				throw new RuntimeException("Invalid page id");
			}

			Edit edit = new Edit();
			edit.setId(entity.getId());
			edit.setAccountID(entity.getAccountID());
			edit.setContent(entity.getContent());
			edit.setTimestamp(entity.getTimestamp());
			edit.setPageID(entity.getPageID());

			return Optional.of(edit);

		}catch(RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Optional<Edit> getModelById(Integer id) {

		try{
			Optional<Edit> edit = convertEntityToModel(getEntityById(id).get());
			return edit;
		}catch(RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}

	}

	public List<Edit> getAllModels() {
		List<EditsEntity> editEntities = getAllEntities();
		List<Edit> edits = new ArrayList<>();
		for(EditsEntity editEntity: editEntities){
			Optional<Edit> edit = convertEntityToModel(editEntity);
			if(edit.isPresent()){
				edits.add(edit.get());
			}
		}
		return edits;
	}

	public List<Edit> getAllModelsByPage(Page page) {
		List<EditsEntity> editEntities = getAllEntitiesByPageId(page.getId());
		List<Edit> edits = new ArrayList<>();
		for(EditsEntity entity : editEntities){
			Optional<Edit> edit = convertEntityToModel(entity);
			if(edit.isPresent()){
				edits.add(edit.get());
			}
		}
		return edits;
	}

	private List<EditsEntity> getAllEntitiesByPageId(Integer pageId) {
		try{
			List<EditsEntity> editsEntities = editsDAO.findAllByPageId(pageId);
			return editsEntities;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public List<Edit> getAllModelsByAccount(Account account) {
		List<EditsEntity> editEntities = getAllEntitiesByAccountId(account.getId());
		List<Edit> edits = new ArrayList<>();
		for(EditsEntity entity : editEntities){
			Optional<Edit> edit = convertEntityToModel(entity);
			if(edit.isPresent()){
				edits.add(edit.get());
			}
		}
		return edits;
	}

	private List<EditsEntity> getAllEntitiesByAccountId(Integer accountId) {
		try{
			List<EditsEntity> editsEntities = editsDAO.findAllByPageId(accountId);
			return editsEntities;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
