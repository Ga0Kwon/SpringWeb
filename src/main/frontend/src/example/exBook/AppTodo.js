// import styles from '../../App.css'

import React, {useState} from 'react';

import Todo from './Todo';

import AddTodo from './AddTodo';

import {List, Paper, Container} from '@mui/material';

export default function AppTodo(props) {
    //1.
    //item 객체에 { id : "0", title : "Hello world 1",  done : true}
    const[items, setItems] = useState(
       [

       ] //array
    )

    //2. items에 새로운 item을 등록하는 함수
    const addItem = (item) => { //함수로부터 매개변수로 전달받은 item
        item.id = "ID-" + item.length //ID 구성
        item.done = false; //체크 여부
        setItems([...items, item]); // 기존 상태 items 메 item 추가
        //setItems([...상태명, 값]); : ... 앞에 추가 : react의 규칙
    }

    //반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style = "maign:16px;"> : HTML 방식*/
        <Paper style = {{margin:16}}>  {/*JSX의 style 속성 방법 중괄호 두번*/}
            <List>{
                items.map((i) =>
                   <Todo item ={i} key = {i.id} />
                 )
               }
           </List>
        </Paper>

    return (<>
        <div clasName="App">
            {/* Container : 박스권 화면 가운데로 잡아주는 컴포넌트*/}
            <Container maxWidth ="md">
                <AddTodo addItem ={addItem}/> {/*함수를 전달*/}
                {/*data 와 같은 props를 전달할때는 이름 아무거나 해도 된다.*/}
                {TodoItems}
            </Container>
        </div>
    </>);
}