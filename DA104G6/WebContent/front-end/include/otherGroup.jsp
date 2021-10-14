<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reviewGroup.css">

</head>
<body>
	<jsp:useBean id="ssvc" class='com.sign_up.model.Sign_upService'/>
	<jsp:useBean id="gsvc" class='com.group.model.GroupService'/>
	<jsp:useBean id="msvc" class='com.mem.model.MemService'/>


	<div class="row" style="margin-right: -21px">
		<div class="col-12" id="ReviewTableBody" style="overflow-x: hidden ; overflow-y: scroll; height:760px">
			<table class="table reviewBody text-center table-striped">
				<thead class="thead-light">
				<tr>
					<th scope="col" class="titleItem text-nowrap">行程名稱</th>
					<th scope="col" class="titleItem text-nowrap">主揪人</th>
					<th scope="col" class="titleItem text-nowrap">評價</th>
					<th scope="col" class="titleItem text-nowrap">詳情</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var='signvo' items='${ssvc.getProgress(param.mem_no)}' varStatus='i'>
					<tr>
						<td>
							<c:forEach var='gvo' items='${gsvc.allGroup}'>
								<c:if test="${signvo.gro_no eq gvo.gro_no}">
									${gvo.gro_name}
								</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach var='gvo' items='${gsvc.allGroup}'>
								<c:if test="${signvo.gro_no eq gvo.gro_no}">
									<c:forEach var='mvo' items='${msvc.allMem}'>
										<c:if test="${gvo.mem_no eq mvo.mem_no}">
											${mvo.mem_name}
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</td>
						<td>
							<div id='${signvo.gro_no}' class='reviewed' data-review='${signvo.total_review}'>

							</div>
						</td>
						<td>
							<form action="<%=request.getContextPath() %>/front-end/Group/group.do" class="m-0">
								<button type="submit" class="reviewGroupBtn btn btn-info" name="action" value="getOne_For_Display">詳情</button>
								<input type="hidden" name="gro_no" value="${signvo.gro_no}">
							</form>
						</td>
					</tr>
				</c:forEach>

				</tbody>
			</table>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/js/jquery.raty.min.js"></script>
	<script>
		var review = $('.reviewed')
		if(review.length != 0){
			for(let i = 0; i < review.length; i++){
				console.log(review[i].id);
				$('#' + review[i].id).raty({
					half: true,
		    	    starHalf: '<%=request.getContextPath()%>/images/icons/star-half-big.png',
		    	    starOff: '<%=request.getContextPath()%>/images/icons/star-off-big.png',
		    	    starOn: '<%=request.getContextPath()%>/images/icons/star-on-big.png',
		   	        readOnly: true,
		   	        score: $('#' + review[i].id).attr('data-review')
				});
			}
		}
	</script>
</body>
</html>