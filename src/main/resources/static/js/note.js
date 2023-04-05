/*js 폴더 오른쪽 클릭 -> new -> file -> note.js 해서 만들어야 함*/
console.log("js 열림")

function onwrite(){
    console.log('등록');

    //[JSON.stringify() : json 타입에서 문자열 타입으로 변환, JSON.parse() : 문자열 타입에서 json 타입으로 변환
    //AJAX 이용한 @PostMapping에게 요청 응답
    $.ajax({
        url: "http://localhost:8080/note/write", //매핑 주소 값
        method: "POST", //매핑 HTTP 메소드
        //body 값에 JSON 형식의 문자열 타입 //contentType : "application/json"
        //전엔 쿼리스트링형식으로 보냈기 때문에 웬만하면 문제가 안생겼지만,
        //이젠 쿼리바디를 사용하기 때문에 application/json형식을 해줘야 한다.
        contentType: "application/json", // json형식으로 보내야한다.
        data: JSON.stringify({
            "ntitle" : document.querySelector(".inputntitle").value,
            "ncontent" : document.querySelector(".inputncontent").value
        }),
        success: function(data){
           if(data == true){
                alert("등록 성공");
                getBoard();
                document.querySelector(".inputntitle").value = "";
                document.querySelector(".inputncontent").value = "";
           }else{
               alert("등록 실패")
           }
        }
    });
}
//get 읽어오기
getBoard();

function getBoard(){
    $.ajax({
        url: "http://localhost:8080/note/get",
        method: "GET",
        success: function(data){
            console.log(data);

            let html = `<tr>
                          <th class = "nno">번호</th>
                          <th class = "ntitle">제목</th>
                          <th class = "ncontent">내용</th>
                          <th>비고</th>
                        </tr>`

            data.forEach(function(item){
               html += `<tr>
                        <td class = "nno">${item.nno}</td>
                        <td class = "ntitle">${item.ntitle}</td>
                        <td class = "ncontent">${item.ncontent}</td>
                        <td>
                            <button type = "button" onclick="onupdate(${item.nno})">수정</button>
                            <button type = "button" onclick="ondelete(${item.nno})">삭제</button>
                        </td>
                        </tr>`
            })

            document.querySelector(".noteTable").innerHTML = html;
        }
    });
}
//수정 바로 전
function onupdate(nno){
    console.log("수정 버튼 누름")
    let html = ` <input type = "text" class = "updatetitle">
                  <input type = "text" class = "updatecontent">
                  <button type = "button" onclick="onRealUpdate(${nno})">수정</button>`

    document.querySelector(".updateDiv").innerHTML = html;
}

//수정
function onRealUpdate(nno){

    $.ajax({
        url : "http://localhost:8080/note/update",
        method: "PUT",
        data: JSON.stringify({
        "nno" : nno,
        "ntitle" : document.querySelector(".updatetitle").value,
        "ncontent" : document.querySelector(".updatecontent").value
        }),
        contentType: "application/json",
        success :function(item){
            if(item == true){
                alert("수정 성공");
                document.querySelector(".updateDiv").innerHTML = "";
                getBoard();
            }else{
                alert("수정 실패");
            }
        }
    });
}

//삭제
function ondelete(nno){
    console.log("삭제버튼 누름")

    $.ajax({
        url : "http://localhost:8080/note/delete",
        method: "DELETE",
        data: {"nno" : nno},
        success : (r) => {
            if(r == true){
                alert("삭제 성공");
                getBoard();
            }else{
                alert("삭제 실패")
            }
        }
    });
}

