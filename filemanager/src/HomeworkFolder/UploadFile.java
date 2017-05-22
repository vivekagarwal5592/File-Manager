package HomeworkFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String current_folder = request.getParameter("current_folder");

		// System.out.println("I am in doget" + current_folder);

		request.setAttribute("current_folder", current_folder);
		request.getRequestDispatcher("WEB-INF/HomeworkFolder/UploadFile.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer current_folder = null;
		File file = null;
		// System.exit(0);
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Count how many files are uploaded
		// The directory we want to save the uploaded files to.
		String fileDir = getServletContext().getRealPath("/WEB-INF/files");

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				// If the item is not a form field - meaning it's an uploaded
				// file, we save it to the target dir
				if (!item.isFormField()) {
					String fileName = (new File(item.getName())).getName();
					file = new File(fileDir, fileName);
					item.write(file);

				} else {
					if ("current_folder".equals(item.getFieldName()) && !item.getString().isEmpty()) {
						current_folder = (Integer.parseInt(item.getString()));
					}
				}
			}

			// getParentFolder(file, current_folder, request);
			make_file_object(file, current_folder, request);

		} catch (Exception e) {
			throw new IOException(e);
		}

		response.setContentType("text/html");

		System.out.println(" file(s) uploaded to " + fileDir);

		if (current_folder != null) {
			response.sendRedirect("DisplayFolder?current_folder=" + current_folder + "");
		} else {
			response.sendRedirect("DisplayFolder");
		}

	}

	public void getParentFolder(File file, int current_folder, HttpServletRequest request) {

		// Folder root = (Folder) request.getSession().getAttribute("root");
		// int id = (int) this.getServletContext().getAttribute("id");
		//
		// Date d = new Date();
		//
		// if (current_folder == 0) {
		// root.getChild().add(new Folder(id++, file.getName(), new
		// ArrayList<Folder>(),
		// URLConnection.guessContentTypeFromName(file.getName()),
		// file.length(), d, false));
		// this.getServletContext().setAttribute("id", id);
		// // response.sendRedirect("DisplayFolder");
		// return;
		//
		// } else {
		// Folder node = traverse(root, current_folder);
		// node.getChild().add(new Folder(id++, file.getName(), new
		// ArrayList<Folder>(),
		// URLConnection.guessContentTypeFromName(file.getName()),
		// file.length(), d, false));
		// this.getServletContext().setAttribute("id", id);
		// return;
		//
		// }
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

	public void make_file_object(File file, Integer current_folder, HttpServletRequest request) {

		Connection c =null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu52";
			String db_username = "cs3220stu52";
			String db_password = "#k**ODW!";
			Integer parent_id = null;
			 c = DriverManager.getConnection(url, db_username, db_password);
			Date d = new Date();
			int user_id = (int) request.getSession().getAttribute("id");

			if (current_folder != null) {
				PreparedStatement stmt = c.prepareStatement(
						"Insert into homework_folder(name,size,creationdate,isfolder,parent_id,user_id) VALUES(?,?,?,?,?,?)");

				stmt.setString(1, file.getName());
				stmt.setInt(2, (int) file.length());
				stmt.setDate(3, new java.sql.Date(d.getTime()));
				stmt.setInt(4, 0);
				stmt.setInt(5, current_folder);
				stmt.setInt(6, user_id);
				stmt.executeUpdate();
			}

			else {
				PreparedStatement stmt2 = c.prepareStatement(
						"Insert into homework_folder(name,size,creationdate,isfolder,user_id) VALUES(?,?,?,?,?)");

				stmt2.setString(1, file.getName());
				stmt2.setInt(2, (int) file.length());
				stmt2.setDate(3, new java.sql.Date(d.getTime()));
				stmt2.setInt(4, 0);
				stmt2.setInt(5, user_id);
				stmt2.executeUpdate();
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

	}

}
