import React from 'react'; //react 컴포넌트 라이브러리

// export default Book 해도 되고
// export default function Product(){return (<>렌더링할 내용</>)}을 해도 됨.

//props : properties 줄임말 [ 매개변수를 JSON 형태로 받는다.]

export default function Product(props){
    console.log(props)
    //let product = {name : props.name, price : props.price}

    return (<>
        <div>
            <h5>제품명 : {props.name} </h5>
            <h6>가격 : {props.price} </h6>
        </div>
    </>)
}