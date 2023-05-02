import React, {useState, useEffect} from 'react'
import axios from 'axios'

import styles from '../../css/board/board.css'

export default function ReplyInput(props){

    //1. 입력받은 댓글 내용을 저장하는 변수
    const[rcontent, setRcontent] = useState(' ');

    //2.입력할 때 마다 [체인지 될때마다]
    const rcontentHandler =(e) => {
        setRcontent(e.target.value)
    }

    //3. 댓글 작성 버튼을 클릭할 때 [ 작성 ]
    const replyWriteHandler =(e) => {
        props.onReplyWrite(rcontent, props.rindex); //view.js로부터 전달받은 메소드실행
        setRcontent(' '); //댓글 작성시 공백 초기화
    }

    return(<div className="replyInputBox">
        {/*재활용이 가능해짐 => 댓글, 대댓글에서 두번 사용하기 위해 */}
         <textarea value={rcontent}
            onChange={rcontentHandler}
            className="rcontent" type="text" />
         <button className = "replyWriteBtn" onClick={replyWriteHandler}> 댓글작성 </button>
    </div>)
}