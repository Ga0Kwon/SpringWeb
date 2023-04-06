console.log("item js 실행");

//1. 조회
getItem();
function getItem(){
    $.ajax({
        type: "GET",
        url: "http://192.168.17.17:8080/item/get",
        success: function(data){
             let html = `<tr>
                              <th class = "pno">번호</th>
                              <th class = "pname">제품이름</th>
                              <th class = "pcontent">제품설명</th>
                              <th>비고</th>
                            </tr>`

            data.forEach(function(item){
               html += `<tr>
                        <td class = "pno">${item.pno}</td>
                        <td class = "pname">${item.pname}</td>
                        <td class = "pcontent">${item.pcontent}</td>
                        <td>
                            <button type = "button" onclick="onupdate(${item.pno})">수정</button>
                            <button type = "button" onclick="ondelete(${item.pno})">삭제</button>
                        </td>
                        </tr>`
            })

            document.querySelector(".productTable").innerHTML = html;
        }
    });
}

//2. 입력
function onwrite(){
    $.ajax({
        url : "http://192.168.17.17:8080/item/write",
        method : "POST",
        data : JSON.stringify({"pname" : document.querySelector(".inputpname").value,
                "pcontent" : document.querySelector(".inputpcontent").value}),
        contentType : "application/json",
        success : function(data){
            if(data == true){
                alert("작성 성공")
                document.querySelector(".inputpname").value = "";
                document.querySelector(".inputpcontent").value = "";
                getItem();
            }else{
                alert("작성 실패")
           }
        }
    })
}

//3. 수정
function onupdate(pno){
    console.log("수정 버튼 누름")
    let html = ` <input type = "text" class = "updatepname">
                  <input type = "text" class = "updatepcontent">
                  <button type = "button" onclick="onRealUpdate(${pno})">수정</button>`

    document.querySelector(".updateDiv").innerHTML = html;
}


function onRealUpdate(pno){
    $.ajax({
        url : "http://192.168.17.17:8080/item/update",
        method : "PUT",
        data : JSON.stringify({"pno" : pno,
        "pname" : document.querySelector(".updatepname").value,
        "pcontent" : document.querySelector(".updatepcontent").value}),
        contentType : "application/json",
        success : function(data){
            if(data == true){
                alert("수정 성공")
                  document.querySelector(".updateDiv").innerHTML = "";
                getItem();
            }else{
                alert("수정 실패")
            }
        }
    });
}

//4. 삭제
function ondelete(pno){
    console.log(pno)
    $.ajax({
        url : "http://192.168.17.17:8080/item/delete",
        method : "DELETE",
        data : {"pno" : pno},
        success : function(data){
            if(data == true){
                alert("삭제 성공")
                getItem();
            }else{
                alert("삭제 실패")
            }
        }
    });
}