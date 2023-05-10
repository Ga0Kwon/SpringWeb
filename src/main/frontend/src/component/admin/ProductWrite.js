import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';

export default function ProductWrite(props){
    //1.재렌더링시 제외
    const writeForm = useRef(null); //useRef() : 객체 ={current : 데이터/돔}반환

    //2. 제품 글쓰기
    const onWriteHandler = () => {
        let writeFormData = new FormData(writeForm.current);
        axios.post("/product" , writeFormData)
            .then(r => {
                if(r.data == true){
                    alert('등록 성공')
                    props.tabChange(null, '3')
                }else{
                    alert('등록 실패')
                }
            })
    }

    return(<>
        <form ref={writeForm}>
            제품명 : <input type="text" name="pname"/> <br/>
            제품가격 : <input type="text" name="pprice"/> <br/>
            제품카테고리 : <input type="text" name="pcategory"/><br/>
            제품설명 : <input type="text" name="pcomment"/><br/>
            제품제조사 : <input type="text" name="pmanufacturer"/><br/>
            제품초기상태 : <input type="text" name="pstate"/><br/>
            제품재고 : <input type="text" name="pstock"/><br/>
            제품이미지 : <input
                    type="file"
                    multiple
                    accept="img/gif, img/jpeg, img/png"
                    name="pimgs"/><br/>
            <button type="button" onClick={onWriteHandler}>제품 등록</button>
        </form>
    </>)
}

//이미지 확장자 지정 = accept="img/gif, img/jpeg, img/png"