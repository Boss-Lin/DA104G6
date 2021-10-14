<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bike_style.model.*" %>

<%
	Bike_styleService bike_styleproductSvc = new Bike_styleService();
    List<Bike_styleVO> list = bike_styleproductSvc.getAllBike_style();
    pageContext.setAttribute("list", list);
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
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/listAllBike_style.css">
    <script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>


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
                    <div class="display-3" id="Title">車型列表</div>
                </div>

                <div class="col-3 text-center" id="Control-panel">

                    <div class="row justify-content-center">

                         <div class="col-6">
                             <a href="<%= request.getContextPath()%>/back-end/bike_style/addBike_style.jsp?requestURL=<%= request.getServletPath()%>">
                                 <button type="button" class="btn btn-outline-info">新增車型</button>
                             </a>
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
                            <th scope="col" class="text-nowrap">車型編號</th>
                            <th scope="col" class="text-nowrap">車型名稱</th>
                            <th scope="col" class="text-nowrap">車型圖片</th>
                            <th scope="col" class="text-nowrap">車型簡介</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>

                        <tbody>
                        <%@ include file="page1.file" %>
                        <c:forEach var="bike_styleVO" items="${list}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                            <tr>
                                <th scope="row" class="text-nowrap">${bike_styleVO.bike_sty_no}</th>
                                <td class="text-nowrap">${bike_styleVO.bike_sty_name}</td>
                                <td><img src="<%=request.getContextPath() %>/bike_style/bike_style.do?getImg=${bike_styleVO.bike_sty_no}"></td>
                                <td>${bike_styleVO.bike_sty_spec}</td>
                                <td class="text-nowrap">
                                    <form action="<%=request.getContextPath()%>/bike_style/bike_style.do" method="post" class="m-0">
                                        <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
                                        <input type="hidden" name="whichPage" value="<%=whichPage%>">
                                        <input type="hidden" name="bike_sty_no" value="${bike_styleVO.bike_sty_no}">
                                        <input type="hidden" name="action" value="getOne_For_Update">
                                        <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
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

<script>

    //搜尋結果跳頁下拉選單

    $("#SelectPage").change(function () {
        let pageIndex = $(this).children("option:selected").val();
        location.href = location.pathname +"?whichPage=" + pageIndex;
    });

    //修改成功的訊息

    <c:if test="${not empty requestScope.updateSuccess}">
    swal.fire({
        icon: 'success',
        title: '怎麼那麼棒',
        text: '${requestScope.updateSuccess}'
    });
    <c:remove var="updateSuccess" scope="request" />
    </c:if>

    //新增成功的訊息

    <c:if test="${not empty requestScope.addSuccess}">
    swal.fire({
        icon: 'success',
        title: '熊蓋讚',
        text: '${requestScope.addSuccess}'
    });
    <c:remove var="addSuccess" scope="request" />
    </c:if>

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