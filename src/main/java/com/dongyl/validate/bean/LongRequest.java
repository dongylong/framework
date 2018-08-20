package com.dongyl.validate.bean;

import javax.validation.constraints.Min;

/**
 * @author dongyl
 * @date 16:16 8/15/18
 * @project framework
 */
public class LongRequest extends BaseRequest{

    @Min(1)
    private Long id;

    public LongRequest() {
    }

    public LongRequest(@Min(1) Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
