import React, {useState, useEffect} from 'react'
import axios from 'axios'

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';


export default function Category(props){
    let [cate, setCate] = useState(0)
    let [list, setList] = useState([]); //현재 선택된 select

    useEffect(() => {
        axios.get("http://localhost:8080/board/category/list")
        .then(res => {
            setList(res.data);
        })
    },[])

    const handleChange = (event) => {
        setCate(event.target.value)
        props.categoryChange(event.target.value)
    };

    //2.
/*    const categorybox = () => {
          let html = ``;
        for(let k in list){
            console.log(list[k])
            html += ` <MenuItem value={k.cno}>{k.cname}</MenuItem>`
        }
    }*/


    return (<>
         <Box sx={{ minWidth: 120 }}>
               <FormControl style={{width : '200px', margin : '20px 0px'}}>
                 <InputLabel id="demo-simple-select-label">카테고리</InputLabel>
                 <Select
                   labelId="demo-simple-select-label"
                   id="demo-simple-select"
                   value={cate}
                   label="카테고리"
                   onChange={handleChange}
                 >
                    <MenuItem value={0}>전체보기</MenuItem>
                   {
                    list.map((c) => {
                       return <MenuItem value={c.cno}>{c.cname}</MenuItem>
                    })
                   }
                 </Select>
               </FormControl>
         </Box>
    </>)
}