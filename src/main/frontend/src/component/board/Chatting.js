import React,{useEffect, useState, useRef} from 'react';

import {Container} from '@mui/material'

export default function Chatting(props) { //export : 내보내기

    //1. 웹 소켓 = webSocket = JS
    //let webSocket = new WebSocket("ws://localhost:8080/서버주소");
    let webSocket = useRef(null); //useRef : 참조하는 Hooks


    //2.
    useEffect(()=>{
        if(!webSocket.current){ //존재하지 않으면
             webSocket.current = new WebSocket("ws://localhost:8080/chat");
             console.log(webSocket);

             //1. 웹 소켓 전송을 성공했을 때
             webSocket.current.onopen = ()=>{
                 console.log("open");
             }
        }

    })

    return(<>
        <Container>
            <h6>익명 채팅방</h6>
            <div className="chatContentBox">

            </div>
            <div className="chatInputBox">
                <span>익명00</span>
                <input type ="text" />
                <button>전송</button>
            </div>
        </Container>
     </>)
}

/*
    JSX : html + javascript { }
        return에 script적인 것을 사용하기 위해서는 { }중괄호가 필수이다.
    JSP : html + java <% >

*/