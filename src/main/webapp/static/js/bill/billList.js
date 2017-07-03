var website = null;
var keywords = null;
var searchName = null;
var searchUserName = null;
var searchState = 2;
var searchState2 = null;
var searchStandard = null;
var standardDays = null;
var createTime = null;
var groupId = null;

var length;
$(document).ready(function () {
    $(function () {
        if ($("#way").val() == 1) {
            $("#applyStopBill").show();
        }
        else {
            $("#applyStopBill").hide();
        }
    })

    $(".close").click(function () {

        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
        $(".changepriceDiv").slideUp();
        $(".billExamineDiv").slideUp();
        $(".billChangeDiv").slideUp();
        $(".samepriceDiv").slideUp();
        $(".billChangeToKeHuDiv").slideUp();

    })
    $(".cancel").click(function () {

        $(".modal-backdrop").hide();
        $("#billCostDetail").hide();
        $(".changepriceDiv").slideUp();
        $(".billExamineDiv").slideUp();
        $(".billChangeDiv").slideUp();
        $(".samepriceDiv").slideUp();
        $(".billChangeToKeHuDiv").slideUp();

    })
    //显示搜索内容
    $(".search").click(function () {
        if ($(".Navs2").css("display") == "block") {
            $(".Navs2").slideUp();

        }
        else {
            $(".Navs2").slideDown();

        }
    })
    //客户导入订单显示
    $("#addBillByKehu").click(function () {
        $(".modal-backdrop").show();
        $(".samepriceDiv").slideDown();
    })
    //客户导入订单确认
    $(".addBillByKehuCmt").click(function () {
        var search = $("#searchengineid option:selected").text();
        var keyword = $.trim($("#keyword").val());
        var url = $.trim($("#url").val());
        var index;
        if (search == "" || keyword == "" || url == "" || search == "--请选择--") {

            layer.alert('请将信息填写完整', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        }
        else {

            var keyword_arr = $.trim(keyword).split('\n');
            var url_arr = $.trim(url).split('\n');
            console.info(keyword_arr.length);
            console.info(url_arr.length);
            console.info(keyword_arr.length > 1 && keyword_arr.length != url_arr.length);
            if (keyword_arr.length >= 1 && keyword_arr.length != url_arr.length) {
                layer.alert('网址行数为1或者与关键词一一对应', {
                    skin: 'layui-layer-molv' //样式类名  自定义样式
                    , anim: 6 //动画类型
                    , icon: 4   // icon
                });
                return;
            }
            else {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/list/addBillByKehuCmt",
                    dataType: 'json',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data: {
                        search: search,
                        keyword: keyword,
                        url: url,
                    },
                    beforeSend: function () {
                        index = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        $(".pload").hide();
                        if (result.code == 200) {

                            if (result.message == "") {
                                layer.alert('导入成功', {
                                    skin: 'layui-layer-molv' //样式类名  自定义样式
                                    , anim: 1 //动画类型
                                    , icon: 4   // icon
                                });
                                $(".modal-backdrop").hide();
                                $(".samepriceDiv").slideUp();
                                $('#myTable').bootstrapTable('refresh');
                                $("#keyword").val("");
                                $("#url").val("");
                                $("#searchengineid").val("0");


                            }
                            else {
                                layer.alert(result.message, {
                                    skin: 'layui-layer-molv' //样式类名  自定义样式
                                    , anim: 1 //动画类型
                                    , icon: 4   // icon
                                });

                            }
                        }
                        else {

                            layer.alert('系统繁忙，请稍后再试！', {
                                skin: 'layui-layer-molv' //样式类名  自定义样式
                                , anim: 1 //动画类型
                                , icon: 4   // icon
                            });

                        }
                        layer.close(index);
                    }

                })
            }
        }
    })


    //今日达标
    $("#searchState").change(function () {
        var state = $("#searchState option:selected").val();
        if (state == 1) {
            searchState = 2;
        }
        else if (state == 2) {
            searchState = 2;
        }
        else {
            searchState = 3;
            searchState2 = null;
        }
    })
    //复选框
    $("#btn_update").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            $(".modal-backdrop").show();
            $(".changepriceDiv").slideDown();

        }
    })
    //搜索按钮
    $("#searchButton").click(function () {
        if ($("#website").val() != "")//网址
        {
            website = $.trim($("#website").val());
        }
        else {
            website = null
        }
        if ($("#keywords").val() != "")//关键词
        {
            keywords = $.trim($("#keywords").val())
        }
        else {
            keywords = null;
        }

        if ($("#searchName option:selected").text() != "--请选择--") {
            searchName = $("#searchName option:selected").text();
        }
        else {
            searchName = null;
        }
        if ($("#searchUserName").val() != "--请选择--") {
            searchUserName = $("#searchUserName").val();
        }
        else {
            searchUserName = null;
        }
        if ($("#searchStandard option:selected").val() != "--请选择--" && $("#searchStandard option:selected").val() != "0") {
            searchStandard = $("#searchStandard").val();
        }
        else {
            searchStandard = null;
        }

        if ($("#standardDays").val() != "")//关键词
        {
            standardDays = $.trim($("#standardDays").val())
        }
        else {
            standardDays = null;
        }
        if ($("#createTime").val() != "")//关键词
        {
            createTime = $.trim($("#createTime").val())
        }
        else {
            createTime = null;
        }

        if ($("#selectGroupId").val() != "0") {
            groupId = $("#selectGroupId").val();
        }
        else {
            groupId = null;
        }
        $('#myTable').bootstrapTable('refresh');
    });

    $("#continue").click(function () {
        $("#continue").css({"background": "#eee"});
        $("#stop").css({"background": "#fff"});
        $("#all").css({"background": "#fff"});
        $("#applyStopBill").show();
        searchState = 2;
        searchState2 = null;
        $('#myTable').bootstrapTable('refresh');
    })
    $("#stop").click(function () {
        $("#stop").css({"background": "#eee"});
        $("#continue").css({"background": "#fff"});
        $("#all").css({"background": "#fff"});
        $("#applyStopBill").hide();
        searchState = 3;
        searchState2 = null;
        $('#myTable').bootstrapTable('refresh');
    })
    $("#all").click(function () {
        $("#stop").css({"background": "#fff"});
        $("#continue").css({"background": "#fff"});
        $("#all").css({"background": "#eee"});
        $("#applyStopBill").hide();
        searchState = 2;
        searchState2 = 3;
        $('#myTable').bootstrapTable('refresh');

    })
    //优化停止
    $("#optimizationStop").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var index;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            if (confirm("是否停止优化?")) {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/billList/optimizationStop",
                    data: {selectContent: selectContent, length: len},
                    beforeSend: function () {
                        index = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            })
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            })
                        }
                        layer.close(index);
                    }

                })
            }
        }
    })
    //调价
    $("#updatePrice").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            $(".billExamineDiv").slideDown();
            $(".modal-backdrop").show();

        }

    })
    //申请优化
    $("#applyToOptimization").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        }
        else {
            var str = "";
            //判断是否为合作停的订单
            for (var i = 0; i < selectContent.length; i++) {
                if (selectContent[i]["state"] == 2) {
                    str += selectContent[i]["keywords"] + ",  ";
                }
            }
            if (str != "") {
                str += "  订单正在优化中!"
                layer.alert(str, {
                    skin: 'layui-layer-molv' //样式类名
                    , closeBtn: 0
                });
            }
            else {
                layer.confirm('是否申请优化？', {
                        btn: ['确定', '取消']
                    }, function () {
                        $.ajax({
                            type: "post",
                            url: CTX + "/order/applyToOptimization",
                            data: {selectContent: selectContent, length: len},
                            success: function (result) {
                                if (result.code == 200) {
                                    $('#myTable').bootstrapTable('refresh');
                                }
                                layer.alert(result.message, {
                                    skin: 'layui-layer-molv' //样式类名
                                    , closeBtn: 0
                                })
                            }
                        })
                    }
                )

            }


        }
    })
    //调价确认
    $(".updatePricecmt").click(function () {
        var rankend = parseInt(jQuery("input[name='updaterankend']").val());
        var price = parseFloat(jQuery("input[name='updateprice']").val());
        var rankend1 = parseInt(jQuery("input[name='updaterankend1']").val());
        var price1 = parseFloat(jQuery("input[name='updateprice1']").val());
        var rankend2 = parseInt(jQuery("input[name='updaterankend2']").val());
        var price2 = parseFloat(jQuery("input[name='updateprice2']").val());
        var rankend3 = parseInt(jQuery("input[name='updaterankend3']").val());
        var price3 = parseFloat(jQuery("input[name='updateprice3']").val());
        if (!isNaN(rankend) && !isNaN(rankend1) && !isNaN(rankend2) && !isNaN(rankend3)
            && !isNaN(price) && !isNaN(price1) && !isNaN(price2) && !isNaN(price3)
            && rankend > 0 && rankend1 > rankend && rankend2 > rankend1 && rankend3 > rankend2 && 51 > rankend3
            && 1000 > price && price > price1 && price1 > price2 && price2 > price3 && price3 > 0
            ||
            !isNaN(rankend) && !isNaN(rankend1) && !isNaN(rankend2) && isNaN(rankend3)
            && !isNaN(price) && !isNaN(price1) && !isNaN(price2) && isNaN(price3)
            && rankend > 0 && rankend1 > rankend && rankend2 > rankend1 && 51 > rankend2
            && 1000 > price && price > price1 && price1 > price2 && price2 > 0
            ||
            !isNaN(rankend) && !isNaN(rankend1) && isNaN(rankend2) && isNaN(rankend3)
            && !isNaN(price) && !isNaN(price1) && isNaN(price2) && isNaN(price3)
            && rankend > 0 && rankend1 > rankend && 51 > rankend1
            && 1000 > price && price > price1 && price1 > 0
            ||
            !isNaN(rankend) && isNaN(rankend1) && isNaN(rankend2) && isNaN(rankend3)
            && !isNaN(price) && isNaN(price1) && isNaN(price2) && isNaN(price3)
            && rankend > 0 && 51 > rankend
            && 1000 > price && price > 0
        ) {

            var rankendA = parseInt(jQuery("input[name='updaterankend']").val());
            var priceA = parseFloat(jQuery("input[name='updateprice']").val());
            var rankend1A = parseInt(jQuery("input[name='updaterankend1']").val());
            var price1A = parseFloat(jQuery("input[name='updateprice1']").val());
            var rankend2A = parseInt(jQuery("input[name='updaterankend2']").val());
            var price2A = parseFloat(jQuery("input[name='updateprice2']").val());
            var rankend3A = parseInt(jQuery("input[name='updaterankend3']").val());
            var price3A = parseFloat(jQuery("input[name='updateprice3']").val());
            var selectContent = $('#myTable').bootstrapTable('getSelections');
            var len = selectContent.length;
            $.ajax({
                type: 'post',
                url: CTX + "/order/billList/updatePrice",
                dataType: 'json',
                data: {
                    rankend: rankendA,
                    price: priceA,
                    rankend1: rankend1A,
                    price1: price1A,
                    rankend2: rankend2A,
                    price2: price2A,
                    rankend3: rankend3A,
                    price3: price3A,
                    selectContent: selectContent,
                    checkboxLength: len
                },
                beforeSend: function () {
                    $(".pload").show();
                    $('.updatePricecmt').attr('disabled', "true");
                },
                success: function (result) {
                    if (result.code == 200) {
                        alert(result.message);
                        $(".billExamineDiv").slideUp();
                        $(".modal-backdrop").hide();
                        $(".pload").hide();
                        $('#myTable').bootstrapTable('refresh');
                        jQuery("input[name='updaterankend']").val("");
                        jQuery("input[name='updateprice']").val("");
                        jQuery("input[name='updaterankend1']").val("");
                        jQuery("input[name='updateprice1']").val("");
                        jQuery("input[name='updaterankend2']").val("");
                        jQuery("input[name='updateprice2']").val("");
                        jQuery("input[name='updaterankend3']").val("");
                        jQuery("input[name='updateprice3']").val("");
                        $('.updatePricecmt').removeAttr("disabled");
                    }
                    else {
                        $(".pload").hide();
                        alert(result.message);
                        jQuery("input[name='updaterankend']").val("");
                        jQuery("input[name='updateprice']").val("");
                        jQuery("input[name='updaterankend1']").val("");
                        jQuery("input[name='updateprice1']").val("");
                        jQuery("input[name='updaterankend2']").val("");
                        jQuery("input[name='updateprice2']").val("");
                        jQuery("input[name='updaterankend3']").val("");
                        jQuery("input[name='updateprice3']").val("");
                        $('.updatePricecmt').removeAttr("disabled");
                    }
                }

            })


        }
        else {
            alert("前N名依次增大，并且值介于1-50之间，收费依次减小，并且值大于0小于1000；前N名和收费必须同时提供");

        }

    })
    //删除订单
    $("#billDelete").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var index;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            layer.confirm('是否删除当前订单？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/billList/deleteBill",
                    data: {selectContent: selectContent, length: len},
                    beforeSend: function () {
                        index = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        if (result.code == 200) {

                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            });
                            $('#myTable').bootstrapTable('refresh');

                        }
                        else {
                            layer.alert(result.message, {
                                skin: 'layui-layer-molv' //样式类名
                                , closeBtn: 0
                            });

                        }
                        layer.close(index);
                    }

                })

            });
        }
    })
    //优化启动
    $("#optimizationStart").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            if (confirm("是否启动优化?")) {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/billList/optimizationStart",
                    data: {selectContent: selectContent, length: len},
                    success: function (result) {
                        if (result.code == 200) {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            alert(result.message);
                        }
                    }

                })
            }
        }
    })
    //订单切换
    $("#billChangeClick").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            $.ajax({
                type: 'get',
                url: CTX + '/order/getAllUsers',
                success: function (result) {
                    if (result != null) {
                        var str = "";
                        $.each(result, function (index, item) {
                            str += "<option>" + item.userName + "</option>";
                        });
                        $("#selectlist").empty().append(str);
                    }
                }

            })

            $(".billChangeDiv").slideDown();
            $(".modal-backdrop").show();

        }
    })
    //订单切换确认
    $(".billChangecmt").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var caozuoyuan1 = $("#selectlist  option:selected").val();

        $.ajax({
            type: "post",
            url: CTX + "/order/billChangeCmt",
            data: {selectContent: selectContent, length: len, caozuoyuan1: caozuoyuan1},
            success: function (result) {
                if (result.code == 200) {
                    alert(result.message);
                    $('#myTable').bootstrapTable('refresh');
                    $(".billChangeDiv").slideUp();
                    $(".modal-backdrop").hide();
                }
                else {
                    alert(result.message);
                }
            }

        })
    })
    //申请停单
    $("#applyStopBill").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {
            if (confirm("是否申请停单?")) {
                $.ajax({
                    type: "post",
                    url: CTX + "/order/applyStopBillConfirm",
                    data: {selectContent: selectContent, length: len},
                    success: function (result) {
                        if (result.code == 200) {
                            alert(result.message);
                            $('#myTable').bootstrapTable('refresh');
                        }
                        else {
                            alert(result.message);
                        }
                    }

                })
            }
        }
    })


    //修改订单
    $("#confirmUpdateBill").click(function () {
        if ($("#keywordUpdate").val() != "" && $("#websiteUpdate").val() != "" && $("#billIdInput").val() != "") {
            $.ajax({
                type: 'post',
                url: CTX + '/order/updateBillDetailsYouHua',
                data: {
                    billId: $("#billIdInput").val(),
                    keyword: $("#keywordUpdate").val(),
                    website: $("#websiteUpdate").val()
                },
                success: function (result) {
                    if (result.code == 200) {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名  自定义样式
                            , anim: 4 //动画类型
                            , icon: 1   // icon
                        });
                        $('#myTable').bootstrapTable('refresh');
                    }
                    else {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名  自定义样式
                            , anim: 6 //动画类型
                            , icon: 2   // icon
                        });
                        $("#keyword").val("");
                        $("#websiteNow").val("");
                    }
                }

            });
        }
        else {
            layer.alert('填写信息有误,请核对信息！', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 4 //动画类型
                , icon: 2   // icon
            });
        }
    })

    //创建分组显示
    $("#billCreateGroupClick").click(function () {
        index1 = layer.open({
            type: 1,
            title: '创建分组',
            skin: 'layui-layer-molv',
            shade: 0.6,
            area: ['50%', '90%'],
            content: $('#billCreateGroupDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            end: function (e, u) {
                $('#billCreateGroupDiv').hide();
            }
        });

    })
    //批设分组显示
    $("#billToGroupClick").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var index1;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });

        } else {

            index1 = layer.open({
                type: 1,
                title: '批设分组',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['50%', '90%'],
                content: $('#billToGroupDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function (e, u) {
                    $('#billToGroupDiv').hide();
                }
            });
        }


    })
    //创建分组
    $("#createGroup").click(function () {
        layer.prompt({title: '请输入分组名称，并确认', formType: 3}, function (pass, index) {
            if (pass != '') {
                $.ajax({
                    type: 'post',
                    url: CTX + '/order/createGroup',
                    data: {groupName: pass},
                    beforeSend: function () {
                        index = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    },
                    success: function (result) {
                        if (result.message == "0") {
                            layer.msg("分组创建失败！");
                            $('#groupTable').bootstrapTable('refresh');
                        }
                        else if (result.message == "1") {
                            layer.msg("分组创建成功！");
                            $('#groupTable').bootstrapTable('refresh');
                            $('#billToGroupTable').bootstrapTable('refresh');
                        }
                        else {
                            layer.msg("分组名称已经存在！");
                            $('#groupTable').bootstrapTable('refresh');
                        }

                    }
                })
            }
            layer.close(index);
        })
    })
    //分组提交
    $("#toGroupCmt").click(function () {
        var selectContent = $('#billToGroupTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var selectContentTable = $('#myTable').bootstrapTable('getSelections');
        var lenTable = selectContentTable.length;
        var index1;
        if (selectContent == "") {
            layer.alert('请选择一个分组', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        } else {
            $.ajax({
                type: 'post',
                url: CTX + "/order/billToGroupCmt",
                data: {selectContent: selectContent, selectContentTable: selectContentTable, lenTable: lenTable},
                beforeSend: function () {
                    index1 = layer.load(1, {
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                },
                success: function (result) {
                    layer.alert(result.message, {
                        skin: 'layui-layer-molv' //样式类名
                        , closeBtn: 0
                    });
                    $('#billToGroupTable').bootstrapTable('refresh');
                    layer.close(index1);
                }
            })
        }
    })
    //分配出租订单
    $("#leaseBill").click(function () {
        var selectContent = $('#myTable').bootstrapTable('getSelections');
        var len = selectContent.length;
        var index1;
        if (selectContent == "") {
            layer.alert('请选择一列数据', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                , anim: 6 //动画类型
                , icon: 4   // icon
            });
        }
        else {
            var flag = true;
            var firstWebsite = selectContent[0]["website"];

            $.each(selectContent, function (index, item) {
                if (item["website"] != firstWebsite) {
                    flag = false;
                    return;
                }

            })
            if (!flag) {
                layer.alert('请选择相同的网址', {
                    skin: 'layui-layer-molv' //样式类名  自定义样式
                    , anim: 6 //动画类型
                    , icon: 4   // icon
                });
                return;
            }
            $("#leaseTbody").empty();
            var str = "";
            $.each(selectContent, function (index, item) {
                str += "<tr>" +
                    "<th>" + item['id'] + "</th>" +
                    "<th>" + item['keywords'] + "</th> " +
                    "<th>" + item['website'] + "</th>" +
                    "<th><input type='radio' name='radio" + index + "' value='1'></th>" +
                    "<th><input type='radio' name='radio" + index + "' value='0'></th>" +
                    "</tr>"

            })
            $("#leaseTbody").append(str);

            index1 = layer.open({
                type: 1,
                title: '租站分配',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['50%', '90%'],
                content: $('#leaseBillDiv'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function (e, u) {
                    $('#leaseBillDiv').hide();
                }
            });
        }
    })
    //出租订单分配确认
    $("#leaseBillCmt").click(function () {
        var arr = new Array();
        $("#leaseTbody tr").each(function (index, item) {
            var val = $("input:radio[name='radio" + index + "']:checked").val();
            if (val != null) {
                arr[index] = new Array();
                arr[index][0] = $(this).children().eq(0).text();
                arr[index][1] = val;
                arr[index][2] =  $(this).children().eq(1).text();
                arr[index][3] =  $(this).children().eq(2).text();
            }
            /* alert($(this).children().eq(0).text());*/
        })
      $.ajax({
          type:'post',
          url:CTX+'/order/leaseBill',
          data:{billarr:arr,website: $("#leaseTbody tr").children().eq(2).text()},
          success:function (result) {
              layer.alert(result.message, {
                  skin: 'layui-layer-molv' //样式类名  自定义样式
                  , anim: 2 //动画类型
                  , icon: 4   // icon
              });
          }

      })




    })

})


$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    var oTable1 = new TableInit1();
    oTable1.Init();
    var oTable2 = new TableInit2();
    oTable2.Init();
    var oTable3 = new TableInit3();
    oTable3.Init();
    //2.初始化Button的点击事件
    /*  var oButtonInit = new ButtonInit();
     oButtonInit.Init();*/

});
var TableInit = function () {
    var oTableInit = new Object();
    console.log(oTableInit);
    //初始化Table
    oTableInit.Init = function () {
        $('#myTable').bootstrapTable({
            url: CTX + '/order/getBillDetails',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 50,                       //每页的记录行数（*）
            pageList: [100, 200, 300, 500, 1000],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            showExport: true,  //是否显示导出
            exportDataType: "basic",
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'displayId',
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },
                {
                    field: 'id',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '数据库序号',
                    visible: false
                },
                {
                    field: 'userName',
                    sortable: true,
                    align: 'center',
                    valign: 'middle',
                    title: '客户',

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
                    sortable: true,
                    title: '网址',

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
                    field: 'firstRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '初排',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value > 50) {
                            a = "<span>" + ">50" + "</span>";
                        }
                        else {
                            a = "<span>" + value + "</span>";
                        }
                        return a;
                    }


                },
                {
                    field: 'newRanking',
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '新排',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value <= 3) {
                            a = "<span style='color:#FF0000;font-weight: bold;'>" + value + "</span>";
                        }
                        else if (value > 50) {
                            a = "<span>" + ">50" + "</span>";
                        }
                        else {
                            a = "<span>" + value + "</span>";
                        }
                        return a;
                    }


                },
                {
                    field: 'billPriceList',
                    align: 'center',
                    valign: 'middle',
                    title: '价格1',
                    formatter: function (value, row, index) {
                        var a = "";
                        a = value[0]["price"];
                        return a;
                    }

                },

                {
                    field: 'billPriceList',
                    align: 'center',
                    valign: 'middle',
                    title: '价格2',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value[1] != null) {
                            a = value[1]["price"];
                        }
                        else {
                            a = "-";
                        }

                        return a;
                    }

                },

                {
                    field: 'dayConsumption',
                    align: 'center',
                    valign: 'middle',
                    title: '当天消费',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value == 0) {
                            a = "<span style='color: #4382CF'>-</span>";
                        }
                        else if (value == null) {
                            a = "<span style='color: #4382CF'>-</span>";
                        }
                        else {
                            a = "<span style='color: #4382CF'>¥ " + value + "</span>";
                        }

                        return a;
                    }

                },
                {
                    field: "monthConsumption",
                    align: 'center',
                    valign: 'middle',
                    title: '本月消费',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value == 0) {
                            a = "<span style='color: #4382CF'>-</span>";
                        }
                        else if (value == null) {
                            a = "<span style='color: #4382CF'>-</span>";
                        }
                        else {
                            a = "<span style='color: #4382CF'>¥ " + value + "</span>";
                        }


                        return a;
                    }

                },
                {
                    field: "standardDays",
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '达标天',

                },
                {
                    field: "roleName",
                    align: 'center',
                    valign: 'middle',
                    title: '角色',
                    visible: false

                },
                {
                    field: "state",
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                    title: '合作状态',
                    formatter: function (value, row, index) {
                        var a = "";
                        if (value == 2) {
                            a = "<span style='color:#94b86e;'>优化中</span>";
                        }
                        else {
                            a = "<span>合作停</span>";
                        }
                        return a;
                    }
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var a = "<span style='color:#4382CF;cursor:pointer;' id='details'>详情</span> ";
                        if (row.roleName == 'SUPER_ADMIN' || row.roleName == 'ADMIN' || row.roleName == 'COMMISSIONER') {
                            a += " <span style='color:#4382CF;cursor:pointer;' id='updateBill'>修改</span> ";
                        }


                        $("#length").html(row.length + "条记录");
                        return a;
                    },
                    events: operateEvents

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
            way: $("#way").val(),
            website: website,
            keywords: keywords,
            searchName: searchName,
            searchUserName: searchUserName,
            state: searchState,
            state2: searchState2,
            searchStandard: searchStandard,
            standardDays: standardDays,
            createTime: createTime,
            groupId: groupId
        };

        return temp;
    }
    window.operateEvents = {
        'click #details': function (e, value, row, index) {
            $("#detailTable").empty();
            $("#billCostDetail").show();
            $(".modal-backdrop").show();
            var str = "";
            for (var i = 0; i < row.billPriceList.length; i++) {
                str += "<tr><td>前<span class='red'>" + row.billPriceList[i]['billRankingStandard'] + "</span>名</td><td>￥" + row.billPriceList[i]['price'] + "</td></tr>";
            }
            $("#detailTable").append(str);
            $("input[name='Bid']").val(row.id);

            $('#pricetable').bootstrapTable('refresh');
        },
        'click #updateBill': function (e, value, row, index) {
            $("#keywordUpdate").val(row.keywords);
            $("#websiteUpdate").val(row.website);
            $("#billIdInput").val(row.id);
            index1 = layer.open({
                type: 1,
                title: '修改订单',
                skin: 'layui-layer-molv',
                shade: 0.6,
                area: ['30%', '40%'],
                content: $('#offerSetUp'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                end: function (e, u) {
                    $('#offerSetUp').hide();
                }
            });

        }
    }


    return oTableInit;
    /*  e.preventDefault();
     var url = this.href;
     if (url != null && url != 'javascript:;') {
     $.get(url, function (data) {
     $('.page-content').html(data);
     });
     }*/
};

var TableInit1 = function () {
    var oTableInit1 = new Object();
    //初始化Table
    oTableInit1.Init = function () {
        $('#pricetable').bootstrapTable({
            url: CTX + '/order/getPriceDetails',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 20,                       //每页的记录行数（*）
            pageList: [20, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit1.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 330,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    title: '序号',

                },

                {
                    field: 'ranking',
                    align: 'center',
                    valign: 'middle',
                    title: '名次',

                },
                {
                    field: 'costAmount',
                    align: 'center',
                    valign: 'middle',
                    title: '金额',

                }, {
                    field: 'costDate',
                    align: 'center',
                    valign: 'middle',
                    title: '消费日期',

                },
            ],

        });
    };

    //得到查询的参数
    oTableInit1.queryParams = function (params) {
        var temp1 = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            way: $("#way").val(),//上下游
            billId: $("input[name='Bid']").val()

        };
        return temp1;
    }

    return oTableInit1;
};
var TableInit2 = function () {
    var oTableInit2 = new Object();
    //初始化Table
    oTableInit2.Init = function () {
        $('#groupTable').bootstrapTable({
            url: CTX + '/order/getBillGroupTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [30, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit2.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            /* height: 330,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度*/
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            singleSelect: true,
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    title: '数据库编号',
                    visible: false

                },
                {
                    field: 'group_name',
                    align: 'center',
                    valign: 'middle',
                    title: '分组名称',

                },
                {
                    field: 'task_count',
                    align: 'center',
                    valign: 'middle',
                    title: '任务数',

                },
                {
                    field: 'create_time',
                    align: 'center',
                    valign: 'middle',
                    title: '增加时间',
                    formatter: function (value, row, index) {
                        var time = new Date(value);
                        var a = "<span style='color:#4382CF;cursor:pointer;' id='deleteGroup'>" + time.toLocaleDateString() + "</span> ";

                        return a;
                    },

                }
                , {
                    field: 'costDate',
                    align: 'center',
                    valign: 'middle',
                    title: '操作',
                    formatter: function (value, row, index) {
                        var a = "<span style='color:#4382CF;cursor:pointer;' id='deleteGroup'>删除</span> ";

                        return a;
                    },
                    events: operateEvents

                },
            ],

        });
    };

    //得到查询的参数
    oTableInit2.queryParams = function (params) {
        var temp2 = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            sortOrder: params.sortOrder,
            sortName: params.sortName,
            way: $("#way").val(),
            website: website,
            keywords: keywords,
            searchName: searchName,
            searchUserName: searchUserName,
            state: searchState,
            state2: searchState2,
            searchStandard: searchStandard,
            standardDays: standardDays,
            createTime: createTime
        };
        return temp2;
    }
    window.operateEvents = {
        'click #deleteGroup': function (e, value, row, index) {
            layer.confirm('是否删除当前分组？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    type: 'post',
                    url: CTX + '/order/deleteGroup',
                    data: {groupId: row.id},
                    success: function (result) {
                        layer.alert(result.message, {
                            skin: 'layui-layer-molv' //样式类名  自定义样式
                            , anim: 4 //动画类型
                            , icon: 2   // icon
                        });
                        $('#groupTable').bootstrapTable('refresh');
                        $('#billToGroupTable').bootstrapTable('refresh');
                    }

                })
            })
        },

    }
    return oTableInit2;
};
var TableInit3 = function () {
    var oTableInit3 = new Object();
    //初始化Table
    oTableInit3.Init = function () {
        $('#billToGroupTable').bootstrapTable({
            url: CTX + '/order/getBillGroupTable',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，
            pagination: true,                   //是否显示分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [30, 50, 100],        //可供选择的每页的行数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            queryParams: oTableInit3.queryParams,//传递参数（*）
            queryParamsType: "",
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            /* height: 330,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度*/
            uniqueId: "Id",                     //每一行的唯一标识，一般为主键列
            singleSelect: true,
            rowStyle: function (row, index) {
                //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
                var strclass = "";
                if ((row.id) % 2 == 0) {
                    strclass = '';
                }
                else {
                    strclass = 'active';
                }
                return {classes: strclass}
            },
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    align: 'center',
                    valign: 'middle',
                    title: '数据库编号',
                    visible: false

                },
                {
                    field: 'group_name',
                    align: 'center',
                    valign: 'middle',
                    title: '分组名称',

                },
                {
                    field: 'task_count',
                    align: 'center',
                    valign: 'middle',
                    title: '任务数',

                },
            ],

        });
    };

    //得到查询的参数
    oTableInit3.queryParams = function (params) {
        var temp3 = {
            limit: params.pageSize,   //页面大小
            offset: params.pageNumber,  //页码
            sortOrder: params.sortOrder,
            sortName: params.sortName,
        };
        return temp3;
    }
    return oTableInit3;
};


$(function () {
    $("#queren").click(function () {

        $('#myTable').bootstrapTable('refresh');
    });

});
$(function () {
    $("#queren").click(function () {

        $('#pricetable').bootstrapTable('refresh');
    });
});
$(function () {
    //绑定滚动条事件
    //绑定滚动条事件
    $(window).bind("scroll", function () {
        var sTop = $(window).scrollTop();
        var sTop = parseInt(sTop);
        if (sTop >= 130) {
            if (!$(".go-top").is(":visible")) {
                try {
                    $(".go-top").slideDown();
                } catch (e) {
                    $(".go-top").show();
                }
            }
        }
        else {
            if ($(".go-top").is(":visible")) {
                try {
                    $(".go-top").slideUp();
                } catch (e) {
                    $(".go-top").hide();
                }
            }
        }
    });
})






