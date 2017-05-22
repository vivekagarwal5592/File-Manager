package HomeworkFolder;

import java.io.IOException;
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

@WebServlet("/DisplaySharedFile")
public class DisplaySharedFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DisplaySharedFile() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer current_folder = null;

		if ((request.getParameter("current_folder") != null) && request.getParameter("current_folder") != "") {
			current_folder = Integer.parseInt(request.getParameter("current_folder"));
		}

		Folder current_folder_details = parent_folder(request, current_folder);

		String shared_folder_id = (String) request.getSession().getAttribute("shared_folder_id");
		String[] shared_folder_ids;
		ArrayList<Folder> folders = null;
		
		if (shared_folder_id != null) {
			shared_folder_ids = shared_folder_id.split(",");
			folders = get_folders(shared_folder_ids);
			request.setAttribute("folder", folders);
		}
		request.setAttribute("current_folder", current_folder);
		request.setAttribute("current_folder_details", current_folder_details);

		request.getRequestDispatcher("WEB-INF/HomeworkFolder/DisplaySharedFile.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public ArrayList<Folder> get_folders(String[] folder_id) {

		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
		String db_username = "cs3220stu52";
		String db_password = "#k**ODW!";

		ArrayList<Folder> folders = new ArrayList<Folder>();
		Connection c = null;

		try {
			String question_mark = "";

			for (int i = 0; i <= folder_id.length - 1; i++) {

				if (i != folder_id.length - 1) {
					question_mark += "?,";
				} else {
					question_mark += "?";
				}

			}

			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement preparestatement = c
					.prepareStatement("select * from homework_folder where id in (" + question_mark + ")");

			for (int i = 0; i <= folder_id.length - 1; i++) {
				preparestatement.setString(i + 1, folder_id[i]);
			}

			ResultSet rs = preparestatement.executeQuery();

			while (rs.next()) {
				folders.add(new Folder(rs.getInt("id"), rs.getString("name"), rs.getLong("size"),
						rs.getDate("creationdate")));
			}

			System.out.println(folders.size());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return folders;

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
