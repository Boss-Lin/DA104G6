<%@page import="com.route_collection.model.Route_CollectionVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.route.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.route_comment.model.*"%>
<%@ page import="com.route_collection.model.*"%>
<%
	RouteVO routeVO = (RouteVO) request.getAttribute("routeVO");
	Route_CommentVO route_commentVO = (Route_CommentVO) request.getAttribute("route_commentVO");
	Route_CollectionVO route_collectionVO = (Route_CollectionVO) request.getAttribute("route_collectionVO");
%>
<%
	//該路線有哪些留言的接收端
	Route_CommentService route_commentSvc = new Route_CommentService();
	
	if(routeVO != null){
		List<Route_CommentVO> list = route_commentSvc.getOneRoute(routeVO.getRoute_no());
		request.setAttribute("list", list);
	}
		
	
%>

<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<jsp:useBean id="routeSvc" scope="page" class="com.route.model.RouteService" />
<jsp:useBean id="route_collectionSvc" scope="page" class="com.route_collection.model.Route_CollectionService" />



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
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <!--上線時改位址-->
    <script src="https://kit.fontawesome.com/e218ab780d.js" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <title>Bike Seeker路線詳情</title>
    
    <style>
    #gpx{
    	width:100%;
    	height:500px;
    }	
    	
    </style>
    
</head>

<body>

<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />
   
    <!--Container開頭-->
    <div class="container-fluid" id="View">
        <!--首頁開頭-->
        <!--*********************************************麵包屑*********************************************-->
        <div class="row" style="padding-top: 128px">
            <div class="col-12 col-md-2"></div>
            <div class="path col-12 col-md-2 ">
                <div class="container">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/Index.jsp">首頁</a></li>
                            <li class="breadcrumb-item active"><a href='<%=request.getContextPath()%>/front-end/route/listAllRoute.jsp'>找尋路線</a></li>
                            <li class="breadcrumb-item active" aria-current="page">路線詳情</li>
                        </ol>
                    </nav>
                </div>
            </div>
            <div class="col-12 col-md-5"></div>
            <div class="col-12 col-md-3"></div>
        </div>
        <hr>
        <!--*********************************************麵包屑*********************************************-->
        <!--*********************************************路線詳情封面圖+路線名稱+(下載收藏分享)*********************************************-->
        <div class="row justify-content-start" id="RouteDetail">
            <div class="col-md-2"></div>
            <div class="col-3 mr-5" id="RouteImg">
                <img src="<%=request.getContextPath()%>/DBGifReader?route_no=${routeVO.route_no}">
            </div>
            <div class="col-md-5">
                <div class="card-body">
                    <div class="row" id="Favorite">
                        <div class="col-md-8">
                            <h1 class="card-title">
                                <%=routeVO.getRoute_name()%>
                            </h1>
                        </div>
                        <div class="d-flex justify-content-end col">
                            <i class="fas fa-download mr-3" data-toggle="modal" data-target="#exampleModalCenter">下載</i>
                       		<i class="fas fa-share-alt mr-2">分享</i>
                       		
                               <c:if test="${routeVO.mem_no != memVO.mem_no && memVO.mem_name != null }"> 
									<div class="ml-2">
										<c:if test="${routeVO.route_no != route_collectionVO.route_no}">
											<input type="button" id="like-button" value="❤收藏">
											
										</c:if>
										<c:if test="${routeVO.route_no == route_collectionVO.route_no}">
											<input type="button" id="like-button" value="❤取消收藏">
		                              	</c:if>
                               		</div>
                              </c:if> 
                        	
                        </div>
                        <div class="col-md-6"></div>
                    </div>
                    <!--*********************************************路線詳情封面圖+路線名稱+(下載收藏分享)*********************************************-->
                   
                    
                    
                    <!--*********************************************下載跳窗*********************************************-->
                    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalCenterTitle">下載GPX路線檔</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="modal-body-100">
                                        <form class="route-form">
                                            <h4 class="modal_line">請選擇您要儲存資料的地點。您可以儲存資料到個人空間或電腦裡。</h4>
                                            <label> <input name="download_route_radio" type="radio" id="RadioGroup3_0" value="dl_to_device" checked=""> 到個人空間
                                            </label> <br> <label> <input type="radio" name="download_route_radio" value="dl_to_xml" id="RadioGroup3_2"> 路徑資料(*.xml)
                                            </label> <br> <label> <input type="radio" name="download_route_radio" value="dl_to_pc" id="RadioGroup3_1"> 到電腦檔案
                                            </label> <label for="dl_select_type"></label> <select name="dl_select_type" id="dl_select_type">
                                                <option value="trk">路徑資料格式(*.trk)</option>
                                                <option value="gpx">GPS資料交換格式(*.gpx)</option>
                                            </select> <br>
                                        </form>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary">下載</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">放棄</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--*********************************************下載跳窗*********************************************-->
                    <!--*********************************************路線詳情介紹+路線資料*********************************************-->
                    <div id="RouteArticle">
                        <p>
                            <%=routeVO.getRoute_info()%>
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
                                <li><p id="start">路線起點:${routeVO.route_start}</p></li>
                                 <li><p id="end">路線終點:${routeVO.route_end}</p></li>
                                <li><p id="ShareDate">上傳日期:${routeVO.route_date}</p></li>
                              	 <li>建立者:<a href="<%=request.getContextPath()%>/mem/mem.do?action=getOne_For_Display&mem_no=${routeVO.mem_no}">${memSvc.getOneMem(routeVO.mem_no).mem_name }</a></li>
                            </ul>
                        </ul>
                    </div>
                    <div class="col-md-6">
                        <ul class="list-unstyled">
                            <ul>
                               	<li><p id="TotalLength">路線總長度:${routeVO.route_length}公里</p></li>
                                <li><p id="Level">路線難度:${applicationScope.routeLevel[routeVO.difficulty]}</p></li>
                            </ul>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
        <!--*********************************************路線詳情介紹+路線資料*********************************************-->
        <!--*********************************************路線GPX*********************************************-->
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <hr>
                <h2>
                    <i class="fas fa-map-marked-alt"></i> 路線圖
                </h2>
            </div>
            <div class="col-md-2"></div>
        </div>
        <div class="row" id="GpxInfo">
            <div class="col-md-2"></div>
            <div class="col-md-8" id="gpx">
                
            </div>
            <div class="col-md-2"></div>
        </div>
        <!--*********************************************路線GPX*********************************************-->
        <!--*********************************************留言送出區*********************************************-->
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <hr>
                <div id="commentArea" class="my-5 border border-bottom-0 rounded">
                    <h2>
                        <i class="fa fa-comment"></i> 留言討論
                    </h2>
                    <form id="writeArea" METHOD="post" ACTION="<%=request.getContextPath()%>/route_comment/route_comment.do" name="form1">
                        <div class="row">
                            <div class="col-1">
                                <img class="rounded-circle" src="<%= request.getContextPath()%>/mem/mem.do?action=showImg">
                            </div>
                            <div class="col-10 form-group shadow-textarea">
                                <textarea class="col-12" id="route_comment" rows="4" placeholder="寫上你的留言吧!" name="route_comment"></textarea>
                                <div class="postBtn">
                                    <button type="submit" class="btn btn-primary my-1 text-center" id="postBtn" name="action" value="addRouteComment">送出</button>
                                    <input type="hidden" name="mem_no" value="${memVO.mem_no}">
                                    <input type="hidden" name="route_com_no" value="${route_commentVO.route_com_no}"> 
                                    <input type="hidden" name="route_no" value="${routeVO.route_no}">
                                </div>
                            </div>
                        </div>
                    </form>
                    <!--*********************************************留言送出區*********************************************-->
                    <!--*********************************************留言內容區*********************************************-->
                    <c:forEach var="route_commentVO" items="${list}">
                        <div id="comments" class="my-1">
                            <div class="row">
                                <div class="col-1">
                                    <img class="rounded-circle" src="<%= request.getContextPath()%>/mem/mem.do?action=showOthersImg&mem_no=${route_commentVO.mem_no}">
                                </div>
                                <div class="col-md-10">
                                    <div>
                                        <a href="<%=request.getContextPath()%>/mem/mem.do?action=getOne_For_Display&mem_no=${route_commentVO.mem_no}">
                                        	<c:forEach var="memVO" items="${memSvc.allMem}">
                                           		<c:if test="${route_commentVO.mem_no==memVO.mem_no}">${memVO.mem_name}</c:if>
                                            </c:forEach>
                                        </a>
                                    </div>
                                    <p>${route_commentVO.route_comment}</p>
                                    <div>
                                    
                                        <p class="card-text">
                                            <small class="text-muted"><fmt:formatDate value="${route_commentVO.com_time}" pattern="yyyy-MM-dd HH'點'  mm'分'"/></small>
                                        </p>
                                        <hr>
                                    </div>
                                </div>
                                <div class="col-md-1"></div>
                            </div>
                        </div>
                    </c:forEach>
                    <!--*********************************************留言內容區*********************************************-->
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
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
    <script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
    <script src="<%=request.getContextPath()%>/js/popper.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
    
    <script>
    
    <c:if test="${not empty updateRouteSuccess}">
    	swal("修改成功", "您的路線已修改!", "success");
		<%request.removeAttribute("updateRouteSuccess");%>
	</c:if>

	
    <c:if test='${not empty commentEmpty}'>
    	swal("留言null", "留言不得為空!","warning");
    	<%request.removeAttribute("commentEmpty");%>
    </c:if>
    
    
    //收藏鈕ajax
	document.addEventListener("DOMContentLoaded",function() {
		var likeButton = document.getElementById("like-button");
			likeButton.addEventListener("click", function() {
				window.lb = likeButton;
				likeButton.classList.toggle("selected");
			});
		},
		false
	);
	//ADD&DEL收藏 ajax
	$("#like-button").click(function(){
		if($("#like-button").val() == "❤收藏" ){					
		$.ajax({
			type:"POST",
			url: "<%=request.getContextPath()%>/route_collection/route_collection.do",
			data:{
				mem_no:"${sessionScope.memVO.mem_no}",
				route_no:"${routeVO.route_no}",
				action: "addCollection"
			},
			success: function(data){
				$("#like-button").val("🖤取消收藏");
				swal("收藏!", "已將此筆路線加入收藏", "success");
			}
		})
		}else{
			$.ajax({
				type:"POST",
				url: "<%=request.getContextPath()%>/route_collection/route_collection.do",
				data:{
					mem_no:"${sessionScope.memVO.mem_no}",
					route_no:"${routeVO.route_no}",
					action: "deleteCollection",
				},
				success: function(data){
					$("#like-button").val("❤收藏");
					swal("取消收藏", "已取消此筆路線收藏!");
				}
			})
		}
	});	
</script>

		<script>
			var myInitCenter = {lat: 24.9680014, lng: 121.1900142};
			function initMap(){
			    var map;
			    var directionsService = new google.maps.DirectionsService();//繪製服務
			    var directionsDisplay = new google.maps.DirectionsRenderer({
			        panel: document.getElementById('pills-profile')
			    });
			    map = new google.maps.Map(document.getElementById('gpx'), {
			    	center: myInitCenter,
			        zoom: 16
			    });
			    
			    var jstr = JSON.stringify(${routeVO.route_gpx});
			    var result = JSON.parse(jstr);
			    console.log(${routeVO.route_gpx});
			    directionsDisplay.setMap(map);//設定參數
			    directionsDisplay.setDirections(result);//繪製
			    
			}   
		</script>

 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap"  async defer></script>	
	
	<script>
// 			$("#postBtn").click(function(){
// 				$.ajax({
// 					type:"POST",
<%-- 					url:"<%=request.getContextPath()%>/route_comment/route_comment.do", --%>
// 					data:{
// 						route_comment:$("#route_comment").text(),
// 						mem_no:"${memVO.mem_no}",
// 						route_com_no:"${route_commentVO.route_com_no}",
// 						route_no:"${routeVO.route_no}",
// 						action:"addRouteComment"
// 					},
// 					success: function(data){
<%-- 						$("#commentArea").append('<div id="comments" class="my-1"><div class="row"><div class="col-1"><img class="rounded-circle" src="<%= request.getContextPath()%>/mem/mem.do?action=showImg"></div><div class="col-md-10"><div><a href="#"><c:forEach var="memVO" items="${memSvc.allMem}"><c:if test="${route_commentVO.mem_no==memVO.mem_no}">${memVO.mem_name}</c:if></c:forEach></a></div><p>${route_commentVO.route_comment}</p><div><p class="card-text"><small class="text-muted"><fmt:formatDate value="${route_commentVO.com_time}" pattern="yyyy-MM-dd HH'點'  mm'分'"/></small></p><hr></div></div><div class="col-md-1"></div></div></div>'); --%>
// 					}
// 				})
// 			});
// 		$("#postBtn").click(function(){
// 			$("#commentArea").append('123456');
// 		}
	</script>

</body>

</html>