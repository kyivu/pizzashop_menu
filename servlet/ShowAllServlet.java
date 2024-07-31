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

/**
 * Servlet implementation class ShowAllServlet
 */
@WebServlet("/showAll")
public class ShowAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //全件表示リンクをクリック
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

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

		
		String sql = "SELECT p.*, k.kind FROM product_master p INNER JOIN kind_master k ON p.kind_id = k.kind_id";
		
		//DB接続
		try(Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)){

			//検索select文の実行
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
				 NumberFormat ni = NumberFormat.getNumberInstance();    ///←変更箇所　1/6
				 String priceStr = ni.format(price);                      ///←変更箇所　1/6
				

				
				//エンティティをインスタンス生成し、DBから受け取った情報を格納
				Product product = new Product();
				product.setId(id);
				product.setName(name);
				product.setPriceStr(priceStr);                                ///←変更箇所　2/6 　　　3/6はProductクラス
				product.setKind(kind);

				list.add(product);
				
			}
			
			request.setAttribute("list", list);


		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
	

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/show_all_foods.jsp");
		rd.forward(request, response);
	}

}