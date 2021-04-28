
SET SERVEROUTPUT ON;

DROP SEQUENCE issue_id_seq;
DROP SEQUENCE issueUpdate_id_seq;
DROP SEQUENCE user_id_seq;
DROP SEQUENCE role_id_seq;
DROP SEQUENCE department_id_seq;

DROP TABLE department CASCADE CONSTRAINTS;
DROP TABLE role CASCADE CONSTRAINTS;
DROP TABLE cms_user CASCADE CONSTRAINTS;
DROP TABLE user_role CASCADE CONSTRAINTS;

CREATE SEQUENCE issue_id_seq
  START WITH 1
  INCREMENT BY 1 
  CACHE 10;

CREATE SEQUENCE issueUpdate_id_seq
  START WITH 1
  INCREMENT BY 1 
  CACHE 10;

CREATE SEQUENCE user_id_seq
  START WITH 1
  INCREMENT BY 1 
  CACHE 10;  

CREATE SEQUENCE role_id_seq
  START WITH 1
  INCREMENT BY 1 
  CACHE 10; 

CREATE SEQUENCE department_id_seq
  START WITH 1
  INCREMENT BY 1 
  CACHE 10; 

CREATE TABLE department (
  department_id NUMBER(4) NOT NULL,
  department_name VARCHAR2(30) NOT NULL,
  CONSTRAINT dept_pk PRIMARY KEY(department_id),
  CONSTRAINT dept_name_uk UNIQUE(department_name)
);

CREATE TABLE role (
  role_id NUMBER(4) NOT NULL,
  role_name VARCHAR2(30) NOT NULL,
  CONSTRAINT role_pk PRIMARY KEY(role_id),
  CONSTRAINT role_name_uk UNIQUE(role_name)
);

CREATE TABLE cms_user (
  user_id NUMBER(4) NOT NULL,
  fk_department_id NUMBER(4),
  email VARCHAR2(30),
  first_name VARCHAR2(30),
  last_name VARCHAR2(30),
  username VARCHAR2(30) NOT NULL,
  password VARCHAR2(30) NOT NULL,
  CONSTRAINT cms_user_pk PRIMARY KEY(user_id),
  CONSTRAINT username_uk UNIQUE(username),
  CONSTRAINT email_uk UNIQUE(email),
  CONSTRAINT cms_user_dept_fk FOREIGN KEY(fk_department_id) REFERENCES department(department_id)
);

CREATE TABLE issue (
  issue_id NUMBER(4) NOT NULL,
  title VARCHAR2(30) NOT NULL,
  issue_desc VARCHAR2(300) NOT NULL,
  admin_comment VARCHAR2(300),
  assigned_to_dept_id NUMBER(4),
  author NUMBER(4) NOT NULL,
  status VARCHAR(30),
  priority VARCHAR(30),
  date_submitted DATE,
  date_resolved DATE,
  CONSTRAINT issue_pk PRIMARY KEY(issue_id)
);

CREATE TABLE issue_update (
  issueUpdate_id NUMBER(4) NOT NULL,
  submitted_by NUMBER(4) NOT NULL,
  update_date DATE,
  update_details VARCHAR(300) NOT NULL,
  fk_Issue_ID NUMBER(4) NOT NULL,
  CONSTRAINT issue_update_pk PRIMARY KEY(issueUpdate_id),
  CONSTRAINT fk_Issue_ID_fk FOREIGN KEY(fk_Issue_ID) REFERENCES issue(issue_id)
);

CREATE TABLE user_role (
  user_id NUMBER(4) NOT NULL,
  role_id NUMBER(4) NOT NULL,
  CONSTRAINT u_r_user_id_fk FOREIGN KEY(user_id) REFERENCES cms_user(user_id),
  CONSTRAINT u_r_role_id_fk FOREIGN KEY(role_id) REFERENCES role(role_id)
);

CREATE OR REPLACE PROCEDURE insert_user (
  p_email IN cms_user.email%TYPE,
  p_first_name IN cms_user.first_name%TYPE,
  p_last_name IN cms_user.last_name%TYPE,
  p_username IN cms_user.username%TYPE,
  p_password IN cms_user.password%TYPE,
  p_dept_name IN department.department_name%TYPE,
  p_role_ids_sum NUMBER)
IS
  v_user_id NUMBER(4);
  v_dept_id cms_user.fk_department_id%TYPE;
BEGIN
  SELECT user_id_seq.nextval
  INTO v_user_id
  FROM dual;
  
  IF p_dept_name = 'none' THEN
    INSERT INTO cms_user (
      user_id, email, first_name, last_name, username, password)
    VALUES (
      v_user_id, p_email, p_first_name, p_last_name, p_username, p_password);
  ELSE
    SELECT department_id
    INTO v_dept_id
    FROM department
    WHERE department_name = p_dept_name;
  
    INSERT INTO cms_user VALUES (
      v_user_id, v_dept_id, p_email, p_first_name, p_last_name, p_username, p_password);
  END IF;
  
  INSERT INTO user_role VALUES (v_user_id, 1);
  
  IF p_role_ids_sum IN (4, 9) THEN
    INSERT INTO user_role VALUES (v_user_id, 3);
  END IF;
  IF p_role_ids_sum IN (6, 9) THEN
    INSERT INTO user_role VALUES (v_user_id, 5);
  END IF;
END insert_user;
/
SHOW ERRORS PROCEDURE insert_user;

INSERT INTO department VALUES (department_id_seq.nextval, 'Unassigned');
INSERT INTO department VALUES (department_id_seq.nextval, 'Front');
INSERT INTO department VALUES (department_id_seq.nextval, 'Middle');
INSERT INTO department VALUES (department_id_seq.nextval, 'Back');

INSERT INTO role VALUES (1, 'User');
INSERT INTO role VALUES (3, 'Department Admin');
INSERT INTO role VALUES (5, 'General Admin');

EXEC insert_user('john.doe@fdmgroup.com', 'John', 'Doe', 'jdoe', 'qwerty', 'Unassigned', 1);
EXEC insert_user('will.smith@fdmgroup.com', 'Will', 'Smith', 'wsmith', 'fresh', 'Front', 4);
EXEC insert_user('smithers@fdmgroup.com', 'Mr', 'Smithers', 'mrsmithers', 'yesman', 'Unassigned', 6);
EXEC insert_user('monty.burns@fdmgroup.com', 'Monty', 'Burns', 'mburns', 'smithers', 'Back', 9);
 
COMMIT;

