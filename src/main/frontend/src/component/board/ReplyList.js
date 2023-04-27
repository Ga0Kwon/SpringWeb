import React, {useState, useEffect} from 'react'
import axios from 'axios'

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';


export default function ReplyList(props){
    const [replyDtoList, setReplyDtoList] = useState(props.replyDtoList);


    const onWriteHandler = () => {
        props.onReplyWrite(document.querySelector('.rcontent').value) //상위에 rcontent를 넘긴다.
    }

    return (<>
        <input className ="rcontent" type = "text"/> <button onClick={onWriteHandler}>댓글작성</button>
        <h3>댓글 목록</h3>
    </>)
}