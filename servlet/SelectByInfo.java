package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*  SelectByInfo 
 *   曖昧検索の検索ワード入力画面へ遷移する*/

@WebServlet("/selectByInfo")

public class SelectByInfo extends HttpServlet {

	//条件検索リンクをクリック
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		//転送(「管理Top」→「条件検索」へ」)
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/select_foods_by_info.jsp");
		rd.forward(request, response);
		
		
	}
}
