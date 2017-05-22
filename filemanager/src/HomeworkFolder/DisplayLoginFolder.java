package HomeworkFolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

@WebServlet("/DisplayLoginFolder")
public class DisplayLoginFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		// int id = 0;
		//
		// ArrayList<Userdetails> userdetails = new ArrayList<Userdetails>();
		//
		// Date d = new Date();
		// Folder root = new Folder(id++, "root", new ArrayList<Folder>(), "",
		// 0, d, true);
		// userdetails.add(new Userdetails("cysun", "abcd", root));

		// this.getServletContext().setAttribute("userdetails", userdetails);
		//
		// this.getServletContext().setAttribute("id", id);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("username") != null
				&& request.getSession().getAttribute("password") != null) {

			response.sendRedirect("DisplayFolder");
			return;

		}

		else {
			request.getRequestDispatcher("WEB-INF/HomeworkFolder/DisplayLoginFolder.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int id = 0;

		ArrayList<Userdetails> userdetails = new ArrayList<Userdetails>();
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "cs3220stu52";
			String db_password = "#k**ODW!";

			c = DriverManager.getConnection(url, db_username, db_password);
			Statement stmt = (Statement) c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from homework_users");

			while (rs.next()) {
				userdetails.add(new Userdetails(rs.getInt("id"), rs.getString("password"), rs.getString("name"),
						rs.getString("shared_folder_id")));

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

		// ArrayList<Userdetails> userdetails = (ArrayList<Userdetails>)
		// this.getServletContext()
		// .getAttribute("userdetails");

		for (int i = 0; i <= userdetails.size() - 1; i++) {
			if (userdetails.get(i).getName().equals(username) && userdetails.get(i).getPassword().equals(password)) {

				request.getSession().setAttribute("username", userdetails.get(i).getName());
				request.getSession().setAttribute("password", userdetails.get(i).getPassword());
				request.getSession().setAttribute("id", userdetails.get(i).getId());
				request.getSession().setAttribute("shared_folder_id", userdetails.get(i).getShared_folder_id());
				// request.getSession().setAttribute("root",
				// userdetails.get(i).getFolder());

				response.sendRedirect("DisplayFolder");

				return;
			}

			if (i == userdetails.size() - 1) {
				response.sendRedirect("DisplayLoginFolder");
			}
		}

	}

}
