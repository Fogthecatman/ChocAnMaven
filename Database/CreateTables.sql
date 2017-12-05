CREATE TABLE serv_tbl (
    serv_id		int			Not Null,
    serv_name	VARCHAR(50)	Not Null,
	serv_fee	int			Not Null,
	serv_desc	VARCHAR(200),
	PRIMARY KEY(serv_id)
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

CREATE TABLE role_tbl (
    id		int				Not Null,
    role	VARCHAR(30)		Not Null,
	PRIMARY KEY(id)
);

CREATE TABLE serv_his_tbl (
    prov_id		int			Not Null,
    mem_id		int			Not Null,
    serv_id		int			Not Null,
	serv_dte	DATE		  Not Null,
	tim_stmp	TIMESTAMP	Not Null,
	serv_fee	int			  Not Null,
	PRIMARY KEY(prov_id, mem_id, serv_id, serv_dte)
);
