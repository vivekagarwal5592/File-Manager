package HomeworkFolder;

import java.io.File;
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

@WebServlet("/DeleteFolder")
public class DeleteFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Folder root = (Folder) request.getSession().getAttribute("root");

		int currentfolder = (Integer.parseInt(request.getParameter("current_folder")));

		Folder parent_id = parent_folder(request, currentfolder);

		delete_folder(currentfolder);
		// Folder deleted = traverse(root, currentfolder);
		if (parent_id.getParent_id() != null) {
			response.sendRedirect("DisplayFolder?current_folder=" + parent_id.getParent_id());
		} else {
			response.sendRedirect("DisplayFolder");
		}

		return;
	}

	public Folder traverse(Folder node, int val) {

		// Folder deleted = null;
		// for (int i = 0; i <= node.getChild().size() - 1; i++) {
		// if (node.getChild().get(i).getId() == val) {
		//
		// if (node.getChild().get(i).isFolder) {
		// node.getChild().remove(i);
		// } else {
		// delete_file(node.getChild().get(i));
		// node.getChild().remove(i);
		// }
		//
		// // System.out.println("return value of val ");
		// return node;
		// } else {
		// deleted = traverse(node.getChild().get(i), val);
		//
		// if (deleted != null) {
		// return deleted;
		// } else {
		// continue;
		// }
		//
		// }

		// }

		// System.out.println("I am returning null from traverse function");
		// return deleted;
		return null;
	}

	public void delete_file(String name) {

		String path = getServletContext().getRealPath("/WEB-INF/files/" + name);
		System.out.println(path);
		File file = new File(path);

		file.delete();

	}

	public void delete_folder(int current_folder) {

		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "cs3220stu52";
			String db_password = "#k**ODW!";
			Integer parent_id = null;
			Connection c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("select * from homework_folder where id=?");
			PreparedStatement stmt2 = c.prepareStatement("select * from homework_folder where parent_id=?");

			stmt.setInt(1, current_folder);
			stmt2.setInt(1, current_folder);

			ResultSet rs = stmt.executeQuery();
			ResultSet rs2 = stmt2.executeQuery();

			while (rs2.next()) {
				delete_folder(rs2.getInt("id"));
			}

			while (rs.next()) {

				int isFolder = rs.getInt("isfolder");

				if (isFolder == 0) {
					delete_file(rs.getString("name"));
				}

			}

			stmt = c.prepareStatement("delete from homework_folder where id=?");

			stmt.setInt(1, current_folder);

			stmt.executeUpdate();

			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

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
