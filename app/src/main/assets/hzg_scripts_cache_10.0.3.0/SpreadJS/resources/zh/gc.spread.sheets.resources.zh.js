/*!
 * 
 * SpreadJS Library 17.0.5
 *
 * Copyright(c) of respective holders.  All rights reserved.
 *
 * Licensed under the SpreadJS Commercial License.
 *
 */

(function (factory) {
    if (typeof module === 'object' && typeof module.exports === 'object') {
        module.exports = factory(require('@grapecity/spread-sheets'));
    } else if (typeof define === 'function' && define.amd) {
        define(['@grapecity/spread-sheets'], factory)
    } else if (typeof exports === 'object') {
        exports['Spread'] = factory(require('@grapecity/spread-sheets'));
    } else {
        factory(GC);
    }
}(function (GC) {

/******/ (function() { // webpackBootstrap
/******/ 	var __webpack_modules__ = ({

/***/ "./dist/core/core.res.zh.js":
/*!**********************************!*\
  !*** ./dist/core/core.res.zh.js ***!
  \**********************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";





Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ARIA_HasValue = exports.ARIA_Cell = exports.ARIA_Scrollbar_TRACK_Button = exports.ARIA_Scrollbar_Bottom_Button = exports.ARIA_Scrollbar_Right_Button = exports.ARIA_Scrollbar_Thumb_Button = exports.ARIA_Scrollbar_Top_Button = exports.ARIA_Scrollbar_Left_Button = exports.ARIA_Blank = exports.ARIA_NewSheet = exports.ARIA_SheetTab = exports.ARIA_NextButton = exports.ARIA_PreviousButton = exports.ARIA_Last = exports.ARIA_NextArrow = exports.ARIA_PreviousArrow = exports.ARIA_First = exports.ARIA_Resize = exports.NeedCanvasSupport = exports.Exp_OverlappingSpans = exports.Exp_SheetIsNull = exports.Exp_DestSheetIsNull = exports.Exp_ArrayFormulaSpan = exports.Exp_SheetNameConflict = exports.Exp_SheetNameInvalid = exports.Exp_EmptyNamedStyle = exports.NewTab = exports.Tip_Width = exports.Tip_Height = exports.Tip_Column_Offset = exports.Tip_Column = exports.Tip_Row = exports.Exp_PasteChangeMergeCell = exports.Exp_PasteDestinationCellsLocked = exports.Exp_InvalidCopyPasteSize = exports.Exp_PasteSourceCellsLocked = exports.Exp_ArgumentOutOfRange = exports.Exp_NotAFunction = exports.Exp_InvalidRange = exports.Exp_IndexOutOfRange = exports.Exp_InvalidCustomName = exports.Exp_InvalidCustomFunction = exports.Exp_DestIsNull = exports.Exp_SrcIsNull = exports.Exp_InvalidAndSpace = exports.Exp_ChangePartOfArray = exports.Exp_MultipleSelections = exports.Exp_InvalidPastedArea = exports.Exp_PasteExtentIsNull = exports.Exp_NotSupported = void 0;
exports.cancel = exports.ok = exports.pasteSpecialOptionDialog = exports.STEP = exports.EXP_UNSUPPORT_CRYPTO_ALGORITHM = exports.GENERAL = exports.NORMAL = exports.EXP_INVALID_PASSWORD = exports.EXP_NO_PASSWORD = exports.EXP_FILE_FORMAT = exports.EXP_IO = exports.Exp_InvalidOperationInProtect = exports.Exp_InvalidSortPartTableOrMultiTableInRange = exports.Exp_InvalidSortSpanInRange = exports.Exp_InvalidSortArrayFormulaInRange = exports.Exp_InsertCopiedCutCellsAffectTable = exports.Exp_InsertCopiedCutCellsOverlap = exports.Exp_InsertCopiedCutCellsNoRange = exports.Exp_InsertCopiedCutCellsOnSpanTable = exports.Exp_InsertCopiedCutCells = exports.REPORT_SUMMARY = exports.SHEET_NAME = exports.ARIA_ColumnHeader = exports.ARIA_RowHeader = exports.ARIA_HasComment = exports.ARIA_HasHyperlink = exports.ARIA_HasButton = exports.ARIA_HasCheckbox = exports.ARIA_HasFormula = void 0;
var Core_1 = __webpack_require__(/*! Core */ "Core");
var Common_1 = __webpack_require__(/*! Common */ "Common");
var elements = (0, Core_1.GC$)('meta[name=\'spreadjs culture\']');
if (elements.length > 0) {
    var culture = (0, Core_1.GC$)(elements[elements.length - 1]).attr('content');
    if (culture !== null && culture !== undefined && culture.toLowerCase() === 'zh-cn') {
        Common_1.Common.CultureManager.culture('zh-cn');
    }
}
exports.Exp_NotSupported = '\u4e0d\u652f\u6301\u5f02\u5e38';
exports.Exp_PasteExtentIsNull = 'pasteExtent\u4e3a\u7a7a';
exports.Exp_InvalidPastedArea = '\u7c98\u8d34\u7684\u533a\u57df\u5e94\u8be5\u4e0e\u62f7\u8d1d\u7684\u533a\u57df\u6216\u526a\u8d34\u7684\u533a\u57df\u5927\u5c0f\u76f8\u540c\u3002';
exports.Exp_MultipleSelections = "\u6b64\u64cd\u4f5c\u4e0d\u9002\u7528\u4e8e\u591a\u7247\u9009\u62e9\u533a\u57df\u3002";
exports.Exp_ChangePartOfArray = '\u65e0\u6cd5\u4ec5\u6539\u53d8\u90e8\u5206\u7684\u6570\u7ec4\u3002';
exports.Exp_InvalidAndSpace = '\u65e0\u6548\u7684 {0}: {1} (\u5fc5\u987b\u5728 {2} \u5230 {3} \u4e4b\u95f4)\u3002';
exports.Exp_SrcIsNull = '\u53c2\u6570 \'src\' \u4e3a\u7a7a';
exports.Exp_DestIsNull = '\u53c2\u6570 \'dest\' \u4e3a\u7a7a';
exports.Exp_InvalidCustomFunction = '\u65e0\u6548\u81ea\u5b9a\u4e49\u51fd\u6570';
exports.Exp_InvalidCustomName = '\u65e0\u6548\u81ea\u5b9a\u4e49\u540d\u79f0';
exports.Exp_IndexOutOfRange = '\u7d22\u5f15\u8d85\u51fa\u533a\u95f4!';
exports.Exp_InvalidRange = '\u65e0\u6548\u533a\u95f4';
exports.Exp_NotAFunction = '\u65e0\u6548\u51fd\u6570';
exports.Exp_ArgumentOutOfRange = '\u53c2\u6570\u8d85\u51fa\u8303\u56f4';
exports.Exp_PasteSourceCellsLocked = '\u6e90\u8868\u5355\u7684\u5355\u5143\u683c\u88ab\u9501\u5b9a\u3002';
exports.Exp_InvalidCopyPasteSize = '\u590d\u5236\u548c\u7c98\u8d34\u7684\u533a\u57df\u5927\u5c0f\u4e0d\u4e00\u81f4\u3002';
exports.Exp_PasteDestinationCellsLocked = '\u60a8\u5c1d\u8bd5\u6539\u53d8\u7684\u5355\u5143\u683c\u662f\u88ab\u4fdd\u62a4\u7684\uff0c\u56e0\u6b64\u662f\u53ea\u8bfb\u7684\u3002';
exports.Exp_PasteChangeMergeCell = '\u4e0d\u80fd\u4ec5\u6539\u53d8\u5408\u5e76\u5355\u5143\u683c\u7684\u4e00\u90e8\u5206\u3002';
exports.Tip_Row = '\u884c: ';
exports.Tip_Column = '\u5217: ';
exports.Tip_Column_Offset = '\u504f\u79fb\u91cf: ';
exports.Tip_Height = '\u9ad8\u5ea6: {0} \u50cf\u7d20';
exports.Tip_Width = '\u5bbd\u5ea6: {0} \u50cf\u7d20';
exports.NewTab = '\u65b0\u5efa...';
exports.Exp_EmptyNamedStyle = '\u547d\u540d\u6837\u5f0f\u7684\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a';
exports.Exp_SheetNameInvalid = '\u8868\u5355\u540d\u4e0d\u80fd\u4e3a\u7a7a\u4e14\u4e0d\u80fd\u5305\u542b\u4ee5\u4e0b\u5b57\u7b26 : *, :, [, ], ?, \\, /';
exports.Exp_SheetNameConflict = '\u6b64\u540d\u79f0\u5df2\u88ab\u4f7f\u7528\u3002\u8bf7\u5c1d\u8bd5\u5176\u4ed6\u540d\u79f0\u3002';
exports.Exp_ArrayFormulaSpan = '\u5408\u5e76\u5355\u5143\u683c\u7684\u6570\u7ec4\u516c\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u3002';
exports.Exp_DestSheetIsNull = 'destSheet\u4e3a\u7a7a\u3002';
exports.Exp_SheetIsNull = 'sheet\u4e3a\u7a7a\u3002';
exports.Exp_OverlappingSpans = '\u64cd\u4f5c\u5c06\u5e26\u6765\u91cd\u590d\u7684\u5408\u5e76\u6548\u679c\u3002';
exports.NeedCanvasSupport = '\u4f60\u9700\u8981\u4e00\u4e2a\u5b8c\u5168\u652f\u6301HTML5 Canvas\u7684\u6d4f\u89c8\u5668\u6765\u8fd0\u884cSpreadJS';
exports.ARIA_Resize = '\u8c03\u6574\u5927\u5c0f';
exports.ARIA_First = '\u7b2c\u4e00\u4e2a';
exports.ARIA_PreviousArrow = '\u4e0a\u4e00\u4e2a';
exports.ARIA_NextArrow = '\u4e0b\u4e00\u4e2a';
exports.ARIA_Last = '\u6700\u540e\u4e00\u4e2a';
exports.ARIA_PreviousButton = '\u5411\u524d\u4e00\u4e2a';
exports.ARIA_NextButton = '\u5411\u540e\u4e00\u4e2a';
exports.ARIA_SheetTab = '\u5de5\u4f5c\u8868\u6807\u7b7e';
exports.ARIA_NewSheet = '\u65b0\u5efa\u5de5\u4f5c\u8868';
exports.ARIA_Blank = '\u7a7a\u767d';
exports.ARIA_Scrollbar_Left_Button = '\u6eda\u52a8\u6761\u5de6\u6309\u94ae';
exports.ARIA_Scrollbar_Top_Button = '\u6eda\u52a8\u6761\u4e0a\u6309\u94ae';
exports.ARIA_Scrollbar_Thumb_Button = '\u6eda\u52a8\u6761\u6ed1\u5757\u6309\u94ae';
exports.ARIA_Scrollbar_Right_Button = '\u6eda\u52a8\u6761\u53f3\u6309\u94ae';
exports.ARIA_Scrollbar_Bottom_Button = '\u6eda\u52a8\u6761\u4e0b\u6309\u94ae';
exports.ARIA_Scrollbar_TRACK_Button = '\u6eda\u52a8\u6761\u8ddf\u8e2a\u6309\u94ae';
exports.ARIA_Cell = '\u5355\u5143\u683c';
exports.ARIA_HasValue = '\u7684\u503c\u662f {0}';
exports.ARIA_HasFormula = '\u7684\u516c\u5f0f\u662f {0}';
exports.ARIA_HasCheckbox = '\u7684\u590d\u9009\u6846\u662f {0}';
exports.ARIA_HasButton = '\u7684\u6309\u94ae\u662f {0}';
exports.ARIA_HasHyperlink = '\u7684\u8d85\u94fe\u63a5\u662f {0}';
exports.ARIA_HasComment = '\u7684\u6ce8\u89e3\u662f {0}';
exports.ARIA_RowHeader = '\u884c\u5934';
exports.ARIA_ColumnHeader = '\u5217\u5934';
exports.SHEET_NAME = 'Sheet';
exports.REPORT_SUMMARY = '\u6c42\u548c';

var lr = __webpack_require__(/*! lrZh */ "./dist/core/lc/lr.zh.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}
exports.Exp_InsertCopiedCutCells = "\u786e\u4fdd\u590d\u5236\u548c\u7c98\u8d34\u533a\u57df\u4e0d\u91cd\u53e0.";
exports.Exp_InsertCopiedCutCellsOnSpanTable = "\u8fd9\u4e0d\u4f1a\u8d77\u4f5c\u7528\uff0c\u56e0\u4e3a\u5b83\u4f1a\u79fb\u52a8\u5de5\u4f5c\u8868\u4e0a\u8868\u683c\u4e2d\u7684\u5355\u5143\u683c\uff0c\u6216\u8005\u4f1a\u5bfc\u81f4\u67d0\u4e9b\u5408\u5e76\u7684\u5355\u5143\u683c\u53d6\u6d88\u5408\u5e76\u3002";
exports.Exp_InsertCopiedCutCellsNoRange = "SpreadJS\u4e0d\u80fd\u63d2\u5165\u65b0\u7684\u5355\u5143\u683c\uff0c\u56e0\u4e3a\u5b83\u4f1a\u628a\u975e\u7a7a\u7684\u5355\u5143\u683c\u4ece\u5de5\u4f5c\u8868\u7684\u672b\u7aef\u63a8\u51fa\u53bb\u3002";
exports.Exp_InsertCopiedCutCellsOverlap = "\u6b64\u9009\u62e9\u65e0\u6548\u3002\u8bf7\u786e\u4fdd\u590d\u5236\u548c\u7c98\u8d34\u533a\u57df\u4e0d\u4f1a\u91cd\u53e0\uff0c\u9664\u975e\u5b83\u4eec\u5177\u6709\u76f8\u540c\u7684\u5927\u5c0f\u548c\u5f62\u72b6\u3002";
exports.Exp_InsertCopiedCutCellsAffectTable = "\u65e0\u6cd5\u901a\u8fc7\u8fd9\u79cd\u65b9\u5f0f\u91cd\u65b0\u6392\u5217\u8868\u4e2d\u7684\u5355\u5143\u683c\uff0c\u56e0\u4e3a\u5b83\u53ef\u80fd\u4f1a\u4ee5\u610f\u60f3\u4e0d\u5230\u7684\u65b9\u5f0f\u5f71\u54cd\u5176\u4ed6\u8868\u683c\u5355\u5143\u683c\u3002";
exports.Exp_InvalidSortArrayFormulaInRange = "\u5f53\u524d\u533a\u57df\u65e0\u6cd5\u6392\u5e8f\uff0c\u56e0\u4e3a\u5b58\u5728\u6570\u7ec4\u516c\u5f0f\u3002";
exports.Exp_InvalidSortSpanInRange = "\u5f53\u524d\u533a\u57df\u65e0\u6cd5\u6392\u5e8f\uff0c\u56e0\u4e3a\u5b58\u5728\u5408\u5e76\u7684\u5355\u5143\u683c\u3002";
exports.Exp_InvalidSortPartTableOrMultiTableInRange = "\u5f53\u524d\u533a\u57df\u65e0\u6cd5\u6392\u5e8f\uff0c\u56e0\u4e3a\u5b58\u5728\u90e8\u5206\u8868\u683c\u6216\u8005\u5b58\u5728\u591a\u4e2a\u8868\u683c\u3002";
exports.Exp_InvalidOperationInProtect = "\u60a8\u8bd5\u56fe\u66f4\u6539\u7684\u5355\u5143\u683c\u6216\u56fe\u8868\u4f4d\u4e8e\u53d7\u4fdd\u62a4\u7684\u5de5\u4f5c\u8868\u4e2d\u3002\u82e5\u8981\u8fdb\u884c\u66f4\u6539\uff0c\u8bf7\u53d6\u6d88\u5de5\u4f5c\u8868\u4fdd\u62a4\u3002";
exports.EXP_IO = '\u6587\u4ef6\u8bfb\u5199\u5f02\u5e38';
exports.EXP_FILE_FORMAT = '\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\u3002';
exports.EXP_NO_PASSWORD = 'Excel\u6587\u4ef6\u53d7\u5bc6\u7801\u4fdd\u62a4\uff0c\u65e0\u6cd5\u88ab\u6253\u5f00\u3002';
exports.EXP_INVALID_PASSWORD = '\u5bc6\u7801\u9519\u8bef\u3002';
exports.NORMAL = '\u5e38\u89c4';
exports.GENERAL = '\u5e38\u89c4';
exports.EXP_UNSUPPORT_CRYPTO_ALGORITHM = '\u4e0d\u652f\u6301\u7684\u52a0\u5bc6\u7b97\u6cd5';
exports.STEP = {
    start: "\u5f00\u59cb\u52a0\u8f7d {0}",
    loadFileData: "\u52a0\u8f7d\u6587\u4ef6 {0} \u5230\u5de5\u4f5c\u7c3f",
    loadSheetData: "\u52a0\u8f7d {0} \u5de5\u4f5c\u8868\u7684\u5185\u5bb9",
    startCalc: "\u5f00\u59cb\u91cd\u65b0\u8ba1\u7b97",
    loadSheetFormula: "\u52a0\u8f7d {0} \u5de5\u4f5c\u8868\u7684\u516c\u5f0f",
    parseFile: "\u89e3\u6790\u6587\u4ef6 {0} \u7684\u5185\u5bb9"
};
exports.pasteSpecialOptionDialog = {
    title: "\u9009\u62e9\u6027\u7c98\u8d34",
    paste: "\u7c98\u8d34",
    pasteOptions: {
        all: "\u5168\u90e8",
        formulas: "\u516c\u5f0f",
        values: "\u6570\u503c",
        formats: "\u683c\u5f0f",
        comments: "\u6279\u6ce8",
        validation: "\u9a8c\u8bc1",
        usingSourceTheme: "\u6240\u6709\u4f7f\u7528\u6e90\u4e3b\u9898\u7684\u5355\u5143",
        noBorders: "\u8fb9\u6846\u9664\u5916",
        columnWidth: "\u5217\u5bbd",
        formulasAndNumberFormats: "\u516c\u5f0f\u548c\u6570\u5b57\u683c\u5f0f",
        valueAndNumberFormats: "\u503c\u548c\u6570\u5b57\u683c\u5f0f",
    },
    operation: "\u8fd0\u7b97",
    operationOptions: {
        none: "\u65e0",
        add: "\u52a0",
        subtract: "\u51cf",
        multiply: "\u4e58",
        divide: "\u9664"
    },
    skipBlanks: "\u8df3\u8fc7\u7a7a\u5355\u5143",
    transpose: "\u8f6c\u7f6e",
    pasteLink: "\u8f6c\u8d34\u94fe\u63a5"
};
exports.ok = "\u786e\u5b9a";
exports.cancel = "\u53d6\u6d88";


/***/ }),

/***/ "./dist/core/lc/lr.zh.js":
/*!*******************************!*\
  !*** ./dist/core/lc/lr.zh.js ***!
  \*******************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ls11 = exports.ls10 = exports.ls9 = exports.ls8 = exports.ls7 = exports.ls6 = exports.ls5 = exports.ls4 = exports.ls3 = exports.ls2 = exports.ls1 = exports.lsde = exports.lsru = void 0;
exports.lsru = ['907f',
    'e872'];
exports.lsde = ['8b8b56',
    'bea168'];

exports.ls1 = ["895b848457000000000000000000008868634e634f6267656330000000678b4f72674e966757000000304e6500000063674e966d8b7590300000008b60548b8b5390904e520000000000000000000000000000000000000000000030",
    "7f896104ce205370726561644a5320683ca7f6d09b802f2f01020d0a0a2cc430482cc5502c307b317d0234f67b317d8843c5504bd52814020d0a0ae6c5a8e2f7d101aef63073616c65732e7861406772617065636974792e636f6d02"];

exports.ls2 = ["895b848457000000000000000000008868634e634f62676563300000004e650000006367524f0000005930",
    "7f896104ce205370726561644a5320683ca7f6d09b802f2f01020d0a0a34f67b317d884369597b307d2902"];

exports.ls3 = ["67625263674f60300000008f88000000000000000000008868634eff6097899951535f546c63674f60300000004e6563674e968b4f759030000000595d8d4eff8b578d4e904e4e676263674f603000000059975e52ff8b5390904e520000000000000000000000000000000000000000000030",
    "2a7e308843e16f020d0a0ad04c205370726561644a5320683ca7f60ca800819648d69708d58843e16f020d0a0a34f68843c550c4302814020d0a0a82f22d700cf7282d70aef62de57e8843e16f020a0d0a82002ea90cf7d101aef63073616c65732e7861406772617065636974792e636f6d02"];

exports.ls4 = ["656563674f603000000059975e52ff8b5390904e520000000000000000000000000000000000000000000030",
    "e0488843e16f020d0a0a82002ea90cf7d101aef63073616c65732e7861406772617065636974792e636f6d02"];

exports.ls5 = ["895b848457000000000000000000008868634e634f62676563300000004e6500000063675d8f67ff8b5390904e520000000000000000000000000000000000000000000083535e5230",
    "7f896104ce205370726561644a5320683ca7f6d09b802f2f01020d0a0a34f67b317d8843f2c71f0cf7d101aef63073616c65732e7861406772617065636974792e636f6db7d62ea902"];
exports.ls6 = ["6772674e895b848457000000000000000000008868634e4e548b7572ff67535f5152536367300000005997835f6b5f6367ff8b817500000000000000000000000030",
    "2c482c3a7f896104ce205370726561644a5320683ca7f6a7c1d528480c2ad6978d06d18843020d0a0a8200b797630f88430cf7f4353430302d3635372d3630303802"];

exports.ls7 = ["67625263674f60300000008f88000000000000000000008868634eff6097899951535f546c63674f60300000004e6563674e968b4f759030000000595d8d4eff8b578d4e904e4e676263674f603000000059975e52ff8b5390904e520000000000000000000000000000000000000000000030",
    "2a7e308843e16f020d0a0ad04c205370726561644a5320683ca7f60ca800819648d69708d58843e16f020d0a0a34f68843c550c4302814020d0a0a82f22d700cf7282d70aef62de57e8843e16f020a0d0a82002ea90cf7d101aef63073616c65732e7861406772617065636974792e636f6d02"];

exports.ls8 = ["656563674f603000000059975e52ff8b5390904e520000000000000000000000000000000000000000000030",
    "e0488843e16f020d0a0a82002ea90cf7d101aef63073616c65732e7861406772617065636974792e636f6d02"];

exports.ls9 = ["634eff0000000000006765636730", "d2f61a7b307d202d200948884302"];

exports.ls10 = ["634eff00000000000065636730", "d2f61a7b307d202d20e0884302"];

exports.ls11 = ["30", "01"];


/***/ }),

/***/ "./dist/plugins/autoMerge/autoMerge.res.zh.js":
/*!****************************************************!*\
  !*** ./dist/plugins/autoMerge/autoMerge.res.zh.js ***!
  \****************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_RangeIsIntersected = void 0;
exports.Exp_RangeIsIntersected = "\u5f53\u524d\u533a\u57df\u4e0d\u80fd\u548c\u5df2\u5b58\u5728\u7684\u533a\u57df\u76f8\u4ea4\u3002";


/***/ }),

/***/ "./dist/plugins/celltype/celltypes.res.zh.js":
/*!***************************************************!*\
  !*** ./dist/plugins/celltype/celltypes.res.zh.js ***!
  \***************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Calendar_BuiltIn_Yesterday = exports.Calendar_BuiltIn_Today = exports.Calendar_Today = exports.Calendar_FirstYear = exports.Calendar_EraName_4 = exports.Calendar_EraName_3 = exports.Calendar_EraName_2 = exports.Calendar_EraName_1 = exports.Calendar_Time_PM = exports.Calendar_Time_AM = exports.Calendar_Months_12 = exports.Calendar_Months_11 = exports.Calendar_Months_10 = exports.Calendar_Months_9 = exports.Calendar_Months_8 = exports.Calendar_Months_7 = exports.Calendar_Months_6 = exports.Calendar_Months_5 = exports.Calendar_Months_4 = exports.Calendar_Months_3 = exports.Calendar_Months_2 = exports.Calendar_Months_1 = exports.Calendar_ShortMonths_12 = exports.Calendar_ShortMonths_11 = exports.Calendar_ShortMonths_10 = exports.Calendar_ShortMonths_9 = exports.Calendar_ShortMonths_8 = exports.Calendar_ShortMonths_7 = exports.Calendar_ShortMonths_6 = exports.Calendar_ShortMonths_5 = exports.Calendar_ShortMonths_4 = exports.Calendar_ShortMonths_3 = exports.Calendar_ShortMonths_2 = exports.Calendar_ShortMonths_1 = exports.Calendar_Weeks_7 = exports.Calendar_Weeks_6 = exports.Calendar_Weeks_5 = exports.Calendar_Weeks_4 = exports.Calendar_Weeks_3 = exports.Calendar_Weeks_2 = exports.Calendar_Weeks_1 = exports.Calendar_ShortWeeks_7 = exports.Calendar_ShortWeeks_6 = exports.Calendar_ShortWeeks_5 = exports.Calendar_ShortWeeks_4 = exports.Calendar_ShortWeeks_3 = exports.Calendar_ShortWeeks_2 = exports.Calendar_ShortWeeks_1 = exports.Cancel = exports.OK = void 0;
exports.DataObject_MeetTable = exports.DataObject_MeetSpan = exports.DataObject_MeetLock = exports.MultiColumn_InvalidDataSource = exports.Calculator_OverFlowInfo = exports.Calculator_SqrtParameterException = exports.Calculator_InvalidInputInfo = exports.Calculator_DivideByZeroInfo = exports.StandardColor = exports.ThemeColor = exports.Quarter_Format_4 = exports.Quarter_Format_3 = exports.Quarter_Format_2 = exports.Quarter_Format_1 = exports.Calendar_NextTenYear = exports.Calendar_LastTenYear = exports.Calendar_NextYear = exports.Calendar_LastYear = exports.Calendar_NextMonth = exports.Calendar_LastMonth = exports.Calendar_BuiltIn_LastMonth = exports.Calendar_BuiltIn_ThisMonth = exports.Calendar_BuiltIn_Last7Days = void 0;
exports.OK = '\u786e\u5b9a';
exports.Cancel = '\u53d6\u6d88';
exports.Calendar_ShortWeeks_1 = "\u5468\u4e00";
exports.Calendar_ShortWeeks_2 = "\u5468\u4e8c";
exports.Calendar_ShortWeeks_3 = "\u5468\u4e09";
exports.Calendar_ShortWeeks_4 = "\u5468\u56db";
exports.Calendar_ShortWeeks_5 = "\u5468\u4e94";
exports.Calendar_ShortWeeks_6 = "\u5468\u516d";
exports.Calendar_ShortWeeks_7 = "\u5468\u65e5";
exports.Calendar_Weeks_1 = "\u661f\u671f\u4e00";
exports.Calendar_Weeks_2 = "\u661f\u671f\u4e8c";
exports.Calendar_Weeks_3 = "\u661f\u671f\u4e09";
exports.Calendar_Weeks_4 = "\u661f\u671f\u56db";
exports.Calendar_Weeks_5 = "\u661f\u671f\u4e94";
exports.Calendar_Weeks_6 = "\u661f\u671f\u516d";
exports.Calendar_Weeks_7 = "\u661f\u671f\u65e5";
exports.Calendar_ShortMonths_1 = "\u4e00\u6708";
exports.Calendar_ShortMonths_2 = "\u4e8c\u6708";
exports.Calendar_ShortMonths_3 = "\u4e09\u6708";
exports.Calendar_ShortMonths_4 = "\u56db\u6708";
exports.Calendar_ShortMonths_5 = "\u4e94\u6708";
exports.Calendar_ShortMonths_6 = "\u516d\u6708";
exports.Calendar_ShortMonths_7 = "\u4e03\u6708";
exports.Calendar_ShortMonths_8 = "\u516b\u6708";
exports.Calendar_ShortMonths_9 = "\u4e5d\u6708";
exports.Calendar_ShortMonths_10 = "\u5341\u6708";
exports.Calendar_ShortMonths_11 = "\u5341\u4e00\u6708";
exports.Calendar_ShortMonths_12 = "\u5341\u4e8c\u6708";
exports.Calendar_Months_1 = "1\u6708";
exports.Calendar_Months_2 = "2\u6708";
exports.Calendar_Months_3 = "3\u6708";
exports.Calendar_Months_4 = "4\u6708";
exports.Calendar_Months_5 = "5\u6708";
exports.Calendar_Months_6 = "6\u6708";
exports.Calendar_Months_7 = "7\u6708";
exports.Calendar_Months_8 = "8\u6708";
exports.Calendar_Months_9 = "9\u6708";
exports.Calendar_Months_10 = "10\u6708";
exports.Calendar_Months_11 = "11\u6708";
exports.Calendar_Months_12 = "12\u6708";
exports.Calendar_Time_AM = "\u4e0a\u5348";
exports.Calendar_Time_PM = "\u4e0b\u5348";
exports.Calendar_EraName_1 = "\u660e\u6cbb";
exports.Calendar_EraName_2 = "\u5927\u6b63";
exports.Calendar_EraName_3 = "\u662d\u548c";
exports.Calendar_EraName_4 = "\u5e73\u6210";
exports.Calendar_FirstYear = "\u5143";
exports.Calendar_Today = "\u4eca\u5929";
exports.Calendar_BuiltIn_Today = "\u4eca\u5929";
exports.Calendar_BuiltIn_Yesterday = "\u6628\u5929";
exports.Calendar_BuiltIn_Last7Days = "\u8fc7\u53bb7\u5929";
exports.Calendar_BuiltIn_ThisMonth = "\u672c\u6708";
exports.Calendar_BuiltIn_LastMonth = "\u4e0a\u4e2a\u6708";
exports.Calendar_LastMonth = "\u4e0a\u4e2a\u6708";
exports.Calendar_NextMonth = "\u4e0b\u4e2a\u6708";
exports.Calendar_LastYear = "\u4e0a\u4e00\u5e74";
exports.Calendar_NextYear = "\u4e0b\u4e00\u5e74";
exports.Calendar_LastTenYear = "\u4e0a\u5341\u5e74";
exports.Calendar_NextTenYear = "\u4e0b\u5341\u5e74";
exports.Quarter_Format_1 = "\u7b2c\u4e00\u5b63\u5ea6";
exports.Quarter_Format_2 = "\u7b2c\u4e8c\u5b63\u5ea6";
exports.Quarter_Format_3 = "\u7b2c\u4e09\u5b63\u5ea6";
exports.Quarter_Format_4 = "\u7b2c\u56db\u5b63\u5ea6";
exports.ThemeColor = "\u4e3b\u9898\u8272";
exports.StandardColor = "\u6807\u51c6\u8272";
exports.Calculator_DivideByZeroInfo = "\u65e0\u6cd5\u88ab0\u9664";
exports.Calculator_InvalidInputInfo = "\u65e0\u6548\u7684\u8f93\u5165";
exports.Calculator_SqrtParameterException = "\u51fd\u6570\u7684\u8f93\u5165\u65e0\u6548";
exports.Calculator_OverFlowInfo = "\u8fd0\u7b97\u7b26\u64cd\u4f5c\u5bfc\u81f4\u6ea2\u51fa";
exports.MultiColumn_InvalidDataSource = "\u65e0\u6548\u7684\u6570\u636e\u6e90\uff0c\u8be5\u516c\u5f0f\u5e94\u8fd4\u56de\u4e00\u4e2a\u6570\u7ec4\u3002";
exports.DataObject_MeetLock = "\u4e0d\u80fd\u63d2\u5165\u6570\u636e\uff0c\u56e0\u4e3a\u6b64\u65b9\u5411\u4e0a\u6709\u9501\u5b9a\u7684\u5355\u5143\u683c\u3002";
exports.DataObject_MeetSpan = "\u4e0d\u80fd\u63d2\u5165\u6570\u636e\uff0c\u56e0\u4e3a\u6b64\u65b9\u5411\u4e0a\u5b58\u5728\u5408\u5e76\u5355\u5143\u683c\u3002";
exports.DataObject_MeetTable = "\u4e0d\u80fd\u63d2\u5165\u6570\u636e\uff0c\u56e0\u4e3a\u6b64\u65b9\u5411\u4e0a\u5b58\u5728\u8868\u683c\u3002";


/***/ }),

/***/ "./dist/plugins/chart/chart.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/chart/chart.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.AxisTitle = exports.Branch = exports.ChartTitle = exports.TOTAL = exports.DECREASE = exports.INCREASE = exports.SERIES = exports.TOOLTIP_VALUE = exports.TOOLTIP_SIZE = exports.TOOLTIP_VALUE_POINT = exports.TOOLTIP_SERIES = exports.unsupportedChartType = exports.power = exports.polynomial = exports.logarithmic = exports.linear = exports.exponential = exports.movingAverage = exports.period = void 0;
exports.period = '\u5468\u671f';
exports.movingAverage = '\u79fb\u52a8\u5e73\u5747';
exports.exponential = '\u6307\u6570';
exports.linear = '\u7ebf\u6027';
exports.logarithmic = '\u5bf9\u6570';
exports.polynomial = '\u591a\u9879\u5f0f';
exports.power = '\u4e58\u5e42';
exports.unsupportedChartType = '\u4e0d\u652f\u6301\u7684\u56fe\u8868\u7c7b\u578b';
exports.TOOLTIP_SERIES = "\u7cfb\u5217 \"{";
exports.TOOLTIP_VALUE_POINT = ".value}\" \u70b9 \"{";
exports.TOOLTIP_SIZE = "\u5927\u5c0f: ";
exports.TOOLTIP_VALUE = "\u503c: ";
exports.SERIES = "\u7cfb\u5217";
exports.INCREASE = "\u589e\u52a0";
exports.DECREASE = "\u51cf\u5c11";
exports.TOTAL = "\u6c47\u603b";
exports.ChartTitle = "\u56fe\u8868\u6807\u9898";
exports.Branch = "\u5206\u652f";
exports.AxisTitle = "\u5750\u6807\u8f74\u6807\u9898";


/***/ }),

/***/ "./dist/plugins/conditional/conditional.res.zh.js":
/*!********************************************************!*\
  !*** ./dist/plugins/conditional/conditional.res.zh.js ***!
  \********************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_NotSupported = exports.Exp_RuleIsNull = void 0;
exports.Exp_RuleIsNull = '\u53c2\u6570 \'rule\' \u662f\u7a7a';
exports.Exp_NotSupported = '\u4e0d\u652f\u6301\u5f02\u5e38';


/***/ }),

/***/ "./dist/plugins/contextMenu/context-menu.res.zh.js":
/*!*********************************************************!*\
  !*** ./dist/plugins/contextMenu/context-menu.res.zh.js ***!
  \*********************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.tableDeleteColumns = exports.tableDeleteOrInsertEntireSheetRow = exports.tableDeleteRows = exports.tableDelete = exports.tableInsertColumnsRight = exports.tableInsertColumnsLeft = exports.tableInsertRowsBelow = exports.tableInsertRowsAbove = exports.tableInsert = exports.removeFloatingObject = exports.removeSlicer = exports.toggleComment = exports.deleteComment = exports.editComment = exports.unhideRows = exports.unhideColumns = exports.unhideSheet = exports.hideSheet = exports.hideColumns = exports.hideRows = exports.slicerSortDescend = exports.slicerSortAscend = exports.sortDescend = exports.sortAscend = exports.headerInsertCutCells = exports.headerInsertCopiedCells = exports.shiftCellsDown = exports.shiftCellsRight = exports.insertCutCells = exports.insertCopiedCells = exports.sort = exports.filter = exports.insertComment = exports.deleteSheet = exports.insertSheet = exports.deleteColumns = exports.deleteRows = exports.insertColumns = exports.insertRows = exports.pasteSpecial = exports.pasteFormulaFormatting = exports.pasteValuesFormatting = exports.clearContents = exports.pasteFormatting = exports.pasteValues = exports.pasteFormula = exports.pasteAll = exports.pasteOptions = exports.cut = exports.copy = void 0;
exports.pivotTableExpand = exports.pivotTableValueFilters = exports.pivotTableLabelFilters_Date = exports.pivotTableLabelFilters = exports.pivotTableTop10 = exports.pivotTableHideSelectedItems = exports.pivotTableKeepOnlySelectedItems = exports.pivotTableClearFilterFrom_ = exports.pivotTableFieldSettings = exports.pivotTableHideFieldList = exports.pivotTableSubtotal_ = exports.pivotTableFilter = exports.pivotTableExpandOrCollapse = exports.pivotTableUnGroup = exports.pivotTableGroup = exports.pivotTableShowDetails = exports.pivotTableIndex = exports.pivotTableRankLargestToSmallest = exports.pivotTableRankSmallestToLargest = exports.pivotTablePercentRunningTotalIn = exports.pivotTableRunningTotalIN = exports.pivotTablePercentDifferenceFrom = exports.pivotTableDifferenceFrom = exports.pivotTableOfParentTotal = exports.pivotTableParentColumnTotal = exports.pivotTableParentRowTotal = exports.pivotTableOf = exports.pivotTableRowTotal = exports.pivotTableColumnTotal = exports.pivotTableGrandTotal = exports.pivotTableNoCalculation = exports.pivotTableShowValueAs = exports.pivotTableMoreOptions = exports.pivotTableDistinctCount = exports.pivotTableProduct = exports.pivotTableMin = exports.pivotTableMax = exports.pivotTableAverage = exports.pivotTableCount = exports.pivotTableSum = exports.pivotTableSummarizeValuesBy = exports.pivotTableSort = exports.pivotTableNumberFormat = exports.pivotTableOptions = exports.pivotTableValueFieldSettings = exports.pivotTableRemoveGrandTotal = exports.pivotTableRemove_ = exports.pivotTableMove = exports.pivotTableRefresh = exports.hideSheetFailureInfo = void 0;
exports.autoSchedule = exports.manuallySchedule = exports.outdentTasks = exports.indentTasks = exports.deleteTasks = exports.insertTasks = exports.scrollToTasks = exports.reportSheetResetCellValue = exports.reportSheetSubmit = exports.reportSheetDeleteRecord = exports.reportSheetAddRecord = exports.paste = exports.expandLevel9 = exports.expandLevel8 = exports.expandLevel7 = exports.expandLevel6 = exports.expandLevel5 = exports.expandLevel4 = exports.expandLevel3 = exports.expandLevel2 = exports.expandLevel1 = exports.collapseAllLevels = exports.expandAllLevels = exports.addBelow = exports.addAbove = exports.addBefore = exports.addAfter = exports.moveDown = exports.moveUp = exports.demote = exports.promote = exports.pinColumns = exports.pinRows = exports.pivotTableSortMoreOptions = exports.pivotTableSortDescend = exports.pivotTableSortAscend = exports.sigmaValueTemp = exports.sigmaValue = exports.pivotTableMove_ToColumns = exports.pivotTableMove_ToRight = exports.pivotTableMove_ToLeft = exports.pivotTableMove_ToEnd = exports.pivotTableMove_Down = exports.pivotTableMove_Up = exports.pivotTableMove_ToBeginning = exports.pivotTableExpandTo_ = exports.pivotTableCollapseTo_ = exports.pivotTableCollapseEntireField = exports.pivotTableExpandEntireField = exports.pivotTableCollapse = void 0;
exports.unhideColumn = exports.hideColumn = void 0;
exports.copy = '\u590d\u5236';
exports.cut = '\u526a\u5207';
exports.pasteOptions = '\u7c98\u8d34\u9009\u9879:';
exports.pasteAll = '\u5168\u90e8\u7c98\u8d34';
exports.pasteFormula = '\u516c\u5f0f';
exports.pasteValues = '\u503c';
exports.pasteFormatting = '\u683c\u5f0f';
exports.clearContents = '\u6e05\u9664';
exports.pasteValuesFormatting = '\u503c&\u683c\u5f0f';
exports.pasteFormulaFormatting = '\u516c\u5f0f&\u683c\u5f0f';
exports.pasteSpecial = {
    title: '\u9009\u62e9\u6027\u7c98\u8d34...',
    formulaAndNumberFormat: '\u516c\u5f0f\u548c\u6570\u5b57\u683c\u5f0f',
    keepSourceFormatting: '\u4fdd\u7559\u6e90\u683c\u5f0f',
    noBorders: '\u65e0\u8fb9\u6846',
    columnWidth: '\u4fdd\u7559\u6e90\u5217\u5bbd',
    transpose: '\u8f6c\u7f6e',
    valuesAndNumberFormat: '\u503c\u548c\u6570\u5b57\u683c\u5f0f',
    valueAndSourceFormat: '\u503c\u548c\u6e90\u683c\u5f0f',
    pasteLink: '\u7c98\u8d34\u94fe\u63a5',
    otherPasteOptions: '\u5176\u4ed6\u7c98\u8d34\u9009\u9879'
};
exports.insertRows = "\u63d2\u5165";
exports.insertColumns = "\u63d2\u5165";
exports.deleteRows = "\u5220\u9664";
exports.deleteColumns = "\u5220\u9664";
exports.insertSheet = '\u63d2\u5165';
exports.deleteSheet = '\u5220\u9664';
exports.insertComment = '\u63d2\u5165\u6279\u6ce8';
exports.filter = '\u8fc7\u6ee4';
exports.sort = '\u6392\u5e8f';
exports.insertCopiedCells = '\u63d2\u5165\u590d\u5236\u7684\u5355\u5143\u683c...';
exports.insertCutCells = '\u63d2\u5165\u526a\u8d34\u7684\u5355\u5143\u683c...';
exports.shiftCellsRight = '\u6d3b\u52a8\u5355\u5143\u683c\u53f3\u79fb';
exports.shiftCellsDown = '\u6d3b\u52a8\u5355\u5143\u683c\u4e0b\u79fb';
exports.headerInsertCopiedCells = '\u63d2\u5165\u590d\u5236\u7684\u5355\u5143\u683c';
exports.headerInsertCutCells = '\u63d2\u5165\u526a\u8d34\u7684\u5355\u5143\u683c';
exports.sortAscend = '\u4eceA\u5230Z';
exports.sortDescend = '\u4eceZ\u5230A';
exports.slicerSortAscend = "\u4eceA\u5230Z";
exports.slicerSortDescend = "\u4eceZ\u5230A";
exports.hideRows = "\u9690\u85cf";
exports.hideColumns = "\u9690\u85cf";
exports.hideSheet = '\u9690\u85cf';
exports.unhideSheet = '\u663e\u793a';
exports.unhideColumns = "\u663e\u793a";
exports.unhideRows = "\u663e\u793a";
exports.editComment = '\u7f16\u8f91\u6279\u6ce8';
exports.deleteComment = '\u5220\u9664\u6279\u6ce8';
exports.toggleComment = '\u663e\u793a/\u9690\u85cf \u6279\u6ce8';
exports.removeSlicer = '\u79fb\u9664';
exports.removeFloatingObject = '\u79fb\u9664';
exports.tableInsert = "\u63d2\u5165";
exports.tableInsertRowsAbove = "\u5728\u4e0a\u65b9\u63d2\u5165\u8868\u884c(A)";
exports.tableInsertRowsBelow = "\u5728\u4e0b\u65b9\u63d2\u5165\u8868\u884c(B)";
exports.tableInsertColumnsLeft = "\u5728\u5de6\u4fa7\u63d2\u5165\u8868\u5217(L)";
exports.tableInsertColumnsRight = "\u5728\u53f3\u4fa7\u63d2\u5165\u8868\u5217(R)";
exports.tableDelete = "\u5220\u9664";
exports.tableDeleteRows = "\u8868\u884c(R)";
exports.tableDeleteOrInsertEntireSheetRow = "\u5de5\u4f5c\u8868\u6574\u884c";
exports.tableDeleteColumns = "\u8868\u5217(C)";
exports.hideSheetFailureInfo = "\u5de5\u4f5c\u7c3f\u5185\u81f3\u5c11\u542b\u6709\u4e00\u5f20\u53ef\u89c6\u5de5\u4f5c\u8868\u3002\n\u5982\u8981\u9690\u85cf\u3001\u5220\u9664\u6216\u79fb\u52a8\u9009\u5b9a\u7684\u5de5\u4f5c\u8868\uff0c\u5fc5\u987b\u5148\u63d2\u5165\u4e00\u5f20\u65b0\u5de5\u4f5c\u8868\u6216\u91cd\u65b0\u663e\u793a\u4e00\u5f20\u5df2\u88ab\u9690\u85cf\u7684\u5de5\u4f5c\u8868\u3002";
exports.pivotTableRefresh = "\u5237\u65b0";
exports.pivotTableMove = "\u79fb\u52a8";
exports.pivotTableRemove_ = "\u5220\u9664 {0}";
exports.pivotTableRemoveGrandTotal = "\u5220\u9664\u603b\u8ba1";
exports.pivotTableValueFieldSettings = "\u503c\u5b57\u6bb5\u8bbe\u7f6e...";
exports.pivotTableOptions = "\u6570\u636e\u900f\u89c6\u8868\u9009\u9879...";
exports.pivotTableNumberFormat = "\u6570\u5b57\u683c\u5f0f...";
exports.pivotTableSort = "\u6392\u5e8f ";
exports.pivotTableSummarizeValuesBy = "\u503c\u6c47\u603b\u4f9d\u636e";
exports.pivotTableSum = "\u6c42\u548c";
exports.pivotTableCount = "\u8ba1\u6570";
exports.pivotTableAverage = "\u5e73\u5747\u503c";
exports.pivotTableMax = "\u6700\u5927\u503c";
exports.pivotTableMin = "\u6700\u5c0f\u503c";
exports.pivotTableProduct = "\u4e58\u79ef";
exports.pivotTableDistinctCount = "\u975e\u91cd\u590d\u8ba1\u6570";
exports.pivotTableMoreOptions = "\u5176\u4ed6\u9009\u9879";
exports.pivotTableShowValueAs = "\u503c\u663e\u793a\u65b9\u5f0f";
exports.pivotTableNoCalculation = "\u65e0\u8ba1\u7b97";
exports.pivotTableGrandTotal = "\u603b\u8ba1\u7684\u767e\u5206\u6bd4";
exports.pivotTableColumnTotal = "\u5217\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.pivotTableRowTotal = "\u884c\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.pivotTableOf = "\u767e\u5206\u6bd4...";
exports.pivotTableParentRowTotal = "\u7236\u884c\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.pivotTableParentColumnTotal = "\u7236\u5217\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.pivotTableOfParentTotal = "\u7236\u7ea7\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.pivotTableDifferenceFrom = "\u5dee\u5f02...";
exports.pivotTablePercentDifferenceFrom = "\u5dee\u5f02\u767e\u5206\u6bd4...";
exports.pivotTableRunningTotalIN = "\u6309\u67d0\u4e00\u5b57\u6bb5\u6c47\u603b...";
exports.pivotTablePercentRunningTotalIn = "\u6309\u67d0\u4e00\u5b57\u6bb5\u6c47\u603b\u7684\u767e\u5206\u6bd4...";
exports.pivotTableRankSmallestToLargest = "\u5347\u5e8f\u6392\u5217...";
exports.pivotTableRankLargestToSmallest = "\u964d\u5e8f\u6392\u5217...";
exports.pivotTableIndex = "\u6307\u6570";
exports.pivotTableShowDetails = "\u663e\u793a\u8be6\u7ec6\u4fe1\u606f";
exports.pivotTableGroup = "\u7ec4\u5408...";
exports.pivotTableUnGroup = "\u53d6\u6d88\u7ec4\u5408...";
exports.pivotTableExpandOrCollapse = "\u5c55\u5f00/\u6298\u53e0";
exports.pivotTableFilter = "\u7b5b\u9009";
exports.pivotTableSubtotal_ = "\u5206\u7c7b\u6c47\u603b {0}";
exports.pivotTableHideFieldList = "\u9690\u85cf\u5b57\u6bb5\u5217\u8868";
exports.pivotTableFieldSettings = "\u5b57\u6bb5\u8bbe\u7f6e...";
exports.pivotTableClearFilterFrom_ = "\u4ece {0} \u4e2d\u6e05\u9664\u7b5b\u9009";
exports.pivotTableKeepOnlySelectedItems = "\u4ec5\u4fdd\u7559\u6240\u9009\u9879\u76ee";
exports.pivotTableHideSelectedItems = "\u9690\u85cf\u6240\u9009\u9879\u76ee";
exports.pivotTableTop10 = "\u524d10\u4e2a...";
exports.pivotTableLabelFilters = "\u6807\u7b7e\u7b5b\u9009...";
exports.pivotTableLabelFilters_Date = "\u65e5\u671f\u7b5b\u9009...";
exports.pivotTableValueFilters = "\u503c\u7b5b\u9009...";
exports.pivotTableExpand = "\u5c55\u5f00";
exports.pivotTableCollapse = "\u6298\u53e0";
exports.pivotTableExpandEntireField = "\u5c55\u5f00\u6574\u4e2a\u5b57\u6bb5";
exports.pivotTableCollapseEntireField = "\u6298\u53e0\u6574\u4e2a\u5b57\u6bb5";
exports.pivotTableCollapseTo_ = "\u6298\u53e0\u81f3{0}";
exports.pivotTableExpandTo_ = "\u5c55\u5f00\u81f3{0}";
exports.pivotTableMove_ToBeginning = "\u79fb\u52a8{0}\u81f3\u5f00\u5934";
exports.pivotTableMove_Up = "\u5c06{0}\u4e0a\u79fb";
exports.pivotTableMove_Down = "\u5c06{0}\u4e0b\u79fb";
exports.pivotTableMove_ToEnd = "\u5c06{0}\u79fb\u81f3\u672b\u5c3e";
exports.pivotTableMove_ToLeft = "\u5c06{0}\u5de6\u79fb";
exports.pivotTableMove_ToRight = "\u5c06{0}\u53f3\u79fb";
exports.pivotTableMove_ToColumns = "\u5c06{0}\u79fb\u81f3\u5c3e\u5217";
exports.sigmaValue = "\u2211 \u6570\u503c";
exports.sigmaValueTemp = "\u6570\u503c";
exports.pivotTableSortAscend = '\u5347\u5e8f';
exports.pivotTableSortDescend = '\u964d\u5e8f';
exports.pivotTableSortMoreOptions = '\u5176\u4ed6\u6392\u5e8f\u9009\u9879...';
exports.pinRows = "\u7f6e\u9876/\u53d6\u6d88\u7f6e\u9876 \u884c";
exports.pinColumns = "\u7f6e\u9876/\u53d6\u6d88\u7f6e\u9876 \u5217";
exports.promote = "\u664b\u7ea7";
exports.demote = "\u964d\u7ea7";
exports.moveUp = "\u4e0a\u79fb";
exports.moveDown = "\u4e0b\u79fb";
exports.addAfter = "\u5728\u4e0b\u65b9\u63d2\u5165";
exports.addBefore = "\u5728\u4e0a\u65b9\u63d2\u5165";
exports.addAbove = "\u6dfb\u52a0\u4e0a\u7ea7";
exports.addBelow = "\u6dfb\u52a0\u4e0b\u7ea7";
exports.expandAllLevels = "\u5c55\u5f00\u5168\u90e8\u7ea7\u522b";
exports.collapseAllLevels = "\u6298\u53e0\u5168\u90e8\u7ea7\u522b";
exports.expandLevel1 = "\u5c55\u5f00\u5230\u7ea7\u522b1";
exports.expandLevel2 = "\u5c55\u5f00\u5230\u7ea7\u522b2";
exports.expandLevel3 = "\u5c55\u5f00\u5230\u7ea7\u522b3";
exports.expandLevel4 = "\u5c55\u5f00\u5230\u7ea7\u522b4";
exports.expandLevel5 = "\u5c55\u5f00\u5230\u7ea7\u522b5";
exports.expandLevel6 = "\u5c55\u5f00\u5230\u7ea7\u522b6";
exports.expandLevel7 = "\u5c55\u5f00\u5230\u7ea7\u522b7";
exports.expandLevel8 = "\u5c55\u5f00\u5230\u7ea7\u522b8";
exports.expandLevel9 = "\u5c55\u5f00\u5230\u7ea7\u522b9";
exports.paste = "\u7c98\u8d34";

exports.reportSheetAddRecord = '\u6dfb\u52a0\u8bb0\u5f55';
exports.reportSheetDeleteRecord = '\u5220\u9664\u8bb0\u5f55';
exports.reportSheetSubmit = '\u63d0\u4ea4';
exports.reportSheetResetCellValue = '\u91cd\u7f6e\u5355\u5143\u683c\u6570\u636e';

exports.scrollToTasks = "\u6eda\u52a8\u5230\u4efb\u52a1";
exports.insertTasks = "\u63d2\u5165\u4efb\u52a1";
exports.deleteTasks = "\u5220\u9664\u4efb\u52a1";
exports.indentTasks = "\u964d\u7ea7\u4efb\u52a1";
exports.outdentTasks = "\u5347\u7ea7\u4efb\u52a1";
exports.manuallySchedule = "\u81ea\u52a8\u5b89\u6392";
exports.autoSchedule = "\u624b\u52a8\u5b89\u6392";
exports.hideColumn = "\u9690\u85cf\u5217";
exports.unhideColumn = "\u663e\u793a\u5217";


/***/ }),

/***/ "./dist/plugins/data/data.res.zh.js":
/*!******************************************!*\
  !*** ./dist/plugins/data/data.res.zh.js ***!
  \******************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_NotSupportedDataSource = void 0;
exports.Exp_NotSupportedDataSource = '\u6570\u636e\u6e90\u4e0d\u652f\u6301\uff01';


/***/ }),

/***/ "./dist/plugins/exportPDF/printPdf.res.zh.js":
/*!***************************************************!*\
  !*** ./dist/plugins/exportPDF/printPdf.res.zh.js ***!
  \***************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_FontError = exports.Exp_FileIOError = void 0;
exports.Exp_FileIOError = '\u6587\u4ef6\u8bfb\u5199\u5f02\u5e38\u3002';
exports.Exp_FontError = '\u975e\u652f\u6301\u5b57\u4f53\u683c\u5f0f\u6216\u975e\u6807\u51c6PDF\u5b57\u4f53\u3002';


/***/ }),

/***/ "./dist/plugins/fill/fill.res.zh.js":
/*!******************************************!*\
  !*** ./dist/plugins/fill/fill.res.zh.js ***!
  \******************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_ChangePartOfArray = exports.Exp_RangeIsNull = exports.Exp_CellReadOnly = exports.Exp_RowReadOnly = exports.Exp_ColumnReadOnly = exports.Exp_ChangeMergedCell = exports.Exp_FillCellsReadOnly = exports.Exp_FillRangeContainsMergedCell = exports.Exp_MergedCellsIdentical = exports.Exp_TargetContainsMergedCells = exports.Exp_RangeContainsMergedCell = exports.Exp_NumberOnly = exports.FillWithoutFormatting = exports.FillFormattingOnly = exports.FillSeries = exports.CopyCells = void 0;
exports.CopyCells = '\u590d\u5236\u5355\u5143\u683c';
exports.FillSeries = '\u4ee5\u5e8f\u5217\u65b9\u5f0f\u586b\u5145';
exports.FillFormattingOnly = '\u4ec5\u586b\u5145\u683c\u5f0f';
exports.FillWithoutFormatting = '\u4e0d\u5e26\u683c\u5f0f\u586b\u5145';
exports.Exp_NumberOnly = '\u4ec5\u5bf9\u6570\u5b57\u6709\u6548';
exports.Exp_RangeContainsMergedCell = '\u533a\u57df\u5185\u4e0d\u5e94\u5305\u542b\u5408\u5e76\u5355\u5143\u683c\u3002';
exports.Exp_TargetContainsMergedCells = '\u76ee\u6807\u533a\u57df\u4e0d\u5e94\u5305\u542b\u5408\u5e76\u5355\u5143\u683c\u3002';
exports.Exp_MergedCellsIdentical = '\u6b64\u884c\u4e3a\u9700\u8981\u5408\u5e76\u5355\u5143\u683c\u7684\u5927\u5c0f\u4e00\u81f4\u3002';
exports.Exp_FillRangeContainsMergedCell = '\u65e0\u6cd5\u586b\u5145\u4e00\u7247\u5305\u542b\u5408\u5e76\u5355\u5143\u683c\u7684\u533a\u57df\u3002';
exports.Exp_FillCellsReadOnly = '\u60a8\u5c1d\u8bd5\u586b\u5145\u7684\u5355\u5143\u683c\u662f\u88ab\u4fdd\u62a4\u7684\uff0c\u56e0\u6b64\u5b83\u662f\u53ea\u8bfb\u7684\u3002';
exports.Exp_ChangeMergedCell = '\u65e0\u6cd5\u4ec5\u6539\u53d8\u5408\u5e76\u5355\u5143\u683c\u7684\u4e00\u90e8\u5206';
exports.Exp_ColumnReadOnly = '\u60a8\u5c1d\u8bd5\u4fee\u6539\u7684\u5217\u662f\u88ab\u4fdd\u62a4\u7684\uff0c\u56e0\u6b64\u5b83\u662f\u53ea\u8bfb\u7684\u3002';
exports.Exp_RowReadOnly = '\u60a8\u5c1d\u8bd5\u4fee\u6539\u7684\u884c\u662f\u88ab\u4fdd\u62a4\u7684\uff0c\u56e0\u6b64\u5b83\u662f\u53ea\u8bfb\u7684\u3002';
exports.Exp_CellReadOnly = '\u60a8\u5c1d\u8bd5\u4fee\u6539\u7684\u5355\u5143\u683c\u662f\u88ab\u4fdd\u62a4\u7684\uff0c\u56e0\u6b64\u5b83\u662f\u53ea\u8bfb\u7684\u3002';
exports.Exp_RangeIsNull = '\u533a\u95f4\u4e3a\u7a7a';
exports.Exp_ChangePartOfArray = '\u65e0\u6cd5\u4ec5\u6539\u53d8\u90e8\u5206\u7684\u6570\u7ec4\u3002';


/***/ }),

/***/ "./dist/plugins/filter/filter.res.zh.js":
/*!**********************************************!*\
  !*** ./dist/plugins/filter/filter.res.zh.js ***!
  \**********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ThisWeek = exports.NextWeek = exports.Yesterday = exports.Today = exports.Tomorrow = exports.After = exports.Before = exports.NotContain = exports.Contain = exports.End = exports.Begin = exports.BelowAverage = exports.AboveAverage = exports.Top10 = exports.Between = exports.LessThanOrEquals = exports.LessThan = exports.GreaterOrEquals = exports.GreaterThan = exports.NotEqual = exports.Equal = exports.Custom = exports.NumberFilter = exports.DateFilter = exports.TextFilter = exports.Clear = exports.Automatic = exports.NoFill = exports.CellColor = exports.FontColor = exports.SortFontTitle = exports.SortCellTitle = exports.FilterFontTitle = exports.FilterCellTitle = exports.FilterColor = exports.SortColor = exports.Or = exports.And = exports.ShowRows = exports.Show = exports.Exp_FilterItemIsNull = exports.Blanks = exports.UncheckAll = exports.CheckAll = exports.Search = exports.Cancel = exports.OK = exports.SortDescending = exports.SortAscending = exports.Exp_InvalidColumnIndex = void 0;
exports.Aug = exports.Jul = exports.Jun = exports.May = exports.Apr = exports.Mar = exports.Feb = exports.Jan = exports.Q4 = exports.Q3 = exports.Q2 = exports.Q1 = exports.BeforeOrEqual = exports.IsBefore = exports.AfterOrEqual = exports.IsAfter = exports.NotContains = exports.IsContain = exports.NotEndWith = exports.IsEndWith = exports.NotBeginWith = exports.IsBeginWith = exports.LessOrEqual = exports.IsLess = exports.IsGreaterOrEqual = exports.IsGreaterThan = exports.NotEquals = exports.IsEquals = exports.Selected = exports.FilterFont = exports.FilterCell = exports.SortFont = exports.SortCell = exports.bottom = exports.top = exports.ColorTitle = exports.CustomTitle = exports.Top10Filter = exports.AllDates = exports.YearToDate = exports.LastYear = exports.ThisYear = exports.NextYear = exports.LastQuarter = exports.ThisQuarter = exports.NextQuarter = exports.LastMonth = exports.ThisMonth = exports.NextMonth = exports.LastWeek = void 0;
exports.invalidCondition = exports.add_current_filter = exports.Day = exports.Year = exports.Explain2 = exports.Explain1 = exports.Dec = exports.Nov = exports.Oct = exports.Sep = void 0;
exports.Exp_InvalidColumnIndex = '\u65e0\u6548\u5217\u7d22\u5f15\u3002';
exports.SortAscending = '\u5347\u5e8f';
exports.SortDescending = '\u964d\u5e8f';
exports.OK = '\u786e\u5b9a';
exports.Cancel = '\u53d6\u6d88';
exports.Search = '\u641c\u7d22';
exports.CheckAll = '\u5168\u9009';
exports.UncheckAll = '\u53d6\u6d88\u5168\u9009';
exports.Blanks = '(\u7a7a)';
exports.Exp_FilterItemIsNull = '\u7b5b\u9009\u9879\u4e3a\u7a7a\u3002';
exports.Show = '\u663e\u793a';
exports.ShowRows = '\u663e\u793a\u884c:';
exports.And = '\u4e0e';
exports.Or = '\u6216';
exports.SortColor = '\u6309\u989c\u8272\u6392\u5e8f';
exports.FilterColor = '\u6309\u989c\u8272\u7b5b\u9009';
exports.FilterCellTitle = '\u6309\u5355\u5143\u683c\u989c\u8272\u7b5b\u9009';
exports.FilterFontTitle = '\u6309\u5b57\u4f53\u989c\u8272\u7b5b\u9009';
exports.SortCellTitle = '\u6309\u5355\u5143\u683c\u989c\u8272\u6392\u5e8f';
exports.SortFontTitle = '\u6309\u5b57\u4f53\u989c\u8272\u6392\u5e8f';
exports.FontColor = '\u5176\u4ed6\u5b57\u4f53\u989c\u8272...';
exports.CellColor = '\u5176\u4ed6\u5355\u5143\u683c\u989c\u8272...';
exports.NoFill = '\u65e0\u586b\u5145';
exports.Automatic = '\u81ea\u52a8';
exports.Clear = '\u4ece{0}\u4e2d\u6e05\u9664\u7b5b\u9009';
exports.TextFilter = '\u6587\u672c\u7b5b\u9009';
exports.DateFilter = '\u65e5\u671f\u7b5b\u9009';
exports.NumberFilter = '\u6570\u5b57\u7b5b\u9009';
exports.Custom = '\u81ea\u5b9a\u4e49\u7b5b\u9009...';
exports.Equal = '\u7b49\u4e8e...';
exports.NotEqual = '\u4e0d\u7b49\u4e8e...';
exports.GreaterThan = '\u5927\u4e8e...';
exports.GreaterOrEquals = '\u5927\u4e8e\u6216\u7b49\u4e8e...';
exports.LessThan = '\u5c0f\u4e8e...';
exports.LessThanOrEquals = '\u5c0f\u4e8e\u6216\u7b49\u4e8e...';
exports.Between = '\u4ecb\u4e8e...';
exports.Top10 = '\u524d10\u9879...';
exports.AboveAverage = '\u9ad8\u4e8e\u5e73\u5747\u503c';
exports.BelowAverage = '\u4f4e\u4e8e\u5e73\u5747\u503c';
exports.Begin = '\u5f00\u5934\u662f...';
exports.End = '\u7ed3\u5c3e\u662f...';
exports.Contain = '\u5305\u542b...';
exports.NotContain = '\u4e0d\u5305\u542b...';
exports.Before = '\u4e4b\u524d...';
exports.After = '\u4e4b\u540e...';
exports.Tomorrow = '\u660e\u5929';
exports.Today = '\u4eca\u5929';
exports.Yesterday = '\u6628\u5929';
exports.NextWeek = '\u4e0b\u5468';
exports.ThisWeek = '\u672c\u5468';
exports.LastWeek = '\u4e0a\u5468';
exports.NextMonth = '\u4e0b\u6708';
exports.ThisMonth = '\u672c\u6708';
exports.LastMonth = '\u4e0a\u6708';
exports.NextQuarter = '\u4e0b\u5b63\u5ea6';
exports.ThisQuarter = '\u672c\u5b63\u5ea6';
exports.LastQuarter = '\u4e0a\u5b63\u5ea6';
exports.NextYear = '\u660e\u5e74';
exports.ThisYear = '\u4eca\u5e74';
exports.LastYear = '\u53bb\u5e74';
exports.YearToDate = '\u672c\u5e74\u5ea6\u622a\u6b62\u5230\u73b0\u5728';
exports.AllDates = '\u671f\u95f4\u6240\u6709\u65e5\u671f';
exports.Top10Filter = '\u81ea\u52a8\u7b5b\u9009\u524d10\u4e2a';
exports.CustomTitle = '\u81ea\u5b9a\u4e49\u81ea\u52a8\u7b5b\u9009\u65b9\u5f0f';
exports.ColorTitle = '\u53ef\u7528\u5355\u5143\u683c\u989c\u8272';
exports.top = "\u6700\u5927";
exports.bottom = '\u6700\u5c0f';
exports.SortCell = '\u8bf7\u9009\u62e9\u4f5c\u4e3a\u6392\u5e8f\u4f9d\u636e\u7684\u5355\u5143\u683c\u989c\u8272:';
exports.SortFont = '\u9009\u62e9\u7528\u4f5c\u6392\u5e8f\u4f9d\u636e\u7684\u5b57\u4f53\u989c\u8272:';
exports.FilterCell = '\u8bf7\u9009\u62e9\u4f5c\u4e3a\u7b5b\u9009\u4f9d\u636e\u7684\u5355\u5143\u683c\u989c\u8272:';
exports.FilterFont = '\u8bf7\u9009\u62e9\u4f5c\u4e3a\u7b5b\u9009\u4f9d\u636e\u7684\u5b57\u4f53\u989c\u8272:';
exports.Selected = '\u5df2\u9009\u5b9a:';
exports.IsEquals = '\u7b49\u4e8e';
exports.NotEquals = '\u4e0d\u7b49\u4e8e';
exports.IsGreaterThan = '\u5927\u4e8e';
exports.IsGreaterOrEqual = '\u5927\u4e8e\u6216\u7b49\u4e8e';
exports.IsLess = '\u5c0f\u4e8e';
exports.LessOrEqual = '\u5c0f\u4e8e\u6216\u7b49\u4e8e';
exports.IsBeginWith = '\u5f00\u5934\u662f';
exports.NotBeginWith = '\u5f00\u5934\u4e0d\u662f';
exports.IsEndWith = '\u7ed3\u5c3e\u662f';
exports.NotEndWith = '\u7ed3\u5c3e\u4e0d\u662f';
exports.IsContain = '\u5305\u542b';
exports.NotContains = '\u4e0d\u5305\u542b';
exports.IsAfter = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u540e';
exports.AfterOrEqual = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u540e\u6216\u4e0e\u4e4b\u76f8\u540c';
exports.IsBefore = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u524d';
exports.BeforeOrEqual = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u524d\u6216\u4e0e\u4e4b\u76f8\u540c';
exports.Q1 = '\u7b2c 1 \u5b63\u5ea6';
exports.Q2 = '\u7b2c 2 \u5b63\u5ea6';
exports.Q3 = '\u7b2c 3 \u5b63\u5ea6';
exports.Q4 = '\u7b2c 4 \u5b63\u5ea6';
exports.Jan = '\u4e00\u6708';
exports.Feb = '\u4e8c\u6708';
exports.Mar = '\u4e09\u6708';
exports.Apr = '\u56db\u6708';
exports.May = '\u4e94\u6708';
exports.Jun = '\u516d\u6708';
exports.Jul = '\u4e03\u6708';
exports.Aug = '\u516b\u6708';
exports.Sep = '\u4e5d\u6708';
exports.Oct = '\u5341\u6708';
exports.Nov = '\u5341\u4e00\u6708';
exports.Dec = '\u5341\u4e8c\u6708';
exports.Explain1 = '\u53ef\u7528 ? \u4ee3\u8868\u5355\u4e2a\u5b57\u7b26';
exports.Explain2 = '\u7528 * \u4ee3\u8868\u4efb\u610f\u591a\u4e2a\u5b57\u7b26';
exports.Year = '';
exports.Day = '';
exports.add_current_filter = '\u5c06\u5f53\u524d\u9009\u62e9\u6dfb\u52a0\u5230\u7b5b\u9009\u5668';
exports.invalidCondition = '\u5206\u5217\u7ebf\u9519\u8bef';


/***/ }),

/***/ "./dist/plugins/floatingObject/floatingobject.res.zh.js":
/*!**************************************************************!*\
  !*** ./dist/plugins/floatingObject/floatingobject.res.zh.js ***!
  \**************************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_FloatingObjectNameEmptyError = exports.Exp_FloatingObjectHasSameNameError = void 0;
exports.Exp_FloatingObjectHasSameNameError = '\u5f53\u524d\u8868\u5355\u5df2\u7ecf\u5305\u542b\u4e00\u4e2a\u5177\u6709\u76f8\u540c\u540d\u79f0\u7684\u6d6e\u52a8\u5bf9\u8c61\u3002';
exports.Exp_FloatingObjectNameEmptyError = '\u6d6e\u52a8\u5bf9\u8c61\u5fc5\u987b\u5177\u6709\u540d\u79f0\u3002';


/***/ }),

/***/ "./dist/plugins/formulaPanel/editor/editor.res.zh.js":
/*!***********************************************************!*\
  !*** ./dist/plugins/formulaPanel/editor/editor.res.zh.js ***!
  \***********************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.FORMULA = exports.INVALID_FORMULA = exports.INVALID_EXPRESSION = exports.DUPLICATE_IDENTIFIER = exports.INVALID_IDENTIFIER_ARGUMENT = exports.NO_MATCHED_ARGUMENTS = exports.UNKNOWN_SHEET = exports.UNKNOWN_NAME = exports.MISS_LET_FUNCTION_BODY = exports.MISS_RIGHT_PARENTHESES = exports.NO_MATH_PARENTHESES = void 0;
exports.NO_MATH_PARENTHESES = '\u6ca1\u6709\u5339\u914d\u7684\u62ec\u53f7';
exports.MISS_RIGHT_PARENTHESES = "\u671f\u671b\u4e3a')'";
exports.MISS_LET_FUNCTION_BODY = "\u7f3a\u5c11 LET \u8868\u8fbe\u5f0f\u7684\u6b63\u6587\u3002";
exports.UNKNOWN_NAME = '\u672a\u77e5\u540d\u79f0';
exports.UNKNOWN_SHEET = '\u672a\u77e5\u5de5\u4f5c\u8868 "$1"';
exports.NO_MATCHED_ARGUMENTS = '\u9884\u671f $1 \u53c2\u6570\uff0c\u4f46\u5f97\u5230 $2';
exports.INVALID_IDENTIFIER_ARGUMENT = '\u6b64\u53c2\u6570\u5fc5\u987b\u662f\u8981\u7ed1\u5b9a\u7684\u6807\u8bc6\u7b26\u3002';
exports.DUPLICATE_IDENTIFIER = '\u91cd\u590d\u6807\u8bc6\u7b26 $1';
exports.INVALID_EXPRESSION = '\u8868\u8fbe\u5f0f\u65e0\u6548';
exports.INVALID_FORMULA = '\u516c\u5f0f\u65e0\u6548';
exports.FORMULA = '\u516c\u5f0f';


/***/ }),

/***/ "./dist/plugins/gantt/gantt.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/gantt/gantt.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.GANTT_ACTION_FROM_OF = exports.GANTT_ACTION_FINISH_LINK = exports.GANTT_ACTION_START_LINK = exports.GANTT_ACTION_TASK_FINISH = exports.GANTT_ACTION_TASK_START = exports.GANTT_ACTION_FINISH = exports.GANTT_ACTION_START = exports.GANTT_ACTION_TASK_COLON = exports.GANTT_ACTION_TASK = exports.DEFAULT_DATE_TIME_FORMAT = exports.GANTT_VALUE_READONLY = exports.GANTT_VALUE_INVALID = exports.GANTT_INVALID_ASCENDING_STATES = exports.GANTT_INVALID_FIELD = exports.GANTT_INVALID_TASK_LEVEL = exports.GANTT_INVALID_TASK_PARENT_ID = exports.GANTT_INVALID_TASK_ID = exports.GANTT_INVALID_WORK_DAYS = exports.GANTT_INVALID_UNIT = exports.GANTT_INVALID_DEPENDENCY = exports.GANTT_INVALID_TASK = exports.GANTT_INVALID_COUNT = exports.GANTT_INVALID_INDEX = exports.GANTT_INVALID_ROW_INDEX = exports.GANTT_INVALID_ID = exports.GANTT_INVALID_LEVEL = exports.GANTT_INVALID_OPERATION = exports.GANTT_TASK_SCHEDULE_MODE_AUTO = exports.GANTT_TASK_SCHEDULE_MODE_MANUALLY = exports.GANTT_COMPLETE_CHANGE = exports.GANTT_COMPLETE = exports.GANTT_SUCCESSORS = exports.GANTT_PREDECESSORS = exports.GANTT_FINISH_DISPLAYED = exports.GANTT_START_DISPLAYED = exports.GANTT_FINISH = exports.GANTT_START = exports.GANTT_DURATION = exports.GANTT_MODE = exports.GANTT_MILESTONE = exports.GANTT_TASK_NAME = exports.GANTT_TASK_NUMBER = exports.GANTT_NEW_MILESTONE = exports.GANTT_NEW_SUMMARY_TASK = exports.GANTT_NEW_TASK = exports.GANTT_MINUTE = exports.GANTT_HOUR = exports.GANTT_DAY = exports.GANTT_WEEK = exports.GANTT_MONTH = void 0;
exports.GANTT_CALENDAR_UNIT_LABELS_PLURALS = exports.GANTT_CALENDAR_UNIT_LABELS = exports.GANTT_CALENDAR_MONTH_NAMES = exports.GANTT_CALENDAR_MONTH = exports.GANTT_CALENDAR_WEEK_NAMES = exports.GANTT_CALENDAR_WEEK = exports.GANTT_CALENDAR_DAY_NAMES = exports.GANTT_CALENDAR_DAY = exports.GANTT_CALENDAR_HOUR_NAMES = exports.GANTT_CALENDAR_HOUR = exports.GANTT_CALENDAR_MINUTE_NAMES = exports.GANTT_CALENDAR_MINUTE = exports.GANTT_CALENDAR_24HOURS = exports.GANTT_CALENDAR_NIGHT_SHIFT = exports.GANTT_CALENDAR_STANDARD = exports.GANTT_TASK_BAR_COMPLETE = exports.GANTT_TASK_BAR_COMPLETE_THROUGH = exports.GANTT_TASK_BAR_UNNAMED = exports.GANTT_TASK_BAR_SS = exports.GANTT_TASK_BAR_SF = exports.GANTT_TASK_BAR_FF = exports.GANTT_TASK_BAR_FS = exports.GANTT_TASK_BAR_TO = exports.GANTT_TASK_BAR_FROM = exports.GANTT_TASK_BAR_TASK_LINK = exports.GANTT_ACTION_LINE_TIP_TITLE = exports.GANTT_ACTION_COMPLETE_THROUGH = exports.GANTT_ACTION_DURATION = exports.GANTT_ACTION_FINISH_ONLY = exports.GANTT_ACTION_START_ONLY = exports.GANTT_ACTION_TO_OF = void 0;
exports.GANTT_MONTH = "\u6708";
exports.GANTT_WEEK = "\u5468";
exports.GANTT_DAY = "\u5929";
exports.GANTT_HOUR = "\u5c0f\u65f6";
exports.GANTT_MINUTE = "\u5206\u949f";
exports.GANTT_NEW_TASK = "<\u65b0\u4efb\u52a1>";
exports.GANTT_NEW_SUMMARY_TASK = "<\u65b0\u6458\u8981\u4efb\u52a1>";
exports.GANTT_NEW_MILESTONE = "<\u65b0\u91cc\u7a0b\u7891>";
exports.GANTT_TASK_NUMBER = "\u4efb\u52a1\u7d22\u5f15";
exports.GANTT_TASK_NAME = "\u4efb\u52a1\u540d\u79f0";
exports.GANTT_MILESTONE = "\u91cc\u7a0b\u7891";
exports.GANTT_MODE = "\u6a21\u5f0f";
exports.GANTT_DURATION = "\u5de5\u671f";
exports.GANTT_START = "\u5f00\u59cb\u65f6\u95f4";
exports.GANTT_FINISH = "\u5b8c\u6210\u65f6\u95f4";
exports.GANTT_START_DISPLAYED = "\u5f00\u59cb\u8868\u793a\u65f6\u95f4";
exports.GANTT_FINISH_DISPLAYED = "\u5b8c\u6210\u8868\u793a\u65f6\u95f4";
exports.GANTT_PREDECESSORS = "\u524d\u7f6e\u4efb\u52a1";
exports.GANTT_SUCCESSORS = "\u540e\u7eed\u4efb\u52a1";
exports.GANTT_COMPLETE = "\u5b8c\u6210\u767e\u5206\u6bd4";
exports.GANTT_COMPLETE_CHANGE = "\u5b9e\u9645\u5b8c\u6210\u767e\u5206\u6bd4";
exports.GANTT_TASK_SCHEDULE_MODE_MANUALLY = "\u624b\u52a8";
exports.GANTT_TASK_SCHEDULE_MODE_AUTO = "\u81ea\u52a8";
exports.GANTT_INVALID_OPERATION = "\u65e0\u6548\u64cd\u4f5c";
exports.GANTT_INVALID_LEVEL = "\u65e0\u6548\u7ea7\u522b";
exports.GANTT_INVALID_ID = "\u65e0\u6548\u6807\u8bc6";
exports.GANTT_INVALID_ROW_INDEX = "\u65e0\u6548\u884c\u7d22\u5f15";
exports.GANTT_INVALID_INDEX = "\u65e0\u6548\u7d22\u5f15";
exports.GANTT_INVALID_COUNT = "\u65e0\u6548\u4efb\u52a1\u6570\u91cf";
exports.GANTT_INVALID_TASK = "\u65e0\u6548\u4efb\u52a1";
exports.GANTT_INVALID_DEPENDENCY = "\u65e0\u6548\u4f9d\u8d56";
exports.GANTT_INVALID_UNIT = "\u65e0\u6548\u5355\u4f4d";
exports.GANTT_INVALID_WORK_DAYS = "\u65e0\u6548\u5de5\u4f5c\u65e5";
exports.GANTT_INVALID_TASK_ID = "\u65e0\u6548\u4efb\u52a1\u6807\u8bc6";
exports.GANTT_INVALID_TASK_PARENT_ID = "\u65e0\u6548\u4efb\u52a1\u7236\u7c7b\u6807\u8bc6";
exports.GANTT_INVALID_TASK_LEVEL = "\u65e0\u6548\u4efb\u52a1\u7ea7\u522b";
exports.GANTT_INVALID_FIELD = "\u65e0\u6548\u5b57\u6bb5";
exports.GANTT_INVALID_ASCENDING_STATES = "\u65e0\u6548\u6392\u5e8f\u72b6\u6001";
exports.GANTT_VALUE_INVALID = "\u503c\u65e0\u6548";
exports.GANTT_VALUE_READONLY = "\u53ea\u8bfb\u503c";
exports.DEFAULT_DATE_TIME_FORMAT = "yy-mm-dd h:mm";
exports.GANTT_ACTION_TASK = "\u4efb\u52a1";
exports.GANTT_ACTION_TASK_COLON = "\u4efb\u52a1:";
exports.GANTT_ACTION_START = "\u5f00\u59cb\u65f6\u95f4:";
exports.GANTT_ACTION_FINISH = "\u5b8c\u6210\u65f6\u95f4:";
exports.GANTT_ACTION_TASK_START = "\u4efb\u52a1\u5f00\u59cb\u65f6\u95f4:";
exports.GANTT_ACTION_TASK_FINISH = "\u4efb\u52a1\u5b8c\u6210\u65f6\u95f4:";
exports.GANTT_ACTION_START_LINK = "\u5f00\u59cb";
exports.GANTT_ACTION_FINISH_LINK = "\u5b8c\u6210";
exports.GANTT_ACTION_FROM_OF = "\u4ece\u6b64\u4efb\u52a1{0}";
exports.GANTT_ACTION_TO_OF = "\u5230\u6b64\u4efb\u52a1{0}";
exports.GANTT_ACTION_START_ONLY = "\u4ec5\u5f00\u59cb\u65f6\u95f4";
exports.GANTT_ACTION_FINISH_ONLY = "\u4ec5\u5b8c\u6210\u65f6\u95f4";
exports.GANTT_ACTION_DURATION = "\u5de5\u671f:";
exports.GANTT_ACTION_COMPLETE_THROUGH = "\u5b8c\u6210\u81f3\u6b64\u65f6\u95f4: ";
exports.GANTT_ACTION_LINE_TIP_TITLE = "\u4efb\u52a1\u94fe\u63a5\uff1a{0}-{1}";
exports.GANTT_TASK_BAR_TASK_LINK = "\u94fe\u63a5\u4efb\u52a1:";
exports.GANTT_TASK_BAR_FROM = "\u4efb\u52a1\u5f00\u59cb\u65f6\u95f4:";
exports.GANTT_TASK_BAR_TO = "\u4efb\u52a1\u5b8c\u6210\u65f6\u95f4:";
exports.GANTT_TASK_BAR_FS = "\u5b8c\u6210-\u5f00\u59cb (FS)";
exports.GANTT_TASK_BAR_FF = "\u5b8c\u6210-\u5b8c\u6210 (FF)";
exports.GANTT_TASK_BAR_SF = "\u5f00\u59cb-\u5b8c\u6210 (SF)";
exports.GANTT_TASK_BAR_SS = "\u5f00\u59cb-\u5f00\u59cb (SS)";
exports.GANTT_TASK_BAR_UNNAMED = "\u672a\u547d\u540d";
exports.GANTT_TASK_BAR_COMPLETE_THROUGH = "\u5b8c\u6210\u65f6\u95f4:";
exports.GANTT_TASK_BAR_COMPLETE = "\u5b8c\u6210\u767e\u5206\u6bd4:";
exports.GANTT_CALENDAR_STANDARD = "\u6807\u51c6";
exports.GANTT_CALENDAR_NIGHT_SHIFT = "\u591c\u73ed";
exports.GANTT_CALENDAR_24HOURS = "24 \u5c0f\u65f6";
exports.GANTT_CALENDAR_MINUTE = "\u5206\u949f";
exports.GANTT_CALENDAR_MINUTE_NAMES = ["m", "min", "minute"];
exports.GANTT_CALENDAR_HOUR = "\u5c0f\u65f6";
exports.GANTT_CALENDAR_HOUR_NAMES = ["h", "hr", "hour"];
exports.GANTT_CALENDAR_DAY = "\u5929";
exports.GANTT_CALENDAR_DAY_NAMES = ["d", "dy", "day"];
exports.GANTT_CALENDAR_WEEK = "\u5468";
exports.GANTT_CALENDAR_WEEK_NAMES = ["w", "wk", "week"];
exports.GANTT_CALENDAR_MONTH = "\u6708";
exports.GANTT_CALENDAR_MONTH_NAMES = ["mo", "mon", "month"];
exports.GANTT_CALENDAR_UNIT_LABELS = ["\u5206\u949f", "\u5c0f\u65f6", "\u5929", "\u5468", "\u6708", "\u5e74"];
exports.GANTT_CALENDAR_UNIT_LABELS_PLURALS = ["\u5206\u949f", "\u5c0f\u65f6", "\u5929", "\u5468", "\u6708", "\u5e74"];

var lr = __webpack_require__(/*! lrGtZh */ "./dist/plugins/gantt/lr.zh.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}


/***/ }),

/***/ "./dist/plugins/gantt/lr.zh.js":
/*!*************************************!*\
  !*** ./dist/plugins/gantt/lr.zh.js ***!
  \*************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.wmk2 = exports.wmk1 = void 0;
exports.wmk1 = ["757288", "187968"];
exports.wmk2 = ["757288", "187968"];


/***/ }),

/***/ "./dist/plugins/group/group.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/group/group.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_GROUP_PROTECTED = exports.Exp_GroupInfoIsNull = exports.Exp_InvalidLevel = exports.Exp_InvalidCount = exports.Exp_InvalidIndex = void 0;
exports.Exp_InvalidIndex = '\u65e0\u6548\u7d22\u5f15';
exports.Exp_InvalidCount = '\u65e0\u6548\u6570\u91cf';
exports.Exp_InvalidLevel = '\u65e0\u6548\u5c42\u7ea7';
exports.Exp_GroupInfoIsNull = 'groupInfo\u4e3a\u7a7a';
exports.Exp_GROUP_PROTECTED = "\u4e0d\u80fd\u5bf9\u53d7\u4fdd\u62a4\u7684\u5de5\u4f5c\u8868\u4f7f\u7528\u8be5\u547d\u4ee4\u3002\u82e5\u4f7f\u7528\u8be5\u547d\u4ee4\uff0c\u5fc5\u987b\u5148\u64a4\u9500\u5de5\u4f5c\u8868\u4fdd\u62a4\u3002";


/***/ }),

/***/ "./dist/plugins/inputMask/inputMask/inputMask.res.zh.js":
/*!**************************************************************!*\
  !*** ./dist/plugins/inputMask/inputMask/inputMask.res.zh.js ***!
  \**************************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.hourFormatDesignator = exports.hourFormatSingleDesignator = exports.errorMsg = void 0;
exports.errorMsg = {
    invalidPattern: '\u65e0\u6548\u7684\u683c\u5f0f',
    duplicatedDateTimePattern: "\u91CD\u590D\u7684\u65E5\u671F\u683C\u5F0F",
    emptyPattern: '\u7a7a\u7684\u683c\u5f0f',
    unknownPattern: '\u89e3\u6790\u672a\u77e5\u683c\u5f0f\u5931\u8d25',
    invalidOptionalPattern: '\u89e3\u6790\u683c\u5f0f\u4e2d\u7684\u53ef\u9009\u5173\u952e\u5b57\u5185\u5bb9\u5931\u8d25',
    invalidGroupPattern: '\u89e3\u6790\u683c\u5f0f\u4e2d\u7684\u7ec4\u5408\u5173\u952e\u5b57\u5185\u5bb9\u5931\u8d25',
    errorQuantifierPattern: '\u6570\u91cf\u5173\u952e\u5b57\u4f4d\u4e8e\u4e0d\u5408\u6cd5\u7684\u4f4d\u7f6e',
    invalidQuantifierPattern: '\u89e3\u6790\u683c\u5f0f\u4e2d\u7684\u6570\u91cf\u5173\u952e\u5b57\u5185\u5bb9\u5931\u8d25',
    noEntry: "\u6ca1\u6709\u53ef\u8f93\u5165\u7684\u9650\u5236"
};
exports.hourFormatSingleDesignator = ['\u4e0a', '\u4e0b'];
exports.hourFormatDesignator = ['\u4e0a\u5348', '\u4e0b\u5348'];


/***/ }),

/***/ "./dist/plugins/io/io.res.zh.js":
/*!**************************************!*\
  !*** ./dist/plugins/io/io.res.zh.js ***!
  \**************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.EXP_EMPTY_PASSWORD = exports.EXP_WRONG_PASSWORD = exports.GET_PARTIAL_VALUES_ERROR_MSG = exports.EXP_FILE_FORMAT = exports.EXP_IO = void 0;
exports.EXP_IO = '\u6587\u4ef6\u8bfb\u5199\u5f02\u5e38';
exports.EXP_FILE_FORMAT = '\u6587\u4ef6\u683c\u5f0f\u9519\u8bef';
exports.GET_PARTIAL_VALUES_ERROR_MSG = '\u6587\u4ef6\u9519\u8bef';
exports.EXP_WRONG_PASSWORD = '\u5bc6\u7801\u9519\u8bef';
exports.EXP_EMPTY_PASSWORD = 'Excel\u6587\u4ef6\u53d7\u5bc6\u7801\u4fdd\u62a4\uff0c\u65e0\u6cd5\u88ab\u6253\u5f00\u3002';


/***/ }),

/***/ "./dist/plugins/outlineColumn/outlineColumn.res.zh.js":
/*!************************************************************!*\
  !*** ./dist/plugins/outlineColumn/outlineColumn.res.zh.js ***!
  \************************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_GROUP_PROTECTED = void 0;
exports.Exp_GROUP_PROTECTED = "\u4e0d\u80fd\u5bf9\u53d7\u4fdd\u62a4\u7684\u5de5\u4f5c\u8868\u4f7f\u7528\u8be5\u547d\u4ee4\u3002\u82e5\u4f7f\u7528\u8be5\u547d\u4ee4\uff0c\u5fc5\u987b\u5148\u64a4\u9500\u5de5\u4f5c\u8868\u4fdd\u62a4\u3002";


/***/ }),

/***/ "./dist/plugins/pivot/lr.zh.js":
/*!*************************************!*\
  !*** ./dist/plugins/pivot/lr.zh.js ***!
  \*************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.wmk2 = exports.wmk1 = void 0;
exports.wmk1 = ["6563908988", "706e0fc668"];
exports.wmk2 = ["6563908988", "706e0fc668"];


/***/ }),

/***/ "./dist/plugins/pivot/pivot.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/pivot/pivot.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.PivotPanel_ValueFilterOrLabel_NotEqual = exports.PivotPanel_ValueFilterOrLabel_Equals = exports.PivotPanel_LabelOrValue_ClearFilter = exports.PivotPanel_Fields_MoveTo_Value = exports.PivotPanel_Fields_MoveTo_ColLabel = exports.PivotPanel_Fields_MoveTo_RowLabel = exports.PivotPanel_Fields_MoveTo_ReportField = exports.PivotPanel_DropDownList_ValueSet = exports.PivotPanel_DropDownList_Set = exports.PivotPanel_DropDownList_Remove = exports.PivotPanel_DropDownList_Values = exports.PivotPanel_DropDownList_Col = exports.PivotPanel_DropDownList_Row = exports.PivotPanel_DropDownList_ReportFilter = exports.PivotPanel_DropDownList_End = exports.PivotPanel_DropDownList_Beginning = exports.PivotPanel_DropDownList_Down = exports.PivotPanel_DropDownList_Up = exports.PivotPanel_Update = exports.EmptyPivotTable_PromptMessage2 = exports.EmptyPivotTable_PromptMessage1 = exports.PivotPanel_DeferUpdateText = exports.PivotPanel_ValuesItemsTitle = exports.PivotPanel_ColumnsItemsTitle = exports.PivotPanel_RowsItemsTitle = exports.PivotPanel_FiltersItemsTitle = exports.PivotPanel_FieldAreaText = exports.PivotPanel_SearchPlaceholder = exports.PivotPanel_ReportText = exports.PivotPanel_Title = exports.Values = exports.GrandTotal = exports.RowLabels = exports.MultipleItems = exports.All = exports.Total = exports.ColumnLabels = exports.SubtotalType_Prefix_10 = exports.SubtotalType_Prefix_9 = exports.SubtotalType_Prefix_8 = exports.SubtotalType_Prefix_7 = exports.SubtotalType_Prefix_6 = exports.SubtotalType_Prefix_5 = exports.SubtotalType_Prefix_4 = exports.SubtotalType_Prefix_3 = exports.SubtotalType_Prefix_2 = exports.SubtotalType_Prefix_1 = exports.SubtotalType_Prefix_0 = exports.NotExist = exports.RepeatName = void 0;
exports.PivotPanel_ValueFilterOrLabel_LastYear = exports.PivotPanel_ValueFilterOrLabel_ThisYear = exports.PivotPanel_ValueFilterOrLabel_NextYear = exports.PivotPanel_ValueFilterOrLabel_LastQuarter = exports.PivotPanel_ValueFilterOrLabel_ThisQuarter = exports.PivotPanel_ValueFilterOrLabel_NextQuarter = exports.PivotPanel_ValueFilterOrLabel_LastMonth = exports.PivotPanel_ValueFilterOrLabel_ThisMonth = exports.PivotPanel_ValueFilterOrLabel_NextMonth = exports.PivotPanel_ValueFilterOrLabel_LastWeek = exports.PivotPanel_ValueFilterOrLabel_ThisWeek = exports.PivotPanel_ValueFilterOrLabel_NextWeek = exports.PivotPanel_ValueFilterOrLabel_Yesterday = exports.PivotPanel_ValueFilterOrLabel_Today = exports.PivotPanel_ValueFilterOrLabel_Tomorrow = exports.PivotPanel_ValueFilterOrLabel_After = exports.PivotPanel_ValueFilterOrLabel_Before = exports.ValueFilterOrLabel_NotContain = exports.ValueFilterOrLabel_Contain = exports.ValueFilterOrLabel_NotEndWith = exports.ValueFilterOrLabel_End = exports.ValueFilterOrLabel_NotBegin = exports.ValueFilterOrLabel_IsBeginWith = exports.ValueFilterOrLabelSelect_NotBetween = exports.ValueFilterOrLabelSelect_Between = exports.ValueFilterOrLabelSelect_LessOrTo = exports.ValueFilterOrLabelSelect_LessThan = exports.ValueFilterOrLabelSelect_GreaterOrTo = exports.ValueFilterOrLabelSelect_GreaterThan = exports.ValueFilterOrLabel_NotBetween = exports.ValueFilterOrLabel_Between = exports.ValueFilterOrLabel_LessOrTo = exports.ValueFilterOrLabel_LessThan = exports.ValueFilterOrLabel_GreaterOrTo = exports.ValueFilterOrLabel_GreaterThan = exports.ValueFilterOrLabel_NotEqual = exports.ValueFilterOrLabel_Equals = exports.PivotPanel_ValueFilterOrLabel_NotContain = exports.PivotPanel_ValueFilterOrLabel_Contain = exports.PivotPanel_ValueFilterOrLabel_NotEndWith = exports.PivotPanel_ValueFilterOrLabel_End = exports.PivotPanel_ValueFilterOrLabel_NotBegin = exports.PivotPanel_ValueFilterOrLabel_IsBeginWith = exports.PivotPanel_ValueFilter_Top10 = exports.PivotPanel_ValueFilterOrLabel_NotBetween = exports.PivotPanel_ValueFilterOrLabel_Between = exports.PivotPanel_ValueFilterOrLabel_LessOrTo = exports.PivotPanel_ValueFilterOrLabel_LessThan = exports.PivotPanel_ValueFilterOrLabel_GreaterOrTo = exports.PivotPanel_ValueFilterOrLabel_GreaterThan = void 0;
exports.Sort_Value_Dialog_Asc = exports.Sort_Value_Dialog_Sort_Direction = exports.Sort_Value_Dialog_Title = exports.Sort_Field_More_Dialog_Values_In_Selected_Area = exports.Sort_Field_More_Dialog_Sort_By = exports.Sort_Field_More_Dialog_Title = exports.Sort_Field_Dialog_More_Options = exports.Sort_Field_Dialog_Descending_Sort = exports.Sort_Field_Dialog_Ascending_Sort = exports.Sort_Field_Dialog_Summary = exports.Sort_Field_Dialog_Sort_Options = exports.Sort_Field_Dialog_Title = exports.TopTenShow = exports.Top_Ten_Filter = exports.Value_Title = exports.Value_Show = exports.Use_Series_Characters = exports.Use_Single_Character = exports.Label_Show = exports.Label_Title = exports.PivotPanel_Filter_Search = exports.PivotPanel_Filter_NoSelectAll = exports.PivotPanel_Filter_SelectAll = exports.Select_Field = exports.CustomFormats = exports.Type = exports.Sample = exports.FormatCells = exports.Number = exports.LabelFormat = exports.NumberFormat = exports.Cancel = exports.Ok = exports.PivotPanel_Date_Filter = exports.PivotPanel_Filter_Value = exports.PivotPanel_Filter_Label = exports.PivotPanel_Filter_Clear = exports.PivotPanel_Sort_More = exports.PivotPanel_Sort_Largest_Smallest = exports.PivotPanel_Sort_Smallest_Largest = exports.PivotPanel_Sort_Z_A = exports.PivotPanel_Sort_A_Z = exports.PivotPanel_ValueFilterOrLabel_Custom = exports.PivotPanel_ValueFilterOrLabel_AllDates = exports.PivotPanel_ValueFilterOrLabel_ParallelQuarterToDate = exports.PivotPanel_ValueFilterOrLabel_ParallelMonthToDate = exports.PivotPanel_ValueFilterOrLabel_ParallelYearToDate = exports.PivotPanel_ValueFilterOrLabel_QuarterToDate = exports.PivotPanel_ValueFilterOrLabel_MonthToDate = exports.PivotPanel_ValueFilterOrLabel_YearToDate = void 0;
exports.SummarizeValue = exports.CustomName = exports.SourceName = exports.ValueSetting = exports.FieldSetting = exports.WholeDays = exports.DateShow = exports.DateFilterTitle = exports.IsNotBetween = exports.IsBetween = exports.IsAfterOrEqual = exports.IsAfter = exports.IsBeforeOrEqual = exports.IsBefore = exports.Dec = exports.Nov = exports.Oct = exports.Sep = exports.Aug = exports.Jul = exports.Jun = exports.May = exports.Apr = exports.Mar = exports.Feb = exports.Jan = exports.Q4 = exports.Q3 = exports.Q2 = exports.Q1 = exports.AndJoiner = exports.ByJoiner = exports.Top_Sum = exports.Top_Percent = exports.Top_Item = exports.Bottom = exports.Top = exports.Invalid_Field_Sort_By = exports.Invalid_Sort_Range = exports.Sort_More_Summary_Pattern = exports.Sort_Value_Summary_Pattern = exports.Sort_Summary_Pattern = exports.Sort_In_Column = exports.Sort_In_Row = exports.Sort_Order_DESC = exports.Sort_Order_ASC = exports.Custom_Sort_Summary = exports.Sort_Value_Dialog_Left_To_Right = exports.Sort_Value_Dialog_Top_To_Bottom = exports.Sort_Value_Dialog_Desc = void 0;
exports.days = exports.hours = exports.minutes = exports.seconds = exports.groupBy = exports.endingAt = exports.startingAt = exports.auto = exports.grouping = exports.baseItem = exports.baseField = exports.showValueAsDialog = exports.index = exports.rankLargestSmallest = exports.rankSmallestLargest = exports.percentRunningTotal = exports.runningTotal = exports.percentDifference = exports.difference = exports.percentParentTotal = exports.percentParentColumnTotal = exports.percentParentRowTotal = exports.percentEllipsis = exports.percentRowTotal = exports.percentColumnTotal = exports.percentGrandTotal = exports.noCalculation = exports.DateFormatError = exports.sigmaValueTemp = exports.sigmaValue = exports.Varp = exports.Var = exports.StdDevp = exports.StdDev = exports.CountNumbers = exports.Product = exports.Min = exports.Max = exports.Average = exports.Count = exports.Sum = exports.ChooseType = exports.SummarizeValueField = exports.ShowValueAsField = exports.BaseItemPrevious = exports.BaseItemNext = exports.BaseItemForDialog = exports.BaseFieldForDialog = exports.CalculationForDialog = exports.ShowValueAs = void 0;
exports.ALL_DATES_IN = exports.TIMELINE_PLACE_HOLDER = exports.showSubtotalTop = exports.showNoData = exports.Layout = exports.AddSearchResult = exports.search = exports.SlicerNameInFormula = exports.PivotTableErrorFormula = exports.PivotTableMoveCalcItemFieldToReport = exports.PivotTableCalcItemHasMultipleDataField = exports.PivotTableHasSameItemName = exports.PivotTableSubtotalType = exports.PivotTableHasNumberOrDateGroup = exports.PivotTableCalcItemHasTowCacheField = exports.PivotTableForAccessibility = exports.PivotTableErrorMessage_InvalidReference = exports.PivotTableErrorMessage_InvalidGroup = exports.PivotTableErrorMessage_InvalidChange = exports.PivotTableErrorMessage_Overlap = exports.PivotTableErrorMessage_EmptySourceFieldName = exports.PivotTableErrorMessage_Protect = exports.PivotTableErrorMessage_EmptyFieldName = exports.PivotTableErrorMessage_DuplicatedFieldName = exports.PivotTableErrorMessage_EditWhenDefer = exports.PivotTableErrorMessage_ExistData = exports.PivotTableErrorMessage_MakeChange = exports.PivotTableErrorMessage_ShowDetail = exports.getValueFilterItemText = exports.getDateLabelFilterItemText = exports.getStringLabelFilterItemText = exports.deferLayoutUpdate = exports.toolTipContent_NoValue = exports.toolTipContent_Value = exports.toolTipContent_Column = exports.toolTipContent_Row = exports.DefaultPivotTableViewName = exports.EmptyValueFieldError = exports.param_error = exports.Group = exports.Views = exports.FieldAreaLimited = exports.SourceDataOnlyOne = exports.SourceNotIsTableNameOrFormula = exports.SourceError = exports.NoHaveSpread = exports.numberOfDays = exports.years = exports.quarters = exports.months = void 0;
exports.PivotTableErrorMessage_ExistTable = exports.TIME_LEVEL_DAYS = exports.TIME_LEVEL_MONTHS = exports.TIME_LEVEL_QUARTERS = exports.TIME_LEVEL_YEARS = exports.AllDatesIn_December = exports.AllDatesIn_November = exports.AllDatesIn_October = exports.AllDatesIn_September = exports.AllDatesIn_August = exports.AllDatesIn_July = exports.AllDatesIn_June = exports.AllDatesIn_May = exports.AllDatesIn_April = exports.AllDatesIn_March = exports.AllDatesIn_February = exports.AllDatesIn_January = exports.AllDatesIn_Quarter4 = exports.AllDatesIn_Quarter3 = exports.AllDatesIn_Quarter2 = exports.AllDatesIn_Quarter1 = exports.DateNotBetween = exports.DateOlderThanOrEqual = exports.DateOlderThan = exports.DateNewerThanOrEqual = exports.DateNewerThan = exports.DateNotEqual = exports.QUARTER_MAP = exports.INVALID_DATE_SELECTION = exports.ALL_PERIODS = void 0;
exports.RepeatName = "\u91cd\u590d\u540d\u79f0";
exports.NotExist = "\u4e0d\u5b58\u5728";
exports.SubtotalType_Prefix_0 = "\u5e73\u5747\u503c\u9879\uff1a";
exports.SubtotalType_Prefix_1 = "\u8ba1\u6570\u9879\uff1a";
exports.SubtotalType_Prefix_2 = "\u6570\u503c\u8ba1\u6570\u9879\uff1a";
exports.SubtotalType_Prefix_3 = "\u6700\u5927\u503c\u9879\uff1a";
exports.SubtotalType_Prefix_4 = "\u6700\u5c0f\u503c\u9879\uff1a";
exports.SubtotalType_Prefix_5 = "\u4e58\u79ef\u9879\uff1a";
exports.SubtotalType_Prefix_6 = "\u6807\u51c6\u504f\u5dee\u9879\uff1a";
exports.SubtotalType_Prefix_7 = "\u603b\u4f53\u6807\u51c6\u504f\u5dee\u9879\uff1a";
exports.SubtotalType_Prefix_8 = "\u6c42\u548c\u9879\uff1a";
exports.SubtotalType_Prefix_9 = "\u65b9\u5dee\u9879\uff1a";
exports.SubtotalType_Prefix_10 = "\u603b\u4f53\u65b9\u5dee\u9879\uff1a";
exports.ColumnLabels = "\u5217\u6807\u7b7e";
exports.Total = " \u6c47\u603b";
exports.All = "(\u5168\u90e8)";
exports.MultipleItems = "\uff08\u591a\u9879\uff09";
exports.RowLabels = "\u884c\u6807\u7b7e";
exports.GrandTotal = "\u603b\u8ba1";
exports.Values = "\u503c";
exports.PivotPanel_Title = "\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5";
exports.PivotPanel_ReportText = "\u9009\u62e9\u8981\u6dfb\u52a0\u5230\u62a5\u8868\u7684\u5b57\u6bb5";
exports.PivotPanel_SearchPlaceholder = "\u641c\u7d22";
exports.PivotPanel_FieldAreaText = "\u5728\u4ee5\u4e0b\u533a\u95f4\u62d6\u52a8\u5b57\u6bb5:";
exports.PivotPanel_FiltersItemsTitle = "\u7b5b\u9009";
exports.PivotPanel_RowsItemsTitle = "\u884c";
exports.PivotPanel_ColumnsItemsTitle = "\u5217";
exports.PivotPanel_ValuesItemsTitle = "\u503c";
exports.PivotPanel_DeferUpdateText = "\u5ef6\u8fdf\u5e03\u5c40\u66f4\u65b0";
exports.EmptyPivotTable_PromptMessage1 = "\u5728\u533a\u57df\u5185\u5355\u51fb\u53ef\u4ee5\u4f7f\u7528\u6570\u636e\u900f\u89c6\u8868";
exports.EmptyPivotTable_PromptMessage2 = "\u82e5\u8981\u751f\u6210\u62a5\u8868\uff0c\u8bf7\u4ece\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5\u5217\u8868\u4e2d\u9009\u62e9\u5b57\u6bb5";
exports.PivotPanel_Update = "\u66f4\u65b0";
exports.PivotPanel_DropDownList_Up = "\u4e0a\u79fb";
exports.PivotPanel_DropDownList_Down = "\u4e0b\u79fb";
exports.PivotPanel_DropDownList_Beginning = "\u79fb\u52a8\u81f3\u5f00\u5934";
exports.PivotPanel_DropDownList_End = "\u79fb\u52a8\u81f3\u672b\u5c3e";
exports.PivotPanel_DropDownList_ReportFilter = "\u79fb\u52a8\u5230\u62a5\u8868\u7b5b\u9009";
exports.PivotPanel_DropDownList_Row = "\u79fb\u52a8\u5230\u884c\u6807\u7b7e";
exports.PivotPanel_DropDownList_Col = "\u79fb\u52a8\u5230\u5217\u6807\u7b7e";
exports.PivotPanel_DropDownList_Values = "\u79fb\u52a8\u5230\u6570\u503c";
exports.PivotPanel_DropDownList_Remove = "\u5220\u9664\u5b57\u6bb5";
exports.PivotPanel_DropDownList_Set = "\u5b57\u6bb5\u8bbe\u7f6e...";
exports.PivotPanel_DropDownList_ValueSet = "\u503c\u5b57\u6bb5\u8bbe\u7f6e...";
exports.PivotPanel_Fields_MoveTo_ReportField = "\u6dfb\u52a0\u5230\u62a5\u8868\u7b5b\u9009";
exports.PivotPanel_Fields_MoveTo_RowLabel = "\u6dfb\u52a0\u5230\u884c\u6807\u7b7e";
exports.PivotPanel_Fields_MoveTo_ColLabel = "\u6dfb\u52a0\u5230\u5217\u6807\u7b7e";
exports.PivotPanel_Fields_MoveTo_Value = "\u6dfb\u52a0\u5230\u6570\u503c";
exports.PivotPanel_LabelOrValue_ClearFilter = "\u6e05\u9664\u7b5b\u9009";
exports.PivotPanel_ValueFilterOrLabel_Equals = "\u7b49\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_NotEqual = "\u4e0d\u7b49\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_GreaterThan = "\u5927\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_GreaterOrTo = "\u5927\u4e8e\u6216\u7b49\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_LessThan = "\u5c0f\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_LessOrTo = "\u5c0f\u4e8e\u6216\u7b49\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_Between = "\u4ecb\u4e8e...";
exports.PivotPanel_ValueFilterOrLabel_NotBetween = "\u4e0d\u4ecb\u4e8e...";
exports.PivotPanel_ValueFilter_Top10 = "\u524d10\u9879...";
exports.PivotPanel_ValueFilterOrLabel_IsBeginWith = '\u5f00\u5934\u662f...';
exports.PivotPanel_ValueFilterOrLabel_NotBegin = '\u5f00\u5934\u4e0d\u662f...';
exports.PivotPanel_ValueFilterOrLabel_End = '\u7ed3\u5c3e\u662f...';
exports.PivotPanel_ValueFilterOrLabel_NotEndWith = '\u7ed3\u5c3e\u4e0d\u662f...';
exports.PivotPanel_ValueFilterOrLabel_Contain = '\u5305\u542b...';
exports.PivotPanel_ValueFilterOrLabel_NotContain = '\u4e0d\u5305\u542b...';
exports.ValueFilterOrLabel_Equals = "\u7b49\u4e8e";
exports.ValueFilterOrLabel_NotEqual = "\u4e0d\u7b49\u4e8e";
exports.ValueFilterOrLabel_GreaterThan = "\u5927\u4e8e";
exports.ValueFilterOrLabel_GreaterOrTo = "\u5927\u4e8e\u6216\u7b49\u4e8e";
exports.ValueFilterOrLabel_LessThan = "\u5c0f\u4e8e";
exports.ValueFilterOrLabel_LessOrTo = "\u5c0f\u4e8e\u6216\u7b49\u4e8e";
exports.ValueFilterOrLabel_Between = "\u4ecb\u4e8e";
exports.ValueFilterOrLabel_NotBetween = "\u4e0d\u4ecb\u4e8e";
exports.ValueFilterOrLabelSelect_GreaterThan = "\u5927\u4e8e";
exports.ValueFilterOrLabelSelect_GreaterOrTo = "\u5927\u4e8e\u6216\u7b49\u4e8e";
exports.ValueFilterOrLabelSelect_LessThan = "\u5c0f\u4e8e";
exports.ValueFilterOrLabelSelect_LessOrTo = "\u5c0f\u4e8e\u6216\u7b49\u4e8e";
exports.ValueFilterOrLabelSelect_Between = "\u4ecb\u4e8e";
exports.ValueFilterOrLabelSelect_NotBetween = "\u4e0d\u4ecb\u4e8e";
exports.ValueFilterOrLabel_IsBeginWith = '\u5f00\u5934\u662f';
exports.ValueFilterOrLabel_NotBegin = '\u5f00\u5934\u4e0d\u662f';
exports.ValueFilterOrLabel_End = '\u7ed3\u5c3e\u662f';
exports.ValueFilterOrLabel_NotEndWith = '\u7ed3\u5c3e\u4e0d\u662f';
exports.ValueFilterOrLabel_Contain = '\u5305\u542b';
exports.ValueFilterOrLabel_NotContain = '\u4e0d\u5305\u542b';
exports.PivotPanel_ValueFilterOrLabel_Before = '\u4e4b\u524d...';
exports.PivotPanel_ValueFilterOrLabel_After = '\u4e4b\u540e...';
exports.PivotPanel_ValueFilterOrLabel_Tomorrow = '\u660e\u5929';
exports.PivotPanel_ValueFilterOrLabel_Today = '\u4eca\u5929';
exports.PivotPanel_ValueFilterOrLabel_Yesterday = '\u6628\u5929';
exports.PivotPanel_ValueFilterOrLabel_NextWeek = '\u4e0b\u5468';
exports.PivotPanel_ValueFilterOrLabel_ThisWeek = '\u672c\u5468';
exports.PivotPanel_ValueFilterOrLabel_LastWeek = '\u4e0a\u5468';
exports.PivotPanel_ValueFilterOrLabel_NextMonth = '\u4e0b\u6708';
exports.PivotPanel_ValueFilterOrLabel_ThisMonth = '\u672c\u6708';
exports.PivotPanel_ValueFilterOrLabel_LastMonth = '\u4e0a\u6708';
exports.PivotPanel_ValueFilterOrLabel_NextQuarter = '\u4e0b\u5b63\u5ea6';
exports.PivotPanel_ValueFilterOrLabel_ThisQuarter = '\u672c\u5b63\u5ea6';
exports.PivotPanel_ValueFilterOrLabel_LastQuarter = '\u4e0a\u5b63\u5ea6';
exports.PivotPanel_ValueFilterOrLabel_NextYear = '\u660e\u5e74';
exports.PivotPanel_ValueFilterOrLabel_ThisYear = '\u4eca\u5e74';
exports.PivotPanel_ValueFilterOrLabel_LastYear = '\u53bb\u5e74';
exports.PivotPanel_ValueFilterOrLabel_YearToDate = '\u672c\u5e74\u5ea6\u622a\u6b62\u73b0\u5728';
exports.PivotPanel_ValueFilterOrLabel_MonthToDate = '\u672c\u6708\u5ea6\u622a\u6b62\u73b0\u5728';
exports.PivotPanel_ValueFilterOrLabel_QuarterToDate = '\u672c\u5b63\u5ea6\u622a\u6b62\u73b0\u5728';
exports.PivotPanel_ValueFilterOrLabel_ParallelYearToDate = '\u5e73\u884c\u7684\u5e74\u5ea6\u622a\u6b62\u73b0\u5728';
exports.PivotPanel_ValueFilterOrLabel_ParallelMonthToDate = '\u5e73\u884c\u7684\u6708\u5ea6\u622a\u6b62\u73b0\u5728';
exports.PivotPanel_ValueFilterOrLabel_ParallelQuarterToDate = '\u5e73\u884c\u7684\u5b63\u5ea6\u622a\u6b62\u73b0\u5728';
exports.PivotPanel_ValueFilterOrLabel_AllDates = '\u671f\u95f4\u6240\u6709\u65e5\u671f';
exports.PivotPanel_ValueFilterOrLabel_Custom = '\u81ea\u5b9a\u4e49\u7b5b\u9009...';
exports.PivotPanel_Sort_A_Z = '\u5347\u5e8f';
exports.PivotPanel_Sort_Z_A = '\u964d\u5e8f';
exports.PivotPanel_Sort_Smallest_Largest = '\u5347\u5e8f';
exports.PivotPanel_Sort_Largest_Smallest = '\u964d\u5e8f';
exports.PivotPanel_Sort_More = '\u5176\u4ed6\u6392\u5e8f\u9009\u9879...';
exports.PivotPanel_Filter_Clear = '\u4ece{0}\u4e2d\u6e05\u9664\u7b5b\u9009';
exports.PivotPanel_Filter_Label = '\u6807\u7b7e\u7b5b\u9009';
exports.PivotPanel_Filter_Value = '\u503c\u7b5b\u9009';
exports.PivotPanel_Date_Filter = '\u65e5\u671f\u7b5b\u9009';
exports.Ok = '\u786e\u8ba4';
exports.Cancel = '\u53d6\u6d88';
exports.NumberFormat = '\u6570\u5b57\u683c\u5f0f';
exports.LabelFormat = '\u6807\u7b7e\u683c\u5f0f';
exports.Number = '\u6570\u5b57';
exports.FormatCells = '\u8bbe\u7f6e\u5355\u5143\u683c\u683c\u5f0f';
exports.Sample = '\u793a\u4f8b';
exports.Type = '\u5206\u7c7b';
exports.CustomFormats = [
    "General",
    "0",
    "0.00",
    "#,##0",
    "#,##0.00",
    "#,##0;(#,##0)",
    "#,##0;[Red](#,##0)",
    "#,##0.00;(#,##0.00)",
    "#,##0.00;[Red](#,##0.00)",
    "$#,##0;($#,##0)",
    "$#,##0;[Red]($#,##0)",
    "$#,##0.00;($#,##0.00)",
    "$#,##0.00;[Red]($#,##0.00)",
    "0%",
    "0.00%",
    "0.00E+00",
    "##0.0E+0",
    "# ?/?",
    "# ??/??",
    "m/d/yyyy",
    "d-mmm-yy",
    "d-mmm",
    "mmm-yy",
    "h:mm AM/PM",
    "h:mm:ss AM/PM",
    "hh:mm",
    "hh:mm:ss",
    "m/d/yyyy hh:mm",
    "mm:ss",
    "mm:ss.0",
    "@",
    "[h]:mm:ss",
    "$ #,##0;$ (#,##0);$ \"-\";@",
    " #,##0; (#,##0); \"-\";@",
    "$ #,##0.00;$ (#,##0.00);$ \"-\"??;@",
    " #,##0.00; (#,##0.00); \"-\"??;@",
    "hh:mm:ss",
    "00000",
    "# ???/???",
    "000-00-0000",
    "dddd, mmmm dd, yyyy",
    "m/d;@",
    "[<=9999999]###-####;(###) ###-####",
    "# ?/8"
];
exports.Select_Field = '\u9009\u62e9\u5b57\u6bb5:';
exports.PivotPanel_Filter_SelectAll = '\u5168\u9009';
exports.PivotPanel_Filter_NoSelectAll = '\u53d6\u6d88\u5168\u9009';
exports.PivotPanel_Filter_Search = '\u641c\u7d22';
exports.Label_Title = '\u6807\u7b7e\u7b5b\u9009';
exports.Label_Show = '\u663e\u793a\u7684\u9879\u76ee\u7684\u6807\u7b7e';
exports.Use_Single_Character = '\u7528?\u4ee3\u8868\u5355\u4e2a\u5b57\u7b26';
exports.Use_Series_Characters = '\u7528*\u4ee3\u8868\u4efb\u610f\u591a\u4e2a\u5b57\u7b26';
exports.Value_Show = '\u663e\u793a\u7b26\u5408\u4ee5\u4e0b\u6761\u4ef6\u7684\u9879\u76ee';
exports.Value_Title = '\u503c\u7b5b\u9009';
exports.Top_Ten_Filter = '\u524d10\u9879 ';
exports.TopTenShow = '\u663e\u793a';
exports.Sort_Field_Dialog_Title = "\u6392\u5e8f";
exports.Sort_Field_Dialog_Sort_Options = "\u6392\u5e8f\u9009\u9879";
exports.Sort_Field_Dialog_Summary = "\u6458\u8981";
exports.Sort_Field_Dialog_Ascending_Sort = "\u5347\u5e8f\u6392\u5e8f\uff08A \u5230 Z\uff09\u4f9d\u636e\uff1a";
exports.Sort_Field_Dialog_Descending_Sort = "\u964d\u5e8f\u6392\u5e8f\uff08Z \u5230 A\uff09\u4f9d\u636e\uff1a";
exports.Sort_Field_Dialog_More_Options = "\u5176\u4ed6\u9009\u9879...";
exports.Sort_Field_More_Dialog_Title = "\u5176\u4ed6\u6392\u5e8f\u9009\u9879";
exports.Sort_Field_More_Dialog_Sort_By = "\u6392\u5e8f\u4f9d\u636e";
exports.Sort_Field_More_Dialog_Values_In_Selected_Area = "\u6240\u9009{{AREA}}\u4e2d\u7684\u503c";
exports.Sort_Value_Dialog_Title = "\u6309\u503c\u6392\u5e8f";
exports.Sort_Value_Dialog_Sort_Direction = "\u6392\u5e8f\u65b9\u5411";
exports.Sort_Value_Dialog_Asc = "\u5347\u5e8f";
exports.Sort_Value_Dialog_Desc = "\u964d\u5e8f";
exports.Sort_Value_Dialog_Top_To_Bottom = "\u4ece\u4e0a\u5230\u4e0b";
exports.Sort_Value_Dialog_Left_To_Right = "\u4ece\u5de6\u5230\u53f3";
exports.Custom_Sort_Summary = "\u81ea\u5b9a\u4e49\u6392\u5e8f";
exports.Sort_Order_ASC = "\u5347\u5e8f";
exports.Sort_Order_DESC = "\u964d\u5e8f";
exports.Sort_In_Row = "\u884c";
exports.Sort_In_Column = "\u5217";
exports.Sort_Summary_Pattern = "\u6309{{ORDER}}\u5bf9\u201c{{FIELD_NAME}}\u201d\u6392\u5e8f";
exports.Sort_Value_Summary_Pattern = "\u4f9d\u636e\u201c{{SELECTED_FIELD_NAME}}\u201d\u6309{{ORDER}}\u5bf9\u201c{{FIELD_NAME}}\u201d\u6392\u5e8f";
exports.Sort_More_Summary_Pattern = "\u4f7f\u7528\u6b64{{ROW_COLUMN}}\u4e2d\u7684\u503c, \u4f9d\u636e\u201c{{SELECTED_FIELD_NAME}}\u201d\u6309{{ORDER}}\u5bf9\u201c{{FIELD_NAME}}\u201d\u6392\u5e8f:\r\n";
exports.Invalid_Sort_Range = "\u6392\u5e8f\u5f15\u7528\u65e0\u6548\u3002\u8bf7\u786e\u4fdd\u5b83\u5728\u6240\u8981\u6392\u5e8f\u7684\u6570\u636e\u5185\uff0c\u5e76\u4e14\u7b2c\u4e00\u4e2a\u201c\u6392\u5e8f\u4f9d\u636e\u201d\u6846\u4e0d\u76f8\u540c\u4e14\u4e0d\u4e3a\u7a7a\u3002";
exports.Invalid_Field_Sort_By = "\u672a\u6307\u5b9a\u6570\u636e\u900f\u89c6\u8868\u7684\u6392\u5e8f\u5b57\u6bb5\u3002";
exports.Top = '\u6700\u5927';
exports.Bottom = '\u6700\u5c0f';
exports.Top_Item = '\u9879';
exports.Top_Percent = '\u767e\u5206\u6bd4';
exports.Top_Sum = '\u6c42\u548c';
exports.ByJoiner = "\u4f9d\u636e";
exports.AndJoiner = "\u4e0e";
exports.Q1 = '\u7b2c\u4e00\u5b63\u5ea6';
exports.Q2 = '\u7b2c\u4e8c\u5b63\u5ea6';
exports.Q3 = '\u7b2c\u4e09\u5b63\u5ea6';
exports.Q4 = '\u7b2c\u56db\u5b63\u5ea6';
exports.Jan = '\u4e00\u6708';
exports.Feb = '\u4e8c\u6708';
exports.Mar = '\u4e09\u6708';
exports.Apr = '\u56db\u6708';
exports.May = '\u4e94\u6708';
exports.Jun = '\u516d\u6708';
exports.Jul = '\u4e03\u6708';
exports.Aug = '\u516b\u6708';
exports.Sep = '\u4e5d\u6708';
exports.Oct = '\u5341\u6708';
exports.Nov = '\u5341\u4e00\u6708';
exports.Dec = '\u5341\u4e8c\u6708';
exports.IsBefore = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u524d';
exports.IsBeforeOrEqual = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u524d\u6216\u4e0e\u4e4b\u76f8\u540c';
exports.IsAfter = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u540e';
exports.IsAfterOrEqual = '\u5728\u4ee5\u4e0b\u65e5\u671f\u4e4b\u540e\u6216\u4e0e\u4e4b\u76f8\u540c';
exports.IsBetween = '\u4ecb\u4e8e';
exports.IsNotBetween = '\u4e0d\u4ecb\u4e8e';
exports.DateFilterTitle = '\u65e5\u671f\u7b5b\u9009';
exports.DateShow = '\u663e\u793a\u65e5\u671f\u7b26\u5408\u4ee5\u4e0b\u6761\u4ef6\u7684\u9879\u76ee';
exports.WholeDays = '\u5168\u5929';
exports.FieldSetting = "\u5b57\u6bb5\u8bbe\u7f6e";
exports.ValueSetting = '\u503c\u5b57\u6bb5\u8bbe\u7f6e';
exports.SourceName = '\u6e90\u540d\u79f0:';
exports.CustomName = '\u81ea\u5b9a\u4e49\u540d\u79f0:';
exports.SummarizeValue = '\u503c\u6c47\u603b\u65b9\u5f0f';
exports.ShowValueAs = '\u503c\u663e\u793a\u65b9\u5f0f';
exports.CalculationForDialog = '\u8ba1\u7b97: ';
exports.BaseFieldForDialog = "\u57fa\u672c\u5b57\u6bb5: ";
exports.BaseItemForDialog = "\u57fa\u672c\u9879: ";
exports.BaseItemNext = "(\u4e0b\u4e00\u4e2a)";
exports.BaseItemPrevious = "(\u4e0a\u4e00\u4e2a)";
exports.ShowValueAsField = '\u503c\u663e\u793a\u65b9\u5f0f';
exports.SummarizeValueField = '\u503c\u5b57\u6bb5\u6c47\u603b\u65b9\u5f0f';
exports.ChooseType = '\u9009\u62e9\u7528\u4e8e\u6c47\u603b\u6240\u9009\u5b57\u6bb5\u6570\u636e\u7684\u8ba1\u7b97\u7c7b\u578b';
exports.Sum = '\u6c42\u548c';
exports.Count = '\u8ba1\u6570';
exports.Average = '\u5e73\u5747\u503c';
exports.Max = '\u6700\u5927\u503c';
exports.Min = '\u6700\u5c0f\u503c';
exports.Product = '\u4e58\u79ef';
exports.CountNumbers = '\u6570\u503c\u8ba1\u6570';
exports.StdDev = '\u6807\u51c6\u504f\u5dee';
exports.StdDevp = '\u603b\u4f53\u6807\u51c6\u504f\u5dee';
exports.Var = '\u65b9\u5dee';
exports.Varp = '\u603b\u4f53\u65b9\u5dee';
exports.sigmaValue = "\u2211 \u6570\u503c";
exports.sigmaValueTemp = "\u6570\u503c";
exports.DateFormatError = "\u8fd9\u4e0d\u662f\u4e00\u4e2a\u6709\u6548\u7684\u65e5\u671f";
exports.noCalculation = "\u65e0\u8ba1\u7b97";
exports.percentGrandTotal = "\u603b\u8ba1\u7684\u767e\u5206\u6bd4";
exports.percentColumnTotal = "\u5217\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.percentRowTotal = "\u884c\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.percentEllipsis = "\u767e\u5206\u6bd4...";
exports.percentParentRowTotal = "\u7236\u884c\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.percentParentColumnTotal = "\u7236\u5217\u6c47\u603b\u7684\u767e\u5206\u6bd4";
exports.percentParentTotal = "\u7236\u7ea7\u6c47\u603b\u7684\u767e\u5206\u6bd4...";
exports.difference = "\u5dee\u5f02...";
exports.percentDifference = "\u5dee\u5f02\u767e\u5206\u6bd4...";
exports.runningTotal = "\u6309\u67d0\u4e00\u5b57\u6bb5\u6c47\u603b...";
exports.percentRunningTotal = "\u6309\u67d0\u4e00\u5b57\u6bb5\u6c47\u603b\u7684\u767e\u5206\u6bd4...";
exports.rankSmallestLargest = "\u5347\u5e8f\u6392\u5217...";
exports.rankLargestSmallest = "\u964d\u5e8f\u6392\u5217...";
exports.index = "\u6307\u6570";

exports.showValueAsDialog = [
    '', '', '', '',
    "\u767e\u5206\u6bd4",
    '', '',
    "\u7236\u7ea7\u6c47\u603b\u7684\u767e\u5206\u6bd4",
    "\u5dee\u5f02",
    "\u5dee\u5f02\u767e\u5206\u6bd4",
    "\u6309\u67d0\u4e00\u5b57\u6bb5\u6c47\u603b",
    "\u6309\u67d0\u4e00\u5b57\u6bb5\u6c47\u603b\u767e\u5206\u6bd4",
    "\u5347\u5e8f\u6392\u5217",
    "\u964d\u5e8f\u6392\u5217",
    '',
];
exports.baseField = "\u57fa\u672c\u5b57\u6bb5:";
exports.baseItem = "\u57fa\u672c\u9879:";
exports.grouping = "\u7ec4\u5408";
exports.auto = "\u81ea\u52a8";
exports.startingAt = "\u8d77\u59cb\u4e8e:";
exports.endingAt = "\u7ec8\u6b62\u4e8e:";
exports.groupBy = "\u6b65\u957f:";
exports.seconds = "\u79d2";
exports.minutes = "\u5206";
exports.hours = "\u5c0f\u65f6";
exports.days = "\u65e5";
exports.months = "\u6708";
exports.quarters = "\u5b63\u5ea6";
exports.years = "\u5e74";
exports.numberOfDays = "\u5929\u6570";

var lr = __webpack_require__(/*! lrPtZh */ "./dist/plugins/pivot/lr.zh.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}
exports.NoHaveSpread = "\u5de5\u4f5c\u7c3f\u5f02\u5e38";
exports.SourceError = "\u6570\u636e\u6e90\u5f02\u5e38\uff0c\u4e0d\u80fd\u521b\u5efa\u6570\u636e\u8868";
exports.SourceNotIsTableNameOrFormula = "\u6570\u636e\u6e90\u4e0d\u662f\u4e00\u4e2a\u8868\u683c\u540d\u6216\u516c\u5f0f";
exports.SourceDataOnlyOne = "\u6b64\u547d\u4ee4\u8981\u6c42\u6570\u636e\u81f3\u5c11\u6709\u4e24\u884c\u3002\u5982\u679c\u9009\u5b9a\u533a\u57df\u53ea\u6709\u4e00\u884c\uff0c\u5219\u4e0d\u80fd\u4f7f\u7528\u6b64\u547d\u4ee4";
exports.FieldAreaLimited = "\u6b63\u5728\u79fb\u52a8\u7684\u5b57\u6bb5\u4e0d\u80fd\u653e\u5728\u8be5\u62a5\u8868\u533a\u57df\u4e2d\u3002";
exports.Views = "\u89c6\u56fe";
exports.Group = "\u7ec4\u5408";
exports.param_error = "\u53c2\u6570\u4e3a\u7a7a\u6216\u4e0d\u5b58\u5728";
exports.EmptyValueFieldError = "\u82e5\u8981\u5e94\u7528\u503c\u7b5b\u9009\uff0c\u201c\u503c\u201d\u533a\u57df\u5185\u5fc5\u987b\u81f3\u5c11\u5305\u542b\u4e00\u4e2a\u5b57\u6bb5";
exports.DefaultPivotTableViewName = "\u900f\u89c6\u8868\u89c6\u56fe";
exports.toolTipContent_Row = "\u884c: ";
exports.toolTipContent_Column = "\u5217: ";
exports.toolTipContent_Value = "\u503c: ";
exports.toolTipContent_NoValue = "\u65e0\u6570\u503c";
exports.deferLayoutUpdate = "\u5ef6\u8fdf\u5e03\u5c40\u66f4\u65b0";
function getStringLabelFilterItemText() {
    return [
        exports.ValueFilterOrLabel_Equals, exports.ValueFilterOrLabel_NotEqual,
        exports.ValueFilterOrLabel_IsBeginWith, exports.ValueFilterOrLabel_NotBegin, exports.ValueFilterOrLabel_End,
        exports.ValueFilterOrLabel_NotEndWith, exports.ValueFilterOrLabel_Contain, exports.ValueFilterOrLabel_NotContain,
        exports.ValueFilterOrLabelSelect_GreaterThan, exports.ValueFilterOrLabelSelect_GreaterOrTo, exports.ValueFilterOrLabelSelect_LessThan,
        exports.ValueFilterOrLabelSelect_LessOrTo, exports.ValueFilterOrLabelSelect_Between, exports.ValueFilterOrLabelSelect_NotBetween
    ];
}
exports.getStringLabelFilterItemText = getStringLabelFilterItemText;
function getDateLabelFilterItemText() {
    return [
        exports.ValueFilterOrLabel_Equals, exports.ValueFilterOrLabel_NotEqual, exports.IsBefore, exports.IsBeforeOrEqual, exports.IsAfter, exports.IsAfterOrEqual, exports.IsBetween, exports.IsNotBetween
    ];
}
exports.getDateLabelFilterItemText = getDateLabelFilterItemText;







function getValueFilterItemText() {
    return [exports.ValueFilterOrLabel_Equals, exports.ValueFilterOrLabel_NotEqual, exports.ValueFilterOrLabelSelect_GreaterThan, exports.ValueFilterOrLabelSelect_GreaterOrTo,
        exports.ValueFilterOrLabelSelect_LessThan, exports.ValueFilterOrLabelSelect_LessOrTo, exports.ValueFilterOrLabelSelect_Between, exports.ValueFilterOrLabelSelect_NotBetween];
}
exports.getValueFilterItemText = getValueFilterItemText;
exports.PivotTableErrorMessage_ShowDetail = "\u4e0d\u80fd\u663e\u793a\u6216\u9690\u85cf\u9009\u5b9a\u533a\u57df\u7684\u660e\u7ec6\u6570\u636e\u3002";
exports.PivotTableErrorMessage_MakeChange = "\u65e0\u6cd5\u5bf9\u6240\u9009\u5355\u5143\u683c\u8fdb\u884c\u6b64\u66f4\u6539\uff0c\u56e0\u4e3a\u5b83\u4f1a\u5f71\u54cd\u6570\u636e\u900f\u89c6\u8868\u3002\u8bf7\u4f7f\u7528\u5b57\u6bb5\u5217\u8868\u66f4\u6539\u62a5\u8868\u3002\u5982\u679c\u60a8\u8bd5\u56fe\u63d2\u5165\u6216\u5220\u9664\u5355\u5143\u683c\uff0c\u8bf7\u79fb\u52a8\u6570\u636e\u900f\u89c6\u8868\uff0c\u7136\u540e\u518d\u8bd5\u3002";
exports.PivotTableErrorMessage_ExistData = "\u6b64\u5904\u5df2\u6709\u6570\u636e\uff0c\u662f\u5426\u66ff\u6362\u5b83\uff1f";
exports.PivotTableErrorMessage_EditWhenDefer = "\u9009\u4e2d\u201c\u63a8\u8fdf\u5e03\u5c40\u66f4\u65b0\u201d\u590d\u9009\u6846\u65f6\u65e0\u6cd5\u7f16\u8f91 \u6570\u636e\u900f\u89c6\u8868 \u62a5\u8868\u3002\u82e5\u8981\u7f16\u8f91\u8be5\u62a5\u8868\uff0c\u8bf7\u6e05\u9664\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5\u5217\u8868\u5e95\u7aef\u7684\u201c\u63a8\u8fdf\u5e03\u5c40\u66f4\u65b0\u201d\u590d\u9009\u6846\u3002";
exports.PivotTableErrorMessage_DuplicatedFieldName = "\u5df2\u6709\u76f8\u540c\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5\u540d\u5b58\u5728\u3002";
exports.PivotTableErrorMessage_EmptyFieldName = "\u4e0d\u80fd\u5c06\u7a7a\u503c\u7528\u4f5c\u6570\u636e\u900f\u89c6\u8868\u4e2d\u7684\u6570\u636e\u9879\u6216\u5b57\u6bb5\u540d\u3002";
exports.PivotTableErrorMessage_Protect = "\u53d7\u4fdd\u62a4\u7684\u5de5\u4f5c\u8868\u5305\u542b\u57fa\u4e8e\u76f8\u540c\u6570\u636e\u6e90\u7684\u5176\u4ed6\u6570\u636e\u900f\u89c6\u8868\u65f6\uff0c\u6b64\u547d\u4ee4\u65e0\u6cd5\u6267\u884c\u3002\u82e5\u8981\u64a4\u9500\u5bf9\u5305\u542b\u6709\u5176\u4ed6\u6570\u636e\u900f\u89c6\u8868\u7684\u5de5\u4f5c\u8868\u7684\u4fdd\u62a4\uff0c\u8bf7\u5355\u51fb\u8be5\u5de5\u4f5c\u8868\u7684\u6807\u7b7e\uff0c\u518d\u5728\u201c\u5ba1\u9605\u201d\u9009\u9879\u5361\u4e0a\u7684\u201c\u66f4\u6539\u201d\u7ec4\u4e2d\u5355\u51fb\u201c\u64a4\u9500\u5de5\u4f5c\u8868\u4fdd\u62a4\u201d\u3002\u7136\u540e\u91cd\u8bd5\u8be5\u547d\u4ee4\u3002";
exports.PivotTableErrorMessage_EmptySourceFieldName = "\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5\u540d\u65e0\u6548\u3002\u5728\u521b\u5efa\u900f\u89c6\u8868\u65f6\uff0c\u5fc5\u987b\u4f7f\u7528\u7ec4\u5408\u4e3a\u5e26\u6709\u6807\u5fd7\u5217\u5217\u8868\u7684\u6570\u636e\u3002\u5982\u679c\u8981\u66f4\u6539\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5\u7684\u540d\u79f0\uff0c\u5fc5\u987b\u952e\u5165\u5b57\u6bb5\u7684\u65b0\u540d\u79f0\u3002";
exports.PivotTableErrorMessage_Overlap = "\u6570\u636e\u900f\u89c6\u8868\u4e0d\u80fd\u8986\u76d6\u53e6\u4e00\u4e2a\u6570\u636e\u900f\u89c6\u8868\u3002";
exports.PivotTableErrorMessage_InvalidChange = "\u65e0\u6cd5\u66f4\u6539\u6570\u636e\u900f\u89c6\u8868\u7684\u8fd9\u4e00\u90e8\u5206\u3002";
exports.PivotTableErrorMessage_InvalidGroup = "\u9009\u5b9a\u533a\u57df\u4e0d\u80fd\u5206\u7ec4\u3002";
exports.PivotTableErrorMessage_InvalidReference = "\u6570\u636e\u6e90\u5f15\u7528\u65e0\u6548\u3002";
exports.PivotTableForAccessibility = "\u900f\u89c6\u8868: \r\n\u900f\u89c6\u8868\u540d\u79f0: ";
exports.PivotTableCalcItemHasTowCacheField = "\u5982\u679c\u6570\u636e\u900f\u89c6\u8868\u4e2d\u7684\u4e00\u4e2a\u6216\u591a\u4e2a\u5b57\u6bb5\u5177\u6709\u8ba1\u7b97\u9879\uff0c\u5219\u6ca1\u6709\u5b57\u6bb5\u53ef\u4ee5\u5728\u8be5\u6570\u636e\u533a\u57df\u5185\u4f7f\u7528\u4e24\u6b21\u6216\u591a\u6b21\uff0c\u6216\u540c\u65f6\u5728\u8be5\u6570\u636e\u533a\u57df\u53ca\u5176\u4ed6\u533a\u57df\u4e2d\u4f7f\u7528\u3002\u5982\u679c\u5c1d\u8bd5\u6dfb\u52a0\u5b57\u6bb5\uff0c\u8bf7\u5220\u9664\u8ba1\u7b97\u9879\uff0c\u5e76\u518d\u6b21\u6dfb\u52a0\u5b57\u6bb5\u3002\u5982\u679c\u5c1d\u8bd5\u6dfb\u52a0\u8ba1\u7b97\u9879\uff0c\u8bf7\u66f4\u6539\u6570\u636e\u900f\u89c6\u8868\u4ee5\u4fbf\u6ca1\u6709\u5b57\u6bb5\u88ab\u591a\u6b21\u4f7f\u7528\uff0c\u7136\u540e\u6dfb\u52a0\u8ba1\u7b97\u9879\u3002";
exports.PivotTableHasNumberOrDateGroup = "\u6b64\u6570\u636e\u900f\u89c6\u8868\u5b57\u6bb5\u5df2\u88ab\u7ec4\u5408\u3002\u4e0d\u80fd\u5411\u7ec4\u5408\u5b57\u6bb5\u4e2d\u6dfb\u52a0\u8ba1\u7b97\u9879\u3002\u82e5\u8981\u53d6\u6d88\u7ec4\u5408\u8be5\u5b57\u6bb5\uff0c\u8bf7\u786e\u4fdd\u5b83\u4f4d\u4e8e\u884c\u6216\u5217\u533a\u57df\u4e2d\uff0c\u9009\u5b9a\u7ec4\u5408\u5b57\u6bb5\u7684\u67d0\u4e2a\u9879\uff0c\u5355\u51fb\"\u5206\u6790\"\u9009\u9879\u5361\u4e0a\u7684\"\u7ec4\u5408\"\u7ec4\u4e2d\u7684\"\u53d6\u6d88\u7ec4\u5408\"\u6309\u94ae\uff0c\u7136\u540e\u63d2\u5165\u8ba1\u7b97\u9879\u3002\u63d2\u5165\u8ba1\u7b97\u9879\u4e4b\u540e\uff0c\u53ef\u91cd\u65b0\u5bf9\u5b57\u6bb5\u4e2d\u7684\u9879\u8fdb\u884c\u7ec4\u5408\u3002";
exports.PivotTableSubtotalType = "\u5728\u6570\u636e\u900f\u89c6\u8868\u6709\u8ba1\u7b97\u9879\u65f6\uff0c\u5e73\u5747\u503c\u3001\u6807\u51c6\u504f\u5dee\u548c\u65b9\u5dee\u8ba1\u7b97\u4e0d\u88ab\u652f\u6301\u3002";
exports.PivotTableHasSameItemName = "\u6570\u636e\u9879\u540d\u79f0\u6a21\u7cca\u3002\u6570\u636e\u900f\u89c6\u8868\u4e2d\u5176\u4ed6\u5b57\u6bb5\u53ef\u80fd\u5305\u542b\u540c\u540d\u6570\u636e\u9879\u3002";
exports.PivotTableCalcItemHasMultipleDataField = "\u6570\u636e\u900f\u89c6\u8868\u6709\u8ba1\u7b97\u9879\u65f6\uff0c\u76f8\u540c\u5b57\u6bb5\u7684\u591a\u6570\u636e\u5b57\u6bb5\u4e0d\u88ab\u652f\u6301\u3002";
exports.PivotTableMoveCalcItemFieldToReport = "\u65e0\u6cd5\u5c06\u6b64\u5b57\u6bb5\u653e\u5165\u62a5\u8868\u7b5b\u9009\u4e2d\uff0c\u56e0\u4e3a\u5b83\u5305\u542b\u8ba1\u7b97\u9879\u3002\u8bf7\u5148\u5220\u9664\u8ba1\u7b97\u9879\uff0c\u518d\u5c06\u6b64\u5b57\u6bb5\u653e\u5165\u62a5\u8868\u7b5b\u9009\u4e2d\u3002";
exports.PivotTableErrorFormula = "\u67d0\u4e2a\u6570\u636e\u9879\u540d\u79f0\u65e0\u6cd5\u627e\u5230\u3002\u8bf7\u68c0\u67e5\u786e\u8ba4\u540d\u79f0\u952e\u5165\u662f\u5426\u6b63\u786e\uff0c\u4e14\u900f\u89c6\u8868\u4e2d\u6709\u8be5\u6570\u636e\u9879\u3002";
exports.SlicerNameInFormula = "\u5207\u7247\u5668";
exports.search = "\u641c\u7d22";
exports.AddSearchResult = "\u5c06\u5f53\u524d\u6240\u9009\u5185\u5bb9\u6dfb\u52a0\u5230\u7b5b\u9009\u5668";
exports.Layout = "\u5e03\u5c40";
exports.showNoData = "\u663e\u793a\u65e0\u6570\u636e\u7684\u9879\u76ee";
exports.showSubtotalTop = "\u5728\u6bcf\u4e2a\u7ec4\u9876\u7aef\u663e\u793a\u5206\u7c7b\u6c47\u603b";
exports.TIMELINE_PLACE_HOLDER = "{TL}";
exports.ALL_DATES_IN = "\u4e2d\u7684\u6240\u6709\u65e5\u671f";
exports.ALL_PERIODS = "\u6240\u6709\u671f\u95f4";
exports.INVALID_DATE_SELECTION = "\u975e\u6cd5\u7684\u65e5\u671f\u533a\u95f4\uff01";
exports.QUARTER_MAP = ['', '\u7b2c\u4e00\u5b63\u5ea6', '\u7b2c\u4e8c\u5b63\u5ea6', '\u7b2c\u4e09\u5b63\u5ea6', '\u7b2c\u56db\u5b63\u5ea6'];
exports.DateNotEqual = "\u4e0d\u5728" + exports.TIMELINE_PLACE_HOLDER;
exports.DateNewerThan = exports.TIMELINE_PLACE_HOLDER + " \u4e4b\u540e";
exports.DateNewerThanOrEqual = exports.TIMELINE_PLACE_HOLDER + "\u4e4b\u540e\u6216\u5f53\u5929";
exports.DateOlderThan = exports.TIMELINE_PLACE_HOLDER + " \u4e4b\u524d";
exports.DateOlderThanOrEqual = exports.TIMELINE_PLACE_HOLDER + " \u4e4b\u524d\u6216\u5f53\u5929";
exports.DateNotBetween = "\u672a\u4ecb\u4e8e " + exports.TIMELINE_PLACE_HOLDER;
exports.AllDatesIn_Quarter1 = exports.QUARTER_MAP[1] + exports.ALL_DATES_IN;
exports.AllDatesIn_Quarter2 = exports.QUARTER_MAP[2] + exports.ALL_DATES_IN;
exports.AllDatesIn_Quarter3 = exports.QUARTER_MAP[3] + exports.ALL_DATES_IN;
exports.AllDatesIn_Quarter4 = exports.QUARTER_MAP[4] + exports.ALL_DATES_IN;
exports.AllDatesIn_January = exports.Jan + exports.ALL_DATES_IN;
exports.AllDatesIn_February = exports.Feb + exports.ALL_DATES_IN;
exports.AllDatesIn_March = exports.Mar + exports.ALL_DATES_IN;
exports.AllDatesIn_April = exports.Apr + exports.ALL_DATES_IN;
exports.AllDatesIn_May = exports.May + exports.ALL_DATES_IN;
exports.AllDatesIn_June = exports.Jun + exports.ALL_DATES_IN;
exports.AllDatesIn_July = exports.Jul + exports.ALL_DATES_IN;
exports.AllDatesIn_August = exports.Aug + exports.ALL_DATES_IN;
exports.AllDatesIn_September = exports.Sep + exports.ALL_DATES_IN;
exports.AllDatesIn_October = exports.Oct + exports.ALL_DATES_IN;
exports.AllDatesIn_November = exports.Nov + exports.ALL_DATES_IN;
exports.AllDatesIn_December = exports.Dec + exports.ALL_DATES_IN;
exports.TIME_LEVEL_YEARS = '\u5e74';
exports.TIME_LEVEL_QUARTERS = '\u5b63\u5ea6';
exports.TIME_LEVEL_MONTHS = '\u6708';
exports.TIME_LEVEL_DAYS = '\u5929';
exports.PivotTableErrorMessage_ExistTable = "\u4e00\u4e2a\u6570\u636e\u900f\u89c6\u8868\u4e0d\u80fd\u4e0e\u4e00\u4e2a\u8868\u91cd\u53e0.";


/***/ }),

/***/ "./dist/plugins/print/print.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/print/print.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidSheetIndex = void 0;
exports.Exp_InvalidSheetIndex = '\u65e0\u6548\u7684\u8868\u5355\u7d22\u5f15\u3002';


/***/ }),

/***/ "./dist/plugins/report/resources/lr.zh.js":
/*!************************************************!*\
  !*** ./dist/plugins/report/resources/lr.zh.js ***!
  \************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.wmk2 = exports.wmk1 = void 0;
exports.wmk1 = ["6288", "a568"];
exports.wmk2 = ["6288", "a568"];


/***/ }),

/***/ "./dist/plugins/report/resources/res.zh.js":
/*!*************************************************!*\
  !*** ./dist/plugins/report/resources/res.zh.js ***!
  \*************************************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidParameterName = exports.configDataSetting = exports.summaryNoneTip = exports.summaryCountTip = exports.summaryMinTip = exports.summaryMaxTip = exports.summaryAvgTip = exports.summarySumTip = exports.summaryCellType = exports.groupCellType = exports.listCellType = exports.toolTipCellType = exports.toolTipFormula = exports.toolTipColumnName = exports.toolTipTableName = exports.ContainsInvalidCells = void 0;
exports.ContainsInvalidCells = "\u62a5\u8868\u4e2d\u5b58\u5728\u65e0\u6548\u683c\u5b50\uff08{0}\uff09\uff0c\u8bf7\u786e\u8ba4\u540e\u63d0\u4ea4";
exports.toolTipTableName = "\u8868: ";
exports.toolTipColumnName = "\u5217: ";
exports.toolTipFormula = "\u516c\u5f0f: ";
exports.toolTipCellType = "\u7c7b\u578b: ";
exports.listCellType = "\u5217\u8868";
exports.groupCellType = "\u5206\u7ec4";
exports.summaryCellType = "\u6c47\u603b";
exports.summarySumTip = "\uff08\u6c42\u548c\uff09";
exports.summaryAvgTip = "\uff08\u5e73\u5747\u503c\uff09";
exports.summaryMaxTip = "\uff08\u6700\u5927\u503c\uff09";
exports.summaryMinTip = "\uff08\u6700\u5c0f\u503c\uff09";
exports.summaryCountTip = "\uff08\u8ba1\u6570\uff09";
exports.summaryNoneTip = "\uff08\u65e0\uff09";
exports.configDataSetting = '\u8bf7\u914d\u7f6e\u6570\u636e\u8bbe\u7f6e\u3002';
exports.Exp_InvalidParameterName = '\u4e0d\u5408\u6cd5\u7684\u53c2\u6570\u540d';

var lr = __webpack_require__(/*! lrRpZh */ "./dist/plugins/report/resources/lr.zh.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}


/***/ }),

/***/ "./dist/plugins/shape/shape.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/shape/shape.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidRange = exports.Exp_EmptyName = exports.Exp_DuplicatedName = exports.Exp_InvalidConnectionSite = void 0;
exports.Exp_InvalidConnectionSite = '\u65e0\u6548\u7684\u8fde\u63a5\u70b9\u3002';
exports.Exp_DuplicatedName = "\u540d\u5b57\u91cd\u590d\u3002";
exports.Exp_EmptyName = "\u540d\u5b57\u4e3a\u7a7a\u3002";
exports.Exp_InvalidRange = '\u65e0\u6548\u533a\u95f4';


/***/ }),

/***/ "./dist/plugins/slicer/slicer.res.zh.js":
/*!**********************************************!*\
  !*** ./dist/plugins/slicer/slicer.res.zh.js ***!
  \**********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SlicerNameInFormula = exports.Exp_SlicerNameExist = exports.Exp_SlicerNameInvalid = exports.Blank = void 0;
exports.Blank = '(\u7a7a)';
exports.Exp_SlicerNameInvalid = '\u5207\u7247\u5668\u7684\u540d\u79f0\u65e0\u6548\u3002';
exports.Exp_SlicerNameExist = '\u5207\u7247\u5668\u7684\u540d\u79f0\u5df2\u7ecf\u5b58\u5728\uff0c\u8bf7\u8d4b\u4e88\u5176\u552f\u4e00\u540d\u3002';
exports.SlicerNameInFormula = "\u5207\u7247\u5668";


/***/ }),

/***/ "./dist/plugins/statusBar/statusBar.res.zh.js":
/*!****************************************************!*\
  !*** ./dist/plugins/statusBar/statusBar.res.zh.js ***!
  \****************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.progressItem = exports.toolTipZoomPanel = exports.toolTipSlider = exports.toolTipZoomIn = exports.toolTipZoomOut = exports.toolTipFormulaSum = exports.toolTipFormulaMax = exports.toolTipFormulaMin = exports.toolTipFormulaNumericalCount = exports.toolTipFormulaCount = exports.toolTipFormulaAverage = exports.toolTipCellMode = exports.zoom = exports.zoomSlider = exports.formulaSum = exports.formulaMax = exports.formulaMin = exports.formulaNumericalCount = exports.formulaCount = exports.formulaAverage = exports.cellModeEdit = exports.cellModeEnter = exports.cellModeReady = exports.cellMode = void 0;
exports.cellMode = '\u5355\u5143\u683c\u6a21\u5f0f';
exports.cellModeReady = '\u5c31\u7eea';
exports.cellModeEnter = '\u8f93\u5165';
exports.cellModeEdit = '\u7f16\u8f91';
exports.formulaAverage = '\u5e73\u5747\u503c';
exports.formulaCount = '\u8ba1\u6570';
exports.formulaNumericalCount = '\u6570\u503c\u8ba1\u6570';
exports.formulaMin = '\u6700\u5c0f\u503c';
exports.formulaMax = '\u6700\u5927\u503c';
exports.formulaSum = '\u6c42\u548c';
exports.zoomSlider = '\u7f29\u653e\u6ed1\u5757';
exports.zoom = '\u7f29\u653e';
exports.toolTipCellMode = '{0}\u6a21\u5f0f';
exports.toolTipFormulaAverage = '\u9009\u5b9a\u5355\u5143\u683c\u7684\u5e73\u5747\u503c';
exports.toolTipFormulaCount = '\u6240\u9009\u5355\u5143\u683c\u4e2d\u5305\u542b\u6570\u636e\u7684\u5355\u5143\u683c\u6570';
exports.toolTipFormulaNumericalCount = '\u6240\u9009\u5355\u5143\u683c\u4e2d\u5305\u542b\u6570\u503c\u6570\u636e\u7684\u5355\u5143\u683c\u6570';
exports.toolTipFormulaMin = '\u9009\u5b9a\u533a\u57df\u4e2d\u7684\u6700\u5c0f\u503c';
exports.toolTipFormulaMax = '\u9009\u5b9a\u533a\u57df\u4e2d\u7684\u6700\u5927\u503c';
exports.toolTipFormulaSum = '\u9009\u5b9a\u5355\u5143\u683c\u7684\u603b\u548c';
exports.toolTipZoomOut = '\u7f29\u5c0f';
exports.toolTipZoomIn = '\u653e\u5927';
exports.toolTipSlider = '\u663e\u793a\u6bd4\u4f8b';
exports.toolTipZoomPanel = '\u7f29\u653e\u7ea7\u522b';
exports.progressItem = '\u8fdb\u5ea6';


/***/ }),

/***/ "./dist/plugins/tableSheet/tableSheet.res.zh.js":
/*!******************************************************!*\
  !*** ./dist/plugins/tableSheet/tableSheet.res.zh.js ***!
  \******************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidOperationInProtectForTableSheet = exports.CrossColumnDetailList = exports.CrossColumnDetailAttributes = exports.CrossColumnDetailFilter = exports.CrossColumnDetailValueHeader = exports.CrossColumnDetailCaption = exports.CrossColumnDetailOver = exports.CrossColumnDetailValuePlaceHolder = exports.CrossColumnDetailValue = exports.CrossColumnDetailName = exports.CrossColumnDetailFormatter = exports.CrossColumnCrossHeader = exports.StatusBarToolTipRowCount = exports.StatusBarRowCount = exports.GroupPanelAddCalculateColumn = exports.GroupPanelItemRemoveAll = exports.GroupPanelSummaryLabelSlice = exports.GroupPanelSummaryLabelCaption = exports.GroupPanelSummaryLabelFormula = exports.GroupPanelDropDownCalcField = exports.GroupPanelItemAddCalculation = exports.GroupPanelItemRemove = exports.GroupPanelGroupsHeader = exports.GroupPanelFieldsHeader = exports.GroupPanelTip = exports.EXP_TooManyPinRecords = void 0;
exports.EXP_TooManyPinRecords = "\u7f6e\u9876\u7684\u8bb0\u5f55\u4e0d\u80fd\u8d85\u8fc710\u884c\u3002";
exports.GroupPanelTip = "\u62d6\u62fd\u81f3\u6b64\u4ee5\u5206\u7ec4";
exports.GroupPanelFieldsHeader = "\u5b57\u6bb5";
exports.GroupPanelGroupsHeader = "\u5206\u7ec4";
exports.GroupPanelItemRemove = "\u5220\u9664";
exports.GroupPanelItemAddCalculation = "\u6dfb\u52a0\u6c47\u603b\u5b57\u6bb5";
exports.GroupPanelDropDownCalcField = "\u5b57\u6bb5";
exports.GroupPanelSummaryLabelFormula = "\u516c\u5f0f";
exports.GroupPanelSummaryLabelCaption = "\u6807\u9898";
exports.GroupPanelSummaryLabelSlice = "\u5207\u7247";
exports.GroupPanelItemRemoveAll = "\u5168\u90e8\u5220\u9664";
exports.GroupPanelAddCalculateColumn = "\u6dfb\u52a0\u8ba1\u7b97\u5217";
exports.StatusBarRowCount = "\u884c\u6570";
exports.StatusBarToolTipRowCount = '\u9009\u4e2d\u884c\u7684\u6570\u91cf';
exports.CrossColumnCrossHeader = "\u4ea4\u53c9";
exports.CrossColumnDetailFormatter = "\u683c\u5f0f\u5316";
exports.CrossColumnDetailName = "\u4ea4\u53c9\u540d";
exports.CrossColumnDetailValue = "\u4ea4\u53c9\u503c";
exports.CrossColumnDetailValuePlaceHolder = "\u62d6\u62fd\u81f3\u6b64\u4ee5\u8bbe\u7f6e\u4ea4\u53c9\u503c";
exports.CrossColumnDetailOver = "\u4ea4\u53c9\u5b57\u6bb5";
exports.CrossColumnDetailCaption = "\u4ea4\u53c9\u5217\u6807\u9898";
exports.CrossColumnDetailValueHeader = "\u5c55\u793a\u4ea4\u53c9\u503c\u5217\u5934";
exports.CrossColumnDetailFilter = "\u4ea4\u53c9\u5217\u8fc7\u6ee4";
exports.CrossColumnDetailAttributes = "\u4ea4\u53c9\u8868\u5c5e\u6027";
exports.CrossColumnDetailList = "\u4ea4\u53c9\u5b57\u6bb5\u5217\u8868";
exports.Exp_InvalidOperationInProtectForTableSheet = "\u60a8\u5c1d\u8bd5\u4fee\u6539\u7684\u533a\u57df\u662f\u9501\u5b9a\u7684\u3002";


/***/ }),

/***/ "./dist/plugins/table/table.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/table/table.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Table_Var = exports.Table_StdDev = exports.Table_Sum = exports.Table_Min = exports.Table_Max = exports.Table_Count_Numbers = exports.Table_Count = exports.Table_Average = exports.Table_None = exports.Table_Total = exports.Exp_TableAddRowNoEnoughRoom = exports.Exp_TableAddRowIntersectTable = exports.Exp_TableAddRowIntersectSpan = exports.Exp_TableDeleteCountInvalid = exports.Exp_ColParamOutOfRange = exports.Exp_RowParamOutOfRange = exports.Exp_TableResizeToTable = exports.Exp_TableResizeWithHidden = exports.Exp_TableResizeWithFilter = exports.Exp_TableResizeToSpan = exports.Exp_TableResizeInvalidRange = exports.Exp_ArrayFormulaTable = exports.Exp_TableResizeOutOfRange = exports.Exp_TableMoveOutOfRange = exports.Exp_TableDataSourceNullError = exports.Exp_TableRangeHasFilterError = exports.Exp_TableHasSameNameError = exports.Exp_TableIntersectError = exports.Exp_TableInvalidColumn = exports.Exp_TableInvalidRow = exports.Exp_TableNameInvalid = exports.Exp_TableEmptyNameError = exports.Exp_DragDropChangePartOfTable = exports.Exp_DragDropShiftTableCell = void 0;
exports.Exp_DragDropShiftTableCell = '\u6b64\u64cd\u4f5c\u662f\u4e0d\u5141\u8bb8\u7684\u3002\u6b64\u64cd\u4f5c\u8bd5\u56fe\u5728\u4f60\u8868\u5355\u7684\u8868\u683c\u4e2d\u6539\u53d8\u5355\u5143\u683c\u3002';
exports.Exp_DragDropChangePartOfTable = '\u65e0\u6cd5\u5b8c\u6210\u6b64\u64cd\u4f5c: \u60a8\u6b63\u5728\u5c1d\u8bd5\u5c06\u90e8\u5206\u7684\u8868\u683c\u884c\u6216\u5217\u4ee5\u6b64\u65b9\u5f0f\u66f4\u6539\uff0c\u8fd9\u662f\u4e0d\u5141\u8bb8\u7684\u3002';
exports.Exp_TableEmptyNameError = '\u8868\u683c\u7684\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\u3002';
exports.Exp_TableNameInvalid = '\u8868\u683c\u7684\u540d\u79f0\u65e0\u6548\u3002';
exports.Exp_TableInvalidRow = '\u65e0\u6548\u7684\u884c\u7d22\u5f15\u6216\u884c\u6570\u3002';
exports.Exp_TableInvalidColumn = '\u65e0\u6548\u7684\u5217\u7d22\u5f15\u6216\u5217\u6570\u3002';
exports.Exp_TableIntersectError = '\u8868\u683c\u4e0d\u80fd\u76f8\u4ea4\u3002';
exports.Exp_TableHasSameNameError = '\u5f53\u524d\u8868\u5355\u5df2\u5305\u542b\u5177\u6709\u76f8\u540c\u540d\u79f0\u7684\u8868\u683c\u3002';
exports.Exp_TableRangeHasFilterError = '\u5f53\u524d\u8868\u683c\u533a\u57df\u5185\u542b\u6709\u7b5b\u9009\u533a\u57df\u3002';
exports.Exp_TableDataSourceNullError = '\u8868\u683c\u6570\u636e\u6e90\u4e0d\u80fd\u4e3a\u7a7a\u3002';
exports.Exp_TableMoveOutOfRange = '\u8868\u683c\u4e0d\u80fd\u88ab\u79fb\u51fa\u8868\u5355\u3002';
exports.Exp_TableResizeOutOfRange = '\u65e0\u6548\u7684\u884c\u6570\uff0c\u5217\u6570\u548c\u8868\u683c\u5927\u5c0f\u4e0d\u80fd\u8d85\u51fa\u8868\u5355\u3002';
exports.Exp_ArrayFormulaTable = '\u591a\u4e2a\u5355\u5143\u683c\u6570\u7ec4\u516c\u5f0f\u5728\u8868\u683c\u4e2d\u662f\u4e0d\u5141\u8bb8\u7684\u3002';
exports.Exp_TableResizeInvalidRange = '\u6807\u9898\u5fc5\u987b\u4fdd\u7559\u5728\u540c\u4e00\u884c\u4e0a\uff0c\u5e76\u4e14\u6700\u7ec8\u8868\u683c\u533a\u57df\u5fc5\u987b\u8986\u76d6\u539f\u6709\u8868\u683c\u533a\u57df\u3002';
exports.Exp_TableResizeToSpan = '\u65e0\u6cd5\u5b8c\u6210\u64cd\u4f5c\uff1a\u8868\u4e0d\u80fd\u4e0e\u8868\u6216\u5408\u5e76\u7684\u5355\u5143\u683c\u3002';
exports.Exp_TableResizeWithFilter = '\u65e0\u6cd5\u79fb\u52a8\u7b5b\u9009\u533a\u57df\u6216\u8868\u4e2d\u7684\u5355\u5143\u683c\u3002';
exports.Exp_TableResizeWithHidden = '\u65e0\u6cd5\u8c03\u6574\u4e0e\u9690\u85cf\u7684\u884c\u6216\u5217\u76f8\u90bb\u7684\u8868\u7684\u5927\u5c0f\u3002';
exports.Exp_TableResizeToTable = '\u65e0\u6cd5\u5b8c\u6210\u64cd\u4f5c\uff1a\u56e0\u4e3a\u5b83\u4f1a\u5f71\u54cd\u8868\u6216\u591a\u4e2a\u8868\u4e0a\u7684\u5355\u5143\u683c\u3002\u8bf7\u9009\u62e9\u5355\u4e2a\u8868\uff0c\u7136\u540e\u518d\u8bd5\u3002';
exports.Exp_RowParamOutOfRange = '\u53c2\u6570\u884c\u7d22\u5f15\u5df2\u8d85\u51fa\u8868\u683c\u6570\u636e\u533a\u57df\u3002';
exports.Exp_ColParamOutOfRange = '\u53c2\u6570\u5217\u7d22\u5f15\u5df2\u8d85\u51fa\u8868\u683c\u6570\u636e\u533a\u57df\u3002';
exports.Exp_TableDeleteCountInvalid = '\u8981\u5220\u9664\u7684\u8868\u884c(\u8868\u5217)\u4e2a\u6570\u4f1a\u5bfc\u81f4\u8868\u683c\u4e3a\u7a7a\u3002';
exports.Exp_TableAddRowIntersectSpan = "\u65e0\u6cd5\u5b8c\u6210\u6b64\u64cd\u4f5c\uff1a\u6b64\u64cd\u4f5c\u4f1a\u5bfc\u81f4\u4e00\u4e9b\u5408\u5e76\u5355\u5143\u683c\u88ab\u62c6\u6563\u3002";
exports.Exp_TableAddRowIntersectTable = "\u4e0d\u5141\u8bb8\u6b64\u64cd\u4f5c\uff0c\u56e0\u4e3a\u5b83\u4f1a\u79fb\u52a8\u5de5\u4f5c\u8868\u4e0a\u8868\u683c\u4e2d\u7684\u5355\u5143\u683c\u3002";
exports.Exp_TableAddRowNoEnoughRoom = '\u65e0\u6cd5\u63d2\u5165\u65b0\u7684\u5355\u5143\u683c\uff0c\u56e0\u4e3a\u5b83\u4f1a\u5bfc\u81f4\u67d0\u4e9b\u975e\u7a7a\u5355\u5143\u683c\u79fb\u51fa\u5de5\u4f5c\u8868\u3002\u60a8\u53ef\u4ee5\u5c1d\u8bd5\u5220\u9664\u884c\u6216\u5217\u6765\u4fdd\u8bc1\u6709\u8db3\u591f\u7684\u533a\u57df\u53ef\u4ee5\u63d2\u5165\u3002';
exports.Table_Total = '\u6c47\u603b';
exports.Table_None = '\u65e0';
exports.Table_Average = '\u5e73\u5747\u503c';
exports.Table_Count = '\u8ba1\u6570';
exports.Table_Count_Numbers = '\u6570\u503c\u8ba1\u6570';
exports.Table_Max = '\u6700\u5927\u503c';
exports.Table_Min = '\u6700\u5c0f\u503c';
exports.Table_Sum = '\u6c42\u548c';
exports.Table_StdDev = '\u6807\u51c6\u504f\u5dee';
exports.Table_Var = '\u65b9\u5dee';


/***/ }),

/***/ "./dist/plugins/touch/touch.res.zh.js":
/*!********************************************!*\
  !*** ./dist/plugins/touch/touch.res.zh.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ToolStrip_AutoFillText = exports.ToolStrip_CopyText = exports.ToolStrip_CutText = exports.ToolStrip_PasteText = void 0;
exports.ToolStrip_PasteText = '\u7c98\u8d34';
exports.ToolStrip_CutText = '\u526a\u8d34';
exports.ToolStrip_CopyText = '\u590d\u5236';
exports.ToolStrip_AutoFillText = '\u81ea\u52a8\u586b\u5145';


/***/ }),

/***/ "./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.zh.js":
/*!****************************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.zh.js ***!
  \****************************************************************************************/
/***/ (function(module) {

var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./src/calcEngine.res.zh.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.resource = void 0;
function functionDescription(description, parameters) {
    return {
        description: description,
        parameters: parameters
    };
}
function parameterDescription(name, repeatable, optional, enumInfo) {
    return {
        name: name,
        repeatable: repeatable,
        optional: optional,
        enum: enumInfo,
    };
}
var START_NUMBER = '[start_num]';
var SIGNIFICANCE = '[significance]';
var MATCH_MODE = 'match_mode';
var IF_NOT_FOUND = '[if_not_found]';
var PAD_WITH = '[pad_with]';
var MATCH_MODE_ENUM_INFO = [
    {
        value: '0',
        description: '\u533a\u5206\u5927\u5c0f\u5199\u5339\u914d\u9879',
    },
    {
        value: '1',
        description: '\u4e0d\u533a\u5206\u5927\u5c0f\u5199\u5339\u914d\u9879'
    }
];
var LOOKUP_MATCH_MODE_ENUM_INFO = [
    {
        value: '0',
        description: '\u7cbe\u51c6\u5339\u914d'
    },
    {
        value: '-1',
        description: '\u7cbe\u51c6\u5339\u914d\u6216\u4e0b\u4e00\u4e2a\u8f83\u5c0f\u7684\u9879'
    },
    {
        value: '1',
        description: '\u7cbe\u51c6\u5339\u914d\u6216\u4e0b\u4e00\u4e2a\u8f83\u5927\u7684\u9879'
    },
    {
        value: '2',
        description: '\u901a\u914d\u7b26\u5339\u914d'
    }
];
var LOOKUP_SEARCH_MODE_ENUM_INFO = [
    {
        value: '1',
        description: '\u4ece\u7b2c\u4e00\u9879\u5230\u6700\u540e\u4e00\u9879\u8fdb\u884c\u641c\u7d22'
    },
    {
        value: '-1',
        description: '\u4ece\u6700\u540e\u4e00\u9879\u5230\u7b2c\u4e00\u9879\u8fdb\u884c\u641c\u7d22'
    },
    {
        value: '2',
        description: '\u4e8c\u8fdb\u5236\u6587\u4ef6\u641c\u7d22(\u5347\u5e8f\u6392\u5e8f)'
    },
    {
        value: '-2',
        description: '\u4e8c\u8fdb\u5236\u6587\u4ef6\u641c\u7d22(\u964d\u5e8f\u6392\u5e8f)'
    }
];
var RANGE_LOOKUP_ENUM_INFO = [
    {
        value: 'TRUE',
        description: "\u8fd1\u4f3c\u5339\u914d"
    },
    {
        value: 'FALSE',
        description: "\u7cbe\u51c6\u5339\u914d"
    }
];
exports.resource = {
    Exp_InvalidCast: '\u65e0\u6548\u8f6c\u6362\u5f02\u5e38',
    Exp_FormulaInvalidChar: '\u516c\u5f0f\u5305\u542b\u65e0\u6548\u5b57\u7b26: \'{0}\' \u5728 {1} \u7d22\u5f15\u5904\u3002',
    Exp_FormulaInvalid: '\u65e0\u6548\u7684\u516c\u5f0f',
    Exp_InvalidFunctionName: '\u65e0\u6548\u51fd\u6570\u540d',
    Exp_InvalidOverrideFunction: '\u4e0d\u80fd\u91cd\u5199\u5185\u7f6e\u51fd\u6570',
    Exp_InvalidArray: '\u65e0\u6548\u6570\u7ec4',
    Exp_OverrideNotAllowed: '\u5c1d\u8bd5\u91cd\u5199\u51fd\u6570\uff0c\u4f46\u91cd\u5199\u884c\u4e3a\u662f\u4e0d\u88ab\u5141\u8bb8\u7684\u3002',
    Exp_NoSyntax: '\u6ca1\u6709\u8bed\u6cd5\u5728 \'{0}\' \u5904\u5339\u914d\u5230\u8bed\u6cd5 \'{1}\'\u3002',
    Exp_IsValid: '\'{0}\' \u662f\u65e0\u6548\u7684\u3002',
    Exp_InvalidParameters: '\u5728 {0} \u5904\u5b58\u5728\u65e0\u6548\u51fd\u6570\u53c2\u6570\u3002',
    Exp_InvalidArrayColumns: '\u5217\u6570\u7ec4\u957f\u5ea6\u5728 {0} \u5904\u4e0d\u76f8\u7b49\u3002',
    Exp_ExprIsNull: '\u53c2\u6570 \'expr\' \u662f\u7a7a',
    Exp_InvalidOperation: '\u65e0\u6548\u64cd\u4f5c\u5f02\u5e38',
    Exp_ArgumentNull: '\u7a7a\u53c2\u6570\u5f02\u5e38',
    Exp_CriteriaIsNull: '\u6761\u4ef6\u4e3a\u7a7a',
    Exp_Format: '\u683c\u5f0f',
    Exp_ArrayFormulaPart: '\u65e0\u6cd5\u4ec5\u6539\u53d8\u6570\u7ec4\u516c\u5f0f\u7684\u4e00\u90e8\u5206\u3002',
    Exp_NotSupported: '\u4e0d\u652f\u6301\u5f02\u5e38',
    Fbx_Summary: '\u6982\u8981',
    Fbx_TableName_Description: '\u8868\u683c\u540d\u4e3a ',
    Fbx_TableSheetName_Description: 'TableSheet\u540d\u4e3a ',
    Fbx_CustomName_Description: '\u81ea\u5b9a\u4e49\u540d\u79f0\u4e3a ',
    Exp_DuplicatedChar: "\u91cd\u590d\u5b57\u7b26",
    Exp_ArgumentOutOfRangeException: "\u53c2\u6570\u8d8a\u754c",
    Exp_ArgumentException: "\u5f02\u5e38\u53c2\u6570",
    _builtInFunctionsResource: {
        'ABS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u6570\u5b57\u7684\u7edd\u5bf9\u503c\u3002', [
            parameterDescription('number')
        ]),
        'ACCRINT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5b9a\u671f\u4ed8\u606f\u8bc1\u5238\u7684\u5e94\u8ba1\u5229\u606f\u3002', [
            parameterDescription('issue'),
            parameterDescription('first_interest'),
            parameterDescription('settlement'),
            parameterDescription('rate'),
            parameterDescription('par'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ACCRINTM': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5728\u5230\u671f\u65e5\u652f\u4ed8\u5229\u606f\u7684\u6709\u4ef7\u8bc1\u5238\u7684\u5e94\u8ba1\u5229\u606f\u3002', [
            parameterDescription('issue'),
            parameterDescription('settlement'),
            parameterDescription('rate'),
            parameterDescription('par'),
            parameterDescription('basis')
        ]),
        'ACOS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u53cd\u4f59\u5f26\u503c\u3002\u53cd\u4f59\u5f26\u503c\u662f\u89d2\u5ea6\uff0c\u5b83\u7684\u4f59\u5f26\u503c\u4e3a\u6570\u5b57\u3002\u8fd4\u56de\u7684\u89d2\u5ea6\u503c\u4ee5\u5f27\u5ea6\u8868\u793a\uff0c\u8303\u56f4\u662f0\u5230pi\u3002', [
            parameterDescription('number')
        ]),
        'ACOSH': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56denumber\u53c2\u6570\u7684\u53cd\u53cc\u66f2\u4f59\u5f26\u503c\u3002\u53c2\u6570\u5fc5\u987b\u5927\u4e8e\u6216\u7b49\u4e8e1\u3002\u53cd\u53cc\u66f2\u4f59\u5f26\u503c\u7684\u53cc\u66f2\u4f59\u5f26\u5373\u4e3anumber\uff0c\u56e0\u6b64ACOSH(COSH(number))\u7b49\u4e8enumber\u3002', [
            parameterDescription('number')
        ]),
        'ADDRESS': functionDescription('\u8be5\u51fd\u6570\u5728\u7ed9\u51fa\u6307\u5b9a\u884c\u6570\u548c\u5217\u6570\u7684\u60c5\u51b5\u4e0b\uff0c\u53ef\u4ee5\u4f7f\u7528ADDRESS\u51fd\u6570\u83b7\u53d6\u5de5\u4f5c\u8868\u5355\u5143\u683c\u7684\u5730\u5740\u3002', [
            parameterDescription('row_num'),
            parameterDescription('column_num'),
            parameterDescription('abs_num'),
            parameterDescription('a1style'),
            parameterDescription('sheet_text')
        ]),
        'AGGREGATE': functionDescription('\u6b64\u51fd\u6570\u4f7f\u7528\u6307\u5b9a\u7684\u5185\u7f6e\u51fd\u6570\u6c47\u603b\u6570\u5b57\u5217\u8868\u3002', [
            parameterDescription('function_num'),
            parameterDescription('options'),
            parameterDescription('ref1'),
            parameterDescription('ref2', true)
        ]),
        'AMORDEGRC': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6bcf\u4e2a\u7ed3\u7b97\u671f\u95f4\u7684\u6298\u65e7\u503c\u3002\u8be5\u51fd\u6570\u4e3b\u8981\u4e3a\u6cd5\u56fd\u4f1a\u8ba1\u7cfb\u7edf\u63d0\u4f9b\u3002\u5982\u679c\u67d0\u9879\u8d44\u4ea7\u662f\u5728\u8be5\u7ed3\u7b97\u671f\u7684\u4e2d\u671f\u8d2d\u5165\u7684\uff0c\u5219\u6309\u76f4\u7ebf\u6298\u65e7\u6cd5\u8ba1\u7b97\u3002\u8be5\u51fd\u6570\u4e0e\u51fd\u6570AMORLINC\u76f8\u4f3c\uff0c\u4e0d\u540c\u4e4b\u5904\u5728\u4e8e\u8be5\u51fd\u6570\u4e2d\u7528\u4e8e\u8ba1\u7b97\u7684\u6298\u65e7\u7cfb\u6570\u53d6\u51b3\u4e8e\u8d44\u4ea7\u7684\u5bff\u547d\u3002', [
            parameterDescription('cost'),
            parameterDescription('date_purchased'),
            parameterDescription('first_period'),
            parameterDescription('salvage'),
            parameterDescription('period'),
            parameterDescription('rate'),
            parameterDescription('basis')
        ]),
        'AMORLINC': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6bcf\u4e2a\u7ed3\u7b97\u671f\u95f4\u7684\u6298\u65e7\u503c\uff0c\u8be5\u51fd\u6570\u4e3a\u6cd5\u56fd\u4f1a\u8ba1\u7cfb\u7edf\u63d0\u4f9b\u3002\u5982\u679c\u67d0\u9879\u8d44\u4ea7\u662f\u5728\u7ed3\u7b97\u671f\u95f4\u7684\u4e2d\u671f\u8d2d\u5165\u7684\uff0c\u5219\u6309\u7ebf\u6027\u6298\u65e7\u6cd5\u8ba1\u7b97\u3002', [
            parameterDescription('cost'),
            parameterDescription('date_purchased'),
            parameterDescription('first_period'),
            parameterDescription('salvage'),
            parameterDescription('period'),
            parameterDescription('rate'),
            parameterDescription('basis')
        ]),
        'AND': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u903b\u8f91\u4e0e\u3002\u5982\u679c\u6bcf\u4e00\u4e2a\u53c2\u6570\u90fd\u662fTRUE\u65f6\u8fd4\u56deTRUE\u3002', [
            parameterDescription('logical1'),
            parameterDescription('logical2')
        ]),
        'ASIN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u7684\u53cd\u6b63\u5f26\u503c\u3002\u53cd\u6b63\u5f26\u503c\u4e3a\u4e00\u4e2a\u89d2\u5ea6\uff0c\u8be5\u89d2\u5ea6\u7684\u6b63\u5f26\u503c\u5373\u7b49\u4e8e\u6b64\u51fd\u6570\u7684number\u53c2\u6570\u3002\u8fd4\u56de\u7684\u89d2\u5ea6\u503c\u5c06\u4ee5\u5f27\u5ea6\u8868\u793a\uff0c\u8303\u56f4\u4e3a-pi/2\u5230pi/2\u3002', [
            parameterDescription('number')
        ]),
        'ASINH': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u7684\u53cd\u53cc\u66f2\u6b63\u5f26\u503c\u3002\u53cd\u53cc\u66f2\u6b63\u5f26\u503c\u7684\u53cc\u66f2\u6b63\u5f26\u5373\u7b49\u4e8e\u6b64\u51fd\u6570\u7684number\u53c2\u6570\u503c\uff0c\u56e0\u6b64ASINH(SINH(number))\u7b49\u4e8enumber\u53c2\u6570\u503c\u3002', [
            parameterDescription('number')
        ]),
        'ATAN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u53cd\u6b63\u5207\u503c\u3002\u53cd\u6b63\u5207\u503c\u4e3a\u89d2\u5ea6\uff0c\u8d77\u6b63\u5207\u503c\u5373\u7b49\u4e8enumber\u53c2\u6570\u503c\u3002\u8fd4\u56de\u7684\u89d2\u5ea6\u503c\u5c06\u4ee5\u5f27\u5ea6\u8868\u793a\uff0c\u8303\u56f4\u4e3a-pi/2\u5230pi/2\u3002', [
            parameterDescription('number')
        ]),
        'ATAN2': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9aX\u53caY\u5750\u6807\u503c\u7684\u53cd\u6b63\u5207\u503c\u3002', [
            parameterDescription('x_num'),
            parameterDescription('y_num')
        ]),
        'ATANH': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u7684\u53cd\u53cc\u66f2\u6b63\u5207\u503c\u3002', [
            parameterDescription('number')
        ]),
        'AVEDEV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u6570\u636e\u4e0e\u5176\u5747\u503c\u7684\u7edd\u5bf9\u504f\u5dee\u7684\u5e73\u5747\u503c\uff0cAVEDEV\u7528\u4e8e\u8bc4\u6d4b\u8fd9\u7ec4\u6570\u636e\u7684\u79bb\u6563\u5ea6\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'AVERAGE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u7684\u5e73\u5747\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true),
        ]),
        'AVERAGEA': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u53c2\u6570\u5217\u8868\u4e2d\u6570\u503c\u7684\u5e73\u5747\u503c\uff0c\u5305\u62ec\u6587\u672c\u548c\u903b\u8f91\u503c\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'AVERAGEIF': functionDescription('\u8fd4\u56de\u67d0\u4e2a\u533a\u57df\u5185\u6ee1\u8db3\u7ed9\u5b9a\u6761\u4ef6\u7684\u6240\u6709\u5355\u5143\u683c\u7684\u5e73\u5747\u503c\u3002', [
            parameterDescription('range'),
            parameterDescription('criteria'),
            parameterDescription('[average_range]')
        ]),
        'AVERAGEIFS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6ee1\u8db3\u591a\u91cd\u6761\u4ef6\u7684\u6240\u6709\u5355\u5143\u683c\u7684\u5e73\u5747\u503c\u3002', [
            parameterDescription('average_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true)
        ]),
        'BESSELI': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4fee\u6b63Bessel\u51fd\u6570\u503c\uff0c\u5b83\u4e0e\u7528\u7eaf\u865a\u6570\u53c2\u6570\u8fd0\u7b97\u65f6\u7684Bessel\u51fd\u6570\u503c\u76f8\u7b49\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELJ': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56deBessel\u51fd\u6570\u503c\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELK': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4fee\u6b63Bessel\u51fd\u6570\u503c\uff0c\u5b83\u4e0e\u7528\u7eaf\u865a\u6570\u53c2\u6570\u8fd0\u7b97\u65f6\u7684Bessel\u51fd\u6570\u503c\u76f8\u7b49\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELY': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56deBessel\u51fd\u6570\u503c\uff0c\u8be5\u51fd\u6570\u4e5f\u79f0\u4f5c\u8bfa\u4f0a\u66fc\u51fd\u6570\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BETADIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u7d2f\u79efbeta\u5206\u5e03\u7684\u6982\u7387\u5bc6\u5ea6\u51fd\u6570\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'BETAINV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6307\u5b9abeta\u5206\u5e03\u7d2f\u79efbeta\u5206\u5e03\u7684\u6982\u7387\u5bc6\u5ea6\u51fd\u6570\u7684\u53cd\u51fd\u6570\u503c\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'BIN2DEC': functionDescription('\u8be5\u51fd\u6570\u5c06\u4e8c\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u5341\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number')
        ]),
        'BIN2HEX': functionDescription('\u8be5\u51fd\u6570\u5c06\u4e8c\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u5341\u516d\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'BIN2OCT': functionDescription('\u8be5\u51fd\u6570\u5c06\u4e8c\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u516b\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'BINOMDIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e00\u5143\u4e8c\u9879\u5f0f\u5206\u5e03\u7684\u6982\u7387\u503c\u3002', [
            parameterDescription('number_s'),
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'CEILING': functionDescription('\u8be5\u51fd\u6570\u5c06\u53c2\u6570\u5411\u4e0a\u820d\u5165\u3002', [
            parameterDescription('number'),
            parameterDescription('significance')
        ]),
        'CHAR': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5bf9\u5e94\u6570\u5b57\u4ee3\u7801\u7684\u5b57\u7b26\u3002', [
            parameterDescription('number')
        ]),
        'CHIDIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de \u03c72 \u5206\u5e03\u7684\u5355\u5c3e\u6982\u7387\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom')
        ]),
        'CHIINV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de \u03c72 \u5206\u5e03\u5355\u5c3e\u6982\u7387\u7684\u53cd\u51fd\u6570\u503c\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHITEST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u72ec\u7acb\u6027\u68c0\u6d4b\u503c\u3002', [
            parameterDescription('actual_range'),
            parameterDescription('expected_range')
        ]),
        'CHOOSE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ece\u503c\u5f97\u5217\u8868\u4e2d\u9009\u62e9\u503c\u3002', [
            parameterDescription('index_num'),
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'CLEAN': functionDescription('\u8be5\u51fd\u6570\u5220\u9664\u6587\u672c\u4e2d\u6240\u6709\u975e\u6253\u5370\u5b57\u7b26\u3002', [
            parameterDescription('text')
        ]),
        'CODE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7b2c\u4e00\u4e2a\u5b57\u7b26\u7684\u6570\u5b57\u4ee3\u7801\u3002\u8fd4\u56de\u7684\u4ee3\u7801\u5bf9\u5e94\u4e8e\u8ba1\u7b97\u673a\u5f53\u524d\u4f7f\u7528\u7684\u5b57\u7b26\u4e32\u3002', [
            parameterDescription('text')
        ]),
        'COLUMN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5f15\u7528\u7684\u5217\u53f7\u3002', [
            parameterDescription('reference')
        ]),
        'COLUMNS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5f15\u7528\u4e2d\u5305\u542b\u7684\u5217\u6570\u3002', [
            parameterDescription('array')
        ]),
        'COMBIN': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u4ece\u7ed9\u5b9a\u6570\u76ee\u7684\u5bf9\u8c61\u96c6\u5408\u4e2d\u63d0\u53d6\u82e5\u5e72\u5bf9\u8c61\u7684\u7ec4\u5408\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'COMPLEX': functionDescription('\u8be5\u51fd\u6570\u5c06\u5b9e\u7cfb\u6570\u548c\u865a\u7cfb\u6570\u8f6c\u6362\u4e3a\u590d\u6570\u3002', [
            parameterDescription('real_num'),
            parameterDescription('image_num'),
            parameterDescription('suffix')
        ]),
        'CONCATENATE': functionDescription('\u8be5\u51fd\u6570\u5c06\u4e24\u4e2a\u6216\u591a\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u5408\u5e76\u4e3a\u4e00\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u3002', [
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'CONFIDENCE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u603b\u4f53\u5e73\u5747\u503c\u7684\u7f6e\u4fe1\u533a\u95f4\u3002', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'CONVERT': functionDescription('\u8be5\u51fd\u6570\u5c06\u6570\u5b57\u4ece\u4e00\u79cd\u5ea6\u91cf\u7cfb\u7edf\u8f6c\u6362\u4e3a\u53e6\u4e00\u79cd\u5ea6\u91cf\u7cfb\u7edf\u3002', [
            parameterDescription('number'),
            parameterDescription('from_unit'),
            parameterDescription('to_unit')
        ]),
        'CORREL': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u6570\u636e\u96c6\u4e4b\u95f4\u7684\u76f8\u5173\u7cfb\u6570\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'COS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u4f59\u5f26\u503c\u3002', [
            parameterDescription('number')
        ]),
        'COSH': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u53cc\u66f2\u4f59\u5f26\u503c\u3002', [
            parameterDescription('number')
        ]),
        'COUNT': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u53c2\u6570\u5217\u8868\u4e2d\u6570\u5b57\u7684\u4e2a\u6570\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'COUNTA': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u533a\u57df\u4e2d\u4e0d\u4e3a\u7a7a\u7684\u5355\u5143\u683c\u7684\u4e2a\u6570\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'COUNTBLANK': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u6307\u5b9a\u5355\u5143\u683c\u533a\u57df\u4e2d\u7a7a\u767d\u5355\u5143\u683c\u7684\u4e2a\u6570\u3002', [
            parameterDescription('cellrange')
        ]),
        'COUNTIF': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u533a\u57df\u5185\u7b26\u5408\u7ed9\u5b9a\u6761\u4ef6\u7684\u5355\u5143\u683c\u7684\u6570\u91cf\u3002', [
            parameterDescription('cellrange'),
            parameterDescription('criteria')
        ]),
        'COUNTIFS': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u533a\u57df\u5185\u7b26\u5408\u591a\u4e2a\u6761\u4ef6\u7684\u5355\u5143\u683c\u7684\u6570\u91cf\u3002', [
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true)
        ]),
        'COUPDAYBS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ece\u4ed8\u606f\u671f\u5f00\u59cb\u5230\u6210\u4ea4\u65e5\u4e4b\u95f4\u7684\u5929\u6570\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPDAYS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5305\u542b\u6210\u4ea4\u65e5\u7684\u4ed8\u606f\u5929\u6570\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPDAYSNC': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ece\u7ed3\u7b97\u65e5\u5230\u4e0b\u4e00\u4ed8\u606f\u65e5\u4e4b\u95f4\u7684\u5929\u6570\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPNCD': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u8868\u793a\u5728\u7ed3\u7b97\u65e5\u4e4b\u540e\u4e0b\u4e00\u4e2a\u4ed8\u606f\u65e5\u7684\u6570\u5b57\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPNUM': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5728\u7ed3\u7b97\u65e5\u548c\u5230\u671f\u65e5\u4e4b\u95f4\u4ed8\u606f\u6b21\u6570\uff0c\u5411\u4e0a\u820d\u5165\u5230\u6700\u8fd1\u7684\u6574\u6570\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPPCD': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u8868\u793a\u7ed3\u7b97\u65e5\u4e4b\u524d\u7684\u4ed8\u606f\u65e5\u7684\u6570\u5b57\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COVAR': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u534f\u65b9\u5dee\uff0c\u5373\u4e24\u4e2a\u6570\u636e\u96c6\u4e2d\u6bcf\u5bf9\u6570\u636e\u70b9\u7684\u504f\u5dee\u4e58\u79ef\u7684\u5e73\u5747\u503c\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'CRITBINOM': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u7d2f\u79ef\u4e8c\u9879\u5f0f\u5206\u5e03\u5927\u4e8e\u7b49\u4e8e\u4e34\u754c\u503c\u7684\u6700\u5c0f\u503c\u3002', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('alpha')
        ]),
        'CUMIPMT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u4ed8\u6b3e\u671f\u4e4b\u95f4\u7d2f\u79ef\u652f\u4ed8\u7684\u5229\u606f\u3002', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('paytype')
        ]),
        'CUMPRINC': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u4ed8\u6b3e\u671f\u4e4b\u95f4\u4e3a\u8d37\u6b3e\u7d2f\u79ef\u652f\u4ed8\u7684\u672c\u91d1\u3002', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('paytype')
        ]),
        'DATE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56deDateTime\u5bf9\u8c61\uff0c\u4ee3\u8868\u4e86\u4e00\u4e2a\u72ec\u6709\u7684\u65e5\u671f\uff0c\u901a\u8fc7\u5b9a\u5236\u5e74\uff0c\u6708\u548c\u65e5\u3002', [
            parameterDescription('year'),
            parameterDescription('month'),
            parameterDescription('day')
        ]),
        'DATEDIF': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u65e5\u671f\u95f4\u7684\u5929\uff0c\u6708\u548c\u5e74\u7684\u6570\u76ee\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('unit')
        ]),
        'DATEVALUE': functionDescription('\u8be5\u51fd\u6570\u5c06\u6587\u672c\u683c\u5f0f\u7684\u65e5\u671f\u8f6c\u6362\u4e3a\u65e5\u671f\u5bf9\u8c61\u3002', [
            parameterDescription('date_text')
        ]),
        'DAVERAGE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6570\u503c\u7684\u5e73\u5747\u503c\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DAY': functionDescription('\u8be5\u51fd\u6570\u5c06\u5e8f\u5217\u53f7\u8f6c\u6362\u4e3a\u6708\u4efd\u65e5\u671f\u3002', [
            parameterDescription('date')
        ]),
        'DAYS360': functionDescription('\u8be5\u51fd\u6570\u4ee5\u4e00\u5e74360\u5929\u4e3a\u57fa\u51c6\u8ba1\u7b97\u4e24\u4e2a\u65e5\u671f\u95f4\u7684\u5929\u6570\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('method')
        ]),
        'DB': functionDescription('\u8be5\u51fd\u6570\u4f7f\u7528\u56fa\u5b9a\u4f59\u989d\u9012\u51cf\u6cd5\uff0c\u8fd4\u56de\u4e00\u7b14\u8d44\u4ea7\u5728\u7ed9\u5b9a\u671f\u95f4\u5185\u7684\u6298\u65e7\u503c\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period'),
            parameterDescription('month')
        ]),
        'DCOUNT': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u6570\u636e\u5e93\u4e2d\u5305\u542b\u6570\u5b57\u7684\u5355\u5143\u683c\u7684\u6570\u91cf\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DCOUNTA': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u6570\u636e\u5e93\u4e2d\u975e\u7a7a\u5355\u5143\u683c\u7684\u6570\u91cf\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DDB': functionDescription('\u8be5\u51fd\u6570\u4f7f\u7528\u53cc\u500d\u4f59\u989d\u9012\u51cf\u6cd5\u6216\u5176\u4ed6\u6307\u5b9a\u65b9\u6cd5\uff0c\u8fd4\u56de\u4e00\u7b14\u8d44\u4ea7\u5728\u7ed9\u5b9a\u671f\u95f4\u5185\u7684\u6298\u65e7\u503c\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period'),
            parameterDescription('factor')
        ]),
        'DEC2BIN': functionDescription('\u8be5\u51fd\u6570\u5c06\u5341\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u4e8c\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEC2HEX': functionDescription('\u8be5\u51fd\u6570\u5c06\u5341\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u5341\u516d\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEC2OCT': functionDescription('\u8be5\u51fd\u6570\u5c06\u5341\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u516b\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEGREES': functionDescription('\u8be5\u51fd\u6570\u5c06\u5f27\u5ea6\u8f6c\u6362\u4e3a\u5ea6\u3002', [
            parameterDescription('angle')
        ]),
        'DELTA': functionDescription('\u8be5\u51fd\u6570\u6d4b\u8bd5\u4e24\u4e2a\u6570\u503c\u662f\u5426\u76f8\u7b49\u3002\u5982\u679cnumber1= number2\uff0c\u5219\u8fd4\u56de1\uff0c\u5426\u5219\u8fd4\u56de0\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'DEVSQ': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6570\u636e\u70b9\u4e0e\u5404\u81ea\u6837\u672c\u5e73\u5747\u503c\u504f\u5dee\u7684\u5e73\u65b9\u548c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'DGET': functionDescription('\u8be5\u51fd\u6570\u4ece\u5217\u8868\u6216\u6570\u636e\u5e93\u7684\u5217\u4e2d\u63d0\u53d6\u7b26\u5408\u6307\u5b9a\u6761\u4ef6\u7684\u5355\u4e2a\u503c\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DISC': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6709\u4ef7\u8bc1\u5238\u7684\u8d34\u73b0\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'DMAX': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6700\u5927\u6570\u5b57\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DMIN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6700\u5c0f\u6570\u5b57\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DOLLAR': functionDescription('\u8be5\u51fd\u6570\u4f9d\u7167\u8d27\u5e01\u683c\u5f0f\u5c06\u5c0f\u6570\u56db\u820d\u4e94\u5165\u5230\u6307\u5b9a\u7684\u4f4d\u6570\u5e76\u8f6c\u6362\u6210\u6587\u672c\u3002', [
            parameterDescription('number'),
            parameterDescription('decimals')
        ]),
        'DOLLARDE': functionDescription('\u8be5\u51fd\u6570\u5c06\u6309\u5206\u6570\u8868\u793a\u7684\u4ef7\u683c\u8f6c\u6362\u4e3a\u6309\u5c0f\u6570\u8868\u793a\u7684\u4ef7\u683c\u3002', [
            parameterDescription('fractional_dollar'),
            parameterDescription('fraction')
        ]),
        'DOLLARFR': functionDescription('\u8be5\u51fd\u6570\u5c06\u4ee5\u5c0f\u6570\u8868\u793a\u7684\u4ef7\u683c\u8f6c\u6362\u4e3a\u4ee5\u5206\u6570\u8868\u793a\u7684\u4ef7\u683c\u3002', [
            parameterDescription('decimal_dollar'),
            parameterDescription('fraction')
        ]),
        'DPRODUCT': functionDescription('\u8be5\u51fd\u6570\u5c06\u6570\u636e\u5e93\u4e2d\u7b26\u5408\u6761\u4ef6\u7684\u8bb0\u5f55\u7684\u7279\u5b9a\u5b57\u6bb5\u4e2d\u7684\u503c\u76f8\u4e58\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSTDEV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5229\u7528\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6570\u5b57\u4f5c\u4e3a\u4e00\u4e2a\u6837\u672c\u4f30\u7b97\u51fa\u7684\u6837\u672c\u603b\u4f53\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSTDEVP': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5229\u7528\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6570\u5b57\u4f5c\u4e3a\u6837\u672c\u603b\u4f53\u8ba1\u7b97\u51fa\u7684\u603b\u4f53\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSUM': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6570\u5b57\u4e4b\u548c\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DURATION': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5047\u8bbe\u9762\u503c \uffe5100 \u7684\u5b9a\u671f\u4ed8\u606f\u6709\u4ef7\u8bc1\u5238\u7684\u4fee\u6b63\u671f\u9650\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('coupon'),
            parameterDescription('yield'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'DVAR': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5229\u7528\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6570\u5b57\u4f5c\u4e3a\u4e00\u4e2a\u6837\u672c\u4f30\u7b97\u51fa\u7684\u6837\u672c\u603b\u4f53\u65b9\u5dee\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DVARP': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5229\u7528\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u6ee1\u8db3\u6307\u5b9a\u6761\u4ef6\u7684\u8bb0\u5f55\u5b57\u6bb5\uff08\u5217\uff09\u4e2d\u7684\u6570\u5b57\u4f5c\u4e3a\u6837\u672c\u603b\u4f53\u8ba1\u7b97\u51fa\u7684\u6837\u672c\u603b\u4f53\u65b9\u5dee\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'EDATE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u7528\u4e8e\u8868\u793a\u5f00\u59cb\u65e5\u671f\u4e4b\u524d\u6216\u4e4b\u540e\u6708\u6570\u7684\u65e5\u671f\u7684\u5e8f\u5217\u53f7\u3002', [
            parameterDescription('start_date'),
            parameterDescription('months')
        ]),
        'EFFECT': functionDescription('\u8be5\u51fd\u6570\u5229\u7528\u7ed9\u5b9a\u7684\u540d\u4e49\u5e74\u5229\u7387\u548c\u6bcf\u5e74\u7684\u590d\u5229\u671f\u6570\uff0c\u8ba1\u7b97\u6709\u6548\u7684\u5e74\u5229\u7387\u3002', [
            parameterDescription('nominal_rate'),
            parameterDescription('npery')
        ]),
        'EOMONTH': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u6708\u6570\u4e4b\u524d\u6216\u4e4b\u540e\u7684\u6708\u4efd\u7684\u6700\u540e\u4e00\u5929\u7684\u5e8f\u5217\u53f7\u3002', [
            parameterDescription('start_date'),
            parameterDescription('months')
        ]),
        'ERF': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u8bef\u5dee\u51fd\u6570\u5728\u4e0a\u4e0b\u9650\u4e4b\u95f4\u7684\u79ef\u5206\u3002', [
            parameterDescription('lower_limit'),
            parameterDescription('upper_limit')
        ]),
        'ERFC': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ece x \u5230 \u221e\uff08\u65e0\u7a77\uff09\u79ef\u5206\u7684 ERF \u51fd\u6570\u7684\u8865\u4f59\u8bef\u5dee\u51fd\u6570\u3002', [
            parameterDescription('lowerlimit')
        ]),
        'ERROR.TYPE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u5bf9\u5e94\u4e8e\u9519\u8bef\u7c7b\u578b\u7684\u6570\u5b57\u3002', [
            parameterDescription('error_val')
        ]),
        'EURO': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e00\u6b27\u5143\u7b49\u4ef7\u8d27\u5e01\u91d1\u989d\uff0c\u57fa\u4e8eISO\u8d27\u5e01\u4ee3\u7801\u3002', [
            parameterDescription('code')
        ]),
        'EUROCONVERT': functionDescription('\u8be5\u51fd\u6570\u5c06\u6570\u5b57\u8f6c\u6362\u4e3a\u6b27\u5143\u5f62\u5f0f\uff0c\u5c06\u6570\u5b57\u7531\u6b27\u5143\u5f62\u5f0f\u8f6c\u6362\u4e3a\u6b27\u76df\u6210\u5458\u56fd\u8d27\u5e01\u5f62\u5f0f\uff0c\u6216\u5229\u7528\u6b27\u5143\u4f5c\u4e3a\u4e2d\u95f4\u8d27\u5e01\u5c06\u6570\u5b57\u7531\u67d0\u4e00\u6b27\u76df\u6210\u5458\u56fd\u8d27\u5e01\u8f6c\u5316\u4e3a\u53e6\u4e00\u6b27\u76df\u6210\u5458\u56fd\u8d27\u5e01\u7684\u5f62\u5f0f\uff08\u4e09\u89d2\u8f6c\u6362\u5173\u7cfb\uff09\u3002\u53ea\u6709\u91c7\u7528\u4e86\u6b27\u5143\u7684\u6b27\u76df (EU) \u6210\u5458\u56fd\u8d27\u5e01\u624d\u80fd\u8fdb\u884c\u8fd9\u4e9b\u8f6c\u6362\u3002\u6b64\u51fd\u6570\u6240\u4f7f\u7528\u7684\u662f\u7531\u6b27\u76df (EU) \u5efa\u7acb\u7684\u56fa\u5b9a\u8f6c\u6362\u6c47\u7387\u3002', [
            parameterDescription('number'),
            parameterDescription('source'),
            parameterDescription('target'),
            parameterDescription('full_precision'),
            parameterDescription('triangulation_precision')
        ]),
        'EVEN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6cbf\u7edd\u5bf9\u503c\u589e\u5927\u65b9\u5411\u53d6\u6574\u540e\u6700\u63a5\u8fd1\u7684\u5076\u6570\u3002', [
            parameterDescription('number')
        ]),
        'EXACT': functionDescription('\u8be5\u51fd\u6570\u7528\u4e8e\u6bd4\u8f83\u4e24\u4e2a\u5b57\u7b26\u4e32\uff1a\u5982\u679c\u5b83\u4eec\u5b8c\u5168\u76f8\u540c\uff0c\u5219\u8fd4\u56de TRUE\uff1b\u5426\u5219\uff0c\u8fd4\u56de FALSE\u3002', [
            parameterDescription('text1'),
            parameterDescription('text2')
        ]),
        'EXP': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de e \u7684 n \u6b21\u65b9\u3002', [
            parameterDescription('number')
        ]),
        'EXPONDIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6307\u6570\u5206\u5e03\u3002', [
            parameterDescription('value'),
            parameterDescription('lambda'),
            parameterDescription('cumulative')
        ]),
        'FACT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u9636\u4e58\u3002', [
            parameterDescription('number')
        ]),
        'FACTDOUBLE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u53cc\u500d\u9636\u4e58\u3002', [
            parameterDescription('number')
        ]),
        'FALSE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u903b\u8f91\u503c FALSE\u3002', []),
        'FDIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de F \u6982\u7387\u5206\u5e03\u3002\u4f7f\u7528\u6b64\u51fd\u6570\u53ef\u4ee5\u786e\u5b9a\u4e24\u4e2a\u6570\u636e\u96c6\u662f\u5426\u5b58\u5728\u53d8\u5316\u7a0b\u5ea6\u4e0a\u7684\u4e0d\u540c\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'FIND': functionDescription('\u8be5\u51fd\u6570\u5728\u4e00\u4e2a\u6587\u672c\u503c\u4e2d\u67e5\u627e\u53e6\u4e00\u4e2a\u6587\u672c\u503c\uff08\u533a\u5206\u5927\u5c0f\u5199\uff09\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'FINV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de F \u6982\u7387\u5206\u5e03\u7684\u53cd\u51fd\u6570\u503c\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'FISHER': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de Fisher \u53d8\u6362\u503c\u3002', [
            parameterDescription('value')
        ]),
        'FISHERINV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de Fisher \u53d8\u6362\u7684\u53cd\u51fd\u6570\u503c\u3002', [
            parameterDescription('value')
        ]),
        'FIXED': functionDescription('\u8be5\u51fd\u6570\u5c06\u6570\u5b57\u6309\u6307\u5b9a\u7684\u5c0f\u6570\u4f4d\u6570\u8fdb\u884c\u53d6\u6574\uff0c\u5229\u7528\u53e5\u53f7\u548c\u9017\u53f7\uff0c\u4ee5\u5c0f\u6570\u683c\u5f0f\u5bf9\u8be5\u6570\u8fdb\u884c\u683c\u5f0f\u8bbe\u7f6e\uff0c\u5e76\u4ee5\u6587\u672c\u5f62\u5f0f\u8fd4\u56de\u7ed3\u679c\u3002', [
            parameterDescription('number'),
            parameterDescription('decimals'),
            parameterDescription('no_commas')
        ]),
        'FLOOR': functionDescription('\u8be5\u51fd\u6570\u5411\u7edd\u5bf9\u503c\u51cf\u5c0f\u7684\u65b9\u5411\u820d\u5165\u6570\u5b57\u3002', [
            parameterDescription('number'),
            parameterDescription('significance')
        ]),
        'FORECAST': functionDescription('\u8be5\u51fd\u6570\u6839\u636e\u5df2\u6709\u7684\u6570\u503c\u8ba1\u7b97\u6216\u9884\u6d4b\u672a\u6765\u503c\u3002', [
            parameterDescription('value'),
            parameterDescription('Yarray'),
            parameterDescription('Xarray')
        ]),
        'FREQUENCY': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u6570\u503c\u5728\u67d0\u4e2a\u533a\u57df\u5185\u7684\u51fa\u73b0\u9891\u7387\uff0c\u7136\u540e\u8fd4\u56de\u4e00\u4e2a\u5782\u76f4\u6570\u7ec4\u3002', [
            parameterDescription('data_array'),
            parameterDescription('bins_array')
        ]),
        'FTEST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de F \u68c0\u9a8c\u7684\u7ed3\u679c\u3002F \u68c0\u9a8c\u8fd4\u56de\u7684\u662f\u5f53\u6570\u7ec4 1 \u548c\u6570\u7ec4 2 \u7684\u65b9\u5dee\u65e0\u660e\u663e\u5dee\u5f02\u65f6\u7684\u5355\u5c3e\u6982\u7387\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'FV': functionDescription('\u8be5\u51fd\u6570\u57fa\u4e8e\u56fa\u5b9a\u5229\u7387\u53ca\u7b49\u989d\u5206\u671f\u4ed8\u6b3e\u65b9\u5f0f\uff0c\u8fd4\u56de\u67d0\u9879\u6295\u8d44\u7684\u672a\u6765\u503c\u3002', [
            parameterDescription('rate'),
            parameterDescription('numper'),
            parameterDescription('paymt'),
            parameterDescription('pval'),
            parameterDescription('type')
        ]),
        'FVSCHEDULE': functionDescription('\u8be5\u51fd\u6570\u57fa\u4e8e\u4e00\u7cfb\u5217\u590d\u5229\u8fd4\u56de\u672c\u91d1\u7684\u672a\u6765\u503c\u3002\u51fd\u6570 FVSCHEDULE \u7528\u4e8e\u8ba1\u7b97\u67d0\u9879\u6295\u8d44\u5728\u53d8\u52a8\u6216\u53ef\u8c03\u5229\u7387\u4e0b\u7684\u672a\u6765\u503c\u3002', [
            parameterDescription('principal'),
            parameterDescription('schedule')
        ]),
        'GAMMADIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de \u03b3 \u5206\u5e03\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'GAMMAINV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de \u03b3 \u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta')
        ]),
        'GAMMALN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de \u03b3 \u51fd\u6570\u7684\u81ea\u7136\u5bf9\u6570\uff0c\u0393(x)\u3002', [
            parameterDescription('value')
        ]),
        'GCD': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6700\u5927\u516c\u7ea6\u6570\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'GEOMEAN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u6b63\u6570\u6570\u7ec4\u6216\u533a\u57df\u7684\u51e0\u4f55\u5e73\u5747\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'GESTEP': functionDescription('\u8be5\u51fd\u6570\u68c0\u9a8c\u6570\u5b57\u662f\u5426\u5927\u4e8e\u9608\u503c\u3002', [
            parameterDescription('number'),
            parameterDescription('step')
        ]),
        'GROWTH': functionDescription('\u8be5\u51fd\u6570\u6839\u636e\u73b0\u6709\u7684\u6570\u636e\u9884\u6d4b\u6307\u6570\u589e\u957f\u503c\u3002\u6839\u636e\u73b0\u6709\u7684 x \u503c\u548c y \u503c\uff0cGROWTH \u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u65b0\u7684 x \u503c\u5bf9\u5e94\u7684 y \u503c\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('newx'),
            parameterDescription('constant')
        ]),
        'HARMEAN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u8c03\u548c\u5e73\u5747\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'HEX2BIN': functionDescription('\u8be5\u51fd\u6570\u5c06\u5341\u516d\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u4e8c\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'HEX2DEC': functionDescription('\u8be5\u51fd\u6570\u5c06\u5341\u516d\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u5341\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number')
        ]),
        'HEX2OCT': functionDescription('\u8be5\u51fd\u6570\u5c06\u5341\u516d\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u516b\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'HLOOKUP': functionDescription('\u8be5\u51fd\u6570\u67e5\u627e\u6570\u7ec4\u7684\u9996\u884c\uff0c\u5e76\u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u7684\u503c\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('table_array'),
            parameterDescription('row_index_num'),
            parameterDescription('range_lookup', false, true, RANGE_LOOKUP_ENUM_INFO)
        ]),
        'HOUR': functionDescription('\u8fd4\u56de\u65f6\u95f4\u503c\u7684\u5c0f\u65f6\u6570\u3002', [
            parameterDescription('time')
        ]),
        'HYPGEOMDIST': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u8d85\u51e0\u4f55\u5206\u5e03\u3002 ', [
            parameterDescription('sample_s'),
            parameterDescription('number_sample'),
            parameterDescription('population_s'),
            parameterDescription('number_pop')
        ]),
        'IF': functionDescription('\u4f7f\u7528\u903b\u8f91\u51fd\u6570 IF \u51fd\u6570\u65f6\uff0c\u5982\u679c\u6761\u4ef6\u4e3a\u771f\uff0c\u8be5\u51fd\u6570\u5c06\u8fd4\u56de\u4e00\u4e2a\u503c\uff1b\u5982\u679c\u6761\u4ef6\u4e3a\u5047\uff0c\u51fd\u6570\u5c06\u8fd4\u56de\u53e6\u4e00\u4e2a\u503c\u3002', [
            parameterDescription('logical_test'),
            parameterDescription('value_if_true'),
            parameterDescription('value_if_false')
        ]),
        'IFERROR': functionDescription('\u8be5\u51fd\u6570\u5982\u679c\u516c\u5f0f\u7684\u8ba1\u7b97\u7ed3\u679c\u9519\u8bef\uff0c\u5219\u8fd4\u56de\u60a8\u6307\u5b9a\u7684\u503c\uff1b\u5426\u5219\u8fd4\u56de\u516c\u5f0f\u7684\u7ed3\u679c\u3002', [
            parameterDescription('value'),
            parameterDescription('value_if_error')
        ]),
        'IMABS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u590d\u6570\u7684\u7edd\u5bf9\u503c\uff08\u6a21\u6570\uff09\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMAGINARY': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u590d\u6570\u7684\u865a\u7cfb\u6570\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMARGUMENT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u53c2\u6570 theta\uff0c\u5373\u4ee5\u5f27\u5ea6\u8868\u793a\u7684\u89d2\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCONJUGATE': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u5171\u8f6d\u590d\u6570\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCOS': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u4f59\u5f26\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMDIV': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u4e24\u4e2a\u590d\u6570\u7684\u5546\u3002', [
            parameterDescription('complexnum'),
            parameterDescription('complexdenom')
        ]),
        'IMEXP': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u6307\u6570\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMLN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u81ea\u7136\u5bf9\u6570\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMLOG2': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u4ee5 2 \u4e3a\u5e95\u6570\u7684\u5bf9\u6570\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMLOG10': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x + yi \u6216 x + yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u5e38\u7528\u5bf9\u6570\uff08\u4ee5 10 \u4e3a\u5e95\u6570\uff09\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMPOWER': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684 n \u6b21\u5e42\u3002', [
            parameterDescription('complexnum'),
            parameterDescription('powernum')
        ]),
        'IMPRODUCT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684 1 \u81f3 29 \u4e2a\u590d\u6570\u7684\u4e58\u79ef\u3002', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2', true)
        ]),
        'IMREAL': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u5b9e\u7cfb\u6570\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSIN': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u6b63\u5f26\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSQRT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u5e73\u65b9\u6839\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSUB': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u4e24\u4e2a\u590d\u6570\u7684\u5dee\u3002', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2')
        ]),
        'IMSUM': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u4e24\u4e2a\u6216\u591a\u4e2a\u590d\u6570\u7684\u548c\u3002', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2', true)
        ]),
        'INDEX': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u8868\u683c\u6216\u533a\u57df\u4e2d\u7684\u503c\u6216\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('row_num'),
            parameterDescription('column_num'),
            parameterDescription('area_num')
        ]),
        'INDIRECT': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u7531\u6587\u672c\u5b57\u7b26\u4e32\u6307\u5b9a\u7684\u5f15\u7528\u3002', [
            parameterDescription('ref_text'),
            parameterDescription('[a1_style]')
        ]),
        'INT': functionDescription('\u8be5\u51fd\u6570\u5c06\u6570\u5b57\u5411\u4e0b\u820d\u5165\u5230\u6700\u63a5\u8fd1\u7684\u6574\u6570\u3002', [
            parameterDescription('number')
        ]),
        'INTERCEPT': functionDescription('\u8be5\u51fd\u6570\u5229\u7528\u73b0\u6709\u7684 x \u503c\u4e0e y \u503c\u8ba1\u7b97\u5e76\u8fd4\u56de\u76f4\u7ebf\u4e0e y \u8f74\u7684\u622a\u8ddd\u3002', [
            parameterDescription('dependent'),
            parameterDescription('independent')
        ]),
        'INTRATE': functionDescription('\u8be5\u51fd\u6570\u8ba1\u7b97\u5e76\u8fd4\u56de\u4e00\u6b21\u6027\u4ed8\u606f\u8bc1\u5238\u7684\u5229\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('investment'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'IPMT': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u56fa\u5b9a\u5229\u7387\u53ca\u7b49\u989d\u5206\u671f\u4ed8\u6b3e\u65b9\u5f0f\uff0c\u8fd4\u56de\u7ed9\u5b9a\u671f\u6570\u5185\u5bf9\u6295\u8d44\u7684\u5229\u606f\u507f\u8fd8\u989d\u3002', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'IRR': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u7531\u6570\u503c\u4ee3\u8868\u7684\u4e00\u7ec4\u73b0\u91d1\u6d41\u7684\u5185\u90e8\u6536\u76ca\u7387\u3002', [
            parameterDescription('arrayvals'),
            parameterDescription('estimate')
        ]),
        'ISBLANK': functionDescription('\u8be5\u51fd\u6570\u68c0\u9a8c\u6307\u5b9a\u7684\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u4e3a\u7a7a\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISERR': functionDescription('\u8be5\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u4e3a\u9664\u53bb #N/A \u7684\u4efb\u610f\u9519\u8bef\u503c\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISERROR': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u4e3a\u4efb\u610f\u9519\u8bef\u503c\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISEVEN': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u4e3a\u5076\u6570\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISLOGICAL': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u662f\u4e00\u4e2a\u903b\u8f91\u503c\uff08\u5e03\u5c14\u578b\u503c\uff09\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISNA': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u5305\u542b\u503c\u4e0d\u5b58\u5728\uff08#N/A\uff09\u9519\u8bef\u503c\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISNONTEXT': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u4e3a\u4e0d\u662f\u6587\u672c\u7684\u4efb\u610f\u6570\u636e\u7c7b\u578b\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISNUMBER': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u662f\u6570\u503c\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISODD': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u4e3a\u5947\u6570\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISPMT': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u7279\u5b9a\u6295\u8d44\u671f\u5185\u8981\u652f\u4ed8\u7684\u5229\u606f\u3002', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pv')
        ]),
        'ISREF': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5230\u53e6\u5916\u5355\u5143\u683c\u7684\u5f15\u7528\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISTEXT': functionDescription('\u6b64\u51fd\u6570\u68c0\u9a8c\u4e00\u4e2a\u503c\uff0c\u8868\u8fbe\u5f0f\u6216\u8005\u5f15\u7528\u5355\u5143\u683c\u7684\u5185\u5bb9\u662f\u5426\u662f\u6587\u672c\u578b\u6570\u636e\u3002', [
            parameterDescription('cellreference')
        ]),
        'KURT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u7684\u5cf0\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2'),
            parameterDescription('number3'),
            parameterDescription('number4', true)
        ]),
        'LARGE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u4e2d\u7b2c n \u4e2a\u6700\u5927\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'LCM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6574\u6570\u7684\u6700\u5c0f\u516c\u500d\u6570\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'LEFT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7b2c\u4e00\u4e2a\u5b57\u7b26\u6216\u524d\u51e0\u4e2a\u5b57\u7b26\u3002', [
            parameterDescription('text'),
            parameterDescription('num_chars')
        ]),
        'LEN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7684\u5b57\u7b26\u6570\u3002', [
            parameterDescription('text')
        ]),
        'LINEST': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u4e0e\u73b0\u6709\u6570\u636e\u6700\u4f73\u62df\u5408\u7684\u76f4\u7ebf\uff0c\u6765\u8ba1\u7b97\u67d0\u76f4\u7ebf\u7684\u7edf\u8ba1\u503c\uff0c\u7136\u540e\u8fd4\u56de\u63cf\u8ff0\u6b64\u76f4\u7ebf\u7684\u6570\u7ec4\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('constant'),
            parameterDescription('stats')
        ]),
        'LN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u6570\u5b57\u7684\u81ea\u7136\u5bf9\u6570\u3002', [
            parameterDescription('value')
        ]),
        'LOG': functionDescription('\u6b64\u51fd\u6570\u6309\u6240\u6307\u5b9a\u7684\u5e95\u6570\uff0c\u8fd4\u56de\u4e00\u4e2a\u6570\u7684\u5bf9\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('base')
        ]),
        'LOG10': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 10 \u4e3a\u5e95\u7684\u5bf9\u6570\u3002', [
            parameterDescription('number')
        ]),
        'LOGEST': functionDescription('\u6b64\u51fd\u6570\u5728\u56de\u5f52\u5206\u6790\u4e2d\uff0c\u8ba1\u7b97\u6700\u7b26\u5408\u6570\u636e\u7684\u6307\u6570\u56de\u5f52\u62df\u5408\u66f2\u7ebf\uff0c\u5e76\u8fd4\u56de\u63cf\u8ff0\u8be5\u66f2\u7ebf\u7684\u6570\u503c\u6570\u7ec4\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('constant'),
            parameterDescription('stats')
        ]),
        'LOGINV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de x \u7684\u5bf9\u6570\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\uff0c\u6b64\u5904\u7684 LN(x) \u662f\u542b\u6709 mean \u4e0e stdev \u53c2\u6570\u7684\u6b63\u6001\u5206\u5e03\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'LOGNORMDIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de x \u7684\u5bf9\u6570\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\uff0c\u5176\u4e2d ln(x) \u662f\u670d\u4ece\u53c2\u6570 mean \u548c stdev \u7684\u6b63\u6001\u5206\u5e03\u3002\u4f7f\u7528\u6b64\u51fd\u6570\u53ef\u4ee5\u5206\u6790\u7ecf\u8fc7\u5bf9\u6570\u53d8\u6362\u7684\u6570\u636e\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
        ]),
        'LOOKUP': functionDescription('\u6b64\u51fd\u6570\u53ef\u4ece\u5355\u884c\u6216\u5355\u5217\u533a\u57df\u6216\u8005\u4ece\u4e00\u4e2a\u6570\u7ec4\u8fd4\u56de\u503c\u3002LOOKUP \u51fd\u6570\u5177\u6709\u4e24\u79cd\u8bed\u6cd5\u5f62\u5f0f\uff1a\u5411\u91cf\u5f62\u5f0f\u548c\u6570\u7ec4\u5f62\u5f0f\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_vector'),
            parameterDescription('result_vector')
        ]),
        'LOWER': functionDescription('\u6b64\u51fd\u6570\u5c06\u4e00\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7684\u6240\u6709\u5927\u5199\u5b57\u6bcd\u8f6c\u6362\u4e3a\u5c0f\u5199\u5b57\u6bcd\u3002', [
            parameterDescription('text')
        ]),
        'MATCH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u503c\u5728\u5355\u5143\u683c\u533a\u57df\u4e2d\u7684\u76f8\u5bf9\u4f4d\u7f6e\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription('match_type')
        ]),
        'XMATCH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u9879\u76ee\u5728\u6570\u7ec4\u4e2d\u7684\u76f8\u5bf9\u4f4d\u7f6e\u3002\u9ed8\u8ba4\u60c5\u51b5\u4e0b\uff0c\u9700\u8981\u7cbe\u786e\u5339\u914d\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription(MATCH_MODE, false, true, LOOKUP_MATCH_MODE_ENUM_INFO),
            parameterDescription('search_mode', false, true, LOOKUP_SEARCH_MODE_ENUM_INFO)
        ]),
        'XLOOKUP': functionDescription('\u5728\u67d0\u4e2a\u8303\u56f4\u6216\u6570\u7ec4\u4e2d\u641c\u7d22\u5339\u914d\u9879\uff0c\u5e76\u901a\u8fc7\u7b2c\u4e8c\u4e2a\u8303\u56f4\u6216\u6570\u7ec4\u8fd4\u56de\u76f8\u5e94\u7684\u9879\u3002\u9ed8\u8ba4\u60c5\u51b5\u4e0b\u4f7f\u7528\u7cbe\u786e\u5339\u914d\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription('return_array'),
            parameterDescription(IF_NOT_FOUND),
            parameterDescription(MATCH_MODE, false, true, LOOKUP_MATCH_MODE_ENUM_INFO),
            parameterDescription('search_mode', false, true, LOOKUP_SEARCH_MODE_ENUM_INFO)
        ]),
        'MAX': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u503c\u4e2d\u7684\u6700\u5927\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MAXA': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u5217\u8868\u4e2d\u7684\u6700\u5927\u503c\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'MDETERM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u7ec4\u7684\u77e9\u9635\u884c\u5217\u5f0f\u7684\u503c\u3002', [
            parameterDescription('array')
        ]),
        'MDURATION': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5047\u8bbe\u9762\u503c \uffe5100 \u7684\u6709\u4ef7\u8bc1\u5238\u7684 Macauley \u4fee\u6b63\u671f\u9650\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('coupon'),
            parameterDescription('yield'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'MEDIAN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u6570\u503c\u7684\u4e2d\u503c\u3002\u4e2d\u503c\u662f\u5728\u4e00\u7ec4\u6570\u503c\u4e2d\u5c45\u4e8e\u4e2d\u95f4\u7684\u6570\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MID': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u4ece\u6307\u5b9a\u4f4d\u7f6e\u5f00\u59cb\u7684\u7279\u5b9a\u6570\u76ee\u7684\u5b57\u7b26\uff0c\u8be5\u6570\u76ee\u7531\u7528\u6237\u6307\u5b9a\u3002', [
            parameterDescription('text'),
            parameterDescription('start_num'),
            parameterDescription('num_chars')
        ]),
        'MIN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u503c\u4e2d\u7684\u6700\u5c0f\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MINA': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u5305\u542b\u6587\u672c\u548c\u903b\u8f91\u503c\u7684\u503c\u4e2d\u7684\u6700\u5c0f\u503c\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'MINUTE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u65f6\u95f4\u503c\u4e2d\u7684\u5206\u949f\u3002\u4e3a\u4e00\u4e2a\u4ecb\u4e8e 0 \u5230 59 \u4e4b\u95f4\u7684\u6574\u6570\u3002', [
            parameterDescription('time')
        ]),
        'MINVERSE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u7ec4\u4e2d\u5b58\u50a8\u7684\u77e9\u9635\u7684\u9006\u8ddd\u9635\u3002', [
            parameterDescription('array')
        ]),
        'MIRR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u4e00\u8fde\u7eed\u671f\u95f4\u5185\u73b0\u91d1\u6d41\u7684\u4fee\u6b63\u5185\u90e8\u6536\u76ca\u7387\u3002 \u51fd\u6570 MIRR \u540c\u65f6\u8003\u8651\u4e86\u6295\u8d44\u7684\u6210\u672c\u548c\u73b0\u91d1\u518d\u6295\u8d44\u7684\u6536\u76ca\u7387\u3002', [
            parameterDescription('values'),
            parameterDescription('finance_rate'),
            parameterDescription('reinvest_rate')
        ]),
        'MMULT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u6570\u7ec4\u7684\u77e9\u9635\u4e58\u79ef\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'MOD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u6570\u76f8\u9664\u7684\u4f59\u6570\u3002', [
            parameterDescription('dividend'),
            parameterDescription('divisor')
        ]),
        'MODE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5728\u67d0\u4e00\u6570\u7ec4\u6216\u6570\u636e\u533a\u57df\u4e2d\u51fa\u73b0\u9891\u7387\u6700\u591a\u7684\u6570\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MONTH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5\u5e8f\u5217\u53f7\u8868\u793a\u7684\u65e5\u671f\u4e2d\u7684\u6708\u4efd\u3002', [
            parameterDescription('date')
        ]),
        'MROUND': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u6309\u6307\u5b9a\u57fa\u6570\u820d\u5165\u540e\u7684\u6570\u503c\u3002', [
            parameterDescription('number'),
            parameterDescription('multiple')
        ]),
        'MULTINOMIAL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u548c\u7684\u9636\u4e58\u4e0e\u5404\u53c2\u6570\u9636\u4e58\u4e58\u79ef\u7684\u6bd4\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MUNIT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u7ef4\u5ea6\u7684\u5355\u4f4d\u77e9\u9635\u3002', [
            parameterDescription('dimension'),
        ]),
        'N': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u8f6c\u5316\u4e3a\u6570\u503c\u540e\u7684\u503c\u3002', [
            parameterDescription('value')
        ]),
        'NA': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u9519\u8bef\u503c #N/A\u3002\u9519\u8bef\u503c #N/A \u8868\u793a\u201c\u65e0\u6cd5\u5f97\u5230\u6709\u6548\u503c\u201d\u3002', []),
        'SHEET': functionDescription('\u8fd4\u56de\u5f15\u7528\u5de5\u4f5c\u8868\u7684\u5de5\u4f5c\u8868\u7f16\u53f7\u3002', [
            parameterDescription('[value]'),
        ]),
        'SHEETS': functionDescription('\u8fd4\u56de\u5f15\u7528\u4e2d\u7684\u5de5\u4f5c\u8868\u6570\u3002', [
            parameterDescription('[reference]')
        ]),
        'NEGBINOMDIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u8d1f\u4e8c\u9879\u5f0f\u5206\u5e03\u3002', [
            parameterDescription('number_f'),
            parameterDescription('number_s'),
            parameterDescription('probability_s')
        ]),
        'NETWORKDAYS': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u5f00\u59cb\u65e5\u671f\u548c\u7ed3\u675f\u65e5\u671f\u4e4b\u95f4\u5b8c\u6574\u7684\u5de5\u4f5c\u65e5\u6570\u503c\u3002 \u5de5\u4f5c\u65e5\u4e0d\u5305\u62ec\u5468\u672b\u548c\u4e13\u95e8\u6307\u5b9a\u7684\u5047\u671f\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('holidays')
        ]),
        'NOMINAL': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u7ed9\u5b9a\u7684\u5b9e\u9645\u5229\u7387\u548c\u5e74\u590d\u5229\u671f\u6570\uff0c\u8fd4\u56de\u540d\u4e49\u5e74\u5229\u7387\u3002', [
            parameterDescription('effect_rate'),
            parameterDescription('npery')
        ]),
        'NORMDIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u5e73\u5747\u503c\u548c\u6807\u51c6\u504f\u5dee\u7684\u6b63\u6001\u5206\u5e03\u51fd\u6570\u3002 \u6b64\u51fd\u6570\u5728\u7edf\u8ba1\u65b9\u9762\u5e94\u7528\u8303\u56f4\u5e7f\u6cdb\uff08\u5305\u62ec\u5047\u8bbe\u68c0\u9a8c\uff09\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
            parameterDescription('cumulative')
        ]),
        'NORMINV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u5e73\u5747\u503c\u548c\u6807\u51c6\u504f\u5dee\u7684\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORMSDIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6807\u51c6\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u3002', [
            parameterDescription('value')
        ]),
        'NORMSINV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6807\u51c6\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\u3002\u8be5\u5206\u5e03\u7684\u5e73\u5747\u503c\u4e3a 0\uff0c\u6807\u51c6\u504f\u5dee\u4e3a 1\u3002', [
            parameterDescription('probability')
        ]),
        'NOT': functionDescription('\u6b64\u51fd\u6570\u5bf9\u53c2\u6570\u503c\u6c42\u53cd\u3002\u5f53\u8981\u786e\u4fdd\u4e00\u4e2a\u503c\u4e0d\u7b49\u4e8e\u67d0\u4e00\u7279\u5b9a\u503c\u65f6\uff0c\u53ef\u4ee5\u4f7f\u7528 NOT \u51fd\u6570\u3002', [
            parameterDescription('logical')
        ]),
        'NOW': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5f53\u524d\u7684\u65e5\u671f\u548c\u65f6\u95f4\u3002', []),
        'NPER': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u56fa\u5b9a\u5229\u7387\u53ca\u7b49\u989d\u5206\u671f\u4ed8\u6b3e\u65b9\u5f0f\uff0c\u8fd4\u56de\u67d0\u9879\u6295\u8d44\u7684\u603b\u671f\u6570\u3002', [
            parameterDescription('rate'),
            parameterDescription('paymt'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'NPV': functionDescription('\u6b64\u51fd\u6570\u901a\u8fc7\u4f7f\u7528\u8d34\u73b0\u7387\u4ee5\u53ca\u4e00\u7cfb\u5217\u672a\u6765\u652f\u51fa\uff08\u8d1f\u503c\uff09\u548c\u6536\u5165\uff08\u6b63\u503c\uff09\uff0c\u8fd4\u56de\u4e00\u9879\u6295\u8d44\u7684\u51c0\u73b0\u503c\u3002', [
            parameterDescription('rate'),
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'OBJECT': functionDescription('\u6b64\u51fd\u6570\u5c06\u5c5e\u6027\u4ee5\u53ca\u8868\u8fbe\u5f0f\u7684\u7ec4\u5408\u5e8f\u5217\u8f6c\u6362\u4e3a\u5bf9\u8c61\u3002', [
            parameterDescription('property1', true),
            parameterDescription('expression1', true)
        ]),
        'OCT2BIN': functionDescription('\u6b64\u51fd\u6570\u5c06\u516b\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u4e8c\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'OCT2DEC': functionDescription('\u6b64\u51fd\u6570\u5c06\u516b\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u5341\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number')
        ]),
        'OCT2HEX': functionDescription('\u6b64\u51fd\u6570\u5c06\u516b\u8fdb\u5236\u6570\u8f6c\u6362\u4e3a\u5341\u516d\u8fdb\u5236\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'ODD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5bf9\u6307\u5b9a\u6570\u503c\u8fdb\u884c\u5411\u4e0a\u820d\u5165\u540e\u7684\u5947\u6570\u3002', [
            parameterDescription('number')
        ]),
        'ODDFPRICE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u9996\u671f\u4ed8\u606f\u65e5\u4e0d\u56fa\u5b9a\uff08\u957f\u671f\u6216\u77ed\u671f\uff09\u7684\u9762\u503c \uffe5100 \u7684\u6709\u4ef7\u8bc1\u5238\u4ef7\u683c\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('first_coupon'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ODDFYIELD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u9996\u671f\u4ed8\u606f\u65e5\u4e0d\u56fa\u5b9a\u7684\u6709\u4ef7\u8bc1\u5238\uff08\u957f\u671f\u6216\u77ed\u671f\uff09\u7684\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('first_coupon'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ODDLPRICE': functionDescription('T\u6b64\u51fd\u6570\u8fd4\u56de\u672b\u671f\u4ed8\u606f\u65e5\u4e0d\u56fa\u5b9a\u7684\u9762\u503c \uffe5100 \u7684\u6709\u4ef7\u8bc1\u5238\uff08\u957f\u671f\u6216\u77ed\u671f\uff09\u7684\u4ef7\u683c\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('last_interest'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ODDLYIELD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u672b\u671f\u4ed8\u606f\u65e5\u4e0d\u56fa\u5b9a\u7684\u6709\u4ef7\u8bc1\u5238\uff08\u957f\u671f\u6216\u77ed\u671f\uff09\u7684\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('last_interest'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'OFFSET': functionDescription('\u6b64\u51fd\u6570\u4ee5\u6307\u5b9a\u7684\u5f15\u7528\u4e3a\u53c2\u7167\u7cfb\uff0c\u901a\u8fc7\u7ed9\u5b9a\u504f\u79fb\u91cf\u5f97\u5230\u65b0\u7684\u5f15\u7528\u3002 \u8fd4\u56de\u7684\u5f15\u7528\u53ef\u4ee5\u4e3a\u4e00\u4e2a\u5355\u5143\u683c\u6216\u5355\u5143\u683c\u533a\u57df\u3002\u5e76\u53ef\u4ee5\u6307\u5b9a\u8fd4\u56de\u7684\u884c\u6570\u6216\u5217\u6570\u3002', [
            parameterDescription('reference'),
            parameterDescription('rows'),
            parameterDescription('cols'),
            parameterDescription('height'),
            parameterDescription('width')
        ]),
        'OR': functionDescription('\u6b64\u51fd\u6570\u5728\u5176\u53c2\u6570\u7ec4\u4e2d\uff0c\u4efb\u4f55\u4e00\u4e2a\u53c2\u6570\u903b\u8f91\u503c\u4e3a TRUE\uff0c\u5373\u8fd4\u56de TRUE\uff1b \u6240\u6709\u53c2\u6570\u7684\u903b\u8f91\u503c\u4e3a FALSE\uff0c\u5373\u8fd4\u56de FALSE\u3002', [
            parameterDescription('logical1'),
            parameterDescription('logical2', true)
        ]),
        'PEARSON': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de Pearson\uff08\u76ae\u5c14\u751f\uff09\u4e58\u79ef\u77e9\u76f8\u5173\u7cfb\u6570 r\uff0c\u8fd9\u662f\u4e00\u4e2a\u8303\u56f4\u5728 -1.0 \u5230 1.0 \u4e4b\u95f4\uff08\u5305\u62ec -1.0 \u548c 1.0 \u5728\u5185\uff09\u7684\u65e0\u91cf\u7eb2\u6307\u6570\uff0c\u53cd\u6620\u4e86\u4e24\u4e2a\u6570\u636e\u96c6\u5408\u4e4b\u95f4\u7684\u7ebf\u6027\u76f8\u5173\u7a0b\u5ea6\u3002 ', [
            parameterDescription('array_ind'),
            parameterDescription('array_dep')
        ]),
        'PERCENTILE': functionDescription('\u6b64\u51fd\u6570 \u8fd4\u56de\u533a\u57df\u4e2d\u6570\u503c\u7684\u7b2c n \u4e2a\u767e\u5206\u70b9\u7684\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'PERCENTRANK': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7279\u5b9a\u6570\u503c\u5728\u4e00\u4e2a\u6570\u636e\u96c6\u4e2d\u7684\u767e\u5206\u6bd4\u6392\u4f4d\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('significance')
        ]),
        'PERMUT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ece\u7ed9\u5b9a\u6570\u76ee\u7684\u5bf9\u8c61\u96c6\u5408\u4e2d\u9009\u53d6\u7684\u82e5\u5e72\u5bf9\u8c61\u7684\u6392\u5217\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'PI': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de PI \u503c 3.1415926536\u3002', []),
        'PMT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5728\u56fa\u5b9a\u5229\u7387\u4e0b,\u8d37\u6b3e\u7684\u7b49\u989d\u5206\u671f\u507f\u8fd8\u989d', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'POISSON': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6cca\u677e\u5206\u5e03\u3002', [
            parameterDescription('nevents'),
            parameterDescription('mean'),
            parameterDescription('cumulative')
        ]),
        'POWER': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u6570\u5b57\u7684\u4e58\u5e42\u3002', [
            parameterDescription('number'),
            parameterDescription('power')
        ]),
        'PPMT': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u56fa\u5b9a\u5229\u7387\u53ca\u7b49\u989d\u5206\u671f\u4ed8\u6b3e\u65b9\u5f0f\uff0c\u8fd4\u56de\u6295\u8d44\u5728\u67d0\u4e00\u7ed9\u5b9a\u671f\u95f4\u5185\u7684\u672c\u91d1\u507f\u8fd8\u989d\u3002', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'PRICE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5b9a\u671f\u4ed8\u606f\u7684\u9762\u503c \uffe5100 \u7684\u6709\u4ef7\u8bc1\u5238\u7684\u4ef7\u683c\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'PRICEDISC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6298\u4ef7\u53d1\u884c\u7684\u9762\u503c \uffe5100 \u7684\u6709\u4ef7\u8bc1\u5238\u7684\u4ef7\u683c\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'PRICEMAT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5230\u671f\u4ed8\u606f\u7684\u9762\u503c \uffe5100 \u7684\u6709\u4ef7\u8bc1\u5238\u7684\u4ef7\u683c\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('basis')
        ]),
        'PROB': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u533a\u57df\u4e2d\u7684\u6570\u503c\u843d\u5728\u6307\u5b9a\u533a\u95f4\u5185\u7684\u6982\u7387\u3002', [
            parameterDescription('x_range'),
            parameterDescription('prob_range'),
            parameterDescription('lower_limit'),
            parameterDescription('upper_limit')
        ]),
        'PRODUCT': functionDescription('\u6b64\u51fd\u6570\u53ef\u8ba1\u7b97\u7528\u4f5c\u53c2\u6570\u7684\u6240\u6709\u6570\u5b57\u7684\u4e58\u79ef\uff0c\u7136\u540e\u8fd4\u56de\u4e58\u79ef\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'PROPER': functionDescription('\u6b64\u51fd\u6570\u5c06\u6587\u672c\u5b57\u7b26\u4e32\u7684\u9996\u5b57\u6bcd\u53ca\u4efb\u4f55\u975e\u5b57\u6bcd\u5b57\u7b26\u4e4b\u540e\u7684\u9996\u5b57\u6bcd\u8f6c\u6362\u6210\u5927\u5199\u3002', [
            parameterDescription('text')
        ]),
        'PROPERTY': functionDescription('\u6b64\u51fd\u6570\u6839\u636e\u5bf9\u8c61\u4e2d\u5c5e\u6027\u7684\u8def\u5f84\u8fd4\u56de\u8be5\u5c5e\u6027\u5bf9\u5e94\u7684\u503c\u3002', [
            parameterDescription('data_expression'),
            parameterDescription('property_path')
        ]),
        'PV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6295\u8d44\u7684\u73b0\u503c\u3002\u73b0\u503c\u4e3a\u4e00\u7cfb\u5217\u672a\u6765\u4ed8\u6b3e\u7684\u5f53\u524d\u503c\u7684\u7d2f\u79ef\u548c\u3002\u4f8b\u5982\uff0c\u501f\u5165\u65b9\u7684\u501f\u5165\u6b3e\u5373\u4e3a\u8d37\u51fa\u65b9\u8d37\u6b3e\u7684\u73b0\u503c\u3002', [
            parameterDescription('rate'),
            parameterDescription('numper'),
            parameterDescription('paymt'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'QUARTILE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u7684\u56db\u5206\u4f4d\u6570\u3002', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'QUOTIENT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5546\u7684\u6574\u6570\u90e8\u5206\uff0c\u8be5\u51fd\u6570\u53ef\u7528\u4e8e\u820d\u6389\u5546\u7684\u5c0f\u6570\u90e8\u5206\u3002', [
            parameterDescription('numerator'),
            parameterDescription('denominator')
        ]),
        'RADIANS': functionDescription('\u6b64\u51fd\u6570\u5c06\u89d2\u5ea6\u8f6c\u6362\u4e3a\u5f27\u5ea6\u3002', [
            parameterDescription('angle')
        ]),
        'RAND': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5927\u4e8e\u7b49\u4e8e 0 \u53ca\u5c0f\u4e8e 1 \u7684\u5747\u5300\u5206\u5e03\u968f\u673a\u5b9e\u6570\u3002', []),
        'RANDBETWEEN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4f4d\u4e8e\u6307\u5b9a\u7684\u4e24\u4e2a\u6570\u4e4b\u95f4\u7684\u4e00\u4e2a\u968f\u673a\u6574\u6570\u3002', [
            parameterDescription('bottom'),
            parameterDescription('top')
        ]),
        'RANGEBLOCKSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u533a\u57df\u6a21\u677f\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('template_range'),
            parameterDescription('data_expression')
        ]),
        'RANK': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u5b57\u5728\u6570\u5b57\u5217\u8868\u4e2d\u7684\u6392\u4f4d\u3002\u6570\u5b57\u7684\u6392\u4f4d\u662f\u5176\u5927\u5c0f\u4e0e\u5217\u8868\u4e2d\u5176\u4ed6\u503c\u7684\u6bd4\u503c\uff08\u5982\u679c\u5217\u8868\u5df2\u6392\u8fc7\u5e8f\uff0c\u5219\u6570\u5b57\u7684\u6392\u4f4d\u5c31\u662f\u5b83\u5f53\u524d\u7684\u4f4d\u7f6e\uff09\u3002', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('order')
        ]),
        'RATE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5e74\u91d1\u7684\u5404\u671f\u5229\u7387\u3002', [
            parameterDescription('nper'),
            parameterDescription('pmt'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type'),
            parameterDescription('guess')
        ]),
        'RECEIVED': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u6b21\u6027\u4ed8\u606f\u7684\u6709\u4ef7\u8bc1\u5238\u5230\u671f\u6536\u56de\u7684\u91d1\u989d\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('investment'),
            parameterDescription('discount'),
            parameterDescription('basis')
        ]),
        'REPLACE': functionDescription('\u6b64\u51fd\u6570\u4f7f\u7528\u5176\u4ed6\u6587\u672c\u5b57\u7b26\u4e32\u5e76\u6839\u636e\u6240\u6307\u5b9a\u7684\u5b57\u7b26\u6570\u66ff\u6362\u67d0\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7684\u90e8\u5206\u6587\u672c\u3002', [
            parameterDescription('old_text'),
            parameterDescription('start_num'),
            parameterDescription('num_chars'),
            parameterDescription('new_text')
        ]),
        'REPT': functionDescription('\u6b64\u51fd\u6570\u6309\u7167\u7ed9\u5b9a\u7684\u6b21\u6570\u91cd\u590d\u663e\u793a\u6587\u672c\u3002', [
            parameterDescription('text'),
            parameterDescription('number_times')
        ]),
        'RIGHT': functionDescription('\u6b64\u51fd\u6570\u6839\u636e\u6240\u6307\u5b9a\u7684\u5b57\u7b26\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u6700\u540e\u4e00\u4e2a\u6216\u591a\u4e2a\u5b57\u7b26\u3002', [
            parameterDescription('text'),
            parameterDescription('num_chars')
        ]),
        'ROMAN': functionDescription('\u6b64\u51fd\u6570\u5c06\u963f\u62c9\u4f2f\u6570\u5b57\u8f6c\u6362\u4e3a\u5176\u7b49\u4ef7\u7684\u6587\u672c\u5f62\u5f0f\u7684\u7f57\u9a6c\u6570\u5b57\u3002', [
            parameterDescription('number'),
            parameterDescription('form')
        ]),
        'ROUND': functionDescription('\u6b64\u51fd\u6570\u53ef\u5c06\u67d0\u4e2a\u6570\u5b57\u56db\u820d\u4e94\u5165\u4e3a\u6307\u5b9a\u7684\u4f4d\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROUNDDOWN': functionDescription('\u6b64\u51fd\u6570\u9760\u8fd1\u96f6\u503c\uff0c\u5411\u4e0b\uff08\u7edd\u5bf9\u503c\u51cf\u5c0f\u7684\u65b9\u5411\uff09\u820d\u5165\u6570\u5b57\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROUNDUP': functionDescription('\u6b64\u51fd\u6570\u8fdc\u79bb\u96f6\u503c\uff0c\u5411\u4e0a\u820d\u5165\u6570\u5b57\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROW': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5f15\u7528\u7684\u884c\u53f7\u3002', [
            parameterDescription('reference')
        ]),
        'ROWS': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5f15\u7528\u6216\u6570\u7ec4\u7684\u884c\u6570\u3002', [
            parameterDescription('array')
        ]),
        'RSQ': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6839\u636e y s \u548c x s \u4e2d\u6570\u636e\u70b9\u8ba1\u7b97\u5f97\u51fa\u7684 Pearson \u4e58\u79ef\u77e9\u76f8\u5173\u7cfb\u6570\u7684\u5e73\u65b9\u3002', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SEARCH': functionDescription('\u6b64\u51fd\u6570\u5728\u4e00\u4e2a\u6587\u672c\u503c\u4e2d\u67e5\u627e\u53e6\u4e00\u4e2a\u6587\u672c\u503c\u5e76\u8fd4\u56de\u7b2c\u4e00\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u7684\u8d77\u59cb\u4f4d\u7f6e\u7684\u7f16\u53f7\uff0c\u8be5\u7f16\u53f7\u4ece\u7b2c\u4e8c\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u7684\u7b2c\u4e00\u4e2a\u5b57\u7b26\u7b97\u8d77\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'SECOND': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u65f6\u95f4\u503c\u7684\u79d2\u6570\u3002\u8fd4\u56de\u7684\u79d2\u6570\u4e3a 0 \u5230 59 \u4e4b\u95f4\u7684\u6574\u6570\u3002', [
            parameterDescription('time')
        ]),
        'SERIESSUM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5e42\u7ea7\u6570\u4e4b\u548c\u3002', [
            parameterDescription('x'),
            parameterDescription('n'),
            parameterDescription('m'),
            parameterDescription('coefficients')
        ]),
        'SIGN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u7b26\u53f7\u3002', [
            parameterDescription('cellreference')
        ]),
        'SIN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u89d2\u5ea6\u7684\u6b63\u5f26\u503c\u3002', [
            parameterDescription('angle')
        ]),
        'SINH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u4e00\u6570\u5b57\u7684\u53cc\u66f2\u6b63\u5f26\u503c\u3002', [
            parameterDescription('number')
        ]),
        'SKEW': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5206\u5e03\u7684\u4e0d\u5bf9\u79f0\u5ea6\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SLN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u9879\u8d44\u4ea7\u5728\u4e00\u4e2a\u671f\u95f4\u4e2d\u7684\u7ebf\u6027\u6298\u65e7\u503c\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life')
        ]),
        'SLOPE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6839\u636e array_dep \u548c array_ind \u4e2d\u7684\u6570\u636e\u70b9\u62df\u5408\u7684\u7ebf\u6027\u56de\u5f52\u76f4\u7ebf\u7684\u659c\u7387\u3002', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SMALL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u4e2d\u7b2c n \u4e2a\u6700\u5c0f\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'SQRT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6b63\u5e73\u65b9\u6839\u3002', [
            parameterDescription('value')
        ]),
        'SQRTPI': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u6570\u4e0e pi \u7684\u4e58\u79ef\u7684\u5e73\u65b9\u6839\u3002', [
            parameterDescription('multiple')
        ]),
        'STANDARDIZE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 mean \u4e3a\u5e73\u5747\u503c\uff0c\u4ee5 stdev \u4e3a\u6807\u51c6\u504f\u5dee\u7684\u5206\u5e03\u7684\u6b63\u6001\u5316\u6570\u503c\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'STDEVA': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u6837\u672c\uff08\u5305\u62ec\u6570\u5b57\u3001\u6587\u672c\u548c\u903b\u8f91\u503c\uff09\u4f30\u7b97\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'STDEVP': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u6574\u4e2a\u6837\u672c\u603b\u4f53\u8ba1\u7b97\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEVPA': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u603b\u4f53\uff08\u5305\u62ec\u6570\u5b57\u3001\u6587\u672c\u548c\u903b\u8f91\u503c\uff09\u8ba1\u7b97\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'STEYX': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u901a\u8fc7\u7ebf\u6027\u56de\u5f52\u6cd5\u8ba1\u7b97\u6bcf\u4e2a x \u7684 y \u9884\u6d4b\u503c\u65f6\u6240\u4ea7\u751f\u7684\u6807\u51c6\u8bef\u5dee\u3002 \u6807\u51c6\u8bef\u5dee\u7528\u6765\u5ea6\u91cf\u6839\u636e\u5355\u4e2a x \u53d8\u91cf\u8ba1\u7b97\u51fa\u7684 y \u9884\u6d4b\u503c\u7684\u8bef\u5dee\u91cf\u3002', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SUBSTITUTE': functionDescription('\u6b64\u51fd\u6570\u5728\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7528\u65b0\u6587\u672c\u66ff\u6362\u65e7\u6587\u672c\u3002', [
            parameterDescription('text'),
            parameterDescription('old_text'),
            parameterDescription('new_text'),
            parameterDescription('instance_num')
        ]),
        'SUBTOTAL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5217\u8868\u6216\u6570\u636e\u5e93\u4e2d\u7684\u5206\u7c7b\u6c47\u603b\u3002', [
            parameterDescription('function_num'),
            parameterDescription('ref1'),
            parameterDescription('ref2', true)
        ]),
        'SUM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u4e00\u5355\u5143\u683c\u533a\u57df\u4e2d\u6240\u6709\u6570\u5b57\u4e4b\u548c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SUMIF': functionDescription('\u6b64\u51fd\u6570\u6309\u7ed9\u5b9a\u6761\u4ef6\u5bf9\u6307\u5b9a\u5355\u5143\u683c\u6c42\u548c\u3002', [
            parameterDescription('range'),
            parameterDescription('criteria'),
            parameterDescription('sum_range')
        ]),
        'SUMIFS': functionDescription('\u6b64\u51fd\u6570\u5bf9\u533a\u57df\u4e2d\u6ee1\u8db3\u591a\u4e2a\u6761\u4ef6\u7684\u5355\u5143\u683c\u6c42\u548c\u3002', [
            parameterDescription('sum_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true),
        ]),
        'SUMPRODUCT': functionDescription('\u6b64\u51fd\u6570\u5728\u7ed9\u5b9a\u7684\u51e0\u7ec4\u6570\u7ec4\u4e2d\uff0c\u5c06\u6570\u7ec4\u95f4\u5bf9\u5e94\u7684\u5143\u7d20\u76f8\u4e58\uff0c\u5e76\u8fd4\u56de\u4e58\u79ef\u4e4b\u548c\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2', true)
        ]),
        'SUMSQ': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u53c2\u6570\u7684\u5e73\u65b9\u548c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SUMX2MY2': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u6570\u7ec4\u4e2d\u5bf9\u5e94\u6570\u503c\u7684\u5e73\u65b9\u5dee\u4e4b\u548c\u3002', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SUMX2PY2': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u6570\u7ec4\u4e2d\u5bf9\u5e94\u6570\u503c\u7684\u5e73\u65b9\u548c\u4e4b\u548c\u3002', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SUMXMY2': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u6570\u7ec4\u4e2d\u5bf9\u5e94\u6570\u503c\u4e4b\u5dee\u7684\u5e73\u65b9\u548c\u3002', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SYD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u9879\u8d44\u4ea7\u6309\u5e74\u9650\u603b\u548c\u6298\u65e7\u6cd5\u8ba1\u7b97\u7684\u6307\u5b9a\u671f\u95f4\u7684\u6298\u65e7\u503c\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period')
        ]),
        'T': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u7684\u6587\u672c\u3002', [
            parameterDescription('value')
        ]),
        'TAN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u89d2\u5ea6\u7684\u6b63\u5207\u503c\u3002', [
            parameterDescription('angle')
        ]),
        'TANH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u4e00\u6570\u5b57\u7684\u53cc\u66f2\u6b63\u5207\u3002', [
            parameterDescription('number')
        ]),
        'TBILLEQ': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u56fd\u5e93\u5238\u7684\u7b49\u6548\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount')
        ]),
        'TBILLPRICE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u9762\u503c \uffe5100 \u7684\u56fd\u5e93\u5238\u7684\u4ef7\u683c\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount')
        ]),
        'TBILLYIELD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u56fd\u5e93\u5238\u7684\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('priceper')
        ]),
        'TDIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de t \u5206\u5e03\u7684\u767e\u5206\u70b9\uff08\u6982\u7387\uff09\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('tails')
        ]),
        'TEXT': functionDescription('\u6b64\u51fd\u6570\u683c\u5f0f\u5316\u4e00\u4e2a\u6570\u503c\u5e76\u5c06\u5176\u8f6c\u6362\u6210\u6587\u672c\u3002', [
            parameterDescription('value'),
            parameterDescription('format_text')
        ]),
        'TIME': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u65f6\u95f4\u7684\u65f6\u95f4\u95f4\u9694\u5bf9\u8c61\u3002', [
            parameterDescription('hour'),
            parameterDescription('minute'),
            parameterDescription('second')
        ]),
        'TIMEVALUE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7531\u6587\u672c\u5b57\u7b26\u4e32\u6240\u4ee3\u8868\u7684\u65f6\u95f4\u7684\u65f6\u95f4\u95f4\u9694\u5bf9\u8c61\u503c\u3002', [
            parameterDescription('time_text')
        ]),
        'TINV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4f5c\u4e3a\u6982\u7387\u548c\u81ea\u7531\u5ea6\u51fd\u6570\u7684\u5b66\u751f t \u5206\u5e03\u7684 t \u503c\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'TODAY': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5f53\u524d\u7684\u65e5\u671f\u548c\u65f6\u95f4\u3002', []),
        'TRANSPOSE': functionDescription('\u6b64\u51fd\u6570 TRANSPOSE \u51fd\u6570\u53ef\u8fd4\u56de\u8f6c\u7f6e\u5355\u5143\u683c\u533a\u57df\uff0c\u5373\u5c06\u884c\u5355\u5143\u683c\u533a\u57df\u8f6c\u7f6e\u6210\u5217\u5355\u5143\u683c\u533a\u57df\uff0c\u53cd\u4e4b\u4ea6\u7136\u3002', [
            parameterDescription('array')
        ]),
        'TREND': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u6761\u7ebf\u6027\u56de\u5f52\u62df\u5408\u7ebf\u7684\u503c\u3002 \u5373\u627e\u5230\u9002\u5408\u5df2\u77e5\u6570\u7ec4 y s \u548c x s \u7684\u76f4\u7ebf\uff08\u7528\u6700\u5c0f\u4e8c\u4e58\u6cd5\uff09\uff0c \u5e76\u8fd4\u56de\u6307\u5b9a\u6570\u7ec4 newx s \u5728\u76f4\u7ebf\u4e0a\u5bf9\u5e94\u7684 y \u503c\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('newx'),
            parameterDescription('constant')
        ]),
        'TRIM': functionDescription('\u6b64\u51fd\u6570\u9664\u4e86\u5355\u8bcd\u4e4b\u95f4\u7684\u5355\u4e2a\u7a7a\u683c\u5916\uff0c\u6e05\u9664\u6587\u672c\u4e2d\u6240\u6709\u7684\u7a7a\u683c\u3002', [
            parameterDescription('text')
        ]),
        'TRIMMEAN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u7684\u5185\u90e8\u5e73\u5747\u503c\u3002\u51fd\u6570 TRIMMEAN \u5148\u4ece\u6570\u636e\u96c6\u7684\u5934\u90e8\u548c\u5c3e\u90e8\u9664\u53bb\u4e00\u5b9a\u767e\u5206\u6bd4\u7684\u6570\u636e\u70b9\uff0c\u7136\u540e\u518d\u6c42\u5e73\u5747\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('percent')
        ]),
        'TRUE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u903b\u8f91\u503c TRUE\u3002', []),
        'TRUNC': functionDescription('\u6b64\u51fd\u6570\u5c06\u6307\u5b9a\u6570\u5b57\u7684\u5c0f\u6570\u90e8\u5206\u622a\u53bb\uff0c\u8fd4\u56de\u6574\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'TTEST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e0e\u5b66\u751f t \u68c0\u9a8c\u76f8\u5173\u7684\u6982\u7387\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2'),
            parameterDescription('tails'),
            parameterDescription('type')
        ]),
        'TYPE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u503c\u7684\u7c7b\u578b\u3002', [
            parameterDescription('value')
        ]),
        'UPPER': functionDescription('\u6b64\u51fd\u6570\u5c06\u6587\u672c\u8f6c\u6362\u4e3a\u5927\u5199\u5f62\u5f0f\u3002', [
            parameterDescription('text')
        ]),
        'VALUE': functionDescription('\u6b64\u51fd\u6570\u5c06\u4ee3\u8868\u6570\u5b57\u7684\u6587\u672c\u5b57\u7b26\u4e32\u8f6c\u6362\u6210\u6570\u5b57\u3002', [
            parameterDescription('text')
        ]),
        'VAR': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u57fa\u4e8e\u7ed9\u5b9a\u6837\u672c\u7684\u65b9\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VARA': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u6837\u672c\uff08\u5305\u62ec\u6570\u5b57\u3001\u6587\u672c\u548c\u903b\u8f91\u503c\uff09\u4f30\u7b97\u65b9\u5dee\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'VARP': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u57fa\u4e8e\u6574\u4e2a\u6837\u672c\u603b\u4f53\u7684\u65b9\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VARPA': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u57fa\u4e8e\u603b\u4f53\uff08\u5305\u62ec\u6570\u5b57\u3001\u6587\u672c\u548c\u903b\u8f91\u503c\uff09\u4f30\u7b97\u65b9\u5dee', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'VDB': functionDescription('\u6b64\u51fd\u6570\u4f7f\u7528\u53cc\u500d\u4f59\u989d\u9012\u51cf\u6cd5\u6216\u5176\u4ed6\u6307\u5b9a\u7684\u65b9\u6cd5\uff0c\u8fd4\u56de\u6307\u5b9a\u7684\u4efb\u4f55\u671f\u95f4\u5185\uff08\u5305\u62ec\u90e8\u5206\u671f\u95f4\uff09\u7684\u8d44\u4ea7\u6298\u65e7\u503c\u3002\u51fd\u6570 VDB \u4ee3\u8868\u53ef\u53d8\u4f59\u989d\u9012\u51cf\u6cd5\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('factor'),
            parameterDescription('no_switch')
        ]),
        'VLOOKUP': functionDescription('\u6b64\u51fd\u6570\u5728\u8868\u683c\u6570\u7ec4\u7684\u9996\u5217\u67e5\u627e\u6307\u5b9a\u7684\u503c\uff0c\u5e76\u7531\u6b64\u8fd4\u56de\u8868\u683c\u6570\u7ec4\u5f53\u524d\u884c\u4e2d\u5176\u4ed6\u5217\u7684\u503c\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('table_array'),
            parameterDescription('col_index_num'),
            parameterDescription('range_lookup', false, true, RANGE_LOOKUP_ENUM_INFO)
        ]),
        'WEEKDAY': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u65e5\u671f\u4e3a\u661f\u671f\u51e0\u3002\u9ed8\u8ba4\u60c5\u51b5\u4e0b\uff0c\u5176\u503c\u4e3a 1\uff08\u661f\u671f\u5929\uff09\u5230 7\uff08\u661f\u671f\u516d\uff09\u4e4b\u95f4\u7684\u6574\u6570\u3002', [
            parameterDescription('date'),
            parameterDescription('type')
        ]),
        'WEEKNUM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u5b57\uff0c\u8be5\u6570\u5b57\u4ee3\u8868\u4e00\u5e74\u4e2d\u7684\u7b2c\u51e0\u5468\u3002', [
            parameterDescription('date'),
            parameterDescription('weektype')
        ]),
        'DATEPART': functionDescription('\u6b64\u51fd\u6570\u683c\u5f0f\u5316\u4e00\u4e2a\u65e5\u671f\u5e76\u5c06\u5176\u8f6c\u6362\u6210\u6587\u672c\u3002', [
            parameterDescription('date'),
            parameterDescription('format_text'),
            parameterDescription('weektype')
        ]),
        'WEIBULL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u97e6\u4f2f\u5206\u5e03\u3002\u4f7f\u7528\u6b64\u51fd\u6570\u53ef\u4ee5\u8fdb\u884c\u53ef\u9760\u6027\u5206\u6790\uff0c\u6bd4\u5982\u8ba1\u7b97\u8bbe\u5907\u7684\u5e73\u5747\u6545\u969c\u65f6\u95f4\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'WORKDAY': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u65e5\u671f\uff08\u8d77\u59cb\u65e5\u671f\uff09\u4e4b\u524d\u6216\u4e4b\u540e\u76f8\u9694\u6307\u5b9a\u5de5\u4f5c\u65e5\u7684\u67d0\u4e00\u65e5\u671f\u7684\u65e5\u671f\u503c\u3002', [
            parameterDescription('start_date'),
            parameterDescription('days'),
            parameterDescription('holidays')
        ]),
        'XIRR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u73b0\u91d1\u6d41\u7684\u5185\u90e8\u6536\u76ca\u7387\uff0c\u8fd9\u4e9b\u73b0\u91d1\u6d41\u4e0d\u4e00\u5b9a\u5b9a\u671f\u53d1\u751f\u3002', [
            parameterDescription('values'),
            parameterDescription('dates'),
            parameterDescription('guess')
        ]),
        'XNPV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u73b0\u91d1\u6d41\u7684\u51c0\u73b0\u503c\uff0c\u8fd9\u4e9b\u73b0\u91d1\u6d41\u4e0d\u4e00\u5b9a\u5b9a\u671f\u53d1\u751f\u3002', [
            parameterDescription('rate'),
            parameterDescription('values'),
            parameterDescription('dates')
        ]),
        'YEAR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u65e5\u671f\u5bf9\u5e94\u7684\u5e74\u4efd\u3002', [
            parameterDescription('date')
        ]),
        'YEARFRAC': functionDescription('\u6b64\u51fd\u6570 \u8fd4\u56de start \u548c end \u4e4b\u95f4\u7684\u5929\u6570\u5360\u5168\u5e74\u5929\u6570\u7684\u767e\u5206\u6bd4\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('basis')
        ]),
        'YIELD': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5b9a\u671f\u4ed8\u606f\u6709\u4ef7\u8bc1\u5238\u7684\u6536\u76ca\u7387\uff0c\u51fd\u6570 YIELD \u7528\u4e8e\u8ba1\u7b97\u503a\u5238\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'YIELDDISC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6298\u4ef7\u53d1\u884c\u7684\u6709\u4ef7\u8bc1\u5238\u7684\u5e74\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'YIELDMAT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5230\u671f\u4ed8\u606f\u7684\u6709\u4ef7\u8bc1\u5238\u7684\u5e74\u6536\u76ca\u7387\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('basis')
        ]),
        'ZTEST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de z \u68c0\u9a8c\u7684\u5355\u5c3e\u6982\u7387\u503c\u3002\u5bf9\u4e8e\u7ed9\u5b9a\u7684\u5047\u8bbe\u603b\u4f53\u5e73\u5747\u503c \u03bc0\uff0cZTEST \u8fd4\u56de\u6837\u672c\u5e73\u5747\u503c\u5927\u4e8e\u6570\u636e\u96c6\uff08\u6570\u7ec4\uff09\u4e2d\u89c2\u5bdf\u5e73\u5747\u503c\u7684\u6982\u7387\uff0c\u5373\u89c2\u5bdf\u6837\u672c\u5e73\u5747\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('sigma')
        ]),
        'HBARSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u6a2a\u5411\u6761\u72b6\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('colorScheme'),
            parameterDescription('axisVisible'),
            parameterDescription('barHeight')
        ]),
        'VBARSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u7ad6\u5411\u67f1\u72b6\u578b\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('colorScheme'),
            parameterDescription('axisVisible'),
            parameterDescription('barWidth')
        ]),
        'VARISPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u65b9\u5dee\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('variance'),
            parameterDescription('reference'),
            parameterDescription('mini'),
            parameterDescription('maxi'),
            parameterDescription('mark'),
            parameterDescription('tickunit'),
            parameterDescription('legend'),
            parameterDescription('colorPositive'),
            parameterDescription('colorNegative'),
            parameterDescription('vertical')
        ]),
        'LOLLIPOPVARISPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u7edd\u5bf9\u6216\u76f8\u5bf9\u7684\u68d2\u68d2\u7cd6\u65b9\u5dee\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6', [
            parameterDescription('plannedValue'),
            parameterDescription('actualValue'),
            parameterDescription('index'),
            parameterDescription('absolute'),
            parameterDescription('reference'),
            parameterDescription('mini'),
            parameterDescription('maxi'),
            parameterDescription('tickunit'),
            parameterDescription('legend'),
            parameterDescription('colorPositive'),
            parameterDescription('colorNegative'),
            parameterDescription('lollipopHeaderColor'),
            parameterDescription('vertical'),
        ]),
        'PIESPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u997c\u5f62\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('range|percentage'),
            parameterDescription('color', true)
        ]),
        'AREASPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u9762\u79ef\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('points'),
            parameterDescription('mini'),
            parameterDescription('maxi'),
            parameterDescription('line1'),
            parameterDescription('line2'),
            parameterDescription('colorPositive'),
            parameterDescription('colorNegative')
        ]),
        'SCATTERSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u6563\u70b9\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('points1'),
            parameterDescription('points2'),
            parameterDescription('minX'),
            parameterDescription('maxX'),
            parameterDescription('minY'),
            parameterDescription('maxY'),
            parameterDescription('hLine'),
            parameterDescription('vLine'),
            parameterDescription('xMinZone'),
            parameterDescription('xMaxZone'),
            parameterDescription('yMinZone'),
            parameterDescription('yMaxZone'),
            parameterDescription('tags'),
            parameterDescription('drawSymbol'),
            parameterDescription('drawLines'),
            parameterDescription('color1'),
            parameterDescription('color2'),
            parameterDescription('dash')
        ]),
        'LINESPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u6298\u7ebf\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'COLUMNSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u67f1\u5f62\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'WINLOSSSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u76c8\u4e8f\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'BULLETSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u5b50\u5f39\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('measure'),
            parameterDescription('target'),
            parameterDescription('maxi'),
            parameterDescription('good'),
            parameterDescription('bad'),
            parameterDescription('forecast'),
            parameterDescription('tickunit'),
            parameterDescription('colorScheme'),
            parameterDescription('vertical'),
            parameterDescription('measureColor'),
            parameterDescription('targetColor'),
            parameterDescription('maxiColor'),
            parameterDescription('goodColor'),
            parameterDescription('badColor'),
            parameterDescription('forecastColor'),
            parameterDescription('allowMeasureOverMaxi'),
            parameterDescription('barSize')
        ]),
        'SPREADSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u6563\u5e03\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('points'),
            parameterDescription('showAverage'),
            parameterDescription('scaleStart'),
            parameterDescription('scaleEnd'),
            parameterDescription('style'),
            parameterDescription('colorScheme'),
            parameterDescription('vertical')
        ]),
        'STACKEDSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u5806\u79ef\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('points'),
            parameterDescription('colorRange'),
            parameterDescription('labelRange'),
            parameterDescription('maximum'),
            parameterDescription('targetRed'),
            parameterDescription('targetGreen'),
            parameterDescription('targetBlue'),
            parameterDescription('tragetYellow'),
            parameterDescription('color'),
            parameterDescription('highlightPosition'),
            parameterDescription('vertical'),
            parameterDescription('textOrientation'),
            parameterDescription('textSize')
        ]),
        'BOXPLOTSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u7bb1\u7ebf\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('points'),
            parameterDescription('boxPlotClass'),
            parameterDescription('showAverage'),
            parameterDescription('scaleStart'),
            parameterDescription('scaleEnd'),
            parameterDescription('acceptableStart'),
            parameterDescription('acceptableEnd'),
            parameterDescription('colorScheme'),
            parameterDescription('style'),
            parameterDescription('vertical')
        ]),
        'CASCADESPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u7011\u5e03\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('pointsRange'),
            parameterDescription('pointIndex'),
            parameterDescription('labelsRange'),
            parameterDescription('minimum'),
            parameterDescription('maximum'),
            parameterDescription('colorPositive'),
            parameterDescription('colorNegative'),
            parameterDescription('vertical'),
            parameterDescription('itemTypeRange'),
            parameterDescription('colorTotal')
        ]),
        'PARETOSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u9636\u68af\u7011\u5e03\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('points'),
            parameterDescription('pointIndex'),
            parameterDescription('colorRange'),
            parameterDescription('target'),
            parameterDescription('target2'),
            parameterDescription('highlightPosition'),
            parameterDescription('label'),
            parameterDescription('vertical'),
            parameterDescription('targetColor'),
            parameterDescription('target2Color'),
            parameterDescription('labelColor'),
            parameterDescription('barSize')
        ]),
        'MONTHSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u6708\u4efd\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('year'),
            parameterDescription('month'),
            parameterDescription('dataRange'),
            parameterDescription('emptyColor'),
            parameterDescription('startColor'),
            parameterDescription('middleColor'),
            parameterDescription('endColor')
        ]),
        'YEARSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u5e74\u4efd\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('year'),
            parameterDescription('dataRange'),
            parameterDescription('emptyColor'),
            parameterDescription('startColor'),
            parameterDescription('middleColor'),
            parameterDescription('endColor')
        ]),
        'GAUGEKPISPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8KPI\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('targetValue'),
            parameterDescription('currentValue'),
            parameterDescription('minValue'),
            parameterDescription('maxValue'),
            parameterDescription('showLabel'),
            parameterDescription('targetValueLabel'),
            parameterDescription('currentValueLabel'),
            parameterDescription('minValueLabel'),
            parameterDescription('maxValueLabel'),
            parameterDescription('fontArray'),
            parameterDescription('minAngle'),
            parameterDescription('maxAngle'),
            parameterDescription('radiusRatio'),
            parameterDescription('gaugeType'),
            parameterDescription('colorRange'),
            parameterDescription('...')
        ]),
        'HISTOGRAMSPARKLINE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8\u76f4\u65b9\u8ff7\u4f60\u56fe\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('dateRange'),
            parameterDescription('continuous'),
            parameterDescription('paintLabel'),
            parameterDescription('scale'),
            parameterDescription('barWidth'),
            parameterDescription('barColor'),
            parameterDescription('labelFontStyle'),
            parameterDescription('labelColor'),
            parameterDescription('edgeColor'),
        ]),
        'CEILING.PRECISE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u5b57\uff0c\u8be5\u6570\u5b57\u5411\u4e0a\u820d\u5165\u4e3a\u6700\u63a5\u8fd1\u7684\u6574\u6570\u6216\u6700\u63a5\u8fd1\u7684\u6709\u6548\u4f4d\u7684\u500d\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'COVARIANCE.S': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6837\u672c\u534f\u65b9\u5dee\uff0c\u5373\u4e24\u4e2a\u6570\u636e\u96c6\u4e2d\u6bcf\u5bf9\u6570\u636e\u70b9\u7684\u504f\u5dee\u4e58\u79ef\u7684\u5e73\u5747\u503c\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'FLOOR.PRECISE': functionDescription('\u6b64\u51fd\u6570\u5411\u7edd\u5bf9\u503c\u51cf\u5c0f\u7684\u65b9\u5411\u820d\u5165\u6570\u5b57\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'PERCENTILE.EXC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u533a\u57df\u4e2d\u6570\u503c\u7684\u7b2c n \u4e2a\u767e\u5206\u70b9\u7684\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('k')
        ]),
        'QUARTILE.EXC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u7684\u56db\u5206\u4f4d\u6570\u3002', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'RANK.AVG': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u5b57\u5728\u6570\u5b57\u5217\u8868\u4e2d\u7684\u6392\u4f4d\u3002\u6570\u5b57\u7684\u6392\u4f4d\u662f\u5176\u5927\u5c0f\u4e0e\u5217\u8868\u4e2d\u5176\u4ed6\u503c\u7684\u6bd4\u503c\uff08\u5982\u679c\u5217\u8868\u5df2\u6392\u8fc7\u5e8f\uff0c\u5219\u6570\u5b57\u7684\u6392\u4f4d\u5c31\u662f\u5b83\u5f53\u524d\u7684\u4f4d\u7f6e\uff09\u3002', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('[order]')
        ]),
        'MODE.MULT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u6570\u636e\u6216\u6570\u636e\u533a\u57df\u4e2d\u51fa\u73b0\u9891\u7387\u6700\u9ad8\u6216\u91cd\u590d\u51fa\u73b0\u7684\u6570\u503c\u7684\u5782\u76f4\u6570\u7ec4\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEV.P': functionDescription('\u6b64\u51fd\u6570\u4f30\u7b97\u57fa\u4e8e\u6837\u672c\u7684\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VAR.P': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u57fa\u4e8e\u7ed9\u5b9a\u6837\u672c\u7684\u65b9\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'COVARIANCE.P': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u534f\u65b9\u5dee\uff08\u6210\u5bf9\u504f\u5dee\u4e58\u79ef\u7684\u5e73\u5747\u503c\uff09\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'MODE.SNGL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5728\u67d0\u4e00\u6570\u7ec4\u6216\u6570\u636e\u533a\u57df\u4e2d\u51fa\u73b0\u9891\u7387\u6700\u591a\u7684\u6570\u503c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'PERCENTILE.INC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u533a\u57df\u4e2d\u6570\u503c\u7684\u7b2c n \u4e2a\u767e\u5206\u70b9\u7684\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('k')
        ]),
        'QUARTILE.INC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u7684\u56db\u5206\u4f4d\u6570\u3002', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'RANK.EQ': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u5b57\u5728\u6570\u5b57\u5217\u8868\u4e2d\u7684\u6392\u4f4d\u3002\u6570\u5b57\u7684\u6392\u4f4d\u662f\u5176\u5927\u5c0f\u4e0e\u5217\u8868\u4e2d\u5176\u4ed6\u503c\u7684\u6bd4\u503c\uff08\u5982\u679c\u5217\u8868\u5df2\u6392\u8fc7\u5e8f\uff0c\u5219\u6570\u5b57\u7684\u6392\u4f4d\u5c31\u662f\u5b83\u5f53\u524d\u7684\u4f4d\u7f6e\uff09\u3002', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('[order]')
        ]),
        'STDEV': functionDescription('\u6b64\u51fd\u6570\u4f30\u7b97\u57fa\u4e8e\u6837\u672c\u7684\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEV.S': functionDescription('\u6b64\u51fd\u6570\u4f30\u7b97\u57fa\u4e8e\u6837\u672c\u7684\u6807\u51c6\u504f\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VAR.S': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u57fa\u4e8e\u7ed9\u5b9a\u6837\u672c\u7684\u65b9\u5dee\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'BETA.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a Beta \u5206\u5e03\u7684\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('[lower]'),
            parameterDescription('[upper]')
        ]),
        'BINOM.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e8c\u9879\u5f0f\u5206\u5e03\u7684\u6982\u7387\u503c\u3002', [
            parameterDescription('number_s'),
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'BINOM.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4f7f\u7d2f\u79ef\u4e8c\u9879\u5f0f\u5206\u5e03\u5c0f\u4e8e\u6216\u7b49\u4e8e\u4e34\u754c\u503c\u7684\u6700\u5c0f\u503c\u3002', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('alpha')
        ]),
        'CHISQ.DIST.RT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de \u03c72 \u5206\u5e03\u7684\u5355\u5c3e\u6982\u7387\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.INV.RT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de \u03c72 \u5206\u5e03\u7684\u5355\u5c3e\u6982\u7387\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.TEST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u72ec\u7acb\u6027\u68c0\u9a8c\u503c\u3002', [
            parameterDescription('actual_range'),
            parameterDescription('expected_range')
        ]),
        'CONFIDENCE.NORM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u603b\u4f53\u5e73\u5747\u503c\u7684\u7f6e\u4fe1\u533a\u95f4\u3002', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'EXPON.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u6570\u5206\u5e03\u3002', [
            parameterDescription('value'),
            parameterDescription('lambda'),
            parameterDescription('cumulative')
        ]),
        'F.DIST.RT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de F \u6982\u7387\u5206\u5e03\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'F.INV.RT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de F \u6982\u7387\u5206\u5e03\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'F.TEST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de F \u68c0\u9a8c\u7684\u7ed3\u679c\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'GAMMA.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de \u03b3 \u5206\u5e03\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'GAMMA.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de \u03b3 \u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta')
        ]),
        'LOGNORM.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5bf9\u6570\u7d2f\u79ef\u5206\u5e03\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORM.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
            parameterDescription('cumulative')
        ]),
        'NORM.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6807\u51c6\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORM.S.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6807\u51c6\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability')
        ]),
        'PERCENTRANK.INC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u636e\u96c6\u4e2d\u503c\u7684\u767e\u5206\u6bd4\u6392\u4f4d\u3002', [
            parameterDescription('array'),
            parameterDescription('n'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'POISSON.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6cca\u677e\u5206\u5e03\u3002', [
            parameterDescription('nevents'),
            parameterDescription('mean'),
            parameterDescription('cumulative')
        ]),
        'T.INV.2T': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5b66\u751f\u7684 t \u5206\u5e03\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'T.TEST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e0e\u5b66\u751f\u7684 t \u68c0\u9a8c\u76f8\u5173\u7684\u6982\u7387\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2'),
            parameterDescription('tails'),
            parameterDescription('type')
        ]),
        'WEIBULL.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de Weibull \u5206\u5e03\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'Z.TEST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de z \u68c0\u9a8c\u7684\u5355\u5c3e\u6982\u7387\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('[sigma]')
        ]),
        'T.DIST.RT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5b66\u751f\u7684 t \u5206\u5e03\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom')
        ]),
        'T.DIST.2T': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5b66\u751f\u7684 t \u5206\u5e03\u7684\u767e\u5206\u70b9\uff08\u6982\u7387\uff09\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom')
        ]),
        'ISO.CEILING': functionDescription('\u6b64\u51fd\u6570\u5c06\u6570\u5b57\u5411\u4e0a\u820d\u5165\u5230\u6700\u63a5\u8fd1\u7684\u6574\u6570\u6216\u57fa\u6570\u7684\u6700\u63a5\u8fd1\u500d\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'BETA.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de Beta \u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'GAMMALN.PRECISE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4f3d\u739b\u51fd\u6570\u7684\u81ea\u7136\u5bf9\u6570\uff0c\u0393(x)\u3002', [
            parameterDescription('value')
        ]),
        'ERF.PRECISE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u8bef\u5dee\u51fd\u6570\u3002', [
            parameterDescription('lowerlimit')
        ]),
        'ERFC.PRECISE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ece x \u5230\u65e0\u7a77\u5927\u79ef\u5206\u7684\u4e92\u8865 ERF \u51fd\u6570\u3002', [
            parameterDescription('lowerlimit')
        ]),
        'PERCENTRANK.EXC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u67d0\u4e2a\u6570\u503c\u5728\u4e00\u4e2a\u6570\u636e\u96c6\u4e2d\u7684\u767e\u5206\u6bd4\uff080 \u5230 1\uff0c\u4e0d\u5305\u62ec 0 \u548c 1\uff09\u6392\u4f4d\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'HYPGEOM.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u8d85\u51e0\u4f55\u5206\u5e03\u3002', [
            parameterDescription('sample_s'),
            parameterDescription('number_sample'),
            parameterDescription('population_s'),
            parameterDescription('number_pop'),
            parameterDescription('cumulative')
        ]),
        'LOGNORM.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5bf9\u6570\u7d2f\u79ef\u5206\u5e03\u51fd\u6570\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('stdev'),
            parameterDescription('cumulative')
        ]),
        'NEGBINOM.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u8d1f\u4e8c\u9879\u5f0f\u5206\u5e03\u3002', [
            parameterDescription('number_f'),
            parameterDescription('number_s'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'NORM.S.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6807\u51c6\u6b63\u6001\u7d2f\u79ef\u5206\u5e03\u3002', [
            parameterDescription('z'),
            parameterDescription('cumulative')
        ]),
        'T.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5b66\u751f\u7684 t \u5206\u5e03\u7684\u767e\u5206\u70b9\uff08\u6982\u7387\uff09\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('cumulative')
        ]),
        'F.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de F \u6982\u7387\u5206\u5e03\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2'),
            parameterDescription('cumulative')
        ]),
        'CHISQ.DIST': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7d2f\u79ef Beta \u6982\u7387\u5bc6\u5ea6\u51fd\u6570\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('cumulative')
        ]),
        'F.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de F \u6982\u7387\u5206\u5e03\u7684\u53cd\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'T.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4f5c\u4e3a\u6982\u7387\u548c\u81ea\u7531\u5ea6\u51fd\u6570\u7684\u5b66\u751f t \u5206\u5e03\u7684 t \u503c\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.INV': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7d2f\u79ef Beta \u6982\u7387\u5bc6\u5ea6\u51fd\u6570\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CONFIDENCE.T': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u603b\u4f53\u5e73\u5747\u503c\u7684\u7f6e\u4fe1\u533a\u95f4\uff08\u4f7f\u7528\u5b66\u751f\u7684 t \u5206\u5e03\uff09\u3002', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'NETWORKDAYS.INTL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u65e5\u671f\u4e4b\u95f4\u7684\u6240\u6709\u5de5\u4f5c\u65e5\u6570\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('[weekend]'),
            parameterDescription('[holidays]')
        ]),
        'WORKDAY.INTL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6307\u5b9a\u7684\u82e5\u5e72\u4e2a\u5de5\u4f5c\u65e5\u4e4b\u524d\u6216\u4e4b\u540e\u7684\u65e5\u671f\u7684\u5e8f\u5217\u53f7\uff08\u4f7f\u7528\u81ea\u5b9a\u4e49\u5468\u672b\u53c2\u6570\uff09\u3002\u5468\u672b\u53c2\u6570\u6307\u660e\u5468\u672b\u6709\u51e0\u5929\u4ee5\u53ca\u662f\u54ea\u51e0\u5929\u3002', [
            parameterDescription('start_date'),
            parameterDescription('days'),
            parameterDescription('[weekend]'),
            parameterDescription('[holidays]')
        ]),
        'REFRESH': functionDescription('\u6b64\u51fd\u6570\u51b3\u5b9a\u4e86\u5728\u4ec0\u4e48\u65f6\u673a\u91cd\u65b0\u8ba1\u7b97\u516c\u5f0f\uff0c\u53ef\u4ee5\u901a\u8fc7 evaluateMode \u53c2\u6570\u6765\u6307\u5b9a\u662f\u5728\u5355\u5143\u683c\u5f15\u7528\u7684\u503c\u53d1\u751f\u53d8\u5316\u7684\u65f6\u5019\u3001\u53ea\u8ba1\u7b97\u4e00\u6b21\u8fd8\u662f\u4ee5\u4e00\u5b9a\u7684\u65f6\u95f4\u95f4\u9694\u91cd\u590d\u8ba1\u7b97\u3002', [
            parameterDescription('formula'),
            parameterDescription('evaluateMode'),
            parameterDescription('interval')
        ]),
        'DAYS': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u65e5\u671f\u4e4b\u95f4\u7684\u5929\u6570\u3002', [
            parameterDescription('end_date'),
            parameterDescription('start_date'),
        ]),
        'ISOWEEKNUM': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u65e5\u671f\u5728\u5168\u5e74\u4e2d\u7684 ISO \u5468\u6570\u3002', [
            parameterDescription('date')
        ]),
        'BITAND': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u6570\u7684\u6309\u4f4d\u201c\u4e0e\u201d\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'BITLSHIFT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5411\u5de6\u79fb\u52a8\u6307\u5b9a\u4f4d\u6570\u540e\u7684\u6570\u503c\u3002', [
            parameterDescription('number'),
            parameterDescription('shift_amount')
        ]),
        'BITOR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u6570\u7684\u6309\u4f4d\u201c\u6216\u201d\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'BITRSHIFT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5411\u53f3\u79fb\u52a8\u6307\u5b9a\u4f4d\u6570\u540e\u7684\u6570\u503c\u3002', [
            parameterDescription('number'),
            parameterDescription('shift_amount')
        ]),
        'BITXOR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e24\u4e2a\u6570\u503c\u7684\u6309\u4f4d\u201c\u5f02\u6216\u201d\u7ed3\u679c\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'IMCOSH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u53cc\u66f2\u4f59\u5f26\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCOT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u4f59\u5207\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCSC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u4f59\u5272\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCSCH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u53cc\u66f2\u4f59\u5272\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSEC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u6b63\u5272\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSECH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u53cc\u66f2\u6b63\u5272\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSINH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u53cc\u66f2\u6b63\u5f26\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMTAN': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5 x+yi \u6216 x+yj \u6587\u672c\u683c\u5f0f\u8868\u793a\u7684\u590d\u6570\u7684\u6b63\u5207\u503c\u3002', [
            parameterDescription('complexnum')
        ]),
        'PDURATION': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6295\u8d44\u5230\u8fbe\u6307\u5b9a\u503c\u6240\u9700\u7684\u671f\u6570\u3002', [
            parameterDescription('rate'),
            parameterDescription('pval'),
            parameterDescription('fval')
        ]),
        'RRI': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6295\u8d44\u589e\u957f\u7684\u7b49\u6548\u5229\u7387\u3002', [
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval')
        ]),
        'ISFORMULA': functionDescription('\u6b64\u51fd\u6570\u68c0\u67e5\u662f\u5426\u5b58\u5728\u5305\u542b\u516c\u5f0f\u7684\u5355\u5143\u683c\u5f15\u7528\uff0c\u7136\u540e\u8fd4\u56de TRUE \u6216 FALSE\u3002', [
            parameterDescription('cellreference')
        ]),
        'IFNA': functionDescription('\u5982\u679c\u516c\u5f0f\u8fd4\u56de\u9519\u8bef\u503c #N/A\uff0c\u5219\u7ed3\u679c\u8fd4\u56de\u60a8\u6307\u5b9a\u7684\u503c\uff1b\u5426\u5219\u8fd4\u56de\u516c\u5f0f\u7684\u7ed3\u679c\u3002', [
            parameterDescription('value'),
            parameterDescription('value_if_na')
        ]),
        'IFS': functionDescription('\u6b64\u51fd\u6570\u68c0\u67e5\u662f\u5426\u6ee1\u8db3\u4e00\u4e2a\u6216\u591a\u4e2a\u6761\u4ef6\uff0c\u5e76\u8fd4\u56de\u7b2c\u4e00\u4e2a TRUE \u6761\u4ef6\u5bf9\u5e94\u7684\u503c\u3002', [
            parameterDescription('logical_test1'),
            parameterDescription('value_if_true1'),
            parameterDescription('logical_test2', true),
            parameterDescription('value_if_true2', true)
        ]),
        'SWITCH': functionDescription('\u6b64\u51fd\u6570\u6839\u636e\u503c\u5217\u8868\u8ba1\u7b97\u4e00\u4e2a\u503c\uff08\u79f0\u4e3a\u8868\u8fbe\u5f0f\uff09\uff0c\u5e76\u8fd4\u56de\u4e0e\u7b2c\u4e00\u4e2a\u5339\u914d\u503c\u5bf9\u5e94\u7684\u7ed3\u679c', [
            parameterDescription('expression'),
            parameterDescription('value1'),
            parameterDescription('result1'),
            parameterDescription('[default_or_value2]', true),
            parameterDescription('[result2]', true),
        ]),
        'XOR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6240\u6709\u53c2\u6570\u7684\u903b\u8f91\u5f02\u6216\u3002', [
            parameterDescription('logical', true)
        ]),
        'AREAS': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5f15\u7528\u4e2d\u7684\u533a\u57df\u4e2a\u6570\u3002 \u533a\u57df\u662f\u6307\u8fde\u7eed\u7684\u5355\u5143\u683c\u533a\u57df\u6216\u5355\u4e2a\u5355\u5143\u683c\u3002', [
            parameterDescription('reference')
        ]),
        'FORMULATEXT': functionDescription('\u6b64\u51fd\u6570\u4ee5\u5b57\u7b26\u4e32\u7684\u5f62\u5f0f\u8fd4\u56de\u516c\u5f0f\u3002', [
            parameterDescription('reference')
        ]),
        'HYPERLINK': functionDescription('\u6b64\u51fd\u6570\u521b\u5efa\u5feb\u6377\u65b9\u5f0f\u6216\u8df3\u8f6c\uff0c\u4ee5\u6253\u5f00\u5b58\u50a8\u5728\u7f51\u7edc\u670d\u52a1\u5668\u3001intranet \u6216 Internet \u4e0a\u7684\u6587\u6863\u3002', [
            parameterDescription('link_location'),
            parameterDescription('friendly_name')
        ]),
        'ACOT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u53cd\u4f59\u5207\u503c\u7684\u4e3b\u503c\u3002', [
            parameterDescription('number')
        ]),
        'ACOTH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6570\u5b57\u7684\u53cd\u53cc\u66f2\u4f59\u5207\u503c\u3002', [
            parameterDescription('number')
        ]),
        'ARABIC': functionDescription('\u6b64\u51fd\u6570\u5c06\u7f57\u9a6c\u6570\u5b57\u8f6c\u6362\u4e3a\u963f\u62c9\u4f2f\u6570\u5b57\u3002', [
            parameterDescription('text')
        ]),
        'BASE': functionDescription('\u6b64\u51fd\u6570\u5c06\u6570\u5b57\u8f6c\u6362\u4e3a\u5177\u5907\u7ed9\u5b9a\u57fa\u6570\u7684\u6587\u672c\u8868\u793a\u3002', [
            parameterDescription('number'),
            parameterDescription('radix'),
            parameterDescription('min_length')
        ]),
        'CEILING.MATH': functionDescription('\u6b64\u51fd\u6570\u5c06\u6570\u5b57\u5411\u4e0a\u820d\u5165\u4e3a\u6700\u63a5\u8fd1\u7684\u6574\u6570\u6216\u6700\u63a5\u8fd1\u7684\u6307\u5b9a\u57fa\u6570\u7684\u500d\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE),
            parameterDescription('[mode]')
        ]),
        'COMBINA': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u6570\u76ee\u7684\u9879\u7684\u7ec4\u5408\u6570\uff08\u5305\u542b\u91cd\u590d\uff09\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'COT': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4ee5\u5f27\u5ea6\u8868\u793a\u7684\u89d2\u5ea6\u7684\u4f59\u5207\u503c\u3002', [
            parameterDescription('angle')
        ]),
        'COTH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u53cc\u66f2\u89d2\u5ea6\u7684\u53cc\u66f2\u4f59\u5207\u503c\u3002', [
            parameterDescription('value')
        ]),
        'CSC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u89d2\u5ea6\u7684\u4f59\u5272\u503c\uff0c\u4ee5\u5f27\u5ea6\u8868\u793a\u3002', [
            parameterDescription('angle')
        ]),
        'CSCH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u89d2\u5ea6\u7684\u53cc\u66f2\u4f59\u5272\u503c\uff0c\u4ee5\u5f27\u5ea6\u8868\u793a\u3002', [
            parameterDescription('value')
        ]),
        'DECIMAL': functionDescription('\u6b64\u51fd\u6570\u6309\u7ed9\u5b9a\u57fa\u6570\u5c06\u6570\u5b57\u7684\u6587\u672c\u8868\u793a\u5f62\u5f0f\u8f6c\u6362\u6210\u5341\u8fdb\u5236\u6570\u3002', [
            parameterDescription('text'),
            parameterDescription('radix')
        ]),
        'FLOOR.MATH': functionDescription('\u6b64\u51fd\u6570\u5c06\u6570\u5b57\u5411\u4e0b\u820d\u5165\u4e3a\u6700\u63a5\u8fd1\u7684\u6574\u6570\u6216\u6700\u63a5\u8fd1\u7684\u6307\u5b9a\u57fa\u6570\u7684\u500d\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE),
            parameterDescription('[mode]')
        ]),
        'SEC': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u89d2\u5ea6\u7684\u6b63\u5272\u503c\u3002', [
            parameterDescription('angle')
        ]),
        'SECH': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u89d2\u5ea6\u7684\u53cc\u66f2\u6b63\u5272\u503c\u3002', [
            parameterDescription('value')
        ]),
        'BINOM.DIST.RANGE': functionDescription('\u6b64\u51fd\u6570\u4f7f\u7528\u4e8c\u9879\u5f0f\u5206\u5e03\u8fd4\u56de\u8bd5\u9a8c\u7ed3\u679c\u7684\u6982\u7387\u3002', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('number_s'),
            parameterDescription('[number_s2]')
        ]),
        'GAMMA': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de gamma \u51fd\u6570\u503c\u3002', [
            parameterDescription('number')
        ]),
        'MAXIFS': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u7ed9\u5b9a\u6761\u4ef6\u6216\u6807\u51c6\u6307\u5b9a\u7684\u5355\u5143\u683c\u4e2d\u7684\u6700\u5927\u503c\u3002', [
            parameterDescription('max_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true),
        ]),
        'GAUSS': functionDescription('\u6b64\u51fd\u6570\u8ba1\u7b97\u6807\u51c6\u6b63\u6001\u603b\u4f53\u7684\u6210\u5458\u5904\u4e8e\u5e73\u5747\u503c\u4e0e\u5e73\u5747\u503c\u7684 z \u500d\u6807\u51c6\u504f\u5dee\u4e4b\u95f4\u7684\u6982\u7387\u3002', [
            parameterDescription('number')
        ]),
        'MINIFS': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u7ec4\u7ed9\u5b9a\u6761\u4ef6\u6216\u6807\u51c6\u6307\u5b9a\u7684\u5355\u5143\u683c\u4e4b\u95f4\u7684\u6700\u5c0f\u503c\u3002', [
            parameterDescription('min_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true)
        ]),
        'PERMUTATIONA': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u53ef\u4ece\u5bf9\u8c61\u603b\u6570\u4e2d\u9009\u62e9\u7684\u7ed9\u5b9a\u6570\u76ee\u5bf9\u8c61\uff08\u542b\u91cd\u590d\uff09\u7684\u6392\u5217\u6570\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'PHI': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6807\u51c6\u6b63\u6001\u5206\u5e03\u7684\u5bc6\u5ea6\u51fd\u6570\u503c\u3002', [
            parameterDescription('value')
        ]),
        'SKEW.P': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u57fa\u4e8e\u6837\u672c\u603b\u4f53\u7684\u5206\u5e03\u4e0d\u5bf9\u79f0\u5ea6\uff1a\u8868\u660e\u5206\u5e03\u76f8\u5bf9\u4e8e\u5e73\u5747\u503c\u7684\u4e0d\u5bf9\u79f0\u7a0b\u5ea6\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'BAHTTEXT': functionDescription('\u6b64\u51fd\u6570\u5c06\u6570\u5b57\u8f6c\u6362\u4e3a\u6cf0\u8bed\u6587\u672c\u5e76\u6dfb\u52a0\u540e\u7f00\u201c\u6cf0\u94e2\u201d\u3002', [
            parameterDescription('number')
        ]),
        'CONCAT': functionDescription('\u6b64\u51fd\u6570\u5c06\u591a\u4e2a\u533a\u57df\u548c/\u6216\u5b57\u7b26\u4e32\u7684\u6587\u672c\u7ec4\u5408\u8d77\u6765\uff0c\u4f46\u4e0d\u63d0\u4f9b\u5206\u9694\u7b26\u6216 IgnoreEmpty \u53c2\u6570\u3002', [
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'FINDB': functionDescription('\u6b64\u51fd\u6570\u7528\u4e8e\u5728\u7b2c\u4e8c\u4e2a\u6587\u672c\u4e32\u4e2d\u5b9a\u4f4d\u7b2c\u4e00\u4e2a\u6587\u672c\u4e32\uff0c\u5e76\u8fd4\u56de\u7b2c\u4e00\u4e2a\u6587\u672c\u4e32\u7684\u8d77\u59cb\u4f4d\u7f6e\u7684\u503c\uff0c\u8be5\u503c\u4ece\u7b2c\u4e8c\u4e2a\u6587\u672c\u4e32\u7684\u7b2c\u4e00\u4e2a\u5b57\u7b26\u7b97\u8d77\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'LEFTB': functionDescription('\u6b64\u51fd\u6570\u57fa\u4e8e\u6240\u6307\u5b9a\u7684\u5b57\u8282\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7684\u7b2c\u4e00\u4e2a\u6216\u524d\u51e0\u4e2a\u5b57\u7b26\u3002', [
            parameterDescription('text'),
            parameterDescription('num_bytes')
        ]),
        'LENB': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7528\u4e8e\u4ee3\u8868\u5b57\u7b26\u7684\u5b57\u8282\u6570\u3002', [
            parameterDescription('text')
        ]),
        'MIDB': functionDescription('\u6b64\u51fd\u6570\u6839\u636e\u6307\u5b9a\u7684\u5b57\u8282\u6570\uff0c\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u4ece\u6307\u5b9a\u4f4d\u7f6e\u5f00\u59cb\u7684\u7279\u5b9a\u6570\u76ee\u7684\u5b57\u7b26\u3002', [
            parameterDescription('text'),
            parameterDescription('start_num'),
            parameterDescription('num_bytes')
        ]),
        'REPLACEB': functionDescription('\u6b64\u51fd\u6570\u4f7f\u7528\u5176\u4ed6\u6587\u672c\u5b57\u7b26\u4e32\u5e76\u6839\u636e\u6240\u6307\u5b9a\u7684\u5b57\u8282\u6570\u66ff\u6362\u67d0\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7684\u90e8\u5206\u6587\u672c\u3002', [
            parameterDescription('old_text'),
            parameterDescription('start_byte'),
            parameterDescription('num_bytes'),
            parameterDescription('new_text')
        ]),
        'RIGHTB': functionDescription('\u6b64\u51fd\u6570\u6839\u636e\u6240\u6307\u5b9a\u7684\u5b57\u8282\u6570\u8fd4\u56de\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u6700\u540e\u4e00\u4e2a\u6216\u591a\u4e2a\u5b57\u7b26\u3002', [
            parameterDescription('text'),
            parameterDescription('num_bytes')
        ]),
        'SEARCHB': functionDescription('\u6b64\u51fd\u6570\u53ef\u5728\u7b2c\u4e8c\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u67e5\u627e\u7b2c\u4e00\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\uff0c\u5e76\u8fd4\u56de\u7b2c\u4e00\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u7684\u8d77\u59cb\u4f4d\u7f6e\u7684\u7f16\u53f7\uff0c\u8be5\u7f16\u53f7\u4ece\u7b2c\u4e8c\u4e2a\u6587\u672c\u5b57\u7b26\u4e32\u7684\u7b2c\u4e00\u4e2a\u5b57\u7b26\u7b97\u8d77\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'TEXTJOIN': functionDescription('\u6b64\u51fd\u6570\u5c06\u591a\u4e2a\u533a\u57df\u548c/\u6216\u5b57\u7b26\u4e32\u7684\u6587\u672c\u7ec4\u5408\u8d77\u6765\uff0c\u5e76\u5305\u62ec\u4f60\u5728\u8981\u7ec4\u5408\u7684\u5404\u6587\u672c\u503c\u4e4b\u95f4\u6307\u5b9a\u7684\u5206\u9694\u7b26\u3002', [
            parameterDescription('delimiter'),
            parameterDescription('ignore_empty'),
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'UNICHAR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u7ed9\u5b9a\u6570\u503c\u5f15\u7528\u7684 Unicode \u5b57\u7b26\u3002', [
            parameterDescription('number')
        ]),
        'UNICODE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u5bf9\u5e94\u4e8e\u6587\u672c\u7684\u7b2c\u4e00\u4e2a\u5b57\u7b26\u7684\u6570\u5b57\uff08\u4ee3\u7801\u70b9\uff09\u3002', [
            parameterDescription('text')
        ]),
        'ENCODEURL': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de URL \u7f16\u7801\u7684\u5b57\u7b26\u4e32\u3002', [
            parameterDescription('text')
        ]),
        'BC_QRCODE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8QRCode\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('errorCorrectionLevel'),
            parameterDescription('model'),
            parameterDescription('version'),
            parameterDescription('mask'),
            parameterDescription('connection'),
            parameterDescription('connectionNo'),
            parameterDescription('charCode'),
            parameterDescription('charset'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_EAN13': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8EAN13\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('addOn'),
            parameterDescription('addOnLabelPosition'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_EAN8': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8EAN8\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_CODABAR': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8CODABAR\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('checkDigit'),
            parameterDescription('nwRatio'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_CODE39': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8CODE39\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('labelWithStartAndStopCharacter'),
            parameterDescription('checkDigit'),
            parameterDescription('nwRatio'),
            parameterDescription('fullASCII'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_CODE93': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8CODE93\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('checkDigit'),
            parameterDescription('fullASCII'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_CODE128': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8CODE128\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('codeSet'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_GS1_128': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8GS1_128\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_CODE49': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8CODE49\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('showLabel'),
            parameterDescription('labelPosition'),
            parameterDescription('grouping'),
            parameterDescription('groupNo'),
            parameterDescription('fontFamily'),
            parameterDescription('fontStyle'),
            parameterDescription('fontWeight'),
            parameterDescription('textDecoration'),
            parameterDescription('textAlign'),
            parameterDescription('fontSize'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_PDF417': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8PDF417\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('errorCorrectionLevel'),
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('compact'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'BC_DATAMATRIX': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u7528\u4e8e\u63cf\u7ed8DATAMATRIX\u7684\u6570\u636e\u96c6\u3002', [
            parameterDescription('value'),
            parameterDescription('color'),
            parameterDescription('backgroundColor'),
            parameterDescription('eccMode'),
            parameterDescription('ecc200SymbolSize'),
            parameterDescription('ecc200EncodingMode'),
            parameterDescription('ecc00_140SymbolSize'),
            parameterDescription('structuredAppend'),
            parameterDescription('structureNumber'),
            parameterDescription('fileIdentifier'),
            parameterDescription('quietZoneLeft'),
            parameterDescription('quietZoneRight'),
            parameterDescription('quietZoneTop'),
            parameterDescription('quietZoneBottom')
        ]),
        'FILTER': functionDescription('\u6b64\u51fd\u6570\u8fc7\u6ee4\u4e00\u7247\u533a\u57df\u6216\u4e00\u4e2a\u6570\u7ec4\u3002', [
            parameterDescription('array'),
            parameterDescription('include'),
            parameterDescription('if_empty')
        ]),
        'RANDARRAY': functionDescription('\u8be5\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u968f\u673a\u6570\u7ec4\u3002', [
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('min'),
            parameterDescription('max'),
            parameterDescription('whole_number')
        ]),
        'SEQUENCE': functionDescription('\u6b64\u51fd\u6570\u8fd4\u56de\u4e00\u4e2a\u6570\u5b57\u5e8f\u5217\u3002', [
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('start'),
            parameterDescription('step')
        ]),
        'SINGLE': functionDescription('\u5f53\u7ed9\u5b9a\u4e00\u4e2a\u503c\uff0c\u4e00\u7247\u533a\u57df\u6216\u4e00\u4e2a\u6570\u7ec4\u65f6\uff0c\u6b64\u51fd\u6570\u8fd4\u56de\u5355\u4e2a\u503c\u3002', [
            parameterDescription('value')
        ]),
        'SORT': functionDescription('\u6b64\u51fd\u6570\u5bf9\u4e00\u7247\u6216\u4e00\u4e2a\u6570\u7ec4\u8fdb\u884c\u6392\u5e8f\u3002', [
            parameterDescription('array'),
            parameterDescription('sort_index'),
            parameterDescription('sort_order'),
            parameterDescription('by_col')
        ]),
        'SORTBY': functionDescription('\u6b64\u51fd\u6570\u6839\u636e\u76f8\u5e94\u4e00\u7247\u533a\u57df\u6216\u4e00\u4e2a\u6570\u7ec4\u4e2d\u7684\u503c\u5bf9\u4e00\u7247\u533a\u57df\u6216\u4e00\u4e2a\u6570\u7ec4\u8fdb\u884c\u6392\u5e8f\u3002', [
            parameterDescription('array'),
            parameterDescription('by_array1'),
            parameterDescription('sort_order1'),
            parameterDescription('by_array2', true),
            parameterDescription('sort_order2', true),
        ]),
        'UNIQUE': functionDescription('\u6b64\u51fd\u6570\u4ece\u4e00\u7247\u533a\u57df\u6216\u4e00\u4e2a\u6570\u7ec4\u8fd4\u56de\u552f\u4e00\u503c\u3002', [
            parameterDescription('array'),
            parameterDescription('by_col'),
            parameterDescription('exactly_once')
        ]),
        'QUERY': functionDescription('\u6b64\u51fd\u6570\u4ece\u6570\u636e\u6e90\u7684\u6570\u636e\u8868\u4e2d\u8fd4\u56de\u6570\u636e\u3002', [
            parameterDescription('tableAndRows'),
            parameterDescription('columns'),
            parameterDescription('returnObject')
        ]),
        'LET': functionDescription('\u6b64\u51fd\u6570\u5c06\u7ed3\u679c\u5206\u914d\u7ed9\u540d\u79f0\u3002\u53ef\u7528\u4e8e\u901a\u8fc7\u5b9a\u4e49\u516c\u5f0f\u5185\u7684\u540d\u79f0\u50a8\u5b58\u4e2d\u95f4\u8ba1\u7b97\u7ed3\u679c\u548c\u503c\u3002\u8fd9\u4e9b\u540d\u79f0\u4ec5\u5728 LET \u51fd\u6570\u8303\u56f4\u5185\u9002\u7528\u3002', [
            parameterDescription('name1'),
            parameterDescription('name_value1'),
            parameterDescription('name2', true),
            parameterDescription('name_value2', true),
            parameterDescription('calculation')
        ]),
        'IMAGE': functionDescription('\u6b64\u51fd\u6570\u4f1a\u5728\u5355\u5143\u683c\u5185\u6839\u636eurl\u6216\u8005base64\u5b57\u7b26\u4e32\u63d2\u5165\u4e00\u5f20\u56fe\u7247\u3002', [
            parameterDescription("source"),
            parameterDescription("[alt_text]"),
            parameterDescription("[sizing]"),
            parameterDescription("[height]"),
            parameterDescription("[width]"),
            parameterDescription("[clipY]"),
            parameterDescription("[clipX]"),
            parameterDescription("[clipHeight]"),
            parameterDescription("[clipWidth]"),
            parameterDescription("[vAlign]"),
            parameterDescription("[hAlign]"),
        ]),
        'GETPIVOTDATA': functionDescription('\u6b64\u51fd\u6570\u4f1a\u63d0\u53d6\u5b58\u50a8\u5728\u6570\u636e\u900f\u89c6\u8868\u4e2d\u7684\u6570\u636e\u3002', [
            parameterDescription('data_field'),
            parameterDescription('pivot_table'),
            parameterDescription('[field1, item1]'),
            parameterDescription('...')
        ]),
        'WEBSERVICE': functionDescription('\u6b64\u51fd\u6570\u4f1a\u4eceWeb\u670d\u52a1\u8fd4\u56de\u6570\u636e\u3002', [
            parameterDescription('url')
        ]),
        'FILTERJSON': functionDescription('\u6b64\u51fd\u6570\u4f1a\u5c06json\u5b57\u7b26\u4e32\u8f6c\u6362\u4e3a\u4e00\u7ec4\u503c\uff0c\u4e00\u4e2a\u5bf9\u8c61\u6216\u8005\u4e00\u4e2a\u5bf9\u8c61\u6570\u7ec4', [
            parameterDescription('json_string')
        ]),
        "ASC": functionDescription("\u5c06\u53cc\u5b57\u8282\u5b57\u7b26\u8f6c\u6362\u6210\u5355\u5b57\u8282\u5b57\u7b26\u3002\u4e0e\u53cc\u5b57\u8282\u5b57\u7b26\u96c6(DBCS)\u4e00\u8d77\u4f7f\u7528\u3002", [
            parameterDescription("text")
        ]),
        "DBCS": functionDescription("\u5c06\u5355\u5b57\u8282\u5b57\u7b26\u8f6c\u6362\u6210\u53cc\u5b57\u8282\u5b57\u7b26\u3002", [
            parameterDescription("text")
        ]),
        'LAMBDA': functionDescription('\u521b\u5efa\u4e00\u4e2a\u53ef\u5728\u516c\u5f0f\u4e2d\u88ab\u8c03\u7528\u7684\u51fd\u6570\u503c\u3002', [
            parameterDescription('parameter_or_calculation'),
            parameterDescription('[parameter_or_calculation]', true)
        ]),
        'MAP': functionDescription('\u901a\u8fc7\u5e94\u7528 LAMBDA \u6765\u521b\u5efa\u65b0\u503c\uff0c\u8fd4\u56de\u5c06\u6570\u7ec4\u4e2d\u6bcf\u4e2a\u503c\u6620\u5c04\u5230\u65b0\u503c\u800c\u5f62\u6210\u7684\u6570\u7ec4\u3002', [
            parameterDescription('array'),
            parameterDescription('lambda_or_array', true)
        ]),
        'REDUCE': functionDescription('\u901a\u8fc7\u5c06 LAMBDA \u5e94\u7528\u4e8e\u6bcf\u4e2a\u503c\uff0c\u5e76\u5728\u7d2f\u52a0\u5668\u4e2d\u8fd4\u56de\u603b\u503c\uff0c\u5c06\u6570\u7ec4\u51cf\u5c0f\u4e3a\u7d2f\u79ef\u503c\u3002', [
            parameterDescription('init_value'),
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'SCAN': functionDescription('\u901a\u8fc7\u5bf9\u6bcf\u4e2a\u503c\u5e94\u7528 LAMBDA \u6765\u626b\u63cf\u6570\u7ec4\uff0c\u5e76\u8fd4\u56de\u5177\u6709\u6bcf\u4e2a\u4e2d\u95f4\u503c\u7684\u6570\u7ec4\u3002', [
            parameterDescription('init_value'),
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'MAKEARRAY': functionDescription('\u901a\u8fc7\u5e94\u7528 LAMBDA \u6765\u8fd4\u56de\u6307\u5b9a\u884c\u548c\u5217\u5927\u5c0f\u7684\u8ba1\u7b97\u6570\u7ec4\u3002', [
            parameterDescription('rows'),
            parameterDescription('cols'),
            parameterDescription('function')
        ]),
        'BYCOL': functionDescription('\u5c06 LAMBDA \u5e94\u7528\u4e8e\u6bcf\u4e00\u5217\uff0c\u5e76\u8fd4\u56de\u7ed3\u679c\u7684\u6570\u7ec4\u3002 \u4f8b\u5982\uff0c\u5982\u679c\u539f\u59cb\u6570\u7ec4\u662f 3 \u5217\u4e58 2 \u884c\uff0c\u5219\u8fd4\u56de\u7684\u6570\u7ec4\u4e3a 3 \u5217\u4e58 1 \u884c\u3002', [
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'BYROW': functionDescription('\u5c06 LAMBDA \u5e94\u7528\u4e8e\u6bcf\u4e00\u884c\uff0c\u5e76\u8fd4\u56de\u7ed3\u679c\u6570\u7ec4\u3002 \u4f8b\u5982\uff0c\u5982\u679c\u539f\u59cb\u6570\u7ec4\u662f 3 \u5217\u4e58 2 \u884c\uff0c\u5219\u8fd4\u56de\u7684\u6570\u7ec4\u4e3a 1 \u5217\u4e58 2 \u884c\u3002', [
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'ISOMITTED': functionDescription('\u68c0\u67e5LAMBDA\u4e2d\u7684\u503c\u662f\u5426\u7f3a\u5931\uff0c\u5e76\u8fd4\u56de TRUE \u6216 FALSE\u3002', [
            parameterDescription('argument')
        ]),
        'TEXTBEFORE': functionDescription('\u8fd4\u56de\u5206\u9694\u5b57\u7b26\u4e4b\u524d\u7684\u6587\u672c\u3002', [
            parameterDescription('text'),
            parameterDescription('delimiter'),
            parameterDescription('[instance_num]'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
            parameterDescription('[match_end]'),
            parameterDescription(IF_NOT_FOUND)
        ]),
        'TEXTAFTER': functionDescription('\u8fd4\u56de\u5206\u9694\u5b57\u7b26\u4e4b\u540e\u7684\u6587\u672c\u3002', [
            parameterDescription('text'),
            parameterDescription('delimiter'),
            parameterDescription('[instance_num]'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
            parameterDescription('[match_end]'),
            parameterDescription(IF_NOT_FOUND)
        ]),
        'TEXTSPLIT': functionDescription('\u4f7f\u7528\u5217\u548c\u884c\u5206\u9694\u7b26\u62c6\u5206\u6587\u672c\u5b57\u7b26\u4e32\u3002', [
            parameterDescription('text'),
            parameterDescription('col_delimiter'),
            parameterDescription('[row_delimiter]'),
            parameterDescription('[ignore_empty]'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
            parameterDescription(PAD_WITH)
        ]),
        'SJS.REGEXEXTRACT': functionDescription('\u6309\u7167\u6b63\u5219\u8868\u8fbe\u5f0f\u63d0\u53d6\u5339\u914d\u7684\u5b50\u4e32\u3002', [
            parameterDescription('text'),
            parameterDescription('regular_expression'),
            parameterDescription('[modifiers]')
        ]),
        'SJS.REGEXMATCH': functionDescription('\u5224\u65ad\u4e00\u6bb5\u6587\u672c\u662f\u5426\u4e0e\u6b63\u5219\u8868\u8fbe\u5f0f\u76f8\u5339\u914d\u3002', [
            parameterDescription('text'),
            parameterDescription('regular_expression'),
            parameterDescription('[modifiers]')
        ]),
        'SJS.REGEXREPLACE': functionDescription('\u4f7f\u7528\u6b63\u5219\u8868\u8fbe\u5f0f\u5c06\u6587\u672c\u5b57\u7b26\u4e32\u4e2d\u7684\u4e00\u90e8\u5206\u66ff\u6362\u4e3a\u5176\u4ed6\u6587\u672c\u5b57\u7b26\u4e32\u3002', [
            parameterDescription('text'),
            parameterDescription('regular_expression'),
            parameterDescription('replacement'),
            parameterDescription('[modifiers]')
        ]),
        'VSTACK': functionDescription('\u5c06\u6570\u7ec4\u5782\u76f4\u5806\u53e0\u5230\u4e00\u4e2a\u6570\u7ec4\u4e2d\u3002', [
            parameterDescription('array1'),
            parameterDescription('[array2]', true)
        ]),
        'HSTACK': functionDescription('\u5c06\u6570\u7ec4\u6c34\u5e73\u5806\u53e0\u5230\u4e00\u4e2a\u6570\u7ec4\u4e2d\u3002', [
            parameterDescription('array1'),
            parameterDescription('[array2]', true)
        ]),
        'TOROW': functionDescription('\u4ee5\u4e00\u884c\u5f62\u5f0f\u8fd4\u56de\u6570\u7ec4\u3002', [
            parameterDescription('array'),
            parameterDescription('[ignore]'),
            parameterDescription('[scan_by_column]')
        ]),
        'TOCOL': functionDescription('\u4ee5\u4e00\u5217\u5f62\u5f0f\u8fd4\u56de\u6570\u7ec4\u3002', [
            parameterDescription('array'),
            parameterDescription('[ignore]'),
            parameterDescription('[scan_by_column]')
        ]),
        'WRAPROWS': functionDescription('\u5728\u6307\u5b9a\u6570\u76ee\u7684\u503c\u540e\u5c06\u884c\u6216\u5217\u77e2\u91cf\u6362\u884c\u3002', [
            parameterDescription('vector'),
            parameterDescription('wrap_count'),
            parameterDescription(PAD_WITH)
        ]),
        'WRAPCOLS': functionDescription('\u5728\u6307\u5b9a\u6570\u76ee\u7684\u503c\u540e\u5c06\u884c\u6216\u5217\u77e2\u91cf\u6362\u884c\u3002', [
            parameterDescription('vector'),
            parameterDescription('wrap_count'),
            parameterDescription(PAD_WITH)
        ]),
        'TAKE': functionDescription('\u4ece\u6570\u7ec4\u5f00\u5934\u6216\u7ed3\u5c3e\u8fd4\u56de\u884c\u6216\u5217\u3002', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('[columns]')
        ]),
        'DROP': functionDescription('\u4ece\u6570\u7ec4\u5f00\u5934\u6216\u7ed3\u5c3e\u5220\u9664\u884c\u6216\u5217\u3002', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('[columns]')
        ]),
        'EXPAND': functionDescription('\u5c06\u6570\u7ec4\u6269\u5c55\u5230\u6307\u5b9a\u7ef4\u5ea6\u3002', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('[columns]'),
            parameterDescription(PAD_WITH)
        ]),
        'CHOOSEROWS': functionDescription('\u8fd4\u56de\u6570\u7ec4\u4e2d\u7684\u6307\u5b9a\u884c\u3002', [
            parameterDescription('array'),
            parameterDescription('row_num1'),
            parameterDescription('[row_num2]', true)
        ]),
        'CHOOSECOLS': functionDescription('\u8fd4\u56de\u6570\u7ec4\u4e2d\u7684\u6307\u5b9a\u5217\u3002', [
            parameterDescription('array'),
            parameterDescription('col_num1'),
            parameterDescription('[col_num2]', true)
        ]),
        'SJS.UUID': functionDescription('\u8fd4\u56de\u968f\u673a\u7684UUID\u3002', [
            parameterDescription('number'),
        ]),
        'SJS.ENDWITH': functionDescription('\u8fd4\u56de\u5b57\u7b26\u4e321\u662f\u5426\u4ee5\u5b57\u7b26\u4e322\u7ed3\u675f\u3002', [
            parameterDescription('within_text'),
            parameterDescription('find_text'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
        ]),
        'SJS.STARTWITH': functionDescription('\u8fd4\u56de\u5b57\u7b26\u4e321\u662f\u5426\u4ee5\u5b57\u7b26\u4e322\u5f00\u59cb\u3002', [
            parameterDescription('within_text'),
            parameterDescription('find_text'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
        ]),
        'R.INDEX': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u5728\u7236\u5355\u5143\u683c\u7684\u6269\u5c55\u4e2d\u7684\u7d22\u5f15\u3002', [
            parameterDescription('reference'),
            parameterDescription('[context_reference]'),
        ]),
        'R.RANK': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u5728\u7236\u5355\u5143\u683c\u4e2d\u7684\u6392\u540d\u3002', [
            parameterDescription('reference'),
            parameterDescription('[context_reference]'),
            parameterDescription('[order]'),
            parameterDescription('[rank_mode]'),
        ]),
        'SJS.FISCALYEAR': functionDescription('\u8fd4\u56de\u65e5\u671f\u5bf9\u5e94\u7684\u8d22\u5e74\u3002', [
            parameterDescription('date'),
            parameterDescription('[start_month]'),
        ]),
        'R.R': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6839\u636e\u6307\u5b9a\u5355\u5143\u683c\u7684\u76f8\u5bf9\u504f\u79fb\u91cf\u3002', [
            parameterDescription('reference'),
            parameterDescription('offset'),
        ]),
        'R.A': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6839\u636e\u6307\u5b9a\u5355\u5143\u683c\u7684\u7edd\u5bf9\u504f\u79fb\u91cf\u3002', [
            parameterDescription('reference'),
            parameterDescription('offset'),
        ]),
        'R.V': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u5750\u6807\u504f\u79fb\u540e\u5355\u5143\u683c\u4e2d\u7684\u6570\u636e\u3002', [
            parameterDescription('reference'),
            parameterDescription('[offset]', true),
        ]),
        'R.CURRENTPAGE': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u5f53\u524d\u7684\u5206\u9875\u662f\u62a5\u8868\u7684\u7b2c\u51e0\u9875\u3002', []),
        'R.PAGESCOUNT': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u5f53\u524d\u62a5\u8868\u7684\u6240\u6709\u9875\u6570\u3002', []),
        'R.PROPORTION': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u5728\u5176\u7236\u8282\u70b9\u4e2d\u7684\u5360\u6bd4\u3002', [
            parameterDescription('value_reference'),
            parameterDescription('[context_reference1]'),
            parameterDescription('[context_reference2]'),
        ]),
        'R.CUMULATIVE': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u7684\u7d2f\u52a0\u503c\u3002', [
            parameterDescription('value_reference'),
            parameterDescription('[context_reference1]'),
            parameterDescription('[context_reference2]'),
        ]),
        'R.MOM': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u7684\u73af\u6bd4\u6570\u636e\u3002', [
            parameterDescription('value_reference'),
            parameterDescription('[context_reference1]'),
            parameterDescription('[context_reference2]'),
        ]),
        'R.YOY': functionDescription('(\u62a5\u8868\u4e13\u7528) \u8fd4\u56de\u6307\u5b9a\u5355\u5143\u683c\u7684\u540c\u6bd4\u6570\u636e\u3002', [
            parameterDescription('value_reference'),
            parameterDescription('context_reference1'),
            parameterDescription('[context_reference2]'),
        ]),
    },
    _tableFunctionsResource: {
        All: { name: '#All', description: '\u8fd4\u56de\u8868\u6216\u6307\u5b9a\u8868\u5217\u7684\u5b8c\u6574\u5185\u5bb9\uff0c\u5305\u62ec\u5217\u6807\u9898\u3001\u6570\u636e\u548c\u6c47\u603b\u884c' },
        Data: { name: '#Data', description: '\u8fd4\u56de\u8868\u6216\u6307\u5b9a\u8868\u5217\u7684\u6570\u636e\u5355\u5143\u683c' },
        Headers: { name: '#Headers', description: '\u8fd4\u56de\u8868\u6216\u6307\u5b9a\u8868\u5217\u7684\u5217\u6807\u9898' },
        Totals: { name: '#Totals', description: '\u8fd4\u56de\u8868\u6216\u6307\u5b9a\u8868\u5217\u7684\u6c47\u603b\u884c' },
        thisRow: { name: '#This Row', description: '\u6b64\u884c' },
    }
};


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_192383__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_192383__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var calcEngine_res_zh_1 = __nested_webpack_require_192383__( "./src/calcEngine.res.zh.ts");
exports.SR = { zh: calcEngine_res_zh_1.resource };

}();
((GC = typeof GC === "undefined" ? {} : GC).Spread = GC.Spread || {}).CalcEngine = __webpack_exports__;
 })()
;

module.exports = GC.Spread.CalcEngine;

/***/ }),

/***/ "./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.zh.js":
/*!***********************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.zh.js ***!
  \***********************************************************************************/
/***/ (function(module) {

var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./dist/src/res.zh.js":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.resource = void 0;
function functionDescription(description, parameters) {
    return {
        description: description,
        parameters: parameters
    };
}
function parameterDescription(name, optional, repeatable, enumInfo) {
    return {
        name: name,
        optional: optional,
        repeatable: repeatable,
        enum: enumInfo,
    };
}
var FRAME_PRECEDING_ENUM_INFO = [
    {
        value: '-1',
        description: '\u5206\u533a\u7684\u524d\u7f6e\u8fb9\u754c\u3002',
    },
    {
        value: '[@]',
        description: '\u5f53\u524d\u884c\u6216\u884c\u7684\u503c\u3002'
    },
    {
        value: '[@-n]',
        description: '\u5f53\u524d\u884c\u6216\u884c\u7684\u503c\u7684\u524d\u8fb9\u7684\u884c\u6216\u503c\uff08\u5347\u5e8f\uff09\u3002'
    },
    {
        value: '[@+n]',
        description: '\u5f53\u524d\u884c\u6216\u884c\u7684\u503c\u7684\u524d\u8fb9\u7684\u884c\u6216\u503c\uff08\u964d\u5e8f\uff09\u3002'
    },
];
var FRAME_FOLLOWING_ENUM_INFO = [
    {
        value: '-1',
        description: '\u5206\u533a\u7684\u540e\u7f6e\u8fb9\u754c\u3002',
    },
    {
        value: '[@]',
        description: '\u5f53\u524d\u884c\u6216\u884c\u7684\u503c\u3002'
    },
    {
        value: '[@+n]',
        description: '\u5f53\u524d\u884c\u6216\u884c\u7684\u503c\u7684\u540e\u8fb9\u7684\u884c\u6216\u503c\uff08\u5347\u5e8f\uff09\u3002'
    },
    {
        value: '[@-n]',
        description: '\u5f53\u524d\u884c\u6216\u884c\u7684\u503c\u7684\u540e\u8fb9\u7684\u884c\u6216\u503c\uff08\u964d\u5e8f\uff09\u3002'
    },
];
var FRAME_EXCLUDE_MODE_ENUM_INFO = [
    {
        value: '0',
        description: '\u9ed8\u8ba4\u60c5\u51b5\uff0c\u4e0d\u6392\u9664\u4efb\u4f55\u884c\u3002',
    },
    {
        value: '1',
        description: '\u5f53\u524d\u884c\u88ab\u6392\u9664\u5728\u5916\uff0c\u5f53\u524d\u884c\u7684\u5176\u4ed6\u5bf9\u7b49\u884c\u4fdd\u7559\u3002'
    },
    {
        value: '2',
        description: '\u5f53\u524d\u884c\u548c\u5bf9\u7b49\u884c\u90fd\u88ab\u6392\u9664\u5728\u5916\u3002'
    },
    {
        value: '3',
        description: '\u5f53\u524d\u884c\u4fdd\u7559\uff0c\u5176\u4ed6\u5bf9\u7b49\u884c\u88ab\u6392\u9664\u5728\u5916\u3002'
    },
];
exports.resource = {
    _calculationFunctionsResource: {
        WINDOW: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u901a\u8fc7\u5206\u533a\u548c\u6392\u5e8f\u5c06\u884c\u786e\u5b9a\u4e3a\u7a97\u53e3\u7528\u4ee5\u6267\u884c\u7a97\u53e3\u51fd\u6570\u3002', [
            parameterDescription('window_function'),
            parameterDescription('partition_by', true),
            parameterDescription('order_by', true),
            parameterDescription('frame_rows\\range\\groups', true),
        ]),
        WINDOWDEF: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u5b9a\u4e49\u4e00\u4e2a\u5141\u8bb8\u88abWINDOW\u51fd\u6570\u91cd\u7528\u7684\u7b80\u5199\u3002', [
            parameterDescription('partition_by', true),
            parameterDescription('order_by', true),
            parameterDescription('frame_rows\\range\\groups', true),
        ]),
        PARTITIONBY: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u5c06\u884c\u5212\u5206\u4e3a\u591a\u4e2a\u5206\u533a\u3002', [
            parameterDescription('field_function', false, true),
        ]),
        ORDERBY: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u5b9a\u4e49\u6bcf\u4e2a\u5206\u533a\u4e2d\u7684\u903b\u8f91\u987a\u5e8f\u3002', [
            parameterDescription('field_function', false, true),
        ]),
        ORDERASC: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u6309\u7167\u5347\u5e8f\u6392\u5217\u6307\u5b9a\u5b57\u6bb5\u3002', [
            parameterDescription('field_function'),
        ]),
        ORDERDESC: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u6309\u7167\u964d\u5e8f\u6392\u5217\u6307\u5b9a\u5b57\u6bb5\u3002', [
            parameterDescription('field_function'),
        ]),
        FRAMEROWS: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u901a\u8fc7\u9488\u5bf9\u5f53\u524d\u884c\u6307\u5b9a\u524d\u540e\u51e0\u884c\u6765\u9650\u5236\u7a97\u53e3\u7684\u884c\u96c6\u3002', [
            parameterDescription('beginning_function', false, false, FRAME_PRECEDING_ENUM_INFO),
            parameterDescription('ending_function', true, false, FRAME_FOLLOWING_ENUM_INFO),
            parameterDescription('exclude_mode', true, false, FRAME_EXCLUDE_MODE_ENUM_INFO),
        ]),
        FRAMERANGE: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u901a\u8fc7\u6307\u5b9a\u76f8\u5bf9\u4e8e\u5f53\u524d\u884c\u4e2d\u503c\u7684\u524d\u540e\u8303\u56f4\u6765\u9650\u5236\u7a97\u53e3\u7684\u884c\u96c6\u3002', [
            parameterDescription('beginning_function', false, false, FRAME_PRECEDING_ENUM_INFO),
            parameterDescription('ending_function', true, false, FRAME_FOLLOWING_ENUM_INFO),
            parameterDescription('exclude_mode', true, false, FRAME_EXCLUDE_MODE_ENUM_INFO),
        ]),
        FRAMEGROUPS: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u901a\u8fc7\u6307\u5b9a\u76f8\u5bf9\u4e8e\u5f53\u524d\u7ec4\u8ba1\u6570\u7684\u201c\u7ec4\u201d\u7684\u5f00\u59cb\u6216\u7ed3\u675f\u8303\u56f4\u6765\u9650\u5236\u7a97\u53e3\u7684\u884c\u96c6\u3002', [
            parameterDescription('beginning_function', false, false, FRAME_PRECEDING_ENUM_INFO),
            parameterDescription('ending_function', true, false, FRAME_FOLLOWING_ENUM_INFO),
            parameterDescription('exclude_mode', true, false, FRAME_EXCLUDE_MODE_ENUM_INFO),
        ]),
        CUMEDIST: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u5f53\u524d\u503c\u5728\u4e00\u7ec4\u503c\u4e2d\u7684\u7d2f\u79ef\u5206\u5e03\u3002', []),
        W_PERCENTRANK: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8ba1\u7b97\u884c\u5728\u4e00\u4e2a\u884c\u5206\u533a\u4e2d\u7684\u76f8\u5bf9\u6392\u540d\u767e\u5206\u6bd4\u3002', []),
        ROWNUMBER: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8fd4\u56de\u5f53\u524d\u884c\u5728\u5176\u5206\u533a\u5185\u7684\u884c\u53f7\u3002', []),
        W_RANK: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8fd4\u56de\u7ed3\u679c\u96c6\u5206\u533a\u4e2d\u6bcf\u4e00\u884c\u7684\u6392\u540d\u3002', []),
        DENSERANK: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8fd4\u56de\u5f53\u524d\u884c\u5728\u5176\u5206\u533a\u5185\u7684\u8fde\u7eed\u6392\u540d\u3002', []),
        LEAD: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u63d0\u4f9b\u5bf9\u5f53\u524d\u884c\u540e\u9762\u7ed9\u5b9a\u7269\u7406\u504f\u79fb\u5904\u7684\u884c\u7684\u503c\u7684\u8bbf\u95ee\u3002', [
            parameterDescription('value_function'),
            parameterDescription('offset', true),
            parameterDescription('default_value', true),
        ]),
        LAG: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u63d0\u4f9b\u5bf9\u4f4d\u4e8e\u5f53\u524d\u884c\u4e4b\u524d\u7684\u7ed9\u5b9a\u7269\u7406\u504f\u79fb\u5904\u7684\u884c\u7684\u503c\u7684\u8bbf\u95ee\u3002', [
            parameterDescription('value_function'),
            parameterDescription('offset', true),
            parameterDescription('default_value', true),
        ]),
        NTILE: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u5c06\u4e00\u4e2a\u5206\u533a\u5212\u5206\u4e3aN\u4e2a\u5b58\u50a8\u6876\uff0c\u5e76\u4e3a\u6bcf\u4e00\u884c\u5206\u914d\u5176\u5b58\u50a8\u6876\u7f16\u53f7\u3002', [
            parameterDescription('n'),
        ]),
        FIRSTVALUE: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8bbf\u95ee\u7a97\u53e3\u5185\u7b2c\u4e00\u884c\u7684\u503c\u3002', [
            parameterDescription('value_function'),
        ]),
        LASTVALUE: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8bbf\u95ee\u7a97\u53e3\u5185\u6700\u540e\u4e00\u884c\u7684\u503c\u3002', [
            parameterDescription('value_function'),
        ]),
        NTHVALUE: functionDescription('\uff08\u9650\u5b9a\u96c6\u7b97\u8868\uff09\u8bbf\u95ee\u7a97\u53e3\u5185\u7b2cn\u884c\u7684\u503c\u3002', [
            parameterDescription('value_function'),
            parameterDescription('n'),
        ]),
        CALCULATE: functionDescription('\uff08\u9650\u5b9a\u5206\u7ec4\u7684\u96c6\u7b97\u8868\uff09\u4e3a\u516c\u5f0f\u5c55\u5f00\u5206\u7ec4\u4e0a\u4e0b\u6587\u3002', [
            parameterDescription('formula'),
            parameterDescription('remove_filters_function'),
        ]),
        REMOVEFILTERS: functionDescription('\uff08\u9650\u5b9a\u5206\u7ec4\u7684\u96c6\u7b97\u8868\uff09\u9009\u62e9\u4e0a\u7ea7\u5206\u7ec4\u7ea7\u522b\u7684\u4e0a\u4e0b\u6587\u3002', [
            parameterDescription('group_field_function', true, true),
        ]),
        CHILDREN: functionDescription('\uff08\u9650\u5b9a\u5206\u5c42\u7684\u96c6\u7b97\u8868\uff09\u68c0\u7d22\u8ddd\u79bb\u7236\u7ea7\u4e00\u5b9a\u504f\u79fb\u91cf\u5b50\u7ea7\u7684\u503c\u3002', [
            parameterDescription('level_offset'),
            parameterDescription('field_function'),
        ]),
        ONLYCHILDREN: functionDescription('\uff08\u9650\u5b9a\u5206\u5c42\u7684\u96c6\u7b97\u8868\uff09\u68c0\u7d22\u4e0d\u662f\u7236\u7ea7\u7684\u5b50\u7ea7\u7684\u503c\u3002', [
            parameterDescription('field_function'),
        ]),
        PARENT: functionDescription('\uff08\u9650\u5b9a\u5206\u5c42\u7684\u96c6\u7b97\u8868\uff09\u68c0\u7d22\u8ddd\u79bb\u5f53\u524d\u503c\u4e00\u5b9a\u504f\u79fb\u91cf\u7236\u7ea7\u7684\u503c\u3002', [
            parameterDescription('level_offset'),
            parameterDescription('field_function'),
        ]),
        LEVEL: functionDescription('\uff08\u9650\u5b9a\u5206\u5c42\u7684\u96c6\u7b97\u8868\uff09\u68c0\u7d22\u5f53\u524d\u7684\u7ea7\u522b\u3002', []),
        LEVELROWNUMBER: functionDescription('\uff08\u9650\u5b9a\u5206\u5c42\u7684\u96c6\u7b97\u8868\uff09\u68c0\u7d22\u5f53\u524d\u9879\u5728\u5176\u7236\u7ea7\u8303\u56f4\u4e0b\u7684\u884c\u53f7\u3002', []),
    },
};


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_10589__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_10589__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var res_zh_1 = __nested_webpack_require_10589__( "./dist/src/res.zh.js");
exports.SR = { zh: res_zh_1.resource };

}();
(GC = typeof GC === "undefined" ? {} : GC).Data = __webpack_exports__;
 })()
;

module.exports = GC.Data;

/***/ }),

/***/ "./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.zh.js":
/*!*****************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.zh.js ***!
  \*****************************************************************************/
/***/ (function(module, exports) {

var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./src/pivotEngine.res.zh.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.resource = void 0;
exports.resource = {
    dateResource: {
        Jan: "1\u6708",
        Feb: "2\u6708",
        Mar: "3\u6708",
        Apr: "4\u6708",
        May: "5\u6708",
        Jun: "6\u6708",
        Jul: "7\u6708",
        Aug: "8\u6708",
        Sep: "9\u6708",
        Oct: "10\u6708",
        Nov: "11\u6708",
        Dec: "12\u6708",
        Qtr1: "\u5b63\u5ea61",
        Qtr2: "\u5b63\u5ea62",
        Qtr3: "\u5b63\u5ea63",
        Qtr4: "\u5b63\u5ea64",
        Seconds: "\u79d2",
        Minutes: "\u5206",
        Hours: "\u5c0f\u65f6",
        Days: "\u65e5",
        Months: "\u6708",
        Quarters: "\u5b63\u5ea6",
        Years: "\u5e74",
        SECOND_GROUP_ITEMS: [":00", ":01", ":02", ":03", ":04", ":05", ":06", ":07", ":08", ":09", ":10", ":11", ":12", ":13", ":14", ":15", ":16", ":17", ":18", ":19", ":20", ":21", ":22", ":23", ":24", ":25", ":26", ":27", ":28", ":29", ":30", ":31", ":32", ":33", ":34", ":35", ":36", ":37", ":38", ":39", ":40", ":41", ":42", ":43", ":44", ":45", ":46", ":47", ":48", ":49", ":50", ":51", ":52", ":53", ":54", ":55", ":56", ":57", ":58", ":59"],
        MINUTE_GROUP_ITEMS: [":00", ":01", ":02", ":03", ":04", ":05", ":06", ":07", ":08", ":09", ":10", ":11", ":12", ":13", ":14", ":15", ":16", ":17", ":18", ":19", ":20", ":21", ":22", ":23", ":24", ":25", ":26", ":27", ":28", ":29", ":30", ":31", ":32", ":33", ":34", ":35", ":36", ":37", ":38", ":39", ":40", ":41", ":42", ":43", ":44", ":45", ":46", ":47", ":48", ":49", ":50", ":51", ":52", ":53", ":54", ":55", ":56", ":57", ":58", ":59"],
        HOUR_GROUP_ITEMS: ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"]
    },
    Exp_NoItemParseError: "\u67d0\u4e2a\u6570\u636e\u9879\u540d\u79f0\u65e0\u6cd5\u627e\u5230\uff0c\u8bf7\u68c0\u67e5\u786e\u8ba4\u540d\u79f0\u952e\u5165\u662f\u5426\u6b63\u786e\uff0c\u4e14\u900f\u89c6\u8868\u4e2d\u6709\u8be5\u6570\u636e\u9879\u3002",
    Exp_UnsupportedCalcItemType: "\u5728\u6570\u636e\u900f\u89c6\u8868\u516c\u5f0f\u4e2d\u4e0d\u652f\u6301\u5de5\u4f5c\u8868\u5f15\u7528\uff0c\u540d\u79f0\u548c\u6570\u7ec4\u3002",
    blank: "(\u7a7a\u767d)"
};


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_2531__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_2531__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var pivotEngine_res_zh_1 = __nested_webpack_require_2531__( "./src/pivotEngine.res.zh.ts");
exports.SR = { zh: pivotEngine_res_zh_1.resource };

}();
(GC = typeof GC === "undefined" ? {} : GC).Pivot = __webpack_exports__;
 })()
;

(function(GC) {
    if( true && typeof module.exports === 'object') {
        module.exports = GC.Pivot;
    } else if(true) {
        !(__WEBPACK_AMD_DEFINE_ARRAY__ = [], __WEBPACK_AMD_DEFINE_RESULT__ = (function() {return GC.Pivot}).apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__),
		__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__))
    } else {}
 })(GC || {});


/***/ }),

/***/ "./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.zh.js":
/*!*********************************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.zh.js ***!
  \*********************************************************************************************/
/***/ (function(module) {

var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./src/common/util/util.res.zh.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_Separator = exports.Exp_InvalidCast = exports.Exp_InvalidNumberFormat = exports.Exp_BadFormatSpecifier = exports.Exp_InvalidNumberGroupSize = exports.Exp_InvalidSemicolons = exports.Exp_InvalidExponentFormat = exports.Exp_InvalidDateFormat = void 0;
exports.Exp_InvalidDateFormat = '\u65e0\u6548\u65e5\u671f\u683c\u5f0f';
exports.Exp_InvalidExponentFormat = '\u65e0\u6548\u6307\u6570\u683c\u5f0f';
exports.Exp_InvalidSemicolons = '\u65e0\u6548\u683c\u5f0f : \u8fc7\u591a\u5206\u53f7';
exports.Exp_InvalidNumberGroupSize = '\u6570\u503c\u7ec4\u5927\u5c0f\u8303\u56f4\u5fc5\u987b\u5728 1 \u5230 9 \u4e4b\u95f4\uff0c\u9664\u4e86\u6700\u540e\u4e00\u4e2a\u503c\u53ef\u4ee5\u4e3a 0 \u3002';
exports.Exp_BadFormatSpecifier = '\u9519\u8bef\u7684\u683c\u5f0f\u6307\u793a\u7b26';
exports.Exp_InvalidNumberFormat = '\u65e0\u6548\u6570\u503c\u683c\u5f0f';
exports.Exp_InvalidCast = '\u65e0\u6548\u8f6c\u6362\u5f02\u5e38';
exports.Exp_Separator = '\u5728\u6587\u5316\u4fe1\u606f\u4e2d\uff0c\u5c0f\u6570\u70b9\u5206\u9694\u7b26; \u6570\u7ec4\u5206\u9694\u7b26\u548c\u5217\u5206\u9694\u7b26\u5fc5\u987b\u4e0d\u76f8\u540c\u3002';


 }),

 "./src/plugins/formatter/formatter.res.zh.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidCast = exports.Exp_ValueIllegal = exports.Exp_TokenIllegal = exports.Exp_DuplicatedDescriptor = exports.Exp_ValueIsNull = exports.Exp_FormatIllegal = exports.Exp_InvalidBackslash = exports.Exp_TokenIsNull = void 0;
exports.Exp_TokenIsNull = '\u6807\u5fd7\u4e3a\u7a7a\u3002';
exports.Exp_InvalidBackslash = '\u5b57\u7b26 \'\\\' \u65e0\u6cd5\u88ab\u8ba1\u7b97\u3002';
exports.Exp_FormatIllegal = '\u65e0\u6548\u683c\u5f0f\u3002';
exports.Exp_ValueIsNull = '\u6570\u503c\u4e3a\u7a7a';
exports.Exp_DuplicatedDescriptor = '\u91cd\u590d\u7684\u63cf\u8ff0\u88ab\u6dfb\u52a0\u3002';
exports.Exp_TokenIllegal = '\u65e0\u6548\u6807\u5fd7\u3002';
exports.Exp_ValueIllegal = '\u65e0\u6548\u6570\u503c\u3002';
exports.Exp_InvalidCast = '\u65e0\u6548\u8f6c\u6362\u5f02\u5e38';


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_2381__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_2381__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var commonResource = __nested_webpack_require_2381__( "./src/common/util/util.res.zh.ts");
var formatterResource = __nested_webpack_require_2381__( "./src/plugins/formatter/formatter.res.zh.ts");
var resource = commonResource;
for (var prop in formatterResource) {
    if (formatterResource.hasOwnProperty(prop)) {
        resource[prop] = formatterResource[prop];
    }
}
exports.SR = { zh: resource };

}();
((GC = typeof GC === "undefined" ? {} : GC).Spread = GC.Spread || {}).Common = __webpack_exports__;
 })()
;

module.exports = GC.Spread.Common;

/***/ }),

/***/ "./resource.zh.entry.js":
/*!******************************!*\
  !*** ./resource.zh.entry.js ***!
  \******************************/
/***/ (function(module, __unused_webpack_exports, __webpack_require__) {


function extend (to, from1, from2) {
    extend2(to, from1);
    extend2(to, from2);
    return to;
}
function extend2 (to, from) {
    for (var prop in from) {
        if (from.hasOwnProperty(prop)) {
            to[prop] = from[prop];
        }
    }
    return to;
}

GC = GC || {};
GC.Spread = GC.Spread || {};
if ( GC.Spread.Common && GC.Spread.Common.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.zh.js */ "./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.zh.js").SR.zh);
    GC.Spread.Common.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Common = extend2({}, moduleResources);
}
if ( GC.Spread.CalcEngine && GC.Spread.CalcEngine.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.zh.js */ "./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.zh.js").SR.zh);
    GC.Spread.CalcEngine.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].CalcEngine = extend2({}, moduleResources);
}
if ( GC.Data && GC.Data.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.zh.js */ "./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.zh.js").SR.zh);
    GC.Data.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Data = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets && GC.Spread.Sheets.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/core/core.res.zh.js */ "./dist/core/core.res.zh.js");
    GC.Spread.Sheets.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Sheets = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Bindings && GC.Spread.Sheets.Bindings.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/data/data.res.zh.js */ "./dist/plugins/data/data.res.zh.js");
    GC.Spread.Sheets.Bindings.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Bindings = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Outlines && GC.Spread.Sheets.Outlines.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/group/group.res.zh.js */ "./dist/plugins/group/group.res.zh.js");
    GC.Spread.Sheets.Outlines.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Outlines = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.ConditionalFormatting && GC.Spread.Sheets.ConditionalFormatting.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/conditional/conditional.res.zh.js */ "./dist/plugins/conditional/conditional.res.zh.js");
    GC.Spread.Sheets.ConditionalFormatting.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].ConditionalFormatting = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Touch && GC.Spread.Sheets.Touch.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/touch/touch.res.zh.js */ "./dist/plugins/touch/touch.res.zh.js");
    GC.Spread.Sheets.Touch.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Touch = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.FloatingObjects && GC.Spread.Sheets.FloatingObjects.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/floatingObject/floatingobject.res.zh.js */ "./dist/plugins/floatingObject/floatingobject.res.zh.js");
    GC.Spread.Sheets.FloatingObjects.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].FloatingObjects = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.CellTypes && GC.Spread.Sheets.CellTypes.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/celltype/celltypes.res.zh.js */ "./dist/plugins/celltype/celltypes.res.zh.js");
    GC.Spread.Sheets.CellTypes.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].CellTypes = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Filter && GC.Spread.Sheets.Filter.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/filter/filter.res.zh.js */ "./dist/plugins/filter/filter.res.zh.js");
    GC.Spread.Sheets.Filter.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Filter = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Tables && GC.Spread.Sheets.Tables.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/table/table.res.zh.js */ "./dist/plugins/table/table.res.zh.js");
    GC.Spread.Sheets.Tables.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Tables = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Fill && GC.Spread.Sheets.Fill.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/fill/fill.res.zh.js */ "./dist/plugins/fill/fill.res.zh.js");
    GC.Spread.Sheets.Fill.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Fill = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.ContextMenu && GC.Spread.Sheets.ContextMenu.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/contextMenu/context-menu.res.zh.js */ "./dist/plugins/contextMenu/context-menu.res.zh.js");
    GC.Spread.Sheets.ContextMenu.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].ContextMenu = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.OutlineColumn && GC.Spread.Sheets.OutlineColumn.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/outlineColumn/outlineColumn.res.zh.js */ "./dist/plugins/outlineColumn/outlineColumn.res.zh.js");
    GC.Spread.Sheets.OutlineColumn.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].OutlineColumn = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.StatusBar && GC.Spread.Sheets.StatusBar.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/statusBar/statusBar.res.zh.js */ "./dist/plugins/statusBar/statusBar.res.zh.js");
    GC.Spread.Sheets.StatusBar.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].StatusBar = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.AutoMerge && GC.Spread.Sheets.AutoMerge.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/autoMerge/autoMerge.res.zh.js */ "./dist/plugins/autoMerge/autoMerge.res.zh.js");
    GC.Spread.Sheets.AutoMerge.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].AutoMerge = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.InputMask && GC.Spread.Sheets.InputMask.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/inputMask/inputMask/inputMask.res.zh.js */ "./dist/plugins/inputMask/inputMask/inputMask.res.zh.js");
    GC.Spread.Sheets.InputMask.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].InputMask = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Print && GC.Spread.Sheets.Print.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/print/print.res.zh.js */ "./dist/plugins/print/print.res.zh.js");
    GC.Spread.Sheets.Print.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Print = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Charts && GC.Spread.Sheets.Charts.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/chart/chart.res.zh.js */ "./dist/plugins/chart/chart.res.zh.js");
    GC.Spread.Sheets.Charts.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Charts = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Charts && GC.Spread.Sheets.Charts.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/chart/chart.res.zh.js */ "./dist/plugins/chart/chart.res.zh.js");
    GC.Spread.Sheets.Charts.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Charts = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.PDF && GC.Spread.Sheets.PDF.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/exportPDF/printPdf.res.zh.js */ "./dist/plugins/exportPDF/printPdf.res.zh.js");
    GC.Spread.Sheets.PDF.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].PDF = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Shapes && GC.Spread.Sheets.Shapes.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/shape/shape.res.zh.js */ "./dist/plugins/shape/shape.res.zh.js");
    GC.Spread.Sheets.Shapes.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Shapes = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Slicers && GC.Spread.Sheets.Slicers.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/slicer/slicer.res.zh.js */ "./dist/plugins/slicer/slicer.res.zh.js");
    GC.Spread.Sheets.Slicers.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].Slicers = extend2({}, moduleResources);
}
if ( GC.Spread.Pivot && GC.Spread.Pivot.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/pivot/pivot.res.zh.js */ "./dist/plugins/pivot/pivot.res.zh.js");
    GC.Spread.Pivot.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].PivotTables = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.TableSheet && GC.Spread.Sheets.TableSheet.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/tableSheet/tableSheet.res.zh.js */ "./dist/plugins/tableSheet/tableSheet.res.zh.js");
    GC.Spread.Sheets.TableSheet.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].TableSheet = extend2({}, moduleResources);
}
if ( GC.Spread.Report && GC.Spread.Report.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/report/resources/res.zh.js */ "./dist/plugins/report/resources/res.zh.js");
    GC.Spread.Report.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].ReportSheet = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.GanttSheet && GC.Spread.Sheets.GanttSheet.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/gantt/gantt.res.zh.js */ "./dist/plugins/gantt/gantt.res.zh.js");
    GC.Spread.Sheets.GanttSheet.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].GanttSheet = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.IO && GC.Spread.Sheets.IO.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/io/io.res.zh.js */ "./dist/plugins/io/io.res.zh.js");
    GC.Spread.Sheets.IO.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].IO = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.FormulaPanel && GC.Spread.Sheets.FormulaPanel.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/formulaPanel/editor/editor.res.zh.js */ "./dist/plugins/formulaPanel/editor/editor.res.zh.js");
    GC.Spread.Sheets.FormulaPanel.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["zh-cn"]) {
        GC.Spread.Common.CultureManager._resourcesMap["zh-cn"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].FormulaPanel = extend2({}, moduleResources);
}
if ( GC.Pivot && GC.Pivot.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.zh.js */ "./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.zh.js").SR.zh);
    GC.Pivot.SR["zh"] = extend({}, GC.Spread.Common.SR["zh"] || {}, moduleResources);
    GC.Spread.Common.CultureManager._resourcesMap["zh-cn"].PivotEngine = extend2({}, moduleResources);
}
module.exports = GC.Spread;

/***/ }),

/***/ "Common":
/*!****************************!*\
  !*** external "GC.Spread" ***!
  \****************************/
/***/ (function(module) {

"use strict";
module.exports = GC.Spread;

/***/ }),

/***/ "Core":
/*!***********************************!*\
  !*** external "GC.Spread.Sheets" ***!
  \***********************************/
/***/ (function(module) {

"use strict";
module.exports = GC.Spread.Sheets;

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module is referenced by other modules so it can't be inlined
/******/ 	var __webpack_exports__ = __webpack_require__("./resource.zh.entry.js");
/******/ 	(GC = typeof GC === "undefined" ? {} : GC).Spread = __webpack_exports__;
/******/ 	
/******/ })()
;
//# sourceMappingURL=gc.spread.sheets.resources.zh.17.0.5.js.map
}));