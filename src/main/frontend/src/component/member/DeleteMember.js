import React, {useState} from 'react';
import axios from 'axios';

export default function DeleteMember(props){
    //실질적인 삭제
    const accountDelete = () => {
       let mpassword = document.querySelector(".mpassword").value;

        axios.delete('http://localhost:8080/member/info', {params : {"mpassword" : mpassword}})
        .then(r => {
            if(r.data == true){
                alert('계정 삭제 성공되었습니다.');
                loginOut();
            }
        })
    }

    //삭제전 비밀번호 확인
    const checkPwd = () => {
        document.querySelector(".etcDiv").innerHTML = `  비밀번호 입력 : <input type="text" name = "mpassword" className = "mpassword"/>
                <button type = "button" onClick = {accountDelete}>계정 삭제</button><br/>`;
    }
}