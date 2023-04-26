import React, {useState, useEffect} from 'react'
import axios from 'axios'
import {useParams} from 'react-router-dom'; //HTTP 경로 상의 매개변수 호출해주는 함수

import {Container} from '@mui/material'

import ReplyList from './AppReply';

export default function View(props){
    const [board, setBoard] = useState({});

    const params = useParams(); //useParams() 훅 : 경로[URL]상의 매개변수 반환
    /*console.log("params : "+ params.bno)*/

    useEffect(() => {
        axios.get("/board/getBoard", {params : {bno : params.bno}})
        .then(r =>{
            console.log(r.data)
            setBoard(r.data)
        } ).catch(err => {
            console.log(err)
        })
    }, [])

    //게시물 삭제
    const onDelete = () => {
        axios.delete("/board", {params : {bno : params.bno}})
        .then(r => {
            if(r.data == 0){
                alert('게시물이 삭제되었습니다.')
                window.location.href = "/board/list";
            }else if(r.data == 2){
                alert('권한이 없습니다. ')
            }else if(r.data == 1){
                alert('해당 게시물 정보가 없습니다.')
            }else{
                alert('삭제 실패하였습니다. ')
            }
        })
    }

    const onUpdate =() => {
        window.location.href = "/board/update/"+params.bno;
    }
    //1. 세션 스토리지 확인해서 로그인 정보 확인
/*    let login = JSON.parse(sessionStorage.getItem("login_token"));
    console.log(login.mno) //일반 회원 : 숫자 // outh : 0
    console.log(board.mno)*/

    const[login, setLogin] = useState(JSON.parse(sessionStorage.getItem("login_token")));

    //1. 현재 로그인된 회원이 들어왔으면
    const btnBox = login != null && login.mno == board.mno ?
                <div> <button type = "button" onClick = {onDelete}>삭제</button><button type = "button" onClick = {onUpdate}>수정</button></div>
                : <div></div> ;



    return (<>
        <Container>
           <div>
                <h3>제목</h3> {board.btitle}
           </div>
           { btnBox }
           <ReplyList/>
        </Container>
    </>)
}
