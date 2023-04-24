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


    return(<>
        <div>
            <a href = "/" > Home  </a>
            <div className = "etcDiv"></div>
            { login == null ?
                ( <>
                    <a href = "/member/login" > login</a>
                    <a href = "/member/signup" > signup</a>
                 </>)
                 : (<>
                    <button onClick = {loginOut}>로그아웃</button>
                    </>)}
        </div>
    </>)
}