package com.kstarrain.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kstarrain.framework.api.dto.request.BasePageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserQueryReq extends BasePageRequest {

	@ApiModelProperty(value = "姓名", required = true, example = "貂蝉", position = 1)
	private String name;

	@ApiModelProperty(value = "年龄", required = true, example = "20", position = 2)
	private Integer age;

	@ApiModelProperty(value = "生日", required = true, example = "1998-12-12", position = 3)
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

	@ApiModelProperty(value = "存款", required = true, example = "528.12", position = 4)
	private BigDecimal deposit;
		
}