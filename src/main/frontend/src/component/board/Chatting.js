import React,{useEffect, useState, useRef} from 'react';

import {Container} from '@mui/material'
import axios from 'axios';

import styles from '../../css/board/chatting.css'

export default function Chatting(props) { //export : 내보내기

    let[id, setId] = useState(''); //익명채팅에서 사용할 id [난수 저장]

    //채팅 입력창[input] DOM 객체 제어 변수
    let msgInput = useRef(null);

    //첨부파일 관련
    let fileForm = useRef(null);
    let fileInput = useRef(null);

    let[msgContent, setMsgContent] = useState([]); //현재 채팅중인 메시지를 저장하는 변수

   //1. 재렌더링 될 떼 마다 새로운 접속
   //let webSocket = new WebSocket("ws://localhost:8080/서버주소"); => 바깥으로 빼면 재렌더링 할때마다 접속을 한다..

   //2. 재렌더링 될 때 데이터 상태 유지
   let webSocket = useRef(null); //1. 모든 함수 사용할 클라이언트 소켓 변수

    useEffect (() => { //2. 재 렌더링시 1번만 실행
        if(!webSocket.current){//3. 만약 클라이언트 소켓이 없을 때 접속이 안되어 있을 때.
               webSocket.current = new WebSocket("ws://localhost:8080/chat");

               //3. 클라이언트 소켓이 서버 소켓에 접속했을 때 이벤트
               webSocket.current.onopen = ()=>{
                    //floor : 소숫점 자르는 거
                    let randomId = Math.floor(Math.random() * (9999-1) + 1); // 1~9999
                    setId("익명"+randomId);
                    console.log("웹 소켓 접속했습니다.");
               }

               //4. 서버소켓 나갔을 때
               webSocket.current.onclose = (e)=>{console.log("웹 소켓 나갑니다.");}

               //5. 서버소켓과 오류가 발생했을 때
               webSocket.current.onerror = (e)=>{console.log("웹 소켓 에러 발생했습니다.");}

               //6. 서버소켓으로부터 메시지 받았을 때
               webSocket.current.onmessage =  (e)=>{
                    console.log("웹 소켓 메시지왔습니다.");
                    console.log(e.data);

                    /*msgContent.push(e.data); //배열에 내용 추가
                    setMsgContent([...msgContent]); //재 렌더링 기존 배열을 새롭게 하는 것*/
                    let data = JSON.parse(e.data); //형변환 JSON 형식으로
                    setMsgContent((msgContent) => [...msgContent, data]); //재 렌더링, 기존 배열에 e.data를 넣어주는
               }
        }
    })


    //7. 메시지/첨부파일 전송
    const onSend = ()=>{
/*        console.log(msgInput.current.value);
        console.log(document.querySelector(".inputBox").value)*/

        //메시지 전송
        let msgBox = {
            id : id,
            msg : msgInput.current.value,
            time : new Date().toLocaleTimeString(), // 현재 시간만
            type : "msg"
        }

        if(msgBox.msg != ''){ //내용이 비어있지 않을 경우/있으면 보낸다.
            //msgInput 변수가 참조중인  <input ref = {msgInput} type ="text" className="inputBox"/>해당 input을 DOM객체로 호출
            webSocket.current.send(JSON.stringify(msgBox)); //클라이언트가 서버에게 메시지를 전송 [.send()]

            msgInput.current.value = "";
        }

        //2. 첨부파일 전송 [axios 이용한 서버에게 첨부파일 업로드]
        if(fileInput.current.value != ''){ //첨부파일이 존재하면
            axios.post("/chat/fileupload", new FormData(fileForm.current))
                .then(r => {console.log(r.data);})
        }

    }


    //8. 렌더링 할 때마다 스크롤 가장 하단으로 내리기
    useEffect (() => { //메시지 받을 때마다 렌더링
         document.querySelector(".chatContentBox").scrollTop = document.querySelector(".chatContentBox").scrollHeight;
    },[msgContent])

    return(<>
        <Container>
            <h6>익명 채팅방</h6>
            <div className="chatContentBox">
                {
                    msgContent.map((m) => {
                        return (<>
                            {/*조건 스타일링  : styles= {조건 ? {참일 경우} : {거짓일 경우}*/}
                            <div className ="chatCotent" style={m.id == id ? {backgroundColor:'#A2C0FF'} : { }}>
                               <span> {m.id} </span>
                               <span> {m.time} </span>
                               <span> {m.msg} </span>
                            </div>
                        </>)
                    })
                }
            </div>
            <div className="chatInputBox">
                <span>{id}</span>
                <input ref = {msgInput} type ="text" className="inputBox"/>
                <button className="chatBtn" onClick={onSend}>전송</button>
                <form ref={fileForm}>
                     <input type="file" name="attachFile" ref={fileInput}/>
                </form>
            </div>
        </Container>
     </>)
}

/*
    JSX : html + javascript { }
        return에 script적인 것을 사용하기 위해서는 { }중괄호가 필수이다.
    JSP : html + java <% >
 //1. 웹 소켓 = webSocket = JS
//let webSocket = new WebSocket("ws://localhost:8080/서버주소");
let webSocket = useRef(null); //useRef : 참조하는 Hooks*/


/*let 숫자 = 10; //지역변수 : 컴포넌트[함수] 호출될 때마다 초기화
let 숫자2 = useRef(10) // 재렌더링시 초기값이 적용되지 않는 함수
//document.querySelector vs. useRef
let inputRef = useRef(null);
const[id, setId] = useState(0); //set 메소드 사용시 컴포넌트 재호출[재렌더링]*/


   /* const onSend = ()=>{
        console.log(inputRef.current.value);
        console.log(document.querySelector(".inputBox").value)
    }


    //2. 난수 생성
    let randId = Math.floor(Math.random() * (9999 -1) + 1);
    // Math.floor(Math.random() * (최댓값 -최솟값) + 최솟값) : 정수 1부터 9999까지

    숫자 = randId;
    setId(randId);
    숫자2.current = randId;


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

    */