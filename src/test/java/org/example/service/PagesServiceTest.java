package org.example.service;

import org.example.repository.DAO.PagesDAO;
import org.example.repository.entities.AccountsEntity;
import org.example.repository.entities.EditsEntity;
import org.example.repository.entities.PagesEntity;
import org.example.service.model.Account;
import org.example.service.model.Edit;
import org.example.service.model.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagesServiceTest {
	@Mock
	private PagesDAO pagesDAO;
	@InjectMocks
	private PagesService pagesService;

	private PagesEntity testPageEntity;
	private Page testPageModel;
	private PagesEntity testInputPage;

	@BeforeEach
	void setup(){
		testPageEntity = new PagesEntity();
		testPageEntity.setId(20);
		testPageEntity.setTitle("TestPage");
		testPageEntity.setContent("LOREM IPSUM, more latin words");

		testPageModel = new Page();
		testPageModel.setId(20);
		testPageModel.setTitle("TestPage");
		testPageModel.setContent("LOREM IPSUM, more latin words");

		testInputPage = new PagesEntity();
		testInputPage.setTitle("TestPage");
		testInputPage.setContent("LOREM IPSUM, more latin words");
	}

	@Test
	public void createEntity_Success_ReturnsNewId() throws SQLException
	{
		// AAA
		// Arrange
		// We have to prepare the test for the actual scenario we want to test for
		when(pagesDAO.create(testInputPage)).thenReturn(100);

		// Act
		// We use the method as we have mocked
		Integer result = pagesService.createEntity(testInputPage);

		// Assert
		// We verify the result of the method call
		assertEquals(100, result);
		// We also verify the behavior of the service function by how it calls its mocks
		verify(pagesDAO, times(1)).create(testInputPage);
	}

	@Test
	void convertEntityToModel_Success_ReturnsEmployeeModel() {
		// Arrange
		//no calls, no arrange.

		// Act
		Optional<Page> result = pagesService.convertEntityToModel(testPageEntity);

		// Assert
		assertTrue(result.isPresent());
		Page page = result.get();
		assertEquals(testPageEntity.getContent(), page.getContent());
		assertEquals(testPageEntity.getTitle(), page.getTitle());
		assertEquals(testPageEntity.getId(), page.getId());
	}

	@Test
	void createEntity_ThrowsSQLException_ReturnsNegativeOne() throws SQLException{
		// Arrange
		when(pagesDAO.create(testInputPage)).thenThrow(new SQLException("Database error"));

		// Act
		Integer result = pagesService.createEntity(testInputPage);

		// Assert
		assertEquals(-1, result);
		verify(pagesDAO, times(1)).create(testInputPage);
	}


}