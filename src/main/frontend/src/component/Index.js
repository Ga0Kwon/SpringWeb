import React from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom"; //npm install react-router-dom


import Header from "./Header";
import Main from "./Main";
import Footer from "./Footer";

import Login from "./member/Login";
import Signup from "./member/Signup";

/*
    React-router-dom 다양한 라우터 컴포넌트 제공
    1. <BrowserRouter> : 가상 URL 관리[URL 동기화]
    2. <Routes> : 가장 적합한 <Route>컴포넌트를 검토하고 찾는다.(Mapping)
        요청된 PATH에 적합한 <Route> 찾아서 <Routes> 범위내 렌더링
    3. <Route> : 실제 URL 경로를 지정해주는 컴포넌트
        <Route path = "login" element={<Login/>}/> : http://localhost:3000/login : GET 요청시 Login 컴포넌트 반환
         => spring의 @GetMapping("/login")과 유사한 역할
*/
export default function Index(props){
    return (<>
        <BrowserRouter>

            <Header/> {/*헤더는 라우터에 넣지X*/}

            <Routes>
                <Route path = "/" element={<Main/>}/>
                <Route path = "login" element={<Login/>}/>
                <Route path = "signup" element={<Signup/>}/>
            </Routes>

            <Footer/>{/*푸터는 라우터에 넣지X*/}
        </BrowserRouter>
    </>)
}