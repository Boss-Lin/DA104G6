<%@page import="com.group.model.GroupVO" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    GroupVO groVO = (GroupVO) request.getAttribute("groVO");
    GroupVO second = (GroupVO) request.getAttribute("groVo");
    if (groVO == null) {
        groVO = second;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!---------------------------------------------網址縮圖----------------------------------------->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/update_Group_input.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-3.4.1.min.js"></script>
    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->

    <!-------------------------------------- 地址下拉式選單、日期等 套件  ---------------------------------->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.datetimepicker.css"/>
    <!-------------------------------------- 地址下拉式選單、日期等 套件  ---------------------------------->


    <title>更改行程</title>
</head>
<body>

<jsp:include page="/front-end/include/NavBar.jsp" flush="true"/>
<!------------------------------------------------------- 內容 ----------------------------------------------->


<!----------------------------------------------------- 更改輸入區塊 ------------------------------------------->

<div class="container-fluid">
    <form action="<%=request.getContextPath() %>/front-end/Group/group.do" method="post" enctype="multipart/form-data">
        <div class="row justify-content-around align-items-center" id="addGroupPage">

            <div class="col-6 p-5" id="LeftCol">

                <div class="row justify-content-around align-items-center">

                    <div class="col-12 text-center mb-5">

                        <h1 class="display-4" style="color: #2F4858">更改行程</h1>

                    </div>

                    <!------------------------------- 更改行程名稱 ----------------------------------------->

                    <div class="col-10 mb-5">
                        <label for="gro_name" id="gro_name">更改行程名稱 :</label>
                        <c:if test="${not empty errorMsgs}">
                            <font class="updateError">${errorMsgs.get("groNmaeErr")}</font>
                        </c:if>
                        <input type="text" class="form-control in" id="gro_name" placeholder="請輸入行程名稱" maxlength="10" name="gro_name"
                               value="<%= groVO.getGro_name() %>">
                    </div>

                    <!------------------------------- 更改行程名稱 ----------------------------------------->

                    <!---------------------------------- 更改上限 ------------------------------------------>

                    <div class="col-2 mb-5">
                        <label for="limit">更改上限 :</label>
                        <select class="custom-select in" name="peo_limit">
                            <option value="3">不限</option>
                            <% for (int i = 1; i <= 10; i++) { %>
                            <option value="<%= i %>" <%= (i == groVO.getPeo_limit()) ? "selected" : "" %>><%= i %></option>
                            <% } %>
                        </select>
                    </div>

                    <!---------------------------------- 更改上限 -------------------------------------------->


                    <!------------------------------- 更改行程地點 ----------------------------------------->

                    <div class="col-12 mb-5">
                        <label for="address">更改地點 :</label>
                        <c:if test="${not empty errorMsgs}">
                            <font class="updateError">${errorMsgs.get("musterErr")}</font>
                        </c:if>
                        <div id="twzipcode"></div>
                        <input type="text" class="form-control in" id="address"
                               placeholder="例: 屏東縣枋山鄉金龍二街六段707巷395弄451號" maxlength="30" name="muster" value="<%= groVO.getMuster() %>">
                    </div>

                    <!------------------------------- 更改行程地點 ----------------------------------------->


                    <!---------------------------------- 更改活動天數 --------------------------------------->

                    <div class="col-6 mb-5">
                        <label for="days">更改天數 :
                            <output id="choice"><%= groVO.getDuration() %>
                            </output>
                            天</label>
                        <input type="range" class="custom-range" min="1" max="20" step="1" id="days"
                               value="<%= (groVO.getDuration() != null)? groVO.getDuration() : 2 %>"
                               oninput="choice.value = parseInt(days.value)" name="duration">
                    </div>

                    <div class="w-100"></div>

                    <!---------------------------------- 更改活動天數 --------------------------------------->


                    <!---------------------------------- 更改集合時間 ----------------------------------------->

                    <div class="col-6 mb-5">
                        <label for="time">更改集合時間 :</label>
                        <c:if test="${not empty errorMsgs}">
                            <font class="updateError">${errorMsgs.get("timeErr")}</font>
                        </c:if>
                        <input type="text" class="form-control in" id="time" name="time"
                               value="<fmt:formatDate value="${groVO.time}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                    </div>

                    <!---------------------------------- 更改集合時間 ----------------------------------------->


                    <!---------------------------------- 更改報名截止時間 ------------------------------------------>

                    <div class="col-6 mb-5">
                        <label for="time">更改報名截止日 :</label>
                        <c:if test="${not empty errorMsgs}">
                            <font class="updateError">${errorMsgs.get("deadlineErr")}</font>
                        </c:if>
                        <input type="text" class="form-control in" id="deadline" name="deadline"
                               value="<fmt:formatDate value="${groVO.deadline}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                    </div>

                    <!---------------------------------- 新增報名截止時間 ------------------------------------------>


                    <!----------------------------------- 更改連絡電話 -------------------------------------------->

                    <div class="col-6 mb-3">
                        <label class="in" for="phone">更改連絡電話 :</label>
                        <c:if test="${not empty errorMsgs}">
                            <font class="updateError">${errorMsgs.get("phoneErr")}</font>
                        </c:if>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control in" id="phone" maxlength="10" name="phone"
                                   value="<%= groVO.getPhone() %>" placeholder="09xx-xxxxxx">

                        </div>
                    </div>

                    <div class="col-6"></div>

                    <!----------------------------------- 更改連絡電話 -------------------------------------------->


                    <!------------------------------------- 更改簡介 ---------------------------------------------->

                    <div class="col-12">
                        <label for="intro">更改行程簡介 :</label>
                        <textarea class="form-control is-valid updateInfo" id="intro"
                                  name="intro"><%= groVO.getIntro() == null ? "尚無簡介" : groVO.getIntro() %></textarea>
                        <div class="invalid-feedback">打幾個字介紹一下行程吧!</div>
                    </div>
                </div>
                <!------------------------------------- 更改簡介 ---------------------------------------------->
            </div>
            <div class="col-4" id="RightCol">

                <!--------------------------------------- 更改封面圖 ---------------------------------------------->

                <div class="col-12 titleimg">
                    <label class="custom-file-label" for="coverImg">封面圖</label>
                    <input type="file" class="custom-file-input" id="coverImg" onchange="show(this.id)"
                           name="cover_pic" value="">
                    <div class="imgcon">
                    	<c:if test="${not empty errorMsgs}">
                        	<font class="adderror">${errorMsgs.get("imgErr")}</font>
                    	</c:if>
                        <img src="<%= request.getContextPath() %>/front-end/Group/group.do?getImg=<%= groVO.getGro_no()%>"
                             id="showImg">
                    </div>
                </div>

                <!--------------------------------------- 更改封面圖 ---------------------------------------------->


                <!--------------------------------------- 更改路線--------------------------------------------->
                <jsp:useBean id="svc" scope="page" class="com.route.model.RouteService"/>
                <div class="col-12 googleMap mt-3">
                    <c:if test="${not empty errorMsgs}">
                        <font class="adderror">${errorMsgs.get("routeNoErr")}</font>
                    </c:if>
                    <select class="custom-select in" id="route-no" name="route_no">
                        <option value="請選擇">更改路線</option>
                        <c:forEach var="routevo" items="${svc.all}">
                            <option value="${routevo.route_no}" ${(groVO.route_no eq routevo.route_no)? 'selected' : ''}>${routevo.route_name}</option>
                        </c:forEach>
                    </select>
                    <div id="map"></div>
                </div>

                <!--------------------------------------- 更改路線-------------------------------------------->
            </div>

            <!------------------------------------------------ 送出 --------------------------------------->
            <div class="col-12 mt-5 mb-5 text-center">
                <input type="hidden" name="mem_no" value="<%= groVO.getMem_no() %>">
                <input type="hidden" name="gro_no" value="<%= groVO.getGro_no() %>">
                <button type="submit" class="btn btn-primary" name="action" value="update">更改行程</button>
                <a class="btn btn-info" href="<%=request.getContextPath()%>/front-end/mem/member.jsp" role="button">取消修改</a>
            </div>


        </div>
    </form>
</div>


<!----------------------------------------------------- 更改輸入區塊結束 ---------------------------------------->


<!------------------------------------------------------- 內容 ----------------------------------------------->

<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>

<!------------------------------------------------ bootstrap.js --------------------------------------------->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>

<!------------------------------------------------ 日期.js --------------------------------------------------->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap" async
        defer></script>
<script src="<%= request.getContextPath()%>/js/jquery.datetimepicker.full.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetAlert2 9.5.2.js"></script>

<script>
	window.onload = initMap;
    var myInitCenter = {lat: 24.9680014, lng: 121.1900142};
    function initMap() {
        var map;//地圖物件
        var directionsService = new google.maps.DirectionsService();//繪製服務
        var directionsDisplay = new google.maps.DirectionsRenderer();//繪製路線

        map = new google.maps.Map(document.getElementById('map'), {
            center: myInitCenter,
            zoom: 12
        });
        directionsDisplay.setMap(map);
      //把路線圖帶回來
        $(document).ready(function (){
        	var route = '<%=groVO.getRoute_no()%>';
        	if(route != undefined){
        		$.ajax({
            		url: '<%=request.getContextPath()%>/route/route.do',
            		type: 'post',
            		data: {
            			action: 'getgpx',
            			route_no: route
            		},
            		success: (data) => {
            			if(data != ''){
            				var jstr = JSON.stringify(data);
                			var gpxString = JSON.parse(jstr);
                			var gpx = JSON.parse(gpxString)
                			directionsDisplay.setDirections(gpx);
            			}
            		},
            		error: function() {
            			console.log('error');
            		}
            	})
        	}
        });
        
      //取出gpx文字
        $('#route-no').change(function (){
        	var route = this.value;
        	console.log('aaa');
        	$.ajax({
        		url: '<%=request.getContextPath()%>/route/route.do',
        		type: 'post',
        		data: {
        			action: 'getgpx',
        			route_no: route
        		},
        		success: (data) => {
        			var jstr = JSON.stringify(data);
        			var gpxString = JSON.parse(jstr);
        			var gpx = JSON.parse(gpxString)
        			directionsDisplay.setDirections(gpx);
        		},
        		error: function() {
        			console.log('error');
        		}
        	})
        }); 

    }

    //      1.以下為某一天之前的日期無法選擇
    var somedate1 = new Date();
    $('#time').datetimepicker({
        format: 'Y-m-d H:i:s',
        beforeShowDay: function (date) {
            if (date.getYear() < somedate1.getYear() ||
                (date.getYear() == somedate1.getYear() && date.getMonth() < somedate1.getMonth()) ||
                (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
            ) {
                return [false, ""]
            }
            return [true, ""];
        }
    });

    //     2.以下為某一天之後的日期無法選擇
    var somedate2 = null;
    if ($("#time").val() != '') {
        let dead = $("#time").val().substring(0, 10)
        somedate2 = new Date(dead);
        somedate2.setDate(somedate2.getDate() - 2);
    }

    $("#time").change(function () {
        let dead = $("#time").val().substring(0, 10)
        $('#deadline').val('');
        somedate2 = new Date(dead);
        somedate2.setDate(somedate2.getDate() - 2);
    });


    $('#deadline').datetimepicker({
    	format: 'Y-m-d H:i:s',
       	beforeShowDay: function(date) {
        if (  date.getYear() <  somedate1.getYear() || 
        	(date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        	(date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        		||
        	date.getYear() >  somedate2.getYear() || 
        	(date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        	(date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        ) {
        return [false, ""]
        }
        return [true, ""];
        }});

    function show(e) {
        let obj = document.getElementById(e);
        console.log(obj.files[0]);
        let img = document.getElementById("showImg");
        img.src = window.URL.createObjectURL(obj.files[0]);
    }


    function show(e) {
        let obj = document.getElementById(e);
        let img = document.getElementById("showImg");
        img.src = window.URL.createObjectURL(obj.files[0]);
    }

    <!--function-->
    //地址下拉式選單

    //選單事件註冊
    $(document).ready(function () {
        $("#twzipcode select").change(function () {
            $("#address").val($('#twzipcode :selected').text()); //事件發生時把選單數值填入下方輸入盒
        });

    });

    //地址屬性設定
    $("#twzipcode").twzipcode({
        zipcodeIntoDistrict: true, // 郵遞區號自動顯示在地區
        countyName: "city", // 自訂城市 select 標籤的 name 值
        districtName: "town" // 自訂地區 select 標籤的 name 值
    });

    <c:if test="${not empty conflict}">
    swal.fire({
        icon: 'error',
        title: '時間有衝突!!，已有參加的行程',
        html: '${conflict}'
    });
    <% request.removeAttribute("conflict"); %>
    </c:if>
</script>
</body>
</html>