# 09-Back
Mashup 09 backend

# user
- user_num(PK)
- user_id
- user_pw 
- user_name
- user_email
- account_num
- account_bank
- account_holder
<!--- list_of_Owner ( 총대 한 목록)-->
<!--- list_of_Part ( 참여 한 목록 )-->

# listOfParticipantForUser (개인참여리스트)
- user_id(PK, FK)
- item_id(PK, FK)
- owner
    - T : 총대
    - F : 참여자

# setting
- category
    - food
    - clothes
    - entertainments

# item
- item_id(PK)
- user_id(FK)
- category
- title
- reg_date
- like_num  
- location


# tab1
- item_id(PK, FK)
- reg_Date(FK)
- img_path`( !ios )`
- amount
- contents
- end_date_1

# tab2
- item_id(PK, FK)
- tab2_id(PK)
- reg_date
- account_name
- account_number
- account_bank 
- end_date_2

# tab2_option
- tab2_id(PK, FK)
- option_id(PK)
- option_name
- option_val 

# tab4 
- item_id(PK, FK)
- contents
- receipt_img_path `(!ios)`

# tab5
- item_id(PK, FK)
- contents
- location

# listOfParticipantForItem
- item_id(PK, FK)
- step_num 
    - 1 : 수요조사
    - 2 : 참여자
- userid(PK , FK)
- userpermission
- account_num
- account_bank
- account_holder
- amount
- price

