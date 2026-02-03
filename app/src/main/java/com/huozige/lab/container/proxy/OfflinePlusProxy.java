package com.huozige.lab.container.proxy;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.offlinecustomform.dto.FormItemInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.dto.PatternInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.JsonFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflinePlusParseJsonData;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflinePlusListCardItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfflinePlusProxy extends AbstractProxy{


    @Override
    public void beforeActivityPause() {
        super.beforeActivityPause();
    }

    @Override
    public String getName() {
        return "offlinePlus";
    }

    @JavascriptInterface
    public void offlinePlusAddPatternAsync(String input) {
        writeInfoLog("OfflinePlusAddPattern");

        PatternInput inputObj = JSON.parseObject(input, PatternInput.class);

        registryCallbackTicket(inputObj.ticket);

        parseJsonToFile(inputObj);

        callback(CallbackParams.success("success"));

    }

    private void parseJsonToFile(PatternInput input) {

        Context context = this.getWebView().getContext();

        parseJsonToPatternListFile(context, input);

        parseJsonToCustomFormFile(context, input.formItems, input.patternId);
    }

    private void parseJsonToPatternListFile(Context context, PatternInput input) {

        OfflinePlusListCardItem newPattern = new OfflinePlusListCardItem(input.title, input.description, "未完成", input.patternId);

        JSONObject oldJson = JsonFileHelper.readJsonFromExternalStorage(context, JsonFileHelper.FILE_NAME_OFFLINE_FORM);

        List<OfflinePlusListCardItem> list;
        if (oldJson == null) list = new ArrayList<>();
        else list = OfflinePlusParseJsonData.parseJsonToCardList(oldJson);

        list.removeIf(i -> i.getPatternId().equals(input.patternId));
        list.add(newPattern);

        JSONObject newJson = OfflinePlusParseJsonData.parseCardListToJson(list);

        JsonFileHelper.writeJsonToExternalStorage(context, JsonFileHelper.FILE_NAME_OFFLINE_LIST, newJson);

    }

    private void parseJsonToCustomFormFile(Context context, List<FormItemInput> formItems, String patternId) {

        JSONObject jsonObject = OfflinePlusParseJsonData.parseFormItemToJson(formItems, patternId);

        JsonFileHelper.writeJsonToExternalStorage(context, JsonFileHelper.FILE_NAME_OFFLINE_FORM, patternId, jsonObject);
    }
}
