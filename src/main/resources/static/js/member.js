console.log("member js 실행");

onMember();

function checkId(){
    $.ajax({
        url : "/member/find",
        method : "get",
        data : {"memail" : document.querySelector(".memail").value},
        success : (r) =>{
            if(r == false){
                document.querySelector(".checkIdtxt").innerHTML = `중복된 아이디입니다.`;
            }else{
                document.querySelector(".checkIdtxt").innerHTML = `O`;
            }
        }
    })
}
//로그인 정보 요청
function onMember(){
    $.ajax({
        url : "/member/info",
        method : "get",
        success : (r) =>{
            console.log(r);
            if(r.mname != undefined){
              document.querySelector('.loginInfo').innerHTML = `${r.mname}님`;
              document.querySelector('.logSign').innerHTML = `
              <a href ="/member/logout">로그아웃</a>
              <a onClick = "checkPwd()">회원탈퇴</a>
              <a href ="/member/update">회원 수정</a>`

            }
        }
    })
}


/*
function logout(){
    $.ajax({
        url : "/member/logout",
        method : "get",
        success : (r) =>{
            if(r.mname == undefined){
                location.href = "/";
                document.querySelector(".logSign").innerHTML = `<a href ="/member/login">로그인</a><a href ="/member/findId">아이디찾기</a><a href ="/member/findPw">비밀번호찾기</a>`
            }
        }
    })
}*/

function onSignup(){//회원가입
    console.log("onSignup");
    let checkTxt = document.querySelector(".checkIdtxt").innerHTML;

    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value,
        mname : document.querySelector(".mname").value,
        mphone : document.querySelector(".mphone").value
    }

    if(checkTxt == 'O'){
        $.ajax({
                url : "/member/info",
                method : "post",
                data : JSON.stringify(info), // JSON.stringify(info) :JSON형식으로 받아야한다 (RequestBody)
                contentType : "application/json",
                success : function(data){
                    console.log(data);
                    if(data == true){
                        alert('회원가입 성공')
                    }
                }
            })
    }else{
        alert("중복된 아이디입니다. 다시 입력해주세요")
        document.querySelector(".memail").value = ``;
    }
}
//로그인
function onLogin(){
    let loginform = document.querySelectorAll(".loginForm")[0];

    let loginFormData = new FormData(loginform);

    $.ajax({ //폼전송[시큐리티 formLogin() 사용하기 때문에]
        url : "/member/login",
        method : "post",
        data : loginFormData,
        contentType : false, //폼전송할때 multipart
        processData : false, //폼전송할때 multipart
        success : function(r) {
            console.log(r);
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
        mphone : document.querySelector(".mphone").value,
        mname : document.querySelector(".mname").value,
    }

    $.ajax({
        url : "/member/info",
        method : "put",
        data : JSON.stringify(info),
        contentType : "application/json",
        success : function(data){
            if(data == true){
                location.href = "/"
            }
        }
    })
}

function checkPwd(){
    document.querySelector(".etcDiv").innerHTML = `  비밀번호 입력 : <input type="text" name = "mpassword" class = "mpassword"/><button type = "button" onclick="onDelete()">계정 삭제</button><br/>`;
}

function onDelete(){
   let mpassword = document.querySelector(".mpassword").value;

   $.ajax({
        url : "/member/info",
        method : "delete",
        data : {"mpassword" : mpassword},
        success : function(data){
                if(data == true){
                       alert('계정 삭제 성공되었습니다.');
                       location.href = "/member/logout"
                }
        }
   })
}

function onFindId(){ // 아이디 찾기
    let info = {
        "mname" : document.querySelector(".mname").value,
        "mphone" : document.querySelector(".mphone").value
    }

    $.ajax({
            url : "/member/find",
            method : "post",
            data : JSON.stringify(info),
            contentType : "application/json",
            success : function(data){
                  if(data != null){
                        document.querySelector(".returnId").innerHTML = `회원님의 아이디 : ${data}`;
                  }else{
                       alert('해당 정보로 조회할 수 없습니다.')
                       location.href = "/"
                  }
            }
        })
}

function onFindPw(){ // 비밀번호 찾기
let info = {
        "memail" : document.querySelector(".memail").value,
        "mphone" : document.querySelector(".mphone").value
    }

    $.ajax({
            url : "/member/find",
            method : "put",
            data : JSON.stringify(info),
            contentType : "application/json",
            success : function(data){
                  if(data != null){
                        document.querySelector(".returnPw").innerHTML = `회원님의 임시 비밀번호 : ${data}`;
                  }else{
                       alert('해당 정보로 조회할 수 없습니다.')
                       location.href = "/"
                  }
            }
        })
}