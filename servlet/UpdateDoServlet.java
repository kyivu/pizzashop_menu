package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import entity.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateDoServlet
 */
@WebServlet("/updateDo")
public class UpdateDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//更新確認画面で更新を押された時
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//入力ページで入力された情報をセッションスコープから取得
		HttpSession session = request.getSession();
		Product product = (Product)session.getAttribute("product");
	
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


		String sql = "Update product_master SET name = ? , price = ? , kind_id = ? WHERE id = ? ";

		
		//DB接続
		try(Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement pstmt = con.prepareStatement(sql)){

			//更新したい内容をバインドする
			
			pstmt.setString(1,product.getName());
			pstmt.setInt(2,product.getPrice());
			pstmt.setInt(3,product.getKind_id());
			pstmt.setString(4,product.getId());
			
			//update文の実行
			pstmt.executeUpdate();


		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		
		session.removeAttribute("product");
		

		//更新商品選択へ遷移
		RequestDispatcher rd = request.getRequestDispatcher("updateChoice");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
