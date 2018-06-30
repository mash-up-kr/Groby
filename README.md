# 09-Back
Mashup 09 backend


# user
- id (PK)
- pw 
- name
- email
- account_num
- account_bank
- account_holder
- list_of_Owner ( 총대 한 목록)
- list_of_Part ( 참여 한 목록 )

# setting
- categories `(!ios )`
    - food
    - clothes
    - entertainments

# item
- item_id(pk)
- writer(fk)
- category
- title
- reg_Date
- likeNum  
- location


# tab1
- item_id(fk,pk)
- reg_Date(fk)
- img_path`( !ios )`
- amount
- contents
- end_date_1

# tab2
- item_id(pk,fk)
- tab2_id(pk)
- reg_date
- account_name
- account_number
- account_bank 
- end_date_2

# tab2_option
- tab2_id(fk,pk)
- option_id(pk)
- option_name
- option_val 

# tab4 
- item_id(pk,fk)
- contents
- receipt_img_path `(!ios)`

# tab5
- item_id(pk,fk)
- contents
- location

# listOfParticipant
- item_id(pk,fk)
- step_num 
    - 1 : 수요조사
    - 2 : 참여자
- userid(fk , pk)
- userpermission
- account_num
- account_bank
- account_holder
- amount
- price

