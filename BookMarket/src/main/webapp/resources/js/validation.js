/**
 * 
 */

function checkAddBook(){
   
   let bookId = document.getElementById("bookId");
   let name = document.getElementById("name");
   let unitPrice = document.getElementById("unitPrice");
   let author = document.getElementById("author");
   let publisher = document.getElementById("publisher");
   let releaseDate = document.getElementById("releaseDate");
   let unitsInStock = document.getElementById("unitsInStock");
   let description = document.getElementById("description");
   
   
   if(!check(/^ISBN[0-9]{4,11}$/, bookId, "[상품 코드]\nISBN과 숫자를 조합하여 5~12자까지 입력하세요\n첫 글자는 반드시 ISBN로 시작하세요.")){
      return false;
   }
   
   if(name.value.length < 4 || name.value.length > 12){
      alert("[상품명]\n최소 4자에서 최대 50자까지 입력하세요.");
      name.select();
      name.focus();
      return false;
   }
   
   if(unitPrice.value.length == 0 || isNaN(unitPrice.value)){
         alert("[가격]\n숫자만 입력하세요.");
         unitPrice.select();
         unitPrice.focus();
         return false;
   }
   
   if(unitPrice.value < 0){
      alert("[가격]\n음수를 입력할 수 없습니다.");
      unitPrice.select();
      unitPrice.focus();
      return false;
   }else if(!check(/^\d+(?:[.]?[\d]?[\d])?$/, unitPrice, "[가격]\n소수점 둘째 자리까지만 입력하세요.")){
      return false;
   }
   
   if(author.value.length < 3){
            alert("[저자 이름]\n 저자 이름을 3자이상 입력하세요.");
            author.select();
            author.focus();
            return false;
   }
   
   if(publisher.value.length < 2){
      alert("[출판사 이름]\n 저자 이름을 2자이상 입력하세요.");
      publisher.select();
      publisher.focus();
      return false;
   }
   
   if(!check(/^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/, releaseDate, "[출판 날짜]\n숫자로(2024-01-01)형식으로 입력하세요.")){
      return false;
   }
   
   if(unitsInStock.value.length == 0 || isNaN(unitsInStock.value)){
      alert("[재고 수]\n숫자만 입력하세요.");
      unitsInStock.select();
      unitsInStock.focus();
      return false;
   }
   
   if(description.value.length == 0 || description.value.length < 5){
      alert("[상세설명]\n 최소 5자이상 입력하세요.");
      description.select();
      description.focus();
      return false;
   }
   
   document.newBook.submit();
}

function check(regExp, e, msg){
   
   if(regExp.test(e.value)){
      return true;
   }
   
   alert(msg);
   e.select();
   e.focus();
   return false;
}