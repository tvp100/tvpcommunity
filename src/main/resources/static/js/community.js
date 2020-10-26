
//提交回复
function commentPost() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

//回复的Post功能
function comment2target(targetId,type,content) {
    if (!content){
        alert("输入的内容不能为空~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code==200){
                window.location.reload();
            }else {
                if(response.code==2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("http://47.113.92.160/mySignIn");
                        window.localStorage.setItem("closable","true");
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

//提交二级评论
function childCommentPost(e) {
    var commentId = e.getAttribute("data-id");
    console.log(commentId);
    var content = $("#child_content"+commentId).val();
    comment2target(commentId,2,content);
}

//获取二级评论
function getChildComment(e) {
    var commentId = e.getAttribute("data-id");
    var subCommentContainer = $("#twocomment-"+commentId);
    if ($(subCommentContainer).children().length > 0){

    } else {
        $.getJSON("/comment/" + commentId,function (data) {
            $.each(data.data, function (index, comment) {
                var mediaElement = $("<div/>",{
                    "class": "media"
                });
                var mediaLeftElement = $("<div/>",{
                    "class": "media-left"
                });
                var avatarElement = $("<img/>",{
                    "class": "media-object img-rounded",
                    "src": comment.user.avatarUrl
                });
                var mainElement = $("<div/>",{
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                });
                var mediaBodyElement = $("<div/>",{
                    "class": "media-body",
                    "style": "padding-top: 10px"
                });
                var mediaHeadElement = $("<div/>",{
                    "class": "media-heading"
                }).append(comment.user.name);
                var commentElement = $("<div/>",{
                    html: comment.content
                });
                var menuElement = $("<div/>",{
                    "style": "float: right; color: #999",
                    html: moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                });
                mediaLeftElement.append(avatarElement);
                mediaElement.append(mediaLeftElement);
                mediaBodyElement.append(mediaHeadElement);
                mediaBodyElement.append(commentElement);
                mediaBodyElement.append(menuElement);
                mediaElement.append(mediaBodyElement);
                mainElement.append(mediaElement);
                subCommentContainer.prepend(mainElement);
            });
        });
    }
}

//选择标签
function selectTag(value) {
    var previous = $("#tag").val();
    if(previous.indexOf(value)==-1){
        if (previous) {
            $("#tag").val(previous + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#select-tag").attr("style","display:block;");
}

function hiddenSelectTag() {
    $("#select-tag").attr("style","display:none;");
}

//注册
function sighUp() {
    var accountid = $("#register-accountid")[0].value;
    var password1 = $("#register-password")[0].value;
    var password2 = $("#register-passwords")[0].value;
    var username = $("#register-username")[0].value;
    var sex = $("input[name='sex']:checked").val();
    if (accountid==""){
        alert("账号不能为空");
        return;
    }
    if (username==""){
        alert("昵称不能为空");
        return;
    }
    if (password1==""||password2==""){
        alert("请输入密码");
        return;
    }
    if (password1!=password2){
        alert("两次密码输入不一致");
        return;
    }
    if (sex==null){
        alert("请选择你的性别");
        return;
    }


    $.ajax({
        type: "POST",
        url: "/userSignUp",
        contentType: 'application/json',
        data: JSON.stringify({
            "pwd": password1,
            "accountId": accountid,
            "sex": sex,
            "name" : username
        }),
        success: function (response) {
            //$(window).attr('location','http://localhost:8080/');
            console.log(response);
            if (response.code==300){
                alert("账号已存在");
                return;
            }
            if (response.code==200){
                $(window).attr('location','http://47.113.92.160/');
            }
            if (response.code==2004){
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

//登陆
function sighIn() {
    var accountid = $("#sin-accountid")[0].value;
    var password = $("#sin-password")[0].value;
    if (accountid==""){
        alert("账号不能为空");
        return;
    }
    if (password==""){
        alert("请输入密码");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/userSignIn",
        contentType: 'application/json',
        data: JSON.stringify({
            "pwd": password,
            "accountId": accountid
        }),
        success: function (response) {
            //$(window).attr('location','http://47.113.92.160/');
            console.log(response);
            if (response.code==300){
                alert(response.message);
                return;
            }
            if (response.code==2004){
                alert("账号不存在");
                return;
            }
            if (response.code==200){
                $(window).attr('location','http://47.113.92.160/');
            }
        },
        dataType: "json"
    });
}