<#import "/base/base.ftl" as base>
<#import "/base/dict.ftl" as dict>
<input id="veryCode" name="veryCode" type="text" />
<img id="imgObj" alt="" src="code.html" />
<a href="#" onclick="changeImg()">换一张</a>
</body>
<script type="text/javascript">
    function changeImg() {
        var imgSrc = $("#imgObj");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    }
    //时间戳
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        url = url.substring(0, 17);
        if ((url.indexOf("&") >= 0)) {
            url = url + "×tamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }
</script>

</@base.html>