<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <%--<body>
    <div class="pull-left">
        <div class="form-group form-inline">
            总共${page.totalPage} 页，共${page.total} 条数据。
        </div>
    </div>

    <div class="box-tools pull-right">
        <ul class="pagination" style="margin: 0px;">
            <li >
                <a href="javascript:goPage(1)" aria-label="Previous">首页</a>
            </li>
            <li><a href="javascript:goPage(${page.pre})">上一页</a></li>
            <c:forEach begin="${page.beg}" end="${page.end}" var="i">
                <li class="paginate_button ${page.page==i ? 'active':''}"><a href="javascript:goPage(${i})">${i}</a></li>
            </c:forEach>
            <li><a href="javascript:goPage(${page.next})">下一页</a></li>
            <li>
                <a href="javascript:goPage(${page.totalPage})" aria-label="Next">尾页</a>
            </li>
        </ul>
    </div>
    <form id="pageForm" action="${param.pageUrl}" method="post">
        <input type="hidden" name="page" id="pageNum">
    </form>
    <script>
        function goPage(page) {
            document.getElementById("pageNum").value = page
            document.getElementById("pageForm").submit()
        }
    </script>
    </body>--%>


    <body>
        <div class="pull-left">
            <div class="form-group form-inline">
                <form id="pageForm" action="${param.pageUrl}" method="post">

                    总共${page.pages} 页，共${page.total} 条数据。
                    <%--<select class="form-control" name="size">
                        <option ${page.size==5?'selected':''}>5</option>
                        <option ${page.size==10?'selected':''}>10</option>
                    </select> 条--%>
                    <input type="hidden" name="page" id="pageNum">
                </form>
            </div>
        </div>

        <div class="box-tools pull-right">
            <ul class="pagination" style="margin: 0px;">
                <%--首页--%>
                <li>
                    <a href="javascript:goPage(1)" aria-label="Previous">首页</a>
                </li>
                <%--上一页--%>
                <c:if test="${!page.hasPreviousPage}">
                    <li class="disabled"><a href="javascript:void(0);">上一页</a></li>
                </c:if>
                <c:if test="${page.hasPreviousPage}">
                    <li><a href="javascript:goPage(${page.prePage})">上一页</a></li>
                </c:if>


                <c:forEach begin="${page.navigateFirstPage}" end="${page.navigateLastPage}" var="i">
                    <li class="paginate_button ${page.pageNum==i ? 'active':''}"><a
                            href="javascript:goPage(${i})">${i}</a></li>
                </c:forEach>

                <%--下一页--%>
                <c:if test="${page.hasNextPage}">
                    <li><a href="javascript:goPage(${page.nextPage})">下一页</a></li>
                </c:if>
                <c:if test="${!page.hasNextPage}">
                    <li class="disabled"><a href="javascript:void(0);">下一页</a></li>
                </c:if>
                <%--尾页--%>
                <li>
                    <a href="javascript:goPage(${page.pages})" aria-label="Next">尾页</a>
                </li>
            </ul>
        </div>
        <%--<form id="pageForm" action="${param.pageUrl}" method="post">--%>
        <%--<select class="form-control" name="size">--%>
        <%--<option ${page.size==2?'selected':''}>2</option>--%>
        <%--<option ${page.size==3?'selected':''}>3</option>--%>
        <%--<option ${page.size==4?'selected':''}>4</option>--%>
        <%--<option ${page.size==5?'selected':''}>5</option>--%>
        <%--</select> 条--%>
        <%--<input type="hidden" name="page" id="pageNum">--%>
        <%--</form>--%>
        <script>
            function goPage(page) {
                document.getElementById("pageNum").value = page;
                document.getElementById("pageForm").submit()
            }
        </script>
    </body>
</html>
