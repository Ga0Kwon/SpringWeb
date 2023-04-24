/*아이디 찾기 비밀번호 찾기*/
import React from 'react';
import axios from 'axios';

export default function Find(props){

/*    const checkView = (value) =>{
        what = value;
        console.log(what);
    }*/
        const onFindId = () => { // 아이디 찾기
            let info = {
                "mname" : document.querySelector(".mname").value,
                "mphone" : document.querySelector(".mphone1").value
            }

            console.log(info);

            axios.post("http://localhost:8080/member/find", info)
            .then(r => {
                if(r.data != 'undefined'){
                     document.querySelector(".returnId").innerHTML = `회원님의 아이디 : ${r.data}`;
                }else{
                    alert('해당 정보로 조회할 수 없습니다.')
                    window.location.href = "/";
                }
            }).catch(err => {console.log(err)})
        }

        const onFindPw = () => { // 비밀번호 찾기
            let info = {
                "memail" : document.querySelector(".memail").value,
                "mphone" : document.querySelector(".mphone2").value
            }

            console.log(info);

             axios.put("http://localhost:8080/member/find", info)
            .then(r => {
                console.log(r.data)
                if(r.data != 'undefined'){
                     document.querySelector(".returnPw").innerHTML = `회원님의 임시 비밀번호 : ${r.data}`;
                }else{
                    alert('해당 정보로 조회할 수 없습니다.')
                    window.location.href = "/";
                }
            }).catch(err => {console.log(err)})
        }


    return (<>
        {
            /*if (what == 0){
                (<div>
                    <button onClick = {checkView(1)}>아이디 찾기</button>
                    <button onClick = {checkView(2)}>비밀번호 찾기</button>
                </div>)
            }else if(what == 1){
               (<form>[이름] <input type="text" name = "mname" className = "mname"/><br/>
                     [전화번호]  <input type="text" name = "mphone" className = "mphone"/><br/>
                     <button onClick="onFindId()" type = "button">아이디 찾기</button>
                     <div class = "returnId"></div>
                </form>)
            }else if(what == 2){
                (<form>[아이디/이메일] <input type="text" name = "memail" className = "memail"/><br/>
                      전화번호 : <input type="text" name = "mphone" className = "mphone"/><br/>
                      <button onClick="onFindPw()" type = "button">비밀번호 찾기</button>
                      <div class = "returnPw"></div>
                </form>)
            }*/
        }

            <form>
                 <h3>아이디 찾기</h3>
                 [이름] <input type="text" name = "mname" className = "mname"/><br/>
                 [전화번호]  <input type="text" name = "mphone1" className = "mphone1"/><br/>
                 <button onClick={onFindId} type = "button">아이디 찾기</button>
                 <div class = "returnId"></div>
            </form>

            <form>
                  <h3>비밀번호 찾기</h3>
                  [아이디/이메일] <input type="text" name = "memail" className = "memail"/><br/>
                  전화번호 : <input type="text" name = "mphone2" className = "mphone2"/><br/>
                  <button onClick={onFindPw} type = "button">비밀번호 찾기</button>
                  <div class = "returnPw"></div>
            </form>


    </>)
}