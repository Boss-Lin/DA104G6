<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.route.model.*"%>
<%
	RouteVO routeVO = (RouteVO) request.getAttribute("routeVO");
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/route_detail_style.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <!--上線時改位址-->
    <script src="https://kit.fontawesome.com/e218ab780d.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title>Bike Seeker路線修改頁面</title>
   
   <style>
    #gpx{
    	width:100%;
    	height:500px;
    }	
    	
    </style>
   
</head>

<body>
    <!--Container開頭-->
    <div class="container-fluid" id="View">
        <!--首頁開頭-->
        <div class="jumbotron jumbotron-fluid row" id="updatetitle">
            <div class="container">
                <h1 class="display-4"><i>修改路線相關資料</i></h1>
                <p class="lead">
                	<a href="<%=request.getContextPath()%>/front-end/mem/member.jsp">
                	<button type="button" class="btn" id="rollbackBtn">不想修改了! !  返回頁面</button></a>
                </p>
            </div>
        </div>
        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/route/route.do" name="form1" enctype="multipart/form-data">
            <!--*********************************************路線詳情封面圖+路線名稱*********************************************-->
            <div class="row justify-content-start" id="RouteDetail">
                <div class="col-md-2">
                <%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color:red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
                </div>
                <div class="card" id="RouteImg">
                	<img :src="image.src" :height="image.height" v-for="image in images" class="card-img-top"/>
                    <img src="<%=request.getContextPath()%>/DBGifReader?route_no=${routeVO.route_no}" class="card-img-top" v-if="seen"> 
                    <div class="row col"><label for="p">路線封面圖 :</label> 
                    <input id="p" type="file" name="route_cover" id="myphoto" size="45" value="${routeVO.route_cover }" @change="fileSelected" /></div>
                </div>
                <div class="col-md-5">
                    <div class="card-body">
                        <div class="row" id="Favorite">
                            <div class="col-md-8">
                                <h1 class="card-title">
                                    <label for="n"> 路線名稱: </label>
                                    <p><input id="n" type="TEXT" name="route_name" size="25" value="${routeVO.route_name }" maxlength="20"/></p>
                                </h1>
                            </div>
                            <div class="col-md-6"></div>
                        </div>
                        <!--*********************************************路線詳情封面圖+路線名稱*********************************************-->
                        <!--*********************************************路線詳情介紹+路線資料*********************************************-->
                        <div id="RouteArticle" style="overflow-x: visible; overflow-y: visible; width: 46em">
                            <p>
                                <label for="r">路線介紹: </label>
                                <textarea id="r" class="col-12" rows="4" name="route_info">${routeVO.route_info }</textarea>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-2"></div>
                    <h4>—路線資料—</h4>
                    <hr>
                    <div class="row justify-content-between">
                        <div class="col-md-1"></div>
                        <div class="col-md-5">
                            <ul class="list-unstyled">
                                <ul>
                                    <li>路線起點:  
                                    <input type="TEXT" name="route_start" size="40" value="${routeVO.route_start }" readonly style="background-color:transparent;" />
                                        </td>
                                    </li>
                                    <li>路線終點: 
                                    <input type="TEXT" name="route_end" size="40" value="${routeVO.route_end }" readonly style="background-color:transparent;"/>
                                        </td>
                                    </li>
                                     <li><label for="tl">路線總長度: </label> 
                                     <input id="tl" type="TEXT" name="route_length" size="2" value="${routeVO.route_length }" readonly style="background-color:transparent;"/> 公里</li>
                                    <li><label for="d">路線難度: </label> <select id="d" class="custom-select" style="width: 85px" name="difficulty">
                                            <option value="1" ${(1==no.difficulty) ? 'selected': '1'}>新手</option>
                                            <option value="2" ${(2==no.difficulty) ? 'selected': '2'}>簡單</option>
                                            <option value="3" ${(3==no.difficulty) ? 'selected': '3'}>普通</option>
                                            <option value="4" ${(4==no.difficulty) ? 'selected': '4'}>進階</option>
                                            <option value="5" ${(5==no.difficulty) ? 'selected': '5'}>困難</option>
                                        </select></li>
                                    
                                </ul>
                            </ul>
                        </div>
                        <div class="col-md-6">
                        	<input type="hidden" name="action" value="updateRoute"> 
                        	<input type="hidden" name="route_no" value="${routeVO.route_no }">
			                <input type="hidden" name="mem_no" value="${routeVO.mem_no }">
			                <input type="hidden" name="route_date" value="${routeVO.route_date }"> 
                        	<input type="hidden" name="status" value="${routeVO.status }"/>
                        	
                            <button type="submit" class="btn" id="updateBtn">送出修改</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-2"></div>
            </div>
             <!--*********************************************路線GPX*********************************************-->
	        <div class="row">
	            <div class="col-md-2"></div>
	            <div class="col-md-8">
	                <hr>
	                
	            </div>
	            <div class="col-md-2"></div>
	        </div>
	        <div class="row" id="GpxInfo">
	            <div class="col-md-2"></div>
	            <div class="col-md-8" id="gpx">
	                
	            </div>
	            <div class="col-md-2">
	            </div>
	        </div>
	        
	        <div class="row" style="display:none;">
	        <input type="text" name="route_gpx" id="route_gpx" value='${routeVO.route_gpx }'/>
    		</div>
       	 <!--*********************************************路線GPX*********************************************-->
            
            <!--*********************************************路線詳情介紹+路線資料*********************************************-->
            <br><br>
        </FORM>
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
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="<%=request.getContextPath()%>/js/popper.js"></script>
        <script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
        
        
        <script>
        
      //照片預覽js
        new Vue({
            el: "#RouteImg",
            data: {
              images: [],
              seen: true
            },
            methods: {
              	fileSelected(event) {
                this.seen = false;
                this.images=[];
                const files = event.target.files; //取得File物件
                [].forEach.call(files,this.fileReader);
              },
              fileReader(file) {
                const reader = new FileReader(); //建立FileReader 監聽 Load 事件
                reader.addEventListener("load", this.createImage);
                reader.readAsDataURL(file);
              },
              createImage(event) {
                const file = event.target;
                const image = {
                  title : file.name,
                  src : file.result
                };
                this.images.push(image);
              }
            }
          });
        </script>
        
        <script>
			function initMap(){
			    var map;
			    var directionsService = new google.maps.DirectionsService();
			    var directionsDisplay = new google.maps.DirectionsRenderer();
			    map = new google.maps.Map(document.getElementById('gpx'), {
			        zoom: 16
			    });
			    var jstr = JSON.stringify(${routeVO.route_gpx});
			    var result = JSON.parse(jstr);
			    directionsDisplay.setMap(map);
			    directionsDisplay.setDirections(result);
// 			    $('#route_gpx').val(JSON.stringify(result));
			}   
		</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap"  async defer></script>      
          
</body>

</html>