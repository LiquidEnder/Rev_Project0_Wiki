package org.example.repository.DAO;

import org.example.repository.entities.PagesEntity;
import org.example.util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PagesDAO implements DAOInterface<PagesEntity>{
	private Connection connection = ConnectionHandler.getConnection();

	@Override
	public Integer create(PagesEntity entity) throws SQLException {

		String sql = "INSERT INTO pages (page_title, page_content) VALUES (?, ?) RETURNING page_id";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setString(1, entity.getTitle());
			stmt.setString(2, entity.getContent());

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					return rs.getInt("page_id");
				}
			}
		}
		return 0;
	}

	@Override
	public Optional<PagesEntity> findById(Integer id) throws SQLException {
		String sql = "SELECT * FROM pages WHERE page_id = ?";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, id);

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					PagesEntity page = new PagesEntity();
					page.setId(rs.getInt("page_id"));
					page.setContent(rs.getString("page_content"));
					page.setTitle(rs.getString("page_title"));

					return Optional.of(page);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public List<PagesEntity> findAll() throws SQLException {
		List<PagesEntity> pages = new ArrayList<>();

		String sql = "SELECT * FROM pages";
		try(Statement stmt = connection.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				PagesEntity page = new PagesEntity();
				page.setId(rs.getInt("page_id"));
				page.setTitle(rs.getString("page_title"));
				page.setContent(rs.getString("page_content"));

				pages.add(page);
			}
		}
		return pages;
	}

	@Override
	public PagesEntity updateById(PagesEntity entity) throws SQLException{
		String sql = "UPDATE pages SET page_content = ? where page_id = ? RETURNING *;";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, entity.getContent());
			stmt.setInt(2, entity.getId());

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					return (entity);
				}
			}
		}

		return null;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		return false;
	}

	public Optional<PagesEntity> findByTitle(String title) throws SQLException{
		String sql = "SELECT * FROM pages WHERE page_title = ?";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, title);

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					PagesEntity pageEntity = new PagesEntity();
					pageEntity.setId(rs.getInt("page_id"));
					pageEntity.setContent(rs.getString("page_content"));
					pageEntity.setTitle(rs.getString("page_title"));

					return Optional.of(pageEntity);
				}
			}
		}
		return Optional.empty();
	}
}
