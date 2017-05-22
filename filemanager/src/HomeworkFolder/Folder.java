package HomeworkFolder;

import java.util.ArrayList;
import java.util.Date;

public class Folder {

	private int id;
	private String name;
	String type; // content type of the uploaded file
	long size;
	Date date;
	boolean isFolder;
	Integer parent_id;
	int user_id;
	
	
	public Folder(int id, String name, String type, long size, Date date, boolean isFolder, Integer parent_id,
			int user_id) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.date = date;
		this.isFolder = isFolder;
		this.parent_id = parent_id;
		this.user_id = user_id;
	}
	
	

	public Folder(int id, String name, long size, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.date = date;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	

	
}
