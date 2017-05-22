package HomeworkFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditFolder")
public class EditFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditFolder() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String current_folder = request.getParameter("current_folder");
		Folder folder = null;
		try {
			folder = folder_details(Integer.parseInt(current_folder));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("folder", folder);
		request.setAttribute("current_folder", current_folder);
		request.getRequestDispatcher("WEB-INF/HomeworkFolder/EditFolder.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int current_folder_id = (Integer.valueOf(request.getParameter("current_folder")));
		String current_folder_name = (request.getParameter("folder_name"));

		Integer parent_id = null;
		try {
			parent_id = edit_folder(current_folder_name, current_folder_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (parent_id != null) {
			response.sendRedirect("DisplayFolder?current_folder=" + parent_id);
		} else {
			response.sendRedirect("DisplayFolder");
		}

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

	public Folder parent_traverse(Folder node, int current_folder) {

		// Folder search_node = null;
		//
		// for (int i = 0; i <= node.getChild().size() - 1; i++) {
		//
		// if (node.getChild().get(i).getId() == current_folder) {
		// search_node = node;
		// return search_node;
		// } else {
		// search_node = parent_traverse(node.getChild().get(i),
		// current_folder);
		// if (search_node != null) {
		// return search_node;
		// } else {
		// continue;
		// }
		//
		// }
		//
		// // search_node = parent_traverse(node.getChild().get(i),
		// // current_folder);
		// // if (search_node != null) {
		// // return search_node;
		// // } else {
		// // continue;
		// // }
		//
		// // }
		// }
		//
		// return search_node;

		return null;

	}

	public boolean rename_file(String old_name, String new_name) {

		String path = getServletContext().getRealPath("/WEB-INF/files/" + old_name);
		String new_path = getServletContext().getRealPath("/WEB-INF/files/" + new_name);

		File file = new File(path);
		File new_file_name = new File(new_path);
		return file.renameTo(new_file_name);

	}

	public Integer edit_folder(String new_file_name, int id) throws SQLException {

		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
		String db_username = "cs3220stu52";
		String db_password = "#k**ODW!";
		Connection c = DriverManager.getConnection(url, db_username, db_password);

		Integer parent_id = null;
		PreparedStatement stmt = c.prepareStatement("Update homework_folder set name=? where id=?");
		PreparedStatement stmt2 = c.prepareStatement("Select * from homework_folder where id=?");

		stmt2.setInt(1, id);

		ResultSet rs = stmt2.executeQuery();

		while (rs.next()) {
			parent_id = rs.getInt("parent_id");
			if (rs.wasNull()) {
				parent_id = null;
			}

			String current_folder = rs.getString("name");

			int isfolder = rs.getInt("isfolder");

			if (isfolder == 0) {
				rename_file(current_folder, new_file_name);
			}
		}

		stmt.setString(1, new_file_name);
		stmt.setInt(2, id);
		stmt.executeUpdate();
		c.close();
		return parent_id;
	}

	public Folder folder_details(int id) {

		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
		String db_username = "cs3220stu52";
		String db_password = "#k**ODW!";
		Connection c = null;
		Folder folder = null;

		try {
			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt2 = c.prepareStatement("Select * from homework_folder where id=?");

			stmt2.setInt(1, id);

			ResultSet rs = stmt2.executeQuery();

			while (rs.next()) {

				boolean isfolder = false;

				if (rs.getInt("isfolder") == 0) {
					isfolder = true;
				}

				Integer parent_id = rs.getInt("parent_id");
				if (rs.wasNull()) {
					parent_id = null;
				}

				folder = new Folder(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getLong("size"),
						rs.getDate("creationdate"), isfolder, parent_id, rs.getInt("user_id"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
