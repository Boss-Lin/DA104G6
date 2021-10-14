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
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/addMem.css">
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
	<div class="row align-items-center justify-content-around h-100 pb-3 m-0" id="RegisterPage">

		<div class="col-6"></div>

		<!--錯誤訊息-->
		<div class="col-2 order-12" id="ErrorMsg">

			<c:if test="${not empty requestScope.errorMsgs.exception}">
				<span>遭遇錯誤:</span> <br>
				<p><c:out  value="${requestScope.errorMsgs.exception}" /></p>
			</c:if>

		</div>

		<div class="col-3 pt-2" id="RegisterCol">

			<form action="<%= request.getContextPath()%>/mem/mem.do" method="post" enctype="multipart/form-data">

				<div class="form-group">

					<label for="InputEmail">帳號</label>
					<div class="row align-items-start">
						<div class="col-10">
					<input type="email" class="form-control" name="mem_email" id="InputEmail" placeholder="請輸入E-mail" value="${param.mem_email}" />
							<span style="color: #cc2357; font-size: 18px" id="EmailWarn">
								<c:if test="${not empty requestScope.errorMsgs.mem_email}">
									<c:out value="${requestScope.errorMsgs.mem_email}" />
								</c:if>
							</span>
					</div>
						<div class="col-2 p-0">
							<button type="button" class="btn btn-info" id="TestEmail">驗證</button>
						</div>
				</div>
				</div>

				<div class="form-group">
					<label for="InputPassword">密碼</label>
					<input type="password" class="form-control" name="mem_psw" id="InputPassword" placeholder="請輸入不超過8位的密碼" maxlength="8">
					<span style="color: #cc2357; font-size: 18px">
						<c:if test="${not empty requestScope.errorMsgs.mem_psw}">
							<c:out value="${requestScope.errorMsgs.mem_psw}" />
						</c:if>
					</span>

				</div>

				<div class="form-group">
					<label for="InputNickName">暱稱</label>
					<input type="text" class="form-control" name="mem_name" id="InputNickName" value="<c:if test="${empty param.mem_name}">吾名飾</c:if><c:if test="${not empty param.mem_name}">${param.mem_name}</c:if>" maxlength="8">
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
					<input type="text" class="form-control" name="mem_address" id="InputAddress" value="${param.mem_address}" />
					<span style="color: #cc2357; font-size: 18px">
						<c:if test="${not empty requestScope.errorMsgs.mem_address}">
							<c:out value="${requestScope.errorMsgs.mem_address}" />
						</c:if>
					</span>

				</div>

				<div class="form-group">
					性別<br>
					<input type="radio" name="mem_gender" id="Male" value="1" checked>
					<label for="Male">男</label>
					<input type="radio" name="mem_gender" id="Female" value="2" >
					<label for="Female">女</label>
				</div>

				<div class="row align-items-center">

					<div class="col-1"></div>

					<div class="col-5">

					<label class="btn btn-info">
						<input id="InputProfilePic" style="display:none;" type="file" name="mem_img" accept="image/*">
						<img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" id="UploadButton"> 上傳個人頭貼
					</label>

					</div>

					<div class="col-6">
						<div class="img-thumbnail mx-auto" id="ProfilePicPreview">
							<img src="<%= request.getContextPath()%>/images/icons/DefaultMember_Male.png" alt="會員頭貼預覽" id="ProfilePic">
						</div>
					</div>

				</div>

				<div class="row justify-content-around pt-4">
					<div class="col-4 p-0"></div>
					<div class="col-4 text-right p-0 pr-3 order-12" id="AutoFill">
						<input type="button" class="btn btn-warning" value="天使的自動筆記" id="Fill">
					</div>
					<div class="col-3 text-center p-0">
						<input type="hidden" name="action" value="insert">
						<input type="submit" class="btn btn-primary" value="註冊會員">
					</div>

				</div>
			</form>

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
					<form action="<%= request.getContextPath()%>/mem/memLogin.do" method="post">
						帳號 : <input type="email" class="textBox" name="mem_email"><br><br>
						密碼 : <input type="password" class="textBox" name="mem_psw"><br>
						<p><a href="<%= request.getContextPath()%>/front-end/mem/memResendPsw.jsp">忘記密碼</a></p><br>
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

	//依性別更改預設圖片
	$(":radio").change(function () {
        var radioValue = $("input[name='mem_gender']:checked").val();
        if (radioValue === "1") {
	        $("#ProfilePic").attr("src" , "<%= request.getContextPath()%>/images/icons/DefaultMember_Male.png")
	    } else {
            $("#ProfilePic").attr("src" , "<%= request.getContextPath()%>/images/icons/DefaultMember_Female.png")
        }
    });

	// 會員上傳頭貼預覽
    function readURL(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function(e) {
                $('#ProfilePic').attr('src', e.target.result);
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
		value: '${param.mem_dob}',
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

	//電子信箱驗證
	$("#TestEmail").click(function(){
		$.ajax({
			url: "/DA104G6/ajax/ajax.do",
			type:"POST",
			data:{
				mem_email:$("#InputEmail").val()
			},
			success:function(data){
				judgeEmail(data);
			},

			error : function() {
				Swal.fire({
					icon: 'warning',
					title: 'Oops...',
					text: 'Something went wrong!'
				})
			}
		});
	});

	function judgeEmail(data) {

		switch (data) {
			case "0":
					swal.fire({
						icon: 'error',
						title: '嗚哇',
						text: "此電子信箱已被使用過"
					});
				break;
			case "1":
					swal.fire({
						imageUrl: '<%= request.getContextPath()%>/images/CanUseEmail.gif',
						title: '耶嘿~',
						text: "此電子信箱可以使用"
					});
				break;
			case "2":
					swal.fire({
						icon: 'error',
						title: '唉唷',
						text: "電子信箱欄位請勿空白"
					});
					break;
			case "3":
				swal.fire({
					icon: 'error',
					title: '咦',
					text: "電子信箱格式錯誤"
				});
				break;

		}


	}

	//自動筆記

	$("#Fill").click(function () {
		$("#InputEmail").val("leonb860311@gmail.com");
		$("#InputPassword").val("123456");
		$("#InputNickName").val("不是你老婆的結衣");
		$("#f_date1").val("1988-6-11");
		$("#InputAddress").val("桃園市中壢區中央路232巷55號");
		$("#Female").prop("checked" , 'checked');
		$("#ProfilePic").attr("src" , "<%= request.getContextPath()%>/images/icons/DefaultMember_Female.png")
	});


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