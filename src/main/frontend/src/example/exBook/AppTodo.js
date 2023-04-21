// import styles from '../../App.css'

import React, {useState, useEffect} from 'react';

import Todo from './Todo';

import AddTodo from './AddTodo';

import {List, Paper, Container} from '@mui/material';

import axios from 'axios'; //npm install axios

export default function AppTodo(props) {
    //1.
    //item 객체에 { id : "0", title : "Hello world 1",  done : true}
    const[items, setItems] = useState([]);

    //재사용을 위해 함수로 따로 만든다.(처음 들어올때, 수정, 삭제, 등록시)
    const getTodo = () =>{
        axios.get( "http://192.168.219.113:8080/todo" )
            .then( r => {
                console.log( r.data );
                setItems( r.data ); // 서버에게 응답받은 리스트를 재렌더링
            })
    }

  //컴포넌트가 실행될 때 한번 이벤트 발생
   useEffect (() => {
        // ajax : jquery 설치가 필요
        // fetch : 리액트 전송 비동기 통신 함수 [내장]
        // axios : 리액트 외부 라이브러리 [ 설치 필요 ] JSON 통신 기본 값
        //data : JSON.stringfy() => 안해도 됨.

        getTodo();

        /*const getTodo = () => {
            axios.get("http://localhost:8080/todo").then(r => {
                 console.log(r)
            })
        }*/
       /* axios.post("http://localhost:8080/todo", items).then(r => {
             console.log(r)
        })*/

       /* axios.put("http://localhost:8080/todo",  items).then(r => {
             console.log(r)
        })*/

       /* axios.delete("http://localhost:8080/todo", {params : {tNo : 1}}).then(r => {
             console.log(r)
        })*/

   }, []) //대괄호를 빼면, 계속 렌더링 무한루프에 빠진다.



    //2. items에 새로운 item을 등록하는 함수
    const addItem = (item) => { //함수로부터 매개변수로 전달받은 item
       /* item.id = "ID-" + items.length*/ //ID 구성 [items.length 식으로 하면 문제가 있음]
        /*item.done = false;*/ //체크 여부
        setItems([...items, item]); // 기존 상태 items 에 item 추가
        //item = {title : "입력받은값", id = "ID-배열길이", done : "기본값 false"}
        //setItems([...상태명, 값]); : ... 앞에 추가 : react의 규칙
        axios.post("http://192.168.219.113:8080/todo", item).then(r => {
             getTodo();
        })
    }

    //3. item에 삭제할 item 삭제
    const deleteItem = (item) =>{
        console.log(item.id);
        // 만약에 items에 있는 item중 삭제할 id와 다른경우(삭제한 item이 아닌거니까) 해당 item 반환
        const newItems = items.filter(i => i.id !== item.id);
            // * 삭제할 id를 제외한 새로운 newItems 배열이 선언
        //setItems([...newItems])
        axios.delete("http://192.168.219.113:8080/todo", {params : {tno : item.id}}).then(r => {
             getTodo();
        })


        //상태 변수를 사용안했을 때
/*        item.forEach((o, i) => {
            if(o.id == item.id){
                items.splice(i, 1);
            }
        })*/
        // item에는 삭제할 item이 제거된 배열
        // ?? 재 호출/다시 화면 새로고침??   =>    ddTodo() //X



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

    //4. 수정 함수
    const editItem = () => {
        // let newItems = items.map((o) => {return o})
        // setItmems([newItems])

        setItems([...items]); //재 랜더링
    }

    //반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style = "maign:16px;"> : HTML 방식*/
        <Paper style = {{margin:16}}>  {/*JSX의 style 속성 방법 중괄호 두번*/}
            <List>{
                items.map((i) =>
                   <Todo
                   item ={i}
                   key = {i.id}
                   deleteItem = {deleteItem}
                   updateItem = {editItem}/>
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