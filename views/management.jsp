<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>項目選択画面</title>
</head>
<body>

	<h1>PizzaShop</h1>
	
	<%
	  String userID = (String)session.getAttribute("userID");
	
	%>
	<%= userID %>
	
	
	<p><a href="logout">ログアウト</a>
	
	<h2>管理TOP画面</h2>
	
	<ul>
	 <li><a href="showAll">全件表示</a>
	 <li><a href="selectByInfo">条件検索</a>
	 <li><a href="registInput">商品登録</a>
	 <li><a href="updateChoice">商品更新</a>
	 <li><a href="deleteChoice">商品削除</a>
	</ul>

</body>
</html>