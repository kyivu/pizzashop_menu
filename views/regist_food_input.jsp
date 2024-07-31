<%@page import="entity.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録</title>
</head>
<body>
	<h1>PizzaShop</h1>

	<% 
	String message = (String) request.getAttribute("message");
	Product product = (Product)session.getAttribute("product");
	String userID = (String) session.getAttribute("userID");
	%>




	<%=userID%>

	<p>
		<button onclick="location.href='management'">管理TOPへ</button>
	</p>

	<p>
		<a href="logout">ログアウト</a>
	</p>


	<%if(message == null && product == null){%>
	<hr>

	<form action="registCheck" method="get">
		<h2>登録する商品情報を入力してください</h2>
		商品番号：<input type="text" required name="id"><br> 
		　商品名：<input type="text" required name="name"><br> 
		商品価格：<input type="text" required name="price" pattern="\d*" title="数字のみを入力してください"><br>
		商品種別：<select name="kind_id">
			<option value="1">ピザ</option>
			<option value="2">パスタ</option>
			<option value="3">ドリンク</option>
			<option value="4">サイド</option>
		</select><br> <input type="submit" value="確認">

	</form>

	<%}else if (message == null && product != null) {%>
	<hr>

	<form action="registCheck" method="get">
		<h2>登録する商品情報を入力してください</h2>
		商品番号：<input type="text" required name="id" value=<%=  product.getId() %>><br> 
		　商品名：<input type="text" required name="name" value=<%= product.getName() %>><br> 
		商品価格：<input type="text" required name="price" value=<%=product.getPrice()%>><br>
		商品種別：<select name="kind_id">
			<option <%if (product.getKind_id() == 1) { %> selected <%}%> value="1">ピザ</option>
			<option <%if (product.getKind_id() == 2) { %> selected <%}%> value="2">パスタ</option>
			<option <%if (product.getKind_id() == 3) { %> selected <%}%> value="3">ドリンク</option>
			<option <%if (product.getKind_id() == 4) { %> selected <%}%> value="4">サイド</option>
		</select><br> <input type="submit" value="確認">

	</form>


	<%}else{%>

	
	
	<h2>
		<font color="red">その商品番号は登録されています</font>
	</h2>
	<hr>

	<form action="registCheck" method="get">
		<h2>登録する商品情報を入力してください</h2>
		商品番号：<input type="text" required name="id" value=<%=  product.getId() %>><br> 
		　商品名：<input type="text" required name="name" value=<%= product.getName() %>><br> 
		商品価格：<input type="text" required name="price" value=<%=product.getPrice()%>><br>
		商品種別：<select name="kind_id">
			<option <%if (product.getKind_id() == 1) { %> selected <%}%> value="1">ピザ</option>
			<option <%if (product.getKind_id() == 2) { %> selected <%}%> value="2">パスタ</option>
			<option <%if (product.getKind_id() == 3) { %> selected <%}%> value="3">ドリンク</option>
			<option <%if (product.getKind_id() == 4) { %> selected <%}%> value="4">サイド</option>
		</select><br> <input type="submit" value="確認">

	</form>

	<%} %>

</body>
</html>