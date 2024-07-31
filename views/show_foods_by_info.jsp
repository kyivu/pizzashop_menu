<%@page import="java.util.ArrayList"%>
<%@page import="entity.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索結果表示画面</title>
</head>
<body>

	<h1>PizzaShop</h1>

	<%
	  String userID = (String)session.getAttribute("userID");
	 ArrayList<Product> list = (ArrayList<Product>)request.getAttribute("list");
	%>


	<%= userID %>

	

	<%---管理TOPへボタン--%>
	<p>
		<button onclick="location.href='management'">管理TOPへ</button>
	</p>


	<%---ログアウト リンクボタン--%>
	<p>
		<a href="logout">ログアウト</a>
	</p>

	<hr>

	<%----------------------------------------------------------------------------------------------------------------------------------- --%>

	<h2>曖昧検索結果</h2>

	<table>
		<tr>
			<th>商品番号</th>
			<th>商品名</th>
			<th>価格</th>
			<th>商品種別</th>
		</tr>



		<% for (Product product : list){  %>
		<tr>
			<td><%=product.getId() %></td>
			<td><%=product.getName() %></td>
			<td><%=product.getPriceStr()%>円</td>  <%-- 変更箇所　6/6   円を追加--%>
			<td><%=product.getKind() %></td>
			
		</tr>
		<% } %>
		
		
		
		
	
</body>
</html>


