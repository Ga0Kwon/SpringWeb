import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Signup(props){

        const checkId = () => {
            axios.get("http://localhost:8080/member/find", {params : {memail : document.querySelector(".memail").value}})
            .then(r => {
                if(r.data == false){
                    document.querySelector(".checkIdtxt").innerHTML = `중복된 아이디입니다.`;
                }else{
                    document.querySelector(".checkIdtxt").innerHTML = `O`;
                }
            }).catch(err => {console.log(err)})
        }


        const onSignup = () => {
            console.log("signup 켜지냐")

            let checkTxt = document.querySelector(".checkIdtxt").innerHTML;

            let info = {
                memail : document.querySelector(".memail").value,
                mpassword : document.querySelector(".mpassword").value,
                mname : document.querySelector(".mname").value,
                mphone : document.querySelector(".mphone").value
            }

          if(checkTxt == `O`){
                axios.post("http://localhost:8080/member/info", info)
                .then(r => {
                    if(r.data == true){
                        alert('회원가입이 완료되었습니다.')
                        window.location.href = "/login";
                    }
                }).catch(err => {console.log(err)})
            }else{
                 alert("중복된 아이디입니다. 다시 입력해주세요")
                 document.querySelector(".memail").value = ``;
                 return false;
             }
        }


    return (<>
             <form>
                아이디[이메일] : <input onKeyUp={checkId}type="text" name = "memail" className = "memail"/>
                <span className = "checkIdtxt"></span><br/>
                비밀번호 : <input type="text" name = "mpassword" className = "mpassword"/><br/>
                전화번호 : <input type="text" name = "mphone" className = "mphone"/><br/>
                이름 : <input type="text" name = "mname" className = "mname"/><br/>
                <button type = "button" onClick={onSignup}>가입</button>
             </form>
    </>)
}

/*
   HTML ---> JSX
       1. <> </>
       2. class -> className
       3. style -> style={{}}
       4. 카멜표기법
        onclick (x) -> onClick(o)
        margin-top(x) -> marginTop(o)

*/