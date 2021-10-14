<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.order_master.controller.*"  %>
<%@ page import="com.order_detail.model.*" %>
<%@ page import="com.product.model.*"%>
<%@ page import="java.util.*" %>
<%@ page import="com.mem.model.*"%>

<% 
	Vector<Order_detailVO> buylist = (Vector<Order_detailVO>)session.getAttribute("shoppingcart");
	String amount = null;
	
// 	String mem_no = null;
// 	MemVO memVO = (MemVO) session.getAttribute("memVO");
// 	if(memVO != null){
// 		mem_no = memVO.getMem_no();
// 	}
// 	pageContext.setAttribute("memVO", memVO);
%>

<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />

<!doctype html>
<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/Generic.css">
	<script src="<%=request.getContextPath() %>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery.mousewheel.js"></script>
	<!--上線時改位址-->
	<script src="<%=request.getContextPath() %>/js/popper.js"></script>
	<script src="<%=request.getContextPath() %>/js/bootstrap-4.3.1.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@8"></script>
	
	<title>SHOP</title>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/shop.css">
</head>
<body>

<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />

<!-- 表頭 -->
	<div class="container-fluid">
        <div class="row justify-content-center align-items-end" id="header">

            <div class="col-3"></div>
            <div class="col-6 mb-5">
                <form METHOD="post" ACTION="<%= request.getContextPath() %>/product/product.do" class="m-0">
                    <div class="row align-items-center justify-content-center">
                        <div class="form-group col-6">
                            <input class="form-control form-control-lg" type="text" name="product" placeholder="請輸入產品關鍵字">
                        </div>

                        <div class="form-group col-2">
                            <select class="custom-select custom-select-lg" name="category_no" size="1">
                                <jsp:useBean id="proSvc" scope="page" class="com.pro_category.model.Pro_categoryService"/>
                                <option value>商品類別</option>
                                <c:forEach var="pro_categoryVO" items="${proSvc.all}">
                                <option value="${pro_categoryVO.category_no}">${pro_categoryVO.category}
                                    </c:forEach>
                            </select>
                        </div>

                        <div class="form-group col-2">
                            <input type="hidden" name="action" value="getSome_For_Display">
                            <button class="btn btn-success btn-lg" type="submit">搜尋</button>
                        </div>

                    </div>
                </form>
            </div>
            <div class="col-3"></div>

        </div>
<!--購物車列表-->
		<div class="row" id="CartCol">

            <div class="col-12 mt-2" id="selecterror">

                <!-- 錯誤處理 -->
                <b>
                    <c:if test="${not empty errorMsgs}">
                        <c:forEach var="message" items="${errorMsgs}">
                            ${message}
                        </c:forEach>
                    </c:if>
                </b>

            </div>

			<div class="col-2"></div>
			<div class="col-8">
			<div class="row justify-content-center" id="product">
	<%if(buylist != null && (buylist.size() > 0)){ %>
			<div class="col-12" >
                <p style="text-align: center; font-size: 60px;color: #343a40">
                    <img src="<%=request.getContextPath() %>/images/icons/Cart_Icon.png" style="height:70px; margin-right: 25px">購物車內容如下
                </p>
            </div>
                <div class="col-12">
                    <table class="table table-borderless table-hover" style="font-size: 22px">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">商品名稱</th>
                                <th scope="col">商品圖片</th>
                                <th scope="col">價格</th>
                                <th scope="col">數量</th>
                                <th scope="col">小計</th>
                                <th scope="col"></th>

                            </tr>
                        </thead>
						<tbody>
                    <%		int total = 0;
                        for(int index = 0; index < buylist.size(); index++){
                            Order_detailVO order = buylist.get(index);
                            int price = order.getPrice();
                            int quantity = order.getQuantity();
                            total += (price * quantity);
                            amount = String.valueOf(total);
                    %>
                        <tr>
                            <td><a href="<%= request.getContextPath()%>/front-end/product/product.jsp?pro_no=<%= order.getPro_no()%>"><%=productSvc.getOneProduct(order.getPro_no()).getProduct() %></a></td>
                            <td><img style="height:100px;width:100px; box-shadow: 2px 2px 4px #666" src="<%=request.getContextPath() %>/product/product.do?getImg=<%=order.getPro_no() %>"></td>
                            <td>$<%=order.getPrice() %></td>
                            <td><%=order.getQuantity() %></td>
                            <td>$<%=order.getPrice() * order.getQuantity() %></td>
                            <td>
								<form name="deleteForm" action="<%=request.getContextPath()%>/order_master/order_master.do" method="POST">
									<input type="hidden" name="action" value="DELETE">
									<input type="hidden" name="del" value="<%=index %>">
									<button type="submit" class="btn btn-danger btn-lg removeProduct">移除</button>
							</form>
							</td>

                        </tr>
                    <%} %>
						</tbody>
                    </table>
                </div>
			<div class="col-12 text-center">
				<hr style="border: 3px solid #666">
				<div class="row justify-content-end mb-3">



				<div class="col-2 text-center">
					<a href="<%=request.getContextPath() %>/front-end/product/shop.jsp">
						<button type="button" class="btn btn-secondary" id="ContinueShop">繼續購物</button>
					</a>
				</div>

					<div class="col-2"></div>

					<div class="col-2 text-center">
						<form name="checkoutForm" action="<%=request.getContextPath()%>/order_master/order_master.do" method="POST">
							<input type="hidden" name="action" value="CHECKOUT">
							<button type="submit" class="btn btn-primary" id="ConfirmOrder">付款結帳</button>
						</form>
					</div>

					<div class="col-3 text-right">

					<h4>總計: $<span style="color: #0f6674"><%=amount %></span></h4>

				</div>

				</div>
			</div>
		
		<%}else{ %>
			<div class="col-12" ><p style="text-align: center; font-size: 60px;color: brown">購物車內無商品</p></div>
			<div class="col-3"></div>
			<div class="col-6 text-center">
				<img style="width:450px;" src="<%=request.getContextPath() %>/images/noproduct.jpg">
			</div>
			<div class="col-3"></div>
			<div class="col-2 mt-3 mb-3">
				<a href="<%=request.getContextPath() %>/front-end/product/shop.jsp">
					<button style="float:right;color:white;background-color:green;width:150px;height:50px;border-radius:10px">購物去</button>
				</a>
			</div>
		<%} %>
		
			</div>
			</div>
			<div class="col-2"></div>
		</div>
<!-- 確認結帳 -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  		<div class="modal-dialog modal-dialog-centered" role="document">
    		<div class="modal-content">
      			<div class="modal-header">
        		<h5 class="modal-title" id="exampleModalCenterTitle">此次購物總金額<b style="color:red;">$<%=amount %></b></h5>
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          		<span aria-hidden="true">&times;</span>
        		</button>
      			</div>
      		<div class="modal-footer">
      		

        	  <form name="checkoutForm" action="<%=request.getContextPath()%>/order_master/order_master.do" method="POST">
        	  	<input type="hidden" name="action" value="CHECKOUT">
        		<button type="submit" class="btn btn-primary">確認付款</button>
        	  </form>
      		</div>
    		</div>
  		</div>
	</div>
	</div>
<!-- 頁腳 -->
	<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>
<!-- 頁腳 -->
<!-- nav.js -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath() %>/js/shop.js"></script>
<!-- nav.js -->
</body>