/**
 * 提交回复
 */
function post() {
    var questionId = $('#question_id').val();
    var content = $('#comment_content').val();
    comment2Target(questionId, 1, content);
}

/**
 * 封装的post方法
 * @param targetId
 * @param type
 * @param content
 */
function comment2Target(targetId, type, content) {
    if (!content) {
        alert("回复不能为空")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                //没有登录
                if (response.code == 2003) {
                    //confirm是什么?
                    var isAccepted = confirm(response.message);
                    if (isAccepted == true) {
                        window.open("/login");
                        window.localStorage.setItem("closable", true);
                    }
                }
                else{
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

/**
 * 二级评论
 * @param e
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $('#input-' + commentId).val();
    comment2Target(commentId, 2, content);
}

/**
 * 展开二级评论  生成列表
 * */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $('#comment-' + id);
    // 获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        //如果子元素大于1才重新获取
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            // $.getJSON("/comment/" + id, function (data) {
            $.getJSON("/mqcomment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    //左
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded img-icon",
                        "src": (comment.user.icon==null || comment.user.icon=='')? '/images/default.png' : comment.user.icon
                    }));
                    //body
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body my-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreated).format("YYYY-MM-DD")
                    })));

                    //mida窗体
                    var mediaElement = $("<div/>", {
                        "class": "media comment-list"
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    //
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                    });
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });

                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $('#select-tag').fadeIn();
}

function hideSelectTag() {
    $('#select-tag').fadeOut();
}
function fadeInTag(){
    $("#select-tag").fadeIn();
}
//点击标签执行
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    //进来就加逗号
    var previous = $('#tag').val();
    //匹配不中 javascript,        java,
    if ((previous + ',').indexOf(value + ',') == -1) {
        if (previous) {
            $('#tag').val(previous + ',' + value);
        } else {
            $('#tag').val(value);
        }
    }
}



/**
 * 发布或评论点赞 有不同的type
 * @param e
 */
function like(e,type) {
    var likeId = e.getAttribute("data-like");
    likeTarget(likeId, type);
}

//点赞的post
function likeTarget(targetId, type) {
    $.ajax({
        type: "POST",
        url: "/like",
        contentType: "application/json",   //发送过去的格式
        data: JSON.stringify({
            "parentId": targetId,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                // 之后试试做局部刷新
                // var CommentFlashLikes = $('#CommentFlashLikes');
                // CommentFlashLikes.reload();
            } else {
                //没有登录
                if (response.code == 2003) {
                    //confirm是什么?
                    var isAccepted = confirm(response.message);
                    if (isAccepted == true) {
                        window.open("/login");
                        window.localStorage.setItem("closable", true);
                    }
                }
                else{
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"  //返回回来的格式
    });
}

// 点击展开
//之后再做吧
