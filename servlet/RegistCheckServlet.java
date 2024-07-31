package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;

import entity.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/registCheck")
public class RegistCheckServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*リクエストパラメータを取得する*/

		Boolean check= false ; //trueかfalseを入れる

		//文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//送信情報(リクエストパラメータ)の取得
		String id =request.getParameter("id");   //商品番号（id）の受け取り
		String name =request.getParameter("name");  //商品名（name）の受け取り
		int price = Integer.parseInt(request.getParameter("price"));	//商品価格（price)の受け取り
		int kind_id = Integer.parseInt(request.getParameter("kind_id"));	//商品種別（kind_id）の受け取り

		//id,name,priceのいずれかが空値の場合、入力画面に送り返す
		if (id == "" || name == "" || price == -1) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/regist_food_check.jsp");
			rd.forward(request, response);
		}



		//価格をカンマ区切りにする　                                
		NumberFormat ni = NumberFormat.getNumberInstance();    
		String priceStr = ni.format(price);           

		//セッションスコープを利用するためのインスタンス生成
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setPrice(price);
		product.setPriceStr(priceStr);
		product.setKind_id(kind_id);



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

		//▼3▼ id重複チェックのためのSQL発行
		String sql ="SELECT * FROM product_master where id=?;";
		//		String sqlKind = "SELECT * FROM kind_master where id=?;";
		try (Connection connection = DriverManager.getConnection(url, user, password1);
				PreparedStatement statment = connection.prepareStatement(sql);
				) {	

			//バインド
			statment.setString(1,id);

			//SQL文の結果格納
			ResultSet results = statment.executeQuery();


			//表示対象の検索
			while(results.next()) {
				check = true;
			}

		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}

		//種別IDを元にDBから商品種別に変換
		String sql2 = "SELECT kind FROM kind_master where kind_id=?";
		//DB接続
		try(Connection con = DriverManager.getConnection(url, user, password1);
				PreparedStatement pstmt = con.prepareStatement(sql2)){

			//バインド
			pstmt.setInt(1,kind_id);

			//検索select文の実行
			ResultSet rset = pstmt.executeQuery();

			//データベースから参照したデータを、
			while(rset.next()) {

				String kind = rset.getString("kind");

				//インスタンスに格納
				product.setKind(kind);





			}



		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}



		//セッションスコープに入力した商品情報のインスタンスを格納
		HttpSession session = request.getSession();
		session.setAttribute("product", product);




		//条件分岐と遷移
		//既存DBと商品番号一致で登録失敗
		if(check) {
			request.setAttribute("message", "m");
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/WEB-INF/views/regist_food_input.jsp");   
			dispatcher2.forward(request,response);	




			//それ以外：登録確認画面（regist_food_check.jspに移行）
		}else {

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/regist_food_check.jsp");
			rd.forward(request, response);
		}

	}
}