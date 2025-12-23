package org.example.repository.DAO;


import org.example.repository.entities.EditsEntity;
import org.example.util.ConnectionHandler;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditsDAO implements DAOInterface<EditsEntity>{
	private Connection connection = ConnectionHandler.getConnection();

	@Override
	public Integer create(EditsEntity entity) throws SQLException {
		String sql = "INSERT INTO edits (page_id, account_id, page_content) VALUES (?, ?, ?) RETURNING id";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setInt(1, entity.getPageID());
			stmt.setInt(2, entity.getAccountID());
			stmt.setString(3, entity.getContent());

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					return rs.getInt("id");
				}
			}
		}
		return null;    }

	@Override
	public Optional<EditsEntity> findById(Integer id) throws SQLException {
		String sql = "SELECT * FROM edits WHERE id = ?";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, id);

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					EditsEntity edit = new EditsEntity();
					edit.setId(rs.getInt("id"));
					edit.setPageID(rs.getInt("page_id"));
					edit.setAccountID(rs.getInt("account_id"));
					edit.setContent(rs.getString("page_content"));
					edit.setTimestamp(rs.getTimestamp("edited_When").toInstant());

					return Optional.of(edit);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public List<EditsEntity> findAll() throws SQLException {
		List<EditsEntity> edits = new ArrayList<>();

		String sql = "SELECT * FROM edits";
		try(Statement stmt = connection.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				EditsEntity edit = new EditsEntity();
				edit.setId(rs.getInt("id"));
				edit.setPageID(rs.getInt("page_id"));
				edit.setAccountID(rs.getInt("account_id"));
				edit.setContent(rs.getString("page_content"));
				edit.setTimestamp(rs.getTimestamp("edited_When").toInstant());

				edits.add(edit);
			}
		}
		return edits;
	}

	@Override
	public EditsEntity updateById(EditsEntity entity) throws SQLException {
		return null;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		return false;
	}

	public List<EditsEntity> findAllByPageId(Integer pageId) throws SQLException {
		List<EditsEntity> edits = new ArrayList<>();

		String sql = "SELECT * FROM edits WHERE page_id = ?";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, pageId);

			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				EditsEntity edit = new EditsEntity();
				edit.setId(rs.getInt("id"));
				edit.setPageID(rs.getInt("page_id"));
				edit.setAccountID(rs.getInt("account_id"));
				edit.setContent(rs.getString("page_content"));
				edit.setTimestamp(rs.getTimestamp("edited_When").toInstant());

				edits.add(edit);
			}
		}
		return edits;
	}

	public List<EditsEntity> findAllByAccountId(Integer accountId) throws SQLException {
		List<EditsEntity> edits = new ArrayList<>();

		String sql = "SELECT * FROM edits WHERE account_id = ?";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, accountId);

			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				EditsEntity edit = new EditsEntity();
				edit.setId(rs.getInt("id"));
				edit.setPageID(rs.getInt("page_id"));
				edit.setAccountID(rs.getInt("account_id"));
				edit.setContent(rs.getString("page_content"));
				edit.setTimestamp(rs.getTimestamp("edited_When").toInstant());

				edits.add(edit);
			}
		}
		return edits;
	}

	public List<EditsEntity> findAllByUsername(String username) throws SQLException {
		List<EditsEntity> edits = new ArrayList<>();

		String sql = "SELECT *\n" +
				"    FROM edits\n" +
				"    INNER JOIN accounts\n" +
				"        ON edits.account_id = accounts.account_id\n" +
				"    WHERE accounts.username = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				EditsEntity edit = new EditsEntity();
				edit.setId(rs.getInt("id"));
				edit.setPageID(rs.getInt("page_id"));
				edit.setAccountID(rs.getInt("account_id"));
				edit.setContent(rs.getString("page_content"));
				edit.setTimestamp(rs.getTimestamp("edited_When").toInstant());

				edits.add(edit);
			}
		}
		return edits;
	}

	public List<EditsEntity> findAllByTitle(String title) throws SQLException {
		List<EditsEntity> edits = new ArrayList<>();

		String sql = "SELECT *\n" +
				"    FROM edits\n" +
				"    INNER JOIN pages\n" +
				"        ON edits.page_id = pages.page_id\n" +
				"    WHERE pages.page_title = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, title);

			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				EditsEntity edit = new EditsEntity();
				edit.setId(rs.getInt("id"));
				edit.setPageID(rs.getInt("page_id"));
				edit.setAccountID(rs.getInt("account_id"));
				edit.setContent(rs.getString("page_content"));
				edit.setTimestamp(rs.getTimestamp("edited_When").toInstant());

				edits.add(edit);
			}
		}
		return edits;
	}
}
