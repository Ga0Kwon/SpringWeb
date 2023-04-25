import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import Category from './Category';


export default function Write(props){
    let[cno, setCno] = useState(0)

    //게시물 쓰기
    const setBoard = () => {
        let info = {
            btitle : document.querySelector("#btitle").value,
            bcontent : document.querySelector("#bcontent").value,
            cno : cno //선택된 카테고리 번호
        }
        console.log(info)

        axios.post("/board", info)
        .then(r => {
            console.log(r.data)
            if(r.data == 0){
                alert('게시물 등록되었습니다.')
                window.location.href = "/board/list"
            }else if(r.data == 1){
                 alert('해당 카레고리가 없습니다.')
                 window.location.href = "/"
            }else if(r.data == 2){
                alert('로그인을 해야 작성하실 수 있습니다.')
            }else{
                  alert("게시물 등록에 실패하였습니다. 관리자에게 문의해주세요.")
            }
        })
    }
    //카테고리 선택

    const categoryChange2 = (cno) => {
        setCno(cno);
    }

    const cancelBoard = () => {

    }

    return(<>
        <Container>
            <Category categoryChange = {categoryChange2}/>
            <h3>게시물 쓰기</h3>
            <TextField fullWidth id="btitle" className="btitle" label="제목" variant="standard" /><br/>
            <TextField fullWidth id="bcontent" className="bcontent" label="내용"
            multiline
            rows={10}
            variant="standard"/>
            <Button variant="contained"onClick ={setBoard}>등록</Button>
            <Button variant="contained"onClick ={cancelBoard}>취소</Button>
        </Container>
    </>)
}