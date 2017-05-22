package HomeworkFolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

@WebServlet("/AddFolder")
public class AddFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String parent_folder = request.getParameter("current_folder");
		// System.out.println("I am here" + parent_folder);
		PrintWriter out = response.getWriter();

		request.setAttribute("current_folder", parent_folder);
		request.getRequestDispatcher("WEB-INF/HomeworkFolder/AddFolder.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("I am in the post method of add folder");
		Integer current_folder = null;

		if (!(request.getParameter("current_folder").isEmpty() || request.getParameter("current_folder") == null)) {
			current_folder = (Integer.valueOf(request.getParameter("current_folder")));
		}

		String new_folder = request.getParameter("folder_name");
		int user_id = (int) request.getSession().getAttribute("id");

		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/YYYY hh:mm a");
		Date d = new Date();

		Folder folder = new Folder(0, new_folder, "", (long) 0, d, true, current_folder, user_id);

		// System.out.println(folder.getName());
		int id = add_folder(request, folder);

		if (current_folder != null) {
			// response.sendRedirect("DisplayFolder?current_folder=" +
			// current_folder);
		} else {
			// response.sendRedirect("DisplayFolder");
		}

		String date = String.valueOf(dt.format(d));
		String json = "{" + "\"date\":" + "\"" + date + "\"" + "," + "\"id\":" + "\"" + id + "\"" + "}";

		System.out.println(json);

		response.setContentType("application/json");
		response.getWriter().print(json);

	}

	public Folder traverse(Folder node, int current_folder) {

		// Folder search_node = null;
		//
		// if (node.getId() == current_folder) {
		// search_node = node;
		// return search_node;
		// }
		//
		// else {
		// for (int i = 0; i <= node.getChild().size() - 1; i++) {
		// search_node = traverse(node.getChild().get(i), current_folder);
		// if (search_node != null) {
		// return search_node;
		// } else {
		// continue;
		// }
		//
		// }
		// }
		//
		// return search_node;
		return null;
	}

	public int add_folder(HttpServletRequest request, Folder new_folder) {
		Connection c = null;
		int id = 0;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "";
			String db_password = "";

			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement(
					"Insert into homework_folder (name,type,size,creationdate,isfolder,user_id) VALUES (?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			PreparedStatement stmt2 = c.prepareStatement(
					"Insert into homework_folder (name,type,size,creationdate,isfolder,parent_id,user_id) VALUES (?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			int isFolder = 0;
			if (new_folder.isFolder()) {
				isFolder = 1;
			}

			stmt.setString(1, new_folder.getName());
			stmt.setString(2, new_folder.getType());
			stmt.setInt(3, (int) (new_folder.getSize()));
			stmt.setDate(4, new java.sql.Date(new_folder.getDate().getTime()));
			stmt.setInt(5, isFolder);

			stmt2.setString(1, new_folder.getName());
			stmt2.setString(2, new_folder.getType());
			stmt2.setInt(3, (int) (new_folder.getSize()));
			stmt2.setDate(4, new java.sql.Date(new_folder.getDate().getTime()));
			stmt2.setInt(5, isFolder);

			
			if (new_folder.getParent_id() == null) {
				stmt.setInt(6, new_folder.getUser_id());
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
				
			}

			else {
				stmt2.setInt(6, new_folder.getParent_id());
				stmt2.setInt(7, new_folder.getUser_id());
				stmt2.executeUpdate();
				ResultSet rs = stmt2.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
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
		System.out.println("value of id" + id);
		return id;
	}

}
