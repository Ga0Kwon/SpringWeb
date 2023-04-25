import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Header(props){
    //let [login, setLogin] = useState(JSON.parse(localStorage.getItem('login_token')));
    let[login, setLogin] = useState(null); //로그인 상태

    const loginOut = () => {
        sessionStorage.setItem('login_token', null);

        //백엔드의 인증세션 지우기
        axios.get('/member/logout').then(r => {console.log(r)})

        window.location.href ="/member/login" //라우터 경로를 쓰는 것
        setLogin(null); //렌더링
    }

    //로그인 상태 호출
    useEffect(() => {
        axios.get("/member/info").then(r => {
            console.log(r);

            if(r.data != ''){ //로그인이 되어있으면 => 서비스에서 null이면 js에서 ''이다.

                //JS 로컬 스토리지에 저장
                sessionStorage.setItem("login_token", JSON.stringify(r.data));
                //상태 변수에 저장 [ 렌더링 하기 위해]
                setLogin(JSON.parse(sessionStorage.getItem("login_token")));
            }else{

            }
        })
    },[])

    //let loginInfo = JSON.parse(sessionStorage.getItem("login_token"));

    return(<>
        <div>
            <a href = "/" > Home  </a>
            <a href = "/board/list">게시판</a>
            <a href = "/admin/dashboard" > 관리자모드</a>
            {login == null ?
                ( <>
                    <a href = "/member/login" > login</a>
                    <a href = "/member/signup" > signup</a>
                 </>)
                 : (<>
                    <div className = "etcDiv"></div>
                    <button onClick = {loginOut}>로그아웃</button>
                    <a href = "/member/deleteMember">회원탈퇴</a>
                    <a href = "/member/update">회원수정</a>
                    </>)}
        </div>
    </>)
}