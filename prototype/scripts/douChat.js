var ROOT_URL = "http://localhost:8080/DouChatServer";
var LOGIN_PATH = "/login";
var GET_MESSAGE_PATH = "/getMessage";
var SEND_MESSAGE_PATH = "/sendMessage";

var myName;
var accessKey;

var dataType = "html";

function login(username) {
	$.post(LOGIN_URL, {
		"username":username
	}, function(data, status) {
		alert(status);
		return data;
	},dataType);
}

function getMessage() {
	$.post(GET_MESSAGE_URL, {
		"accessKey":accessKey
	}, function(data, status) {
		alert(status);
		return data;
	},dataType);
}

function sendMessage(message) {
	$.post(SEND_MESSAGE_URL, {
		"accessKey":accessKey,
		"message":message
	}, function(data, status) {
		alert(status);
		return data;
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

function loginAction() {
	var username = $("input[name='usernameTf']").val();
	if(username == "") {
		$("#loginFeedback").html("Username cannot be empty.").css({"display","block"});
		return;
	}
	var feedbackJson = login(username);
	if(feedbackJson["status"] != "ok") {
		$("#loginFeedback").html(feedbackJson["message"]).css({"display","block"});
	} else {
		accessKey = feedbackJson["accessKey"];
		myName = username;
		$("#loginFeedback").css({"display","none"});
	}
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

	var feedbackJson = sendMessage(message);

	if(feedbackJson["status"] != "ok") {
		alert(feedbackJson["message"]);
	} else {
		addMessageToList(".myMessagePane", myName, feedbackJson["imageUrl"])
	}
}

function addMessageToList(pane, name, imageUrl) {
	$(pane + ".demoPane").clone()
		.appendTo($(".douMsgList"))
		.removeClass("demoPane")
		.children("namePane").html(name)
		.siblings(".messageContentPane")
		.children("img")
		.attr("src",imageUrl);
}

function startMessageThread() {
	var tid = setInterval(function() {
		var feedback = getMessage();
		if(feedback != "ok") {
			alert(feedback["message"]);
		} else {
			for(msg in feedback["messageList"]) {
				addMessageToList(".messagePane", msg["username"], msg["imageUrl"]);
			}
		}
	},1000);
	return tid;
}


$(document).ready(function() {
	alert(accessKey);
	$("#loginBtn").bind("click",loginAction);
	$("input[name='usernameTf']").bind("keypress", function(keyEvent) {
		if(keyEvent.keyCode == 13) {
			loginAction();
		}
	});
	$("#sendMessageBtn").bind("click",sendMessageAction);
	$("input[name='sendMessageTf']").bind("keypress", function(keyEvent) {
		if(keyEvent.keyCode == 13) {
			sendMessageAction();
		}
	});
	var tid = startMessageThread();
});