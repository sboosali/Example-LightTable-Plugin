if(!lt.util.load.provided_QMARK_('lt.plugins.example')) {
goog.provide('lt.plugins.example');
goog.require('cljs.core');
goog.require('lt.objs.files');
goog.require('lt.util.dom');
goog.require('lt.objs.tabs');
goog.require('lt.objs.popup');
goog.require('lt.objs.popup');
goog.require('lt.objs.proc');
goog.require('lt.util.dom');
goog.require('clojure.string');
goog.require('lt.objs.command');
goog.require('lt.objs.files');
goog.require('clojure.string');
goog.require('lt.object');
goog.require('lt.object');
goog.require('lt.objs.proc');
goog.require('lt.objs.tabs');
goog.require('lt.objs.command');
lt.plugins.example.re_raise = (function re_raise(this$,trigger){return (function (event){lt.util.dom.prevent.call(null,event);
return lt.object.raise.call(null,this$,trigger);
});
});
lt.plugins.example.ok_button = (function ok_button(this$){var e__8189__auto__ = crate.core.html.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"input","input",1114262332),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"type","type",1017479852),"submit",new cljs.core.Keyword(null,"value","value",1125876963),"OK"], null)], null));var seq__8955_8973 = cljs.core.seq.call(null,cljs.core.partition.call(null,2,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"click","click",1108654330),lt.plugins.example.re_raise.call(null,this$,new cljs.core.Keyword(null,"click","click",1108654330))], null)));var chunk__8956_8974 = null;var count__8957_8975 = 0;var i__8958_8976 = 0;while(true){
if((i__8958_8976 < count__8957_8975))
{var vec__8959_8977 = cljs.core._nth.call(null,chunk__8956_8974,i__8958_8976);var ev__8190__auto___8978 = cljs.core.nth.call(null,vec__8959_8977,0,null);var func__8191__auto___8979 = cljs.core.nth.call(null,vec__8959_8977,1,null);lt.util.dom.on.call(null,e__8189__auto__,ev__8190__auto___8978,func__8191__auto___8979);
{
var G__8980 = seq__8955_8973;
var G__8981 = chunk__8956_8974;
var G__8982 = count__8957_8975;
var G__8983 = (i__8958_8976 + 1);
seq__8955_8973 = G__8980;
chunk__8956_8974 = G__8981;
count__8957_8975 = G__8982;
i__8958_8976 = G__8983;
continue;
}
} else
{var temp__4092__auto___8984 = cljs.core.seq.call(null,seq__8955_8973);if(temp__4092__auto___8984)
{var seq__8955_8985__$1 = temp__4092__auto___8984;if(cljs.core.chunked_seq_QMARK_.call(null,seq__8955_8985__$1))
{var c__7561__auto___8986 = cljs.core.chunk_first.call(null,seq__8955_8985__$1);{
var G__8987 = cljs.core.chunk_rest.call(null,seq__8955_8985__$1);
var G__8988 = c__7561__auto___8986;
var G__8989 = cljs.core.count.call(null,c__7561__auto___8986);
var G__8990 = 0;
seq__8955_8973 = G__8987;
chunk__8956_8974 = G__8988;
count__8957_8975 = G__8989;
i__8958_8976 = G__8990;
continue;
}
} else
{var vec__8960_8991 = cljs.core.first.call(null,seq__8955_8985__$1);var ev__8190__auto___8992 = cljs.core.nth.call(null,vec__8960_8991,0,null);var func__8191__auto___8993 = cljs.core.nth.call(null,vec__8960_8991,1,null);lt.util.dom.on.call(null,e__8189__auto__,ev__8190__auto___8992,func__8191__auto___8993);
{
var G__8994 = cljs.core.next.call(null,seq__8955_8985__$1);
var G__8995 = null;
var G__8996 = 0;
var G__8997 = 0;
seq__8955_8973 = G__8994;
chunk__8956_8974 = G__8995;
count__8957_8975 = G__8996;
i__8958_8976 = G__8997;
continue;
}
}
} else
{}
}
break;
}
return e__8189__auto__;
});
lt.plugins.example.hello_panel = (function hello_panel(this$,x){var e__8189__auto__ = crate.core.html.call(null,new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#Id.Class","div#Id.Class",3395077641),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"height","height",4087841945),200,new cljs.core.Keyword(null,"width","width",1127031096),200], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"input","input",1114262332),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"type","type",1017479852),"text",new cljs.core.Keyword(null,"placeholder","placeholder",1612151013),"New Plugin"], null)], null),lt.plugins.example.ok_button.call(null,this$)], null));var seq__8967_8998 = cljs.core.seq.call(null,cljs.core.partition.call(null,2,cljs.core.PersistentVector.EMPTY));var chunk__8968_8999 = null;var count__8969_9000 = 0;var i__8970_9001 = 0;while(true){
if((i__8970_9001 < count__8969_9000))
{var vec__8971_9002 = cljs.core._nth.call(null,chunk__8968_8999,i__8970_9001);var ev__8190__auto___9003 = cljs.core.nth.call(null,vec__8971_9002,0,null);var func__8191__auto___9004 = cljs.core.nth.call(null,vec__8971_9002,1,null);lt.util.dom.on.call(null,e__8189__auto__,ev__8190__auto___9003,func__8191__auto___9004);
{
var G__9005 = seq__8967_8998;
var G__9006 = chunk__8968_8999;
var G__9007 = count__8969_9000;
var G__9008 = (i__8970_9001 + 1);
seq__8967_8998 = G__9005;
chunk__8968_8999 = G__9006;
count__8969_9000 = G__9007;
i__8970_9001 = G__9008;
continue;
}
} else
{var temp__4092__auto___9009 = cljs.core.seq.call(null,seq__8967_8998);if(temp__4092__auto___9009)
{var seq__8967_9010__$1 = temp__4092__auto___9009;if(cljs.core.chunked_seq_QMARK_.call(null,seq__8967_9010__$1))
{var c__7561__auto___9011 = cljs.core.chunk_first.call(null,seq__8967_9010__$1);{
var G__9012 = cljs.core.chunk_rest.call(null,seq__8967_9010__$1);
var G__9013 = c__7561__auto___9011;
var G__9014 = cljs.core.count.call(null,c__7561__auto___9011);
var G__9015 = 0;
seq__8967_8998 = G__9012;
chunk__8968_8999 = G__9013;
count__8969_9000 = G__9014;
i__8970_9001 = G__9015;
continue;
}
} else
{var vec__8972_9016 = cljs.core.first.call(null,seq__8967_9010__$1);var ev__8190__auto___9017 = cljs.core.nth.call(null,vec__8972_9016,0,null);var func__8191__auto___9018 = cljs.core.nth.call(null,vec__8972_9016,1,null);lt.util.dom.on.call(null,e__8189__auto__,ev__8190__auto___9017,func__8191__auto___9018);
{
var G__9019 = cljs.core.next.call(null,seq__8967_9010__$1);
var G__9020 = null;
var G__9021 = 0;
var G__9022 = 0;
seq__8967_8998 = G__9019;
chunk__8968_8999 = G__9020;
count__8969_9000 = G__9021;
i__8970_9001 = G__9022;
continue;
}
}
} else
{}
}
break;
}
return e__8189__auto__;
});
lt.object.object_STAR_.call(null,new cljs.core.Keyword("lt.plugins.example","hello","lt.plugins.example/hello",1017994055),new cljs.core.Keyword(null,"tags","tags",1017456523),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"hello","hello",1113066564)], null),new cljs.core.Keyword(null,"name","name",1017277949),"hello",new cljs.core.Keyword(null,"init","init",1017141378),(function (this$){return lt.plugins.example.hello_panel.call(null,this$,"world!");
}));
lt.plugins.example.__BEH__destroy_on_close = (function __BEH__destroy_on_close(this$){return lt.object.raise.call(null,this$,new cljs.core.Keyword(null,"destroy","destroy",2571277164));
});
lt.object.behavior_STAR_.call(null,new cljs.core.Keyword("lt.plugins.example","destroy-on-close","lt.plugins.example/destroy-on-close",4290993038),new cljs.core.Keyword(null,"reaction","reaction",4441361819),lt.plugins.example.__BEH__destroy_on_close,new cljs.core.Keyword(null,"triggers","triggers",2516997421),new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"close","close",1108660586),null], null), null));
lt.plugins.example.__BEH__print_on_submit = (function __BEH__print_on_submit(this$){var name = lt.util.dom.val.call(null,lt.util.dom.$.call(null,"input"));return cljs.core.print.call(null,name);
});
lt.object.behavior_STAR_.call(null,new cljs.core.Keyword("lt.plugins.example","print-on-submit","lt.plugins.example/print-on-submit",3245686643),new cljs.core.Keyword(null,"reaction","reaction",4441361819),lt.plugins.example.__BEH__print_on_submit,new cljs.core.Keyword(null,"triggers","triggers",2516997421),new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"click","click",1108654330),null], null), null));
lt.plugins.example.hello = lt.object.create.call(null,new cljs.core.Keyword("lt.plugins.example","hello","lt.plugins.example/hello",1017994055));
lt.objs.command.command.call(null,new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"command","command",1964298941),new cljs.core.Keyword("lt.plugins.example","say-hello","lt.plugins.example/say-hello",2025420313),new cljs.core.Keyword(null,"desc","desc",1016984067),"Example: Say Hey",new cljs.core.Keyword(null,"exec","exec",1017031683),(function (){return lt.objs.tabs.add_or_focus_BANG_.call(null,lt.plugins.example.hello);
})], null));
lt.util.dom.val.call(null,lt.util.dom.$.call(null,"input"));
}

//# sourceMappingURL=example_compiled.js.map