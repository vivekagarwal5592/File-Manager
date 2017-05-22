package HomeworkFolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

@WebServlet("/DisplayFolder")
public class DisplayFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		// Date d = new Date();
		//
		// int id = 0;
		// Folder root = new Folder(id++, "root", new ArrayList<Folder>(), "",
		// 0, d, true);
		// Folder f1 = new Folder(id++, "MyFiles", new ArrayList<Folder>(), "",
		// 0, d, true);
		// Folder f2 = new Folder(id++, "Documents", new ArrayList<Folder>(),
		// "", 0, d, true);
		// Folder f3 = new Folder(id++, "Temp", new ArrayList<Folder>(), "", 0,
		// d, true);
		// Folder f4 = new Folder(id++, "FolderA", new ArrayList<Folder>(), "",
		// 0, d, true);
		//
		// root.getChild().add(f1);
		// root.getChild().add(f2);
		// root.getChild().add(f3);
		// f1.getChild().add(f4);
		// ServletContext context = this.getServletContext();
		// context.setAttribute("root", root);
		//
		// // 'id' is being used to assign new id to every new folder created.
		// // Since it needs to be unique for every folder, it is being
		// incremented
		// // and stored in the application scope for every folder created
		// context.setAttribute("id", id);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("username") != null
				&& request.getSession().getAttribute("password") != null) {

			Integer current_folder = null;

			if ((request.getParameter("current_folder") != null) && request.getParameter("current_folder") != "") {
				current_folder = Integer.parseInt(request.getParameter("current_folder"));
			}

			ArrayList<Folder> folder = display_folder(request, current_folder);
			Folder current_folder_details = parent_folder(request, current_folder);

			request.setAttribute("folder", folder);
			request.setAttribute("current_folder_details", current_folder_details);
			request.setAttribute("current_folder", current_folder);

			request.getRequestDispatcher("WEB-INF/HomeworkFolder/Display.jsp").forward(request, response);

		} else {
			response.sendRedirect("DisplayLoginFolder");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int current_folder = Integer.parseInt(request.getParameter("current_folder"));
		request.setAttribute("current_folder", current_folder);
		response.sendRedirect("DisplayFolder");
		return;
	}

	public Folder traverse(Folder node, int val) {

		// Folder search_node = null;
		// if (node.getId() == val) {
		// search_node = node;
		// return search_node;
		// }
		//
		// else {
		// for (int i = 0; i <= node.getChild().size() - 1; i++) {
		// if (node.getChild().get(i).getId() == val) {
		// search_node = node.getChild().get(i);
		// return search_node;
		// } else {
		// search_node = traverse(node.getChild().get(i), val);
		// if (search_node != null) {
		// return search_node;
		// } else {
		// continue;
		// }
		// }
		// }
		// }
		// return search_node;
		return null;
	}

	public Folder back(Folder node, int val) {

		// Folder search_node = null;
		// for (int i = 0; i <= node.getChild().size() - 1; i++) {
		// if (node.getChild().get(i).getId() == val) {
		//
		// search_node = node;
		// return search_node;
		// } else {
		// search_node = back(node.getChild().get(i), val);
		// if (search_node != null) {
		// return search_node;
		// } else {
		// continue;
		// }
		// }
		// }
		// return search_node;

		return null;
	}

	public String get_size(long size) {

		String size_unit = "B";

		if (size > 1023) {
			size /= 1024;
			size_unit = "KB";
		}

		return String.valueOf(size) + size_unit;

	}

	public ArrayList<Folder> display_folder(HttpServletRequest request, Integer current_folder) {

		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
		String db_username = "cs3220stu52";
		String db_password = "#k**ODW!";

		int user_id = (int) request.getSession().getAttribute("id");

		ArrayList<Folder> folder = new ArrayList<Folder>();
		Connection c = null;
		try {
			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("select * from homework_folder a left join homework_users b on "
					+ "a.user_id = b.id where a.parent_id =? and b.id=?");

			PreparedStatement stmt2 = c
					.prepareStatement("select * from homework_folder a left join homework_users b on "
							+ "a.user_id = b.id where a.parent_id is null and b.id=?");

			ResultSet rs = null;

			if (current_folder != null) {
				stmt.setInt(1, current_folder);
				stmt.setInt(2, user_id);
				rs = stmt.executeQuery();

				while (rs.next()) {
					folder.add(new Folder(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
							rs.getInt("size"), rs.getDate("creationdate"), rs.getBoolean("isfolder"),
							rs.getInt("parent_id"), rs.getInt("user_id")));
				}

			} else {
				stmt2.setInt(1, user_id);
				rs = stmt2.executeQuery();
				while (rs.next()) {
					folder.add(new Folder(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
							rs.getInt("size"), rs.getDate("creationdate"), rs.getBoolean("isfolder"),
							rs.getInt("parent_id"), rs.getInt("user_id")));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return folder;
	}

	public Folder parent_folder(HttpServletRequest request, Integer current_folder) {

		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
		String db_username = "cs3220stu52";
		String db_password = "#k**ODW!";
		Integer parent_folder = null;
		// int user_id = (int) request.getSession().getAttribute("id");

		Folder folder = null;
		Connection c = null;
		try {
			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("select * from homework_folder where id =?");

			ResultSet rs = null;

			if (current_folder != null) {
				stmt.setInt(1, current_folder);
				rs = stmt.executeQuery();

				while (rs.next()) {
					parent_folder = rs.getInt("parent_id");

					if (rs.wasNull()) {
						parent_folder = null;
					}

					System.out.println(parent_folder);

					folder = new Folder(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("size"),
							rs.getDate("creationdate"), rs.getBoolean("isfolder"), parent_folder, rs.getInt("user_id"));
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return folder;

	}

}
