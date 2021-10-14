<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bike_style.model.*" %>

<%
	Bike_styleVO bike_styleVO = (Bike_styleVO) request.getAttribute("bike_styleVO");
%>
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

            <form action="<%=request.getContextPath() %>/bike_style/bike_style.do" method="post" enctype="multipart/form-data"
                  class="m-0">

                <div class="row align-items-center justify-content-center">

                    <div class="col-12 text-center">
                        <div class="display-3" id="Title">車型修改</div>
                    </div>


                    <div class="col-3 align-middle text-center" id="ManagerProfile">

                        <h2>車型編號 : <%=bike_styleVO.getBike_sty_no() %></h2>


                        <div class="form-group text-left mt-5">
                            <label for="InputName">車型名稱</label>
                            <input type="text" class="form-control" name="bike_sty_name" id="InputName" placeholder="請輸入車型名稱"
                                   value="<%=bike_styleVO.getBike_sty_name() %>">
                        </div>

                        <div class="form-group text-left">
                            <label for="InputSpec">車型簡介</label>
                            <textarea id="InputSpec" name="bike_sty_spec" style="width:335.5px;height:150px;"><%=bike_styleVO.getBike_sty_spec() %></textarea>
                        </div>

                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="bike_sty_no" value="<%=bike_styleVO.getBike_sty_no() %>">
                        <input type="hidden" name="requestURL" value="<%=request.getAttribute("requestURL")%>">
                        <input type="hidden" name="whichPage" value="<%=request.getAttribute("whichPage")%>">
                        <button class="btn btn-info" type="submit">送出修改</button>

                    </div>

                    <div class="col-1"></div>

                    <div class="col-3 align-middle text-center" id="ManagerPermission">
                        <h2 class="text-center">圖片上傳</h2>

                        <img src="<%=request.getContextPath() %>/bike_style/bike_style.do?getImg=${requestScope.bike_styleVO.bike_sty_no}"
                             alt="車型圖片預覽" id="ProductPic" class="mx-auto mb-3">

                        <div class="form-group mt-2 text-center">
                            <label class="btn btn-info">
                                <input id="InputProductPic" style="display:none;" type="file" name="bike_sty_pic"
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