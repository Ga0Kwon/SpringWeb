import React, {useState, useEffect} from 'react';
import axios from 'axios';

import {Container} from '@mui/material'

import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList'; //npm i @mui/lab
import TabPanel from '@mui/lab/TabPanel';

//제품 출력
import ProductTable from './ProductTable';

export default function DashBoard(props){

    //1. 현재 선택한 탭 번호
    const [tab, setTab] = useState('1');

    //2. 탭 변경 함수
    const handleChange = (event, newTab) => {
        setTab(newTab);
    }

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
                 <Box sx={{ width: '100%', typography: 'body1' }}>
                      <TabContext value={tab}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                          <TabList onChange={handleChange} aria-label="lab API tabs example">
                            <Tab label="게시판카테고리등록" value="1" />
                            <Tab label="제품 등록" value="2" />
                            <Tab label="제품 관리" value="3" />
                            <Tab label="제품 통계" value="4" />
                          </TabList>
                        </Box>
                        <TabPanel value="1">
                            <h6>카테고리 등록</h6>
                            <input type = "text" className = "cname"/>
                            <button onClick={setCategory} type = "button">카테고리 등록</button>
                        </TabPanel>
                        <TabPanel value="2">
                            제품 등록
                        </TabPanel>
                        <TabPanel value="3">
                            <ProductTable />
                        </TabPanel>
                        <TabPanel value="4">
                            제품 통계
                        </TabPanel>
                      </TabContext>
                    </Box>
            </Container>
        </>)
}