package l.hu.v1wac.firstapp.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserPostgresDaoImpl extends PostgresBaseDao implements UserDao {

	@Override
	public String findRoleForUser(String name, String pass) {
		String rol = "";
		try (Connection connection = super.getConnection()){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from useraccount");
			while (result.next()) {
				if (name.equals(result.getString("username")) && pass.equals(result.getString("password"))) {
					rol = result.getString("role");
					return rol;
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return null;
	}

}
