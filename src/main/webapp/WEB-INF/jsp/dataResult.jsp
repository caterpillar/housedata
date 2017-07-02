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
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <link href="http://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.css" rel="stylesheet">
    <script src="http://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>

    <script src="../js/bootstrap-datetimepicker.min.js"></script>
    <script src="../js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

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
                <li role="presentation"><a href="/home/index">城市配置</a></li>
                <li role="presentation" class="active"><a href="#">数据查看</a></li>
                <li role="presentation"><a href="/home/about">使用说明</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">屋啦数据抓取系统</h3>
    </div>


    <%--<h4>系统中已抓取的数据</h4>--%>

    <div id="toolbar">
        <div class="form-inline" role="form">
            <div class="form-group">
                <%--<span>开始: </span>--%>
                <%--<input name="beginTime" class="form-control w70" type="number" value="0">--%>
                <div class="input-append date" id="startTimeDiv" startView="1" data-date-format="yyyy-mm-dd hh:ii">
                    <input name="startTime" id="startTime" class="form-control" size="16" type="text" readonly>
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
            </div>
            <div class="form-group">
                <%--<span>结束: </span>--%>
                <%--<input name="endTime" class="form-control w70" type="number" value="5">--%>
                <div class="input-append date" id="endTimeDiv" startView="2" data-date-format="yyyy-mm-dd hh:ii">
                    <input name="endTime" id="endTime" class="form-control" size="16" type="text" readonly>
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
            </div>
            <div class="form-group">
                <input name="cityName" id="cityName" class="form-control" type="text" placeholder="城市">
            </div>
            <div class="form-group">
                <input name="saleStatus" id="saleStatus" class="form-control" type="text" placeholder="销售状态">
            </div>
            <button id="ok" class="btn btn-default">查询</button>
            <button id="exportExcl" class="btn btn-success">导出</button>
            <button id="deleteData" class="btn btn-danger">删除</button>
        </div>
    </div>
    <table id="table"
           data-toggle="table"
           data-url="/houseData/getHouseInfo"
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
            <th data-field="cityName">城市</th>
            <th data-field="title">标题</th>
            <th data-field="area">区域</th>
            <th data-field="section">商圈</th>
            <th data-field="address">地址</th>
            <th data-field="saleStatus">销售状态</th>
            <th data-field="originUrl">请求地址</th>
            <th data-field="createTime">抓取时间</th>

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
    $('#startTimeDiv').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true
    });
    $('#endTimeDiv').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true
    });
    var $table = $('#table'),
        $button = $('#ok');

    $(function () {
        $button.click(function () {
            $table.bootstrapTable('refresh');
        });
    });

    $(function () {
        $('#exportExcl').click(function () {
            var url = '/houseData/exportExcel';
            var params = queryParams(undefined);
            requestUrl(url, parseParam(params), 'POST');
        });
        $('#deleteData').click(function () {
            var url = '/houseData/deleteHouseData';
            var params = queryParams(undefined);
            requestUrl(url, parseParam(params), 'POST');
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
    function responseHandler(res) {
        return res.rows;
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
    function requestUrl(url, data, method) {
        // 获取url和data
        if (url && data) {
            // data 是 string 或者 array/object
            data = typeof data == 'string' ? data : jQuery.param(data);
            // 把参数组装成 form的  input
            var inputs = '';
            $.each(data.split('&'), function () {
                var pair = this.split('=');
                inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
            });
            // request发送请求
            jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>')
                .appendTo('body').submit().remove();
        };
    };
</script>
</body>
</html>