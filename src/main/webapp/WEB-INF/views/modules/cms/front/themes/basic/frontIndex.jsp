<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="JeeSite ${site.description}" />
	<meta name="keywords" content="JeeSite ${site.keywords}" />
</head>
<body>
   <%--  <div class="hero-unit" style="padding-bottom:35px;margin:10px 0;">
      <c:set var="article" value="${fnc:getArticle('2')}"/>
      <h1>${fns:abbr(article.title,28)}</h1><p></p>
      <p>${fns:abbr(fns:replaceHtml(article.articleData.content),260)}</p>
      <p><a href="${article.url}" class="btn btn-primary btn-large">&nbsp;&nbsp;&nbsp;查看详情 &raquo;&nbsp;&nbsp;&nbsp;</a></p>
    </div> --%>
    <div class="row clearfix home-function">
        <div class="service-title">
            <h5>客户服务</h5>
            <hr>
            <h6>customer service</h6>
        </div>
        <div class="quick-service" id="quick-search">
            <i></i>
           <div class='quick-sea'>
                   <h4>快速查询</h4>
                   <p>输入单号进行查询</p>
                   <textarea name='code' id='code' cols='30' rows='6'></textarea>
                   <div><button id="seachByCode" type='button'>查询</button></div></div>
        </div>
        <div class="quick-service" id="quick-mail">
            <i></i>
            <h4>快速寄件</h4>
            <p>在线寄件方便快捷</p>
        </div>
        <div class="quick-service" id="quick-evaluate">
            <i></i>
            <h4>快速评价</h4>
            <p>输入单号进行评价</p>
        </div>
    </div>
    <div class="row clearfix news-function">
        <div class="news-banner">
            <img src="" alt="">
        </div> 
        <div class="tabbable"> <!-- Only required for left/right tabs -->
            <ul class="nav nav-tabs">
                <li class="active"><a href="#tab1" data-toggle="tab">组织机构</a></li>
                <li><a href="#tab2" data-toggle="tab">质量监督</a></li>
                <li><a href="#tab3" data-toggle="tab">政策法规</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                    <ul class="tab-list"><c:forEach items="${fnc:getArticleList(site.id, 2, 8, '')}" var="article">
                        <li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy-MM-dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
                    </c:forEach></ul>
                    <div class="text-center">
                        <a href="${ctx}/list-2${urlSuffix}" class="btn btn-primary btn-more">更多</a>
                    </div>
                </div>
                <div class="tab-pane" id="tab2">
                    <ul class="tab-list"><c:forEach items="${fnc:getArticleList(site.id, 6, 8, '')}" var="article">
                        <li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy-MM-dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
                    </c:forEach></ul>
                    <div class="text-center">
                        <a href="${ctx}/list-6${urlSuffix}" class="btn btn-primary btn-more">更多</a>
                    </div>
                </div>
                <div class="tab-pane" id="tab3">
                    <ul class="tab-list"><c:forEach items="${fnc:getArticleList(site.id, 10, 8, '')}" var="article">
                        <li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy-MM-dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
                    </c:forEach></ul>
                    <div class="text-center">
                        <a href="${ctx}/list-10${urlSuffix}" class="btn btn-primary btn-more">更多</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div class="modal hide fade" id="online-evaluate" aria-backdrop="hidden">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3>快速评价</h3>
        </div>
        <div class="modal-body">
            <table>
                <colgroup>
                    <col>
                    <col>
                </colgroup>
                <tbody>
                    <tr>
                        <td>快递单号</td>
                        <td><input id="code2" type="text" placeholder="请输入快递单号"></td>
                    </tr>
                    <tr>
                        <td>评价内容</td>
                        <td>
                            <textarea name="comment" id="comment"  rows="5"></textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
            <!-- <a href="#" class="btn btn-primary online-btn">提交</a> -->
             <button class="online-btn">提交</button>
        </div>
    </div>
    <script>
        $(function(){
            var $quickMail = $("#quick-mail"),
                $quickSearch = $("#seachByCode"),
                $quickEvaluate = $("#quick-evaluate");


            var $onlineMail = $("#online-mail"),
                $onlineEvaluate = $("#online-evaluate"),
                $onlineMailSub = $onlineMail.find("button.online-btn"),
                $onlineEvaluateSub = $onlineEvaluate.find("button.online-btn");

           //快速查询
            $quickSearch.on("click",function(){
               var code=$("#code").val();
               window.open("${ctxAddOrder}/list?code="+code);
            });

            //快速寄件
            $quickMail.on("click",function(){
              window.open("${ctxAddOrder}/form");
            });

            //快速评价
            $quickEvaluate.on("click",function(){
              $onlineEvaluate.modal();
            });
            
            //提交按钮
            $onlineEvaluateSub.click(function(){
            	var code2=$("#code2").val();
            	var comment=$("#comment").val();
            	 window.open("${ctxAddOrder}/saveComment?code="+code2+"&comment="+comment);
            });
        });
    </script>
</body>
</html>