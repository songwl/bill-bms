/**
 * Created by Administrator on 2017/3/16.
 */

var website=null;
var keywords=null;
var searchName=null;
var searchUserName=null;
$("#searchButton").click(function () {

    if($("#website").val()!="")
    {
        website=$("#website").val();
    }
    else
    {
        website=null
    }
    if($("#keywords").val()!="")
    {
        keywords=$("#keywords").val();
    }
    else
    {
        keywords=null;
    }
    if($("#searchName option:selected").text()!="--请选择--")
    {
        searchName=$("#searchName option:selected").text();
    }
    else
    {
        searchName=null;
    }
    if($("#searchUserName option:selected").text()!="--请选择--")
    {
        searchUserName=$("#searchUserName option:selected").text();
    }
    else
    {
        searchUserName=null;
    }


    $('#myTable').bootstrapTable('refresh');
})
//删除订单
$("#deleteBill").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    var len = selectContent.length;
    var index;
    if(selectContent == "") {
        layer.alert('请选择一列数据!', {
            skin: 'layui-layer-molv' //样式类名
            ,closeBtn: 0
        });
    }else{

        layer.confirm('是否删除订单？',{
                btn:['确定','取消']
            },function () {
            $.ajax({
                type:'post',
                url:CTX+'/order/deleteBillPendingAuditView',
                data:{selectContent: selectContent, length: len},
                beforeSend: function () {
                    index  = layer.load(1, {
                        shade: [0.1,'#fff'] //0.1透明度的白色背景
                    });
                },
                success:function (result) {
                    if (result.code == 200) {

                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                        $('#myTable').bootstrapTable('refresh');

                    }
                    else {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });

                    }
                    layer.close(index);
                }

            })
            }
        )



    }
})

//批设分组显示
$("#zhuanDetails").click(function () {
    var index1;
        index1 = layer.open({
            type: 1,
            title: '专员详情',
            skin: 'layui-layer-molv',
            shade: 0.6,
            area: ['70%', '95%'],
            content: $('#zhuanyuanDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            end: function (e, u) {
                $('#zhuanyuanDiv').hide();
            }
        });
})
$.ajax({
    type:'get',
    url:CTX+'/order/getZhuanyuanDetails',
    async: true,
    success:function (result) {
        $("#caozuoyuanTbody").empty();
        var str="";
        console.log(result.data);
        var j=0;
         for(var i=0;i<result.data.length;i++)
         {
             j++;
             str+='<tr> <th>'+j+'</th><th>'+result.data[i]['userName']+'</th>' +
                 '<th>'+result.data[i]['countNoCostByWebsite']+'</th>' +
                 '<th>'+((result.data[i]['billStandardRate']).toFixed(2))*100+'%</th>' +
                 '<th>'+result.data[i]['keywordsCount']+'</th><th>'+result.data[i]['billCount']+'</th>' +
                 '<th>'+result.data[i]['weekCount']+'</th><th>'+result.data[i]['monthCount']+'</th><th>'+result.data[i]['cost']+'</th>' +
                 '<th>'+result.data[i]['allCost']+'</th> </tr>';
         }
          $("#caozuoyuanTbody").append(str);
    }

})


$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
});
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX+'/order/pendingAuditList',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 50,                       //每页的记录行数（*）
            pageList:[100,200,300,500,1000],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id)%2==0){
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return { classes: strclass }
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'displayId',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },{
                    field: 'id',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '数据库序号',
                    visible:false

                },
                {
                    field: 'userName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '客户',

                },
                {
                    field: 'userNameByKehu',
                    align: 'center',
                    valign: 'middle',
                    title: '子级客户',

                },
                {
                    field: 'keywords',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '关键词'
                }, {
                    field: 'website',
                    align: 'center',
                    valign: 'middle',
                    title: '网址',
                    sortable: true,

                }, {
                    field: 'searchName',
                    align: 'center',
                    sortable: true,
                    valign: 'middle',
                    title: '搜索引擎'
                },
                {
                    field: 'createTime',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '增加时间'
                },

                {
                    field: 'updateUserId',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '修改者ID',
                    visible:false
                },
                {
                    field: 'billType',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '属性',
                    formatter:function (value,row,index) {
                        var  a="";
                       if(value==1)
                       {
                           a= "<span style='color:#09C;'>正常</span>";
                       }
                       else if(value==2)
                       {
                           a= "<span style='color:#09C;'>快排</span>";
                       }
                       else if(value==3)
                       {
                           a= "<span style='color:#09C;'>包年</span>";
                       }
                       else if(value==4)
                        {
                            a= "<span style='color:#09C;'>出租</span>";
                        }
                        return a;
                    }
                },

                {
                    field: "state",
                    align: 'center',
                    valign: 'middle',
                    title: '状态',
                    formatter:function (value,row,index) {
                        var  a="<span style='color:#94b86e;'>待审核</span>";
                        $("#length").html(row.length+"条记录");
                        return a;
                    }
                },



            ],

        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            sortOrder: params.sortOrder,
            sortName: params.sortName,
            website:website,
            keywords:keywords,
            searchName:searchName,
            searchUserName:searchUserName,

        };
        return temp;
    }
    window.operateEvents = {
        'click #details': function (e, value, row, index) {
            $("#billCostDetail").show();
            $(".modal-backdrop").show();

        }

    }


    return oTableInit;
};



$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});
function detail( ) {
    var bid=$("input[name='Bid']").val();


}