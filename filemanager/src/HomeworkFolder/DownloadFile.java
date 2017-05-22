package HomeworkFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int current_folder = (Integer.parseInt(request.getParameter("current_folder")));
		// Folder root = (Folder) request.getSession().getAttribute("root");
		//
		// Folder node = traverse(root, current_folder);
		//
		// if (node.isFolder()) {
		// response.sendRedirect("DisplayFolder?current_folder=" + node.getId()
		// + "");
		// }
		//
		// else {

		String name = download_file(current_folder);
		download_file(name, response);

		// System.out.println("I am in download file");
		// response.sendRedirect("DisplayFolder?current_folder="+parent.getId()+"");
		// return;
		// }

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		//
		// search_node = traverse(node.getChild().get(i), current_folder);
		//
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

	public void download_file(String name, HttpServletResponse response) {

		String path = getServletContext().getRealPath("/WEB-INF/files/" + name);
		File file = new File(path);

		// Set the response headers. File.length() returns the size of the file
		// as a long, which we need to convert to a String.
		response.setContentType("image/jpg");
		response.setHeader("Content-Length", "" + file.length());
		response.setHeader("Content-Disposition", "attachment; filename=" + name);

		// Binary files need to read/written in bytes.
		try {
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			byte buffer[] = new byte[2048];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) > 0)
				out.write(buffer, 0, bytesRead);
			in.close();
		} catch (Exception e) {

		}

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
		// return search_node;
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

	public String download_file(Integer id) {

		String name = "";
		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "";
			String db_password = "";

			c = DriverManager.getConnection(url, db_username, db_password);

			PreparedStatement stmt = c.prepareStatement("Select * from homework_folder where id=?");

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
			}

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

		return name;

	}

}
