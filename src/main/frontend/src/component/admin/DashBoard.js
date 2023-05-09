import React, {useState, useEffect} from 'react';
import axios from 'axios';

import {Container} from '@mui/material'

//제품 출력
import ProductTable from './ProductTable';

export default function DashBoard(props){

    const setCategory = () => {
        let cname = document.querySelector(".cname")

        axios.post("/board/category/write", {"cname" : cname.value})
        .then((r) => {
            if(r.data == true){
                alert('카테고리 등록 성공')
                cname.value = '';
            }
        }

        )
    }
    return(<>
            <Container>
                <h3>관리자 페이지</h3>

                <h6>카테고리 등록</h6>
                <input type = "text" className = "cname"/>
                <button onClick={setCategory} type = "button">카테고리 등록</button>

                <ProductTable />
            </Container>
        </>)
}