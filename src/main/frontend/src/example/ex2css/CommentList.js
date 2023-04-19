//CommentList.js

import React from 'react';
import Comment from './Comment';

export default function CommentList(props){

    // 서버로 부터 받은 데이터[JSON] 예시
    let r = [
        {name : "유재석", comment : "안녕하세요."},
        {name : "이광수", comment : "하하하하하하."},
        {name : "강호동", comment : "나도 안녕하신가요."}
    ];

    console.log(r);

    //return 안에서 js문 사용시 { }
    //map [return 가능] vs. forEach[return 불가능]
    {/*jsx 안에서 주석 처리하려면 {} 중괄호를 사용해야한다.*/}
    return(<>
        <div>
             {
                r.map( (c) =>{
                    return (<Comment name ={c.name} comment ={c.comment}/>);
                })
             }
        </div>
    </> );
}