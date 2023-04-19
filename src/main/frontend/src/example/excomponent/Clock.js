import React from 'react';

export default function Clock(props) {

   // 함수 안에서 js 문법은 자유적으로 작성 가능
  // let clock = new Date().toLocaleTimeString();

   //함수 안에서 return () 안에서 js 문법은 {} 처리
    return (<>
        <div>
            <h3>React Clock</h3>
            <h4>현재 시간 : {new Date().toLocaleTimeString()}</h4>
        </div>
    </>)
}