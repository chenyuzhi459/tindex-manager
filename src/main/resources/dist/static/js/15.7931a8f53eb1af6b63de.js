webpackJsonp([15],{1026:function(e,t,i){e.exports=i.p+"static/img/img.2aab7b4.jpg"},1038:function(e,t,i){i(1097);var s=i(198)(i(599),i(1056),"data-v-1335eb38",null);e.exports=s.exports},1039:function(e,t,i){i(1096);var s=i(198)(i(601),i(1055),"data-v-11d0447e",null);e.exports=s.exports},1055:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"sidebar"},[i("el-menu",{staticClass:"el-menu-vertical-demo",attrs:{"default-active":e.onRoutes,theme:"dark","unique-opened":"",router:""}},[e._l(e.items,function(t){return[t.subs?[i("el-submenu",{attrs:{index:t.index}},[i("template",{attrs:{slot:"title"},slot:"title"},[i("i",{class:t.icon}),e._v(e._s(t.title))]),e._v(" "),e._l(t.subs,function(t,s){return i("el-menu-item",{key:s,attrs:{index:t.index}},[e._v(e._s(t.title)+"\n          ")])})],2)]:[i("el-menu-item",{attrs:{index:t.index}},[i("i",{class:t.icon}),e._v(e._s(t.title)+"\n        ")])]]})],2)],1)},staticRenderFns:[]}},1056:function(e,t,i){e.exports={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"header"},[s("div",{staticClass:"logo"},[s("span",[e._v("\n            "+e._s(e.$t("message.header.title"))+"\n        ")])]),e._v(" "),s("div",{staticClass:"user-info"},[s("el-dropdown",{on:{command:e.handleCommand}},[s("span",{staticClass:"el-dropdown-link"},[s("img",{staticClass:"user-logo",attrs:{src:i(1026)}}),e._v(" "+e._s(e.username)+"\n            ")]),e._v(" "),s("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[s("el-dropdown-item",{attrs:{command:"loginout"}},[e._v(" "+e._s(e.$t("message.header.logout")))])],1)],1)],1),e._v(" "),s("div",{staticClass:"lanuage"},[s("el-dropdown",{on:{command:e.changeLanuage}},[s("span",{staticClass:"el-dropdown-link"},[e._v("\n                "+e._s(e.$t("message.header.changeLanuage"))+"\n            ")]),e._v(" "),s("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[s("el-dropdown-item",{attrs:{command:"zh"}},[e._v(" 中文")]),e._v(" "),s("el-dropdown-item",{attrs:{command:"en"}},[e._v("English")])],1)],1)],1)])},staticRenderFns:[]}},1058:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"wrapper"},[i("v-head"),e._v(" "),i("v-sidebar"),e._v(" "),i("div",{staticClass:"content"},[i("transition",{attrs:{name:"move",mode:"out-in"}},[i("router-view")],1)],1)],1)},staticRenderFns:[]}},1096:function(e,t,i){var s=i(743);"string"==typeof s&&(s=[[e.i,s,""]]),s.locals&&(e.exports=s.locals);i(196)("71ccfea0",s,!0)},1097:function(e,t,i){var s=i(744);"string"==typeof s&&(s=[[e.i,s,""]]),s.locals&&(e.exports=s.locals);i(196)("3f2d7fb7",s,!0)},513:function(e,t,i){var s=i(198)(i(600),i(1058),null,null);e.exports=s.exports},599:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={data:function(){return{name:"admin"}},created:function(){this.init()},computed:{username:function(){var e=localStorage.getItem("ms_username");return e||this.name}},methods:{init:function(){var e=this.$common.methods.getCookie("lanuage");if("zh"!==e&&"en"!==e)return this._i18n.locale="zh",void this.$common.methods.setCookie("lanuage","zh");this._i18n.locale=e},handleCommand:function(e){"loginout"===e&&(localStorage.removeItem("ms_username"),this.$router.push("/login"))},changeLanuage:function(e){"zh"!==e&&"en"!==e||this._i18n.locale!==e&&(this.$common.methods.setCookie("lanuage",e),history.go(0))}}}},600:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=i(1038),a=i.n(s),n=i(1039),o=i.n(n);t.default={components:{vHead:a.a,vSidebar:o.a}}},601:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.default={data:function(){return{items:[{icon:"el-icon-setting",index:"readme",title:"自述"},{index:"dataAccess",title:this.$t("message.sideBar.dataAccess.title"),subs:[{index:"/supervisorManager",title:this.$t("message.sideBar.dataAccess.supervisor")},{index:"/taskManager",title:this.$t("message.sideBar.dataAccess.task")}]},{index:"info",title:this.$t("message.sideBar.info.title"),subs:[{index:"/status",title:this.$t("message.sideBar.info.status")},{index:"/serversInfo",title:this.$t("message.sideBar.info.serversInfo")}]},{index:"dataSource",title:this.$t("message.sideBar.dataSource.title"),subs:[{index:"/dataSource",title:this.$t("message.sideBar.dataSource.dataSource")},{index:"/mDataSource",title:this.$t("message.sideBar.dataSource.metadata")}]},{index:"lookup",title:this.$t("message.sideBar.lookup.title"),subs:[{index:"/lookupCoordinator",title:this.$t("message.sideBar.lookup.lookupCoordinator")},{index:"/lookupHistorical",title:this.$t("message.sideBar.lookup.lookupHistorical")}]},{index:"systemConfig",title:this.$t("message.sideBar.systemConfig.title")},{icon:"el-icon-menu",index:"2",title:"表格",subs:[{index:"basetable",title:"基础表格"},{index:"vuetable",title:"Vue表格组件"}]},{icon:"el-icon-date",index:"3",title:"表单",subs:[{index:"baseform",title:"基本表单"},{index:"vueeditor",title:"编辑器"},{index:"markdown",title:"markdown"},{index:"upload",title:"文件上传"}]}]}},computed:{onRoutes:function(){return this.$route.path.replace("/","")}}}},743:function(e,t,i){t=e.exports=i(87)(void 0),t.push([e.i,".sidebar[data-v-11d0447e]{display:block;position:absolute;width:250px;left:0;top:70px;bottom:0;background:#2e363f}.sidebar>ul[data-v-11d0447e]{height:100%}",""])},744:function(e,t,i){t=e.exports=i(87)(void 0),t.push([e.i,".header[data-v-1335eb38]{position:relative;box-sizing:border-box;width:100%;height:70px;font-size:22px;line-height:70px;color:#fff}.header .logo[data-v-1335eb38]{float:left;width:400px;padding-left:20px;text-align:left}.user-info[data-v-1335eb38]{float:right;padding-right:50px;font-size:16px;color:#fff}.user-info .el-dropdown-link[data-v-1335eb38]{position:relative;display:inline-block;padding-left:50px;color:#fff;cursor:pointer;vertical-align:middle}.user-info .user-logo[data-v-1335eb38]{position:absolute;left:0;top:15px;width:40px;height:40px;border-radius:50%}.lanuage[data-v-1335eb38]{float:right;padding-right:25px;font-size:16px;color:#fff}.lanuage .el-dropdown-link[data-v-1335eb38]{position:relative;display:inline-block;padding-left:50px;color:#fff;cursor:pointer;vertical-align:middle}.el-dropdown-menu__item[data-v-1335eb38]{text-align:center}",""])}});