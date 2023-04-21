import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css'

export default function Login(props){

    //로그인
    const onLogin = () =>{
        let loginform = document.querySelectorAll(".loginForm")[0];

        let loginFormData = new FormData(loginform);
        /*axios에서 form 전송
            login은 스프링 시큐리티가 처리한다.
        */
        axios.post("http://localhost:8080/member/login", loginFormData)
        .then(r => {
            if(r.data == false){
                alert('동일한 회원정보가 없습니다.')
            }else{
                alert('[로그인 성공] 환영합니다.')
                window.location.href = "/";
            }
        })
    }


    return (<>
            <form className ="loginForm">
                아이디[이메일] : <input type="text" name = "memail" className = "memail"/><br/>
                비밀번호 : <input type="text" name = "mpassword" className = "mpassword"/><br/>
                <button onClick = {onLogin} type = "button">로그인</button>
                <a href = "http://localhost:8080/oauth2/authorization/google">구글로그인</a>
                <a href = "http://localhost:8080/oauth2/authorization/kakao">카카오로그인</a>
                <a href = "http://localhost:8080/oauth2/authorization/naver">네이버로그인</a>
            </form>
    </>)
}