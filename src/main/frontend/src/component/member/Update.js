import React from 'react';
import axios from 'axios';

export default function Update(props){
    let tokenInfo = JSON.parse(sessionStorage.getItem('login_token'));

    const onUpdate = () => {
        let info = {
            memail : tokenInfo.memail,
            mname : document.querySelector(".mname").value,
            mphone : document.querySelector(".mphone").value
        }
        console.log(info)
        axios.put("/member/info", info)
        .then(r => {
            if(r.data == true){
                alert('회원수정이 완료되었습니다.');
                window.location.href = "/"
            }else{
                alert('회원 수정에 실패하였습니다. 관리자에게 문의해주세요.')
            }
        }).catch(err => {console.log(err)})
    }

    return(
        <>
             <h3>회원 수정 페이지</h3>
              <form>
                전화번호 : <input type="text" name = "mphone" className = "mphone"/><br/>
                이름 : <input type="text" name = "mname" className = "mname"/><br/>
                <button type = "button" onClick={onUpdate}>수정</button>
              </form>
        </>
    )
}