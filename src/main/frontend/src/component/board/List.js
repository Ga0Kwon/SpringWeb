import React, {useState, useEffect} from 'react'
import axios from 'axios'

/*테이블 관련*/
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import Button from '@mui/material/Button';
import Pagination from '@mui/material/Pagination';

import Category from './Category';

/*import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@mui/material'*/

import {Container} from '@mui/material'
export default function List(props){

    //1. 요청한 게시물 정보를 가지고 있는 리스트 변수 [상태 관리변수]
    //usState함수를 이용한 값[배열] = [필드명, set필드명]

    let [rows, setRows] = useState([])
    let[pageInfo, setPageInfo] = useState({"cno" : 0, "page" : 1})
    let[totalPage, setTotalPage] = useState(1);
    let[totalCount, setTotalCount] = useState(0);

  /*  let [cno, setCno] = useState(0)
    let[page, setPage] = useState(1);*/

    //useEffect (() => {}) : 생성, 업데이트
    //useEffect(() => {} , []) : 생성될 때 1번
    //useEffect(() => {}, [변수]) : 생성, 해당 변수가 업데이트 될때마다 새 렌더링

    //2. useEffect
     useEffect (() => {
            axios.get("/board", {params: pageInfo})
            .then(res => {
                console.log(res.data)
                setRows(res.data.boardDtoList) //응답받은 게시물 대입
                setTotalPage(res.data.totalPage) //응답은 페이지 수 대입
                setTotalCount(res.data.totalCount) //응답 받은 총 게시물 수
            })
            .catch(err => {console.log(err)})
        }, [pageInfo]) //cno는 변할때마다 다시 조회

    // 2-1. 글쓰면 해당 글쓰기 페이지로 넘어감
    const goWrite = () => {
        window.location.href = "/board/write"

    }

    //3. 카테고리   변경
    const categoryChange1 = (cno) => {
        pageInfo.cno = cno; //안에 있는 것을 바꾼다고 렌더링X
        console.log("넘어온 cno : " + cno)
        setPageInfo({...pageInfo}); //렌더링을 위해
        //[...배열병] vs {...객체명} : 기존 배열/객체의 새로운 메모리 할당
    }



    //4. 페이지 번호
    const selectPage = (e) => {
/*        console.log(e.target)
        console.log(e.target.value) //button이여서 value 속성 없음
        console.log(e.target.outerText)
        console.log(e.target.outerText) // Tag 밖에있는 Text 출력 [해당 페이징 버튼의 번호가 Tag밖에 있다]*/
        pageInfo.page = e.target.outerText;
        setPageInfo({...pageInfo})
    }


    return(<>
        <Container>
        <div>현재페이지 : {pageInfo.page}  게시물 수 : {totalCount}</div>
        <div style={{display:'flex', justifyContent : 'space-between', alignItems : 'center'}}>
            <Category categoryChange = {categoryChange1}/>
            <Button variant="contained"onClick ={goWrite}>글쓰기</Button>
        </div>
         <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell align ="center" style={{width:'10%'}}>번호</TableCell>
                    <TableCell align ="center" style={{width:'45%'}}>제목</TableCell>
                    <TableCell align ="center" style={{width:'15%'}}>작성자</TableCell>
                    <TableCell align ="center" style={{width:'15%'}}>작성일</TableCell>
                    <TableCell align ="center" style={{width:'15%'}}>조회수</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows.map((row) => (
                    <TableRow
                      key={row.name}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <TableCell component ="th" align="center" scope="row">{row.bno}</TableCell>
                      <TableCell component ="th" align="left">{row.btitle}</TableCell>
                      <TableCell component ="th" align="center">{row.memail}</TableCell>
                      <TableCell component ="th" align="center">{row.bdate}</TableCell>
                      <TableCell component ="th" align="center">{row.bview}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <div style={{display : "flex", justifyContent : "center", margin : '40px 0px'}}>
                <Pagination count={totalPage} color="primary" onClick = {selectPage}/> {/*count : 전체페이지수[TotalPage]*/}
            </div>
          </Container>
    </>)
}