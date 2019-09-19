package com.jiangdong.sunshine.result;

import java.util.List;
import java.util.Map;

public abstract class BaseResult {

    abstract List<String> getParam();

    abstract Map<String,Object> getParamAndValue();

}
