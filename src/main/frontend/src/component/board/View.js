import React, {useState, useEffect} from 'react'
import axios from 'axios'
import {useParams} from 'react-router-dom'; //HTTP 경로 상의 매개변수 호출해주는 함수

import {Container} from '@mui/material'

import ReplyList from './ReplyList';

export default function View(props){
    const [board, setBoard] = useState({replyDtoList : []}); //board 안에 replyDtoList이 들어있다.

    const params = useParams(); //useParams() 훅 : 경로[URL]상의 매개변수 반환
    /*console.log("params : "+ params.bno)*/

    const getBoard = () => {
        axios.get("/board/getBoard", {params : {bno : params.bno}})
        .then(r =>{
            console.log(r.data)
            setBoard(r.data)
        } ).catch(err => {
            console.log(err)
        })
    }
    useEffect(() => {
        getBoard();
    }, []) //setBoard() 할때마다 실행되는 useEffect

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

    //댓글 작성시 렌더링
    const onReplyWrite = (rcontent) => {
        let info = {
            rcontent : rcontent,
            bno : board.bno
        }
        console.log(info);

        axios.post("/board/reply", info).then((r) => {
               if(r.data == 0){
                   alert('댓글이 등록되었습니다.');
                   setBoard({...board});
                   getBoard()//재렌더링
               }else if(r.data == 1){
                   alert('로그인한 사용자만 댓글 입력이 가능합니다. 로그인해주세요.')
                   window.location.href = "/member/login"
               }else if(r.data == 2){
                   alert('댓글이 등록에 실패하였습니다. 관리자에게 문의해주세요.')
               }else if(r.data == 3){
                    alert('해당 게시물에 댓글을 작성할 수 없습니다.')
               }
         })
    }

    const onReplyDelete = (rno) => {
         axios.delete("/board/reply", {params : {rno : rno}})
        .then(r => {
            if(r.data == true){
                alert('삭제에 성공하였습니다.')
                getBoard();
            }else{
                alert('삭제 실패하였습니다.')
            }
        })
    }

    const onReplyUpdate = (rno, rcontent) => {
        let info = {
            rno : rno,
            rcontent : rcontent
       }
      console.log(info)
      axios.put("/board/reply", info).then(r => {
        if(r.data == true){
            alert('댓글이 수정되었습니다.')
            getBoard();
        }else{
            alert('댓글 수정에 실패하였습니다.')
        }
      })
    }

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
           <ReplyList onReplyWrite = {onReplyWrite} onReplyDelete = {onReplyDelete} onReplyUpdate = {onReplyUpdate} replyList = {board.replyDtoList}/>
        </Container>
    </>)
}
