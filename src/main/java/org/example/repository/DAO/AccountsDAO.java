package org.example.repository.DAO;

import org.example.repository.entities.AccountsEntity;
import org.example.util.ConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountsDAO implements DAOInterface<AccountsEntity>{
	private Connection connection = ConnectionHandler.getConnection();

	@Override
	public Integer create(AccountsEntity entity) throws SQLException {
		String sql = "INSERT INTO accounts (username, user_password) VALUES (?, ?) RETURNING account_id";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){

			stmt.setString(1, entity.getUsername());
			stmt.setString(2, entity.getPassword());

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					return rs.getInt("account_id");
				}
			}
		}
		return 0;
	}

	@Override
	public Optional<AccountsEntity> findById(Integer id) throws SQLException {
		String sql = "SELECT * FROM accounts WHERE account_id = ?";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, id);

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					AccountsEntity account = new AccountsEntity();
					account.setId(rs.getInt("account_id"));
					account.setPassword(rs.getString("user_password"));
					account.setUsername(rs.getString("username"));

					return Optional.of(account);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public List<AccountsEntity> findAll() throws SQLException {
		List<AccountsEntity> accounts = new ArrayList<>();

		String sql = "SELECT * FROM accounts";
		try(Statement stmt = connection.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				AccountsEntity account = new AccountsEntity();
				account.setId(rs.getInt("account_id"));
				account.setPassword(rs.getString("user_password"));
				account.setUsername(rs.getString("username"));

				accounts.add(account);
			}
		}
		return accounts;
	}

	@Override
	public AccountsEntity updateById(AccountsEntity entity) throws SQLException {
		return null;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		return false;
	}

	public Optional<AccountsEntity> findByUsername(String username) throws SQLException{
		String sql = "SELECT * FROM accounts WHERE username = ?";

		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, username);

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					AccountsEntity accountEntity = new AccountsEntity();
					accountEntity.setId(rs.getInt("account_id"));
					accountEntity.setUsername(rs.getString("username"));
					accountEntity.setPassword(rs.getString("user_password"));

					return Optional.of(accountEntity);
				}
			}
		}
		return Optional.empty();
	}
}
