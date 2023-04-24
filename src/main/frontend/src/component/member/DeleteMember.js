import React from 'react';
import axios from 'axios';

export default function DeleteMember(props){

    //실질적인 삭제
    const accountDelete = () => {
       let mpassword = document.querySelector(".mpassword").value;
       let info = JSON.parse(sessionStorage.getItem('login_token'));

        axios.delete('http://localhost:8080/member/info', {params : {mpassword : mpassword, memail : info.memail }})
        .then(r => {
            if(r.data == true){
                alert('계정 삭제 성공되었습니다.');
                window.location.href = '/login';
            }
        })
    }

    return(<>
         비밀번호 입력 : <input type="text" name = "mpassword" className = "mpassword"/>
         <button type = "button" onClick = {accountDelete}>계정 삭제</button><br/>
    </>)


}