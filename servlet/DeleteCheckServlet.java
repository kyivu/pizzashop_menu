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

/**
 * Servlet implementation class DeleteCheckServlet
 */
@WebServlet("/deleteCheck")
public class DeleteCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//商品削除選択画面で選択を押されたら
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//削除選択画面で選択したIDを取得
		String deleteId = request.getParameter("deleteId");

		//削除選択画面で選択されなかったとき
		if(deleteId == null) {

			String errMessage = "削除する商品が選択されていません。";
			request.setAttribute("errMessage", errMessage);


			//削除商品選択へ遷移
			RequestDispatcher rd = request.getRequestDispatcher("deleteChoice");
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
			pstmt.setString(1, deleteId);

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

				//価格をカンマ区切りにする　                                　
				NumberFormat ni = NumberFormat.getNumberInstance();  
				String priceStr = ni.format(price);

				//エンティティをインスタンス生成し、DBから受け取った情報を格納
				Product product = new Product();
				product.setId(id);
				product.setName(name);
				product.setPriceStr(priceStr);
				product.setKind(kind);

				HttpSession session = request.getSession();
				session.setAttribute("product", product);

			}

		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}


		//削除の確認画面へ遷移
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/delete_food_check.jsp");
		rd.forward(request, response);



	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
