package org.example.service;
import org.example.repository.entities.AccountsEntity;
import org.example.repository.entities.EditsEntity;
import org.example.repository.DAO.EditsDAO;
import org.example.repository.entities.PagesEntity;
import org.example.service.model.Account;
import org.example.service.model.Edit;
import org.example.service.model.Page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditsServiceTest {

	@Mock
	private EditsDAO editsDAO;
	@Mock
	private AccountsService accountsService;
	@Mock
	private PagesService pagesService;
	@InjectMocks
	private EditsService editsService;

	private EditsEntity testEditEntity;
	private Edit testEditModel;
	private AccountsEntity testAccountEntity;
	private Account testAccountModel;
	private PagesEntity testPageEntity;
	private Page testPageModel;

	@BeforeEach
	void setup(){
		// Setup Test EmployeeEntity
		testEditEntity = new EditsEntity();
		testEditEntity.setId(1);
		testEditEntity.setTimestamp(Instant.now());
		testEditEntity.setAccountID(10);
		testEditEntity.setPageID(20);
		testEditEntity.setContent("LOREM IPSUM, more latin words");

		// Setup Test EmployeeModel
		testEditModel = new Edit();
		testEditModel.setId(1);
		testEditModel.setTimestamp(Instant.now());
		testEditModel.setAccountID(10);
		testEditModel.setPageID(20);
		testEditModel.setContent("LOREM IPSUM, more latin words");


		testAccountEntity = new AccountsEntity();
		testAccountEntity.setId(10);
		testAccountEntity.setUsername("TestUser");
		testAccountEntity.setPassword("FakeWord");

		testAccountModel = new Account();
		testAccountModel.setId(10);
		testAccountModel.setUsername("TestUser");
		testAccountModel.setPassword("FakeWord");

		testPageEntity = new PagesEntity();
		testPageEntity.setId(20);
		testPageEntity.setTitle("TestPage");
		testPageEntity.setContent("LOREM IPSUM, more latin words");

		testPageModel = new Page();
		testPageModel.setId(20);
		testPageModel.setTitle("TestPage");
		testPageModel.setContent("LOREM IPSUM, more latin words");
	}

	@Test
	public void createEntity_Success_ReturnsNewId() throws SQLException
	{
		// AAA
		// Arrange
		// We have to prepare the test for the actual scenario we want to test for
		when(editsDAO.create(testEditEntity)).thenReturn(100);

		// Act
		// We use the method as we have mocked
		Integer result = editsService.createEntity(testEditEntity);

		// Assert
		// We verify the result of the method call
		assertEquals(100, result);
		// We also verify the behavior of the service function by how it calls its mocks
		verify(editsDAO, times(1)).create(testEditEntity);
	}

	@Test
	void convertEntityToModel_Success_ReturnsEmployeeModel() {
		// Arrange
		when(accountsService.getModelById(testEditEntity.getAccountID())).thenReturn(Optional.of(testAccountModel));
		when(pagesService.getModelById(testPageEntity.getId())).thenReturn(Optional.of(testPageModel));

		// Act
		Optional<Edit> result = editsService.convertEntityToModel(testEditEntity);

		// Assert
		assertTrue(result.isPresent());
		Edit edit = result.get();
		assertEquals(testEditEntity.getContent(), edit.getContent());
		assertEquals(testEditEntity.getTimestamp(), edit.getTimestamp());
		assertEquals(testEditEntity.getAccountID(), edit.getAccountID());
		assertEquals(testEditEntity.getPageID(), edit.getPageID());

		verify(accountsService, times(1)).getModelById(10);
		verify(pagesService, times(1)).getModelById(20);
	}

	@Test
	void createEntity_ThrowsSQLException_ReturnsNegativeOne() throws SQLException{
		// Arrange
		when(editsDAO.create(testEditEntity)).thenThrow(new SQLException("Database error"));

		// Act
		Integer result = editsService.createEntity(testEditEntity);

		// Assert
		assertEquals(-1, result);
		verify(editsDAO, times(1)).create(testEditEntity);
	}
}