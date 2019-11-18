package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDB {
	public static void main(String[] args) {
		try {
			FileInputStream fis=new FileInputStream("assets/ejdic-hand-utf8.txt");
			InputStreamReader isr=new InputStreamReader(fis,"utf-8");
			BufferedReader br=new BufferedReader(isr);

			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/ejword?useUnicode=true&characterEncoding=utf8";
			String user="root";
			Connection db=DriverManager.getConnection(url,user,null);

			//トランザクション処理開始 処理を保障する仕組み
			db.setAutoCommit(false);
			PreparedStatement ps=db.prepareStatement("insert into words(title,body) values(?,?)");
			String line;
			while((line=br.readLine())!=null) {
				String[] vals=line.split("\t");
				ps.setString(1,vals[0]);
				ps.setString(2, vals[1]);
				ps.execute();
			}
			db.commit();
			db.close();
			System.out.println("done!");


		} catch ( ClassNotFoundException | SQLException | IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
