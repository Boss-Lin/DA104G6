<%@page import="com.sign_up.model.Sign_upVO" %>
<%@page import="java.util.List" %>
<%@page import="com.mem.model.MemVO" %>
<%@page import="com.sign_up.model.Sign_upService" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reviewGroup.css">
	<script src="<%=request.getContextPath()%>/js/jquery.raty.min.js"></script>

    <title>Bike Seeker</title>
</head>


<body>

<%
    HttpSession Signsession = request.getSession();
    MemVO memVO = (MemVO) Signsession.getAttribute("memVO");
    Sign_upService svc = new Sign_upService();
    List<Sign_upVO> signList = svc.getBySelf(memVO.getMem_no());
    pageContext.setAttribute("signList", signList);
%>

<div class="row" style="margin-right: -21px">
    <div class="col-12" id="ReviewTableBody" style="overflow-x: hidden ; overflow-y: scroll; height:760px">
        <table class="table reviewBody text-center table-striped">
            <thead class="thead-light">
            <tr>
                <th scope="col" class="titleItem text-nowrap">團名</th>
                <th scope="col" class="titleItem text-nowrap">審核狀態</th>
                <th scope="col" class="titleItem text-nowrap">詳情</th>
                <th scope="col" class="titleItem text-nowrap">聊天室</th>
                <th scope="col" class="titleItem text-nowrap">評分</th>
                <th scope="col" class="titleItem text-nowrap">退出行程</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="grosvc" scope="page" class="com.group.model.GroupService" />
            <c:forEach var="signVO" items="${signList}">
                <tr>
                    <td><c:forEach var="grovo" items="${grosvc.allGroup}">
                        <c:if test="${signVO.gro_no eq grovo.gro_no}">
                            ${grovo.gro_name}
                        </c:if>
                    </c:forEach></td>
                    <td>${signUpStatus[signVO.status]}</td>
                    <td>
                        <form action="<%=request.getContextPath() %>/front-end/Group/group.do" class="m-0">
                            <button type="submit" class="reviewGroupBtn btn btn-info" name="action" value="getOne_For_Display">詳情</button>
                            <input type="hidden" name="gro_no" value="${signVO.gro_no}">
                        </form>
                    </td>
                    <td>
                        <form action="<%=request.getContextPath() %>/front-end/Group/group.do" class="m-0">
<%--                         	<c:if test="${(signVO.status ne 1 && signVO.status ne 3)}"> --%>
                        	<button type="submit" class="btn chat" name="action" value="groChat" style="border: none;" ${(signVO.status ne 1 && signVO.status ne 3)? '' : 'disabled'}><img src="<%=request.getContextPath()%>/images/icons/Group_Chat_Icon.png"></button>
<%--                         	</c:if> --%>
                            <input type="hidden" name="gro_no" value="${signVO.gro_no}">
                            <input type="hidden" name="mem_no" value="<%=memVO.getMem_no() %>">
                        </form>
                    </td>
                    <td>
                        <!-- 報名狀態不等於以評分(4) 才顯現評分 -->
                        <c:if test="${signVO.status ne 4 }">
                            <button type="button" class="btn btn-warning reviewgro" data-toggle="modal" data-target="#reviewModal" value="${signVO.gro_no}">評分
                            </button>
                        </c:if>
                        <c:if test="${signVO.status eq 4}">
                            <div id="${signVO.gro_no}" class='reviewed' data-review="${signVO.review}"></div>
                        </c:if>
                    </td>

                    <td>
                    	<c:forEach var='grovo' items='${grosvc.allGroup}'>
                    		<c:if test="${signVO.mem_no ne grovo.mem_no && signVO.gro_no eq grovo.gro_no}">
                        		<button type="button" class="btn btn-danger quit" data-toggle="modal" data-target="#quitModal" value="${signVO.gro_no}">退出</button>
                        	</c:if>
                        	<c:if test="${signVO.mem_no eq grovo.mem_no && signVO.gro_no eq grovo.gro_no}">
                        		<button type="button" class="btn btn-danger dismiss" data-toggle="modal" data-target="#dismissModal" value="${signVO.gro_no}">解散</button>
            				</c:if>
                    	</c:forEach>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>


<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content reviewContent text-center">
            <div class="modal-header">
                <h5 class="modal-title" style="color: #000" id="ModalLabelReview">給予評分</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body review">
                <div id="star"></div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>

                <form method="post" action="<%=request.getContextPath() %>/front-end/Sign_up/sign_up.do"
                      class="reviewSubmit">
                    <button type="submit" id="starSub" class="btn btn-primary" name="review" value="">送出評分</button>
                    <input type="hidden" name="action" value="review">
                    <input type="hidden" id="grocatch" name="gro_no" value="">
                </form>

            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="quitModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h5 class="modal-title" id="exampleModalCenterTitle">退出此行程?</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>

                <form action="<%=request.getContextPath() %>/front-end/Sign_up/sign_up.do" class="reviewSubmit">
                    <button type="submit" id="quitSubmit" class="btn btn-primary" name="gro_no" value="">確定</button>
                    <input type="hidden" name="action" value="quit">
                </form>

            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="dismissModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <h5 class="modal-title" id="DismissModalCenterTitle">取消此行程?</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                <form action="<%=request.getContextPath() %>/front-end/Group/group.do" class="reviewSubmit" method="post">
                    <button type="submit" id='primarySubmit' class="btn btn-primary" name="gro_no" value="">確定</button>
                    <input type="hidden" name="action" value="delete">
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    $('#star').raty({
        cancel: true,
        cancelOff: '<%=request.getContextPath()%>/images/icons/cancel-off-big.png',
        cancelOn: '<%=request.getContextPath()%>/images/icons/cancel-on-big.png',
        size: 24,
        targetType: 'number',
        half: true,
        starHalf: '<%=request.getContextPath()%>/images/icons/star-half-big.png',
        starOff: '<%=request.getContextPath()%>/images/icons/star-off-big.png',
        starOn: '<%=request.getContextPath()%>/images/icons/star-on-big.png',
        click: function (score) {
            $('#starSub').val(score);
        }
    });


    
    //評分星星
    if($('.reviewed').length != 0){
    	var reviewed = $('.reviewed');
    	for(let i = 0; i < reviewed.length; i++){
	    	$('#' + reviewed[i].id).raty({
	   	        half: true,
	    	       starHalf: '<%=request.getContextPath()%>/images/icons/star-half-big.png',
	    	       starOff: '<%=request.getContextPath()%>/images/icons/star-off-big.png',
	    	       starOn: '<%=request.getContextPath()%>/images/icons/star-on-big.png',
	   	        readOnly: true,
	   	        score: $('#' + reviewed[i].id).attr('data-review')
	   	    });
    	}
    }
    
    $('.reviewgro').click(function () {
        var value = $(this).val();
        console.log(value);
        $('#grocatch').val(value);
    });

    $('.quit').click(function () {
        var quitValue = $(this).val();
        $('#quitSubmit').val(quitValue);
    });
    
    $('.dismiss').click(function() {
    	var dismissValue = $(this).val();
    	$('#primarySubmit').val(dismissValue);
    });

    <c:if test="${not empty situation.notFinish}">
    swal.fire({
        icon:'warning',
        title:'哈囉?',
        text:"行程還未結束，不可評價"
    });
    <% request.removeAttribute("notFinish"); %>
    </c:if>
    
    <c:if test="${not empty situation.notReview}">
    swal.fire({
        icon:'error',
        title:'哈囉?',
        text:"請選擇評分"
    });
    <% request.removeAttribute("notFinish"); %>
    </c:if>

    <c:if test="${not empty situation.success}">
    swal.fire({
        icon:'success',
        title:'成功',
        text:"已送出評價!!"
    });
    <% request.removeAttribute("success"); %>
    </c:if>

    <c:if test="${not empty situation.isEnd}">
    swal.fire({
        icon:'warning',
        title:'oops',
        text:"行程已結束!!"
    });
    <% request.removeAttribute("isEnd"); %>
    </c:if>

    <c:if test="${not empty situation.quit}">
    swal.fire({
        icon:'success',
        title:'GoodBye',
        text:"已退出此行程"
    });
    <% request.removeAttribute("quit"); %>
    </c:if>


</script>

</body>
</html>