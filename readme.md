1. jdbc 설정 파일은 /WEB-INF/classes/info.properties 파일 안에 있습니다.
2. jdbc 설정 라이브러리는 /WEB-INF/lib 안에 있습니다.
3. user로 회원가입 후에, DBMS에서 프로시저를 호출하여 테스트 데이터를 생성할 수 있습니다.
	PROCEDURE `proc_dummyInsert` (IN countNum int, IN userNum int)...
4. 자기가 쓴 글은 글을 읽어도 조회수에 포함되지 않습니다.