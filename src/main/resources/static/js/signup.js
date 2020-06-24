//获取id并返回 方便调用
function $(id) {
    return document.getElementById(id);
}
//获取按钮
var btn = $("btn-submit");

//默认按钮封闭
        btn.disabled = true;
var hint1 = document.createElement("p");
hint1.innerHTML = '请输入姓名!';
hint1.style.color = "red";

// var hint2 = document.createElement("p");
// hint2.innerHTML = '请输入11位手机号!';
// hint2.style.color = "red";

var hint3 = document.createElement("p");
hint3.innerHTML = '请输入密码!';
hint3.style.color = "red";

var hint4 = document.createElement("p");
hint4.innerHTML = '请输入邮箱!';
hint4.style.color = "red";



//动态修改图片
function reloadCaptcha() {
    var captchImg = $("captchaImg");
    //时间戳来更新
    captchaImg.src = "/captcha?t=" + (new Date()).getTime();
}

//等60秒
var wait = 60;

function time() {
var getCode = $('rg-getCode');
    if (wait == 0) {
         getCode.removeAttribute("disabled");
      getCode.value ="获取验证码";
        wait = 60;
    } else {
        getCode.value = wait + "秒后重新发送";
        getCode.setAttribute("disabled", true);
//        getCode.disabled = true;
        wait--;
        setTimeout(function () {
            time();
        }, 1000);
    }
}

//邮箱验证码
function sendEmail() {
    var toEmail = $('rg-user_email');
    var captcha = $('getCaptcha');
    var data = {
        email: email
    };
    // //60秒限制
    fetch('/sendemail?email=' + toEmail.value+"&"+"captcha="+ captcha.value
        )
        .then(function (res) {
            return res.json();
        })
        .then(function (data) {
            if (data == 0) {
                alert("邮箱格式错误\n请检查邮箱是否正确");
                reloadCaptcha();
            } else if (data == 2) {
                alert("邮箱已被注册！\n请更换邮箱");
                reloadCaptcha();
            } else if (data == 3) {
                alert("邮件发送错误！\n请检查邮箱");
                reloadCaptcha();
            } else if(data == 4){
                 alert('验证码填写错误!\n发送失败');
                 reloadCaptcha();
            }  else if(data == 1) {
                alert("发送成功！");
            }
        });
        time();
}


function checkName() {
    var nameEL = $("rg-user_name");
    var parentunEL = document.querySelector(".name");
    if (!nameEL.value) {
        //在输入内容为空的时候
        //如果父节点的子节点数小于2 也就是还没有插入提示子节点 就插入子节点 否则不重复插入
        if (parentunEL.childElementCount < 2) {
            parentunEL.appendChild(hint1);
            //恢复原色
            nameEL.style.border = "1px solid #ff5b5b";
        }
        //按钮封闭
        btn.disabled = true;
    } else {
        //输入内容不为空
        //如果父节点的子节点数大于1 也就是已经插入提示过子节点 就把子节点移除
        //边框变红
        nameEL.style.border = "1px solid #aaa";
        if (parentunEL.childElementCount > 1) {
            parentunEL.removeChild(parentunEL.lastChild);
        }
        //按钮解封
        btn.disabled = false;
    }
}

// function checkMobile() {
//     var nameEL = $("mobile");
//     var parentunEL = $("phone");
//     if (!nameEL.value) {
//         //在输入内容为空的时候
//         //如果父节点的子节点数小于2 也就是还没有插入提示子节点 就插入子节点 否则不重复插入
//         if (parentunEL.childElementCount < 2) {
//             parentunEL.appendChild(hint2);
//             //恢复原色
//             nameEL.style.border = "1px solid #ff5b5b";
//         }
//         //按钮封闭
//         btn.disabled = true;
//     } else {
//         //输入内容不为空
//         //如果父节点的子节点数大于1 也就是已经插入提示过子节点 就把子节点移除
//         //边框变红
//         nameEL.style.border = "1px solid #aaa";
//         if (parentunEL.childElementCount > 1) {
//             parentunEL.removeChild(parentunEL.lastChild);
//         }
//         //按钮解封
//         btn.disabled = false;
//     }
// }


function checkEmail() {
    var nameEL = $("rg-user_email");
    var parentunEL = $("email");
    if (!nameEL.value) {
        //在输入内容为空的时候
        //如果父节点的子节点数小于2 也就是还没有插入提示子节点 就插入子节点 否则不重复插入
        if (parentunEL.childElementCount < 2) {
            parentunEL.appendChild(hint4);
            //恢复原色
            nameEL.style.border = "1px solid #ff5b5b";
        }
        //按钮封闭
        btn.disabled = true;
    } else {
        //输入内容不为空
        //如果父节点的子节点数大于1 也就是已经插入提示过子节点 就把子节点移除
        //边框变红
        nameEL.style.border = "1px solid #aaa";
        if (parentunEL.childElementCount > 1) {
            parentunEL.removeChild(parentunEL.lastChild);
        }
        //按钮解封
        btn.disabled = false;
    }
}

//密码为空判断
function checkPwd() {
    var nameEL = $("rg-user_pwd");
    var parentunEL = document.querySelector(".pwd");
    if (!nameEL.value) {
        //在输入内容为空的时候
        //如果父节点的子节点数小于2 也就是还没有插入提示子节点 就插入子节点 否则不重复插入
        if (parentunEL.childElementCount < 2) {
            parentunEL.appendChild(hint3);
            //恢复原色
            nameEL.style.border = "1px solid #ff5b5b";
        }
    } else {
        //输入内容不为空
        //如果父节点的子节点数大于1 也就是已经插入提示过子节点 就把子节点移除
        //边框变红
        nameEL.style.border = "1px solid #aaa";
        if (parentunEL.childElementCount > 1) {
            parentunEL.removeChild(parentunEL.lastChild);
        }
    }
}