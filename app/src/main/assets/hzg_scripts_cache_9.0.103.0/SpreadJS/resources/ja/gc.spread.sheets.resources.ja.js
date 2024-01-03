/*!
 * 
 * SpreadJS Library 15.2.7
 * 
 * Copyright(c) GrapeCity, Inc.  All rights reserved.
 * 
 * Licensed under the SpreadJS Commercial License.
 * us.sales@grapecity.com
 * http://www.grapecity.com/en/licensing/grapecity/
 * 
 * 
 */

(function (factory) {
   if(typeof module === 'object' && typeof module.exports === 'object') {
       module.exports = factory(require('@grapecity/spread-sheets'));
   } else if(typeof define === 'function' && define.amd) {
       define(['@grapecity/spread-sheets'], factory)
   } else if(typeof exports === 'object') {
       exports['Spread'] = factory(require('@grapecity/spread-sheets'));
   } else {
       factory(GC);
   }
}(function (GC) {
GC = typeof GC === "object" ? GC : {}; GC["Spread"] =
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./resource.ja.entry.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./dist/core/core.res.ja.js":
/*!**********************************!*\
  !*** ./dist/core/core.res.ja.js ***!
  \**********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";





Object.defineProperty(exports, "__esModule", { value: true });
var Core_1 = __webpack_require__(/*! Core */ "Core");
var Common_1 = __webpack_require__(/*! Common */ "Common");
var elements = Core_1.GC$('meta[name=\'spreadjs culture\']');
if (elements.length > 0) {
    var culture = Core_1.GC$(elements[elements.length - 1]).attr('content');
    if (culture !== null && culture !== undefined && culture.toLowerCase() === 'ja-jp') {
        Common_1.Common.CultureManager.culture('ja-jp');
    }
}
exports.Exp_NotSupported = '\u4f8b\u5916:\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u6a5f\u80fd\u306e\u5229\u7528\u3092\u8a66\u307f\u307e\u3057\u305f\u3002';
exports.Exp_PasteExtentIsNull = 'pasteExtent \u304c null \u3067\u3059';
exports.Exp_InvalidPastedArea = '\u8cbc\u308a\u4ed8\u3051\u9818\u57df\u306f\u30b3\u30d4\u30fc/\u5207\u308a\u53d6\u308a\u7bc4\u56f2\u3068\u540c\u30b5\u30a4\u30ba\u3067\u3042\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002';
exports.Exp_MultipleSelections = "\u3053\u306e\u64cd\u4f5c\u306f\u8907\u6570\u9078\u629e\u3067\u306f\u6a5f\u80fd\u3057\u307e\u305b\u3093\u3002";
exports.Exp_ChangePartOfArray = '\u914d\u5217\u306e\u4e00\u90e8\u3092\u5909\u66f4\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_InvalidAndSpace = '\u7121\u52b9\u306a {0}: {1} \u306f {2} \u304b\u3089 {3}\u306e\u9593\u3067\u3042\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002';
exports.Exp_SrcIsNull = '\u5f15\u6570 \'src\' \u306f null \u3067\u3059';
exports.Exp_DestIsNull = '\u5f15\u6570 \'dest\' \u306f null \u3067\u3059';
exports.Exp_InvalidCustomFunction = '\u7121\u52b9\u306a\u30ab\u30b9\u30bf\u30e0\u95a2\u6570';
exports.Exp_InvalidCustomName = '\u7121\u52b9\u306a\u540d\u524d';
exports.Exp_IndexOutOfRange = '\u7bc4\u56f2\u5916\u306e\u30a4\u30f3\u30c7\u30c3\u30af\u30b9\u3067\u3059!';
exports.Exp_InvalidRange = '\u7121\u52b9\u306a\u7bc4\u56f2';
exports.Exp_NotAFunction = '{0}\u306f\u95a2\u6570\u3067\u306f\u3042\u308a\u307e\u305b\u3093';
exports.Exp_ArgumentOutOfRange = '\u7bc4\u56f2\u5916\u306e\u5f15\u6570';
exports.Exp_PasteSourceCellsLocked = '\u53c2\u7167\u5143\u3068\u306a\u3063\u3066\u3044\u308b\u30b7\u30fc\u30c8\u306e\u30bb\u30eb\u306f\u30ed\u30c3\u30af\u3055\u308c\u3066\u3044\u307e\u3059\u3002';
exports.Exp_InvalidCopyPasteSize = '\u30b3\u30d4\u30fc\u3068\u8cbc\u308a\u4ed8\u3051\u306e\u7bc4\u56f2\u30b5\u30a4\u30ba\u304c\u7570\u306a\u3063\u3066\u3044\u307e\u3059\u3002';
exports.Exp_PasteDestinationCellsLocked = '\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u30bb\u30eb\u306f\u4fdd\u8b77\u3055\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u8aad\u307f\u53d6\u308a\u5c02\u7528\u3068\u306a\u3063\u3066\u3044\u307e\u3059\u3002';
exports.Exp_PasteChangeMergeCell = '\u7d50\u5408\u3057\u305f\u30bb\u30eb\u306e\u4e00\u90e8\u306f\u5909\u66f4\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Tip_Row = '\u884c: ';
exports.Tip_Column = '\u5217: ';
exports.Tip_Height = '\u9ad8\u3055: {0} \u30d4\u30af\u30bb\u30eb';
exports.Tip_Width = '\u5e45: {0} \u30d4\u30af\u30bb\u30eb';
exports.NewTab = 'New...';
exports.Exp_EmptyNamedStyle = '\u540d\u524d\u4ed8\u304d\u30b9\u30bf\u30a4\u30eb\u306e\u540d\u79f0\u3092\u7a7a\u306b\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_SheetNameInvalid = '\u30b7\u30fc\u30c8\u540d\u3092\u7a7a\u306b\u8a2d\u5b9a\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002\u307e\u305f\u6b21\u306e\u6587\u5b57\u3092\u30b7\u30fc\u30c8\u540d\u306b\u4f7f\u7528\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093 : *, :, [, ], ?, \\, /';
exports.Exp_SheetNameConflict = '\u3053\u306e\u540d\u524d\u306f\u65e2\u306b\u4f7f\u7528\u3055\u308c\u3066\u3044\u307e\u3059\u3002\u5225\u306e\u540d\u524d\u3092\u5165\u529b\u3057\u3066\u304f\u3060\u3055\u3044\u3002';
exports.Exp_ArrayFormulaSpan = '\u914d\u5217\u6570\u5f0f\u306f\u3001\u7d50\u5408\u3057\u305f\u30bb\u30eb\u3067\u306f\u7121\u52b9\u3067\u3059\u3002';
exports.Exp_DestSheetIsNull = 'destSheet \u304c null \u3067\u3059\u3002';
exports.Exp_SheetIsNull = 'sheet \u304c null\u3067\u3059\u3002';
exports.Exp_OverlappingSpans = '\u3053\u306e\u64cd\u4f5c\u306f\u7d50\u5408\u90e8\u5206\u306e\u91cd\u8907\u3092\u5f15\u304d\u8d77\u3053\u3057\u307e\u3059\u3002';
exports.NeedCanvasSupport = 'HTML5 Canvas\u306b\u5bfe\u5fdc\u3057\u305f\u30d6\u30e9\u30a6\u30b6\u3092\u3054\u5229\u7528\u304f\u3060\u3055\u3044\u3002';
exports.ARIA_Resize = '\u30bf\u30d6\u30b9\u30c8\u30ea\u30c3\u30d7\u306e\u30ea\u30b5\u30a4\u30ba';
exports.ARIA_First = '\u30bf\u30d6\u30b9\u30c8\u30ea\u30c3\u30d7\u306e\u5148\u982d';
exports.ARIA_PreviousArrow = '\u524d\u3078\u306e\u77e2\u5370\u30dc\u30bf\u30f3';
exports.ARIA_NextArrow = '\u6b21\u3078\u306e\u77e2\u5370\u30dc\u30bf\u30f3';
exports.ARIA_Last = '\u30bf\u30d6\u30b9\u30c8\u30ea\u30c3\u30d7\u306e\u6700\u5f8c';
exports.ARIA_PreviousButton = '\u524d\u3078\u306e\u30dc\u30bf\u30f3';
exports.ARIA_NextButton = '\u6b21\u3078\u306e\u30dc\u30bf\u30f3';
exports.ARIA_SheetTab = '\u30b7\u30fc\u30c8\u30bf\u30d6';
exports.ARIA_NewSheet = '\u65b0\u3057\u3044\u30b7\u30fc\u30c8';
exports.ARIA_Blank = '\u30bf\u30d6\u30b9\u30c8\u30ea\u30c3\u30d7\u306e\u30d6\u30e9\u30f3\u30af\u9818\u57df';
exports.ARIA_Scrollbar_Left_Button = '\u30b9\u30af\u30ed\u30fc\u30eb\u30d0\u30fc\u306e\u5de6\u30dc\u30bf\u30f3';
exports.ARIA_Scrollbar_Top_Button = '\u30b9\u30af\u30ed\u30fc\u30eb\u30d0\u30fc\u306e\u4e0a\u30dc\u30bf\u30f3';
exports.ARIA_Scrollbar_Thumb_Button = '\u30b9\u30af\u30ed\u30fc\u30eb\u30d0\u30fc\u306e\u30b5\u30e0';
exports.ARIA_Scrollbar_Right_Button = '\u30b9\u30af\u30ed\u30fc\u30eb\u30d0\u30fc\u306e\u53f3\u30dc\u30bf\u30f3';
exports.ARIA_Scrollbar_Bottom_Button = '\u30b9\u30af\u30ed\u30fc\u30eb\u30d0\u30fc\u306e\u4e0b\u30dc\u30bf\u30f3';
exports.ARIA_Scrollbar_TRACK_Button = '\u30b9\u30af\u30ed\u30fc\u30eb\u30d0\u30fc\u306e\u30c8\u30e9\u30c3\u30af';
exports.ARIA_Cell = '\u30bb\u30eb';
exports.ARIA_HasValue = '\u306e\u5024\u306f{0}\u3067\u3059';
exports.ARIA_HasFormula = '\u306e\u6570\u5f0f\u306f{0}\u3067\u3059';
exports.ARIA_HasComment = '\u306e\u30b3\u30e1\u30f3\u30c8\u306f{0}\u3067\u3059';
exports.ARIA_RowHeader = '\u884c\u30d8\u30c3\u30c0';
exports.ARIA_ColumnHeader = '\u5217\u30d8\u30c3\u30c0';
exports.SHEET_NAME = 'Sheet';

exports.ls1 = ['30303030303030685f4f7900000000000000000000673030303030723030527530303030307458303030529630303030303030008a4f3076763030303030303030304e667630915e3030698a30585430005f795569903030768a3030303030',
    'b0ecfcd7b7c6a32a0f1a3e205370726561644a530a2cc8e9a4a2eb486e5429286fedfcabebb0836e7f6b3650558c66447e59020a55a192ee8468575fb5fcd0fc786e0042846a4d0392541c0e6e34086f0a0a3eb66de86b54f8c74f60554402'];
exports.ls1a = '1';

exports.ls2 = ['30303030303030685f4f790000000000000000000000303030303030303067526795306b30000000000065303030',
    'b0ecfcd7b7c6a32a0f1a3e205370726561644a53200ac8e9a4a2ebadfc6e09b91f936f8b8a207b307d20e5675902'];

exports.ls3 = ['30303030306058308930303030303030303030008854303052753030303030303030300030303030303030305f89303000',
    'e9a4bbf3b9c5314c8b644b8a7e5b9367575f020afdc16e5429286b6fe9a4bbf3b9adfc2fc8e9a4a2ebadfc4cc58167590a'];

exports.ls4 = ['6b30303030303030605830535f303030303030303030',
    '635744e9a4bbf3b9c53192d697674d7e5b9367575f02'];

exports.ls5 = ['30303030303030685f4f790000000000000000000000303030303030303067526795307d4e3030303030',
    'b0ecfcd7b7c6a32a0f1a3e205370726561644a53200ac8e9a4a2ebadfc6e09b91f934c4286577e575f02'];
exports.ls6 = ['30303030303030685f4f790000000000000000003030303030720067303030303030915e30796b303030303030',
    'b0ecfcd7b7c6a32a0f1a3e205370726561644a53c8e9a4a2eb480a2cd0fcb8e7f36e4d036f8162558c66447e59'];

exports.ls7 = ['30303030306058308930303030303030303030008854303052753030303030303030300030303030303030305f89303000',
    'e9a4bbf3b9c5314c8b644b8a7e5b9367575f020afdc16e5429286b6fe9a4bbf3b9adfc2fc8e9a4a2ebadfc4cc58167590a'];

exports.ls8 = ['6b30303030303030605830535f303030303030303030',
    '635744e9a4bbf3b9c53192d697674d7e5b9367575f02'];
exports.Exp_InsertCopiedCutCells = "\u30b3\u30d4\u30fc\u30a2\u30f3\u30c9\u30da\u30fc\u30b9\u30c8\u9818\u57df\u304c\u91cd\u306a\u3089\u306a\u3044\u3088\u3046\u306b\u3059\u308b.";
exports.Exp_InsertCopiedCutCellsOnSpanTable = "\u3053\u308c\u306f\u3001\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u306e\u30c6\u30fc\u30d6\u30eb\u5185\u306e\u30bb\u30eb\u3092\u79fb\u52d5\u3057\u305f\u308a\u3001\u4e00\u90e8\u306e\u7d50\u5408\u3055\u308c\u305f\u30bb\u30eb\u306e\u7d50\u5408\u3092\u89e3\u9664\u3057\u305f\u308a\u3059\u308b\u305f\u3081\u3001\u6a5f\u80fd\u3057\u307e\u305b\u3093\u3002";
exports.Exp_InsertCopiedCutCellsNoRange = "SpreadJS\u306f\u3001\u7a7a\u3067\u306a\u3044\u30bb\u30eb\u3092\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u306e\u7aef\u304b\u3089\u30dd\u30c3\u30d7\u3059\u308b\u305f\u3081\u3001\u65b0\u305f\u306a\u30bb\u30eb\u3092\u633f\u5165\u3059\u308b\u3053\u3068\u304c\u3067\u304d\u307e\u305b\u3093\u3002";
exports.Exp_InvalidSortArrayFormulaInRange = "\u914d\u5217\u6570\u5f0f\u304c\u5b58\u5728\u3059\u308b\u305f\u3081\u3001\u73fe\u5728\u306e\u7bc4\u56f2\u3092\u30bd\u30fc\u30c8\u3067\u304d\u307e\u305b\u3093.";
exports.Exp_InvalidSortSpanInRange = "\u7d50\u5408\u3055\u308c\u305f\u30bb\u30eb\u304c\u5b58\u5728\u3059\u308b\u305f\u3081\u3001\u73fe\u5728\u306e\u7bc4\u56f2\u3092\u30bd\u30fc\u30c8\u3067\u304d\u307e\u305b\u3093.";
exports.Exp_InvalidSortPartTableOrMultiTableInRange = "\u30c6\u30fc\u30d6\u30eb\u306e\u4e00\u90e8\u307e\u305f\u306f\u8907\u6570\u306e\u30c6\u30fc\u30d6\u30eb\u304c\u5b58\u5728\u3059\u308b\u305f\u3081\u3001\u73fe\u5728\u306e\u7bc4\u56f2\u3092\u30bd\u30fc\u30c8\u3067\u304d\u307e\u305b\u3093.";
exports.Exp_InvalidOperationInProtect = "\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u30bb\u30eb\u307e\u305f\u306f\u30c1\u30e3\u30fc\u30c8\u306f\u3001\u4fdd\u8b77\u3055\u308c\u305f\u30b7\u30fc\u30c8\u4e0a\u306b\u3042\u308a\u307e\u3059\u3002\u5909\u66f4\u3059\u308b\u306b\u306f\u3001\u30b7\u30fc\u30c8\u306e\u4fdd\u8b77\u3092\u89e3\u9664\u3057\u3066\u304f\u3060\u3055\u3044\u3002";


/***/ }),

/***/ "./dist/plugins/autoMerge/autoMerge.res.ja.js":
/*!****************************************************!*\
  !*** ./dist/plugins/autoMerge/autoMerge.res.ja.js ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_RangeIsIntersected = "\u7bc4\u56f2\u306f\u65e2\u5b58\u306e\u7bc4\u56f2\u3068\u63a5\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002";


/***/ }),

/***/ "./dist/plugins/celltype/celltypes.res.ja.js":
/*!***************************************************!*\
  !*** ./dist/plugins/celltype/celltypes.res.ja.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.OK = 'OK';
exports.Cancel = '\u30ad\u30e3\u30f3\u30bb\u30eb';
exports.Calendar_ShortWeeks_1 = "\u6708";
exports.Calendar_ShortWeeks_2 = "\u706b";
exports.Calendar_ShortWeeks_3 = "\u6c34";
exports.Calendar_ShortWeeks_4 = "\u6728";
exports.Calendar_ShortWeeks_5 = "\u91d1";
exports.Calendar_ShortWeeks_6 = "\u571f";
exports.Calendar_ShortWeeks_7 = "\u65e5";
exports.Calendar_Weeks_1 = "\u6708\u66dc\u65e5";
exports.Calendar_Weeks_2 = "\u706b\u66dc\u65e5";
exports.Calendar_Weeks_3 = "\u6c34\u66dc\u65e5";
exports.Calendar_Weeks_4 = "\u6728\u66dc\u65e5";
exports.Calendar_Weeks_5 = "\u91d1\u66dc\u65e5";
exports.Calendar_Weeks_6 = "\u571f\u66dc\u65e5";
exports.Calendar_Weeks_7 = "\u65e5\u66dc\u65e5";
exports.Calendar_ShortMonths_1 = "1\u6708";
exports.Calendar_ShortMonths_2 = "2\u6708";
exports.Calendar_ShortMonths_3 = "3\u6708";
exports.Calendar_ShortMonths_4 = "4\u6708";
exports.Calendar_ShortMonths_5 = "5\u6708";
exports.Calendar_ShortMonths_6 = "6\u6708";
exports.Calendar_ShortMonths_7 = "7\u6708";
exports.Calendar_ShortMonths_8 = "8\u6708";
exports.Calendar_ShortMonths_9 = "9\u6708";
exports.Calendar_ShortMonths_10 = "10\u6708";
exports.Calendar_ShortMonths_11 = "11\u6708";
exports.Calendar_ShortMonths_12 = "12\u6708";
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
exports.Calendar_Time_AM = "\u5348\u524d";
exports.Calendar_Time_PM = "\u5348\u5f8c";
exports.Calendar_EraName_1 = "\u660e\u6cbb";
exports.Calendar_EraName_2 = "\u5927\u6b63";
exports.Calendar_EraName_3 = "\u662d\u548c";
exports.Calendar_EraName_4 = "\u5e73\u6210";
exports.Calendar_FirstYear = "\u5143";
exports.Calendar_Today = "\u4eca\u65e5";
exports.Calendar_LastMonth = "\u524d\u306e\u6708";
exports.Calendar_NextMonth = "\u6b21\u306e\u6708";
exports.Calendar_LastYear = "\u524d\u306e\u5e74";
exports.Calendar_NextYear = "\u6b21\u306e\u5e74";
exports.Calendar_LastTenYear = "\u524d\u306e10\u5e74";
exports.Calendar_NextTenYear = "\u6b21\u306e10\u5e74";
exports.Quarter_Format_1 = "\u7b2c1\u56db\u534a\u671f";
exports.Quarter_Format_2 = "\u7b2c2\u56db\u534a\u671f";
exports.Quarter_Format_3 = "\u7b2c3\u56db\u534a\u671f";
exports.Quarter_Format_4 = "\u7b2c4\u56db\u534a\u671f";
exports.ThemeColor = "\u30c6\u30fc\u30de\u306e\u8272";
exports.StandardColor = "\u6a19\u6e96\u306e\u8272";
exports.Calculator_DivideByZeroInfo = "\u30bc\u30ed\u3067\u5272\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002";
exports.Calculator_InvalidInputInfo = "\u7121\u52b9\u306a\u5165\u529b\u3067\u3059\u3002";
exports.Calculator_SqrtParameterException = "\u7121\u52b9\u306a\u5165\u529b\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u3067\u3059\u3002";
exports.Calculator_OverFlowInfo = "\u7b97\u8853\u6f14\u7b97\u306b\u3088\u308a\u30aa\u30fc\u30d0\u30fc\u30d5\u30ed\u30fc\u304c\u767a\u751f\u3057\u307e\u3057\u305f\u3002";
exports.MultiColumn_InvalidDataSource = "\u7121\u52b9\u306a\u30c7\u30fc\u30bf\u30bd\u30fc\u30b9\u3067\u3059\u3002\u5f0f\u306f\u914d\u5217\u3092\u8fd4\u3059\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002";
exports.DataObject_MeetLock = "\u30ed\u30c3\u30af\u3055\u308c\u305f\u30bb\u30eb\u304c\u3042\u308b\u305f\u3081\u3001\u30c7\u30fc\u30bf\u3092\u633f\u5165\u3067\u304d\u307e\u305b\u3093\u3002";
exports.DataObject_MeetSpan = "\u7d50\u5408\u3055\u308c\u305f\u30bb\u30eb\u304c\u3042\u308b\u305f\u3081\u3001\u30c7\u30fc\u30bf\u3092\u633f\u5165\u3067\u304d\u307e\u305b\u3093\u3002";
exports.DataObject_MeetTable = "\u30c6\u30fc\u30d6\u30eb\u304c\u3042\u308b\u305f\u3081\u3001\u30c7\u30fc\u30bf\u3092\u633f\u5165\u3067\u304d\u307e\u305b\u3093\u3002";


/***/ }),

/***/ "./dist/plugins/chart/chart.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/chart/chart.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.period = '\u671f\u9593';
exports.movingAverage = '\u79fb\u52d5\u5e73\u5747';
exports.exponential = '\u6307\u6570';
exports.linear = '\u7dda\u5f62';
exports.logarithmic = '\u5bfe\u6570';
exports.polynomial = '\u591a\u9805\u5f0f';
exports.power = '\u3079\u304d\u4e57';


/***/ }),

/***/ "./dist/plugins/conditional/conditional.res.ja.js":
/*!********************************************************!*\
  !*** ./dist/plugins/conditional/conditional.res.ja.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_RuleIsNull = '\u5f15\u6570 \'rule\' \u304c null \u3067\u3059';
exports.Exp_NotSupported = '\u4f8b\u5916:\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u6a5f\u80fd\u306e\u5229\u7528\u3092\u8a66\u307f\u307e\u3057\u305f';


/***/ }),

/***/ "./dist/plugins/contextMenu/context-menu.res.ja.js":
/*!*********************************************************!*\
  !*** ./dist/plugins/contextMenu/context-menu.res.ja.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.copy = '\u30b3\u30d4\u30fc';
exports.cut = '\u5207\u308a\u53d6\u308a';
exports.pasteOptions = '\u8cbc\u308a\u4ed8\u3051\u306e\u30aa\u30d7\u30b7\u30e7\u30f3:';
exports.pasteAll = '\u3059\u3079\u3066';
exports.pasteFormula = '\u6570\u5f0f';
exports.pasteValues = '\u5024';
exports.pasteFormatting = '\u66f8\u5f0f';
exports.pasteValuesFormatting = '\u5024&\u66f8\u5f0f';
exports.pasteFormulaFormatting = '\u6570\u5f0f&\u66f8\u5f0f';
exports.clearContents = '\u6570\u5f0f\u3068\u5024\u306e\u30af\u30ea\u30a2';
exports.insertRows = "\u633f\u5165";
exports.insertColumns = "\u633f\u5165";
exports.deleteRows = "\u524a\u9664";
exports.deleteColumns = "\u524a\u9664";
exports.insertSheet = '\u633f\u5165';
exports.deleteSheet = '\u524a\u9664';
exports.insertComment = '\u30b3\u30e1\u30f3\u30c8\u306e\u633f\u5165';
exports.filter = '\u30d5\u30a3\u30eb\u30bf\u30ea\u30f3\u30b0 ';
exports.sort = '\u30bd\u30fc\u30c8';
exports.insertCopiedCells = '\u30b3\u30d4\u30fc\u3057\u305f\u30bb\u30eb\u306e\u633f\u5165...';
exports.insertCutCells = '\u5207\u308a\u53d6\u3063\u305f\u30bb\u30eb\u306e\u633f\u5165...';
exports.shiftCellsRight = ' \u53f3\u65b9\u5411\u306b\u30b7\u30d5\u30c8';
exports.shiftCellsDown = '\u4e0b\u65b9\u5411\u306b\u30b7\u30d5\u30c8';
exports.headerInsertCopiedCells = '\u30b3\u30d4\u30fc\u3057\u305f\u30bb\u30eb\u306e\u633f\u5165';
exports.headerInsertCutCells = '\u5207\u308a\u53d6\u3063\u305f\u30bb\u30eb\u306e\u633f\u5165';
exports.slicerSortAscend = "\u6607\u9806";
exports.slicerSortDescend = "\u964d\u9806";
exports.sortAscend = '\u6607\u9806';
exports.sortDescend = '\u964d\u9806';
exports.hideRows = "\u975e\u8868\u793a";
exports.hideColumns = "\u975e\u8868\u793a";
exports.hideSheet = '\u975e\u8868\u793a';
exports.unhideSheet = '\u518d\u8868\u793a';
exports.unhideColumns = "\u518d\u8868\u793a";
exports.unhideRows = "\u518d\u8868\u793a";
exports.editComment = '\u30b3\u30e1\u30f3\u30c8\u306e\u7de8\u96c6';
exports.deleteComment = '\u30b3\u30e1\u30f3\u30c8\u306e\u524a\u9664';
exports.toggleComment = '\u30b3\u30e1\u30f3\u30c8\u306e\u8868\u793a/\u975e\u8868\u793a';
exports.removeSlicer = '\u524a\u9664';
exports.removeFloatingObject = '\u524a\u9664';
exports.tableInsert = "\u633f\u5165";
exports.tableInsertRowsAbove = "\u30c6\u30fc\u30d6\u30eb\u306e\u884c\uff08\u4e0a\uff09";
exports.tableInsertRowsBelow = "\u30c6\u30fc\u30d6\u30eb\u306e\u884c\uff08\u4e0b\uff09";
exports.tableInsertColumnsLeft = "\u30c6\u30fc\u30d6\u30eb\u306e\u5217\uff08\u5de6\uff09";
exports.tableInsertColumnsRight = "\u30c6\u30fc\u30d6\u30eb\u306e\u5217\uff08\u53f3\uff09";
exports.tableDelete = "\u524a\u9664";
exports.tableDeleteRows = "\u30c6\u30fc\u30d6\u30eb\u306e\u884c";
exports.tableDeleteColumns = "\u30c6\u30fc\u30d6\u30eb\u306e\u5217";
exports.hideSheetFailureInfo = "\u30ef\u30fc\u30af\u30d6\u30c3\u30af\u306b\u306f\u5c11\u306a\u304f\u3068\u30821\u3064\u306e\u8868\u793a\u53ef\u80fd\u306a\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u304c\u542b\u307e\u308c\u3066\u3044\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\n\u9078\u629e\u3057\u305f\u30b7\u30fc\u30c8\u3092\u975e\u8868\u793a\u3001\u524a\u9664\u3001\u307e\u305f\u306f\u79fb\u52d5\u3059\u308b\u306b\u306f\u3001\u307e\u305a\u65b0\u3057\u3044\u30b7\u30fc\u30c8\u3092\u633f\u5165\u3059\u308b\u304b\u3001\u3059\u3067\u306b\u975e\u8868\u793a\u306b\u306a\u3063\u3066\u3044\u308b\u30b7\u30fc\u30c8\u3092\u518d\u8868\u793a\u3059\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059";
exports.pivotTableRefresh = "\u66f4\u65b0";
exports.pivotTableMove = "\u79fb\u52d5";
exports.pivotTableRemove_ = "{0} \u306e\u524a\u9664";
exports.pivotTableRemoveGrandTotal = "\u7dcf\u8a08\u306e\u524a\u9664";
exports.pivotTableValueFieldSettings = "\u5024\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8a2d\u5b9a...";
exports.pivotTableOptions = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb \u30aa\u30d7\u30b7\u30e7\u30f3...";
exports.pivotTableNumberFormat = "\u8ca0\u306e\u6570\u306e\u8868\u793a\u5f62\u5f0f...";
exports.pivotTableSort = "\u4e26\u3079\u66ff\u3048";
exports.pivotTableSummarizeValuesBy = "\u96c6\u8a08\u65b9\u6cd5";
exports.pivotTableSum = "\u5408\u8a08";
exports.pivotTableCount = "\u30c7\u30fc\u30bf\u306e\u500b\u6570";
exports.pivotTableAverage = "\u5e73\u5747";
exports.pivotTableMax = "\u6700\u5927\u5024";
exports.pivotTableMin = "\u6700\u5c0f\u5024";
exports.pivotTableProduct = "\u7a4d";
exports.pivotTableDistinctCount = "\u500b\u5225\u306e\u30ab\u30a6\u30f3\u30c8";
exports.pivotTableMoreOptions = "\u305d\u306e\u4ed6\u306e\u30aa\u30d7\u30b7\u30e7\u30f3";
exports.pivotTableShowValueAs = "\u8a08\u7b97\u306e\u7a2e\u985e";
exports.pivotTableNoCalculation = "\u8a08\u7b97\u306a\u3057";
exports.pivotTableGrandTotal = "\u7dcf\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.pivotTableColumnTotal = "\u5217\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.pivotTableRowTotal = "\u884c\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.pivotTableOf = "\u57fa\u6e96\u5024\u306b\u5bfe\u3059\u308b\u6bd4\u7387...";
exports.pivotTableParentRowTotal = "\u89aa\u884c\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.pivotTableParentColumnTotal = "\u89aa\u5217\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.pivotTableOfParentTotal = "\u89aa\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387...";
exports.pivotTableDifferenceFrom = "\u57fa\u6e96\u5024\u3068\u306e\u5dee\u5206...";
exports.pivotTablePercentDifferenceFrom = "\u57fa\u6e96\u5024\u3068\u306e\u5dee\u5206\u306e\u6bd4\u7387...";
exports.pivotTableRunningTotalIN = "\u7d2f\u8a08...";
exports.pivotTablePercentRunningTotalIn = "\u6bd4\u7387\u306e\u7d2f\u8a08...";
exports.pivotTableRankSmallestToLargest = "\u6607\u9806\u3067\u306e\u9806\u4f4d...";
exports.pivotTableRankLargestToSmallest = "\u964d\u9806\u3067\u306e\u9806\u4f4d...";
exports.pivotTableIndex = "\u6307\u6570 (\u30a4\u30f3\u30c7\u30c3\u30af\u30b9)";
exports.pivotTableShowDetails = "\u8a73\u7d30\u306e\u8868\u793a";
exports.pivotTableGroup = "\u30b0\u30eb\u30fc\u30d7\u5316...";
exports.pivotTableUnGroup = "\u30b0\u30eb\u30fc\u30d7\u89e3\u9664...";
exports.pivotTableExpandOrCollapse = "\u5c55\u958b/\u6298\u308a\u305f\u305f\u307f";
exports.pivotTableFilter = "\u30d5\u30a3\u30eb\u30bf\u30fc";
exports.pivotTableSubtotal_ = "{0} \u306e\u5c0f\u8a08";
exports.pivotTableHideFieldList = "\u30d5\u30a3\u30fc\u30eb\u30c9 \u30ea\u30b9\u30c8\u3092\u8868\u793a\u3057\u306a\u3044";
exports.pivotTableFieldSettings = "\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8a2d\u5b9a...";
exports.pivotTableClearFilterFrom_ = "{0} \u304b\u3089\u30d5\u30a3\u30eb\u30bf\u30fc\u3092\u30af\u30ea\u30a2";
exports.pivotTableKeepOnlySelectedItems = "\u9078\u629e\u3057\u305f\u9805\u76ee\u306e\u307f\u3092\u4fdd\u6301";
exports.pivotTableHideSelectedItems = "\u9078\u629e\u3057\u305f\u9805\u76ee\u3092\u8868\u793a\u3057\u306a\u3044";
exports.pivotTableTop10 = "\u30c8\u30c3\u30d7\u30c6\u30f3...";
exports.pivotTableLabelFilters = "\u30e9\u30d9\u30eb\u30d5\u30a3\u30eb\u30bf\u30fc...";
exports.pivotTableLabelFilters_Date = "\u65e5\u4ed8\u30d5\u30a3\u30eb\u30bf\u30fc...";
exports.pivotTableValueFilters = "\u5024\u30d5\u30a3\u30eb\u30bf\u30fc...";
exports.pivotTableExpand = "\u5c55\u958b";
exports.pivotTableCollapse = "\u6298\u308a\u305f\u305f\u307f";
exports.pivotTableExpandEntireField = "\u30d5\u30a3\u30fc\u30eb\u30c9\u5168\u4f53\u306e\u5c55\u958b";
exports.pivotTableCollapseEntireField = "\u30d5\u30a3\u30fc\u30eb\u30c9\u5168\u4f53\u306e\u6298\u308a\u305f\u305f\u307f";
exports.pivotTableCollapseTo_ = "{0} \u306e\u6298\u308a\u305f\u305f\u307f";
exports.pivotTableExpandTo_ = "{0} \u306e\u5c55\u958b";
exports.pivotTableMove_ToBeginning = "{0} \u3092\u5148\u982d\u3078\u79fb\u52d5";
exports.pivotTableMove_Up = "{0} \u3092\u4e0a\u3078\u79fb\u52d5";
exports.pivotTableMove_Down = "{0} \u3092\u4e0b\u3078\u79fb\u52d5";
exports.pivotTableMove_ToEnd = "{0} \u3092\u672b\u5c3e\u3078\u79fb\u52d5";
exports.pivotTableMove_ToLeft = "{0} \u3092\u5de6\u3078\u79fb\u52d5";
exports.pivotTableMove_ToRight = "{0} \u3092\u53f3\u3078\u79fb\u52d5";
exports.pivotTableMove_ToColumns = "{0} \u3092\u5217\u306b\u79fb\u52d5";
exports.sigmaValue = "\u2211\u5024";
exports.sigmaValueTemp = "\u5024";
exports.pivotTableAscend = '\u6700\u5c0f\u304b\u3089\u6700\u5927\u306e\u9806\u306b\u4e26\u3079\u66ff\u3048';
exports.pivotTableSortDescend = '\u6700\u5927\u304b\u3089\u6700\u5c0f\u306e\u9806\u306b\u4e26\u3079\u66ff\u3048';
exports.pinRows = "\u884c\u306e\u56fa\u5b9a/\u56fa\u5b9a\u306e\u89e3\u9664";
exports.pinColumns = "\u5217\u306e\u56fa\u5b9a/\u56fa\u5b9a\u306e\u89e3\u9664";
exports.paste = "\u8cbc\u308a\u4ed8\u3051";


/***/ }),

/***/ "./dist/plugins/data/data.res.ja.js":
/*!******************************************!*\
  !*** ./dist/plugins/data/data.res.ja.js ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_NotSupportedDataSource = '\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u30c7\u30fc\u30bf\u30bd\u30fc\u30b9\u3067\u3059\u3002';


/***/ }),

/***/ "./dist/plugins/exportPDF/printPdf.res.ja.js":
/*!***************************************************!*\
  !*** ./dist/plugins/exportPDF/printPdf.res.ja.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_FileIOError = '\u30d5\u30a1\u30a4\u30eb\u306e\u8aad\u307f\u53d6\u308a\u3068\u66f8\u304d\u8fbc\u307f\u306e\u4f8b\u5916\u3002';
exports.Exp_FontError = '\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u30d5\u30a9\u30f3\u30c8\u5f62\u5f0f\u307e\u305f\u306f\u6a19\u6e96\u306e PDF \u30d5\u30a9\u30f3\u30c8\u3002';


/***/ }),

/***/ "./dist/plugins/fill/fill.res.ja.js":
/*!******************************************!*\
  !*** ./dist/plugins/fill/fill.res.ja.js ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.CopyCells = '\u30bb\u30eb\u306e\u30b3\u30d4\u30fc';
exports.FillSeries = '\u9023\u7d9a\u30c7\u30fc\u30bf';
exports.FillFormattingOnly = '\u66f8\u5f0f\u306e\u307f\u30b3\u30d4\u30fc';
exports.FillWithoutFormatting = '\u66f8\u5f0f\u306a\u3057\u30b3\u30d4\u30fc';
exports.Exp_NumberOnly = '\u6570\u5b57\u306e\u307f\u6709\u52b9\u3067\u3059';
exports.Exp_RangeContainsMergedCell = '\u7d50\u5408\u3055\u308c\u305f\u30bb\u30eb\u304c\u7bc4\u56f2\u306b\u542b\u307e\u308c\u3066\u3044\u307e\u3059\u3002';
exports.Exp_TargetContainsMergedCells = '\u7d50\u5408\u3055\u308c\u305f\u30bb\u30eb\u304c\u6307\u5b9a\u306e\u7bc4\u56f2\u306b\u542b\u307e\u308c\u3066\u3044\u307e\u3059\u3002';
exports.Exp_MergedCellsIdentical = '\u3053\u306e\u64cd\u4f5c\u306b\u306f\u540c\u3058\u30b5\u30a4\u30ba\u306e\u7d50\u5408\u30bb\u30eb\u304c\u5fc5\u8981\u3067\u3059\u3002';
exports.Exp_FillRangeContainsMergedCell = '\u7d50\u5408\u3057\u305f\u30bb\u30eb\u304c\u542b\u307e\u308c\u308b\u7bc4\u56f2\u3092\u30d5\u30a3\u30eb\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_FillCellsReadOnly = '\u30d5\u30a3\u30eb\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u7bc4\u56f2\u306f\u4fdd\u8b77\u3055\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u8aad\u307f\u53d6\u308a\u5c02\u7528\u3068\u306a\u3063\u3066\u3044\u307e\u3059\u3002';
exports.Exp_ChangeMergedCell = '\u7d50\u5408\u3055\u308c\u305f\u30bb\u30eb\u306e\u4e00\u90e8\u3092\u5909\u66f4\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_ColumnReadOnly = '\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u5217\u306f\u4fdd\u8b77\u3055\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u8aad\u307f\u53d6\u308a\u5c02\u7528\u3068\u306a\u3063\u3066\u3044\u307e\u3059\u3002';
exports.Exp_RowReadOnly = '\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u884c\u306f\u4fdd\u8b77\u3055\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u8aad\u307f\u53d6\u308a\u5c02\u7528\u3068\u306a\u3063\u3066\u3044\u307e\u3059\u3002';
exports.Exp_CellReadOnly = '\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u30bb\u30eb\u306f\u4fdd\u8b77\u3055\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u8aad\u307f\u53d6\u308a\u5c02\u7528\u3068\u306a\u3063\u3066\u3044\u307e\u3059\u3002';
exports.Exp_RangeIsNull = '\u7bc4\u56f2\u304c null \u3067\u3059';
exports.Exp_ChangePartOfArray = '\u914d\u5217\u306e\u4e00\u90e8\u3092\u5909\u66f4\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';


/***/ }),

/***/ "./dist/plugins/filter/filter.res.ja.js":
/*!**********************************************!*\
  !*** ./dist/plugins/filter/filter.res.ja.js ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_InvalidColumnIndex = '\u7121\u52b9\u306a\u5217\u30a4\u30f3\u30c7\u30c3\u30af\u30b9\u3067\u3059\u3002';
exports.SortAscending = '\u6607\u9806';
exports.SortDescending = '\u964d\u9806';
exports.OK = 'OK';
exports.Cancel = '\u30ad\u30e3\u30f3\u30bb\u30eb';
exports.Search = '\u691c\u7d22';
exports.CheckAll = '\u3059\u3079\u3066\u9078\u629e';
exports.UncheckAll = '\u3059\u3079\u3066\u89e3\u9664';
exports.Blanks = '(\u7a7a\u767d\u30bb\u30eb)';
exports.Exp_FilterItemIsNull = '\u30d5\u30a3\u30eb\u30bf\u9805\u76ee\u304c null \u3067\u3059\u3002';
exports.Show = '\u8868\u793a';
exports.ShowRows = '\u62bd\u51fa\u6761\u4ef6\u306e\u6307\u5b9a :';
exports.And = 'AND';
exports.Or = 'OR';
exports.SortColor = '\u8272\u3067\u4e26\u3079\u66ff\u3048';
exports.FilterColor = '\u8272\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.FilterCellTitle = '\u30bb\u30eb\u306e\u8272\u3067\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.FilterFontTitle = '\u30d5\u30a9\u30f3\u30c8\u306e\u8272\u3067\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.SortCellTitle = '\u30bb\u30eb\u306e\u8272\u3067\u4e26\u3079\u66ff\u3048';
exports.SortFontTitle = '\u30d5\u30a9\u30f3\u30c8\u306e\u8272\u3067\u4e26\u3079\u66ff\u3048';
exports.FontColor = '\u305d\u306e\u4ed6\u306e\u30d5\u30a9\u30f3\u30c8\u306e\u8272...';
exports.CellColor = '\u305d\u306e\u4ed6\u306e\u30bb\u30eb\u306e\u8272...';
exports.NoFill = '\u5857\u308a\u3064\u3076\u3057\u306a\u3057';
exports.Automatic = '\u81ea\u52d5';
exports.Clear = '{0} \u304b\u3089\u30d5\u30a3\u30eb\u30bf\u30fc\u3092\u30af\u30ea\u30a2';
exports.TextFilter = '\u30c6\u30ad\u30b9\u30c8 \u30d5\u30a3\u30eb\u30bf\u30fc';
exports.DateFilter = '\u65e5\u4ed8\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.NumberFilter = '\u6570\u5024\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.Custom = '\u30e6\u30fc\u30b6\u30fc\u8a2d\u5b9a\u30d5\u30a3\u30eb\u30bf\u30fc...';
exports.Equal = '\u6307\u5b9a\u306e\u5024\u306b\u7b49\u3057\u3044...';
exports.NotEqual = '\u6307\u5b9a\u306e\u5024\u306b\u7b49\u3057\u304f\u306a\u3044...';
exports.GreaterThan = '\u6307\u5b9a\u306e\u5024\u3088\u308a\u5927\u304d\u3044...';
exports.GreaterOrEquals = '\u6307\u5b9a\u306e\u5024\u4ee5\u4e0a...';
exports.LessThan = '\u6307\u5b9a\u306e\u5024\u3088\u308a\u5c0f\u3055\u3044...';
exports.LessThanOrEquals = '\u6307\u5b9a\u306e\u5024\u4ee5\u4e0b...';
exports.Between = '\u6307\u5b9a\u306e\u7bc4\u56f2\u5185...';
exports.Top10 = '\u30c8\u30c3\u30d7\u30c6\u30f3...';
exports.AboveAverage = '\u5e73\u5747\u3088\u308a\u4e0a';
exports.BelowAverage = '\u5e73\u5747\u3088\u308a\u4e0b';
exports.Begin = '\u6307\u5b9a\u306e\u5024\u3067\u59cb\u307e\u308b...';
exports.End = '\u6307\u5b9a\u306e\u5024\u3067\u7d42\u308f\u308b...';
exports.Contain = '\u6307\u5b9a\u306e\u5024\u3092\u542b\u3080...';
exports.NotContain = '\u6307\u5b9a\u306e\u5024\u3092\u542b\u307e\u306a\u3044...';
exports.Before = '\u6307\u5b9a\u306e\u5024\u3088\u308a\u524d...';
exports.After = '\u6307\u5b9a\u306e\u5024\u3088\u308a\u5f8c...';
exports.Tomorrow = '\u660e\u65e5';
exports.Today = '\u4eca\u65e5';
exports.Yesterday = '\u6628\u65e5';
exports.NextWeek = '\u6765\u9031';
exports.ThisWeek = '\u4eca\u9031';
exports.LastWeek = '\u5148\u9031';
exports.NextMonth = '\u6765\u6708';
exports.ThisMonth = '\u4eca\u6708';
exports.LastMonth = '\u5148\u6708';
exports.NextQuarter = '\u6765\u56db\u534a\u671f';
exports.ThisQuarter = '\u4eca\u56db\u534a\u671f';
exports.LastQuarter = '\u524d\u56db\u534a\u671f';
exports.NextYear = '\u6765\u5e74';
exports.ThisYear = '\u4eca\u5e74';
exports.LastYear = '\u6628\u5e74';
exports.YearToDate = '\u4eca\u5e74\u306e\u521d\u3081\u304b\u3089\u4eca\u65e5\u307e\u3067';
exports.AllDates = '\u671f\u9593\u5185\u306e\u5168\u65e5\u4ed8';
exports.Top10Filter = '\u30c8\u30c3\u30d7\u30c6\u30f3 \u30aa\u30fc\u30c8\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.CustomTitle = '\u30aa\u30fc\u30c8\u30d5\u30a3\u30eb\u30bf\u30fc \u30aa\u30d7\u30b7\u30e7\u30f3';
exports.ColorTitle = '\u5229\u7528\u53ef\u80fd\u306a\u30bb\u30eb\u306e\u8272';
exports.top = '\u4e0a\u4f4d';
exports.bottom = '\u4e0b\u4f4d';
exports.SortCell = '\u4e26\u3079\u66ff\u3048\u306e\u57fa\u6e96\u306b\u3059\u308b\u30bb\u30eb\u306e\u8272\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044:';
exports.SortFont = '\u4e26\u3079\u66ff\u3048\u306e\u57fa\u6e96\u306b\u3059\u308b\u30d5\u30a9\u30f3\u30c8\u306e\u8272\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044:';
exports.FilterCell = '\u30d5\u30a3\u30eb\u30bf\u30fc\u306e\u57fa\u6e96\u306b\u3059\u308b\u30bb\u30eb\u306e\u8272\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044:';
exports.FilterFont = '\u30d5\u30a3\u30eb\u30bf\u30fc\u306e\u57fa\u6e96\u306b\u3059\u308b\u30d5\u30a9\u30f3\u30c8\u306e\u8272\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044:';
exports.Selected = '\u9078\u629e\u6e08\u307f:';
exports.IsEquals = '\u3068\u7b49\u3057\u3044';
exports.NotEquals = '\u3068\u7b49\u3057\u304f\u306a\u3044';
exports.IsGreaterThan = '\u3088\u308a\u5927\u304d\u3044';
exports.IsGreaterOrEqual = '\u4ee5\u4e0a';
exports.IsLess = '\u3088\u308a\u5c0f\u3055\u3044';
exports.LessOrEqual = '\u4ee5\u4e0b';
exports.IsBeginWith = '\u3067\u59cb\u307e\u308b';
exports.NotBeginWith = '\u3067\u59cb\u307e\u3089\u306a\u3044';
exports.IsEndWith = '\u3067\u7d42\u308f\u308b';
exports.NotEndWith = '\u3067\u7d42\u308f\u3089\u306a\u3044';
exports.IsContain = '\u3092\u542b\u3080';
exports.NotContains = '\u3092\u542b\u307e\u306a\u3044';
exports.IsAfter = '\u3088\u308a\u5f8c';
exports.AfterOrEqual = '\u4ee5\u964d';
exports.IsBefore = '\u3088\u308a\u524d';
exports.BeforeOrEqual = '\u4ee5\u524d';
exports.Q1 = '\u7b2c 1 \u56db\u534a\u671f';
exports.Q2 = '\u7b2c 2 \u56db\u534a\u671f';
exports.Q3 = '\u7b2c 3 \u56db\u534a\u671f';
exports.Q4 = '\u7b2c 4 \u56db\u534a\u671f';
exports.Jan = '1\u6708';
exports.Feb = '2\u6708';
exports.Mar = '3\u6708';
exports.Apr = '4\u6708';
exports.May = '5\u6708';
exports.Jun = '6\u6708';
exports.Jul = '7\u6708';
exports.Aug = '8\u6708';
exports.Sep = '9\u6708';
exports.Oct = '10\u6708';
exports.Nov = '11\u6708';
exports.Dec = '12\u6708';
exports.Explain1 = '? \u3092\u4f7f\u3063\u3066\u4efb\u610f\u306e 1 \u6587\u5b57\u3092\u8868\u3059\u3053\u3068\u304c\u3067\u304d\u307e\u3059\u3002';
exports.Explain2 = '* \u3092\u4f7f\u3063\u3066\u4efb\u610f\u306e\u6587\u5b57\u5217\u3092\u8868\u3059\u3053\u3068\u304c\u3067\u304d\u307e\u3059\u3002';
exports.Year = '\u5e74';
exports.Day = '\u65e5';
exports.add_current_filter = '\u73fe\u5728\u306e\u9078\u629e\u3092\u30d5\u30a3\u30eb\u30bf\u30fc\u306b\u8ffd\u52a0\u3059\u308b';
exports.invalidCondition = '\u89e3\u6790\u3059\u308b\u884c\u306b\u8aa4\u308a\u304c\u3042\u308a\u307e\u3059';


/***/ }),

/***/ "./dist/plugins/floatingObject/floatingobject.res.ja.js":
/*!**************************************************************!*\
  !*** ./dist/plugins/floatingObject/floatingobject.res.ja.js ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_FloatingObjectHasSameNameError = '\u3059\u3067\u306b\u540c\u540d\u306e\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u540d\u304c\u5b58\u5728\u3057\u307e\u3059\u3002';
exports.Exp_FloatingObjectNameEmptyError = '\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u540d\u3092\u7a7a\u306b\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';


/***/ }),

/***/ "./dist/plugins/group/group.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/group/group.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_InvalidIndex = '\u7121\u52b9\u306a\u30a4\u30f3\u30c7\u30c3\u30af\u30b9\u3067\u3059';
exports.Exp_InvalidCount = '\u7121\u52b9\u306a\u30ab\u30a6\u30f3\u30c8\u3067\u3059';
exports.Exp_InvalidLevel = '\u7121\u52b9\u306a\u30ec\u30d9\u30eb\u3067\u3059';
exports.Exp_GroupInfoIsNull = 'groupInfo \u304c null \u3067\u3059';
exports.Exp_GROUP_PROTECTED = "\u3053\u306e\u30b3\u30de\u30f3\u30c9\u306f\u4fdd\u8b77\u3055\u308c\u305f\u30b7\u30fc\u30c8\u3067\u306f\u4f7f\u7528\u3067\u304d\u307e\u305b\u3093\u3002\u3053\u306e\u30b3\u30de\u30f3\u30c9\u3092\u4f7f\u7528\u3059\u308b\u524d\u306b\u3001\u30b7\u30fc\u30c8\u306e\u4fdd\u8b77\u3092\u89e3\u9664\u3057\u3066\u304f\u3060\u3055\u3044\u3002";


/***/ }),

/***/ "./dist/plugins/outlineColumn/outlineColumn.res.ja.js":
/*!************************************************************!*\
  !*** ./dist/plugins/outlineColumn/outlineColumn.res.ja.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_GROUP_PROTECTED = "\u3053\u306e\u30b3\u30de\u30f3\u30c9\u306f\u4fdd\u8b77\u3055\u308c\u305f\u30b7\u30fc\u30c8\u3067\u306f\u4f7f\u7528\u3067\u304d\u307e\u305b\u3093\u3002\u3053\u306e\u30b3\u30de\u30f3\u30c9\u3092\u4f7f\u7528\u3059\u308b\u524d\u306b\u3001\u30b7\u30fc\u30c8\u306e\u4fdd\u8b77\u3092\u89e3\u9664\u3057\u3066\u304f\u3060\u3055\u3044\u3002";


/***/ }),

/***/ "./dist/plugins/pivot/pivot.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/pivot/pivot.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.RepeatName = "\u7e70\u308a\u8fd4\u3057";
exports.NotExist = "\u306a\u3057";
exports.SubtotalType_Prefix_0 = "\u5e73\u5747 / ";
exports.SubtotalType_Prefix_1 = "\u500b\u6570 / ";
exports.SubtotalType_Prefix_2 = "\u6570\u5024\u306e\u500b\u6570 / ";
exports.SubtotalType_Prefix_3 = "\u6700\u5927 / ";
exports.SubtotalType_Prefix_4 = "\u6700\u5c0f / ";
exports.SubtotalType_Prefix_5 = "\u7a4d / ";
exports.SubtotalType_Prefix_6 = "\u6a19\u672c\u6a19\u6e96\u504f\u5dee / ";
exports.SubtotalType_Prefix_7 = "\u6a19\u6e96\u504f\u5dee / ";
exports.SubtotalType_Prefix_8 = "\u5408\u8a08 / ";
exports.SubtotalType_Prefix_9 = "\u6a19\u672c\u5206\u6563 / ";
exports.SubtotalType_Prefix_10 = "\u5206\u6563 / ";
exports.ColumnLabels = "\u5217\u30e9\u30d9\u30eb";
exports.Total = "\u5408\u8a08";
exports.All = "\u3059\u3079\u3066";
exports.MultipleItems = "\u8907\u6570\u30a2\u30a4\u30c6\u30e0";
exports.RowLabels = "\u884c\u30e9\u30d9\u30eb";
exports.GrandTotal = "\u7dcf\u8a08";
exports.Values = "\u5024";
exports.PivotPanel_Title = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e\u30d5\u30a3\u30fc\u30eb\u30c9";
exports.PivotPanel_ReportText = "\u30ec\u30dd\u30fc\u30c8\u306b\u8ffd\u52a0\u3059\u308b\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044\uff1a";
exports.PivotPanel_SearchPlaceholder = "\u691c\u7d22";
exports.PivotPanel_FieldAreaText = "\u6b21\u306e\u30dc\u30c3\u30af\u30b9\u9593\u3067\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u304f\u3060\u3055\u3044\uff1a";
exports.PivotPanel_FiltersItemsTitle = "\u30d5\u30a3\u30eb\u30bf\u30fc";
exports.PivotPanel_RowsItemsTitle = "\u884c";
exports.PivotPanel_ColumnsItemsTitle = "\u5217";
exports.PivotPanel_ValuesItemsTitle = "\u5024";
exports.PivotPanel_DeferUpdateText = "\u30ec\u30a4\u30a2\u30a6\u30c8\u306e\u66f4\u65b0\u3092\u4fdd\u7559\u3059\u308b";
exports.EmptyPivotTable_PromptMessage1 = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u3092\u4f7f\u7528\u3059\u308b\u306b\u306f\u3001\u3053\u306e\u9818\u57df\u3092\u30af\u30ea\u30c3\u30af\u3057\u3066\u304f\u3060\u3055\u3044";
exports.EmptyPivotTable_PromptMessage2 = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u3092\u4f7f\u7528\u3059\u308b\u306b\u306f\u3001\u30d5\u30a3\u30fc\u30eb\u30c9\u30ea\u30b9\u30c8\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044";
exports.PivotPanel_Update = "\u66f4\u65b0";
exports.PivotPanel_DropDownList_Up = "\u4e0a\u3078\u79fb\u52d5";
exports.PivotPanel_DropDownList_Down = "\u4e0b\u3078\u79fb\u52d5";
exports.PivotPanel_DropDownList_Beginning = "\u5148\u982d\u3078\u79fb\u52d5";
exports.PivotPanel_DropDownList_End = "\u672b\u5c3e\u3078\u79fb\u52d5";
exports.PivotPanel_DropDownList_ReportFilter = "\u30ec\u30dd\u30fc\u30c8\u30d5\u30a3\u30eb\u30bf\u30fc\u306b\u79fb\u52d5";
exports.PivotPanel_DropDownList_Row = "\u884c\u30e9\u30d9\u30eb\u306b\u79fb\u52d5";
exports.PivotPanel_DropDownList_Col = "\u5217\u30e9\u30d9\u30eb\u306b\u79fb\u52d5";
exports.PivotPanel_DropDownList_Values = "\u5024\u306b\u79fb\u52d5";
exports.PivotPanel_DropDownList_Remove = "\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u524a\u9664";
exports.PivotPanel_DropDownList_Set = "\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8a2d\u5b9a...";
exports.PivotPanel_DropDownList_ValueSet = "\u5024\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8a2d\u5b9a...";
exports.PivotPanel_Fields_MoveTo_ReportField = "\u30ec\u30dd\u30fc\u30c8\u30d5\u30a3\u30eb\u30bf\u30fc\u306b\u8ffd\u52a0";
exports.PivotPanel_Fields_MoveTo_RowLabel = "\u884c\u30e9\u30d9\u30eb\u306b\u8ffd\u52a0";
exports.PivotPanel_Fields_MoveTo_ColLabel = "\u5217\u30e9\u30d9\u30eb\u306b\u8ffd\u52a0";
exports.PivotPanel_Fields_MoveTo_Value = "\u5024\u306b\u8ffd\u52a0";
exports.wmk1 = ["3030303030303030304f7530715230303030303030303030", "d4dcc3c8c6fcd6eb6e7f284c21b96ae9a4bbf3b9adfc6759"];
exports.wmk2 = "";
exports.PivotPanel_LabelOrValue_ClearFilter = "\u30d5\u30a3\u30eb\u30bf\u30fc\u306e\u30af\u30ea\u30a2";
exports.PivotPanel_ValueFilterOrLabel_Equals = "\u6307\u5b9a\u306e\u5024\u306b\u7b49\u3057\u3044...";
exports.PivotPanel_ValueFilterOrLabel_NotEqual = "\u6307\u5b9a\u306e\u5024\u306b\u7b49\u3057\u304f\u306a\u3044...";
exports.PivotPanel_ValueFilterOrLabel_GreaterThan = "\u6307\u5b9a\u306e\u5024\u3088\u308a\u5927\u304d\u3044...";
exports.PivotPanel_ValueFilterOrLabel_GreaterOrTo = "\u6307\u5b9a\u306e\u5024\u4ee5\u4e0a...";
exports.PivotPanel_ValueFilterOrLabel_LessThan = "\u6307\u5b9a\u306e\u5024\u3088\u308a\u5c0f\u3055\u3044...";
exports.PivotPanel_ValueFilterOrLabel_LessOrTo = "\u6307\u5b9a\u306e\u5024\u4ee5\u4e0b...";
exports.PivotPanel_ValueFilterOrLabel_Between = "\u6307\u5b9a\u306e\u7bc4\u56f2\u5185...";
exports.PivotPanel_ValueFilterOrLabel_NotBetween = "\u6307\u5b9a\u306e\u7bc4\u56f2\u5916...";
exports.PivotPanel_ValueFilter_Top10 = "\u30c8\u30c3\u30d7\u30c6\u30f3...";
exports.PivotPanel_ValueFilterOrLabel_IsBeginWith = '\u6307\u5b9a\u306e\u5024\u3067\u59cb\u307e\u308b...';
exports.PivotPanel_ValueFilterOrLabel_NotBegin = '\u6307\u5b9a\u306e\u5024\u3067\u59cb\u307e\u3089\u306a\u3044...';
exports.PivotPanel_ValueFilterOrLabel_End = '\u6307\u5b9a\u306e\u5024\u3067\u7d42\u308f\u308b...';
exports.PivotPanel_ValueFilterOrLabel_NotEndWith = '\u6307\u5b9a\u306e\u5024\u3067\u7d42\u308f\u3089\u306a\u3044...';
exports.PivotPanel_ValueFilterOrLabel_Contain = '\u6307\u5b9a\u306e\u5024\u3092\u542b\u3080...';
exports.PivotPanel_ValueFilterOrLabel_NotContain = '\u6307\u5b9a\u306e\u5024\u3092\u542b\u307e\u306a\u3044...';
exports.ValueFilterOrLabel_Equals = "\u3068\u7b49\u3057\u3044";
exports.ValueFilterOrLabel_NotEqual = "\u3068\u7b49\u3057\u304f\u306a\u3044";
exports.ValueFilterOrLabel_GreaterThan = "\u3088\u308a\u5927\u304d\u3044";
exports.ValueFilterOrLabel_GreaterOrTo = "\u4ee5\u4e0a";
exports.ValueFilterOrLabel_LessThan = "\u3088\u308a\u5c0f\u3055\u3044";
exports.ValueFilterOrLabel_LessOrTo = "\u4ee5\u4e0b";
exports.ValueFilterOrLabel_Between = "\u306e\u9593";
exports.ValueFilterOrLabel_NotBetween = "\u306e\u9593\u4ee5\u5916";
exports.ValueFilterOrLabelSelect_GreaterThan = "\u3088\u308a\u5927\u304d\u3044";
exports.ValueFilterOrLabelSelect_GreaterOrTo = "\u4ee5\u4e0a";
exports.ValueFilterOrLabelSelect_LessThan = "\u3088\u308a\u5c0f\u3055\u3044";
exports.ValueFilterOrLabelSelect_LessOrTo = "\u4ee5\u4e0b";
exports.ValueFilterOrLabelSelect_Between = "\u306e\u9593";
exports.ValueFilterOrLabelSelect_NotBetween = "\u306e\u9593\u4ee5\u5916";
exports.ValueFilterOrLabel_IsBeginWith = '\u3067\u59cb\u307e\u308b';
exports.ValueFilterOrLabel_NotBegin = '\u3067\u59cb\u307e\u3089\u306a\u3044';
exports.ValueFilterOrLabel_End = '\u3067\u7d42\u308f\u308b';
exports.ValueFilterOrLabel_NotEndWith = '\u3067\u7d42\u308f\u3089\u306a\u3044';
exports.ValueFilterOrLabel_Contain = '\u3092\u542b\u3080';
exports.ValueFilterOrLabel_NotContain = '\u3092\u542b\u307e\u306a\u3044';
exports.PivotPanel_ValueFilterOrLabel_Before = '\u6307\u5b9a\u306e\u5024\u3088\u308a\u524d...';
exports.PivotPanel_ValueFilterOrLabel_After = '\u6307\u5b9a\u306e\u5024\u3088\u308a\u5f8c...';
exports.PivotPanel_ValueFilterOrLabel_Tomorrow = '\u660e\u65e5';
exports.PivotPanel_ValueFilterOrLabel_Today = '\u4eca\u65e5';
exports.PivotPanel_ValueFilterOrLabel_Yesterday = '\u6628\u65e5';
exports.PivotPanel_ValueFilterOrLabel_NextWeek = '\u6765\u9031';
exports.PivotPanel_ValueFilterOrLabel_ThisWeek = '\u4eca\u9031';
exports.PivotPanel_ValueFilterOrLabel_LastWeek = '\u5148\u9031';
exports.PivotPanel_ValueFilterOrLabel_NextMonth = '\u6765\u6708';
exports.PivotPanel_ValueFilterOrLabel_ThisMonth = '\u4eca\u6708';
exports.PivotPanel_ValueFilterOrLabel_LastMonth = '\u5148\u6708';
exports.PivotPanel_ValueFilterOrLabel_NextQuarter = '\u6765\u56db\u534a\u671f';
exports.PivotPanel_ValueFilterOrLabel_ThisQuarter = '\u4eca\u56db\u534a\u671f';
exports.PivotPanel_ValueFilterOrLabel_LastQuarter = '\u524d\u56db\u534a\u671f';
exports.PivotPanel_ValueFilterOrLabel_NextYear = '\u6765\u5e74';
exports.PivotPanel_ValueFilterOrLabel_ThisYear = '\u4eca\u5e74';
exports.PivotPanel_ValueFilterOrLabel_LastYear = '\u6628\u5e74';
exports.PivotPanel_ValueFilterOrLabel_YearToDate = '\u4eca\u5e74\u306e\u521d\u3081\u304b\u3089\u4eca\u65e5\u307e\u3067';
exports.PivotPanel_ValueFilterOrLabel_MonthToDate = '\u4eca\u6708\u306e\u521d\u3081\u304b\u3089\u4eca\u65e5\u307e\u3067';
exports.PivotPanel_ValueFilterOrLabel_QuarterToDate = '\u4eca\u56db\u534a\u671f\u306e\u521d\u3081\u304b\u3089\u4eca\u65e5\u307e\u3067';
exports.PivotPanel_ValueFilterOrLabel_ParallelYearToDate = '\u5e74\u6bce\u306e\u958b\u59cb\u65e5\u304b\u3089\u73fe\u5728\u65e5\u307e\u3067';
exports.PivotPanel_ValueFilterOrLabel_ParallelMonthToDate = '\u6708\u6bce\u306e\u958b\u59cb\u65e5\u304b\u3089\u73fe\u5728\u65e5\u307e\u3067';
exports.PivotPanel_ValueFilterOrLabel_ParallelQuarterToDate = '\u56db\u534a\u671f\u6bce\u306e\u958b\u59cb\u65e5\u304b\u3089\u73fe\u5728\u65e5\u307e\u3067';
exports.PivotPanel_ValueFilterOrLabel_AllDates = '\u671f\u9593\u5185\u306e\u5168\u65e5\u4ed8';
exports.PivotPanel_ValueFilterOrLabel_Custom = '\u30e6\u30fc\u30b6\u30fc\u5b9a\u7fa9\u30d5\u30a3\u30eb\u30bf\u30fc...';
exports.PivotPanel_Sort_A_Z = '\u6607\u9806';
exports.PivotPanel_Sort_Z_A = '\u964d\u9806';
exports.PivotPanel_Sort_Smallest_Largest = '\u6700\u5c0f\u304b\u3089\u6700\u5927\u306e\u9806\u306b\u4e26\u3079\u66ff\u3048';
exports.PivotPanel_Sort_Largest_Smallest = '\u6700\u5927\u304b\u3089\u6700\u5c0f\u306e\u9806\u306b\u4e26\u3079\u66ff\u3048';
exports.PivotPanel_Sort_More = '\u305d\u306e\u4ed6\u306e\u4e26\u3079\u66ff\u3048\u30aa\u30d7\u30b7\u30e7\u30f3...';
exports.PivotPanel_Filter_Clear = '{0} \u304b\u3089\u30d5\u30a3\u30eb\u30bf\u30fc\u3092\u30af\u30ea\u30a2';
exports.PivotPanel_Filter_Label = '\u30e9\u30d9\u30eb\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.PivotPanel_Filter_Value = '\u5024\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.PivotPanel_Date_Filter = '\u65e5\u4ed8\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.Ok = 'OK';
exports.Cancel = '\u30ad\u30e3\u30f3\u30bb\u30eb';
exports.NumberFormat = '\u8868\u793a\u5f62\u5f0f';
exports.LabelFormat = '\u8868\u793a\u5f62\u5f0f';
exports.Number = '\u6570\u5024';
exports.FormatCells = '\u30bb\u30eb\u306e\u66f8\u5f0f\u8a2d\u5b9a';
exports.Sample = '\u30b5\u30f3\u30d7\u30eb';
exports.Type = '\u7a2e\u985e';
exports.CustomFormats = [
    "General",
    "0",
    "0.00",
    "#,##0",
    "#,##0.00",
    '_ * #,##0_ ;_ * -#,##0_ ;_ * "-"_ ;_ @_ ',
    '_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * "-"??_ ;_ @_ ',
    '_ \* #,##0_ ;_ \* -#,##0_ ;_ \* "-"_ ;_ @_ ',
    '_ \* #,##0.00_ ;_ \* -#,##0.00_ ;_ \* "-"??_ ;_ @_ ',
    "#,##0;-#,##0",
    "#,##0;[Red]-#,##0",
    "#,##0.00;-#,##0.00",
    "#,##0.00;[Red]-#,##0.00",
    "\#,##0;\-#,##0",
    "\#,##0;[Red]\-#,##0",
    "\#,##0.00;\-#,##0.00",
    "\#,##0.00;[Red]\-#,##0.00",
    "0%",
    "0.00%",
    "0.00E+00",
    "##0.0E+0",
    "# ?/?",
    "# ??/??",
    "$#,##0_);($#,##0)",
    "$#,##0_);[Red]($#,##0)",
    "$#,##0.00_);($#,##0.00)",
    "$#,##0.00_);[Red]($#,##0.00)",
    "[$-411]ge.m.d",
    '[$-411]ggge"\u5e74"m"\u6708"d"\u65e5"',
    "yyyy/m/d",
    'yyyy"\u5e74"m"\u6708"d"\u65e5"',
    'yyyy"\u5e74"m"\u6708"',
    'm"\u6708"d"\u65e5"',
    "m/d/yy",
    "d-mmm-yy",
    "d-mmm",
    "mmm-yy",
    "h:mm AM/PM",
    "h:mm:ss AM/PM",
    "h:mm",
    "h:mm:ss",
    'h"\u6642"mm"\u5206"',
    'h"\u6642"mm"\u5206"ss"\u79d2"',
    "yyyy/m/d h:mm",
    "mm:ss",
    "mm:ss.0",
    "@",
    "[h]:mm:ss"
];
exports.Select_Field = '\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u9078\u629e';
exports.PivotPanel_Filter_SelectAll = '\u3059\u3079\u3066\u9078\u629e';
exports.PivotPanel_Filter_NoSelectAll = '\u3059\u3079\u3066\u9078\u629e\u89e3\u9664';
exports.PivotPanel_Filter_Search = '\u691c\u7d22';
exports.Label_Title = '\u30e9\u30d9\u30eb\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.Label_Show = '\u30e9\u30d9\u30eb\u304c\u6b21\u306e\u6761\u4ef6\u306b\u4e00\u81f4\u3059\u308b\u9805\u76ee\u3092\u8868\u793a';
exports.Use_Single_Character = '? \u3092\u4f7f\u3063\u3066\u3001\u4efb\u610f\u306e1\u6587\u5b57\u3092\u8868\u3059\u3053\u3068\u304c\u3067\u304d\u307e\u3059\u3002';
exports.Use_Series_Characters = '* \u3092\u4f7f\u3063\u3066\u3001\u4efb\u610f\u306e\u6587\u5b57\u5217\u3092\u8868\u3059\u3053\u3068\u304c\u3067\u304d\u307e\u3059\u3002';
exports.Value_Show = '\u6b21\u306e\u6761\u4ef6\u306b\u4e00\u81f4\u3059\u308b\u9805\u76ee\u3092\u8868\u793a';
exports.Value_Title = '\u5024\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.Top_Ten_Filter = '\u30c8\u30c3\u30d7\u30c6\u30f3\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.TopTenShow = '\u8868\u793a';
exports.Top = '\u4e0a\u4f4d';
exports.Bottom = '\u4e0b\u4f4d';
exports.Top_Item = '\u9805\u76ee';
exports.Top_Percent = '\u30d1\u30fc\u30bb\u30f3\u30c8';
exports.Top_Sum = '\u5408\u8a08';
exports.ByJoiner = "\u306e";
exports.AndJoiner = "\u3068";
exports.Q1 = '\u7b2c1\u56db\u534a\u671f';
exports.Q2 = '\u7b2c2\u56db\u534a\u671f';
exports.Q3 = '\u7b2c3\u56db\u534a\u671f';
exports.Q4 = '\u7b2c4\u56db\u534a\u671f';
exports.Jan = '1\u6708';
exports.Feb = '2\u6708';
exports.Mar = '3\u6708';
exports.Apr = '4\u6708';
exports.May = '5\u6708';
exports.Jun = '6\u6708';
exports.Jul = '7\u6708';
exports.Aug = '8\u6708';
exports.Sep = '9\u6708';
exports.Oct = '10\u6708';
exports.Nov = '11\u6708';
exports.Dec = '12\u6708';
exports.IsBefore = '\u3088\u308a\u524d';
exports.IsBeforeOrEqual = '\u4ee5\u524d';
exports.IsAfter = '\u3088\u308a\u5f8c';
exports.IsAfterOrEqual = '\u4ee5\u964d';
exports.IsBetween = '\u306e\u9593';
exports.IsNotBetween = '\u306e\u9593\u4ee5\u5916';
exports.DateFilterTitle = '\u65e5\u4ed8\u30d5\u30a3\u30eb\u30bf\u30fc';
exports.DateShow = '\u6b21\u306e\u6761\u4ef6\u306b\u4e00\u81f4\u3059\u308b\u65e5\u4ed8\u3092\u8868\u793a';
exports.WholeDays = '\u65e5\u5358\u4f4d';
exports.FieldSetting = "\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8a2d\u5b9a";
exports.ValueSetting = '\u5024\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8a2d\u5b9a';
exports.SourceName = '\u30bd\u30fc\u30b9\u540d\uff1a';
exports.CustomName = '\u540d\u524d\u306e\u6307\u5b9a\uff1a';
exports.SummarizeValue = '\u96c6\u8a08\u65b9\u6cd5';
exports.ShowValueAs = '\u8a08\u7b97\u306e\u7a2e\u985e';
exports.CalculationForDialog = '\u8a08\u7b97\uff1a';
exports.BaseFieldForDialog = "\u57fa\u6e96\u30d5\u30a3\u30fc\u30eb\u30c9\uff1a";
exports.BaseItemForDialog = "\u57fa\u6e96\u30a2\u30a4\u30c6\u30e0\uff1a";
exports.BaseItemNext = "(\u6b21\u306e\u5024)";
exports.BaseItemPrevious = "(\u524d\u306e\u5024)";
exports.SummarizeValueField = '\u96c6\u8a08\u65b9\u6cd5';
exports.ShowValueAsField = '\u8a08\u7b97\u306e\u7a2e\u985e';
exports.ChooseType = '\u96c6\u8a08\u306b\u4f7f\u7528\u3059\u308b\u8a08\u7b97\u306e\u7a2e\u985e\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044<br>\u9078\u629e\u3057\u305f\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u30c7\u30fc\u30bf';
exports.Sum = '\u5408\u8a08';
exports.Count = '\u500b\u6570';
exports.Average = '\u5e73\u5747';
exports.Max = '\u6700\u5927';
exports.Min = '\u6700\u5c0f';
exports.Product = '\u7a4d';
exports.CountNumbers = '\u6570\u5024\u306e\u500b\u6570';
exports.StdDev = '\u6a19\u672c\u6a19\u6e96\u504f\u5dee';
exports.StdDevp = '\u6a19\u6e96\u504f\u5dee';
exports.Var = '\u6a19\u672c\u5206\u6563';
exports.Varp = '\u5206\u6563';
exports.sigmaValue = "\u2211\u5024";
exports.sigmaValueTemp = "\u5024";
exports.DateFormatError = "\u6709\u52b9\u306a\u65e5\u4ed8\u3067\u306f\u3042\u308a\u307e\u305b\u3093";
exports.noCalculation = "\u8a08\u7b97\u306a\u3057";
exports.percentGrandTotal = "\u7dcf\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.percentColumnTotal = "\u5217\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.percentRowTotal = "\u884c\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.percentEllipsis = "\u57fa\u6e96\u5024\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.percentParentRowTotal = "\u89aa\u884c\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.percentParentColumnTotal = "\u89aa\u5217\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.percentParentTotal = "\u89aa\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387";
exports.difference = "\u57fa\u6e96\u5024\u3068\u306e\u5dee\u5206";
exports.percentDifference = "\u5dee\u5206\u306e\u6bd4\u7387";
exports.runningTotal = "\u7d2f\u8a08";
exports.percentRunningTotal = "\u6bd4\u7387\u306e\u7d2f\u8a08";
exports.rankSmallestLargest = "\u6607\u9806\u3067\u306e\u9806\u4f4d";
exports.rankLargestSmallest = "\u964d\u9806\u3067\u306e\u9806\u4f4d";
exports.index = "\u6307\u6570 (\u30a4\u30f3\u30c7\u30c3\u30af\u30b9)";

exports.showValueAsDialog = [
    '', '', '', '',
    "\u57fa\u6e96\u5024\u306b\u5bfe\u3059\u308b\u6bd4\u7387",
    '', '',
    "\u89aa\u96c6\u8a08\u306b\u5bfe\u3059\u308b\u6bd4\u7387",
    "\u57fa\u6e96\u5024\u3068\u306e\u5dee\u5206",
    "% Difference From ",
    "\u7d2f\u8a08",
    "\u6bd4\u7387\u306e\u7d2f\u8a08",
    "\u6607\u9806\u3067\u306e\u9806\u4f4d",
    "\u964d\u9806\u3067\u306e\u9806\u4f4d",
    '',
];
exports.baseField = "\u57fa\u6e96\u30d5\u30a3\u30fc\u30eb\u30c9\uff1a";
exports.baseItem = "\u57fa\u6e96\u30a2\u30a4\u30c6\u30e0\uff1a";
exports.grouping = "\u30b0\u30eb\u30fc\u30d7\u5316";
exports.auto = "\u81ea\u52d5";
exports.startingAt = "\u958b\u59cb\u65e5\uff1a";
exports.endingAt = "\u7d42\u4e86\u65e5\uff1a";
exports.groupBy = "\u5358\u4f4d\uff1a";
exports.seconds = "\u79d2";
exports.minutes = "\u5206";
exports.hours = "\u6642";
exports.days = "\u65e5";
exports.months = "\u6708";
exports.quarters = "\u56db\u534a\u671f";
exports.years = "\u5e74";
exports.numberOfDays = "\u65e5\u6570\uff1a";
exports.NoHaveSpread = "\u30ef\u30fc\u30af\u30d6\u30c3\u30af\u306e\u4f8b\u5916";
exports.SourceError = "\u30bd\u30fc\u30b9\u30c7\u30fc\u30bf\u306e\u4f8b\u5916\u306b\u3088\u308a\u3001\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u3092\u4f5c\u6210\u3067\u304d\u307e\u305b\u3093\u3002";
exports.SourceNotIsTableNameOrFormula = "\u30bd\u30fc\u30b9\u30c7\u30fc\u30bf\u304c\u30c6\u30fc\u30d6\u30eb\u540d\u307e\u305f\u306f\u5f0f\u3067\u306f\u3042\u308a\u307e\u305b\u3093\u3002";
exports.SourceDataOnlyOne = "\u3053\u306e\u30b3\u30de\u30f3\u30c9\u306b\u306f\u3001\u5c11\u306a\u304f\u3068\u30822\u884c\u306e\u30bd\u30fc\u30b9\u30c7\u30fc\u30bf\u304c\u5fc5\u8981\u3067\u3059\u30021\u884c\u306e\u307f\u306e\u9078\u629e\u3067\u306f\u4f7f\u7528\u3067\u304d\u307e\u305b\u3093\u3002";
exports.FieldAreaLimited = "\u79fb\u52d5\u3057\u3066\u3044\u308b\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u3001\u30ec\u30dd\u30fc\u30c8\u306e\u305d\u306e\u9818\u57df\u306b\u914d\u7f6e\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002";
exports.Views = "\u30d3\u30e5\u30fc";
exports.Group = "\u30b0\u30eb\u30fc\u30d7";
exports.param_error = "\u30d1\u30e9\u30e1\u30fc\u30bf\u304cnull\u307e\u305f\u306f\u672a\u5b9a\u7fa9\u3067\u3059";
exports.EmptyValueFieldError = "\u5024\u30d5\u30a3\u30eb\u30bf\u30fc\u3092\u9069\u7528\u3059\u308b\u306b\u306f\u3001[\u5024] \u9818\u57df\u306b\u5c11\u306a\u304f\u3068\u3082\uff11\u3064\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u304c\u5fc5\u8981\u3067\u3059";
exports.DefaultPivotTableViewName = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30d3\u30e5\u30fc";
exports.toolTipContent_Row = "\u884c\uff1a ";
exports.toolTipContent_Column = "\u5217\uff1a ";
exports.toolTipContent_Value = "\u5024\uff1a ";
exports.toolTipContent_NoValue = "\u5024\u306a\u3057";
exports.deferLayoutUpdate = "\u30ec\u30a4\u30a2\u30a6\u30c8\u306e\u66f4\u65b0\u3092\u4fdd\u7559\u3059\u308b";
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
exports.PivotTableErrorMessage_ShowDetail = "\u3053\u306e\u9078\u629e\u7bc4\u56f2\u306e\u8a73\u7d30\u3092\u8868\u793a\u307e\u305f\u306f\u975e\u8868\u793a\u306b\u3067\u304d\u307e\u305b\u3093\u3002";
exports.PivotTableErrorMessage_MakeChange = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306b\u5f71\u97ff\u304c\u3042\u308b\u305f\u3081\u3001\u9078\u629e\u3057\u305f\u30bb\u30eb\u306f\u5909\u66f4\u3067\u304d\u307e\u305b\u3093\u3002\u30d5\u30a3\u30fc\u30eb\u30c9\u30ea\u30b9\u30c8\u3092\u4f7f\u7528\u3057\u3066\u30ec\u30dd\u30fc\u30c8\u3092\u5909\u66f4\u3057\u3066\u304f\u3060\u3055\u3044\u3002\u30bb\u30eb\u3092\u633f\u5165\u307e\u305f\u306f\u524a\u9664\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u5834\u5408\u306f\u3001\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u3092\u79fb\u52d5\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
exports.PivotTableErrorMessage_ExistData = "{} \u306b\u306f\u3059\u3067\u306b\u30c7\u30fc\u30bf\u304c\u3042\u308a\u307e\u3059\u3002\u7f6e\u304d\u63db\u3048\u307e\u3059\u304b\uff1f";
exports.PivotTableErrorMessage_EditWhenDefer = "[\u30ec\u30a4\u30a2\u30a6\u30c8\u306e\u66f4\u65b0\u3092\u4fdd\u7559\u3059\u308b] \u306e\u30c1\u30a7\u30c3\u30af\u304c\u30aa\u30f3\u306e\u3068\u304d\u3001\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u306f\u7de8\u96c6\u3067\u304d\u307e\u305b\u3093\u3002\u30ec\u30dd\u30fc\u30c8\u3092\u7de8\u96c6\u3059\u308b\u306b\u306f\u30c1\u30a7\u30c3\u30af\u3092\u30aa\u30d5\u306b\u3057\u307e\u3059\u3002";
exports.PivotTableErrorMessage_DuplicatedFieldName = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u540d\u304c\u65e2\u306b\u5b58\u5728\u3057\u307e\u3059\u3002";
exports.PivotTableErrorMessage_EmptyFieldName = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u306e\u9805\u76ee\u307e\u305f\u306f\u30d5\u30a3\u30fc\u30eb\u30c9\u540d\u306bnull\u3092\u5165\u529b\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002";
exports.PivotTableErrorMessage_Protect = "\u4fdd\u8b77\u3055\u308c\u305f\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u306b\u3001\u540c\u3058\u30c7\u30fc\u30bf\u30bd\u30fc\u30b9\u306b\u57fa\u3065\u304f\u5225\u306e\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u304c\u542b\u307e\u308c\u308b\u3068\u304d\u3001\u3053\u306e\u30b3\u30de\u30f3\u30c9\u306f\u5b9f\u884c\u3067\u304d\u307e\u305b\u3093\u3002\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u306e\u4fdd\u8b77\u3092\u89e3\u9664\u3059\u308b\u306b\u306f\u3001[\u30b7\u30fc\u30c8] \u30bf\u30d6\u3092\u30af\u30ea\u30c3\u30af\u3057\u3001[\u30b7\u30fc\u30c8\u306e\u4fdd\u8b77\u3092\u89e3\u9664] \u3092\u30af\u30ea\u30c3\u30af\u3057\u307e\u3059\u3002";
exports.PivotTableErrorMessage_EmptySourceFieldName = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u540d\u304c\u7121\u52b9\u3067\u3059\u3002\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u3092\u4f5c\u6210\u3059\u308b\u306b\u306f\u3001\u30e9\u30d9\u30eb\u4ed8\u304d\u306e\u5217\u3092\u6301\u3064\u30ea\u30b9\u30c8\u3092\u4f7f\u7528\u3059\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u540d\u3092\u5909\u66f4\u3059\u308b\u306b\u306f\u3001\u65b0\u3057\u3044\u540d\u524d\u3092\u5165\u529b\u3057\u307e\u3059\u3002";
exports.PivotTableErrorMessage_Overlap = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u306f\u3001\u5225\u306e\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u3068\u91cd\u306d\u308b\u3053\u3068\u304c\u3067\u304d\u307e\u305b\u3093\u3002";
exports.PivotTableErrorMessage_InvalidChange = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e\u3053\u306e\u90e8\u5206\u306f\u5909\u66f4\u3067\u304d\u307e\u305b\u3093\u3002";
exports.PivotTableErrorMessage_InvalidGroup = "\u9078\u629e\u3057\u305f\u9818\u57df\u306f\u30b0\u30eb\u30fc\u30d7\u5316\u3067\u304d\u307e\u305b\u3093\u3002";
exports.PivotTableErrorMessage_InvalidReference = "\u53c2\u7167\u304c\u7121\u52b9\u3067\u3059";
exports.PivotTableForAccessibility = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\uff1a\r\n\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u540d\uff1a";
exports.PivotTableCalcItemHasTowCacheField = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e1\u3064\u4ee5\u4e0a\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u306b\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u304c\u3042\u308b\u5834\u5408\u3001\u305d\u306e\u30c7\u30fc\u30bf\u9818\u57df\u30672\u56de\u4ee5\u4e0a\u3001\u307e\u305f\u306f\u305d\u306e\u30c7\u30fc\u30bf\u9818\u57df\u3068\u4ed6\u306e\u9818\u57df\u306e\u4e21\u65b9\u3067\u3001\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u4f7f\u7528\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u8ffd\u52a0\u3057\u3088\u3046\u3068\u3059\u308b\u5834\u5408\u306f\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u3092\u524a\u9664\u3057\u3066\u3001\u518d\u5ea6\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u8ffd\u52a0\u3057\u3066\u304f\u3060\u3055\u3044\u3002\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u3092\u8ffd\u52a0\u3057\u3088\u3046\u3068\u3059\u308b\u5834\u5408\u3001\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u3092\u5909\u66f4\u3057\u3066\u3001\u3069\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u3082\u8907\u6570\u56de\u4f7f\u7528\u3055\u308c\u306a\u3044\u3088\u3046\u306b\u3057\u3066\u304b\u3089\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u3092\u8ffd\u52a0\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
exports.PivotTableHasSameCalcItemName = "\u540c\u3058\u540d\u524d\u306e\u30a2\u30a4\u30c6\u30e0\u307e\u305f\u306f\u30d5\u30a3\u30fc\u30eb\u30c9\u304c\u5b58\u5728\u3059\u308b\u305f\u3081\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u307e\u305f\u306f\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u4f5c\u6210\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f\u3002";
exports.PivotTableHasNumberOrDateGroup = "\u30b0\u30eb\u30fc\u30d7\u5316\u3055\u308c\u305f\u30d5\u30a3\u30fc\u30eb\u30c9\u306b\u306f\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u3092\u8ffd\u52a0\u3067\u304d\u307e\u305b\u3093\u3002\u30b0\u30eb\u30fc\u30d7\u5316\u3092\u89e3\u9664\u3059\u308b\u306b\u306f\u3001\u30d5\u30a3\u30fc\u30eb\u30c9\u304c\u884c\u307e\u305f\u306f\u5217\u9818\u57df\u306b\u3042\u308b\u3053\u3068\u3092\u78ba\u8a8d\u3057\u3001\u30b0\u30eb\u30fc\u30d7\u5316\u3055\u308c\u305f\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u30a2\u30a4\u30c6\u30e0\u306e1\u3064\u3092\u9078\u629e\u3057\u3066\u3001[\u5206\u6790]\u30bf\u30d6\u306e[\u30b0\u30eb\u30fc\u30d7]\u30b0\u30eb\u30fc\u30d7\u306e[\u30b0\u30eb\u30fc\u30d7\u89e3\u9664]\u30dc\u30bf\u30f3\u3092\u30af\u30ea\u30c3\u30af\u3057\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u3092\u633f\u5165\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
exports.PivotTableSubtotalType = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u306b\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u304c\u3042\u308b\u5834\u5408\u3001\u5e73\u5747\u5024\u3001\u6a19\u6e96\u504f\u5dee\u3001\u5206\u6563\u306f\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002";
exports.PivotTableHasSameItemName = "\u540c\u3058\u540d\u524d\u306e\u5225\u306e\u30a2\u30a4\u30c6\u30e0\u307e\u305f\u306f\u30d5\u30a3\u30fc\u30eb\u30c9\u304c\u5b58\u5728\u3059\u308b\u305f\u3081\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u307e\u305f\u306f\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u4f5c\u6210\u3067\u304d\u307e\u305b\u3093\u3067\u3057\u305f\u3002";
exports.PivotTableCalcItemHasMultipleDataField = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u306b\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u304c\u3042\u308b\u5834\u5408\u3001\u540c\u3058\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8907\u6570\u306e\u30c7\u30fc\u30bf\u30d5\u30a3\u30fc\u30eb\u30c9\u306f\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002";
exports.PivotTableMoveCalcItemFieldToReport = "\u3053\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u306f\u3001\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u304c\u542b\u307e\u308c\u3066\u3044\u308b\u305f\u3081\u3001\u30ec\u30dd\u30fc\u30c8\u30d5\u30a3\u30eb\u30bf\u306b\u914d\u7f6e\u3067\u304d\u307e\u305b\u3093\u3002\u3053\u306e\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u30ec\u30dd\u30fc\u30c8\u30d5\u30a3\u30eb\u30bf\u306b\u914d\u7f6e\u3059\u308b\u306b\u306f\u3001\u306f\u3058\u3081\u306b\u96c6\u8a08\u30a2\u30a4\u30c6\u30e0\u3092\u524a\u9664\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
exports.PivotTableErrorFormula = "\u30a2\u30a4\u30c6\u30e0\u540d\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3002\u30a2\u30a4\u30c6\u30e0\u540d\u304c\u6b63\u3057\u304f\u5165\u529b\u3055\u308c\u3066\u3044\u308b\u304b\u3001\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u30ec\u30dd\u30fc\u30c8\u306b\u30a2\u30a4\u30c6\u30e0\u304c\u5b58\u5728\u3059\u308b\u304b\u3092\u78ba\u8a8d\u3057\u3066\u304f\u3060\u3055\u3044\u3002";
exports.SlicerNameInFormula = "\u30b9\u30e9\u30a4\u30b5\u30fc";
exports.search = "\u691c\u7d22";
exports.AddSearchResult = "\u73fe\u5728\u306e\u9078\u629e\u9805\u76ee\u3092\u30d5\u30a3\u30eb\u30bf\u30fc\u306b\u8ffd\u52a0\u3059\u308b";
exports.Layout = "\u30ec\u30a4\u30a2\u30a6\u30c8";
exports.showNoData = "\u30c7\u30fc\u30bf\u306e\u306a\u3044\u30a2\u30a4\u30c6\u30e0\u3092\u8868\u793a\u3059\u308b";
exports.TIMELINE_PLACE_HOLDER = "{TL}";
exports.ALL_DATES_IN = "\u306e\u3059\u3079\u3066\u306e\u65e5\u4ed8";
exports.ALL_PERIODS = "\u3059\u3079\u3066\u306e\u671f\u9593";
exports.INVALID_DATE_SELECTION = "\u65e5\u4ed8\u304c\u6b63\u3057\u304f\u3042\u308a\u307e\u305b\u3093\u3002";
exports.QUARTER_MAP = ['', exports.Q1, exports.Q2, exports.Q3, exports.Q4];
exports.DateNotEqual = exports.TIMELINE_PLACE_HOLDER + " \u3068\u7b49\u3057\u304f\u306a\u3044";
exports.DateNewerThan = exports.TIMELINE_PLACE_HOLDER + " \u3088\u308a\u5f8c";
exports.DateNewerThanOrEqual = exports.TIMELINE_PLACE_HOLDER + " \u4ee5\u964d";
exports.DateOlderThan = exports.TIMELINE_PLACE_HOLDER + "\u3088\u308a\u524d";
exports.DateOlderThanOrEqual = exports.TIMELINE_PLACE_HOLDER + " \u4ee5\u524d";
exports.DateNotBetween = exports.TIMELINE_PLACE_HOLDER + " \u306e\u9593\u4ee5\u5916";
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
exports.TIME_LEVEL_YEARS = '\u5e74';
exports.TIME_LEVEL_QUARTERS = '\u56db\u534a\u671f';
exports.TIME_LEVEL_MONTHS = '\u6708';
exports.TIME_LEVEL_DAYS = '\u65e5';
exports.PivotTableErrorMessage_ExistTable = "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb \u30ec\u30dd\u30fc\u30c8\u306f\u3001\u307b\u304b\u306e\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb \u30ec\u30dd\u30fc\u30c8\u3068\u91cd\u306a\u308a\u5408\u3046\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002";


/***/ }),

/***/ "./dist/plugins/print/print.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/print/print.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_InvalidSheetIndex = '\u30b7\u30fc\u30c8\u30a4\u30f3\u30c7\u30c3\u30af\u30b9\u304c\u7121\u52b9\u3067\u3059\u3002';


/***/ }),

/***/ "./dist/plugins/shape/shape.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/shape/shape.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_InvalidConnectionSite = '\u63a5\u7d9a\u30b5\u30a4\u30c8\u304c\u7121\u52b9\u3067\u3059\u3002';
exports.Exp_DuplicatedName = "\u540d\u524d\u304c\u91cd\u8907\u3057\u3066\u3044\u307e\u3059\u3002";
exports.Exp_EmptyName = "\u540d\u524d\u304c\u7a7a\u3067\u3059\u3002";
exports.Exp_InvalidRange = '\u7121\u52b9\u306a\u7bc4\u56f2';


/***/ }),

/***/ "./dist/plugins/slicer/slicer.res.ja.js":
/*!**********************************************!*\
  !*** ./dist/plugins/slicer/slicer.res.ja.js ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Blank = '(\u7a7a\u767d)';
exports.Exp_SlicerNameInvalid = '\u6b63\u3057\u304f\u306a\u3044\u30b9\u30e9\u30a4\u30b5\u30fc\u540d\u3067\u3059\u3002';
exports.Exp_SlicerNameExist = '\u305d\u306e\u30b9\u30e9\u30a4\u30b5\u30fc\u540d\u306f\u65e2\u306b\u4f7f\u7528\u3055\u308c\u3066\u3044\u307e\u3059\u3002\u65b0\u3057\u3044\u30b9\u30e9\u30a4\u30b5\u30fc\u540d\u3092\u5165\u529b\u3057\u3066\u304f\u3060\u3055\u3044\u3002';
exports.SlicerNameInFormula = "\u30b9\u30e9\u30a4\u30b5\u30fc";


/***/ }),

/***/ "./dist/plugins/statusBar/statusBar.res.ja.js":
/*!****************************************************!*\
  !*** ./dist/plugins/statusBar/statusBar.res.ja.js ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.cellMode = '\u30bb\u30eb \u30e2\u30fc\u30c9';
exports.cellModeReady = '\u6e96\u5099\u5b8c\u4e86';
exports.cellModeEnter = '\u5165\u529b';
exports.cellModeEdit = '\u7de8\u96c6';
exports.formulaAverage = '\u5e73\u5747';
exports.formulaCount = '\u30c7\u30fc\u30bf\u306e\u500b\u6570';
exports.formulaNumericalCount = '\u6570\u5024\u306e\u500b\u6570';
exports.formulaMin = '\u6700\u5c0f\u5024';
exports.formulaMax = '\u6700\u5927\u5024';
exports.formulaSum = '\u5408\u8a08';
exports.zoomSlider = '\u30ba\u30fc\u30e0 \u30b9\u30e9\u30a4\u30c0\u30fc';
exports.zoom = '\u30ba\u30fc\u30e0';
exports.toolTipCellMode = '{0} \u30e2\u30fc\u30c9';
exports.toolTipFormulaAverage = '\u9078\u629e\u3055\u308c\u3066\u3044\u308b\u30bb\u30eb\u306e\u5e73\u5747';
exports.toolTipFormulaCount = '\u9078\u629e\u3055\u308c\u3066\u3044\u308b\u30bb\u30eb\u306e\u3046\u3061\u30c7\u30fc\u30bf\u304c\u542b\u307e\u308c\u3066\u3044\u308b\u30bb\u30eb\u306e\u500b\u6570';
exports.toolTipFormulaNumericalCount = '\u9078\u629e\u3055\u308c\u3066\u3044\u308b\u30bb\u30eb\u306e\u3046\u3061\u6570\u5024\u30c7\u30fc\u30bf\u304c\u542b\u307e\u308c\u3066\u3044\u308b\u30bb\u30eb\u306e\u500b\u6570';
exports.toolTipFormulaMin = '\u9078\u629e\u7bc4\u56f2\u5185\u306e\u6700\u5c0f\u5024';
exports.toolTipFormulaMax = '\u9078\u629e\u7bc4\u56f2\u5185\u306e\u6700\u5927\u5024';
exports.toolTipFormulaSum = '\u9078\u629e\u3055\u308c\u3066\u3044\u308b\u30bb\u30eb\u306e\u5408\u8a08';
exports.toolTipZoomOut = '\u7e2e\u5c0f';
exports.toolTipZoomIn = '\u62e1\u5927';
exports.toolTipSlider = '\u30ba\u30fc\u30e0';
exports.toolTipZoomPanel = '\u8868\u793a\u500d\u7387';


/***/ }),

/***/ "./dist/plugins/table/table.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/table/table.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_DragDropShiftTableCell = '\u3053\u306e\u64cd\u4f5c\u306f\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u4e0a\u306e\u30c6\u30fc\u30d6\u30eb\u5185\u3067\u30bb\u30eb\u3092\u30b7\u30d5\u30c8\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u305f\u3081\u8a31\u53ef\u3055\u308c\u307e\u305b\u3093\u3002';
exports.Exp_DragDropChangePartOfTable = '\u64cd\u4f5c\u3092\u5b8c\u4e86\u3067\u304d\u307e\u305b\u3093\u3002\u8a31\u53ef\u3055\u308c\u3066\u3044\u306a\u3044\u65b9\u6cd5\u3067\u30c6\u30fc\u30d6\u30eb\u306e\u884c\u307e\u305f\u306f\u5217\u306e\u4e00\u90e8\u3092\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u307e\u3059\u3002';
exports.Exp_TableEmptyNameError = '\u30c6\u30fc\u30d6\u30eb\u540d\u3092\u7a7a\u306b\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_TableNameInvalid = '\u6b63\u3057\u304f\u306a\u3044\u30c6\u30fc\u30d6\u30eb\u540d\u3067\u3059\u3002';
exports.Exp_TableInvalidRow = '\u7121\u52b9\u306a\u884c\u30a4\u30f3\u30c7\u30c3\u30af\u30b9\u3082\u3057\u304f\u306f\u884c\u6570\u3067\u3059\u3002';
exports.Exp_TableInvalidColumn = '\u7121\u52b9\u306a\u5217\u30a4\u30f3\u30c7\u30c3\u30af\u30b9\u3082\u3057\u304f\u306f\u5217\u6570\u3067\u3059\u3002';
exports.Exp_TableIntersectError = '\u30c6\u30fc\u30d6\u30eb\u3092\u91cd\u306d\u5408\u308f\u305b\u308b\u3053\u3068\u306f\u51fa\u6765\u307e\u305b\u3093\u3002';
exports.Exp_TableHasSameNameError = '\u3059\u3067\u306b\u540c\u540d\u306e\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u304c\u5b58\u5728\u3057\u307e\u3059\u3002';
exports.Exp_TableRangeHasFilterError = 'The current table range contains a filter range.';
exports.Exp_TableDataSourceNullError = '\u30c6\u30fc\u30d6\u30eb\u306e\u30c7\u30fc\u30bf\u30bd\u30fc\u30b9\u3092 null \u306b\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_TableMoveOutOfRange = '\u30c6\u30fc\u30d6\u30eb\u3092\u30b7\u30fc\u30c8\u5916\u306b\u79fb\u52d5\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_TableResizeOutOfRange = '\u7121\u52b9\u306a\u884c\u6570\u3001\u5217\u6570\u3067\u3059\u3002\u30c6\u30fc\u30d6\u30eb\u3092\u30b7\u30fc\u30c8\u7bc4\u56f2\u5916\u306b\u30ea\u30b5\u30a4\u30ba\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_ArrayFormulaTable = '\u8907\u6570\u30bb\u30eb\u306e\u914d\u5217\u6570\u5f0f\u306f\u30c6\u30fc\u30d6\u30eb\u3067\u306f\u8a31\u53ef\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002';
exports.Exp_TableResizeInvalidRange = '\u30c6\u30fc\u30d6\u30eb\u306e\u898b\u51fa\u3057\u306e\u4f4d\u7f6e\u306f\u5909\u66f4\u3067\u304d\u307e\u305b\u3093\u3002\u307e\u305f\u3001\u65b0\u3057\u3044\u30c6\u30fc\u30d6\u30eb\u7bc4\u56f2\u304c\u5143\u306e\u30c6\u30fc\u30d6\u30eb\u7bc4\u56f2\u306b\u91cd\u306a\u308b\u3088\u3046\u306b\u3057\u3066\u304f\u3060\u3055\u3044\u3002';
exports.Exp_TableResizeToSpan = '\u64cd\u4f5c\u3092\u5b8c\u4e86\u3067\u304d\u307e\u305b\u3093\uff1a\u30c6\u30fc\u30d6\u30eb\u306f\u7d50\u5408\u30bb\u30eb\u3084\u30c6\u30fc\u30d6\u30eb\u3068\u91cd\u306d\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_TableResizeWithFilter = '\u30d5\u30a3\u30eb\u30bf\u30fc\u51e6\u7406\u3055\u308c\u305f\u7bc4\u56f2\u307e\u305f\u306f\u30c6\u30fc\u30d6\u30eb\u5185\u306e\u30bb\u30eb\u306f\u79fb\u52d5\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_TableResizeWithHidden = "\u975e\u8868\u793a\u306e\u884c\u307e\u305f\u306f\u5217\u306b\u96a3\u63a5\u3059\u308b\u30c6\u30fc\u30d6\u30eb\u306e\u30b5\u30a4\u30ba\u3092\u5909\u66f4\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093\u3002";
exports.Exp_TableResizeToTable = '\u64cd\u4f5c\u3092\u5b8c\u4e86\u3067\u304d\u307e\u305b\u3093\uff1a\u30c6\u30fc\u30d6\u30eb\u7bc4\u56f2\u306e\u5185\u5074\u3068\u5916\u5074\u306e\u4e21\u65b9\u306b\u30bb\u30eb\u3092\u542b\u3080\u64cd\u4f5c\u304a\u3088\u3073\u8907\u6570\u306e\u30c6\u30fc\u30d6\u30eb\u306b\u91cd\u306a\u308b\u30bb\u30eb\u306b\u5f71\u97ff\u3059\u308b\u64cd\u4f5c\u306f\u5b9f\u884c\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_RowParamOutOfRange = '\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u306e\u884c\u306f\u30c6\u30fc\u30d6\u30eb\u306e\u7bc4\u56f2\u5916\u3067\u3059\u3002';
exports.Exp_ColParamOutOfRange = '\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u306e\u5217\u884c\u306f\u30c6\u30fc\u30d6\u30eb\u306e\u7bc4\u56f2\u5916\u3067\u3059\u3002';
exports.Exp_TableDeleteCountInvalid = '\u524a\u9664\u3059\u308b\u500b\u6570\u306b\u3088\u308a\u7a7a\u306e\u30c6\u30fc\u30d6\u30eb\u304c\u767a\u751f\u3057\u307e\u3059\u3002';
exports.Exp_TableAddRowIntersectSpan = "\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u4e0a\u306e\u30b9\u30d1\u30f3\u5185\u306e\u30bb\u30eb\u79fb\u52d5\u304c\u767a\u751f\u3059\u308b\u305f\u3081\u5b9f\u884c\u3067\u304d\u307e\u305b\u3093\u3002";
exports.Exp_TableAddRowIntersectTable = "\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u4e0a\u306e\u30c6\u30fc\u30d6\u30eb\u306e\u30bb\u30eb\u79fb\u52d5\u304c\u767a\u751f\u3059\u308b\u305f\u3081\u5b9f\u884c\u3067\u304d\u307e\u305b\u3093\u3002";
exports.Exp_TableAddRowNoEnoughRoom = '\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u306e\u6700\u5f8c\u306b\u7a7a\u3067\u306a\u3044\u30bb\u30eb\u304c\u3042\u308b\u305f\u3081\u3001\u65b0\u3057\u3044\u30bb\u30eb\u3092\u633f\u5165\u3067\u304d\u307e\u305b\u3093\u3002\u305d\u306e\u30bb\u30eb\u306b\u7a7a\u767d\u306e\u5024\u3001\u66f8\u5f0f\u8a2d\u5b9a\u3001\u6570\u5f0f\u306a\u3069\u304c\u8a2d\u5b9a\u3055\u308c\u3066\u3044\u308b\u5834\u5408\u304c\u3042\u308a\u307e\u3059\u3002 \u633f\u5165\u3059\u308b\u30b9\u30da\u30fc\u30b9\u3092\u78ba\u4fdd\u3059\u308b\u305f\u3081\u306b\u5341\u5206\u306a\u884c\u307e\u305f\u306f\u5217\u3092\u524a\u9664\u3057\u3066\u304b\u3089\u518d\u8a66\u884c\u3057\u3066\u304f\u3060\u3055\u3044\u3002';
exports.Table_Total = '\u96c6\u8a08';
exports.Table_None = '\u306a\u3057';
exports.Table_Average = '\u5e73\u5747';
exports.Table_Count = '\u500b\u6570';
exports.Table_Count_Numbers = '\u6570\u5024\u306e\u500b\u6570';
exports.Table_Max = '\u6700\u5927';
exports.Table_Min = '\u6700\u5c0f';
exports.Table_Sum = '\u5408\u8a08';
exports.Table_StdDev = '\u6a19\u672c\u6a19\u6e96\u504f\u5dee';
exports.Table_Var = '\u6a19\u672c\u5206\u6563';


/***/ }),

/***/ "./dist/plugins/tableSheet/tableSheet.res.ja.js":
/*!******************************************************!*\
  !*** ./dist/plugins/tableSheet/tableSheet.res.ja.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.EXP_TooManyPinRecords = "\u30d4\u30f3\u7559\u3081\u53ef\u80fd\u306a\u30c7\u30fc\u30bf\u306f10\u4ef6\u307e\u3067\u3067\u3059\u3002";
exports.GroupPanelTip = "\u3053\u3053\u306b\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u30b0\u30eb\u30fc\u30d7\u5316\u3059\u308b";
exports.GroupPanelFieldsHeader = "\u30d5\u30a3\u30fc\u30eb\u30c9";
exports.GroupPanelGroupsHeader = "\u30b0\u30eb\u30fc\u30d7";
exports.GroupPanelItemRemove = "\u524a\u9664";
exports.GroupPanelItemAddCalculation = "\u30b5\u30de\u30ea\u30fc\u30d5\u30a3\u30fc\u30eb\u30c9\u306e\u8ffd\u52a0";
exports.GroupPanelDropDownCalcField = "\u30d5\u30a3\u30fc\u30eb\u30c9";
exports.GroupPanelSummaryLabelFormula = "\u6570\u5f0f";
exports.GroupPanelSummaryLabelCaption = "\u30ad\u30e3\u30d7\u30b7\u30e7\u30f3";
exports.GroupPanelSummaryLabelSlice = "\u30b9\u30e9\u30a4\u30b9";
exports.GroupPanelItemRemoveAll = "\u3059\u3079\u3066\u524a\u9664";
exports.GroupPanelAddCalculateColumn = "\u96c6\u8a08\u30d5\u30a3\u30fc\u30eb\u30c9\u3092\u8ffd\u52a0";
exports.StatusBarRowCount = "\u884c\u6570";
exports.StatusBarToolTipRowCount = '\u9078\u629e\u3055\u308c\u305f\u884c\u306e\u6570';
exports.CrossColumnCrossHeader = "\u30af\u30ed\u30b9";
exports.CrossColumnDetailFormatter = "\u30d5\u30a9\u30fc\u30de\u30c3\u30bf";
exports.CrossColumnDetailName = "\u540d\u524d";
exports.CrossColumnDetailValue = "\u5024";
exports.CrossColumnDetailValuePlaceHolder = "\u3053\u3053\u306b\u9805\u76ee\u3092\u30c9\u30e9\u30c3\u30b0\u3057\u3066\u5024\u3092\u8a2d\u5b9a\u3057\u307e\u3059";
exports.CrossColumnDetailOver = "\u30aa\u30fc\u30d0\u30fc";
exports.CrossColumnDetailCaption = "\u30ad\u30e3\u30d7\u30b7\u30e7\u30f3";
exports.CrossColumnDetailValueHeader = "\u30af\u30ed\u30b9\u5024\u30d8\u30c3\u30c0\u3092\u8868\u793a\u3059\u308b";
exports.CrossColumnDetailFilter = "\u30d5\u30a3\u30eb\u30bf\u30fc";
exports.CrossColumnDetailAttributes = "\u5c5e\u6027";
exports.CrossColumnDetailList = "\u30ea\u30b9\u30c8";
exports.Exp_InvalidOperationInProtectForTableSheet = "\u5909\u66f4\u3057\u3088\u3046\u3068\u3057\u3066\u3044\u308b\u7bc4\u56f2\u306f\u30ed\u30c3\u30af\u3055\u308c\u3066\u3044\u307e\u3059\u3002";


/***/ }),

/***/ "./dist/plugins/touch/touch.res.ja.js":
/*!********************************************!*\
  !*** ./dist/plugins/touch/touch.res.ja.js ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.ToolStrip_PasteText = '\u8cbc\u308a\u4ed8\u3051';
exports.ToolStrip_CutText = '\u5207\u308a\u53d6\u308a';
exports.ToolStrip_CopyText = '\u30b3\u30d4\u30fc';
exports.ToolStrip_AutoFillText = '\u30aa\u30fc\u30c8\u30d5\u30a3\u30eb';


/***/ }),

/***/ "./node_modules_local/@grapecity/js-calc/dist/gc.spread.calcengine.resources.ja.js":
/*!*****************************************************************************************!*\
  !*** ./node_modules_local/@grapecity/js-calc/dist/gc.spread.calcengine.resources.ja.js ***!
  \*****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

var GC = typeof GC === "object" ? GC : {}; GC["Spread"] = GC["Spread"] || {}; GC["Spread"]["CalcEngine"] =
 (function(modules) {
 
 	var installedModules = {};
 
 	function __webpack_require__(moduleId) {
 	
 		if(installedModules[moduleId]) {
 			return installedModules[moduleId].exports;
 		}
 	
 		var module = installedModules[moduleId] = {
 			i: moduleId,
 			l: false,
 			exports: {}
 		};
 	
 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
 	
 		module.l = true;
 	
 		return module.exports;
 	}
 
 	__webpack_require__.m = modules;
 
 	__webpack_require__.c = installedModules;
 
 	__webpack_require__.d = function(exports, name, getter) {
 		if(!__webpack_require__.o(exports, name)) {
 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
 		}
 	};
 
 	__webpack_require__.r = function(exports) {
 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
 		}
 		Object.defineProperty(exports, '__esModule', { value: true });
 	};
 
 
 
 
 
 	__webpack_require__.t = function(value, mode) {
 		if(mode & 1) value = __webpack_require__(value);
 		if(mode & 8) return value;
 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
 		var ns = Object.create(null);
 		__webpack_require__.r(ns);
 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
 		return ns;
 	};
 
 	__webpack_require__.n = function(module) {
 		var getter = module && module.__esModule ?
 			function getDefault() { return module['default']; } :
 			function getModuleExports() { return module; };
 		__webpack_require__.d(getter, 'a', getter);
 		return getter;
 	};
 
 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
 
 	__webpack_require__.p = "/assets/";
 
 	return __webpack_require__(__webpack_require__.s = "./src/resource.ja.entry.ts");
 })
 ({

 "./src/calcEngine.res.ja.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
function functionDescription(description, parameters) {
    return {
        description: description,
        parameters: parameters
    };
}
function parameterDescription(name, repeatable) {
    return {
        name: name,
        repeatable: repeatable
    };
}
var START_NUMBER = '[start_num]';
var SIGNIFICANCE = '[significance]';
var MATCH_MODE = '[match_mode]';
var IF_NOT_FOUND = '[if_not_found]';
var PAD_WITH = '[pad_with]';
exports.resource = {
    Exp_InvalidCast: '\u4f8b\u5916:\u7121\u52b9\u306a\u30ad\u30e3\u30b9\u30c8',
    Exp_FormulaInvalidChar: '\u5165\u529b\u3055\u308c\u305f\u6570\u5f0f\u306f\u7121\u52b9\u306a\u6587\u5b57\u3092\u542b\u3093\u3067\u3044\u307e\u3059 : \'{0}\' \u8a72\u5f53\u3059\u308b\u30a4\u30f3\u30c7\u30c3\u30af\u30b9 : {1}',
    Exp_FormulaInvalid: '\u7121\u52b9\u306a\u516c\u5f0f',
    Exp_InvalidFunctionName: '\u7121\u52b9\u306a\u95a2\u6570\u540d',
    Exp_InvalidOverrideFunction: '\u7d44\u8fbc\u95a2\u6570\u3092\u30aa\u30fc\u30d0\u30fc\u30e9\u30a4\u30c9\u3059\u308b\u3053\u3068\u306f\u3067\u304d\u307e\u305b\u3093',
    Exp_InvalidArray: '\u7121\u52b9\u306a\u914d\u5217 : ',
    Exp_OverrideNotAllowed: '\u8a31\u53ef\u3055\u308c\u3066\u3044\u306a\u3044\u95a2\u6570\u30aa\u30fc\u30d0\u30fc\u30e9\u30a4\u30c9\u306e\u5b9f\u884c\u3067\u3059',
    Exp_NoSyntax: '\u69cb\u6587 \'{0}\' \u306f\u6b21\u306e\u69cb\u6587 \'{1}\' \u3068\u30de\u30c3\u30c1\u3057\u307e\u305b\u3093\u3067\u3057\u305f\u3002',
    Exp_IsValid: '\'{0}\' \u306f\u7121\u52b9\u3067\u3059\u3002',
    Exp_InvalidParameters: '\u7121\u52b9\u306a\u30d1\u30e9\u30e1\u30fc\u30bf\u306e\u691c\u51fa : {0}\u3002',
    Exp_InvalidArrayColumns: '\u914d\u5217\u306e\u30ab\u30e9\u30e0\u9577\u304c\u4e00\u81f4\u3057\u307e\u305b\u3093 \u4f4d\u7f6e : {0}\u3002',
    Exp_ExprIsNull: '\u5f15\u6570 \'expr\' \u304c null \u3067\u3059',
    Exp_InvalidOperation: '\u7121\u52b9\u306a\u64cd\u4f5c\u306b\u3088\u308b\u4f8b\u5916',
    Exp_ArgumentNull: 'null \u5f15\u6570\u306b\u3088\u308b\u4f8b\u5916',
    Exp_CriteriaIsNull: '\u6761\u4ef6\u3068\u306a\u308b\u5f15\u6570\u304c null \u3067\u3059',
    Exp_Format: '\u30d5\u30a9\u30fc\u30de\u30c3\u30c8',
    Exp_ArrayFormulaPart: '\u914d\u5217\u306e\u4e00\u90e8\u3092\u5909\u66f4\u3067\u304d\u307e\u305b\u3093\u3002',
    Exp_NotSupported: '\u4f8b\u5916:\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u6a5f\u80fd\u306e\u5229\u7528\u3092\u8a66\u307f\u307e\u3057\u305f',
    Fbx_Summary: '\u6982\u8981',
    Fbx_TableName_Description: '\u30c6\u30fc\u30d6\u30eb\u540d ',
    Fbx_TableSheetName_Description: '\u30c6\u30fc\u30d6\u30eb\u30b7\u30fc\u30c8\u540d ',
    Fbx_CustomName_Description: '\u30ab\u30b9\u30bf\u30e0\u540d ',
    Exp_DuplicatedChar: "\u91cd\u8907\u3057\u305f\u30ad\u30e3\u30e9\u30af\u30bf\u30fc",
    Exp_ArgumentOutOfRangeException: "\u5f15\u6570\u304c\u7bc4\u56f2\u5916\u306e\u4f8b\u5916",
    Exp_ArgumentException: "\u5f15\u6570\u306e\u4f8b\u5916",
    _builtInFunctionsResource: {
        'ABS': functionDescription('\u6307\u5b9a\u3057\u305f\u5024\u306e\u7d76\u5bfe\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ACCRINT': functionDescription('\u5b9a\u671f\u7684\u306b\u5229\u606f\u304c\u652f\u6255\u308f\u308c\u308b\u8a3c\u5238\u306e\u672a\u53ce\u5229\u606f\u984d\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('issue'),
            parameterDescription('first_interest'),
            parameterDescription('settlement'),
            parameterDescription('rate'),
            parameterDescription('par'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ACCRINTM': functionDescription('\u6e80\u671f\u306b\u5229\u606f\u304c\u652f\u6255\u308f\u308c\u308b\u8a3c\u5238\u306e\u672a\u53ce\u5229\u606f\u984d\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('issue'),
            parameterDescription('settlement'),
            parameterDescription('rate'),
            parameterDescription('par'),
            parameterDescription('basis')
        ]),
        'ACOS': functionDescription('\u6307\u5b9a\u306e\u5024\u304c\u30b3\u30b5\u30a4\u30f3\u3068\u306a\u308b\u89d2\u5ea6\u3092\u8fd4\u3057\u307e\u3059\u3002\u623b\u308a\u5024\u306e\u89d2\u5ea6\u306f 0\uff5e\u03c0\uff08\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\uff09\u3067\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ACOSH': functionDescription('\u6307\u5b9a\u3057\u305f\u89d2\u5ea6\u306e\u30cf\u30a4\u30d1\u30fc\u30dc\u30ea\u30c3\u30af\u30b3\u30b5\u30a4\u30f3\u306e\u9006\u95a2\u6570\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ADDRESS': functionDescription('\u6307\u5b9a\u306e\u884c\u756a\u53f7\u304a\u3088\u3073\u5217\u756a\u53f7\u306b\u57fa\u3065\u304d\u3001\u30bb\u30eb \u30a2\u30c9\u30ec\u30b9\u3092\u8868\u3059\u30c6\u30ad\u30b9\u30c8\u3092\u751f\u6210\u3057\u307e\u3059\u3002', [
            parameterDescription('row_num'),
            parameterDescription('column_num'),
            parameterDescription('abs_num'),
            parameterDescription('a1style'),
            parameterDescription('sheet_text')
        ]),
        'AGGREGATE': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u7d44\u307f\u8fbc\u307f\u95a2\u6570\u3092\u4f7f\u7528\u3057\u3066\u6570\u5024\u306e\u30ea\u30b9\u30c8\u3092\u96c6\u8a08\u3057\u307e\u3059\u3002', [
            parameterDescription('function_num'),
            parameterDescription('options'),
            parameterDescription('ref1'),
            parameterDescription('ref2', true)
        ]),
        'AMORDEGRC': functionDescription('\u65e5\u5272\u308a\u8a08\u7b97\u306b\u3088\u308b\u6e1b\u4fa1\u511f\u5374\u3092\u8003\u616e\u3057\u3001\u8cc7\u7523\u8010\u7528\u5e74\u6570\u306b\u57fa\u3065\u304f\u6e1b\u4fa1\u511f\u5374\u4fc2\u6570\u3092\u8a08\u7b97\u306b\u9069\u7528\u3057\u3066\u3001\u4f1a\u8a08\u671f\u3054\u3068\u306e\u6e1b\u4fa1\u511f\u5374\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('date_purchased'),
            parameterDescription('first_period'),
            parameterDescription('salvage'),
            parameterDescription('period'),
            parameterDescription('rate'),
            parameterDescription('basis')
        ]),
        'AMORLINC': functionDescription('\u65e5\u5272\u308a\u8a08\u7b97\u306b\u3088\u308b\u6e1b\u4fa1\u6d88\u5374\u3092\u8003\u616e\u3057\u3001\u6307\u5b9a\u306e\u4f1a\u8a08\u671f\u306e\u6e1b\u4fa1\u511f\u5374\u8cbb\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('date_purchased'),
            parameterDescription('first_period'),
            parameterDescription('salvage'),
            parameterDescription('period'),
            parameterDescription('rate'),
            parameterDescription('basis')
        ]),
        'AND': functionDescription('\u3059\u3079\u3066\u306e\u5f15\u6570\u304c\u771f\u3067\u3042\u308c\u3070 True \u3092\u3001\uff11\u3064\u4ee5\u4e0a\u306e\u5f15\u6570\u304c\u507d\u3067\u3042\u308c\u3070 False \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('logical1'),
            parameterDescription('logical2')
        ]),
        'ASIN': functionDescription('\u6307\u5b9a\u306e\u5024\u304c\u30b5\u30a4\u30f3\u3068\u306a\u308b\u89d2\u5ea6\u3092\u8fd4\u3057\u307e\u3059\u3002\u623b\u308a\u5024\u306e\u89d2\u5ea6\u306f -\u03c0/2\uff5e\u03c0/2\uff08\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\uff09\u3067\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ASINH': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u30cf\u30a4\u30d1\u30fc\u30dc\u30ea\u30c3\u30af\u30b5\u30a4\u30f3\u306e\u9006\u95a2\u6570\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ATAN': functionDescription('\u6307\u5b9a\u306e\u5024\u304c\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u3068\u306a\u308b\u89d2\u5ea6\u3092\u8fd4\u3057\u307e\u3059\u3002\u623b\u308a\u5024\u306e\u89d2\u5ea6\u306f -\u03c0/2\uff5e\u03c0/2\uff08\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\uff09\u3067\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ATAN2': functionDescription('\u6307\u5b9a\u306e x \u5ea7\u6a19\u304a\u3088\u3073 y \u5ea7\u6a19\u306e\u30a2\u30fc\u30af\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002\u623b\u308a\u5024\u306e\u89d2\u5ea6\u306f -\u03c0\uff5e\u03c0\uff08\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\u3001\u305f\u3060\u3057 -\u03c0 \u3092\u9664\u304f\uff09\u3067\u3059\u3002', [
            parameterDescription('x_num'),
            parameterDescription('y_num')
        ]),
        'ATANH': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u30cf\u30a4\u30d1\u30fc\u30dc\u30ea\u30c3\u30af\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u306e\u9006\u95a2\u6570\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'AVEDEV': functionDescription('\u6307\u5b9a\u306e\u30c7\u30fc\u30bf\u5168\u4f53\u306e\u5e73\u5747\u5024\u306b\u5bfe\u3059\u308b\u3001\u500b\u3005\u306e\u5024\u306e\u7d76\u5bfe\u504f\u5dee\u306e\u5e73\u5747\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'AVERAGE': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u5e73\u5747\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true),
        ]),
        'AVERAGEA': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u5e73\u5747\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'AVERAGEIF': functionDescription('\u6307\u5b9a\u3057\u305f\u57fa\u6e96\u3092\u6e80\u305f\u3059\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u306e\u5e73\u5747\u5024\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('range'),
            parameterDescription('criteria'),
            parameterDescription('[average_range]')
        ]),
        'AVERAGEIFS': functionDescription('\u6307\u5b9a\u3057\u305f\u8907\u6570\u306e\u57fa\u6e96\u3092\u6e80\u305f\u3059\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u306e\u5e73\u5747\u5024\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('average_range'),
            parameterDescription('criteria_range1', true),
            parameterDescription('criteria1', true),
        ]),
        'BESSELI': functionDescription('\u7d14\u865a\u6570\u3092\u5f15\u6570\u3068\u3057\u305f\u3068\u304d\u306e\u7b2c\uff11\u7a2e\u5909\u5f62\u30d9\u30c3\u30bb\u30eb\u95a2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELJ': functionDescription('\u7b2c\uff11\u7a2e\u30d9\u30c3\u30bb\u30eb\u95a2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELK': functionDescription('\u7d14\u865a\u6570\u3092\u5f15\u6570\u3068\u3057\u305f\u3068\u304d\u306e\u7b2c\uff12\u7a2e\u5909\u5f62\u30d9\u30c3\u30bb\u30eb\u95a2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BESSELY': functionDescription('\u7b2c\uff12\u7a2e\u30d9\u30c3\u30bb\u30eb\u95a2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('order')
        ]),
        'BETADIST': functionDescription('\u7d2f\u7a4d\u03b2\u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'BETAINV': functionDescription('\u7d2f\u7a4d\u03b2\u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'BIN2DEC': functionDescription('\uff12\u9032\u6570\u5024\u3092 10 \u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'BIN2HEX': functionDescription('\uff12\u9032\u6570\u5024\u3092 16 \u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'BIN2OCT': functionDescription('\uff12\u9032\u6570\u5024\u3092\uff18\u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'BINOMDIST': functionDescription('\u500b\u5225\u9805\u306e\u4e8c\u9805\u5206\u5e03\u306e\u78ba\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number_s'),
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'CEILING': functionDescription('\u6307\u5b9a\u3057\u305f\u57fa\u6e96\u5024\u306e\u500d\u6570\u306b\u306a\u308b\u3088\u3046\u306b\u6570\u5024\u3092\u4e38\u3081\u3001\u5143\u306e\u5024\u306b\u6700\u3082\u8fd1\u3044\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('significance')
        ]),
        'CHAR': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306b\u5bfe\u5fdc\u3059\u308b\u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'CHIDIST': functionDescription('\u7247\u5074\u30ab\u30a4\uff12\u4e57\u5206\u5e03\u306e\u78ba\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom')
        ]),
        'CHIINV': functionDescription('\u7247\u5074\u30ab\u30a4\uff12\u4e57\u5206\u5e03\u78ba\u7387\u306e\u9006\u95a2\u6570\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHITEST': functionDescription('\u30ab\u30a4\uff12\u4e57\u5206\u5e03\u304b\u3089\u306e\u72ec\u7acb\u6027\u3092\u691c\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('actual_range'),
            parameterDescription('expected_range')
        ]),
        'CHOOSE': functionDescription('\u5024\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u7279\u5b9a\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('index_num'),
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'CLEAN': functionDescription('\u6307\u5b9a\u306e\u30c6\u30ad\u30b9\u30c8\u304b\u3089\u3001\u5370\u5237\u3067\u304d\u306a\u3044\u3059\u3079\u3066\u306e\u6587\u5b57\u3092\u524a\u9664\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'CODE': functionDescription('\u30c6\u30ad\u30b9\u30c8\u5185\u306e\u5148\u982d\u6587\u5b57\u306b\u5bfe\u5fdc\u3059\u308b\u6570\u5024\u30b3\u30fc\u30c9\u3092\u8fd4\u3057\u307e\u3059\u3002\u8fd4\u3055\u308c\u308b\u30b3\u30fc\u30c9\u306f\u3001Unicode\u3067\u3059\u3002', [
            parameterDescription('text')
        ]),
        'COLUMN': functionDescription('\u53c2\u7167\u306e\u5217\u756a\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('reference')
        ]),
        'COLUMNS': functionDescription('\u914d\u5217\u5185\u306e\u5217\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array')
        ]),
        'COMBIN': functionDescription('\u7dcf\u6570\u304b\u3089\u6307\u5b9a\u306e\u500b\u6570\u3092\u629c\u304d\u53d6\u308b\u5834\u5408\u3001\u9078\u629e\u53ef\u80fd\u306a\u7d44\u307f\u5408\u308f\u305b\u306e\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'COMPLEX': functionDescription('\u5b9f\u6570\u4fc2\u6570\u304a\u3088\u3073\u865a\u6570\u4fc2\u6570\u3092\u8907\u7d20\u6570\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('real_num'),
            parameterDescription('image_num'),
            parameterDescription('suffix')
        ]),
        'CONCATENATE': functionDescription('\u6307\u5b9a\u306e\u6587\u5b57\u5217\u307e\u305f\u306f\u6570\u5024\u3092\uff11\u3064\u306e\u6587\u5b57\u5217\u306b\u7d71\u5408\u3057\u307e\u3059\u3002', [
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'CONFIDENCE': functionDescription('\u6bcd\u96c6\u56e3\u306e\u5e73\u5747\u5024\u306b\u5bfe\u3059\u308b\u4fe1\u983c\u533a\u9593\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'CONVERT': functionDescription('\u3042\u308b\u8a08\u6e2c\u5358\u4f4d\u306e\u5024\u3092\u3001\u5225\u306e\u8a08\u6e2c\u5358\u4f4d\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('from_unit'),
            parameterDescription('to_unit')
        ]),
        'CORREL': functionDescription('\uff12\u7d44\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u9593\u306e\u76f8\u95a2\u4fc2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'COS': functionDescription('\u6307\u5b9a\u3057\u305f\u89d2\u5ea6\u306e\u30b3\u30b5\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'COSH': functionDescription('\u6307\u5b9a\u3057\u305f\u89d2\u5ea6\u306e\u30cf\u30a4\u30d1\u30fc\u30dc\u30ea\u30c3\u30af\u30b3\u30b5\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'COUNT': functionDescription('\u6307\u5b9a\u3057\u305f\u8907\u6570\u306e\u5024\u306b\u6570\u5024\u304c\u4f55\u500b\u542b\u307e\u308c\u3066\u3044\u308b\u304b\u500b\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'COUNTA': functionDescription('\u6307\u5b9a\u3057\u305f\u8907\u6570\u306e\u5024\u306b\u6570\u5024\u304c\u4f55\u500b\u542b\u307e\u308c\u3066\u3044\u308b\u304b\u500b\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'COUNTBLANK': functionDescription('\u30b7\u30fc\u30c8\u4e0a\u306e\u6307\u5b9a\u306e\u30bb\u30eb\u7bc4\u56f2\u304b\u3089\u3001\u7a7a\u767d\u30bb\u30eb\u306e\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('cellrange')
        ]),
        'COUNTIF': functionDescription('\u7279\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u30bb\u30eb\u306e\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('cellrange'),
            parameterDescription('criteria')
        ]),
        'COUNTIFS': functionDescription('\u8907\u6570\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u30bb\u30eb\u306e\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('criteria_range1', true),
            parameterDescription('criteria1', true)
        ]),
        'COUPDAYBS': functionDescription('\u8a3c\u5238\u306e\u5229\u6255\u671f\u9593\u306e\uff11\u65e5\u76ee\u304b\u3089\u53d7\u6e21\u65e5\u307e\u3067\u306e\u65e5\u6570\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPDAYS': functionDescription('\u8a3c\u5238\u306e\u5229\u6255\u671f\u9593\uff08\u53d7\u6e21\u65e5\u3092\u542b\u3080\uff09\u3092\u8868\u3059\u65e5\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPDAYSNC': functionDescription('\u8a3c\u5238\u306e\u53d7\u6e21\u65e5\u304b\u3089\u6b21\u306e\u5229\u6255\u65e5\u307e\u3067\u306e\u65e5\u6570\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPNCD': functionDescription('\u8a3c\u5238\u306e\u53d7\u6e21\u5f8c\u306e\u6b21\u56de\u306e\u5229\u6255\u65e5\u3092\u65e5\u4ed8\u5024\u3067\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPNUM': functionDescription('\u8a3c\u5238\u306e\u53d7\u6e21\u65e5\u304b\u3089\u6e80\u671f\u65e5\u307e\u3067\u306e\u671f\u9593\u4e2d\u306e\u5229\u6255\u56de\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COUPPCD': functionDescription('\u8a3c\u5238\u306e\u53d7\u6e21\u65e5\u76f4\u524d\u306e\u5229\u6255\u65e5\u3092\u65e5\u4ed8\u5024\u3067\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'COVAR': functionDescription('\uff12\u7d44\u306e\u5bfe\u5fdc\u3059\u308b\u30c7\u30fc\u30bf\u306e\u6a19\u6e96\u504f\u5dee\u306e\u7a4d\u306e\u5e73\u5747\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'CRITBINOM': functionDescription('\u7d2f\u7a4d\u4e8c\u9805\u5206\u5e03\u306e\u5024\u304c\u57fa\u6e96\u5024\u4ee5\u4e0a\u3068\u306a\u308b\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('alpha')
        ]),
        'CUMIPMT': functionDescription('\u958b\u59cb\u671f\u304b\u3089\u7d42\u4e86\u671f\u307e\u3067\u306e\u671f\u9593\u5185\u3067\u3001\u8cb8\u4ed8\u91d1\u306b\u5bfe\u3057\u3066\u652f\u6255\u308f\u308c\u308b\u5229\u606f\u306e\u7d2f\u8a08\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('paytype')
        ]),
        'CUMPRINC': functionDescription('\u958b\u59cb\u671f\u304b\u3089\u7d42\u4e86\u671f\u307e\u3067\u306e\u671f\u9593\u5185\u3067\u3001\u8cb8\u4ed8\u91d1\u306b\u5bfe\u3057\u3066\u652f\u6255\u308f\u308c\u308b\u5143\u91d1\u306e\u7d2f\u8a08\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('paytype')
        ]),
        'DATE': functionDescription('\u5e74\u3001\u6708\u3001\u65e5\u3067\u6307\u5b9a\u3057\u305f\u65e5\u4ed8\u306b\u5bfe\u3059\u308b\u65e5\u4ed8\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('year'),
            parameterDescription('month'),
            parameterDescription('day')
        ]),
        'DATEDIF': functionDescription('\uff12\u3064\u306e\u65e5\u4ed8\u9593\u306e\u65e5\u6570\u3001\u6708\u6570\u3001\u307e\u305f\u306f\u5e74\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('unit')
        ]),
        'DATEVALUE': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u306b\u5bfe\u3059\u308b\u65e5\u6642\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date_text')
        ]),
        'DAVERAGE': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u5024\u306e\u5e73\u5747\u5024\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DAY': functionDescription('\u7279\u5b9a\u306e\u65e5\u4ed8\u306b\u5bfe\u5fdc\u3059\u308b\u3001\u6708\u5185\u306e\u65e5\uff081\uff5e31\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date')
        ]),
        'DAYS360': functionDescription('\uff11\u5e74\u3092 360 \u65e5\uff08\uff11\u6708\u304c 30 \u65e5\uff09\u3068\u307f\u306a\u3057\u3001\uff12\u3064\u306e\u65e5\u4ed8\u9593\u306e\u65e5\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('method')
        ]),
        'DB': functionDescription('\u5b9a\u7387\u6cd5\u3092\u4f7f\u7528\u3057\u3066\u3001\u7279\u5b9a\u306e\u671f\u306e\u8cc7\u7523\u306e\u6e1b\u4fa1\u511f\u5374\u8cbb\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period'),
            parameterDescription('month')
        ]),
        'DCOUNT': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6570\u5024\u3092\u4fdd\u6301\u3059\u308b\u30bb\u30eb\u6570\u3092\u30ab\u30a6\u30f3\u30c8\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DCOUNTA': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u3001\u7a7a\u767d\u4ee5\u5916\u306e\u30bb\u30eb\u6570\u3092\u30ab\u30a6\u30f3\u30c8\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DDB': functionDescription('\u500d\u7387\u6cd5\u3001\u307e\u305f\u306f\u305d\u306e\u4ed6\u306e\u6307\u5b9a\u306e\u8a08\u7b97\u65b9\u6cd5\u3092\u4f7f\u7528\u3057\u3066\u3001\u7279\u5b9a\u306e\u671f\u306e\u8cc7\u7523\u306e\u6e1b\u4fa1\u511f\u5374\u8cbb\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period'),
            parameterDescription('factor')
        ]),
        'DEC2BIN': functionDescription('10 \u9032\u6570\u5024\u3092\uff12\u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEC2HEX': functionDescription('10 \u9032\u6570\u5024\u3092 16 \u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEC2OCT': functionDescription('10 \u9032\u6570\u5024\u3092\uff18\u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'DEGREES': functionDescription('\u6307\u5b9a\u3057\u305f\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\u306e\u89d2\u5ea6\u306e\u5024\u3092\u5ea6\u5358\u4f4d\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'DELTA': functionDescription('\uff12\u3064\u306e\u5024\u304c\u7b49\u3057\u3044\u304b\u3069\u3046\u304b\u3092\u8abf\u3079\u307e\u3059\u3002\uff12\u3064\u306e\u5024\u304c\u7b49\u3057\u3051\u308c\u3070\uff11\u3001\u305d\u3046\u3067\u306a\u3051\u308c\u3070\uff10\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'DEVSQ': functionDescription('\u5e73\u5747\u5024\u306b\u5bfe\u3059\u308b\u500b\u3005\u306e\u30c7\u30fc\u30bf\u70b9\u306e\u504f\u5dee\u306e\u5e73\u65b9\u548c\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'DGET': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\uff11\u3064\u306e\u5024\u3092\u62bd\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DISC': functionDescription('\u8a3c\u5238\u306e\u5272\u5f15\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'DMAX': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6700\u5927\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DMIN': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DOLLAR': functionDescription('\u6570\u5024\u3092\u6307\u5b9a\u306e\u5c0f\u6570\u4f4d\u306b\u306a\u308b\u3088\u3046\u306b\u56db\u6368\u4e94\u5165\u3057\u3001\u901a\u8ca8\u66f8\u5f0f\u3092\u65bd\u3057\u305f\u6587\u5b57\u5217\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('decimals')
        ]),
        'DOLLARDE': functionDescription('\u5206\u6570\u8868\u8a18\u3055\u308c\u305f\u30c9\u30eb\u5024\u3092\u3001\u5c0f\u6570\u8868\u8a18\u306e\u30c9\u30eb\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('fractional_dollar'),
            parameterDescription('fraction')
        ]),
        'DOLLARFR': functionDescription('\u5c0f\u6570\u8868\u8a18\u3055\u308c\u305f\u30c9\u30eb\u5024\u3092\u3001\u5206\u6570\u8868\u8a18\u306e\u30c9\u30eb\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('decimal_dollar'),
            parameterDescription('fraction')
        ]),
        'DPRODUCT': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u5024\u3092\u4e57\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSTDEV': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3051\u308b\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6570\u5024\u3092\u6a19\u672c\u3068\u3057\u3066\u4f7f\u7528\u3057\u3066\u3001\u6bcd\u96c6\u56e3\u306e\u6a19\u6e96\u504f\u5dee\u3092\u8a55\u4fa1\u3057\u307e\u3059', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSTDEVP': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3051\u308b\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6570\u5024\u3092\u4f7f\u7528\u3057\u3066\u3001\u6bcd\u96c6\u56e3\u5168\u4f53\u306b\u57fa\u3065\u304f\u6a19\u6e96\u504f\u5dee\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DSUM': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3044\u3066\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6570\u5024\u3092\u52a0\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DURATION': functionDescription('\u984d\u9762\u3092 $100 \u3068\u307f\u306a\u3057\u305f\u8a3c\u5238\u306e\u30de\u30b3\u30fc\u30ec\u30fc \u30c7\u30e5\u30ec\u30fc\u30b7\u30e7\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('coupon'),
            parameterDescription('yield'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'DVAR': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3051\u308b\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6570\u5024\u3092\u6a19\u672c\u3068\u3057\u3066\u4f7f\u7528\u3057\u3066\u3001\u6bcd\u96c6\u56e3\u306e\u5206\u6563\u3092\u8a55\u4fa1\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'DVARP': functionDescription('\u30ea\u30b9\u30c8\u307e\u305f\u306f\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u5185\u306e\u6307\u5b9a\u306e\uff11\u5217\u306b\u304a\u3051\u308b\u3001\u6307\u5b9a\u306e\u6761\u4ef6\u3092\u6e80\u305f\u3059\u6570\u5024\u3092\u4f7f\u7528\u3057\u3066\u3001\u6bcd\u96c6\u56e3\u5168\u4f53\u306b\u57fa\u3065\u304f\u5206\u6563\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('database'),
            parameterDescription('field'),
            parameterDescription('criteria')
        ]),
        'EDATE': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u304b\u3089\u3001\u6307\u5b9a\u306e\u6708\u6570\u3060\u3051\u524d\u307e\u305f\u306f\u5f8c\u306e\u65e5\u6642\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('months')
        ]),
        'EFFECT': functionDescription('\u6307\u5b9a\u306e\u540d\u76ee\u5e74\u5229\u7387\u3068\uff11\u5e74\u3042\u305f\u308a\u306e\u8907\u5229\u8a08\u7b97\u671f\u9593\u306b\u57fa\u3065\u304d\u3001\u5b9f\u52b9\u5e74\u5229\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('nominal_rate'),
            parameterDescription('npery')
        ]),
        'EOMONTH': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u304b\u3089\u3001\u6307\u5b9a\u306e\u6708\u6570\u3060\u3051\u524d\u307e\u305f\u306f\u5f8c\u306e\u6708\u306e\u6700\u7d42\u65e5\uff08\u6708\u672b\u65e5\uff09\u3068\u306a\u308b\u65e5\u6642\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('months')
        ]),
        'ERF': functionDescription('\u4e0a\u9650\u304b\u3089\u4e0b\u9650\u306e\u7bc4\u56f2\u3067\u3001\u8aa4\u5dee\u95a2\u6570\u306e\u7a4d\u5206\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lower_limit'),
            parameterDescription('upper_limit')
        ]),
        'ERFC': functionDescription('\u4e0b\u9650\u304b\u3089\u7121\u9650\u5927\u306e\u7bc4\u56f2\u3067\u3001\u76f8\u88dc\u8aa4\u5dee\u95a2\u6570\u306e\u7a4d\u5206\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lowerlimit')
        ]),
        'ERROR.TYPE': functionDescription('\u30a8\u30e9\u30fc\u5024\u306b\u5bfe\u5fdc\u3059\u308b\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002\u30a8\u30e9\u30fc\u304c\u306a\u3044\u5834\u5408\u306f\u3001#N/A \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('error_val')
        ]),
        'EURO': functionDescription('ISO \u901a\u8ca8\u30b3\u30fc\u30c9\u306b\u57fa\u3065\u304d\u3001\uff11\u30e6\u30fc\u30ed\u306b\u76f8\u5f53\u3059\u308b\u901a\u8ca8\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('code')
        ]),
        'EUROCONVERT': functionDescription('\u30e6\u30fc\u30ed\u52a0\u76df\u56fd\u901a\u8ca8\uff08\u30e6\u30fc\u30ed\u3092\u542b\u3080\uff09\u9593\u3067\u3001\u3042\u308b\u901a\u8ca8\u5024\u3092\u5225\u306e\u901a\u8ca8\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('source'),
            parameterDescription('target'),
            parameterDescription('full_precision'),
            parameterDescription('triangulation_precision')
        ]),
        'EVEN': functionDescription('\u6307\u5b9a\u3057\u305f\u5024\u3092\u5207\u308a\u4e0a\u3052\u3001\u6700\u3082\u8fd1\u3044\u5076\u6570\u306e\u6574\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'EXACT': functionDescription('\uff12\u3064\u306e\u6587\u5b57\u5217\u304c\u7b49\u3057\u3051\u308c\u3070 True \u3092\u3001\u305d\u3046\u3067\u306a\u3051\u308c\u3070 False \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text1'),
            parameterDescription('text2')
        ]),
        'EXP': functionDescription('e\uff08\u81ea\u7136\u5bfe\u6570\u306e\u5e95\uff09\u3092\u5e95\u3068\u3059\u308b\u3001\u6307\u5b9a\u306e\u6570\u306e\u3079\u304d\u4e57 (ex) \u3092\u8fd4\u3057\u307e\u3059\u3002EXP \u95a2\u6570\u306f LN \u306e\u9006\u95a2\u6570\u3067\u3059\u3002', [
            parameterDescription('number')
        ]),
        'EXPONDIST': functionDescription('\u6307\u6570\u5206\u5e03\u95a2\u6570\u307e\u305f\u306f\u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('lambda'),
            parameterDescription('cumulative')
        ]),
        'FACT': functionDescription('\u6307\u5b9a\u3057\u305f\u5024\u306e\u968e\u4e57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'FACTDOUBLE': functionDescription('\u6570\u5024\u306e\uff12\u4e57\u968e\u4e57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'FALSE': functionDescription('\u8ad6\u7406\u5024\uff10\uff08False\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', []),
        'FDIST': functionDescription('\uff12\u7d44\u306e\u30c7\u30fc\u30bf\u9593\u306e\u5206\u6563\u5ea6\u3092\u6bd4\u8f03\u3059\u308b F \u78ba\u7387\u5206\u5e03\u95a2\u6570\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'FIND': functionDescription('\u30c6\u30ad\u30b9\u30c8\u5185\u304b\u3089\u6307\u5b9a\u306e\u6587\u5b57\u3092\u691c\u7d22\u3057\u3001\u3053\u306e\u6587\u5b57\u306e\u4f4d\u7f6e\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'FINV': functionDescription('F \u78ba\u7387\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002p = FDIST(x,...) \u3067\u3042\u308b\u3068\u304d\u3001FINV(p,...) = x \u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'FISHER': functionDescription('\u6307\u5b9a\u306e\u5024\u306b\u5bfe\u3059\u308b\u30d5\u30a3\u30c3\u30b7\u30e3\u30fc\u5909\u63db\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'FISHERINV': functionDescription('\u30d5\u30a3\u30c3\u30b7\u30e3\u30fc\u5909\u63db\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'FIXED': functionDescription('\u6570\u5024\u3092\u6307\u5b9a\u306e\u5c0f\u6570\u4f4d\u306b\u306a\u308b\u3088\u3046\u306b\u56db\u6368\u4e94\u5165\u3057\u3001\u30d4\u30ea\u30aa\u30c9\u3068\u30b3\u30f3\u30de\uff08\u6307\u5b9a\u3057\u305f\u5834\u5408\uff09\u306b\u3088\u308b\u5c0f\u6570\u66f8\u5f0f\u3092\u9069\u7528\u3057\u305f\u7d50\u679c\u3092\u30c6\u30ad\u30b9\u30c8\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('decimals'),
            parameterDescription('no_commas')
        ]),
        'FLOOR': functionDescription('\u6307\u5b9a\u3057\u305f\u57fa\u6e96\u5024\u306e\u500d\u6570\u306b\u306a\u308b\u3088\u3046\u306b\u6570\u5024\u3092\u5207\u308a\u6368\u3066\u3001\u5143\u306e\u5024\u306b\u6700\u3082\u8fd1\u3044\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('significance')
        ]),
        'FORECAST': functionDescription('\u65e2\u77e5\u306e\u5024\u3092\u4f7f\u7528\u3057\u3066\u4e88\u6e2c\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('Yarray'),
            parameterDescription('Xarray')
        ]),
        'FREQUENCY': functionDescription('\u6307\u5b9a\u306e\u5024\u7bc4\u56f2\u5185\u3067\u5024\u304c\u51fa\u73fe\u3059\u308b\u983b\u5ea6\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002 \u3053\u306e\u95a2\u6570\u306f\u3001\u6570\u5024\u306e\u5782\u76f4\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('data_array'),
            parameterDescription('bins_array')
        ]),
        'FTEST': functionDescription('F \u691c\u5b9a\u306e\u7d50\u679c\u3092\u8fd4\u3057\u307e\u3059\u3002\u3053\u308c\u306f\u3001\uff12\u3064\u306e\u914d\u5217\u5185\u306e\u30c7\u30fc\u30bf\u306e\u5206\u6563\u306b\u6709\u610f\u306a\u5dee\u304c\u8a8d\u3081\u3089\u308c\u306a\u3044\u7247\u5074\u78ba\u7387\u306e\u7b97\u51fa\u7d50\u679c\u3067\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'FV': functionDescription('\u73fe\u5728\u4fa1\u5024\u3001\u5b9a\u671f\u6255\u3044\u3001\u304a\u3088\u3073\u7279\u5b9a\u306e\u5229\u7387\u3092\u6761\u4ef6\u3068\u3057\u3001\u6295\u8cc7\u306e\u5c06\u6765\u4fa1\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('numper'),
            parameterDescription('paymt'),
            parameterDescription('pval'),
            parameterDescription('type')
        ]),
        'FVSCHEDULE': functionDescription('\u6295\u8cc7\u671f\u9593\u5185\u306e\u4e00\u9023\u306e\u91d1\u5229\u3092\u8907\u5408\u8a08\u7b97\u3059\u308b\u3053\u3068\u306b\u3088\u308a\u3001\u521d\u671f\u6295\u8cc7\uff08\u5143\u91d1\uff09\u306e\u5c06\u6765\u4fa1\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('principal'),
            parameterDescription('schedule')
        ]),
        'GAMMADIST': functionDescription('\u30ac\u30f3\u30de\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'GAMMAINV': functionDescription('\u30ac\u30f3\u30de\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002p = GAMMADIST(x,...) \u3067\u3042\u308b\u3068\u304d\u3001GAMMAINV(p,...) = x \u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta')
        ]),
        'GAMMALN': functionDescription('\u30ac\u30f3\u30de\u95a2\u6570\u306e\u5024\u306e\u81ea\u7136\u5bfe\u6570 (x) \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'GCD': functionDescription('\uff12\u3064\u306e\u6570\u5024\u9593\u306e\u6700\u5927\u516c\u7d04\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002\u6700\u5927\u516c\u7d04\u6570\u3068\u306f\u3001\u5bfe\u8c61\u3068\u306a\u308b\u3059\u3079\u3066\u306e\u5024\u3092\u4f59\u308a\u3092\u51fa\u3055\u305a\u306b\u5272\u308a\u5207\u308b\u3053\u3068\u306e\u3067\u304d\u308b\u6700\u5927\u306e\u6574\u6570\u3067\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'GEOMEAN': functionDescription('\u4e00\u7fa4\u306e\u6b63\u6570\u306e\u76f8\u4e57\u5e73\u5747\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'GESTEP': functionDescription('\u6570\u5024\u304c\u3057\u304d\u3044\u5024\u306b\u7b49\u3057\u3044\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002\u6307\u5b9a\u3057\u305f\u6570\u5024\u304c\u3057\u304d\u3044\u5024\u3068\u7b49\u3057\u3044\u304b\u3001\u305d\u308c\u4ee5\u4e0a\u3067\u3042\u308c\u3070\uff11\u3001\u305d\u3046\u3067\u306a\u3044\u5834\u5408\u306f\uff10\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('step')
        ]),
        'GROWTH': functionDescription('\u4e88\u6e2c\u3055\u308c\u308b\u6307\u6570\u66f2\u7dda\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('newx'),
            parameterDescription('constant')
        ]),
        'HARMEAN': functionDescription('\u4e00\u7fa4\u306e\u6570\u5024\u306e\u8abf\u548c\u5e73\u5747\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'HEX2BIN': functionDescription('16 \u9032\u6570\u5024\u3092\uff12\u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'HEX2DEC': functionDescription('16 \u9032\u6570\u5024\u3092 10 \u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'HEX2OCT': functionDescription('16 \u9032\u6570\u5024\u3092\uff18\u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'HLOOKUP': functionDescription('\u6307\u5b9a\u7bc4\u56f2\u306e\u6700\u4e0a\u884c\u304b\u3089\u5024\u3092\u691c\u7d22\u3057\u3001\u6307\u5b9a\u306e\u884c\u304b\u3089\u3001\u3053\u306e\u5024\u3068\u540c\u3058\u5217\u5185\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('table_array'),
            parameterDescription('row_index_num'),
            parameterDescription('range_lookup')
        ]),
        'HOUR': functionDescription('\u6307\u5b9a\u306e\u6642\u523b\u5024\u306b\u5bfe\u5fdc\u3059\u308b\u6642\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('time')
        ]),
        'HYPGEOMDIST': functionDescription('\u8d85\u5e7e\u4f55\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('sample_s'),
            parameterDescription('number_sample'),
            parameterDescription('population_s'),
            parameterDescription('number_pop')
        ]),
        'IF': functionDescription('\u8ad6\u7406\u5f0f\u306e\u7d50\u679c\u3092\u8868\u3059\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('logical_test'),
            parameterDescription('value_if_true'),
            parameterDescription('value_if_false')
        ]),
        'IFERROR': functionDescription('\u6570\u5f0f\u3092\u8a55\u4fa1\u3057\u3001\u30a8\u30e9\u30fc\u306e\u5834\u5408\u306b\u306f\u6307\u5b9a\u3057\u305f\u5024\u3001\u305d\u306e\u4ed6\u306e\u5834\u5408\u306b\u306f\u6570\u5f0f\u306e\u7d50\u679c\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('value_if_error')
        ]),
        'IMABS': functionDescription('\u8907\u7d20\u6570\u306e\u7d76\u5bfe\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMAGINARY': functionDescription('\u8907\u7d20\u6570\u306e\u865a\u6570\u4fc2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMARGUMENT': functionDescription('\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\u306e\u89d2\u5ea6\u3067\u3042\u308b\u5f15\u6570\u03b8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCONJUGATE': functionDescription('\u8907\u7d20\u6570\u306e\u8907\u7d20\u5171\u5f79\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCOS': functionDescription('\u8907\u7d20\u6570\u306e\u30b3\u30b5\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMDIV': functionDescription('\uff12\u3064\u306e\u8907\u7d20\u6570\u306e\u5546\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum'),
            parameterDescription('complexdenom')
        ]),
        'IMEXP': functionDescription('\u8907\u7d20\u6570\u306e\u6307\u6570\u95a2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMLN': functionDescription('\u8907\u7d20\u6570\u306e\u81ea\u7136\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMLOG2': functionDescription('\u8907\u7d20\u6570\u306e\uff12\u3092\u5e95\u3068\u3059\u308b\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMLOG10': functionDescription('\u8907\u7d20\u6570\u306e\u5e38\u7528\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMPOWER': functionDescription('\u8907\u7d20\u6570\u306e\u6574\u6570\u4e57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum'),
            parameterDescription('powernum')
        ]),
        'IMPRODUCT': functionDescription('"x+yi" \u307e\u305f\u306f "x+yj" \u5f62\u5f0f\u306e\u30c6\u30ad\u30b9\u30c8\u3067\u6307\u5b9a\u3057\u305f\u3001\u6700\u5927 29 \u500b\u306e\u8907\u7d20\u6570\u306e\u7a4d\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2', true)
        ]),
        'IMREAL': functionDescription('"x+yi" \u307e\u305f\u306f "x+yj" \u5f62\u5f0f\u306e\u30c6\u30ad\u30b9\u30c8\u3067\u6307\u5b9a\u3057\u305f\u8907\u7d20\u6570\u306e\u5b9f\u6570\u4fc2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSIN': functionDescription('"x+yi" \u307e\u305f\u306f "x+yj" \u5f62\u5f0f\u306e\u30c6\u30ad\u30b9\u30c8\u3067\u6307\u5b9a\u3057\u305f\u8907\u7d20\u6570\u306e\u30b5\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSQRT': functionDescription('"x+yi" \u307e\u305f\u306f "x+yj" \u5f62\u5f0f\u306e\u30c6\u30ad\u30b9\u30c8\u3067\u6307\u5b9a\u3057\u305f\u8907\u7d20\u6570\u306e\u5e73\u65b9\u6839\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSUB': functionDescription('"x+yi" \u307e\u305f\u306f "x+yj" \u5f62\u5f0f\u306e\u30c6\u30ad\u30b9\u30c8\u3067\u6307\u5b9a\u3057\u305f\uff12\u3064\u306e\u8907\u7d20\u6570\u306e\u5dee\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2')
        ]),
        'IMSUM': functionDescription('"x+yi" \u307e\u305f\u306f "x+yj" \u5f62\u5f0f\u306e\u30c6\u30ad\u30b9\u30c8\u3067\u6307\u5b9a\u3057\u305f\uff12\u3064\u4ee5\u4e0a\u306e\u8907\u7d20\u6570\u306e\u5408\u8a08\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum1'),
            parameterDescription('complexnum2', true)
        ]),
        'INDEX': functionDescription('\u914d\u5217\u307e\u305f\u306f\u30bb\u30eb\u7bc4\u56f2\u306e\u4e2d\u304b\u3089\u3001\u7279\u5b9a\u306e\u5024\u307e\u305f\u306f\u5024\u3078\u306e\u53c2\u7167\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('row_num'),
            parameterDescription('column_num'),
            parameterDescription('area_num')
        ]),
        'INDIRECT': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001\u30c6\u30ad\u30b9\u30c8\u6587\u5b57\u5217\u306b\u3088\u3063\u3066\u6307\u5b9a\u3057\u305f\u53c2\u7167\u3092\u8fd4\u3057\u307e\u3059\u3002\u53c2\u7167\u306f\u305f\u3060\u3061\u306b\u8a55\u4fa1\u3055\u308c\u3001\u305d\u306e\u5185\u5bb9\u304c\u8868\u793a\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('ref_text'),
            parameterDescription('[a1_style]')
        ]),
        'INT': functionDescription('\u6307\u5b9a\u3057\u305f\u5024\u306e\u5c0f\u6570\u90e8\u5206\u3092\u5207\u308a\u6368\u3066\u3001\u6700\u3082\u8fd1\u3044\u6574\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'INTERCEPT': functionDescription('\u6307\u5b9a\u306e x \u5024\u3068 y \u5024\u3092\u4f7f\u7528\u3057\u3066\u5f97\u305f\u56de\u5e30\u76f4\u7dda\u304c y \u8ef8\u3068\u4ea4\u308f\u308b\u70b9\u3092\u6c42\u3081\u307e\u3059\u3002', [
            parameterDescription('dependent'),
            parameterDescription('independent')
        ]),
        'INTRATE': functionDescription('\u5168\u984d\u6295\u8cc7\u3055\u308c\u305f\u8a3c\u5238\u306e\u5229\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('investment'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'IPMT': functionDescription('\u501f\u5165\u91d1\u8fd4\u6e08\u306b\u304a\u3044\u3066\u3001\u652f\u6255\u3046\u3079\u304d\u91d1\u5229\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'IRR': functionDescription('\u4e00\u9023\u306e\u5b9a\u671f\u7684\u306a\u30ad\u30e3\u30c3\u30b7\u30e5\u30d5\u30ed\u30fc\uff08values \u5f15\u6570\u306e\u914d\u5217\u5024\u3067\u6307\u5b9a\uff09\u306b\u57fa\u3065\u304d\u3001\u5185\u90e8\u5229\u76ca\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('arrayvals'),
            parameterDescription('estimate')
        ]),
        'ISBLANK': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u7a7a\u767d\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISERR': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u3001#N/A\uff08\u5229\u7528\u4e0d\u53ef\uff09\u4ee5\u5916\u306e\u30a8\u30e9\u30fc\u5024\u3092\u53c2\u7167\u3059\u308b\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISERROR': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u3001\u4efb\u610f\u306e\u30a8\u30e9\u30fc\u5024\u3092\u53c2\u7167\u3059\u308b\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISEVEN': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u5076\u6570\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISLOGICAL': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u8ad6\u7406\u5024\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISNA': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u3001\u30a8\u30e9\u30fc\u5024 #N/A\uff08\u5229\u7528\u4e0d\u53ef\uff09\u3092\u53c2\u7167\u3059\u308b\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISNONTEXT': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u3001\u30c6\u30ad\u30b9\u30c8\u4ee5\u5916\u306e\u30c7\u30fc\u30bf\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISNUMBER': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u6570\u5024\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISODD': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u5947\u6570\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISPMT': functionDescription('\u6307\u5b9a\u306e\u6295\u8cc7\u671f\u9593\u306b\u652f\u6255\u308f\u308c\u308b\u91d1\u5229\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pv')
        ]),
        'ISREF': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u3001\u4ed6\u306e\u30bb\u30eb\u3078\u306e\u53c2\u7167\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'ISTEXT': functionDescription('\u30bb\u30eb\u306a\u3069\u306e\u5024\u304c\u6587\u5b57\u5217\u304b\u3069\u3046\u304b\u3092\u5224\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'KURT': functionDescription('\u30c7\u30fc\u30bf\u96c6\u5408\u306e\u5c16\u5ea6\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2'),
            parameterDescription('number3'),
            parameterDescription('number4', true)
        ]),
        'LARGE': functionDescription('\u30c7\u30fc\u30bf\u96c6\u5408\u5185\u3067 n \u756a\u76ee\u306b\u5927\u304d\u3044\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'LCM': functionDescription('\u6307\u5b9a\u3057\u305f\u6574\u6570\u306e\u6700\u5c0f\u516c\u500d\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'LEFT': functionDescription('\u30c6\u30ad\u30b9\u30c8\u5024\u304b\u3089\u5148\u982d\uff08\u5de6\u7aef\uff09\u306e\u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('num_chars')
        ]),
        'LEN': functionDescription('\u30c6\u30ad\u30b9\u30c8\u306e\u6587\u5b57\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'LINEST': functionDescription('\u76f4\u7dda\u306b\u57fa\u3065\u304f\u7d71\u8a08\u5024\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('constant'),
            parameterDescription('stats')
        ]),
        'LN': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u306e\u81ea\u7136\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002LN \u306f EXP \u306e\u9006\u95a2\u6570\u3067\u3059\u3002', [
            parameterDescription('value')
        ]),
        'LOG': functionDescription('Y \u3092\u5e95\u3068\u3059\u308b\u6570\u5024 X \u306e\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('base')
        ]),
        'LOG10': functionDescription('10 \u3092\u5e95\u3068\u3059\u308b\u6570\u5024 X \u306e\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'LOGEST': functionDescription('\u30c7\u30fc\u30bf\u306b\u9069\u5408\u3059\u308b\u6307\u6570\u66f2\u7dda\u3092\u8a08\u7b97\u3057\u3001\u3053\u306e\u66f2\u7dda\u3092\u8868\u3059\u5024\u306e\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('constant'),
            parameterDescription('stats')
        ]),
        'LOGINV': functionDescription('x \u306e\u5bfe\u6570\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'LOGNORMDIST': functionDescription('x \u306e\u5bfe\u6570\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
        ]),
        'LOOKUP': functionDescription('\uff11\u884c\u307e\u305f\u306f\uff11\u5217\u5185\u306e\u30bb\u30eb\u7bc4\u56f2\u3001\u307e\u305f\u306f\u914d\u5217\u304b\u3089\u5024\u3092\u691c\u7d22\u3057\u307e\u3059\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_vector'),
            parameterDescription('result_vector')
        ]),
        'LOWER': functionDescription('\u30c6\u30ad\u30b9\u30c8\u3092\u5c0f\u6587\u5b57\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'MATCH': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u9805\u76ee\u306e\u7bc4\u56f2\u5185\u306b\u304a\u3051\u308b\u76f8\u5bfe\u4f4d\u7f6e\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription('match_type')
        ]),
        'XMATCH': functionDescription('\u914d\u5217\u5185\u3067\u306e\u9805\u76ee\u306e\u76f8\u5bfe\u7684\u306a\u4f4d\u7f6e\u3092\u8fd4\u3057\u307e\u3059\u3002\u65e2\u5b9a\u3067\u306f\u3001\u5b8c\u5168\u4e00\u81f4\u304c\u5fc5\u8981\u3067\u3059\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription(MATCH_MODE),
            parameterDescription('[search_mode]')
        ]),
        'XLOOKUP': functionDescription('\u7bc4\u56f2\u307e\u305f\u306f\u914d\u5217\u3067\u4e00\u81f4\u306e\u691c\u7d22\u3092\u884c\u3044\u3001\uff12\u3064\u3081\u306e\u7bc4\u56f2\u307e\u305f\u306f\u914d\u5217\u304b\u3089\u5bfe\u5fdc\u3059\u308b\u9805\u76ee\u3092\u8fd4\u3057\u307e\u3059\u3002\u4e00\u81f4\u3059\u308b\u9805\u76ee\u304c\u306a\u3044\u5834\u5408\u306f\u3001\u6700\u3082\u8fd1\u3044\uff08\u8fd1\u4f3c\u7684\u306a\uff09\u5024\u3092\u8fd4\u3059\u3053\u3068\u3082\u3067\u304d\u307e\u3059\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('lookup_array'),
            parameterDescription('return_array'),
            parameterDescription(IF_NOT_FOUND),
            parameterDescription(MATCH_MODE),
            parameterDescription('[search_mode]')
        ]),
        'MAX': functionDescription('\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u6700\u5927\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MAXA': functionDescription('\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u6700\u5927\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'MDETERM': functionDescription('\u914d\u5217\u306e\u884c\u5217\u5f0f\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array')
        ]),
        'MDURATION': functionDescription('\u984d\u9762\u3092 $100 \u3068\u307f\u306a\u3057\u305f\u8a3c\u5238\u306e\u4fee\u6b63\u30de\u30b3\u30fc\u30ec\u30fc \u30c7\u30e5\u30ec\u30fc\u30b7\u30e7\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('coupon'),
            parameterDescription('yield'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'MEDIAN': functionDescription('\u6307\u5b9a\u3057\u305f\u4e00\u7fa4\u306e\u6570\u5024\u306e\u4e2d\u304b\u3089\u30e1\u30b8\u30a2\u30f3\uff08\u4e2d\u592e\u5024\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MID': functionDescription('\u30c6\u30ad\u30b9\u30c8\u5185\u306e\u6307\u5b9a\u4f4d\u7f6e\u304b\u3089\u3001\u6307\u5b9a\u3057\u305f\u6570\u306e\u6587\u5b57\u3092\u53d6\u308a\u51fa\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('start_num'),
            parameterDescription('num_chars')
        ]),
        'MIN': functionDescription('\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MINA': functionDescription('\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'MINUTE': functionDescription('\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('time')
        ]),
        'MINVERSE': functionDescription('\u914d\u5217\u306b\u6307\u5b9a\u3057\u305f\u884c\u5217\u306e\u9006\u884c\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array')
        ]),
        'MIRR': functionDescription('\u4e00\u9023\u306e\u5b9a\u671f\u7684\u306a\u30ad\u30e3\u30c3\u30b7\u30e5\u30d5\u30ed\u30fc\u306b\u57fa\u3065\u304d\u3001\u4fee\u6b63\u5185\u90e8\u5229\u76ca\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('values'),
            parameterDescription('finance_rate'),
            parameterDescription('reinvest_rate')
        ]),
        'MMULT': functionDescription('\uff12\u3064\u306e\u914d\u5217\u306b\u6307\u5b9a\u3057\u305f\u884c\u5217\u306e\u7a4d\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'MOD': functionDescription('number \u5f15\u6570\uff08\u88ab\u9664\u6570\uff09\u3092 divisor \u5f15\u6570\uff08\u9664\u6570\uff09\u3067\u5272\u3063\u305f\u3068\u304d\u306e\u5270\u4f59\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('dividend'),
            parameterDescription('divisor')
        ]),
        'MODE': functionDescription('\u6307\u5b9a\u306e\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u3067\u3001\u6700\u3082\u983b\u7e41\u306b\u51fa\u73fe\u3059\u308b\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MONTH': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u5024\u306b\u5bfe\u5fdc\u3059\u308b\u6708\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date')
        ]),
        'MROUND': functionDescription('\u6307\u5b9a\u306e\u500d\u6570\u3068\u306a\u308b\u3088\u3046\u306b\u4e38\u3081\u305f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('multiple')
        ]),
        'MULTINOMIAL': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u591a\u9805\u4fc2\u6570\uff08\u30ea\u30b9\u30c8\u5185\u306e\u5024\u306e\u548c\u306e\u968e\u4e57\u3068\u3001\u5404\u5024\u306e\u968e\u4e57\u306e\u7a4d\u3068\u306e\u6bd4\uff09\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'MUNIT': functionDescription('Munit\u95a2\u6570\u306f\u3001\u6307\u5b9a\u3057\u305f\u6b21\u5143\u306e\u5358\u4f4d\u884c\u5217\u3092\u8fd4\u3057\u307e\u3059.', [
            parameterDescription('dimension'),
        ]),
        'N': functionDescription('\u6570\u5024\u306b\u5909\u63db\u3057\u305f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'NA': functionDescription('\u300c\u5229\u7528\u4e0d\u53ef\u300d\u3092\u610f\u5473\u3059\u308b\u30a8\u30e9\u30fc\u5024 #N/A \u3092\u8fd4\u3057\u307e\u3059\u3002', []),
        'SHEET': functionDescription('\u53c2\u7167\u30b7\u30fc\u30c8\u306e\u30b7\u30fc\u30c8\u756a\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('[value]'),
        ]),
        'SHEETS': functionDescription('\u7bc4\u56f2\u5185\u306e\u30b7\u30fc\u30c8\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('[reference]')
        ]),
        'NEGBINOMDIST': functionDescription('\u8ca0\u306e\u4e8c\u9805\u5206\u5e03\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number_f'),
            parameterDescription('number_s'),
            parameterDescription('probability_s')
        ]),
        'NETWORKDAYS': functionDescription('\u958b\u59cb\u65e5\u304b\u3089\u7d42\u4e86\u65e5\u307e\u3067\u306e\u671f\u9593\u5185\u3067\u3001\u5b8c\u5168\u306a\u7a3c\u50cd\u65e5\u306e\u5408\u8a08\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('holidays')
        ]),
        'NOMINAL': functionDescription('\u6307\u5b9a\u306e\u5b9f\u52b9\u5229\u7387\u3068\uff11\u5e74\u3042\u305f\u308a\u306e\u8907\u5229\u8a08\u7b97\u671f\u9593\u306b\u57fa\u3065\u304d\u3001\u540d\u76ee\u4e0a\u306e\u5e74\u5229\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('effect_rate'),
            parameterDescription('npery')
        ]),
        'NORMDIST': functionDescription('\u6307\u5b9a\u306e\u5e73\u5747\u3068\u6a19\u6e96\u504f\u5dee\u306b\u5bfe\u3059\u308b\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
            parameterDescription('cumulative')
        ]),
        'NORMINV': functionDescription('\u6307\u5b9a\u306e\u5e73\u5747\u3068\u6a19\u6e96\u504f\u5dee\u306b\u5bfe\u3059\u308b\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORMSDIST': functionDescription('\u6a19\u6e96\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'NORMSINV': functionDescription('\u6a19\u6e96\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002\u3053\u306e\u5206\u5e03\u3067\u306f\u3001\u5e73\u5747\u304c\uff10\u3001\u6a19\u6e96\u504f\u5dee\u304c\uff11\u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('probability')
        ]),
        'NOT': functionDescription('\u5f15\u6570\u306e\u8ad6\u7406\u5024\u3092\u9006\u306b\u3057\u307e\u3059\u3002', [
            parameterDescription('logical')
        ]),
        'NOW': functionDescription('\u73fe\u5728\u306e\u65e5\u4ed8\u3068\u6642\u523b\u3092\u8868\u3059\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', []),
        'NPER': functionDescription('\u73fe\u884c\u4fa1\u5024\u3001\u5c06\u6765\u4fa1\u5024\u3001\u5b9a\u671f\u6255\u3044\u3001\u304a\u3088\u3073\u7279\u5b9a\u306e\u5229\u7387\u3092\u6761\u4ef6\u3068\u3057\u3001\u6295\u8cc7\u306b\u5fc5\u8981\u306a\u671f\u9593\uff08\u652f\u6255\u56de\u6570\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('paymt'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'NPV': functionDescription('\u5272\u5f15\u7387\u3068\u3001\u5c06\u6765\u884c\u308f\u308c\u308b\u4e00\u9023\u306e\u652f\u6255\u3044\u304a\u3088\u3073\u305d\u306e\u53ce\u76ca\u306b\u57fa\u3065\u3044\u3066\u3001\u6295\u8cc7\u306e\u6b63\u5473\u73fe\u5728\u4fa1\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'OBJECT': functionDescription('\u30d7\u30ed\u30d1\u30c6\u30a3\u3068\u30c7\u30fc\u30bf\u306e\u96c6\u5408\u3092\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('property1', true),
            parameterDescription('expression1', true)
        ]),
        'OCT2BIN': functionDescription('\uff18\u9032\u6570\u5024\u3092\uff12\u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'OCT2DEC': functionDescription('\uff18\u9032\u6570\u5024\u3092 10 \u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'OCT2HEX': functionDescription('\uff18\u9032\u6570\u5024\u3092 16 \u9032\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('places')
        ]),
        'ODD': functionDescription('\u6307\u5b9a\u3057\u305f\u5024\u3092\u5207\u308a\u4e0a\u3052\u3001\u6700\u3082\u8fd1\u3044\u5947\u6570\u306e\u6574\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ODDFPRICE': functionDescription('\uff11\u671f\u76ee\u306e\u65e5\u6570\u304c\u534a\u7aef\u306a\u8a3c\u5238\u306b\u5bfe\u3057\u3001\u984d\u9762 $100 \u3042\u305f\u308a\u306e\u4fa1\u683c\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
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
        'ODDFYIELD': functionDescription('\uff11\u671f\u76ee\u306e\u65e5\u6570\u304c\u534a\u7aef\u306a\u8a3c\u5238\u306e\u5229\u56de\u308a\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
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
        'ODDLPRICE': functionDescription('\u6700\u7d42\u671f\u306e\u65e5\u6570\u304c\u534a\u7aef\u306a\u8a3c\u5238\u306b\u5bfe\u3057\u3001\u984d\u9762 $100 \u3042\u305f\u308a\u306e\u4fa1\u683c\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('last_interest'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'ODDLYIELD': functionDescription('\u6700\u7d42\u671f\u306e\u65e5\u6570\u304c\u534a\u7aef\u306a\u8a3c\u5238\u306e\u5229\u56de\u308a\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('last_interest'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'OFFSET': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001\u30bb\u30eb\u7bc4\u56f2\u3078\u306e\u53c2\u7167\u3092\u8fd4\u3057\u307e\u3059\u3002 \u8fd4\u3055\u308c\u308b\u30bb\u30eb\u7bc4\u56f2\u306f\u3001\u5358\u4e00\u306e\u30bb\u30eb\u307e\u305f\u306f\u30bb\u30eb\u7bc4\u56f2\u304b\u3089\u306e\u884c\u6570\u3068\u5217\u6570\u3067\u6307\u5b9a\u3057\u307e\u3059\u3002 \u3053\u308c\u306b\u3088\u308a\u3001\u5358\u4e00\u306e\u30bb\u30eb\u307e\u305f\u306f\u30bb\u30eb\u7bc4\u56f2\u304c\u8fd4\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('reference'),
            parameterDescription('rows'),
            parameterDescription('cols'),
            parameterDescription('height'),
            parameterDescription('width')
        ]),
        'OR': functionDescription('\u3044\u305a\u308c\u304b\u306e\u5f15\u6570\u304c\u771f\u3067\u3042\u308c\u3070\uff11\uff08True\uff09\u3092\u3001\u3059\u3079\u3066\u306e\u5f15\u6570\u304c\u507d\u3067\u3042\u308c\u3070\uff10\uff08False\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('logical1'),
            parameterDescription('logical2', true)
        ]),
        'PEARSON': functionDescription('\u30d4\u30a2\u30bd\u30f3\u306e\u7a4d\u7387\u76f8\u95a2\u4fc2\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002\u3053\u308c\u306f -1.0\uff5e1.0 \u306e\u7bc4\u56f2\u306e\u6570\u5024\u3067\u3042\u308a\u3001\uff12\u7d44\u306e\u30c7\u30fc\u30bf\u9593\u3067\u306e\u7dda\u5f62\u76f8\u95a2\u306e\u7a0b\u5ea6\u3092\u793a\u3057\u307e\u3059\u3002', [
            parameterDescription('array_ind'),
            parameterDescription('array_dep')
        ]),
        'PERCENTILE': functionDescription('\u3042\u308b\u7bc4\u56f2\u5185\u306e\u5024\u306e\u4e2d\u3067 n \u756a\u76ee\u306e\u767e\u5206\u4f4d\u3092\u6301\u3064\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'PERCENTRANK': functionDescription('\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u5185\u306e\u5024\u306e\u9806\u4f4d\u3092\u3001\u3053\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u5185\u306e\u767e\u5206\u7387\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('significance')
        ]),
        'PERMUT': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u306e\u6a19\u672c\u3092\u629c\u304d\u53d6\u308b\u969b\u306e\u3001\u6709\u52b9\u306a\u9806\u5217\u306e\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'PI': functionDescription('\u5186\u5468\u7387\uff08\u03c0\uff09\u3092 3.1415926536 \u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', []),
        'PMT': functionDescription('\u73fe\u5728\u4fa1\u5024\u3001\u6307\u5b9a\u306e\u5229\u7387\u3001\u304a\u3088\u3073\u652f\u6255\u56de\u6570\u306b\u57fa\u3065\u304d\u3001\u501f\u5165\u91d1\u8fd4\u6e08\u3067\u306e\u5b9a\u671f\u652f\u6255\u984d\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'POISSON': functionDescription('\u30dd\u30a2\u30bd\u30f3\u78ba\u7387\u5206\u5e03\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('nevents'),
            parameterDescription('mean'),
            parameterDescription('cumulative')
        ]),
        'POWER': functionDescription('\u6307\u5b9a\u306e\u6570\uff08X\uff09\u3092\u5e95\u3068\u3059\u308b\u6307\u6570\uff08Y\uff09\u306e\u3079\u304d\u4e57\u3092\u6c42\u3081\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('power')
        ]),
        'PPMT': functionDescription('\u5143\u91d1\u306e\u8fd4\u6e08\u984d\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('per'),
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'PRICE': functionDescription('\u5b9a\u671f\u7684\u306b\u5229\u606f\u304c\u652f\u6255\u308f\u308c\u308b\u8a3c\u5238\u306b\u5bfe\u3057\u3001\u984d\u9762 $100 \u3042\u305f\u308a\u306e\u4fa1\u683c\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'PRICEDISC': functionDescription('\u5272\u5f15\u50b5\u306e\u984d\u9762 $100 \u3042\u305f\u308a\u306e\u4fa1\u683c\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'PRICEMAT': functionDescription('\u6e80\u671f\u65e5\u306b\u5229\u606f\u304c\u6255\u308f\u308c\u308b\u8a3c\u5238\u306b\u5bfe\u3057\u3001\u984d\u9762 $100 \u3042\u305f\u308a\u306e\u4fa1\u683c\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('rate'),
            parameterDescription('yield'),
            parameterDescription('basis')
        ]),
        'PROB': functionDescription('\u7279\u5b9a\u7bc4\u56f2\u5185\u306e\u5024\u304c\u4e0a\u9650\u3068\u4e0b\u9650\u306e\u9593\u306b\u53ce\u307e\u308b\u78ba\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x_range'),
            parameterDescription('prob_range'),
            parameterDescription('lower_limit'),
            parameterDescription('upper_limit')
        ]),
        'PRODUCT': functionDescription('\u3059\u3079\u3066\u306e\u5f15\u6570\u5024\u3092\u4e57\u7b97\u3057\u3066\u5f97\u305f\u7a4d\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'PROPER': functionDescription('\u6587\u5b57\u5217\u5185\u306e\u5168\u82f1\u5358\u8a9e\u306e\u5148\u982d\u6587\u5b57\u3092\u5927\u6587\u5b57\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'PROPERTY': functionDescription('\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u5185\u3067\u6307\u5b9a\u3057\u305f\u30d7\u30ed\u30d1\u30c6\u30a3\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('data_expression'),
            parameterDescription('property_path')
        ]),
        'PV': functionDescription('\u7279\u5b9a\u306e\u5229\u7387\u3001\u5b9a\u671f\u6255\u3044\u306e\u56de\u6570\u3068\u652f\u6255\u984d\u3001\u304a\u3088\u3073\u5c06\u6765\u4fa1\u5024\u3092\u6761\u4ef6\u3068\u3057\u3066\u3001\u6295\u8cc7\u306e\u73fe\u5728\u4fa1\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('numper'),
            parameterDescription('paymt'),
            parameterDescription('fval'),
            parameterDescription('type')
        ]),
        'QUARTILE': functionDescription('\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u304b\u3089\u3001\u6307\u5b9a\u3057\u305f\u56db\u5206\u4f4d\u6570\uff08\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u306e 1/4\uff3b25%\uff3d\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'QUOTIENT': functionDescription('\u9664\u7b97\u3067\u5f97\u305f\u5546\u306e\u6574\u6570\u90e8\u5206\u3092\u8fd4\u3057\u307e\u3059\u3002QUOTIENT \u95a2\u6570\u306f\u3001\u5546\u306e\u4f59\u308a\uff08\u5270\u4f59\uff09\u3092\u7121\u8996\u3057\u305f\u3044\u5834\u5408\u306b\u4f7f\u7528\u3057\u307e\u3059\u3002', [
            parameterDescription('numerator'),
            parameterDescription('denominator')
        ]),
        'RADIANS': functionDescription('\u5ea6\u5358\u4f4d\u306e\u89d2\u5ea6\u306e\u5024\u3092\u30e9\u30b8\u30a2\u30f3\u5358\u4f4d\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'RAND': functionDescription('\u5206\u5e03\u304c\u4e00\u69d8\u306a\u30010\u4ee5\u4e0a1\u672a\u6e80\u306e\u4e71\u6570\u3092\u767a\u751f\u3055\u305b\u307e\u3059\u3002RAND \u95a2\u6570\u306f\u3001\u30b9\u30d7\u30ec\u30c3\u30c9\u30b7\u30fc\u30c8\u304c\u518d\u8a08\u7b97\u3055\u308c\u308b\u305f\u3073\u306b\u65b0\u305f\u306a\u4e71\u6570\u3092\u767a\u751f\u3055\u305b\u307e\u3059\u3002', []),
        'RANDBETWEEN': functionDescription('\u6307\u5b9a\u3057\u305f\uff12\u3064\u306e\u6570\u5024\u9593\u306e\u7bc4\u56f2\u3067\u4e71\u6570\u3092\u767a\u751f\u3055\u305b\u307e\u3059\u3002RANDBETWEEN \u95a2\u6570\u306f\u3001\u30b7\u30fc\u30c8\u304c\u518d\u8a08\u7b97\u3055\u308c\u308b\u305f\u3073\u306b\u65b0\u305f\u306b\u4e71\u6570\u3092\u767a\u751f\u3055\u305b\u307e\u3059\u3002', [
            parameterDescription('bottom'),
            parameterDescription('top')
        ]),
        'RANGEBLOCKSPARKLINE': functionDescription('\u30ec\u30f3\u30b8\u30d6\u30ed\u30c3\u30af\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('template_range'),
            parameterDescription('data_expression')
        ]),
        'RANK': functionDescription('\u6570\u5024\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u3001\u6307\u5b9a\u306e\u6570\u5024\u304c\u4f55\u756a\u76ee\u306b\u4f4d\u7f6e\u3059\u308b\u304b\u3092\u8fd4\u3057\u307e\u3059\u3002RANK \u95a2\u6570\u306e\u8fd4\u3059\u9806\u4f4d\u306f\u3001\u30ea\u30b9\u30c8\u5185\u306e\u6570\u5024\u3092\u4e26\u3079\u66ff\u3048\u305f\u5834\u5408\u306e\u6570\u5024\u306e\u9806\u4f4d\u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('order')
        ]),
        'RATE': functionDescription('\u6295\u8cc7\u671f\u9593\u3092\u901a\u3058\u305f\u5229\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('nper'),
            parameterDescription('pmt'),
            parameterDescription('pval'),
            parameterDescription('fval'),
            parameterDescription('type'),
            parameterDescription('guess')
        ]),
        'RECEIVED': functionDescription('\u5168\u984d\u6295\u8cc7\u3055\u308c\u305f\u8a3c\u5238\u306b\u5bfe\u3057\u3066\u3001\u6e80\u671f\u306b\u652f\u6255\u308f\u308c\u308b\u91d1\u984d\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('investment'),
            parameterDescription('discount'),
            parameterDescription('basis')
        ]),
        'REPLACE': functionDescription('\u6587\u5b57\u5217\u306e\u4e00\u90e8\u3092\u5225\u306e\u6587\u5b57\u5217\u306b\u7f6e\u304d\u63db\u3048\u307e\u3059\u3002', [
            parameterDescription('old_text'),
            parameterDescription('start_num'),
            parameterDescription('num_chars'),
            parameterDescription('new_text')
        ]),
        'REPT': functionDescription('\u6587\u5b57\u5217\u3092\u6307\u5b9a\u306e\u56de\u6570\u5206\u3001\u7e70\u308a\u8fd4\u3057\u8868\u793a\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('number_times')
        ]),
        'RIGHT': functionDescription('\u30c6\u30ad\u30b9\u30c8\u5024\u304b\u3089\u53f3\u7aef\u306e\u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('num_chars')
        ]),
        'ROMAN': functionDescription('\u30a2\u30e9\u30d3\u30a2\u6570\u5b57\u3092\u3001\u30ed\u30fc\u30de\u6570\u5b57\u3092\u8868\u3059\u30c6\u30ad\u30b9\u30c8\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('form')
        ]),
        'ROUND': functionDescription('\u6307\u5b9a\u306e\u6570\u5024\u3092\u3001\u6307\u5b9a\u306e\u6841\u6570\u306b\u306a\u308b\u3088\u3046\u306b\u56db\u6368\u4e94\u5165\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROUNDDOWN': functionDescription('\u6307\u5b9a\u306e\u6570\u5024\u3092\u3001\u6307\u5b9a\u306e\u6841\u6570\u306b\u306a\u308b\u3088\u3046\u306b\u5207\u308a\u6368\u3066\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROUNDUP': functionDescription('\u6307\u5b9a\u306e\u6570\u5024\u3092\u3001\u6307\u5b9a\u306e\u6841\u6570\u306b\u306a\u308b\u3088\u3046\u306b\u5207\u308a\u4e0a\u3052\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'ROW': functionDescription('\u53c2\u7167\u306e\u884c\u756a\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('reference')
        ]),
        'ROWS': functionDescription('\u914d\u5217\u5185\u306e\u884c\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array')
        ]),
        'RSQ': functionDescription('\u65e2\u77e5\u306e x \u3068\u65e2\u77e5\u306e Y \u3092\u901a\u904e\u3059\u308b\u56de\u5e30\u76f4\u7dda\u306e\u30c7\u30fc\u30bf\u70b9\u3092\u4f7f\u7528\u3057\u3066\u3001\u30d4\u30a2\u30bd\u30f3\u7a4d\u7387\u76f8\u95a2\u4fc2\u6570\u306e\u4e8c\u4e57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SEARCH': functionDescription('\u30c6\u30ad\u30b9\u30c8\u5185\u304b\u3089\u6307\u5b9a\u306e\u6587\u5b57\u3092\u691c\u7d22\u3057\u3001\u30c6\u30ad\u30b9\u30c8\u5185\u306b\u304a\u3051\u308b\u3053\u306e\u6587\u5b57\u306e\u958b\u59cb\u4f4d\u7f6e\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'SECOND': functionDescription('\u6307\u5b9a\u306e\u6642\u523b\u5024\u306b\u5bfe\u5fdc\u3059\u308b\u79d2\u306e\u5024\uff080\uff5e59\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('time')
        ]),
        'SERIESSUM': functionDescription('\u3079\u304d\u7d1a\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('n'),
            parameterDescription('m'),
            parameterDescription('coefficients')
        ]),
        'SIGN': functionDescription('\u6570\u5024\u306e\u7b26\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'SIN': functionDescription('\u6307\u5b9a\u3057\u305f\u89d2\u5ea6\u306e\u30b5\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'SINH': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u30cf\u30a4\u30d1\u30fc\u30dc\u30ea\u30c3\u30af\u30b5\u30a4\u30f3\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'SKEW': functionDescription('\u5206\u5e03\u306e\u6b6a\u5ea6\uff08\u5e73\u5747\u5024\u304b\u3089\u306e\u30c7\u30fc\u30bf\u306e\u504f\u308a\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SLN': functionDescription('\u5b9a\u984d\u6cd5\u3092\u4f7f\u7528\u3057\u3066\u3001\uff11\u671f\u3042\u305f\u308a\u306e\u8cc7\u7523\u306e\u6e1b\u4fa1\u511f\u5374\u8cbb\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life')
        ]),
        'SLOPE': functionDescription('\u56de\u5e30\u76f4\u7dda\u306e\u50be\u304d\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SMALL': functionDescription('\u30c7\u30fc\u30bf\u96c6\u5408\u5185\u3067 n \u756a\u76ee\u306b\u5c0f\u3055\u3044\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('n')
        ]),
        'SQRT': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u6b63\u306e\u5e73\u65b9\u6839\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'SQRTPI': functionDescription('\u6307\u5b9a\u306e\u6570\u5024\u3092\u5186\u5468\u7387\u306b\u639b\u3051\u305f\u5024\u306e\u6b63\u306e\u5e73\u65b9\u6839\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('multiple')
        ]),
        'STANDARDIZE': functionDescription('\u7279\u5b9a\u306e\u5e73\u5747\u5024\u3068\u6a19\u6e96\u504f\u5dee\u3067\u6c7a\u5b9a\u3055\u308c\u308b\u5206\u5e03\u3092\u6a19\u6e96\u5316\u3059\u308b\u305f\u3081\u306e\u5909\u91cf\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'STDEVA': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u96c6\u5408\u304b\u3089\u6a19\u6e96\u504f\u5dee\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'STDEVP': functionDescription('\u5f15\u6570\u3068\u3057\u3066\u6307\u5b9a\u3057\u305f\u6bcd\u96c6\u56e3\u5168\u4f53\u306e\u6a19\u6e96\u504f\u5dee\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEVPA': functionDescription('\u5f15\u6570\u3068\u3057\u3066\u6307\u5b9a\u3057\u305f\u6bcd\u96c6\u56e3\u5168\u4f53\u306e\u6a19\u6e96\u504f\u5dee\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'STEYX': functionDescription('\u500b\u5225\u306e x \u306b\u5bfe\u3059\u308b y \u306e\u4e88\u6e2c\u5024\u306e\u6a19\u6e96\u8aa4\u5dee\u3092\u8fd4\u3057\u307e\u3059\u3002\u6a19\u6e96\u8aa4\u5dee\u3068\u306f\u3001x \u5024\u306b\u5bfe\u3057\u3066\u4e88\u6e2c\u3055\u308c\u308b y \u5024\u306e\u8aa4\u5dee\u306e\u91cf\u3092\u8868\u3059\u6307\u6a19\u3067\u3059\u3002', [
            parameterDescription('array_dep'),
            parameterDescription('array_ind')
        ]),
        'SUBSTITUTE': functionDescription('\u65e2\u5b58\u6587\u5b57\u5217\u5185\u306e\u6307\u5b9a\u306e\u6587\u5b57\u3092\u3001\u65b0\u898f\u6587\u5b57\u5217\u306b\u7f6e\u304d\u63db\u3048\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('old_text'),
            parameterDescription('new_text'),
            parameterDescription('instance_num')
        ]),
        'SUBTOTAL': functionDescription('\u5c0f\u8a08\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('function_num'),
            parameterDescription('ref1'),
            parameterDescription('ref2', true)
        ]),
        'SUM': functionDescription('\u30bb\u30eb\u307e\u305f\u306f\u30bb\u30eb\u30d6\u30ed\u30c3\u30af\u306e\u548c\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SUMIF': functionDescription('\u6307\u5b9a\u306e\u57fa\u6e96\u306b\u57fa\u3065\u304d\u3001\u30bb\u30eb\u5024\u3092\u5408\u8a08\u3057\u307e\u3059\u3002', [
            parameterDescription('range'),
            parameterDescription('criteria'),
            parameterDescription('sum_range')
        ]),
        'SUMIFS': functionDescription('\u8907\u6570\u306e\u57fa\u6e96\u306b\u57fa\u3065\u304d\u3001\u30bb\u30eb\u5024\u3092\u5408\u8a08\u3057\u307e\u3059\u3002', [
            parameterDescription('sum_range'),
            parameterDescription('criteria_range1', true),
            parameterDescription('criteria1', true)
        ]),
        'SUMPRODUCT': functionDescription('\u6307\u5b9a\u306e\u914d\u5217\u5185\u306e\u5bfe\u5fdc\u3059\u308b\u8981\u7d20\u306e\u7a4d\u3092\u7b97\u51fa\u3057\u3001\u3053\u308c\u3089\u306e\u7a4d\u306e\u5408\u8a08\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2', true)
        ]),
        'SUMSQ': functionDescription('\u5f15\u6570\u306b\u6307\u5b9a\u3057\u305f\u5024\u306e\uff12\u4e57\u306e\u5408\u8a08\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'SUMX2MY2': functionDescription('\uff12\u3064\u306e\u914d\u5217\u5185\u306e\u5bfe\u5fdc\u3059\u308b\u8981\u7d20\u306e\u5e73\u65b9\u5dee\u3092\u5408\u8a08\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SUMX2PY2': functionDescription('\uff12\u3064\u306e\u914d\u5217\u5185\u306e\u5bfe\u5fdc\u3059\u308b\u8981\u7d20\u306e\u5e73\u65b9\u548c\u3092\u5408\u8a08\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SUMXMY2': functionDescription('\uff12\u3064\u306e\u914d\u5217\u5185\u306e\u5bfe\u5fdc\u3059\u308b\u8981\u7d20\u306e\u5dee\u3092\uff12\u4e57\u3057\u3066\u5408\u8a08\u3057\u307e\u3059\u3002', [
            parameterDescription('array_x'),
            parameterDescription('array_y')
        ]),
        'SYD': functionDescription('\u5b9a\u984d\u9013\u6e1b\u6cd5\u3092\u4f7f\u7528\u3057\u3066\u3001\u7279\u5b9a\u671f\u9593\u306e\u8cc7\u7523\u306e\u6e1b\u4fa1\u511f\u5374\u8cbb\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('period')
        ]),
        'T': functionDescription('\u30bb\u30eb\u5185\u306b\u30c6\u30ad\u30b9\u30c8\u304c\u4fdd\u6301\u3055\u308c\u3066\u3044\u308b\u5834\u5408\u306b\u3053\u306e\u30c6\u30ad\u30b9\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'TAN': functionDescription('\u6307\u5b9a\u3057\u305f\u89d2\u5ea6\u306e\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'TANH': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u30cf\u30a4\u30d1\u30fc\u30dc\u30ea\u30c3\u30af\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'TBILLEQ': functionDescription('\u7c73\u56fd\u8ca1\u52d9\u7701\u77ed\u671f\u8a3c\u5238\uff08TB\uff09\u306e\u50b5\u5238\u306b\u76f8\u5f53\u3059\u308b\u5229\u56de\u308a\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount')
        ]),
        'TBILLPRICE': functionDescription('\u7c73\u56fd\u8ca1\u52d9\u7701\u77ed\u671f\u8a3c\u5238\uff08TB\uff09\u306e\u984d\u9762 $100 \u3042\u305f\u308a\u306e\u4fa1\u683c\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('discount')
        ]),
        'TBILLYIELD': functionDescription('\u7c73\u56fd\u8ca1\u52d9\u7701\u77ed\u671f\u8a3c\u5238\uff08TB\uff09\u306e\u5229\u56de\u308a\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('priceper')
        ]),
        'TDIST': functionDescription('t \u5206\u5e03\u306e\u78ba\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('tails')
        ]),
        'TEXT': functionDescription('\u6570\u5024\u3092\u66f8\u5f0f\u8a2d\u5b9a\u3057\u3001\u30c6\u30ad\u30b9\u30c8\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('format_text')
        ]),
        'TIME': functionDescription('\u6307\u5b9a\u306e\u6642\u9593\u306b\u5bfe\u3059\u308b DateTime \u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('hour'),
            parameterDescription('minute'),
            parameterDescription('second')
        ]),
        'TIMEVALUE': functionDescription('\u6587\u5b57\u5217\u3067\u8868\u3055\u308c\u308b\u6642\u523b\u306b\u5bfe\u5fdc\u3059\u308b\u6642\u9593\u9593\u9694\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('time_text')
        ]),
        'TINV': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u5206\u5e03\u306e\u5024\u3092\u3001\u78ba\u7387\u3068\u81ea\u7531\u5ea6\u3092\u4f7f\u7528\u3057\u305f\u95a2\u6570\u3068\u3057\u3066\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'TODAY': functionDescription('\u73fe\u5728\u306e\u65e5\u4ed8\u3092\u8868\u3059\u9023\u7d9a\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', []),
        'TRANSPOSE': functionDescription('\u6c34\u5e73\u30bb\u30eb\u7bc4\u56f2\u3092\u5782\u76f4\u30bb\u30eb\u7bc4\u56f2\u3068\u3057\u3066\u8fd4\u3057\u3001\u5782\u76f4\u30bb\u30eb\u7bc4\u56f2\u3092\u6c34\u5e73\u30bb\u30eb\u7bc4\u56f2\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array')
        ]),
        'TREND': functionDescription('\u56de\u5e30\u76f4\u7dda\u306b\u5bfe\u3057\u3066\u4e88\u6e2c\u3055\u308c\u308b\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('y'),
            parameterDescription('x'),
            parameterDescription('newx'),
            parameterDescription('constant')
        ]),
        'TRIM': functionDescription('\u6587\u5b57\u5217\u304b\u3089\u4f59\u5206\u306a\u30b9\u30da\u30fc\u30b9\u3092\u524a\u9664\u3057\u3001\u5358\u8a9e\u9593\u306b\uff11\u6587\u5b57\u5206\u306e\u30b9\u30da\u30fc\u30b9\u3092\u4fdd\u3061\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'TRIMMEAN': functionDescription('\u4e0a\u4f4d\u304a\u3088\u3073\u4e0b\u4f4d\u306e\u30c7\u30fc\u30bf\u3092\u9664\u5916\u3057\u305f\u3001\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u5185\u306e\u4e2d\u9593\u30c7\u30fc\u30bf\u306e\u5e73\u5747\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('percent')
        ]),
        'TRUE': functionDescription('\u8ad6\u7406\u5024\uff11\uff08True\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', []),
        'TRUNC': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u691c\u5b9a\u306b\u95a2\u9023\u3059\u308b\u78ba\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('num_digits')
        ]),
        'TTEST': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u691c\u5b9a\u306b\u95a2\u9023\u3059\u308b\u78ba\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2'),
            parameterDescription('tails'),
            parameterDescription('type')
        ]),
        'TYPE': functionDescription('\u5024\u306e\u30c7\u30fc\u30bf\u578b\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'UPPER': functionDescription('\u30c6\u30ad\u30b9\u30c8\u3092\u5927\u6587\u5b57\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'VALUE': functionDescription('\u6570\u5024\u3092\u8868\u3059\u6587\u5b57\u5217\u3092\u6570\u5024\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'VAR': functionDescription('\u5f15\u6570\u5024\u3092\u6bcd\u96c6\u56e3\u306e\u6a19\u672c\u3068\u307f\u306a\u3057\u3001\u6bcd\u96c6\u56e3\u306e\u5206\u6563\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VARA': functionDescription('\u5f15\u6570\u5024\u3092\u6bcd\u96c6\u56e3\u306e\u6a19\u672c\u3068\u307f\u306a\u3057\u3001\u6bcd\u96c6\u56e3\u306e\u5206\u6563\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'VARP': functionDescription('\u6bcd\u96c6\u56e3\u5168\u4f53\u306e\u5206\u6563\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VARPA': functionDescription('\u6bcd\u96c6\u56e3\u5168\u4f53\u306e\u5206\u6563\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value1'),
            parameterDescription('value2', true)
        ]),
        'VDB': functionDescription('\u500d\u7387\u6cd5\u3092\u4f7f\u7528\u3057\u3066\u3001\u6307\u5b9a\u3057\u305f\u4efb\u610f\u306e\u671f\u9593\u306b\u304a\u3051\u308b\u8cc7\u7523\u306e\u6e1b\u4fa1\u511f\u5374\u8cbb\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('cost'),
            parameterDescription('salvage'),
            parameterDescription('life'),
            parameterDescription('start_period'),
            parameterDescription('end_period'),
            parameterDescription('factor'),
            parameterDescription('no_switch')
        ]),
        'VLOOKUP': functionDescription('\u6307\u5b9a\u7bc4\u56f2\u306e\u6700\u5de6\u5217\u304b\u3089\u5024\u3092\u691c\u7d22\u3057\u3001\u6307\u5b9a\u306e\u5217\u304b\u3089\u3001\u3053\u306e\u5024\u3068\u540c\u3058\u884c\u5185\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lookup_value'),
            parameterDescription('table_array'),
            parameterDescription('col_index_num'),
            parameterDescription('range_lookup')
        ]),
        'WEEKDAY': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u306b\u5bfe\u5fdc\u3059\u308b\u66dc\u65e5\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date'),
            parameterDescription('type')
        ]),
        'WEEKNUM': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u304c\u305d\u306e\u5e74\u306e\u4f55\u9031\u76ee\u306b\u5f53\u305f\u308b\u304b\u3092\u8868\u3059\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date'),
            parameterDescription('weektype')
        ]),
        'DATEPART': functionDescription('\u65e5\u4ed8\u578b\u30c7\u30fc\u30bf\u3092\u66f8\u5f0f\u5316\u3057\u305f\u30c6\u30ad\u30b9\u30c8\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('date'),
            parameterDescription('format_text'),
            parameterDescription('weektype')
        ]),
        'WEIBULL': functionDescription('\uff12\u3064\u306e\u30d1\u30e9\u30e1\u30fc\u30bf\u306b\u3088\u308b\u30ef\u30a4\u30d6\u30eb\u5206\u5e03\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002\u3053\u308c\u306f\u3001\u4fe1\u983c\u6027\u306e\u5206\u6790\u306a\u3069\u306b\u3088\u304f\u4f7f\u7528\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'WORKDAY': functionDescription('\u958b\u59cb\u65e5\u3088\u308a\u6307\u5b9a\u306e\u65e5\u6570\u5206\u4ee5\u524d\u307e\u305f\u306f\u4ee5\u964d\u306e\u7a3c\u50cd\u65e5\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('days'),
            parameterDescription('holidays')
        ]),
        'XIRR': functionDescription('\u4e88\u5b9a\u3055\u308c\u3066\u3044\u308b\u30ad\u30e3\u30c3\u30b7\u30e5\u30d5\u30ed\u30fc\uff08\u5b9a\u671f\u7684\u3001\u307e\u305f\u306f\u4e0d\u5b9a\u671f\uff09\u306b\u57fa\u3065\u304d\u3001\u5185\u90e8\u5229\u76ca\u7387\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('values'),
            parameterDescription('dates'),
            parameterDescription('guess')
        ]),
        'XNPV': functionDescription('\u4e88\u5b9a\u3055\u308c\u3066\u3044\u308b\u30ad\u30e3\u30c3\u30b7\u30e5\u30d5\u30ed\u30fc\uff08\u5b9a\u671f\u7684\u3001\u307e\u305f\u306f\u4e0d\u5b9a\u671f\uff09\u306b\u57fa\u3065\u304d\u3001\u6b63\u5473\u73fe\u5728\u4fa1\u5024\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('values'),
            parameterDescription('dates')
        ]),
        'YEAR': functionDescription('\u6307\u5b9a\u306e\u65e5\u4ed8\u306b\u5bfe\u5fdc\u3059\u308b\u5e74\u3092\u8868\u3059\u6574\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date')
        ]),
        'YEARFRAC': functionDescription('\u958b\u59cb\u65e5\u304b\u3089\u7d42\u4e86\u65e5\u307e\u3067\u306e\u671f\u9593\u5185\u306e\u5b8c\u5168\u306a\u65e5\u6570\u304c\u3001\uff11\u5e74\u306e\u3069\u308c\u3060\u3051\u3092\u5360\u3081\u308b\u304b\u3092\u8868\u3059\u5272\u5408\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('basis')
        ]),
        'YIELD': functionDescription('\u5b9a\u671f\u7684\u306b\u5229\u606f\u304c\u652f\u6255\u308f\u308c\u308b\u8a3c\u5238\u306e\u5229\u56de\u308a\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('frequency'),
            parameterDescription('basis')
        ]),
        'YIELDDISC': functionDescription('\u5272\u5f15\u50b5\u306e\u5e74\u5229\u56de\u308a\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('price'),
            parameterDescription('redemption'),
            parameterDescription('basis')
        ]),
        'YIELDMAT': functionDescription('\u6e80\u671f\u306b\u5229\u606f\u304c\u652f\u6255\u308f\u308c\u308b\u8a3c\u5238\u306e\u5e74\u5229\u56de\u308a\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('settlement'),
            parameterDescription('maturity'),
            parameterDescription('issue'),
            parameterDescription('rate'),
            parameterDescription('price'),
            parameterDescription('basis')
        ]),
        'ZTEST': functionDescription('z \u691c\u5b9a\u306e\u6709\u610f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002z \u691c\u5b9a\u3067\u306f\u3001\u4e00\u7fa4\u306e\u30c7\u30fc\u30bf\u306b\u5bfe\u3059\u308b\u691c\u5b9a\u5024 x \u306e\u6a19\u6e96\u30b9\u30b3\u30a2\u3092\u751f\u6210\u3057\u3001\u6b63\u898f\u5206\u5e03\u306e\u4e21\u5074\u306e\u78ba\u7387\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('sigma')
        ]),
        'HBARSPARKLINE': functionDescription('\u6c34\u5e73\u30d0\u30fc\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('colorScheme'),
            parameterDescription('axisVisible'),
            parameterDescription('barHeight')
        ]),
        'VBARSPARKLINE': functionDescription('\u5782\u76f4\u30d0\u30fc\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('colorScheme'),
            parameterDescription('axisVisible'),
            parameterDescription('barWidth')
        ]),
        'VARISPARKLINE': functionDescription('\u30d0\u30ea\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'PIESPARKLINE': functionDescription('\u5186\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('range|percentage'),
            parameterDescription('color', true)
        ]),
        'AREASPARKLINE': functionDescription('\u9762\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('points'),
            parameterDescription('mini'),
            parameterDescription('maxi'),
            parameterDescription('line1'),
            parameterDescription('line2'),
            parameterDescription('colorPositive'),
            parameterDescription('colorNegative')
        ]),
        'SCATTERSPARKLINE': functionDescription('\u6563\u5e03\u56f3\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'LINESPARKLINE': functionDescription('\u6298\u308c\u7dda\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'COLUMNSPARKLINE': functionDescription('\u7e26\u68d2\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'WINLOSSSPARKLINE': functionDescription('\u52dd\u6557\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('data'),
            parameterDescription('dataOrientation'),
            parameterDescription('dateAxisData'),
            parameterDescription('dateAxisOrientation'),
            parameterDescription('setting')
        ]),
        'BULLETSPARKLINE': functionDescription('\u30d6\u30ec\u30c3\u30c8\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'SPREADSPARKLINE': functionDescription('\u30b9\u30d7\u30ec\u30c3\u30c9\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('points'),
            parameterDescription('showAverage'),
            parameterDescription('scaleStart'),
            parameterDescription('scaleEnd'),
            parameterDescription('style'),
            parameterDescription('colorScheme'),
            parameterDescription('vertical')
        ]),
        'STACKEDSPARKLINE': functionDescription('\u7a4d\u307f\u4e0a\u3052\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BOXPLOTSPARKLINE': functionDescription('\u30dc\u30c3\u30af\u30b9\u30d7\u30ed\u30c3\u30c8\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'CASCADESPARKLINE': functionDescription('\u30ab\u30b9\u30b1\u30fc\u30c9\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'PARETOSPARKLINE': functionDescription('\u30d1\u30ec\u30fc\u30c8\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'MONTHSPARKLINE': functionDescription('\u6708\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('year'),
            parameterDescription('month'),
            parameterDescription('dataRange'),
            parameterDescription('emptyColor'),
            parameterDescription('startColor'),
            parameterDescription('middleColor'),
            parameterDescription('endColor')
        ]),
        'YEARSPARKLINE': functionDescription('\u5e74\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('year'),
            parameterDescription('dataRange'),
            parameterDescription('emptyColor'),
            parameterDescription('startColor'),
            parameterDescription('middleColor'),
            parameterDescription('endColor')
        ]),
        'GAUGEKPISPARKLINE': functionDescription('\u30b2\u30fc\u30b8KPI\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'HISTOGRAMSPARKLINE': functionDescription('\u30d2\u30b9\u30c8\u30b0\u30e9\u30e0\u30b9\u30d1\u30fc\u30af\u30e9\u30a4\u30f3\u3092\u8868\u793a\u3059\u308b\u70ba\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'CEILING.PRECISE': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u57fa\u6e96\u5024\u306e\u500d\u6570\u306e\u3046\u3061\u3001\u6700\u3082\u8fd1\u3044\u5024\u306b\u6570\u5024\u3092\u5207\u308a\u4e0a\u3052\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'COVARIANCE.S': functionDescription('\u6a19\u672c\u306e\u5171\u5206\u6563\u3092\u8fd4\u3057\u307e\u3059\u3002\u5171\u5206\u6563\u3068\u306f\u30012 \u7d44\u306e\u5bfe\u5fdc\u3059\u308b\u30c7\u30fc\u30bf\u9593\u3067\u306e\u6a19\u6e96\u504f\u5dee\u306e\u7a4d\u306e\u5e73\u5747\u5024\u3067\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'FLOOR.PRECISE': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u57fa\u6e96\u5024\u306e\u500d\u6570\u306e\u3046\u3061\u3001\u6700\u3082\u8fd1\u3044\u5024\u306b\u6570\u5024\u3092\u5207\u308a\u4e0a\u3052\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'PERCENTILE.EXC': functionDescription('\u7279\u5b9a\u306e\u7bc4\u56f2\u306b\u542b\u307e\u308c\u308b\u30c7\u30fc\u30bf\u306e\u7b2c n \u767e\u5206\u4f4d\u6570\u306b\u5f53\u305f\u308b\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('k')
        ]),
        'QUARTILE.EXC': functionDescription('0 \u3088\u308a\u5927\u304d\u304f 1 \u3088\u308a\u5c0f\u3055\u3044\u767e\u5206\u4f4d\u5024\u306b\u57fa\u3065\u3044\u3066\u3001\u914d\u5217\u306b\u542b\u307e\u308c\u308b\u30c7\u30fc\u30bf\u304b\u3089\u56db\u5206\u4f4d\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'RANK.AVG': functionDescription('\u6570\u5024\u306e\u30ea\u30b9\u30c8\u306e\u4e2d\u3067\u3001\u6307\u5b9a\u3057\u305f\u6570\u5024\u306e\u5e8f\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002\u30ea\u30b9\u30c8\u306e\u4e2d\u306b\u540c\u3058\u6570\u5024\u304c\u5b58\u5728\u3059\u308b\u5834\u5408\u3001\u5e73\u5747\u306e\u9806\u4f4d\u304c\u8fd4\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('[order]')
        ]),
        'MODE.MULT': functionDescription('\u914d\u5217\u307e\u305f\u306f\u30bb\u30eb\u7bc4\u56f2\u3068\u3057\u3066\u6307\u5b9a\u3055\u308c\u305f\u30c7\u30fc\u30bf\u306e\u4e2d\u3067\u3001\u6700\u3082\u983b\u7e41\u306b\u51fa\u73fe\u3059\u308b\u5024 (\u6700\u983b\u5024) \u3092\u7e26\u65b9\u5411\u306e\u914d\u5217\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEV.P': functionDescription('\u5f15\u6570\u3068\u3057\u3066\u6307\u5b9a\u3057\u305f\u6bcd\u96c6\u56e3\u5168\u4f53\u306e\u6a19\u6e96\u504f\u5dee\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VAR.P': functionDescription('\u6bcd\u96c6\u56e3\u5168\u4f53\u306e\u5206\u6563\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'COVARIANCE.P': functionDescription('\uff12\u7d44\u306e\u5bfe\u5fdc\u3059\u308b\u30c7\u30fc\u30bf\u306e\u6a19\u6e96\u504f\u5dee\u306e\u7a4d\u306e\u5e73\u5747\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'MODE.SNGL': functionDescription('\u6307\u5b9a\u306e\u5f15\u6570\u30ea\u30b9\u30c8\u306e\u4e2d\u3067\u3001\u6700\u3082\u983b\u7e41\u306b\u51fa\u73fe\u3059\u308b\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'PERCENTILE.INC': functionDescription('\u3042\u308b\u7bc4\u56f2\u5185\u306e\u5024\u306e\u4e2d\u3067 n \u756a\u76ee\u306e\u767e\u5206\u4f4d\u3092\u6301\u3064\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('k')
        ]),
        'QUARTILE.INC': functionDescription('\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u304b\u3089\u3001\u6307\u5b9a\u3057\u305f\u56db\u5206\u4f4d\u6570\uff08\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u306e 1/4\uff3b25%\uff3d\uff09\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('quart')
        ]),
        'RANK.EQ': functionDescription('\u6570\u5024\u30ea\u30b9\u30c8\u306e\u4e2d\u304b\u3089\u3001\u6307\u5b9a\u306e\u6570\u5024\u304c\u4f55\u756a\u76ee\u306b\u4f4d\u7f6e\u3059\u308b\u304b\u3092\u8fd4\u3057\u307e\u3059\u3002RANK \u95a2\u6570\u306e\u8fd4\u3059\u9806\u4f4d\u306f\u3001\u30ea\u30b9\u30c8\u5185\u306e\u6570\u5024\u3092\u4e26\u3079\u66ff\u3048\u305f\u5834\u5408\u306e\u6570\u5024\u306e\u9806\u4f4d\u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('ref'),
            parameterDescription('[order]')
        ]),
        'STDEV': functionDescription('\u5f15\u6570\u3092\u6a19\u672c\u3068\u898b\u306a\u3057\u3001\u6a19\u672c\u306b\u57fa\u3065\u3044\u3066\u6bcd\u96c6\u56e3\u306e\u6a19\u6e96\u504f\u5dee\u306e\u63a8\u5b9a\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'STDEV.S': functionDescription('\u5f15\u6570\u3092\u6a19\u672c\u3068\u898b\u306a\u3057\u3001\u6a19\u672c\u306b\u57fa\u3065\u3044\u3066\u6bcd\u96c6\u56e3\u306e\u6a19\u6e96\u504f\u5dee\u306e\u63a8\u5b9a\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'VAR.S': functionDescription('\u5f15\u6570\u5024\u3092\u6bcd\u96c6\u56e3\u306e\u6a19\u672c\u3068\u307f\u306a\u3057\u3001\u6bcd\u96c6\u56e3\u306e\u5206\u6563\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'BETA.INV': functionDescription('\u7d2f\u7a4d\u03b2\u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('[lower]'),
            parameterDescription('[upper]')
        ]),
        'BINOM.DIST': functionDescription('\u500b\u5225\u9805\u306e\u4e8c\u9805\u5206\u5e03\u306e\u78ba\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('number_s'),
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'BINOM.INV': functionDescription('\u7d2f\u7a4d\u4e8c\u9805\u5206\u5e03\u306e\u5024\u304c\u57fa\u6e96\u5024\u4ee5\u4e0a\u3068\u306a\u308b\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('alpha')
        ]),
        'CHISQ.DIST.RT': functionDescription('\u7247\u5074\u30ab\u30a4\uff12\u4e57\u5206\u5e03\u306e\u78ba\u7387\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.INV.RT': functionDescription('\u7247\u5074\u30ab\u30a4\uff12\u4e57\u5206\u5e03\u78ba\u7387\u306e\u9006\u95a2\u6570\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.TEST': functionDescription('\u30ab\u30a4\uff12\u4e57\u5206\u5e03\u304b\u3089\u306e\u72ec\u7acb\u6027\u3092\u691c\u5b9a\u3057\u307e\u3059\u3002', [
            parameterDescription('actual_range'),
            parameterDescription('expected_range')
        ]),
        'CONFIDENCE.NORM': functionDescription('\u6bcd\u96c6\u56e3\u306e\u5e73\u5747\u5024\u306b\u5bfe\u3059\u308b\u4fe1\u983c\u533a\u9593\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'EXPON.DIST': functionDescription('\u6307\u6570\u5206\u5e03\u95a2\u6570\u307e\u305f\u306f\u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('lambda'),
            parameterDescription('cumulative')
        ]),
        'F.DIST.RT': functionDescription('\uff12\u7d44\u306e\u30c7\u30fc\u30bf\u9593\u306e\u5206\u6563\u5ea6\u3092\u6bd4\u8f03\u3059\u308b F \u78ba\u7387\u5206\u5e03\u95a2\u6570\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'F.INV.RT': functionDescription('F \u78ba\u7387\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002p = FDIST(x,...) \u3067\u3042\u308b\u3068\u304d\u3001FINV(p,...) = x \u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'F.TEST': functionDescription('F \u691c\u5b9a\u306e\u7d50\u679c\u3092\u8fd4\u3057\u307e\u3059\u3002\u3053\u308c\u306f\u3001\uff12\u3064\u306e\u914d\u5217\u5185\u306e\u30c7\u30fc\u30bf\u306e\u5206\u6563\u306b\u6709\u610f\u306a\u5dee\u304c\u8a8d\u3081\u3089\u308c\u306a\u3044\u7247\u5074\u78ba\u7387\u306e\u7b97\u51fa\u7d50\u679c\u3067\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2')
        ]),
        'GAMMA.DIST': functionDescription('\u30ac\u30f3\u30de\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'GAMMA.INV': functionDescription('\u30ac\u30f3\u30de\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002p = GAMMADIST(x,...) \u3067\u3042\u308b\u3068\u304d\u3001GAMMAINV(p,...) = x \u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('alpha'),
            parameterDescription('beta')
        ]),
        'LOGNORM.INV': functionDescription('x \u306e\u5bfe\u6570\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORM.DIST': functionDescription('\u6307\u5b9a\u306e\u5e73\u5747\u3068\u6a19\u6e96\u504f\u5dee\u306b\u5bfe\u3059\u308b\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('standard_dev'),
            parameterDescription('cumulative')
        ]),
        'NORM.INV': functionDescription('\u6307\u5b9a\u306e\u5e73\u5747\u3068\u6a19\u6e96\u504f\u5dee\u306b\u5bfe\u3059\u308b\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('mean'),
            parameterDescription('standard_dev')
        ]),
        'NORM.S.INV': functionDescription('\u6a19\u6e96\u6b63\u898f\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u9006\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002\u3053\u306e\u5206\u5e03\u3067\u306f\u3001\u5e73\u5747\u304c\uff10\u3001\u6a19\u6e96\u504f\u5dee\u304c\uff11\u3068\u306a\u308a\u307e\u3059\u3002', [
            parameterDescription('probability')
        ]),
        'PERCENTRANK.INC': functionDescription('\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u5185\u306e\u5024\u306e\u9806\u4f4d\u3092\u3001\u3053\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u5185\u306e\u767e\u5206\u7387\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('n'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'POISSON.DIST': functionDescription('\u30dd\u30a2\u30bd\u30f3\u78ba\u7387\u5206\u5e03\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('nevents'),
            parameterDescription('mean'),
            parameterDescription('cumulative')
        ]),
        'T.INV.2T': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u5206\u5e03\u306e\u5024\u3092\u3001\u78ba\u7387\u3068\u81ea\u7531\u5ea6\u3092\u4f7f\u7528\u3057\u305f\u95a2\u6570\u3068\u3057\u3066\u7b97\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'T.TEST': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u691c\u5b9a\u306b\u95a2\u9023\u3059\u308b\u78ba\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('array2'),
            parameterDescription('tails'),
            parameterDescription('type')
        ]),
        'WEIBULL.DIST': functionDescription('\uff12\u3064\u306e\u30d1\u30e9\u30e1\u30fc\u30bf\u306b\u3088\u308b\u30ef\u30a4\u30d6\u30eb\u5206\u5e03\u306e\u5024\u3092\u7b97\u51fa\u3057\u307e\u3059\u3002\u3053\u308c\u306f\u3001\u4fe1\u983c\u6027\u306e\u5206\u6790\u306a\u3069\u306b\u3088\u304f\u4f7f\u7528\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative')
        ]),
        'Z.TEST': functionDescription('z \u691c\u5b9a\u306e\u6709\u610f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002z \u691c\u5b9a\u3067\u306f\u3001\u4e00\u7fa4\u306e\u30c7\u30fc\u30bf\u306b\u5bfe\u3059\u308b\u691c\u5b9a\u5024 x \u306e\u6a19\u6e96\u30b9\u30b3\u30a2\u3092\u751f\u6210\u3057\u3001\u6b63\u898f\u5206\u5e03\u306e\u4e21\u5074\u306e\u78ba\u7387\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription('[sigma]')
        ]),
        'T.DIST.RT': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e\u53f3\u5074 t \u5206\u5e03\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom')
        ]),
        'T.DIST.2T': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e\u4e21\u5074 t \u5206\u5e03\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom')
        ]),
        'ISO.CEILING': functionDescription('\u6700\u3082\u8fd1\u3044\u6574\u6570\u306b\u5207\u308a\u4e0a\u3052\u305f\u5024\u3001\u307e\u305f\u306f\u3001\u6307\u5b9a\u3055\u308c\u305f\u57fa\u6e96\u5024\u306e\u500d\u6570\u306e\u3046\u3061\u6700\u3082\u8fd1\u3044\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'BETA.DIST': functionDescription('\u03b2 \u5206\u5e03\u306e\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('alpha'),
            parameterDescription('beta'),
            parameterDescription('cumulative'),
            parameterDescription('lower'),
            parameterDescription('upper')
        ]),
        'GAMMALN.PRECISE': functionDescription('\u30ac\u30f3\u30de\u95a2\u6570\u306e\u5024\u306e\u81ea\u7136\u5bfe\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'ERF.PRECISE': functionDescription('\u8aa4\u5dee\u95a2\u6570\u306e\u7a4d\u5206\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lowerlimit')
        ]),
        'ERFC.PRECISE': functionDescription('x \uff5e\u7121\u9650\u5927\u306e\u7bc4\u56f2\u3067\u3001\u76f8\u88dc\u8aa4\u5dee\u95a2\u6570\u306e\u7a4d\u5206\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('lowerlimit')
        ]),
        'PERCENTRANK.EXC': functionDescription('\u914d\u5217\u5185\u3067\u306e\u5024\u306e\u9806\u4f4d\u3092\u767e\u5206\u7387 (0 \u3088\u308a\u5927\u304d\u304f 1 \u3088\u308a\u5c0f\u3055\u3044) \u3067\u8868\u3057\u305f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('x'),
            parameterDescription(SIGNIFICANCE)
        ]),
        'HYPGEOM.DIST': functionDescription('\u8d85\u5e7e\u4f55\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('sample_s'),
            parameterDescription('number_sample'),
            parameterDescription('population_s'),
            parameterDescription('number_pop'),
            parameterDescription('cumulative')
        ]),
        'LOGNORM.DIST': functionDescription('\u5bfe\u6570\u6b63\u898f\u5206\u5e03\u306e\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('mean'),
            parameterDescription('stdev'),
            parameterDescription('cumulative')
        ]),
        'NEGBINOM.DIST': functionDescription('\u8ca0\u306e\u4e8c\u9805\u5206\u5e03\u306e\u78ba\u7387\u95a2\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number_f'),
            parameterDescription('number_s'),
            parameterDescription('probability_s'),
            parameterDescription('cumulative')
        ]),
        'NORM.S.DIST': functionDescription('\u6a19\u6e96\u6b63\u898f\u5206\u5e03\u306e\u7d2f\u7a4d\u5206\u5e03\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('z'),
            parameterDescription('cumulative')
        ]),
        'T.DIST': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u5206\u5e03\u306e\u30d1\u30fc\u30bb\u30f3\u30c6\u30fc\u30b8 (\u78ba\u7387) \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('cumulative')
        ]),
        'F.DIST': functionDescription('F \u5206\u5e03\u306e\u78ba\u7387\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2'),
            parameterDescription('cumulative')
        ]),
        'CHISQ.DIST': functionDescription('\u7d2f\u7a4d \u03b2 \u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('x'),
            parameterDescription('deg_freedom'),
            parameterDescription('cumulative')
        ]),
        'F.INV': functionDescription('F \u5206\u5e03\u306e\u78ba\u7387\u95a2\u6570\u306e\u9006\u95a2\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom1'),
            parameterDescription('deg_freedom2')
        ]),
        'T.INV': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u5206\u5e03\u306e t \u5024\u3092\u3001\u78ba\u7387\u3068\u81ea\u7531\u5ea6\u306e\u95a2\u6570\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CHISQ.INV': functionDescription('\u7d2f\u7a4d \u03b2 \u78ba\u7387\u5bc6\u5ea6\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('probability'),
            parameterDescription('deg_freedom')
        ]),
        'CONFIDENCE.T': functionDescription('\u30b9\u30c1\u30e5\u30fc\u30c7\u30f3\u30c8\u306e t \u5206\u5e03\u3092\u4f7f\u7528\u3057\u3066\u3001\u6bcd\u96c6\u56e3\u306b\u5bfe\u3059\u308b\u4fe1\u983c\u533a\u9593\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('alpha'),
            parameterDescription('standard_dev'),
            parameterDescription('size')
        ]),
        'NETWORKDAYS.INTL': functionDescription('\u9031\u672b\u304c\u3069\u306e\u66dc\u65e5\u3067\u4f55\u65e5\u9593\u3042\u308b\u304b\u3092\u793a\u3059\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u3092\u4f7f\u7528\u3057\u3066\u3001\u958b\u59cb\u65e5\u3068\u7d42\u4e86\u65e5\u306e\u9593\u306b\u3042\u308b\u7a3c\u50cd\u65e5\u306e\u65e5\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('end_date'),
            parameterDescription('[weekend]'),
            parameterDescription('[holidays]')
        ]),
        'WORKDAY.INTL': functionDescription('\u9031\u672b\u304c\u3069\u306e\u66dc\u65e5\u3067\u4f55\u65e5\u9593\u3042\u308b\u304b\u3092\u793a\u3059\u30d1\u30e9\u30e1\u30fc\u30bf\u30fc\u3092\u4f7f\u7528\u3057\u3066\u3001\u958b\u59cb\u65e5\u304b\u3089\u8d77\u7b97\u3057\u3066\u6307\u5b9a\u3057\u305f\u7a3c\u50cd\u65e5\u6570\u3060\u3051\u524d\u307e\u305f\u306f\u5f8c\u306e\u65e5\u4ed8\u306b\u5bfe\u5fdc\u3059\u308b\u30b7\u30ea\u30a2\u30eb\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('start_date'),
            parameterDescription('days'),
            parameterDescription('[weekend]'),
            parameterDescription('[holidays]')
        ]),
        'REFRESH': functionDescription('\u95a2\u6570\u306e\u518d\u8a08\u7b97\u65b9\u6cd5\u3092\u6c7a\u5b9a\u3057\u307e\u3059\u3002\u5bfe\u8c61\u306e\u95a2\u6570\u304c\u53c2\u7167\u3059\u308b\u5024\u306b\u5909\u66f4\u304c\u3042\u3063\u305f\u5834\u5408\u306e\u518d\u8a08\u7b97\u65b9\u6cd5\u3092evaluateMode\u5f15\u6570\u3092\u4f7f\u3063\u3066\u6307\u5b9a\u3067\u304d\u307e\u3059\u3002 \u6307\u5b9a\u3067\u304d\u308b\u518d\u8a08\u7b97\u65b9\u6cd5\u306f"calculateOnce"\uff08\u4e00\u5ea6\u306e\u307f\u8a08\u7b97\uff09\u3001"onRecalculation"\uff08\u53c2\u7167\u5024\u306e\u5909\u66f4\u306b\u5fdc\u3058\u3066\u518d\u8a08\u7b97\uff09\u3001"onInterval"\uff08\u7b2c3\u5f15\u6570\u3067\u6307\u5b9a\u3057\u305f\u9593\u9694\u3067\u518d\u8a08\u7b97\uff09\u306e\u4e09\u7a2e\u985e\u3067\u3059\u3002', [
            parameterDescription('formula'),
            parameterDescription('evaluateMode'),
            parameterDescription('interval')
        ]),
        'DAYS': functionDescription('2 \u3064\u306e\u65e5\u4ed8\u9593\u306e\u65e5\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('end_date'),
            parameterDescription('start_date'),
        ]),
        'ISOWEEKNUM': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u65e5\u4ed8\u306e\u305d\u306e\u5e74\u306b\u304a\u3051\u308b ISO \u9031\u756a\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('date')
        ]),
        'BITAND': functionDescription('2 \u3064\u306e\u6570\u5024\u306e\u30d3\u30c3\u30c8\u5358\u4f4d\u306e \u201cAND\u201d \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'BITLSHIFT': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u30d3\u30c3\u30c8\u306e\u6570\u3060\u3051\u5de6\u3078\u30b7\u30d5\u30c8\u3057\u305f\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('shift_amount')
        ]),
        'BITOR': functionDescription('2 \u3064\u306e\u6570\u5024\u306e\u30d3\u30c3\u30c8\u5358\u4f4d\u306e \u201cOR\u201d \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'BITRSHIFT': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u30d3\u30c3\u30c8\u306e\u6570\u3060\u3051\u53f3\u3078\u30b7\u30d5\u30c8\u3057\u305f\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('shift_amount')
        ]),
        'BITXOR': functionDescription('2 \u3064\u306e\u6570\u5024\u306e\u30d3\u30c3\u30c8\u5358\u4f4d\u306e \u201cXOR\u201d \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2')
        ]),
        'IMCOSH': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u53cc\u66f2\u7dda\u4f59\u5f26\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCOT': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u4f59\u63a5\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCSC': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u4f59\u5272\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMCSCH': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u53cc\u66f2\u7dda\u4f59\u5272\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSEC': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u6b63\u5272\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSECH': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u53cc\u66f2\u7dda\u6b63\u5272\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMSINH': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u53cc\u66f2\u7dda\u6b63\u5f26\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'IMTAN': functionDescription('\u6587\u5b57\u5217 "x+yi" \u307e\u305f\u306f "x+yj" \u306e\u5f62\u5f0f\u3067\u6307\u5b9a\u3055\u308c\u305f\u8907\u7d20\u6570\u306e\u6b63\u63a5\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('complexnum')
        ]),
        'PDURATION': functionDescription('\u6295\u8cc7\u304c\u6307\u5b9a\u3057\u305f\u4fa1\u5024\u306b\u9054\u3059\u308b\u307e\u3067\u306e\u6295\u8cc7\u671f\u9593\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rate'),
            parameterDescription('pval'),
            parameterDescription('fval')
        ]),
        'RRI': functionDescription('\u6295\u8cc7\u306e\u6210\u9577\u306b\u5bfe\u3059\u308b\u7b49\u4fa1\u5229\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('nper'),
            parameterDescription('pval'),
            parameterDescription('fval')
        ]),
        'ISFORMULA': functionDescription('\u6570\u5f0f\u304c\u542b\u307e\u308c\u308b\u30bb\u30eb\u3078\u306e\u53c2\u7167\u304c\u3042\u308b\u304b\u3069\u3046\u304b\u3092\u78ba\u8a8d\u3057\u3001TRUE \u307e\u305f\u306f FALSE \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('cellreference')
        ]),
        'IFNA': functionDescription('\u6570\u5f0f\u306e\u7d50\u679c\u304c #N/A \u30a8\u30e9\u30fc\u5024\u306e\u5834\u5408\u306f\u6307\u5b9a\u3057\u305f\u5024\u3092\u8fd4\u3057\u3001\u305d\u308c\u4ee5\u5916\u306e\u5834\u5408\u306f\u6570\u5f0f\u306e\u7d50\u679c\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value'),
            parameterDescription('value_if_na')
        ]),
        'IFS': functionDescription('IFS \u95a2\u6570\u306f\u30011 \u3064\u4ee5\u4e0a\u306e\u6761\u4ef6\u304c\u6e80\u305f\u3055\u308c\u3066\u3044\u308b\u304b\u3069\u3046\u304b\u3092\u30c1\u30a7\u30c3\u30af\u3057\u3066\u3001\u6700\u521d\u306e TRUE \u6761\u4ef6\u306b\u5bfe\u5fdc\u3059\u308b\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('logical_test1'),
            parameterDescription('value_if_true1', true)
        ]),
        'SWITCH': functionDescription('SWITCH \u95a2\u6570\u306f\u3001(\u5f0f\u3068\u547c\u3070\u308c\u308b) 1 \u3064\u306e\u5024\u306b\u5bfe\u3057\u3066\u5024\u306e\u4e00\u89a7\u3092\u8a55\u4fa1\u3057\u3001\u6700\u521d\u306b\u4e00\u81f4\u3059\u308b\u5024\u306b\u5bfe\u5fdc\u3059\u308b\u7d50\u679c\u3092\u8fd4\u3057\u307e\u3059\u3002\u3044\u305a\u308c\u306b\u3082\u4e00\u81f4\u3057\u306a\u3044\u5834\u5408\u306f\u3001\u4efb\u610f\u6307\u5b9a\u306e\u65e2\u5b9a\u5024\u304c\u8fd4\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('expression'),
            parameterDescription('value1'),
            parameterDescription('result1'),
            parameterDescription('[default_or_value2]', true),
            parameterDescription('[result2]', true),
        ]),
        'XOR': functionDescription('\u3059\u3079\u3066\u306e\u5f15\u6570\u306e\u6392\u4ed6\u7684\u8ad6\u7406\u548c\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('logical', true)
        ]),
        'AREAS': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u7bc4\u56f2\u306b\u542b\u307e\u308c\u308b\u9818\u57df\u306e\u500b\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002 \u9023\u7d9a\u3057\u305f\u30bb\u30eb\u7bc4\u56f2\u3001\u307e\u305f\u306f 1 \u3064\u306e\u30bb\u30eb\u304c\u9818\u57df\u3068\u898b\u306a\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('reference')
        ]),
        'FORMULATEXT': functionDescription('\u6570\u5f0f\u3092\u6587\u5b57\u5217\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('reference')
        ]),
        'HYPERLINK': functionDescription('\u30cd\u30c3\u30c8\u30ef\u30fc\u30af \u30b5\u30fc\u30d0\u30fc\u3001\u30a4\u30f3\u30c8\u30e9\u30cd\u30c3\u30c8\u3001\u307e\u305f\u306f\u30a4\u30f3\u30bf\u30fc\u30cd\u30c3\u30c8\u4e0a\u306b\u4fdd\u5b58\u3055\u308c\u3066\u3044\u308b\u30c9\u30ad\u30e5\u30e1\u30f3\u30c8\u3092\u958b\u304f\u305f\u3081\u306b\u3001\u30b7\u30e7\u30fc\u30c8\u30ab\u30c3\u30c8\u307e\u305f\u306f\u30b8\u30e3\u30f3\u30d7\u3092\u4f5c\u6210\u3057\u307e\u3059\u3002', [
            parameterDescription('link_location'),
            parameterDescription('friendly_name')
        ]),
        'ACOT': functionDescription('\u30b3\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u3001\u307e\u305f\u306f\u9006\u30b3\u30bf\u30f3\u30b8\u30a7\u30f3\u30c8\u3001\u6570\u5024\u306e\u4e3b\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ACOTH': functionDescription('\u6570\u5024\u306e\u9006\u53cc\u66f2\u7dda\u4f59\u63a5\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'ARABIC': functionDescription('\u30ed\u30fc\u30de\u6570\u5b57\u3092\u30a2\u30e9\u30d3\u30a2\u6570\u5b57\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'BASE': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u57fa\u6570 (\u5e95) \u306e\u30c6\u30ad\u30b9\u30c8\u8868\u73fe\u306b\u3001\u6570\u5024\u3092\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('radix'),
            parameterDescription('[min_length]')
        ]),
        'CEILING.MATH': functionDescription('\u6570\u5024\u3092\u6700\u3082\u8fd1\u3044\u6574\u6570\u3001\u307e\u305f\u306f\u57fa\u6e96\u5024\u306e\u500d\u6570\u3067\u6700\u3082\u8fd1\u3044\u6570\u306b\u5207\u308a\u4e0a\u3052\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE),
            parameterDescription('[mode]')
        ]),
        'COMBINA': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u500b\u6570\u3092\u9078\u629e\u3059\u308b\u3068\u304d\u306e\u7d44\u307f\u5408\u308f\u305b (\u53cd\u5fa9\u3042\u308a) \u306e\u6570\u3092\u8fd4\u3057\u307e\u3059', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'COT': functionDescription('\u30e9\u30b8\u30a2\u30f3\u3067\u6307\u5b9a\u3055\u308c\u305f\u89d2\u5ea6\u306e\u4f59\u63a5\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'COTH': functionDescription('\u53cc\u66f2\u7dda\u89d2\u5ea6\u306e\u53cc\u66f2\u7dda\u4f59\u63a5\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'CSC': functionDescription('\u30e9\u30b8\u30a2\u30f3\u3067\u6307\u5b9a\u3055\u308c\u305f\u89d2\u5ea6\u306e\u4f59\u5272\u304c\u8fd4\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'CSCH': functionDescription('\u30e9\u30b8\u30a2\u30f3\u3067\u6307\u5b9a\u3055\u308c\u305f\u89d2\u5ea6\u306e\u53cc\u66f2\u7dda\u4f59\u5272\u304c\u8fd4\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'DECIMAL': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u5e95\u306e\u6570\u5024\u306e\u6587\u5b57\u5217\u8868\u73fe\u3092 10 \u9032\u6570\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('radix')
        ]),
        'FLOOR.MATH': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u57fa\u6e96\u5024\u306e\u500d\u6570\u306e\u3046\u3061\u3001\u6700\u3082\u8fd1\u3044\u5024\u306b\u6570\u5024\u3092\u5207\u308a\u6368\u3066\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription(SIGNIFICANCE),
            parameterDescription('[mode]')
        ]),
        'SEC': functionDescription('\u89d2\u5ea6\u306e\u6b63\u5272\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('angle')
        ]),
        'SECH': functionDescription('\u89d2\u5ea6\u306e\u53cc\u66f2\u7dda\u6b63\u5272\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'BINOM.DIST.RANGE': functionDescription('\u4e8c\u9805\u5206\u5e03\u3092\u4f7f\u7528\u3057\u305f\u8a66\u884c\u7d50\u679c\u306e\u78ba\u7387\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('trials'),
            parameterDescription('probability_s'),
            parameterDescription('number_s'),
            parameterDescription('[number_s2]')
        ]),
        'GAMMA': functionDescription('\u30ac\u30f3\u30de\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'MAXIFS': functionDescription('MAXIFS \u95a2\u6570\u306f\u3001\u6761\u4ef6\u30bb\u30c3\u30c8\u3067\u6307\u5b9a\u3055\u308c\u305f\u30bb\u30eb\u306e\u4e2d\u306e\u6700\u5927\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('max_range'),
            parameterDescription('criteria_range', true),
            parameterDescription('criteria', true)
        ]),
        'GAUSS': functionDescription('\u6a19\u6e96\u6b63\u898f\u6bcd\u96c6\u56e3\u306e\u30e1\u30f3\u30d0\u30fc\u304c\u3001\u5e73\u5747\u3068\u5e73\u5747\u304b\u3089\u6a19\u6e96\u504f\u5dee\u306e z \u500d\u306e\u7bc4\u56f2\u306b\u306a\u308b\u78ba\u7387\u3092\u8a08\u7b97\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'MINIFS': functionDescription('MINIFS \u95a2\u6570\u306f\u3001\u6761\u4ef6\u30bb\u30c3\u30c8\u3067\u6307\u5b9a\u3055\u308c\u305f\u30bb\u30eb\u306e\u4e2d\u306e\u6700\u5c0f\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('min_range'),
            parameterDescription('criteria_range', true),
            parameterDescription('criteria', true)
        ]),
        'PERMUTATIONA': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u306e\u5bfe\u8c61 (\u53cd\u5fa9\u3042\u308a) \u304b\u3089\u3001\u6307\u5b9a\u3057\u305f\u6570\u3060\u3051\u629c\u304d\u53d6\u308b\u5834\u5408\u306e\u9806\u5217\u306e\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number'),
            parameterDescription('number_chosen')
        ]),
        'PHI': functionDescription('\u6a19\u6e96\u6b63\u898f\u5206\u5e03\u306e\u5bc6\u5ea6\u95a2\u6570\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'SKEW.P': functionDescription('\u6bcd\u96c6\u56e3\u306b\u57fa\u3065\u304f\u5206\u5e03\u306e\u6b6a\u5ea6\u3092\u8fd4\u3057\u307e\u3059\u3002\u6b6a\u5ea6\u3068\u306f\u3001\u5206\u5e03\u306e\u5e73\u5747\u5024\u5468\u8fba\u3067\u306e\u4e21\u5074\u306e\u975e\u5bfe\u79f0\u5ea6\u3092\u8868\u3059\u5024\u3067\u3059\u3002', [
            parameterDescription('number1'),
            parameterDescription('number2', true)
        ]),
        'BAHTTEXT': functionDescription('\u6570\u5024\u3092\u30bf\u30a4\u8a9e\u306e\u6587\u5b57\u5217\u306b\u5909\u63db\u3057\u3001\u30d0\u30fc\u30c4\u3092\u8868\u3059\u63a5\u5c3e\u6587\u5b57\u5217\u3092\u4ed8\u52a0\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'CONCAT': functionDescription('CONCAT \u95a2\u6570\u306f\u8907\u6570\u306e\u7bc4\u56f2\u3084\u6587\u5b57\u5217\u304b\u3089\u306e\u30c6\u30ad\u30b9\u30c8\u3092\u7d50\u5408\u3057\u307e\u3059\u304c\u3001\u533a\u5207\u308a\u8a18\u53f7\u307e\u305f\u306f IgnoreEmpty \u5f15\u6570\u306f\u63d0\u4f9b\u3057\u307e\u305b\u3093\u3002', [
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'FINDB': functionDescription('FINDB \u95a2\u6570\u3067\u306f\u3001DBCS \u3092\u30b5\u30dd\u30fc\u30c8\u3059\u308b\u8a00\u8a9e\u306e\u7de8\u96c6\u3092\u6709\u52b9\u306b\u3057\u305f\u5f8c\u3067\u305d\u306e\u8a00\u8a9e\u3092\u65e2\u5b9a\u306e\u8a00\u8a9e\u3068\u3057\u3066\u8a2d\u5b9a\u3057\u305f\u5834\u5408\u306b\u3001\u5404 2 \u30d0\u30a4\u30c8\u6587\u5b57\u304c 2 \u3064\u3068\u3057\u3066\u6570\u3048\u3089\u308c\u307e\u3059\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'LEFTB': functionDescription('LEFTB \u95a2\u6570\u306f\u3001\u6587\u5b57\u5217\u306e\u5148\u982d\u304b\u3089\u6307\u5b9a\u3055\u308c\u305f\u30d0\u30a4\u30c8\u6570\u306e\u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('num_bytes')
        ]),
        'LENB': functionDescription('LENB \u95a2\u6570\u306f\u3001\u6587\u5b57\u5217\u306e\u30d0\u30a4\u30c8\u6570\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'MIDB': functionDescription('MIDB \u95a2\u6570\u306f\u3001\u6587\u5b57\u5217\u306e\u4efb\u610f\u306e\u4f4d\u7f6e\u304b\u3089\u6307\u5b9a\u3055\u308c\u305f\u30d0\u30a4\u30c8\u6570\u306e\u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('start_num'),
            parameterDescription('num_bytes')
        ]),
        'REPLACEB': functionDescription('REPLACEB \u95a2\u6570\u306f\u3001\u6587\u5b57\u5217\u4e2d\u306e\u6307\u5b9a\u3055\u308c\u305f\u30d0\u30a4\u30c8\u6570\u306e\u6587\u5b57\u3092\u5225\u306e\u6587\u5b57\u306b\u7f6e\u304d\u63db\u3048\u307e\u3059\u3002', [
            parameterDescription('old_text'),
            parameterDescription('start_byte'),
            parameterDescription('num_bytes'),
            parameterDescription('new_text')
        ]),
        'RIGHTB': functionDescription('RIGHTB \u95a2\u6570\u306f\u3001\u6587\u5b57\u5217\u306e\u672b\u5c3e (\u53f3\u7aef) \u304b\u3089\u6307\u5b9a\u3055\u308c\u305f\u30d0\u30a4\u30c8\u6570\u306e\u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('num_bytes')
        ]),
        'SEARCHB': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u6587\u5b57\u5217\u3092\u4ed6\u306e\u6587\u5b57\u5217\u306e\u4e2d\u3067\u691c\u7d22\u3057\u3001\u305d\u306e\u6587\u5b57\u5217\u304c\u6700\u521d\u306b\u73fe\u308c\u308b\u4f4d\u7f6e\u3092\u5de6\u7aef\u304b\u3089\u6570\u3048\u3001\u305d\u306e\u756a\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('find_text'),
            parameterDescription('within_text'),
            parameterDescription(START_NUMBER)
        ]),
        'TEXTJOIN': functionDescription('TEXTJOIN \u95a2\u6570\u306f\u3001\u8907\u6570\u306e\u7bc4\u56f2\u3084\u6587\u5b57\u5217\u304b\u3089\u306e\u30c6\u30ad\u30b9\u30c8\u3092\u7d50\u5408\u3057\u3001\u7d50\u5408\u3059\u308b\u5404\u30c6\u30ad\u30b9\u30c8\u5024\u306e\u9593\u306b\u3001\u6307\u5b9a\u3057\u305f\u533a\u5207\u308a\u8a18\u53f7\u3092\u633f\u5165\u3057\u307e\u3059\u3002\u533a\u5207\u308a\u8a18\u53f7\u304c\u7a7a\u306e\u6587\u5b57\u5217\u306e\u5834\u5408\u306f\u3001\u7bc4\u56f2\u304c\u9023\u7d50\u3055\u308c\u307e\u3059\u3002', [
            parameterDescription('delimiter'),
            parameterDescription('ignore_empty'),
            parameterDescription('text1'),
            parameterDescription('text2', true)
        ]),
        'UNICHAR': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u6570\u5024\u306b\u3088\u308a\u53c2\u7167\u3055\u308c\u308b Unicode \u6587\u5b57\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('number')
        ]),
        'UNICODE': functionDescription('\u6587\u5b57\u5217\u306e\u6700\u521d\u306e\u6587\u5b57\u306b\u5bfe\u5fdc\u3059\u308b\u756a\u53f7 (\u30b3\u30fc\u30c9 \u30dd\u30a4\u30f3\u30c8) \u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'ENCODEURL': functionDescription('URL \u5f62\u5f0f\u3067\u30a8\u30f3\u30b3\u30fc\u30c9\u3055\u308c\u305f\u6587\u5b57\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text')
        ]),
        'BC_QRCODE': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001QR\u30b3\u30fc\u30c9\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059', [
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
        'BC_EAN13': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001EAN13\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_EAN8': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001EAN8\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_CODABAR': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001CODABAR\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_CODE39': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001CODE39\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_CODE93': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001CODE93\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_CODE128': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001CODE128\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_GS1_128': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001GS1_128\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_CODE49': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001CODE49\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_PDF417': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001PDF417\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'BC_DATAMATRIX': functionDescription('\u3053\u306e\u95a2\u6570\u306f\u3001DATAMATRIX\u3092\u8868\u3059\u305f\u3081\u306e\u30c7\u30fc\u30bf\u30bb\u30c3\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
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
        'FILTER': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u7bc4\u56f2\u307e\u305f\u306f\u914d\u5217\u304b\u3089\u7279\u5b9a\u306e\u30c7\u30fc\u30bf\u3092\u30d5\u30a3\u30eb\u30bf\u30ea\u30f3\u30b0\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('include'),
            parameterDescription('if_empty')
        ]),
        'RANDARRAY': functionDescription('\u30e9\u30f3\u30c0\u30e0\u306a\u6570\u5024\u306e\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('min'),
            parameterDescription('max'),
            parameterDescription('whole_number')
        ]),
        'SEQUENCE': functionDescription('\u9806\u756a\u306b\u4e26\u3093\u3060\u4e00\u9023\u306e\u6570\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rows'),
            parameterDescription('columns'),
            parameterDescription('start'),
            parameterDescription('step')
        ]),
        'SINGLE': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u5024\u3001\u7bc4\u56f2\u3001\u307e\u305f\u306f\u914d\u5217\u304b\u3089\u5358\u4e00\u306e\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('value')
        ]),
        'SORT': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u7bc4\u56f2\u307e\u305f\u306f\u914d\u5217\u3092\u30bd\u30fc\u30c8\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('sort_index'),
            parameterDescription('sort_order'),
            parameterDescription('by_col')
        ]),
        'SORTBY': functionDescription('\u7bc4\u56f2\u307e\u305f\u306f\u914d\u5217\u3092\u6307\u5b9a\u3055\u308c\u305f\u5024\u306b\u57fa\u3065\u3044\u3066\u30bd\u30fc\u30c8\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('by_array1'),
            parameterDescription('sort_order1'),
            parameterDescription('by_array2', true),
            parameterDescription('sort_order2', true),
        ]),
        'UNIQUE': functionDescription('\u6307\u5b9a\u3055\u308c\u305f\u7bc4\u56f2\u307e\u305f\u306f\u914d\u5217\u304b\u3089\u4e00\u610f\u306a\u5024\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('by_col'),
            parameterDescription('exactly_once')
        ]),
        'QUERY': functionDescription('\u30c7\u30fc\u30bf\u30de\u30cd\u30fc\u30b8\u30e3\u30fc\u306b\u8ffd\u52a0\u3055\u308c\u305f\u30c6\u30fc\u30d6\u30eb\u304b\u3089\u30c7\u30fc\u30bf\u3092\u8fd4\u3057\u307e\u3059\u3002\u3053\u306e\u95a2\u6570\u306f\u30ef\u30fc\u30af\u30b7\u30fc\u30c8\u3067\u306e\u307f\u4f7f\u7528\u53ef\u80fd\u3067\u3059\u3002', [
            parameterDescription('tableAndRows'),
            parameterDescription('columns'),
            parameterDescription('returnObject')
        ]),
        'LET': functionDescription('\u8a08\u7b97\u7d50\u679c\u306b\u540d\u524d\u3092\u5272\u308a\u5f53\u3066\u307e\u3059\u3002\u6570\u5f0f\u5185\u3067\u540d\u524d\u3092\u5b9a\u7fa9\u3057\u3066\u3001\u4e2d\u9593\u306e\u8a08\u7b97\u7d50\u679c\u3068\u5024\u3092\u4fdd\u5b58\u3059\u308b\u3053\u3068\u304c\u3067\u304d\u307e\u3059\u3002', [
            parameterDescription('name1'),
            parameterDescription('name_value1'),
            parameterDescription('calculation_or_name2'),
            parameterDescription('[name_value2, ...')
        ]),
        'IMAGE': functionDescription('URL\u307e\u305f\u306fBASE64\u5f62\u5f0f\u306e\u6587\u5b57\u5217\u3092\u4f7f\u7528\u3057\u3066\u3001\u30bb\u30eb\u306b\u753b\u50cf\u3092\u8868\u793a\u3057\u307e\u3059\u3002', [
            parameterDescription("URL"),
            parameterDescription("[mode]"),
            parameterDescription("[height]"),
            parameterDescription("[width]"),
            parameterDescription("[clipY]"),
            parameterDescription("[clipX]"),
            parameterDescription("[clipHeight]"),
            parameterDescription("[clipWidth]"),
            parameterDescription("[vAlign]"),
            parameterDescription("[hAlign]"),
        ]),
        'GETPIVOTDATA': functionDescription('\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u3067\u96c6\u8a08\u3055\u308c\u308b\u30c7\u30fc\u30bf\u3092\u62bd\u51fa\u3057\u307e\u3059\u3002', [
            parameterDescription('data_field'),
            parameterDescription('pivot_table'),
            parameterDescription('[field1, item1]'),
            parameterDescription('...')
        ]),
        'WEBSERVICE': functionDescription('Web\u30b5\u30fc\u30d3\u30b9\u304b\u3089\u53d6\u5f97\u3057\u305f\u30c7\u30fc\u30bf\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('url')
        ]),
        'FILTERJSON': functionDescription('JSON\u6587\u5b57\u5217\u3092\u30b9\u30ab\u30e9\u5024\u3001\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u3001\u307e\u305f\u306f\u30aa\u30d6\u30b8\u30a7\u30af\u30c8\u306e\u914d\u5217\u306b\u5909\u63db\u3057\u307e\u3059\u3002', [
            parameterDescription('json_string')
        ]),
        "ASC": functionDescription("2\u30d0\u30a4\u30c8\u6587\u5b57\u30bb\u30c3\u30c8(DBCS)\u8a00\u8a9e\u306e\u5834\u5408\u3001\u5168\u89d2(2\u30d0\u30a4\u30c8)\u6587\u5b57\u3092\u534a\u89d2(1\u30d0\u30a4\u30c8)\u6587\u5b57\u306b\u5909\u66f4\u3057\u307e\u3059\u3002", [
            parameterDescription("text")
        ]),
        "DBCS": functionDescription("\u6587\u5b57\u5217\u5185\u306e\u534a\u89d2(1\u30d0\u30a4\u30c8)\u306e\u82f1\u6570\u30ab\u30ca\u6587\u5b57\u3092\u5168\u89d2(2\u30d0\u30a4\u30c8)\u306e\u6587\u5b57\u306b\u5909\u63db\u3057\u307e\u3059\u3002", [
            parameterDescription("text")
        ]),
        'LAMBDA': functionDescription('\u6570\u5f0f\u3067\u547c\u3073\u51fa\u3059\u3053\u3068\u304c\u3067\u304d\u308b\u95a2\u6570\u3092\u4f5c\u6210\u3057\u307e\u3059\u3002', [
            parameterDescription('parameter_or_calculation'),
            parameterDescription('[parameter_or_calculation]', true)
        ]),
        'MAP': functionDescription('LAMBDA\u3092\u9069\u7528\u3057\u3066\u65b0\u3057\u3044\u5024\u3092\u4f5c\u6210\u3059\u308b\u3053\u3068\u306b\u3088\u308a\u3001\u914d\u5217\u5185\u306e\u5404\u5024\u3092\u65b0\u3057\u3044\u5024\u306b\u30de\u30c3\u30d4\u30f3\u30b0\u3057\u3066\u5f62\u6210\u3055\u308c\u305f\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('lambda_or_array', true)
        ]),
        'REDUCE': functionDescription('\u5404\u5024\u306b LAMBDA\u3092\u9069\u7528\u3057\u3001\u30a2\u30ad\u30e5\u30e0\u30ec\u30fc\u30bf\u30fc\u306b\u5408\u8a08\u5024\u3092\u8fd4\u3059\u3053\u3068\u3067\u3001\u914d\u5217\u3092\u7d2f\u7a4d\u5024\u306b\u6e1b\u3089\u3057\u307e\u3059\u3002', [
            parameterDescription('init_value'),
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'SCAN': functionDescription('LAMBDA\u3092\u5404\u5024\u306b\u9069\u7528\u3057\u3066\u914d\u5217\u3092\u30b9\u30ad\u30e3\u30f3\u3057\u3001\u5404\u4e2d\u9593\u5024\u3092\u6301\u3064\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('init_value'),
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'MAKEARRAY': functionDescription('LAMBDA\u3092\u9069\u7528\u3057\u3066\u3001\u6307\u5b9a\u3055\u308c\u305f\u884c\u3068\u5217\u306e\u30b5\u30a4\u30ba\u306e\u8a08\u7b97\u3055\u308c\u305f\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('rows'),
            parameterDescription('cols'),
            parameterDescription('function')
        ]),
        'BYCOL': functionDescription('LAMBDA\u3092\u5404\u5217\u306b\u9069\u7528\u3057\u3001\u7d50\u679c\u306e\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002 \u305f\u3068\u3048\u3070\u3001\u5143\u306e\u914d\u5217\u304c 3\u5217 x 2\u884c\u306e\u5834\u5408\u3001\u8fd4\u3055\u308c\u308b\u914d\u5217\u306f 3\u5217 x 1\u884c\u3067\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'BYROW': functionDescription('LAMBDA\u3092\u5404\u884c\u306b\u9069\u7528\u3057\u3001\u7d50\u679c\u306e\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002 \u305f\u3068\u3048\u3070\u3001\u5143\u306e\u914d\u5217\u304c 3\u5217 x 2\u884c\u306e\u5834\u5408\u3001\u8fd4\u3055\u308c\u308b\u914d\u5217\u306f 1\u5217 x 2\u884c\u3067\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('function')
        ]),
        'ISOMITTED': functionDescription('LAMBDA\u306e\u5024\u304c\u306a\u3044\u304b\u3069\u3046\u304b\u3092\u78ba\u8a8d\u3057\u3001TRUE \u307e\u305f\u306f FALSE \u3092\u8fd4\u3057\u307e\u3059\u3002  ', [
            parameterDescription('argument')
        ]),
        'TEXTBEFORE': functionDescription('\u6307\u5b9a\u3057\u305f\u6587\u5b57\u307e\u305f\u306f\u6587\u5b57\u5217\u306e\u524d\u306e\u30c6\u30ad\u30b9\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('delimiter'),
            parameterDescription('[instance_num]'),
            parameterDescription(MATCH_MODE),
            parameterDescription('[match_end]'),
            parameterDescription(IF_NOT_FOUND)
        ]),
        'TEXTAFTER': functionDescription('\u6307\u5b9a\u3057\u305f\u6587\u5b57\u307e\u305f\u306f\u6587\u5b57\u5217\u306e\u5f8c\u306e\u30c6\u30ad\u30b9\u30c8\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('delimiter'),
            parameterDescription('[instance_num]'),
            parameterDescription(MATCH_MODE),
            parameterDescription('[match_end]'),
            parameterDescription(IF_NOT_FOUND)
        ]),
        'TEXTSPLIT': functionDescription('\u533a\u5207\u308a\u8a18\u53f7\u3092\u4f7f\u7528\u3057\u3066\u3001\u30c6\u30ad\u30b9\u30c8\u3092\u884c\u307e\u305f\u306f\u5217\u306b\u5206\u5272\u3057\u307e\u3059\u3002', [
            parameterDescription('text'),
            parameterDescription('col_delimiter'),
            parameterDescription('[row_delimiter]'),
            parameterDescription('[ignore_empty]'),
            parameterDescription(MATCH_MODE),
            parameterDescription(PAD_WITH)
        ]),
        'VSTACK': functionDescription('\u914d\u5217\u3092\u5782\u76f4\u65b9\u5411\u306b\u9806\u756a\u306b\u8ffd\u52a0\u3057\u3066\u3001\u3088\u308a\u5927\u304d\u306a\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('[array2]', true)
        ]),
        'HSTACK': functionDescription('\u914d\u5217\u3092\u6c34\u5e73\u65b9\u5411\u306b\u9806\u756a\u306b\u8ffd\u52a0\u3057\u3066\u3001\u3088\u308a\u5927\u304d\u306a\u914d\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array1'),
            parameterDescription('[array2]', true)
        ]),
        'TOROW': functionDescription('\u914d\u5217\u30921\u884c\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('[ignore]'),
            parameterDescription('[scan_by_column]')
        ]),
        'TOCOL': functionDescription('\u914d\u5217\u30921\u3064\u306e\u5217\u3068\u3057\u3066\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('[ignore]'),
            parameterDescription('[scan_by_column]')
        ]),
        'WRAPROWS': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u306e\u8981\u7d20\u306e\u5f8c\u306b\u3001\u6307\u5b9a\u3055\u308c\u305f\u884c\u307e\u305f\u306f\u5024\u306e\u5217\u3092\u884c\u3054\u3068\u306b\u30e9\u30c3\u30d7\u3057\u3066\u3001\u65b0\u3057\u3044\u914d\u5217\u3092\u5f62\u6210\u3057\u307e\u3059\u3002', [
            parameterDescription('vector'),
            parameterDescription('wrap_count'),
            parameterDescription(PAD_WITH)
        ]),
        'WRAPCOLS': functionDescription('\u6307\u5b9a\u3057\u305f\u6570\u306e\u8981\u7d20\u306e\u5f8c\u306b\u3001\u6307\u5b9a\u3055\u308c\u305f\u5024\u306e\u884c\u307e\u305f\u306f\u5217\u3092\u5217\u3054\u3068\u306b\u30e9\u30c3\u30d7\u3057\u3066\u3001\u65b0\u3057\u3044\u914d\u5217\u3092\u5f62\u6210\u3057\u307e\u3059\u3002', [
            parameterDescription('vector'),
            parameterDescription('wrap_count'),
            parameterDescription(PAD_WITH)
        ]),
        'TAKE': functionDescription('\u914d\u5217\u306e\u5148\u982d\u307e\u305f\u306f\u672b\u5c3e\u304b\u3089\u3001\u6307\u5b9a\u3057\u305f\u6570\u306e\u9023\u7d9a\u3059\u308b\u884c\u307e\u305f\u306f\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('[columns]')
        ]),
        'DROP': functionDescription('\u914d\u5217\u306e\u5148\u982d\u307e\u305f\u306f\u672b\u5c3e\u304b\u3089\u3001\u6307\u5b9a\u3057\u305f\u6570\u306e\u884c\u307e\u305f\u306f\u5217\u3092\u9664\u5916\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('[columns]')
        ]),
        'EXPAND': functionDescription('\u914d\u5217\u3092\u5c55\u958b\u3001\u307e\u305f\u306f\u57cb\u3081\u8fbc\u3093\u3067\u3001\u6307\u5b9a\u3057\u305f\u884c\u3068\u5217\u306e\u5927\u304d\u3055\u306b\u5408\u308f\u305b\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('rows'),
            parameterDescription('[columns]'),
            parameterDescription(PAD_WITH)
        ]),
        'CHOOSEROWS': functionDescription('\u914d\u5217\u304b\u3089\u6307\u5b9a\u3055\u308c\u305f\u884c\u3092\u8fd4\u3057\u307e\u3059\u3002 ', [
            parameterDescription('array'),
            parameterDescription('row_num1'),
            parameterDescription('[row_num2]', true)
        ]),
        'CHOOSECOLS': functionDescription('\u914d\u5217\u304b\u3089\u6307\u5b9a\u3055\u308c\u305f\u5217\u3092\u8fd4\u3057\u307e\u3059\u3002', [
            parameterDescription('array'),
            parameterDescription('col_num1'),
            parameterDescription('[col_num2]', true)
        ])
    },
    _tableFunctionsResource: {
        All: { name: '#All', description: '\u30c6\u30fc\u30d6\u30eb\u306e\u3059\u3079\u3066\u306e\u5024\u3001\u307e\u305f\u306f\u3001\u6307\u5b9a\u3057\u305f\u30c6\u30fc\u30d6\u30eb\u5217\u3068\u5217\u756a\u53f7\u3001\u30c7\u30fc\u30bf\u304a\u3088\u3073\u96c6\u8a08\u884c\u3092\u8fd4\u3057\u307e\u3059\u3002' },
        Data: { name: '#Data', description: '\u30c6\u30fc\u30d6\u30eb\u307e\u305f\u306f\u6307\u5b9a\u3057\u305f\u30c6\u30fc\u30d6\u30eb\u5217\u306e\u30c7\u30fc\u30bf \u30bb\u30eb\u3092\u8fd4\u3057\u307e\u3059\u3002' },
        Headers: { name: '#Headers', description: '\u30c6\u30fc\u30d6\u30eb\u307e\u305f\u306f\u6307\u5b9a\u3057\u305f\u30c6\u30fc\u30d6\u30eb\u5217\u306e\u5217\u756a\u53f7\u3092\u8fd4\u3057\u307e\u3059\u3002' },
        Totals: { name: '#Totals', description: '\u30c6\u30fc\u30d6\u30eb\u307e\u305f\u306f\u6307\u5b9a\u3057\u305f\u30c6\u30fc\u30d6\u30eb\u5217\u306e\u96c6\u8a08\u884c\u3092\u8fd4\u3057\u307e\u3059\u3002' },
        thisRow: { name: '#This Row', description: '\u3053\u306e\u884c\u3002' },
    },
};


 }),

 "./src/resource.ja.entry.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var calcEngine_res_ja_1 = __webpack_require__( "./src/calcEngine.res.ja.ts");
exports.SR = { ja: calcEngine_res_ja_1.resource };


 })

 });

module.exports = GC.Spread.CalcEngine;

/***/ }),

/***/ "./node_modules_local/@grapecity/js-pivot/dist/gc.pivot.resources.ja.js":
/*!******************************************************************************!*\
  !*** ./node_modules_local/@grapecity/js-pivot/dist/gc.pivot.resources.ja.js ***!
  \******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;var GC = typeof GC === "object" ? GC : {}; GC["Pivot"] =
 (function(modules) {
 
 	var installedModules = {};
 
 	function __webpack_require__(moduleId) {
 	
 		if(installedModules[moduleId]) {
 			return installedModules[moduleId].exports;
 		}
 	
 		var module = installedModules[moduleId] = {
 			i: moduleId,
 			l: false,
 			exports: {}
 		};
 	
 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
 	
 		module.l = true;
 	
 		return module.exports;
 	}
 
 	__webpack_require__.m = modules;
 
 	__webpack_require__.c = installedModules;
 
 	__webpack_require__.d = function(exports, name, getter) {
 		if(!__webpack_require__.o(exports, name)) {
 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
 		}
 	};
 
 	__webpack_require__.r = function(exports) {
 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
 		}
 		Object.defineProperty(exports, '__esModule', { value: true });
 	};
 
 
 
 
 
 	__webpack_require__.t = function(value, mode) {
 		if(mode & 1) value = __webpack_require__(value);
 		if(mode & 8) return value;
 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
 		var ns = Object.create(null);
 		__webpack_require__.r(ns);
 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
 		return ns;
 	};
 
 	__webpack_require__.n = function(module) {
 		var getter = module && module.__esModule ?
 			function getDefault() { return module['default']; } :
 			function getModuleExports() { return module; };
 		__webpack_require__.d(getter, 'a', getter);
 		return getter;
 	};
 
 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
 
 	__webpack_require__.p = "";
 
 	return __webpack_require__(__webpack_require__.s = "./src/resource.ja.entry.ts");
 })
 ({

 "./src/pivotEngine.res.ja.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
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
        Qtr1: "\u7b2c1\u56db\u534a\u671f",
        Qtr2: "\u7b2c2\u56db\u534a\u671f",
        Qtr3: "\u7b2c3\u56db\u534a\u671f",
        Qtr4: "\u7b2c4\u56db\u534a\u671f",
        Seconds: "\u79d2",
        Minutes: "\u5206",
        Hours: "\u6642",
        Days: "\u65e5",
        Months: "\u6708",
        Quarters: "\u56db\u534a\u671f",
        Years: "\u5e74",
        SECOND_GROUP_ITEMS: [":00", ":01", ":02", ":03", ":04", ":05", ":06", ":07", ":08", ":09", ":10", ":11", ":12", ":13", ":14", ":15", ":16", ":17", ":18", ":19", ":20", ":21", ":22", ":23", ":24", ":25", ":26", ":27", ":28", ":29", ":30", ":31", ":32", ":33", ":34", ":35", ":36", ":37", ":38", ":39", ":40", ":41", ":42", ":43", ":44", ":45", ":46", ":47", ":48", ":49", ":50", ":51", ":52", ":53", ":54", ":55", ":56", ":57", ":58", ":59"],
        MINUTE_GROUP_ITEMS: [":00", ":01", ":02", ":03", ":04", ":05", ":06", ":07", ":08", ":09", ":10", ":11", ":12", ":13", ":14", ":15", ":16", ":17", ":18", ":19", ":20", ":21", ":22", ":23", ":24", ":25", ":26", ":27", ":28", ":29", ":30", ":31", ":32", ":33", ":34", ":35", ":36", ":37", ":38", ":39", ":40", ":41", ":42", ":43", ":44", ":45", ":46", ":47", ":48", ":49", ":50", ":51", ":52", ":53", ":54", ":55", ":56", ":57", ":58", ":59"],
        HOUR_GROUP_ITEMS: ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"]
    },
    Exp_NoItemParseError: "\u30a2\u30a4\u30c6\u30e0\u540d\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3002\u5165\u529b\u3057\u305f\u540d\u524d\u304c\u6b63\u3057\u3044\u3053\u3068\u3068\u3001\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb \u30ec\u30dd\u30fc\u30c8\u306b\u30a2\u30a4\u30c6\u30e0\u304c\u3042\u308b\u3053\u3068\u3092\u78ba\u8a8d\u3057\u3066\u304f\u3060\u3055\u3044\u3002",
    Exp_UnsupportedCalcItemType: "\u30d4\u30dc\u30c3\u30c8\u30c6\u30fc\u30d6\u30eb\u306e\u6570\u5f0f\u3067\u306f\u3001\u53c2\u7167\u3001\u540d\u524d\u3001\u304a\u3088\u3073\u914d\u5217\u306f\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002",
    blank: "(\u7a7a\u767d)"
};


 }),

 "./src/resource.ja.entry.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var pivotEngine_res_ja_1 = __webpack_require__( "./src/pivotEngine.res.ja.ts");
exports.SR = { ja: pivotEngine_res_ja_1.resource };


 })

 });

(function(GC) {
    if( true && typeof module.exports === 'object') {
        module.exports = GC.Pivot;
    } else if(true) {
        !(__WEBPACK_AMD_DEFINE_ARRAY__ = [], __WEBPACK_AMD_DEFINE_RESULT__ = (function() {return GC.Pivot}).apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__))
    } else {}
 })(GC || {});


/***/ }),

/***/ "./node_modules_local/@grapecity/js-sheets-common/dist/gc.spread.common.resources.ja.js":
/*!**********************************************************************************************!*\
  !*** ./node_modules_local/@grapecity/js-sheets-common/dist/gc.spread.common.resources.ja.js ***!
  \**********************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

var GC = typeof GC === "object" ? GC : {}; GC["Spread"] = GC["Spread"] || {}; GC["Spread"]["Common"] =
 (function(modules) {
 
 	var installedModules = {};
 
 	function __webpack_require__(moduleId) {
 	
 		if(installedModules[moduleId]) {
 			return installedModules[moduleId].exports;
 		}
 	
 		var module = installedModules[moduleId] = {
 			i: moduleId,
 			l: false,
 			exports: {}
 		};
 	
 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
 	
 		module.l = true;
 	
 		return module.exports;
 	}
 
 	__webpack_require__.m = modules;
 
 	__webpack_require__.c = installedModules;
 
 	__webpack_require__.d = function(exports, name, getter) {
 		if(!__webpack_require__.o(exports, name)) {
 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
 		}
 	};
 
 	__webpack_require__.r = function(exports) {
 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
 		}
 		Object.defineProperty(exports, '__esModule', { value: true });
 	};
 
 
 
 
 
 	__webpack_require__.t = function(value, mode) {
 		if(mode & 1) value = __webpack_require__(value);
 		if(mode & 8) return value;
 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
 		var ns = Object.create(null);
 		__webpack_require__.r(ns);
 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
 		return ns;
 	};
 
 	__webpack_require__.n = function(module) {
 		var getter = module && module.__esModule ?
 			function getDefault() { return module['default']; } :
 			function getModuleExports() { return module; };
 		__webpack_require__.d(getter, 'a', getter);
 		return getter;
 	};
 
 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
 
 	__webpack_require__.p = "/assets/";
 
 	return __webpack_require__(__webpack_require__.s = "./src/resource.ja.entry.ts");
 })
 ({

 "./src/common/util/util.res.ja.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_InvalidDateFormat = '\u7121\u52b9\u306a\u65e5\u4ed8\u30d5\u30a9\u30fc\u30de\u30c3\u30c8';
exports.Exp_InvalidExponentFormat = '\u7121\u52b9\u306a\u6307\u6570\u306e\u30d5\u30a9\u30fc\u30de\u30c3\u30c8\u3067\u3059';
exports.Exp_InvalidSemicolons = '\u7121\u52b9\u306a\u30d5\u30a9\u30fc\u30de\u30c3\u30c8: \u30bb\u30df\u30b3\u30ed\u30f3\u304c\u591a\u3059\u304e\u307e\u3059';
exports.Exp_InvalidNumberGroupSize = 'NumberGroupSize\u306f\u3001\u6700\u5f8c\u306e\u8981\u7d20\u3092\u9664\u3044\u30661\u304b\u30899\u306e\u9593\u3067\u306a\u3051\u308c\u3070\u306a\u308a\u307e\u305b\u3093\u3002\u6700\u5f8c\u306e\u8981\u7d20\u306f0\u3067\u3082\u304b\u307e\u3044\u307e\u305b\u3093\u3002';
exports.Exp_BadFormatSpecifier = '\u8aa4\u3063\u305f\u30d5\u30a9\u30fc\u30de\u30c3\u30c8\u6307\u793a\u5b50';
exports.Exp_InvalidNumberFormat = '\u7121\u52b9\u306a\u6570\u5024\u30d5\u30a9\u30fc\u30de\u30c3\u30c8\u306e\u30d1\u30bf\u30fc\u30f3\u3067\u3059';
exports.Exp_InvalidCast = '\u4f8b\u5916:\u7121\u52b9\u306a\u30ad\u30e3\u30b9\u30c8';
exports.Exp_Separator = 'numberDecimalSeparator\u3001listSeparator\u304a\u3088\u3073arrayListSeparator\u306f\u30ab\u30eb\u30c1\u30e3\u60c5\u5831\u304c\u7570\u306a\u308b\u5fc5\u8981\u304c\u3042\u308a\u307e\u3059\u3002';


 }),

 "./src/plugins/formatter/formatter.res.ja.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.Exp_TokenIsNull = '\u30c8\u30fc\u30af\u30f3\u304c null \u3067\u3059';
exports.Exp_InvalidBackslash = '\'\\\' \u3092\u8a55\u4fa1\u3067\u304d\u307e\u305b\u3093\u3002';
exports.Exp_FormatIllegal = '\u30d5\u30a9\u30fc\u30de\u30c3\u30c8\u304c\u4e0d\u6b63\u3067\u3059\u3002';
exports.Exp_ValueIsNull = '\u5024\u306f null \u3067\u3059';
exports.Exp_DuplicatedDescriptor = '\u305d\u306e\u7a2e\u985e\u306e\u8a18\u8ff0\u5b50\u306f\u65e2\u306b\u8ffd\u52a0\u3055\u308c\u3066\u3044\u307e\u3059\u3002';
exports.Exp_TokenIllegal = '\u30c8\u30fc\u30af\u30f3\u304c\u4e0d\u6b63\u3067\u3059\u3002';
exports.Exp_ValueIllegal = '\u5024\u304c\u4e0d\u6b63\u3067\u3059\u3002';
exports.Exp_InvalidCast = '\u4f8b\u5916=\u7121\u52b9\u306a\u30ad\u30e3\u30b9\u30c8';


 }),

 "./src/resource.ja.entry.ts":
 (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var commonResource = __webpack_require__( "./src/common/util/util.res.ja.ts");
var formatterResource = __webpack_require__( "./src/plugins/formatter/formatter.res.ja.ts");
var resource = commonResource;
for (var prop in formatterResource) {
    if (formatterResource.hasOwnProperty(prop)) {
        resource[prop] = formatterResource[prop];
    }
}
exports.SR = { ja: resource };


 })

 });

module.exports = GC.Spread.Common;

/***/ }),

/***/ "./resource.ja.entry.js":
/*!******************************!*\
  !*** ./resource.ja.entry.js ***!
  \******************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {


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
    var moduleResources = __webpack_require__(/*! ./node_modules_local/@grapecity/js-sheets-common/dist/gc.spread.common.resources.ja.js */ "./node_modules_local/@grapecity/js-sheets-common/dist/gc.spread.common.resources.ja.js").SR["ja"];
    GC.Spread.Common.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Common = extend2({}, moduleResources);
}
if ( GC.Spread.CalcEngine && GC.Spread.CalcEngine.SR) {
    var moduleResources = __webpack_require__(/*! ./node_modules_local/@grapecity/js-calc/dist/gc.spread.calcengine.resources.ja.js */ "./node_modules_local/@grapecity/js-calc/dist/gc.spread.calcengine.resources.ja.js").SR["ja"];
    GC.Spread.CalcEngine.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].CalcEngine = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets && GC.Spread.Sheets.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/core/core.res.ja.js */ "./dist/core/core.res.ja.js");
    GC.Spread.Sheets.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Sheets = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Bindings && GC.Spread.Sheets.Bindings.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/data/data.res.ja.js */ "./dist/plugins/data/data.res.ja.js");
    GC.Spread.Sheets.Bindings.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Bindings = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Outlines && GC.Spread.Sheets.Outlines.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/group/group.res.ja.js */ "./dist/plugins/group/group.res.ja.js");
    GC.Spread.Sheets.Outlines.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Outlines = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.ConditionalFormatting && GC.Spread.Sheets.ConditionalFormatting.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/conditional/conditional.res.ja.js */ "./dist/plugins/conditional/conditional.res.ja.js");
    GC.Spread.Sheets.ConditionalFormatting.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].ConditionalFormatting = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Touch && GC.Spread.Sheets.Touch.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/touch/touch.res.ja.js */ "./dist/plugins/touch/touch.res.ja.js");
    GC.Spread.Sheets.Touch.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Touch = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.FloatingObjects && GC.Spread.Sheets.FloatingObjects.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/floatingObject/floatingobject.res.ja.js */ "./dist/plugins/floatingObject/floatingobject.res.ja.js");
    GC.Spread.Sheets.FloatingObjects.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].FloatingObjects = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.CellTypes && GC.Spread.Sheets.CellTypes.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/celltype/celltypes.res.ja.js */ "./dist/plugins/celltype/celltypes.res.ja.js");
    GC.Spread.Sheets.CellTypes.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].CellTypes = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Filter && GC.Spread.Sheets.Filter.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/filter/filter.res.ja.js */ "./dist/plugins/filter/filter.res.ja.js");
    GC.Spread.Sheets.Filter.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Filter = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Tables && GC.Spread.Sheets.Tables.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/table/table.res.ja.js */ "./dist/plugins/table/table.res.ja.js");
    GC.Spread.Sheets.Tables.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Tables = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Fill && GC.Spread.Sheets.Fill.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/fill/fill.res.ja.js */ "./dist/plugins/fill/fill.res.ja.js");
    GC.Spread.Sheets.Fill.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Fill = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.ContextMenu && GC.Spread.Sheets.ContextMenu.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/contextMenu/context-menu.res.ja.js */ "./dist/plugins/contextMenu/context-menu.res.ja.js");
    GC.Spread.Sheets.ContextMenu.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].ContextMenu = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.OutlineColumn && GC.Spread.Sheets.OutlineColumn.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/outlineColumn/outlineColumn.res.ja.js */ "./dist/plugins/outlineColumn/outlineColumn.res.ja.js");
    GC.Spread.Sheets.OutlineColumn.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].OutlineColumn = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.StatusBar && GC.Spread.Sheets.StatusBar.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/statusBar/statusBar.res.ja.js */ "./dist/plugins/statusBar/statusBar.res.ja.js");
    GC.Spread.Sheets.StatusBar.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].StatusBar = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.AutoMerge && GC.Spread.Sheets.AutoMerge.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/autoMerge/autoMerge.res.ja.js */ "./dist/plugins/autoMerge/autoMerge.res.ja.js");
    GC.Spread.Sheets.AutoMerge.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].AutoMerge = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Print && GC.Spread.Sheets.Print.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/print/print.res.ja.js */ "./dist/plugins/print/print.res.ja.js");
    GC.Spread.Sheets.Print.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Print = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Charts && GC.Spread.Sheets.Charts.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/chart/chart.res.ja.js */ "./dist/plugins/chart/chart.res.ja.js");
    GC.Spread.Sheets.Charts.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Charts = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.PDF && GC.Spread.Sheets.PDF.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/exportPDF/printPdf.res.ja.js */ "./dist/plugins/exportPDF/printPdf.res.ja.js");
    GC.Spread.Sheets.PDF.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].PDF = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Shapes && GC.Spread.Sheets.Shapes.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/shape/shape.res.ja.js */ "./dist/plugins/shape/shape.res.ja.js");
    GC.Spread.Sheets.Shapes.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Shapes = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.Slicers && GC.Spread.Sheets.Slicers.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/slicer/slicer.res.ja.js */ "./dist/plugins/slicer/slicer.res.ja.js");
    GC.Spread.Sheets.Slicers.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].Slicers = extend2({}, moduleResources);
}
if ( GC.Spread.Pivot && GC.Spread.Pivot.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/pivot/pivot.res.ja.js */ "./dist/plugins/pivot/pivot.res.ja.js");
    GC.Spread.Pivot.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].PivotTables = extend2({}, moduleResources);
}
if ( GC.Spread.Sheets.TableSheet && GC.Spread.Sheets.TableSheet.SR) {
    var moduleResources = __webpack_require__(/*! ./dist/plugins/tableSheet/tableSheet.res.ja.js */ "./dist/plugins/tableSheet/tableSheet.res.ja.js");
    GC.Spread.Sheets.TableSheet.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    if (!GC.Spread.Common.CultureManager._resourcesMap["ja-jp"]) {
        GC.Spread.Common.CultureManager._resourcesMap["ja-jp"] = {};
    }
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].TableSheet = extend2({}, moduleResources);
}
if ( GC.Pivot && GC.Pivot.SR) {
    var moduleResources = __webpack_require__(/*! ./node_modules_local/@grapecity/js-pivot/dist/gc.pivot.resources.ja.js */ "./node_modules_local/@grapecity/js-pivot/dist/gc.pivot.resources.ja.js").SR["ja"];
    GC.Pivot.SR["ja"] = extend({}, GC.Spread.Common.SR["ja"] || {}, moduleResources);
    GC.Spread.Common.CultureManager._resourcesMap["ja-jp"].PivotEngine = extend2({}, moduleResources);
}
module.exports = GC.Spread;

/***/ }),

/***/ "Common":
/*!****************************!*\
  !*** external "GC.Spread" ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = GC.Spread;

/***/ }),

/***/ "Core":
/*!***********************************!*\
  !*** external "GC.Spread.Sheets" ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = GC.Spread.Sheets;

/***/ })

/******/ });
//# sourceMappingURL=gc.spread.sheets.resources.ja.15.2.7.js.map
}));