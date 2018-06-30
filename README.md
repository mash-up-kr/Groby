# 09-Back
Mashup 09 backend

# DataBase
## user
- user_num(PK)
- user_id
- user_pw 
- user_name
- user_email
- account_num
- account_bank
- account_holder

## listOfParticipantForUser (개인참여리스트)
- user_id(PK, FK)
- item_id(PK, FK)
- owner
    - T : 총대
    - F : 참여자

## setting
- category
    - food
    - clothes
    - entertainments

## item
- item_id(PK)
- user_id(FK)
- category
- title
- reg_date
- like_num  
- location
- current_step


## tab1
- item_id(PK, FK)
- reg_Date(FK)
- img_path
- amount
- contents
- end_date_1

## tab2
- item_id(PK, FK)
- tab2_id(PK)
- reg_date
- account_name
- account_number
- account_bank 
- end_date_2

## tab2_option
- tab2_id(PK, FK)
- option_id(PK)
- option_name
- option_val 

## tab4 
- item_id(PK, FK)
- contents
- receipt_img_path 

## tab5
- item_id(PK, FK)
- contents
- location

## listOfParticipantForItem
- item_id(PK, FK)
- likeOrParticipant 
    - 1 : 수요조사
    - 2 : 참여자
- userid(PK , FK)
- userpermission
- account_num
- account_bank
- account_holder
- amount
- price

# REST

—— Setting 

GET /category

-> @[category] 

POST /category

-> Body : category

-> @status(200,40x)

DELETE /category

-> Body : category

-> @status(200,40x)


—— USER 

GET /user?id=1234

-> id가 1234인 @user

POST /user

<- Body : user_num 을 제외한 값

->  해당 값을 가진 user 생성

-> @status ( 200 , 40x ) 

—— ITEM 

GET /item

-> @ [ item ] limit 5

GET /item?item_id=1234

-> 없는 부분들은 null

-> @ item + tab 1~5

POST /item

<- Body : ITEM< item_id 를 제외한 값 ( FK : user_id )  > + TAB1< item_id 를 제외한 값 > 

-> 해당 값을 가진 item , tab1 생성 

-> listOfParticipantForUser< owner : true ( FK : item_id, user_id )  > 생성

-> listOfparticipantForItem< likeOrParticipant : 1  ( FK : item_id, user_id )  > 생성

-> tab 2~5 더미 데이터 생성 

-> @status ( 200, 40x )

POST /item/tab/2

<- Body : TAB2< tab2_id를 제외한 값 ( FK : item_id ) > + TAB2_OPTION< option_id 제외한 값 ( FK : tab2_id ) >

<- update dummy

<- ITEM -> current_step update : 2

-> @status ( 200, 40x )

POST /item/tab/3~5

<- Body : ( FK : item_id )

<- update dummy

<- ITEM -> current_step update : 3~5

-> @status ( 200, 40x )

GET /item/participant_list?item_id=1234&user_id=6789

-> @해당 테이블 인스턴스

POST /item/participant/1

<- Body : item_id, user_id

-> @status ( 200, 40x )

POST /item/participant/2

<- Body : listOfparticipantForItem 정보

-> listOfparticipantForItem< likeOrParticipant : 2  > 수정

-> listOfParticipantForUser< owner : false ( FK : item_id, user_id )  > 생성

-> permission : 0 ( default ) 

-> @status ( 200, 40x )

POST /item/permission

<- Body : user_id , item_id , permission 

->  permission 수정

-> @status ( 200, 40x )
