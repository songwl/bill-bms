<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>富文本编辑器 - layui</title>

    <link rel="stylesheet" href="../src/css/layui.css">

    <style>
        body{padding: 10px;}
    </style>
</head>
<body>

<div style="width: 800px;">
    <form class="layui-form">
        <div class="layui-form-item">
      <textarea name="demo" id="demo" class="layui-hide">
    <p><span>一个范围具有两个边界点，即一个开始点和一个结束点。每个边界点由一个节点和那个节点的偏移量指定。该节点通常是&nbsp;</span><a href="http://www.w3school.com.cn/xmldom/dom_element.asp" title="XML DOM - Element 对象">Element 节点</a><span>、</span><a href="http://www.w3school.com.cn/xmldom/dom_document.asp" title="XML DOM - Document 对象">Document 节点</a><span>或&nbsp;</span><a href="http://www.w3school.com.cn/xmldom/dom_text.asp" title="XML DOM - Text 对象">Text 节点</a><span>。对于 Element 节点和 Document 节点，偏移量指该节点的子节点。偏移量为 0，说明边界点位于该节点的第一个子节点之前。偏移量为 1，说明边界点位于该节点的第一个子节点之后，第二个子节点之前。但如果边界节点是 Text 节点，偏移量则指的是文本中两个字符之间的位置。</span></p>
      </textarea>
        </div>
        <button class="layui-btn">提交</button>
        <a class="layui-btn" id="getChoose">获取选中内容</a>
    </form>
</div>


<script src="../src/layui.js"></script>
<script>
    layui.use('layedit', function(){
        var layedit = layui.layedit;

        var index = layedit.build('demo', {
            //hideTool: ['image']
            uploadImage: {
                url: '/upload/test/'
                ,type: 'post'
            }
            //,tool: []
            //,height: 100
        });

        getChoose.onclick = function(){
            alert(layedit.getSelection(index));
        };

    });
</script>
</body>
</html>