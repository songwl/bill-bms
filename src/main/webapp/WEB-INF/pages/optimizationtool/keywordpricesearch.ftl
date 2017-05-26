<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "操作员列表">
<link href="${ctx}/static/css/bill/KeyWordsRanking.css" rel="stylesheet">
<link href="${ctx}/static/css/optimizationtool/css1.css" rel="stylesheet">
<link href="${ctx}/static/css/optimizationtool/css3.css" rel="stylesheet">
<script src="${ctx}/static/js/operator/operatorList.js"></script>
<script src="${ctx}/static/js/optimizationtool/keywordpricesearch.js"></script>
<div class="Navs">
    <div class="nav_L left">
        <i class="fa fa-home">&nbsp;</i><span>优化工具</span> > <span>关键词价格查询</span>
    </div>
    <div class="nav_R right" id="divQx">


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
<p class="title-line" style="font-weight:normal; margin:0 auto; padding-bottom:20px;margin-top: 20px;">
    <span>查询报价</span></p>
<div id="taocan">
    <div class='tc-content'>

        <div class="container center" style=' background-color: #ffffff;width: 1097px;'>
            <div class="nrong" style="margin-left:100px;">
                <div class="box price" style="margin-right: auto; width:500px; ">
                    <div class="grid935 bg_white" id="tabsbox">
                        <ul class="p_tab_conbox">
                            <li class="tab_con" style="display:block;">
                                <div class="price_batch">
                                    <div class="price_inquire">
                                        <textarea id="tKeywordMulti" name="tKeywordMulti" cols="" rows="" class="price_text font14" placeholder="一行一个关键词，请不要带空格和特殊符号" style="width:100%;height:100%;"></textarea>
                                    </div>
                                    <div class="price_explanation">
                                        <span class="price_anniu font20">批量查询</span>
                                </div>
                                <div class="hg10"></div>
                                <div class="clear"></div>
                                <div class="price_interest">
                                    <div class="price_interest_head">查询结果</div>
                                    <table width="750" id="biaoge2">
                                        <tr>
                                            <td colspan="2" style="width:16%;">关键词</td>
                                            <td colspan="2" style="width:16%;">
                                                <img src="/static/img/cxjg_img1.png" style="width:100px;" /></td>
                                            <td colspan="2" style="width:16%;">
                                                <img src="/static/img/cxjg_img3.png" style="width:100px;" /></td>
                                            <td colspan="2" style="width:16%;">
                                                <img src="/static/img/cxjg_img4.png" style="width:100px;" /></td>
                                            <td colspan="2" style="width:16%;">
                                                <img src="/static/img/cxjg_img5.png" style="width:100px;" /></td>
                                            <td colspan="2" style="width:16%;">
                                                <img src="/static/img/cxjg_img6.png" style="width:100px;" /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"></td>
                                            <td colspan="2">元/月</td>
                                            <td colspan="2">元/月</td>
                                            <td colspan="2">元/月</td>
                                            <td colspan="2">元/月</td>
                                            <td colspan="2">元/月</td>
                                        </tr>
                                        <tbody id="t_body">
                                        <tr>
                                            <td colspan="2">A</td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>

                                        <tr>
                                            <td colspan="2">B</td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>

                                        <tr>
                                            <td colspan="2">C</td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                            <td colspan="2"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </li>
                          </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear" style='background:#ffffff;'></div>
    </div>
</div>
</div>
</div>
</div>
</div>


</@base.html>