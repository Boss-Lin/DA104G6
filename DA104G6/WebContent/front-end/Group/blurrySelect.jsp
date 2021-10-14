<%@page import="com.group.model.GroupVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html>
<head>
	<!-- Required meta tags -->
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!---------------------------------------------網址縮圖----------------------------------------->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">
	
	<!-------------------------------------- Bootstrap CSS、jquery---------------------------------->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<!-------------------------------------- Bootstrap CSS ----------------------------------------->
	
	
	<!-------------------------------------------自訂CSS--------------------------------------------->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/listAllGroup.css">
	<!-------------------------------------------自訂CSS--------------------------------------------->
	
	<title>搜尋結果</title>
</head>
<body>

				<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />
				<% 
					List<GroupVO> list = (List)session.getAttribute("list");
				%>
				<jsp:useBean id="msvc" class="com.mem.model.MemService"/>
<!------------------------------------------------- 內容 ---------------------------------------------->
<div class="container-fluid">

<!------------------------------------------------- 搜尋列 --------------------------------------------->
	<div class="row h-75">
		<div class="col-12" id="header">
			<div id="item_search">
			<nav class="navbar_search" id="navbar_search">
				<form class="form-inline" action="<%=request.getContextPath() %>/front-end/Group/group.do">
					<input class="form-control ml-auto mr-2" type="search" placeholder="想找什麼團??" id="search_input" name="search">
					<input type="hidden" name="action" value="search">
					<button class="btn btn-outline-success mr-auto" type="submit" id="search_button">搜尋行程</button>
				</form>
			</nav>
			</div>	
		</div>
	</div>
<!------------------------------------------------- 搜尋列 --------------------------------------------->	
	
	<div class="row" id="body">
		
		
<!------------------------------------------------- 導覽按鈕 -------------------------------------------->
		<div class="col-2 pt-5" id="list_body">
			<div class="list-group" id="list">
				<form action="<%=request.getContextPath() %>/front-end/Group/group.do" method="post">
					<button type="submit" class="list-group-item list-group-item-action item" name='action' value='getAll_For_Display'>全部行程</button>
				</form>
				<form action="<%=request.getContextPath() %>/front-end/Group/group.do" method="post">
					<button type="submit" class="list-group-item list-group-item-action item" name='action' value='hot'>熱門行程</button>
				</form>
				<form action="<%=request.getContextPath() %>/front-end/Group/group.do" method="post">
					<button type="submit" class="list-group-item list-group-item-action item" name="action" value="isNew">新開行程</button>
				</form>
				<form action="<%=request.getContextPath() %>/front-end/Group/group.do" method="post">
					<button type="submit" class="list-group-item list-group-item-action item" name="action" value="full">即將滿員</button>
				</form>
				<form action="<%=request.getContextPath() %>/front-end/Group/group.do" method="post">
					<button type="submit" class="list-group-item list-group-item-action item" name ="action" value="comingSoon">即將出行</button>
				</form>
				<button type="button" class="list-group-item list-group-item-action item_group" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">都不喜歡??</button>
				<div class="collapse mt-3" id="collapseExample">
					<div class="card card-body" id="create_div">

						<a href="<%=request.getContextPath()%>/front-end/Group/addGroup.jsp" class="btn btn-primary btn-lg active" role="button" id= "create"><img src="<%= request.getContextPath()%>/images/icons/Create_Group_Icon.png">建立行程</a>

					</div>
				</div>
			</div>
		</div>
<!------------------------------------------------- 導覽按鈕 -------------------------------------------->
		
		
		<%@ include file="page1.file" %><!-- 換頁 -->
<!--------------------------------------------- listAllGroup區塊 --------------------------------------->	
		<div class="col-10">
<!---------------------------------------------- 標題(全部行程...) --------------------------------------->			
			<div class="alert alert-success mt-5 p-2" role="alert" id="title_div">
				<h4 class="alert-heading" id="title_group">${title}</h4>
			</div>
<!---------------------------------------------- 標題(全部行程...) --------------------------------------->


<!------------------------------------------------ 行程瀏覽身體 ------------------------------------------->			
			<div class="row" id="group_list">
				<c:forEach var = "groVO" items = "${list}" begin = "<%= index %>" end = "<%= index + rowsNum -1 %>" >
				
				<div class="col-xl-3 col-12">
					<div class="card">
						<div class="list_img" style="min-height: 250px; max-height: 250px"><img src="<%= request.getContextPath() %>/front-end/Group/group.do?getImg=${groVO.gro_no}" class="card-img-top" alt="..."></div>
					 	<div class="card-body">
					    	<h5 class="card-title">行程名稱 :  ${groVO.gro_name}</h5>
					    	<p class="card-text">連絡電話 : ${groVO.phone}</p>
						</div>
							<ul class="list-group list-group-flush">
							    <li class="list-group-item">主揪人 : <c:forEach var='memvo' items='${msvc.allMem}'>
			                    										<c:if test="${groVO.mem_no eq memvo.mem_no}">
			                    											${memvo.mem_name}
			                    										</c:if>
                    												</c:forEach></li>
							    <li class="list-group-item">集合時間 : <fmt:formatDate value="${groVO.time}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
							    <li class="list-group-item">參加人數 : ${groVO["comfirm_mem"]}</li>
							</ul>
						<div class="card-body">
							<form class = "submitOne" method= "get">
								<button type="submit" class="btn btn-outline-danger" name = "action" value = "getOne_For_Display" formaction = "<%=request.getContextPath() %>/front-end/Group/group.do">觀看詳情</button>
								<input type = "hidden" name = "gro_no" value = "${groVO.gro_no}">
								<input type = "hidden" name = 'page' value = '<%=whichPage%>'>
								<input type = "hidden" name = 'requestURL' value = '<%=request.getServletPath() %>'>
							</form>
							
							<c:if test = "${memVO.mem_no ne groVO.mem_no}">
								<form class = "submitOne" method= "get" id="submitjoin" action="<%=request.getContextPath() %>/front-end/Sign_up/sign_up.do">
									<input type="hidden" name = "gro_no" value="${groVO.gro_no}">
									<input type = "hidden" name = 'page' value = '<%=whichPage%>'>
									<input type = "hidden" name = 'requestURL' value = '<%=request.getServletPath() %>'>
									<button type="submit" class="btn btn-outline-dark" name="action" value="insert" id="join">參團</button>
								</form>
							</c:if>
							
						</div>
					</div>
				</div>
				</c:forEach>
<!------------------------------------------------ 行程瀏覽身體 ------------------------------------------->				
				
				<%@include file= "page2.file" %><!-- 換頁 -->
			</div>
		</div>
<!--------------------------------------------- listAllGroup區塊 --------------------------------------->		
		
	</div>
</div>
<!------------------------------------------------- 內容 ---------------------------------------------->


					<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>




<!------------------------------------- bootstrap.js and other.js ------------------------------------>
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetAlert2 9.5.2.js"></script>
<jsp:include page="/front-end/include/LoginAlerts.jsp" />
<script type="text/javascript">
<c:if test="${not empty situation.error}">
	swal.fire({
		icon:'error',
		title:'錯誤',
		text:"請輸入關鍵字"
	});
	<% request.removeAttribute("error"); %>
</c:if>

<c:if test="${not empty situation.data_not_found}">
	swal.fire({
		icon:'warning',
		title:'oops',
		text:"查無此資料"
	});
	<% request.removeAttribute("error"); %>
</c:if>

<c:if test="${not empty conflict}">
swal.fire({
	icon:'error',
	title:'時間有衝突!!，已有參加的行程',
	text:'${conflict}'
});
<% request.removeAttribute("conflict"); %>
</c:if>

<c:if test="${not empty situation.overTime}">
swal.fire({
	icon:'warning',
	title:'oops',
	text:"報名截止日已過"
});
<% request.removeAttribute("overTime"); %>
</c:if>

<c:if test="${not empty situation.registered}">
swal.fire({
	icon:'warning',
	title:'oops',
	text:"已報名，等待審核"
});
<% request.removeAttribute("registered"); %>
</c:if>

<c:if test="${not empty situation.isFull}">
swal.fire({
	icon:'warning',
	title:'oops',
	text:"人數已滿"
});
<% request.removeAttribute("isFull"); %>
</c:if>

<c:if test="${not empty situation.success}">
swal.fire({
	icon:'success',
	title:'YEAH',
	text:"報名成功，等待審核"
});
<% request.removeAttribute("success"); %>
</c:if>
</script>
</body>
</html>