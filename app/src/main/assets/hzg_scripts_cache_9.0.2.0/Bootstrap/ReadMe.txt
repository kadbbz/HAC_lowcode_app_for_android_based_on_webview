tooltip和dropdown是bootstrap库的一部分
http://www.bootcss.com/

一. JS
在bootstrap代码包中
tooltip.js文件来源：\js\tooltip.js
dropdown.js文件来源：\js\dropdown.js

a. tooltip
在Forguncy，用于显示validation tooltip
为了不干扰用户引用bootstrap库，
在文件中做了如下修改：
1. 注释了// TOOLTIP PLUGIN DEFINITION 部分代码
2. 注释了// TOOLTIP NO CONFLICT 部分代码
3. 将Tooltip类加入Forguncy命名空间
  //Add by Forguncy
  // ===================
  if (Forguncy) {
      Forguncy.Tooltip = Tooltip;
  }

b. dropdown
在Forguncy，用于current user cell type
为了不干扰用户引用bootstrap库，
在文件中做了如下修改：
1. 注释了// DROPDOWN PLUGIN DEFINITION 部分代码
2. 注释了// DROPDOWN NO CONFLICT 部分代码
3. 将属性值dropdown改为_dropdown
  var toggle   = '[data-toggle=_dropdown]'

二. CSS
bootstrap.css文件来源：\dist\css\bootstrap.css
css文件删除了与tooltip，dropdown无关部分，并做了如下修改
  
  .tooltip.top .tooltip-arrow {
   border-top-color: #000; ->#f00
  }

  .tooltip.bottom .tooltip-arrow {
   border-bottom-color: #000; ->#f00
  }