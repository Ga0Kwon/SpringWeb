import React, {useState, useEffect} from 'react'
import axios from 'axios'

import TreeView from '@mui/lab/TreeView';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import TreeItem from '@mui/lab/TreeItem';

export default function ReplyList(props){
    const[login, setLogin] = useState(JSON.parse(sessionStorage.getItem("login_token")));

    const onWriteHandler = () => {
        props.onReplyWrite(document.querySelector('.rcontent').value) //상위에 rcontent를 넘긴다.
    }

    const onDeleteHandler = (e, rno) => {
        props.onReplyDelete(rno)
    }

    const onUpdateHandler = (e, rno) => {
        props.onReplyUpdate(rno, document.querySelector('.newrcontent').value)
    }

    let deleteBtn = (mno, rno) => {
        console.log(mno)
        if(mno == login.mno){
          return <>
              <button onClick={ (e)=>onDeleteHandler( e , rno ) } >삭제</button>
              <input className="newrcontent" type="text"/>
              <button onClick={ (e)=>onUpdateHandler( e ,rno ) } >수정</button>
          </>;

        }else{
          return  <div></div>;
        }
    }

    let findRReply = (mno, rno) => {
        let html = ``;
        props.replyList.map((rr) => {
            if(rr.rindex == rno){
                console.log(rno + "가 같네")
                 html +=  <TreeItem nodeId={rr.rno} label={rr.rcontent}/>
            }
        })
        return html;
    }

    //compontent가 실행되는 것보단 axios가 느려서 r.rconent 깡통
    //  replyDtoList.map((r) => { return (<div>{r.rcontent}</div>) })
   return (<>
           <input className="rcontent" type="text" />  <button onClick={onWriteHandler}> 댓글작성 </button>
           <h6> 댓글 목록 </h6>
            <TreeView
                 aria-label="multi-select"
                 defaultCollapseIcon={<ExpandMoreIcon />}
                 defaultExpandIcon={<ChevronRightIcon />}
                 multiSelect
                 sx={{ height: 216, flexGrow: 1, maxWidth: 400, overflowY: 'auto' }}>

           {


                props.replyList.map((r)=>{
                   return(<div>
                            {
                                r.rindex == 0 ?
                                <TreeItem nodeId ={r.rno} label={r.rcontent}>
                                {findRReply(r.memberDto.mno, r.rno)}
                                {deleteBtn(r.memberDto.mno, r.rno)}
                                </TreeItem>
                                : <div></div>
                            }

                           {
                               /* JSX 형식에서 함수에 매개변수 전달 */
                               /*  <마크업 이벤트 = { (e)=>{ 함수명( e , 매개변수 ) } } /> */
                           }

                       </div>)
                })
         }

         </TreeView>
    </>)
}