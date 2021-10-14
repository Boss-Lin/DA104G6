<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.bike_rental.model.*" %>

<%
    Bike_rentalVO bike_rentalVO = (Bike_rentalVO) request.getAttribute("bike_rentalVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic_Manager.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/addbike_rental.css">

    <!--JQuery-->
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>

    <title>Bike Seeker Management</title>
</head>
<body>


<div class="container-fluid">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <jsp:include page="/back-end/include/NavBar.jsp" flush="true"/>


        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <form action="<%= request.getContextPath()%>/bike_rental/bike_rental.do" method="post" enctype="multipart/form-data" class="m-0">

            <div class="row align-items-center justify-content-center">

                <div class="col-12 text-center">
                    <div class="display-3" id="Title">新增租車店</div>
                </div>


                <div class="col-3 align-middle text-center" id="ManagerProfile">


                        <div class="form-group text-left">
                            <label for="InputName">出租店店名</label>
                            <input type="text" class="form-control" name="bk_rt_name" id="InputName" placeholder="請輸入店名" value="${param.bk_rt_name}">
                            <span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.errorMsgs.bk_rt_name}">
                                        <c:out value="${requestScope.errorMsgs.bk_rt_name}" />
                                    </c:if>
								</span>
                        </div>

                        <div class="form-group text-left">
                            <label for="InputPhone">電話</label>
                            <input type="tel" class="form-control" name="bk_rt_phone" id="InputPhone" value="${param.bk_rt_phone}" maxlength="10">
                            <span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.errorMsgs.bk_rt_phone}">
                                        <c:out value="${requestScope.errorMsgs.bk_rt_phone}" />
                                    </c:if>
								</span>
                        </div>

                        <div class="form-group text-left">
                            地址
                            <div id="twzipcode"></div>
                            <input type="text" class="form-control" name="bk_rt_address" id="InputAddress" value="${param.bk_rt_address}" />
                            <span style="color: #cc2357; font-size: 14px">
                            <c:if test="${not empty requestScope.errorMsgs.bk_rt_address}">
                                <c:out value="${requestScope.errorMsgs.bk_rt_address}" />
                            </c:if>
                        </span>

                        </div>

                        <div class="form-group text-left">
                            <label for="InputLon">經度</label>
                            <input type="number" class="form-control" readonly name="lon" id="InputLon" placeholder="請輸入經度" value="${param.lon}">
                            <span style="color: #cc2357; font-size: 14px;">
									<c:if test="${not empty requestScope.errorMsgs.lon}">
                                        <c:out value="${requestScope.errorMsgs.lon}" />
                                    </c:if>
								</span>
                        </div>

                        <div class="form-group text-left">
                            <label for="InputLat">緯度</label>
                            <input type="number" class="form-control" readonly name="lat" id="InputLat" placeholder="請輸入緯度" value="${param.lat}">
                            <span style="color: #cc2357; font-size: 14px">
									<c:if test="${not empty requestScope.errorMsgs.lat}">
                                        <c:out value="${requestScope.errorMsgs.lat}" />
                                    </c:if>
								</span>
                        </div>



                        <div class="form-group text-left">
                            <label for="InputSpec">說明</label><br>
                            <textarea id="InputSpec" name="bk_rt_spec" style="width:335.5px;height:75px;">${param.bk_rt_spec}</textarea>
                        </div>

                        <input type="hidden" name="action" value="insert">
                        <button class="btn btn-info" type="submit">下一步 : 選擇車型</button>
                    <a href="listAllbike_rental.jsp" ><button type="button" class="btn btn-secondary">回上頁</button></a>


                </div>

                <div class="col-1">

                    <span style="color: #cc2357; font-size: 14px">
						<c:if test="${not empty requestScope.errorMsgs.Exception}">
                            <c:out value="${requestScope.errorMsgs.Exception}" />
                        </c:if>
                    </span>

                </div>

                <div class="col-5 align-middle text-center" id="ManagerPasswordUpdate">
                    <h2 class="text-center">租車店照片</h2>

                    <img src="<%= request.getContextPath()%>/images/icons/DefaultProduct.png" alt="租車店照片預覽" id="ManagerProfilePic" class="mx-auto mt-4">

                    <div class="form-group mt-4 text-center">
                        <label class="btn btn-info">
                            <input id="InputProfilePic" style="display:none;" type="file" name="bk_rt_pic" accept="image/*">
                            <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" id="UploadButton"> 上傳圖片
                        </label>
                    </div>

                    <div class="row justify-content-around align-items-center">
                        <div class="col-8">
                            <input class="form-control mr-sm-2" type="search" placeholder="有請谷歌大神" id='searchInput'>
                        </div>
                        <div class="col-3">
                            <button class="btn btn-info my-2 my-sm-0" type="button" id='searchButton'>搜尋</button>
                        </div>
                    </div>

                    <div id='map'></div>

                </div>	
            </div>
            </form>
        </main>
    </div>
</div>


<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap" async defer></script>
<script>

	var myInitCenter = {lat: 24.9680014, lng: 121.1900142};
	window.onload = initMap;
	function initMap() {
    	var map;//地圖物件
    	var location;//緯經度
    	var geocoder = new google.maps.Geocoder();//查詢地址
    	map = new google.maps.Map(document.getElementById('map'), {
            center: myInitCenter,
            zoom: 12
        });
    	
    	
    	google.maps.event.addListener(map, 'click', (e) => {
    		location = e.latLng;
    		var x = location.lat();
    		var y = location.lng();
    		document.getElementById('InputLat').value = x;
    		document.getElementById('InputLon').value = y;
    		markerCreator(location);
    	});
    	
    	//建立marker
    	var markerdyn;
        function markerCreator(location){
            if(markerdyn == null){
                markerdyn = new google.maps.Marker({
                position: location,
                map: map,
                icon: '<%=request.getContextPath()%>/images/icons/cycling.png',
                })
            }else
                markerdyn.setPosition(location);
        }
        
        //送出查詢
        google.maps.event.addDomListener(document.getElementById('searchButton'), 'click', (e) => {
        	selectAddress(geocoder, map);
        });
        
        //地址查詢
        function selectAddress(geocoder, map){
        	var inputAddress = document.getElementById('searchInput').value;
        	console.log(inputAddress);
        	geocoder.geocode({'address' : inputAddress}, function(results, status) {
        		if(status === 'OK'){
        			map.setCenter(results[0].geometry.location);
        			markerCreator(results[0].geometry.location);
        			document.getElementById('InputLat').value = results[0].geometry.location.lat();
            		document.getElementById('InputLon').value = results[0].geometry.location.lng();
        		}
        	});
        }
	}



    //地址下拉式選單

    //選單事件註冊
    $(document).ready(function () {
        $("select").change(function () {
            $("#InputAddress").val($('#twzipcode :selected').text()); //事件發生時把選單數值填入下方輸入盒
        });

    });

    //地址屬性設定
    $("#twzipcode").twzipcode({
        zipcodeIntoDistrict: true, // 郵遞區號自動顯示在地區
        countyName: "city", // 自訂城市 select 標籤的 name 值
        districtName: "town" // 自訂地區 select 標籤的 name 值
    });

    // 上傳照片預覽
    function readURL(input) {
        console.log(input);
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function(e) {
                $('#ManagerProfilePic').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        } else {
            $('#ManagerProfilePic').attr("src", "<%= request.getContextPath()%>/manager/manager.do?action=showOthersImg&mg_no=${requestScope.managerVO.mg_no}");
        }
    }

    $("#InputProfilePic").change(function() {
        readURL(this);
    });

    feather.replace()
	
    function getSubstr (str) {
    	var substr = str.substring(0, 11);
    	return substr;
    }
    
</script>
</body>
</html>