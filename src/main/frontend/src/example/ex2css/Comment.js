//Comment.js
    // class -> className
import React from 'react';
import styles from './Comment.css'
import logo from '../../logo.svg'//img 파일 가져오기

export default function Comment(props){
    return (<>
        <div className = "wrapper">
            <div>
                <img className = "logoImg" src ={logo}/>
            </div>
            <div className="contentContainer">
                <div className = "nameText"> {props.name} </div>
                <div className = "commentText"> {props.comment}</div>
            </div>
        </div>
    </>)
}