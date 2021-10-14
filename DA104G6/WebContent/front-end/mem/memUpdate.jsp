<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%

%>

<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/memUpdate.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/jquery.datetimepicker.css">
	<!--JQuery-->
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
	<script src="<%= request.getContextPath()%>/js/jquery.datetimepicker.full.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>

	<title>Bike Seeker</title>

</head>
<body>
<!--導覽列開頭-->
							<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />
	
<!--Container開頭-->
<div class="container-fluid h-100">

	<!--首頁開頭-->
	<div class="row align-items-center h-100" id="MemberPage">

		<!--個人頁面開頭-->

		<div class="col-12 col-xl-1 text-center"></div>

		<!--會員側邊欄-->
		<div class="col-12 col-xl-3 text-center" id="MemberInfo">
			<form action="<%= request.getContextPath()%>/mem/mem.do" method="post" enctype="multipart/form-data">

				<div class="row justify-content-center pt-2 pb-2" id="ProfilePic">

					<div class="col-12">
							<img src="<%= request.getContextPath()%>/mem/mem.do?action=showImg" alt="會員頭貼預覽" id="UserPic">
					</div>

					<div class="col-5 mt-3">
						<label class="btn btn-info">
							<input id="InputProfilePic" style="display:none;" type="file" name="mem_img" accept="image/*">
							<img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" id="UploadButton"> 上傳個人頭貼
						</label>
					</div>

				</div>

				<div class="row align-items-start pt-2" id="UpdateCol">
					<div class="col-12 text-left">

							<div class="form-group">
								<label for="InputNickName">暱稱</label>
								<input type="text" class="form-control" name="mem_name" id="InputNickName" value="${requestScope.memVO.mem_name}" maxlength="8">
							</div>

							<div class="form-group">
								<label for="f_date1">生日</label>
								<input id="f_date1" class="form-control" name="mem_dob">
								<span style="color: #cc2357; font-size: 18px">
									<c:if test="${not empty requestScope.errorMsgs.mem_dob}">
										<c:out value="${requestScope.errorMsgs.mem_dob}" />
									</c:if>
								</span>

							</div>

							<div class="form-group">
								住址
								<div id="twzipcode"></div>
								<input type="text" class="form-control" name="mem_address" id="InputAddress" value="${requestScope.memVO.mem_address}" />
								<span style="color: #cc2357; font-size: 18px">
									<c:if test="${not empty requestScope.errorMsgs.mem_address}">
										<c:out value="${requestScope.errorMsgs.mem_address}" />
									</c:if>
								</span>

							</div>

							<div class="form-group">
								性別<br>
								<input type="radio" name="mem_gender" id="Male" value="1" <c:if test="${sessionScope.memVO.mem_gender == 1}"> checked </c:if>>
								<label for="Male">男</label>
								<input type="radio" name="mem_gender" id="Female" value="2" <c:if test="${sessionScope.memVO.mem_gender == 2}"> checked </c:if>>
								<label for="Female">女</label>
							</div>

							<div class="row justify-content-center pt-4">

								<div class="col-5 text-center p-0" id="Cancel">
									<input type="button" class="btn btn-secondary" onclick="location.href = '<%= request.getContextPath()%>/front-end/mem/member.jsp';" value="取消修改">
								</div>

								<div class="col-5 text-center p-0">
									<input type="hidden" name="action" value="update">
									<input type="hidden" name="requestURL" value="<%= request.getServletPath()%>">
									<input type="submit" class="btn btn-primary" value="修改資料">
								</div>

							</div>

					</div>
				</div>
			</form>
		</div>

		<div class="col-12 col-xl-2 text-center"></div>

		<div class="col-12 col-xl-3 mb-5 text-center" id="pswUpdate">
			<h2 class="text-center">更換密碼</h2>

			<form action="<%= request.getContextPath()%>/mem/mem.do" method="post">

				<div class="form-group text-left">
					<label for="InputOldPassword">舊密碼</label>
					<input type="password" class="form-control" name="mem_psw" id="InputOldPassword">
					<span style="color: #cc2357; font-size: 14px">
							<c:if test="${not empty requestScope.errorMsgs.old_mem_psw}">
								<c:out value="${requestScope.errorMsgs.old_mem_psw}" />
							</c:if>
						</span>
				</div>

				<div class="form-group text-left">
					<label for="InputNewPassword">新密碼</label>
					<input type="password" class="form-control" name="new_mem_psw" id="InputNewPassword">
					<span style="color: #cc2357; font-size: 14px">
							<c:if test="${not empty requestScope.errorMsgs.new_mem_psw}">
								<c:out value="${requestScope.errorMsgs.new_mem_psw}" />
							</c:if>
						</span>
				</div>
				<input type="hidden" name="mem_no" value="${sessionScope.memVO.mem_no}">
				<input type="hidden" name="action" value="pswUpdate">
				<button class="btn btn-primary" type="submit">更新密碼</button>
			</form>

		</div>

</div>


<script>

	// 會員上傳頭貼預覽
    function readURL(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function(e) {
                $('#UserPic').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#InputProfilePic").change(function() {
        readURL(this);
    });

    //地址下拉式選單

	//選單事件註冊
	$(document).ready(function() {
		$("select").change(function(){
			$("#InputAddress").val($('#twzipcode :selected').text()); //事件發生時把選單數值填入下方輸入盒
		});

	});

	//地址屬性設定
	$("#twzipcode").twzipcode({
		zipcodeIntoDistrict: true, // 郵遞區號自動顯示在地區
		countyName: "city", // 自訂城市 select 標籤的 name 值
		districtName: "town" // 自訂地區 select 標籤的 name 值
	});

	//日期選擇

	var somedate2 = new Date();
	$.datetimepicker.setLocale('zh-TW');
	$('#f_date1').datetimepicker({
		format:'Y-m-d',
		timepicker:false,
		value: '${requestScope.memVO.mem_dob}',
		yearStart:'1900',
		beforeShowDay: function(date) {
			if (  date.getYear() >  somedate2.getYear() ||
					(date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) ||
					(date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
			) {
				return [false, ""]
			}
			return [true, ""];
		}});



</script>
<%--登入失敗的訊息--%>
<jsp:include page="/front-end/include/LoginAlerts.jsp" />
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>