


SELECT *FROM MEMBER;
commit;

SELECT *FROM ACCOUNT;
COMMIT;

SELECT *FROM TRANSACTION;
COMMIT;


DROP TABLE TRANSACTION;
COMMIT;

DROP TABLE ACCOUNT;
COMMIT;
DROP TABLE MEMBER;
COMMIT;

--------------------------------------
CREATE TABLE MEMBER(

	ID INT PRIMARY KEY,
	NAME VARCHAR(20) NOT NULL UNIQUE,
	MEMBERNO VARCHAR(20) NOT NULL,
	GENDER VARCHAR(3) CHECK(GENDER IN ('M','F')) NOT NULL,
	ADDRESS VARCHAR(40)NOT NULL,
	PHONENUM VARCHAR(15)NOT NULL UNIQUE,
	EMAIL VARCHAR(30),
	BIRTH DATE NOT NULL


);
COMMIT;

CREATE TABLE ACCOUNT(

    ID INT PRIMARY KEY,
    MEMBER_ID INT,
    ACCOUNT_NUMBER VARCHAR(30) UNIQUE NOT NULL,
    ACCOUNT_PASSWORD VARCHAR(30) NOT NULL,
    ACCOUNT_TYPE VARCHAR(10) CHECK(ACCOUNT_TYPE IN ('저축','투자','일반')) NOT NULL,
    OPENING_DATE DATE DEFAULT CURRENT_DATE NOT NULL,
    BALANCE INT DEFAULT 0 NOT NULL CHECK(BALANCE>=0),
    CONSTRAINT MEMBER_FK FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER(ID)
);
COMMIT;

CREATE TABLE TRANSACTION(

    ID INT PRIMARY KEY,
    ACCOUNT_ID INT,
    TRANSACTION_TYPE VARCHAR(10) CHECK (TRANSACTION_TYPE IN ('입금','출금','이체')) NOT NULL,
    AMOUNT VARCHAR(30), -- 요청 거래 금액
    COUNTERPARTY_ACCOUNT_NUM VARCHAR(30),
    BALANCE INT DEFAULT 0 NOT NULL CHECK(BALANCE>=0), -- 요청후 거래 금액
  
    
    CONSTRAINT ACCOUNT_FK FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT(ID) ON DELETE CASCADE

);

--- 거래요청 테이블------
CREATE TABLE TRANSACTION_REQUEST(

   AMOUNT INT NOT NULL, --거래 금액
   ACCOUNT_ID INT NOT NULL,--거래계좌ID,
   ACCOUNT_NUM VARCHAR(30),--거래 계좌번호
   
   COUNTERPARTY_ACCOUNT_ID  INT, --이체시 상대방 계좌ID
   COUNTERPARTY_ACCOUNT_NUM VARCHAR(30),--상대방계좌번호
   
   transaction_KIND varchar(15) check(transaction_KIND in ('입금','출금','이체')) NOT NULL,--거래유형
   
   ACCOUNT_BALANCE INT DEFAULT 0 NOT NULL CHECK(ACCOUNT_BALANCE>=0),-- 거래계좌 총 금액 (거래후 시점 파악하기 위함)
   ACCOUNTCOUNTER_BALANCE INT DEFAULT 0 NOT NULL CHECK(ACCOUNTCOUNTER_BALANCE>=0)-- 이체거래계좌 총 금액 (거래후 시점 파악하기 위함)

 );
 
 
 
----------------------------------------

--alter table transaction add balance INT DEFAULT 0 NOT NULL CHECK(BALANCE>=0);
--commit;
--
--ALTER TABLE transaction DROP  CONSTRAINT ACCOUNT_FK;
--ALTER TABLE transaction ADD  CONSTRAINT ACCOUNT_FK FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT(ID) ON DELETE CASCADE;
--
--
--ALTER TABLE TRANSACTION_REQUEST ADD ACCOUNT_BALANCE INT DEFAULT 0 NOT NULL CHECK(ACCOUNT_BALANCE>=0);
--ALTER TABLE TRANSACTION_REQUEST ADD ACCOUNTCOUNTER_BALANCE INT DEFAULT 0 NOT NULL CHECK(ACCOUNTCOUNTER_BALANCE>=0);
--ALTER TABLE TRANSACTION_REQUEST RENAME COLUMN COUNTERPARTY_ACCOUNT_NUM TO COUNTERPARTY_ACCOUNT_ID;
--ALTER TABLE TRANSACTION_REQUEST ADD  COUNTERPARTY_ACCOUNT_NUM VARCHAR(30);
--
--alter table Account add transaction_request 
--alter table Account  alter column  balance SET not null;
--alter table Account  add constraint balance CHECK(BALANCE>=0);
--
--alter table Account add Amount INT;
--rollback;
--------------------------------------------------------------------------------


 ----- 시퀸스------
CREATE SEQUENCE SEQ_USER
START WITH 1;
COMMIT;

CREATE SEQUENCE SEQ_ACCOUNT
START WITH 100;
COMMIT;

CREATE SEQUENCE SEQ_TRANSACTION
START WITH 200;
COMMIT;
------------------------



----- 트리거 생성-----

CREATE OR REPLACE FUNCTION TRANSACTION_TRIGGER()
RETURNS TRIGGER AS $$
BEGIN
	
	IF NEW.transaction_KIND='입금' THEN
	
	    UPDATE ACCOUNT SET 
				   BALANCE=BALANCE+(NEW.AMOUNT)
				WHERE ID=NEW.ACCOUNT_ID;
				
		 		
	     INSERT INTO TRANSACTION
	        VALUES(nextval('SEQ_TRANSACTION'),
				 NEW.ACCOUNT_ID,
				 NEW.transaction_KIND,
				 '+'||CAST(NEW.AMOUNT AS TEXT),
				 NEW.COUNTERPARTY_ACCOUNT_NUM,
				 NEW.ACCOUNT_BALANCE+NEW.AMOUNT
				);
		  		
    
	END IF;
	
    IF NEW.transaction_KIND='출금' THEN
	
	    UPDATE ACCOUNT SET 
				   BALANCE=BALANCE-(NEW.AMOUNT)
               WHERE ID=NEW.ACCOUNT_ID;
          
               
         INSERT INTO TRANSACTION
	        VALUES(nextval('SEQ_TRANSACTION'),
				 NEW.ACCOUNT_ID,
				 NEW.transaction_KIND ,
				 '-'||CAST(NEW.AMOUNT AS TEXT),
				 NEW.COUNTERPARTY_ACCOUNT_NUM,
				 NEW.ACCOUNT_BALANCE-NEW.AMOUNT
				);        
               
    
	END IF;
	
	
	IF NEW.transaction_KIND='이체' THEN
	
	    UPDATE ACCOUNT SET 
				   BALANCE=BALANCE-(NEW.AMOUNT)
               WHERE ID=NEW.ACCOUNT_ID;
       
               
        UPDATE ACCOUNT SET 
				   BALANCE=BALANCE+(NEW.AMOUNT)
               WHERE ID=NEW.COUNTERPARTY_ACCOUNT_ID;
               
        	           
	  INSERT INTO TRANSACTION
	     VALUES(nextval('SEQ_TRANSACTION'),
				 NEW.ACCOUNT_ID,
				 NEW.transaction_KIND,
				 '-'||CAST(NEW.AMOUNT AS TEXT),
				 NEW.COUNTERPARTY_ACCOUNT_NUM,
				 NEW.ACCOUNT_BALANCE-NEW.AMOUNT
				); 
               
      INSERT INTO TRANSACTION
	     VALUES(nextval('SEQ_TRANSACTION'),
				 NEW.COUNTERPARTY_ACCOUNT_ID,
				 NEW.transaction_KIND,
				 '+'||CAST(NEW.AMOUNT AS TEXT),
				 NEW.ACCOUNT_NUM,
				 NEW.ACCOUNTCOUNTER_BALANCE+NEW.AMOUNT
			 );        
    
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE plpgsql;


CREATE TRIGGER TRANSACTION
AFTER INSERT ON TRANSACTION_REQUEST
FOR EACH ROW EXECUTE FUNCTION TRANSACTION_TRIGGER();
COMMIT;

--DROP TRIGGER TRAN ON ACCOUNT;
--DROP TRIGGER TRANSACTION ON  transaction_request;
--DROP FUNCTION TRANSACTION_TRIGGER;
COMMIT;





----- 테스트----
insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'admin','admin1234','M','서울','01000000000','admin@admin.com','1998-04-16');
commit;
insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'010101010','1234','일반');
commit;

----------------------------------------

insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'tori','tori','M','경기','01001111111','tori@leli.com','1998-03-16');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'123456789','1234','일반');
commit;
----------------------------------------------------
     
                               
--SELECT  TRANSACTION_TYPE,AMOUNT,coalesce(COUNTERPARTY_ACCOUNT_NUM,'-') AS "이체계좌",T.BALANCE AS "거래후 계좌 잔액" 
--				FROM MEMBER M JOIN ACCOUNT A ON (M.ID=A.MEMBER_ID) 
--			    JOIN TRANSACTION T ON (A.ID=T.ACCOUNT_ID) 
--				WHERE (ACCOUNT_NUMBER='0001000' AND ACCOUNT_PASSWORD='admin1234')AND NAME='0admin';
--				
--				
--SELECT  TRANSACTION_TYPE,AMOUNT,coalesce(COUNTERPARTY_ACCOUNT_NUM,'-') AS "이체계좌",T.BALANCE AS "거래후 계좌 잔액" 
--				FROM MEMBER M JOIN ACCOUNT A ON (M.ID=A.MEMBER_ID) 
--			    JOIN TRANSACTION T ON (A.ID=T.ACCOUNT_ID) 
--				WHERE (ACCOUNT_NUMBER='1111111' AND ACCOUNT_PASSWORD='tori1234')AND NAME='tori';
--COMMIT;


--거래 테스트---
--- 이체----

-----1111111->000010000

insert into TRANSACTION_REQUEST VALUES (10000,121,'0001000',null,NULL,'입금',0,0);
insert into TRANSACTION_REQUEST VALUES (15000,121,'0001000',null,NULL,'입금',10000,0);
insert into TRANSACTION_REQUEST VALUES (5000,121,'0001000',null,NULL,'출금',25000,0);

insert into TRANSACTION_REQUEST VALUES (5000,121,'0001000',122,'1111111','이체',20000,0);
insert into TRANSACTION_REQUEST VALUES (3000,121,'0001000',122,'1111111','이체',15000,5000);

COMMIT;               
ROLLBACK;
--------


---- 데이터 초기화--------
insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'admin','admin1234','M','서울','01012341234','admin@admin.com','1998-04-16');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000101010','1234','일반');
commit;





insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user1','user11234','M','서울','01012341235','user1@account.com','1998-04-17');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000111111','1234','일반');
commit;







insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user2','user21234','M','서울','01023412361','user2@account.com','1998-04-18');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000222222','1234','일반');
commit;





insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user3','user31234','F','경기','01014311234','user3@account.com','1998-04-19');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000333333','1234','일반');
commit;




insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user4','user41234','M','서울','01033331234','user4@account.com','1998-04-20');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000444444','1234','일반');
commit;




insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user5','user51234','M','서울','01055551234','user5@account.com','1998-04-21');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000555555','1234','일반');
commit;





insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user6','user61234','F','서울','01066661234','user6@account.com','1998-04-22');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000666666','1234','일반');
commit;





insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user7','user71234','F','경기','01077771234','user7@account.com','1998-04-23');
commit;

insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000777777','1234','일반');
commit;






insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user8','user81234','F','경기','01088881234','user8@account.com','1998-04-24');
commit;




insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000888888','1234','일반');
commit;





insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'user9','user91234','F','강원','01099991234','user9@account.com','1998-04-26');
commit;




insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'000999999','1234','일반');
commit;

insert into MEMBER 
values(NEXTVAL('SEQ_USER'),'USER10','user101234','F','제주도','01010101234','user10@account.com','1998-04-27');
commit;





insert into account(id,member_id,account_number,account_password,account_type) 
values(nextval('seq_account'),CURRVAL('SEQ_USER'),'1010111111','1234','일반');
commit;






















