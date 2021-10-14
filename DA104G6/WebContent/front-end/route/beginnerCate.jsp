<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.route.model.*"%>
<%
	List<RouteVO> list = (List<RouteVO>)session.getAttribute("searchlist");
	session.setAttribute("list", list);
%>
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"></jsp:useBean>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/route_style.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <!--上線時改位址-->
    <script src="https://kit.fontawesome.com/e218ab780d.js" crossorigin="anonymous"></script>
    <title>Bike Seeker路線</title>
</head>

<body>
    <jsp:include page="/front-end/include/NavBar.jsp" flush="true" />
    
    <!--*********************************************導覽列開頭*********************************************-->
    <!--Container開頭-->
    <div class="container-fluid">
        
        <!--首頁開頭-->
        <div class="jumbotron jumbotron-fluid row">
            <div class="container">
                <h1 class="display-4">人生</h1>
                <p class="lead">只要路是對的，就不怕路遠</p>
                <p>山不轉，路轉；路不轉，人轉。只要心念一轉，逆境也能成機遇，拐彎也是前進的一種方式。</p>
            </div>
        </div>
        <!--*********************************************麵包屑+搜尋*********************************************-->
        <div class="row">
            <div class="col-12 col-md-2"></div>
            <div class="path col-12 col-md-2 ">
                <div class="container">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/index/Index.jsp">首頁</a></li>
                            <li class="breadcrumb-item active" aria-current="page">找尋路線</li>
                        </ol>
                    </nav>
                </div>
            </div>
            <div class="col-12 col-md-5"></div>
            <div class="col-12 col-md-3">
                
            </div>
        </div>
        <hr>
        <!--*********************************************麵包屑+搜尋*********************************************-->
        <div class="row">
            <!--*********************************************路線類別選單*********************************************-->
            <div class="col-12 col-md-1"></div>
            <div class="col-4 col-md-2 text-center" id="RouteCategory">
                <div class="list-group">
                    <a href="<%=request.getContextPath()%>/front-end/route/listAllRoute.jsp" 
                    class="list-group-item list-group-item-action action"><i class="fas fa-road"></i> 全部路線</a>
                     
                    <a href="<%=request.getContextPath()%>/route_category/route_category.do?route_cate_no=RC004&action=newCate" 
                    class="list-group-item list-group-item-action"><i class="fa fa-plus-circle"></i> 新進路線</a> 
                    
                    <a href="<%=request.getContextPath()%>/route_category/route_category.do?route_cate_no=RC005&action=popularCate"
                     class="list-group-item list-group-item-action "><i class="fab fa-hotjar"></i> 熱門路線</a> 
                    
                    <a href="<%=request.getContextPath()%>/route_category/route_category.do?route_cate_no=RC001&action=RouteDistinguish"
                     class="list-group-item list-group-item-action "><i class="fa fa-battery-empty"></i> 新手路線</a> 
                    
                    <a href="<%=request.getContextPath()%>/route_category/route_category.do?route_cate_no=RC002&action=RouteDistinguish"
                     class="list-group-item list-group-item-action "><i class="fa fa-battery-full"></i> 老手路線</a> 
                    
                    <a href="<%=request.getContextPath()%>/route_category/route_category.do?route_cate_no=RC003&action=RouteDistinguish"
                     class="list-group-item list-group-item-action "><i class="fas fa-charging-station"></i> 專業路線</a>
                    
                </div>
                 <br>
          
                <a href="<%=request.getContextPath()%>/front-end/route/addRoute.jsp">
                <button id="goforadd"><font color=red>${memVO.mem_name}</font>您好!<i class="fas fa-bicycle"> 前往建立路線</i></button>
                </a>
              
            </div>
            <!--*********************************************路線類別選單*********************************************-->
            <div class="col-md-8" id="RouteInfo">
                <h1>${routeTitle}</h1>
                
               <!--*********************************************路線列表*********************************************-->
                <%@ include file="page1.file"%>
                <div class="row" id="routelist">
                    <c:forEach var="routeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                        <div class="col-md-4">
                            <div class="card">
                                <a href="<%=request.getContextPath()%>/route/route.do?route_no=${routeVO.route_no}&mem_no=${memVO.mem_no}&action=getOneRoute">
                                	<img src="<%=request.getContextPath()%>/DBGifReader?route_no=${routeVO.route_no}" class="card-img-top">
                                </a>
                                <div class="card-body">
                                    <h5 class="card-title">${routeVO.route_name}</h5>
                                    <div id="CardText">
                                        <p>${routeVO.route_info}</p>
                                    </div>
                                    <br>
                                        <i class="fa fa-line-chart"></i> 路線總長: ${routeVO.route_length}公里.
                                   <div class="row">
	                                   <div class="col-8">
	                                        <i class="fa fa-user"></i> 建立者:
	                                       		<a href="<%=request.getContextPath()%>/mem/mem.do?action=getOne_For_Display&mem_no=${routeVO.mem_no}">${memSvc.getOneMem(routeVO.mem_no).mem_name}</a>
	                                        <br>
	                                        <small class="collect" id="${routeVO.route_no}">收藏次數:</small>
	                                        <br>
	                                        <small class="text-muted"> 路線建立日期: ${routeVO.route_date}</small>
	                                   </div>
	                                    <div class="col-4">
	                                    	<a href="<%=request.getContextPath()%>/route/route.do?route_no=${routeVO.route_no}&mem_no=${memVO.mem_no}&action=getOneRoute">
	                                    		<button class="btn third" id="btndetail">路線詳情</button>
	                                    	</a>
	                                    </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <hr>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <%@ include file="page2.file"%>
                <!--*********************************************路線列表*********************************************-->
            </div>
        </div>
    </div>
    </div>

    <!--Footer開始-->
	<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>
    <!--*********************************************以下為JS*********************************************-->
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
    <script src="<%=request.getContextPath()%>/js/popper.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
    <jsp:include page="/front-end/include/LoginAlerts.jsp" />
    <script>
    
    <c:if test="${empty list}">
		swal("無資料", "目前還沒有路線相關資料!", "error");
	</c:if>
    
    
    $(document).ready(function(){
		var colarray = $('.collect');
		console.log(colarray);
		
		for(let i = 0; i < colarray.length; i++){
			$.ajax({
				type:"POST",
					url:"<%=request.getContextPath()%>/route_category/route_category.do",
				data:{
					route_no: colarray[i].id,
					action: "colectionCount"
				},
				success: function(data){
					if(data == 'null'){
						data = 0;
					}
					colarray[i].innerHTML = colarray[i].innerHTML + data + "次";
				},
				error: function(){
					console.log("error");
				}
			})
		}
			
		});
    </script>
</body>

</html>