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
           {
                id : "0",
                title : "Hello world 1",
                done : true
            },

             {
                id : "1",
                title : "Hello world 2",
                done : false
             }

       ] //array
    )
    //반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style = "maign:16px;"> : HTML 방식*/
        <Paper style = {{margin:16}}> {/*JSX의 style 속성 방법 중괄호 두번*/}
            <List>{
                items.map((i) =>
                   <Todo item ={i} />
                 )
               }
           </List>
        </Paper>

    return (<>
        <div clasName="App">
            {/* Container : 박스권 화면 가운데로 잡아주는 컴포넌트*/}
            <Container maxWidth ="md">
                <AddTodo />
                {/*data 와 같은 props를 전달할때는 이름 아무거나 해도 된다.*/}
                {TodoItems}
            </Container>
        </div>
    </>);
}