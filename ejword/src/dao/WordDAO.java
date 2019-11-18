package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Word;

public class WordDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	private void connect() throws NamingException, SQLException {
			Context context=new InitialContext();
			DataSource ds=(DataSource)context.lookup("java:comp/env/ejword");
			this.db=ds.getConnection();
	}
	private void disconnect() throws SQLException {
		if(rs !=null) {
			rs.close();
		}
		if(ps !=null) {
			ps.close();
		}
		if(db != null) {
			db.close();
		}
	}
	public List<Word> getListBySearchWord(String searchWord,String mode){
		List<Word> list=new ArrayList<>();
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		try {
			this.connect();
			ps=db.prepareStatement("SELECT * FROM words WHERE title LIKE ?");
			ps.setString(1, searchWord);
			//System.out.println(ps);
			rs=ps.executeQuery();
			while(rs.next()) {
				String title=rs.getString("title");
				String body=rs.getString("body");
				Word w=new Word(title,body);
				list.add(w);
			}

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public List<Word> getListBySearchWord(String searchWord,String mode,int limit){
		List<Word> list=new ArrayList<>();
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM words WHERE title LIKE ? LIMIT ?");
			ps.setString(1, searchWord);
			ps.setInt(2, limit);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				list.add(new Word(id, title, body));
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return list;
	}

	//一致件数を求めるメソッド
	public int getCount(String searchWord,String mode){
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		int total=0;
		try {
			this.connect();
			ps = db.prepareStatement("SELECT count(*) AS total FROM words WHERE title LIKE ?");
			ps.setString(1, searchWord);
			rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getInt("total");
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return total;
	}
	public List<Word> getListBySearchWord(String searchWord,String mode,int limit,int offset){
		List<Word> list=new ArrayList<>();
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM words WHERE title LIKE ? LIMIT ? offset ?");
			ps.setString(1, searchWord);
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				list.add(new Word(id, title, body));
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return list;
	}

}