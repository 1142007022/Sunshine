package com.jiangdong.sunshine.result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseResult implements Serializable {

    abstract List<String> getParam();

    abstract Map<String,Object> getParamAndValue();

}
