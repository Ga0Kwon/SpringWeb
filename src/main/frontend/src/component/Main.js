import React,{useState,useEffect} from 'react';
import axios from 'axios';

export default function Main(props){
    //localStorage.getItem('login_token') String형으로 변환했기 때문에 객체로 바꿔야한다.
    //
    //let [login, setLogin] = useState(JSON.parse(localStorage.getItem('login_token')));
    const [items, setItems] = useState([]);

    useEffect(() =>{
        axios.get('/product/main')
            .then((r) => {
                setItems(r.data);
            })
    },[])

    console.log(items)

    return(<>
        <h5>Main Page</h5>
        {/*img src="C://java//" +path/ => 안됨*/ }
        {
            items.map(it => {
                return(<div>
                    <img src={'http://localhost:8080/static/media/'+it.files[0].uuidFilename}/>
                </div>)
            })
        }
    </>)
}