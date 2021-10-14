<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.product.model.*" %>
<%@ page import="com.pro_category.model.*" %>
<%@ page import="com.order_detail.model.*" %>
<%@ page import="com.order_master.model.*" %>
<%@ page import="com.mem.model.*" %>

<% ProductService productSvc = new ProductService();
    String pro_no = request.getParameter("pro_no");
    ProductVO productVO = productSvc.getOneProduct(pro_no);
    pageContext.setAttribute("productVO", productVO);

    Order_detailService order_detailSVC = new Order_detailService();
    List<Order_detailVO> order_detailVO = order_detailSVC.findByPro_no(pro_no);
    pageContext.setAttribute("order_detailVO", order_detailVO);

%>
<jsp:useBean id="order_mastertSvc" scope="page" class="com.order_master.model.Order_masterService"/>
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="pro_categorySvc" class="com.pro_category.model.Pro_categoryService" />



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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.raty.css">
    <script src="<%=request.getContextPath() %>/js/jquery-slim-3.3.1.js"></script>
    <!--上線時改位址-->
    <script src="<%=request.getContextPath() %>/js/popper.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap-4.3.1.js"></script>
    <script src="<%=request.getContextPath() %>/js/jquery.raty.min.js"></script>

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

    <!--商品列表-->
    <div class="row" id="shop">

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
        <div class="col-8" style="min-height: 100vh;">
            <div class="row justify-content-center h-100 pt-2" id="product">

                <div class="col-6">
                    <div class="productimg">
                        <img src="<%=request.getContextPath() %>/product/product.do?getImg=${productVO.pro_no}"
                             class="img-product">
                    </div>
                </div>
                <div class="col-6" id="productmessage">
                    <form name="shoppingForm" action="<%=request.getContextPath()%>/order_master/order_master.do" method="POST">
                        <h2 style="margin: 2vh 0;">${productVO.product}</h2>
                        <h6 class="text-left" style="color: #666666">${pro_categorySvc.getOnePro_category(productVO.category_no).category}</h6>
                        <div id="reviewed" data-review="${productVO.score/productVO.score_peo}" style="display: inline;"></div>
                        <span id="spanscore">${productVO.score/productVO.score_peo}</span>

                        <hr style="border: 1px solid #666666">

                        <h4 style="margin: 3vh 0;">$${productVO.price}</h4>

                        <div id="message" style="margin: 2vh 0;height: 21vh">
                            <h6 style="color: #666666">${productVO.message}</h6>
                        </div>
                        <p>付款方式:信用卡</p>
                        <p>運送方式:宅配</p>

                        <div class="row">
                            <div class="col-4 my-auto">
                                <p style="margin: 0">選擇數量:</p>
                            </div>
                            <div class="col-4 my-auto">
                                <div class="input-group input-number-group">
                                    <input type='button' value='-' class='qtyminus' field='quantity'>
                                    <input type='text' name='quantity' value='1' class='qty' readonly>
                                    <input type='button' value='+' class='qtyplus' field='quantity'>

                                    <input type="hidden" name="pro_no" value="${productVO.pro_no}">
                                    <input type="hidden" name="price" value="${productVO.price}">
                                    <input type="hidden" name="price" value="${productVO.pic}">
                                    <input type="hidden" name="action" value="ADD">
                                </div>
                            </div>
                            <div class="col-4 text-left">
                                <button id="addbuttin" class="btn btn-outline-secondary"><img src="<%= request.getContextPath()%>/images/icons/Cart_Icon.png" style="width: 30px;margin-right: 8px">加入購物車</button>
                            </div>

                        </div>
                    </form>
                </div>


                <div class="col-12" id="productreview">
                    <b>商品評價</b>
                    <hr style="border: 3px solid #666666">
                </div>
                <div class="col-12">

                    <c:forEach var="order_detailVO" items="${order_detailVO}" begin="<%=0 %>"
                               end="<%=order_detailVO.size() %>">
                        <c:if test="${not empty order_detailVO.review}">
                            <div class="row mb-1 ml-1 mr-1" id="review">
                                <div class="col-1 pl-0" style="height: 100px">
                                    <img src="<%=request.getContextPath() %>/mem/mem.do?action=showOthersImg&mem_no=${order_mastertSvc.findByprimaryKey(order_detailVO.order_id).mem_no}">
                                </div>
                                <div class="col-10">
                                    <div class="col-12">
                                        <h5 class="mt-2">${memSvc.getOneMem(order_mastertSvc.findByprimaryKey(order_detailVO.order_id).mem_no).mem_name}</h5>

                                    </div>
                                    <div class="col-12">
                                            ${order_detailVO.review}
                                    </div>
                                    <div class="col-12 text-right">
                                            ${order_detailVO.review_date}
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-2"></div>
    </div>
    <!-- 頁腳 -->
	<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>

	<!-- 頁腳 -->
<!-- nav.js -->
<script src="<%=request.getContextPath() %>/js/shop.js"></script>
<script>
    $('#reviewed').raty({
        size: 24,
        half: true,
        starHalf: '<%=request.getContextPath()%>/images/icons/star-half-big.png',
        starOff: '<%=request.getContextPath()%>/images/icons/star-off-big.png',
        starOn: '<%=request.getContextPath()%>/images/icons/star-on-big.png',
        readOnly: true,
        score: $('#reviewed').attr('data-review')
    });

    var spanscore = $('#spanscore').text();
    var average = spanscore.substr(0, 3);
    // 	window.alert(spanscore)
    if (spanscore == 'NaN') {
        $('#spanscore').text("尚無評價")
    } else {
        $('#spanscore').text(average)
    }
</script>
<!-- nav.js -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
</body>
</html>