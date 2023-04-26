import React,{useState} from 'react';

import {Container} from '@mui/material'
import {Button, Grid, TextField} from '@mui/material';
import {useParams} from 'react-router-dom';

export default function AddTodo(props) {
    const params = useParams(); //useParams() 훅 : 경로[URL]상의 매개변수 반환
    const [reply, setReply] = useState({rcontent : "", bno : ""}) //초기값 깡통

    console.log()
    const onInputChange =  (e) => {
        setReply({rcontent : e.target.value})
    }

    const addReply = props.addReply;

    const onButtonClick =  (e) => {
        addReply(reply); //상위에 전달
        //입력받은 데이터를 AppTodo컴포넌트 한테 전달받은 addItem함수의 매개변수 넣고 함수 실행
        setReply({rcontent : "", bno : params.bno})
    }

    const enterKeyEventHandler = (e) =>{
        if(e.key === 'Enter' || e.keyCode === 13){
            onButtonClick();
        }
    }


     return(<>
            {/*style {}안에 지정할때 하이픈(-)안쓰고 카멜표기법을 사용*/}
            <Grid container style = {{marginTop : 20}}>
                <Grid xs ={5} md = {5} item style = {{paddingRight:16}}>
                    <TextField
                    placeholder ="댓글 작성"
                    fullWidth
                    value = {reply.rcontent} /*상태변수 초기화*/
                    onChange={onInputChange}
                    onKeyPress={enterKeyEventHandler}/>
                </Grid>
                <Grid xs = {1} md = {1} item>
                    <Button
                    fullWidth style = {{height : '100%'}}
                    color = "secondary"
                    variant = "outlined"
                    onClick = {onButtonClick}>
                        +
                    </Button>
                </Grid>
            </Grid>
        </>)


}