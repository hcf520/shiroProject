//分页插件主方法
layui.use('table', function() {
			var table = layui.table;

			table.render({ //其它参数在此省略
				elem : '#test',
				url : '/table/tableUserData',
				/* where : {
					start : 1,
					size : 10
				},  *///如果无需传递额外参数，可不加该参数
				method : 'GET', //如果无需自定义HTTP类型，可不加该参数
				toolbar: '#toolbarDemo',
				cols : [ [ {type: 'checkbox', fixed: 'left'},
				           {
					field : 'id',
					width : 80,
					title : 'ID',
					sort : true
				}, {
					field : 'username',
					width : 80,
					title : '用户名'
				}, {
					field : 'sex',
					width : 80,
					title : '性别',
					sort : true
				}, {
					field : 'city',
					width : 80,
					title : '城市'
				}, {
					field : 'sign',
					title : '签名',
					minWidth : 150
				}, {
					field : 'experience',
					width : 80,
					title : '积分',
					sort : true
				}, {
					field : 'score',
					width : 80,
					edit: 'text',
					title : '评分',
					sort : true
				}, {
					field : 'classify',
					width : 80,
					title : '职业'
				}, {
					field : 'wealth',
					width : 135,
					title : '财富',
					sort : true
				},{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
				] ],
				
				request: {
				    pageName: 'start' //页码的参数名称，默认：page
				    ,limitName: 'size' //每页数据量的参数名，默认：limit
				  },
				response: {
				      statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
				    },
				    
				text: {
				      none: '暂无相关数据😂😂😂😂😂😂😂😂😂😂😂' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
				      },
				page: true,
				parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
				      return {
				        "code": 200, //解析接口状态
				        "msg": "测试解析提示文本", //解析提示文本
				        "count": res.total, //解析数据长度
				        "data": res.list //解析数据列表
				      };
				    }
			});
		

//分割线————————————————————————————————————————————————————————————————————————————
//头工具栏事件
table.on('toolbar(test)', function(obj){
  var checkStatus = table.checkStatus(obj.config.id);
  switch(obj.event){
    case 'getCheckData':
      var data = checkStatus.data;
      layer.alert(JSON.stringify(data));
    break;
    case 'getCheckLength':
      var data = checkStatus.data;
      layer.msg('选中了：'+ data.length + ' 个');
    break;
    case 'isAll':
      layer.msg(checkStatus.isAll ? '全选': '未全选');
    break;
  };
});

//监听行工具事件
table.on('tool(test)', function(obj){
    var data = obj.data;
    console.log(data.id);//得到行主键值
    if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
    	  console.log("删除行主键ID："+obj.id);
    	  //修改更新积分数值
	        $.get("/table/delUserDataRow", { id: data.id},
	        		  function(data){
	        		    layer.msg("Data Loaded: " + data);
	        		  });
	       
        obj.del();
        layer.close(index);
        //重载页面
        table.reload('test', {
      	  page: {
      	    curr: 1 //重新从第 1 页开始,此处属性虽然说是curr,但是由于配置request，会自动变更
      	  }
      	});
      });
    
    } else if(obj.event === 'edit'){
    	  //配置一个透明的询问框
    	layer.open({
    		  content: '👉请选择编辑属性'
    		  ,btn: ['积分', '评分', '财富']
    		  ,btn1: function(index, layero){
    			  //弹窗型属性值编辑框
    			   layer.prompt({
    			        formType: 0		//输入框类型，支持0（文本）默认1（密码）2（多行文本）
    			        ,value: data.id    //输入框初始值
    			        ,title: '请编辑积分'
    			      }, function(value, index){
    			        obj.update({
    			        experience: value
    			        });
    			        //修改更新积分数值
    			        $.get("/table/updateUserDataRow", { id: data.id, experience:value },
    			        		  function(data){
    			        		    layer.msg("Data Loaded: " + data);
    			        		  });
    			        //重载页面
    			        table.reload('test', {
    			      	  page: {
    			      	    curr: 1 //重新从第 1 页开始,此处属性虽然说是curr,但是由于配置request，会自动变更
    			      	  }
    			      	});
    			        layer.close(index);
    			      });
    			   layer.close(index);
    		  },btn2: function(index, layero){
    			  layer.prompt({
  			        formType: 0		//输入框类型，支持0（文本）默认1（密码）2（多行文本）
  			        ,value: data.id    //输入框初始值
  			        ,title: '请编辑评分'
  			      }, function(value, index){
  			        obj.update({
  			        	score: value
  			        });
  			      $.get("/table/updateUserDataRow", { id: data.id, score:value },
		        		  function(data){
		        		    layer.msg("Data Loaded: " + data);
		        		  });
  			    table.reload('test', {
			      	  page: {
			      	    curr: 1 //重新从第 1 页开始,此处属性虽然说是curr,但是由于配置request，会自动变更
			      	  }
			      	});
  			        layer.close(index);
  			      });
    		  },btn3: function(index, layero){
    			  layer.prompt({
  			        formType: 0		//输入框类型，支持0（文本）默认1（密码）2（多行文本）
  			        ,value: data.id    //输入框初始值
  			        ,title: '请编辑财富'
  			      }, function(value, index){
  			        obj.update({
  			        	wealth: value
  			        });
  			      $.get("/table/updateUserDataRow", { id: data.id, wealth:value },
		        		  function(data){
		        		    layer.msg("Data Loaded: " + data);
		        		  });
  			    table.reload('test', {
			      	  page: {
			      	    curr: 1 //重新从第 1 页开始,此处属性虽然说是curr,但是由于配置request，会自动变更
			      	  }
			      	});
  			        layer.close(index);
  			      });
    		  }
    		  ,cancel: function(){ 
    		    //右上角关闭回调
    		  }
    		});
    	
    	
    	
    }
  });

});
//表控制独立函数————————————————————————————————————

  
 