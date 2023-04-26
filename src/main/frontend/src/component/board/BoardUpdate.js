import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom'; //HTTP 경로 상의 매개변수 호출해주는 함수

import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';

export default function BoardUpdate(props){

   const [board, setBoard] = useState({}); //해당 게시물 기본 정보

    const params = useParams(); //useParams() 훅 : 경로[URL]상의 매개변수 반환
    console.log("params : "+ params.bno)

    //기존 게시물 내용 가져오기
    const getDetailBoard = () => {
        axios.get("/board/getBoard", {params : {bno : params.bno}})
        .then(r => {
            console.log(r.data)
            setBoard(r.data)
        }).catch(err => {
            console.log(err)
        })
    }

    useEffect(() => {
          getDetailBoard()
    }, [])

    //수정한 내용 취소
    const updateCancel = () => {
        getDetailBoard()
    }

    //수정
    const boardUpdate = () => {
        let info = {
            btitle : document.querySelector('#btitle').value,
            bcontent : document.querySelector('#bcontent').value,
            bno : params.bno
        }

        console.log("btitle : " + info.btitle + " content : " + info.bcontent);

        axios.put("/board", info)
        .then(r =>{
            if(r.data == true){
                alert('수정 완료되었습니다.')
                window.location.href = "/board/view/"+board.bno;
            }else{
                alert('수정에 실패하였습니다.')
                getDetailBoard();
            }
        } ).catch(err => {
            console.log(err)
        })
    }


   return (<>
           <Container>
              <div>
                   <h3>제목</h3>
                    <TextField fullWidth id="btitle" className="btitle" label={board.btitle} variant="standard" /><br/>
                    <h3>내용</h3>
                    <TextField fullWidth id="bcontent" className="bcontent" label={board.bcontent}

                    multiline
                    rows={10}
                    variant="standard"/>
              </div>
              <div>
                <button type = "button" onClick = {updateCancel}>취소</button>
                <button type = "button" onClick = {boardUpdate}>수정</button>
              </div>
           </Container>
       </>)
}