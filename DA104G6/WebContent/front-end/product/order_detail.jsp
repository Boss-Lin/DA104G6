<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.order_detail.model.*" %>
<%@ page import="com.order_master.model.*" %>
<%@ page import="com.product.model.*" %>


<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService"/>
<jsp:useBean id="order_masterSvc" scope="page" class="com.order_master.model.Order_masterService"/>


<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.raty.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/Generic.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/order_detail.css">
    <script src="<%=request.getContextPath() %>/js/jquery-slim-3.3.1.js"></script>
    <!--上線時改位址-->


    <title>SHOP</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/shop.css">
</head>
<body>

<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />
<div class="container-fluid clear-top">
    
    <div class="col-12"></div>
    <!--訂單明細-->
    <div class="row">
        <div class="col-12" id="order_detail">
            <div class="row align-items-center justify-content-center" style="padding-top: 10vh">
                <div class="col-2"></div>
                <div class="col-8 text-center">
                    <table class="table table-striped">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col" class="text-nowrap">商品</th>
                            <th scope="col" class="text-nowrap">數量</th>
                            <th scope="col" class="text-nowrap">單價</th>
                            <th scope="col" class="text-nowrap">合計</th>
                            <th scope="col" class="text-nowrap">評價內容</th>
                            <th scope="col" class="text-nowrap">評價日期</th>
                            <th scope="col" class="text-nowrap">評價</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order_detailVO" items="${order_detailList}">
                            <tr>
                                <td class="text-nowrap text-left">${productSvc.getOneProduct(order_detailVO.pro_no).product}</td>
                                <td>${order_detailVO.quantity}</td>
                                <td>$${order_detailVO.price}</td>
                                <td>$${order_detailVO.quantity*order_detailVO.price}</td>
                                <td style="display: block; overflow-y: scroll; overflow-x: hidden; width: 400px;height: 100px;  word-break: break-all">${order_detailVO.review}</td>
                                <td>${order_detailVO.review_date}</td>
                                <td>
                                    <c:if test="${empty order_detailVO.review_date && order_masterSvc.findByprimaryKey(order_detailVO.order_id).status == 2}">
                                        <button type="button" data-pro_no="${order_detailVO.pro_no}" data-order_id="${order_detailVO.order_id}" class="btn btn-info review" data-toggle="modal" data-target=".bd-example-modal-sm">評價</button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-2"></div>
            </div>
        </div>
    </div>
    <!-- 評價跳窗 -->
    <div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-sm modal-dialog-centered">
            <div class="modal-content">
                <FORM METHOD="post" ACTION="<%= request.getContextPath()%>/order_detail/order_detail.do" name="form1">
                    <div class="col-12 text-center">
                        <b style="font-size:24px;">評價分數</b>
                    </div>
                    <div class="col-12">
                        <div class="col-12 text-center" id="star"></div>
                    </div>
                    <div class="col-12 text-center">
                        <b style="font-size:24px;">評價內容</b>
                    </div>
                    <div class="col-12">
                        <textarea name="review" cols="25" rows="5"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">確認送出</button>
                    </div>
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="order_id" value="" id="order_id">
                    <input type="hidden" name="pro_no" value="" id="pro_no">
                </FORM>
            </div>
        </div>
    </div>
    <!--Footer開始-->
	<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>
	<!-- 頁腳 -->
<!-- nav.js -->
<script src="<%=request.getContextPath() %>/js/shop.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.raty.min.js"></script>
<script src="<%=request.getContextPath() %>/js/popper.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-4.3.1.js"></script>
<script>
    $('#star').raty({
        size: 24,
        targetType: 'number',
        score: 1,
        starHalf: '<%=request.getContextPath()%>/images/icons/star-half.png',
        starOff: '<%=request.getContextPath()%>/images/icons/star-off.png',
        starOn: '<%=request.getContextPath()%>/images/icons/star-on.png',
        click: function (score) {
            $('starSub').val(score);
        }
    });

    $('.review').click((e) => {
        var pro_no = e.target.dataset.pro_no;
        console.log(pro_no);
        var order_id = e.target.dataset.order_id;
        console.log(order_id);
        $('#pro_no').val(pro_no);
        $('#order_id').val(order_id);
    });
</script>
</body>