<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		var para = window.location.search;
		if(para != null && para != ""){
			para = para.substring(0);
			
			var arr = para.split("&");
			var node =new Array();
			
			//判断参数个数
			if(arr.length === 3 ){	
				node.funcId = arr[0].substring(arr[0].indexOf("=")+1);
				node.funcUrl = arr[1].substring(arr[1].indexOf("=")+1);
				node.funcName = unescape(arr[2].substring(arr[2].indexOf("=")+1));
				
				parent.window.nameCOP.addTab(node.funcName, node.funcUrl);
			}
		}
	</script>
</body>
</html>