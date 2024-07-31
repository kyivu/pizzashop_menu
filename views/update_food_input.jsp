
<%@page import="entity.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新箇所入力画面</title>
</head>
<body>
	<h1>PizzaShop</h1>
	
	<%! String price  = null ; %>

	<%
	 String userID = (String) session.getAttribute("userID");
	 Product product = (Product) request.getAttribute("product");

	 if (product == null) {
	 	product = (Product) session.getAttribute("product");
	 }
	%>
	
	<%--  文字列チェックをプログラムでやる場合
	 String errMessage = (String)request.getAttribute("errMessage");
	--%>

	<%=userID%>

	<p>
		<button onclick="location.href='management'">管理TOPへ</button>
	</p>

	<p>
		<a href="logout">ログアウト</a>
	</p>

	<hr>

	<h2>更新する箇所を入力してください。</h2>

	<form action="updateCheck" method="post">
		<table>
			<tr>
				<td>商品番号：</td>
				<td><%=product.getId()%><input type="hidden" name=id
					value="<%=product.getId()%>"></td>
			</tr>
			<tr>
				<td style="text-align: right">商品名：</td>
				<td><input type="text" name="name"
					value="<%=product.getName()%>" required></td>
			</tr>
			<tr>
				<td>商品価格：</td>
				<td>
					<input type="text" name="price" value="<%= product.getPrice()%>" required pattern="\d*" title="数字のみを入力してください">
				</td>
			</tr>
			
			<%-- 文字列チェックをプログラムでやる場合
			<%
			if (errMessage != null) {
			%>
			<tr>
				<td colspan="3" style="color: red"><%=errMessage%></td>
			</tr>
			<%
			}
			%>
			
			--%>
			
			<tr>
				<td>商品種別：</td>
				<td><select name="kind">
						<option <%if (product.getKind().equals("ピザ")) {%> selected <%}%> value="1,ピザ">ピザ</option>
						<option <%if (product.getKind().equals("パスタ")) {%> selected <%}%>
							value="2,パスタ">パスタ</option>
						<option <%if (product.getKind().equals("ドリンク")) {%> selected <%}%>
							value="3,ドリンク">ドリンク</option>
						<option <%if (product.getKind().equals("サイド")) {%> selected <%}%>
							value="4,サイド">サイド</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="確認"></td>
			</tr>

			</form>

		</table>
</body>
</html>