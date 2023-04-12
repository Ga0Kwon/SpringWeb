console.log("member js 실행");

function onSignup(){//회원가입
    console.log("onSignup");

    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value,
        mname : document.querySelector(".mname").value,
        mphone : document.querySelector(".mphone").value
    }

    $.ajax({
        url : "/member/info",
        method : "post",
        data : JSON.stringify(info), // JSON.stringify(info) :JSON형식으로 받아야한다 (RequestBody)
        contentType : "application/json",
        success : function(data){
            console.log(data);
        }
    })
}

function onLogin(){ //로그인
    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value,
    }

    $.ajax({
            url : "/member/login",
            method : "post",
            data : JSON.stringify(info),
            contentType : "application/json",
            success : function(data){
                if(data == true){
                    alert('로그인 성공')
                }
            }
      })

}