package servlet;

import java.io.IOException;
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
 * Servlet implementation class UpdateCheckServlet
 */
@WebServlet("/updateCheck")
public class UpdateCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 		HttpSession session = request.getSession();
		
		//フォームのパラメーターを受け取る
		String id = request.getParameter("id");
		String name  = request.getParameter("name");	
		
		
		//種別を種別名と種別idどちらも取得
		String kindIntStr = request.getParameter("kind");		
		String[] kinds = kindIntStr.split(",");
		int kind_id = Integer.parseInt(kinds[0]);
 		String kind = kinds[1];
 		
 		
 		//プログラムで価格のエラーチェックを行わない場合
 		int price = Integer.parseInt(request.getParameter("price"));
 		
 		
 		//プログラムで価格の入力チェックを行う場合
 		/*
 		//価格についてはエラーチェックを行う　文字列が入れられたときエラー
 		int price = 0 ;
 		
 		try {
 		 price = Integer.parseInt(request.getParameter("price"));
 		 
 		 
 		}catch(NumberFormatException e) {
 			
 	 		String errMessage = "商品価格に文字が入力されています。";
 			request.setAttribute("errMessage", errMessage);
 			
 			
 			//入力値を表示するパターン			
 			String priceStr = request.getParameter("price");
 			
 	 		Product product = new Product();
 	 		product.setId(id);
 	 		product.setName(name);
 	 		product.setPriceStr(priceStr);
 	 		product.setKind(kind);
 	 		
 	 		request.setAttribute("product", product);
 	 	
 			
 			//更新確認画面へ遷移
 			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/update_food_input.jsp");
 			rd.forward(request, response);
	
 		}
 		*/
 		
 		
 		//価格をカンマ区切りにする　                                　
 		NumberFormat ni = NumberFormat.getNumberInstance();  
 		String priceStr = ni.format(price);

 		//エンティティをインスタンス生成し、フォームで入力された情報を格納
 		Product product = new Product();
 		product.setId(id);
 		product.setName(name);
 		product.setPriceStr(priceStr);
 		product.setPrice(price);
 		product.setKind_id(kind_id);
 		product.setKind(kind);

 		session.setAttribute("product", product);
 		
		//更新確認画面へ遷移
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/update_food_check.jsp");
		rd.forward(request, response);
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		

		
		
		
	}

}
