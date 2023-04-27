import React, {useState, useEffect} from 'react'
import axios from 'axios'
import {useParams} from 'react-router-dom'; //HTTP 경로 상의 매개변수 호출해주는 함수

import {List, Paper, Container} from '@mui/material';
import Pagination from '@mui/material/Pagination';

import OneWayAddReply from './OneWayAddReply';
import OneWayReply from './OneWayReply';

export default function OneWayReply(props){
    const params = useParams(); //useParams() 훅 : 경로[URL]상의 매개변수 반환
    console.log("bno : " + params.bno)
    const[items, setItems] = useState([]);
    const[pageInfo, setPageInfo] = useState({"bno" : params.bno, "page" : 1});
    let[totalPage, setTotalPage] = useState(1);
    let[totalCount, setTotalCount] = useState(0);

    const getReply = () => {
        console.log(pageInfo)
        axios.get("/board", {params : {bno : params.bno}})
        .then(r => {
            console.log(r.data.replyDtoList);
            setItems(r.data.replyDtoList)
/*            setTotalCount(r.data.totalCount)
            setTotalPage(r.data.totalPage)*/
        })
    }

    useEffect(() => {
        getReply();
    },[])

    //댓글 추가
    const addReply = (item) => {
        item.bno = params.bno;
        setItems([...items, items]);
        console.log("댓글 추가" + item.bno)
        axios.post("/reply", item)
        .then(r => {
            if(r.data == 0){
                alert('댓글이 등록되었습니다.');
                getReply();
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

    //댓글 삭제
    const deleteReply = (rp) => {
        console.log(rp.rno);

        axios.delete("/reply", {params : {rno : rp.rno}})
        .then(r => {
            getReply();
        })
    }

    //댓글 수정
    const updateReply = () => {
        setItems([...items]);
    }

    const selectPage = (event, value) => {
        pageInfo.page = value;
        setPageInfo({...pageInfo})
    }

    //반복문으로 댓글 모두 가져오기
    let totalReplys = <Paper style = {{margin:16}}>  {/*JSX의 style 속성 방법 중괄호 두번*/}
                                  <List>{
                                      items.map((i) =>
                                       <OneWayReply
                                         item ={i}
                                         key = {i.rno}
                                         deleteReply = {deleteReply}
                                         updateReply = {updateReply}/>
                                       )
                                     }
                                 </List>
                              </Paper>

    return (<>
           <div clasName="App">
               {/*<div>페이지 수 : {pageInfo.page}  댓글 수 : {totalCount}</div>*/}
               <Container maxWidth ="md">
                   <OneWayAddReply addReply ={addReply}/>
                   {totalReplys}
                   {/* <div style={{display : "flex", justifyContent : "center", margin : '40px 0px'}}>
                       <Pagination count={totalPage} color="primary" onChange = {selectPage}/> {*//*count : 전체페이지수[TotalPage]*//*}
                   </div>*/}
               </Container>
           </div>
    </>)
}