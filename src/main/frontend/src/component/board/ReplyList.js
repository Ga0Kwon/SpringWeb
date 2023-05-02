import React, {useState, useEffect} from 'react'
import axios from 'axios'

import ReplyInput from './ReplyInput';
import styles from '../../css/board/board.css'

export default function ReplyList(props){
    // props으로 부터 전달받은 댓글 리스트를 관리하는 상태 변수
    let[replyList, setReplyList] = useState([]); //속도 차이로 처음에 깡통이 들어와서 []을 해준다

    //props가 변경되었을 때 [view.js.axios 실행]
    useEffect(() => {
        setReplyList(props.replyList)
    },[props.replyList]) // 상위 컴포넌트[props가 렌더링될 때마다]

    const[login, setLogin] = useState(JSON.parse(sessionStorage.getItem("login_token")));

    const onWriteHandler = (rcontent) => {
        props.onReplyWrite(rcontent) //상위에 rcontent를 넘긴다.
    }

    //삭제
    const onDeleteHandler = (e, rno) => {
        props.onReplyDelete(rno)
    }

    //수정
    const onUpdateHandler = (e, rno, i) => {
        //Rcontent 읽기 모드 해제
        if(replyList[i].readOnly == true){
            replyList[i].readOnly = false;
            alert('수정 후 완료버튼을 눌러주세요.')
        }else{//Rcontent 읽기모드 적용
            replyList[i].readOnly = true;
            props.onReplyUpdate(rno, replyList[i].rcontent)
        }

        setReplyList([...replyList])
    }
    //답글 핸들러
    const onRereplyHandler= (e, rno) => {
        console.log('대댓글 : ' + rno);
        replyList.forEach((r, i) => { //전체 댓글
             // 전체 댓글 번호중에 본인이 선택한 댓글 번호와 같으면
             if(r.rno == rno){
                if(e.cusHTML == '' ||  r.cusHTML == undefined){
                      // 새로운 필드에 새로운 html 구성
                    replyList[i].cusHTML = <div>
                        <ReplyInput onReplyWrite={props.onReplyWrite} rindex ={rno}/> {/*상위 댓글을 작성하는*/}
                            {/*답글 출력*/}
                            {
                                r.rereplyDtoList.map((rr, i) => {
                                    return(<>
                                        <div className ="replyBox">
                                           <span className="replayMname"> { rr.mname } </span>
                                           <span className="replyRdate"> { rr.rdate } </span>

                                           <input className ="replyRcontent"
                                                value={r.rcontent}
                                                readOnly = {r.readOnly}
                                                onChange={(e) => onRcontentChange(e, rr.rno, i)}/>{/*읽기 모드*/}
                                            {
                                                 login != null && login.mno == r.mno
                                                ?
                                                    <>
                                                        <button onClick={ (e)=>onUpdateHandler( e , r.rno, i ) } >{
                                                            r.readOnly == true? '수정' : '완료'
                                                        }</button>
                                                        <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >삭제</button>
                                                    </>
                                                :
                                                <></>
                                            }
                                    </>)
                                })
                            }

                    </div>
                }else{ //해당 답글 구역 숨기기
                    replyList[i].cusHTML = ''
                }

             }
        })
        setReplyList([...replyList])
    }

    // 댓글 내용 수정
    const onRcontentChange = (e, rno, i) => {
        replyList[i].rcontent = e.target.value;
        setReplyList([...replyList]) // 렌더링
    }

    //compontent가 실행되는 것보단 axios가 느려서 r.rconent 깡통
    //  replyDtoList.map((r) => { return (<div>{r.rcontent}</div>) })
    return (<>
           { /* 상위 댓글[ rindex=0 ] 작성하는 input */ }
           <ReplyInput
               onReplyWrite = { props.onReplyWrite }
               rindex = {0}
           />

           <div className="replyCount"> 댓글 {replyList.length}개 </div>
           {
                replyList.map( (r, i)=>{
                    {/*Rcontent 읽기모드 설정값 저장하는 필드 만들기*/}
                    if(r.readOnly == undefined){r.readOnly = true}
                   return(<div className ="replyBox">
                           <span className="replayMname"> { r.mname } </span>
                           <span className="replyRdate"> { r.rdate } </span>
                           {/*상태변수를 넣으면 렌더링이 되는 와중에 값을 입력하기 때문에 값이 변하지 않는다.*/}
                           <input className ="replyRcontent"
                                value={r.rcontent}
                                readOnly = {r.readOnly}
                                onChange={(e) => onRcontentChange(e, r.rno, i)}/>{/*읽기 모드*/}
                           <div class="replyBtn">
                             <button className ="rerplyBtn" onClick={ (e)=>onRereplyHandler( e , r.rno ) } >답글</button>
                             {
                                login != null && login.mno == r.mno
                                ?
                                    <>
                                        <button onClick={ (e)=>onUpdateHandler( e , r.rno, i ) } >{
                                            r.readOnly == true? '수정' : '완료'
                                        }</button>
                                        <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >삭제</button>
                                    </>
                                :
                                <></>
                             }

                           </div>
                           { r.cusHTML } { /* API 없던 필드 */}
                       </div>)
               })
           }
       </>)
}