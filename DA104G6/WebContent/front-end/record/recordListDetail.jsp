<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.MemVO"%>

<%
    MemVO memVO = (MemVO) session.getAttribute("memVO");
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
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/recordDetail.css">
	
    <script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>

    <!--上線時改位址-->

    <title>Bike Seeker</title>

</head>
<body>
<!--導覽列開頭-->
<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />

<!--Container開頭-->
<div class="container-fluid h-100">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <div class="col-7" id="RecordMap">

            <div id="map"></div>

        </div>

        <div class="col-5" id="RecordDetail">

            <div class="row align-items-center justify-content-center">

                <div class="col-12" id="RecordPicCol">

                    <img src="<%= request.getContextPath()%>/record/record.do?action=showImg&mem_no=${recordVO.mem_no}&start_time=${recordVO.start_time}" id="RecordPic">

                </div>

                <div class="col-10 mt-2">
                    <fmt:parseDate value="${requestScope.recordVO.start_time}" var="start_date" pattern="yyyy-MM-dd HH:mm:ss" />
                    <fmt:parseDate value="${requestScope.recordVO.end_time}" var="end_date" pattern="yyyy-MM-dd HH:mm:ss" />
                    <table class="table table-borderless">
                        <tr><th>路線名</th><td>${routeSvc.getOneRoute(requestScope.recordVO.route_no).route_name}</td></tr>
                        <tr><th>開始時間</th><td><fmt:formatDate value="${start_date}" pattern="yyyy/MM/dd HH:mm:ss" /></td></tr>
                        <tr><th>結束時間</th><td><fmt:formatDate value="${end_date}" pattern="yyyy/MM/dd HH:mm:ss" /></td></tr>
                        <tr><th>花費時間</th><td><script>document.write((Math.floor(parseInt(${requestScope.recordVO.duration}) / 3600))  + ":" + (Math.floor(parseInt(${requestScope.recordVO.duration}) / 60 % 60)) + ":" + ${requestScope.recordVO.duration} % 60);</script></td></tr>
                        <tr><th>距離</th><td>${requestScope.recordVO.distance}公里</td></tr>
                        <tr><th>海拔高度</th><td>${requestScope.recordVO.elevation}公尺</td></tr>
                    </table>

                </div>

                <div class="col-12 text-center mt-3" id="RecordDetailBtn">

                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#ModalId1">修改封面</button>
                    <a href="<%= request.getContextPath()%>/front-end/record/recordListAll.jsp"><button type="button" class="btn btn-primary">其他里程</button></a>

                    <div class="routeModal modal fade " id="ModalId1" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <form action="<%= request.getContextPath()%>/record/record.do" method="post" class="m-0" enctype="multipart/form-data">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="ModalTitle1" style="color:#000;">修改封面圖片</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body text-center">
                                        <img src="<%= request.getContextPath()%>/record/record.do?action=showImg&mem_no=${requestScope.recordVO.mem_no}&start_time=${requestScope.recordVO.start_time}" alt="封面圖預覽" class="mx-auto" style="max-width: 150px;max-height: 150px;border: 5px solid #000;border-radius: 10px;" id="CoverPic1" >
                                        <div class="form-group mt-2 text-center">
                                            <label class="btn btn-info">
                                                <input id="InputCoverPic1" style="display:none;" type="file" name="record_pic" accept="image/*">
                                                <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" style="width: 30px;height: 30px;border:0" class="fileUploadBtn"> 上傳圖片
                                            </label>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                            <input type="hidden" name="action" value="update">
                                            <input type="hidden" name="mem_no" value="${requestScope.recordVO.mem_no}">
                                            <input type="hidden" name="start_time" value="${requestScope.recordVO.start_time}">
                                            <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
                                        <button type="submit" class="btn btn-primary" style="width: 58px;height: 38px; font-size: 16px">修改</button>

                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>

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
    <c:if test="${not empty requestScope.updateSuccess}">
    swal.fire({
        icon: 'success',
        title: '叮鈴',
        text: "${requestScope.updateSuccess}"
    });
    <c:remove var="updateSuccess" scope="request" />
    </c:if>

    //地圖

    var map;
    function initMap() {
        var location = {lat: 24.272311, lng: 120.740825};
        var directionsService = new google.maps.DirectionsService();//繪製服務
        var directionsDisplay = new google.maps.DirectionsRenderer();//繪製路線
        map = new google.maps.Map(document.getElementById('map'), {
            center: location,
            zoom: 16,
        });

        var marker = new google.maps.Marker({
            position: location,
            map: map,
            animation: google.maps.Animation.BOUNCE,
        });
        directionsDisplay.setMap(map);
        
        $(document).ready(function (){
        	var route = '${requestScope.recordVO.route_no}';
        	if(route != ''){
        		$.ajax({
            		url: '<%=request.getContextPath()%>/route/route.do',
            		type: 'post',
            		data: {
            			action: 'getgpx',
            			route_no: route
            		},
            		success: (data) => {
            			console.log(data);
            			var jstr = JSON.stringify(data);
            			var gpxString = JSON.parse(jstr);
            			var gpx = JSON.parse(gpxString)
            			directionsDisplay.setDirections(gpx);
            		},
            		error: function() {
            			console.log('error');
            		}
            	});
        	}
        });
    }

</script>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDim1wrKK_MVnCI_D6vwveKjR-9rv_cDts&callback=initMap" async defer></script>
<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>