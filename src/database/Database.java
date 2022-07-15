package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import models.Comment;
import models.CompoundPun;
import models.HomographicPun;
import models.HomophonicPun;
import models.Pun;
import models.RecursivePun;
import models.User;
import models.VisualPun;

public class Database {
	private ArrayList<User> users;
	private ArrayList<Pun> puns;
	private static Database database;
	
	private Database() {
		users = new ArrayList<User>();
		puns = new ArrayList<Pun>();
		fetchUserCSV();
	}
	
	public static Database getInstance() {
		if(database == null) {
			database = new Database();
		}
		
		return database;
	}
	
	public void addUser(User user) {
		users.add(user);
		writeUserCSV();
	}
	
	public User authUser(String username, String password) {
		for(User u : users) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return u;
			}
		}
		
		return null;
	}
	
	public int getProfilePicture(String username) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				return u.getProfilePicture();
			}
		}
		
		return -1;
	}
	
	public boolean usernameAvailable(String username) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Pun> getAllPun(){
		if(puns.isEmpty()) {
			fetchPunCSV();
		}
		
		return puns;
	}
	
	public void addPun(Pun pun) {
		puns.add(pun);
		writePunCSV();
	}
	
	public void writeUserCSV() {
		String res = "";
		for (User user : users) {
			res += user.getStringFormat() + "\n";
		}
		try {
			FileWriter wr = new FileWriter(new File("./data/users-asd.txt"), false);
			wr.write(res, 0, res.length());
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fetchUserCSV() {
		users.clear();
		
		try {
			Scanner scan = new Scanner(new File("./data/users.txt"));
			scan.useDelimiter("\n");
			while(scan.hasNext()) {
				String s = scan.next();
				String[] res = s.split(",");
				User u = new User(res[1], res[2].trim(), Integer.parseInt(res[0].trim()));
				
				users.add(u);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void writePunCSV() {
		String res = "";
		for (Pun pun : puns) {
			String s = pun.getType() + "," + pun.getAuthor() + "," + pun.getTitle() + "," + pun.getDate().getTime();
			
			List<Comment> comments = pun.getComments();
			for(int i = 0; i < comments.size(); i++) {
				if(i == 0) {
					s += comments.get(i).getPrintFormat();
				}
				else {
					s += ";" + comments.get(i).getPrintFormat();
				}
			}
			s += "," + pun.getUniqueStringFormat() + "\n";
			res += s;
		}
		
		try {
			FileWriter wr = new FileWriter(new File("./data/puns-asd.txt"), false);
			wr.write(res, 0, res.length());
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void fetchPunCSV() {
		puns.clear();
		
		try {
			Scanner scan = new Scanner(new File("./data/puns.txt"));
			scan.useDelimiter("\n");
			while(scan.hasNext()) {
				String s = scan.next();
				String[] res = s.split(",");
				
				Pun p = null;
				
				if(res[0].equals("Homophonic")) {
					p = new HomophonicPun(res[1], res[2], new Date(Long.parseLong(res[3])), res[5].trim());
				}
				else if(res[0].equals("Homographic")) {
					p = new HomographicPun(res[1], res[2], new Date(Long.parseLong(res[3])), res[5], res[6].trim());
				}
				else if(res[0].equals("Compound")) {
					List<String> l = new ArrayList<String>();
					for(int i = 5; i < res.length; i++) {
						l.add(res[i].trim());
					}
					
					p = new CompoundPun(res[1], res[2], new Date(Long.parseLong(res[3])), l);
				}
				else if(res[0].equals("Recursive")) {
					p = new RecursivePun(res[1], res[2], new Date(Long.parseLong(res[3])), res[5], res[6].trim());
				}
				else if(res[0].equals("Visual")) {
					p = new VisualPun(res[1], res[2], new Date(Long.parseLong(res[3])), res[5].trim());
				}
				
				String c = res[4];
				List<String> commentsStr = Arrays.asList(c.split(";"));
				List<Comment> comments = new ArrayList<Comment>();
				
				for (String string : commentsStr) {
					if(string != "") {
						String[] cc = string.split("\\^");
						if(cc.length == 3) {
							comments.add(new Comment(cc[1], cc[0], new Date(Long.parseLong(cc[2]))));
						}
					}
				}
				p.setComments(comments);
				
				puns.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		/*
		 * 
			Homophonic,Jho123Star,First Pun,1657808194021,Punny indeed^KodokSalto^1657808194021;hahaha^Acexoul^1657808194021,My very punny pun
			Homographic,KodokSalto,Punny Pun,1657808194021,,Pun Tech,Tech
			Compound,Acexoul,Wu Shang,1657808194021,,Pun 1,Pun 2,Pun 3
			Recursive,KambingGanteng,I Love Kambing,1657808194021,,PunKambing,Kambing
			Visual,Jho123Star,Best Pun,1657808194021,,pun1.jpg
		 * 
		 */
	}
	
}
