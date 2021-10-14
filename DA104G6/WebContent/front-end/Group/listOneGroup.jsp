<%@page import="com.sign_up.model.Sign_upService"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.group.model.GroupVO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--網址縮圖-->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/listOneGroup.css">

    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>

    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->

    <title>行程詳情瀏覽</title>

</head>
<body onload="CheckAlert()">

<!---------------------------------------------- 取得單筆詳情的vo --------------------------------------------->

<%
    GroupVO vo = (GroupVO) request.getAttribute("OneGroup"); //動過
    Map<String, String> error =  (Map<String, String>)request.getAttribute("errorMsgs");
    Map<Integer, String> status = (HashMap) request.getServletContext().getAttribute("groupStatus");
%>
<jsp:useBean id="msvc" class="com.mem.model.MemService"/>
<jsp:useBean id="rsvc" class='com.route.model.RouteService'/>
<!---------------------------------------------- 取得單筆詳情的vo --------------------------------------------->

						<jsp:include page="/front-end/include/NavBar.jsp" flush="true" />

<!--------------------------------------------------- 內容 --------------------------------------------------->
<div class="container-fluid">
    <div class="row justify-content-around align-items-center">

        <div class="col-12 mb-4" id="GroupCoverCol">

            <img src="<%= request.getContextPath() %>/front-end/Group/group.do?getImg=<%=vo.getGro_no()%>" alt="揪團封面圖" id="GroupCover">

        </div>

        <!----------------------------------------------- 顯示單筆資料詳情 ---------------------------------------------->
        <div class="col-8 p-5" id="GroupFile">
            <div class="row justify-content-around align-items-center">
                <div class="col-9">
                    <h1><%=vo.getGro_name()%></h1>
                    <br>
                    <p><%=vo.getIntro() == null ? "尚無簡介" : vo.getIntro()%></p>
                </div>

                <div class="col-3" style="height: 86px">
                    <div style="display: inline-block;vertical-align: middle ;font-size: 24px">揪團評分 : <%=vo.getTotal_review()%>/5.0 分</div><br>
                    <div style="display: inline-block;vertical-align: middle ;font-size: 24px">成員數量 : <%=vo.getComfirm_mem()%>/<%=vo.getPeo_limit().equals(100)? "&#8734;" : vo.getPeo_limit()%> 人</div><br>
                </div>

                <div><hr style="border: 3px solid #7b4735; width: 78em"></div>

                <div class="col-12">
                    <table class="table table-borderless">
                        <tbody>
                        <tr><th scope="row">團主 : </th><td>
                        <a href="<%=request.getContextPath()%><c:if test="${OneGroup.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${OneGroup.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${OneGroup.mem_no}</c:if>">${msvc.getOneMem(OneGroup.mem_no).mem_name}</a>
                        </td></tr>
                        <tr><th scope="row">集合地 : </th><td><%=vo.getMuster()%></td></tr>
                        <tr><th scope="row">集合時間 : </th><td><fmt:formatDate value="<%=vo.getTime()%>" pattern="yyyy-MM-dd HH:mm:ss"/></td></tr>
                        <tr><th scope="row">活動天數 : </th><td><%=vo.getDuration()%>天</td></tr>
                        <tr><th scope="row">連絡電話 : </th><td><%=vo.getPhone()%></td></tr>
                        <tr><th scope="row">揪團建立日期 : </th><td><fmt:formatDate value="<%=vo.getCreate_time()%>" pattern="yyyy-MM-dd HH:mm:ss"/></td></tr>
                        <tr><th scope="row">報名截止日 : </th><td><fmt:formatDate value="<%=vo.getDeadline()%>" pattern="yyyy-MM-dd HH:mm:ss"/></td></tr>
                        <tr><th scope="row">狀態 : </th><td><%=status.get(vo.getStatus())%></td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!----------------------------------------------- 顯示單筆資料詳情 ---------------------------------------------->

        <!----------------------------------------------- 一些按鈕操作 ---------------------------------------------->

        <div class="col-2 text-center pt-3" id="ControlPanel">

            <c:if test = "${memVO.mem_no ne OneGroup.mem_no}">
                <form class = "submitOne" method= "get" id="submitjoin" action="<%=request.getContextPath() %>/front-end/Sign_up/sign_up.do">
                    <input type="hidden" name = "gro_no" value="<%=vo.getGro_no()%>">
                    <input type = "hidden" name = 'requestURL' value = '<%=request.getServletPath() %>'>
                    <button type="submit" class="btn control" name="action" value="insert"><img src="<%= request.getContextPath()%>/images/icons/JoinGroup_Icon.png"><br>馬上加入</button>
                </form>
            </c:if>

            <c:if test="${memVO.mem_no eq OneGroup.mem_no}">
                <button type="button" class="btn control" data-toggle="modal" data-target="#verify"><img src="<%= request.getContextPath()%>/images/icons/VerifyMember_Icon.png"><br>審核成員</button>
            </c:if>

            <form action="<%=request.getContextPath()%>/front-end/Group/group.do" method = "post">
                <button type="submit" class="btn control" name="action" value = "getAll_For_Display"><img src="<%= request.getContextPath()%>/images/icons/ListAllGroup_Icon.png"><br>行程瀏覽</button>
                <input type="hidden" name = 'page' value='${param.page}'>
                <input type="hidden" name='requestURL' value='${param.requestURL}'>
            </form>
            <c:if test="${not empty sessionScope.memVO}">
            	<button data-toggle="modal" data-target="#bugModal" class="btn control"><img src="<%= request.getContextPath()%>/images/icons/ReportGroup_Icon.png"><br>檢舉此揪團</button>
			</c:if>
        </div>
        <!----------------------------------------------- 一些按鈕操作 ---------------------------------------------->


        <!------------------------------------------------ 顯示封面、路線圖 ---------------------------------------------->
        <div class="col-12" id="MapPage">
            <div class="mt-3 mb-3 ml-5">
                <img src="<%= request.getContextPath()%>/images/icons/Google_Map_Icon.png" style="width: 100px;height: 100px">
                <span style="font-size: 50px; vertical-align: middle">路線圖</span>
            </div>
            <div id="map"></div>
        </div>
        <!------------------------------------------------ 顯示封面、路線圖 ---------------------------------------------->

    </div>
</div>
<!--------------------------------------------------- 內容 --------------------------------------------------->

<jsp:include page="/front-end/include/Footer.jsp" flush="true"/>

<!--------------------------------------------------- 檢舉跳窗 --------------------------------------------------->
<div class="modal fade" id="bugModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">檢舉</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="POST" enctype="multipart/form-data" action="<%=request.getContextPath() %>/back-end/Group_Report/gro_report.do">
                    <div class="form-group">
                        <input type="hidden" name="gro_no" value="<%=vo.getGro_no()%>">
                        <input type="hidden" name="mem_no" value="${memVO.mem_no}">
                        <label for="recipient-name" class="col-form-label">檢舉人:</label>
                        <font class="errReport">${(not empty errorMsgs)? errorMsgs.memErr : ''}</font>
                        <input class="form-control" type="text" id = "recipient-name" name = "mem_name" readonly value = "${memVO.mem_name}">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">原因:</label>
                        <textarea class="form-control" id="message-text" name="reason">${errorVo.reason}</textarea>
                    </div>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="customFile" name="proof">
                        <label class="custom-file-label" for="customFile">佐證</label>
                        <font class="errReport">${(not empty errorMsgs)? errorMsgs.imgErr : ''}</font>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
                        <button type="submit" class="btn btn-primary" name="action" value="insert">送出</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!--------------------------------------------------- 檢舉跳窗 --------------------------------------------------->

<!--------------------------------------------------- 審核跳窗 --------------------------------------------------->

<!-- 取出service物件 -->
<jsp:useBean id="ssvc" class="com.sign_up.model.Sign_upService"/>
<%
    Sign_upService svc = (Sign_upService)pageContext.getAttribute("ssvc");
%>

<div class="modal fade" id="verify" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="verifyTitle">審核列表</h5>
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-sm">
                    <tbody>
                    <c:forEach var="signvo" items="<%=svc.getVerify(vo.getGro_no()) %>">
                        <tr>
                            <th scope="row" class="memno">${msvc.getOneMem(signvo.mem_no).mem_name}</th>
                            <td><button type="button" class="btn btn-light" onclick="location.href = '<%=request.getContextPath()%>/mem/mem.do?action=getOne_For_Display&mem_no=${signvo.mem_no}'">資訊</button></td>
                            <td><button type="button" class="btn btn-success successVerify" value="${OneGroup.gro_no}" id="${signvo.mem_no}">允許</button></td>
                            <td><button type="button" class="btn btn-danger failVerify" value="${OneGroup.gro_no}" id="${signvo.mem_no}">拒絕</button></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!--------------------------------------------------- 審核跳窗 --------------------------------------------------->





<!------------------------------------------------ bootstrap.js --------------------------------------------->
<script src="<%= request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="<%=request.getContextPath()%>/js/sweetAlert2 9.5.2.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap"  async defer></script>
<jsp:include page="/front-end/include/LoginAlerts.jsp" />
<script type="text/javascript">

	//地圖init
    var myInitCenter = {lat: 24.9680014, lng: 121.1900142};
    window.onload = initMap;
    function initMap(){
        var map;//地圖物件
        var directionsService = new google.maps.DirectionsService();//繪製服務
        var directionsDisplay = new google.maps.DirectionsRenderer();//繪製路線

        map = new google.maps.Map(document.getElementById('map'), {
            center: myInitCenter,
            zoom: 12
        });
        
        <c:forEach var='routevo' items='${rsvc.all}'>
        	<c:if test='${OneGroup.route_no eq routevo.route_no}'>
        		var gpx = JSON.stringify(${routevo.route_gpx});
        	</c:if>
        </c:forEach>

        var result = JSON.parse(gpx);
        directionsDisplay.setMap(map);
        directionsDisplay.setDirections(result);
    }

	//更改寄信(通知)
	var sendMail = '${mail}';
	if(sendMail != ''){
		<%request.removeAttribute("mail");%>
		$.ajax({
			url: '<%=request.getContextPath()%>/front-end/Sign_up/sign_up.do',
			type: 'post',
			data: {
				action: 'mail',
				gro_no: sendMail,
				message: 'update'
			},
			success: function(){console.log('發送成功')},
			error: function(){console.log('發送失敗')}
		});
	}
	
    function CheckAlert() {
        if(${not empty errorMsgs}){
            $("#bugModal").modal();
            <%request.removeAttribute("errorMsgs"); %>
        }
    }

    <c:if test="${not empty success}">
    swal.fire({
        icon:'success',
        title:'成功',
        text:"已發送檢舉"
    });
    <%request.removeAttribute("success");%>
    </c:if>


    <c:if test="${not empty verifySuccess}">
    $('#verify').modal();
    <% request.removeAttribute("verifySuccess"); %>
    </c:if>


    $('.successVerify').click(function(){
        $.ajax({
            url: '<%=request.getContextPath()%>/front-end/Sign_up/sign_up.do',
            type: 'post',
            data: {
                action: 'verify',
                verify: 'success',
                gro_no: $(this).val(),
                mem_no: $(this).attr('id')
            },
        });
        $(this).parent().parent().css('display', 'none');
    });

    $('.failVerify').click(function(){
        $.ajax({
            url: '<%=request.getContextPath()%>/front-end/Sign_up/sign_up.do',
            type: 'post',
            data: {
                action: 'verify',
                verify: 'fail',
                gro_no: $(this).val(),
                mem_no: $(this).attr('id')
            },
        });
        let i = $('#curpe').text();
        $('#curpe').text(i - 1);
        $(this).parent().parent().css('display', 'none');
    });

    <c:if test="${not empty situation.success}">
    swal.fire({
        icon:'success',
        title:'YEAH',
        text:"報名成功，等待審核"
    });
    <% request.removeAttribute("success"); %>
    </c:if>

    <c:if test="${not empty situation.isFull}">
    swal.fire({
        icon:'warning',
        title:'oops',
        text:"人數已滿"
    });
    <% request.removeAttribute("isFull"); %>
    </c:if>

    <c:if test="${not empty situation.registered}">
    swal.fire({
        icon:'warning',
        title:'oops',
        text:"已報名，等待審核"
    });
    <% request.removeAttribute("registered"); %>
    </c:if>

    <c:if test="${not empty situation.overTime}">
    swal.fire({
        icon:'warning',
        title:'oops',
        text:"報名截止日已過"
    });
    <% request.removeAttribute("overTime"); %>
    </c:if>
    
    <c:if test="${not empty conflict}">
    swal.fire({
        icon: 'error',
        title: '時間有衝突!!，已有參加的行程',
        html: '${conflict}' + '\n'
    });
    <% request.removeAttribute("conflict"); %>
    </c:if>
</script>
</body>
</html>