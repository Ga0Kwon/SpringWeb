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

import Category from './Category';

/*import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper} from '@mui/material'*/

import {Container} from '@mui/material'
export default function List(props){

    //1. 요청한 게시물 정보를 가지고 있는 리스트 변수 [상태 관리변수]
    //usState함수를 이용한 값[배열] = [필드명, set필드명]

    let [rows, setRows] = useState([])
    let [cno, setCno] = useState(0)

    //3. 카테고리   변경
    const categoryChange = (cno) => {
        console.log("넘어온 cno : " + cno)
        setCno(cno);
    }

    useEffect (() => {
        axios.get("http://localhost:8080/board/list", {params: {cno : cno}})
        .then(res => {
            console.log(res.data)
            setRows(res.data)
        })
        .catch(err => {console.log(err)})
    }, [cno]) //cno는 변할때마다 다시 조회

    //useEffect (() => {}) : 생성, 업데이트
    //useEffect(() => {} , []) : 생성될 때 1번
    //useEffect(() => {}, [변수]) : 생성, 해당 변수가 업데이트 될때마다 새 렌더링

    const goWrite = () => {
        window.location.href = "/board/write"
    }


    return(<>
        <Container>
        <div style={{display:'flex', justifyContent : 'space-between', alignItems : 'center'}}>
            <Category categoryChange = {categoryChange}/>
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
          </Container>
    </>)
}