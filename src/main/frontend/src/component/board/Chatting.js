import React,{useEffect, useState, useRef} from 'react';

import {Container} from '@mui/material'

export default function Chatting(props) { //export : 내보내기

    let 숫자 = 10; //지역변수 : 컴포넌트[함수] 호출될 때마다 초기화
    let 숫자2 = useRef(10) // 재렌더링시 초기값이 적용되지 않는 함수
    //document.querySelector vs. useRef
    let inputRef = useRef(null);
    const[id, setId] = useState(0); //set 메소드 사용시 컴포넌트 재호출[재렌더링]

    //1. 웹 소켓 = webSocket = JS
    //let webSocket = new WebSocket("ws://localhost:8080/서버주소");
    let webSocket = useRef(null); //useRef : 참조하는 Hooks


    //2.
    useEffect(()=>{
        if(!webSocket.current){ //존재하지 않으면
            //소켓은 접속은 한번인데 메시지는 계속해서 받아야함.
             webSocket.current = new WebSocket("ws://localhost:8080/chat");
             console.log(webSocket);

             //1. 웹 소켓 전송을 성공했을 때
             webSocket.current.onopen = ()=>{
                 console.log("open");
             }
        }

    })

    const onSend = ()=>{
        console.log(inputRef.current.value);
        console.log(document.querySelector(".inputBox").value)
    }


    //2. 난수 생성
    let randId = Math.floor(Math.random() * (9999 -1) + 1);
    // Math.floor(Math.random() * (최댓값 -최솟값) + 최솟값) : 정수 1부터 1000까지

    숫자 = randId;
    setId(randId);
    숫자2.current = randId;


    return(<>
        <Container>
            <h6>익명 채팅방</h6>
            <div className="chatContentBox">

            </div>
            <div className="chatInputBox">
                <span>익명00</span>
                <input ref={inputRef} type ="text" className="inputBox"/>
                <button onClick={onSend}>전송</button>
            </div>
        </Container>
     </>)
}

/*
    JSX : html + javascript { }
        return에 script적인 것을 사용하기 위해서는 { }중괄호가 필수이다.
    JSP : html + java <% >

*/