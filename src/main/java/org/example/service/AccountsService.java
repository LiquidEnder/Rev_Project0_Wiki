package org.example.service;

import org.example.repository.DAO.AccountsDAO;
import org.example.repository.entities.AccountsEntity;
import org.example.service.Interface.ServiceInterface;
import org.example.service.model.Account;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountsService implements ServiceInterface <AccountsEntity, Account>{

	private AccountsDAO accountsDAO = new AccountsDAO();

	@Override
	public Integer createEntity(AccountsEntity entity) {
		try{
			Integer newId = accountsDAO.create(entity);
			return newId;
		}catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Optional<AccountsEntity> getEntityById(Integer id) {
		try{
			Optional<AccountsEntity> accountsEntity = accountsDAO.findById(id);
			if(accountsEntity.isEmpty()){
				throw new RuntimeException("Account not found");
			}

			return accountsEntity;
		}catch(SQLException | RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public List<AccountsEntity> getAllEntities() {
		try{
			List<AccountsEntity> accountsEntities = accountsDAO.findAll();
			return accountsEntities;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AccountsEntity updateEntity(AccountsEntity newEntity) {return null;}

	@Override
	public boolean deleteEntity(Integer id) {return false;}

	@Override
	public Optional<Account> convertEntityToModel(AccountsEntity entity) {
		Account account = new Account();
		account.setId(entity.getId());
		account.setPassword(entity.getPassword());
		account.setUsername(entity.getUsername());

		return Optional.of(account);
	}

	@Override
	public Optional<Account> getModelById(Integer id) {
		Optional<AccountsEntity> accountsEntity = getEntityById(id);
		try{
			if(accountsEntity.isPresent()){
				Optional<Account> account = convertEntityToModel(accountsEntity.get());
				if(account.isPresent()){
					return account;
				}else{
					throw new RuntimeException("AccountEntity conversion failed");
				}
			}else{
				throw new RuntimeException("AccountEntity not found");
			}

		}catch(RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public List<Account> getAllModels() {
		List<AccountsEntity> accountsEntities = getAllEntities();
		List<Account> accounts = new ArrayList<>();
		for(AccountsEntity accountEntity : accountsEntities){
			Optional<Account> account = convertEntityToModel(accountEntity);
			if(account.isPresent()){
				accounts.add(account.get());
			}
		}
		return accounts;
	}

	public Optional<Account> getModelByUsername(String username) {
		Optional<AccountsEntity> accountEntity = getEntityByUsername(username);
		try{
			if(accountEntity.isPresent()){
				Optional<Account> account = convertEntityToModel(accountEntity.get());
				if(account.isPresent()){
					return account;
				}else{
					throw new RuntimeException("AccountEntity conversion failed");
				}
			}else{
				throw new RuntimeException("AccountEntity not found");
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	private Optional<AccountsEntity> getEntityByUsername(String username) {
		try{
			Optional<AccountsEntity> accountEntity = accountsDAO.findByUsername(username);
			return accountEntity;
		}catch (SQLException e){
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public int Login(String username, String password)
	{
		Optional<Account> user;
		try {
			user = getModelByUsername(username);
		}
		catch (RuntimeException e)
		{
			user = Optional.empty();
		}

		if(user.isPresent() && user.get().getPassword().equals(password)) {
				return user.get().getId();
		}
		else {
			throw new RuntimeException("Username or password incorrect. try again.");
		}
	}
}

