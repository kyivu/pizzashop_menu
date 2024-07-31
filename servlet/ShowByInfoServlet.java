package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;

import entity.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*   ShowByInfoServlet   
 *    検索ワードに該当するものを判断して表示するJSPへ遷移*/

@WebServlet("/showByInfo")


public class ShowByInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードを指定
		request.setCharacterEncoding("UTF-8");

		//エラー判定用の変数
		Boolean check= false ; //trueかfalseを入れる
		String errMessage;



		//PARAMETERの受け取り     
		//どの検索ジャンルが選ばれたかがkeywordに入る
		String keyword = request.getParameter("keyword");  


		//検索用に入力された値がwordに入る
		String word = request.getParameter("word");
		String searchword = "%" + word + "%";



		//1.DB
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


		//入力値(?にsearchwordがはまる)


		String sql = "SELECT * FROM product_master INNER JOIN kind_master ON product_master.kind_id = kind_master.kind_id"
				+ " where "+ keyword + " like ? ";


		//SQL文実行
		try(Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)){


			//バインド(させるのは検索ワードのみ)     
			pstmt.setString(1,searchword);


			//「sql1」の実行
			ResultSet rset = pstmt.executeQuery();



			ArrayList<Product> list = new ArrayList<>();

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

				list.add(product);

				//DBに一致するものがあったとき
				check = true;
			}

			//リクエストパラメーターに格納
			request.setAttribute("list", list);

			//コンソールに結果表示
			System.out.println(list);

		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}

		//=============================================================================================================================

		//エラー判定
		if(check==true) {

			//転送
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/show_foods_by_info.jsp");
			rd.forward(request, response);

		}else if(check==false) {

			errMessage="その条件に合う商品がありません";


			request.setAttribute("errMessage",errMessage );
		}
		//転送
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/select_foods_by_info.jsp");
		rd.forward(request, response);

	}

}

