<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>

<@base.html "写信息">

<style>@charset "gb2312";

body{
    background-color:#f1f4f4;
}

#MainContentDIV{
    background-color:#fff;
}

dl,body,form,dt,dd{
    margin:0;padding:0;
}

.gd_system{

}
.gd_system .gd_tips{ background:#f1faff url(/newimages/Manager/gongdan/iconimg.gif) no-repeat -90px -1390px ; border-radius:5px; border:1px solid #a7deff; font-size:14px; color:#444444; padding:10px 10px 10px 35px;}
.gd_system  .xtime{background:#ffffbe ;border:solid 1px #ffb38d;padding:10px 10px 10px 10px; text-align:center;color: #e82600;}
/*全局定义*/
.gd_system .ocolor{color:#FF6600;}
.gd_system .floatr{ float:right;}
.gd_system .floatl{ float:left;}
.gd_system p{ line-height:normal; margin:0; padding:0;}
.gd_system .bcolor{ color:#1e7bb4; font-weight:bold;}
.gd_system .lcolor{ color:#006dcc; }
.gd_system  .smallbtn,.gd_system  .bigbtn,.gd_system  .uploadbtn,.gd_system .smalllock{ padding:6px 18px; cursor:pointer; border:0; color:#fff; display:inline-block; background:#1e7bb4; border-radius:5px;}
.gd_system  .smalllock{ color:#fff; background:#419E00;border:1px solid #2AAB0A}

.gd_system  .bigbtn{ padding:10px 50px;}
.gd_system  .smallbtn:hover,.gd_system  .bigbtn:hover{background:#F60;}
.gd_system  .uploadbtn{ padding:4px 30px; background:#d2eeff;  border:1px solid #1e7bb4;}
.gd_system .fgreen{ color:green;}
.gd_system .redcolor{ color:#ff0000;}
.gd_system .blackcolor{ color:#333;}
.gd_system a.boldlink{ color:#1e7bb4; font-weight:bold;}
.gd_system a.boldlink:hover{ color:#fc5c00;}
.gd_system a.scorelink{ color:#009eff;}
.gd_system .scorelink{ color:#009eff; cursor:pointer;}
.gd_system a.jclink{ color:#ff3c00;}
.gd_system .gd_btn{font-size: 16px;}
/*tab start*/
.gd_system .tabout { padding-top:0; padding-bottom:15px;}
.gd_system .tabout ul.gd_tab{ background:#f0f0f0; border:1px solid #dddddd;height:44px; position:relative;margin: 0 -20px;}
.gd_system .tabout ul.gd_tab li{ height:43px;  padding:0 28px; line-height:43px; font-size:14px; display:inline-block; border-right:1px solid #ddd; float:left; 	-webkit-transition:all 0.2s cubic-bezier(0.33,0.66,0.66,1);
    -moz-transition:all 0.2s cubic-bezier(0.33,0.66,0.66,1);
    -o-transition:all 0.2s cubic-bezier(0.33,0.66,0.66,1);
    transition:all 0.2s cubic-bezier(0.33,0.66,0.66,1);}
.gd_system .tabout ul.gd_tab li a{ display:inline-block;}
.gd_system .tabout ul.gd_tab li.tab_active{ background:#fff; border-top:2px solid #1e7bb4; color:#1e7bb4; font-weight:bold;}
.gd_system .tabout ul.gd_tab i{ background:#1e7bb4; color:#fff; font-size:10px; padding:2px; margin-left:2px; border-radius:3px; height:13px; line-height:13px;position:absolute;top:10px; font-style:normal; font-weight: normal; font-family: Tahoma, Geneva, sans-serif;min-width:9px }
.gd_system .inputtext {background-color: #FFF;border: 1px solid #CCC;box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.1) inset;color: #666;cursor: text;height: 22px;line-height: 22px;padding: 3px 5px;   width: 330px;  margin-top: 3px;}
.gd_system .inputtext:hover {    border: 1px solid #2F8ABE;}



/*提交工单*/
/*选择业务*/
.gd_system .con_out{ border-top:none; padding-top:10px;/* overflow:hidden; zoom:1;*/}
.gd_system .gd_option{ padding-top:10px; height:112px;}
.gd_system .con_out ul.gd_option li{ background:#f8f8f8 url(/newimages/Manager/gongdan/iconimg.gif) no-repeat center top; border:1px solid #dddddd;height:55px; width:163px; cursor:pointer; display:inline-block; margin: 0px -1px 0px 0px; float:left; text-align:center; font-size:14px; padding-top:55px;}
.gd_system .con_out ul.gd_option li a{ display:inline-block;height:110px; cursor:pointer; border:1px solid red; width:133px; position:relative; z-index:2;}
.gd_system .con_out ul.gd_option li a:hover{ color:#666;}
.gd_system .con_out ul.gd_option li.tab_active{ background-color:#fff; border-top:2px solid #1e7bb4; border-bottom:1px solid #fff; height:54px; font-weight:bold; color:#1e7bb4;}
.gd_system .con_out ul.gd_option li.hover_option{ background-color:#fff; border-top:2px solid #1e7bb4; border-bottom:1px solid #fff; height:54px; font-weight:bold; color:#1e7bb4;	-webkit-transition:all 1s cubic-bezier(0.33,0.66,0.66,1);
    -moz-transition:all 1s cubic-bezier(0.33,0.66,0.66,1);
    -o-transition:all 1s cubic-bezier(0.33,0.66,0.66,1);
    transition:all 1s cubic-bezier(0.33,0.66,0.66,1);}
.gd_system .con_out ul.gd_option li:hover{  -o-transition: color .20s linear;
    -webkit-transition: color .20s linear;
    -moz-transition: color .20s linear;
    transition:  color .20s linear;}
.gd_system .con_out ul.gd_option li.option_icon2{ background-position:center -204px;}
.gd_system .con_out ul.gd_option li.option_icon3{ background-position:center -407px;}
.gd_system .con_out ul.gd_option li.option_icon4{ background-position:center -611px;}
.gd_system .con_out ul.gd_option li.option_icon5{ background-position:center -814px;}
.gd_system .con_out ul.gd_option li.option_icon6{ background-position:center -1018px;}
.gd_system .con_out ul.gd_option li.option_icon7{ background-position:center -1221px;}
/*左边问题类型选择*/
/*.gd_system .gd_item { position:relative; padding-top:19px; }*/
.gd_system .out_option,.gd_system .out_option2{ position:relative; padding-top:19px; }
.gd_system .gd_item { overflow:hidden; zoom:1;}
.gd_item .gd_arrow, .gd_item .gd_arrow2{background:url(/newimages/Manager/gongdan/iconimg.gif) no-repeat left -1478px; width:26px; height:20px; position:absolute; top:0px; left:190px;display:none;}
.gd_item .gd_arrow2{top:0px; left:460px;}
.gd_item .s_option{ border:1px solid #ddd; padding-bottom:10px;overflow:hidden; zoom:1; }
.gd_item .leftwidth{width:94%; }
.gd_item .s_option .s_alink,.gd_item .s_option .s_alink2{ background:#f6f6f6; overflow:hidden; zoom:1;}
.gd_item .s_option .s_alink  a,.gd_item .s_option .s_alink2  a{ display:inline-block;  float:left; padding:10px 12px; cursor:pointer; font-size: 14px;}
.gd_item .s_option .s_alink  a.tab_active,.gd_item .s_option .s_alink2  a.tab_active{color:#1e7bb4; font-weight:bold; border-bottom:2px solid #1e7bb4;}

.gd_item .s_radio{ padding: 0px 15px 0 15px ; }
.gd_system .sleft .s_radio,.gd_item .tutorial { height:320px; overflow:auto;}
.tutorialvps{padding:15px;min-height:100px;}
.tutorialvps h1{ padding:0; margin:0; color: #30729B; font-size:16px; line-height:25px;}
.tutorialvps p{ line-height:25px; margin-bottom:10px;}

.gd_item .sleft{ padding-top:10px;width: 60%;}
.gd_item .s_radio li{ list-style:none; margin:0;  padding:7px 0; font-size:14px;}
.gd_item .s_radio label:hover{color:#1E7BB4;}
.gd_item .s_radio li em{color:#ff0000;padding-left:10px; font-size:12px}
.gd_system .marginl{ margin:15px 0 10px 10px;}


/*左边问题类型选择 end*/

/*右边帮助教程*/
.gd_system .gd_item .tutorial{ background:#f6f6f6; border:1px solid #ddd; float:right; width:34%; padding:15px; display:inline-block; margin:10px;}
.gd_system .gd_item .tutorial h1{ color:#30729b; margin:0 0 10px 0; padding:0;}
.gd_system .gd_item .tutorial p{ line-height:23px; margin-bottom:10px; /*text-indent:2em;*/}
.gd_system .gd_item .tutorial a:hover{ color:#F60;}
/*右边帮助教程 end*/

/*VPS云主机 选择*/
.gd_system .gd_item .s_option .s_alink  a.upgrade_alink{ font-weight:bold;color: #1E7BB4; padding:0px; float:right; }
.gd_system .gd_item .s_option .s_alink .floatr{ float:right;padding:10px 12px;}
/*vps云主机 服务表*/
table.gd_table {
    width:100%;
    margin:auto;
    border-collapse:collapse;
    margin-top: 10px;

}
table.gd_table tr.gd_tbg{ background:#f8f8f8;}
table.gd_table tr th {
    border-bottom:1px #dddddd solid;
    color:#666; font-size:12px;
    text-align:left;
    padding-bottom:5px;

}
table.gd_table tr td {
    padding:6px 0px;
    color:#666;
    text-align:left;

}

table.gd_table .gdtit{ cursor:default; padding-left:5px;}
table.gd_table .table_jc{ border:1px solid #fbeed5; background:#fcf8e3; padding:5px 10px;}

table.gd_table td{
    font-size:14px;
}
/*vps云主机 服务表 end*/

/*VPS云主机 选择 end*/


/*提交表单*/
.gd_system .form_layout{ clear:both; padding-top:20px;}
.gd_system .gd_form{ background:#fbfbfb; border:1px solid #ddd;  margin-top:10px; padding:15px; overflow:hidden; zoom:1;}
.gd_system .gd_form dl{ font-size:14px; color:#333; padding:5px 0; overflow:hidden; zoom:1;line-height: 22px;}
.gd_system .gd_form dl dt{ width:124px;float:left; text-align:right; font-size:14px; display:inline-block; padding-right:15px;}
.gd_system .gd_form dl dd{ float:left; font-size:14px;line-height:180%;}
.gd_system .gd_form dl dt strong{color:red;}

/*sun add */
.w150{width:150px;display:inline-block;}
.per35{width:35%;display:inline-block;}
.gd_system #auto_binds{ font-size:16px ; color:#004080; font-family: Verdana, Arial; background: transparent;}

/*.gd_system .gd_form dl label { padding-right:15px; padding-bottom:5px; display:inline-block; width:320px;}*/
/*.gd_system .gd_form dl .sel_input{ width: 290px; font-size:14px;}*/

/*

div.selector { width: 290px;}
div.selector span {    width: 258px;}
*/

/*
.gd_system .gd_form ul li  .inputtext {
    background-color: #FFF;
    border: 1px solid #CCC;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.1) inset;
    color: #666;
    cursor: text;
    height: 22px;
    line-height: 22px;
    padding: 3px 5px;
    width: 330px;
    margin-top: 3px;
}
.gd_system .gd_form ul li .inputtext:hover {
    border: 1px solid #2F8ABE;
}
*/

.gd_system .gd_btn{ border:none; font-size:16px; margin-top:15px; cursor:pointer;}
/*提交表单 end */

/*选择业务 end*/




/*工单状态*/
table .question_zt{ display:inline-block; padding:1px 8px; color:#fff; border-radius:5px;}
table .ztbg1{background:#bd362f; }
table .ztbg2{background:#006dcc; }
table .ztbg3{background:#fff;color:#008040;font-weight:bold;}
table .ztbg61{background:#51a351; }
table .ztbg62{background:#3C3CFF;}
/*tab end */

/*工单列表*/

/*问题列表*/
.gd_mytable table.question_table {
    width:100%;
    margin:auto;
    border-collapse:collapse;
}
.gd_mytable table.question_table tr.gd_tbg{ background:#f8f8f8;}
.gd_mytable table.question_table tr th {
    border:1px #dddddd solid;
    color:#666; font-size:14px;
    text-align:center;
    padding:10px;
    background:#f0f0f0;
}

.gd_mytable table.question_table tr td {
    padding:8px 10px;
    color:#666;
    text-align:center;
    border:1px #dddddd solid;
}
.gd_mytable table.question_table .question_link{ text-align:left;width:400px; }
.gd_search{ padding:0 0 10px 0; font-size:14px;}
.gd_search  .inputtext{ width:200px; margin:0 10px;}

/*page start*/
.pagelist{  padding:10px 0 0 8px; float:right;}
.pagelist a{ font-size:14px; border:1px solid #ddd;display:inline-block;
    width:auto;
    height: 24px;
    line-height: 24px;
    margin-right:2px;
    padding: 0 10px;
    text-align: center;}
.pagelist a:hover{border:1px solid #2f8abe; color:#2f8abe;}
.pagelist a.active_page{ background:#2f8abe; color:#fff; border:1px solid #2f8abe;}
/*page end*/


/*问题列表 end*/

/*工单列表 end*/
.t_bg{background:#F0F3F8;}
.t_hover{color:red;}
.div_more{margin:0 auto;font-size:14px;background:#FFF9ED; width:100%; height:100%;text-align:center; line-height:35px;}

a.yd_link:link, a.yd_link:visited, a.yd_link:active{ color:#06c; }
a.yd_link:hover{ color:#ff8432; text-decoration:underline;}
.gd_infotable .key_dt{
    color:#1E7BB4;
    margin-right:5px;
}
.gd_infotable .key_dd{
    margin-right:15px;
    color:#666;
    /*border-bottom:1px dashed #FF3300;*/
}

/*问题详情*/
.gd_system .gd_progress_out{width:960px; margin:0 auto;}
.gd_system .gd_prolayout{/*width:837px;*/ width:940px;  margin:0 auto;}
.gd_system .gd_progress,.gd_system .gd_progress2{ height:33px; background:url(/newimages/Manager/gongdan/progress.gif) no-repeat left bottom;width:940px; }
.gd_system .gd_progress2{ background-position:left top; }
.gd_system .gd_progress_status1{width:32px;}
.gd_system .gd_progress_status2{width:256px;}
.gd_system .gd_progress_status3{width:479px;}
.gd_system .gd_progress_status4{width:703px;}
.gd_system .gd_progress_status5{width:940px;}
.gd_system .pro_status{  padding-top:5px; font-size:14px;}
.gd_system .pro_status .marginleft{ margin-left:168px;}
.gd_system .pro_status .marginleft2{margin-left:160px;*margin-left:145px; _margin-left:150px;}

.gd_system .gd_infos{background:#f1faff; border-radius:5px; border:1px solid #a7deff;  color:#444444; padding:10px 20px; margin-top:20px; }
table.gd_infotable {    width: 100%;    margin: auto;    border-collapse: collapse;}
table.gd_infotable td{font-size:14px; padding:5px 0; word-wrap: break-word;
    word-break: break-all;}
table.gd_infotable .infowidth{ width:180px; }

/*问题消息*/
.gd_message{ overflow:hidden; zoom:1; font-size:14px; padding-top:15px;}
.gd_message .gd_user,.gd_message .gd_manage{  padding-bottom:15px;}
.gd_message .tw_time{ color:#666; font-size:12px;}
.gd_message .gd_user{ float:left;}
.gd_message .gd_manage{ float:right; }
.gd_message .user_left{background:url(/newimages/Manager/gongdan/iconimg.gif) no-repeat right -1518px ; float:left; margin-right:-1px; z-index:2;position: relative; margin-top:10px; display:inline-block; text-align:right; font-size:14px; padding-right:13px;}
.gd_message .user_right{ float:left; background:#f8f9fd; border:1px solid #e2e3e4; border-radius:5px; padding:10px; line-height:22px; width:620px; font-size:14px;word-wrap: break-word; word-break: break-all; color:#444444; margin-top:10px;}
.gd_message .user_right p{font-size:14px;}

.gd_message .manage_right{background:url(/newimages/Manager/gongdan/iconimg.gif) no-repeat left -1678px; float:left; margin-left:-1px; z-index:2;position: relative; margin-top:10px; display:inline-block; text-align:left; font-size:14px; padding-left:13px;}
.gd_message .manage_left{ float:left; background:#fffef5; border:1px solid #ede6ae; border-radius:5px; padding:10px;  width:620px;word-wrap: break-word; word-break: break-all; margin-top:10px;}
.gd_message .message_con{font-size:14px;line-height:22px; color:#ff3300; padding-bottom:10px; border-bottom:1px solid #f5efc2;}
.gd_message .message_con p{font-size:14px;line-height:22px; color:#1E7BB4;}
.gd_message .message_other{ padding:10px 0 0 0;}
.gd_message .message_other p{ padding:3px 0; color:#444;}
.gd_message .message_other .user_pj{background:url(/newimages/Manager/gongdan/iconimg.gif) no-repeat -101px -1803px ;color:#444; padding-left:18px; }
.gd_message .message_other .user_pj .inputtext {    width:180px;}
.message_other .haoping,.message_other .zhongping,.message_other .chaping{background:url(/newimages/Manager/gongdan/iconimg.gif) no-repeat 50px -1848px ; display:inline-block; width:80px;}
.message_other .zhongping{ background-position:50px -1894px;}
.message_other .chaping{ background-position:50px -1940px;}

.gd_message_out .yw_btn{ background:url(/newimages/Manager/gongdan/yiwenbtn.gif) no-repeat; display:inline-block; width:233px; height:59px; font-size:26px; line-height:59px; color:#2c4e63; text-align:center; text-indent:40px;}
.gd_message_out .ping_fw{ font-size:14px; margin-left:10px;}
.gd_message_out .yiwen_l{ text-align:center; margin:0 auto; padding:15px 0;}


div.gd_reply{ padding:15px 0;text-align:center}
.gd_message_out .gd_reply small {
    width: 120px;
    text-align: right;
    font-size: 14px;
    display: inline-block;
    padding-right: 15px;
}


/*问题消息 end*/

/*问题详情 end*/

/*滚动条*/
#scrollbar4 { width: 520px; margin: 20px 0 10px; }
#scrollbar4 .viewport { width: 500px; height: 200px; overflow: hidden; position: relative; }
#scrollbar4 .overview { list-style: none; position: absolute; left: 0; top: 0; padding: 0; margin: 0; }
#scrollbar4 .scrollbar{ position: relative; background-position: 0 0; float: right; width: 15px; }
.s_radio .track { background-color: #ccc; height: 100%; width:5px; position: relative;}
.s_radio .thumb { background:#cc0071; height: 20px; width:5px; cursor: pointer; overflow: hidden; position: absolute; top: 0; left: 0; }


.edui-container{
    border:0;
}
.edui-container .edui-toolbar{
    border:0;
    background:#FBFBFB;
    box-shadow:none;
}
.edui-editor-body .edui-body-container{
    border:1px solid #96BFE7;
}
.edui-body-container p{
    font-size:14px;
    line-height:140%;
    font-family:Verdana;
    margin-top:15px;
}
.edui-body-container img{
    width:100px;
    height:70px;
}

.gd_system input.btnlock{
    background-color:#ccc;
    color:#666;
}







/* 智能*/
.autocomplete-suggestions { border: 1px solid #999; background: #FFF; cursor: default; overflow: auto; -webkit-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); -moz-box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); box-shadow: 1px 4px 3px rgba(50, 50, 50, 0.64); }
.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
.autocomplete-no-suggestion { padding: 2px 5px;}
.autocomplete-selected { background: #F0F0F0; }
.autocomplete-suggestions strong { font-weight: bold; color: #000; }
.autocomplete-group { padding: 2px 5px; }
.autocomplete-group strong { font-weight: bold; font-size: 16px; color: #000; display: block; border-bottom: 1px solid #000; }


/*vps云主机 教程*/
.tutorial_con{ border:1px solid #ddd;  margin:10px 20px;margin-top:40px;}
.faq_tutorialvps{ padding:10px 0 10px 15px; font-size:14px; font-weight:bold;border-bottom:1px dashed #ddd;}
a.direct_tj{ margin-left:18px; color: #1E7BB4; font-weight:bold;}
.use_btn{ padding:0 20px 15px 0; font-size:14px; text-align:right; clear: both;}
.gd_system a.useful{ background:#1E7BB4 url(/newimages/Manager/gongdan/zan.gif) no-repeat 5px center; text-indent:8px;}
.gd_system a.useful:hover{ background:#f60 url(/newimages/Manager/gongdan/zanhover.gif) no-repeat 5px center;}
a.useless{padding: 6px 18px; border:1px solid #1e7bb4; background:#fff; border-radius:5px;}
a.useless:hover{background:#F60;border:1px solid #F60; color:#fff;}

/*工单引导*/
.tp_img{ margin:0 auto; text-align:center;  padding:30px 0;}
.pic_tips{ background:url(/newimages/manager/gongdan/yd_icon.jpg) no-repeat left center;width:650px;  margin:0 auto; height:241px; }
.pic_tips p{padding-left:200px;text-align:left; font-size:18px; line-height:25px; padding-top:80px; }
.xiaban{background:url(/newimages/manager/gongdan/yd_icon_xb.jpg) no-repeat left center;}
.free_ms{ border:1px solid #ddd; padding:20px; margin-top:10px;}
.free_ms h1{ margin:0 0 10px 0; padding:0; border-bottom:1px dashed #ddd; padding-bottom:10px;}
.free_ms p{ font-size:14px; padding-bottom:10px; line-height:22px;}
.mbottom{ margin-bottom:10px;}



.attupload{
    background:url(/images/20050703091653449.gif) no-repeat left center;
    padding-left:12px;
    margin-left:10px;
    font-size:14px;
    color:#333;
    display:inline-block;
}

.fl{float:left;}
.fr{float:right;}
.clear{clear:both;}
#attimglist li{
    display:inline-block;margin-right:5px;position:relative;
    overflow:hidden;
}
#attimglist li img {
    height:60px;
    width:80px;
}
#attimglist li a.close{
    position:absolute;
    left:2px;
    border:1px solid #fff;
    background:#fff;
    font-size:12px;
}






img.usertx{
    width:60px;
    height:60px;
    border-radius:30px;
}

a.ac_link{
    border:1px solid #C6C8DF;
    display:inline-block;
    max-width:180px;
    max-height:100px;
    margin-right:5px;
    background:#fff;
    overflow:hidden;
}
a.ac_link:hover{
    background:#C6C8DF;
    border-color:#66CC66;
}
a.ac_link img{
    border:0;
    max-width: 180px;
    max-height: 100px;
}


.gopay{
    background:url("/newimages/Manager/045631239.gif") no-repeat scroll 0px 1px transparent;
    padding-left:20px;
}

a.cyx{ background:#ddf3ff url("/newimages/manager/gongdan/cyx.gif") no-repeat 5px center; padding:2px 5px; border:1px solid #b1d7ec; border-radius:3px; display:inline-block; text-indent:25px;}
a.cyx:hover{ border:1px solid #ffdc7e; background:#fdff50  url("/newimages/manager/gongdan/cyx.gif") no-repeat 5px center;}

.helpps{
    background:url(/newimages/on_help_ico.jpg) no-repeat;
    padding-left:20px;
}

.longmsg{
    text-align:center;
    background:url(/newimages/Manager/tabicons_060.gif) no-repeat left center;
    padding-left:20px;
    color:#0080C0;
}

@media screen and (max-width: 1206px) {
    .gd_option{
        width:950px;
        margin: 0 auto;
    }
    .gd_system .con_out ul.gd_option li{
        width:134px;
    }
}
.desktop .gd_system .con_out ul.gd_option li{
    width:134px;
}
.desktop .gd_option{
    width:950px;
    margin: 0 auto;
}</style>


<div id="MainContentDIV">
    <div class="gd_system">



        <!--    <div class="gd_tips">欢迎使用西部数码有问必答系统</div>-->

        <!-- 工单系统 start-->
        <div class="tabout">

            <ul class="gd_tab">
                <li class="tab_active"><a href="index.asp">提交工单</a></li>
                <li><a href="list.asp?module=1">待处理工单</a></li>
                <li><a href="list.asp">所有工单</a></li>
            </ul>

            <!-- 提交工单-->
            <div class="con_out">
                <div class="gd_submit  pt-10">
                    <p class="font14">请选择您需要咨询的业务:</p>


                    <ul class="gd_option">
                        <li class="option_icon1" tag="101">市场咨询<br>渠道问题</li>
                        <li class="option_icon2" tag="102">虚拟主机<br>数据库问题</li>
                        <li class="option_icon3" tag="103">域名问题</li>
                        <li class="option_icon4 hover_option" tag="888">主机租用<br>vps、云主机</li>
                        <li class="option_icon5" tag="204">财务问题</li>
                        <li class="option_icon6" tag="104">企业邮局</li>
                        <li class="option_icon7" tag="801">其他问题</li>
                    </ul>

                    <div class="tp_img">

                        <div class="pic_tips">
                            <p>请根据您的业务类型选择上面相应的工单类型来提交。<br>我们提供7x24小时服务支持！</p>
                        </div>

                        <div class="product-detail-desc free_ms">
                            <h1 class="fgreen">关于工单服务范围的说明</h1>
                            <p>我们向您提供产品的同时，也为您提供了完善的售后服务。在产品使用过程中您可能遇到一些问题，请您先参看<a href="/faq" target="_blank"><font color="#0000FF">常见问题</font></a>栏目，也许其中就有您遇到问题的解决方法。在常见问题中找不到相关答案的，再提交工单联系客服人工协助。</p>
                            <p> 对于云主机我们提供的服务： 咨询西部数码主机产品有关功能和服务，与西部数码云资源相关的操作或系统问题的故障诊断，与西部数码云主机的管理控制台或其他西部数码官方工具相关的问题。</p>
                            <p> 我们暂时不提供的服务：1. 代码开发 2. 第三方软件的调试 3. 日常运维服务 </p>
                            <p>对于第三方软件与主机内部应用程序（如PHP+Mysql配置、伪静态设备、网站挂马清除等），属于客户应自行处理的范畴。对于确实不太懂技术的客户，我们也可以提供收费服务.此类工单会有费用产生。</p>
                        </div>
                    </div>

                    <div class="gd_item"></div><!--gd_item-->
                    <div class="gd_item"></div>
                    <div class="gd_item"></div>
                    <div class="gd_item"></div>
                    <div class="gd_item"></div>
                    <div class="gd_item"></div>
                    <div class="gd_item"></div>

                    <!--FROM-->
                    <form name="mygdform" method="post" onsubmit="return false;">

                        <div class="gd_form " style="display: none;">
                            <dl>
                                <dt>工单标题：</dt>
                                <dd>
                                    <input type="text" placeholder="请输入问题简要介绍" class="inputtext" name="subject"> 建议填写<div id="subject_tip"></div>
                                </dd>
                            </dl>

                            <div id="gdform_moban"><!--mobans-->
                            </div>
                            <div id="gdform_binds"><!--binds-->
                                <dl><dt><strong>*</strong>绑定业务：</dt>
                                    <dd>
                                        <input type="text" name="binds" id="auto_binds" class="inputtext" value="" autocomplete="off">
                                        <span>.</span>
                                    </dd></dl>
                            </div>
                            <div id="gdform_system"><!--system-->
                            </div>

                            <dl>
                                <dt>问题描述：</dt>
                                <dd>
                                    <div class="edui-container" style="width: 740px; z-index: 999;"><div class="edui-toolbar"><div class="edui-btn-toolbar" unselectable="on" onmousedown="return false"><div class="edui-btn edui-btn-drafts " unselectable="on" onmousedown="return false" data-original-title="草稿箱"> <div unselectable="on" class="edui-icon-drafts edui-icon westicon"></div><div class="edui-tooltip" unselectable="on" onmousedown="return false"><div class="edui-tooltip-arrow" unselectable="on" onmousedown="return false"></div><div class="edui-tooltip-inner" unselectable="on" onmousedown="return false"></div></div></div><div class="edui-btn edui-btn-removeformat " unselectable="on" onmousedown="return false" data-original-title="清除格式"> <div unselectable="on" class="edui-icon-removeformat edui-icon westicon"></div><div class="edui-tooltip" unselectable="on" onmousedown="return false"><div class="edui-tooltip-arrow" unselectable="on" onmousedown="return false"></div><div class="edui-tooltip-inner" unselectable="on" onmousedown="return false"></div></div></div><div class="edui-separator" unselectable="on" onmousedown="return false"></div><div class="edui-btn edui-btn-image " unselectable="on" onmousedown="return false" data-original-title="上传或粘贴图片"> <div unselectable="on" class="edui-icon-image edui-icon westicon"></div><div class="edui-tooltip" unselectable="on" onmousedown="return false" style="z-index: 1000;"><div class="edui-tooltip-arrow" unselectable="on" onmousedown="return false"></div><div class="edui-tooltip-inner" unselectable="on" onmousedown="return false"></div></div></div></div><div class="edui-dialog-container"></div></div><div class="edui-editor-body"><div id="Content" class=" edui-body-container" contenteditable="true" style="width: 720px; min-height: 240px; z-index: 999;"><p><br></p></div></div></div>
                                </dd>
                            </dl>

                            <dl>
                                <dt>回复通知：</dt>
                                <dd>

                                    <label class="fl"><input type="checkbox" name="tzfs" value="youjian">邮件通知我</label>

                                    <!--<label><input type="checkbox" name="tzfs" value="duanxin" />短信通知我(扣费0.1元)</label>&nbsp;&nbsp;-->

                                    <label class="fl"><input type="checkbox" name="tzfs" value="weixin">微信通知我</label>

                                    <label id="btnupload" class="fl attupload jUploader-button" style="cursor: pointer; position: relative; overflow: hidden; direction: ltr;"><span>上传附件</span><input type="file" onchange="this.blur();" id="jUploader-file1" name="upfile" style="position: absolute; right: 0px; top: 0px; margin: 0px; opacity: 0; padding: 0px; font-family: Arial; font-size: 118px; vertical-align: baseline; cursor: pointer;"></label>

                                    <label class="fl helpps tip" original-title="可上传3个附件,每个附件大小不得超过1M. 附件支持的格式有:'jpg', 'png', 'gif'">&nbsp;</label>

                                </dd>
                            </dl>

                            <dl>
                                <dt>&nbsp;</dt>
                                <dd>
                                    <div id="gdform_message" class="redcolor"><!--message--></div>
                                    <input id="btnsubmit" type="button" class="bigbtn gd_btn" value="提交工单">
                                </dd>
                                <div class="fr" id="attimglist"></div>
                                <div class="clear"></div>

                            </dl>
                        </div>
                        <!--FORM-->
                    </form>

                </div><!--gd_submit-->
            </div><!--con_out-->
        </div><!--tabout-->
    </div><!--gd_system-->
    <script type="text/javascript">
        var wel_dv = $(".gd_submit .tp_img");
        var arrpos = [60,190,320,457,595,728,860];
        var moption_li = $('.gd_option>li');	//大菜单块
        var gdform_dv = $('.gd_form');			//表单
        var gditem_dv=$(".gd_submit>.gd_item");

        var moban_dv = $("#gdform_moban");
        var binds_dv = $("#gdform_binds");
        var message_dv = $("#gdform_message");
        var system_dv = $("#gdform_system");
        var subjecttip_dv = $("#subject_tip")
        var lenovlab = "";

        var btnsubmit = $("#btnsubmit");
        var gdform = window.document.mygdform;
        var skipclass = false;	//跳过二级

        moption_li.hover(function(){
            $(this).addClass('hover_option');
        }, function(){
            moption_li.removeClass("hover_option");
        }).click(function(){
            if(isloadbusy) return false;
            getmainlist($(this));
        })

        btnsubmit.click(function(){
            if( btnsubmit.hasClass("btnlock") ) return false;
            SubGdForm();
        });

        $('#auto_binds').autocomplete({
            serviceUrl: action,
            params: {"act":"lenov","lab":function(){return lenovlab} },
            deferRequestBy: 500,
            minChars: 0,
            showNoSuggestionNotice: true,
            noSuggestionNotice: '没有匹配结果',
            onSelect: function (suggestion) {
                var ip = suggestion.value;
                var ipm = ip.match(/\d+\.\d+\.\d+\.\d+/g);
                if(ipm) ip = ipm[0];
                ajaxywmessage(ip, $(gdform.lxid).val());
            }
        });

        $("#auto_binds").blur(function(){
            ajaxywmessage( this.value, $(gdform.lxid).val());
        })

        function ajaxywmessage(bind, lxid){
            httpjson(action,"act=ywmessage&bind=" + bind + "&lxid=" + lxid,function(J){
                if(J.text) message_dv.html( J.text );
                $('.tip').tipsy();
            })
        }

        gdform_dv.hide();

        var um = UM.getEditor('Content');



        initattrimg();

        function GetRequest(a1) {
            var url = location.search//获取url中"?"符后的字串
            var the=''
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                var  strs = str.split("&");
                for(var i = 0; i < strs.length; i ++) {
                    if(strs[i].split("=")[0]==a1){
                        the=(strs[i].split("=")[1])
                    }
                }
            }
            return the
        }
        function setload(){
            var domain=GetRequest("domain");
            if(domain){

                $(".option_icon3 ").click();
                setTimeout(function(){
                    var obj=$('.gd_item').eq(2).children('.out_option').children(".s_option");
                    obj.children('.gd_iclass').children("a").removeClass('tab_active').eq(3).addClass("tab_active");
                    obj.children('.gd_ilists').children(".s_radio").hide().eq(3).show();
                    obj.children('.gd_ilists').children(".s_radio").eq(3).find("input[name='lx_103']").click();
                    $(".useless").click();
                    $("input[name='binds']").val(domain);
                    $("input[name='subject']").val('域名赎回问题');
                },500)
            }
            else{
                return false
            }
        }


    </script>

</div>

</@base.html>