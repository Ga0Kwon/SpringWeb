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
/*
    // 시큐리티 사용하므로 아래 코드 사용X => 폼 전송 이용
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
                    location.href = "/"
                }
            }
      })

}
*/

function onUpdate(){ //회원 정보 수정
    let info = {
        mpassword : document.querySelector(".mpassword").value,
        mphone : document.querySelector(".mphone").value,
        mname : document.querySelector(".mname").value,
        mrole : document.querySelector(".mrole").value
    }

    $.ajax({
        url : "/member/info",
        method : "put",
        data : JSON.stringify(info),
        success : function(data){
            if(data == true){
                location.href = "/"
            }
        }
    })
}

function onDelete(){
    document.querySelector(".etcDiv").innerHTML = `  비밀번호 입력 : <input type="text" name = "mpassword" class = "mpassword"/><br/>`;
}
