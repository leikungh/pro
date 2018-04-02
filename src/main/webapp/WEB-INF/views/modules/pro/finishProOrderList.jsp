<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/pro/proOrder/list2">订单列表</a></li>
		<shiro:hasPermission name="pro:proOrder:edit"><li><a href="${ctx}/pro/proOrder/finishForm">订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="proOrder" action="${ctx}/pro/proOrder/list2" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>收件人姓名：</label>
				<form:input path="receiveName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>收件人姓名</th>
				<th>寄件人姓名</th>
				<th>物流状态</th>
				<th>评价</th>
				<th>更新时间</th>
				<shiro:hasPermission name="pro:proOrder:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="proOrder">
			<tr>
				<td><a href="${ctx}/pro/proOrder/finishForm?id=${proOrder.id}">
					${proOrder.code}
				</a></td>
				<td>
					${proOrder.receiveName}
				</td>
				<td>
					${proOrder.sendName}
				</td>
				<td>
					${fns:getDictLabel(proOrder.status, 'status', '')}
				</td>
				<td>
					${proOrder.comment}
				</td>
				<td>
					<fmt:formatDate value="${proOrder.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="pro:proOrder:edit"><td>
    				<a href="${ctx}/pro/proOrder/finishForm?id=${proOrder.id}">修改</a>
					<a href="${ctx}/pro/proOrder/finishDelete?id=${proOrder.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>