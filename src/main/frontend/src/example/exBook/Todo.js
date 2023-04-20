//컴포넌트 만들기
import React, {useState} from 'react';

import {ListItem, ListItemText, InputBase, Checkbox,
        ListItemSecondaryAction, IconButton} from '@mui/material';

//IconButton : icon이 들어가는 박스권[hover도 있는듯]
//아이콘 설치[터미널] : npm install @mui/icons-material [ 다양한 아이콘 제공 ]

//삭제 아이콘
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

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

    return (<>
        <ListItem>
            <Checkbox checked = {items.done}/>
            <ListItemText>
                <InputBase
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