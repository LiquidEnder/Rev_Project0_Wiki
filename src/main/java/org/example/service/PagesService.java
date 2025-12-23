package org.example.service;


import org.example.repository.DAO.PagesDAO;
import org.example.repository.entities.PagesEntity;
import org.example.service.Interface.ServiceInterface;
import org.example.service.model.Page;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PagesService implements ServiceInterface<PagesEntity, Page>{

	private PagesDAO pageDAO = new PagesDAO();

	@Override
	public Integer createEntity(PagesEntity entity) {
		try{
			Integer newId = pageDAO.create(entity);
			return newId;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Optional<PagesEntity> getEntityById(Integer id) {
		try{
			Optional<PagesEntity> pageEntity = pageDAO.findById(id);
			if(pageEntity.isEmpty()){
				throw new RuntimeException("Page not found");
			}

			return pageEntity;
		}catch(SQLException | RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public List<PagesEntity> getAllEntities() {
		try{
			List<PagesEntity> pageEntities = pageDAO.findAll();
			return pageEntities;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	//can be called by edits
	@Override
	public PagesEntity updateEntity(PagesEntity newEntity) {
		Optional<PagesEntity> oldPage = Optional.empty();
		Optional<Boolean> pageExists;
		Optional<Boolean> titleExists;

		//handle bad input
			//no errors from subfuctions
			//title must be unique
			//page must exist
		pageExists = pageExists(newEntity.getId());
		titleExists = pageExists(newEntity.getTitle());
		if(pageExists.isEmpty()) return null;
		if(titleExists.isEmpty()) return null;

		if(titleExists.get())
		{
			oldPage = getEntityByTitle(newEntity.getTitle());
			if(!oldPage.get().getId().equals(newEntity.getId()))
			{
				throw new RuntimeException("Title must be unique");
			}
		} else if (pageExists.get()) {
			oldPage = getEntityById(newEntity.getId());
		}

		if(oldPage.isEmpty())
		{
			throw new RuntimeException("page does not exist");
		}

		//handle update old page
		try {
			pageDAO.updateById(newEntity);
			return oldPage.get();
		}
		catch(SQLException | RuntimeException e)
		{
			e.printStackTrace();
			return oldPage.get();
		}
	}

	@Override
	public boolean deleteEntity(Integer id) {
		return false;
	}

	@Override
	public Optional<Page> convertEntityToModel(PagesEntity entity) {
		Page page = new Page();
		page.setId(entity.getId());
		page.setContent(entity.getContent());
		page.setTitle(entity.getTitle());

		return Optional.of(page);
	}

	@Override
	public Optional<Page> getModelById(Integer id) {
		Optional<PagesEntity> pageEntity = getEntityById(id);
		try{
			if(pageEntity.isPresent()){
				Optional<Page> page = convertEntityToModel(pageEntity.get());
				if(page.isPresent()){
					return page;
				}else{
					throw new RuntimeException("PageEntity conversion failed");
				}
			}else{
				throw new RuntimeException("PageEntity not found");
			}

		}catch(RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public List<Page> getAllModels() {
		List<PagesEntity> pageEntities = getAllEntities();
		List<Page> pages = new ArrayList<>();
		for(PagesEntity pageEntity : pageEntities){
			Optional<Page> page = convertEntityToModel(pageEntity);
			if(page.isPresent()){
				pages.add(page.get());
			}
		}
		return pages;
	}

	public Optional<Page> getModelByTitle(String title) {
		Optional<PagesEntity> pageEntity = getEntityByTitle(title);
		try{
			if(pageEntity.isPresent()){
				Optional<Page> page = convertEntityToModel(pageEntity.get());
				if(page.isPresent()){
					return page;
				}else{
					throw new RuntimeException("PageEntity conversion failed");
				}
			}else{
				throw new RuntimeException("PageEntity not found");
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private Optional<PagesEntity> getEntityByTitle(String title) {
		try{
			Optional<PagesEntity> pageEntity = pageDAO.findByTitle(title);
			return pageEntity;
		}catch (SQLException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public Optional<Boolean> pageExists(int id)
	{
		try{
			Optional<PagesEntity> pageEntity = pageDAO.findById(id);
			if(pageEntity.isPresent()){

				return Optional.of((Boolean) true);
			}

			return Optional.of((Boolean) false);
		}catch(SQLException | RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public Optional<Boolean> pageExists(String title)
	{
		try{
			Optional<PagesEntity> pageEntity = pageDAO.findByTitle(title);
			if(pageEntity.isPresent()){

				return Optional.of((Boolean) true);
			}

			return Optional.of((Boolean) false);
		}catch(SQLException | RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}
}

