
//提交回复
function commentPost() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

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
                        window.open("https://github.com/login/oauth/authorize?client_id=f1dc16ae477b1a50128f&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
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