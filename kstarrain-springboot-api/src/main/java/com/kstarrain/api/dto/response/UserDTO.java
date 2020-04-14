package com.kstarrain.api.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDTO {

	/** 主键 */
	private String id;

	/** 姓名 */
	private String name;
	
	/** 年龄 */
	private Integer age;
	
//	/** 生日 */
//	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//	private Date birthday;

	/** 生日 */
	private String birthday;

	/** 存款 */
	private BigDecimal deposit;

}