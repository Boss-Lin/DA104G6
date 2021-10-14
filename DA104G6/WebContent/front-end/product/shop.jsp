<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.product.model.*" %>
<%@ page import="com.pro_category.model.*" %>


<%
    ProductService productSvc = new ProductService();
    List<ProductVO> list = productSvc.getStatus(2);
    pageContext.setAttribute("list", list);
%>

<jsp:useBean id="pro_categorySvc" class="com.pro_category.model.Pro_categoryService" />

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/shop.css">
    <script src="<%=request.getContextPath() %>/js/jquery-slim-3.3.1.js"></script>
    <script src="<%=request.getContextPath() %>/js/sweetAlert2%209.5.2.js"></script>
    <!--上線時改位址-->


    <title>SHOP</title>
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

    <!--商品詳情-->
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
        <div class="col-8 mt-3">
            <div class="row">

                <%@ include file="page1.file" %>
                <c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1 %>">
                    <div class="col-3 mb-4 product">
                        <a href="<%=request.getContextPath() %>/front-end/product/product.jsp?pro_no=${productVO.pro_no}">
                            <div class="item">
                                <figure>
                                        <img src="<%=request.getContextPath() %>/product/product.do?getImg=${productVO.pro_no}">
                                </figure>
                                <div class="row">
                                    <div class="col-12">
                                        <h4 class="text-left" style="height: 56px">${productVO.product}</h4>
                                    </div>
                                    <div class="col-12">
                                        <h6 class="text-left" style="color: #666666">${pro_categorySvc.getOnePro_category(productVO.category_no).category}</h6>
                                    </div>
                                    <div class="col-12">
                                        <h5 class="text-left" style="color: #0f6674">$${productVO.price}</h5>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>

            </div>
        </div>
        <div class="col-2"></div>
    </div>
    <!--分頁-->
    <div class="row" id="pagination">
        <div class="col-12">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">

                    <%@ include file="page2.file" %>

                </ul>
            </nav>
        </div>
    </div>
    <!-- 頁腳 -->
    <jsp:include page="/front-end/include/Footer.jsp" flush="true"/>

<script>

    <c:if test="${not empty requestScope.success}">

    swal.fire({
        icon: 'success',
        text: '${requestScope.success}'
    });
    <c:remove var="success" scope="request" />
    </c:if>


</script>

<!-- nav.js -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath() %>/js/shop.js"></script>
<script src="<%=request.getContextPath() %>/js/popper.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-4.3.1.js"></script>
<!-- nav.js -->
</body>
</html>