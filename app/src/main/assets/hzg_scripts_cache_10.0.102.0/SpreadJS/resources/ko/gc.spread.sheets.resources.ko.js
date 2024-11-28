/*!
 * 
 * SpreadJS Library 17.1.3
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

/***/ "./dist/core/core.res.ko.js":
/*!**********************************!*\
  !*** ./dist/core/core.res.ko.js ***!
  \**********************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";





Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ARIA_Cell = exports.ARIA_Scrollbar_TRACK_Button = exports.ARIA_Scrollbar_Bottom_Button = exports.ARIA_Scrollbar_Right_Button = exports.ARIA_Scrollbar_Thumb_Button = exports.ARIA_Scrollbar_Top_Button = exports.ARIA_Scrollbar_Left_Button = exports.ARIA_Blank = exports.ARIA_NewSheet = exports.ARIA_SheetTab = exports.ARIA_NextButton = exports.ARIA_PreviousButton = exports.ARIA_Last = exports.ARIA_NextArrow = exports.ARIA_PreviousArrow = exports.ARIA_First = exports.ARIA_Resize = exports.NeedCanvasSupport = exports.Exp_OverlappingSpans = exports.Exp_SheetIsNull = exports.Exp_DestSheetIsNull = exports.Exp_ArrayFormulaSpan = exports.Exp_SheetNameConflict = exports.Exp_SheetNameInvalid = exports.Exp_EmptyNamedStyle = exports.NewTab = exports.Tip_Width = exports.Tip_Height = exports.Tip_Column_Offset = exports.Tip_Column = exports.Tip_Row = exports.Exp_PasteChangeMergeCell = exports.Exp_PasteDestinationCellsLocked = exports.Exp_InvalidCopyPasteSize = exports.Exp_PasteSourceCellsLocked = exports.Exp_ArgumentOutOfRange = exports.Exp_NotAFunction = exports.Exp_InvalidRange = exports.Exp_IndexOutOfRange = exports.Exp_CustomNameBothInUse = exports.Exp_InvalidCustomName = exports.Exp_InvalidCustomFunction = exports.Exp_DestIsNull = exports.Exp_SrcIsNull = exports.Exp_InvalidAndSpace = exports.Exp_ChangePartOfArray = exports.Exp_MultipleSelections = exports.Exp_InvalidPastedArea = exports.Exp_PasteExtentIsNull = exports.Exp_NotSupported = void 0;
exports.cancel = exports.ok = exports.pasteSpecialOptionDialog = exports.STEP = exports.Exp_InvalidOperationSizeLimitExceeded = exports.EXP_UNSUPPORT_CRYPTO_ALGORITHM = exports.GENERAL = exports.NORMAL = exports.EXP_INVALID_PASSWORD = exports.EXP_NO_PASSWORD = exports.EXP_FILE_FORMAT = exports.EXP_IO = exports.Exp_InvalidOperationInProtect = exports.Exp_InvalidSortPartTableOrMultiTableInRange = exports.Exp_InvalidSortSpanInRange = exports.Exp_InvalidSortArrayFormulaInRange = exports.Exp_InsertCopiedCutCellsAffectTable = exports.Exp_InsertCopiedCutCellsOverlap = exports.Exp_InsertCopiedCutCellsNoRange = exports.Exp_InsertCopiedCutCellsOnSpanTable = exports.Exp_InsertCopiedCutCells = exports.REPORT_SUMMARY = exports.SHEET_NAME = exports.ARIA_ColumnHeader = exports.ARIA_RowHeader = exports.ARIA_HasComment = exports.ARIA_HasHyperlink = exports.ARIA_HasButton = exports.ARIA_HasCheckbox = exports.ARIA_HasFormula = exports.ARIA_HasValue = void 0;
var Core_1 = __webpack_require__(/*! Core */ "Core");
var Common_1 = __webpack_require__(/*! Common */ "Common");
var elements = (0, Core_1.GC$)('meta[name=\'spreadjs culture\']');
if (elements.length > 0) {
    var culture = (0, Core_1.GC$)(elements[elements.length - 1]).attr('content');
    if (culture !== null && culture !== undefined && culture.toLowerCase() === 'ko-kr') {
        Common_1.Common.CultureManager.culture('ko-kr');
    }
}
exports.Exp_NotSupported = '\uc608\uc678: \uc9c0\uc6d0\ub418\uc9c0 \uc54a\ub294 \uae30\ub2a5\uc758 \uc774\uc6a9\uc744 \uc2dc\ub3c4\ud588\uc2b5\ub2c8\ub2e4.';
exports.Exp_PasteExtentIsNull = 'pasteExtent\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_InvalidPastedArea = '\ubd99\uc5ec\ub123\uc744 \uc601\uc5ed\uc758 \ud06c\uae30\uac00 \ubcf5\uc0ac \ub610\ub294 \uc798\ub77c\ub0b8 \uc601\uc5ed\uacfc \uac19\uc544\uc57c \ud569\ub2c8\ub2e4.';
exports.Exp_MultipleSelections = "\uc774 \uc791\uc5c5\uc740 \uc5ec\ub7ec \uc120\ud0dd \ud56d\ubaa9\uc5d0\uc11c \uc791\ub3d9\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.";
exports.Exp_ChangePartOfArray = '\ubc30\uc5f4\uc758 \uc77c\ubd80\ub97c \ubcc0\uacbd\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_InvalidAndSpace = '\uc798\ubabb\ub41c {0}: {1}({2}\uc5d0\uc11c {3} \uc0ac\uc774\uc5ec\uc57c \ud568).';
exports.Exp_SrcIsNull = '\uc778\uc218 \'src\'\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_DestIsNull = '\uc778\uc218 \'dest\'\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_InvalidCustomFunction = '\uc798\ubabb\ub41c \uc0ac\uc6a9\uc790 \uc9c0\uc815 \ud568\uc218';
exports.Exp_InvalidCustomName = '\uc798\ubabb\ub41c \uc0ac\uc6a9\uc790 \uc9c0\uc815 \uc774\ub984';
exports.Exp_CustomNameBothInUse = '\ud604\uc7ac \uc774\ub984\uacfc \uc0c8 \uc774\ub984 \ubaa8\ub450 \uc140 \uc218\uc2dd\uc5d0\uc11c \ucc38\uc870\ub429\ub2c8\ub2e4';
exports.Exp_IndexOutOfRange = '\uc778\ub371\uc2a4\uac00 \ubc94\uc704\ub97c \ubc97\uc5b4\ub0ac\uc2b5\ub2c8\ub2e4!';
exports.Exp_InvalidRange = '\uc798\ubabb\ub41c \ubc94\uc704';
exports.Exp_NotAFunction = 'A \ud568\uc218\uac00 \uc544\ub2d8';
exports.Exp_ArgumentOutOfRange = '\ubc94\uc704\uc678\uc758 \uc778\uc218';
exports.Exp_PasteSourceCellsLocked = '\uc6d0\ubcf8 \uc2dc\ud2b8\uc758 \uc140\uc774 \uc7a0\uaca8 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_InvalidCopyPasteSize = '\ubcf5\uc0ac \uc601\uc5ed\uacfc \ubd99\uc5ec\ub123\uae30 \uc601\uc5ed\uc758 \ud06c\uae30\uac00 \uac19\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.';
exports.Exp_PasteDestinationCellsLocked = '\ubcc0\uacbd\ud558\ub824\ub294 \uc140\uc740 \ubcf4\ud638\ub418\uc5b4 \uc788\uc73c\ubbc0\ub85c \uc77d\uae30 \uc804\uc6a9\uc785\ub2c8\ub2e4.';
exports.Exp_PasteChangeMergeCell = '\ubcd1\ud569\ub41c \uc140\uc758 \uc77c\ubd80\ub97c \ubcc0\uacbd\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Tip_Row = '\ud589: ';
exports.Tip_Column = '\uc5f4: ';
exports.Tip_Column_Offset = '\uc624\ud504\uc14b: ';
exports.Tip_Height = '\ub192\uc774: {0}\ud53d\uc140';
exports.Tip_Width = '\ub108\ube44: {0}\ud53d\uc140';
exports.NewTab = '\uc0c8\ub85c \ub9cc\ub4e4\uae30...';
exports.Exp_EmptyNamedStyle = '\uba85\uba85\ub41c \uc2a4\ud0c0\uc77c\uc758 \uc774\ub984\uc740 \ube44\uc6cc \ub450\uac70\ub098 null\ub85c \uc124\uc815\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_SheetNameInvalid = '\uc2dc\ud2b8 \uc774\ub984\uc740 \ube44\uc6cc \ub450\uac70\ub098 *, :, [, ], ?, \\, / \ubb38\uc790\ub97c \ud3ec\ud568\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_SheetNameConflict = '\uc774 \uc774\ub984 \uc740 \uc774\ubbf8 \uc0ac\uc6a9 \ub418 \uc5c8 \uc2b5 \ub2c8 \ub2e4. \ub2e4\ub978 \uc774\ub984 \uc744 \uc2dc\ub3c4 \ud574 \ubcf4\uc2ed\uc2dc\uc624.';
exports.Exp_ArrayFormulaSpan = '\ubc30\uc5f4 \uc218\uc2dd\uc740 \ubcd1\ud569\ub41c \uc140\uc5d0 \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_DestSheetIsNull = 'destSheet\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_SheetIsNull = '\uc2dc\ud2b8\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_OverlappingSpans = '\uc774 \uc791\uc5c5\uc73c\ub85c \ubc94\uc704\uac00 \uacb9\uce60 \uc218 \uc788\uc2b5\ub2c8\ub2e4.';
exports.NeedCanvasSupport = 'SpreadJS\ub97c \uc2e4\ud589\ud558\ub824\uba74 HTML5 Canvas\ub97c \uc644\ubcbd\ud558\uac8c \uc9c0\uc6d0\ud558\ub294 \ube0c\ub77c\uc6b0\uc800\uac00 \ud544\uc694\ud569\ub2c8\ub2e4.';
exports.ARIA_Resize = '\ud06c\uae30 \uc870\uc815';
exports.ARIA_First = '\uccab \ubc88\uc9f8';
exports.ARIA_PreviousArrow = '\uc774\uc804 \ud654\uc0b4\ud45c';
exports.ARIA_NextArrow = '\ub2e4\uc74c \ud654\uc0b4\ud45c';
exports.ARIA_Last = '\ub9c8\uc9c0\ub9c9';
exports.ARIA_PreviousButton = '\uc774\uc804 \ubc84\ud2bc';
exports.ARIA_NextButton = '\ub2e4\uc74c \ubc84\ud2bc';
exports.ARIA_SheetTab = '\uc2dc\ud2b8 \ud0ed';
exports.ARIA_NewSheet = '\uc0c8 \uc2dc\ud2b8';
exports.ARIA_Blank = '\uacf5\ubc31';
exports.ARIA_Scrollbar_Left_Button = '\uc2a4\ud06c\ub864 \ub9c9\ub300 \uc67c\ucabd \ubc84\ud2bc';
exports.ARIA_Scrollbar_Top_Button = '\uc2a4\ud06c\ub864 \ub9c9\ub300 \uc704\ucabd \ubc84\ud2bc';
exports.ARIA_Scrollbar_Thumb_Button = '\uc2a4\ud06c\ub864 \ub9c9\ub300 \ucd95\uc18c\ud310 \uadf8\ub9bc \ubc84\ud2bc';
exports.ARIA_Scrollbar_Right_Button = '\uc2a4\ud06c\ub864 \ub9c9\ub300 \uc624\ub978\ucabd \ubc84\ud2bc';
exports.ARIA_Scrollbar_Bottom_Button = '\uc2a4\ud06c\ub864 \ub9c9\ub300 \uc544\ub798\ucabd \ubc84\ud2bc';
exports.ARIA_Scrollbar_TRACK_Button = '\uc2a4\ud06c\ub864 \ub9c9\ub300 \ud2b8\ub799 \ubc84\ud2bc';
exports.ARIA_Cell = '\uc140';
exports.ARIA_HasValue = '\uac12 {0} \uc788\uc74c';
exports.ARIA_HasFormula = '\ud568\uc218 {0} \uc788\uc74c';
exports.ARIA_HasCheckbox = '\uccb4\ud06c \ubc15\uc2a4 {0} \uc788\uc74c';
exports.ARIA_HasButton = '\ubc84\ud2bc {0} \uc788\uc74c';
exports.ARIA_HasHyperlink = '\ud558\uc774\ud37c\ub9c1\ud06c {0} \uc788\uc74c';
exports.ARIA_HasComment = '\uba54\ubaa8 {0} \uc788\uc74c';
exports.ARIA_RowHeader = '\ud589 \uba38\ub9ac\uae00';
exports.ARIA_ColumnHeader = '\uc5f4 \uba38\ub9ac\uae00';
exports.SHEET_NAME = 'Sheet';
exports.REPORT_SUMMARY = 'Sum';

var lr = __webpack_require__(/*! lrKo */ "./dist/core/lc/lr.ko.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}
exports.Exp_InsertCopiedCutCells = '\ubcf5\uc0ac\uc640 \ubd99\uc5ec\ub123\uae30 \uc601\uc5ed\uc774 \uacb9\uce58\uc9c0 \uc54a\ub3c4\ub85d \ubcf4\uc7a5\ud569\ub2c8\ub2e4.';
exports.Exp_InsertCopiedCutCellsOnSpanTable = '\uc6cc\ud06c\uc2dc\ud2b8\uc758 \ud45c\uc5d0\uc11c \uc140\uc744 \uc774\ub3d9 \uc2dc\ud0a4\uac70\ub098 \ubcd1\ud569 \ub41c \uc77c\ubd80 \uc140\uc774 \ubcd1\ud569 \ud574\uc81c\ub418\uae30 \ub54c\ubb38\uc5d0 \uc791\ub3d9\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.';
exports.Exp_InsertCopiedCutCellsNoRange = 'SpreadJS\ub294 \ube44\ube48 \uc140\uc744 \uc2dc\ud2b8\uc758 \ub05d\uc5d0\uc11c \ubc00\uc5b4\ub0b4\uae30 \ub54c\ubb38\uc5d0 \uc0c8\ub85c\uc6b4 \uc140\uc744 \uc0bd\uc785\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_InsertCopiedCutCellsOverlap = "\uc120\ud0dd \ubc94\uc704\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4. \ubcf5\uc0ac \uc601\uc5ed\uacfc \ubd99\uc5ec\ub123\uae30 \uc601\uc5ed\uc758 \ud06c\uae30\uc640 \ubaa8\uc591\uc774 \uac19\uc544\uc57c\ub9cc \uacb9\uce60 \uc218 \uc788\uc2b5\ub2c8\ub2e4.";
exports.Exp_InsertCopiedCutCellsAffectTable = "\uc608\uae30\uce58 \uc54a\uc740 \ubc29\uc2dd\uc73c\ub85c \ub2e4\ub978 \ud45c \uc140\uc5d0 \uc601\ud5a5\uc744 \uc904 \uc218 \uc788\uae30 \ub54c\ubb38\uc5d0 \uc774 \ubc29\uc2dd\uc73c\ub85c \ud45c \uc548\uc5d0\uc11c \uc140\uc744 \ub2e4\uc2dc \uc815\ub82c\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.Exp_InvalidSortArrayFormulaInRange = '\ubc30\uc5f4 \uc218\uc2dd\uc774 \uc874\uc7ac\ud558\ubbc0\ub85c \ud604\uc7ac \ubc94\uc704\ub97c \uc815\ub82c \ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_InvalidSortSpanInRange = '\ubc94\uc704\uac00 \uc874\uc7ac\ud558\uc5ec \ud604\uc7ac \ubc94\uc704\ub97c \uc815\ub82c \ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_InvalidSortPartTableOrMultiTableInRange = '\ubd80\ud488 \ud14c\uc774\ube14 \ub610\ub294 \ub458 \uc774\uc0c1\uc758 \ud14c\uc774\ube14\uc774 \uc874\uc7ac\ud558\uc5ec \ud604\uc7ac \ubc94\uc704\ub97c \uc815\ub82c \ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_InvalidOperationInProtect = "\ubcc0\uacbd\ud558\ub824\ub294 \uc140 \ub610\ub294 \ucc28\ud2b8\uac00 \ubcf4\ud638\ub41c \uc2dc\ud2b8\uc5d0 \uc788\uc2b5\ub2c8\ub2e4. \ubcc0\uacbd\ud558\ub824\uba74 \uc2dc\ud2b8\uc758 \ubcf4\ud638\ub97c \ud574\uc81c\ud558\uc138\uc694.";
exports.EXP_IO = '\ud30c\uc77c \uc77d\uae30 \ubc0f \uc4f0\uae30 \uc608\uc678';
exports.EXP_FILE_FORMAT = '\uc798\ubabb\ub41c \ud30c\uc77c \ud615\uc2dd';
exports.EXP_NO_PASSWORD = '\uc6cc\ud06c\ubd81/\uc6cc\ud06c\uc2dc\ud2b8\uac00 \uc554\ud638\ub85c \ubcf4\ud638\ub418\uc5b4 \uc788\uc73c\ubbc0\ub85c Excel \ud30c\uc77c\uc744 \uc5f4 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.EXP_INVALID_PASSWORD = '\uc9c0\uc815\ud55c \uc554\ud638\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.NORMAL = '\ubcf4\ud1b5';
exports.GENERAL = '\uc77c\ubc18';
exports.EXP_UNSUPPORT_CRYPTO_ALGORITHM = '\uc9c0\uc6d0\ub418\uc9c0 \uc54a\ub294 \uc554\ud638\ud654 \uc54c\uace0\ub9ac\uc998\uc785\ub2c8\ub2e4.';
exports.Exp_InvalidOperationSizeLimitExceeded = "\ud30c\uc77c \ud06c\uae30\uac00 \uc124\uc815\ub41c \ucd5c\ub300 \ud06c\uae30 \uc81c\ud55c\uc744 \ucd08\uacfc\ud588\uc2b5\ub2c8\ub2e4.";
exports.STEP = {
    start: "{0} \ub85c\ub4dc\ub97c \uc2dc\uc791\ud569\ub2c8\ub2e4.",
    loadFileData: "{0}\uc758 \ub370\uc774\ud130\ub97c \uc2a4\ud504\ub808\ub4dc\uc5d0 \ub85c\ub4dc\ud569\ub2c8\ub2e4.",
    loadSheetData: "{0}\uc758 \ub370\uc774\ud130\ub97c \ubd88\ub7ec\uc635\ub2c8\ub2e4.",
    startCalc: "\uc7ac\uacc4\uc0b0\uc744 \uc2dc\uc791\ud569\ub2c8\ub2e4.",
    loadSheetFormula: "{0}\uc758 \uc218\uc2dd\uc744 \ub85c\ub4dc\ud569\ub2c8\ub2e4.",
    parseFile: "{0} \ub0b4\uc6a9\uc744 \ud30c\uc2f1\ud569\ub2c8\ub2e4."
};
exports.pasteSpecialOptionDialog = {
    title: "\ud2b9\uc218 \ubd99\uc5ec\ub123\uae30",
    paste: "\ubd99\uc5ec\ub123\uae30",
    pasteOptions: {
        all: "\ubaa8\ub450",
        formulas: "\uc218\uc2dd",
        values: "\uac12",
        formats: "\uc11c\uc2dd",
        comments: "\uba54\ubaa8",
        validation: "\uc720\ud6a8\uc131 \uac80\uc0ac",
        usingSourceTheme: "\ubaa8\ub4e0 \uc6d0\ubcf8 \ud14c\ub9c8 \uc0ac\uc6a9",
        noBorders: "\ud14c\ub450\ub9ac\ub97c \uc81c\uc678\ud55c \ubaa8\ub4e0 \ud56d\ubaa9",
        columnWidth: "\uc5f4 \ub108\ube44",
        formulasAndNumberFormats: "\uc218\uc2dd \ubc0f \uc22b\uc790 \uc11c\uc2dd",
        valueAndNumberFormats: "\uac12 \ubc0f \uc22b\uc790 \uc11c\uc2dd",
    },
    operation: "\uc791\uc5c5",
    operationOptions: {
        none: "\uc5c6\uc74c",
        add: "\ub354\ud558\uae30",
        subtract: "\ube7c\uae30",
        multiply: "\uacf1\ud558\uae30",
        divide: "\ub098\ub204\uae30"
    },
    skipBlanks: "\ube48\uce78 \uc0dd\ub7b5",
    transpose: "\ud589/\uc5f4 \ubc14\uafb8\uae30",
    pasteLink: "\uc5f0\uacb0\ud558\uc5ec \ubd99\uc5ec\ub123\uae30"
};
exports.ok = "\ud655\uc778";
exports.cancel = "\ucde8\uc18c";


/***/ }),

/***/ "./dist/core/lc/lr.ko.js":
/*!*******************************!*\
  !*** ./dist/core/lc/lr.ko.js ***!
  \*******************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ls11 = exports.ls10 = exports.ls9 = exports.ls8 = exports.ls7 = exports.ls6 = exports.ls5 = exports.ls4 = exports.ls3 = exports.ls2 = exports.ls1 = void 0;

exports.ls1 = ['0000000000000000000000000000000000000000000000000000000000000000c700ccd5d3c700b8cec5b900bcd3d500c200c7c2b2b2000000c7c200bcd300d0b200d1c2d2c6c7b800c8acb4b2b20000000000000000000000000000000000000000000000000000c7b800c7bac7c700bcb000c8c2c2c600',
    '506f776572656420627920477261706543697479205370726561644a532e0d0a7420b4d81040205cecd0cc2030ec6020182088b5c8e42e0d0a84dc2030ec20a494204ca4b8a93c5c201cf529c8e42e0d0a73616c65732d6b6f72406772617065636974792e636f6d3c5c2074547c4420f4b420fceddc242e'];

exports.ls2 = ['0000000000000000000000000000000000000000000000000000000000000000c7c200bcd300d0ac00000000c700d6c500b9b8b4b2b2',
    '506f776572656420627920477261706543697479205370726561644a532e0d0a84dc2030ec20a400207b307d7c20c4d020cccc29c8e4'];

exports.ls3 = ['b7c7c1c2b900ccc700c200c5c700000000000000000000b900c2d5d5b8ba00c7d6d500b7c7c1c200d0ac00d5c6d5b2b2000000c7c200d0b200ccd5c6c7b800c8acb4b2b2000000b7c7c1c2b900adc7d500acc600adb900d6c700c7bac7c5c100d0b900d6c7d500c200c7c2b2b2000000b3c6c700d5c6d5b2ba000000000000000000000000000000000000000000000000c7b800c7bac7c700bcb000c8c2c2c600',
    '7c7420a47c203e44201820c64c0d0a5370726561644a537c20e4895824742020a85c207c7420a420a40020449469c8e42e0d0a84dc20a49420b4d8a93c5c201cf529c8e42e0d0a7c7420a47c206c855c20bdb0206ce42055782074547cd01c20a47c2055786020182088b5c8e42e0d0ac4c07420449458e4742073616c65732d6b6f72406772617065636974792e636f6d3c5c2074547c4420f4b420fceddc242e'];

exports.ls4 = ['c7bab400b7c7c1c200d000b3c6c700d5c6d5b2ba000000000000000000000000000000000000000000000000c7b800c7bac7c700bcb000c8c2c2c600',
    '98bb1c207c7420a420a40ac4c07420449458e4742073616c65732d6b6f72406772617065636974792e636f6d3c5c2074547c4420f4b420fceddc242e'];

exports.ls5 = ['0000000000000000000000000000000000000000000000000000000000000000c7c200bcd300d0ac00b9b8b4c5c2b2b2000000b3c6c700d5c6d5b2ba000000000000000000000000000000000000000000000000c7b800c7bac7c700bcb000c8c2c2c600',
    '506f776572656420627920477261706543697479205370726561644a532e0d0a84dc2030ec20a40020cccc18c8b5c8e42e0d0ac4c07420449458e4742073616c65732d6b6f72406772617065636974792e636f6d3c5c2074547c4420f4b420fceddc242e'];
exports.ls6 = ['c8ac000000000000000000000000000000000000000000ccd5d30000bcd3c6c7b800d5acb4c900c5c7',
    '1cf53a20477261706543697479205370726561644a5320b4d8100d0a30eca93c5c20c80018c0204a4c'];

exports.ls7 = ['b7c7c1c2b900ccc700c200c5c700000000000000000000b900c2d5d5b8ba00c7d6d500b7c7c1c200d0ac00d5c6d5b2b2000000c7c200d0b200ccd5c6c7b800c8acb4b2b2000000b7c7c1c2b900adc7d500acc600adb900d6c700c7bac7c5c100d0b900d6c7d500c200c7c2b2b2000000b3c6c700d5c6d5b2ba000000000000000000000000000000000000000000000000c7b800c7bac7c700bcb000c8c2c2c600',
    '7c7420a47c203e44201820c64c0d0a5370726561644a537c20e4895824742020a85c207c7420a420a40020449469c8e42e0d0a84dc20a49420b4d8a93c5c201cf529c8e42e0d0a7c7420a47c206c855c20bdb0206ce42055782074547cd01c20a47c2055786020182088b5c8e42e0d0ac4c07420449458e4742073616c65732d6b6f72406772617065636974792e636f6d3c5c2074547c4420f4b420fceddc242e'];

exports.ls8 = ['c7bab400b7c7c1c200d000b3c6c700d5c6d5b2ba000000000000000000000000000000000000000000000000c7b800c7bac7c700bcb000c8c2c2c600',
    '98bb1c207c7420a420a40ac4c07420449458e4742073616c65732d6b6f72406772617065636974792e636f6d3c5c2074547c4420f4b420fceddc242e'];

exports.ls9 = ["000000c700c0c6c700acb2d500b7c7c1c2c7b2b200", "7b307d5820aca9742000a55c207c7420a485c8e42e"];

exports.ls10 = ["000000c700c0c6c700bdacb2d500b7c7c1c2c7b2b200", "7b307d5820aca974208800a55c207c7420a485c8e42e"];

exports.ls11 = ["", "2c"];


/***/ }),

/***/ "./dist/plugins/autoMerge/autoMerge.res.ko.js":
/*!****************************************************!*\
  !*** ./dist/plugins/autoMerge/autoMerge.res.ko.js ***!
  \****************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_RangeIsIntersected = void 0;
exports.Exp_RangeIsIntersected = "\ubc94\uc704\ub294 \uae30\uc874 \ubc94\uc704\uc640 \uad50\ucc28\ud574\uc11c\ub294 \uc548 \ub429\ub2c8\ub2e4.";


/***/ }),

/***/ "./dist/plugins/celltype/celltypes.res.ko.js":
/*!***************************************************!*\
  !*** ./dist/plugins/celltype/celltypes.res.ko.js ***!
  \***************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Calendar_BuiltIn_Yesterday = exports.Calendar_BuiltIn_Today = exports.Calendar_Today = exports.Calendar_FirstYear = exports.Calendar_EraName_4 = exports.Calendar_EraName_3 = exports.Calendar_EraName_2 = exports.Calendar_EraName_1 = exports.Calendar_Time_PM = exports.Calendar_Time_AM = exports.Calendar_Months_12 = exports.Calendar_Months_11 = exports.Calendar_Months_10 = exports.Calendar_Months_9 = exports.Calendar_Months_8 = exports.Calendar_Months_7 = exports.Calendar_Months_6 = exports.Calendar_Months_5 = exports.Calendar_Months_4 = exports.Calendar_Months_3 = exports.Calendar_Months_2 = exports.Calendar_Months_1 = exports.Calendar_ShortMonths_12 = exports.Calendar_ShortMonths_11 = exports.Calendar_ShortMonths_10 = exports.Calendar_ShortMonths_9 = exports.Calendar_ShortMonths_8 = exports.Calendar_ShortMonths_7 = exports.Calendar_ShortMonths_6 = exports.Calendar_ShortMonths_5 = exports.Calendar_ShortMonths_4 = exports.Calendar_ShortMonths_3 = exports.Calendar_ShortMonths_2 = exports.Calendar_ShortMonths_1 = exports.Calendar_Weeks_7 = exports.Calendar_Weeks_6 = exports.Calendar_Weeks_5 = exports.Calendar_Weeks_4 = exports.Calendar_Weeks_3 = exports.Calendar_Weeks_2 = exports.Calendar_Weeks_1 = exports.Calendar_ShortWeeks_7 = exports.Calendar_ShortWeeks_6 = exports.Calendar_ShortWeeks_5 = exports.Calendar_ShortWeeks_4 = exports.Calendar_ShortWeeks_3 = exports.Calendar_ShortWeeks_2 = exports.Calendar_ShortWeeks_1 = exports.Cancel = exports.OK = void 0;
exports.FileUpload_Title = exports.DataObject_MeetTable = exports.DataObject_MeetSpan = exports.DataObject_MeetLock = exports.MultiColumn_InvalidDataSource = exports.Calculator_OverFlowInfo = exports.Calculator_SqrtParameterException = exports.Calculator_InvalidInputInfo = exports.Calculator_DivideByZeroInfo = exports.StandardColor = exports.ThemeColor = exports.Quarter_Format_4 = exports.Quarter_Format_3 = exports.Quarter_Format_2 = exports.Quarter_Format_1 = exports.Calendar_NextTenYear = exports.Calendar_LastTenYear = exports.Calendar_NextYear = exports.Calendar_LastYear = exports.Calendar_NextMonth = exports.Calendar_LastMonth = exports.Calendar_BuiltIn_LastMonth = exports.Calendar_BuiltIn_ThisMonth = exports.Calendar_BuiltIn_Last7Days = void 0;
exports.OK = '\ud655\uc778';
exports.Cancel = '\ucde8\uc18c';
exports.Calendar_ShortWeeks_1 = "\uc6d4";
exports.Calendar_ShortWeeks_2 = "\ud654";
exports.Calendar_ShortWeeks_3 = "\uc218";
exports.Calendar_ShortWeeks_4 = "\ubaa9";
exports.Calendar_ShortWeeks_5 = "\uae08";
exports.Calendar_ShortWeeks_6 = "\ud1a0";
exports.Calendar_ShortWeeks_7 = "\uc77c";
exports.Calendar_Weeks_1 = "\uc6d4\uc694\uc77c";
exports.Calendar_Weeks_2 = "\ud654\uc694\uc77c";
exports.Calendar_Weeks_3 = "\uc218\uc694\uc77c";
exports.Calendar_Weeks_4 = "\ubaa9\uc694\uc77c";
exports.Calendar_Weeks_5 = "\uae08\uc694\uc77c";
exports.Calendar_Weeks_6 = "\ud1a0\uc694\uc77c";
exports.Calendar_Weeks_7 = "\uc77c\uc694\uc77c";
exports.Calendar_ShortMonths_1 = "1\uc6d4";
exports.Calendar_ShortMonths_2 = "2\uc6d4";
exports.Calendar_ShortMonths_3 = "3\uc6d4";
exports.Calendar_ShortMonths_4 = "4\uc6d4";
exports.Calendar_ShortMonths_5 = "5\uc6d4";
exports.Calendar_ShortMonths_6 = "6\uc6d4";
exports.Calendar_ShortMonths_7 = "7\uc6d4";
exports.Calendar_ShortMonths_8 = "8\uc6d4";
exports.Calendar_ShortMonths_9 = "9\uc6d4";
exports.Calendar_ShortMonths_10 = "10\uc6d4";
exports.Calendar_ShortMonths_11 = "11\uc6d4";
exports.Calendar_ShortMonths_12 = "12\uc6d4";
exports.Calendar_Months_1 = "1 \uc6d4";
exports.Calendar_Months_2 = "2 \uc6d4";
exports.Calendar_Months_3 = "3 \uc6d4";
exports.Calendar_Months_4 = "4 \uc6d4";
exports.Calendar_Months_5 = "5 \uc6d4";
exports.Calendar_Months_6 = "6 \uc6d4";
exports.Calendar_Months_7 = "7 \uc6d4";
exports.Calendar_Months_8 = "8 \uc6d4";
exports.Calendar_Months_9 = "9 \uc6d4";
exports.Calendar_Months_10 = "10 \uc6d4";
exports.Calendar_Months_11 = "11 \uc6d4";
exports.Calendar_Months_12 = "12 \uc6d4";
exports.Calendar_Time_AM = "\uc624\uc804";
exports.Calendar_Time_PM = "\uc624\ud6c4";
exports.Calendar_EraName_1 = "\uba54\uc774\uc9c0";
exports.Calendar_EraName_2 = "\ub2e4\uc774\uc1fc";
exports.Calendar_EraName_3 = "\uc1fc\uc640";
exports.Calendar_EraName_4 = "\ud5e4\uc774\uc138\uc774";
exports.Calendar_FirstYear = "1";
exports.Calendar_Today = "\uc624\ub298";
exports.Calendar_BuiltIn_Today = "\uc624\ub298";
exports.Calendar_BuiltIn_Yesterday = "\uc5b4\uc81c";
exports.Calendar_BuiltIn_Last7Days = "\uc9c0\ub09c 7\uc77c";
exports.Calendar_BuiltIn_ThisMonth = "\uc774\ubc88 \ub2ec";
exports.Calendar_BuiltIn_LastMonth = "\uc9c0\ub09c \ub2ec";
exports.Calendar_LastMonth = "\uc774\uc804 \ub2ec\ub85c \uc774\ub3d9";
exports.Calendar_NextMonth = "\ub2e4\uc74c \ub2ec\ub85c \uc774\ub3d9";
exports.Calendar_LastYear = "\uc804\ub144\ub3c4\ub85c \ub3cc\uc544 \uac00\uae30";
exports.Calendar_NextYear = "\ub2e4\uc74c\ub144\ub3c4\ub85c \uac00\uae30";
exports.Calendar_LastTenYear = "\uc9c0\ub09c 10 \ub144\uc73c\ub85c \uc774\ub3d9";
exports.Calendar_NextTenYear = "\ud5a5\ud6c4 10 \ub144\uc73c\ub85c \uc774\ub3d9";
exports.Quarter_Format_1 = "\uc81c1\ubd84\uae30";
exports.Quarter_Format_2 = "\uc81c2\ubd84\uae30";
exports.Quarter_Format_3 = "\uc81c3\ubd84\uae30";
exports.Quarter_Format_4 = "\uc81c4\ubd84\uae30";
exports.ThemeColor = "\ud14c\ub9c8 \uc0c9\uc0c1";
exports.StandardColor = "\ud45c\uc900 \uc0c9\uc0c1";
exports.Calculator_DivideByZeroInfo = "0\uc73c\ub85c \ub098\ub20c \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.Calculator_InvalidInputInfo = "\uc785\ub825\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.";
exports.Calculator_SqrtParameterException = "\ud568\uc218\uc5d0 \ub300\ud55c \uc785\ub825\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.";
exports.Calculator_OverFlowInfo = "\uc0b0\uc220 \uc5f0\uc0b0\uc73c\ub85c \uc778\ud574 \uc624\ubc84\ud50c\ub85c\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.";
exports.MultiColumn_InvalidDataSource = "\uc798\ubabb\ub41c \ub370\uc774\ud130 \uc18c\uc2a4\uc785\ub2c8\ub2e4. \uc218\uc2dd\uc774 \ubc30\uc5f4\uc744 \ubc18\ud658\ud574\uc57c \ud569\ub2c8\ub2e4.";
exports.DataObject_MeetLock = "\uc140\uc774 \uc7a0\uaca8 \uc788\uae30 \ub54c\ubb38\uc5d0 \ub370\uc774\ud130\ub97c \uc0bd\uc785\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.DataObject_MeetSpan = "\ubcd1\ud569\ub41c \uc140\uc774 \uc788\uae30 \ub54c\ubb38\uc5d0 \ub370\uc774\ud130\ub97c \uc0bd\uc785\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.DataObject_MeetTable = "\ud45c\uac00 \uc788\uae30 \ub54c\ubb38\uc5d0 \ub370\uc774\ud130\ub97c \uc0bd\uc785\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.FileUpload_Title = "+ \uc5c5\ub85c\ub4dc";


/***/ }),

/***/ "./dist/plugins/chart/chart.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/chart/chart.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.AxisTitle = exports.Branch = exports.ChartTitle = exports.TOTAL = exports.DECREASE = exports.INCREASE = exports.SIZE = exports.POINT = exports.VALUE = exports.SERIES = exports.unsupportedChartType = exports.power = exports.polynomial = exports.logarithmic = exports.linear = exports.exponential = exports.movingAverage = exports.period = void 0;
exports.period = '\uae30\uac04';
exports.movingAverage = '\uc774\ub3d9 \ud3c9\uade0';
exports.exponential = '\uc9c0\uc218';
exports.linear = '\uc120\ud615';
exports.logarithmic = '\ub85c\uadf8';
exports.polynomial = '\ub2e4\ud56d\uc2dd';
exports.power = '\uac70\ub4ed\uc81c\uacf1';
exports.unsupportedChartType = '\uc9c0\uc6d0\ub418\uc9c0 \uc54a\ub294 \ucc28\ud2b8 \uc720\ud615';
exports.SERIES = "\uc2dc\ub9ac\uc988";
exports.VALUE = "\uac00\uce58: ";
exports.POINT = "\uc9c0\ud5a5 ";
exports.SIZE = "\ud06c\uae30: ";
exports.INCREASE = "\uc99d\uac00";
exports.DECREASE = "\uac10\uc18c";
exports.TOTAL = "\uc804\ubd80\uc758";
exports.ChartTitle = "\ucc28\ud2b8 \uc81c\ubaa9";
exports.Branch = "\ub098\ubb47\uac00\uc9c0";
exports.AxisTitle = "\uc88c\ud45c\ucd95 \uc81c\ubaa9";


/***/ }),

/***/ "./dist/plugins/conditional/conditional.res.ko.js":
/*!********************************************************!*\
  !*** ./dist/plugins/conditional/conditional.res.ko.js ***!
  \********************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_NotSupported = exports.Exp_RuleIsNull = void 0;
exports.Exp_RuleIsNull = '\uc778\uc218 \'rule\'\uc774 null\uc785\ub2c8\ub2e4.';
exports.Exp_NotSupported = '\uc608\uc678: \uc9c0\uc6d0\ub418\uc9c0 \uc54a\ub294 \uae30\ub2a5\uc758 \uc774\uc6a9\uc744 \uc2dc\ub3c4\ud588\uc2b5\ub2c8\ub2e4.';


/***/ }),

/***/ "./dist/plugins/contextMenu/context-menu.res.ko.js":
/*!*********************************************************!*\
  !*** ./dist/plugins/contextMenu/context-menu.res.ko.js ***!
  \*********************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.tableDeleteColumns = exports.tableDeleteOrInsertEntireSheetRow = exports.tableDeleteRows = exports.tableDelete = exports.tableInsertColumnsRight = exports.tableInsertColumnsLeft = exports.tableInsertRowsBelow = exports.tableInsertRowsAbove = exports.tableInsert = exports.removeFloatingObject = exports.removeSlicer = exports.toggleComment = exports.deleteComment = exports.editComment = exports.unhideRows = exports.unhideColumns = exports.unhideSheet = exports.hideSheet = exports.hideColumns = exports.hideRows = exports.sortDescend = exports.sortAscend = exports.slicerSortDescend = exports.slicerSortAscend = exports.headerInsertCutCells = exports.headerInsertCopiedCells = exports.shiftCellsDown = exports.shiftCellsRight = exports.insertCutCells = exports.insertCopiedCells = exports.sort = exports.filter = exports.insertComment = exports.deleteSheet = exports.insertSheet = exports.deleteColumns = exports.deleteRows = exports.insertColumns = exports.insertRows = exports.clearContents = exports.pasteSpecial = exports.pasteFormulaFormatting = exports.pasteValuesFormatting = exports.pasteFormatting = exports.pasteValues = exports.pasteFormula = exports.pasteAll = exports.pasteOptions = exports.cut = exports.copy = void 0;
exports.pivotTableExpand = exports.pivotTableValueFilters = exports.pivotTableLabelFilters_Date = exports.pivotTableLabelFilters = exports.pivotTableTop10 = exports.pivotTableHideSelectedItems = exports.pivotTableKeepOnlySelectedItems = exports.pivotTableClearFilterFrom_ = exports.pivotTableFieldSettings = exports.pivotTableHideFieldList = exports.pivotTableSubtotal_ = exports.pivotTableFilter = exports.pivotTableExpandOrCollapse = exports.pivotTableUnGroup = exports.pivotTableGroup = exports.pivotTableShowDetails = exports.pivotTableIndex = exports.pivotTableRankLargestToSmallest = exports.pivotTableRankSmallestToLargest = exports.pivotTablePercentRunningTotalIn = exports.pivotTableRunningTotalIN = exports.pivotTablePercentDifferenceFrom = exports.pivotTableDifferenceFrom = exports.pivotTableOfParentTotal = exports.pivotTableParentColumnTotal = exports.pivotTableParentRowTotal = exports.pivotTableOf = exports.pivotTableRowTotal = exports.pivotTableColumnTotal = exports.pivotTableGrandTotal = exports.pivotTableNoCalculation = exports.pivotTableShowValueAs = exports.pivotTableMoreOptions = exports.pivotTableDistinctCount = exports.pivotTableProduct = exports.pivotTableMin = exports.pivotTableMax = exports.pivotTableAverage = exports.pivotTableCount = exports.pivotTableSum = exports.pivotTableSummarizeValuesBy = exports.pivotTableSort = exports.pivotTableNumberFormat = exports.pivotTableOptions = exports.pivotTableValueFieldSettings = exports.pivotTableRemoveGrandTotal = exports.pivotTableRemove_ = exports.pivotTableMove = exports.pivotTableRefresh = exports.hideSheetFailureInfo = void 0;
exports.expandLevel2 = exports.expandLevel1 = exports.collapseAllLevels = exports.expandAllLevels = exports.reportSheetResetCellValue = exports.reportSheetSubmit = exports.reportSheetDeleteRecord = exports.reportSheetAddRecord = exports.showOutlineLevel9 = exports.showOutlineLevel8 = exports.showOutlineLevel7 = exports.showOutlineLevel6 = exports.showOutlineLevel5 = exports.showOutlineLevel4 = exports.showOutlineLevel3 = exports.showOutlineLevel2 = exports.showOutlineLevel1 = exports.hideAllOutlineLevel = exports.showAllOutlineLevel = exports.addBelow = exports.addAbove = exports.addBefore = exports.addAfter = exports.moveDown = exports.moveUp = exports.demote = exports.promote = exports.setPrimaryKey = exports.removeColumn = exports.modifyColumn = exports.defineColumn = exports.pinColumns = exports.pinRows = exports.pivotTableSortMoreOptions = exports.pivotTableSortDescend = exports.pivotTableSortAscend = exports.sigmaValueTemp = exports.sigmaValue = exports.pivotTableMove_ToColumns = exports.pivotTableMove_ToRight = exports.pivotTableMove_ToLeft = exports.pivotTableMove_ToEnd = exports.pivotTableMove_Down = exports.pivotTableMove_Up = exports.pivotTableMove_ToBeginning = exports.pivotTableExpandTo_ = exports.pivotTableCollapseTo_ = exports.pivotTableCollapseEntireField = exports.pivotTableExpandEntireField = exports.pivotTableCollapse = void 0;
exports.unhideColumn = exports.hideColumn = exports.autoSchedule = exports.manuallySchedule = exports.outdentTasks = exports.indentTasks = exports.deleteTasks = exports.insertTasks = exports.scrollToTasks = exports.paste = exports.expandLevel9 = exports.expandLevel8 = exports.expandLevel7 = exports.expandLevel6 = exports.expandLevel5 = exports.expandLevel4 = exports.expandLevel3 = void 0;
exports.copy = '\ubcf5\uc0ac';
exports.cut = '\uc798\ub77c\ub0b4\uae30';
exports.pasteOptions = '\ubd99\uc5ec\ub123\uae30 \uc635\uc158:';
exports.pasteAll = '\ubaa8\ub450';
exports.pasteFormula = '\uc218\uc2dd';
exports.pasteValues = '\uac12';
exports.pasteFormatting = '\uc11c\uc2dd';
exports.pasteValuesFormatting = '\uac12 \ubc0f \uc22b\uc790 \uc11c\uc2dd';
exports.pasteFormulaFormatting = '\uc218\uc2dd \ubc0f \uc22b\uc790 \uc11c\uc2dd';
exports.pasteSpecial = {
    title: '\ud2b9\uc218 \ubd99\uc5ec\ub123\uae30...',
    formulaAndNumberFormat: '\uc218\uc2dd \ubc0f \uc22b\uc790 \uc11c\uc2dd',
    keepSourceFormatting: '\uc6d0\ubcf8 \uc11c\uc2dd \uc720\uc9c0',
    noBorders: '\ud14c\ub450\ub9ac \uc5c6\uc74c',
    columnWidth: '\uc6d0\ubcf8 \uc5f4 \ub108\ube44 \uc720\uc9c0',
    transpose: '\ud589/\uc5f4 \ubc14\uafc8',
    valuesAndNumberFormat: '\uac12 \ubc0f \uc22b\uc790 \uc11c\uc2dd',
    valueAndSourceFormat: '\uac12 \ubc0f \uc6d0\ubcf8 \uc11c\uc2dd',
    pasteLink: '\uc5f0\uacb0\ud558\uc5ec \ubd99\uc5ec\ub123\uae30',
    otherPasteOptions: '\uae30\ud0c0 \ubd99\uc5ec\ub123\uae30 \uc635\uc158'
};
exports.clearContents = '\ub0b4\uc6a9 \uc9c0\uc6b0\uae30';
exports.insertRows = "\uc0bd\uc785";
exports.insertColumns = "\uc0bd\uc785";
exports.deleteRows = "\uc0ad\uc81c";
exports.deleteColumns = "\uc0ad\uc81c";
exports.insertSheet = '\uc0bd\uc785';
exports.deleteSheet = '\uc0ad\uc81c';
exports.insertComment = '\uba54\ubaa8 \uc0bd\uc785';
exports.filter = '\ud544\ud130';
exports.sort = '\uc815\ub82c';
exports.insertCopiedCells = '\ubcf5\uc0ac \ud55c \uc140 \uc0bd\uc785...';
exports.insertCutCells = '\uc798\ub77c\ub0b8 \uc140 \uc0bd\uc785...';
exports.shiftCellsRight = '\uc140\uc744 \uc624\ub978\ucabd\uc73c\ub85c \uc774\ub3d9';
exports.shiftCellsDown = '\uc140\uc744 \uc544\ub798\ub85c \uc774\ub3d9';
exports.headerInsertCopiedCells = '\ubcf5\uc0ac \ud55c \uc140 \uc0bd\uc785';
exports.headerInsertCutCells = '\uc798\ub77c\ub0b8 \uc140 \uc0bd\uc785';
exports.slicerSortAscend = "\uc624\ub984\ucc28\uc21c \uc815\ub82c";
exports.slicerSortDescend = "\ub0b4\ub9bc\ucc28\uc21c \uc815\ub82c";
exports.sortAscend = '\uc624\ub984\ucc28\uc21c \uc815\ub82c';
exports.sortDescend = '\ub0b4\ub9bc\ucc28\uc21c \uc815\ub82c';
exports.hideRows = "\uc228\uae30\uae30";
exports.hideColumns = "\uc228\uae30\uae30";
exports.hideSheet = '\uc228\uae30\uae30';
exports.unhideSheet = '\uc228\uae30\uae30 \ucde8\uc18c';
exports.unhideColumns = "\uc228\uae30\uae30 \ucde8\uc18c";
exports.unhideRows = "\uc228\uae30\uae30 \ucde8\uc18c";
exports.editComment = '\uba54\ubaa8 \ud3b8\uc9d1';
exports.deleteComment = '\uba54\ubaa8 \uc0ad\uc81c';
exports.toggleComment = '\uba54\ubaa8 \ud45c\uc2dc/\uc228\uae30\uae30';
exports.removeSlicer = '\uc81c\uac70';
exports.removeFloatingObject = '\uc81c\uac70';
exports.tableInsert = "\uc0bd\uc785";
exports.tableInsertRowsAbove = "\uc704\ucabd\uc5d0 \ud45c \ud589 \uc0bd\uc785";
exports.tableInsertRowsBelow = "\uc544\ub798\ucabd\uc5d0 \ud45c \ud589 \uc0bd\uc785";
exports.tableInsertColumnsLeft = "\uc67c\ucabd\uc5d0 \ud45c \uc5f4 \uc0bd\uc785";
exports.tableInsertColumnsRight = "\uc624\ub978\ucabd\uc5d0 \ud45c \uc5f4 \uc0bd\uc785";
exports.tableDelete = "\uc0ad\uc81c";
exports.tableDeleteRows = "\ud45c \ud589";
exports.tableDeleteOrInsertEntireSheetRow = "\uc6cc\ud06c\uc2dc\ud2b8 \uc804\uccb4 \ud589";
exports.tableDeleteColumns = "\ud45c \uc5f4";
exports.hideSheetFailureInfo = "\ud1b5\ud569 \ubb38\uc11c\uc5d0\ub294 \ud654\uba74\uc5d0 \ubcf4\uc774\ub294 \uc2dc\ud2b8\uac00 \uc801\uc5b4\ub3c4 \ud558\ub098\ub294 \uc788\uc5b4\uc57c \ud569\ub2c8\ub2e4.\n\uc120\ud0dd\ud55c \uc2dc\ud2b8\ub97c \uc0ad\uc81c\ud558\uac70\ub098, \uc228\uae30\ub824\uba74 \uc0c8 \uc2dc\ud2b8\ub97c \uba3c\uc800 \uc0bd\uc785\ud558\uac70\ub098 \uc228\uaca8\uc838 \uc788\ub294 \uc2dc\ud2b8\uc758 \uc228\uae30\uae30\ub97c \ucde8\uc18c\ud574\uc57c \ud569\ub2c8\ub2e4.";
exports.pivotTableRefresh = "\uc0c8\ub85c \uace0\uce68";
exports.pivotTableMove = "\uc774\ub3d9";
exports.pivotTableRemove_ = "{0} \uc81c\uac70";
exports.pivotTableRemoveGrandTotal = "\ucd1d\ud569\uacc4 \uc81c\uac70";
exports.pivotTableValueFieldSettings = "\uac12 \ud544\ub4dc \uc124\uc815...";
exports.pivotTableOptions = "\ud53c\ubc97 \ud14c\uc774\ube14 \uc635\uc158...";
exports.pivotTableNumberFormat = "\uc22b\uc790 \uc11c\uc2dd...";
exports.pivotTableSort = "\uc815\ub82c";
exports.pivotTableSummarizeValuesBy = "\uac12 \uc694\uc57d \uae30\uc900";
exports.pivotTableSum = "\ud569\uacc4";
exports.pivotTableCount = "\uac1c\uc218";
exports.pivotTableAverage = "\ud3c9\uade0";
exports.pivotTableMax = "\ucd5c\ub300";
exports.pivotTableMin = "\ucd5c\uc18c";
exports.pivotTableProduct = "\uacf1";
exports.pivotTableDistinctCount = "\uace0\uc720 \uac1c\uc218";
exports.pivotTableMoreOptions = "\uae30\ud0c0 \uc635\uc158";
exports.pivotTableShowValueAs = "\uac12 \ud45c\uc2dc \ud615\uc2dd";
exports.pivotTableNoCalculation = "\uacc4\uc0b0 \uc5c6\uc74c";
exports.pivotTableGrandTotal = "\ucd1d\ud569\uacc4 \ube44\uc728";
exports.pivotTableColumnTotal = "\uc5f4 \ud569\uacc4 \ube44\uc728";
exports.pivotTableRowTotal = "\ud589 \ud569\uacc4 \ube44\uc728";
exports.pivotTableOf = "[\uae30\uc900\uac12]\uc5d0 \ub300\ud55c \ube44\uc728...";
exports.pivotTableParentRowTotal = "\uc0c1\uc704 \ud589 \ud569\uacc4 \ube44\uc728";
exports.pivotTableParentColumnTotal = "\uc0c1\uc704 \uc5f4 \ud569\uacc4 \ube44\uc728";
exports.pivotTableOfParentTotal = "\uc0c1\uc704 \ud569\uacc4 \ube44\uc728...";
exports.pivotTableDifferenceFrom = "[\uae30\uc900\uac12]\uacfc\uc758 \ucc28\uc774...";
exports.pivotTablePercentDifferenceFrom = "[\uae30\uc900\uac12]\uc5d0 \ub300\ud55c \ube44\uc728\uc758 \ucc28\uc774...";
exports.pivotTableRunningTotalIN = "\ub204\uacc4...";
exports.pivotTablePercentRunningTotalIn = "\ub204\uacc4 \ube44\uc728...";
exports.pivotTableRankSmallestToLargest = "\uc624\ub984\ucc28\uc21c \uc21c\uc704 \uc9c0\uc815...";
exports.pivotTableRankLargestToSmallest = "\ub0b4\ub9bc\ucc28\uc21c \uc21c\uc704 \uc9c0\uc815...";
exports.pivotTableIndex = "\uc778\ub371\uc2a4";
exports.pivotTableShowDetails = "\uc790\uc138\ud55c \uc815\ubcf4 \ud45c\uc2dc";
exports.pivotTableGroup = "\uadf8\ub8f9...";
exports.pivotTableUnGroup = "\uadf8\ub8f9 \ud574\uc81c...";
exports.pivotTableExpandOrCollapse = "\ud655\uc7a5/\ucd95\uc18c";
exports.pivotTableFilter = "\ud544\ud130";
exports.pivotTableSubtotal_ = "{0} \ubd80\ubd84\ud569";
exports.pivotTableHideFieldList = "\ud544\ub4dc \ubaa9\ub85d \uc228\uae30\uae30";
exports.pivotTableFieldSettings = "\ud544\ub4dc \uc124\uc815...";
exports.pivotTableClearFilterFrom_ = "{0}\uc5d0\uc11c \ud544\ud130 \ud574\uc81c";
exports.pivotTableKeepOnlySelectedItems = "\uc120\ud0dd\ud55c \ud56d\ubaa9\ub9cc \uc720\uc9c0";
exports.pivotTableHideSelectedItems = "\uc120\ud0dd\ud55c \ud56d\ubaa9 \uc228\uae30\uae30";
exports.pivotTableTop10 = "\uc0c1\uc704 10...";
exports.pivotTableLabelFilters = "\ub808\uc774\ube14 \ud544\ud130...";
exports.pivotTableLabelFilters_Date = "\ub0a0\uc9dc \ud544\ud130...";
exports.pivotTableValueFilters = "\uac12 \ud544\ud130...";
exports.pivotTableExpand = "\ud655\uc7a5";
exports.pivotTableCollapse = "\ucd95\uc18c";
exports.pivotTableExpandEntireField = "\uc804\uccb4 \ud544\ub4dc \ud655\uc7a5";
exports.pivotTableCollapseEntireField = "\uc804\uccb4 \ud544\ub4dc \ucd95\uc18c";
exports.pivotTableCollapseTo_ = "{0}(\uc73c)\ub85c \ucd95\uc18c";
exports.pivotTableExpandTo_ = "{0}(\uc73c)\ub85c \ud655\uc7a5";
exports.pivotTableMove_ToBeginning = "\ucc98\uc74c\uc73c\ub85c {0} \uc774\ub3d9";
exports.pivotTableMove_Up = "\uc704\ub85c {0} \uc774\ub3d9";
exports.pivotTableMove_Down = "\uc544\ub798\ub85c {0} \uc774\ub3d9";
exports.pivotTableMove_ToEnd = "\ub05d\uc73c\ub85c {0} \uc774\ub3d9";
exports.pivotTableMove_ToLeft = "\uc67c\ucabd\uc73c\ub85c {0} \uc774\ub3d9";
exports.pivotTableMove_ToRight = "\uc624\ub978\ucabd\uc73c\ub85c {0} \uc774\ub3d9";
exports.pivotTableMove_ToColumns = "\uc5f4\ub85c {0} \uc774\ub3d9";
exports.sigmaValue = "\u2211 \uac12";
exports.sigmaValueTemp = "\uac12";
exports.pivotTableSortAscend = '\uc624\ub984\ucc28\uc21c \uc815\ub82c';
exports.pivotTableSortDescend = '\ub0b4\ub9bc\ucc28\uc21c \uc815\ub82c';
exports.pivotTableSortMoreOptions = '\uae30\ud0c0 \uc815\ub82c \uc635\uc158...';
exports.pinRows = "\ud589 \uace0\uc815/\uace0\uc815 \ud574\uc81c";
exports.pinColumns = "\uc5f4 \uace0\uc815/\uace0\uc815 \ud574\uc81c";
exports.defineColumn = "\uc5f4 \uc815\uc758";
exports.modifyColumn = "\uc5f4 \uc218\uc815";
exports.removeColumn = "\uc5f4 \uc81c\uac70";
exports.setPrimaryKey = "\uae30\ubcf8 \ud0a4 \uc124\uc815/\ud574\uc81c";
exports.promote = "\uc2b9\uaca9";
exports.demote = "\uac15\ub4f1";
exports.moveUp = "\uc704\ub85c \uc774\ub3d9";
exports.moveDown = "\uc544\ub798\ub85c \uc774\ub3d9";
exports.addAfter = "\ub4a4\uc5d0 \ucd94\uac00";
exports.addBefore = "\uc55e\uc5d0 \ucd94\uac00";
exports.addAbove = "\uc704\uc5d0 \ucd94\uac00";
exports.addBelow = "\uc544\ub798\uc5d0 \ucd94\uac00";
exports.showAllOutlineLevel = "\ubaa8\ub4e0 \uc218\uc900 \ubcf4\uc774\uae30";
exports.hideAllOutlineLevel = "\ubaa8\ub4e0 \uc218\uc900 \uc228\uae30\uae30";
exports.showOutlineLevel1 = "1 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel2 = "2 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel3 = "3 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel4 = "4 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel5 = "5 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel6 = "6 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel7 = "7 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel8 = "8 \uc218\uc900 \ubcf4\uc774\uae30";
exports.showOutlineLevel9 = "9 \uc218\uc900 \ubcf4\uc774\uae30";

exports.reportSheetAddRecord = '\ub808\ucf54\ub4dc \ucd94\uac00';
exports.reportSheetDeleteRecord = '\ub808\ucf54\ub4dc \uc0ad\uc81c';
exports.reportSheetSubmit = '\uc81c\ucd9c';
exports.reportSheetResetCellValue = '\ud604\uc7ac \uc140 \uc7ac\uc124\uc815';

exports.expandAllLevels = "\uc804\uccb4 \uc218\uc900 \ud655\uc7a5";
exports.collapseAllLevels = "\uc804\uccb4 \uc218\uc900 \ucd95\uc18c";
exports.expandLevel1 = "1 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel2 = "2 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel3 = "3 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel4 = "4 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel5 = "5 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel6 = "6 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel7 = "7 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel8 = "8 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.expandLevel9 = "9 \uc218\uc900\uc73c\ub85c \ud655\uc7a5";
exports.paste = "\ubd99\uc5ec\ub123\uae30";
exports.scrollToTasks = "\uc791\uc5c5\uc73c\ub85c \uc2a4\ud06c\ub864";
exports.insertTasks = "\uc791\uc5c5 \uc0bd\uc785";
exports.deleteTasks = "\uc791\uc5c5 \uc0ad\uc81c";
exports.indentTasks = "\uc791\uc5c5 \ub4e4\uc5ec\uc4f0\uae30";
exports.outdentTasks = "\uc791\uc5c5 \ub0b4\uc5b4\uc4f0\uae30";
exports.manuallySchedule = "\uc218\ub3d9\uc73c\ub85c \uc608\uc57d";
exports.autoSchedule = "\uc790\ub3d9\uc73c\ub85c \uc608\uc57d";
exports.hideColumn = "\uc5f4 \uc228\uae30\uae30";
exports.unhideColumn = "\uc5f4 \uc228\uae40 \ud574\uc81c";


/***/ }),

/***/ "./dist/plugins/data/data.res.ko.js":
/*!******************************************!*\
  !*** ./dist/plugins/data/data.res.ko.js ***!
  \******************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_NotSupportedDataSource = void 0;
exports.Exp_NotSupportedDataSource = "\ub370\uc774\ud130 \uc18c\uc2a4\uac00 \uc9c0\uc6d0\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4!";


/***/ }),

/***/ "./dist/plugins/exportPDF/printPdf.res.ko.js":
/*!***************************************************!*\
  !*** ./dist/plugins/exportPDF/printPdf.res.ko.js ***!
  \***************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_FontError = exports.Exp_FileIOError = void 0;
exports.Exp_FileIOError = '\ud30c\uc77c \uc77d\uae30 \ubc0f \uc4f0\uae30 \uc608\uc678\uc785\ub2c8\ub2e4.';
exports.Exp_FontError = '\uc9c0\uc6d0 \ub418\ub294 \uae00\uaf34 \ud615\uc2dd\uc774 \ub098 \ud45c\uc900 PDF \uae00\uaf34\uc774 \uc544\ub2d9\ub2c8\ub2e4.';


/***/ }),

/***/ "./dist/plugins/fill/fill.res.ko.js":
/*!******************************************!*\
  !*** ./dist/plugins/fill/fill.res.ko.js ***!
  \******************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_ChangePartOfArray = exports.Exp_RangeIsNull = exports.Exp_CellReadOnly = exports.Exp_RowReadOnly = exports.Exp_ColumnReadOnly = exports.Exp_ChangeMergedCell = exports.Exp_FillCellsReadOnly = exports.Exp_FillRangeContainsMergedCell = exports.Exp_MergedCellsIdentical = exports.Exp_TargetContainsMergedCells = exports.Exp_RangeContainsMergedCell = exports.Exp_NumberOnly = exports.FillWithoutFormatting = exports.FillFormattingOnly = exports.FillSeries = exports.CopyCells = void 0;
exports.CopyCells = '\uc140 \ubcf5\uc0ac';
exports.FillSeries = '\uacc4\uc5f4 \ucc44\uc6b0\uae30';
exports.FillFormattingOnly = '\uc11c\uc2dd\ub9cc \ucc44\uc6b0\uae30';
exports.FillWithoutFormatting = '\uc11c\uc2dd \uc5c6\uc774 \ucc44\uc6b0\uae30';
exports.Exp_NumberOnly = '\uc22b\uc790\uc5d0 \ub300\ud574\uc11c\ub9cc \uc791\ub3d9';
exports.Exp_RangeContainsMergedCell = '\ubc94\uc704\ub294 \ubcd1\ud569\ub41c \uc140\uc744 \ud3ec\ud568\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TargetContainsMergedCells = '\ub300\uc0c1 \ubc94\uc704\ub294 \ubcd1\ud569\ub41c \uc140\uc744 \ud3ec\ud568\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_MergedCellsIdentical = '\uc774 \uc791\uc5c5\uc744 \uc218\ud589\ud558\ub824\uba74 \ubcd1\ud569\ud558\ub824\ub294 \uc140\uc758 \ud06c\uae30\uac00 \ub3d9\uc77c\ud574\uc57c \ud569\ub2c8\ub2e4.';
exports.Exp_FillRangeContainsMergedCell = '\ubcd1\ud569\ub41c \uc140\uc744 \ud3ec\ud568\ud558\ub294 \ubc94\uc704\ub97c \ucc44\uc6b8 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_FillCellsReadOnly = '\ucc44\uc6b0\ub824\ub294 \uc140\uc740 \uc77d\uae30 \uc804\uc6a9\uc73c\ub85c \ubcf4\ud638\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_ChangeMergedCell = '\ubcd1\ud569\ub41c \uc140\uc758 \uc77c\ubd80\ub97c \ubcc0\uacbd\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_ColumnReadOnly = '\ubcc0\uacbd\ud558\ub824\ub294 \uc5f4\uc740 \uc77d\uae30 \uc804\uc6a9\uc73c\ub85c \ubcf4\ud638\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_RowReadOnly = '\ubcc0\uacbd\ud558\ub824\ub294 \ud589\uc740 \uc77d\uae30 \uc804\uc6a9\uc73c\ub85c \ubcf4\ud638\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_CellReadOnly = '\ubcc0\uacbd\ud558\ub824\ub294 \uc140\uc740 \uc77d\uae30 \uc804\uc6a9\uc73c\ub85c \ubcf4\ud638\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_RangeIsNull = '\ubc94\uc704\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_ChangePartOfArray = '\ubc30\uc5f4\uc758 \uc77c\ubd80\ub97c \ubcc0\uacbd\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';


/***/ }),

/***/ "./dist/plugins/filter/filter.res.ko.js":
/*!**********************************************!*\
  !*** ./dist/plugins/filter/filter.res.ko.js ***!
  \**********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ThisWeek = exports.NextWeek = exports.Yesterday = exports.Today = exports.Tomorrow = exports.After = exports.Before = exports.NotContain = exports.Contain = exports.End = exports.Begin = exports.BelowAverage = exports.AboveAverage = exports.Top10 = exports.Between = exports.LessThanOrEquals = exports.LessThan = exports.GreaterOrEquals = exports.GreaterThan = exports.NotEqual = exports.Equal = exports.Custom = exports.NumberFilter = exports.DateFilter = exports.TextFilter = exports.Clear = exports.Automatic = exports.NoFill = exports.CellColor = exports.FontColor = exports.SortFontTitle = exports.SortCellTitle = exports.FilterFontTitle = exports.FilterCellTitle = exports.FilterColor = exports.SortColor = exports.Or = exports.And = exports.ShowRows = exports.Show = exports.Exp_FilterItemIsNull = exports.Blanks = exports.UncheckAll = exports.CheckAll = exports.Search = exports.Cancel = exports.OK = exports.SortDescending = exports.SortAscending = exports.Exp_InvalidColumnIndex = void 0;
exports.Aug = exports.Jul = exports.Jun = exports.May = exports.Apr = exports.Mar = exports.Feb = exports.Jan = exports.Q4 = exports.Q3 = exports.Q2 = exports.Q1 = exports.BeforeOrEqual = exports.IsBefore = exports.AfterOrEqual = exports.IsAfter = exports.NotContains = exports.IsContain = exports.NotEndWith = exports.IsEndWith = exports.NotBeginWith = exports.IsBeginWith = exports.LessOrEqual = exports.IsLess = exports.IsGreaterOrEqual = exports.IsGreaterThan = exports.NotEquals = exports.IsEquals = exports.Selected = exports.FilterFont = exports.FilterCell = exports.SortFont = exports.SortCell = exports.bottom = exports.top = exports.ColorTitle = exports.CustomTitle = exports.Top10Filter = exports.AllDates = exports.YearToDate = exports.LastYear = exports.ThisYear = exports.NextYear = exports.LastQuarter = exports.ThisQuarter = exports.NextQuarter = exports.LastMonth = exports.ThisMonth = exports.NextMonth = exports.LastWeek = void 0;
exports.invalidCondition = exports.add_current_filter = exports.Day = exports.Year = exports.Explain2 = exports.Explain1 = exports.Dec = exports.Nov = exports.Oct = exports.Sep = void 0;
exports.Exp_InvalidColumnIndex = '\uc5f4 \uc778\ub371\uc2a4\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.SortAscending = '\uc624\ub984\ucc28\uc21c \uc815\ub82c';
exports.SortDescending = '\ub0b4\ub9bc\ucc28\uc21c \uc815\ub82c';
exports.OK = '\ud655\uc778';
exports.Cancel = '\ucde8\uc18c';
exports.Search = '\uac80\uc0c9';
exports.CheckAll = '\ubaa8\ub450 \uc120\ud0dd';
exports.UncheckAll = '\ubaa8\ub450 \uc120\ud0dd \ucde8\uc18c';
exports.Blanks = '(\uacf5\ubc31)';
exports.Exp_FilterItemIsNull = 'FilterItem\uc774 null\uc785\ub2c8\ub2e4.';
exports.Show = '\ud45c\uc2dc';
exports.ShowRows = '\ucc3e\uc744 \uc870\uac74:';
exports.And = '\uadf8\ub9ac\uace0';
exports.Or = '\ub610\ub294';
exports.SortColor = '\uc0c9 \uae30\uc900 \uc815\ub82c';
exports.FilterColor = '\uc0c9 \uae30\uc900 \ud544\ud130';
exports.FilterCellTitle = '\uc140 \uc0c9 \uae30\uc900 \ud544\ud130';
exports.FilterFontTitle = '\uae00\uaf34 \uc0c9 \uae30\uc900 \ud544\ud130';
exports.SortCellTitle = '\uc140 \uc0c9 \uae30\uc900 \uc815\ub82c';
exports.SortFontTitle = '\uae00\uaf34 \uc0c9 \uae30\uc900 \uc815\ub82c';
exports.FontColor = '\ucd94\uac00 \uae00\uaf34 \uc0c9...';
exports.CellColor = '\ucd94\uac00 \uc140 \uc0c9...';
exports.NoFill = '\ucc44\uc6b0\uae30 \uc5c6\uc74c';
exports.Automatic = '\uc790\ub3d9';
exports.Clear = '\ud544\ud130 \uc9c0\uc6b0\uae30:{0}';
exports.TextFilter = '\ud14d\uc2a4\ud2b8 \ud544\ud130';
exports.DateFilter = '\ub0a0\uc9dc \ud544\ud130';
exports.NumberFilter = '\uc22b\uc790 \ud544\ud130';
exports.Custom = '\uc0ac\uc6a9\uc790 \uc9c0\uc815 \ud544\ud130...';
exports.Equal = '\uac19\uc74c...';
exports.NotEqual = '\uac19\uc9c0 \uc54a\uc74c...';
exports.GreaterThan = '\ubcf4\ub2e4 \ud07c...';
exports.GreaterOrEquals = '\ud06c\uac70\ub098 \uac19\uc74c...';
exports.LessThan = '\ubcf4\ub2e4 \uc791\uc74c...';
exports.LessThanOrEquals = '\uc791\uac70\ub098 \uac19\uc74c...';
exports.Between = '\ud574\ub2f9 \ubc94\uc704...';
exports.Top10 = '\uc0c1\uc704 10...';
exports.AboveAverage = '\ud3c9\uade0 \ucd08\uacfc';
exports.BelowAverage = '\ud3c9\uade0 \ubbf8\ub9cc';
exports.Begin = '\uc2dc\uc791 \ubb38\uc790...';
exports.End = '\ub05d \ubb38\uc790...';
exports.Contain = '\ud3ec\ud568...';
exports.NotContain = '\ud3ec\ud568\ud558\uc9c0 \uc54a\uc74c...';
exports.Before = '\uc774\uc804...';
exports.After = '\uc774\ud6c4...';
exports.Tomorrow = '\ub0b4\uc77c';
exports.Today = '\uc624\ub298';
exports.Yesterday = '\uc5b4\uc81c';
exports.NextWeek = '\ub2e4\uc74c \uc8fc';
exports.ThisWeek = '\uc774\ubc88 \uc8fc';
exports.LastWeek = '\uc9c0\ub09c \uc8fc';
exports.NextMonth = '\ub2e4\uc74c \ub2ec';
exports.ThisMonth = '\uc774\ubc88 \ub2ec';
exports.LastMonth = '\uc9c0\ub09c \ub2ec';
exports.NextQuarter = '\ub2e4\uc74c \ubd84\uae30';
exports.ThisQuarter = '\uc774\ubc88 \ubd84\uae30';
exports.LastQuarter = '\uc9c0\ub09c \ubd84\uae30';
exports.NextYear = '\ub0b4\ub144';
exports.ThisYear = '\uc62c\ud574';
exports.LastYear = '\uc791\ub144';
exports.YearToDate = '\uc5f0\uac04 \ub204\uacc4';
exports.AllDates = '\uae30\uac04\uc758 \ubaa8\ub4e0 \ub0a0\uc9dc';
exports.Top10Filter = '\uc0c1\uc704 10\uac1c \uc790\ub3d9 \ud544\ud130';
exports.CustomTitle = '\uc0ac\uc6a9\uc790 \uc9c0\uc815 \uc790\ub3d9 \ud544\ud130';
exports.ColorTitle = '\uc0ac\uc6a9\ud560 \uc218 \uc788\ub294 \uc140 \uc0c9';
exports.top = '\uc704\ucabd';
exports.bottom = '\uc544\ub798\ucabd';
exports.SortCell = '\uc815\ub82c \uae30\uc900\uc73c\ub85c \uc0ac\uc6a9\ud560 \uc140 \uc0c9 \uc120\ud0dd:';
exports.SortFont = '\uc815\ub82c \uae30\uc900\uc73c\ub85c \uc0ac\uc6a9\ud560 \uae00\uaf34 \uc0c9 \uc120\ud0dd:';
exports.FilterCell = '\ud544\ud130 \uae30\uc900\uc73c\ub85c \uc0ac\uc6a9\ud560 \uc140 \uc0c9 \uc120\ud0dd:';
exports.FilterFont = '\ud544\ud130 \uae30\uc900\uc73c\ub85c \uc0ac\uc6a9\ud560 \uae00\uaf34 \uc0c9 \uc120\ud0dd:';
exports.Selected = '\uc120\ud0dd \ud56d\ubaa9:';
exports.IsEquals = '=';
exports.NotEquals = '<>';
exports.IsGreaterThan = '>';
exports.IsGreaterOrEqual = '>=';
exports.IsLess = '<';
exports.LessOrEqual = '<=';
exports.IsBeginWith = '\uc2dc\uc791 \ubb38\uc790';
exports.NotBeginWith = '\uc81c\uc678\ud560 \uc2dc\uc791 \ubb38\uc790';
exports.IsEndWith = '\ub05d \ubb38\uc790';
exports.NotEndWith = '\uc81c\uc678\ud560 \ub05d \ubb38\uc790';
exports.IsContain = '\ud3ec\ud568';
exports.NotContains = '\ud3ec\ud568\ud558\uc9c0 \uc54a\uc74c';
exports.IsAfter = '\uc774\ud6c4';
exports.AfterOrEqual = '\uc774\ud6c4\uc774\uac70\ub098 \uac19\uc74c';
exports.IsBefore = '\uc774\uc804';
exports.BeforeOrEqual = '\uc774\uc804\uc774\uac70\ub098 \uac19\uc74c';
exports.Q1 = '1\ubd84\uae30';
exports.Q2 = '2\ubd84\uae30';
exports.Q3 = '3\ubd84\uae30';
exports.Q4 = '4\ubd84\uae30';
exports.Jan = '1\uc6d4';
exports.Feb = '2\uc6d4';
exports.Mar = '3\uc6d4';
exports.Apr = '4\uc6d4';
exports.May = '5\uc6d4';
exports.Jun = '6\uc6d4';
exports.Jul = '7\uc6d4';
exports.Aug = '8\uc6d4';
exports.Sep = '9\uc6d4';
exports.Oct = '10\uc6d4';
exports.Nov = '11\uc6d4';
exports.Dec = '12\uc6d4';
exports.Explain1 = '? \uae30\ud638\ub97c \uc0ac\uc6a9\ud558\uc5ec \ud55c \ubb38\uc790\ub97c \ub098\ud0c0\ub0bc \uc218 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Explain2 = '* \uae30\ud638\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc5ec\ub7ec \ubb38\uc790\ub97c \ub098\ud0c0\ub0bc \uc218 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Year = '\ub144';
exports.Day = '\uc77c';
exports.add_current_filter = '\ud544\ud130\uc5d0 \ud604\uc7ac \uc120\ud0dd \ub0b4\uc6a9 \ucd94\uac00';
exports.invalidCondition = '\uad6c\ubb38 \ubd84\uc11d \uc904\uc5d0 \uc624\ub958 \ubc1c\uc0dd';


/***/ }),

/***/ "./dist/plugins/floatingObject/floatingobject.res.ko.js":
/*!**************************************************************!*\
  !*** ./dist/plugins/floatingObject/floatingobject.res.ko.js ***!
  \**************************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_FloatingObjectNameEmptyError = exports.Exp_FloatingObjectHasSameNameError = void 0;
exports.Exp_FloatingObjectHasSameNameError = '\ud604\uc7ac \uc6cc\ud06c\uc2dc\ud2b8\uc5d0 \ub3d9\uc77c\ud55c \uc774\ub984\uc744 \uac00\uc9c4 \ubd80\ub3d9 \uac1c\uccb4(FloatingObject)\uac00 \uc774\ubbf8 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_FloatingObjectNameEmptyError = '\ubd80\ub3d9 \uac1c\uccb4(FloatingObject)\ub294 \uc774\ub984\uc774 \uc788\uc5b4\uc57c \ud569\ub2c8\ub2e4.';


/***/ }),

/***/ "./dist/plugins/formulaPanel/editor/editor.res.ko.js":
/*!***********************************************************!*\
  !*** ./dist/plugins/formulaPanel/editor/editor.res.ko.js ***!
  \***********************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.FORMULA = exports.INVALID_FORMULA = exports.INVALID_EXPRESSION = exports.DUPLICATE_IDENTIFIER = exports.INVALID_IDENTIFIER_ARGUMENT = exports.NO_MATCHED_ARGUMENTS = exports.UNKNOWN_SHEET = exports.UNKNOWN_NAME = exports.MISS_LET_FUNCTION_BODY = exports.MISS_RIGHT_PARENTHESES = exports.NO_MATH_PARENTHESES = void 0;
exports.NO_MATH_PARENTHESES = '\uc77c\uce58\ud558\ub294 \uad04\ud638 \uc5c6\uc74c';
exports.MISS_RIGHT_PARENTHESES = "')'\uac00 \uc608\uc0c1\ub429\ub2c8\ub2e4.";
exports.MISS_LET_FUNCTION_BODY = "LET \ud45c\ud604\uc2dd \ubcf8\ubb38 \ub204\ub77d.";
exports.UNKNOWN_NAME = '\uc54c \uc218 \uc5c6\ub294 \uc774\ub984';
exports.UNKNOWN_SHEET = '\uc54c \uc218 \uc5c6\ub294 \uc2dc\ud2b8 "$1"';
exports.NO_MATCHED_ARGUMENTS = '$1 \uc778\uc218\uac00 \uc608\uc0c1\ub418\uc9c0\ub9cc $2\uac00 \uc785\ub825\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.INVALID_IDENTIFIER_ARGUMENT = '\uc774 \uc778\uc218\ub294 \ubc14\uc778\ub529\ub420 \uc2dd\ubcc4\uc790\uc5ec\uc57c \ud569\ub2c8\ub2e4.';
exports.DUPLICATE_IDENTIFIER = '\uc911\ubcf5 \uc2dd\ubcc4\uc790 $1';
exports.INVALID_EXPRESSION = '\uc798\ubabb\ub41c \ud45c\ud604\uc2dd';
exports.INVALID_FORMULA = '\uc798\ubabb\ub41c \uc218\uc2dd';
exports.FORMULA = '\uc218\uc2dd';


/***/ }),

/***/ "./dist/plugins/gantt/gantt.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/gantt/gantt.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.GANTT_ACTION_FROM_OF = exports.GANTT_ACTION_FINISH_LINK = exports.GANTT_ACTION_START_LINK = exports.GANTT_ACTION_TASK_FINISH = exports.GANTT_ACTION_TASK_START = exports.GANTT_ACTION_FINISH = exports.GANTT_ACTION_START = exports.GANTT_ACTION_TASK_COLON = exports.GANTT_ACTION_TASK = exports.DEFAULT_DATE_TIME_FORMAT = exports.GANTT_VALUE_READONLY = exports.GANTT_VALUE_INVALID = exports.GANTT_INVALID_ASCENDING_STATES = exports.GANTT_INVALID_FIELD = exports.GANTT_INVALID_TASK_LEVEL = exports.GANTT_INVALID_TASK_PARENT_ID = exports.GANTT_INVALID_TASK_ID = exports.GANTT_INVALID_WORK_DAYS = exports.GANTT_INVALID_UNIT = exports.GANTT_INVALID_DEPENDENCY = exports.GANTT_INVALID_TASK = exports.GANTT_INVALID_COUNT = exports.GANTT_INVALID_INDEX = exports.GANTT_INVALID_ROW_INDEX = exports.GANTT_INVALID_ID = exports.GANTT_INVALID_LEVEL = exports.GANTT_INVALID_OPERATION = exports.GANTT_TASK_SCHEDULE_MODE_AUTO = exports.GANTT_TASK_SCHEDULE_MODE_MANUALLY = exports.GANTT_COMPLETE_CHANGE = exports.GANTT_COMPLETE = exports.GANTT_SUCCESSORS = exports.GANTT_PREDECESSORS = exports.GANTT_FINISH_DISPLAYED = exports.GANTT_START_DISPLAYED = exports.GANTT_FINISH = exports.GANTT_START = exports.GANTT_DURATION = exports.GANTT_MODE = exports.GANTT_MILESTONE = exports.GANTT_TASK_NAME = exports.GANTT_TASK_NUMBER = exports.GANTT_NEW_MILESTONE = exports.GANTT_NEW_SUMMARY_TASK = exports.GANTT_NEW_TASK = exports.GANTT_MINUTE = exports.GANTT_HOUR = exports.GANTT_DAY = exports.GANTT_WEEK = exports.GANTT_MONTH = void 0;
exports.GANTT_CALENDAR_UNIT_LABELS_PLURALS = exports.GANTT_CALENDAR_UNIT_LABELS = exports.GANTT_CALENDAR_MONTH_NAMES = exports.GANTT_CALENDAR_MONTH = exports.GANTT_CALENDAR_WEEK_NAMES = exports.GANTT_CALENDAR_WEEK = exports.GANTT_CALENDAR_DAY_NAMES = exports.GANTT_CALENDAR_DAY = exports.GANTT_CALENDAR_HOUR_NAMES = exports.GANTT_CALENDAR_HOUR = exports.GANTT_CALENDAR_MINUTE_NAMES = exports.GANTT_CALENDAR_MINUTE = exports.GANTT_CALENDAR_24HOURS = exports.GANTT_CALENDAR_NIGHT_SHIFT = exports.GANTT_CALENDAR_STANDARD = exports.GANTT_TASK_BAR_COMPLETE = exports.GANTT_TASK_BAR_COMPLETE_THROUGH = exports.GANTT_TASK_BAR_UNNAMED = exports.GANTT_TASK_BAR_SS = exports.GANTT_TASK_BAR_SF = exports.GANTT_TASK_BAR_FF = exports.GANTT_TASK_BAR_FS = exports.GANTT_TASK_BAR_TO = exports.GANTT_TASK_BAR_FROM = exports.GANTT_TASK_BAR_TASK_LINK = exports.GANTT_ACTION_LINE_TIP_TITLE = exports.GANTT_ACTION_COMPLETE_THROUGH = exports.GANTT_ACTION_DURATION = exports.GANTT_ACTION_FINISH_ONLY = exports.GANTT_ACTION_START_ONLY = exports.GANTT_ACTION_TO_OF = void 0;
exports.GANTT_MONTH = "\uc6d4";
exports.GANTT_WEEK = "\uc8fc";
exports.GANTT_DAY = "\uc77c";
exports.GANTT_HOUR = "\uc2dc";
exports.GANTT_MINUTE = "\ubd84";
exports.GANTT_NEW_TASK = "<\uc0c8 \uc791\uc5c5>";
exports.GANTT_NEW_SUMMARY_TASK = "<\uc0c8 \uc694\uc57d \uc791\uc5c5>";
exports.GANTT_NEW_MILESTONE = "<\uc0c8 \ub9c8\uc77c\uc2a4\ud1a4>";
exports.GANTT_TASK_NUMBER = "\uc791\uc5c5 \ubc88\ud638";
exports.GANTT_TASK_NAME = "\uc791\uc5c5 \uc774\ub984";
exports.GANTT_MILESTONE = "\ub9c8\uc77c\uc2a4\ud1a4";
exports.GANTT_MODE = "\ubaa8\ub4dc";
exports.GANTT_DURATION = "\uae30\uac04";
exports.GANTT_START = "\uc2dc\uc791";
exports.GANTT_FINISH = "\uc885\ub8cc";
exports.GANTT_START_DISPLAYED = "\uc2dc\uc791\uc77c \ud45c\uc2dc";
exports.GANTT_FINISH_DISPLAYED = "\uc885\ub8cc\uc77c \ud45c\uc2dc";
exports.GANTT_PREDECESSORS = "\uc120\ud589 \uc791\uc5c5";
exports.GANTT_SUCCESSORS = "\ud6c4\ud589 \uc791\uc5c5";
exports.GANTT_COMPLETE = "% \uc644\ub8cc";
exports.GANTT_COMPLETE_CHANGE = "\uc644\ub8cc \uae30\uac04";
exports.GANTT_TASK_SCHEDULE_MODE_MANUALLY = "\uc218\ub3d9";
exports.GANTT_TASK_SCHEDULE_MODE_AUTO = "\uc790\ub3d9";
exports.GANTT_INVALID_OPERATION = "\uc791\uc5c5\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_LEVEL = "\ub808\ubca8\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_ID = "ID\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_ROW_INDEX = "\ud589 \uc778\ub371\uc2a4\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_INDEX = "\uc778\ub371\uc2a4\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_COUNT = "\uc218\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_TASK = "\uc791\uc5c5\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_DEPENDENCY = "\uc885\uc18d\uc131\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_UNIT = "\ub2e8\uc704\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_WORK_DAYS = "\uc791\uc5c5\uc77c\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_TASK_ID = "\uc791\uc5c5 ID\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_TASK_PARENT_ID = "\uc791\uc5c5 \ubd80\ubaa8 ID\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_TASK_LEVEL = "\uc791\uc5c5 \ub808\ubca8\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_FIELD = "\ud544\ub4dc\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_INVALID_ASCENDING_STATES = "\uc624\ub984\ucc28\uc21c \uc0c1\ud0dc\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_VALUE_INVALID = "\uac12\uc774 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc74c";
exports.GANTT_VALUE_READONLY = "\uac12 \uc77d\uae30 \uc804\uc6a9";
exports.DEFAULT_DATE_TIME_FORMAT = "yy-mm-dd h:mm";
exports.GANTT_ACTION_TASK = "\uc791\uc5c5";
exports.GANTT_ACTION_TASK_COLON = "\uc791\uc5c5:";
exports.GANTT_ACTION_START = "\uc2dc\uc791:";
exports.GANTT_ACTION_FINISH = "\uc885\ub8cc:";
exports.GANTT_ACTION_TASK_START = "\uc791\uc5c5 \uc2dc\uc791:";
exports.GANTT_ACTION_TASK_FINISH = "\uc791\uc5c5 \uc885\ub8cc:";
exports.GANTT_ACTION_START_LINK = "\uc2dc\uc791";
exports.GANTT_ACTION_FINISH_LINK = "\uc885\ub8cc";
exports.GANTT_ACTION_FROM_OF = "From {0} Of:";
exports.GANTT_ACTION_TO_OF = "To {0} Of:";
exports.GANTT_ACTION_START_ONLY = "\uc2dc\uc791\uc77c\ub9cc";
exports.GANTT_ACTION_FINISH_ONLY = "\uc885\ub8cc\uc77c\ub9cc";
exports.GANTT_ACTION_DURATION = "\uae30\uac04:";
exports.GANTT_ACTION_COMPLETE_THROUGH = "\uc644\ub8cc \uae30\uac04:";
exports.GANTT_ACTION_LINE_TIP_TITLE = "{0}\uc5d0\uc11c {1}\ub85c \uc5f0\uacb0";
exports.GANTT_TASK_BAR_TASK_LINK = "\uc791\uc5c5 \uc5f0\uacb0:";
exports.GANTT_TASK_BAR_FROM = "\uc2dc\uc791:";
exports.GANTT_TASK_BAR_TO = "\uc885\ub8cc:";
exports.GANTT_TASK_BAR_FS = "Finish-to-Start (FS)";
exports.GANTT_TASK_BAR_FF = "Finish-to-Finish (FF)";
exports.GANTT_TASK_BAR_SF = "Start-to-Finish (SF)";
exports.GANTT_TASK_BAR_SS = "Start-to-Start (SS)";
exports.GANTT_TASK_BAR_UNNAMED = "\ubb34\uba85";
exports.GANTT_TASK_BAR_COMPLETE_THROUGH = "\uc644\ub8cc \uae30\uac04:";
exports.GANTT_TASK_BAR_COMPLETE = "% \uc644\ub8cc:";
exports.GANTT_CALENDAR_STANDARD = "\ud45c\uc900";
exports.GANTT_CALENDAR_NIGHT_SHIFT = "\uc57c\uac04 \ubaa8\ub4dc";
exports.GANTT_CALENDAR_24HOURS = "24 \uc2dc";
exports.GANTT_CALENDAR_MINUTE = "\ubd84";
exports.GANTT_CALENDAR_MINUTE_NAMES = ["m", "min", "minute"];
exports.GANTT_CALENDAR_HOUR = "\uc2dc";
exports.GANTT_CALENDAR_HOUR_NAMES = ["h", "hr", "hour"];
exports.GANTT_CALENDAR_DAY = "\uc77c";
exports.GANTT_CALENDAR_DAY_NAMES = ["d", "dy", "day"];
exports.GANTT_CALENDAR_WEEK = "\uc8fc";
exports.GANTT_CALENDAR_WEEK_NAMES = ["w", "wk", "week"];
exports.GANTT_CALENDAR_MONTH = "\uc6d4";
exports.GANTT_CALENDAR_MONTH_NAMES = ["mo", "mon", "month"];
exports.GANTT_CALENDAR_UNIT_LABELS = ["min", "hr", "day", "wk", "mon", "yr"];
exports.GANTT_CALENDAR_UNIT_LABELS_PLURALS = ["mins", "hrs", "days", "wks", "mons", "yrs"];

var lr = __webpack_require__(/*! lrGtKo */ "./dist/plugins/gantt/lr.ko.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}


/***/ }),

/***/ "./dist/plugins/gantt/lr.ko.js":
/*!*************************************!*\
  !*** ./dist/plugins/gantt/lr.ko.js ***!
  \*************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.wmk2 = exports.wmk1 = void 0;
exports.wmk1 = ["acd200c2d2", "04b820dcb8"];
exports.wmk2 = ["acd200c2d2", "04b820dcb8"];


/***/ }),

/***/ "./dist/plugins/group/group.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/group/group.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_GROUP_PROTECTED = exports.Exp_GroupInfoIsNull = exports.Exp_InvalidLevel = exports.Exp_InvalidCount = exports.Exp_InvalidIndex = void 0;
exports.Exp_InvalidIndex = '\uc798\ubabb\ub41c \uc778\ub371\uc2a4';
exports.Exp_InvalidCount = '\uc798\ubabb\ub41c \uac1c\uc218';
exports.Exp_InvalidLevel = '\uc798\ubabb\ub41c \ub808\ubca8';
exports.Exp_GroupInfoIsNull = 'groupInfo\uac00 null\uc785\ub2c8\ub2e4.';
exports.Exp_GROUP_PROTECTED = "\ubcf4\ud638\ub41c \uc2dc\ud2b8\uc5d0\uc11c \uc774 \uba85\ub839\uc744 \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \uc774 \uba85\ub839\uc744 \uc0ac\uc6a9\ud558\ub824\uba74 \uba3c\uc800 \uc2dc\ud2b8 \ubcf4\ud638\ub97c \ud574\uc81c\ud574\uc57c \ud569\ub2c8\ub2e4.";


/***/ }),

/***/ "./dist/plugins/inputMask/inputMask/inputMask.res.ko.js":
/*!**************************************************************!*\
  !*** ./dist/plugins/inputMask/inputMask/inputMask.res.ko.js ***!
  \**************************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.hourFormatDesignator = exports.hourFormatSingleDesignator = exports.errorMsg = void 0;
exports.errorMsg = {
    invalidPattern: '\uc798\ubabb\ub41c \ud328\ud134',
    duplicatedDateTimePattern: '\uc911\ubcf5\ub41c \ub0a0\uc9dc \uc2dc\uac04 \ud328\ud134',
    emptyPattern: '\ud328\ud134 \uc5c6\uc74c',
    unknownPattern: '\uc54c \uc218 \uc5c6\ub294 \ud328\ud134 \uad6c\ubb38 \ubd84\uc11d \uc2e4\ud328',
    invalidOptionalPattern: '\uc120\ud0dd\uc801 \ud328\ud134 \uad6c\ubb38 \ubd84\uc11d \uc2e4\ud328',
    invalidGroupPattern: '\uadf8\ub8f9 \ud328\ud134 \uad6c\ubb38 \ubd84\uc11d \uc2e4\ud328',
    errorQuantifierPattern: '\uc624\ub958 \uc218\ub7c9\ud654 \uc704\uce58',
    invalidQuantifierPattern: '\uc218\ub7c9\ud654 \ud328\ud134 \uad6c\ubb38 \ubd84\uc11d \uc2e4\ud328',
    noEntry: "\uc81c\ud55c\uc0ac\ud56d\uc744 \uc785\ub825\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4"
};
exports.hourFormatSingleDesignator = ['A', 'P'];
exports.hourFormatDesignator = ['AM', 'PM'];


/***/ }),

/***/ "./dist/plugins/io/io.res.ko.js":
/*!**************************************!*\
  !*** ./dist/plugins/io/io.res.ko.js ***!
  \**************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.EXP_EMPTY_PASSWORD = exports.EXP_WRONG_PASSWORD = exports.GET_PARTIAL_VALUES_ERROR_MSG = exports.EXP_FILE_FORMAT = exports.EXP_IO = void 0;
exports.EXP_IO = '\ud30c\uc77c \uc77d\uae30 \ubc0f \uc4f0\uae30 \uc608\uc678';
exports.EXP_FILE_FORMAT = '\uc798\ubabb\ub41c \ud30c\uc77c \ud615\uc2dd';
exports.GET_PARTIAL_VALUES_ERROR_MSG = '\uc624\ub958 \ud30c\uc77c';
exports.EXP_WRONG_PASSWORD = '\uc785\ub825\ub41c \ube44\ubc00\ubc88\ud638\uac00 \uc62c\ubc14\ub974\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.';
exports.EXP_EMPTY_PASSWORD = '\uc6cc\ud06c\ubd81\uc774 \uc554\ud638\ub85c \ubcf4\ud638\ub418\uc5b4 \uc5d1\uc140 \ud30c\uc77c\uc744 \uc5f4 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';


/***/ }),

/***/ "./dist/plugins/namebox/namebox.res.ko.js":
/*!************************************************!*\
  !*** ./dist/plugins/namebox/namebox.res.ko.js ***!
  \************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.nameBox = exports.invalidName = void 0;
exports.invalidName = "\uc774\ub3d9\ud558\ub824\ub294 \uc720\ud6a8\ud55c \ucc38\uc870\ub97c \uc785\ub825\ud558\uac70\ub098 \uc120\ud0dd \ud56d\ubaa9\uc5d0 \ub300\ud574 \uc720\ud6a8\ud55c \uc774\ub984\uc744 \uc785\ub825\ud574\uc57c \ud569\ub2c8\ub2e4.";
exports.nameBox = "\uc774\ub984 \uc0c1\uc790";


/***/ }),

/***/ "./dist/plugins/outlineColumn/outlineColumn.res.ko.js":
/*!************************************************************!*\
  !*** ./dist/plugins/outlineColumn/outlineColumn.res.ko.js ***!
  \************************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_GROUP_PROTECTED = void 0;
exports.Exp_GROUP_PROTECTED = "\ubcf4\ud638\ub41c \uc2dc\ud2b8\uc5d0\uc11c \uc774 \uba85\ub839\uc744 \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \uc774 \uba85\ub839\uc744 \uc0ac\uc6a9\ud558\ub824\uba74 \uba3c\uc800 \uc2dc\ud2b8 \ubcf4\ud638\ub97c \ud574\uc81c\ud574\uc57c \ud569\ub2c8\ub2e4.";


/***/ }),

/***/ "./dist/plugins/pivot/lr.ko.js":
/*!*************************************!*\
  !*** ./dist/plugins/pivot/lr.ko.js ***!
  \*************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.wmk2 = exports.wmk1 = void 0;
exports.wmk1 = ["d5bc00d1c7be", "3c97204c7414"];
exports.wmk2 = ["d5bc00d1c7be", "3c97204c7414"];


/***/ }),

/***/ "./dist/plugins/pivot/pivot.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/pivot/pivot.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.PivotPanel_ValueFilterOrLabel_NotEqual = exports.PivotPanel_ValueFilterOrLabel_Equals = exports.PivotPanel_LabelOrValue_ClearFilter = exports.PivotPanel_Fields_MoveTo_Value = exports.PivotPanel_Fields_MoveTo_ColLabel = exports.PivotPanel_Fields_MoveTo_RowLabel = exports.PivotPanel_Fields_MoveTo_ReportField = exports.PivotPanel_DropDownList_ValueSet = exports.PivotPanel_DropDownList_Set = exports.PivotPanel_DropDownList_Remove = exports.PivotPanel_DropDownList_Values = exports.PivotPanel_DropDownList_Col = exports.PivotPanel_DropDownList_Row = exports.PivotPanel_DropDownList_ReportFilter = exports.PivotPanel_DropDownList_End = exports.PivotPanel_DropDownList_Beginning = exports.PivotPanel_DropDownList_Down = exports.PivotPanel_DropDownList_Up = exports.PivotPanel_Update = exports.EmptyPivotTable_PromptMessage2 = exports.EmptyPivotTable_PromptMessage1 = exports.PivotPanel_DeferUpdateText = exports.PivotPanel_ValuesItemsTitle = exports.PivotPanel_ColumnsItemsTitle = exports.PivotPanel_RowsItemsTitle = exports.PivotPanel_FiltersItemsTitle = exports.PivotPanel_FieldAreaText = exports.PivotPanel_SearchPlaceholder = exports.PivotPanel_ReportText = exports.PivotPanel_Title = exports.Values = exports.GrandTotal = exports.RowLabels = exports.MultipleItems = exports.All = exports.Total = exports.ColumnLabels = exports.SubtotalType_Prefix_10 = exports.SubtotalType_Prefix_9 = exports.SubtotalType_Prefix_8 = exports.SubtotalType_Prefix_7 = exports.SubtotalType_Prefix_6 = exports.SubtotalType_Prefix_5 = exports.SubtotalType_Prefix_4 = exports.SubtotalType_Prefix_3 = exports.SubtotalType_Prefix_2 = exports.SubtotalType_Prefix_1 = exports.SubtotalType_Prefix_0 = exports.NotExist = exports.RepeatName = void 0;
exports.PivotPanel_ValueFilterOrLabel_ThisYear = exports.PivotPanel_ValueFilterOrLabel_NextYear = exports.PivotPanel_ValueFilterOrLabel_LastQuarter = exports.PivotPanel_ValueFilterOrLabel_ThisQuarter = exports.PivotPanel_ValueFilterOrLabel_NextQuarter = exports.PivotPanel_ValueFilterOrLabel_LastMonth = exports.PivotPanel_ValueFilterOrLabel_ThisMonth = exports.PivotPanel_ValueFilterOrLabel_NextMonth = exports.PivotPanel_ValueFilterOrLabel_LastWeek = exports.PivotPanel_ValueFilterOrLabel_ThisWeek = exports.PivotPanel_ValueFilterOrLabel_NextWeek = exports.PivotPanel_ValueFilterOrLabel_Yesterday = exports.PivotPanel_ValueFilterOrLabel_Today = exports.PivotPanel_ValueFilterOrLabel_Tomorrow = exports.PivotPanel_ValueFilterOrLabel_After = exports.PivotPanel_ValueFilterOrLabel_Before = exports.PivotPanel_Date_Filter = exports.ValueFilterOrLabel_NotContain = exports.ValueFilterOrLabel_Contain = exports.ValueFilterOrLabel_NotEndWith = exports.ValueFilterOrLabel_End = exports.ValueFilterOrLabel_NotBegin = exports.ValueFilterOrLabel_IsBeginWith = exports.ValueFilterOrLabelSelect_NotBetween = exports.ValueFilterOrLabelSelect_Between = exports.ValueFilterOrLabelSelect_LessOrTo = exports.ValueFilterOrLabelSelect_LessThan = exports.ValueFilterOrLabelSelect_GreaterOrTo = exports.ValueFilterOrLabelSelect_GreaterThan = exports.ValueFilterOrLabel_NotBetween = exports.ValueFilterOrLabel_Between = exports.ValueFilterOrLabel_LessOrTo = exports.ValueFilterOrLabel_LessThan = exports.ValueFilterOrLabel_GreaterOrTo = exports.ValueFilterOrLabel_GreaterThan = exports.ValueFilterOrLabel_NotEqual = exports.ValueFilterOrLabel_Equals = exports.PivotPanel_ValueFilterOrLabel_NotContain = exports.PivotPanel_ValueFilterOrLabel_Contain = exports.PivotPanel_ValueFilterOrLabel_NotEndWith = exports.PivotPanel_ValueFilterOrLabel_End = exports.PivotPanel_ValueFilterOrLabel_NotBegin = exports.PivotPanel_ValueFilterOrLabel_IsBeginWith = exports.PivotPanel_ValueFilter_Top10 = exports.PivotPanel_ValueFilterOrLabel_NotBetween = exports.PivotPanel_ValueFilterOrLabel_Between = exports.PivotPanel_ValueFilterOrLabel_LessOrTo = exports.PivotPanel_ValueFilterOrLabel_LessThan = exports.PivotPanel_ValueFilterOrLabel_GreaterOrTo = exports.PivotPanel_ValueFilterOrLabel_GreaterThan = void 0;
exports.Sort_Value_Dialog_Asc = exports.Sort_Value_Dialog_Sort_Direction = exports.Sort_Value_Dialog_Title = exports.Sort_Field_More_Dialog_Values_In_Selected_Area = exports.Sort_Field_More_Dialog_Sort_By = exports.Sort_Field_More_Dialog_Title = exports.Sort_Field_Dialog_More_Options = exports.Sort_Field_Dialog_Descending_Sort = exports.Sort_Field_Dialog_Ascending_Sort = exports.Sort_Field_Dialog_Summary = exports.Sort_Field_Dialog_Sort_Options = exports.Sort_Field_Dialog_Title = exports.TopTenShow = exports.Top_Ten_Filter = exports.Value_Title = exports.Value_Show = exports.Use_Series_Characters = exports.Use_Single_Character = exports.Label_Show = exports.Label_Title = exports.PivotPanel_Filter_Search = exports.PivotPanel_Filter_NoSelectAll = exports.PivotPanel_Filter_SelectAll = exports.Select_Field = exports.CustomFormats = exports.Type = exports.Sample = exports.FormatCells = exports.Number = exports.LabelFormat = exports.NumberFormat = exports.Cancel = exports.Ok = exports.PivotPanel_Filter_Value = exports.PivotPanel_Filter_Label = exports.PivotPanel_Filter_Clear = exports.PivotPanel_Sort_More = exports.PivotPanel_Sort_Largest_Smallest = exports.PivotPanel_Sort_Smallest_Largest = exports.PivotPanel_Sort_Z_A = exports.PivotPanel_Sort_A_Z = exports.PivotPanel_ValueFilterOrLabel_Custom = exports.PivotPanel_ValueFilterOrLabel_AllDates = exports.PivotPanel_ValueFilterOrLabel_ParallelQuarterToDate = exports.PivotPanel_ValueFilterOrLabel_ParallelMonthToDate = exports.PivotPanel_ValueFilterOrLabel_ParallelYearToDate = exports.PivotPanel_ValueFilterOrLabel_QuarterToDate = exports.PivotPanel_ValueFilterOrLabel_MonthToDate = exports.PivotPanel_ValueFilterOrLabel_YearToDate = exports.PivotPanel_ValueFilterOrLabel_LastYear = void 0;
exports.SummarizeValue = exports.CustomName = exports.SourceName = exports.ValueSetting = exports.FieldSetting = exports.WholeDays = exports.DateShow = exports.DateFilterTitle = exports.IsNotBetween = exports.IsBetween = exports.IsAfterOrEqual = exports.IsAfter = exports.IsBeforeOrEqual = exports.IsBefore = exports.Dec = exports.Nov = exports.Oct = exports.Sep = exports.Aug = exports.Jul = exports.Jun = exports.May = exports.Apr = exports.Mar = exports.Feb = exports.Jan = exports.Q4 = exports.Q3 = exports.Q2 = exports.Q1 = exports.AndJoiner = exports.ByJoiner = exports.Top_Sum = exports.Top_Percent = exports.Top_Item = exports.Bottom = exports.Top = exports.Invalid_Field_Sort_By = exports.Invalid_Sort_Range = exports.Sort_More_Summary_Pattern = exports.Sort_Value_Summary_Pattern = exports.Sort_Summary_Pattern = exports.Sort_In_Column = exports.Sort_In_Row = exports.Sort_Order_DESC = exports.Sort_Order_ASC = exports.Custom_Sort_Summary = exports.Sort_Value_Dialog_Left_To_Right = exports.Sort_Value_Dialog_Top_To_Bottom = exports.Sort_Value_Dialog_Desc = void 0;
exports.startingAt = exports.auto = exports.grouping = exports.baseItem = exports.baseField = exports.showValueAsDialog = exports.index = exports.rankLargestSmallest = exports.rankSmallestLargest = exports.percentRunningTotal = exports.runningTotal = exports.percentDifference = exports.difference = exports.percentParentTotal = exports.percentParentColumnTotal = exports.percentParentRowTotal = exports.percentEllipsis = exports.percentRowTotal = exports.percentColumnTotal = exports.percentGrandTotal = exports.noCalculation = exports.DefaultPivotTableViewName = exports.FieldAreaLimited = exports.SourceDataOnlyOne = exports.SourceNotIsTableNameOrFormula = exports.SourceError = exports.NoHaveSpread = exports.DateFormatError = exports.sigmaValueTemp = exports.sigmaValue = exports.Varp = exports.Var = exports.StdDevp = exports.StdDev = exports.CountNumbers = exports.Product = exports.Min = exports.Max = exports.Average = exports.Count = exports.Sum = exports.ChooseType = exports.SummarizeValueField = exports.ShowValueAsField = exports.BaseItemPrevious = exports.BaseItemNext = exports.BaseItemForDialog = exports.BaseFieldForDialog = exports.CalculationForDialog = exports.ShowValueAs = void 0;
exports.TIMELINE_PLACE_HOLDER = exports.showSubtotalTop = exports.showNoData = exports.Layout = exports.AddSearchResult = exports.search = exports.SlicerNameInFormula = exports.PivotTableErrorFormula = exports.PivotTableMoveCalcItemFieldToReport = exports.PivotTableCalcItemHasMultipleDataField = exports.PivotTableHasSameItemName = exports.PivotTableSubtotalType = exports.PivotTableHasNumberOrDateGroup = exports.PivotTableHasSameCalcItemName = exports.PivotTableCalcItemHasTowCacheField = exports.PivotTableForAccessibility = exports.PivotTableErrorMessage_InvalidReference = exports.PivotTableErrorMessage_InvalidGroup = exports.PivotTableErrorMessage_InvalidChange = exports.PivotTableErrorMessage_Overlap = exports.PivotTableErrorMessage_EmptySourceFieldName = exports.PivotTableErrorMessage_Protect = exports.PivotTableErrorMessage_EmptyFieldName = exports.PivotTableErrorMessage_DuplicatedFieldName = exports.PivotTableErrorMessage_EditWhenDefer = exports.PivotTableErrorMessage_ExistData = exports.PivotTableErrorMessage_MakeChange = exports.PivotTableErrorMessage_ShowDetail = exports.Views = exports.getValueFilterItemText = exports.getDateLabelFilterItemText = exports.getStringLabelFilterItemText = exports.deferLayoutUpdate = exports.toolTipContent_NoValue = exports.toolTipContent_Value = exports.toolTipContent_Column = exports.toolTipContent_Row = exports.EmptyValueFieldError = exports.param_error = exports.Group = exports.numberOfDays = exports.years = exports.quarters = exports.months = exports.days = exports.hours = exports.minutes = exports.seconds = exports.groupBy = exports.endingAt = void 0;
exports.POSITIVE_INTEGER = exports.ENTRY_NUMBER = exports.VALUE_MUST_NUMBER = exports.PivotTableErrorMessage_ExistTable = exports.TIME_LEVEL_DAYS = exports.TIME_LEVEL_MONTHS = exports.TIME_LEVEL_QUARTERS = exports.TIME_LEVEL_YEARS = exports.AllDatesIn_December = exports.AllDatesIn_November = exports.AllDatesIn_October = exports.AllDatesIn_September = exports.AllDatesIn_August = exports.AllDatesIn_July = exports.AllDatesIn_June = exports.AllDatesIn_May = exports.AllDatesIn_April = exports.AllDatesIn_March = exports.AllDatesIn_February = exports.AllDatesIn_January = exports.AllDatesIn_Quarter4 = exports.AllDatesIn_Quarter3 = exports.AllDatesIn_Quarter2 = exports.AllDatesIn_Quarter1 = exports.DateNotBetween = exports.DateOlderThanOrEqual = exports.DateOlderThan = exports.DateNewerThanOrEqual = exports.DateNewerThan = exports.DateNotEqual = exports.QUARTER_MAP = exports.INVALID_DATE_SELECTION = exports.ALL_PERIODS = exports.ALL_DATES_IN = void 0;
exports.RepeatName = "\ubc18\ubcf5 \uc774\ub984";
exports.NotExist = "\uc5c6\uc74c";
exports.SubtotalType_Prefix_0 = "\ud3c9\uade0: ";
exports.SubtotalType_Prefix_1 = "\uac1c\uc218: ";
exports.SubtotalType_Prefix_2 = "\uc22b\uc790 \uac1c\uc218: ";
exports.SubtotalType_Prefix_3 = "\ucd5c\ub300: ";
exports.SubtotalType_Prefix_4 = "\ucd5c\uc18c: ";
exports.SubtotalType_Prefix_5 = "\uacf1: ";
exports.SubtotalType_Prefix_6 = "\ud45c\ubcf8 \ud45c\uc900 \ud3b8\ucc28: ";
exports.SubtotalType_Prefix_7 = "\ud45c\uc900 \ud3b8\ucc28: ";
exports.SubtotalType_Prefix_8 = "\ud569\uacc4: ";
exports.SubtotalType_Prefix_9 = "\ud45c\ubcf8 \ubd84\uc0b0: ";
exports.SubtotalType_Prefix_10 = "\ubaa8\uc9d1\ub2e8 \ubd84\uc0b0: ";
exports.ColumnLabels = "\uc5f4 \ub808\uc774\ube14";
exports.Total = "{0} \uc694\uc57d{1}";
exports.All = "\ubaa8\ub450";
exports.MultipleItems = "\uc5ec\ub7ec \ud56d\ubaa9";
exports.RowLabels = "\ud589 \ub808\uc774\ube14";
exports.GrandTotal = "\ucd1d\ud569\uacc4";
exports.Values = "\uac12";
exports.PivotPanel_Title = "\ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc";
exports.PivotPanel_ReportText = "\ubcf4\uace0\uc11c\uc5d0 \ucd94\uac00\ud560 \ud544\ub4dc \uc120\ud0dd";
exports.PivotPanel_SearchPlaceholder = "\uac80\uc0c9";
exports.PivotPanel_FieldAreaText = "\uc544\ub798 \uc601\uc5ed \uc0ac\uc774\uc5d0 \ud544\ub4dc\ub97c \ub04c\uc5b4 \ub193\uc73c\uc2ed\uc2dc\uc624.";
exports.PivotPanel_FiltersItemsTitle = "\ud544\ud130";
exports.PivotPanel_RowsItemsTitle = "\ud589";
exports.PivotPanel_ColumnsItemsTitle = "\uc5f4";
exports.PivotPanel_ValuesItemsTitle = "\uac12";
exports.PivotPanel_DeferUpdateText = "\ub098\uc911\uc5d0 \ub808\uc774\uc544\uc6c3 \uc5c5\ub370\uc774\ud2b8";
exports.EmptyPivotTable_PromptMessage1 = "\ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub85c \uc791\uc5c5\ud558\ub824\uba74 \uc774 \uc601\uc5ed\uc744 \ud074\ub9ad\ud558\uc2ed\uc2dc\uc624.";
exports.EmptyPivotTable_PromptMessage2 = "\ud53c\ubc97 \ud14c\uc774\ube14\ub85c \uc791\uc5c5\ud558\ub824\uba74 \ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc \ubaa9\ub85d\uc744 \ucf1c\uc2ed\uc2dc\uc624.";
exports.PivotPanel_Update = "\uc5c5\ub370\uc774\ud2b8";
exports.PivotPanel_DropDownList_Up = "\uc704\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_Down = "\uc544\ub798\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_Beginning = "\ucc98\uc74c\uc73c\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_End = "\ub05d\uc73c\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_ReportFilter = "\ubcf4\uace0\uc11c \ud544\ud130\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_Row = "\ud589 \ub808\uc774\ube14\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_Col = "\uc5f4 \ub808\uc774\ube14\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_Values = "\uac12\uc73c\ub85c \uc774\ub3d9";
exports.PivotPanel_DropDownList_Remove = "\ud544\ub4dc \uc81c\uac70";
exports.PivotPanel_DropDownList_Set = "\ud544\ub4dc \uc124\uc815...";
exports.PivotPanel_DropDownList_ValueSet = "\uac12 \ud544\ub4dc \uc124\uc815...";
exports.PivotPanel_Fields_MoveTo_ReportField = "\ubcf4\uace0\uc11c \ud544\ud130\uc5d0 \ucd94\uac00";
exports.PivotPanel_Fields_MoveTo_RowLabel = "\ud589 \ub808\uc774\ube14\uc5d0 \ucd94\uac00";
exports.PivotPanel_Fields_MoveTo_ColLabel = "\uc5f4 \ub808\uc774\ube14\uc5d0 \ucd94\uac00";
exports.PivotPanel_Fields_MoveTo_Value = "\uac12\uc5d0 \ucd94\uac00";
exports.PivotPanel_LabelOrValue_ClearFilter = "\ud544\ud130 \uc9c0\uc6b0\uae30";
exports.PivotPanel_ValueFilterOrLabel_Equals = "\uac19\uc74c...";
exports.PivotPanel_ValueFilterOrLabel_NotEqual = "\uac19\uc9c0 \uc54a\uc74c...";
exports.PivotPanel_ValueFilterOrLabel_GreaterThan = "\ubcf4\ub2e4 \ud07c...";
exports.PivotPanel_ValueFilterOrLabel_GreaterOrTo = "\ubcf4\ub2e4 \ud06c\uac70\ub098 \uac19\uc74c...";
exports.PivotPanel_ValueFilterOrLabel_LessThan = "\ubcf4\ub2e4 \uc791\uc74c...";
exports.PivotPanel_ValueFilterOrLabel_LessOrTo = "\uc791\uac70\ub098 \uac19\uc74c...";
exports.PivotPanel_ValueFilterOrLabel_Between = "\ud574\ub2f9 \ubc94\uc704...";
exports.PivotPanel_ValueFilterOrLabel_NotBetween = "\uc81c\uc678 \ubc94\uc704...";
exports.PivotPanel_ValueFilter_Top10 = "\uc0c1\uc704 10...";
exports.PivotPanel_ValueFilterOrLabel_IsBeginWith = '\uc2dc\uc791 \ubb38\uc790...';
exports.PivotPanel_ValueFilterOrLabel_NotBegin = '\uc81c\uc678\ud560 \uc2dc\uc791 \ubb38\uc790...';
exports.PivotPanel_ValueFilterOrLabel_End = '\ub05d \ubb38\uc790...';
exports.PivotPanel_ValueFilterOrLabel_NotEndWith = '\uc81c\uc678\ud560 \ub05d \ubb38\uc790';
exports.PivotPanel_ValueFilterOrLabel_Contain = '\ud3ec\ud568...';
exports.PivotPanel_ValueFilterOrLabel_NotContain = '\ud3ec\ud568\ud558\uc9c0 \uc54a\uc74c...';
exports.ValueFilterOrLabel_Equals = "\uac19\uc74c";
exports.ValueFilterOrLabel_NotEqual = "\uac19\uc9c0 \uc54a\uc74c";
exports.ValueFilterOrLabel_GreaterThan = "\ubcf4\ub2e4 \ud07c";
exports.ValueFilterOrLabel_GreaterOrTo = "\ud06c\uac70\ub098 \uac19\uc74c";
exports.ValueFilterOrLabel_LessThan = "\ubcf4\ub2e4 \uc791\uc74c";
exports.ValueFilterOrLabel_LessOrTo = "\uc791\uac70\ub098 \uac19\uc74c";
exports.ValueFilterOrLabel_Between = "\ud574\ub2f9 \ubc94\uc704";
exports.ValueFilterOrLabel_NotBetween = "\uc81c\uc678 \ubc94\uc704";
exports.ValueFilterOrLabelSelect_GreaterThan = "\ubcf4\ub2e4 \ud07c";
exports.ValueFilterOrLabelSelect_GreaterOrTo = "\ud06c\uac70\ub098 \uac19\uc74c";
exports.ValueFilterOrLabelSelect_LessThan = "\ubcf4\ub2e4 \uc791\uc74c";
exports.ValueFilterOrLabelSelect_LessOrTo = "\uc791\uac70\ub098 \uac19\uc74c";
exports.ValueFilterOrLabelSelect_Between = "\ud574\ub2f9 \ubc94\uc704";
exports.ValueFilterOrLabelSelect_NotBetween = "\uc81c\uc678 \ubc94\uc704";
exports.ValueFilterOrLabel_IsBeginWith = '\uc2dc\uc791 \ubb38\uc790';
exports.ValueFilterOrLabel_NotBegin = '\uc81c\uc678\ud560 \uc2dc\uc791 \ubb38\uc790';
exports.ValueFilterOrLabel_End = '\ub05d \ubb38\uc790';
exports.ValueFilterOrLabel_NotEndWith = '\uc81c\uc678\ud560 \ub05d \ubb38\uc790';
exports.ValueFilterOrLabel_Contain = '\ud3ec\ud568';
exports.ValueFilterOrLabel_NotContain = '\ud3ec\ud568\ud558\uc9c0 \uc54a\uc74c';
exports.PivotPanel_Date_Filter = '\ub0a0\uc9dc \ud544\ud130';

var lr = __webpack_require__(/*! lrPtKo */ "./dist/plugins/pivot/lr.ko.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}
exports.PivotPanel_ValueFilterOrLabel_Before = '\uc774\uc804...';
exports.PivotPanel_ValueFilterOrLabel_After = '\uc774\ud6c4...';
exports.PivotPanel_ValueFilterOrLabel_Tomorrow = '\ub0b4\uc77c';
exports.PivotPanel_ValueFilterOrLabel_Today = '\uc624\ub298';
exports.PivotPanel_ValueFilterOrLabel_Yesterday = '\uc5b4\uc81c';
exports.PivotPanel_ValueFilterOrLabel_NextWeek = '\ub2e4\uc74c \uc8fc';
exports.PivotPanel_ValueFilterOrLabel_ThisWeek = '\uc774\ubc88 \uc8fc';
exports.PivotPanel_ValueFilterOrLabel_LastWeek = '\uc9c0\ub09c \uc8fc';
exports.PivotPanel_ValueFilterOrLabel_NextMonth = '\ub2e4\uc74c \ub2ec';
exports.PivotPanel_ValueFilterOrLabel_ThisMonth = '\uc774\ubc88 \ub2ec';
exports.PivotPanel_ValueFilterOrLabel_LastMonth = '\uc9c0\ub09c \ub2ec';
exports.PivotPanel_ValueFilterOrLabel_NextQuarter = '\ub2e4\uc74c \ubd84\uae30';
exports.PivotPanel_ValueFilterOrLabel_ThisQuarter = '\uc774\ubc88 \ubd84\uae30';
exports.PivotPanel_ValueFilterOrLabel_LastQuarter = '\uc9c0\ub09c \ubd84\uae30';
exports.PivotPanel_ValueFilterOrLabel_NextYear = '\ub0b4\ub144';
exports.PivotPanel_ValueFilterOrLabel_ThisYear = '\uc62c\ud574';
exports.PivotPanel_ValueFilterOrLabel_LastYear = '\uc791\ub144';
exports.PivotPanel_ValueFilterOrLabel_YearToDate = '\uc5f0\uac04 \ub204\uacc4';
exports.PivotPanel_ValueFilterOrLabel_MonthToDate = '\uc6d4\uac04 \ub204\uacc4';
exports.PivotPanel_ValueFilterOrLabel_QuarterToDate = '\ubd84\uae30\ubcc4 \ub204\uacc4';
exports.PivotPanel_ValueFilterOrLabel_ParallelYearToDate = '\uc5f0\uc77c\ubcd1\ub82c';
exports.PivotPanel_ValueFilterOrLabel_ParallelMonthToDate = '\uc6d4\uc77c\ubcd1\ub82c';
exports.PivotPanel_ValueFilterOrLabel_ParallelQuarterToDate = '\ubd84\uae30\uc77c\ubcd1\ub82c';
exports.PivotPanel_ValueFilterOrLabel_AllDates = '\ud574\ub2f9 \uae30\uac04\uc758 \ubaa8\ub4e0 \ub0a0\uc9dc';
exports.PivotPanel_ValueFilterOrLabel_Custom = '\uc0ac\uc6a9\uc790 \uc9c0\uc815 \ud544\ud130...';
exports.PivotPanel_Sort_A_Z = '\uc624\ub984\ucc28\uc21c \uc815\ub82c';
exports.PivotPanel_Sort_Z_A = '\ub0b4\ub9bc\ucc28\uc21c \uc815\ub82c';
exports.PivotPanel_Sort_Smallest_Largest = '\uc22b\uc790 \uc624\ub984\ucc28\uc21c \uc815\ub82c';
exports.PivotPanel_Sort_Largest_Smallest = '\uc22b\uc790 \ub0b4\ub9bc\ucc28\uc21c \uc815\ub82c';
exports.PivotPanel_Sort_More = '\uae30\ud0c0 \uc815\ub82c \uc635\uc158...';
exports.PivotPanel_Filter_Clear = '{0}\uc5d0\uc11c \ud544\ud130 \uc9c0\uc6b0\uae30';
exports.PivotPanel_Filter_Label = '\ub808\uc774\ube14 \ud544\ud130';
exports.PivotPanel_Filter_Value = '\uac12 \ud544\ud130';
exports.Ok = '\ud655\uc778';
exports.Cancel = '\ucde8\uc18c';
exports.NumberFormat = '\uc22b\uc790 \uc11c\uc2dd';
exports.LabelFormat = '\ub808\uc774\ube14 \uc11c\uc2dd';
exports.Number = '\uc22b\uc790';
exports.FormatCells = '\uc140 \uc11c\uc2dd';
exports.Sample = '\uc0d8\ud50c';
exports.Type = '\uc720\ud615';
exports.CustomFormats = [
    "\uc77c\ubc18",
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
exports.Select_Field = '\ud544\ub4dc \uc120\ud0dd';
exports.PivotPanel_Filter_SelectAll = '\ubaa8\ub450 \uc120\ud0dd';
exports.PivotPanel_Filter_NoSelectAll = '\ubaa8\ub450 \uc120\ud0dd \ucde8\uc18c';
exports.PivotPanel_Filter_Search = '\uac80\uc0c9';
exports.Label_Title = '\ub808\uc774\ube14 \ud544\ud130';
exports.Label_Show = '\ub808\uc774\ube14\uc774 \ub2e4\uc74c \uc870\uac74\uc5d0 \ub9de\ub294 \ud56d\ubaa9 \ud45c\uc2dc';
exports.Use_Single_Character = '? \uae30\ud638\ub97c \uc0ac\uc6a9\ud558\uc5ec \ud55c \ubb38\uc790\ub97c \ub098\ud0c0\ub0bc \uc218 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Use_Series_Characters = '* \uae30\ud638\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc5ec\ub7ec \ubb38\uc790\ub97c \ub098\ud0c0\ub0bc \uc218 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Value_Show = '\ub2e4\uc74c \uc870\uac74\uc5d0 \ub9de\ub294 \ud56d\ubaa9 \ud45c\uc2dc';
exports.Value_Title = '\uac12 \ud544\ud130';
exports.Top_Ten_Filter = '\uc0c1\uc704 10\uac1c \ud544\ud130';
exports.TopTenShow = '\ud45c\uc2dc';
exports.Sort_Field_Dialog_Title = "\uc815\ub82c";
exports.Sort_Field_Dialog_Sort_Options = "\uc815\ub82c \uc635\uc158";
exports.Sort_Field_Dialog_Summary = "\uc694\uc57d \uc815\ubcf4";
exports.Sort_Field_Dialog_Ascending_Sort = "\uc624\ub984\ucc28\uc21c \uae30\uc900:";
exports.Sort_Field_Dialog_Descending_Sort = "\ub0b4\ub9bc\ucc28\uc21c \uae30\uc900:";
exports.Sort_Field_Dialog_More_Options = "\uae30\ud0c0 \uc635\uc158...";
exports.Sort_Field_More_Dialog_Title = "\uae30\ud0c0 \uc815\ub82c \uc635\uc158";
exports.Sort_Field_More_Dialog_Sort_By = "\uc815\ub82c \uae30\uc900";
exports.Sort_Field_More_Dialog_Values_In_Selected_Area = "\uc120\ud0dd\ud55c {{AREA}}\uc758 \uac12";
exports.Sort_Value_Dialog_Title = "\uac12 \uae30\uc900 \uc815\ub82c";
exports.Sort_Value_Dialog_Sort_Direction = "\uc815\ub82c \ubc29\ud5a5";
exports.Sort_Value_Dialog_Asc = "\uc624\ub984\ucc28\uc21c";
exports.Sort_Value_Dialog_Desc = "\ub0b4\ub9bc\ucc28\uc21c";
exports.Sort_Value_Dialog_Top_To_Bottom = "\uc704\ucabd\uc5d0\uc11c \uc544\ub798\ucabd";
exports.Sort_Value_Dialog_Left_To_Right = "\uc67c\ucabd\uc5d0\uc11c \uc624\ub978\ucabd";
exports.Custom_Sort_Summary = "\uc0ac\uc6a9\uc790 \uc9c0\uc815 \uc815\ub82c";
exports.Sort_Order_ASC = "\uc624\ub984\ucc28\uc21c";
exports.Sort_Order_DESC = "\ub0b4\ub9bc\ucc28\uc21c";
exports.Sort_In_Row = "\ud589";
exports.Sort_In_Column = "\uc5f4";
exports.Sort_Summary_Pattern = "{{FIELD_NAME}}\uc744(\ub97c) {{ORDER}}\uc73c\ub85c \uc815\ub82c";
exports.Sort_Value_Summary_Pattern = "{{SELECTED_FIELD_NAME}}\uc744(\ub97c) \uae30\uc900\uc73c\ub85c {{FIELD_NAME}}\uc744(\ub97c) {{ORDER}}\uc73c\ub85c \uc815\ub82c";
exports.Sort_More_Summary_Pattern = "{{SELECTED_FIELD_NAME}}\uc744(\ub97c) \uae30\uc900\uc73c\ub85c {{FIELD_NAME}}\uc744(\ub97c) \uc774 {{ROW_COLUMN}}\uc758 \uac12\uc744 \uc0ac\uc6a9\ud558\uc5ec {{ORDER}}\uc73c\ub85c \uc815\ub82c:\r\n";
exports.Invalid_Sort_Range = "\uc815\ub82c \ucc38\uc870\uac00 \uc720\ud6a8\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4. \uc815\ub82c\ud558\ub824\ub294 \ub370\uc774\ud130 \ubc94\uc704 \ub0b4\uc5d0 \uc788\ub294\uc9c0 \ud655\uc778\ud558\uace0, \uccab \ubc88\uc9f8 \uc815\ub82c \uae30\uc900 \uc0c1\uc790\uac00 \ub3d9\uc77c\ud558\uc9c0 \uc54a\uac70\ub098 \ube44\uc5b4\uc788\uc9c0 \uc54a\uc740\uc9c0 \ud655\uc778\ud558\uc138\uc694";
exports.Invalid_Field_Sort_By = "\uc815\ub82c \uae30\uc900\uc73c\ub85c \uc0ac\uc6a9\ud560 \ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc\ub97c \uc815\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.Top = '\uc704\ucabd';
exports.Bottom = '\uc544\ub798\ucabd';
exports.Top_Item = '\ud56d\ubaa9';
exports.Top_Percent = '\ubc31\ubd84\uc728';
exports.Top_Sum = '\ud569\uacc4';
exports.ByJoiner = "by";
exports.AndJoiner = "and";
exports.Q1 = '1\uc0ac\ubd84\uae30';
exports.Q2 = '2\uc0ac\ubd84\uae30';
exports.Q3 = '3\uc0ac\ubd84\uae30';
exports.Q4 = '4\uc0ac\ubd84\uae30';
exports.Jan = '1\uc6d4';
exports.Feb = '2\uc6d4';
exports.Mar = '3\uc6d4';
exports.Apr = '4\uc6d4';
exports.May = '5\uc6d4';
exports.Jun = '6\uc6d4';
exports.Jul = '7\uc6d4';
exports.Aug = '8\uc6d4';
exports.Sep = '9\uc6d4';
exports.Oct = '10\uc6d4';
exports.Nov = '11\uc6d4';
exports.Dec = '12\uc6d4';
exports.IsBefore = '\uc774\uc804';
exports.IsBeforeOrEqual = '\uc774\uc804 \ub610\ub294 \uac19\uc74c';
exports.IsAfter = '\uc774\ud6c4';
exports.IsAfterOrEqual = '\uc774\ud6c4 \ub610\ub294 \uac19\uc74c';
exports.IsBetween = '\ud574\ub2f9 \ubc94\uc704';
exports.IsNotBetween = '\uc81c\uc678 \ubc94\uc704';
exports.DateFilterTitle = '\ub0a0\uc9dc \ud544\ud130';
exports.DateShow = '\ub2e4\uc74c \uc870\uac74\uc5d0 \ub9de\ub294 \ub0a0\uc9dc \ud45c\uc2dc';
exports.WholeDays = '\uc804\uccb4 \uc77c\uc218';
exports.FieldSetting = "\ud544\ub4dc \uc124\uc815";
exports.ValueSetting = '\uac12 \ud544\ub4dc \uc124\uc815';
exports.SourceName = '\uc6d0\ubcf8 \uc774\ub984:';
exports.CustomName = '\uc0ac\uc6a9\uc790 \uc815\uc758 \uc774\ub984:';
exports.SummarizeValue = '\uac12 \uc694\uc57d \uae30\uc900';
exports.ShowValueAs = '\uac12 \ud45c\uc2dc \ud615\uc2dd';
exports.CalculationForDialog = '\uacc4\uc0b0: ';
exports.BaseFieldForDialog = "\uae30\ubcf8 \ud544\ub4dc: ";
exports.BaseItemForDialog = "\uae30\ubcf8 \ud56d\ubaa9: ";
exports.BaseItemNext = "(\ub2e4\uc74c)";
exports.BaseItemPrevious = "(\uc774\uc804)";
exports.ShowValueAsField = '\uac12 \ud45c\uc2dc \ud615\uc2dd';
exports.SummarizeValueField = '\uac12 \ud544\ub4dc \uc694\uc57d \uae30\uc900';
exports.ChooseType = '\uc120\ud0dd\ud55c \ud544\ub4dc\uc758 \ub370\uc774\ud130\ub97c \uc694\uc57d\ud558\ub294 \ub370 \uc0ac\uc6a9\ud560 \uacc4\uc0b0 \uc720\ud615\uc744 \uc120\ud0dd\ud569\ub2c8\ub2e4.';
exports.Sum = '\ud569\uacc4';
exports.Count = '\uac1c\uc218';
exports.Average = '\ud3c9\uade0';
exports.Max = '\ucd5c\ub300';
exports.Min = '\ucd5c\uc18c';
exports.Product = '\uacf1';
exports.CountNumbers = '\uc22b\uc790 \uac1c\uc218';
exports.StdDev = '\ud45c\ubcf8 \ud45c\uc900 \ud3b8\ucc28';
exports.StdDevp = '\ud45c\uc900 \ud3b8\ucc28';
exports.Var = '\ud45c\ubcf8 \ubd84\uc0b0';
exports.Varp = '\ubd84\uc0b0';
exports.sigmaValue = "\u2211 \uac12";
exports.sigmaValueTemp = "\uac12";
exports.DateFormatError = "\uc720\ud6a8\ud55c \ub0a0\uc9dc\uac00 \uc544\ub2d9\ub2c8\ub2e4.";
exports.NoHaveSpread = "\ud1b5\ud569 \ubb38\uc11c \uc608\uc678";
exports.SourceError = "\uc18c\uc2a4 \ub370\uc774\ud130 \uc608\uc678, \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub97c \ub9cc\ub4e4 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.SourceNotIsTableNameOrFormula = "\uc6d0\ubcf8 \ub370\uc774\ud130\uac00 \ud45c \uc774\ub984 \ubc0f \ud568\uc218\uac00 \uc544\ub2d9\ub2c8\ub2e4.";
exports.SourceDataOnlyOne = "\uc774 \uba85\ub839\uc5d0\ub294 \uc6d0\ubcf8 \ub370\uc774\ud130\uc758 \ud589\uc774 \ub450 \uac1c \uc774\uc0c1 \ud544\uc694\ud569\ub2c8\ub2e4. \ud589\uc5d0\uc11c\ub9cc \uc120\ud0dd \ud56d\ubaa9\uc5d0 \uba85\ub839\uc744 \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.FieldAreaLimited = "\uc774\ub3d9 \uc911\uc778 \ud544\ub4dc\ub294 \ubcf4\uace0\uc11c\uc758 \ud574\ub2f9 \uc601\uc5ed\uc5d0 \ubc30\uce58\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.DefaultPivotTableViewName = "\ud53c\ubc97 \ubcf4\uae30";
exports.noCalculation = "\uacc4\uc0b0 \uc5c6\uc74c";
exports.percentGrandTotal = "\ucd1d\ud569\uacc4 \ube44\uc728";
exports.percentColumnTotal = "\uc5f4 \ud569\uacc4 \ube44\uc728";
exports.percentRowTotal = "\ud589 \ud569\uacc4 \ube44\uc728";
exports.percentEllipsis = "[\uae30\uc900\uac12]\uc5d0 \ub300\ud55c \ube44\uc728...";
exports.percentParentRowTotal = "\uc0c1\uc704 \ud589 \ud569\uacc4 \ube44\uc728";
exports.percentParentColumnTotal = "\uc0c1\uc704 \uc5f4 \ud569\uacc4 \ube44\uc728";
exports.percentParentTotal = "\uc0c1\uc704 \ud569\uacc4 \ube44\uc728...";
exports.difference = "[\uae30\uc900\uac12]\uacfc\uc758 \ucc28\uc774...";
exports.percentDifference = "[\uae30\uc900\uac12]\uc5d0 \ub300\ud55c \ube44\uc728\uc758 \ucc28\uc774...";
exports.runningTotal = "\ub204\uacc4...";
exports.percentRunningTotal = "\ub204\uacc4 \ube44\uc728...";
exports.rankSmallestLargest = "\uc624\ub984\ucc28\uc21c \uc21c\uc704 \uc9c0\uc815...";
exports.rankLargestSmallest = "\ub0b4\ub9bc\ucc28\uc21c \uc21c\uc704 \uc9c0\uc815...";
exports.index = "\uc778\ub371\uc2a4";
exports.showValueAsDialog = [
    '', '', '', '',
    "[\uae30\uc900\uac12]\uc5d0 \ub300\ud55c \ube44\uc728 ",
    '', '',
    "\uc0c1\uc704 \ud569\uacc4 \ube44\uc728 ",
    "[\uae30\uc900\uac12]\uacfc\uc758 \ucc28\uc774 ",
    "[\uae30\uc900\uac12]\uc5d0 \ub300\ud55c \ube44\uc728\uc758 \ucc28\uc774 ",
    "\ub204\uacc4 ",
    "\ub204\uacc4 \ube44\uc728 ",
    "\uc624\ub984\ucc28\uc21c \uc21c\uc704 \uc9c0\uc815 ",
    "\ub0b4\ub9bc\ucc28\uc21c \uc21c\uc704 \uc9c0\uc815 ",
    '',
];
exports.baseField = "\uae30\ubcf8 \ud544\ub4dc:";
exports.baseItem = "\uae30\ubcf8 \ud56d\ubaa9:";
exports.grouping = "\uadf8\ub8f9\ud654";
exports.auto = "\uc790\ub3d9";
exports.startingAt = "\uc2dc\uc791:";
exports.endingAt = "\ub05d:";
exports.groupBy = "\ub2e8\uc704:";
exports.seconds = "\ucd08";
exports.minutes = "\ubd84";
exports.hours = "\uc2dc";
exports.days = "\uc77c";
exports.months = "\uc6d4";
exports.quarters = "\ubd84\uae30";
exports.years = "\uc5f0";
exports.numberOfDays = "\ub0a0\uc9dc \uc218";
exports.Group = "\uadf8\ub8f9";
exports.param_error = "\ub9e4\uac1c \ubcc0\uc218\uac00 null\uc774\uac70\ub098 \uc815\uc758\ub418\uc9c0 \uc54a\uc558\uc2b5\ub2c8\ub2e4.";
exports.EmptyValueFieldError = "\uac12 \ud544\ud130\ub97c \uc801\uc6a9\ud558\ub824\uba74 \uac12 \uc601\uc5ed\uc5d0 \ud558\ub098 \uc774\uc0c1\uc758 \ud544\ub4dc\uac00 \ud544\uc694\ud569\ub2c8\ub2e4.";
exports.toolTipContent_Row = "\ud589: ";
exports.toolTipContent_Column = "\uc5f4: ";
exports.toolTipContent_Value = "\uac12: ";
exports.toolTipContent_NoValue = "\uac12 \uc5c6\uc74c";
exports.deferLayoutUpdate = "\ub098\uc911\uc5d0 \ub808\uc774\uc544\uc6c3 \uc5c5\ub370\uc774\ud2b8";
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
exports.Views = "\ubcf4\uae30";
exports.PivotTableErrorMessage_ShowDetail = "\uc774 \uc120\ud0dd \ud56d\ubaa9\uc5d0 \ub300\ud55c \uc138\ubd80 \uc815\ubcf4\ub97c \ud45c\uc2dc\ud558\uac70\ub098 \uc228\uae38 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableErrorMessage_MakeChange = "\ud53c\ubc97 \ud14c\uc774\ube14\uc5d0 \uc601\ud5a5\uc744 \uc8fc\uae30 \ub54c\ubb38\uc5d0 \uc120\ud0dd\ud55c \uc140\uc5d0 \uc774 \ubcc0\uacbd\uc744 \uc801\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ud544\ub4dc \ubaa9\ub85d\uc744 \uc0ac\uc6a9\ud558\uc5ec \ubcf4\uace0\uc11c\ub97c \ubcc0\uacbd\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4. \uc140\uc744 \uc0bd\uc785\ud558\uac70\ub098 \uc0ad\uc81c\ud558\ub824\uba74 \ud53c\ubc97 \ud14c\uc774\ube14\uc744 \uc62e\uae30\uace0 \ub2e4\uc2dc \uc2dc\ub3c4\ud558\uc138\uc694.";
exports.PivotTableErrorMessage_ExistData = "{}\uc5d0 \uc774\ubbf8 \ub370\uc774\ud130\uac00 \uc788\uc2b5\ub2c8\ub2e4. \uad50\uccb4\ud558\uc2dc\uaca0\uc2b5\ub2c8\uae4c?";
exports.PivotTableErrorMessage_EditWhenDefer = "[\ub098\uc911\uc5d0 \ub808\uc774\uc544\uc6c3 \uc5c5\ub370\uc774\ud2b8] \ud655\uc778\ub780\uc744 \uc120\ud0dd\ud558\uba74 \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub97c \ud3b8\uc9d1\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ubcf4\uace0\uc11c\ub97c \ud3b8\uc9d1\ud558\ub824\uba74 \ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc \ubaa9\ub85d \uc544\ub798\ucabd\uc5d0 \uc788\ub294 [\ub098\uc911\uc5d0 \ub808\uc774\uc544\uc6c3 \uc5c5\ub370\uc774\ud2b8] \ud655\uc778\ub780\uc758 \uc120\ud0dd\uc744 \ucde8\uc18c\ud558\uc2ed\uc2dc\uc624.";
exports.PivotTableErrorMessage_DuplicatedFieldName = "\ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc \uc774\ub984\uc774 \uc774\ubbf8 \uc788\uc2b5\ub2c8\ub2e4.";
exports.PivotTableErrorMessage_EmptyFieldName = "\ud53c\ubc97 \ud14c\uc774\ube14 \ub9ac\ud3ec\ud2b8\uc5d0 null \uac12\uc744 \ud56d\ubaa9 \ub610\ub294 \ud544\ub4dc \uc774\ub984\uc73c\ub85c \uc785\ub825\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableErrorMessage_Protect = "\ubcf4\ud638\ub41c \uc2dc\ud2b8\uc5d0 \ub3d9\uc77c\ud55c \uc6d0\ubcf8 \ub370\uc774\ud130\ub97c \uae30\ubc18\uc73c\ub85c \ud558\ub294 \ub2e4\ub978 \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\uac00 \ud3ec\ud568\ub418\uc5b4 \uc788\ub294 \ub3d9\uc548\uc5d0\ub294 \uc774 \uba85\ub839\uc744 \uc218\ud589\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ub2e4\ub978 \ubcf4\uace0\uc11c\uac00 \uc788\ub294 \uc2dc\ud2b8\uc5d0\uc11c \ubcf4\ud638\ub97c \uc81c\uac70\ud558\ub824\uba74 \uc2dc\ud2b8 \ud0ed\uc744 \ud074\ub9ad\ud55c \ub2e4\uc74c \uc2dc\ud2b8 \ubcf4\ud638 \ud574\uc81c(\uac80\ud1a0 \ud0ed, \ubcc0\uacbd \uadf8\ub8f9)\ub97c \ud074\ub9ad\ud569\ub2c8\ub2e4. \uadf8\ub7f0 \ub2e4\uc74c \uba85\ub839\uc744 \ub2e4\uc2dc \uc2dc\ub3c4\ud558\uc2ed\uc2dc\uc624.";
exports.PivotTableErrorMessage_EmptySourceFieldName = "\ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc \uc774\ub984\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4. \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub97c \uc791\uc131\ud558\ub824\uba74 \uc5f4 \uc774\ub984\uacfc \ubaa9\ub85d\uc73c\ub85c \ub41c \ub370\uc774\ud130\ub97c \uc0ac\uc6a9\ud574\uc57c \ud569\ub2c8\ub2e4. \ud53c\ubc97 \ud14c\uc774\ube14 \ud544\ub4dc \uc774\ub984\uc744 \ubcc0\uacbd\ud558\ub824\uba74 \uc0c8 \ud544\ub4dc\uc5d0 \uc774\ub984\uc744 \uc785\ub825\ud558\uc2ed\uc2dc\uc624.";
exports.PivotTableErrorMessage_Overlap = "\ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub294 \uc11c\ub85c \uacb9\uce60 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableErrorMessage_InvalidChange = "\ud53c\ubc97 \ud14c\uc774\ube14\uc5d0\uc11c \uc774 \ubd80\ubd84\uc744 \ubcc0\uacbd\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableErrorMessage_InvalidGroup = "\uc120\ud0dd \ubc94\uc704\ub97c \uadf8\ub8f9\uc73c\ub85c \ubb36\uc744 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableErrorMessage_InvalidReference = "\ucc38\uc870\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.";
exports.PivotTableForAccessibility = "\ud53c\ubc97 \ud14c\uc774\ube14: \r\n\ud53c\ubc97\ud14c\uc774\ube14 \uc774\ub984: ";
exports.PivotTableCalcItemHasTowCacheField = "\ud53c\ubc97 \ud14c\uc774\ube14\uc758 \ud544\ub4dc\uc5d0 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc774 \ud558\ub098 \uc774\uc0c1 \uc788\uc73c\uba74 \ub370\uc774\ud130 \uc601\uc5ed \uacac\uc778 \ub610\ub294 \ub370\uc774\ud130 \uc601\uc5ed\uacfc \ub2e4\ub978 \uc601\uc5ed\uc5d0\uc11c \ud544\ub4dc\ub97c \ub3d9\uc2dc\uc5d0 \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ud544\ub4dc\ub97c \ucd94\uac00\ud558\ub824\ub294 \uacbd\uc6b0 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc744 \uc81c\uac70\ud558\uace0 \ud544\ub4dc\ub97c \ub2e4\uc2dc \ucd94\uac00\ud569\ub2c8\ub2e4. \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc744 \ucd94\uac00\ud558\ub824\ub294 \uacbd\uc6b0 \ud544\ub4dc\uac00 \ub450 \ubc88 \uc774\uc0c1 \uc0ac\uc6a9\ub418\uc9c0 \uc54a\ub3c4\ub85d \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub97c \ubcc0\uacbd\ud558\uace0 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc744 \ucd94\uac00\ud569\ub2c8\ub2e4.";
exports.PivotTableHasSameCalcItemName = "\uac19\uc740 \uc774\ub984\uc758 \ub2e4\ub978 \ud56d\ubaa9 \ub610\ub294 \ud544\ub4dc\uac00 \uc788\uc73c\ubbc0\ub85c \uacc4\uc0b0\ub41c \ud56d\ubaa9 \ub610\ub294 \ud544\ub4dc\ub97c \uc0dd\uc131\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableHasNumberOrDateGroup = "\uc774 \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c \ud544\ub4dc\uac00 \uadf8\ub8f9\ud654\ub429\ub2c8\ub2e4. \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc744 \uadf8\ub8f9\ud654\ub41c \ud544\ub4dc\uc5d0 \ucd94\uac00\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ud544\ub4dc\uc758 \uadf8\ub8f9\ud654\ub97c \ud574\uc81c\ud558\ub824\uba74 \ud574\ub2f9 \ud544\ub4dc\uac00 \ud589 \ub610\ub294 \uc5f4 \uc601\uc5ed\uc5d0 \uc788\ub294\uc9c0 \ud655\uc778\ud558\uace0 \uadf8\ub8f9\ud654\ub41c \ud544\ub4dc\uc758 \ud56d\ubaa9 \uc911 \ud558\ub098\ub97c \uc120\ud0dd\ud55c \ub2e4\uc74c \ubd84\uc11d \ud0ed\uc758 \uadf8\ub8f9 \uadf8\ub8f9\uc5d0\uc11c \uadf8\ub8f9 \ud574\uc81c \ub2e8\ucd94\ub97c \ud074\ub9ad\ud55c \ub2e4\uc74c \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc744 \uc0bd\uc785\ud55c \ud6c4 \ub2e4\uc2dc \uadf8\ub8f9\ud654\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4.";
exports.PivotTableSubtotalType = "\ud3c9\uade0, \ud45c\uc900 \ud3b8\ucc28 \ubc0f \ubd84\uc0b0\uc740 PivotTable \ubcf4\uace0\uc11c\uc5d0 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc774 \uc788\ub294 \uacbd\uc6b0 \uc9c0\uc6d0\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.";
exports.PivotTableHasSameItemName = "\uac19\uc740 \uc774\ub984\uc758 \ub2e4\ub978 \ud56d\ubaa9 \ub610\ub294 \ud544\ub4dc\uac00 \uc788\uc73c\ubbc0\ub85c \uacc4\uc0b0\ub41c \ud56d\ubaa9 \ub610\ub294 \ud544\ub4dc\ub97c \ub9cc\ub4e4 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.PivotTableCalcItemHasMultipleDataField = "\ud53c\ubc97 \ud14c\uc774\ube14 \ub9ac\ud3ec\ud2b8\uc5d0 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc774 \uc788\ub294 \uacbd\uc6b0 \ub3d9\uc77c\ud55c \ud544\ub4dc\uc758 \uc5ec\ub7ec \ub370\uc774\ud130 \ud544\ub4dc\ub294 \uc9c0\uc6d0\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.";
exports.PivotTableMoveCalcItemFieldToReport = "\uc774 \ud544\ub4dc\uc5d0\ub294 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc774 \ud3ec\ud568\ub418\uc5b4 \uc788\uc73c\ubbc0\ub85c \ub9ac\ud3ec\ud2b8 \ud544\ud130\uc5d0 \ubc30\uce58\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ub9ac\ud3ec\ud2b8 \ud544\ud130\uc5d0 \uc774 \ud544\ub4dc\ub97c \ubc30\uce58\ud558\ub824\uba74 \uba3c\uc800 \uacc4\uc0b0\ub41c \ud56d\ubaa9\uc744 \uc0ad\uc81c\ud558\uc2ed\uc2dc\uc624.";
exports.PivotTableErrorFormula = "\ud56d\ubaa9 \uc774\ub984\uc744 \ucc3e\uc744 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \uc774\ub984\uc744 \uc62c\ubc14\ub974\uac8c \uc785\ub825\ud588\uc73c\uba70 \ud56d\ubaa9\uc774 \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\uc5d0 \uc788\ub294\uc9c0 \ud655\uc778\ud569\ub2c8\ub2e4.";
exports.SlicerNameInFormula = "\uc2ac\ub77c\uc774\uc11c";
exports.search = "\uac80\uc0c9";
exports.AddSearchResult = "\ud544\ud130\uc5d0 \ud604\uc7ac \uc120\ud0dd \ud56d\ubaa9 \ucd94\uac00";
exports.Layout = "\ub808\uc774\uc544\uc6c3";
exports.showNoData = "\ub370\uc774\ud130\uac00 \uc5c6\ub294 \ud56d\ubaa9 \ud45c\uc2dc";
exports.showSubtotalTop = "\uac01 \uadf8\ub8f9 \uc0c1\ub2e8\uc5d0 \ubd80\ubd84\ud569 \ud45c\uc2dc";
exports.TIMELINE_PLACE_HOLDER = "{TL}";
exports.ALL_DATES_IN = "\ubaa8\ub4e0 \ub0a0\uc9dc";
exports.ALL_PERIODS = "\ubaa8\ub4e0 \uae30\uac04";
exports.INVALID_DATE_SELECTION = "\uc798\ubabb\ub41c \ub0a0\uc9dc \uc120\ud0dd!";
exports.QUARTER_MAP = ['', exports.Q1, exports.Q2, exports.Q3, exports.Q4];
exports.DateNotEqual = exports.TIMELINE_PLACE_HOLDER + " \uc81c\uc678";
exports.DateNewerThan = exports.TIMELINE_PLACE_HOLDER + " \uc774\ud6c4";
exports.DateNewerThanOrEqual = exports.TIMELINE_PLACE_HOLDER + " \uc774\ud6c4 \ub610\ub294 \uac19\uc74c";
exports.DateOlderThan = exports.TIMELINE_PLACE_HOLDER + " \uc774\uc804";
exports.DateOlderThanOrEqual = exports.TIMELINE_PLACE_HOLDER + " \uc774\uc804 \ub610\ub294 \uac19\uc74c";
exports.DateNotBetween = exports.TIMELINE_PLACE_HOLDER + " \uc0ac\uc774 \uc81c\uc678";
exports.AllDatesIn_Quarter1 = exports.Q1 + exports.ALL_DATES_IN;
exports.AllDatesIn_Quarter2 = exports.Q2 + exports.ALL_DATES_IN;
exports.AllDatesIn_Quarter3 = exports.Q3 + exports.ALL_DATES_IN;
exports.AllDatesIn_Quarter4 = exports.Q4 + exports.ALL_DATES_IN;
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
exports.TIME_LEVEL_YEARS = '\ub144';
exports.TIME_LEVEL_QUARTERS = '\ubd84\uae30';
exports.TIME_LEVEL_MONTHS = '\uc6d4';
exports.TIME_LEVEL_DAYS = '\uc77c';
exports.PivotTableErrorMessage_ExistTable = "\ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\ub294 \ud45c\uc640 \uacb9\uce60 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.";
exports.VALUE_MUST_NUMBER = "\uac12\uc740 \uc22b\uc790\uc5ec\uc57c \ud569\ub2c8\ub2e4.";
exports.ENTRY_NUMBER = "\uc798\ubabb\ub41c \uac12\uc744 \uc785\ub825\ud588\uc2b5\ub2c8\ub2e4. \uc815\uc218\ub098 \uc2e4\uc218\ub97c \uc785\ub825\ud574\uc57c\ud569\ub2c8\ub2e4.";
exports.POSITIVE_INTEGER = "0\ubcf4\ub2e4 \ud070 \uc218\ub97c \uc0ac\uc6a9\ud574\uc57c \ud569\ub2c8\ub2e4.";


/***/ }),

/***/ "./dist/plugins/print/print.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/print/print.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidSheetIndex = void 0;
exports.Exp_InvalidSheetIndex = '\uc798\ubabb\ub41c \uc2dc\ud2b8 \uc778\ub371\uc2a4\uc785\ub2c8\ub2e4.';


/***/ }),

/***/ "./dist/plugins/report/resources/lr.ko.js":
/*!************************************************!*\
  !*** ./dist/plugins/report/resources/lr.ko.js ***!
  \************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.wmk2 = exports.wmk1 = void 0;
exports.wmk1 = ["b9d3d200c2d2", "acecb820dcb8"];
exports.wmk2 = ["b9d3d200c2d2", "acecb820dcb8"];


/***/ }),

/***/ "./dist/plugins/report/resources/res.ko.js":
/*!*************************************************!*\
  !*** ./dist/plugins/report/resources/res.ko.js ***!
  \*************************************************/
/***/ (function(__unused_webpack_module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidParameterName = exports.configDataSetting = exports.summaryNoneTip = exports.summaryCountTip = exports.summaryMinTip = exports.summaryMaxTip = exports.summaryAvgTip = exports.summarySumTip = exports.summaryCellType = exports.groupCellType = exports.listCellType = exports.toolTipCellType = exports.toolTipFormula = exports.toolTipColumnName = exports.toolTipTableName = exports.ContainsInvalidCells = void 0;
exports.ContainsInvalidCells = '\ub9ac\ud3ec\ud2b8 \uc2dc\ud2b8\uc5d0 \uc720\ud6a8\ud558\uc9c0 \uc54a\uc740 \uc140 ({0})\uc774 \ud3ec\ud568\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4. \ud655\uc778\ud574\uc8fc\uc138\uc694.';
exports.toolTipTableName = "\ud45c: ";
exports.toolTipColumnName = "\uc5f4: ";
exports.toolTipFormula = "\uc218\uc2dd: ";
exports.toolTipCellType = "\ud615\uc2dd: ";
exports.listCellType = "\ub9ac\uc2a4\ud2b8";
exports.groupCellType = "\uadf8\ub8f9";
exports.summaryCellType = "\uc694\uc57d";
exports.summarySumTip = "(Sum)";
exports.summaryAvgTip = "(Avg)";
exports.summaryMaxTip = "(Max)";
exports.summaryMinTip = "(Min)";
exports.summaryCountTip = "(Count)";
exports.summaryNoneTip = "(None)";
exports.configDataSetting = '\ub370\uc774\ud130 \uc124\uc815\uc744 \uad6c\uc131\ud574 \uc8fc\uc138\uc694.';
exports.Exp_InvalidParameterName = '\uc798\ubabb\ub41c \ub9e4\uac1c\ubcc0\uc218 \uc774\ub984';

var lr = __webpack_require__(/*! lrRpKo */ "./dist/plugins/report/resources/lr.ko.js");
for (var p in lr) {
    if (lr.hasOwnProperty(p)) {
        exports[p] = lr[p];
    }
}


/***/ }),

/***/ "./dist/plugins/shape/shape.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/shape/shape.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidRange = exports.Exp_EmptyName = exports.Exp_DuplicatedName = exports.Exp_InvalidConnectionSite = void 0;
exports.Exp_InvalidConnectionSite = '\uc798\ubabb\ub41c \uc5f0\uacb0 \uc0ac\uc774\ud2b8\uc785\ub2c8\ub2e4.';
exports.Exp_DuplicatedName = "\uc911\ubcf5\ub41c \uc774\ub984\uc785\ub2c8\ub2e4.";
exports.Exp_EmptyName = "\ube48 \uc774\ub984\uc785\ub2c8\ub2e4.";
exports.Exp_InvalidRange = '\uc798\ubabb\ub41c \ubc94\uc704';


/***/ }),

/***/ "./dist/plugins/slicer/slicer.res.ko.js":
/*!**********************************************!*\
  !*** ./dist/plugins/slicer/slicer.res.ko.js ***!
  \**********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SlicerNameInFormula = exports.Exp_SlicerNameExist = exports.Exp_SlicerNameInvalid = exports.Blank = void 0;
exports.Blank = '(\uacf5\ubc31)';
exports.Exp_SlicerNameInvalid = '\uc2ac\ub77c\uc774\uc11c \uc774\ub984\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_SlicerNameExist = '\uc2ac\ub77c\uc774\uc11c \uc774\ub984\uc740 \uc774\ubbf8 \uc0ac\uc6a9\ub418\uace0 \uc788\uc2b5\ub2c8\ub2e4. \uace0\uc720\ud55c \uc774\ub984\uc744 \uc785\ub825\ud558\uc2ed\uc2dc\uc624.';
exports.SlicerNameInFormula = "\uc2ac\ub77c\uc774\uc11c";


/***/ }),

/***/ "./dist/plugins/statusBar/statusBar.res.ko.js":
/*!****************************************************!*\
  !*** ./dist/plugins/statusBar/statusBar.res.ko.js ***!
  \****************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.progressItem = exports.toolTipZoomPanel = exports.toolTipSlider = exports.toolTipZoomIn = exports.toolTipZoomOut = exports.toolTipFormulaSum = exports.toolTipFormulaMax = exports.toolTipFormulaMin = exports.toolTipFormulaNumericalCount = exports.toolTipFormulaCount = exports.toolTipFormulaAverage = exports.toolTipCellMode = exports.zoom = exports.zoomSlider = exports.formulaSum = exports.formulaMax = exports.formulaMin = exports.formulaNumericalCount = exports.formulaCount = exports.formulaAverage = exports.iterateCalculation = exports.inCalculation = exports.cellModeEdit = exports.cellModeEnter = exports.cellModeReady = exports.cellMode = void 0;
exports.cellMode = '\uc140 \ubaa8\ub4dc';
exports.cellModeReady = '\uc900\ube44';
exports.cellModeEnter = '\ub4e4\uc5b4\uac10';
exports.cellModeEdit = '\ud3b8\uc9d1';
exports.inCalculation = '\uacc4\uc0b0 \uc911: {0}';
exports.iterateCalculation = '\ubc18\ubcf5 \uacc4\uc0b0 \uc911: {0}';
exports.formulaAverage = '\ud3c9\uade0';
exports.formulaCount = '\uac1c\uc218';
exports.formulaNumericalCount = '\uc22b\uc790 \ub370\uc774\ud130 \uac1c\uc218';
exports.formulaMin = '\ucd5c\uc18c\uac12';
exports.formulaMax = '\ucd5c\ub300\uac12';
exports.formulaSum = '\ud569\uacc4';
exports.zoomSlider = '\ud655\ub300/\ucd95\uc18c \uc2ac\ub77c\uc774\ub354';
exports.zoom = '\ud655\ub300/\ucd95\uc18c';
exports.toolTipCellMode = '{0} \ubc29\ubc95';
exports.toolTipFormulaAverage = '\uc120\ud0dd\ub41c \uc140\uc758 \ud3c9\uade0';
exports.toolTipFormulaCount = '\ub370\uc774\ud130\uac00 \ud3ec\ud568 \ub41c \uc120\ud0dd\ub41c \uc140 \uc218';
exports.toolTipFormulaNumericalCount = '\uc22b\uc790 \ub370\uc774\ud130\uac00 \ud3ec\ud568 \ub41c \uc120\ud0dd\ub41c \uc140 \uc218';
exports.toolTipFormulaMin = '\uc120\ud0dd\uc758 \ucd5c\uc18c\uac12';
exports.toolTipFormulaMax = '\uc120\ud0dd\uc2dc \ucd5c\ub300 \uac12';
exports.toolTipFormulaSum = '\uc120\ud0dd\ub41c \uc140\uc758 \ud569';
exports.toolTipZoomOut = '\ucd95\uc18c';
exports.toolTipZoomIn = '\ud655\ub300';
exports.toolTipSlider = '\ud655\ub300/\ucd95\uc18c';
exports.toolTipZoomPanel = '\ud655\ub300/\ucd95\uc18c \ub808\ubca8';
exports.progressItem = '\uc9c4\ud589';


/***/ }),

/***/ "./dist/plugins/tableSheet/tableSheet.res.ko.js":
/*!******************************************************!*\
  !*** ./dist/plugins/tableSheet/tableSheet.res.ko.js ***!
  \******************************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.columnTypeFormula = exports.columnTypeText = exports.columnTypeNumber = exports.defineColumnExisted = exports.defineColumnRequired = exports.defineColumnNoResults = exports.defineColumnAll = exports.defineColumnNone = exports.defineColumnFormula = exports.defineColumnOthers = exports.defineColumnDefaultValue = exports.defineColumnConfiguration = exports.defineColumnFormatting = exports.defineColumnType = exports.defineColumnCaption = exports.defineColumnValue = exports.submit = exports.cancel = exports.defineColumn = exports.Exp_InvalidOperationInProtectForTableSheet = exports.CrossColumnDetailList = exports.CrossColumnDetailAttributes = exports.CrossColumnDetailFilter = exports.CrossColumnDetailValueHeader = exports.CrossColumnDetailCaption = exports.CrossColumnDetailOver = exports.CrossColumnDetailValuePlaceHolder = exports.CrossColumnDetailValue = exports.CrossColumnDetailName = exports.CrossColumnDetailFormatter = exports.CrossColumnCrossHeader = exports.StatusBarToolTipRowCount = exports.StatusBarRowCount = exports.TextColon = exports.GroupPanelAddCalculateColumn = exports.GroupPanelItemRemoveAll = exports.GroupPanelSummaryLabelPositionFooter = exports.GroupPanelSummaryLabelPositionHeader = exports.GroupPanelSummaryLabelPosition = exports.GroupPanelSummaryLabelRelateTo = exports.GroupPanelSummaryLabelSlice = exports.GroupPanelSummaryLabelCaption = exports.GroupPanelSummaryLabelFormula = exports.GroupPanelDropDownCalcField = exports.GroupPanelItemAddCalculation = exports.GroupPanelItemRemove = exports.GroupPanelGroupsHeader = exports.GroupPanelFieldsHeader = exports.GroupPanelTip = exports.EXP_TooManyPinRecords = void 0;
exports.columnTypeAttachmentRight = exports.columnTypeAttachmentTop = exports.columnTypeAttachmentLeft = exports.columnTypeAttachmentMarginGroup = exports.columnTypeCalendarType = exports.columnTypeShowEraFirstYear = exports.columnTypeCalendar = exports.columnTypeDateLocale = exports.columnTypeDateType = exports.columnTypeDateShowBuiltInDateRange = exports.columnTypeDateShowDateRange = exports.columnTypeDateShowTime = exports.columnTypeDateMonth = exports.columnTypeDateYear = exports.columnTypeDateDay = exports.columnTypeDateCalendarPage = exports.columnTypeDateSunday = exports.columnTypeDateSaturday = exports.columnTypeDateFriday = exports.columnTypeDateThursday = exports.columnTypeDateWednesday = exports.columnTypeDateTuesday = exports.columnTypeDateMonday = exports.columnTypeDateStartDay = exports.columnTypeDateTimePicker = exports.columnTypeAllEditableFields = exports.columnTypeMask = exports.columnTypeFormattingCategory = exports.columnTypeLookupIndexes = exports.columnTypeLookupFields = exports.columnTypeLookupTables = exports.columnTypeNumberFormatting = exports.columnTypeNumberFormattingSymbol = exports.columnTypeNumberFormattingNegativeNumbers = exports.columnTypeNumberFormattingThousandSeparator = exports.columnTypeNumberFormattingDecimalPlaces = exports.columnTypeNumberFormattingSample = exports.columnTypeBarcode = exports.columnTypeSelect = exports.columnTypeAttachment = exports.columnTypeModifiedTime = exports.columnTypeCreatedTime = exports.columnTypeLookup = exports.columnTypeURL = exports.columnTypeEmail = exports.columnTypePhone = exports.columnTypePercent = exports.columnTypeCurrency = exports.columnTypeDate = exports.columnTypeCheckbox = void 0;
exports.columnTypeUrlLabel = exports.columnTypeComboBoxItemHeight = exports.columnTypeComboBoxEditable = exports.columnTypeComboBoxEditorValueType = exports.columnTypeComboBoxRemove = exports.columnTypeComboBoxAdd = exports.columnTypeComboBoxValue = exports.columnTypeComboBoxText = exports.columnTypeComboBoxItemProperties = exports.columnTypeComboBoxItems = exports.columnTypeComboBoxEditorValueTypes = exports.columnTypeBarcodeLabel = exports.columnTypeCheckboxTextAlign = exports.columnTypeCheckboxAuto = exports.columnTypeCheckboxBoxSize = exports.columnTypeCheckboxIsThreeState = exports.columnTypeCheckboxCaption = exports.columnTypeCheckboxOther = exports.columnTypeCheckboxAlign = exports.columnTypeCheckboxFalse = exports.columnTypeCheckboxIndeterminate = exports.columnTypeCheckboxTrue = exports.columnTypeCheckboxTextGroup = exports.columnTypeCheckboxTitle = exports.columnTypeAttachmentSizeUnit = exports.columnTypeAttachmentIsDownloadEnabled = exports.columnTypeAttachmentIsClearEnabled = exports.columnTypeAttachmentIsPreviewEnabled = exports.columnTypeAttachmentAcceptValueTypes = exports.columnTypeAttachmentAccept = exports.columnTypeAttachmentMaxSize = exports.columnTypeAttachmentBottom = void 0;
exports.EXP_TooManyPinRecords = "10\uac1c \ubbf8\ub9cc\uc758 \ub808\ucf54\ub4dc\ub9cc \uace0\uc815\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4.";
exports.GroupPanelTip = "\ud589 \uadf8\ub8f9\uc744 \uc124\uc815\ud558\ub824\uba74 \uc5ec\uae30\ub85c \ub04c\uc5b4\ub2e4 \ub193\uc73c\uc2ed\uc2dc\uc624.";
exports.GroupPanelFieldsHeader = "\ud544\ub4dc";
exports.GroupPanelGroupsHeader = "\uadf8\ub8f9";
exports.GroupPanelItemRemove = "\uc81c\uac70";
exports.GroupPanelItemAddCalculation = "\uc694\uc57d \ud544\ub4dc \ucd94\uac00";
exports.GroupPanelDropDownCalcField = "\ud544\ub4dc";
exports.GroupPanelSummaryLabelFormula = "\ud568\uc218";
exports.GroupPanelSummaryLabelCaption = "\ucea1\uc158";
exports.GroupPanelSummaryLabelSlice = "\uc2ac\ub77c\uc774\uc2a4";
exports.GroupPanelSummaryLabelRelateTo = "\uad00\ub828";
exports.GroupPanelSummaryLabelPosition = "\uc704\uce58";
exports.GroupPanelSummaryLabelPositionHeader = "\uba38\ub9ac\uae00";
exports.GroupPanelSummaryLabelPositionFooter = "\ubc14\ub2e5\uae00";
exports.GroupPanelItemRemoveAll = "\ubaa8\ub450 \uc81c\uac70";
exports.GroupPanelAddCalculateColumn = "\uacc4\uc0b0 \uc5f4 \ucd94\uac00";
exports.TextColon = ":";
exports.StatusBarRowCount = "\ud589 \uac1c\uc218";
exports.StatusBarToolTipRowCount = '\uc120\ud0dd\ud55c \ud589 \uc218';
exports.CrossColumnCrossHeader = "\uad50\ucc28";
exports.CrossColumnDetailFormatter = "\ud615\uc2dd";
exports.CrossColumnDetailName = "\uc774\ub984";
exports.CrossColumnDetailValue = "\uac12";
exports.CrossColumnDetailValuePlaceHolder = "\uac12\uc744 \uc124\uc815\ud558\ub824\uba74 \ud56d\ubaa9\uc744 \uc5ec\uae30\ub85c \ub04c\uc5b4\ub2e4 \ub193\uc73c\uc138\uc694";
exports.CrossColumnDetailOver = "\ub05d";
exports.CrossColumnDetailCaption = "\ucea1\uc158";
exports.CrossColumnDetailValueHeader = "\uad50\ucc28 \uac12 \uba38\ub9ac\uae00 \ud45c\uc2dc";
exports.CrossColumnDetailFilter = "\ud544\ud130";
exports.CrossColumnDetailAttributes = "\uc18d\uc131";
exports.CrossColumnDetailList = "\ud56d\ubaa9";
exports.Exp_InvalidOperationInProtectForTableSheet = "\ubcc0\uacbd\ud558\ub824\ub294 \ubc94\uc704\uac00 \uc7a0\uaca8 \uc788\uc2b5\ub2c8\ub2e4.";
exports.defineColumn = "\uc5f4 \uc815\uc758";
exports.cancel = "\ucde8\uc18c";
exports.submit = "\uc81c\ucd9c";
exports.defineColumnValue = "\uac12";
exports.defineColumnCaption = "\ucea1\uc158";
exports.defineColumnType = "\ud615\uc2dd";
exports.defineColumnFormatting = "\uc11c\uc2dd";
exports.defineColumnConfiguration = "\uad6c\uc131";
exports.defineColumnDefaultValue = "\uae30\ubcf8 \uac12";
exports.defineColumnOthers = "\uae30\ud0c0";
exports.defineColumnFormula = "\uc218\uc2dd";
exports.defineColumnNone = "\uc5c6\uc74c";
exports.defineColumnAll = "\ubaa8\ub450";
exports.defineColumnNoResults = "\uacb0\uacfc\uac12 \uc5c6\uc74c";
exports.defineColumnRequired = "\ud544\uc218\uc785\ub2c8\ub2e4.";
exports.defineColumnExisted = "\uc874\uc7ac\ud569\ub2c8\ub2e4.";
exports.columnTypeNumber = "\uc22b\uc790";
exports.columnTypeText = "\ud14d\uc2a4\ud2b8";
exports.columnTypeFormula = "\uc218\uc2dd";
exports.columnTypeCheckbox = "\uccb4\ud06c \ubc15\uc2a4";
exports.columnTypeDate = "\ub0a0\uc9dc";
exports.columnTypeCurrency = "\ud1b5\ud654";
exports.columnTypePercent = "\ubc31\ubd84\uc728";
exports.columnTypePhone = "\uc804\ud654";
exports.columnTypeEmail = "\uc774\uba54\uc77c";
exports.columnTypeURL = "URL";
exports.columnTypeLookup = "\uc870\ud68c";
exports.columnTypeCreatedTime = "\uc0dd\uc131 \uc2dc\uac04";
exports.columnTypeModifiedTime = "\uc218\uc815 \uc2dc\uac04";
exports.columnTypeAttachment = "\ucca8\ubd80 \ud30c\uc77c";
exports.columnTypeSelect = "\uc120\ud0dd";
exports.columnTypeBarcode = "\ubc14\ucf54\ub4dc";
exports.columnTypeNumberFormattingSample = "\ubcf4\uae30";
exports.columnTypeNumberFormattingDecimalPlaces = "\uc18c\uc218 \uc790\ub9bf\uc218";
exports.columnTypeNumberFormattingThousandSeparator = "1000 \ub2e8\uc704 \uad6c\ubd84 \uae30\ud638(,) \uc0ac\uc6a9";
exports.columnTypeNumberFormattingNegativeNumbers = '\uc74c\uc218';
exports.columnTypeNumberFormattingSymbol = '\uae30\ud638';
exports.columnTypeNumberFormatting = {
    negativeNumbers: {
        "-1234.10": "-1234.10",
        "red:1234.10": "1234.10",
        "(1234.10)": "(1234.10)",
        "red:(1234.10)": "(1234.10)"
    },
    numberCategoryFormats: [
        "0",
        "0;[Red]0",
        "0_);(0)",
        "0_);[Red](0)",
        "#,##0",
        "#,##0;[Red]#,##0",
        "#,##0_);(#,##0)",
        "#,##0_);[Red](#,##0)"
    ],
    japanEmperorReignDateFormat: [
        "[$-411]ge.m.d;@",
        '[$-411]ggge"\u5e74"m"\u6708"d"\u65e5";@'
    ],
    japanEmperorReignFirstYearDateFormat: [
        "[$-411]ge.m.d;@",
        '[$-ja-JP-x-gannen]ggge"\u5e74"m"\u6708"d"\u65e5";@'
    ],
    accountingSymbol: [
        ["\uc5c6\uc74c", "", ""],
        ["$", "$", "en-US"],
        ["\xa5(Chinese)", "\xa5", "zh-cn"],
        ["\xa5(Japanese)", "\xa5", "ja-jp"],
        ["\u20a9(Korean)", "\u20a9", "ko-kr"]
    ],
    percentageFormats: [
        "0%"
    ],
    currencyFormatWithoutSymbol: [
        "#,##0",
        "#,##0;[Red]#,##0",
        "#,##0_);(#,##0)",
        "#,##0_);[Red](#,##0)"
    ]
};
exports.columnTypeLookupTables = "\uc870\ud68c \ud14c\uc774\ube14 \uc120\ud0dd";
exports.columnTypeLookupFields = "\uc870\ud68c\ud560 \ud544\ub4dc";
exports.columnTypeLookupIndexes = "\ub808\ucf54\ub4dc \ubc88\ud638";
exports.columnTypeFormattingCategory = "\ubc94\uc8fc";
exports.columnTypeMask = {
    pattern: "\ud328\ud134",
    placeholder: "\uc790\ub9ac \ud45c\uc2dc\uc790",
    excludeLiteral: "\ub9ac\ud130\ub7f4 \uc81c\uc678",
    excludePlaceholder: "\uc790\ub9ac \ud45c\uc2dc\uc790 \uc81c\uc678",
};
exports.columnTypeAllEditableFields = "\ubaa8\ub4e0 \ud3b8\uc9d1 \uac00\ub2a5\ud55c \ud544\ub4dc";
exports.columnTypeDateTimePicker = "\ub0a0\uc9dc \uc2dc\uac04 \uc120\ud0dd\uae30";
exports.columnTypeDateStartDay = "\uc2dc\uc791\uc77c";
exports.columnTypeDateMonday = "\uc6d4\uc694\uc77c";
exports.columnTypeDateTuesday = "\ud654\uc694\uc77c";
exports.columnTypeDateWednesday = "\uc218\uc694\uc77c";
exports.columnTypeDateThursday = "\ubaa9\uc694\uc77c";
exports.columnTypeDateFriday = "\uae08\uc694\uc77c";
exports.columnTypeDateSaturday = "\ud1a0\uc694\uc77c";
exports.columnTypeDateSunday = "\uc77c\uc694\uc77c";
exports.columnTypeDateCalendarPage = "\ub2ec\ub825 \ud398\uc774\uc9c0";
exports.columnTypeDateDay = "\uc77c";
exports.columnTypeDateYear = "\ub144";
exports.columnTypeDateMonth = "\uc6d4";
exports.columnTypeDateShowTime = "\uc2dc\uac04 \ud45c\uc2dc";
exports.columnTypeDateShowDateRange = "\ub0a0\uc9dc \ubc94\uc704 \ud45c\uc2dc";
exports.columnTypeDateShowBuiltInDateRange = "\ube4c\ud2b8\uc778 \ub0a0\uc9dc \ubc94\uc704 \ud45c\uc2dc";
exports.columnTypeDateType = "\uc720\ud615";
exports.columnTypeDateLocale = "\ub85c\uce98(\uc704\uce58)";
exports.columnTypeCalendar = "\ub2ec\ub825 \uc720\ud615";
exports.columnTypeShowEraFirstYear = "Gannen\uc744 \uc0ac\uc6a9\ud558\uc5ec 1\ub144\ucc28\ub97c \ud45c\uc2dc\ud569\ub2c8\ub2e4.";
exports.columnTypeCalendarType = {
    western: "\uc11c\uc720\ub7fd/\ubbf8\uad6d \uc601\ubb38\uc790",
    JER: "\uc77c\ubcf8 \uc5f0\ud638"
};
exports.columnTypeAttachmentMarginGroup = "\uc5ec\ubc31";
exports.columnTypeAttachmentLeft = "\uc67c\ucabd";
exports.columnTypeAttachmentTop = "\uc704\ucabd";
exports.columnTypeAttachmentRight = "\uc624\ub978\ucabd";
exports.columnTypeAttachmentBottom = "\uc544\ub798\ucabd";
exports.columnTypeAttachmentMaxSize = "\ud06c\uae30 \uc81c\ud55c";
exports.columnTypeAttachmentAccept = "\ud30c\uc77c \ud615\uc2dd";
exports.columnTypeAttachmentAcceptValueTypes = {
    txt: 'txt',
    all: '\ubaa8\ub450',
    pdf: 'pdf',
    image: 'jpg,png,gif',
    excel: 'doc,xls,ppt',
};
exports.columnTypeAttachmentIsPreviewEnabled = "\ubbf8\ub9ac\ubcf4\uae30";
exports.columnTypeAttachmentIsClearEnabled = "\uc9c0\uc6b0\uae30";
exports.columnTypeAttachmentIsDownloadEnabled = "\ub2e4\uc6b4\ub85c\ub4dc";
exports.columnTypeAttachmentSizeUnit = "KB";
exports.columnTypeCheckboxTitle = "\uccb4\ud06c\ubc15\uc2a4 \uc140 \uc720\ud615";
exports.columnTypeCheckboxTextGroup = "\ud14d\uc2a4\ud2b8";
exports.columnTypeCheckboxTrue = "True";
exports.columnTypeCheckboxIndeterminate = "\ubd88\ud655\uc815 \uc0c1\ud0dc";
exports.columnTypeCheckboxFalse = "False";
exports.columnTypeCheckboxAlign = "\uc815\ub82c";
exports.columnTypeCheckboxOther = "\uae30\ud0c0";
exports.columnTypeCheckboxCaption = "\ucea1\uc158";
exports.columnTypeCheckboxIsThreeState = "\uc138 \uac00\uc9c0 \uc0c1\ud0dc";
exports.columnTypeCheckboxBoxSize = "\uc0c1\uc790 \ud06c\uae30";
exports.columnTypeCheckboxAuto = "\uc790\ub3d9";
exports.columnTypeCheckboxTextAlign = {
    top: "\uc704\ucabd",
    bottom: "\uc544\ub798\ucabd",
    left: "\uc67c\ucabd",
    right: "\uc624\ub978\ucabd"
};
exports.columnTypeBarcodeLabel = {
    showLabel: "\ub808\uc774\ube14 \ud45c\uc2dc",
    barcodeType: "\ubc14\ucf54\ub4dc \uc720\ud615",
    color: "\uc0c9",
    errorCorrectionLevel: "\uc624\ub958 \uc218\uc815 \uc218\uc900",
    backgroundColor: "\ubc30\uacbd\uc0c9",
    version: "\ubc84\uc804",
    model: "\ubaa8\ub378",
    mask: "\ub9c8\uc2a4\ud06c",
    connection: "\uc5f0\uacb0",
    charCode: "\ubb38\uc790 \ucf54\ub4dc",
    connectionNo: "\uc5f0\uacb0 \ubc88\ud638",
    charset: "\ubb38\uc790 \uc9d1\ud569",
    quietZoneLeft: "\uc67c\ucabd \uc790\ub3d9 \uc601\uc5ed",
    quietZoneRight: "\uc624\ub978\ucabd \uc790\ub3d9 \uc601\uc5ed",
    quietZoneTop: "\uc704\ucabd \uc790\ub3d9 \uc601\uc5ed",
    quietZoneBottom: "\uc544\ub798\ucabd \uc790\ub3d9 \uc601\uc5ed",
    labelPosition: "\ub808\uc774\ube14 \uc704\uce58",
    addOn: "\uc560\ub4dc\uc628",
    addOnLabelPosition: "\uc560\ub4dc\uc628 \ub808\uc774\ube14 \uc704\uce58",
    fontFamily: "\uae00\uaf34",
    fontStyle: "\uae00\uaf34 \uc2a4\ud0c0\uc77c",
    fontWeight: "\uae00\uaf34 \ub450\uaed8",
    fontTextDecoration: "\uae00\uaf34 \ud14d\uc2a4\ud2b8 \uc7a5\uc2dd",
    fontTextAlign: "\uae00\uaf34 \ud14d\uc2a4\ud2b8 \ub9de\ucda4",
    fontSize: "\uae00\uaf34 \ud06c\uae30",
    fileIdentifier: "\ud30c\uc77c \uc2dd\ubcc4\uc790",
    structureNumber: "\uad6c\uc870 \ubc88\ud638",
    structureAppend: "\uad6c\uc870 \ucd94\uac00",
    ecc00_140Symbol: "Ecc000_140 \uae30\ud638 \ud06c\uae30",
    ecc200EncodingMode: "Ecc200 \uc778\ucf54\ub529 \ubaa8\ub4dc",
    ecc200SymbolSize: "Ecc200 \uae30\ud638 \ud06c\uae30",
    eccMode: "Ecc \ubaa8\ub4dc",
    compact: "\ucef4\ud329\ud2b8",
    columns: "\uc5f4",
    rows: "\ud589",
    groupNo: "\uadf8\ub8f9 \ubc88\ud638",
    grouping: "\uadf8\ub8f9\ud654",
    codeSet: "\ucf54\ub4dc \uc9d1\ud569",
    fullASCII: "\uc804\uccb4 ASCII",
    checkDigit: "\uc22b\uc790 \ud655\uc778",
    nwRatio: "\uc804\uac01 \ubc0f \ubc18\uac01 \ube44\uc728",
    labelWithStartAndStopCharacter: "\uc2dc\uc791 \ubc0f \ub05d \ubb38\uc790\uac00 \uc788\ub294 \ub808\uc774\ube14"
};
exports.columnTypeComboBoxEditorValueTypes = "\ud3b8\uc9d1\uae30 \uac12 \uc720\ud615";
exports.columnTypeComboBoxItems = "\ud56d\ubaa9";
exports.columnTypeComboBoxItemProperties = "\ud56d\ubaa9 \uc18d\uc131";
exports.columnTypeComboBoxText = "\ud14d\uc2a4\ud2b8";
exports.columnTypeComboBoxValue = "\uac12";
exports.columnTypeComboBoxAdd = "\ucd94\uac00";
exports.columnTypeComboBoxRemove = "\uc81c\uac70";
exports.columnTypeComboBoxEditorValueType = {
    text: "\ud14d\uc2a4\ud2b8",
    index: "\uc778\ub371\uc2a4",
    value: "\uac12"
};
exports.columnTypeComboBoxEditable = "\uc218\uc815 \uac00\ub2a5";
exports.columnTypeComboBoxItemHeight = "\ud56d\ubaa9 \ub192\uc774";
exports.columnTypeUrlLabel = {
    linkColor: "\ub9c1\ud06c \uc0c9\uc0c1",
    visitedLinkColor: "\ubc29\ubb38\ud55c \ub9c1\ud06c \uc0c9\uc0c1",
};


/***/ }),

/***/ "./dist/plugins/table/table.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/table/table.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Table_Var = exports.Table_StdDev = exports.Table_Sum = exports.Table_Min = exports.Table_Max = exports.Table_Count_Numbers = exports.Table_Count = exports.Table_Average = exports.Table_None = exports.Table_Total = exports.Exp_TableAddRowNoEnoughRoom = exports.Exp_TableAddRowIntersectTable = exports.Exp_TableAddRowIntersectSpan = exports.Exp_TableDeleteCountInvalid = exports.Exp_ColParamOutOfRange = exports.Exp_RowParamOutOfRange = exports.Exp_TableResizeToTable = exports.Exp_TableResizeWithHidden = exports.Exp_TableResizeWithFilter = exports.Exp_TableResizeToSpan = exports.Exp_TableResizeInvalidRange = exports.Exp_ArrayFormulaTable = exports.Exp_TableResizeOutOfRange = exports.Exp_TableMoveOutOfRange = exports.Exp_TableDataSourceNullError = exports.Exp_TableRangeHasFilterError = exports.Exp_TableHasSameNameError = exports.Exp_TableIntersectError = exports.Exp_TableInvalidColumn = exports.Exp_TableInvalidRow = exports.Exp_TableNameInvalid = exports.Exp_TableEmptyNameError = exports.Exp_DragDropChangePartOfTable = exports.Exp_DragDropShiftTableCell = void 0;
exports.Exp_DragDropShiftTableCell = '\uc774 \uc791\uc5c5\uc740 \ud5c8\uc6a9\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4. \uc774 \uc791\uc5c5\uc740 \uc6cc\ud06c\uc2dc\ud2b8\uc758 \ud14c\uc774\ube14\uc5d0\uc11c \uc140\uc744 \ubcc0\ud658\ud558\ub824\uace0 \uc2dc\ub3c4\ud569\ub2c8\ub2e4.';
exports.Exp_DragDropChangePartOfTable = '\uc791\uc5c5\uc744 \uc644\ub8cc\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ud14c\uc774\ube14 \ud589 \ub610\ub294 \uc5f4\uc758 \uc77c\ubd80\ub97c \ud5c8\uc6a9\ub418\uc9c0 \uc54a\ub294 \ubc29\uc2dd\uc73c\ub85c \ubcc0\uacbd\ud558\ub824\uace0 \ud569\ub2c8\ub2e4.';
exports.Exp_TableEmptyNameError = '\ud14c\uc774\ube14 \uc774\ub984\uc740 \ube44\uc6cc \ub458 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableNameInvalid = '\ud14c\uc774\ube14 \uc774\ub984\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableInvalidRow = '\ud589 \uc778\ub371\uc2a4 \ub610\ub294 \ud589 \uc218\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableInvalidColumn = '\uc5f4 \uc778\ub371\uc2a4 \ub610\ub294 \uc5f4 \uc218\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableIntersectError = '\ud14c\uc774\ube14\uc774 \uad50\ucc28\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableHasSameNameError = '\ud604\uc7ac \uc6cc\ud06c\uc2dc\ud2b8\uac00 \ub3d9\uc77c\ud55c \uc774\ub984\uc744 \uac00\uc9c4 \ud14c\uc774\ube14\uc5d0 \uc774\ubbf8 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableRangeHasFilterError = '\ud604\uc7ac \ud14c\uc774\ube14 \ubc94\uc704\uc5d0 \ud544\ud130 \ubc94\uc704\uac00 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableDataSourceNullError = '\ud14c\uc774\ube14 \uc6d0\ubcf8 \ub370\uc774\ud130\ub294 null\uc77c \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableMoveOutOfRange = '\ud14c\uc774\ube14\uc744 \uc2dc\ud2b8 \ubc16\uc73c\ub85c \uc774\ub3d9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableResizeOutOfRange = '\uc798\ubabb\ub41c \ud589 \uc218, \uc5f4 \uc218 \ubc0f \ud14c\uc774\ube14\uc758 \ud06c\uae30\ub97c \uc2dc\ud2b8 \ubc16\uc73c\ub85c \uc870\uc815\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_ArrayFormulaTable = '\ub2e4\uc911 \uc140 \ubc30\uc5f4 \uc218\uc2dd\uc740 \ud45c\uc5d0\uc11c \uc0ac\uc6a9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableResizeInvalidRange = '\uba38\ub9ac\uae00\uc740 \ub3d9\uc77c\ud55c \ud589\uc5d0 \uc720\uc9c0\ub418\uace0 \uacb0\uacfc \ud45c \ubc94\uc704\ub294 \uc6d0\ub798 \ud45c \ubc94\uc704\uc640 \uc77c\uce58\ud574\uc57c \ud569\ub2c8\ub2e4.';
exports.Exp_TableResizeToSpan = '\uc791\uc5c5\uc744 \uc644\ub8cc\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ud45c\ub294 \ud45c \ub610\ub294 \ubcd1\ud569\ub41c \uc140\uacfc \uacb9\uce60 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableResizeWithFilter = '\ud544\ud130\ub9c1\ub41c \ubc94\uc704 \ub610\ub294 \ud45c\uc5d0 \uc788\ub294 \uc140\uc744 \uc774\ub3d9\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableResizeWithHidden = '\uc228\uaca8\uc9c4 \ud589 \ub610\ub294 \uc5f4\uc5d0 \uc778\uc811\ud55c \ud45c\uc758 \ud06c\uae30\ub97c \uc870\uc815\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableResizeToTable = '\uc791\uc5c5\uc744 \uc644\ub8cc\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \ud45c \ubc94\uc704 \ub0b4\ubd80 \ubc0f \uc678\ubd80\uc5d0 \ubaa8\ub450 \uc140\uc774 \ud3ec\ud568\ub41c \uc791\uc5c5 \ubc0f \uc5ec\ub7ec \ud45c\uc640 \uacb9\uce58\ub294 \uc140\uc5d0 \uc601\ud5a5\uc744 \uc8fc\ub294 \uc791\uc5c5\uc740 \ud5c8\uc6a9\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.';
exports.Exp_RowParamOutOfRange = '\ud45c\uc758 \ub370\uc774\ud130 \ubc94\uc704\ub97c \ubc97\uc5b4\ub098\ub294 \ub9e4\uac1c \ubcc0\uc218 \ud589\uc785\ub2c8\ub2e4.';
exports.Exp_ColParamOutOfRange = '\ud45c\uc758 \ub370\uc774\ud130 \ubc94\uc704\ub97c \ubc97\uc5b4\ub098\ub294 \ub9e4\uac1c \ubcc0\uc218 \uc5f4\uc785\ub2c8\ub2e4.';
exports.Exp_TableDeleteCountInvalid = '\uc0ad\uc81c\ud560 \ub9e4\uac1c \ubcc0\uc218 \uc218\ub85c \uc778\ud574 \ube48 \ud45c\uac00 \uc0dd\uc131\ub429\ub2c8\ub2e4.';
exports.Exp_TableAddRowIntersectSpan = '\uc6cc\ud06c\uc2dc\ud2b8\uc758 \ubc94\uc704\uc5d0 \uc788\ub294 \uc140\uc774 \uc774\ub3d9\ub420 \uc218 \uc788\uc73c\ubbc0\ub85c \uc774 \uc791\uc5c5\uc740 \uc218\ud589\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableAddRowIntersectTable = '\uc6cc\ud06c\uc2dc\ud2b8\uc758 \ud45c\uc5d0 \uc788\ub294 \uc140\uc774 \uc774\ub3d9\ub420 \uc218 \uc788\uc73c\ubbc0\ub85c \uc774 \uc791\uc5c5\uc740 \uc218\ud589\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.';
exports.Exp_TableAddRowNoEnoughRoom = '\uc6cc\ud06c\uc2dc\ud2b8 \ub05d\uc758 \ube44\uc5b4 \uc788\uc9c0 \uc54a\uc740 \uc140\uc744 \ubc00\uc5b4\ub123\uc744 \uc218 \uc788\uc73c\ubbc0\ub85c \uc0c8 \uc140\uc744 \uc0bd\uc785\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \uc774 \uc140\uc740 \ube44\uc5b4 \uc788\ub294 \uac83\uc73c\ub85c \ud45c\uc2dc\ub418\uc9c0\ub9cc \ube48 \uac12, \uc77c\ubd80 \uc11c\uc2dd \ub610\ub294 \uc218\uc2dd\uc744 \ud3ec\ud568\ud569\ub2c8\ub2e4. \uc0bd\uc785\ud560 \ud56d\ubaa9\uc5d0 \uc0ac\uc6a9\ud560 \uc218 \uc788\ub294 \ucda9\ubd84\ud55c \ud589 \ub610\ub294 \uc5f4\uc744 \uc0ad\uc81c\ud55c \ub2e4\uc74c, \ub2e4\uc2dc \uc2dc\ub3c4\ud558\uc2ed\uc2dc\uc624.';
exports.Table_Total = '\uc694\uc57d';
exports.Table_None = '\uc5c6\uc74c';
exports.Table_Average = '\ud3c9\uade0';
exports.Table_Count = '\uac1c\uc218';
exports.Table_Count_Numbers = '\uc22b\uc790 \uac1c\uc218';
exports.Table_Max = '\ucd5c\ub300';
exports.Table_Min = '\ucd5c\uc18c';
exports.Table_Sum = '\ud569\uacc4';
exports.Table_StdDev = '\ud45c\ubcf8 \ud45c\uc900 \ud3b8\ucc28';
exports.Table_Var = '\ud45c\ubcf8 \ubd84\uc0b0';


/***/ }),

/***/ "./dist/plugins/touch/touch.res.ko.js":
/*!********************************************!*\
  !*** ./dist/plugins/touch/touch.res.ko.js ***!
  \********************************************/
/***/ (function(__unused_webpack_module, exports) {

"use strict";

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.ToolStrip_AutoFillText = exports.ToolStrip_CopyText = exports.ToolStrip_CutText = exports.ToolStrip_PasteText = void 0;
exports.ToolStrip_PasteText = '\ubd99\uc5ec\ub123\uae30';
exports.ToolStrip_CutText = '\uc798\ub77c\ub0b4\uae30';
exports.ToolStrip_CopyText = '\ubcf5\uc0ac';
exports.ToolStrip_AutoFillText = '\uc790\ub3d9 \ucc44\uc6b0\uae30';


/***/ }),

/***/ "./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.ko.js":
/*!****************************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.ko.js ***!
  \****************************************************************************************/
/***/ (function(module) {

var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./src/calcEngine.res.ko.ts":
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
var START_NUMBER = 'start_num';
var SIGNIFICANCE = 'significance';
var MATCH_MODE = 'match_mode';
var IF_NOT_FOUND = 'if_not_found';
var PAD_WITH = 'pad_with';
var MATCH_MODE_ENUM_INFO = [
    {
        value: '0',
        description: '\ub300\uc18c\ubb38\uc790 \uad6c\ubd84',
    },
    {
        value: '1',
        description: '\ub300\uc18c\ubb38\uc790 \uad6c\ubd84 \uc548 \ud568'
    }
];
var LOOKUP_MATCH_MODE_ENUM_INFO = [
    {
        value: '0',
        description: '\uc815\ud655\ud788 \uc77c\uce58 (\uae30\ubcf8)'
    },
    {
        value: '-1',
        description: '\uc815\ud655\ud788 \uc77c\uce58\ud558\uac70\ub098 \ub2e4\uc74c\uc73c\ub85c \uc791\uc740 \ud56d\ubaa9'
    },
    {
        value: '1',
        description: '\uc815\ud655\ud788 \uc77c\uce58\ud558\uac70\ub098 \ub2e4\uc74c\uc73c\ub85c \ud070 \ud56d\ubaa9'
    },
    {
        value: '2',
        description: '*, ? \ubc0f ~\uac00 \ud2b9\ubcc4\ud55c \uc758\ubbf8\ub97c \uac00\uc9c0\ub294 \uc640\uc77c\ub4dc\uce74\ub4dc \ub9e4\uce58'
    }
];
var LOOKUP_SEARCH_MODE_ENUM_INFO = [
    {
        value: '1',
        description: '\ucc98\uc74c\ubd80\ud130 \ub05d\uae4c\uc9c0 \uac80\uc0c9 (\uae30\ubcf8)'
    },
    {
        value: '-1',
        description: '\ub05d\uc5d0\uc11c \ucc98\uc74c\uae4c\uc9c0 \uac80\uc0c9 (\uc5ed\uac80\uc0c9)'
    },
    {
        value: '2',
        description: '\uc624\ub984\ucc28\uc21c\uc73c\ub85c \uc815\ub82c\ub418\ub294 lookup_array\uc5d0 \uc758\uc874\ud558\ub294 \uc774\uc9c4 \uac80\uc0c9\uc744 \uc218\ud589\ud569\ub2c8\ub2e4. \uc815\ub82c\ub418\uc9c0 \uc54a\uc73c\uba74 \uc798\ubabb\ub41c \uacb0\uacfc\uac00 \ubc18\ud658\ub429\ub2c8\ub2e4.'
    },
    {
        value: '-2',
        description: '\ub0b4\ub9bc\ucc28\uc21c\uc73c\ub85c \uc815\ub82c\ub418\ub294 lookup_array\uc5d0 \uc758\uc874\ud558\ub294 \uc774\uc9c4 \uac80\uc0c9\uc744 \uc218\ud589\ud569\ub2c8\ub2e4. \uc815\ub82c\ub418\uc9c0 \uc54a\uc73c\uba74 \uc798\ubabb\ub41c \uacb0\uacfc\uac00 \ubc18\ud658\ub429\ub2c8\ub2e4.'
    }
];
var RANGE_LOOKUP_ENUM_INFO = [
    {
        value: 'TRUE',
        description: "\uadfc\uc0ac\uc77c\uce58"
    },
    {
        value: 'FALSE',
        description: "\uc815\ud655\ud788 \uc77c\uce58"
    }
];
exports.resource = {
    Exp_InvalidCast: '\uc798\ubabb\ub41c \uce90\uc2a4\ud2b8 \uc608\uc678',
    Exp_FormulaInvalidChar: '\uc785\ub825\ud55c \uc218\uc2dd\uc5d0 \uc798\ubabb\ub41c \ubb38\uc790\uac00 \ud3ec\ud568\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4. {1}\uc5d0 \ub300\ud55c \uc778\ub371\uc2a4\uc5d0 \'{0}\'\uc774(\uac00) \ud3ec\ud568\ub418\uc5b4 \uc788\uc2b5\ub2c8\ub2e4.',
    Exp_FormulaInvalid: '\uc785\ub825\ud55c \uc218\uc2dd\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.',
    Exp_InvalidFunctionName: '\uc798\ubabb\ub41c \ud568\uc218 \uc774\ub984',
    Exp_InvalidOverrideFunction: '\uae30\ubcf8 \ud568\uc218\ub97c \uc7ac\uc815\uc758\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.',
    Exp_InvalidArray: '\uc798\ubabb\ub41c \ubc30\uc5f4',
    Exp_OverrideNotAllowed: '\ud568\uc218\ub97c \uc7ac\uc815\uc758\ud558\ub824\uace0 \ud558\uc9c0\ub9cc \uc7ac\uc815\uc758\uac00 \ud5c8\uc6a9\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.',
    Exp_NoSyntax: '\uad6c\ubb38 \'{1}\'\uacfc(\uc640) \uc77c\uce58\ud558\ub294 \uad6c\ubb38 \'{0}\'\uc774(\uac00) \uc5c6\uc2b5\ub2c8\ub2e4.',
    Exp_IsValid: '\'{0}\'\uc774(\uac00) \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.',
    Exp_InvalidParameters: '{0}\uc758 \ud568\uc218 \ub9e4\uac1c \ubcc0\uc218\uac00 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.',
    Exp_InvalidArrayColumns: '{0}\uc5d0\uc11c \ubc30\uc5f4 \uc5f4\uc758 \uae38\uc774\uac00 \ub2e4\ub985\ub2c8\ub2e4.',
    Exp_ExprIsNull: '\uc778\uc218 \'expr\'\uc774(\uac00) null\uc785\ub2c8\ub2e4.',
    Exp_InvalidOperation: '\uc798\ubabb\ub41c \uc791\uc5c5 \uc608\uc678',
    Exp_ArgumentNull: '\uc778\uc218 Null \uc608\uc678',
    Exp_CriteriaIsNull: '\uae30\uc900\uc774 null\uc785\ub2c8\ub2e4.',
    Exp_Format: '\ud615\uc2dd',
    Exp_ArrayFormulaPart: '\ubc30\uc5f4\uc758 \uc77c\ubd80\ub97c \ubcc0\uacbd\ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.',
    Exp_NotSupported: '\uc608\uc678 \uc9c0\uc6d0 \uc548 \ud568',
    Exp_DuplicatedChar: "\uc911\ubcf5\ub41c \ubb38\uc790",
    Exp_ArgumentOutOfRangeException: "\uc778\uc218\uac00 \ubc94\uc704\ub97c \ubc97\uc5b4\ub0a8",
    Exp_ArgumentException: "\uc778\uc218 \uc608\uc678",
    _builtInFunctionsResource: {
        'ABS': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \uc808\ub300\uac12\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ACCRINT': functionDescription('\uc774 \ud568\uc218\ub294 \uc815\uae30\uc801\uc73c\ub85c \uc774\uc790\ub97c \uc9c0\uae09\ud558\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \uacbd\uacfc \uc774\uc790\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('issue'),
            parameterDescription('first_interest'),
            parameterDescription('settlement'),
            parameterDescription('rate'),
            parameterDescription('par'),
            parameterDescription('frequency'),
            parameterDescription('basis', false, true)
        ]),
        'ACCRINTM': functionDescription('\uc774 \ud568\uc218\ub294 \uc815\uae30\uc801\uc73c\ub85c \uc774\uc790\ub97c \uc9c0\uae09\ud558\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \ub9cc\uae30 \uc2dc \uacbd\uacfc \uc774\uc790\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('issue'),
            parameterDescription('settlement'),
            parameterDescription('rate'),
            parameterDescription('par'),
            parameterDescription('basis', false, true)
        ]),
        'ACOS': functionDescription('\uc774 \ud568\uc218\ub294 ACOS \uc989, COS\uc774 \uc9c0\uc815\ub41c \uac12\uc778 \uac01\ub3c4\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ACOSH': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 ACOSH\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ADDRESS': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \ud589/\uc5f4 \ubc88\ud638\ub97c \uac00\uc9c0\uace0 \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc758 \uc140 \uc8fc\uc18c\ub97c \ub9cc\ub4ed\ub2c8\ub2e4.', [
            parameterDescription('row_num'),
            parameterDescription('column_num'),
            parameterDescription('abs_num', false, true),
            parameterDescription('a1style', false, true),
            parameterDescription('sheet_text', false, true)
        ]),
        'AGGREGATE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uae30\ubcf8 \uc81c\uacf5 \ud568\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc22b\uc790 \ubaa9\ub85d\uc744 \uc9d1\uacc4\ud569\ub2c8\ub2e4.', [
            parameterDescription('function_num'),
            parameterDescription('options'),
            parameterDescription('ref1'),
            parameterDescription('ref2', true)
        ]),
        'AMORDEGRC': functionDescription('\uc774 \ud568\uc218\ub294 \ube44\ub840 \ubc30\ubd84 \ub41c \uac10\uac00 \uc0c1\uac01\uc744 \uace0\ub824\ud558\uc5ec \ud68c\uacc4 \uae30\uac04\uc758 \uac10\uac00 \uc0c1\uac01\uc744 \ubc18\ud658\ud558\uace0 \uc790\uc0b0\uc758 \uc218\uba85\uc744 \uae30\uc900\uc73c\ub85c \uac10\uac00 \uc0c1\uac01 \uacc4\uc218\ub97c \uacc4\uc0b0\uc5d0 \uc801\uc6a9\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('date_purchased'),
            parameterDescription('first_period'),
            parameterDescription('salvage'),
            parameterDescription('period'),
            parameterDescription('rate'),
            parameterDescription('basis')
        ]),
        'AMORLINC': functionDescription('\uc774 \ud568\uc218\ub294 \uc77c\ud560 \uacc4\uc0b0\ub41c \uac10\uac00 \uc0c1\uac01\uc561\uc744 \uace0\ub824\ud558\uc5ec \ud68c\uacc4 \uae30\uac04\uc5d0 \ub300\ud55c \uac10\uac00 \uc0c1\uac01\uc561\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('date_purchased'),
            parameterDescription('first_period'),
            parameterDescription('salvage'),
            parameterDescription('period'),
            parameterDescription('rate'),
            parameterDescription('basis')
        ]),
        'AND': functionDescription('\ubaa8\ub4e0 \uc778\uc218\uac00 True\uc778\uc9c0 \ud655\uc778\ud558\uc2ed\uc2dc\uc624. \uc778\uc218\uac00 \ubaa8\ub450 True\uc774\uba74 True\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('logical1'),
            parameterDescription('logical2')
        ]),
        'ASIN': functionDescription('\uc774 \ud568\uc218\ub294 ASIN \uc989, SIN\uc774 \uc9c0\uc815\ub41c \uac12\uc778 \uac01\ub3c4\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ASINH': functionDescription('\uc774 \ud568\uc218\ub294 ASINH\uac12\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ATAN': functionDescription('\uc774 \ud568\uc218\ub294 ATAN \uc989, TAN\uac00 \uc9c0\uc815\ub41c \uac12\uc778 \uac01\ub3c4\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ATAN2': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c x \uc88c\ud45c\uc640 y \uc88c\ud45c\uc758 ATAN\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('x_num'),
            parameterDescription('y_num')
        ]),
        'ATANH': functionDescription('\uc774 \ud568\uc218\ub294 ATANH\uac12\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'AVEDEV': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \uc808\ub300 \ud3b8\ucc28\uc758 \ud3c9\uade0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'AVERAGE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc22b\uc790 \uac12\uc758 \ud3c9\uade0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true),
        ]),
        'AVERAGEA': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8, \ub17c\ub9ac\uac12, \uc22b\uc790\uac12\uc744 \ube44\ub86f\ud55c \uc9c0\uc815\ub41c \uac12\uc758 \ud3c9\uade0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'AVERAGEIF': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc22b\uc790 \uac12\uc774 \uc9c0\uc815\ub41c \uae30\uc900\uc744 \ucda9\uc871\ud560 \uacbd\uc6b0\uc758 \ud3c9\uade0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('range'),
            parameterDescription('criteria'),
            parameterDescription('average_range', false, true)
        ]),
        'AVERAGEIFS': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc5ec\ub7ec \uae30\uc900\uc744 \ucda9\uc871\ud558\ub294 \ubaa8\ub4e0 \uc140\uc758 \ud3c9\uade0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('average_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true)
        ]),
        'BESSELI': functionDescription('\uc774 \ud568\uc218\ub294 \uc21c \ud5c8\uc218 \uc778\uc218\uc5d0 \ub300\ud574 \uacc4\uc0b0\ub418\ub294 \uccab \ubc88\uc9f8 \uc885\ub958\uc758 \uc218\uc815\ub41c Bessel \ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELJ': functionDescription('\uc774 \ud568\uc218\ub294 \uccab \ubc88\uc9f8 \uc885\ub958\uc758 Bessel \ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELK': functionDescription('\uc774 \ud568\uc218\ub294 \uc21c \ud5c8\uc218 \uc778\uc218\uc5d0 \ub300\ud574 \uacc4\uc0b0\ub418\ub294 \ub450 \ubc88\uc9f8 \uc885\ub958\uc758 \uc218\uc815\ub41c Bessel \ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELY': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc88\uc9f8 \uc885\ub958\uc758 Bessel \ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BETADIST': functionDescription('\uc774 \ud568\uc218\ub294 \ub204\uc801 \ubca0\ud0c0 \ubd84\ud3ec \ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower', false, true),
            parameterDescription('upper', false, true)
        ]),
        'BETAINV': functionDescription('\uc774 \ud568\uc218\ub294 \ub204\uc801 \ubca0\ud0c0 \ubd84\ud3ec \ud568\uc218\uc758 \uc5ed\ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower', false, true),
            parameterDescription('upper', false, true)
        ]),
        'BIN2DEC': functionDescription('\uc774 \ud568\uc218\ub294 2\uc9c4\uc218\ub97c 10\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'BIN2HEX': functionDescription('\uc774 \ud568\uc218\ub294 2\uc9c4\uc218\ub97c 16\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'BIN2OCT': functionDescription('\uc774 \ud568\uc218\ub294 2\uc9c4\uc218\ub97c 8\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'BINOMDIST': functionDescription('\uc774 \ud568\uc218\ub294 \uac1c\ubcc4\ud56d \uc774\ud56d \ubd84\ud3ec \ud655\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number_s'),
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'CEILING': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \ubc30\uc218\uac00 \ub418\ub3c4\ub85d \uc808\ub300 \uac12\uc744 \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('significance')
        ]),
        'CHAR': functionDescription('\uc774 \ud568\uc218\ub294 \ubc88\ud638\uc5d0 \ud574\ub2f9\ud558\ub294 \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'CHIDIST': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc758 \ub2e8\uce21 \ud655\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('deg_freedom')
        ]),
        'CHIINV': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc758 \ub2e8\uce21 \ud655\ub960\uc758 \uc5ed\ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHITEST': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc5d0\uc11c \ub3c5\ub9bd \uac80\uc99d \uacb0\uacfc\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('actual_range'),
            parameterDescription('expected_range')
        ]),
        'CHOOSE': functionDescription('\uc774 \ud568\uc218\ub294 \uac12 \ubaa9\ub85d\uc5d0\uc11c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('index_num'),
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'CLEAN': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8\uc5d0\uc11c \uc778\uc1c4\ud560 \uc218 \uc5c6\ub294 \ubb38\uc790\ub97c \ubaa8\ub450 \uc81c\uac70\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'CODE': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uccab\uc9f8 \ubb38\uc790\ub97c \ub098\ud0c0\ub0b4\ub294 \uc22b\uc790 \ucf54\ub4dc\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \ubc18\ud658\ub418\ub294 \ucf54\ub4dc\ub294 Windows \ubb38\uc790 \uc9d1\ud569(ANSI)\uc5d0 \ud574\ub2f9\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'COLUMN': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc601\uc5ed\uc758 \uc5f4 \ubc88\ud638\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference')
        ]),
        'COLUMNS': functionDescription('\uc774 \ud568\uc218\ub294 \ubc30\uc5f4\uc5d0 \uc788\ub294 \uc5f4 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array')
        ]),
        'COMBIN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \ud56d\ubaa9 \uc218\uc5d0 \ub300\ud574 \uac00\ub2a5\ud55c \uc870\ud569 \uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'COMPLEX': functionDescription('\uc774 \ud568\uc218\ub294 \uc2e4\uc218\ubd80\uc640 \ud5c8\uc218\ubd80\uc758 \uacc4\uc218\ub97c \ubcf5\uc18c\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('real_num'),
            parameterDescription('image_num'),
            parameterDescription('suffix')
        ]),
        'CONCATENATE': functionDescription('\uc774 \ud568\uc218\ub294 \uc5ec\ub7ec \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4 \ub610\ub294 \uc22b\uc790\ub97c \ud558\ub098\uc758 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\ub85c \uacb0\ud569\ud569\ub2c8\ub2e4.', [
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'CONFIDENCE': functionDescription('\uc774 \ud568\uc218\ub294 \ubaa8\uc9d1\ub2e8 \ud3c9\uade0\uc758 \uc2e0\ub8b0 \uad6c\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'CONVERT': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790\ub97c \ub2e4\ub978 \ub2e8\uc704 \uccb4\uacc4\uc758 \uc22b\uc790\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('from_unit'),
            parameterDescription('to_unit')
        ]),
        'CORREL': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc138\ud2b8\uc758 \uc0c1\uad00 \uacc4\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'COS': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac01\ub3c4\uc758 COS\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'COSH': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 COSH\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'COUNT': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790\uac00 \uc788\ub294 \uc140 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'COUNTA': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790, \ud14d\uc2a4\ud2b8 \ub610\ub294 \ub17c\ub9ac\uac12\uc774 \uc788\ub294 \uc140 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'COUNTBLANK': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\ud2b8\uc758 \uc140 \ubc94\uc704 \ub0b4\uc5d0 \uc788\ub294 \ube48 \uc140\uc758 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellrange')
        ]),
        'COUNTIF': functionDescription('\uc774 \ud568\uc218\ub294 \ud2b9\uc815 \uc870\uac74\uc744 \ub9cc\uc871\ud558\ub294 \uc140 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellrange'),
            parameterDescription('criteria')
        ]),
        'COUNTIFS': functionDescription('\uc774 \ud568\uc218\ub294 \uc5ec\ub7ec \uc870\uac74\uc744 \ub9cc\uc871\ud558\ub294 \uc140 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true)
        ]),
        'COUPDAYBS': functionDescription('\uc774 \ud568\uc218\ub294 \uc774\uc790 \uc9c0\uae09 \uae30\uac04\uc758 \uc2dc\uc791\uc77c\ubd80\ud130 \uacb0\uc0b0\uc77c\uae4c\uc9c0\uc758 \ub0a0\uc9dc \uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPDAYS': functionDescription('\uc774 \ud568\uc218\ub294 \uacb0\uc0b0\uc77c\uc774 \ub4e4\uc5b4 \uc788\ub294 \uc774\uc790 \uc9c0\uae09 \uae30\uac04\uc758 \ub0a0\uc9dc \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPDAYSNC': functionDescription('\uc774 \ud568\uc218\ub294 \uacb0\uc0b0\uc77c\ubd80\ud130 \ub2e4\uc74c \uc774\uc790 \uc9c0\uae09\uc77c\uae4c\uc9c0\uc758 \ub0a0\uc9dc \uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPNCD': functionDescription('\uc774 \ud568\uc218\ub294 \uacb0\uc0b0\uc77c \uc774\ud6c4\uc758 \ub2e4\uc74c \uc774\uc790 \uc9c0\uae09\uc77c\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPNUM': functionDescription('\uc774 \ud568\uc218\ub294 \uacb0\uc0b0\uc77c\uacfc \ub9cc\uae30\uc77c \uc0ac\uc774\uc758 \uc774\uc790 \uc9c0\uae09 \ud69f\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPPCD': functionDescription('\uc774 \ud568\uc218\ub294 \uacb0\uc0b0\uc77c \ubc14\ub85c \uc804 \uc774\uc790 \uc9c0\uae09\uc77c\uc744 \ub098\ud0c0\ub0b4\ub294 \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COVAR': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \uac01 \ub370\uc774\ud130 \uc694\uc18c \uc30d\uc5d0 \ub300\ud55c \ud3b8\ucc28\ub97c \uacf1\ud55c \uac12\uc758 \ud3c9\uade0\uc744 \ub098\ud0c0\ub0b4\ub294 \uacf5 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'CRITBINOM': functionDescription('\uc774 \ud568\uc218\ub294 \ub204\uc801 \uc774\ud56d \ubd84\ud3ec\uac00 \uae30\uc900\uce58 \uc774\uc0c1\uc774 \ub418\ub294 \uac12 \uc911 \ucd5c\uc18c\uac12\uc744 \ub098\ud0c0\ub0b4\ub294 \uc870\uac74 \uc774\ud56d\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('alpha')
        ]),
        'CUMIPMT': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\uc791 \uae30\uac04\uacfc \uc885\ub8cc \uae30\uac04 \uc0ac\uc774\uc5d0 \ub0a9\uc785\ud558\ub294 \ub300\ucd9c\uae08 \uc774\uc790\uc758 \ub204\uacc4\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('paytype')
        ]),
        'CUMPRINC': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\uc791 \uae30\uac04\uacfc \uc885\ub8cc \uae30\uac04 \uc0ac\uc774\uc5d0 \ub0a9\uc785\ud558\ub294 \ub300\ucd9c\uae08 \uc6d0\uae08\uc758 \ub204\uacc4\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('paytype')
        ]),
        'DATE': functionDescription('\uc774 \ud568\uc218\ub294 \ud2b9\uc815 \ub0a0\uc9dc\uc5d0 \ub300\ud574 \ub144, \uc6d4, \uc77c\ub85c \uc9c0\uc815\ub41c DateTime \uac1c\uccb4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('year'),
            parameterDescription('month'),
            parameterDescription('day')
        ]),
        'DATEDIF': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub0a0\uc9dc \uc0ac\uc774\uc758 \uc77c \uc218, \uc6d4 \uc218 \ub610\ub294 \ub144 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('unit')
        ]),
        'DATEVALUE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \ub0a0\uc9dc\uc758 DateTime \uac1c\uccb4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date_text')
        ]),
        'DAVERAGE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0 \uc788\ub294 \uac12\uc758 \ud3c9\uade0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DAY': functionDescription('\uc774 \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \ub2ec\uc758 \uc9c0\uc815\ub41c \ub0a0\uc9dc\uc5d0 \ud574\ub2f9\ud558\ub294 \uac12\uc744 1\uacfc 31 \uc0ac\uc774\uc758 \uc22b\uc790\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date')
        ]),
        'DAYS360': functionDescription('\uc774 \ud568\uc218\ub294 1\ub144\uc744 360\uc77c\ub85c \ud558\uc5ec, \ub450 \ub0a0\uc9dc \uc0ac\uc774\uc758 \ub0a0\uc9dc \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('method', false, true)
        ]),
        'DB': functionDescription('\uc774 \ud568\uc218\ub294 \uace0\uc815 \uc77c\ubcc4 \uade0\ud615 \ubc29\ubc95\uc744 \uc0ac\uc6a9\ud558\uc5ec \uc9c0\uc815\ud55c \uae30\uac04 \ub3d9\uc548 \uc790\uc0b0\uc758 \uac10\uac00 \uc0c1\uac01\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period'),
            parameterDescription('month')
        ]),
        'DCOUNT': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \uc22b\uc790\ub97c \ud3ec\ud568\ud558\ub294 \uc140\uc758 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DCOUNTA': functionDescription('\uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \ube44\uc5b4 \uc788\uc9c0 \uc54a\uc740 \uc140\uc758 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DDB': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ud55c \uae30\uac04 \ub3d9\uc548 \uc774\uc911 \uccb4\uac10\ubc95\uc774\ub098 \uc0ac\uc6a9\uc790\uac00 \uc815\ud558\ub294 \ub2e4\ub978 \ubc29\ubc95\uc73c\ub85c \uc790\uc0b0\uc758 \uac10\uac00 \uc0c1\uac01\uc561\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period'),
            parameterDescription('factor')
        ]),
        'DEC2BIN': functionDescription('\uc774 \ud568\uc218\ub294 10\uc9c4\uc218\ub97c 2\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEC2HEX': functionDescription('\uc774 \ud568\uc218\ub294 10\uc9c4\uc218\ub97c 16\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEC2OCT': functionDescription('\uc774 \ud568\uc218\ub294 10\uc9c4\uc218\ub97c 8\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEGREES': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc744 \ub77c\ub514\uc548 \ud615\ud0dc\uc758 \uac01\ub3c4\uc5d0\uc11c \ub3c4 \ub2e8\uc704\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'DELTA': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \uac12\uc774 \uac19\uc740\uc9c0 \uc2dd\ubcc4\ud569\ub2c8\ub2e4. \ub450 \uac12\uc774 \uac19\uc73c\uba74 1\uc744 \ubc18\ud658\ud558\uace0, \uadf8\ub807\uc9c0 \uc54a\uc73c\uba74 0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'DEVSQ': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\ubcf8 \ud3c9\uade0\uc73c\ub85c\ubd80\ud130 \ub370\uc774\ud130 \uc694\uc18c \ub610\ub294 \ub370\uc774\ud130 \uc694\uc18c \ubc30\uc5f4\uc758 \ud3b8\ucc28\uc758 \uc81c\uacf1\uc758 \ud569\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'DGET': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \ub2e8\uc77c \uac12\uc744 \ucd94\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DISC': functionDescription('\uc774 \ud568\uc218\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \ud560\uc778\uc728\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'DMAX': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \uac00\uc7a5 \ud070 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DMIN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \uac00\uc7a5 \uc791\uc740 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DOLLAR': functionDescription('\uc774 \ud568\uc218\ub294 \ud1b5\ud654 \ud615\uc2dd\uc744 \uc0ac\uc6a9\ud558\uc5ec \uc22b\uc790\ub97c \ud14d\uc2a4\ud2b8\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4. \uc18c\uc218\ub294 \uc9c0\uc815\ub41c \uc790\ub9bf\uc218\ub85c \ubc18\uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('decimals', false, true)
        ]),
        'DOLLARDE': functionDescription('\uc774 \ud568\uc218\ub294 \ubd84\uc218\ub85c \ud45c\uc2dc\ub41c \uae08\uc561\uc744 \uc18c\uc218\ub85c \ud45c\uc2dc\ub41c \uae08\uc561\uc73c\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('fractional_dollar'),
            parameterDescription('fraction')
        ]),
        'DOLLARFR': functionDescription('\uc774 \ud568\uc218\ub294 \uc18c\uc218\ub85c \ud45c\uc2dc\ub41c \uae08\uc561\uc744 \ubd84\uc218\ub85c \ud45c\uc2dc\ub41c \uae08\uc561\uc73c\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('decimal_dollar'),
            parameterDescription('fraction')
        ]),
        'DPRODUCT': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \uac12\ub4e4\uc758 \uacf1\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSTDEV': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0 \uc788\ub294 \uc22b\uc790\ub97c \uc0ac\uc6a9\ud558\uc5ec \ud45c\ubcf8 \uc9d1\ub2e8\uc758 \ud45c\uc900 \ud3b8\ucc28\ub97c \uad6c\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSTDEVP': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0 \uc788\ub294 \uc22b\uc790\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc804\uccb4 \ubaa8\uc9d1\ub2e8\uc758 \ud45c\uc900 \ud3b8\ucc28\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSUM': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0\uc11c \uac12\ub4e4\uc758 \ud569\uc744 \uad6c\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DURATION': functionDescription('\uc774 \ud568\uc218\ub294 \uac00\uc815\ub41c \uc561\uba74\uac00 $100\uc5d0 \ub300\ud55c Macaulay \uae30\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('coupon'),
            parameterDescription('yield'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'DVAR': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0 \uc788\ub294 \uc22b\uc790\ub97c \uc0ac\uc6a9\ud558\uc5ec \ud45c\ubcf8 \uc9d1\ub2e8\uc758 \ubd84\uc0b0\uc744 \uad6c\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DVARP': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc870\uac74\uc5d0 \ub9de\ub294 \ub370\uc774\ud130\ubca0\uc774\uc2a4\ub098 \ubaa9\ub85d\uc758 \uc5f4\uc5d0 \uc788\ub294 \uc22b\uc790\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc804\uccb4 \ubaa8\uc9d1\ub2e8\uc758 \ubd84\uc0b0\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'EDATE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ud55c \ub0a0\uc9dc \uc804\uc774\ub098 \ud6c4\uc758 \uac1c\uc6d4 \uc218\ub97c \ub098\ud0c0\ub0b4\ub294 \ub0a0\uc9dc\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('months')
        ]),
        'EFFECT': functionDescription('\uc774 \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \uba85\ubaa9\uc0c1\uc758 \uc5f0\uc774\uc728\uc5d0 \ub300\ud55c \uc2e4\uc9c8\uc801\uc778 \uc5f0\uc774\uc728\uacfc \uc5f0\uac04 \ubcf5\ub9ac \uacc4\uc0b0 \ud69f\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('nominal_rate'),
            parameterDescription('npery')
        ]),
        'EOMONTH': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\uc791 \ub0a0\uc9dc \uc804\uc774\ub098 \ud6c4\uc758 \uac1c\uc6d4 \uc218\ub97c \ub098\ud0c0\ub0b4\ub294 \uc6d4\ub9d0 \ub0a0\uc9dc\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('months')
        ]),
        'ERF': functionDescription('\uc774 \ud568\uc218\ub294 \ud558\ud55c\uac12\uacfc \uc0c1\ud55c\uac12 \uc0ac\uc774\uc5d0 \ud1b5\ud569\ub41c \uc624\ucc28 \ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('lower_limit'),
            parameterDescription('upper_limit')
        ]),
        'ERFC': functionDescription('\uc774 \ud568\uc218\ub294 \ud558\ud55c\uac12\uacfc \ubb34\ud55c\ub300 \uc0ac\uc774\uc5d0 \ud1b5\ud569\ub41c \uc624\ucc28 \ud568\uc218\uc758 \uc5ec\uac12\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('lowerlimit')
        ]),
        'ERROR.TYPE': functionDescription('\uc774 \ud568\uc218\ub294 \uc624\ub958 \uac12 \uc911 \ud558\ub098\uc5d0 \ud574\ub2f9\ud558\ub294 \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('error_val')
        ]),
        'EURO': functionDescription('\uc774 \ud568\uc218\ub294 ISO \ud1b5\ud654 \ucf54\ub4dc\ub97c \uae30\uc900\uc73c\ub85c 1\uc720\ub85c\uc758 \ub4f1\uac00\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('code')
        ]),
        'EUROCONVERT': functionDescription('\uc774 \ud568\uc218\ub294 \uc720\ub85c \ud68c\uc6d0\uad6d \ud1b5\ud654(\uc720\ub85c \ud3ec\ud568) \uac04\uc5d0 \ud1b5\ud654\ub97c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('source'),
            parameterDescription('target'),
            parameterDescription('full_precision'),
            parameterDescription('triangulation_precision')
        ]),
        'EVEN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc744 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc9dd\uc218\uc778 \uc815\uc218\ub85c \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'EXACT': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubb38\uc790\uc5f4\uc774 \uac19\uc73c\uba74 true\ub97c \ubc18\ud658\ud558\uace0, \uadf8\ub807\uc9c0 \uc54a\uc73c\uba74 false\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text1'),
            parameterDescription('text2')
        ]),
        'EXP': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \uac70\ub4ed\uc81c\uacf1\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'EXPONDIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc218 \ubd84\ud3ec \ub610\ub294 \ud655\ub960 \ubc00\ub3c4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('lambda'),
            parameterDescription('cumulative')
        ]),
        'FACT': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 \uacc4\uc2b9\uac12\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'FACTDOUBLE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 \uc774\uc911 \uacc4\uc2b9\uac12\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'FALSE': functionDescription('\uc774 \ud568\uc218\ub294 \ub17c\ub9ac\uac12 FALSE\uc5d0 \ud574\ub2f9\ud558\ub294 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'FDIST': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc9d1\ud569 \uc0ac\uc774\uc758 \ubd84\ud3ec\ub3c4\ub97c \ub098\ud0c0\ub0b4\ub294 F \ud655\ub960 \ubd84\ud3ec\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'FIND': functionDescription('\uc774 \ud568\uc218\ub294 \ub2e4\ub978 \ud14d\uc2a4\ud2b8 \uac12\uc5d0\uc11c \ud14d\uc2a4\ud2b8 \uac12\uc744 \ucc3e\uace0 \uac80\uc0c9\ud55c \ud14d\uc2a4\ud2b8\uc5d0\uc11c \ud574\ub2f9 \ud14d\uc2a4\ud2b8 \uac12\uc758 \uc704\uce58\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER, false, true)
        ]),
        'FINV': functionDescription('\uc774 \ud568\uc218\ub294 F \ud655\ub960 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'FISHER': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc5d0 \ub300\ud55c Fisher \ubcc0\ud658\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'FISHERINV': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc5d0 \ub300\ud55c Fisher \ubcc0\ud658\uc758 \uc5ed\ubcc0\ud658 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'FIXED': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790\ub97c \uc9c0\uc815\ub41c \uc18c\uc218 \uc790\ub9bf\uc218\ub85c \ubc18\uc62c\ub9bc\ud558\uace0, \ub9c8\uce68\ud45c\uc640 \uc27c\ud45c(\uc9c0\uc815\ub41c \uacbd\uc6b0)\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc18c\uc218\uc758 \uc11c\uc2dd\uc744 \uc9c0\uc815\ud558\uace0, \uacb0\uacfc\ub97c \ud14d\uc2a4\ud2b8\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('decimals', false, true),
            parameterDescription('no_commas', false, true)
        ]),
        'FLOOR': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \ubc30\uc218\uac00 \ub418\ub3c4\ub85d \uc808\ub300 \uac12\uc744 \ub0b4\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('significance')
        ]),
        'FORECAST': functionDescription('\uc774 \ud568\uc218\ub294 \uae30\uc874 \uac12\uc744 \uc0ac\uc6a9\ud558\uc5ec \ubbf8\ub798 \uac00\uce58\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('Yarray'),
            parameterDescription('Xarray')
        ]),
        'FREQUENCY': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc758 \ubc94\uc704 \ub0b4\uc5d0\uc11c \uac12\uc758 \ube48\ub3c4\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4. \uc774 \ud568\uc218\ub294 \uc218\uc758 \uc138\ub85c \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('data_array'),
            parameterDescription('bins_array')
        ]),
        'FTEST': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc30\uc5f4\uc758 \ubd84\uc0b0\uc774 \ud06c\uac8c \ucc28\uc774\uac00 \ub098\uc9c0 \uc54a\ub294 \uacbd\uc6b0 \ub2e8\uce21 \ud655\ub960\uc778 F-\uac80\uc815 \uacb0\uacfc\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'FV': functionDescription('\uc774 \ud568\uc218\ub294 \ud604\uc7ac \uac00\uce58, \uc8fc\uae30\uc801\uc778 \ub0a9\uc785 \ubc0f \uc9c0\uc815\ub41c \uc774\uc790\uc728\uc5d0 \uc758\uac70\ud55c \ud22c\uc790\uc758 \ubbf8\ub798 \uac00\uce58\ub97c \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('numper'),
            parameterDescription('paymt'),
            parameterDescription('pval'),
            parameterDescription('type')
        ]),
        'FVSCHEDULE': functionDescription('\uc774 \ud568\uc218\ub294 \ucd08\uae30 \uc6d0\uae08\uc5d0 \uc77c\ub828\uc758 \ubcf5\ub9ac \uc774\uc728\uc744 \uc801\uc6a9\ud588\uc744 \ub54c\uc758 \ubbf8\ub798 \uac00\uce58\ub97c \uc0b0\ucd9c\ud569\ub2c8\ub2e4. \ubcc0\ub3d9 \uae08\ub9ac\ub97c \uc801\uc6a9\ud558\uc5ec \ud22c\uc790\uc758 \ubbf8\ub798 \uac00\uce58\ub97c \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('principal'),
            parameterDescription('schedule')
        ]),
        'GAMMADIST': functionDescription('\uc774 \ud568\uc218\ub294 \uac10\ub9c8 \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'GAMMAINV': functionDescription('\uc774 \ud568\uc218\ub294 \uac10\ub9c8 \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta')
        ]),
        'GAMMALN': functionDescription('\uc774 \ud568\uc218\ub294 \uac10\ub9c8 \ud568\uc218 G(x)\uc758 \uc790\uc5f0 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'GCD': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \uc218\uc758 \ucd5c\ub300 \uacf5\uc57d\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'GEOMEAN': functionDescription('\uc774 \ud568\uc218\ub294 \uc591\uc218 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \uae30\ud558 \ud3c9\uade0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'GESTEP': functionDescription('\uc774 \ud568\uc218(\ubcf4\ub2e4 \ud06c\uac70\ub098 \uac19\uc74c)\ub294 \uc22b\uc790\uac00 \uc784\uacc4\uac12\uacfc \uac19\uc740\uc9c0 \uc5ec\ubd80\ub97c \ub098\ud0c0\ub0c5\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('step')
        ]),
        'GROWTH': functionDescription('\uc774 \ud568\uc218\ub294 \uc608\uc0c1 \uc9c0\uc218 \uc99d\uac00\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4. \uc774 \ud568\uc218\ub294 \uae30\uc874 x \ubc0f y \uac12\uc744 \uc0ac\uc6a9\ud558\uc5ec \uc9c0\uc815\ud55c \uc0c8 x \uac12 \uacc4\uc5f4\uc5d0 \ub300\ud55c y \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('newx'),
            parameterDescription('constant')
        ]),
        'HARMEAN': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \uc870\ud654 \ud3c9\uade0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'HEX2BIN': functionDescription('\uc774 \ud568\uc218\ub294 16\uc9c4\uc218\ub97c 2\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'HEX2DEC': functionDescription('\uc774 \ud568\uc218\ub294 16\uc9c4\uc218\ub97c 10\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'HEX2OCT': functionDescription('\uc774 \ud568\uc218\ub294 16\uc9c4\uc218\ub97c 8\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'HLOOKUP': functionDescription('\uc774 \ud568\uc218\ub294 \uccab \ud589\uc5d0\uc11c \uac12\uc744 \uac80\uc0c9\ud55c \ub2e4\uc74c \uc9c0\uc815\ub41c \ud589\uc758 \ub3d9\uc77c\ud55c \uc5f4\uc5d0 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('lookup_value'),
            parameterDescription('table_array'),
            parameterDescription('row_index_num'),
            parameterDescription('range_lookup', false, true, RANGE_LOOKUP_ENUM_INFO)
        ]),
        'HOUR': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc2dc\uac04\uc5d0 \ud574\ub2f9\ud558\ub294 \uc2dc\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('time')
        ]),
        'HYPGEOMDIST': functionDescription('\uc774 \ud568\uc218\ub294 \ucd08\uae30 \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('sample_s'),
            parameterDescription('number_sample'),
            parameterDescription('population_s'),
            parameterDescription('number_pop')
        ]),
        'IF': functionDescription('\uc774 \ud568\uc218\ub294 \uc81c\uacf5\ub41c \ub450 \uac12\uc744 \ube44\uad50\ud55c \ud6c4 \uacb0\uacfc\uc5d0 \ub530\ub77c \ub458 \uc911 \ud558\ub098\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('logical_test'),
            parameterDescription('value_if_true'),
            parameterDescription('value_if_false')
        ]),
        'IFERROR': functionDescription('\uc774 \ud568\uc218\ub294 \uc218\uc2dd\uc744 \ud3c9\uac00\ud55c \ud6c4 \uc81c\uacf5\ub41c \uac12(\uc624\ub958\uac00 \uc788\ub294 \uacbd\uc6b0) \ub610\ub294 \uc218\uc2dd \uacb0\uacfc\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('value_if_error')
        ]),
        'IMABS': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc808\ub300\uac12 \ub610\ub294 \ubaa8\ub4c8\ub7ec\uc2a4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMAGINARY': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \ud5c8\uc218\ubd80 \uacc4\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMARGUMENT': functionDescription('\uc774 \ud568\uc218\ub294 \uac01\ub3c4\ub97c \ub77c\ub514\uc548\uc73c\ub85c \ud45c\uc2dc\ud55c \uc778\uc218theta\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMCONJUGATE': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \ucf24\ub808 \ubcf5\uc18c\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMCOS': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 COS\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMDIV': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubcf5\uc18c\uc218\uc758 \ubaab\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum'),
            parameterDescription('complexdenom')
        ]),
        'IMEXP': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc9c0\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMLN': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc790\uc5f0 \ub85c\uadf8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMLOG2': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \ubc11\uc774 2\uc778 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMLOG10': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc0c1\uc6a9 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMPOWER': functionDescription('\uc774 \ud568\uc218\ub294 \uac70\ub4ed\uc81c\uacf1\ud55c \ubcf5\uc18c\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum'),
            parameterDescription('powernum')
        ]),
        'IMPRODUCT': functionDescription('\uc774 \ud568\uc218\ub294 \ucd5c\ub300 29\uac1c \ubcf5\uc18c\uc218\uc758 \uacf1\uc744 x+yi \ub610\ub294 x+yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2', true)
        ]),
        'IMREAL': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc2e4\uc218\ubd80 \uacc4\uc218\ub97c x+yi \ub610\ub294 x+yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMSIN': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc0ac\uc778\uc744 x+yi \ub610\ub294 x+yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMSQRT': functionDescription('\uc774 \ud568\uc218\ub294 \ubcf5\uc18c\uc218\uc758 \uc81c\uacf1\uadfc\uc744 x+yi \ub610\ub294 x+yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMSUB': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubcf5\uc18c\uc218\uc758 \ucc28\ub97c x+yi \ub610\ub294 x+yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2')
        ]),
        'IMSUM': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \uac1c \uc774\uc0c1\uc758 \ubcf5\uc18c\uc218\uc758 \ud569\uc744 x+yi \ub610\ub294 x+yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2', true)
        ]),
        'INDEX': functionDescription('\uc774 \ud568\uc218\ub294 \ubc30\uc5f4 \ub610\ub294 \ubc94\uc704 \ub0b4\uc5d0\uc11c \uac12 \ub610\ub294 \uac12\uc5d0 \ub300\ud55c \ucc38\uc870\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('row_num'),
            parameterDescription('column_num'),
            parameterDescription('area_num')
        ]),
        'INDIRECT': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\ub85c \uc9c0\uc815\ub41c \ucc38\uc870\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \ucc38\uc870\ub97c \uc989\uc2dc \uacc4\uc0b0\ud558\uc5ec \ucf58\ud150\uce20\ub97c \ud45c\uc2dc\ud569\ub2c8\ub2e4.', [
            parameterDescription('ref_text'),
            parameterDescription('a1_style', false, true)
        ]),
        'INT': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\ub97c \uc18c\uc218\uc810 \uc544\ub798\ub97c \ubc84\ub9ac\uace0 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc815\uc218\ub85c \ub0b4\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'INTERCEPT': functionDescription('\uc774 \ud568\uc218\ub294 \uae30\uc874 x \uac12\uacfc y \uac12\uc744 \uc0ac\uc6a9\ud558\uc5ec \uc120\uc774 y\ucd95\uacfc \uad50\ucc28\ud558\ub294 \uc810\uc758 \uc88c\ud45c\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('dependent'),
            parameterDescription('independent')
        ]),
        'INTRATE': functionDescription('\uc774 \ud568\uc218\ub294 \uc644\uc804 \ud22c\uc790\ud55c \uc720\uac00 \uc99d\uad8c\uc758 \uc774\uc790\uc728\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('investment'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'IPMT': functionDescription('\uc774 \ud568\uc218\ub294 \ub300\ucd9c \uc774\uc790 \ub0a9\uc785\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'IRR': functionDescription('\uc774 \ud568\uc218\ub294 \ubc30\uc5f4\uc5d0 \uc22b\uc790\ub85c \ud45c\uc2dc\ub418\ub294 \uc77c\ub828\uc758 \ud604\uae08 \ud750\ub984\uc5d0 \ub300\ud55c \ub0b4\ubd80 \uc218\uc775\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('arrayvals'),
            parameterDescription('estimate')
        ]),
        'ISBLANK': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc774 \ube44\uc5b4 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISERR': functionDescription('\uc774 \ud568\uc218(Is Error Other Than Not Available)\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc5d0 \uc0ac\uc6a9 \ubd88\uac00\ub2a5(#N/A) \uc774\uc678 \uc624\ub958\uac00 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISERROR': functionDescription('\uc774 \ud568\uc218(Is Error of Any Kind)\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc5d0 \uc624\ub958\uac00 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISEVEN': functionDescription('\uc774 \ud568\uc218(Is Number Even)\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc774 \uc9dd\uc218\uc778\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISLOGICAL': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc774 \ub17c\ub9ac\uac12(Boolean)\uc778\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISNA': functionDescription('\uc774 \ud568\uc218(Is Not Available)\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc5d0 \uc0ac\uc6a9 \ubd88\uac00\ub2a5(#N/A) \uc624\ub958 \uac12\uc774 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISNONTEXT': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc5d0 \ud14d\uc2a4\ud2b8 \uc774\uc678\uc758 \ub370\uc774\ud130 \ud615\uc2dd\uc774 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISNUMBER': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc5d0 \uc22b\uc790 \ub370\uc774\ud130\uac00 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISODD': functionDescription('\uc774 \ud568\uc218sms \uc22b\uc790\uac00 \ud640\uc218\uc774\uba74 TRUE\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISPMT': functionDescription('\uc774 \ud568\uc218\ub294 \uc77c\uc815 \uae30\uac04 \ub3d9\uc548\uc758 \ud22c\uc790\uc5d0 \ub300\ud55c \uc774\uc790 \uc9c0\uae09\uc561\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pv')
        ]),
        'ISREF': functionDescription('\uc774 \ud568\uc218(Is Reference)\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc774 \ub2e4\ub978 \uc140\uc5d0 \ub300\ud55c \ucc38\uc870\uc778\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'ISTEXT': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc140\uc758 \uac12, \uc2dd \ub610\ub294 \ub0b4\uc6a9\uc5d0 \ud14d\uc2a4\ud2b8 \ub370\uc774\ud130\uac00 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud14c\uc2a4\ud2b8\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'KURT': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \ucca8\ub3c4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2'),
            parameterDescription('number3'),
            parameterDescription('number4', true)
        ]),
        'LARGE': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c n\ubc88\uc9f8\ub85c \ud070 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. n\uc740 \uc9c0\uc815\ub429\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'LCM': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \uc218\uc758 \ucd5c\uc18c \uacf5\ubc30\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'LEFT': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \uac12\uc5d0\uc11c \ub9e8 \uc67c\ucabd\uc5d0 \uc704\uce58\ud55c \uc9c0\uc815\ub41c \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('num_chars')
        ]),
        'LEN': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uae38\uc774(\ubb38\uc790 \uc218)\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'LINEST': functionDescription('\uc774 \ud568\uc218\ub294 \uc904\uc5d0 \ub300\ud55c \ud1b5\uacc4\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('constant'),
            parameterDescription('stats')
        ]),
        'LN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 \uc790\uc5f0 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'LOG': functionDescription('\uc774 \ud568\uc218\ub294 \uc218 X\uc5d0 \ub300\ud574 \ubc11\uc774 Y\uc778 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('base')
        ]),
        'LOG10': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc5d0 \ub300\ud574 \ubc11\uc774 10\uc778 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'LOGEST': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130\uc640 \uc77c\uce58\ud558\ub294 \uc9c0\uc218 \uace1\uc120\uc744 \uacc4\uc0b0\ud558\uace0 \uace1\uc120\uc744 \uc124\uba85\ud558\ub294 \uac12\uc758 \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('constant'),
            parameterDescription('stats')
        ]),
        'LOGINV': functionDescription('\uc774 \ud568\uc218\ub294 x\uc5d0 \ub300\ud55c \ub85c\uadf8 \uc815\uaddc \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc5ec\uae30\uc11c LN(x)\uc740 \uc9c0\uc815\ub41c \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\ub97c \uac16\ub294 \uc815\uaddc \ubd84\ud3ec\uc785\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'LOGNORMDIST': functionDescription('\uc774 \ud568\uc218\ub294 x\uc5d0 \ub300\ud55c \ub204\uc801 \uc790\uc5f0 \ub85c\uadf8 \uc815\uaddc \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc5ec\uae30\uc11c LN(x)\uc740 \uc9c0\uc815\ub41c \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\ub97c \uac16\ub294 \uc815\uaddc \ubd84\ud3ec\uc785\ub2c8\ub2e4. \uc774 \ud568\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \ub300\uc218\uc801\uc73c\ub85c \ubcc0\ud658\ub41c \ub370\uc774\ud130\ub97c \ubd84\uc11d\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
        ]),
        'LOOKUP': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc744 \uac80\uc0c9\ud558\uace0 \ub450 \ubc88\uc9f8 \uc601\uc5ed\uc758 \ub3d9\uc77c\ud55c \uc704\uce58\uc5d0\uc11c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_vector'),
            parameterDescription('result_vector')
        ]),
        'LOWER': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8\ub97c \uc18c\ubb38\uc790\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'MATCH': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704 \ub0b4\uc5d0\uc11c \uc9c0\uc815\ub41c \ud56d\ubaa9\uc758 \uc0c1\ub300 \uc704\uce58\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription('match_type')
        ]),
        'XMATCH': functionDescription('\ubc30\uc5f4\uc5d0 \uc788\ub294 \ud56d\ubaa9\uc758 \uc0c1\ub300 \uc704\uce58\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uae30\ubcf8\uc801\uc73c\ub85c \uc815\ud655\ud788 \uc77c\uce58\ud574\uc57c \ud569\ub2c8\ub2e4 .', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription(MATCH_MODE, false, true, LOOKUP_MATCH_MODE_ENUM_INFO),
            parameterDescription('search_mode', false, true, LOOKUP_SEARCH_MODE_ENUM_INFO)
        ]),
        'XLOOKUP': functionDescription('\uc77c\uce58 \ud56d\ubaa9\uc5d0 \ub300\ud55c \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc744 \uac80\uc0c9\ud558\uace0 \ub450 \ubc88\uc9f8 \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc5d0\uc11c \ud574\ub2f9 \ud56d\ubaa9\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uae30\ubcf8\uc801\uc73c\ub85c \uc815\ud655\ud558\uac8c \uc77c\uce58\ud558\ub294 \ud56d\ubaa9\uc774 \uc0ac\uc6a9\ub429\ub2c8\ub2e4.', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription('return_array'),
            parameterDescription(IF_NOT_FOUND, false, true),
            parameterDescription(MATCH_MODE, false, true, LOOKUP_MATCH_MODE_ENUM_INFO),
            parameterDescription('search_mode', false, true, LOOKUP_SEARCH_MODE_ENUM_INFO)
        ]),
        'MAX': functionDescription('\uc774 \ud568\uc218\ub294 \uc778\uc218\uc758 \ubaa8\ub4e0 \uac12 \uc911\uc5d0\uc11c \uac00\uc7a5 \ud070 \uac12\uc778 \ucd5c\ub300\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MAXA': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8\uc640 \ub17c\ub9ac\uac12\uc744 \ud3ec\ud568\ud558\uc5ec \uc778\uc218 \ubaa9\ub85d\uc5d0\uc11c \uac00\uc7a5 \ud070 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'MDETERM': functionDescription('\uc774 \ud568\uc218\ub294 \ubc30\uc5f4\uc758 \ud589\ub82c \uc2dd\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array')
        ]),
        'MDURATION': functionDescription('\uc774 \ud568\uc218\ub294 \uac00\uc815\ub41c \uc561\uba74\uac00\uac00 $100\uc778 \uc720\uac00 \uc99d\uad8c\uc758 \uc218\uc815\ub41c Macaulay \uae30\uac04\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('coupon'),
            parameterDescription('yield'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'MEDIAN': functionDescription('\uc774 \ud568\uc218\ub294 \uc81c\uacf5\ub41c \uc22b\uc790\ub4e4 \uc911 \uc911\uac04\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc989, \uc22b\uc790\ub4e4 \uc911 \uc808\ubc18\uc740 \uc911\uac04\uac12\ubcf4\ub2e4 \ud070 \uac12\uc744 \uac16\uace0, \uc808\ubc18\uc740 \uc911\uac04\uac12\ubcf4\ub2e4 \uc791\uc740 \uac12\uc744 \uac16\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MID': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc704\uce58\uc5d0\uc11c \uc2dc\uc791\ud558\uc5ec \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc5d0\uc11c \uc694\uccad\ub41c \uc218\uc758 \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('start_num'),
            parameterDescription('num_chars')
        ]),
        'MIN': functionDescription('\uc774 \ud568\uc218\ub294 \uc778\uc218\uc758 \ubaa8\ub4e0 \uac12 \uc911\uc5d0\uc11c \uac00\uc7a5 \uc791\uc740 \uac12\uc778 \ucd5c\uc18c\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MINA': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8\uc640 \ub17c\ub9ac\uac12\uc744 \ud3ec\ud568\ud558\uc5ec \uc778\uc218 \ubaa9\ub85d\uc5d0\uc11c \ucd5c\uc18c\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'MINUTE': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc2dc\uac04\uc5d0 \ud574\ub2f9\ud558\ub294 \ubd84\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('time')
        ]),
        'MINVERSE': functionDescription('\uc774 \ud568\uc218\ub294 \ubc30\uc5f4\uc758 \uc5ed\ud589\ub82c\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array')
        ]),
        'MIRR': functionDescription('\uc774 \ud568\uc218\ub294 \uc77c\ub828\uc758 \uc8fc\uae30\uc801\uc778 \ud604\uae08 \ud750\ub984\uc5d0 \ub300\ud55c \uc218\uc815\ub41c \ub0b4\ubd80 \uc218\uc775\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('values'),
            parameterDescription('finance_rate'),
            parameterDescription('reinvest_rate')
        ]),
        'MMULT': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc30\uc5f4\uc758 \ud589\ub82c \uacf1\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'MOD': functionDescription('\uc774 \ud568\uc218\ub294 \ub098\ub217\uc148\uc758 \ub098\uba38\uc9c0\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('dividend'),
            parameterDescription('divisor')
        ]),
        'MODE': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c \uac00\uc7a5 \uc790\uc8fc \ubc1c\uc0dd\ud558\ub294 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MONTH': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \ub0a0\uc9dc \uac12\uc5d0 \ud574\ub2f9\ud558\ub294 \uc6d4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date')
        ]),
        'MROUND': functionDescription('\uc774 \ud568\uc218\ub294 \uc6d0\ud558\ub294 \ubc30\uc218\ub85c \ubc18\uc62c\ub9bc\ub41c \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('multiple')
        ]),
        'MULTINOMIAL': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc758 \ud569\uacc4\uc640 \uacc4\uc2b9\uac12\uc758 \uacf1\uc758 \ube44\uc728\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MUNIT': functionDescription('Munit \ud568\uc218\ub294 \uc9c0\uc815 \ub41c \ucc28\uc6d0\uc5d0 \ub300 \ud55c \ub2e8\uc704 \ud589\ub82c\uc744 \ubc18\ud658 \ud569\ub2c8\ub2e4.', [
            parameterDescription('dimension'),
        ]),
        'N': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790\ub85c \ubcc0\ud658\ub41c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'NA': functionDescription('\uc774 \ud568\uc218\ub294 \ud3c9\uade0\uc774 \uc0ac\uc6a9 \ubd88\uac00\ud55c \uc624\ucc28 \uac12 #N/A\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'SHEET': functionDescription('\ucc38\uc870 \uc2dc\ud2b8\uc758 \uc2dc\ud2b8 \ubc88\ud638\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value', false, true),
        ]),
        'SHEETS': functionDescription('\ucc38\uc870 \uc601\uc5ed\uc5d0 \uc788\ub294 \uc2dc\ud2b8 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference', false, true)
        ]),
        'NEGBINOMDIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc74c \uc774\ud56d \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number_f'),
            parameterDescription('number_s'),
            parameterDescription('probability_s')
        ]),
        'NETWORKDAYS': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\uc791 \ub0a0\uc9dc\uc640 \uc885\ub8cc \ub0a0\uc9dc \uc0ac\uc774\uc758 \uc804\uccb4 \uc791\uc5c5\uc77c\uc758 \ucd1d \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('holidays', false, true)
        ]),
        'NOMINAL': functionDescription('\uc774 \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \uc2e4\ud6a8 \uc774\uc728\uc5d0 \ub300\ud55c \uba85\ubaa9\uc0c1\uc758 \uc5f0\uc774\uc728\uacfc \uc5f0\uac04 \ubcf5\ub9ac \uacc4\uc0b0 \ud69f\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('effect_rate'),
            parameterDescription('npery')
        ]),
        'NORMDIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ud55c \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\uc5d0 \uc758\uac70\ud558\uc5ec \uc815\uaddc \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
            parameterDescription('cumulative')
        ]),
        'NORMINV': functionDescription('\uc774 \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\uc5d0 \uc758\uac70\ud558\uc5ec \uc815\uaddc \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORMSDIST': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\uc900 \uc815\uaddc \ub204\uc801 \ubd84\ud3ec \ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'NORMSINV': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\uc900 \uc815\uaddc \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \ubd84\ud3ec\uc758 \ud3c9\uade0\uc774 0\uc774\uace0 \ud45c\uc900 \ud3b8\ucc28\uac00 1\uc785\ub2c8\ub2e4.', [
            parameterDescription('probability')
        ]),
        'NOT': functionDescription('\uc774 \ud568\uc218\ub294 \uc778\uc218\uc758 \ub17c\ub9ac\uac12\uc744 \ub418\ub3cc\ub9bd\ub2c8\ub2e4.', [
            parameterDescription('logical')
        ]),
        'NOW': functionDescription('\uc774 \ud568\uc218\ub294 \ud604\uc7ac \ub0a0\uc9dc\uc640 \uc2dc\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'NPER': functionDescription('\uc774 \ud568\uc218\ub294 \ud604\uc7ac \uac00\uce58, \ubbf8\ub798 \uac00\uce58, \uc8fc\uae30\uc801\uc778 \ub0a9\uc785 \ubc0f \uc9c0\uc815\ub41c \uc774\uc790\uc728\uc5d0 \uc758\uac70\ud55c \ud22c\uc790 \uae30\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('paymt'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'NPV': functionDescription('\uc774 \ud568\uc218\ub294 \ud560\uc778\uc728\uacfc \uc77c\ub828\uc758 \ubbf8\ub798 \ud22c\uc790 \ubc0f \uc218\uc785\uc744 \uae30\uc900\uc73c\ub85c \ud22c\uc790\uc758 \uc21c \ud604\uc7ac \uac00\uce58\ub97c \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'OBJECT': functionDescription('\uc774 \ud568\uc218\ub294 \uc18d\uc131/\uc2dd \uc2dc\ud000\uc2a4\ub97c \uac1c\uccb4\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('property1', true),
            parameterDescription('expression1', true)
        ]),
        'OCT2BIN': functionDescription('\uc774 \ud568\uc218\ub294 8\uc9c4\uc218\ub97c 2\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'OCT2DEC': functionDescription('\uc774 \ud568\uc218\ub294 8\uc9c4\uc218\ub97c 10\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'OCT2HEX': functionDescription('\uc774 \ud568\uc218\ub294 8\uc9c4\uc218\ub97c 16\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'ODD': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac12\uc744 \uac00\uc7a5 \uac00\uae4c\uc6b4 \ud640\uc218\uc778 \uc815\uc218\ub85c \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ODDFPRICE': functionDescription('\uc774 \ud568\uc218\ub294 \uccab \uc774\uc218 \uae30\uac04\uc774 \uacbd\uc0c1 \uc774\uc218 \uae30\uac04\uacfc \ub2e4\ub978(\uc9e7\uac70\ub098 \uae34) \uc720\uac00 \uc99d\uad8c\uc758 \uc561\uba74\uac00 $100\ub2f9 \uac00\uaca9\uc744 \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
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
        'ODDFYIELD': functionDescription('\uc774 \ud568\uc218\ub294 \uccab \uc774\uc218 \uae30\uac04\uc774 \uacbd\uc0c1 \uc774\uc218 \uae30\uac04\uacfc \ub2e4\ub978(\uc9e7\uac70\ub098 \uae34) \uc720\uac00 \uc99d\uad8c\uc758 \uc5f0 \uc218\uc775\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
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
        'ODDLPRICE': functionDescription('\uc774 \ud568\uc218\ub294 \ub9c8\uc9c0\ub9c9 \uc774\uc218 \uae30\uac04\uc774 \uacbd\uc0c1 \uc774\uc218 \uae30\uac04\uacfc \ub2e4\ub978(\uc9e7\uac70\ub098 \uae34) \uc720\uac00 \uc99d\uad8c\uc758 \uc561\uba74\uac00 $100\ub2f9 \uac00\uaca9\uc744 \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('last_interest'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ODDLYIELD': functionDescription('\uc774 \ud568\uc218\ub294 \ub9c8\uc9c0\ub9c9 \uc774\uc218 \uae30\uac04\uc774 \uacbd\uc0c1 \uc774\uc218 \uae30\uac04\uacfc \ub2e4\ub978(\uc9e7\uac70\ub098 \uae34) \uc720\uac00 \uc99d\uad8c\uc758 \uc5f0 \uc218\uc775\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('last_interest'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'OFFSET': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704\uc5d0 \ub300\ud55c \ucc38\uc870\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \ubc94\uc704\ub294 \uc140 \ub610\ub294 \uc140 \ubc94\uc704\uc5d0\uc11c \uc9c0\uc815\ub41c \ud589\uacfc \uc5f4\uc758 \uac1c\uc218\uc785\ub2c8\ub2e4. \uc774 \ud568\uc218\ub294 \ub2e8\uc77c \uc140 \ub610\ub294 \uc140 \ubc94\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference'),
            parameterDescription('rows'),
            parameterDescription('cols'),
            parameterDescription('height', false, true),
            parameterDescription('width', false, true)
        ]),
        'OR': functionDescription('\uc774 \ud568\uc218\ub294 \ub17c\ub9ac\uac12 OR\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4. \uc778\uc218 \uc911 \ud558\ub098 \uc774\uc0c1\uc774 \ucc38\uc774\uba74 TRUE\ub97c \ubc18\ud658\ud558\uace0, \ubaa8\ub4e0 \uc778\uc218\uac00 \uac70\uc9d3\uc774\uba74 FALSE\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('logical1'),
            parameterDescription('logical2', true)
        ]),
        'PEARSON': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc138\ud2b8\uc758 \uc120\ud615 \uad00\uacc4\ub97c \ub098\ud0c0\ub0b4\ub294 -1.0\uc5d0\uc11c 1.0 \uc0ac\uc774\uc758 \ubb34 \ucc28\uc6d0 \uc778\ub371\uc2a4 \uc778 Pearson \uacf1\uc148 \uc0c1\uad00 \uacc4\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array_ind'),
            parameterDescription('array_dep')
        ]),
        'PERCENTILE': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704\uc5d0\uc11c n\ubc88\uc9f8 \ubc31\ubd84\uc704\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'PERCENTRANK': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c \ubc31\ubd84\uc728 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('significance')
        ]),
        'PERMUT': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \ud56d\ubaa9 \uc218\uc5d0 \ub300\ud574 \uac00\ub2a5\ud55c \uc21c\uc5f4\uc758 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'PI': functionDescription('\uc774 \ud568\uc218\ub294 PI\ub97c 3.1415926536\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'PMT': functionDescription('\uc774 \ud568\uc218\ub294 \ud604\uc7ac \uac00\uce58, \uc9c0\uc815\ub41c \uc774\uc790\uc728 \ubc0f \ud56d\ubaa9 \uc218\uc5d0 \uc758\uac70\ud55c \ub300\ucd9c \ub300\uae08 \uc9c0\uae09\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'POISSON': functionDescription('\uc774 \ud568\uc218\ub294 Poisson \ud655\ub960 \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('nevents'),
            parameterDescription('mean'),
            parameterDescription('cumulative')
        ]),
        'POWER': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ud55c \uc218\ub97c \uc9c0\uc815\ud55c \uac70\ub4ed\uc81c\uacf1\uc73c\ub85c \uc62c\ub9bd\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('power')
        ]),
        'PPMT': functionDescription('\uc774 \ud568\uc218\ub294 \ud604\uc7ac \uac00\uce58, \uc9c0\uc815\ub41c \uc774\uc790\uc728 \ubc0f \ud56d\ubaa9 \uc218\uc5d0 \uc758\uac70\ud55c \ub300\ucd9c\uc758 \uc6d0\uae08 \ub0a9\uc785\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'PRICE': functionDescription('\uc774 \ud568\uc218\ub294 \uc815\uae30\uc801\uc73c\ub85c \uc774\uc790\ub97c \uc9c0\uae09\ud558\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \uc561\uba74\uac00 $100\ub2f9 \uac00\uaca9\uc744 \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'PRICEDISC': functionDescription('\uc774 \ud568\uc218\ub294 \ud560\uc778\ub41c \uc720\uac00 \uc99d\uad8c\uc758 \uc561\uba74\uac00 $100\ub2f9 \uac00\uaca9\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'PRICEMAT': functionDescription('\uc774 \ud568\uc218\ub294 \uc774\uc790\ub97c \uc9c0\uae09\ud558\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \uc561\uba74\uac00 $100\ub2f9 \ub9cc\uae30 \uc2dc \uac00\uaca9\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('basis')
        ]),
        'PROB': functionDescription('\uc774 \ud568\uc218\ub294 \uc601\uc5ed \ub0b4\uc758 \uac12\uc774 \ub450 \ud55c\uacc4\uac12 \uc0ac\uc774\uc5d0 \uc788\uc744 \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x_range'),
            parameterDescription('prob_range'),
            parameterDescription('lower_limit'),
            parameterDescription('upper_limit')
        ]),
        'PRODUCT': functionDescription('\uc774 \ud568\uc218\ub294 \ubaa8\ub4e0 \uc778\uc218\uc758 \uacf1\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'PROPER': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uac01 \ub2e8\uc5b4\uc5d0\uc11c \uccab \uae00\uc790\ub97c \ub300\ubb38\uc790\ud654\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'PROPERTY': functionDescription('\uc774 \ud568\uc218\ub294 \uac1c\uccb4\uc758 \uc18d\uc131 \uacbd\ub85c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('data_expression'),
            parameterDescription('property_path')
        ]),
        'PV': functionDescription('\uc774 \ud568\uc218\ub294 \uc774\uc790\uc728, \uc8fc\uae30\uc801\uc778 \ub0a9\uc785 \ud69f\uc218\uc640 \uae08\uc561, \ubbf8\ub798 \uac00\uce58\uc5d0 \uc758\uac70\ud55c \ud22c\uc790\uc758 \ud604\uc7ac \uac00\uce58\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \ud604\uc7ac \uac00\uce58\ub294 \uc77c\ub828\uc758 \ubbf8\ub798 \ud22c\uc790\uac00 \uc0c1\uc751\ud558\ub294 \ucd1d\ud569\uacc4\uc785\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('numper'),
            parameterDescription('paymt'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'QUARTILE': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc774 \uc18d\ud558\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \ubc31\ubd84\uc704\uc218(1/4 \ub610\ub294 25%)\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'QUOTIENT': functionDescription('\uc774 \ud568\uc218\ub294 \ub098\ub217\uc148 \ubaab\uc758 \uc815\uc218 \ubd80\ubd84\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \ub098\ub217\uc148 \ubaab\uc758 \ub098\uba38\uc9c0\ub97c \ubb34\uc2dc\ud558\ub824\uba74 \uc774 \ud568\uc218\ub97c \uc0ac\uc6a9\ud569\ub2c8\ub2e4.', [
            parameterDescription('numerator'),
            parameterDescription('denominator')
        ]),
        'RADIANS': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\ub97c \ub3c4 \ub2e8\uc704\uc5d0\uc11c \ub77c\ub514\uc548 \ud615\ud0dc\uc758 \uac01\ub3c4\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'RAND': functionDescription('\uc774 \ud568\uc218\ub294 0\uacfc 1 \uc0ac\uc774\uc758 \uade0\ub4f1\ud558\uac8c \ubd84\ud3ec\ub41c \ub09c\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'RANDBETWEEN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ud55c \uc22b\uc790 \uc0ac\uc774\uc758 \ub09c\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('bottom'),
            parameterDescription('top')
        ]),
        'RANGEBLOCKSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704 \ud15c\ud50c\ub9bf\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('template_range'),
            parameterDescription('data_expression')
        ]),
        'RANK': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uc9d1\ud569 \ub0b4\uc5d0\uc11c \uc9c0\uc815\ud55c \uc218\uc758 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc9d1\ud569\uc744 \uc815\ub82c\ud560 \uacbd\uc6b0 \uc218\uc758 \uc21c\uc704\ub294 \ubaa9\ub85d \ub0b4\uc5d0\uc11c \ud574\ub2f9 \uc218\uc758 \uc704\uce58\uc785\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('order', false, true)
        ]),
        'RATE': functionDescription('\uc774 \ud568\uc218\ub294 \uc5f0\uae08\uc758 \uae30\uac04\ubcc4 \uc774\uc790\uc728\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('nper'),
            parameterDescription('pmt'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type'),
            parameterDescription('guess')
        ]),
        'RECEIVED': functionDescription('\uc774 \ud568\uc218\ub294 \uc644\uc804 \ud22c\uc790 \uc720\uac00 \uc99d\uad8c\uc5d0 \ub300\ud574 \ub9cc\uae30 \uc2dc \uc218\ub839\ud558\ub294 \uae08\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('investment'),
            parameterDescription('discount'),
            parameterDescription('basis')
        ]),
        'REPLACE': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uc77c\ubd80\ub97c \ub2e4\ub978 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\ub85c \ubc14\uafc9\ub2c8\ub2e4.', [
            parameterDescription('old_text'),
            parameterDescription('start_num'),
            parameterDescription('num_chars'),
            parameterDescription('new_text')
        ]),
        'REPT': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8\ub97c \uc9c0\uc815\ub41c \ud69f\uc218\ub9cc\ud07c \ubc18\ubcf5\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('number_times')
        ]),
        'RIGHT': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \uac12\uc5d0\uc11c \ub9e8 \uc624\ub978\ucabd\uc5d0 \uc704\uce58\ud55c \uc9c0\uc815\ub41c \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('num_chars')
        ]),
        'ROMAN': functionDescription('\uc774 \ud568\uc218\ub294 \uc544\ub77c\ube44\uc544 \uc22b\uc790\ub97c \ud14d\uc2a4\ud2b8\uc778 \ub85c\ub9c8 \uc22b\uc790\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('form')
        ]),
        'ROUND': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc18c\uc218 \uc790\ub9bf\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc9c0\uc815\ub41c \uac12\uc744 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc22b\uc790\ub85c \ubc18\uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROUNDDOWN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc18c\uc218 \uc790\ub9bf\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc9c0\uc815\ub41c \uac12\uc744 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc22b\uc790\ub85c \ub0b4\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROUNDUP': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc18c\uc218 \uc790\ub9bf\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc9c0\uc815\ub41c \uac12\uc744 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc22b\uc790\ub85c \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROW': functionDescription('\uc774 \ud568\uc218\ub294 \ucc38\uc870 \uc601\uc5ed\uc758 \ud589 \ubc88\ud638\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference')
        ]),
        'ROWS': functionDescription('\uc774 \ud568\uc218\ub294 \ubc30\uc5f4\uc5d0 \uc788\ub294 \ud589 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array')
        ]),
        'RSQ': functionDescription('\uc774 \ud568\uc218\ub294 y \uac12\uacfc x \uac12\uc774 \uc54c\ub824\uc9c4 \ub370\uc774\ud130 \uc694\uc18c\uc5d0 \uc758\uac70\ud558\uc5ec Pearson \uacf1 \uc21c\uac04 \uc0c1\uad00 \uacc4\uc218\uc758 \uc81c\uacf1(R \uc81c\uacf1)\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SEARCH': functionDescription('\uc774 \ud568\uc218\ub294 \ub2e4\ub978 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc5d0\uc11c \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc744 \ucc3e\uc740 \ub2e4\uc74c \ubc1c\uacac\ub41c \ud14d\uc2a4\ud2b8\uc758 \uc2dc\uc791 \uc704\uce58\uc758 \uc9c0\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER, false, true)
        ]),
        'SECOND': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc2dc\uac04\uc5d0 \ub300\ud55c \ucd08(0~59) \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('time')
        ]),
        'SERIESSUM': functionDescription('\uc774 \ud568\uc218\ub294 \uba71\uae09\uc218\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('n'),
            parameterDescription('m'),
            parameterDescription('coefficients')
        ]),
        'SIGN': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dd \ub610\ub294 \uc218\uc758 \ubd80\ud638\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'SIN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac01\ub3c4\uc758 SIN\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'SINH': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 SINH\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'SKEW': functionDescription('\uc774 \ud568\uc218\ub294 \ubd84\ud3ec\uc758 \uc65c\uace1\ub3c4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SLN': functionDescription('\uc774 \ud568\uc218\ub294 \ud55c \uae30\uac04 \ub3d9\uc548 \uc815\uc561\ubc95\uc5d0 \uc758\ud55c \uc790\uc0b0\uc758 \uac10\uac00 \uc0c1\uac01\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life')
        ]),
        'SLOPE': functionDescription('\uc774 \ud568\uc218\ub294 \uc120\ud615 \ud68c\uadc0\uc120\uc758 \uae30\uc6b8\uae30\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SMALL': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c n\ubc88\uc9f8\ub85c \uc791\uc740 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. n\uc740 \uc9c0\uc815\ub429\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'SQRT': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 \uc591\uc758 \uc81c\uacf1\uadfc\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'SQRTPI': functionDescription('\uc774 \ud568\uc218\ub294 \ud30c\uc774(p)\uc758 \ubc30\uc218\uc758 \uc591\uc758 \uc81c\uacf1\uadfc\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('multiple')
        ]),
        'STANDARDIZE': functionDescription('\uc774 \ud568\uc218\ub294 \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\ub85c \ud2b9\uc9d5\ub418\ub294 \ubd84\ud3ec\uc5d0\uc11c \uc815\uaddc\ud654\ub41c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'STDEVA': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790, \ud14d\uc2a4\ud2b8 \ub610\ub294 \ub17c\ub9ac\uac12\uc758 \uc9d1\ud569\uc5d0 \ub300\ud55c \ud45c\uc900 \ud3b8\ucc28\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'STDEVP': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uac12\uc758 \uc804\uccb4 \uc9c0\uc815\ub41c \ubaa8\uc9d1\ub2e8\uc5d0 \ub300\ud55c \ud45c\uc900 \ud3b8\ucc28\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEVPA': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ub610\ub294 \ub17c\ub9ac\uac12\uacfc \uc22b\uc790 \uac12\uc744 \ube44\ub86f\ud558\uc5ec \uc804\uccb4 \uc9c0\uc815\ub41c \ubaa8\uc9d1\ub2e8\uc5d0 \ub300\ud55c \ud45c\uc900 \ud3b8\ucc28\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'STEYX': functionDescription('\uc774 \ud568\uc218\ub294 \uc608\uce21\ud55c y \uac12\uc758 \ud45c\uc900 \uc624\ucc28\ub97c \uac01 x \uac12\uc5d0 \ub300\ud558\uc5ec \ubc18\ud658\ud569\ub2c8\ub2e4. \ud45c\uc900 \uc624\ucc28\ub294 x \uac12\uc5d0 \ub300\ud574 \uc608\uce21\ud55c y \uac12\uc758 \uc624\ucc28 \ud06c\uae30\uc785\ub2c8\ub2e4.', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SUBSTITUTE': functionDescription('\uc774 \ud568\uc218\ub294 \uae30\uc874 \ubb38\uc790\uc5f4\uc758 \uc9c0\uc815\ub41c \ubb38\uc790\ub97c \uc0c8 \ubb38\uc790\uc5f4\ub85c \ub300\uccb4\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('old_text'),
            parameterDescription('new_text'),
            parameterDescription('instance_num', false, true)
        ]),
        'SUBTOTAL': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uae30\ubcf8 \ud568\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \uc22b\uc790 \ubaa9\ub85d\uc758 \ubd80\ubd84\ud569\uc744 \uc0b0\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('function_num'),
            parameterDescription('ref1'),
            parameterDescription('ref2', true)
        ]),
        'SUM': functionDescription('\uc774 \ud568\uc218\ub294 \uc140 \ub610\ub294 \uc140 \ubc94\uc704\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SUMIF': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uae30\uc900\uc744 \uc0ac\uc6a9\ud558\uc5ec \uc140\ub4e4\uc758 \ud569\uc744 \uad6c\ud569\ub2c8\ub2e4.', [
            parameterDescription('range'),
            parameterDescription('criteria'),
            parameterDescription('sum_range')
        ]),
        'SUMIFS': functionDescription('\uc774 \ud568\uc218\ub294 \uc5ec\ub7ec \uae30\uc900\uc744 \uc0ac\uc6a9\ud558\uc5ec \ubc94\uc704 \ub0b4 \uc140\ub4e4\uc758 \ud569\uc744 \uad6c\ud569\ub2c8\ub2e4.', [
            parameterDescription('sum_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true),
        ]),
        'SUMPRODUCT': functionDescription('\uc774 \ud568\uc218\ub294 \uc140\ub4e4\uc758 \uc81c\uacf1\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc8fc\uc5b4\uc9c4 \ubc30\uc5f4\uc5d0\uc11c \ud574\ub2f9 \uc694\uc18c\ub4e4\uc758 \uc81c\uacf1\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2', true)
        ]),
        'SUMSQ': functionDescription('\uc774 \ud568\uc218\ub294 \uc778\uc218\uc758 \uc81c\uacf1\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SUMX2MY2': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc30\uc5f4\uc5d0\uc11c \ud574\ub2f9 \uac12\uc758 \uc81c\uacf1\uc758 \ucc28\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SUMX2PY2': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc30\uc5f4\uc5d0\uc11c \ud574\ub2f9 \uac12\uc758 \uc81c\uacf1\uc758 \ud569\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SUMXMY2': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc30\uc5f4\uc5d0\uc11c \ud574\ub2f9 \uac12\uc758 \ucc28\uc758 \uc81c\uacf1\uc758 \ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SYD': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uae30\uac04 \ub3d9\uc548 \ub144\uc218 \ud569\uacc4\ubc95\uc5d0 \uc758\ud55c \uc790\uc0b0\uc758 \uac10\uac00 \uc0c1\uac01\uc561\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period')
        ]),
        'T': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc140\uc5d0 \uc788\ub294 \ud14d\uc2a4\ud2b8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'TAN': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uac01\ub3c4\uc758 TAN\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'TANH': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 TANH\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'TBILLEQ': functionDescription('\uc774 \ud568\uc218\ub294 \uad6d\ucc44\uc5d0 \ud574\ub2f9\ud558\ub294 \uc218\uc775\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount')
        ]),
        'TBILLPRICE': functionDescription('\uc774 \ud568\uc218\ub294 \uc7ac\ubb34\ubd80 \uccad\uad6c\uc11c (\ub610\ub294 T- \uccad\uad6c\uc11c)\uc758 \uc561\uba74\uac00 $ 100 \ub2f9 \uac00\uaca9\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount')
        ]),
        'TBILLYIELD': functionDescription('\uc774 \ud568\uc218\ub294 \uad6d\ucc44\uc5d0 \ub300\ud55c \uc218\uc775\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('priceper')
        ]),
        'TDIST': functionDescription('\uc774 \ud568\uc218\ub294 t-\ubd84\ud3ec\uc5d0 \ub300\ud55c \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('tails')
        ]),
        'TEXT': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790\uc758 \uc11c\uc2dd\uc744 \uc9c0\uc815\ud558\uace0 \uc22b\uc790\ub97c \ud14d\uc2a4\ud2b8\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('format_text')
        ]),
        'TIME': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc2dc\uac04\uc5d0 \ub300\ud55c TimeSpan \uac1c\uccb4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('hour'),
            parameterDescription('minute'),
            parameterDescription('second')
        ]),
        'TIMEVALUE': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\ub85c \ud45c\uc2dc\ub41c \uc2dc\uac04\uc758 TimeSpan \uac1c\uccb4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('time_text')
        ]),
        'TINV': functionDescription('\uc774 \ud568\uc218\ub294 \ud559\uc0dd\uc758 t-\ubd84\ud3ec\uc5d0 \ub300\ud55c t \uac12\uc744 \ud655\ub960 \ud568\uc218\uc640 \uc790\uc720\ub3c4\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'TODAY': functionDescription('\uc774 \ud568\uc218\ub294 \ud604\uc7ac \ub0a0\uc9dc\uc640 \uc2dc\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'TRANSPOSE': functionDescription('\uc774 \ud568\uc218\ub294 \uc140\uc758 \ud589\uacfc \uc5f4\uc744 \ubc14\uafc9\ub2c8\ub2e4.', [
            parameterDescription('array')
        ]),
        'TREND': functionDescription('\uc774 \ud568\uc218\ub294 \uc120\ud615 \ucd94\uc138\ub97c \ub530\ub77c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc774 \ud568\uc218\ub294 x \uac12\uacfc y \uac12\uc774 \uc54c\ub824\uc9c4 \ubc30\uc5f4\uc5d0 \uc9c1\uc120\uc744 \ub9de\ucda5\ub2c8\ub2e4. \uc9c0\uc815\ub41c \uc0c8 x \uac12\uc758 \ubc30\uc5f4\uc5d0 \ub300\ud574 \ud574\ub2f9 \uc120\uc744 \ub530\ub77c y \uac12\uc744 \ubc18\ud658\ud558\ub294 \uac83\uc774 \ucd94\uc138\uc785\ub2c8\ub2e4.', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('newx'),
            parameterDescription('constant')
        ]),
        'TRIM': functionDescription('\uc774 \ud568\uc218\ub294 \ubb38\uc790\uc5f4\uc5d0\uc11c \ucd94\uac00 \uacf5\ubc31\uc744 \uc81c\uac70\ud558\uace0 \ub2e8\uc5b4 \uc0ac\uc774\uc758 \uacf5\ubc31\uc744 \ud55c \uce78\uc73c\ub85c \uc720\uc9c0\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'TRIMMEAN': functionDescription('\uc774 \ud568\uc218\ub294 \ub9e8 \uc704 \ub370\uc774\ud130\uc640 \ub9e8 \uc544\ub798 \ub370\uc774\ud130\ub97c \uc81c\uc678\ud558\uace0 \ub370\uc774\ud130 \ud558\uc704 \uc9d1\ud569\uc758 \ud3c9\uade0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('percent')
        ]),
        'TRUE': functionDescription('\uc774 \ud568\uc218\ub294 \ub17c\ub9ac\uac12 TRUE\uc5d0 \ud574\ub2f9\ud558\ub294 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'TRUNC': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \uc218\uc758 \uc9c0\uc815\ub41c \ubd84\uc218 \ubd80\ubd84\uc744 \uc81c\uac70\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'TTEST': functionDescription('\uc774 \ud568\uc218\ub294 t-\uac80\uc99d\uacfc \uad00\ub828\ub41c \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2'),
            parameterDescription('tails'),
            parameterDescription('type')
        ]),
        'TYPE': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc758 \uc720\ud615\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'UPPER': functionDescription('\uc774 \ud568\uc218\ub294 \ud14d\uc2a4\ud2b8\ub97c \ub300\ubb38\uc790\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'VALUE': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790\uc778 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc744 \uc22b\uc790 \uac12\uc73c\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'VAR': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uac12\ub9cc \uc0ac\uc6a9\ud558\ub294 \ubaa8\uc9d1\ub2e8\uc758 \ud45c\ubcf8 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VARA': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790, \ub17c\ub9ac\uac12 \ub610\ub294 \ud14d\uc2a4\ud2b8 \uac12\uc744 \ud3ec\ud568\ud558\ub294 \ubaa8\uc9d1\ub2e8\uc758 \ud45c\ubcf8 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'VARP': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uac12\ub9cc \uc0ac\uc6a9\ud558\ub294 \uc804\uccb4 \ubaa8\uc9d1\ub2e8\uc758 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VARPA': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790, \ub17c\ub9ac\uac12 \ub610\ub294 \ud14d\uc2a4\ud2b8 \uac12\uc744 \ud3ec\ud568\ud558\ub294 \uc804\uccb4 \ubaa8\uc9d1\ub2e8\uc758 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'VDB': functionDescription('\uc774 \ud568\uc218\ub294 \uac00\ubcc0 \uc815\uc728\ubc95\uc744 \uc0ac\uc6a9\ud558\uc5ec \uc9c0\uc815\ud55c \uae30\uac04 \ub3d9\uc548 \uc790\uc0b0\uc758 \uac10\uac00 \uc0c1\uac01\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('factor'),
            parameterDescription('no_switch')
        ]),
        'VLOOKUP': functionDescription('\uc774 \ud568\uc218\ub294 \uac00\uc7a5 \uc67c\ucabd \uc5f4\uc5d0\uc11c \uac12\uc744 \uac80\uc0c9\ud55c \ub2e4\uc74c \uc9c0\uc815\ub41c \uc5f4\uc758 \ub3d9\uc77c\ud55c \ud589\uc5d0 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('lookup_value'),
            parameterDescription('table_array'),
            parameterDescription('col_index_num'),
            parameterDescription('range_lookup', false, true, RANGE_LOOKUP_ENUM_INFO)
        ]),
        'WEEKDAY': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ub41c \ub0a0\uc9dc\uc758 \uc694\uc77c\uc5d0 \ud574\ub2f9\ud558\ub294 \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date'),
            parameterDescription('type')
        ]),
        'WEEKNUM': functionDescription('\uc774 \ud568\uc218\ub294 \uc5f0\ub3c4\uc758 \uc8fc\uac04\uc744 \ub098\ud0c0\ub0b4\ub294 \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date'),
            parameterDescription('weektype')
        ]),
        'DATEPART': functionDescription('\uc774 \ud568\uc218\ub294 \ub0a0\uc9dc\ub97c \ud3ec\ub9f7\ud558\uace0 \ud14d\uc2a4\ud2b8\ub85c \ubcc0\ud658\ud558\ub294 \ub370 \uc0ac\uc6a9\ub429\ub2c8\ub2e4.', [
            parameterDescription('date'),
            parameterDescription('format_text'),
            parameterDescription('weektype', false, true)
        ]),
        'WEIBULL': functionDescription('\uc774 \ud568\uc218\ub294 \uc548\uc815\uc131 \ubd84\uc11d\uc5d0 \uc790\uc8fc \uc0ac\uc6a9\ub418\ub294 2\uac1c\uc758 \ub9e4\uac1c \ubcc0\uc218 WEIBULL \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'WORKDAY': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\uc791 \ub0a0\uc9dc \uc774\uc804 \ub610\ub294 \uc774\ud6c4\uc758 \uc791\uc5c5\uc77c \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('days'),
            parameterDescription('holidays', false, true)
        ]),
        'XIRR': functionDescription('\uc774 \ud568\uc218\ub294 \ube44\uc815\uae30\uc801\uc77c \uc218\ub3c4 \uc788\ub294 \ud604\uae08 \ud750\ub984\uc758 \ub0b4\ubd80 \ud68c\uc218\uc728\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('values'),
            parameterDescription('dates'),
            parameterDescription('guess')
        ]),
        'XNPV': functionDescription('\uc774 \ud568\uc218\ub294 \ube44\uc815\uae30\uc801\uc77c \uc218\ub3c4 \uc788\ub294 \ud604\uae08 \ud750\ub984\uc758 \uc21c \ud604\uc7ac \uac00\uce58\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('values'),
            parameterDescription('dates')
        ]),
        'YEAR': functionDescription('\uc774 \ud568\uc218\ub294 \uc5f0\ub3c4\ub97c \uc9c0\uc815\ub41c \ub0a0\uc9dc\uc5d0 \ub300\ud55c \uc815\uc218\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date')
        ]),
        'YEARFRAC': functionDescription('\uc774 \ud568\uc218\ub294 \uc2dc\uc791 \ub0a0\uc9dc\uc640 \uc885\ub8cc \ub0a0\uc9dc \uc0ac\uc774\uc758 \uc804\uccb4 \ub0a0\uc9dc \uc218\ub85c \ud45c\uc2dc\ub41c \uc5f0\ub3c4 \ubd80\ubd84\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('basis')
        ]),
        'YIELD': functionDescription('\uc774 \ud568\uc218\ub294 \uc815\uae30\uc801\uc73c\ub85c \uc774\uc790\ub97c \uc9c0\uae09\ud558\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \uc218\uc775\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'YIELDDISC': functionDescription('\uc774 \ud568\uc218\ub294 \ud560\uc778\ub41c \uc720\uac00 \uc99d\uad8c\uc758 \uc5f0\uac04 \uc218\uc775\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'YIELDMAT': functionDescription('\uc774 \ud568\uc218\ub294 \ub9cc\uae30 \uc2dc \uc774\uc790\ub97c \uc9c0\uae09\ud558\ub294 \uc720\uac00 \uc99d\uad8c\uc758 \uc5f0\uac04 \uc218\uc775\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('basis')
        ]),
        'ZTEST': functionDescription('\uc774 \ud568\uc218\ub294 z-\uac80\uc99d\uc758 significance \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. z-\uac80\uc99d\uc5d0\uc11c\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0 \ub300\ud55c x\uc758 \ud45c\uc900 \uc131\uacfc\ub97c \uc0dd\uc131\ud558\uace0 \uc815\uaddc \ubd84\ud3ec\uc5d0 \ub300\ud55c \uc591\uce21 \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('sigma', false, true)
        ]),
        'HBARSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 Hbar \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('colorScheme'),
            parameterDescription('axisVisible'),
            parameterDescription('barHeight')
        ]),
        'VBARSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 Vbar \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('colorScheme'),
            parameterDescription('axisVisible'),
            parameterDescription('barWidth')
        ]),
        'VARISPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ubcc0\ub7c9 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'LOLLIPOPVARISPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc808\ub300 \ub610\ub294 \uc0c1\ub300 \ub9c9\ub300\uc0ac\ud0d5 \ubd84\uc0b0 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ub098\ud0c0\ub0b4\uae30 \uc704\ud574 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'PIESPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc6d0\ud615 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('range|percentage'),
            parameterDescription('color', true)
        ]),
        'AREASPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc601\uc5ed \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('points'),
            parameterDescription('mini'),
            parameterDescription('maxi'),
            parameterDescription('line1'),
            parameterDescription('line2'),
            parameterDescription('colorPositive'),
            parameterDescription('colorNegative')
        ]),
        'SCATTERSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ubd84\uc0b0\ud615 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'LINESPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc120 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'COLUMNSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc5f4 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'WINLOSSSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc2b9\ud328 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'BULLETSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uae00\uba38\ub9ac \uae30\ud638 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'SPREADSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ubd84\uc0ac \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('points'),
            parameterDescription('showAverage'),
            parameterDescription('scaleStart'),
            parameterDescription('scaleEnd'),
            parameterDescription('style'),
            parameterDescription('colorScheme'),
            parameterDescription('vertical')
        ]),
        'STACKEDSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ub204\uc801 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BOXPLOTSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc0c1\uc790 \uadf8\ub9bc \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'CASCADESPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uacc4\ub2e8\uc2dd \ubc30\uc5f4 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'PARETOSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ud30c\ub808\ud1a0 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'MONTHSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uc6d4 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('year'),
            parameterDescription('month'),
            parameterDescription('dataRange'),
            parameterDescription('emptyColor'),
            parameterDescription('startColor'),
            parameterDescription('middleColor'),
            parameterDescription('endColor')
        ]),
        'YEARSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ub144 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ud45c\uc2dc\ud558\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('year'),
            parameterDescription('dataRange'),
            parameterDescription('emptyColor'),
            parameterDescription('startColor'),
            parameterDescription('middleColor'),
            parameterDescription('endColor')
        ]),
        'GAUGEKPISPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \uac8c\uc774\uc9c0 KPI \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc138\ud2b8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('targetValue'),
            parameterDescription('currentValue'),
            parameterDescription('minValue'),
            parameterDescription('maxValue'),
            parameterDescription('showLabel', false, true),
            parameterDescription('targetValueLabel', false, true),
            parameterDescription('currentValueLabel', false, true),
            parameterDescription('minValueLabel', false, true),
            parameterDescription('maxValueLabel', false, true),
            parameterDescription('fontArray', false, true),
            parameterDescription('minAngle', false, true),
            parameterDescription('maxAngle', false, true),
            parameterDescription('radiusRatio', false, true),
            parameterDescription('gaugeType', false, true),
            parameterDescription('colorRange', true, true)
        ]),
        'HISTOGRAMSPARKLINE': functionDescription('\uc774 \ud568\uc218\ub294 \ud788\uc2a4\ud1a0\uadf8\ub7a8 \uc2a4\ud30c\ud06c\ub77c\uc778\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ub370\uc774\ud130 \uc138\ud2b8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'CEILING.PRECISE': functionDescription('\uc774 \ud568\uc218\ub294 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc815\uc218 \ub610\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \uac00\uc7a5 \uac00\uae4c\uc6b4 \ubc30\uc218\uac00 \ub418\ub3c4\ub85d \uac12\uc744 \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE, false, true)
        ]),
        'COVARIANCE.S': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \uac01 \ub370\uc774\ud130 \uc694\uc18c \uc30d\uc5d0 \ub300\ud55c \ud3b8\ucc28\ub97c \uacf1\ud55c \uac12\uc758 \ud3c9\uade0\uc744 \ub098\ud0c0\ub0b4\ub294 \ud45c\ubcf8 \uacf5 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'FLOOR.PRECISE': functionDescription('\uc774 \ud568\uc218\ub294 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc815\uc218 \ub610\ub294 \uc9c0\uc815\ub41c \uac12\uc758 \uac00\uc7a5 \uac00\uae4c\uc6b4 \ubc30\uc218\uac00 \ub418\ub3c4\ub85d \uac12\uc744 \ub0b4\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE, false, true)
        ]),
        'PERCENTILE.EXC': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704\uc5d0\uc11c n\ubc88\uc9f8 \ubc31\ubd84\uc704\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('k')
        ]),
        'QUARTILE.EXC': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc774 \uc18d\ud558\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \ubc31\ubd84\uc704\uc218(1/4 \ub610\ub294 25%)\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'RANK.AVG': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uc9d1\ud569 \ub0b4\uc5d0\uc11c \uc9c0\uc815\ud55c \uc218\uc758 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc77c\ubd80 \uac12\uc758 \uc21c\uc704\uac00 \ub3d9\uc77c\ud55c \uacbd\uc6b0 \ud3c9\uade0 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('order', false, true)
        ]),
        'MODE.MULT': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c \uac00\uc7a5 \uc790\uc8fc \ubc1c\uc0dd\ud558\ub294 \uac12 \ub610\ub294 \uac00\uc7a5 \uc790\uc8fc \ubc1c\uc0dd\ud558\ub294 \uc138\ub85c \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEV.P': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uac12\uc758 \uc804\uccb4 \uc9c0\uc815\ub41c \ubaa8\uc9d1\ub2e8\uc5d0 \ub300\ud55c \ud45c\uc900 \ud3b8\ucc28\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VAR.P': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uac12\ub9cc \uc0ac\uc6a9\ud558\ub294 \uc804\uccb4 \ubaa8\uc9d1\ub2e8\uc758 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'COVARIANCE.P': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \uac01 \ub370\uc774\ud130 \uc694\uc18c \uc30d\uc5d0 \ub300\ud55c \ud3b8\ucc28\ub97c \uacf1\ud55c \uac12\uc758 \ud3c9\uade0\uc744 \ub098\ud0c0\ub0b4\ub294 \uacf5 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'MODE.SNGL': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c \uac00\uc7a5 \uc790\uc8fc \ubc1c\uc0dd\ud558\ub294 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'PERCENTILE.INC': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704\uc5d0\uc11c n\ubc88\uc9f8 \ubc31\ubd84\uc704\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('k')
        ]),
        'QUARTILE.INC': functionDescription('\uc774 \ud568\uc218\ub294 \uac12\uc774 \uc18d\ud558\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc758 \ubc31\ubd84\uc704\uc218(1/4 \ub610\ub294 25%)\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'RANK.EQ': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uc9d1\ud569 \ub0b4\uc5d0\uc11c \uc9c0\uc815\ud55c \uc218\uc758 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc9d1\ud569\uc744 \uc815\ub82c\ud560 \uacbd\uc6b0 \uc218\uc758 \uc21c\uc704\ub294 \ubaa9\ub85d \ub0b4\uc5d0\uc11c \ud574\ub2f9 \uc218\uc758 \uc704\uce58\uc785\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('order', false, true)
        ]),
        'STDEV': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\ubcf8\uc5d0 \ub530\ub77c \uc608\uce21\ub418\ub294 \ud45c\uc900 \ud3b8\ucc28\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEV.S': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\ubcf8\uc5d0 \ub530\ub77c \uc608\uce21\ub418\ub294 \ud45c\uc900 \ud3b8\ucc28\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VAR.S': functionDescription('\uc774 \ud568\uc218\ub294 \uc22b\uc790 \uac12\ub9cc \uc0ac\uc6a9\ud558\ub294 \ubaa8\uc9d1\ub2e8\uc758 \ud45c\ubcf8 \ubd84\uc0b0\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'BETA.INV': functionDescription('\uc774 \ud568\uc218\ub294 \ub204\uc801 \ubca0\ud0c0 \ubd84\ud3ec \ud568\uc218\uc758 \uc5ed\ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower', false, true),
            parameterDescription('upper', false, true)
        ]),
        'BINOM.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uac1c\ubcc4\ud56d \uc774\ud56d \ubd84\ud3ec \ud655\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number_s'),
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'BINOM.INV': functionDescription('\uc774 \ud568\uc218\ub294 \ub204\uc801 \uc774\ud56d \ubd84\ud3ec\uac00 \uae30\uc900\uce58 \uc774\uc0c1\uc774 \ub418\ub294 \uac12 \uc911 \ucd5c\uc18c\uac12\uc744 \ub098\ud0c0\ub0b4\ub294 \uc870\uac74 \uc774\ud56d\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('alpha')
        ]),
        'CHISQ.DIST.RT': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc758 \ub2e8\uce21 \ud655\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.INV.RT': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc758 \ub2e8\uce21 \ud655\ub960\uc758 \uc5ed\ud568\uc218\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.TEST': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc5d0\uc11c \ub3c5\ub9bd \uac80\uc99d \uacb0\uacfc\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('actual_range'),
            parameterDescription('expected_range')
        ]),
        'CONFIDENCE.NORM': functionDescription('\uc774 \ud568\uc218\ub294 \ubaa8\uc9d1\ub2e8 \ud3c9\uade0\uc758 \uc2e0\ub8b0 \uad6c\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'EXPON.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc218 \ubd84\ud3ec \ub610\ub294 \ud655\ub960 \ubc00\ub3c4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('lambda'),
            parameterDescription('cumulative')
        ]),
        'F.DIST.RT': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ub370\uc774\ud130 \uc9d1\ud569 \uc0ac\uc774\uc758 \ubd84\ud3ec\ub3c4\ub97c \ub098\ud0c0\ub0b4\ub294 F \ud655\ub960 \ubd84\ud3ec\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'F.INV.RT': functionDescription('\uc774 \ud568\uc218\ub294 F \ud655\ub960 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'F.TEST': functionDescription('\uc774 \ud568\uc218\ub294 \ub450 \ubc30\uc5f4\uc758 \ubd84\uc0b0\uc774 \ud06c\uac8c \ucc28\uc774\uac00 \ub098\uc9c0 \uc54a\ub294 \uacbd\uc6b0 \ub2e8\uce21 \ud655\ub960\uc778 F-\uac80\uc815 \uacb0\uacfc\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'GAMMA.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uac10\ub9c8 \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'GAMMA.INV': functionDescription('\uc774 \ud568\uc218\ub294 \uac10\ub9c8 \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta')
        ]),
        'LOGNORM.INV': functionDescription('\uc774 \ud568\uc218\ub294 x\uc5d0 \ub300\ud55c \ub85c\uadf8 \uc815\uaddc \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc5ec\uae30\uc11c LN(x)\uc740 \uc9c0\uc815\ub41c \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\ub97c \uac16\ub294 \uc815\uaddc \ubd84\ud3ec\uc785\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORM.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc9c0\uc815\ud55c \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\uc5d0 \uc758\uac70\ud558\uc5ec \uc815\uaddc \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
            parameterDescription('cumulative')
        ]),
        'NORM.INV': functionDescription('\uc774 \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \ud3c9\uade0\uacfc \ud45c\uc900 \ud3b8\ucc28\uc5d0 \uc758\uac70\ud558\uc5ec \uc815\uaddc \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORM.S.INV': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\uc900 \uc815\uaddc \ub204\uc801 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \ubd84\ud3ec\uc758 \ud3c9\uade0\uc774 0\uc774\uace0 \ud45c\uc900 \ud3b8\ucc28\uac00 1\uc785\ub2c8\ub2e4.', [
            parameterDescription('probability')
        ]),
        'PERCENTRANK.INC': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0\uc11c \ubc31\ubd84\uc728 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('n'),
            parameterDescription(SIGNIFICANCE, false, true)
        ]),
        'POISSON.DIST': functionDescription('\uc774 \ud568\uc218\ub294POISSON \ud655\ub960 \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('nevents'),
            parameterDescription('mean'),
            parameterDescription('cumulative')
        ]),
        'T.INV.2T': functionDescription('\uc774 \ud568\uc218\ub294 \ud559\uc0dd\uc758 t-\ubd84\ud3ec\uc5d0 \ub300\ud55c t \uac12\uc744 \ud655\ub960 \ud568\uc218\uc640 \uc790\uc720\ub3c4\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'T.TEST': functionDescription('\uc774 \ud568\uc218\ub294 t-\uac80\uc99d\uacfc \uad00\ub828\ub41c \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2'),
            parameterDescription('tails'),
            parameterDescription('type')
        ]),
        'WEIBULL.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc548\uc815\uc131 \ubd84\uc11d\uc5d0 \uc790\uc8fc \uc0ac\uc6a9\ub418\ub294 2 \ub9e4\uac1c \ubcc0\uc218 WEIBULL \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'Z.TEST': functionDescription('\uc774 \ud568\uc218\ub294 z-\uac80\uc99d\uc758 significance \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. z-\uac80\uc99d\uc5d0\uc11c\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0 \ub300\ud55c x\uc758 \ud45c\uc900 \uc131\uacfc\ub97c \uc0dd\uc131\ud558\uace0 \uc815\uaddc \ubd84\ud3ec\uc5d0 \ub300\ud55c \uc591\uce21 \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('sigma', false, true)
        ]),
        'T.DIST.RT': functionDescription('\uc774 \ud568\uc218\ub294 \uc6b0\uce21 \uac80\uc99d t-\ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('deg_freedom')
        ]),
        'T.DIST.2T': functionDescription('\uc774 \ud568\uc218\ub294 \uc591\uce21 t-\ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('deg_freedom')
        ]),
        'ISO.CEILING': functionDescription('\uc774 \ud568\uc218\ub294 significance\uc758 \ubd80\ud638\uc5d0 \uc0c1\uad00\uc5c6\uc774 \uac00\uc7a5 \uac00\uae4c\uc6b4 \uc815\uc218 \ub610\ub294 significance\uc758 \uac00\uc7a5 \uac00\uae4c\uc6b4 \ubc30\uc218\ub85c \uac12\uc744 \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE, false, true)
        ]),
        'BETA.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \ubca0\ud0c0 \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'GAMMALN.PRECISE': functionDescription('\uc774 \ud568\uc218\ub294 \uac10\ub9c8 \ud568\uc218\uc758 \uc790\uc5f0 \ub85c\uadf8\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'ERF.PRECISE': functionDescription('\uc774 \ud568\uc218\ub294 \uc624\ucc28 \ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('lowerlimit')
        ]),
        'ERFC.PRECISE': functionDescription('\uc774 \ud568\uc218\ub294 ERF \ud568\uc218\uc758 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('lowerlimit')
        ]),
        'PERCENTRANK.EXC': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc5d0 \uc788\ub294 \uac12\uc758 \ubc31\ubd84\uc728 \uc21c\uc704(0..1, \ub2e8\ub3c5)\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription(SIGNIFICANCE, false, true)
        ]),
        'HYPGEOM.DIST': functionDescription('\uc774 \ud568\uc218\ub294 hypergeometric \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('sample_s'),
            parameterDescription('number_sample'),
            parameterDescription('population_s'),
            parameterDescription('number_pop'),
            parameterDescription('cumulative')
        ]),
        'LOGNORM.DIST': functionDescription('\uc774 \ud568\uc218\ub294 x\uc758 \ub85c\uadf8 \uc815\uaddc \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('stdev'),
            parameterDescription('cumulative')
        ]),
        'NEGBINOM.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uc74c \uc774\ud56d \ubd84\ud3ec\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number_f'),
            parameterDescription('number_s'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'NORM.S.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \ud45c\uc900 \uc815\uaddc \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('z'),
            parameterDescription('cumulative')
        ]),
        'T.DIST': functionDescription('\uc774 \ud568\uc218\ub294 t-\ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('cumulative')
        ]),
        'F.DIST': functionDescription('\uc774 \ud568\uc218\ub294 F \ud655\ub960 \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2'),
            parameterDescription('cumulative')
        ]),
        'CHISQ.DIST': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('cumulative')
        ]),
        'F.INV': functionDescription('\uc774 \ud568\uc218\ub294 F \ud655\ub960 \ubd84\ud3ec\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'T.INV': functionDescription('\uc774 \ud568\uc218\ub294 t-\ubd84\ud3ec\uc758 \uc88c\uce21 \uc5ed\ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.INV': functionDescription('\uc774 \ud568\uc218\ub294 \uce74\uc774 \uc81c\uacf1 \ubd84\ud3ec\uc758 \uc88c\uce21 \ud655\ub960\uc758 \uc5ed\ud568\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CONFIDENCE.T': functionDescription('\uc774 \ud568\uc218\ub294 \ud559\uc0dd \ubd84\ud3ec\uc5d0 \ub300\ud55c \uc2e0\ub8b0 \uad6c\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'NETWORKDAYS.INTL': functionDescription('\uc774 \ud568\uc218\ub294 \uc778\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \uacf5\ud734\uc77c\uacfc \uc8fc\ub9d0\uc744 \ub098\ud0c0\ub0b4\uc5b4 \ub450 \ub0a0\uc9dc \uc0ac\uc774\uc758 \uc791\uc5c5\uc77c \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('weekend', false, true),
            parameterDescription('holidays', false, true)
        ]),
        'WORKDAY.INTL': functionDescription('\uc774 \ud568\uc218\ub294 \uc0ac\uc6a9\uc790 \uc9c0\uc815 weekend \ub9e4\uac1c \ubcc0\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \ud2b9\uc815 \uc77c\uc758 \uc804\uc774\ub098 \ud6c4\uc5d0 \ud574\ub2f9\ud558\ub294 \ub0a0\uc9dc\uc758 \uc77c\ub828 \ubc88\ud638\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc774\ub7ec\ud55c \ub9e4\uac1c \ubcc0\uc218\ub294 \uc8fc\ub9d0\uacfc \uacf5\ud734\uc77c\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.', [
            parameterDescription('start_date'),
            parameterDescription('days'),
            parameterDescription('weekend', false, true),
            parameterDescription('holidays', false, true)
        ]),
        'REFRESH': functionDescription('\uc774 \ud568\uc218\ub294 \uc218\uc2dd\uc744 \ub2e4\uc2dc \uacc4\uc0b0\ud558\ub294 \ubc29\ubc95\uc744 \uacb0\uc815\ud569\ub2c8\ub2e4. evaluateMode \uc778\uc218\ub97c \uc0ac\uc6a9\ud558\uc5ec \ubcc0\uacbd\ub41c \ucc38\uc870 \uac12\uc5d0 \ub300\ud55c \uc218\uc2dd \uc7ac\uacc4\uc0b0\uc744 \ud55c \ubc88\ub9cc \uc218\ud589\ud558\uac70\ub098 \ud2b9\uc815 \uac04\uaca9\uc73c\ub85c \ub2e4\uc2dc \uacc4\uc0b0\ud558\ub3c4\ub85d \uc9c0\uc815\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('formula'),
            parameterDescription('evaluateMode'),
            parameterDescription('interval')
        ]),
        'DAYS': functionDescription('\ub450 \ub0a0\uc9dc \uc0ac\uc774\uc758 \uc77c \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('end_date'),
            parameterDescription('start_date'),
        ]),
        'ISOWEEKNUM': functionDescription('\uc9c0\uc815\ub41c \ub0a0\uc9dc\uc758 \uc5f0\ub3c4\uc5d0 \ud574\ub2f9\ud558\ub294 ISO \uc8fc \ubc88\ud638\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date')
        ]),
        'BITAND': functionDescription('\ub450 \uc22b\uc790\uc758 \ube44\ud2b8 \ub2e8\uc704 "AND"\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'BITLSHIFT': functionDescription('\uc9c0\uc815\ub41c \ube44\ud2b8\ub9cc\ud07c \uc67c\ucabd\uc73c\ub85c \uc774\ub3d9\ud55c \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('shift_amount')
        ]),
        'BITOR': functionDescription('\ub450 \uc22b\uc790\uc758 \ube44\ud2b8 \ub2e8\uc704 "OR"\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'BITRSHIFT': functionDescription('\uc9c0\uc815\ub41c \ube44\ud2b8\ub9cc\ud07c \uc624\ub978\ucabd\uc73c\ub85c \uc774\ub3d9\ud55c \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('shift_amount')
        ]),
        'BITXOR': functionDescription('\ub450 \uc22b\uc790\uc758 \ube44\ud2b8 \ub2e8\uc704 "XOR"\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'IMCOSH': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ud558\uc774\ud37c\ubcfc\ub9ad \ucf54\uc0ac\uc778 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMCOT': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ucf54\ud0c4\uc820\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMCSC': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ucf54\uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMCSCH': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ud558\uc774\ud37c\ubcfc\ub9ad \ucf54\uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMSEC': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMSECH': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ud558\uc774\ud37c\ubcfc\ub9ad \uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMSINH': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ud558\uc774\ud37c\ubcfc\ub9ad \uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'IMTAN': functionDescription('x + yi \ub610\ub294 x + yj \ud14d\uc2a4\ud2b8 \ud615\uc2dd\uc778 \ubcf5\uc18c\uc218\uc758 \ud0c4\uc820\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('complexnum')
        ]),
        'PDURATION': functionDescription('\ud22c\uc790 \uae08\uc561\uc774 \uc9c0\uc815\ub41c \uac12\uc5d0 \ub3c4\ub2ec\ud560 \ub54c\uae4c\uc9c0 \ud544\uc694\ud55c \uae30\uac04\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rate'),
            parameterDescription('pval'),
            parameterDescription('fval')
        ]),
        'RRI': functionDescription('\ud22c\uc790 \uc218\uc775\uc5d0 \ud574\ub2f9\ud558\ub294 \uc774\uc790\uc728\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval')
        ]),
        'ISFORMULA': functionDescription('\uc218\uc2dd\uc744 \ud3ec\ud568\ud558\ub294 \uc140\uc5d0 \ub300\ud55c \ucc38\uc870\uac00 \uc788\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud655\uc778\ud558\uace0 TRUE \ub610\ub294 FALSE\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('cellreference')
        ]),
        'IFNA': functionDescription('\uc218\uc2dd\uc774 #N/A \uc624\ub958 \uac12\uc744 \ubc18\ud658\ud558\ub294 \uacbd\uc6b0 \uc9c0\uc815\ud55c \uac12\uc744 \ubc18\ud658\ud558\uace0, \uadf8\ub807\uc9c0 \uc54a\uc73c\uba74 \uc218\uc2dd \uacb0\uacfc\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value'),
            parameterDescription('value_if_na')
        ]),
        'IFS': functionDescription('IFS \ud568\uc218\ub294 \ud558\ub098 \uc774\uc0c1\uc758 \uc870\uac74\uc774 \ucda9\uc871\ub418\ub294\uc9c0\ub97c \ud655\uc778\ud558\uace0 \uccab \ubc88\uc9f8 TRUE \uc870\uac74\uc5d0 \ud574\ub2f9\ud558\ub294 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('logical_test1'),
            parameterDescription('value_if_true1'),
            parameterDescription('logical_test2', true),
            parameterDescription('value_if_true2', true)
        ]),
        'SWITCH': functionDescription('SWITCH \ud568\uc218\ub294 \uac12\uc758 \ubaa9\ub85d\uc5d0 \ub300\ud55c \ud558\ub098\uc758 \uac12(\uc2dd\uc774\ub77c\uace0 \ud568)\uc744 \uacc4\uc0b0\ud558\uace0 \uccab \ubc88\uc9f8 \uc77c\uce58\ud558\ub294 \uac12\uc5d0 \ud574\ub2f9\ud558\ub294 \uacb0\uacfc\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. ', [
            parameterDescription('expression'),
            parameterDescription('value1'),
            parameterDescription('result1'),
            parameterDescription('default_or_value2', true, true),
            parameterDescription('result2', true, true),
        ]),
        'XOR': functionDescription('\ubaa8\ub4e0 \uc778\uc218\uc758 \ub17c\ub9ac \ubc30\ud0c0\uc801 OR\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('logical', true)
        ]),
        'AREAS': functionDescription('\ucc38\uc870 \uc601\uc5ed \ub0b4\uc758 \uc601\uc5ed \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc601\uc5ed\uc740 \uc778\uc811\ud55c \uc140\uc758 \ubc94\uc704 \ub610\ub294 \ub2e8\uc77c \uc140\uc785\ub2c8\ub2e4.', [
            parameterDescription('reference')
        ]),
        'FORMULATEXT': functionDescription('\uc218\uc2dd\uc744 \ubb38\uc790\uc5f4\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference')
        ]),
        'HYPERLINK': functionDescription('\ub124\ud2b8\uc6cc\ud06c \uc11c\ubc84, \uc778\ud2b8\ub77c\ub137 \ub610\ub294 \uc778\ud130\ub137\uc5d0 \uc800\uc7a5\ub41c \ubb38\uc11c\ub85c \uc774\ub3d9\ud560 \ubc14\ub85c \uac00\uae30 \ud0a4\ub97c \ub9cc\ub4ed\ub2c8\ub2e4. ', [
            parameterDescription('link_location'),
            parameterDescription('friendly_name')
        ]),
        'ACOT': functionDescription('\uc544\ud06c\ucf54\ud0c4\uc820\ud2b8, \uc989 \uc5ed \ucf54\ud0c4\uc820\ud2b8\uc758 \uc8fc\uce58\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ACOTH': functionDescription('\uc5ed \ud558\uc774\ud37c\ubcfc\ub9ad \ucf54\ud0c4\uc820\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'ARABIC': functionDescription('\ub85c\ub9c8 \uc22b\uc790\ub97c \uc544\ub77c\ube44\uc544 \uc22b\uc790\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'BASE': functionDescription('\uc22b\uc790\ub97c \uc9c0\uc815\ub41c \uae30\uc218\uc758 \ud14d\uc2a4\ud2b8 \ud45c\ud604\uc73c\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('radix'),
            parameterDescription('min_length', false, true)
        ]),
        'CEILING.MATH': functionDescription('\uac00\uc7a5 \uac00\uae4c\uc6b4 \uc815\uc218 \ub610\ub294 \uac00\uc7a5 \uac00\uae4c\uc6b4 significance\uc758 \ubc30\uc218\ub85c \uc22b\uc790\ub97c \uc62c\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE, false, true),
            parameterDescription('mode', false, true)
        ]),
        'COMBINA': functionDescription('\uc8fc\uc5b4\uc9c4 \uac1c\uccb4 \uc218\ub85c \ub9cc\ub4e4 \uc218 \uc788\ub294 \uc870\ud569(\ubc18\ubcf5 \ud3ec\ud568)\uc758 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'COT': functionDescription('\uac01\ub3c4\uc758 \ucf54\ud0c4\uc820\ud2b8\ub97c \ub77c\ub514\uc548 \ub2e8\uc704\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'COTH': functionDescription('\ud558\uc774\ud37c\ubcfc\ub9ad \uac01\ub3c4\uc758 \ud558\uc774\ud37c\ubcfc\ub9ad \ucf54\ud0c4\uc820\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'CSC': functionDescription('\uac01\ub3c4\uc758 \ucf54\uc2dc\ucee8\ud2b8\ub97c \ub77c\ub514\uc548 \ub2e8\uc704\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'CSCH': functionDescription('\uac01\ub3c4\uac00 \ub77c\ub514\uc548\uc73c\ub85c \uc9c0\uc815\ub418\ub294 \ud558\uc774\ud37c\ubcfc\ub9ad \ucf54\uc2dc\ucee8\ud2b8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'DECIMAL': functionDescription('\ud2b9\uc815 \uae30\uc218\uc758 \ud14d\uc2a4\ud2b8 \ud45c\ud604\uc744 10\uc9c4\uc218\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('radix')
        ]),
        'FLOOR.MATH': functionDescription('\uac00\uc7a5 \uac00\uae4c\uc6b4 \uc815\uc218 \ub610\ub294 \uac00\uc7a5 \uac00\uae4c\uc6b4 significance\uc758 \ubc30\uc218\ub85c \uc22b\uc790\ub97c \ub0b4\ub9bc\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE, false, true),
            parameterDescription('mode', false, true)
        ]),
        'SEC': functionDescription('\uac01\ub3c4\uc758 \uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('angle')
        ]),
        'SECH': functionDescription('\uac01\ub3c4\uc758 \ud558\uc774\ud37c\ubcfc\ub9ad \uc2dc\ucee8\ud2b8 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'BINOM.DIST.RANGE': functionDescription('\uc774\ud56d \ubd84\ud3ec\ub97c \uc0ac\uc6a9\ud55c \uc2dc\ud589 \uacb0\uacfc\uc758 \ud655\ub960\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('number_s'),
            parameterDescription('number_s2', false, true)
        ]),
        'GAMMA': functionDescription('\uac10\ub9c8 \ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'MAXIFS': functionDescription('MAXIFS \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \uc870\uac74\uc5d0 \ub9de\ub294 \uc140\uc5d0\uc11c \ucd5c\ub300\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('max_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true),
        ]),
        'GAUSS': functionDescription('\ud45c\uc900 \uc815\uaddc \ubaa8\uc9d1\ub2e8 \uad6c\uc131\uc6d0\uc774 \ud3c9\uade0 \ubc0f \ud3c9\uade0\uc758 z \ud45c\uc900 \ud3b8\ucc28 \uc0ac\uc774\uc5d0 \uc788\uc744 \ud655\ub960\uc744 \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'MINIFS': functionDescription('MINIFS \ud568\uc218\ub294 \uc8fc\uc5b4\uc9c4 \uc870\uac74 \uc9d1\ud569\uc5d0 \ub9de\ub294 \uc140\uc5d0\uc11c \ucd5c\uc18c\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('min_range'),
            parameterDescription('criteria_range1'),
            parameterDescription('criteria1'),
            parameterDescription('criteria_range2', true),
            parameterDescription('criteria2', true)
        ]),
        'PERMUTATIONA': functionDescription('\uc804\uccb4 \uac1c\uccb4\uc5d0\uc11c \uc120\ud0dd\ud558\uc5ec \uc8fc\uc5b4\uc9c4 \uac1c\uccb4 \uc218(\ubc18\ubcf5 \ud3ec\ud568)\ub85c \ub9cc\ub4e4 \uc218 \uc788\ub294 \uc21c\uc5f4\uc758 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'PHI': functionDescription('\ud45c\uc900 \uc815\uaddc \ubd84\ud3ec\uc758 \ubc00\ub3c4 \ud568\uc218 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'SKEW.P': functionDescription('\ubaa8\uc9d1\ub2e8\uc744 \uae30\uc900\uc73c\ub85c \ubd84\ud3ec\uc758 \uc65c\uace1\ub3c4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. \uc65c\uace1\ub3c4\ub780 \ud3c9\uade0\uc5d0 \ub300\ud55c \ubd84\ud3ec\uc758 \ube44\ub300\uce6d \uc815\ub3c4\ub97c \ub098\ud0c0\ub0c5\ub2c8\ub2e4.', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'BAHTTEXT': functionDescription('\uc22b\uc790\ub97c \ud0dc\uad6d\uc5b4 \ud14d\uc2a4\ud2b8\ub85c \ubcc0\ud658\ud558\uace0 "Baht" \uc811\ubbf8\uc0ac\ub97c \ucd94\uac00\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'CONCAT': functionDescription('CONCAT \ud568\uc218\ub294 \uc5ec\ub7ec \ubc94\uc704 \ubc0f/\ub610\ub294 \ubb38\uc790\uc5f4\uc758 \ud14d\uc2a4\ud2b8\ub97c \uacb0\ud569\ud558\uc9c0\ub9cc \uad6c\ubd84 \uae30\ud638\ub098 IgnoreEmpty \uc778\uc218\ub294 \uc81c\uacf5\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'FINDB': functionDescription('FINDB\ub294 DBCS\ub97c \uc9c0\uc6d0\ud558\ub294 \uc5b8\uc5b4\ub97c \ud3b8\uc9d1\ud560 \uc218 \uc788\ub3c4\ub85d \uc124\uc815\ud558\uace0 \uc774 \uc5b8\uc5b4\ub97c \uae30\ubcf8 \uc5b8\uc5b4\ub85c \uc124\uc815\ud55c \uacbd\uc6b0 \uac01 \ub354\ube14\ubc14\uc774\ud2b8 \ubb38\uc790\ub97c 2\ub85c \uacc4\uc0b0\ud569\ub2c8\ub2e4. \uc774\ub7ec\ud55c \uacbd\uc6b0\uac00 \uc544\ub2c8\uba74 FINDB\ub294 \uac01 \ubb38\uc790\ub97c 1\ub85c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER, false, true)
        ]),
        'LEFTB': functionDescription('LEFTB\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uccab \ubc88\uc9f8 \ubb38\uc790\ubd80\ud130 \uc2dc\uc791\ud558\uc5ec \uc9c0\uc815\ud55c \ubc14\uc774\ud2b8 \uc218\ub9cc\ud07c \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('num_bytes')
        ]),
        'LENB': functionDescription('LENB\ub294 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \ubb38\uc790\ub97c \ub098\ud0c0\ub0b4\ub294 \ub370 \uc0ac\uc6a9\ub418\ub294 \ubc14\uc774\ud2b8 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'MIDB': functionDescription('MIDB\ub294 \uc9c0\uc815\ud55c \ubc14\uc774\ud2b8 \uc218\uc5d0 \ub530\ub77c \ubb38\uc790\uc5f4\uc758 \uc9c0\uc815\ud55c \uc704\uce58\ub85c\ubd80\ud130 \uc9c0\uc815\ud55c \uac1c\uc218\uc758 \ubb38\uc790\ub97c \ud45c\uc2dc\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('start_num'),
            parameterDescription('num_bytes')
        ]),
        'REPLACEB': functionDescription('REPLACEB\ub294 \uc9c0\uc815\ud55c \ubc14\uc774\ud2b8 \uc218\uc5d0 \ub530\ub77c \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uc77c\ubd80\ub97c \ub2e4\ub978 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\ub85c \ubc14\uafc9\ub2c8\ub2e4.', [
            parameterDescription('old_text'),
            parameterDescription('start_byte'),
            parameterDescription('num_bytes'),
            parameterDescription('new_text')
        ]),
        'RIGHTB': functionDescription('RIGHTB\ub294 \uc9c0\uc815\ud55c \ubc14\uc774\ud2b8 \uc218\uc5d0 \ub530\ub77c \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \ub9c8\uc9c0\ub9c9 \ubb38\uc790\ubd80\ud130 \uc9c0\uc815\ub41c \uae38\uc774\uc758 \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('num_bytes')
        ]),
        'SEARCHB': functionDescription('\ud568\uc218\ub294 \ub450 \ubc88\uc9f8 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc5d0\uc11c \uc9c0\uc815\ub41c \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc744 \uac80\uc0c9\ud558\uace0, \ub450 \ubc88\uc9f8 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uccab \ubb38\uc790\ub97c \uae30\uc900\uc73c\ub85c \uba87 \ubc88\uc9f8 \uc704\uce58\uc5d0\uc11c \uccab \ubc88\uc9f8 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc774 \uc2dc\uc791\ud558\ub294\uc9c0 \ub098\ud0c0\ub0b4\ub294 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. ', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER, false, true)
        ]),
        'TEXTJOIN': functionDescription('TEXTJOIN \ud568\uc218\ub294 \uc5ec\ub7ec \ubc94\uc704 \ubc0f/\ub610\ub294 \ubb38\uc790\uc5f4\uc758 \ud14d\uc2a4\ud2b8\ub97c \uacb0\ud569\ud558\uba70, \uacb0\ud569\ud560 \uac01 \ud14d\uc2a4\ud2b8 \uac12 \uc0ac\uc774\uc5d0 \uc9c0\uc815\ub418\ub294 \uad6c\ubd84 \uae30\ud638\ub97c \ud3ec\ud568\ud569\ub2c8\ub2e4. \uad6c\ubd84 \uae30\ud638\uac00 \ube48 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc778 \uacbd\uc6b0 \uc774 \ud568\uc218\ub294 \ubc94\uc704\ub97c \ud6a8\uc728\uc801\uc73c\ub85c \uc5f0\uacb0\ud569\ub2c8\ub2e4.', [
            parameterDescription('delimiter'),
            parameterDescription('ignore_empty'),
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'UNICHAR': functionDescription('\uc8fc\uc5b4\uc9c4 \uc22b\uc790 \uac12\uc774 \ucc38\uc870\ud558\ub294 \uc720\ub2c8\ucf54\ub4dc \ubb38\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number')
        ]),
        'UNICODE': functionDescription('\ud14d\uc2a4\ud2b8\uc758 \uccab \ubb38\uc790\uc5d0 \ud574\ub2f9\ud558\ub294 \uc22b\uc790(\ucf54\ub4dc \ud3ec\uc778\ud2b8)\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'ENCODEURL': functionDescription('URL\ub85c \uc778\ucf54\ub529\ub41c \ubb38\uc790\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text')
        ]),
        'BC_QRCODE': functionDescription('\uc774 \ud568\uc218\ub294 QRCode\ub97c \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_EAN13': functionDescription('\uc774 \ud568\uc218\ub294 EAN13\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_EAN8': functionDescription('\uc774 \ud568\uc218\ub294 EAN8\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_CODABAR': functionDescription('\uc774 \ud568\uc218\ub294 CODABAR\ub97c \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_CODE39': functionDescription('\uc774 \ud568\uc218\ub294 CODE39\ub97c \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_CODE93': functionDescription('\uc774 \ud568\uc218\ub294 CODE93\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_CODE128': functionDescription('\uc774 \ud568\uc218\ub294 CODE128\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_GS1_128': functionDescription('\uc774 \ud568\uc218\ub294 GS1_128\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_CODE49': functionDescription('\uc774 \ud568\uc218\ub294 CODE49\ub97c \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_PDF417': functionDescription('\uc774 \ud568\uc218\ub294 PDF417\uc744 \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'BC_DATAMATRIX': functionDescription('\uc774 \ud568\uc218\ub294 DATAMATRIX\ub97c \ub098\ud0c0\ub0b4\ub294 \ub370\uc774\ud130 \uc9d1\ud569\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
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
        'FILTER': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc744 \ud544\ud130\ub9c1\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('include'),
            parameterDescription('if_empty', false, true)
        ]),
        'RANDARRAY': functionDescription('\uc774 \ud568\uc218\ub294 \ub09c\uc218 \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('min'),
            parameterDescription('max'),
            parameterDescription('whole_number')
        ]),
        'SEQUENCE': functionDescription('\uc774 \ud568\uc218\ub294 \uc77c\ub828\uc758 \uc22b\uc790\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('start'),
            parameterDescription('step')
        ]),
        'SINGLE': functionDescription('\uc774 \ud568\uc218\ub294 \uac12, \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc774 \uc81c\uacf5\ub420 \ub54c \ub2e8\uc77c \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value')
        ]),
        'SORT': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc744 \uc815\ub82c\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('sort_index'),
            parameterDescription('sort_order'),
            parameterDescription('by_col')
        ]),
        'SORTBY': functionDescription('\uc774 \ud568\uc218\ub294 \ud574\ub2f9 \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc758 \uac12\uc744 \uae30\uc900\uc73c\ub85c \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc744 \uc815\ub82c\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('by_array1'),
            parameterDescription('sort_order1'),
            parameterDescription('by_array2', true),
            parameterDescription('sort_order2', true),
        ]),
        'UNIQUE': functionDescription('\uc774 \ud568\uc218\ub294 \ubc94\uc704 \ub610\ub294 \ubc30\uc5f4\uc758 \uace0\uc720 \uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('by_col', false, true),
            parameterDescription('exactly_once', false, true)
        ]),
        'QUERY': functionDescription('\uc774 \ud568\uc218\ub294 \ub370\uc774\ud130 \uad00\ub9ac\uc790\uc758 \ud14c\uc774\ube14\uc5d0\uc11c \ub370\uc774\ud130\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('tableAndRows'),
            parameterDescription('columns'),
            parameterDescription('returnObject')
        ]),
        'LET': functionDescription('\uc774 \ub984\uc5d0 \uacc4\uc0b0 \uacb0\uacfc\ub97c \ud560\ub2f9\ud569\ub2c8\ub2e4. \uc911\uac04 \uacc4\uc0b0, \uac12\uc744 \uc800\uc7a5\ud558\uace0 \uc218\uc2dd \ub0b4 \uc774\ub984\uc744 \uc815\uc758\ud558\ub294 \ub370 \uc720\uc6a9\ud569\ub2c8\ub2e4. \uc774 \uc774\ub984\uc740 LET \ud568\uc218 \ubc94\uc704  \ub0b4\uc5d0\ub9cc \uc801\uc6a9\ub429\ub2c8\ub2e4.', [
            parameterDescription('name1'),
            parameterDescription('name_value1'),
            parameterDescription('name2', true),
            parameterDescription('name_value2', true),
            parameterDescription('calculation')
        ]),
        'IMAGE': functionDescription('\uc774 \ud568\uc218\ub294 \uc774\ubbf8\uc9c0\ub97c \uc140\uc5d0 \ud45c\uc2dc\ud558\uae30 \uc704\ud574 URL \ub610\ub294 base64 \ubb38\uc790\uc5f4\uc744 \uc785\ub825\ud569\ub2c8\ub2e4.', [
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
        'GETPIVOTDATA': functionDescription('\uc774 \ud568\uc218\ub294 \ud53c\ubc97 \ud14c\uc774\ube14\uc5d0 \uc800\uc7a5\ub41c \ub370\uc774\ud130\ub97c \ucd94\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('data_field'),
            parameterDescription('pivot_table'),
            parameterDescription('field1, item1', true, true)
        ]),
        'WEBSERVICE': functionDescription('\uc774 \ud568\uc218\ub294 \uc6f9 \uc11c\ube44\uc2a4\uc5d0\uc11c \ub370\uc774\ud130\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('url')
        ]),
        'FILTERJSON': functionDescription('\uc774 \ud568\uc218\ub294 json \ubb38\uc790\uc5f4\uc744 \uc2a4\uce7c\ub77c \uac12, \uac1c\uccb4 \ub610\ub294 \uac1c\uccb4 \ubc30\uc5f4\ub85c \uad6c\ubb38 \ubd84\uc11d\ud569\ub2c8\ub2e4.', [
            parameterDescription('json_string')
        ]),
        "ASC": functionDescription("ASC \ud568\uc218\ub294 \uc804\uccb4 \ub108\ube44(\ub354\ube14 \ubc14\uc774\ud2b8) \ubb38\uc790\ub97c \uc808\ubc18 \ub108\ube44(\ub2e8\uc77c \ubc14\uc774\ud2b8) \ubb38\uc790\ub85c \ubcc0\uacbd\ud569\ub2c8\ub2e4. \ub354\ube14 \ubc14\uc774\ud2b8 \ubb38\uc790 \uc9d1\ud569\uacfc \ud568\uaed8 \uc0ac\uc6a9\ud569\ub2c8\ub2e4(DBCS).", [
            parameterDescription("text")
        ]),
        "DBCS": functionDescription("DBCS \ud568\uc218\ub294 \ubb38\uc790\uc5f4 \ub0b4\uc758 \uc808\ubc18 \ub108\ube44(\ub2e8\uc77c \ubc14\uc774\ud2b8) \ubb38\uc790\ub97c \uc804\uccb4 \ub108\ube44(\ub354\ube14 \ubc14\uc774\ud2b8) \ubb38\uc790\ub85c \ubcc0\ud658\ud569\ub2c8\ub2e4.", [
            parameterDescription("text")
        ]),
        'LAMBDA': functionDescription('\uc218\uc2dd \ub0b4\uc5d0\uc11c \ud638\ucd9c\ud560 \uc218 \uc788\ub294 \ud568\uc218 \uac12\uc744 \uc791\uc131\ud569\ub2c8\ub2e4.', [
            parameterDescription('parameter_or_calculation'),
            parameterDescription('parameter_or_calculation', true, true)
        ]),
        'MAP': functionDescription('LAMBDA\ub97c \uc801\uc6a9\ud558\uc5ec \uc0c8 \uac12\uc744 \ub9cc\ub4e4\uc5b4 \ubc30\uc5f4\uc758 \uac01 \uac12\uc744 \uc0c8 \uac12\uc5d0 \ub9e4\ud551\ud558\uc5ec \ud615\uc131\ub41c \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('lambda_or_array', true)
        ]),
        'REDUCE': functionDescription('\uac01 \uac12\uc5d0 LAMBDA\ub97c \uc801\uc6a9\ud558\uace0 \ub204\uc801\uae30\uc758 \ucd1d \uac12\uc744 \ubc18\ud658\ud558\uc5ec \ubc30\uc5f4\uc744 \ub204\uc801 \uac12\uc73c\ub85c \uc904\uc785\ub2c8\ub2e4.', [
            parameterDescription('init_value'),
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'SCAN': functionDescription('\uac01 \uac12\uc5d0 LAMBDA\ub97c \uc801\uc6a9\ud558\uc5ec \ubc30\uc5f4\uc744 \uac80\uc0c9\ud558\uace0 \uac01 \uc911\uac04 \uac12\uc744 \uac16\ub294 \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('init_value', false, true),
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'MAKEARRAY': functionDescription('LAMBDA\ub97c \uc801\uc6a9\ud558\uc5ec \uc9c0\uc815\ub41c \ud589 \ubc0f \uc5f4 \ud06c\uae30\uc758 \uacc4\uc0b0\ub41c \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('rows'),
            parameterDescription('cols'),
            parameterDescription('function')
        ]),
        'BYCOL': functionDescription('\uac01 \uc5f4\uc5d0 LAMBDA\ub97c \uc801\uc6a9\ud558\uace0 \uacb0\uacfc\uc758 \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc608\ub97c \ub4e4\uc5b4, \uc6d0\ub798 \ubc30\uc5f4\uc774 3\uc5f4 2\ud589\uc774\uba74 \ubc18\ud658\ub418\ub294 \ubc30\uc5f4\uc740 3\uc5f4 1\ud589\uc785\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'BYROW': functionDescription('\uac01 \ud589\uc5d0 LAMBDA\ub97c \uc801\uc6a9\ud558\uace0 \uacb0\uacfc\uc758 \ubc30\uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4. \uc608\ub97c \ub4e4\uc5b4 \uc6d0\ub798 \ubc30\uc5f4\uc774 3\uc5f4 2\ud589\uc774\uba74 \ubc18\ud658\ub418\ub294 \ubc30\uc5f4\uc740 1\uc5f4 2\ud589\uc785\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'ISOMITTED': functionDescription('LAMBDA\uc758 \uac12\uc774 \ub204\ub77d\ub418\uc5c8\ub294\uc9c0 \uc5ec\ubd80\ub97c \ud655\uc778\ud558\uace0 TRUE \ub610\ub294 FALSE\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('argument')
        ]),
        'TEXTBEFORE': functionDescription('\ubb38\uc790\ub97c \uad6c\ubd84\ud558\uae30 \uc804\uc758 \ud14d\uc2a4\ud2b8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('delimiter'),
            parameterDescription('instance_num', false, true),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
            parameterDescription('match_end', false, true),
            parameterDescription(IF_NOT_FOUND, false, true)
        ]),
        'TEXTAFTER': functionDescription('\ubb38\uc790\ub97c \uad6c\ubd84\ud55c \ud6c4\uc758 \ud14d\uc2a4\ud2b8\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('delimiter'),
            parameterDescription('instance_num', false, true),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
            parameterDescription('match_end', false, true),
            parameterDescription(IF_NOT_FOUND, false, true)
        ]),
        'TEXTSPLIT': functionDescription('\uad6c\ubd84 \uae30\ud638\ub97c \uc0ac\uc6a9\ud558\uc5ec \ud14d\uc2a4\ud2b8\ub97c \ud589 \ub610\ub294 \uc5f4\ub85c \ubd84\ud560\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('col_delimiter'),
            parameterDescription('row_delimiter', false, true),
            parameterDescription('ignore_empty', false, true),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
            parameterDescription(PAD_WITH, false, true)
        ]),
        'SJS.REGEXEXTRACT': functionDescription('\uc815\uaddc\uc2dd\uc5d0 \ub530\ub77c \uc77c\uce58\ud558\ub294 \ud558\uc704 \ubb38\uc790\uc5f4\uc744 \ucd94\ucd9c\ud569\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('regular_expression'),
            parameterDescription('modifiers', false, true)
        ]),
        'SJS.REGEXMATCH': functionDescription('\ud14d\uc2a4\ud2b8\uac00 \uc815\uaddc\uc2dd\uacfc \uc77c\uce58\ud558\ub294\uc9c0 \uc5ec\ubd80\ub97c \ub098\ud0c0\ub0c5\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('regular_expression'),
            parameterDescription('modifiers', false, true)
        ]),
        'SJS.REGEXREPLACE': functionDescription('\uc815\uaddc\uc2dd\uc744 \uc0ac\uc6a9\ud558\uc5ec \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\uc758 \uc77c\ubd80\ub97c \ub2e4\ub978 \ud14d\uc2a4\ud2b8 \ubb38\uc790\uc5f4\ub85c \ubc14\uafc9\ub2c8\ub2e4.', [
            parameterDescription('text'),
            parameterDescription('regular_expression'),
            parameterDescription('replacement'),
            parameterDescription('modifiers', false, true)
        ]),
        'VSTACK': functionDescription('\uc218\uc9c1\uc73c\ub85c \ubc30\uc5f4\uc744 \ud558\ub098\uc758 \ubc30\uc5f4\ub85c \uc313\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2', true, true)
        ]),
        'HSTACK': functionDescription('\uc218\ud3c9\uc73c\ub85c \ubc30\uc5f4\uc744 \ud558\ub098\uc758 \ubc30\uc5f4\ub85c \uc313\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('array1'),
            parameterDescription('array2', true, true)
        ]),
        'TOROW': functionDescription('\ubc30\uc5f4\uc744 \ud558\ub098\uc758 \ud589\uc73c\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('ignore', false, true),
            parameterDescription('scan_by_column', false, true)
        ]),
        'TOCOL': functionDescription('\ubc30\uc5f4\uc744 \ud558\ub098\uc758 \uc5f4\ub85c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('ignore', false, true),
            parameterDescription('scan_by_column', false, true)
        ]),
        'WRAPROWS': functionDescription('\uc9c0\uc815\ub41c \uc218\uc758 \uac12 \ub4a4\uc5d0 \ud589 \ub610\ub294 \uc5f4 \ubca1\ud130\ub97c \ub798\ud551\ud569\ub2c8\ub2e4.', [
            parameterDescription('vector'),
            parameterDescription('wrap_count'),
            parameterDescription(PAD_WITH, false, true)
        ]),
        'WRAPCOLS': functionDescription('\uc9c0\uc815\ub41c \uc218\uc758 \uac12 \ub4a4\uc5d0 \ud589 \ub610\ub294 \uc5f4 \ubca1\ud130\ub97c \ub798\ud551\ud569\ub2c8\ub2e4.', [
            parameterDescription('vector'),
            parameterDescription('wrap_count'),
            parameterDescription(PAD_WITH, false, true)
        ]),
        'TAKE': functionDescription('\ubc30\uc5f4 \uc2dc\uc791 \ub610\ub294 \ub05d\uc5d0\uc11c \ud589 \ub610\ub294 \uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('columns', false, true)
        ]),
        'DROP': functionDescription('\ubc30\uc5f4 \uc2dc\uc791 \ub610\ub294 \ub05d\uc5d0\uc11c \ud589 \ub610\ub294 \uc5f4\uc744 \uc0ad\uc81c\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('columns', false, true)
        ]),
        'EXPAND': functionDescription('\ubc30\uc5f4\uc744 \uc9c0\uc815\ub41c \ucc28\uc6d0\uc73c\ub85c \ud655\uc7a5\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('columns', false, true),
            parameterDescription(PAD_WITH, false, true)
        ]),
        'CHOOSEROWS': functionDescription('\ubc30\uc5f4\uc774\ub098 \ucc38\uc870\uc5d0\uc11c \ud589\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('row_num1'),
            parameterDescription('row_num2', true, true)
        ]),
        'CHOOSECOLS': functionDescription('\ubc30\uc5f4 \ub610\ub294 \ucc38\uc870\uc5d0\uc11c \uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('array'),
            parameterDescription('col_num1'),
            parameterDescription('col_num2', true, true)
        ]),
        'SJS.UUID': functionDescription('\ub79c\ub364 UUID\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('number'),
        ]),
        'SJS.ENDWITH': functionDescription('str1\uc774 str2\ub85c \ub05d\ub098\ub294\uc9c0 \uc5ec\ubd80\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('within_text'),
            parameterDescription('find_text'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
        ]),
        'SJS.STARTWITH': functionDescription('str1 \ubb38\uc790\uc5f4\uc774 str2\ub85c \uc2dc\uc791\ud558\ub294\uc9c0 \uc5ec\ubd80\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('within_text'),
            parameterDescription('find_text'),
            parameterDescription(MATCH_MODE, false, true, MATCH_MODE_ENUM_INFO),
        ]),
        'R.INDEX': functionDescription('(ReportSheet \ud55c\uc815) \ubd80\ubaa8 \uce21\uc815 \ud655\uc7a5\uc5d0\uc11c \uc9c0\uc815\ud55c \uc140\uc758 \uc778\ub371\uc2a4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference'),
            parameterDescription('context_reference', false, true),
        ]),
        'R.RANK': functionDescription('(ReportSheet \ud55c\uc815) \uc0c1\uc704 \ub178\ub4dc\uc5d0\uc11c \uc9c0\uc815\ud55c \uc140\uc758 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('reference'),
            parameterDescription('context_reference', false, true),
            parameterDescription('order', false, true),
            parameterDescription('rank_mode', false, true),
        ]),
        'SJS.FISCALYEAR': functionDescription('\ud68c\uacc4\uc5f0\ub3c4\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('date'),
            parameterDescription('start_month', false, true),
        ]),
        'R.R': functionDescription('(ReportSheet \ud55c\uc815) \uc9c0\uc815\ub41c \uc140\uc5d0 \ub530\ub978 \uc0c1\ub300 \uc624\ud504\uc14b.', [
            parameterDescription('reference'),
            parameterDescription('offset'),
        ]),
        'R.A': functionDescription('(ReportSheet \ud55c\uc815) \uc9c0\uc815\ub41c \uc140\uc744 \uae30\uc900\uc73c\ub85c \uc808\ub300 \uc624\ud504\uc14b.', [
            parameterDescription('reference'),
            parameterDescription('offset'),
        ]),
        'R.V': functionDescription('(ReportSheet \ud55c\uc815) \uc9c0\uc815\ub41c \uc624\ud504\uc14b\uc774\ub098 \ucee8\ud14d\uc2a4\ud2b8\uc5d0 \ub530\ub77c \uc140\uc758 \ub370\uc774\ud130\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4. ', [
            parameterDescription('reference'),
            parameterDescription('offset | context_reference | "CurrentPage"', true, true),
        ]),
        'R.CURRENTPAGE': functionDescription('(ReportSheet \ud55c\uc815) \ud604\uc7ac \ud398\uc774\uc9c0\uac00 \ubcf4\uace0\uc11c\uc758 \uba87 \ubc88\uc9f8 \ud398\uc774\uc9c0\uc778\uc9c0 \ubc18\ud658\ud574\uc8fc\uc138\uc694.', []),
        'R.PAGESCOUNT': functionDescription('(ReportSheet \ud55c\uc815) \ud604\uc7ac \ubcf4\uace0\uc11c\uc758 \ucd1d \uac1c\uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        'R.PROPORTION': functionDescription('(ReportSheet \ud55c\uc815) \ubd80\ubaa8 \ub178\ub4dc\uc5d0\uc11c \uc9c0\uc815\ud55c \uc140\uc758 \ubc31\ubd84\uc728\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_reference'),
            parameterDescription('context_reference1', false, true),
            parameterDescription('context_reference2', false, true),
        ]),
        'R.CUMULATIVE': functionDescription('(ReportSheet \ud55c\uc815) \ud604\uc7ac \uc140\uc758 \ub204\uc801\uac12\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_reference'),
            parameterDescription('context_reference1', false, true),
            parameterDescription('context_reference2', false, true),
        ]),
        'R.MOM': functionDescription('(ReportSheet \ud55c\uc815) \ub300\uc0c1 \uc140\uc758 \ub9c1 \ub370\uc774\ud130\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_reference'),
            parameterDescription('context_reference1', false, true),
            parameterDescription('context_reference2', false, true),
        ]),
        'R.YOY': functionDescription('(ReportSheet \ud55c\uc815) \ub300\uc0c1 \uc140\uc758 \uc804\ub144 \ub300\ube44 \ub370\uc774\ud130\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_reference'),
            parameterDescription('context_reference1'),
            parameterDescription('context_reference2', false, true),
        ]),
    },
    Fbx_Summary: '\uc694\uc57d',
    Fbx_TableName_Description: '\ud45c \uc774\ub984 ',
    Fbx_TableSheetName_Description: '\ub2e4\uc74c\uc5d0 \ub300\ud55c \ud14c\uc774\ube14\uc2dc\ud2b8 \uc774\ub984',
    Fbx_CustomName_Description: '\uc0ac\uc6a9\uc790 \uc9c0\uc815 \uc774\ub984 ',
    _tableFunctionsResource: {
        All: { name: '#All', description: '\uc5f4 \uba38\ub9ac\uae00, \ub370\uc774\ud130, \uc694\uc57d \ud589 \ub4f1 \uc9c0\uc815\ud55c \ud45c \uc5f4\uc774\ub098 \ud45c\uc758 \uc804\uccb4 \ub0b4\uc6a9\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.' },
        Data: { name: '#Data', description: '\ud45c\uc758 \ub370\uc774\ud130 \uc140\uc774\ub098 \uc9c0\uc815\ud55c \ud45c \uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.' },
        Headers: { name: '#Headers', description: '\ud45c\uc758 \uc5f4 \uba38\ub9ac\uae00\uc774\ub098 \uc9c0\uc815\ud55c \ud45c \uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.' },
        Totals: { name: '#Totals', description: '\ud45c\uc758 \uc694\uc57d \ud589\uc774\ub098 \uc9c0\uc815\ud55c \ud45c \uc5f4\uc744 \ubc18\ud658\ud569\ub2c8\ub2e4.' },
        thisRow: { name: '#This Row', description: '\uc774 \ud589.' },
    }
};


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_211651__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_211651__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var calcEngine_res_ko_1 = __nested_webpack_require_211651__( "./src/calcEngine.res.ko.ts");
exports.SR = { ko: calcEngine_res_ko_1.resource };

}();
((GC = typeof GC === "undefined" ? {} : GC).Spread = GC.Spread || {}).CalcEngine = __webpack_exports__;
 })()
;

module.exports = GC.Spread.CalcEngine;

/***/ }),

/***/ "./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.ko.js":
/*!***********************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.ko.js ***!
  \***********************************************************************************/
/***/ (function(module) {

var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./dist/src/res.ko.js":
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
        description: '\ud30c\ud2f0\uc158\uc758 \uc704\ucabd \uacbd\uacc4\ub97c \ub098\ud0c0\ub0c5\ub2c8\ub2e4.',
    },
    {
        value: '[@]',
        description: '\ud604\uc7ac \ud589 \ub610\ub294 \uac12\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.'
    },
    {
        value: '[@-n]',
        description: '\uc21c\uc11c\uac00 \uc624\ub984\ucc28\uc21c\uc778 \uacbd\uc6b0 n\ubc88\uc9f8 \uc55e\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.'
    },
    {
        value: '[@+n]',
        description: '\uc21c\uc11c\uac00 \ub0b4\ub9bc\ucc28\uc21c\uc778 \uacbd\uc6b0 n\ubc88\uc9f8 \uc55e\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.'
    },
];
var FRAME_FOLLOWING_ENUM_INFO = [
    {
        value: '-1',
        description: '\ud30c\ud2f0\uc158\uc758 \ud558\uc704 \ud56d\ubaa9\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.',
    },
    {
        value: '[@]',
        description: '\ud604\uc7ac \ud589 \ub610\ub294 \uac12\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.'
    },
    {
        value: '[@+n]',
        description: '\uc21c\uc11c\uac00 \uc624\ub984\ucc28\uc21c\uc778 \uacbd\uc6b0 n\ubc88\uc9f8 \ub2e4\uc74c\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.'
    },
    {
        value: '[@-n]',
        description: '\uc21c\uc11c\uac00 \ub0b4\ub9bc\ucc28\uc21c\uc778 \uacbd\uc6b0 n\ubc88\uc9f8 \ub2e4\uc74c\uc744 \ub098\ud0c0\ub0c5\ub2c8\ub2e4.'
    },
];
var FRAME_EXCLUDE_MODE_ENUM_INFO = [
    {
        value: '0',
        description: '\uc774\ub294 \uae30\ubcf8 \uacbd\uc6b0\ub85c \ud589\uc774 \uc81c\uc678\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.',
    },
    {
        value: '1',
        description: '\ud604\uc7ac \ud589\uc740 \uc81c\uc678\ub418\uace0 \ud604\uc7ac \ud589\uc758 \ub2e4\ub978 \ud53c\uc5b4\ub294 \ub0a8\uc544 \uc788\uc2b5\ub2c8\ub2e4.'
    },
    {
        value: '2',
        description: '\ud604\uc7ac \ud589\uacfc \ud53c\uc5b4\ub294 \ubaa8\ub450 \uc81c\uc678\ub429\ub2c8\ub2e4.'
    },
    {
        value: '3',
        description: '\ud604\uc7ac \ud589\uc774 \ub0a8\uc544 \uc788\uace0 \ub2e4\ub978 \ud53c\uc5b4\ub294 \uc81c\uc678\ub429\ub2c8\ub2e4.'
    },
];
exports.resource = {
    _calculationFunctionsResource: {
        WINDOW: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uc708\ub3c4\uc6b0 \uae30\ub2a5 \uc801\uc6a9\uc744 \uc704\ud574 \ubd84\ud560 \ubc0f \uc21c\uc11c\ub97c \uc9c0\uc815\ud558\uc5ec \ud589\uc744 \uc708\ub3c4\uc6b0\ub85c \uacb0\uc815\ud569\ub2c8\ub2e4.', [
            parameterDescription('window_function'),
            parameterDescription('partition_by', true),
            parameterDescription('order_by', true),
            parameterDescription('frame_rows\\range\\groups', true),
        ]),
        WINDOWDEF: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) WINDOW \ud568\uc218\ub85c \uc7ac\uc0ac\uc6a9\ud560 \uc218 \uc788\ub294 \ub2e8\ucd95\ud0a4\ub97c \uc815\uc758\ud569\ub2c8\ub2e4.', [
            parameterDescription('partition_by', true),
            parameterDescription('order_by', true),
            parameterDescription('frame_rows\\range\\groups', true),
        ]),
        PARTITIONBY: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud589\uc744 \ud30c\ud2f0\uc158\uc73c\ub85c \ub098\ub215\ub2c8\ub2e4.', [
            parameterDescription('field_function', false, true),
        ]),
        ORDERBY: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uac01 \ud30c\ud2f0\uc158\uc758 \ub17c\ub9ac\uc801 \uc21c\uc11c\ub97c \uc815\uc758\ud569\ub2c8\ub2e4.', [
            parameterDescription('field_function', false, true),
        ]),
        ORDERASC: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uc9c0\uc815\ud55c \ud544\ub4dc\uc758 \uc624\ub984\ucc28\uc21c\uc744 \uc9c0\uc815\ud569\ub2c8\ub2e4.', [
            parameterDescription('field_function'),
        ]),
        ORDERDESC: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uc9c0\uc815\ud55c \ud544\ub4dc\uc758 \ub0b4\ub9bc\ucc28\uc21c\uc744 \uc9c0\uc815\ud569\ub2c8\ub2e4.', [
            parameterDescription('field_function'),
        ]),
        FRAMEROWS: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud604\uc7ac \ud589\uc744 \uae30\uc900\uc73c\ub85c \uc55e \ud589 \ub610\ub294 \ub4a4 \ud589\uc744 \uc9c0\uc815\ud558\uc5ec \uc708\ub3c4\uc6b0\uc758 \ud589 \uc9d1\ud569\uc744 \uc81c\ud55c\ud569\ub2c8\ub2e4.', [
            parameterDescription('beginning_function', false, false, FRAME_PRECEDING_ENUM_INFO),
            parameterDescription('ending_function', true, false, FRAME_FOLLOWING_ENUM_INFO),
            parameterDescription('exclude_mode', true, false, FRAME_EXCLUDE_MODE_ENUM_INFO),
        ]),
        FRAMERANGE: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud604\uc7ac \ud589\uc758 \uac12\uc5d0 \ub300\ud574 \uc55e \ub610\ub294 \ub4a4 \ubc94\uc704\ub97c \uc9c0\uc815\ud558\uc5ec \uc708\ub3c4\uc6b0\uc758 \ud589 \uc9d1\ud569\uc744 \uc81c\ud55c\ud569\ub2c8\ub2e4.', [
            parameterDescription('beginning_function', false, false, FRAME_PRECEDING_ENUM_INFO),
            parameterDescription('ending_function', true, false, FRAME_FOLLOWING_ENUM_INFO),
            parameterDescription('exclude_mode', true, false, FRAME_EXCLUDE_MODE_ENUM_INFO),
        ]),
        FRAMEGROUPS: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud604\uc7ac \uadf8\ub8f9\uacfc \uad00\ub828\ub41c \uce74\uc6b4\ud2b8 "\uadf8\ub8f9"\uacfc \uad00\ub828\ud558\uc5ec \uc2dc\uc791 \ub610\ub294 \uc885\ub8cc \ubc94\uc704\ub97c \uc9c0\uc815\ud558\uc5ec \ucc3d\uc758 \ud589 \uc9d1\ud569\uc744 \uc81c\ud55c\ud569\ub2c8\ub2e4.', [
            parameterDescription('beginning_function', false, false, FRAME_PRECEDING_ENUM_INFO),
            parameterDescription('ending_function', true, false, FRAME_FOLLOWING_ENUM_INFO),
            parameterDescription('exclude_mode', true, false, FRAME_EXCLUDE_MODE_ENUM_INFO),
        ]),
        CUMEDIST: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uac12 \uadf8\ub8f9 \ub0b4\uc5d0 \uc788\ub294 \uac12\uc758 \ub204\uc801 \ubd84\ud3ec\uc785\ub2c8\ub2e4.', []),
        W_PERCENTRANK: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud589\uc758 \ud30c\ud2f0\uc158 \ub0b4\uc5d0\uc11c \ud589\uc758 \uc0c1\ub300\uc801 \uc21c\uc704(%)\ub97c \uacc4\uc0b0\ud569\ub2c8\ub2e4.', []),
        ROWNUMBER: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud30c\ud2f0\uc158 \ub0b4\uc758 \ud604\uc7ac \ud589 \uc218\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        W_RANK: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uacb0\uacfc \uc9d1\ud569\uc758 \ud30c\ud2f0\uc158 \ub0b4\uc5d0 \uc788\ub294 \uac01 \ud589\uc758 \uc21c\uc704\ub97c \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        DENSERANK: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud30c\ud2f0\uc158 \ub0b4\uc758 \ud604\uc7ac \ud589 \uc21c\uc704\ub97c \uacf5\ubc31 \uc5c6\uc774 \ubc18\ud658\ud569\ub2c8\ub2e4.', []),
        LEAD: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud604\uc7ac \ud589\uc758 \ub4a4\uc5d0 \uc8fc\uc5b4\uc9c4 \ubb3c\ub9ac\uc801 \uc624\ud504\uc14b\uc5d0\uc11c \ud589\uc758 \uac12\uc5d0 \uc561\uc138\uc2a4\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('value_function'),
            parameterDescription('offset', true),
            parameterDescription('default_value', true),
        ]),
        LAG: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud604\uc7ac \ud589\uc758 \uc55e\uc5d0 \uc8fc\uc5b4\uc9c4 \ubb3c\ub9ac\uc801 \uc624\ud504\uc14b\uc5d0\uc11c \ud589\uc758 \uac12\uc5d0 \uc561\uc138\uc2a4\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4.', [
            parameterDescription('value_function'),
            parameterDescription('offset', true),
            parameterDescription('default_value', true),
        ]),
        NTILE: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ud30c\ud2f0\uc158\uc744 N\uac1c\uc758 \ubc84\ud0b7\uc73c\ub85c \ub098\ub204\uace0 \uac01 \ud589\uc5d0 \ubc84\ud0b7 \ubc88\ud638\ub97c \ud560\ub2f9\ud569\ub2c8\ub2e4.', [
            parameterDescription('n'),
        ]),
        FIRSTVALUE: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uc708\ub3c4\uc6b0 \ub0b4\uc758 \uccab \ubc88\uc9f8 \ud589\uc5d0\uc11c \uac12\uc5d0 \uc561\uc138\uc2a4\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_function'),
        ]),
        LASTVALUE: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \uc708\ub3c4\uc6b0 \ub0b4\uc758 \ub9c8\uc9c0\ub9c9 \ud589\uc5d0\uc11c \uac12\uc5d0 \uc561\uc138\uc2a4\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_function'),
        ]),
        NTHVALUE: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \ud55c\uc815) \ucc3d \ud504\ub808\uc784 \ub0b4\uc758 n\ubc88\uc9f8 \ud589\uc5d0\uc11c \uac12\uc5d0 \uc561\uc138\uc2a4\ud569\ub2c8\ub2e4.', [
            parameterDescription('value_function'),
            parameterDescription('n'),
        ]),
        CALCULATE: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uadf8\ub8f9\ud654 \ud55c\uc815) \uc218\uc2dd\uc758 \uadf8\ub8f9 \ucee8\ud14d\uc2a4\ud2b8\ub97c \ud655\uc7a5\ud569\ub2c8\ub2e4.', [
            parameterDescription('formula'),
            parameterDescription('remove_filters_function'),
        ]),
        REMOVEFILTERS: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uadf8\ub8f9\ud654 \ud55c\uc815) \uc0c1\uc704 \uadf8\ub8f9 \uc218\uc900\uc758 \ucee8\ud14d\uc2a4\ud2b8\ub97c \uc120\ud0dd\ud569\ub2c8\ub2e4.', [
            parameterDescription('group_field_function', true, true),
        ]),
        CHILDREN: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uacc4\uce35 \ud55c\uc815) \uc0c1\uc704 \ud56d\ubaa9\uc5d0\uc11c \ud2b9\uc815 \uc624\ud504\uc14b\uc774 \ub418\ub294 \ud558\uc704 \ud56d\ubaa9\uc758 \uac12\uc744 \uac80\uc0c9\ud569\ub2c8\ub2e4.', [
            parameterDescription('level_offset'),
            parameterDescription('field_function'),
        ]),
        ONLYCHILDREN: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uacc4\uce35 \ud55c\uc815) \ubd80\ubaa8\uac00 \uc544\ub2cc \uc790\uc2dd \uac12\uc744 \uac80\uc0c9\ud569\ub2c8\ub2e4.', [
            parameterDescription('field_function'),
        ]),
        PARENT: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uacc4\uce35 \ud55c\uc815) \ud604\uc7ac\uc5d0\uc11c \ud2b9\uc815 \uc624\ud504\uc14b\uc778 \uc0c1\uc704 \uac12\uc744 \uac80\uc0c9\ud569\ub2c8\ub2e4.', [
            parameterDescription('level_offset'),
            parameterDescription('field_function'),
        ]),
        LEVEL: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uacc4\uce35 \ud55c\uc815) \ud604\uc7ac \uc218\uc900\uc744 \uac80\uc0c9\ud569\ub2c8\ub2e4.', []),
        LEVELROWNUMBER: functionDescription('(\ud14c\uc774\ube14\uc2dc\ud2b8 \uacc4\uce35 \ud55c\uc815) \ubd80\ubaa8 \uc544\ub798\uc5d0 \uc788\ub294 \ud604\uc7ac\uc758 \ud589 \ubc88\ud638\ub97c \uac80\uc0c9\ud569\ub2c8\ub2e4.', []),
    },
};


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_11908__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_11908__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var res_ko_1 = __nested_webpack_require_11908__( "./dist/src/res.ko.js");
exports.SR = { ko: res_ko_1.resource };

}();
(GC = typeof GC === "undefined" ? {} : GC).Data = __webpack_exports__;
 })()
;

module.exports = GC.Data;

/***/ }),

/***/ "./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.ko.js":
/*!*****************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.ko.js ***!
  \*****************************************************************************/
/***/ (function(module, exports) {

var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./src/pivotEngine.res.ko.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.resource = void 0;
exports.resource = {
    dateResource: {
        Jan: "1\uc6d4",
        Feb: "2\uc6d4",
        Mar: "3\uc6d4",
        Apr: "4\uc6d4",
        May: "5\uc6d4",
        Jun: "6\uc6d4",
        Jul: "7\uc6d4",
        Aug: "8\uc6d4",
        Sep: "9\uc6d4",
        Oct: "10\uc6d4",
        Nov: "11\uc6d4",
        Dec: "12\uc6d4",
        Qtr1: "1\uc0ac\ubd84\uae30",
        Qtr2: "2\uc0ac\ubd84\uae30",
        Qtr3: "3\uc0ac\ubd84\uae30",
        Qtr4: "4\uc0ac\ubd84\uae30",
        Seconds: "\ucd08",
        Minutes: "\ubd84",
        Hours: "\uc2dc",
        Days: "\uc77c",
        Months: "\uc6d4",
        Quarters: "\ubd84\uae30",
        Years: "\uc5f0",
        SECOND_GROUP_ITEMS: ["00\ucd08", "01\ucd08", "02\ucd08", "03\ucd08", "04\ucd08", "05\ucd08", "06\ucd08", "07\ucd08", "08\ucd08", "09\ucd08", "10\ucd08", "11\ucd08", "12\ucd08", "13\ucd08", "14\ucd08", "15\ucd08", "16\ucd08", "17\ucd08", "18\ucd08", "19\ucd08", "20\ucd08", "21\ucd08", "22\ucd08", "23\ucd08", "24\ucd08", "25\ucd08", "26\ucd08", "27\ucd08", "28\ucd08", "29\ucd08", "30\ucd08", "31\ucd08", "32\ucd08", "33\ucd08", "34\ucd08", "35\ucd08", "36\ucd08", "37\ucd08", "38\ucd08", "39\ucd08", "40\ucd08", "41\ucd08", "42\ucd08", "43\ucd08", "44\ucd08", "45\ucd08", "46\ucd08", "47\ucd08", "48\ucd08", "49\ucd08", "50\ucd08", "51\ucd08", "52\ucd08", "53\ucd08", "54\ucd08", "55\ucd08", "56\ucd08", "57\ucd08", "58\ucd08", "59\ucd08"],
        MINUTE_GROUP_ITEMS: ["00\ubd84", "01\ubd84", "02\ubd84", "03\ubd84", "04\ubd84", "05\ubd84", "06\ubd84", "07\ubd84", "08\ubd84", "09\ubd84", "10\ubd84", "11\ubd84", "12\ubd84", "13\ubd84", "14\ubd84", "15\ubd84", "16\ubd84", "17\ubd84", "18\ubd84", "19\ubd84", "20\ubd84", "21\ubd84", "22\ubd84", "23\ubd84", "24\ubd84", "25\ubd84", "26\ubd84", "27\ubd84", "28\ubd84", "29\ubd84", "30\ubd84", "31\ubd84", "32\ubd84", "33\ubd84", "34\ubd84", "35\ubd84", "36\ubd84", "37\ubd84", "38\ubd84", "39\ubd84", "40\ubd84", "41\ubd84", "42\ubd84", "43\ubd84", "44\ubd84", "45\ubd84", "46\ubd84", "47\ubd84", "48\ubd84", "49\ubd84", "50\ubd84", "51\ubd84", "52\ubd84", "53\ubd84", "54\ubd84", "55\ubd84", "56\ubd84", "57\ubd84", "58\ubd84", "59\ubd84"],
        HOUR_GROUP_ITEMS: ["00\uc2dc", "01\uc2dc", "02\uc2dc", "03\uc2dc", "04\uc2dc", "05\uc2dc", "06\uc2dc", "07\uc2dc", "08\uc2dc", "09\uc2dc", "10\uc2dc", "11\uc2dc", "12\uc2dc", "13\uc2dc", "14\uc2dc", "15\uc2dc", "16\uc2dc", "17\uc2dc", "18\uc2dc", "19\uc2dc", "20\uc2dc", "21\uc2dc", "22\uc2dc", "23\uc2dc"]
    },
    Exp_NoItemParseError: "\ud56d\ubaa9 \uc774\ub984\uc744 \ucc3e\uc744 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4. \uc774\ub984\uc744 \uc62c\ubc14\ub974\uac8c \uc785\ub825\ud588\ub294\uc9c0, \ud53c\ubc97 \ud14c\uc774\ube14 \ubcf4\uace0\uc11c\uc5d0 \ud56d\ubaa9\uc774 \uc788\ub294\uc9c0 \ud655\uc778\ud569\ub2c8\ub2e4.",
    Exp_UnsupportedCalcItemType: "\ucc38\uc870, \uc774\ub984 \ubc0f \ubc30\uc5f4\uc740 \ud53c\ubc97 \ud14c\uc774\ube14 \ud568\uc218\uc5d0\uc11c \uc9c0\uc6d0\ub418\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.",
    blank: "(\ube44\uc5b4 \uc788\uc74c)"
};


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_3380__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_3380__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var pivotEngine_res_ko_1 = __nested_webpack_require_3380__( "./src/pivotEngine.res.ko.ts");
exports.SR = { ko: pivotEngine_res_ko_1.resource };

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

/***/ "./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.ko.js":
/*!*********************************************************************************************!*\
  !*** ./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.ko.js ***!
  \*********************************************************************************************/
/***/ (function(module) {

var GC;
 (function() {
 	"use strict";
 	var __webpack_modules__ = ({

 "./src/common/util/util.res.ko.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_Separator = exports.Exp_InvalidCast = exports.Exp_InvalidNumberFormat = exports.Exp_BadFormatSpecifier = exports.Exp_InvalidNumberGroupSize = exports.Exp_InvalidSemicolons = exports.Exp_InvalidExponentFormat = exports.Exp_InvalidDateFormat = void 0;
exports.Exp_InvalidDateFormat = '\uc720\ud6a8\ud558\uc9c0 \uc54a\uc740 \ud615\uc2dd\uc758 \ub0a0\uc9dc \ud328\ud134';
exports.Exp_InvalidExponentFormat = '\uc720\ud6a8\ud558\uc9c0 \uc54a\uc740 \ud615\uc2dd\uc758 \uc9c0\uc218 ';
exports.Exp_InvalidSemicolons = '\uc720\ud6a8\ud558\uc9c0 \uc54a\uc740 \ud615\uc2dd: \uc138\ubbf8\ucf5c\ub860\uc774 \ub108\ubb34 \ub9ce\uc74c';
exports.Exp_InvalidNumberGroupSize = 'NumberGroupSize\ub294 1\uacfc 9 \uc0ac\uc774 \uc5ec\uc57c\ud558\uba70 \ub9c8\uc9c0\ub9c9 \uc694\uc18c\ub294 0\uc774 \ub420 \uc218 \uc788\uc2b5\ub2c8\ub2e4.';
exports.Exp_BadFormatSpecifier = '\uc798\ubabb\ub41c \ud615\uc2dd \uc9c0\uc815\uc790';
exports.Exp_InvalidNumberFormat = '\uc798\ubabb\ub41c \uc22b\uc790 \uc11c\uc2dd \ud328\ud134';
exports.Exp_InvalidCast = '\uc720\ud6a8\ud558\uc9c0 \uc54a\uc740 \uce90\uc2a4\ud2b8 \uc608\uc678';
exports.Exp_Separator = '\uc18c\uc218 \uc790\ub9bf\uc218 \uad6c\ubd84\uc790\uc640 \ubaa9\ub85d \uad6c\ubd84\uc790 \uadf8\ub9ac\uace0 \ubc30\uc5f4 \ubaa9\ub85d \uad6c\ubd84\uc790\ub294 culture info\uc640 \ub2ec\ub77c\uc57c \ud569\ub2c8\ub2e4.';


 }),

 "./src/plugins/formatter/formatter.res.ko.ts":
 (function(__unused_webpack_module, exports) {


Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.Exp_InvalidCast = exports.Exp_ValueIllegal = exports.Exp_TokenIllegal = exports.Exp_DuplicatedDescriptor = exports.Exp_ValueIsNull = exports.Exp_FormatIllegal = exports.Exp_InvalidBackslash = exports.Exp_TokenIsNull = void 0;
exports.Exp_TokenIsNull = 'token\uc740 null\uc785\ub2c8\ub2e4';
exports.Exp_InvalidBackslash = '\'\\\'\ub294 \uc778\uc2dd\ud560\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.';
exports.Exp_FormatIllegal = '\ud615\uc2dd\uc774 \uc798\ubabb \ub418\uc5c8\uc2b5\ub2c8\ub2e4..';
exports.Exp_ValueIsNull = '\uac12\uc740 null \uc785\ub2c8\ub2e4';
exports.Exp_DuplicatedDescriptor = '\uc124\uba85\uc790 \uc720\ud615\uc774 \ucd94\uac00 \ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_TokenIllegal = 'token\uc774 \uc798\ubabb\ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_ValueIllegal = 'value\uac00 \uc798\ubabb \ub418\uc5c8\uc2b5\ub2c8\ub2e4.';
exports.Exp_InvalidCast = '\uc720\ud6a8\ud558\uc9c0 \uc54a\uc740 Cast \uc608\uc678\uc785\ub2c8\ub2e4.';


 })

 	});
 
 	var __webpack_module_cache__ = {};
 	
 
 	function __nested_webpack_require_2784__(moduleId) {
 	
 		var cachedModule = __webpack_module_cache__[moduleId];
 		if (cachedModule !== undefined) {
 			return cachedModule.exports;
 		}
 	
 		var module = __webpack_module_cache__[moduleId] = {
 		
 		
 			exports: {}
 		};
 	
 	
 		__webpack_modules__[moduleId](module, module.exports, __nested_webpack_require_2784__);
 	
 	
 		return module.exports;
 	}
 	
var __webpack_exports__ = {};

!function() {
var exports = __webpack_exports__;

Object.defineProperty(exports, "__esModule", ({ value: true }));
exports.SR = void 0;
var commonResource = __nested_webpack_require_2784__( "./src/common/util/util.res.ko.ts");
var formatterResource = __nested_webpack_require_2784__( "./src/plugins/formatter/formatter.res.ko.ts");
var resource = commonResource;
for (var prop in formatterResource) {
    if (formatterResource.hasOwnProperty(prop)) {
        resource[prop] = formatterResource[prop];
    }
}
exports.SR = { ko: resource };

}();
((GC = typeof GC === "undefined" ? {} : GC).Spread = GC.Spread || {}).Common = __webpack_exports__;
 })()
;

module.exports = GC.Spread.Common;

/***/ }),

/***/ "./resource.ko.entry.js":
/*!******************************!*\
  !*** ./resource.ko.entry.js ***!
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
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.ko.js */ "./node_modules_local/@spreadjs/js-sheets-common/dist/gc.spread.common.resources.ko.js").SR.ko);
    GC.Spread.Common.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Common = extend2({}, moduleResources);
}
if ( GC.Spread.CalcEngine && GC.Spread.CalcEngine.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.ko.js */ "./node_modules_local/@spreadjs/js-calc/dist/gc.spread.calcengine.resources.ko.js").SR.ko);
    GC.Spread.CalcEngine.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].CalcEngine = extend2({}, moduleResources);
}
if ( GC.Data && GC.Data.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.ko.js */ "./node_modules_local/@spreadjs/js-data-manager/dist/gc.data.resources.ko.js").SR.ko);
    GC.Data.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Data = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets && GC.Spread.Sheets.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/core/core.res.ko.js */ "./dist/core/core.res.ko.js");
    GC.Spread.Sheets.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Sheets = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Bindings && GC.Spread.Sheets.Bindings.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/data/data.res.ko.js */ "./dist/plugins/data/data.res.ko.js");
    GC.Spread.Sheets.Bindings.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Bindings = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Outlines && GC.Spread.Sheets.Outlines.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/group/group.res.ko.js */ "./dist/plugins/group/group.res.ko.js");
    GC.Spread.Sheets.Outlines.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Outlines = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.ConditionalFormatting && GC.Spread.Sheets.ConditionalFormatting.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/conditional/conditional.res.ko.js */ "./dist/plugins/conditional/conditional.res.ko.js");
    GC.Spread.Sheets.ConditionalFormatting.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].ConditionalFormatting = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Touch && GC.Spread.Sheets.Touch.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/touch/touch.res.ko.js */ "./dist/plugins/touch/touch.res.ko.js");
    GC.Spread.Sheets.Touch.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Touch = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.FloatingObjects && GC.Spread.Sheets.FloatingObjects.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/floatingObject/floatingobject.res.ko.js */ "./dist/plugins/floatingObject/floatingobject.res.ko.js");
    GC.Spread.Sheets.FloatingObjects.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].FloatingObjects = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.CellTypes && GC.Spread.Sheets.CellTypes.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/celltype/celltypes.res.ko.js */ "./dist/plugins/celltype/celltypes.res.ko.js");
    GC.Spread.Sheets.CellTypes.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].CellTypes = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Filter && GC.Spread.Sheets.Filter.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/filter/filter.res.ko.js */ "./dist/plugins/filter/filter.res.ko.js");
    GC.Spread.Sheets.Filter.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Filter = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Tables && GC.Spread.Sheets.Tables.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/table/table.res.ko.js */ "./dist/plugins/table/table.res.ko.js");
    GC.Spread.Sheets.Tables.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Tables = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Fill && GC.Spread.Sheets.Fill.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/fill/fill.res.ko.js */ "./dist/plugins/fill/fill.res.ko.js");
    GC.Spread.Sheets.Fill.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Fill = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.ContextMenu && GC.Spread.Sheets.ContextMenu.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/contextMenu/context-menu.res.ko.js */ "./dist/plugins/contextMenu/context-menu.res.ko.js");
    GC.Spread.Sheets.ContextMenu.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].ContextMenu = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.OutlineColumn && GC.Spread.Sheets.OutlineColumn.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/outlineColumn/outlineColumn.res.ko.js */ "./dist/plugins/outlineColumn/outlineColumn.res.ko.js");
    GC.Spread.Sheets.OutlineColumn.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].OutlineColumn = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.StatusBar && GC.Spread.Sheets.StatusBar.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/statusBar/statusBar.res.ko.js */ "./dist/plugins/statusBar/statusBar.res.ko.js");
    GC.Spread.Sheets.StatusBar.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].StatusBar = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.AutoMerge && GC.Spread.Sheets.AutoMerge.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/autoMerge/autoMerge.res.ko.js */ "./dist/plugins/autoMerge/autoMerge.res.ko.js");
    GC.Spread.Sheets.AutoMerge.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].AutoMerge = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.NameBox && GC.Spread.Sheets.NameBox.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/namebox/namebox.res.ko.js */ "./dist/plugins/namebox/namebox.res.ko.js");
    GC.Spread.Sheets.NameBox.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].NameBox = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.InputMask && GC.Spread.Sheets.InputMask.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/inputMask/inputMask/inputMask.res.ko.js */ "./dist/plugins/inputMask/inputMask/inputMask.res.ko.js");
    GC.Spread.Sheets.InputMask.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].InputMask = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Print && GC.Spread.Sheets.Print.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/print/print.res.ko.js */ "./dist/plugins/print/print.res.ko.js");
    GC.Spread.Sheets.Print.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Print = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Charts && GC.Spread.Sheets.Charts.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/chart/chart.res.ko.js */ "./dist/plugins/chart/chart.res.ko.js");
    GC.Spread.Sheets.Charts.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Charts = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Charts && GC.Spread.Sheets.Charts.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/chart/chart.res.ko.js */ "./dist/plugins/chart/chart.res.ko.js");
    GC.Spread.Sheets.Charts.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Charts = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.PDF && GC.Spread.Sheets.PDF.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/exportPDF/printPdf.res.ko.js */ "./dist/plugins/exportPDF/printPdf.res.ko.js");
    GC.Spread.Sheets.PDF.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].PDF = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Shapes && GC.Spread.Sheets.Shapes.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/shape/shape.res.ko.js */ "./dist/plugins/shape/shape.res.ko.js");
    GC.Spread.Sheets.Shapes.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Shapes = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Slicers && GC.Spread.Sheets.Slicers.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/slicer/slicer.res.ko.js */ "./dist/plugins/slicer/slicer.res.ko.js");
    GC.Spread.Sheets.Slicers.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].Slicers = extend2({}, moduleResources);
}
if ( GC.Spread.Pivot && GC.Spread.Pivot.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/pivot/pivot.res.ko.js */ "./dist/plugins/pivot/pivot.res.ko.js");
    GC.Spread.Pivot.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].PivotTables = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.TableSheet && GC.Spread.Sheets.TableSheet.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/tableSheet/tableSheet.res.ko.js */ "./dist/plugins/tableSheet/tableSheet.res.ko.js");
    GC.Spread.Sheets.TableSheet.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].TableSheet = extend2({}, moduleResources);
}
if ( GC.Spread.Report && GC.Spread.Report.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/report/resources/res.ko.js */ "./dist/plugins/report/resources/res.ko.js");
    GC.Spread.Report.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].ReportSheet = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.GanttSheet && GC.Spread.Sheets.GanttSheet.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/gantt/gantt.res.ko.js */ "./dist/plugins/gantt/gantt.res.ko.js");
    GC.Spread.Sheets.GanttSheet.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].GanttSheet = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.IO && GC.Spread.Sheets.IO.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/io/io.res.ko.js */ "./dist/plugins/io/io.res.ko.js");
    GC.Spread.Sheets.IO.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].IO = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.FormulaPanel && GC.Spread.Sheets.FormulaPanel.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/formulaPanel/editor/editor.res.ko.js */ "./dist/plugins/formulaPanel/editor/editor.res.ko.js");
    GC.Spread.Sheets.FormulaPanel.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ko-ka"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ko-ka"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].FormulaPanel = extend2({}, moduleResources);
}
if ( GC.Pivot && GC.Pivot.SR) {
    var moduleResources = (__webpack_require__(/*! ./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.ko.js */ "./node_modules_local/@spreadjs/js-pivot/dist/gc.pivot.resources.ko.js").SR.ko);
    GC.Pivot.SR["ko"] = extend({}, GC.Spread.Common.SR["ko"] || {}, moduleResources);
    GC.Spread.Common.CultureManager._resourcesMap["ko-ka"].PivotEngine = extend2({}, moduleResources);
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
/******/ 	var __webpack_exports__ = __webpack_require__("./resource.ko.entry.js");
/******/ 	(GC = typeof GC === "undefined" ? {} : GC).Spread = __webpack_exports__;
/******/ 	
/******/ })()
;
//# sourceMappingURL=gc.spread.sheets.resources.ko.17.1.3.js.map
}));