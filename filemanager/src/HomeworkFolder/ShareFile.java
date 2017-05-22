package HomeworkFolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShareFile")
public class ShareFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		String res = "0";
		String folder_id = request.getParameter("folder_id");
		String user_name = request.getParameter("user_name");

		Userdetails user = get_user_details(user_name);

		if (user == null) {
			message = "User does not exist";
			res = "0";
		}

		else if (user.getId() == (int) request.getSession().getAttribute("id")) {
			message = "You cannot share file with yourself";
			res = "0";
		} else {
			share_file(folder_id, user);
			message = "File has been shared with " + user.getName();
			res = "1";
		}

		String json = "{" + "\"message\":" + "\"" + message + "\"" + "," + "\"res\":" + "\"" + res + "\"" + "}";

	//	System.out.println(json);

		response.setContentType("application/json");
		response.getWriter().print(json);

		// request.getRequestDispatcher("DisplayLoginFolder").forward(request,
		// response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public Userdetails get_user_details(String username) {

		Userdetails user = null;
		Connection c = null;
		try {

			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "cs3220stu52";
			String db_password = "#k**ODW!";

			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("select * from homework_users where name=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				String shared_folder_id = rs.getString("shared_folder_id");

				if (rs.wasNull()) {
					shared_folder_id = "";
				}

				user = new Userdetails(rs.getInt("id"), rs.getString("password"),rs.getString("name"),
						shared_folder_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public void share_file(String folder_id, Userdetails userdetails) {

		Connection c = null;
		try {

			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "cs3220stu52";
			String db_password = "#k**ODW!";
			c = DriverManager.getConnection(url, db_username, db_password);

			String shared_folder_id = "";
			if (userdetails.getShared_folder_id() != "") {
				shared_folder_id = userdetails.getShared_folder_id() + "," + folder_id;
			} else {
				shared_folder_id = folder_id;
			}

			PreparedStatement stmt = c.prepareStatement("update homework_users set shared_folder_id =? where id=?");
			stmt.setString(1, shared_folder_id);
			stmt.setInt(2, userdetails.getId());
			stmt.executeUpdate();
		} catch (Exception e) {

		}
	}

}
