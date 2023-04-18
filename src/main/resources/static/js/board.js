console.log("board js")

let selectCno = 0; //초기값. [0 : 전체보기]

//1. 카테고리 등록
function setCategory() {
    console.log("setCategory")

    let cname = document.querySelector(".cname").value;

    $.ajax({
        url : "/board/category/write",
        method : "POST",
        data : JSON.stringify({"cname" : cname}),
        contentType : "application/json",
        success : function(r){
            console.log(r)
            document.querySelector(".cname").value = "";
            getCategory();
        }
    })

}

getCategory();

//2. 모든 카테고리 출력
function getCategory() {
    console.log("getCategory")

    $.ajax({
        url : "/board/category/list",
        method : "GET",
        success : (r)=>{
            let html = `<button onclick = "selectorCno(0)" type = "button">전체보기</button>`
            for(let cno in r){
                console.log("키 : " + cno)
                 html += `<button onclick = "selectorCno(${cno})" type = "button">${r[cno]}</button>`
            }
            document.querySelector(".categoryListBox").innerHTML = html;
        }
    })
}

//3. 카테고리 선택
function selectorCno(cno){
    console.log(cno + "의 카테고리 선택")
    selectCno = cno; //이벤트로 선택한 카테고리 번호 전역변수에 대입
    getBoard(cno); //선택한 카테고리 기준으로 게시물 출력
}
//4. 게시물 쓰기
function setBoard(){

    if(selectCno == 0){
        alert("작성할 게시물의 카테고리를 선택해주세요.")
        return;
    }

    let info = {
        btitle : document.querySelector(".btitle").value,
        bcontent : document.querySelector(".bcontent").value,
        cno : selectCno
    }


    $.ajax({
        url : "/board/write",
        method : "POST",
        data : JSON.stringify(info),
        contentType : "application/json",
        success : (r) =>{
            if(r == 0){
                alert('게시물이 등록되었습니다.')
                document.querySelector(".btitle").value = ''
                document.querySelector(".bcontent").value = ''
                getBoard(selectCno)
            }else if(r == 1){
                alert('알 수 없는 카테고리입니다.')
            }else if(r == 2){
                alert('로그인을 해야 게시물을 작성할 수 있습니다.')
            }else if(r == 3){
                alert('게시물 등록에 실패했습니다.')
            }
        }
    })
}
getBoard(0) // 0 : 전체 보기
//5. 게시물 출력 [선택된 카테고리의 게시물 출력]
function getBoard(cno){

    $.ajax({
        url : "/board/list",
        method : "GET",
        data : {"cno" : cno},
        success : (r) => {
            console.log(r);
            let html = `<tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                        </tr>`
            r.forEach((o) => {
                html += `<tr onclick="getDetailBoard(${o.bno})">
                            <td>${o.bno}</td>
                            <td>${o.btitle}</td>
                            <td>${o.memail}</td>
                            <td>${o.bdate}</td>
                            <td>${o.bview}</td>
                        </tr>`
            })

            document.querySelector(".boardListBox").innerHTML = html;
        }
    })
}
//6. 내가 작성한 (로그인 되어 있는 가정) 게시물
function myBoard(){
    $.ajax({
            url : "/board/myboards",
            method : "GET",
            success : (r) => {
                console.log(r);
                let html = `<tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>조회수</th>
                            </tr>`
                r.forEach((o) => {
                    html += `<tr onclick="getDetailBoard(${o.bno})">
                                <td>${o.bno}</td>
                                <td>${o.btitle}</td>
                                <td>${o.memail}</td>
                                <td>${o.bdate}</td>
                                <td>${o.bview}</td>
                            </tr>`
                })
                document.querySelector(".boardListBox").innerHTML = html;
            }
    })
}

function getDetailBoard(bno){
    console.log(bno + "상세보기")
    $.ajax({
        url : "/board/details",
        method : "GET",
        data : {"bno" : bno},
        success : (r) => {
             console.log(r);
            let html = `<tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>내용</th>
                            <th>카테고리</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                            <th>비고</th>
                        </tr>`

                html += `<tr>
                            <td>${r.bno}</td>
                            <td>${r.btitle}</td>
                            <td>${r.bcontent}</td>
                            <td>${r.cname}</td>
                            <td>${r.memail}</td>
                            <td>${r.bdate}</td>
                            <td>${r.bview}</td>
                            <td><button onclick="deleteBoard(${r.bno})">삭제</button></td>
                        </tr>`
            document.querySelector(".boardDetailsDiv").innerHTML = html;
        }
    })
}

function deleteBoard(bno){
    $.ajax({
        url : "/board/details",
        method : "DELETE",
        data : {"bno" : bno},
        success : (r) => {
            if(r == 0){
                alert('삭제 성공')
                getBoard(0)
                document.querySelector(".boardDetailsDiv").innerHTML = `<tr>
                                                                           <th>번호</th>
                                                                           <th>제목</th>
                                                                           <th>내용</th>
                                                                           <th>카테고리</th>
                                                                           <th>작성자</th>
                                                                           <th>작성일</th>
                                                                           <th>조회수</th>
                                                                           <th>비고</th>
                                                                       </tr>`
            }else if (r == 1){
                alert('삭제할 수 없는 게시물입니다')
            }else if(r == 2){
                alert('삭제 권한이 없습니다. 자기 게시물만 삭제해주세요.')
            }
        }
    })
}

/*
    해당 변수의 자료형 확인 prototype
    Array : forEach() 가능
          {object, object, object, object, object}

      Object : forEach() 불가능
            {
                필드명 : 값,
                필드명 : 값,
                필드명 : 값
            }
       Object[필드명] : 해당 필드의 값 호출
*/