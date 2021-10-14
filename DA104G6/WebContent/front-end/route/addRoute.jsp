<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.route.model.*"%>


<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/route_set_style.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <!--上線時改位址-->
    <script src="https://kit.fontawesome.com/e218ab780d.js" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <title>Bike Seeker建立路線</title>
  
</head>

<body>
    <!--Container開頭-->
    <div class="container-fluid" id="Routeindex">
        <!--首頁開頭-->
        <div class="row" id="Header">
            <a href='<%=request.getContextPath()%>/front-end/index/Index.jsp'><img src="<%=request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png" alt="Bike Seeker" title="Bike Seeker"></a>
            <h1>路線編輯器</h1>
        </div>
        <div class="row">
            <div class="col">
                <!--*********************************************GPX功能列*********************************************-->
                <nav class="navbar2 navbar-expand-lg navbar-dark bg-dark">
                    <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
                         <!--*******************************************地址查詢*********************************************-->
                        	<div class="col form-inline">
                        		<input class="form-control" type="text" placeholder="輸入欲查詢的地址" size="45" aria-label="Search" id="address">
                            	<button class="btn btn-outline-success mx-1" id="addsubmit">
                                	<i class="fas fa-search"></i>
                            	</button>
                        	</div>
                         <!--*******************************************地址查詢*********************************************-->   
                            <div class="d-flex justify-content-end form-inline">
	                            <button type="button" class="btn btn-success mx-2" id="cre" data-toggle="modal" data-target="#exampleModalCenter">save儲存路線</button>
	                            <button type="button" class="btn btn-danger mx-3" id="clearall">Reset清除</button>
                      		</div>
                    </div>
                </nav>
                <!--*******************************************地圖*********************************************-->
				<div id="map"></div>

				<!--*******************************************地圖*********************************************-->
            </div>
            <div class="col-md-3" id="routebackground">
                <center>
                    <!--*******************************************右欄路線資訊與指示*********************************************-->
                    <h2>
                        <i class="fas fa-file-alt"> 路線資料</i>
                    </h2>
                </center>
                <hr>
                <div class="row">
                    <ul class="nav nav-pills mb-3" style="width: 100%" id="pills-tab" role="tablist">
                        <li class="nav-item col"><a class="nav-link btn-outline-secondary text-center" id="pills-home-tab" data-toggle="pill" href="#pills-home" role="tab" aria-controls="pills-home" aria-selected="true">路線資訊</a>
                        </li>
                        <li class="nav-item col"><a class="nav-link btn-outline-secondary text-center" id="pills-profile-tab" data-toggle="pill" href="#pills-profile" role="tab" aria-controls="pills-profile" aria-selected="false">路線指示</a>
                        </li>
                    </ul>
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active col" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
                            <ul>
                            	<li>
                            	<p>目前位置: 
                            	<input class="form-control" type="text" id="current" readonly style="background-color:transparent;"></p>
                            	</li>
                            	<hr>
                                <li>
                                   <p>起始點: 
	                                   <button type="button" class="btn btn-outline-dark" id="startPoint" data-index="">新增</button>
						               <button type="button" class="btn btn-outline-dark" id="removeStartPoint">移除</button>
						           </p><input class="form-control" type="text" id="startInput" readonly>
                                
                                <li>
                                   <div id="checkPointList">
					                 <p>檢查點: <button type="button" class="btn btn-outline-dark" id="newCheckPoint">新增</button></p> 
					               </div>
                                </li>
                                <li>
                                    <p>終點: 
	                                    <button type="button" class="btn btn-outline-dark" id="endPoint" data-index="">新增</button>
						                <button type="button" class="btn btn-outline-dark" id="removeEndPoint">移除</button>
					                </p><input class="form-control" type="text" id="endInput" readonly>
                                </li>
                                <hr>
                                <li>路線總長度: <span id="total" style="color:red"></span></li>
                                    <div class="checkPoint" style="display:none">
					                    <input type="text" style="width: 80%; margin-top: 5px;" class="add" size="45" data-index="" readonly>
					                    <input type="button" class="del" value="X">
					                </div>
					                <br><br>
                                <button type="button" class="btn btn-secondary btn-lg btn-block" id='createSubmit'>建立路線圖</button>
                            </ul>
                        </div>
                        <div class="tab-pane fade col" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">
                            
                        </div>
                    </div>
                </div>
                <!--*******************************************右欄路線資訊與指示*********************************************-->
            </div>
        </div>
        <!--*******************************************跳窗(含form表單)*********************************************-->
        <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header" id="exampleModalheader">
                        <h5 class="modal-title" id="exampleModalCenterTitle">儲存/上傳當前路線</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="modalBtn">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body1">
                        <form class="setroute-form col" METHOD="post" ACTION="<%=request.getContextPath()%>/route/route.do" name="form1" enctype="multipart/form-data">
                            <div class="row">
                                <div class="col-6">
                                    <div id="showphoto"></div>
                                </div>
                                <div class="col-6" style="margin-top: 20px;">
                                    <ul id="errorMsgs">
                                        <c:if test="${not empty errorMsgs}">
                                            <font style="color: red">請修正以下錯誤:</font>
                                            <ul>
                                                <c:forEach var="message" items="${errorMsgs}">
                                                    <li style="color: red">${message}</li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                            <table>
                                <p>上傳您的封面照片:
                                <input id="myphoto" type="file" name="route_cover" multiple class="file-loading" onchange="show(myphoto)" value="11">
                                </p>
                                <p>
                                    <label for="n">
                                        <font color=red><b>*</b></font>路線名稱:
                                    </label> <input id="n" class="col-12" type="text" name="route_name" placeholder="請輸入路線名稱" value="${routeVO.route_name }" maxlength='20'><br>
                                </p>
                                <p>
                                    <label for="r">
                                        <font color=red><b>*</b></font>路線介紹:
                                    </label>
                                    <textarea id="r" class="col-12" rows="4" name="route_info" placeholder="關於此路線的介紹">${routeVO.route_info }</textarea>
                                </p>
                                <p>
                                    <font color=red><b>*</b></font><label for="difficulty">設定路線難度: </label> <select id="difficulty" name="difficulty">
                                        <option value="1">新手</option>
                                        <option value="2">簡單</option>
                                        <option value="3">普通</option>
                                        <option value="4">進階</option>
                                        <option value="5">困難</option>
                                    </select>
                                </p>
                                <tr>
                                    <td>路線總長度:</td>
                                    <td><input type="TEXT" name="route_length" size="8" value="${routeVO.route_length}" id="route_length" readonly style="background-color:transparent;"/>(km)</td>
                                </tr>
                                <tr>
                                    <td>路線起點:</td>
                                    <td><input type="TEXT" name="route_start" size="40" value="${routeVO.route_start }" id="route_start" readonly style="background-color:transparent;"/></td>
                                </tr>
                                <tr>
                                    <td>路線終點:</td>
                                    <td><input type="TEXT" name="route_end" size="40" value="${routeVO.route_end }" id="route_end" readonly style="background-color:transparent;"/></td>
                                </tr>
                                <tr>
                                    
                                    <td><input type="TEXT" name="route_gpx" size="40" value='${routeVO.route_gpx }' id="route_gpx" readonly style="display:none;"/></td>
                                </tr>
                            </table>
                            <input type="hidden" name="mem_no" value="${memVO.mem_no}">
                            <input type="hidden" name="action" value="addRoute">
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">送出新增</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!--*******************************************跳窗(含form表單)*********************************************-->
        <!--*********************************************右側回路線頁面**********************************************-->
        <a href="<%=request.getContextPath()%>/front-end/route/listAllRoute.jsp">
           <button id="goforroute"><i class="fas fa-search-location"> 返回找尋路線</i></button>
        </a>
    </div>
    <!--*********************************************右側回路線頁面**********************************************-->
    <!--Footer開始-->
    <div class="col-12" id="FooterCol">
        <div class="row align-items-center h-25" id="FooterBackground">
            <div class="col-12 col-md-2"></div>
            <div class="col-12 col-md-2" id="FooterContact">
                <h1>Contact.</h1>
                <br> <br>
                <p>人加仕旅森股份有限公司</p>
                <p>320 桃園市中壢區中大路300號</p>
                <p>wieduappclass@gmail.com</p>
                <p>03-425-7387</p>
            </div>
            <div class="col-12 col-md-4 text-center">
                <a href="#"><img src="<%=request.getContextPath()%>/images/bikeSeekerICRolling.gif"></a>
            </div>
            <div class="col-12 col-md-4 text-center">
                <img src="<%=request.getContextPath()%>/images/bikeRolling2.gif" width="350" height="250">
            </div>
        </div>
    </div>
    <!--*********************************************以下為JS*********************************************-->
    <script type="text/javascript">
    
    /*控制導覽列隱藏or顯示or上色or透明*/
    var doc = $(document);
    var win = $(window);
    var nav = $(".navbar");
    var navFont = $(".navbar-light .navbar-nav .nav-link");
    var navLogin = $("#Login");
    var preScroll = window.pageYOffset;

    doc.ready(function() {
        win.on("scroll", function() {
            var curScroll = window.pageYOffset;
            if (preScroll > curScroll) {
                nav.removeClass("navHide").addClass("navColor");
                navFont.addClass("dark");
                navLogin.removeClass("btn-outline-light").addClass(
                    "btn-outline-dark");
            } else {
                nav.addClass("navHide");
            }
            if (curScroll === 0) {
                nav.removeClass("navHide navColor");
                navFont.removeClass("dark");
                navLogin.removeClass("btn-outline-dark").addClass(
                    "btn-outline-light");
            }
            preScroll = curScroll;
        })
    });
   
    /*控制使用者上傳圖檔顯示*/
    var myphoto = document.getElementById("myphoto");

    function show(myphoto) {
        let photo = URL.createObjectURL(myphoto.files[0]);
        document.getElementById("showphoto").innerHTML = "<img src =" +
            photo + " width='100%' height='100%'>";
    }
    </script>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="<%=request.getContextPath()%>/js/popper.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
        if ($("#errorMsgs").has("li").length != 0) {
            $("#cre").click();
        }
    })
    </script>
    
    <script>
        // 地圖初始位置
        var myInitCenter = {lat: 24.9680014, lng: 121.1900142};
        function initMap(){
            var map;//地圖物件
            var directionsService = new google.maps.DirectionsService();//繪製服務
		    var directionsDisplay = new google.maps.DirectionsRenderer({
		        panel: document.getElementById('pills-profile')
		      });//繪製路線+宣告路線指示區域
		    var geocoder = new google.maps.Geocoder();//查詢地址
            var location;//緯經度
            
            var latLngarr = new Array();//裝著每個點的陣列
            latLngarr.push(undefined);//先幫第一筆加入資料
            
            var markers = new Array();//裝著marker
            markers.push(undefined);//先幫第一筆加入資料
            
            var infoWindows = new Array();//裝著infoWindow
            infoWindows.push(undefined);//先幫第一筆加入資料
            
            var index = 1;
            var address = "";
            var endpoint;
            var endWindow;
            var count = 0;
            // 地圖初始化
            map = new google.maps.Map(document.getElementById('map'), {
                center: myInitCenter,
                zoom: 16
            });     

            // 點擊產生marker
            google.maps.event.addListener(map, 'click', (e) => { 
                location = e.latLng;
                markerCreator(location);
                showAddress(location);
            });      
			
            // 設定還未確定的點位
            var markerdyn;
            function markerCreator(location){
                if(markerdyn == null){
                    markerdyn = new google.maps.Marker({
                    position: location,
                    map: map,
                    icon: '<%=request.getContextPath()%>/images/icons/flag.png',
                    draggable: true
                    })
                }else
                    markerdyn.setPosition(location);
            }

            //點擊送出地址
            document.getElementById('addsubmit').addEventListener('click', function() {
                geocodeAddress(geocoder, map);
            });
            
            
            //地址輸入查詢
			function geocodeAddress(geocoder, resultsMap) {
	        var address = document.getElementById('address').value;
	        geocoder.geocode({'address': address}, function(results, status) {
	          if (status === 'OK') {
	            resultsMap.setCenter(results[0].geometry.location);
	          } else {
	            alert('Geocode was not successful for the following reason: ' + status);
	          }
	        });
	      }
            
            // 把經緯度轉成地址
            function showAddress(location, id) {
                var geocoder = new google.maps.Geocoder();
                let lat = parseFloat(location.lat());
                let lng = parseFloat(location.lng());

                var coder = new google.maps.LatLng(lat, lng);
                geocoder.geocode({'latLng': coder}, (results, status) => {
                    if(status === google.maps.GeocoderStatus.OK) {

                        if(document.getElementById(id) != null){
                            document.getElementById(id).value = results[0].formatted_address;
                        }
                        document.getElementById('current').value = results[0].formatted_address;
                        address = results[0].formatted_address;
                    }else
                        window.alert(status);
                })
            }

            // 確定起始點
            google.maps.event.addDomListener(document.getElementById('startPoint'), 'click', (e) => {
                if(document.getElementById('startInput').value == '' && address != ''){
                    var marker = new google.maps.Marker({
                        position: location,
                        map: map,
                    })
                    
                    var infoWindow = new google.maps.InfoWindow({
                    	content: '<h7>起點:</h7><hr><p>' + address + '</p>' ,
                    	position: location
                    })
                    
                    showAddress(location, 'startInput');
                    showAddress(location, 'route_start');
                    
                    markers[0] = marker;
                    latLngarr[0] = location;
                    infoWindows[0] = infoWindow;
                    document.getElementById('startInput').dataset.index = index;
                }
            });

            // 移除起始點
            google.maps.event.addDomListener(document.getElementById('removeStartPoint'), 'click', (e) => {
                
                var index = document.getElementById('startInput').dataset.index;
                if(index != undefined && latLngarr.length > 1){
                	document.getElementById('startInput').value = '';
                    latLngarr[0] = undefined;
                    infoWindows[0].open(null);
                    infoWindows[0] = undefined;
                    show();

                }
                
            });

            // 確定終點
            google.maps.event.addDomListener(document.getElementById('endPoint'), 'click', (e) => {
                if(document.getElementById('endInput').value == '' && address != ''){
                    var marker = new google.maps.Marker({
                        position: location,
                        map: map,
<%--                         icon: '<%=request.getContextPath()%>/images/icons/flagCommited.png' --%>
                    });
                    
                    var infoWindow = new google.maps.InfoWindow({
                    	content: '<h7>終點:</h7><hr><p>' + address + '</p>' ,
                    	position: location
                    });
                    
                    showAddress(location, 'endInput');
                    showAddress(location, 'route_end');
                    markers.push(marker);
                    latLngarr.push(undefined);
                    infoWindows.push(undefined);
                    document.getElementById('endInput').dataset.index = index;
                    index++;
                    endpoint = location;
                    endWindow = infoWindow;
                    console.log(location.toString());
                }
            });

            // 移除終點
            google.maps.event.addDomListener(document.getElementById('removeEndPoint'), 'click', (e) => {
                document.getElementById('endInput').value = '';
                var index = document.getElementById('endInput').dataset.index;
                console.log(index);
                if(index != undefined){
//                     markers[index].setMap(null);
                    endpoint = '';
                    endWindow = null;
                    clearMarker();
                }
            });


            //新增檢查點
            //將document.getElementById("XXX")寫成一個方法
            function $id(id){
                return document.getElementById(id);
            }
            
            function addSpot() {
                let pointList = $id("checkPointList");
                let checkPoint = document.querySelector(".checkPoint");

                let newSpot = checkPoint.cloneNode(true);
                newSpot.style.display ="";

                let input = newSpot.firstChild;
                input.nextSibling.value = '';

                newSpot.getElementsByClassName("del")[0].onclick = removeSpot;
                newSpot.getElementsByClassName("add")[0].onclick = createPoint;
                
                
                pointList.insertBefore(newSpot , null);
                
            }
            
            // 新增檢查點
            function createPoint(e) {
                if(e.target.value == '' && address != ''){
                	console.log('aaa');
                    var marker = new google.maps.Marker({
                        position: location,
                        map: map,
<%--                         icon: '<%=request.getContextPath()%>/images/icons/flagCommited.png' --%>
                    })
                    
                    
                    e.target.value = address;
					
                    markers.push(marker);
                    console.log(markers.toString());
                    latLngarr.push(location);
                    e.target.dataset.index = index;
                    index++;
                    show();
                }
            }

            //移除檢查點
            function removeSpot(e) { //e = .del
                var previ = $(e.target).prev();
                if($(previ).val() != '' && $(previ).val() != undefined){
                    $(previ).val('');
                    var index = $(previ).data('index');
					latLngarr[index] = undefined;
                    show();
                }
                $id("checkPointList").removeChild(e.target.parentNode);
            }
            
            
            window.addEventListener("load", function(){
                $id("newCheckPoint").onclick = addSpot;
                document.querySelector(".checkPoint .del").onclick = removeSpot; //註冊最原始的節點的行為
            })
  
            
            //準備一個真正要裝的array
			function filter(latLngarr, realarr = new Array()) {
    			for(let i = 0; i < latLngarr.length; i++){
	        		if(latLngarr[i] != undefined && latLngarr[i] != ''){
	            		let lat = latLngarr[i].lat();
	            		let lng = latLngarr[i].lng();
	            		let latlng =new  google.maps.LatLng(lat, lng);
	            		realarr.push(latlng);
	        		}	
    			}
			}
			
          //清除全部
			function deleteMarkers(){
				directionsDisplay.setMap(null);
				latLngarr = [];
				realarr = [];
				
				
				clearMarker();
				
				for(let i = 0; i <= infoWindows.length; i++){
					if(infoWindows[i] != undefined){
						infoWindows[i].open(null);
					}
				}
				infoWindows = [];
				document.getElementById('endInput').value = '';
				document.getElementById('startInput').value = '';
				document.getElementById('total').innerHTML = '';
				document.getElementById('checkPointList').innerHTML = '<p>檢查點: <button type="button" class="btn btn-outline-dark" id="newCheckPoint">新增</button></p>'
				$id("newCheckPoint").onclick = addSpot;
				document.querySelector(".checkPoint .del").onclick = removeSpot;
				index = 1;
			}
			
			google.maps.event.addDomListener(document.getElementById('clearall'), 'click', (e) => {
				deleteMarkers();
				$('#pills-home button, .del').attr('disabled', false);
			});
			
		   
			
			//送出控制器
			google.maps.event.addDomListener(document.getElementById('createSubmit'), 'click', (e) => {
				let start = document.getElementById('startInput');
				let end = document.getElementById('endInput');
				
				
				if(start.value == '' || end.value == ''){
					swal("起點終點isEmpty", "起始點或終點不得為空!","warning");
					return;
				}
				clearMarker();
				$('#pills-home button, .del').attr('disabled', true);
				infoWindows.push(endWindow)
				latLngarr.push(endpoint);
				show();
			});
			
			
			function show(){
				//準備一個真正要裝的array
				var realarr = new Array();
				filter(latLngarr, realarr);
				let waypoints = new Array();		
				//創建檢查點
				for(let i = 1; i < (realarr.length)-1; i++){
					waypoints.push({
						location: realarr[i],
						stopover: true
					});
				}
				//設定參數
				directionsDisplay.setMap(map);
				var request = {
					origin: realarr[0],
					destination: realarr[realarr.length-1],
					travelMode: 'DRIVING',
					waypoints: waypoints,
					avoidHighways: true
				};
				
				//繪製路線
				directionsService.route(request, function (result, status) {
					if (status == 'OK') {
                        directionsDisplay.setDirections(result);//繪製
                        $('#route_gpx').val(JSON.stringify(result));
				    } else {
				    	console.log(status);
				    }
				});
				// 使用路徑變更事件取得路徑總長
	            directionsDisplay.addListener('directions_changed', function() {
			          computeTotalDistance(directionsDisplay.getDirections());
			    });
				
				//路線總長
				function computeTotalDistance(result) {
			        var total = 0;
			        var myroute = result.routes[0];
			        for (var i = 0; i < myroute.legs.length; i++) {
			          total += myroute.legs[i].distance.value;
			        }
			        total = total / 1000;
			        document.getElementById('total').innerHTML = total + " km";
			    	  $('#route_length').val(total);	       		   
			      }
				 
					
				
				//路線指示
				function displayRoute(origin, destination, service, display) {
					service.route({
			          origin: realarr[0],
					  destination: realarr[realarr.length-1],
					  travelMode: 'WALKING',
					  waypoints: waypoints
			        }, function(response, status) {
			          if (status === 'OK') {
			        	  display.setDirections(response);
			          } else {
			            alert('Could not display directions due to: ' + status);
			          }
			        });
			      }
				clearMarker();
				//秀出視窗
				if(infoWindows[0] != undefined){
					infoWindows[0].open(map);
					infoWindows[infoWindows.length-1].open(map);
				}
				
			}
			
			//清除marker
			function clearMarker(){
				for(let i = 0; i < markers.length; i++){
					if(markers[i] != undefined){
						markers[i].setMap(null);
					}
				}
				markers = [];
			}
			
        }
    </script>
    
     <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap"  async defer></script>
  	
</body>

</html>