/**
 * Created by Administrator on 2017/3/17.
 */
$("#billExamine").click(function () {
    var selectContent = $('#myTable').bootstrapTable('getSelections');
    if(selectContent == "") {
        alert('请选择一列数据!');

    }else{
        $(".modal-backdrop").show();
        $(".billExamineDiv").slideDown();

    }
})
$(".close").click(function () {
    $(".billExamineDiv").slideUp();
    $(".modal-backdrop").hide();

})
$(".cancel").click(function () {
    $(".billExamineDiv").slideUp();
    $(".modal-backdrop").hide();
})