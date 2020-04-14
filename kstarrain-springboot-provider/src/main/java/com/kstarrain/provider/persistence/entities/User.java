package com.kstarrain.provider.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	
	/** 姓名 */
	private String name;
	
	/** 年龄 */
	private Integer age;
	
	/** 生日 */
	private Date birthday;
	
	/** 存款 */
	private BigDecimal deposit;
	
	/** 创建者 */
	private String createUser;
	
	/** 创建时间 */
	private Date createDate;
	
	/** 更新者 */
	private String updateUser;
	
	/** 更新时间 */
	private Date updateDate;
	
	/** 数据存活标记 */
	private Integer aliveFlag;
	
		
	
		
}