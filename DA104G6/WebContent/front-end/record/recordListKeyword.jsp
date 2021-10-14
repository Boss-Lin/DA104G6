<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.MemVO"%>
<%@ page import="com.record.model.RecordVO" %>
<%@ page import="java.util.List" %>

<%
    MemVO memVO = (MemVO) session.getAttribute("memVO");
    List<RecordVO> list = (List<RecordVO>) request.getAttribute("list");
%>

<jsp:useBean id="routeSvc" scope="request" class="com.route.model.RouteService" />

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
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/recordList.css">

    <script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>

    <!--上線時改位址-->

    <title>Bike Seeker</title>

</head>
<body>
<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />

<!--Container開頭-->
<div class="container-fluid h-100">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <main role="main" class="col-10 px-4" id="RecordPage">

            <div class="row align-items-center justify-content-center p-4">

                <div class="col-12 text-center mb-4" id="Control-panel">

                    <div class="row align-items-center justify-content-center">

                        <div class="col-4 text-left">
                            <h2 class="display-4">我的里程</h2>
                        </div>

                        <div class="col-4">

                            <form action="<%= request.getContextPath()%>/record/record.do" method="get" class="m-0">

                                <div class="row align-items-end">
                                    <div class="col-8">
                                        <input type="text" class="form-control" name="route_name" id="InputRouteName" placeholder="搜尋路線名稱">
                                    </div>

                                    <div class="col-4 text-left pl-0 pr-0">
                                        <input type="hidden" name="action" value="fuzzy_Search">
                                        <input type="hidden" name="mem_no" value=${sessionScope.memVO.mem_no}>
                                        <button type="submit" class="btn btn-outline-info mt-2">搜尋</button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="col-4">
                            <button class="btn btn-outline-info mt-2" onclick="location.href='/DA104G6/front-end/record/recordListAll.jsp';">顯示全部里程</button>
                        </div>


                    </div>

                </div>

                <div class="w-100"><hr style="border-color: #0c5460; border-width: 3px; width: 93em"></div>

                <div class="col-12 text-center align-middle" id="RecordList">

                    <c:if test="${not empty requestScope.list}">
                    <table class="table table-striped text-center">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">封面圖</th>
                            <th scope="col">路線名</th>
                            <th scope="col">開始時間</th>
                            <th scope="col">結束時間</th>
                            <th scope="col">花費時間(時分秒)</th>
                            <th scope="col">距離</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>

                        <tbody>
<%--                        <%@ include file="page1.file" %>--%>
<%--                        <c:forEach var="recordVO" items="${requestScope.list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="count">--%>
                            <c:forEach var="recordVO" items="${requestScope.list}" varStatus="count">
                            <fmt:parseDate value="${recordVO.start_time}" var="start_date" pattern="yyyy-MM-dd HH:mm:ss" />
                            <fmt:parseDate value="${recordVO.end_time}" var="end_date" pattern="yyyy-MM-dd HH:mm:ss" />
                            <tr>
                                <td><img src="<%= request.getContextPath()%>/record/record.do?action=showImg&mem_no=${recordVO.mem_no}&start_time=${recordVO.start_time}" alt="路線封面圖" style="max-width: 160px; max-height: 90px"></td>
                                <td>${routeSvc.getOneRoute(recordVO.route_no).route_name}</td>
                                <td><fmt:formatDate value="${start_date}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
                                <td><fmt:formatDate value="${end_date}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
                                <td><script>document.write((Math.floor(parseInt(${recordVO.duration}) / 3600))  + ":" + (Math.floor(parseInt(${recordVO.duration}) / 60 % 60)) + ":" + ${recordVO.duration} % 60);</script></td>
                                <td>${recordVO.distance}公里</td>
                                <td>
                                    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#ModalId${count.index}">
                                        修改封面
                                    </button>
                                    <form action="<%= request.getContextPath()%>/record/record.do" method="get" class="m-0 mt-3">
                                        <input type="hidden" name="action" value="getOne_For_Detail">
                                        <input type="hidden" name="mem_no" value="${recordVO.mem_no}">
                                        <input type="hidden" name="start_time" value="${recordVO.start_time}">
                                        <button type="submit" class="btn btn-info">
                                            查看詳情
                                        </button>
                                    </form>
                                    <div class="routeModal modal fade " id="ModalId${count.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                            <div class="modal-content">
                                                <form action="<%= request.getContextPath()%>/record/record.do" method="post" class="m-0" enctype="multipart/form-data">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="ModalTitle${count.index}" style="color:#000;">修改封面圖片</h5>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <img src="<%= request.getContextPath()%>/record/record.do?action=showImg&mem_no=${recordVO.mem_no}&start_time=${recordVO.start_time}" alt="封面圖預覽" class="mx-auto" style="max-width: 150px;max-height: 150px;border: 5px solid #000;border-radius: 10px;" id="CoverPic${count.index}" >
                                                        <div class="form-group mt-2 text-center">
                                                            <label class="btn btn-info">
                                                                <input id="InputCoverPic${count.index}" style="display:none;" type="file" name="record_pic" accept="image/*">
                                                                <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" style="width: 30px;height: 30px;border:0" class="fileUploadBtn"> 上傳圖片
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="mem_no" value="${recordVO.mem_no}">
                                                        <input type="hidden" name="start_time" value="${recordVO.start_time}">
                                                        <button type="submit" class="btn btn-primary">修改</button>

                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
<%--                    <%@ include file="keywordPage2.file" %>--%>
                    </c:if>

                    <c:if test="${empty requestScope.list}">

                        <img src="<%= request.getContextPath()%>/images/FailBike.gif" alt="失敗">
                        <h1>搜尋結果為空，換個關鍵字吧!</h1>

                    </c:if>

                </div>

            </div>

        </main>
    </div>

    <!--登入燈箱-->
    <div class="modal fade" id="LoginBox" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">登入會員</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    <form action="<%= request.getContextPath()%>/mem/memLogin.do" method="post" target="_blank">
                        帳號 : <input type="email" class="textBox" name="mem_email"><br><br>
                        密碼 : <input type="password" class="textBox" name="mem_psw"><br>
                        <p><a href="#">忘記密碼</a></p><br>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">登入</button>
                            <button type="button" onclick="location.href = '<%= request.getContextPath()%>/front-end/mem/addMem.jsp';" class="btn btn-secondary" data-dismiss="modal">註冊</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!--登入燈箱-->
</div>


<script>

    //封面圖預覽
    function readURL(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function(e) {
                $('#CoverPic1').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $('#InputCoverPic1').change(function () {
        readURL(this);
    });


    //修改成功的通知
    <c:if test="${not empty sessionScope.updateSuccess}">
    swal.fire({
        icon: 'success',
        title: '叮鈴',
        text: "${sessionScope.updateSuccess}"
    });
    <c:remove var="updateSuccess" scope="session" />
    </c:if>

</script>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>