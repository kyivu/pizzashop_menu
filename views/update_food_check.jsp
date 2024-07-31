<%@page import="entity.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新確認画面</title>
</head>
<body>
	<h1>PizzaShop</h1>

	<%
	 String userID = (String) session.getAttribute("userID");
	 Product product = (Product)session.getAttribute("product"); 
	%>

	<%=userID%>

	<p>
		<button onclick="location.href='management'">管理TOPへ</button>
	</p>

	<p>
		<a href="logout">ログアウト</a>
	</p>

	<hr>

	<h2>以下の商品を更新しますか？</h2>

	<form action="updateDo" method="post">
		<table>
			<tr>
				<th>商品番号</th>
				<th>商品名</th>
				<th>価格</th>
				<th>商品種別</th>
			</tr>

			<tr>
				<td><%=product.getId()%></td>
				<td><%=product.getName()%></td>
				<td><%=product.getPriceStr()%>円</td>
				<td><%=product.getKind()%></td>
			</tr>

			<tr height="80">
				<td><button type="submit" name="updateId" value="<%=product.getId()%>" formaction="updateInput">訂正</td>
			</tr>

			<tr>
				<td><button type="submit">更新</button></td>
			</tr>

			</form>

		</table>
</body>
</html>