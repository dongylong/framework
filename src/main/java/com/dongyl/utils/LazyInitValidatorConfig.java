package com.dongyl.utils;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author dongyl
 * @date 21:14 8/13/18
 * @project framework
 */
public class LazyInitValidatorConfig {
    private Validator validator;

    private boolean needValidate = true;

    private static LazyInitValidatorConfig lazyInitValidatorConfig = new LazyInitValidatorConfig();

    private synchronized Validator init(){
        if(validator !=null){
            return validator;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        return validator;
    }

    public static Validator getValidator() {
        if(lazyInitValidatorConfig.validator ==null) {
            return lazyInitValidatorConfig.init();
        }
        return lazyInitValidatorConfig.validator;
    }

    public static boolean needValidate(){
        return lazyInitValidatorConfig.needValidate;
    }

}
