// requires tableHelper
var oTable = [];

var killSupervisor = function(taskId) {
  if(confirm('Do you really want to kill: '+taskId)) {
    $.ajax({
    	type:"POST",
    	url: '/druid/indexer/v1/supervisor/'+ taskId +'/shutdown',
    	data:'',
//    	datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
    	success:function(data){
    	    alert("delete successfully, id:" + data.id);
    	},
    	complete: function(request, textStatus){
    	   setTimeout(function() { location.reload(true) }, 750);
    	},
    	error: function(data){
    		alert('Kill request failed with status: '+data.status+' please check overlord logs');
    	}
     });
  }
}

var resetSupervisor = function(taskId) {
  if(confirm('Do you really want to reset: '+taskId)) {
    $.ajax({
    	type:"POST",
    	url: '/druid/indexer/v1/supervisor/'+ taskId +'/reset',
    	data:'',
//    	datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
    	success:function(data){
    	    alert("reset successfully, id:" + data.id);
    	},
    	complete: function(request, textStatus){
    	   setTimeout(function() { location.reload(true) }, 750);
    	},
    	error: function(data){
    		alert('reset request failed with status: '+data.status+' please check overlord logs');
    	}
     });
  }
}

var deleteTopic = function(topic) {
  if(confirm('Do you really want to delete topic: '+topic)) {
    $.ajax({
      	type:"POST",
      	url: '/druid/indexer/v1/supervisor/topic/'+ topic +'/delete',
      	data:'',
  //    	datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
      	success:function(data){
      	    alert("delete topic successfully, topic:" + data);
      	},
      	complete: function(request, textStatus){
      	   setTimeout(function() { location.reload(true) }, 1000);
      	},
      	error: function(data){
      		alert('Kill request failed with status: '+data.status+' please check overlord logs');
      	}
     });
  }
}

var getSupervisorSpec = function(supervisorId, version){
  $.get('/druid/indexer/v1/supervisor/' + supervisorId + '/history', function(data) {
    $.each(data, function(idx, obj){
      if(obj.spec.type != "NoopSupervisorSpec") {
        if(obj.version == version) {
          var formated = JSON.stringify(obj.spec, null, 4);
          console.log(formated);
          $('#supervisor').val(formated);
        }
      }
    });
  });
}

$(document).ready(function() {
  $.get('/druid/indexer/v1/supervisor', function(data) {
    $('.running_loading').hide();
    var items = [];
    for (i = 0 ; i < data.length ; i++) {
      var id = data[i];
      var item = {};
      items.push(item);
      item.id = id;
      item.more =
        '<a target="_blank" href="/druid/indexer/v1/supervisor/' + id + '">Spec Info</a>' +
        '<a target="_blank" href="/druid/indexer/v1/supervisor/' + id + '/status">status</a>' +
        '<a target="_blank" onclick="resetSupervisor(\''+ id +'\');">reset</a>' +
        '<a target="_blank" onclick="killSupervisor(\''+ id +'\');">kill</a>';
    }
    buildTable(items, $('#runningTable'));
  });

  $.get('/druid/indexer/v1/supervisor/history', function(data) {
    $('.complete_loading').hide();
    var items = [];
    $.each(data, function(supervisorId, arr){
      $.each(arr, function(idx, obj){
        if(obj.spec.type != "NoopSupervisorSpec") {
          var item = {};
          items.push(item);
          item.id= supervisorId;
          item.version = obj.version;
          spec = obj.spec;
          item.type = spec.type;
          if(spec.tuningConfig.type == 'kafka') {
            item.topic = spec.ioConfig.topic;
            item.taskDuration = spec.ioConfig.taskDuration;
          } else {
            item.topic = '';
            item.taskDuration = '';
          }
          item.more =
            '<a target="_blank" href="/druid/indexer/v1/supervisor/' + supervisorId + '/status">status</a>'
            + '<a target="_blank" onclick="getSupervisorSpec(\''+ supervisorId +'\', \'' + item.version + '\');">reuse</a>';
        }
      });
    });
    buildTable(items, $('#completeTable'));
  });

  $('#formatSupervisor').click(function(){
    var spec = JSON.parse($('#supervisor').val());
    var formated = JSON.stringify(spec, null, 4);
    $('#supervisor').val(formated);
  });
//  curl -X POST -H 'Content-Type: application/json' -d @supervisor-spec.json http://192.168.0.212:8090/druid/indexer/v1/supervisor
  $('#createSupervisor').click(function(){
    var specStr = $('#supervisor').val();
    var spec = JSON.parse($('#supervisor').val());
    var formated = JSON.stringify(spec, null, 4);
    console.log(formated);
    $('#supervisor').val(formated);
    var created = true;
    if(confirm('Do you really want to create supervisor?')) {
        $.ajax({
        	type:"POST",
        	url: '/druid/indexer/v1/supervisor',
        	data: specStr,
        	contentType: "application/json;charset=utf-8",
        	datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        	success:function(data){
        	  console.log(data);
        	  alert("create successfully, id:" + data.id);
        	},
        	complete: function(request, textStatus){
        	  if(created) {
        	    setTimeout(function() { location.reload(true) }, 750);
        	  }
        	},
        	error: function(data){
        	  created = false;
        	  console.log("error data:");
        	  console.log(data);
              alert('request failed with status: '+data.status+' please check overlord logs');
        	}
         });
      }
  });

  $('#deleteTopicBtn').click(function(){
    var topicStr = $('#topics').val();
    var topicList = topicStr.split(',');
    $.each(topicList, function(index, topic){
        console.log('index:' + index + ', topic:' + topic.trim());
        deleteTopic(topic);
    });
  });

  var defaultSupervisorStr = '{"type":"lucene_supervisor","ioConfig":{"topic":"datasourceA","replicas":1,"taskCount":1,"taskDuration":"P1D","useEarliestOffset":"true","consumerProperties":{"bootstrap.servers":"192.168.0.213:9092,192.168.0.214:9092,192.168.0.216:9092"}},"dataSchema":{"parser":{"type":"string","parseSpec":{"format":"url","timestampSpec":{"column":"EventDateTime","format":"millis"},"dimensionsSpec":{"dimensions":[{"name":"IP","type":"string"},{"name":"EventDateTime","type":"string"},{"name":"EventLabel","type":"string"}],"spatialDimensions":[],"dimensionExclusions":[]}}},"dataSource":"datasourceA","metricsSpec":[],"granularitySpec":{"type":"uniform","queryGranularity":"NONE","segmentGranularity":"DAY"}},"tuningConfig":{"type":"kafka","buildV9Directly":true,"maxRowsInMemory":500000,"maxRowsPerSegment":20000000,"basePersistDirectory":"/data1/druid/storage/datasourceA","intermediatePersistPeriod":"PT10M"}}';
  $('#supervisor').val(defaultSupervisorStr);
  $('#formatSupervisor').click();
});