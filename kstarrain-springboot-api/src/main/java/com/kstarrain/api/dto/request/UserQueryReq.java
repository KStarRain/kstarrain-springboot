package com.kstarrain.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kstarrain.framework.api.dto.request.BasePageRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserQueryReq extends BasePageRequest {


	/** 姓名 */
	private String name;
	
	/** 年龄 */
	private Integer age;
	
	/** 生日 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;
	
	/** 存款 */
	private BigDecimal deposit;
		
}