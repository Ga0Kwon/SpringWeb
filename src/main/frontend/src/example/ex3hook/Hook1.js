import React, {useState, useEffect} from 'react';

//1. 예제 Hook[useState()]을 사용 안했을 때
    //return : 렌더링 될 HTML 반환 해주는 곳 [ 화면에 표시할 코드]
    // 한번 실행
    // 컨포넌트가 당 1번씩 실행
/*
export default function Hook1(props){

    console.log("Hook1 컴포넌트 실행")
    let count = 0;


    const countHandler = (e) => {
         count++;
         console.log("현재 count : " + count) //증가는 하지만 밑의 return이 자동 렌더링이 안됨(count)값이 바뀌어도.
    }

    return(<>
        <div>
           <p> 총 {count}번 클릭했습니다. </p>
           <button onClick = {countHandler}>
               증가
           </button>
       </div>
    </>)
}
*/

//2. 예제 훅 [useState()]을 사용안했을 때
    //didMount -->
    //상태변수 상태 변경 : set ~~ () ---> didupdate => 재렌더링
/*export default function Hook1(props){

    console.log("Hook1 컴포넌트 실행")

    let [count, setCount] = useState(0); //초기값 count = 0

    const countHandler = () => {
        console.log("count 증가")
        setCount(++count)
    }

    return (<>
         <div>
               <p> 총 {count}번 클릭했습니다. </p>
               <button onClick = {countHandler}>
                   증가
               </button>
         </div>
    </>)

}*/


export default function Hook1(props){

    console.log("Hook1 컴포넌트 실행")

    let [count, setCount] = useState(0); //초기값 count = 0

    //1. useEffect() 함수 원형 : useEffect(() => {})
        /*
            컴포넌트의 생명 주기[LIFE Cycle]
                - mount : 생성 ,update : 업데이트, unmount : 제거
                1. useEffect(() => {})
                2. mount, update 시 실행되는 함수
                3. 하나의 컴포넌트에서 여러 번 사용 가능
                4. unmount 작동할 경우 return 사용
        */
    useEffect(() => {
        console.log("useEffect() 실행 총 클릭 수 : " + count)
    })

    useEffect(() => {
        console.log("useEffect() 실행 총 클릭 수 : " + count)
    })


    useEffect(() => {
        console.log("useEffect() 실행 총 클릭 수 : " + count)
        return () => {console.log('제거 되었음 ')}
    })

    const countHandler = () => {
        console.log("count 증가")
        setCount(++count)
    }

    return (<>
         <div>
               <p> 총 {count}번 클릭했습니다. </p>
               <button onClick = {countHandler}>
                   증가
               </button>
         </div>
    </>)

}