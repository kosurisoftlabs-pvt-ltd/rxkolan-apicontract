create table amb_booking_details (
       booking_date DATE not null,
        booking_no varchar(45) not null,
        active INT default 1,
        created_on TIMESTAMP not null,
        created_by bigint,
        updated_on TIMESTAMP not null,
        updated_by bigint,
        booked_by varchar(45),
        contact_person varchar(45),
        customer_cont_num varchar(45),
        from_location varchar(45) not null,
        patient_name varchar(45) not null,
        remarks varchar(45),
        status varchar(45),
        to_location varchar(45) not null,
        ambulance_reg_no varchar(255),
        primary key (booking_date, booking_no));
create table amb_subscriber_detail_history (
       subscriber_id varchar(255) not null,
        active INT default 1,
        created_on TIMESTAMP not null,
        created_by bigint,
        updated_on TIMESTAMP not null,
        updated_by bigint,
        amount_paid varchar(45),
        expiry_date DATE,
        pay_by varchar(45),
        payment_method varchar(45),
        purchase_id varchar(45),
        purchased_date DATE,
        renewal_date DATE,
        ambulance_reg_no varchar(255),
        plan_id varchar(255),
        primary key (subscriber_id)
    );
create table ambulance (
       ambulance_reg_no varchar(255) not null,
        active INT default 1,
        created_on TIMESTAMP not null,
        created_by bigint,
        updated_on TIMESTAMP not null,
        updated_by bigint,
        additional_features varchar(100),
        base_location varchar(300),
        owner_name varchar(45),
        phone_number varchar(50),
        reg_date DATE,
        rto_doc varchar(45),
        rto_reg_location varchar(300),
        state varchar(45),
        user_id varchar(100),
        vehicle_brand varchar(300),
        vehicle_model varchar(300),
        verified INT default 1,
        verified_by varchar(45),
        vin varchar(45),
        primary key (ambulance_reg_no)
    );
create table ambulance_membership_plan (
       plan_id varchar(255) not null,
        active INT default 1,
        created_on TIMESTAMP not null,
        created_by bigint,
        updated_on TIMESTAMP not null,
        updated_by bigint,
        district varchar(45),
        no_of_days bigint,
        state varchar(45),
        primary key (plan_id)
    );
create table ambulance_price (
       price_id varchar(255) not null,
        active INT default 1,
        created_on TIMESTAMP not null,
        created_by bigint,
        updated_on TIMESTAMP not null,
        updated_by bigint,
        contact_number bigint,
        price_per_km decimal(19,2) not null,
        update_src_location varchar(45) not null,
        waiting_charges decimal(19,2),
        primary key (price_id)
    );
alter table amb_booking_details add constraint FKorcq36fdadla7kyywbhf93cst foreign key (ambulance_reg_no)  references ambulance (ambulance_reg_no);
alter table amb_subscriber_detail_history add constraint FK16opfjfbfb5inbsykuuw20u6e  foreign key (ambulance_reg_no) references ambulance (ambulance_reg_no);
alter table amb_subscriber_detail_history  add constraint FK83iplm13ruhqqey9fbwalndku  foreign key (plan_id) references ambulance_membership_plan (plan_id);
create table role (
       id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    );
    create table role_user (
           user_id varchar(45) not null,
            role_id integer not null
        );
 create table user_reg (
       phone_number varchar(45) not null,
        active INT default 1,
        created_on TIMESTAMP not null,
        created_by bigint,
        updated_on TIMESTAMP not null,
        updated_by bigint,
        address varchar(100),
        business_name varchar(100),
        contact_person varchar(100),
        district_location varchar(100),
        email varchar(45) not null,
        email_verified INT default 0,
        enabled INT default 0,
        password varchar(200),
        phone_verified INT default 0,
        service_offer varchar(100),
        speciality varchar(100),
        primary key (phone_number)
    );
alter table role_user
       add constraint FKiqpmjd2qb4rdkej916ymonic6
       foreign key (role_id)
       references role (id);
alter table role_user
       add constraint FK80uojj7jkuw92xbetkbg8ltac
       foreign key (user_id)
       references user_reg (phone_number);