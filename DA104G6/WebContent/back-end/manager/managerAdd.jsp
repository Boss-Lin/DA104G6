<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.manager.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.permission.model.PermissionService" %>
<%@ page import="com.permission.model.PermissionVO" %>

<%
//	ManagerVO managerVO = new ManagerVO();
	PermissionService permiSvc = new PermissionService();
	List<PermissionVO> list = permiSvc.getAll();
	request.setAttribute("list" , list);
%>


<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/managerUpdate.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
	<title>Bike Seeker Manager</title>
</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
			<form action="<%= request.getContextPath()%>/manager/manager.do" method="post" enctype="multipart/form-data" class="m-0">

                <div class="row align-items-center justify-content-center">

                    <div class="col-12 text-center">
                        <div class="display-3" id="Title">新增員工</div>
                    </div>


                    <div class="col-3 align-middle text-center" id="ManagerProfile">


                            <img src="<%= request.getContextPath()%>/images/icons/DefaultManager.png" alt="管理員頭貼預覽" id="ManagerProfilePic" class="mx-auto">

                            <div class="form-group mt-2 text-center">
                                <label class="btn btn-info">
                                    <input id="InputProfilePic" style="display:none;" type="file" name="mg_profile_pic" accept="image/*">
                                    <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" id="UploadButton"> 上傳頭貼
                                </label>
                            </div>

                            <div class="form-group text-left mb-0">
                                <label for="InputName">姓名</label>
                                <input type="text" class="form-control" name="mg_name" id="InputName" placeholder="請輸入姓名" maxlength="5" value="${param.mg_name}">
                                <span style="color: #cc2357; font-size: 14px">
                                    ${requestScope.errorMsgs.mg_name}
                                </span>
                            </div>

                            <div class="form-group text-left mb-0">
                                <label for="InputTitle">職稱</label>
                                <input type="text" class="form-control" name="mg_title" id="InputTitle" value="${param.mg_title}" maxlength="8">
                                <span style="color: #cc2357; font-size: 14px">
                                    ${requestScope.errorMsgs.mg_title}
                                </span>
                            </div>

                            <div class="form-group text-left mb-0">
                                <label for="InputAccount">帳號</label>
                                <input type="text" class="form-control" name="mg_account" id="InputAccount" value="${param.mg_account}">
                                <span style="color: #cc2357; font-size: 14px">
                                    ${requestScope.errorMsgs.mg_account}
                                </span>
                            </div>

                            <div class="form-group text-left mb-0">
                                <label for="InputEmail">E-mail</label>
                                <input type="text" class="form-control" name="mg_email" id="InputEmail" value="${param.mg_email}">
                                <span style="color: #cc2357; font-size: 14px">
                                        ${requestScope.errorMsgs.mg_email}
                                    </span>
                            </div>

                            <div class="form-group text-left">
                                <label for="InputSpec">說明</label><br>
                                <textarea id="InputSpec" name="mg_spec" style="width:335.5px;height:75px;">${param.mg_spec}</textarea>
                            </div>

                            <input type="hidden" name="action" value="insert">
                            <button class="btn btn-info" type="submit">新增員工</button>
                            
                            <button class="btn btn-warning ml-4" type="button" id="Fill">天使的自動筆記</button>



                    </div>

                    <div class="col-1"></div>

                    <div class="col-3 align-middle text-left" id="ManagerPermission">
                        <h2 class="text-center">權限管理</h2>

                        <c:forEach var="permission" items="${list}" varStatus="count">
                            <div style="margin-left: 6.5em;margin-top: 1vh">
                                <input type="checkbox" data-toggle="toggle" data-onstyle="success" ${paramValues.permissions.stream().anyMatch(v->v == permission.pm_no).get() ? 'checked' : ''} data-on=" " data-off=" " name="permissions" value="${permission.pm_no}">
                                <label style="font-size: 22px;color: #0c5460;">${permission.pm_name}</label>
                            </div>
                        </c:forEach>
                    </div>

                </div>
			</form>
		</main>
	</div>
</div>

<script>

	// 會員上傳頭貼預覽
	function readURL(input) {
		console.log(input);
		if (input.files && input.files[0]) {
			let reader = new FileReader();

			reader.onload = function(e) {
				$('#ManagerProfilePic').attr('src', e.target.result);
			};

			reader.readAsDataURL(input.files[0]);
		} else {
			$('#ManagerProfilePic').attr("src", "<%= request.getContextPath()%>/images/icons/DefaultManager.png");
		}
	}

	$("#InputProfilePic").change(function() {
		readURL(this);
	});

	//其他錯誤的訊息
	<c:if test="${not empty requestScope.errorMsgs.exception}">
	swal.fire({
		icon: 'error',
		title: '唉唷',
		text: "${requestScope.errorMsgs.exception}"
	});
	<c:remove var="errorMsg.exception" scope="request" />
	</c:if>
	
	//自動筆記

    $("#Fill").click(function () {
        $("#InputName").val("SEAFU");
        $("#InputTitle").val("主管");
        $("#InputAccount").val("DD104");
        $("#InputEmail").val("leonb860311@gmail.com");
        $("#InputSpec").val("專門用來兇下屬的");
    });

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