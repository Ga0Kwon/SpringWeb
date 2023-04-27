import React, {useState} from 'react';

import {ListItem, ListItemText, InputBase,
        ListItemSecondaryAction, IconButton} from '@mui/material';

import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

import {Container} from '@mui/material'
import axios from 'axios';


export default function OneWayReply(props) {
    const [reply, setReply] = useState(props.item);

    const[readOnly, setReadOnly] = useState(true); //읽기모드
    const[login, setLogin] = useState(JSON.parse(sessionStorage.getItem("login_token")));

    console.log("reply" + reply)

    const deleteReply = props.deleteReply

    const deleteEventHandler = () => {
        deleteReply(reply);
    }

   const updateReply = props.updateReply

    const turnOffReadOnly = () => {
        if(login.mno == reply.mno){ // 자기가 쓴 댓글의 경우
            setReadOnly(false); //읽기모드 해제
        }
    }

    const turnOnReadOnly = (e) => {
        if(e.key === "Enter" || e.keyCode === 13){
             console.log("turnOnReadOnly")
             setReadOnly(true);
             //엔터를 칠때 수정이 완료된 것.
             axios.put("/onewayReply",  reply).then(r => {
                updateReply();
             })
        }
    }

    const editEventHandler = (e) => {
        console.log("editEventHandler")
        reply.rcontent = e.target.value; // InputBase에 값이 변경될 때마다 상태 변수에 입력된 값 저장
        updateReply();
    }

    const deleteBtnView = login.mno == reply.mno ?
        <IconButton onClick = {deleteEventHandler}><DeleteOutlined/></IconButton>
        : <div></div>



    return (<>
            <ListItem>
                <ListItemText>
                    <InputBase style ={{width : '150px'}}inputProps= {{readOnly : readOnly}}
                        onClick = {turnOffReadOnly}
                        onKeyDown = {turnOnReadOnly}
                        onChange = {editEventHandler}
                        type ="text"
                        rno = {reply.rno}
                        name = {reply.name}
                        value= {reply.rcontent}
                        multiline={true}
                    />
                </ListItemText>

                <ListItemSecondaryAction>
                    {deleteBtnView}
                </ListItemSecondaryAction>
            </ListItem>
        </>)

}