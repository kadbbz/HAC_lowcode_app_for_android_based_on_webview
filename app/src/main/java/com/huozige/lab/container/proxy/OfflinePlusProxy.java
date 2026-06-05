package com.huozige.lab.container.proxy;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.proxy.support.offlinecustomform.dto.PatternInput;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineComputedHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineComputedInfo;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinition;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionFactory;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionIndexItem;

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
    public void getAllPatternsAsync(String ticket) {

        writeInfoLog("OfflinePlusGetAllPatterns");



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

        OfflineFormDefinitionIndexItem listItem = updateDefinitionOrder(context, input);

        parseJsonToCustomFormFile(context, input, listItem);
    }

    private OfflineFormDefinitionIndexItem updateDefinitionOrder(Context context, PatternInput input) {

        List<OfflineFormDefinitionIndexItem> list = OfflineFormFileHelper.readDefinitions(context);
        OfflineFormDefinitionFile oldDefinition = OfflineFormFileHelper.readDefinition(context, input.patternId);
        String theme = oldDefinition == null ? "" : oldDefinition.getComputed().getTheme();

        int oldIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            OfflineFormDefinitionIndexItem item = list.get(i);
            if (item.getPatternId().equals(input.patternId)) {
                oldIndex = i;
                if (theme.isEmpty()) {
                    theme = item.getTheme();
                }
                break;
            }
        }

        // 同一个项目编号重复导入时沿用旧主题色；只有新增项目才按导入顺序分配主题色。
        if (theme.isEmpty()) {
            theme = OfflineComputedHelper.getThemeColor(list.size());
        }
        OfflineFormDefinitionIndexItem newPattern = new OfflineFormDefinitionIndexItem(input.title, input.description, "", input.patternId, input.schemaVersion, new OfflineComputedInfo(theme));
        if (oldIndex >= 0) {
            list.set(oldIndex, newPattern);
        } else {
            list.add(newPattern);
        }

        OfflineFormFileHelper.writeDefinitionOrder(context, list);
        return newPattern;

    }

    private void parseJsonToCustomFormFile(Context context, PatternInput input, OfflineFormDefinitionIndexItem listItem) {

        OfflineFormDefinition definition = OfflineFormDefinitionFactory.fromPatternInput(input);
        OfflineFormDefinitionFile definitionFile = new OfflineFormDefinitionFile(definition, listItem.getComputed());
        OfflineFormFileHelper.writeDefinition(context, input.patternId, definitionFile);
    }
}
