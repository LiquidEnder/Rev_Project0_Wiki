package org.example.service;

import org.example.repository.DAO.AccountsDAO;
import org.example.repository.entities.AccountsEntity;
import org.example.service.model.Account;
import org.example.service.model.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {
	@Mock
	private AccountsDAO accountsDAO;
	@InjectMocks
	private AccountsService accountsService;

	private AccountsEntity testAccountEntity;
	private Account testAccountModel;
	private String userPass;
	private String userName;

	@BeforeEach
	void setup(){
		userPass = "fakePass";
		userName = "fakeName";

		testAccountEntity = new AccountsEntity();
		testAccountEntity.setId(10);
		testAccountEntity.setUsername(userName);
		testAccountEntity.setPassword(userPass);

		testAccountModel = new Account();
		testAccountModel.setId(10);
		testAccountModel.setUsername(userName);
		testAccountModel.setPassword(userPass);
	}

	@Test
	public void createEntity_Success_ReturnsNewId() throws SQLException
	{
		// AAA
		// Arrange
		// We have to prepare the test for the actual scenario we want to test for
		when(accountsDAO.create(testAccountEntity)).thenReturn(100);

		// Act
		// We use the method as we have mocked
		Integer result = accountsService.createEntity(testAccountEntity);

		// Assert
		// We verify the result of the method call
		assertEquals(100, result);
		// We also verify the behavior of the service function by how it calls its mocks
		verify(accountsDAO, times(1)).create(testAccountEntity);
	}

	@Test
	void convertEntityToModel_Success_ReturnsEmployeeModel() {
		// Arrange
		//no calls, no arrange.

		// Act
		Optional<Account> result = accountsService.convertEntityToModel(testAccountEntity);

		// Assert
		assertTrue(result.isPresent());
		Account account = result.get();
		assertEquals(testAccountEntity.getUsername(), account.getUsername());
		assertEquals(testAccountEntity.getPassword(), account.getPassword());
		assertEquals(testAccountEntity.getId(), account.getId());
	}

	@Test
	void createEntity_ThrowsSQLException_ReturnsNegativeOne() throws SQLException{
		// Arrange
		when(accountsDAO.create(testAccountEntity)).thenThrow(new SQLException("Database error"));

		// Act
		Integer result = accountsService.createEntity(testAccountEntity);

		// Assert
		assertEquals(-1, result);
		verify(accountsDAO, times(1)).create(testAccountEntity);
	}


	@Test
	void Login_Success_ReturnsID()
	{
		//arrange
		AccountsService selfCall = Mockito.spy(accountsService);
		Mockito.doReturn(Optional.of(testAccountModel)).when(selfCall).getModelByUsername(userName);

		// Act
		Integer result = selfCall.Login(userName, userPass);

		//assert
		assertEquals(testAccountModel.getId(), result);
		verify(selfCall, times(1)).getModelByUsername(userName);
	}

	@Test
	void LoginFailure_UsernameNotFound(){
		// Arrange
		AccountsService selfCall = Mockito.spy(accountsService);
		Mockito.doReturn(Optional.empty()).when(selfCall).getModelByUsername(userName);
		//RuntimeException e;

		// Act
		try {
			selfCall.Login(userName, userPass);
			assertEquals(1, 2);//should not reach this line
		}
		catch (RuntimeException e)
		{
			verify(selfCall, times(1)).getModelByUsername(userName);
			assertEquals("Username or password incorrect. try again.", e.getMessage());
		}
	}

	@Test
	void LoginFailure_PasswordIncorrect()
	{
		//arrange
		AccountsService selfCall = Mockito.spy(accountsService);
		Mockito.doReturn(Optional.of(testAccountModel)).when(selfCall).getModelByUsername(userName);

		// Act
		try {
			selfCall.Login(userName, userPass+" make incorrect");
			assertEquals(1, 2);//should not reach this line
		}
		catch (RuntimeException e)
		{
			verify(selfCall, times(1)).getModelByUsername(userName);
			assertEquals("Username or password incorrect. try again.", e.getMessage());
		}
	}
}