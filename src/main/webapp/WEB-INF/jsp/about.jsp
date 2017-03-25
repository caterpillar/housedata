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
    <script src="http://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table-locale-all.min.js"></script>

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
                <li role="presentation"><a href="/houseData/review">数据查看</a></li>
                <li role="presentation" class="active"><a href="#">使用说明</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">屋啦数据抓取系统</h3>
    </div>


    <div class="container">

        <div class="blog-header">
            <h3 class="blog-title">屋啦数据抓取系统使用说明</h3>

            <p>屋啦数据抓取系统的简单使用说明，包括使用步骤、使用技巧和注意事项</p>
        </div>

        <div class="row">
            <div class="col-sm-11 blog-main">

                <div class="blog-post">
                    <p>

                        目前系统主要有两部分，<strong>城市配置</strong>和<strong>数据查看</strong>。
                    </p>
                    <hr>

                    <h3>城市配置</h3>

                    <p>在城市配置中，可以查看到系统中
                        默认初始化的全国600多个城市。用户可以从中选择需要抓取数据的城市，并点击“开始抓取”按钮开始抓取改该城市的楼盘数据数据。
                        由于数据抓取任务耗时，系统反馈给用户“数据已经开始抓取，请耐心等待”的信息后，后台以静默方式开始抓取数据。
                        此时用户可以刷新城市列表，可以看到对应城市的任务状态为“正在抓取”或“等待执行”，这表示用户选择的城市数据已经开始抓取或等待抓取。
                    </p>

                    <p>
                        为了防止系统过于频繁范围目标网站而导致的一些异常情况，系统一次只抓取一个城市数据，
                        多个城市会排队依次进行，所以需要用户耐心等待，一般单个城市的等待时间不会超过5分钟。
                    </p>
                    <blockquote>
                        <p>
                            等到城市对应的任务状态由<strong>正在抓取</strong>恢复到<strong>停止</strong>时表示数据抓取完成。
                            如果用户急于看到城市数据的抓取状态，用户可以每隔几秒中，根据正确的查询条件，点击“查询”按钮查看任务最新状态。
                        </p>
                    </blockquote>
                    <p>用户可以点击“数据查看”标签查看数据数据抓取结果。</p>
                    <blockquote>
                        <p>
                            为了防止由于网络原因导致的数据漏抓的情况，用户可以等到任务结束后，重复执行该城市对应的数据抓取任务，进行补抓。
                            系统会按照楼盘名称去重，不会重复入库。
                        </p>
                    </blockquote>
                    <h3>数据查看</h3>

                    <p>抓取的楼盘数据可以在该页面查看，并根据抓取的时间、城市、销售状态筛选和过滤。</p>

                    <p>用户还可以报查询出的数据导出到excel文件中，只需要按照相同的查询条件，并点击“导出”按钮，就可以直接下载excel文件。</p>
                    <blockquote>
                        <p>然而，随着时间的推移，数据抓取量会非常大。这里，并<strong>不建议</strong>用户不加任何检索条件，导出系统中的所有楼盘数据。
                            因为，这样对系统的性能尤其是数据库的性能损耗比较大。</p>
                    </blockquote>
                </div>
                <!-- /.blog-main -->

                <%--<div class="col-sm-3 col-sm-offset-1 blog-sidebar">--%>
                <%--<div class="sidebar-module sidebar-module-inset">--%>
                <%--<h4>About</h4>--%>
                <%--<p>Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>--%>
                <%--</div>--%>
                <%--<div class="sidebar-module">--%>
                <%--<h4>Archives</h4>--%>
                <%--<ol class="list-unstyled">--%>
                <%--<li><a href="#">March 2014</a></li>--%>
                <%--<li><a href="#">February 2014</a></li>--%>
                <%--<li><a href="#">January 2014</a></li>--%>
                <%--<li><a href="#">December 2013</a></li>--%>
                <%--<li><a href="#">November 2013</a></li>--%>
                <%--<li><a href="#">October 2013</a></li>--%>
                <%--<li><a href="#">September 2013</a></li>--%>
                <%--<li><a href="#">August 2013</a></li>--%>
                <%--<li><a href="#">July 2013</a></li>--%>
                <%--<li><a href="#">June 2013</a></li>--%>
                <%--<li><a href="#">May 2013</a></li>--%>
                <%--<li><a href="#">April 2013</a></li>--%>
                <%--</ol>--%>
                <%--</div>--%>
                <%--<div class="sidebar-module">--%>
                <%--<h4>Elsewhere</h4>--%>
                <%--<ol class="list-unstyled">--%>
                <%--<li><a href="#">GitHub</a></li>--%>
                <%--<li><a href="#">Twitter</a></li>--%>
                <%--<li><a href="#">Facebook</a></li>--%>
                <%--</ol>--%>
                <%--</div>--%>
                <%--</div><!-- /.blog-sidebar -->--%>

            </div>
            <!-- /.row -->

        </div>


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
</div>
<!-- /container -->

<script language="javascript">

</script>
</body>
</html>