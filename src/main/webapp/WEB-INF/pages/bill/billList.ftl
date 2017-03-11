<#import "/base/base.ftl" as base>
<#import "/base/func.ftl" as func>
<@base.html "关键词优化">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/bill/billList.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化管理</span> > <span>关键词排名</span>
    </div>
    <div class="nav_R right" id="divQx">
        <div>
            <span>&nbsp;<i class="fa fa-play"></i>&nbsp;优化启动</span>
        </div>
        <div>
            <span>&nbsp;<i class="fa fa-certificate"></i>&nbsp;优化调整</span>
        </div>

        <div>
            <span>&nbsp;<i class="fa fa-stop"></i>&nbsp;合作停止</span>
        </div>
        <div>
            <span>&nbsp;<i class="fa fa-trash"></i>&nbsp;删除</span>
        </div>
        <div>
            <span>&nbsp;<i class="fa fa-dollar"></i>&nbsp;调价</span>

        </div>
        <div class="Import">
            <span id="Import">&nbsp;<i class="fa fa-arrow-down"></i>&nbsp;导入</span>
            <ul class="ImportPrice">
                <li id="Sameprice">相同价导入</li>
                <li id="Differentprice">不同价导入</li>
            </ul>
        </div>

        <div>
            <span>&nbsp;<i class="fa fa-arrow-up"></i>&nbsp;导出</span>
        </div>
        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>

    </div>
    <div class="cls">
    </div>
</div>
<div class="Navs2">

    <div class="nav_R2 right" >
        <div>
            客户ID:
        </div>
        <input name="acid" class="form-control" value="" style="width: 60px;" type="text">
    </div>
    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;关键词排名</div>
                    <div class="Nav_Right">
                        <div>优化中</div>
                        <div>合作停</div>
                        <div>全部</div>


                    </div>
                    <div class="cls"></div>
                </div>
                <table id="myTable" class="table table-striped " style="width:100%">


                </table>
            </div>

        </div>
    </div>
</div>
</@base.html>