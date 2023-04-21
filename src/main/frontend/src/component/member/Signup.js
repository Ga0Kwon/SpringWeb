import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Signup(props){
    return (<>
             <form>
                아이디[이메일] : <input onkeyup="checkId()" type="text" name = "memail" className = "memail"/>
                <span className = "checkIdtxt"></span><br/>
                비밀번호 : <input type="text" name = "mpassword" className = "mpassword"/><br/>
                전화번호 : <input type="text" name = "mphone" className = "mphone"/><br/>
                이름 : <input type="text" name = "mname" className = "mname"/><br/>
                <button type = "button" onclick="onSignup()">가입</button>
             </form>
    </>)
}