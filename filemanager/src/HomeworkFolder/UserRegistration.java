package HomeworkFolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/HomeworkFolder/UserRegistration.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (!username.isEmpty() && !password.isEmpty()) {

			ArrayList<Userdetails> userdetails = userdetails();

			String message = "";
			String res = "";

			for (int i = 0; i <= userdetails.size() - 1; i++) {
				if (userdetails.get(i).getName().equals(username.toLowerCase().trim())) {
					// response.sendRedirect("UserRegistration");

					message = "Username already exists. Please select another uername";
					res = "0";
					break;
				}

				if (i == userdetails.size() - 1) {
					add_user(username, password);
					message = "Username has been added";
					res = "1";
				}
			}

			String json = "{" + "\"message\":" + "\"" + message + "\"" + "," + "\"res\":" + "\"" + res + "\"" + "}";

			System.out.println(json);

			response.setContentType("application/json");
			response.getWriter().print(json);

		}

		else {
			response.sendRedirect("UserRegistration");
		}

	}

	public ArrayList<Userdetails> userdetails() {

		ArrayList<Userdetails> details = new ArrayList<Userdetails>();

		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "";
			String db_password = "";

			Connection c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("select * from homework_users");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				details.add(new Userdetails(rs.getInt("id"), rs.getString("password"), rs.getString("name")));
			}

			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return details;

	}

	public void add_user(String name, String password) {

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "cs3220stu52";
			String db_password = "#k**ODW!";

			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("Insert into homework_users(name,password) VALUES (?,?)");

			stmt.setString(1, name);
			stmt.setString(2, password);
			stmt.executeUpdate();
			System.out.println("I am here again");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
