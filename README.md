# 09-Back
Mashup 09 backend

# DataBase

[img here](https://github.com/mash-up-kr/09-Back/tree/master/ERDiagram_img)

# Architecture
- main
    - controller
        - user
            - UserController
        - item
        - setting  
    - domain
        - user
            - User
        - item
        - setting 
   - service
       - user
           - UserService
       - item
       - setting
   - persistence
       - user
           - UserRepository
       - item
       - setting
       
# REST

- Setting 
    - GET /category 
        - re ) [category] 
    - POST /category
    - Body : category
        - re ) status(200,40x)
    - DELETE /category
    - Body : category
        - re ) status(200,40x)


- USER 
    - GET /user?id=1234
        - re ) id가 1234인 User Entity
    - POST /user
        - Body : PK 을 제외한 값
        - 해당 값을 가진 user 생성 
        - re ) status ( 200 , 40x ) 
    - PATCH /user?user_id=1234
        - body : 변경될 user 의 정보들 ex) current_account 등등...
        - re ) status ( 200 , 40x )
- ITEM 
    - GET /item
    - re )  [ item ] limit 5 ( 최대 5개 ? )
    - GET /item?item_id=1234
        - 없는 부분들은 null
        - ios 단에서 hide show
        - re ) item + tab 1~5

    - POST /item
        - Body : ITEM < item_id 를 제외한 값 ( FK : user_id )  > + TAB1 < item_id 를 제외한 값 > 
            - 해당 값을 가진 item , tab1 생성 
            - listOfParticipantForUser< owner : true ( FK : item_id, user_id )  > 생성
            - listOfparticipantForItem< likeOrParticipant : 1  ( FK : item_id, user_id )  > 생성
            - tab 2~5 더미 데이터 생성 ( null )
            - re ) status ( 200, 40x )
    - POST /item?item_id=1234&tab=2
        - Body : TAB2< tab2_id를 제외한 값 ( FK : item_id ) > + TAB2_OPTION< option_id 제외한 값 ( FK : tab2_id ) >
        - update dummy
        - current step 변경
        - re ) status ( 200, 40x )
    
    - PATCH /item?id=1234?tab=2
        - tab 2 내용 수정
        - Body : ( FK : item_id )
        - update dummy
        - re ) status ( 200, 40x )

- GET /item/participant_list?item_id=1234&user_id=6789
    - item_id 의 owner 의 아이디가 user_id
    - re ) 해당 테이블 인스턴스
    
- POST /item/participant/1
    - Body : item_id, user_id ( 참가 )
    - re ) status ( 200, 40x )
    -> like 로 변경 

- POST /item?item_id=1234/participant
    - Body : listOfparticipantForItem 정보
    - listOfparticipantForItem< likeOrParticipant : 2  > 수정
    - listOfParticipantForUser< owner : false ( FK : item_id, user_id )  > 생성
    - permission : 0 ( default ) 
    - re ) status ( 200, 40x )

- POST /item/permission
    - Body : user_id , item_id , permission 
    -  permission 수정    
    - status ( 200, 40x )
