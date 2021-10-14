<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.bike_rental.model.*" %>
<%@ page import="com.bike_style.model.*" %>
<%@ page import="com.bike_rental_style.model.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="bikeStyleSvc" class="com.bike_style.model.Bike_styleService"/>
<jsp:useBean id="bikeRentalStyleSvc" class="com.bike_rental_style.model.Bike_rental_styleService"/>
<%


    //暫時使用假資料
    String bike_rental_no = (String) request.getAttribute("bike_rental_no");
    Bike_rentalService bike_rentalSVC = new Bike_rentalService();
    Bike_rentalVO bike_rentalVO = bike_rentalSVC.getOneBike_rental(bike_rental_no);

    request.setAttribute("bike_rentalVO", bike_rentalVO);

    //取得租車店已經有的車型
    List<Bike_rental_styleVO> Bike_rental_style_list = bikeRentalStyleSvc.findByBike_Rental(bike_rental_no);
    List<String> Bk_sty_no_List = new ArrayList<String>();
    for (Bike_rental_styleVO bike_rental_styleVO : Bike_rental_style_list) {

        Bk_sty_no_List.add(bike_rental_styleVO.getBk_sty_no());
    }

    pageContext.setAttribute("Bk_sty_no_List", Bk_sty_no_List);
%>

<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!--網址縮圖-->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
    <link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/addBike_rental_style.css">
    <script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
    <title>Bike Seeker Manager</title>
</head>
<body>

<div class="container-fluid">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <jsp:include page="/back-end/include/NavBar.jsp" flush="true"/>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <form method="post" action="<%=request.getContextPath()%>/bike_rental_style/bike_rental_style.do" name="form1">

                <div class="row align-items-center justify-content-center">

                    <div class="col-12 text-center">
                        <div class="display-3" id="Title">新增${bike_rentalVO.bk_rt_name}的車型</div>
                    </div>

                    <div class="col-3 text-center mb-4" id="Control-panel">

                        <div class="row justify-content-center">

                            <div class="col-6">
                                <input type="hidden" name="action" value="insert">
                                <input type="hidden" name=bike_rental_no value="${bike_rentalVO.bk_rt_no}">
                                <input type="submit" class="btn btn-outline-info" value="送出">
                            </div>

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

                    <div class="col-10 align-middle" id="ManagerListTable">

                        <table class="table table-striped text-center">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col" class="text-nowrap"></th>
                                <th scope="col" class="text-nowrap">圖片</th>
                                <th scope="col" class="text-nowrap">車型</th>
                                <th scope="col" class="text-nowrap">說明</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="bike_styleVO" items="${bikeStyleSvc.allBike_style}">
                                <tr>
                                    <th scope="row">
                                        <input type="checkbox" <c:if test="${fn:contains(bikeRentalStyleSvc.findByBike_Rental(requestScope.bike_rentalVO.bk_rt_no), bike_styleVO.bike_sty_no)}"> checked </c:if> data-toggle="toggle" data-onstyle="success" data-on=" " data-off=" " name="bikeStyle" value="${bike_styleVO.bike_sty_no}">

                                    </th>
                                    <td><img src="<%= request.getContextPath()%>/bike_rental_style/bike_rental_style.do?action=showImg&bk_sty_no=${bike_styleVO.bike_sty_no}" alt="車型相片"></td>
                                    <td class="text-nowrap">${bike_styleVO.bike_sty_name}</td>
                                    <td>${bike_styleVO.bike_sty_spec}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>

                </div>
            </form>
        </main>
    </div>
</div>

<script>

    //員工編號下拉選單
    $("#ManagerNoDropdown").change(function () {
        $("#ManagerNoForm").submit();
    });


    //搜尋結果跳頁下拉選單

    $("#SelectPage").change(function () {
        let pageIndex = $(this).children("option:selected").val();
        location.href = location.pathname + "?whichPage=" + pageIndex;
    });

    //查詢失敗的訊息
    <c:if test="${not empty requestScope.errorMsgs}">
    swal.fire({
        icon: 'error',
        title: '唉唷',
        text: "<c:forEach var="msg" items="${requestScope.errorMsgs}">${msg}</c:forEach>"
    });
    <c:remove var="errorMsgs" scope="request" />
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
<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>