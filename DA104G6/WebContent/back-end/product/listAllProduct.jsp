<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.product.model.*" %>

<%
    ProductService productSvc = new ProductService();
    List<ProductVO> list = productSvc.getAll();
    pageContext.setAttribute("list", list);
%>
<jsp:useBean id="pro_categorySvc" scope="page" class="com.pro_category.model.Pro_categoryService"/>

<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!--網址縮圖-->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/listAllProduct.css">
    <script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>


    <title>Bike Seeker Management</title>
</head>
<body>


<div class="container-fluid">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <jsp:include page="/back-end/include/NavBar.jsp" flush="true"/>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

            <div class="row align-items-center justify-content-center">

                <div class="col-12 text-center">
                    <div class="display-3" id="Title">商品列表</div>
                </div>

                <div class="col-3 text-center" id="Control-panel">

                    <div class="row justify-content-center">

                        <div class="col-6">
                            <button class="btn btn-outline-info" onclick="location.href='<%=request.getContextPath()%>/back-end/product/addProduct.jsp';">新增商品</button>
                        </div>


                    </div>

                </div>

                <div class="w-100">
                    <hr style="border-color: #EAB965; border-width: 5px; width: 80em">
                </div>

                <div class="col-10 align-middle" id="ProductListTable">

                    <table class="table table-striped text-center">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">商品編號</th>
                            <th scope="col">商品名稱</th>
                            <th scope="col">圖片</th>
                            <th scope="col">價錢</th>
                            <th scope="col">分數</th>
                            <th scope="col">評分人數</th>
                            <th scope="col">類別</th>
                            <th scope="col">狀態</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>

                        <tbody>
                        <%@ include file="page1.file" %>
                        <c:forEach var="productVO" items="${list}" begin="<%=pageIndex%>"
                                   end="<%=pageIndex+rowsPerPage-1%>">
                            <tr>
                                <th scope="row">${productVO.pro_no}</th>
                                <td>${productVO.product}</td>
                                <td><img src="<%=request.getContextPath() %>/product/product.do?getImg=${productVO.pro_no}" style="width: 100px;height: 100px;"></td>
                                <td>$${productVO.price}</td>
                                <td>${productVO.score}</td>
                                <td>${productVO.score_peo}</td>
                                <td><c:forEach var="pro_categorytVO" items="${pro_categorySvc.all}">
                                    <c:if test="${productVO.category_no==pro_categorytVO.category_no}">
                                        ${pro_categorytVO.category}
                                    </c:if>
                                </c:forEach>
                                </td>
                                <td>${applicationScope.productStatus[productVO.status]}</td>
                                <td>
                                    <form action="<%=request.getContextPath()%>/product/product.do" method="post"
                                          class="m-0">
                                        <input type="hidden" name="requestURL"
                                               value="<%=request.getServletPath()%>">
                                        <input type="hidden" name="whichPage" value="<%=whichPage%>">
                                        <input type="hidden" name="pro_no" value="${productVO.pro_no}">
                                        <input type="hidden" name="action" value="getOne_For_Update">
                                        <button type="submit" class="btn btn-info">修改</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%@ include file="page2.file" %>

                </div>

            </div>

        </main>
    </div>
</div>

<script type="text/javascript">
	//搜尋結果跳頁下拉選單
	
	$("#SelectPage").change(function () {
		let pageIndex = $(this).children("option:selected").val();
		location.href = location.pathname +"?whichPage=" + pageIndex;
	});
</script>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>