package com.kstarrain.provider.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserImportModel {


	/** 姓名 */
	private String name;
	
	/** 年龄 */
	private Integer age;
	
	/** 生日 */
	private Date birthday;
	
	/** 存款 */
	private BigDecimal deposit;
		
}