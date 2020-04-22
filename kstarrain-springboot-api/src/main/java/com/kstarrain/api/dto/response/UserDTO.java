package com.kstarrain.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("用户信息")
public class UserDTO {

	@ApiModelProperty(value = "ID", required = true, example = "1", position = 1)
	private String id;

	@ApiModelProperty(value = "姓名", required = true, example = "貂蝉", position = 2)
	private String name;
	
	@ApiModelProperty(value = "年龄", required = true, example = "20", position = 3)
	private Integer age;
	
	@ApiModelProperty(value = "生日", required = true, example = "1998-12-12", position = 4)
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

	@ApiModelProperty(value = "存款", required = true, example = "528.12", position = 5)
	private BigDecimal deposit;

}