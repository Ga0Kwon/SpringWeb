import React, {useState} from 'react';
import axios from 'axios';

export default function Header(props){
    //let [login, setLogin] = useState(JSON.parse(localStorage.getItem('login_token')));
    let [login, setLogin] = useState(JSON.parse(sessionStorage.getItem('login_token')));

    const loginOut = () => {
        sessionStorage.setItem('login_token', null);

        //백엔드의 인증세션 지우기
        axios.get('http://localhost:8080/member/logout').then(r => {console.log(r)})

        window.location.href ="/login"
    }

    let loginInfo = JSON.parse(sessionStorage.getItem("login_token"));

    return(<>
        <div>
            <a href = "/" > Home  </a>
            <a href = "/board/list">게시판</a>
            <a href = "/admin/dashboard" > 관리자모드</a>
            { login == null ?
                ( <>
                    <a href = "/member/login" > login</a>
                    <a href = "/member/signup" > signup</a>
                 </>)
                 : (<>
                    <button onClick = {loginOut}>로그아웃</button>
                    <a href = "/member/deleteMember">회원탈퇴</a>
                    </>)}
        </div>
    </>)
}