-- we don't know how to generate root <with-no-name> (class Root) :(

grant select on performance_schema.* to 'mysql.session'@localhost;

grant trigger on sys.* to 'mysql.sys'@localhost;

grant alter, alter routine, create, create routine, create temporary tables, create user, create view, delete, drop, event, execute, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, trigger, update, grant option on *.* to admin;

grant audit_abort_exempt, firewall_exempt, select, system_user on *.* to 'mysql.infoschema'@localhost;

grant audit_abort_exempt, authentication_policy_admin, backup_admin, clone_admin, connection_admin, firewall_exempt, persist_ro_variables_admin, session_variables_admin, system_user, system_variables_admin on *.* to 'mysql.session'@localhost;

grant audit_abort_exempt, firewall_exempt, system_user on *.* to 'mysql.sys'@localhost;

grant alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_user_id, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, telemetry_log_admin, trigger, update, xa_recover_admin, grant option on *.* to rdsadmin@localhost;

create table alarm
(
    alarm_index int auto_increment
        primary key,
    content     varchar(255) not null,
    reg_date    timestamp    not null,
    read_date   timestamp    null,
    role_type   varchar(100) not null,
    user_index  int          not null
);

create table award
(
    id           int auto_increment
        primary key,
    user_index   int          not null,
    title        varchar(255) not null,
    organization varchar(255) null,
    awar_date    date         null,
    details      text         null,
    award_type   varchar(30)  null
);

create table board
(
    board_index int auto_increment
        primary key,
    user_index  int                                 not null,
    title       varchar(255)                        not null,
    content     text                                not null,
    board_image varchar(255)                        null,
    created_at  timestamp default CURRENT_TIMESTAMP null,
    updated_at  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    status      varchar(20)                         null comment '상태값(삭제여부)'
)
    collate = utf8mb4_unicode_ci;

create table chat_room
(
    room_id  int auto_increment
        primary key,
    title    varchar(255)                        not null,
    reg_date timestamp default CURRENT_TIMESTAMP null
);

create table company
(
    company_index                bigint auto_increment comment '회사 인덱스'
        primary key,
    manager_email                varchar(255)  not null comment '담당자 이메일',
    company_name                 varchar(255)  not null comment '회사 이름',
    company_password             varchar(255)  not null comment '회사 비밀번호',
    company_scale                varchar(30)   not null comment '회사 규모',
    company_opendate             varchar(30)   null comment '개업 일자',
    company_license              varchar(11)   not null comment '사업자 등록번호',
    company_phonenumber          varchar(11)   null comment '회사 전화번호',
    company_address              varchar(255)  not null comment '회사 주소',
    company_category             varchar(30)   not null comment '회사 카테고리',
    company_content              text          not null comment '사업 내용',
    company_welfare              text          null comment '기업 복리후생',
    company_vision               text          null comment '기업 비전',
    company_history              text          null comment '기업 연혁',
    master_name                  varchar(10)   not null comment '대표 이름',
    manager_name                 varchar(10)   not null comment '담당자 이름',
    manager_phonenumber          varchar(11)   not null comment '담당자 번호',
    company_size                 int           not null comment '사원수',
    company_logo                 varchar(255)  null comment '회사 로고',
    company_img                  varchar(255)  null comment '회사 대표 이미지',
    company_homepage             varchar(255)  null comment '회사 주소',
    company_subsidiary           text          null comment '계열사',
    company_financial_statements varchar(255)  null comment '재무재표 ( 파일형태 )',
    opencv_key                   int default 0 not null comment '제안 횟수',
    status                       varchar(10)   not null comment '상태',
    role                         varchar(20)   not null comment 'JWT용 역할'
)
    comment '회사';

create table email_verification_token
(
    id          bigint auto_increment
        primary key,
    token       varchar(255) not null,
    expiry_date varchar(255) not null,
    email       varchar(255) not null,
    constraint email
        unique (email),
    constraint token
        unique (token)
)
    comment '이메일 인증 토큰 테이블';

create table item
(
    item_index int auto_increment
        primary key,
    item_name  varchar(255) null comment '상품명',
    price      int          null comment '가격'
)
    comment '상품';

create table language_certification
(
    id               int auto_increment
        primary key,
    user_index       int          not null,
    title            varchar(255) not null,
    institution      varchar(255) null,
    acquisition_date date         null
);

create table military
(
    id               int auto_increment
        primary key,
    user_index       int                     not null,
    military_status  enum ('군필', '미필', '면제') not null,
    military_type    varchar(255)            null,
    specialty        varchar(255)            null,
    rank_name        varchar(255)            null,
    discharge_reason varchar(255)            null,
    enlistment_date  date                    null,
    discharge_date   date                    null,
    exemption_reason varchar(255)            null
);

create table payment
(
    order_index  int auto_increment comment '주문 인덱스'
        primary key,
    item_index   int          null comment '제품 인덱스',
    user_index   int          null comment '사용자 index',
    order_status varchar(255) null comment '주문 상태',
    order_roles  varchar(50)  not null comment '유저인지 기업인지 나누기위한 컬럼',
    created_at   datetime     not null,
    constraint item_index
        foreign key (item_index) references item (item_index)
)
    comment '주문정보 테이블';

create table recruitment
(
    recruitment_index int auto_increment comment '인덱스'
        primary key,
    company_index     bigint                              not null comment '회사 인덱스',
    recruitment_title varchar(30)                         not null,
    recruitment_type  varchar(30)                         not null comment '채용형태',
    category          varchar(30)                         not null comment '직군',
    skill             varchar(255)                        null comment '기술스택',
    career            varchar(255)                        null comment '자격 조건',
    salary            int                                 null comment '공고에 대한 연봉',
    work_detail       text                                null comment '업무 내용',
    hospitality       text                                null comment '우대사항',
    requirement       text                                null comment '자격요건',
    start_date        varchar(50)                         not null comment '게시 시작일',
    period varchar (30) not null comment '기간',
    status            varchar(10)                         not null comment '상태값(마감여부)',
    view_count        int       default 0                 not null comment '조회수',
    created_at        timestamp default CURRENT_TIMESTAMP null comment '생성 날짜',
    updated_at        timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '수정 날짜',
    constraint fk_company_to_recruitment
        foreign key (company_index) references company (company_index)
)
    comment '공고';

create table advertisement
(
    advertisement_index int auto_increment
        primary key,
    recruitment_index   int          not null comment '공고 인덱스',
    display_time        varchar(255) null,
    start_date          date         null comment '시작 날짜',
    end_date            date         null comment '끝 날짜',
    view_count          int          not null comment '광고 클릭 횟수',
    click_count         int          not null comment '클릭 횟수',
    order_index         int          null comment '결제 연동',
    status              varchar(20)  not null comment '광고 상태',
    constraint advertisement_payment_order_index_fk
        foreign key (order_index) references payment (order_index),
    constraint fk_recruitment_to_advertisement
        foreign key (recruitment_index) references recruitment (recruitment_index)
)
    comment '광고';

create table advertisement_announcement
(
    announcement_index  int auto_increment
        primary key,
    advertisement_index int not null comment '광고 인덱스',
    constraint advertisement_announcement_ibfk_1
        foreign key (advertisement_index) references advertisement (advertisement_index)
);

create index advertisement_index
    on advertisement_announcement (advertisement_index);

create table advertisement_banner
(
    banner_index        int auto_increment
        primary key,
    advertisement_index int          not null comment '광고 인덱스',
    banner_image        varchar(255) null,
    constraint advertisement_banner_ibfk_1
        foreign key (advertisement_index) references advertisement (advertisement_index)
);

create index advertisement_index
    on advertisement_banner (advertisement_index);

create table advertisement_main
(
    main_index          int auto_increment
        primary key,
    advertisement_index int           not null comment '광고 인덱스',
    discount_rate       decimal(5, 2) null comment '할인율',
    main_image          varchar(255)  null,
    constraint advertisement_main_ibfk_1
        foreign key (advertisement_index) references advertisement (advertisement_index)
);

create index advertisement_index
    on advertisement_main (advertisement_index);

create table advertisement_premium
(
    premium_index       int auto_increment
        primary key,
    advertisement_index int          not null comment '광고 인덱스',
    ad_type             varchar(10)  not null comment '광고 타입 (GP, P1, P2, P3)',
    time_slot           varchar(50)  not null comment '노출 시간대',
    premium_image       varchar(255) null,
    constraint advertisement_premium_ibfk_1
        foreign key (advertisement_index) references advertisement (advertisement_index)
);

create index advertisement_index
    on advertisement_premium (advertisement_index);

create table receipt
(
    receipt_index       int auto_increment comment '구매내역인덱스'
        primary key,
    advertisement_index int         not null comment '광고인덱스',
    price               int         not null comment '가격',
    payment_date        varchar(50) not null comment '결제일',
    payment_method      varchar(50) not null comment '결제방법',
    pay_statement       varchar(50) not null comment '결제상태',
    constraint fk_advertisement_to_receipt
        foreign key (advertisement_index) references advertisement (advertisement_index)
)
    comment '구매내역 ( 기업 )';

create table recruitment_freelancer
(
    project_index       bigint auto_increment comment '프로젝트 ID'
        primary key,
    company_index       bigint                              not null comment '회사 인덱스',
    project_title       varchar(100)                        not null comment '프로젝트 제목',
    project_description text                                not null comment '프로젝트 설명',
    expected_duration   varchar(50)                         not null comment '예상 개발 기간',
    expected_cost       int                                 not null comment '예상 비용',
    required_skills     varchar(255)                        not null comment '자격 요건 (필요 기술)',
    preferred_skills    varchar(255)                        null comment '우대 조건',
    work_conditions     varchar(255)                        not null comment '근무 조건',
    view_count          int                                 not null,
    status              varchar(50)                         not null comment '프로젝트 상태',
    period date not null comment '접수 마감일',
    created_at          timestamp default CURRENT_TIMESTAMP null comment '등록 날짜',
    constraint recruitment_freelancer_ibfk_1
        foreign key (company_index) references company (company_index)
            on delete cascade
)
    comment '프리랜서 공고';

create index company_index
    on recruitment_freelancer (company_index);

create table salary
(
    id_index       bigint auto_increment
        primary key,
    position       varchar(100) not null,
    average_salary int          not null,
    min_salary     int          not null,
    max_salary     int          not null,
    company_index  bigint       not null
)
    comment '연봉';

create index fk_salary_to_category
    on salary (company_index);

create table school
(
    school_index int auto_increment
        primary key,
    user_index   int                                 not null,
    type         enum ('고등', '전문', '학사', '석사', '박사') not null,
    school_name  varchar(255)                        not null,
    location     varchar(255)                        null,
    start_date   date                                null,
    end_date     date                                null,
    start_status varchar(255)                        null,
    end_status   varchar(255)                        null,
    department   varchar(255)                        null,
    major        varchar(255)                        null,
    double_major varchar(255)                        null,
    grade        decimal(3, 2)                       null,
    max_grade    decimal(3, 2)                       null
);

create table user
(
    user_index       bigint auto_increment comment '유저 인덱스'
        primary key,
    user_email       varchar(20)          not null comment '유저 이메일',
    user_password    varchar(500)         null comment '유저 비밀번호',
    user_name        varchar(10)          not null comment '유저 이름',
    user_address     varchar(30)          null comment '유저 주소',
    user_phonenumber varchar(200)         null comment '유저 핸드폰',
    user_birthday    varchar(30)          null comment '유저 생년월일',
    freelancer       tinyint(1) default 0 null comment '프리랜서 구분',
    status           varchar(10)          not null comment '상태',
    role             varchar(30)          not null comment '권한',
    github_token     varchar(255)         null comment '깃허브 토큰',
    gender           enum ('남', '여')      null
)
    comment '유저';

create table blog
(
    blog_index int auto_increment comment '블로그 인덱스'
        primary key,
    user_index bigint       not null,
    url        varchar(255) not null,
    constraint user_index
        foreign key (user_index) references user (user_index)
);

create table career
(
    career_index      int auto_increment comment '인덱스 번호'
        primary key,
    career_start      varchar(10)                  not null comment '시작경력',
    career_end        varchar(30)                  null comment '끝경력',
    company_name      varchar(30)                  not null comment '회사이름',
    career_position   varchar(30)                  not null comment '직급',
    user_index        bigint                       not null comment '유저 인덱스',
    career_department varchar(100) charset utf8mb3 not null comment '부서',
    career_work       varchar(200) charset utf8mb3 not null comment '담당업무',
    constraint fk_user_to_career
        foreign key (user_index) references user (user_index)
)
    comment '경력';

create table certificate
(
    certificate_index int auto_increment comment '블로그 인덱스'
        primary key,
    user_index        bigint       not null,
    title             varchar(255) not null,
    issue_status      varchar(10)  not null,
    issue_center      varchar(255) not null,
    certificate_type  varchar(30)  null,
    constraint fk_user_index
        foreign key (user_index) references user (user_index)
);

create table chat
(
    chat_id           int auto_increment
        primary key,
    room_id           int                                 not null,
    sender_user_index bigint                              not null,
    message           varchar(255)                        null,
    reg_date          timestamp default CURRENT_TIMESTAMP null,
    constraint chat_ibfk_1
        foreign key (room_id) references chat_room (room_id),
    constraint chat_ibfk_2
        foreign key (sender_user_index) references user (user_index)
);

create index room_id
    on chat (room_id);

create index sender_user_index
    on chat (sender_user_index);

create table chat_room_members
(
    room_id    int       not null,
    user_index bigint    not null,
    reg_date   timestamp not null,
    mod_date   timestamp null,
    primary key (room_id, user_index),
    constraint chat_room_members_ibfk_1
        foreign key (room_id) references chat_room (room_id),
    constraint chat_room_members_ibfk_2
        foreign key (user_index) references user (user_index)
);

create index user_index
    on chat_room_members (user_index);

create table company_bookmark
(
    id            int auto_increment comment '고유 인덱스'
        primary key,
    company_index bigint      not null comment '외래키로 받아온 기업인덱스',
    user_index    bigint      not null comment '외래키로 받아온 개인인덱스',
    status        varchar(10) not null,
    constraint company_bookmark_user_user_index_fk
        foreign key (user_index) references user (user_index),
    constraint fk_company_to_company_bookmark
        foreign key (company_index) references company (company_index)
);

create table company_review
(
    company_review_index int auto_increment comment '기업리뷰인덱스',
    user_index           bigint        not null comment '유저 인덱스',
    company_index        bigint        not null comment '회사 인덱스',
    advancement          int default 3 not null comment '승진기회',
    benefit              int default 3 not null comment '복지및급여',
    work_life            int default 3 not null comment '워라벨',
    company_culture      int default 3 not null comment '사내문화',
    management           int default 3 not null comment '경영진',
    content              text          null comment '기업리뷰내용',
    company_review_title varchar(30)   not null comment '기업리뷰타이틀',
    company_review_date  varchar(30)   not null comment '기업리뷰작성날짜',
    status               varchar(10)   not null comment '상태',
    primary key (company_review_index, user_index, company_index),
    constraint fk_company_to_company_review
        foreign key (company_index) references company (company_index),
    constraint fk_user_to_company_review
        foreign key (user_index) references user (user_index)
)
    comment '기업리뷰';

create table cv
(
    cv_index   int auto_increment comment '인덱스번호'
        primary key,
    main_cv    tinyint(1) default 0 null comment '메인이력서 구분',
    skill      varchar(500)         null comment '기술스택',
    user_index bigint               not null comment '유저 인덱스',
    title      varchar(255)         not null comment '상태',
    status     varchar(100)         not null,
    constraint fk_user_to_cv
        foreign key (user_index) references user (user_index)
)
    comment '이력서';

create table apply_cv
(
    apply_cv_index    int auto_increment comment '인덱스'
        primary key,
    cv_index          int          not null comment '이력서인덱스',
    recruitment_index int          not null comment '공고인덱스',
    application_type  varchar(50)  not null comment '지원타입',
    status            varchar(50)  not null comment '조회여부',
    apply_cv_date     varchar(255) not null comment '지원일자',
    constraint apply_cv_cv_cv_index_fk
        foreign key (cv_index) references cv (cv_index)
)
    comment '지원한공고';

create index apply_cv_cv_index_index
    on apply_cv (cv_index);

create index apply_cv_recruitment_index_index
    on apply_cv (recruitment_index);

create table cv_award
(
    id       int not null,
    cv_index int not null,
    primary key (id, cv_index),
    constraint cv_award_ibfk_1
        foreign key (id) references award (id)
            on update cascade on delete cascade,
    constraint cv_award_ibfk_2
        foreign key (cv_index) references cv (cv_index)
            on update cascade on delete cascade
);

create index cv_index
    on cv_award (cv_index);

create table cv_blog
(
    blog_index int not null,
    cv_index   int not null,
    primary key (blog_index, cv_index),
    constraint cv_blog_ibfk_1
        foreign key (blog_index) references blog (blog_index)
            on update cascade on delete cascade,
    constraint cv_blog_ibfk_2
        foreign key (cv_index) references cv (cv_index)
            on update cascade on delete cascade
);

create index cv_index
    on cv_blog (cv_index);

create table cv_career
(
    career_index int not null,
    cv_index     int not null,
    primary key (career_index, cv_index),
    constraint cv_career_ibfk_1
        foreign key (career_index) references career (career_index)
            on update cascade on delete cascade,
    constraint cv_career_ibfk_2
        foreign key (cv_index) references cv (cv_index)
            on update cascade on delete cascade
);

create index cv_index
    on cv_career (cv_index);

create table cv_certificate
(
    certificate_index int not null,
    cv_index          int not null,
    primary key (certificate_index, cv_index),
    constraint cv_certificate_ibfk_1
        foreign key (certificate_index) references certificate (certificate_index)
            on update cascade on delete cascade,
    constraint cv_certificate_ibfk_2
        foreign key (cv_index) references cv (cv_index)
            on update cascade on delete cascade
);

create index cv_index
    on cv_certificate (cv_index);

create table cv_open
(
    cv_index      int auto_increment comment '인덱스번호',
    company_index bigint not null comment '회사 인덱스',
    primary key (cv_index, company_index),
    constraint fk_company_to_cv_open
        foreign key (company_index) references company (company_index),
    constraint fk_cv_to_cv_open
        foreign key (cv_index) references cv (cv_index)
)
    comment '이력서열람';

create table cv_school
(
    school_index int not null,
    cv_index     int not null,
    primary key (school_index, cv_index),
    constraint cv_school_ibfk_1
        foreign key (school_index) references school (school_index)
            on update cascade on delete cascade,
    constraint cv_school_ibfk_2
        foreign key (cv_index) references cv (cv_index)
            on update cascade on delete cascade
);

create index cv_index
    on cv_school (cv_index);

create table github
(
    github_index int auto_increment
        primary key,
    github_url   varchar(255) not null,
    cv_index     int          not null,
    constraint github_ibfk_1
        foreign key (cv_index) references cv (cv_index)
            on update cascade on delete cascade
);

create index cv_index
    on github (cv_index);

create table interview_review
(
    interview_review_index   int auto_increment comment '면접리뷰인덱스',
    company_index            bigint        not null comment '회사 인덱스',
    user_index               bigint        not null comment '유저 인덱스',
    ambience                 int default 3 not null comment '면접분위기',
    difficulty               int default 3 not null comment '질문난이도',
    interview_review_title   varchar(30)   not null comment '면접리뷰 제목',
    interview_review_date    varchar(30)   not null comment '면접리뷰 작성 날짜',
    interview_review_content varchar(150)  null comment '면접리뷰 내용',
    status                   varchar(10)   not null comment '상태',
    primary key (interview_review_index, company_index, user_index),
    constraint fk_company_to_interview_review
        foreign key (company_index) references company (company_index),
    constraint fk_user_to_interview_review
        foreign key (user_index) references user (user_index)
)
    comment '면접리뷰';

create table letter
(
    answer1    text                 null comment '자소서내용',
    user_index bigint               not null comment '유저 인덱스'
        primary key,
    status     varchar(10)          not null comment '상태',
    answer2    text charset utf8mb3 null,
    answer3    text charset utf8mb3 null,
    constraint fk_user_to_letter
        foreign key (user_index) references user (user_index)
)
    comment '자소서';

create table portfolio
(
    portfolio_index int auto_increment comment '인덱스번호',
    user_index      bigint      not null comment '유저 인덱스',
    portfolio_type  varchar(10) not null comment '포트폴리오 타입',
    status          varchar(10) not null comment '상태',
    primary key (portfolio_index, user_index),
    constraint fk_user_to_portfolio
        foreign key (user_index) references user (user_index)
)
    comment '포트폴리오';

create table recruitment_bookmark
(
    bookmark_index    int auto_increment comment '공고 스크랩인덱스'
        primary key,
    recruitment_index int         not null comment '공고 인덱스',
    user_index        bigint      not null comment '유저 인덱스',
    status            varchar(10) not null comment '상태',
    constraint fk_recruitment_to_recruitment_bookmark
        foreign key (recruitment_index) references recruitment (recruitment_index),
    constraint fk_user_to_recruitment_bookmark
        foreign key (user_index) references user (user_index)
)
    comment '공고 스크랩';

create table recruitment_visit
(
    visit_index       int auto_increment comment '고유 id'
        primary key,
    user_index        bigint      not null comment '유저 id',
    recruitment_index int         not null,
    visit_date        varchar(50) not null comment '방문 날짜',
    constraint recruitment_visit_user_user_index_fk
        foreign key (user_index) references user (user_index)
)
    comment '방문한 공고';

create table user_membership
(
    user_index bigint      not null comment '유저 인덱스'
        primary key,
    start_date varchar(30) not null comment '맴버쉽 시작날짜',
    period varchar (30) not null comment '기간',
    constraint fk_user_to_user_membership
        foreign key (user_index) references user (user_index)
)
    comment '유저맴버쉽';

