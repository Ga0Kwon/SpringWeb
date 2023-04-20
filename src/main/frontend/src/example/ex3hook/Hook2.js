import React, {useState, useEffect} from 'react';

export default function Hook2 (props){

    let [value, setValue] = useState(0);
    console.log("value: " + value);

    useEffect (() => {
        console.log('[] 있는 useEffect 실행')

        return () => {
            console.log('[] 있는 useEffect 종료되면서 실행')
        }
    }, []) //빈 배열 [] 대입 update 제외 딱 1번만 실행

    useEffect (() => {
        console.log('[] 없는 useEffect 실행')

        return () => {
            console.log('[] 없는 useEffect 종료되면서 실행')
        }
    }, )

    useEffect (() => {
            console.log('[상태변수] 있는 useEffect 실행')

            return () => {
                console.log('[상태변수] 있는 useEffect 종료되면서 실행')
            }
    }, [value]) //배열[ 상태변수명] 대입 해당 상태변수가 update될 때 mount



    return (<>
        <div>
            <p>{value}</p>
            <button onClick = { (o) => setValue(++value)}>
                +
            </button>
        </div>
    </>)
}