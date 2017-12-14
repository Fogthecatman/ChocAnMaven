CREATE TABLE serv_tbl (
    serv_id		int			Not Null,
    serv_name	VARCHAR(50)	Not Null,
	serv_fee	int			Not Null,
	serv_desc	VARCHAR(200),
	PRIMARY KEY(serv_id)
);

CREATE TABLE man_tbl (
    man_id			int			Not Null,
    man_name	VARCHAR(25)	Not Null,
	PRIMARY KEY(man_id)
);

CREATE TABLE opr_tbl (
    opr_id			int			Not Null,
    opr_name	VARCHAR(25)	Not Null,
	PRIMARY KEY(opr_id)
);

CREATE TABLE mem_tbl (
    mem_id			int			Not Null,
    mem_name	VARCHAR(25)	Not Null,
	mem_addr		VARCHAR(30)	Not Null,
	mem_city		VARCHAR(15)		Not Null,
	mem_state		VARCHAR(2)		Not Null,
	mem_zip			int				Not Null,
	acc_err_flg		int,
	PRIMARY KEY(mem_id)
);

CREATE TABLE prov_tbl (
    prov_id		int				Not Null,
    prov_name	VARCHAR(30)		Not Null,
    prov_addr	VARCHAR(30)	    Not Null,
	prov_city	VARCHAR(15)		Not Null,
	prov_state	VARCHAR(2)		Not Null,
	prov_zip	int				Not Null,
	PRIMARY KEY(prov_id)
);

CREATE TABLE serv_his_tbl (
    prov_id		int			    Not Null,
    mem_id		int			    Not Null,
    serv_id		int			    Not Null,
	serv_dte	varchar(40)		Not Null,
	tim_stmp	DATETIME	    Not Null DEFAULT(GETDATE()),
	serv_fee	int			    Not Null,
    serv_com    varchar(101)    Not Null,
	PRIMARY KEY(prov_id, mem_id, serv_id, serv_dte)
);
