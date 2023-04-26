import React, {useState, useEffect} from 'react'
import axios from 'axios'
import {useParams} from 'react-router-dom'; //HTTP 경로 상의 매개변수 호출해주는 함수

import {Container} from '@mui/material'

export default function View(props){
    const [board, setBoard] = useState({});

    const params = useParams(); //useParams() 훅 : 경로[URL]상의 매개변수 반환
    console.log("params : "+ params.bno)

    useEffect(() => {
        axios.get("/board/getBoard", {params : {bno : params.bno}})
        .then(r =>{
            console.log(r.data)
            setBoard(r.data)
        } ).catch(err => {
            console.log(err)
        })
    }, [])

    //1. 현재 로그인된 회원이 들어왔으면
    const btnBox = () => {
        //1. 세션 스토리지 확인해서 로그인 정보 확인
        let login = JSON.parse(sessionStorage.getItem("login_token"));
        console.log(login.mno)
        console.log(board.mno)

        if(login.mno == board.mno) {
            return (<div>
                        <button type = "button">삭제</button>
                        <button type = "button">수정</button>
                    </div>)
        }
    }

    return (<>
        <Container>
           <div>
                <h3>제목</h3> {board.btitle}
           </div>

        </Container>
        { btnBox }
    </>)
}
