console.log("index.js 실행");

onMember();

function onMember(){
    $.ajax({
        url : "/member/info",
        method : "get",
        success : (r) =>{
            console.log(r);
            if(r.mname != undefined){
              document.querySelector('.loginInfo').innerHTML = `${r.mname}님`;
              document.querySelector('.logSign').innerHTML = `<a href ="/member/logout">로그아웃</a><a href ="/member/delete">회원탈퇴</a><a href ="/member/update">회원 수정</a>`

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
