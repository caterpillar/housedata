<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>屋啦数据抓取系统</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 首页css样式 -->
    <link rel="stylesheet" href="../css/index.css">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <link href="http://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.css" rel="stylesheet">
    <script src="http://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation" class="active"><a href="#">城市配置</a></li>
                <li role="presentation"><a href="/houseData/review">数据查看</a></li>
                <li role="presentation"><a href="/home/about">使用说明</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">屋啦数据抓取系统</h3>
    </div>

    <div id="toolbar">
        <div class="form-inline" role="form">
            <div class="form-group">
                <input name="cityName" id="cityName" class="form-control" type="text" placeholder="城市">
            </div>
            <button id="ok" class="btn btn-default">查询</button>
            <button id="grabStartBtn" class="btn btn-warning">启动任务</button>
        </div>
    </div>

    <table id="table"
           data-toggle="table"
           data-url="/home/getCityConfig"
           <%--data-height="600"--%>
           data-side-pagination="server"
           data-pagination="true"
           data-page-list="[5, 10, 20, 50, 100, 200]"
           data-click-to-select="true"
    <%--data-search="true"--%>
           data-query-params="queryParams"
    <%--data-response-handler="responseHandler"--%>
           data-toolbar="#toolbar"
           data-toolbar-align="right">
        <thead>
        <tr>
            <th data-field="state" data-checkbox="true"></th>
            <th data-field="id">ID</th>
            <th data-field="name">城市</th>
            <th data-field="requestUrl">请求url</th>
            <th data-field="taskStatus">任务状态</th>
        </tr>
        </thead>
    </table>

    <div class="row marketing">
        <div class="col-lg-6">
            <h4>按城市抓取</h4>
            <p>按照城市批量抓取数据</p>
        </div>

        <div class="col-lg-6">
            <h4>查看和导出</h4>
            <p>查看导出数据结果，并可以实时导出</p>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2017 上海屋啦信息科技有限公司</p>
    </footer>

</div>
<!-- /container -->

<script language="javascript">
    function startGrab() {
        var selections = $table.bootstrapTable('getSelections');
        if (selections.length == 0) {
            alert('请选择需要发起任务的城市');
            return;
        }

        var confirmResult = confirm("请确认抓取选择城市数据");
        if (confirmResult) {
            var params = 'n=1';
            $(selections).each(function(i, d) {
                params = params + '&cityId=' + d.id;
            });
            $.ajax({
                type: 'POST',
                url: '/houseData/startGrabTask',
                data: params,
                success: function (data) {
                    alert(data);
                },
                error: function (data) {
                    alert(data)
                }
            });
        }
    }
    $(function () {
        $('#grabStartBtn').click(startGrab);
    });

    var $table = $('#table'),
            $button = $('#ok');

    $(function () {
        $button.click(function () {
            $table.bootstrapTable('refresh');
        });
    });

    function queryParams(params) {
        if (params == undefined) {
            params = {};
        }
        $('#toolbar').find('input[name]').each(function () {
            params[$(this).attr('name')] = $(this).val();
        });
        return params;
    }

    function parseParam(param, key) {
        var paramStr = "";
        if (param instanceof String || param instanceof Number || param instanceof Boolean) {
            paramStr += "&" + key + "=" + param;
        } else {
            $.each(param, function (i) {
                var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
                paramStr += '&' + parseParam(this, k);
            });
        }
        return paramStr.substr(1);
    }
</script>
</body>
</html>