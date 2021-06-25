CREATE TABLE usuarios
(
	id  int  AUTO_INCREMENT  NOT NULL ,
	email  varchar(30)  NOT NULL ,
	nome  varchar(10)  NOT NULL ,
	senha  varchar(30)  NOT NULL ,
	nivel int null,
	isdelete  integer  not NULL ,
	user_delete  integer  NULL ,
	dt_delete  datetime  NULL,
   primary key(id));
   
      
CREATE TABLE clientes
(
	id  int  AUTO_INCREMENT  NOT NULL ,
	nome_completo  varchar(60)  NOT NULL ,
	cpf  varchar(15)  NOT NULL ,
	rg  varchar(15)   NULL ,
	celular  varchar(11)  NULL ,
	telefone  varchar(11)  NULL ,
	dt_nascimento  datetime  NULL ,
	isdelete  integer  NULL ,
	dt_delete  datetime  NULL ,
	user_delete  integer  NULL ,
	genero  varchar(1)  NULL ,
	primary key(id));


CREATE TABLE enderecos
(
	id  int  AUTO_INCREMENT  NOT NULL ,
	Cep  varchar(10)  NOT NULL ,
	logradouro  varchar(60)  NOT NULL ,
	Estado  varchar(20)  NOT NULL ,
	Cidade  varchar(20)  NOT NULL ,
	Bairro  varchar(20)  NOT NULL ,
	Complemento  varchar(15)  NOT NULL ,
	Local_Descricao  varchar(15)  NOT NULL ,
	isdelete  integer  NULL ,
	cliente_id  int NULL ,
	user_delete  integer  NULL ,
	dt_delete  datetime  NULL ,
	principal  integer  NULL ,
	numero  integer  NULL ,
	primary key(id),
  	CONSTRAINT  PK_enderecos 
	   FOREIGN KEY (cliente_id)
	  REFERENCES clientes(id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	
CREATE TABLE Produtos
(
`id` INT(11) NOT NULL AUTO_INCREMENT,
	Descricao  varchar(80)  NULL ,
	Custo  decimal(14,2)  NULL ,
	Preco  decimal(14,2)  NULL ,
	isdelete  integer not NULL ,
	dt_delete  datetime  NULL ,
	user_delete  integer  NULL ,
	primary key(id));

CREATE TABLE IF NOT EXISTS `shop`.`pedidos` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `dt_Pedido` DATE NULL DEFAULT NULL,
  `dt_entrega` DATE NULL DEFAULT NULL,
  `status_pedido` VARCHAR(10) NULL DEFAULT NULL,
  `cliente_id` INT(11) NOT NULL,
  `forma_pagamento` INT(11) NOT NULL,
  `vl_pedido` DOUBLE NULL DEFAULT NULL,
  `isDelete` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pedidos_clientes_idx` (`cliente_id` ASC),
  CONSTRAINT `fk_pedidos_clientes`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `clientes` (`id`)
     ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `projeto_pi`.`itens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shop`.`pitens` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `Quantidade` INT(11) NULL DEFAULT NULL,
  `Preco` DOUBLE NULL DEFAULT NULL,
  `desconto_valor` DOUBLE NULL DEFAULT NULL,
  `desconto_percentual` DOUBLE NULL DEFAULT NULL,
  `produto_id` INT(11) NOT NULL,
  `pedido_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_itens_produtos1_idx` (`produto_id` ASC) ,
  INDEX `fk_itens_pedidos1_idx` (`pedido_id` ASC) ,
  CONSTRAINT `fk_itens_produtos1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `produtos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_itens_pedidos1`
    FOREIGN KEY (`pedido_id`)
    REFERENCES `pedidos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;






   