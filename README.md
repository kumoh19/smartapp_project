<h1>BookHive</h1>
<h3>1. 서론</h3>
도서 정보 앱 프로젝트를 설계하고자 한다. 최근 독서 인구가 급격히 줄어들고 있는 상황에서, 앱 기반으로 도서에 접근할 수 있는 편의성을 제공함으로써 독서 인구를 증가시킬 수 있다. 이를 통해 문맥 이해 능력을 향상시키고, 문맹률을 줄이며, 사회적으로 긍정적인 효과를 가져올 수 있다.<br>
본 프로젝트는 독서를 장려하고 도서 접근성을 높이는 데에 초점을 둔다. 책 목록과 책의 세부 정보를 보여주는 기능을 제공하여 사용자들이 독서에 대한 흥미를 공유하고 함께 성장할 수 있는 환경을 제공한다.
이를 통해 독서를 즐기는 문화를 조성하고, 문맥 이해 능력을 향상시켜 사회 전반에 긍정적인 영향을 미칠 수 있다.

<h3>2. 작품 개요</h3>

**2.1 전체 구성도**


![image](https://github.com/kumoh19/smartapp_project/assets/104006988/ab3c328c-1669-456f-9bfa-24912c96898e)


**2.2 작품 설명**
1. 책 목록 요청
- 사용자가 앱에서 책 목록을 요청한다.
- 클라이언트 앱은 서버에 HTTP 요청을 보낸다.
- 서버는 MariaDB에서 책의 데이터를 조회한다.
- 조회된 데이터를 클라이언트에게 JSON 형태로 응답한다.
- 클라이언트 앱은 응답 데이터를 사용하여 책 목록을 화면에 표시한다.

2. 책 세부 정보 요청
- 사용자가 특정 책의 세부 정보를 보기 위해 선택한다.
- 클라이언트 앱은 선택된 책의 세부 정보를 서버에 HTTP 요청한다.
- 서버는 요청받은 책의 세부 정보를 MariaDB에서 조회한다.
- 조회된 세부 정보를 클라이언트에게 JSON 형태로 응답한다.
- 클라이언트 앱은 응답 받은 데이터를 사용하여 세부 정보 화면을 구성하여 표시한다.

<h3>3. 설계 </h3>

**3.1 UI Design**
1. 책 목록 보기<br>
![image](https://github.com/kumoh19/smartapp_project/assets/104006988/9dcb7641-3151-4783-94cd-e6e62d78cf77)

사용자가 모바일 애플리케이션을 통해 볼 수 있는 책 목록을 보여준다. 각 책은 아래와 같은 방식으로 표시된다.

- 책 표지: 왼쪽에는 각 책의 표지 이미지가 둥근 모서리의 카드에 표시된다.
- 책 제목: 표지 이미지의 오른쪽에 큰 글씨로 책 제목이 표시된다.
- 저자 이름: 제목 아래에 저자의 이름이 더 작은 글씨로 표시된다.

이 화면은 사용자가 책을 탐색하고 원하는 책을 빠르게 식별할 수 있게 해준다. 각 책은 클릭 가능하며, 사용자가 책을 선택하면 그 책의 상세 정보 화면으로 넘어간다.

2. 책 상세 정보 보기<br>
![image](https://github.com/kumoh19/smartapp_project/assets/104006988/bc40e542-edcc-4c7b-a235-e6567b7cff6b)

사용자가 특정 책을 선택했을 때 보여지는 상세 정보 화면이다. 다음 정보가 표시된다.

- 별점: 화면 상단에는 책의 평가를 나타내는 별 모양의 아이콘이 있다. 별의 수는 책의 평점을 나타낸다.
- 책 제목: 책의 제목이 큰 글씨로 중앙에 표시된다.
- 책 표지: 제목 아래에는 책의 표지 이미지가 크게 표시된다.
- 저자 이름: 표지 이미지 아래에 저자의 이름이 있다.

추가적으로, 화면 하단에는 책의 상세 설명이 표시되며, '책 검색' 버튼을 통해 사용자가 구글에서 해당 책에 대해 더 많은 정보를 검색할 수 있도록 한다. 이 버튼을 누르면 사용자의 웹 브라우저가 열리고 구글 검색 결과가 표시된다.


**3.2 DB Design**

3.2.1 Conceptual Design


![image](https://github.com/kumoh19/smartapp_project/assets/104006988/8e469391-8242-4662-bc93-9fb89949d487)


3.2.2 Logical Design


CREATE TABLE Book (<br>
    id INT AUTO_INCREMENT PRIMARY KEY,<br>
    title VARCHAR(255) NOT NULL,<br>
    writer VARCHAR(255) NOT NULL,<br>
    rating INT,<br>
    image VARCHAR(255),<br>
    description TEXT<br>
);<br>
- id: 책의 고유 식별자로, 기본키이며, 자동 증가되는 정수 값이다.
- title: 책의 제목을 나타내는 문자열이다.
- writer: 책의 저자를 나타내는 문자열이다.
- rating: 책의 평점을 나타내는 정수 값이다. 선택적 필드이다.
- image: 책의 이미지 URL을 나타내는 문자열이다. 선택적 필드이다.
- description: 책에 대한 설명을 나타내는 텍스트이다. 선택적 필드이다.

<h3>4. 구현</h3>

1. 책 목록 보기<br>
![image](https://github.com/kumoh19/smartapp_project/assets/104006988/7d1f981c-e9a4-493d-81c8-a83d7697a649)

이 화면은 BookList 및 BookItem 컴포저블 함수에 의해 구현된다. 사용자가 앱을 실행하면, BookList함수는 LazyColumn을 사용하여 전달받은 책 목록을 스크롤 가능한 리스트로 표시한다. 각 BookItem은 Card 안에 Row를 사용하여 구성되며, 책의 표지, 제목, 그리고 작가 이름을 포함한다.

코드에서 AsyncImage는 coil 라이브러리를 사용하여 이미지 URL로부터 책 표지를 비동기적으로 로드하고, Text 컴포저블은 제목과 작가 이름을 표시한다. 사용자가 어떤 Card를 클릭하면, 해당 책의 상세 정보 화면으로 네비게이션한다(onNavigateToDetail 콜백 함수가 호출된다).

2. 책 세부 정보 보기<br>
![image](https://github.com/kumoh19/smartapp_project/assets/104006988/afb3fda0-fc93-485b-8b60-dbcb7d278782)
![image](https://github.com/kumoh19/smartapp_project/assets/104006988/dd1884d3-6e8d-4a9f-9cc1-8fd1ef6ebd19)
![image](https://github.com/kumoh19/smartapp_project/assets/104006988/57b36e0d-5045-4b07-a13b-649f0b2b182b)


BookDetail 컴포저블 함수는 사용자가 선택한 책의 상세 정보를 표시하는 화면을 구현한다. Column 레이아웃을 사용하여 별점(RatingBar), 책 제목, 책 표지(AsyncImage), 저자 이름, 책 설명(Text), 그리고 구글 검색 버튼(Button)을 수직으로 정렬한다.

별점은 RatingBar 함수에 의해 표시되며, 이는 별점의 수에 따라 적절한 수의 별 아이콘을 렌더링한다. 사용자가 '책 검색' 버튼을 클릭하면, Intent를 사용하여 사용자의 기본 웹 브라우저에서 구글 검색을 실행한다.
