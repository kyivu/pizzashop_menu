<%@page import="java.util.ArrayList"%>
<%@page import="entity.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新商品選択画面</title>
</head>
<body>
	<h1>PizzaShop</h1>

	<%
	 String userID = (String) session.getAttribute("userID");
	 ArrayList<Product> list = (ArrayList<Product>) request.getAttribute("list");
	 
	 String errMessage = (String)request.getAttribute("errMessage");
	%>

	<%=userID%>

	<p>
		<button onclick="location.href='management'">管理TOPへ</button>
	</p>

	<p>
		<a href="logout">ログアウト</a>
	</p>
	
	<%
	if (errMessage != null) {
	%>
	<h2>
		<font color="red"><%=errMessage%></font>
	</h2>
	<%
	}
	%>

	<hr>

	<h2>更新する商品を選択してください。</h2>



	<form action="updateInput" method="post">
		<table>
			<tr>
				<th>変更選択</th>
				<th>商品番号</th>
				<th>商品名</th>
				<th>価格</th>
				<th>商品種別</th>
			</tr>

			<%
			for (Product product : list) {
			%>
			<tr>
				<td><input type="radio" value="<%=product.getId()%>"
					name="updateId"></td>
				<td><%=product.getId()%></td>
				<td><%=product.getName()%></td>
				<td><%=product.getPriceStr()%>円</td>
				<td><%=product.getKind()%></td>
			</tr>


			<%
			}
			%>
			<tr>
				<td><input type="submit" value="選択"></td>
			</tr>

			</form>

		</table>
</body>
</html>