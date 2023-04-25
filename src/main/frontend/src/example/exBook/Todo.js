//컴포넌트 만들기
import React, {useState} from 'react';

import {ListItem, ListItemText, InputBase, Checkbox,
        ListItemSecondaryAction, IconButton} from '@mui/material';

//IconButton : icon이 들어가는 박스권[hover도 있는듯]
//아이콘 설치[터미널] : npm install @mui/icons-material [ 다양한 아이콘 제공 ]

//삭제 아이콘
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

import axios from 'axios'; //npm install axios

//react Bootstrap 버전이 따로 있다.
//=> mui 를 더 많이 쓴다.


export default function Todo(props) {

    //1. Hook 상태관리 useState
    const [items, setItems] = useState(props.item);

    //2. props로 전달된 삭제함수 변수로 이동
    const deleteItem = props.deleteItem;
    //3. 삭제함수 이벤트처리 핸들러
    const deleteEventHandler = () => {
        deleteItem(items);
    }

    // 수정 함수 가져오기
    let editItem = props.updateItem;

    //4. readOnly = true 초기화가 된 필드/변수와 해당 필드르 수정할 수 있는 함수 setReadOnly[배열]
    const [readOnly, setReadOnly] = useState(true);
    //vs.
    /*let readOnly = true;*/

    //5. 읽기모드 해제
    const turnOffReadOnly = () => {
        console.log("turnOffReadOnly")
        setReadOnly(false); // readOnly가 true이면 읽기모드(수정 불가능), false이면 읽기모드해제(수정가능)
    }

    //6. 엔터키 눌렀을 때 -> 수정 금지
    const turnOnReadOnly = (e) => {
        if(e.key === "Enter" || e.keyCode === 13){
             console.log("turnOnReadOnly")
             setReadOnly(true);
             //엔터를 칠때 수정이 완료된 것.
             axios.put("/todo",  items).then(r => {
                editItem();
             })
        }

    }

    //7. 입력받은 값을 변경
    const editEventHandler = (e) => {
        console.log("editEventHandler")
        items.title = e.target.value; // InputBase에 값이 변경될 때마다 상태 변수에 입력된 값 저장
        editItem();
    }

    //8. 체크박스 업데이트
    const checkboxEventHandler = (e) => {
        items.done = e.target.checked;
        axios.put("/todo",  items).then(r => {
            editItem();
        })
    }

    return (<>
        <ListItem>
            <Checkbox
            checked = {items.done}
            onChange={checkboxEventHandler}
            />
            <ListItemText>
                <InputBase inputProps= {{readOnly : readOnly}}
                    onClick = {turnOffReadOnly}
                    onKeyDown = {turnOnReadOnly}
                    onChange = {editEventHandler}
                    type ="text"
                    id = {items.id}
                    name = {items.name}
                    value= {items.title}
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>

            <ListItemSecondaryAction>
                <IconButton onClick = {deleteEventHandler}>
                    <DeleteOutlined/>
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    </>)
}