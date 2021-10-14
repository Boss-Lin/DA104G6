<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.product.model.*" %>
<%@ page import="com.pro_category.model.*" %>

<%
    ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>
<jsp:useBean id="proSvc" scope="page" class="com.pro_category.model.Pro_categoryService"/>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!--網址縮圖-->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/update_product_input.css">
    <script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <title>Bike Seeker Management</title>
</head>
<body>


<div class="container-fluid">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <jsp:include page="/back-end/include/NavBar.jsp" flush="true"/>


        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

            <form action="<%=request.getContextPath() %>/product/product.do" method="post" enctype="multipart/form-data"
                  class="m-0">

                <div class="row align-items-center justify-content-center">

                    <div class="col-12 text-center">
                        <div class="display-3" id="Title">商品修改</div>
                    </div>


                    <div class="col-3 align-middle text-center" id="ManagerProfile">

                        <h2>商品編號 : <%=productVO.getPro_no()%>
                        </h2>


                        <div class="form-group text-left">
                            <label for="InputName">產品名稱</label>
                            <input type="text" class="form-control" name="product" id="InputName" placeholder="請輸入產品名稱"
                                   value="<%=productVO.getProduct()%>">
                        </div>

                        <div class="form-group text-left">
                            <label for="InputPrice">價錢</label>
                            <input type="text" class="form-control" name="price" id="InputPrice"
                                   value="<%=productVO.getPrice()%>">
                        </div>

                        <div class="form-group text-left">
                            <label for="InputMessage">商品訊息</label><br>
                            <textarea id="InputMessage" name="message"
                                      style="width:335.5px;height:75px;"><%=productVO.getMessage()%></textarea>
                        </div>

                        <div class="form-group">
                            <div class="row">
                                <div class="col-3 text-left">
                                    狀態
                                </div>
                                <div class="col-6">
                                    <select class="custom-select" size="1" name="status">
                                        <option value="1"  ${(productVO.status == 1)?'selected':'' }>下架</option>
                                        <option value="2"  ${(productVO.status == 2)?'selected':'' }>信用卡購買</option>
                                        <option value="3"  ${(productVO.status == 3)?'selected':'' }>里程兌換</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="row">
                                <div class="col-3 text-left">
                                    類別
                                </div>
                                <div class="col-6">
                                    <select class="custom-select" size="1" name="category_no">
                                        <c:forEach var="pro_categoryVO" items="${proSvc.all}">
                                        <option value="${pro_categoryVO.category_no}" ${(productVO.category_no==pro_categoryVO.category_no)? 'selected':'' } >${pro_categoryVO.category}
                                            </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="form-group text-left">
                            <label for="InputScore">分數</label>
                            <input type="text" class="form-control" name="score" id="InputScore" readonly
                                   value="<%=productVO.getScore()%>">
                        </div>

                        <div class="form-group text-left">
                            <label for="InputScore_Peo">評分人數</label>
                            <input type="text" class="form-control" name="score_peo" id="InputScore_Peo" readonly
                                   value="<%=productVO.getScore_peo()%>">
                        </div>

                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="pro_no" value="<%=productVO.getPro_no()%>">
                        <input type="hidden" name="requestURL" value="<%=request.getAttribute("requestURL")%>">
                        <input type="hidden" name="whichPage" value="<%=request.getAttribute("whichPage")%>">
                        <button class="btn btn-info" type="submit">送出修改</button>

                    </div>

                    <div class="col-1"></div>

                    <div class="col-3 align-middle text-center" id="ManagerPermission">
                        <h2 class="text-center">圖片上傳</h2>

                        <img src="<%=request.getContextPath() %>/product/product.do?getImg=${requestScope.productVO.pro_no}"
                             alt="商品圖片預覽" id="ProductPic" class="mx-auto mb-3">

                        <div class="form-group mt-2 text-center">
                            <label class="btn btn-info">
                                <input id="InputProductPic" style="display:none;" type="file" name="pic"
                                       accept="image/*">
                                <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕"
                                     id="UploadButton"> 上傳圖片
                            </label>
                        </div>

                        <c:if test="${not empty requestScope.errorMsgs}">
                            <span>遭遇錯誤:</span> <br>
                            <div style="color: darkred">
                                <c:forEach var="msg" items="${requestScope.errorMsgs}">
                                    ${msg}
                                </c:forEach>
                            </div>
                        </c:if>


                    </div>


                </div>
            </form>
        </main>

    </div>
</div>


<script>

    // 商品上傳圖片預覽
    function readURL(input) {
        console.log(input);
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function (e) {
                $('#ProductPic').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        } else {
            $('#ProductPic').attr("src", "<%=request.getContextPath() %>/product/product.do?getImg=${requestScope.productVO.pro_no}");
        }
    }

    $("#InputProductPic").change(function () {
        readURL(this);
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