webpackJsonp([15],{188:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=o(92),r=o.n(a),n=o(20),s=o.n(n),i=o(484),l=o.n(i),u=o(234),c=o(469),p=o.n(c),d=o(482),m=(o.n(d),o(134)),f=(o.n(m),o(486)),g=o(231),v=o(483),h=o(232),k=o(233),S=o(472),y=o.n(S),T=o(180),b=o.n(T),x=o(85),I=o.n(x);s.a.use(v.a),s.a.use(f.a),s.a.use(p.a);var C=new v.a({locale:"zh",messages:{en:r()({},h.a,y.a),zh:r()({},k.a,b.a)}});I.a.i18n(function(e,t){return C.t(e,t)}),s.a.prototype.$t=I.a.t,s.a.prototype._i18n=C,s.a.prototype.$common=g.a,s.a.http.interceptors.push(function(e,t){e._timeout&&setTimeout(function(){t(e.respondWith(e.body,{status:408,statusText:"请求超时"}))},e._timeout),t(function(e){return e})}),new s.a({router:u.a,render:function(e){return e(l.a)}}).$mount("#app")},228:function(e,t,o){"use strict";var a="";console.log("NODE_ENV:","production"),console.log("base:",a),t.a={status:a+"/status",serversInfo:a+"/druid/coordinator/v1/servers",login:a+"/login",runningTasks:a+"/druid/indexer/v1/runningTasks",completeTasks:a+"/druid/indexer/v1/completeTasks",pendingTasks:a+"/druid/indexer/v1/pendingTasks",waitingTasks:a+"/druid/indexer/v1/waitingTasks",searchCompleteTasks:a+"/druid/indexer/v1/search/completeTasks",taskChatUrl:a+"/druid/worker/v1/task",baseTaskUrl:a+"/druid/indexer/v1/task",overlordUrl:a+"/druid/indexer/v1",supervisor:a+"/druid/indexer/v1/supervisor",disableDataSource:a+"/druid/coordinator/v1/metadata/disableDatasources",intervals:a+"/druid/coordinator/v1/intervals",dataSource:a+"/druid/coordinator/v1/metadata/datasources",clientInfo:a+"/druid/v2/datasources",rules:a+"/druid/coordinator/v1/rules",lookups:a+"/druid/coordinator/v1/lookups",historicalIps:a+"/system/ip/historical",lookupsHis:a+"/druid/listen/v1/lookups",systemConfig:a+"/druid/systemConfig",loadstatus:a+"/druid/coordinator/v1/loadstatus"}},229:function(e,t,o){"use strict";var a=o(200),r=o.n(a),n=function(e,t){for(var o in e)t.push(e[o])},s={toString:function(e){return e?r()(e,null,2):null},toJsonObject:function(e){return JSON.parse(e)}},i=function(e,t,o){for(var a=[],r=(t-1)*o,n=r+o>=e.length?e.length-r:o,s=0;s<n;s++)a.push(e[r+s]);return a},l=function(e,t,o){return _.orderBy(e,[t],[o])},u=function(e,t,o){for(var a=[],r=0;r<e.length;r++)e[r][t].indexOf(o)>=0&&a.push(e[r]);return a},c=function(e,t,o){var a=new Date;a.setDate(a.getDate()+o),document.cookie=e+"="+escape(t)+(null===o?"":";expires="+a.toGMTString())},p=function(e){var t=void 0,o=new RegExp("(^| )"+e+"=([^;]*)(;|$)");return(t=document.cookie.match(o))?t[2]:null},d=function(e){var t=new Date;t.setTime(t.getTime()-1);var o=p(e);null!=o&&(document.cookie=e+"="+o+";expires="+t.toGMTString())},m=function(e,t){for(var o in e)t[o]=e[o]},f=function(e){var t=void 0;t=e<1024?e.toFixed(2)+"B":e<1048576?(e/1024).toFixed(2)+"KB":e<1073741824?(e/1048576).toFixed(2)+"MB":(e/1073741824).toFixed(2)+"GB";var o=t+"",a=o.indexOf(".");return"00"==o.substr(a+1,2)?o.substring(0,a)+o.substr(a+3,2):o};t.a={pushData:n,JSONUtils:s,fillShowTableData:i,sortArray:l,searchArray:u,setCookie:c,getCookie:p,delCookie:d,fillObject:m,conver:f}},230:function(e,t,o){"use strict";var a=o(20),r=o.n(a);t.a=new r.a},231:function(e,t,o){"use strict";var a=o(228),r=o(229),n=o(230);t.a={apis:a.a,methods:r.a,eventBus:n.a}},232:function(e,t,o){"use strict";t.a={message:{login:{buttonContent:"login",username:"username",password:"password",ruleMessage1:"please input username",ruleMessage2:"please input password",errorTips:"username or password is wrong"},header:{title:"Tindex Backstage Manager System",logout:"Logout",changeLanuage:"Change Lanuage",lanuage1:"Chinese",lanuage2:"English"},sideBar:{dataAccess:{title:"Data Access",supervisor:"Supervisor Manager",task:"Task Manager"},info:{title:"System Manager",status:"System Status",serversInfo:"Servers Info",systemConfig:"System Config"},dataSource:{title:"DataSource Manage",dataSource:"DataSources(cluster)",metadata:"DataSources(Metadata)"},lookup:{title:"Lookup Manage",lookupCoordinator:"Lookup Coordinator",lookupHistorical:"Lookup Historical"}},status:{version:"Version:",module:"System Modules",name:"name",artifact:"artifact",_version:"version",memory:"Memory",usedMemory:"usedMemory",freeMemory:"freeMemory",totalMemory:"totalMemory",maxMemory:"maxMemory"},serversInfo:{host:"host",title:"Servers Info",server:"Server",tier:"tier",priority:"priority",currSize:"currSize",maxSize:"maxSize",segments:"segments",searchTips:"please input name"},tasks:{runningTasksTitle:"Running Tasks",pendingTasksTitle:"Pending Tasks - Tasks waiting to be assigned to a worker",waitingTasksTitle:"Waiting Tasks - Tasks waiting on locks ",completeTasksTitle:"Complete Tasks - Tasks recently completed",createTask:"Create Task",taskSpec:"Task Spec",format:"format",jsonSyntaxError:"wrong json syntax",createDialogConfirm:"Create",creatSuccess:"create successfully, taskId",createFailedFront:"request failed with status",createFailedLast:"please check overlord logs",searchTips:"please input id",searchTopicTips:"please input topic name",search:"Search",refresh:"Refresh",createdTime:"createdTime",queueInsertTime:"queueInsertTime",location:"location",operation:"more",taskPayloadTitle:"Task Payload",payload:"payload",taskStatusTitle:"Task Status",status:"status",allLog:"log(all)",partLog:"log(8kb)",delete:"kill",dialogConfirm:"Confirm",dialogCancel:"Cancel",killTips:"Do you really want to kill",killConfirmTitle:"Warning",killSuccess:"Kill Successfully!",locationHost:"location host",locationPort:"location port",selectStatus:"please select status",statusItem1:"ALL",statusItem2:"SUCCESS",statusItem3:"FAILED",duration:"duration",topic:"topic",offsets:"offsets",currOffset:"currOffset",taskOffsetTitle:"Offset",metrics:"metrics",period:"period",datasource:"datasource",startOffset:"startOffset"},supervisors:{supervisorManager:"Supervisor Manager",supervisorTasks:"Supervisor Tasks",runningSuperviorTitle:"Running Supervisors",completeSupervisorsTitle:"Complete Supervisors",searchTips:"please input id",search:"Search",refresh:"Refresh",operation:"more",tasks:"tasks",spec:"spec",status:"status",reset:"reset",delete:"kill",dialogConfirm:"Confirm",dialogCancel:"Cancel",specTitle:"Supervisor Spec",statusTitle:"Supervisor Status",resetTips:"Do you really want to reset",resetSuccessMsg:"reset successfully, id",resetErrMsg:"reset failed,please check overlord logs",dialogWarningTitle:"Warning",killTips:"Do you really want to kill",killSuccess:"Kill Successfully!",killFailed:"Kill failed,please check overlord logs",version:"version",taskDuration:"taskDuration",type:"type",topic:"topic",reuse:"reuse",supervisorStatusTitle:"Supervisor Status",supervisorSpec:"Supervisor Spec",createSupervisor:"createSupervisor",format:"format",createDialogConfirm:"Create",jsonSyntaxError:"wrong json syntax",creatSuccess:"create successfully, id",createFailedFront:"request failed with status",createFailedLast:"please check overlord logs"},dataSource:{dataSourceTitle:"DataSources",dataSource:"DataSource",segments:"segments",maxTime:"maxTime",minTime:"minTime",tiers:"tiers",intervals:"intervals",tiersInfo:"Tiers Info",warning:"Warning",createTime:"createTime",dimensions:"dimensions",metrics:"metrics",candidates:"candidates",dimensionsInfo:"Dimensions Info",metricsInfo:"Metrics Info",candidatesInfo:"Candidates Info",rules:"rules",history:"history",add:"add",rulesInfo:"Rules Info",rulesHistory:"Rules History",addRulesSuccess:"Add Rules Success!",addRulesFail:"Add Rules Fail!",addRuleWarning:"Do you really add rule?",dataSourceInfo:"DataSource Info",serversInfo:"Server Info",granularity:"granularity",operate:"operate",granularityInfo:"Please select a granularity",operateInfo:"Please select an operation",periodInputInfo:"Please input a valid ISO 8601 duration",intervalInputInfo:"Please input a valid ISO 8601 interval",addRule:"Add A Rule",servers:"servers"},interval:{intervalTitle:"Intervals",interval:"Interval",refresh:"Refresh",name:"name",segments:"segments",more:"more",segmentCount:"segment count"},segment:{segmentTitle:"Segments",segment:"Segment",refresh:"Refresh",name:"name",info:"info",more:"more",delete:"delete",segmentInfo:"Segment Info",confirm:"Cofirm",cancle:"Cancle",confirmButtonText:"Confirm",cancleButtonTextL:"Cancle"},lookup:{lookupCoordinator:{lookupTitle:"Lookup"},lookupHistorical:{},lookups:"Lookup Tier",userGroupLookup:"Lookup",type:"type",version:"version",lookupInfo:"Lookup Info",addLookup:"Create Lookup",lookupName:"Lookup Name",lookupNameIndex:"Please input the lookup name",addLookupWarning:"Do you really want to add lookup:",updateLookupWarning:"Do you really want to update lookup:",inputLookupName:"Please input lookup name",groupId:"groupId"},common:{more:"more",info:"info",delete:"delete",size:"size",confirm:"Cofirm",cancle:"Cancle",refresh:"Refresh",name:"name",count:"count",search:"Search",warning:"Warning",deleteWarning:"Do you really want to delete:",disableWarning:"Do you really want to disable:",enableWarning:"Do you really want to enable:",enableSuccess:"Enable Success!",enableFail:"Enable Fail!",deleteSuccess:"Delete Success!",deleteFail:"Delete Fail!",addSuccess:"Add Success!",updateSuccess:"Update Success!",updateFail:"Update Fail!",disableSuccess:"Disable Success!",disableFail:"Disable Fail!",addFail:"Add Fail!",items:"items",disable:"disable",inputName:"Please input name",update:"update",return:"Back",type:"type",operation:"operation",detail:"detail",close:"close",enable:"enable",state:"state"},error:{chooseAction:"Action can not be null! Please choose an action",chooseGranularity:"Granularity can not be null! Please choose a granularity",canNotBeNull:" can not be null!"},systemConfig:{systemConfigTitle:"System Config",updateConfig:"Do you really want to update config?"}}}},233:function(e,t,o){"use strict";var a,r=o(201),n=o.n(r);t.a={message:{login:{buttonContent:"登录",username:"用户名",password:"密码",ruleMessage1:"请输入用户名",ruleMessage2:"请输入密码",errorTips:"用户名或密码错误"},header:{title:"Tindex后台管理系统",logout:"退出",changeLanuage:"切换语言",lanuage1:"中文",lanuage2:"英文"},tasks:{runningTasksTitle:"运行任务",pendingTasksTitle:"队列任务 - 等待分配资源的任务",waitingTasksTitle:"阻塞任务 - 等待获取锁的任务 ",completeTasksTitle:"已完成任务",createTask:"创建新任务",taskSpec:"任务说明",format:"格式化",jsonSyntaxError:"json格式错误",createDialogConfirm:"创建",creatSuccess:"任务创建成功, 任务Id",createFailedFront:"任务创建失败,状态码",createFailedLast:"请查看overlord日志",searchTips:"请输入id",searchTopicTips:"请输入topic名称",search:"搜索",refresh:"刷新",createdTime:"创建时间",queueInsertTime:"入队时间",location:"位置",operation:"操作",taskPayloadTitle:"任务说明",payload:"说明",taskStatusTitle:"任务状态",status:"状态",allLog:"日志",partLog:"日志(8kb)",delete:"删除",dialogConfirm:"确定",dialogCancel:"取消",killTips:"你确定要删除",killConfirmTitle:"警告",killSuccess:"删除成功!",locationHost:"主机",locationPort:"端口",selectStatus:"请选择状态",statusItem1:"全部",statusItem2:"成功",statusItem3:"失败",duration:"运行时长",topic:"topic",offsets:"offsets",currOffset:"currOffset",taskOffsetTitle:"Offset",metrics:"指标",period:"阶段",datasource:"数据源",startOffset:"startOffset"},supervisors:{supervisorManager:"Supervisor管理",supervisorTasks:"Supervisor任务",runningSuperviorTitle:"运行supervisors",completeSupervisorsTitle:"已完成Supervisors",searchTips:"请输入id",search:"搜索",refresh:"刷新",operation:"操作",tasks:"任务",spec:"说明",status:"状态",reset:"重置",delete:"删除",dialogConfirm:"确定",dialogCancel:"取消",specTitle:"Supervisor 说明",statusTitle:"Supervisor 状态",resetTips:"你确定要重置",resetSuccessMsg:"重置成功, id",resetErrMsg:"重置失败,请查看overlord日志",dialogWarningTitle:"警告",killTips:"你确定要删除",killSuccess:"删除成功!",killFailed:"删除失败,请查看overlord日志",version:"版本",taskDuration:"任务持续时间",type:"类型",topic:"topic",reuse:"重用",supervisorStatusTitle:"Supervisor 状态",supervisorSpec:"Supervisor 说明",createSupervisor:"创建Supervisor",format:"格式化",createDialogConfirm:"创建",jsonSyntaxError:"json格式错误",creatSuccess:"创建成功, id",createFailedFront:"创建失败,状态码",createFailedLast:"请查看overlord日志"},sideBar:{dataAccess:{title:"数据接入",supervisor:"supervisor管理",task:"task管理"},info:{title:"系统管理",status:"系统状态",serversInfo:"节点信息",systemConfig:"系统配置"},dataSource:{title:"数据源管理",dataSource:"数据源（集群）",metadata:"数据源（元数据）"},lookup:{title:"用户分群管理",lookupCoordinator:"用户分群（Coordinator）",lookupHistorical:"用户分群（Historical）"}},status:{version:"版本:",module:"系统模块",name:"名称",artifact:"模块",_version:"版本",memory:"内存",usedMemory:"已用内存",freeMemory:"可用内存",totalMemory:"总内存",maxMemory:"最大内存"},serversInfo:{host:"host",title:"节点信息",server:"节点",tier:"tier",priority:"优先级",currSize:"当前大小",maxSize:"最大大小",segments:"数据段",searchTips:"请输入名称"},dataSource:{dataSourceTitle:"数据源",dataSource:"数据源",search:"搜索",refresh:"刷新",name:"名称",segments:"数据段",count:"数量",size:"大小",maxTime:"最大时间",minTime:"最小时间",more:"更多",tiers:"等级",intervals:"区间",delete:"删除",tiersInfo:"等级信息",createTime:"创建时间",dimensions:"维度",metrics:"指标",candidates:"candidates",dimensionsInfo:"维度信息",metricsInfo:"指标信息",candidatesInfo:"Candidates信息",rules:"规则",history:"历史",add:"添加",rulesInfo:"规则信息",serversInfo:"节点信息",rulesHistory:"规则（历史信息）",addRulesSuccess:"添加规则成功！",addRulesFail:"添加规则失败！",addRuleWarning:"你确定要添加规则？",dataSourceInfo:"数据源信息",granularity:"操作粒度",operate:"操作",granularityInfo:"请选择操作粒度",operateInfo:"请选择操作",periodInputInfo:"请输入一个有效的 ISO 8601 period",intervalInputInfo:"请输入一个有效的 ISO 8601 interval",addRule:"添加规则",servers:"节点"},interval:{intervalTitle:"区间",interval:"区间",refresh:"刷新",name:"名称",segments:"数据段",more:"更多",segmentCount:"数据段数量"},segment:{segmentTitle:"数据段",segment:"数据段",refresh:"刷新",name:"名称",info:"信息",more:"更多",delete:"删除",segmentInfo:"数据段信息",confirm:"确 定",cancle:"取 消",confirmButtonText:"确定",cancleButtonTextL:"取消"},lookup:{lookupCoordinator:{lookupTitle:"用户分群"},lookupHistorical:{},lookups:"分群组",userGroupLookup:"分群",type:"类型",version:"版本",lookupInfo:"分群信息",addLookup:"创建分群",lookupName:"分群名称",lookupNameIndex:"请输入分群名称",addLookupWarning:"你确定要添加分群：",updateLookupWarning:"你确定要更新分群：",inputLookupName:"请输入分群名称",groupId:"分组Id"},common:(a={more:"操作",info:"详情",delete:"删除",size:"大小",confirm:"确 认",cancle:"取 消",refresh:"刷新",name:"名称",count:"数量",search:"搜索",warning:"提醒",deleteWarning:"你确定要删除：",disableWarning:"你确定要失效:",enableWarning:"你确定要恢复:",enableSuccess:"恢复成功!",enableFail:"恢复失败!",deleteSuccess:"删除成功!",deleteFail:"删除失败!",addSuccess:"添加成功！",updateSuccess:"更新成功！",updateFail:"更新失败！",disableSuccess:"失效成功!",disableFail:"失效失败!",addFail:"添加失败！",items:"条目明细",disable:"失效",return:"返回",inputName:"请输入名称",update:"更新"},n()(a,"return","返回"),n()(a,"type","类型"),n()(a,"operation","操作"),n()(a,"detail","详情"),n()(a,"close","关闭"),n()(a,"enable","恢复"),n()(a,"state","状态"),a),error:{chooseAction:"操作不能为空! 请选择一种操作",chooseGranularity:"操作粒度不能为空! 请选择一种操作粒度",canNotBeNull:" 不能为空!"},systemConfig:{systemConfigTitle:"系统配置",updateConfig:"你确认要更新配置吗？"}}}},234:function(e,t,o){"use strict";var a=o(20),r=o.n(a),n=o(487);r.a.use(n.a),t.a=new n.a({routes:[{path:"/",redirect:"/login"},{path:"/index",component:function(e){return o.e(13).then(function(){var t=[o(492)];e.apply(null,t)}.bind(this)).catch(o.oe)},children:[{path:"/",component:function(e){return o.e(0).then(function(){var t=[o(204)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/status",component:function(e){return o.e(11).then(function(){var t=[o(499)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{name:"serversInfo",path:"/serversInfo",component:function(e){return o.e(12).then(function(){var t=[o(498)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{name:"serversSegment",path:"/serversSegment/:serverName",component:function(e){return o.e(8).then(function(){var t=[o(500)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{name:"supervisorManager",path:"/supervisorManager",component:function(e){return o.e(0).then(function(){var t=[o(204)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{name:"supervisorTasks",path:"/:supervisorId/supervisorTasks",component:function(e){return o.e(3).then(function(){var t=[o(494)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{name:"taskManager",path:"/taskManager",component:function(e){return o.e(4).then(function(){var t=[o(493)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/dataSource",component:function(e){return o.e(1).then(function(){var t=[o(495)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/interval",component:function(e){return o.e(2).then(function(){var t=[o(496)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/segment",component:function(e){return o.e(6).then(function(){var t=[o(497)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/lookupCoordinator",component:function(e){return o.e(10).then(function(){var t=[o(502)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/lookupHistorical",component:function(e){return o.e(9).then(function(){var t=[o(503)];e.apply(null,t)}.bind(this)).catch(o.oe)}},{path:"/systemConfig",component:function(e){return o.e(5).then(function(){var t=[o(504)];e.apply(null,t)}.bind(this)).catch(o.oe)}}]},{path:"/login",component:function(e){return o.e(7).then(function(){var t=[o(501)];e.apply(null,t)}.bind(this)).catch(o.oe)}}]})},461:function(e,t,o){t=e.exports=o(90)(void 0),t.i(o(463),""),t.i(o(462),""),t.push([e.i,"",""])},462:function(e,t,o){t=e.exports=o(90)(void 0),t.push([e.i,".header{background-color:#242f42}.login-wrap{background:#324157}.plugins-tips{background:#eef1f6}.el-upload--text em,.plugins-tips a{color:#20a0ff}.pure-button{background:#20a0ff}",""])},463:function(e,t,o){t=e.exports=o(90)(void 0),t.push([e.i,"*{margin:0;padding:0}#app,.wrapper,body,html{width:100%;height:100%;overflow:hidden}body{font-family:Helvetica Neue,Helvetica,microsoft yahei,arial,STHeiTi,sans-serif}a{text-decoration:none}.content{background:none repeat scroll 0 0 #fff;position:absolute;left:250px;right:0;top:70px;bottom:0;width:auto;padding:35px;box-sizing:border-box;overflow-y:scroll}.crumbs{margin-bottom:20px}.pagination{margin:20px 0;text-align:right}.plugins-tips{padding:20px 10px;margin-bottom:20px}.el-button+.el-tooltip{margin-left:10px}.el-table tr:hover{background:#f6faff}.mgb20{margin-bottom:20px}.move-enter-active,.move-leave-active{transition:opacity .5s}.move-enter,.move-leave{opacity:0}.form-box{width:600px}.form-box .line{text-align:center}.el-time-panel__content:after,.el-time-panel__content:before{margin-top:-7px}.ms-doc .el-checkbox__input.is-disabled+.el-checkbox__label{color:#333;cursor:pointer}.pure-button{width:150px;height:40px;line-height:40px;text-align:center;color:#fff;border-radius:3px}.g-core-image-corp-container .info-aside{height:45px}.el-upload--text{background-color:#fff;border:1px dashed #d9d9d9;border-radius:6px;box-sizing:border-box;width:360px;height:180px;cursor:pointer;position:relative;overflow:hidden}.el-upload--text .el-icon-upload{font-size:67px;color:#97a8be;margin:40px 0 16px;line-height:50px}.el-upload--text{color:#97a8be;font-size:14px;text-align:center}.el-upload--text em{font-style:normal}.ql-container{min-height:400px}.ql-snow .ql-tooltip{transform:translateX(117.5px) translateY(10px)!important}.editor-btn{margin-top:20px}",""])},482:function(e,t){},484:function(e,t,o){o(488);var a=o(191)(null,o(485),null,null);e.exports=a.exports},485:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{attrs:{id:"app"}},[o("router-view")],1)},staticRenderFns:[]}},488:function(e,t,o){var a=o(461);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);o(198)("1022fd05",a,!0)},490:function(e,t){},491:function(e,t,o){o(134),e.exports=o(188)}},[491]);