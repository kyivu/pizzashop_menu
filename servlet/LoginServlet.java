package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*リクエストパラメータを取得する*/

		Boolean check= false ; //trueかfalseを入れる

		//文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//送信情報(リクエストパラメータ)の取得
		String userID =request.getParameter("userID");   //userIDの受け取り
		String password =request.getParameter("password");  //passwordの受け取り

		/* データ接続==================================================================================== */

		//▼１▼ DB接続に必要な情報を記述
		String url = "jdbc:mysql://localhost:3306/pizzashop";
		String user = "root";
		String password1 = "1234";

		//▼2▼ JDBCドライバーの設定(読み込み)
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		//▼3▼ SQL文発行
		String sql ="SELECT * FROM users_master where userID=? AND password=?;";
		try (Connection connection = DriverManager.getConnection(url, user, password1);
				PreparedStatement statment = connection.prepareStatement(sql);
				) {	

			//バインド
			statment.setString(1,userID);
			statment.setString(2,password);


			//SQL文の結果格納
			ResultSet results = statment.executeQuery();


			//表示対象の検索
			while(results.next()) {
				check = true;

			}


		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}

		//条件分岐  (どっちか空文字なら)
		if(userID.equals("")||password.equals("")) {

			//失敗(→false.jspへ遷移)
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/views/failure.html");    
			dispatcher2.forward(request,response);	

			//どっちかnullなら
		}else if(userID.equals(null)||password.equals(null)) {	

			//失敗(→failure.htmlへ遷移)
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/views/failure.html");    
			dispatcher2.forward(request,response);	

		}else if(check) {
			//成功(→management.servletに遷移)
			
			//セッションスコープにログインIDを格納
			HttpSession session = request.getSession();
			session.setAttribute("userID",userID );
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/management");    
			dispatcher.forward(request,response);


			//それ以外なら
		}else {
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/views/failure.html");
			dispatcher2.forward(request, response);
		}

	}
}
