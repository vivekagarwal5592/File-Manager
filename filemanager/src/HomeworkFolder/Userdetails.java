package HomeworkFolder;

public class Userdetails {

	int id;
	String name;
	String password;
	Folder folder;
	String shared_folder_id;
	
	
	public Userdetails(int id, String password, String name) {
		super();
		this.id =id;
		this.password = password;
		this.name = name;
		
	}
	
	
	

	public Userdetails(int id, String password,String name, String shared_folder_id) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.shared_folder_id = shared_folder_id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getShared_folder_id() {
		return shared_folder_id;
	}



	public void setShared_folder_id(String shared_folder_id) {
		this.shared_folder_id = shared_folder_id;
	}
	
	
	
	
	
	
	
	
	
}
