package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateInputServlet
 */
@WebServlet("/updateInput")
public class UpdateInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//更新選択画面で商品を選択し、選択ボタンが押されたとき
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//更新選択画面で選択したIDを取得
		String updateId = request.getParameter("updateId");	


		//セッションオブジェクトproductがnullの場合＝入力値がない場合
		HttpSession session = request.getSession();
		Product product = (Product) session.getAttribute("product");

		if (product == null) {



			//更新選択画面で選択されなかったら商品選択画面から遷移しない
			if(updateId == null) {

				String errMessage = "更新する商品が選択されていません。";
				request.setAttribute("errMessage", errMessage);


				//更新商品選択へ遷移
				RequestDispatcher rd = request.getRequestDispatcher("updateChoice");
				rd.forward(request, response);

			}


			//DB接続情報の準備
			String url = "jdbc:mysql://localhost:3306/pizzashop";
			String user = "root";
			String password = "1234";

			//JDBCドライバを読み込む
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String sql = "SELECT p.*, k.kind FROM product_master p INNER JOIN kind_master k ON p.kind_id = k.kind_id WHERE id = ?";


			//DB接続
			try(Connection con = DriverManager.getConnection(url, user, password);
					PreparedStatement pstmt = con.prepareStatement(sql)){

				//所得したidをバインド
				pstmt.setString(1, updateId);

				//検索select文の実行
				ResultSet rset = pstmt.executeQuery();


				//データベースから参照したデータを、
				//インスタンスに格納し、リクエストスコープに格納
				while(rset.next()) {

					//DBの情報を受け取る
					String id = rset.getString("id");
					String name  = rset.getString("name");	
					int price = rset.getInt("price");
					String kind = rset.getString("kind");


					//エンティティをインスタンス生成し、DBから受け取った情報を格納
					Product productDB = new Product();
					productDB.setId(id);
					productDB.setName(name);
					productDB.setPrice(price);
					productDB.setKind(kind);

					//価格に文字列を入力時に、DBの値を表示するパターン
					//HttpSession session = request.getSession();
					//session.setAttribute("productBefore", product);

					//価格に文字列を入力時に、入力値を表示するパターン
					request.setAttribute("product",productDB);

				}

			} catch (Exception e) {
				System.out.println("Exception" + e.getMessage());
			}

		}

		//更新箇所入力画面へ遷移
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/update_food_input.jsp");
		rd.forward(request, response);


	}

}
