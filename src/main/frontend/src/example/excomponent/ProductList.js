import React from 'react';

import Product from './Product'; //Product 컴포넌트 호출

export default function ProductList(){
    return(<>
        <Product name ='사이다' price ='1200'/>
        <Product name ='제로콜라' price ='1800'/>
        <Product name ='환타' price ='1000'/>
        <Product name ='닥터페퍼' price ='1600'/>
    </>);
}