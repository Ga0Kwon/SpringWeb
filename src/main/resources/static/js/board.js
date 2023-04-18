console.log("board js")

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

//2. 모든 카테고리 출력
function getCategory() {
    console.log("getCategory")

    $.ajax({
        url : "board/category/list",
        method : "GET",
        success : (r)=>{
            let html = `<button onclick = "selectCno(0)" type = "button">전체보기</button>`
            for(let cno in r){
                console.log("키 : " + cno)
                 html += `<button onclick = "selectCno(${cno})" type = "button">${r[cno]}</button>`
            }
            document.querySelector(".categoryListBox").innerHTML = html;
        }
    })
}

//3. 카테고리 선택
function selectCno(cno){
    console.log(cno + "의 카테고리 선택")
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