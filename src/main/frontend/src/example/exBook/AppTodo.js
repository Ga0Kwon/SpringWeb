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
        item.id = "ID-" + items.length //ID 구성 [items.length 식으로 하면 문제가 있음]
        item.done = false; //체크 여부
        setItems([...items, item]); // 기존 상태 items 메 item 추가
        //item = {title : "입력받은값", id = "ID-배열길이", done : "기본값 false"}
        //setItems([...상태명, 값]); : ... 앞에 추가 : react의 규칙
    }

    //3. item에 삭제할 item 삭제
    const deleteItem = (item) =>{
        console.log(item.id);
        // 만약에 items에 있는 item중 삭제할 id와 다른경우(삭제한 item이 아닌거니까) 해당 item 반환
        const newItems = items.filter(i => i.id !== item.id);
            // * 삭제할 id를 제외한 새로운 newItems 배열이 선언
        setItems([...newItems])

        //JS 반복문 함수 제공
            //r = [1, 2, 3]
            //배열/리스트.forEach((o) => {}) : 반복문만 가능 [return 없음]
                //let array = r.forEach((o) => { o+3 });
                // 반복문이 끝나도 array에 아무것도 들어있지 않다.

            //배열/리스트.map((o) => { }) : + return 값들을 새로운 배열 저장
                // let array = r.map((o) =? {return o + 3});
                // 반복문이 끝나면 array에는 [4, 5, 6]

            //배열/리스트.filter((o) => { }) : + 조건 충족할 경우 객체 반환
                // let array = r.filter((o) => { o>= 3});
                //반복문이 끝나면 array에는 [3]

    }

    //반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style = "maign:16px;"> : HTML 방식*/
        <Paper style = {{margin:16}}>  {/*JSX의 style 속성 방법 중괄호 두번*/}
            <List>{
                items.map((i) =>
                   <Todo item ={i} key = {i.id} deleteItem = {deleteItem}/>
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