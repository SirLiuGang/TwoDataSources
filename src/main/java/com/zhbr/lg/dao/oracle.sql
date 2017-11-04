-- oracle
-- Create table
create table EXERCISES
(
  GUID VARCHAR2(100),
  SSFL VARCHAR2(100),
  SSWD VARCHAR2(50),
  TXFL VARCHAR2(50),
  TM   VARCHAR2(4000),
  XXA  VARCHAR2(4000),
  XXB  VARCHAR2(4000),
  XXC  VARCHAR2(4000),
  XXD  VARCHAR2(4000),
  DA   VARCHAR2(50),
  JS   VARCHAR2(4000)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
