var ROOT_URL = "/DouChatServer";
var LOGIN_PATH = "/login";
var CHAT_PATH = "/chat";
var LOGIN_URL = ROOT_URL + LOGIN_PATH;
var CHAT_URL = ROOT_URL + CHAT_PATH;

var myName;
var accessKey;
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
			accessKey = data["accessKey"];
			myName = username;
			hideLoginFrame();
		}
	},dataType);
}

function getMessage() {
	$.get(GET_MESSAGE_URL, {
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
				addMessageToList(".messagePane", msg["username"], msg["imageUrl"]);
			});
		}
	},dataType);
}

function sendMessage(message) {
	$.post(SEND_MESSAGE_URL, {
		"message":message
	}, function(data, status) {
		if(data["status"] != "ok") {
			alert(data["message"]);
		} else {
			addMessageToList(".myMessagePane", myName, data["imageUrl"])
		}
	},dataType);
}

function checkLogin() {
	if(typeof(accessKey) == "undefined") {
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
	if(message == "") {
		return;
	}
	sendMessage(message);
}

function addMessageToList(pane, name, imageUrl) {
	$(pane + ".demoPane").clone()
		.appendTo($(".douMsgList"))
		.removeClass("demoPane")
		.find(".headIcon").html(name[0])
		.siblings(".messageBody")
		.find(".namePane").html(name)
		.siblings(".messageContentPane")
		.children("img")
		.attr("src",imageUrl);
	$(".douMsgList").scrollTop($(".douMsgList")[0].scrollHeight);
}

function startMessageThread() {
	var tid = setInterval(function() {
		var feedback = getMessage();
	},2000);
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