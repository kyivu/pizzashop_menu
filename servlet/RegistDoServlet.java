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

@WebServlet("/registDo")
public class RegistDoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//セッションスコープから値を受け取る
		HttpSession session = request.getSession();
		Product product = (Product)session.getAttribute("product");
		
		String id = product.getId();
		String name =  product.getName();
		int price = product.getPrice();	//商品価格（price)の受け取り
		int kind_id = product.getKind_id();	//商品種別（kind_id）の受け取り
		
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

				//▼3▼ insert文に変更
				String sql ="INSERT INTO product_master(id,name,price,kind_id) VALUES(?,?,?,?)";
				//		String sqlKind = "SELECT * FROM kind_master where id=?;";
				try (Connection connection = DriverManager.getConnection(url, user, password1);
						PreparedStatement statment = connection.prepareStatement(sql);
						) {	
					
					
					//バインド
					statment.setString(1, id);
					statment.setString(2, name);
					statment.setInt(3, price);
					statment.setInt(4, kind_id);
					

					//INSERT,UPDATE,DELETEで使う
					statment.executeUpdate();

					
				} catch (Exception e) {
					System.out.println("Exception" + e.getMessage());
			
				}
		
				//入力した商品情報の削除
				session.removeAttribute("product");		
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/regist_food_input.jsp");
		rd.forward(request, response);
		
		
	}

}
