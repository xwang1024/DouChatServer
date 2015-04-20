var ROOT_URL = "/DouChatServer";
var LOGIN_PATH = "/login";
var CHAT_PATH = "/chat";
var IMAGE_PATH = "/cache";
var LOGIN_URL = ROOT_URL + LOGIN_PATH;
var CHAT_URL = ROOT_URL + CHAT_PATH;
var IMAGE_URL = ROOT_URL + IMAGE_PATH;

var myName;
var lastGetStamp = 0;

var dataType = "json";

function login(username) {
	$("#loginFeedback").css("display","none");
	$.post(LOGIN_URL, {
		"username":username
	}, function(data, status) {
		if(data["status"] != "ok") {
			$("#loginFeedback").html(data["message"]).css("display","block");
		} else {
			myName = username;
			hideLoginFrame();
		}
	},dataType);
}

function getMessage() {
	$.get(CHAT_URL, {
		"timestamp": lastGetStamp
	}, function(data, status) {
		if(status != "success") {
			alert("Feedback: " + status);
			return;
		}
		if(data["status"] != "ok") {
			alert(data["message"]);
		} else {
			$.each(data["messageList"], function(idx,msg) {
				addMessageToList(".messagePane", msg["username"], msg["imageId"]);
			});
			lastGetStamp = data["timestamp"];
		}
	},dataType);
}

function sendMessage(message) {
	$.post(CHAT_URL, {
		"message":message
	}, function(data, status) {
		if(data["status"] != "ok") {
			alert(data["message"]);
		} else {
			addMessageToList(".myMessagePane", myName, data["imageId"])
		}
	},dataType);
}

function checkLogin() {
	if(typeof(myName) == "undefined") {
		return false;
	}
	return true;
}

function showLoginFrame() {
	$(".mask").css({"display":"block"});
}

function hideLoginFrame() {
	$(".mask").css({"display":"none"});
}

function loginAction() {
	var username = $("input[name='usernameTf']").val();
	if(username == "") {
		$("#loginFeedback").html("Nick name cannot be empty.").css("display","block");
		return;
	}
	login(username);
}

function sendMessageAction() {
	if(!checkLogin()) {
		showLoginFrame();
		return;
	}
	var message = $("input[name='sendMessageTf']").val();
	$("input[name='sendMessageTf']").val("");
	if(message == "") {
		return;
	}
	sendMessage(message);
}

function generateImageQueryUrl(imageId) {
	return IMAGE_URL + "?id=" + imageId;
}

function addMessageToList(pane, name, imageId) {
//	alert(generateImageQueryUrl(imageId));
	$img = $(pane + ".demoPane").clone()
		.appendTo($(".douMsgList"))
		.removeClass("demoPane")
		.find(".headIcon").html(name[0])
		.siblings(".messageBody")
		.find(".namePane").html(name)
		.siblings(".messageContentPane")
		.find("img")
		.attr("src",generateImageQueryUrl(imageId));
	$(".douMsgList").scrollTop($(".douMsgList")[0].scrollHeight);
}

function startMessageThread() {
	var tid = setInterval(function() {
		var feedback = getMessage();
	},1000);
	return tid;
}


$(document).ready(function() {
	 $("#loginBtn").bind("click",loginAction);
	 $("input[name='usernameTf']").bind("keypress", function(keyEvent) {
	 	if(keyEvent.keyCode == 13) {
	 		loginAction();
	 	}
	 });
	 $("#loginReturnBtn").bind("click",hideLoginFrame);
	 $("#sendMessageBtn").bind("click",sendMessageAction);
	 $("input[name='sendMessageTf']").bind("keypress", function(keyEvent) {
	 	if(keyEvent.keyCode == 13) {
	 		sendMessageAction();
	 	}
	 });
	var tid = startMessageThread();
});