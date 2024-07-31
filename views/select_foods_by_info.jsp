
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索入力画面</title>
</head>
<body>

<h1>PizzaShop</h1>

   <%
	  String userID = (String)session.getAttribute("userID");
      String errMessage = (String)request.getAttribute("errMessage");
	%>
	
	
<%= userID %>

<%---管理TOPへボタン--%>
<p> <button onclick="location.href='management'">管理TOPへ</button> </p>


<%---ログアウト リンクボタン--%>
<p>  <a href="logout">ログアウト</a> </p>


<%---エラー時メッセージ表示(ShowServletで判断されてここに戻る)--%>

<% if ( errMessage != null ) { %>
<b><p style="color:red ">	 <%= errMessage %>  </p></b>
<% } %>

	<hr>

<h2>曖昧検索</h2>

<form action ="showByInfo" method="get">

<select name="keyword">

<option value="product_master.name">商品名</option>  <%-- name --%>
<option value="product_master.id">商品番号</option> <%-- id --%>
<option value="product_master.price">価格</option>      <%-- price --%>
<option value="kind_master.kind">商品種別</option>   <%-- kind --%>

 </select>
 
 
 検索文字入力：<input type="text" required name="word">  <br>
 
 <%---検索ボタン("/showByInfoのサーブレットへ遷移)--%>

 <button type="submit" formaction="showByInfo" name="検索" >検索</button>     <%--　　← requiredに変更しました！ --%>

 
 
 </form>

 
 



</body>
</html>