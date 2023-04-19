//컴포넌트 만들기
import React, {useState} from 'react';

import {ListItem, ListItemText, InputBase, Checkbox} from '@mui/material';

//react Bootstrap 버전이 따로 있다.
//=> mui 를 더 많이 쓴다.

export default function Todo(props) {

    //1. Hook 상태관리 useState
    const [items, setItems] = useState(props.item);

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
        </ListItem>
    </>)
}