package dataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class userAccess {
	private static Connection con = Conn.getConnection();

	public void showUsers() {
		try {
			String query = "Select * from users";
			Statement statement = getCon().createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + " "
						+ result.getString(4));
			}

		} catch (Exception e) {

			System.out.println("Conection failed!");
		}
	}
	public ArrayList<String> showWaiting() throws SQLException
	{

		String query="Select * from waitinglist";
		Statement statement=con.createStatement();
		ResultSet result=statement.executeQuery(query);
		ArrayList<String> waitList=new ArrayList<String>();	
			while(result.next()) {
					String unu = result.getString(1)+" "+result.getString(2);
					waitList.add(unu);
			}
			return waitList;
	}
	public void returnBook(String email, String title) {
		try {
			java.sql.CallableStatement myStmt = userAccess.getCon().prepareCall("{call add_return(?,?)}");
			myStmt.setString(1, email);
			myStmt.setString(2, title);	
			myStmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	public void addPayment(String email, String method, String payed) {
		try {
			java.sql.CallableStatement myStmt = userAccess.getCon().prepareCall("{call add_payment(?,?,?)}");

			myStmt.setString(1, email);
			myStmt.setString(2, method);
			myStmt.setString(3, payed);
			myStmt.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void addUser(String firstName, String surname, String mail, String password) {
		try {
			java.sql.CallableStatement myStmt = userAccess.getCon().prepareCall("{call add_user(?,?,?,?)}");
			myStmt.setString(1, firstName);
			myStmt.setString(2, surname);
			myStmt.setString(3, mail);
			myStmt.setString(4, password);
			myStmt.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public int login(String mail, String pass) {
		try {

			String query = "Select * from users where email='" + mail + "'and parola = md5('" + pass + "');";
			String query3 = "Select * from users where email='" + mail + "'";
			String query1 = "Select * from users where parola= md5('" + pass + "');";
			String query2 = "Select * from users where parola= md5('";
			Statement statement = getCon().createStatement();
			ResultSet result = statement.executeQuery(query);
			ResultSet result1 = statement.executeQuery(query3);
			int startPos = query2.length();
			int length = query1.indexOf("');");
			String sub = query1.substring(startPos, length);
			result1.next();
			if (mail.equals(result1.getString(3))) {
				if (pass.equals(sub))

				{
					if (result1.getString(5).equals(null) || result1.getString(5).equals(""))
						return 1;
					else if (result1.getString(5).equals("staff"))
						return 2;
				}
				JOptionPane.showMessageDialog(null, "Incorect username or password");
			}
			JOptionPane.showMessageDialog(null, "Incorect username or password");

		} catch (Exception e) {

			System.out.println(e);
		}
		return 0;
	}

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		userAccess.con = con;
	}

}
