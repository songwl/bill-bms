<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "调点击">

<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<script src="${ctx}/static/js/dataStatistics/getBillOptimization.js "></script>

<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>数据统计</span> > <span>调点击</span>
    </div>
    <div class="nav_R right" id="divQx">

        <div class="search">
            <span>&nbsp;<i class="fa fa-search"></i>&nbsp;查询</span>
        </div>

    </div>
</div>
<div class="cls">
</div>
</div>
<div class="Navs2">
    <div class="nav_R2 right col-md-11" >
        <div>
            用户名:
        </div>
        <input id="searchUserName" name="keywords" class="form-control" value="" style="width: 150px;" type="text">
        <span style="font-size:13px;text-align:center;cursor:pointer;font-weight:bold;margin-left: 10px;" id="state">
            状态:
        </span>
        <select style="height: 35px;border: none;border: 1px solid #aaaaaa;"  id="searchState">
            <option  value="2">--请选择--</option>
            <option value="1">正常</option>
            <option value="0">冻结</option>
        </select>
        <span id="searchButton">查询</span>
    </div>
    <div class="cls">
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" id="list-panel">
            <div class="panel-body">
                <div class="panel-nav">
                    <div class="Nav_Left">&nbsp;<i class="fa fa-paper-plane"></i>&nbsp;调点击</div>
                    <div class="Nav_Right">

                    </div>
                    <div class="cls"></div>
                </div>
                <ul class="class-content">
                    <#list optimizationsList as item>
                        <li class="pull-left margin-large-35" style="border-top: 5px solid #d9d9d9;border-left: 1px solid #d9d9d9;border-right: 1px solid #d9d9d9;
                    border-bottom: 1px solid #d9d9d9;height: 200px;width:15%; margin: 20px 40px 0 10px;">
                            <div class="class-detail-top">
                                <div class="text-center" style="height:40px;line-height:40px;font-size:18px;font-weight:bold;background:#09c;color:#fff;">${item.userName}</div>
                                <ul class="class-detail-has margin-top text-lh-big" style="padding: 20px;">
                                    <li style="height: 30px;line-height: 30px;border-bottom: 1px solid #d9d9d9">
                                        <span class="text-black-gray" style="font-weight: 800;"><span class="fa fa-circle" style="color: #ff0000;"></span>本周点击</span>
                                        <span class="pull-right text-gray-white"><i class="fa fa-cny"></i>${item.weekOptimization}</span>
                                    </li>
                                    <li style="height: 30px;line-height: 30px;border-bottom: 1px solid #d9d9d9">
                                        <span class="text-black-gray" style="font-weight: 800;"><span class="fa fa-circle" style="color: #00ff00;"></span>本月点击</span>
                                        <span class="pull-right text-gray-white"><i class="fa fa-cny"></i>${item.monthOptimization}</span>
                                    </li>
                                    <li style="height: 30px;line-height: 30px;border-bottom: 1px solid #d9d9d9">
                                        <span class="text-black-gray" style="font-weight: 800;"><span class="fa fa-circle" style="color: #0000ff;"></span>总点击</span>
                                        <span class="pull-right text-gray-white"><i class="fa fa-cny"></i>${item.allOptimization}</span>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </#list>

                </ul>


            </div>

        </div>
    </div>
</div>


<div class="modal-backdrop in" style="display: none">
</div>

<!--客户充值end-->
</@base.html>