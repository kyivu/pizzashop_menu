package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteDoServlet
 */
@WebServlet("/deleteDo")
public class DeleteDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//フォームのパラメーターを受け取る
		String deleteId = request.getParameter("deleteId");
		
		
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


				String sql = "DELETE FROM product_master WHERE id = ? ";

				
				//DB接続
				try(Connection con = DriverManager.getConnection(url, user, password);
						PreparedStatement pstmt = con.prepareStatement(sql)){

					//更新したい内容をバインドする
					
					pstmt.setString(1,deleteId);

					
					//update文の実行
					pstmt.executeUpdate();


				} catch (Exception e) {
					System.out.println("Exception" + e.getMessage());
				}
				
				//セッションオブジェクトを削除	
				HttpSession session = request.getSession();
				session.removeAttribute("product");	
				

				//削除商品選択画面へ遷移
				RequestDispatcher rd = request.getRequestDispatcher("deleteChoice");
				rd.forward(request, response);
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
