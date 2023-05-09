import React, {useState, useEffect} from 'react';
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid'; //npm install 패키지명 vs npm i @mui/x-data-grid

const columns = [
  { field: 'id', headerName: '제품 번호', width: 150 },
  { field: 'pname', headerName: '제품명', width: 150 },
  { field: 'pprice', headerName: '제품 가격', type : 'number', width: 70 },
  { field: 'pcategory', headerName: '카테고리', width: 70 },
  { field: 'pcommnet', headerName: '제품 설명',width: 50,},
  { field: 'pmanufacturer', headerName: '제조사', width: 70 },
  { field: 'pstate', headerName: '상태', type : 'number', width: 30 },
  { field: 'pstock', headerName: '재고수량', type : 'number', width: 70 },
  { field: 'cdate', headerName: '최초 등록일', width: 100 },
  { field: 'udate', headerName: '최근 수정일', width: 100 },
];

export default function ProductTable(props){
    //1. 상태 변수
    const [rows, setRows] = useState([]) //제품 목록 가져와 저장할 useState

    //2. 제품 목록 가져오기
    const getProductList = () =>  {
         axios.get('/product').then(r => {setRows(r.data)}) //주소값이 바뀌는
    }


    //3. 컴포넌트 생명 주기에 따른 함수 호출
    useEffect(() => { //한번만 실행
        getProductList();
    }, [])

    //4. 데이터 테이블에서 선택된 제품 id 리스트
    const [rowsSelectionModel, setRowsSelectionModel] = useState([]) //선택된 제품 목록 가져와 저장할

    //5. 삭제 함수
    const onDeleteHandler = () => {
        let yesOrNo = window.confirm('정말 삭제하시겠습니까?')

        if(yesOrNo == true) { //확인 버튼을 클릭했을 때
            //선택된 제품 리스트를 하나씩 서버에게 전달
            rowsSelectionModel.forEach(r => { //반복문 돌려서 하나씩 보낸다.
                 axios.delete('/product', {params : {id : r}})
                    .then(r => {
                        if(r.data == true){
                            getProductList();
                        }
                    })
            })

        }
    }

    return(<>
        <button
            type="button"
            onClick ={onDeleteHandler}
            disabled={rowsSelectionModel.length <= 0 ? true : false}
        >선택 삭제 </button>
        <div style={{ height: 400, width: '100%' }}>
          <DataGrid
            rows={rows}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 5 },
              },
            }}
            pageSizeOptions={[5, 10, 15, 20]}
            checkboxSelection
            onRowSelectionModelChange={(newRowSelectionModel) => {
                setRowsSelectionModel(newRowSelectionModel);
            }}
          />
        </div>
    </>)
}