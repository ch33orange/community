//获取id并返回 方便调用
function $(id){
    return document.getElementById(id);
}
//获取按钮
var btn = $("btn-submit");
//按钮封闭
//         btn.disabled = true;
var hint1 = document.createElement("p");
hint1.innerHTML = '昵称不能为空!';
hint1.style.color="red";

var hint3 = document.createElement("p");
hint3.innerHTML = '请输入密码!';
hint3.style.color="red";


function checkName() {
    var nameEL = $("rg-user_name");
    var parentunEL = $("name");
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

//密码为空判断
 function checkPwd () {
     var nameEL = $("rg-user_pwd");
     var parentunEL = document.querySelector(".pwd");
    if(!nameEL.value){
        //在输入内容为空的时候
        //如果父节点的子节点数小于2 也就是还没有插入提示子节点 就插入子节点 否则不重复插入
        if (parentunEL.childElementCount < 2) {
            parentunEL.appendChild(hint3);
            //恢复原色
            nameEL.style.border="1px solid #ff5b5b";
        }
    }else{
       //输入内容不为空
       //如果父节点的子节点数大于1 也就是已经插入提示过子节点 就把子节点移除
       //边框变红
       nameEL.style.border="1px solid #aaa";
       if(parentunEL.childElementCount > 1){
            parentunEL.removeChild(parentunEL.lastChild);
        }
    }
}