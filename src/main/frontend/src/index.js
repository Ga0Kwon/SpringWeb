import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // * 1. import를 이용해 App컴포넌트[함수]를 불러온다

import reportWebVitals from './reportWebVitals';

//import 컴포넌트명 form index.js 파일 기준으로 상대 경로
import Book from './example/excomponent/Book'; // * 1. import 를 이용해 Book 컴포넌트[함수]를 불러온다.

//1. HTML 에 존재하는 div 가져오기 [document.getElementById('root')]
//2. ReactDOM.createRoot(해당 div[엘리먼트]) : 해당 div를 react root로 사용하여 root 객체 생성
const root = ReactDOM.createRoot(document.getElementById('root'));

//3. root.render() : 해당 root 객체 (우리가 가져온 div)의 컴포넌트 렌더링
//*2. 괄호 컴포넌트 슬래시 괄호 을 이용해 컴포넌트를 사용한다.
//4. App 컴포넌트에 render 함수에 포함[App 호출하는 방법 : 상단에 import]
root.render(
  <React.StrictMode>

   <Book />
  </React.StrictMode>
);

/* ------------------------------- vs. ---------------------------*/
// react 를 사용하기 전에 직접 렌더링
/*document.getElementById("#root").innerHTML =
        `<div className="App">
            <header className="App-header">
              <img src={logo} className="App-logo" alt="logo" />
              <p>
                Edit <code>src/App.js</code> and save to reload.
              </p>
              <a
                className="App-link"
                href="https://reactjs.org"
                target="_blank"
                rel="noopener noreferrer"
              >
                Learn React
              </a>
            </header>
          </div>`*/

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
