var ROOT_URL = "/DouChatServer";
var LOGIN_PATH = "/login";
var CHAT_PATH = "/chat";
var IMAGE_PATH = "/cache";
var LOGIN_URL = ROOT_URL + LOGIN_PATH;
var CHAT_URL = ROOT_URL + CHAT_PATH;
var IMAGE_URL = ROOT_URL + IMAGE_PATH;

var ws;
var accessKey;

function login(username) {
	$("#loginFeedback").css("display", "none");
	var requestJson = {
		"action" : "login",
		"username" : username
	};
	ws.send(JSON.stringify(requestJson));
}

function sendMessage(message) {
	var requestJson = {
		"action" : "send",
		"accessKey" : accessKey,
		"message" : message
	};
	ws.send(JSON.stringify(requestJson));
}

function checkLogin() {
	if (typeof (accessKey) == "undefined") {
		return false;
	}
	return true;
}

function showLoginFrame() {
	$(".mask").css({
		"display" : "block"
	});
}

function hideLoginFrame() {
	$(".mask").css({
		"display" : "none"
	});
}

function loginAction() {
	var username = $("input[name='usernameTf']").val();
	if (username == "") {
		$("#loginFeedback").html("Nick name cannot be empty.").css("display",
				"block");
		return;
	}
	login(username);
}

function sendMessageAction() {
	if (!checkLogin()) {
		showLoginFrame();
		return;
	}
	var message = $("input[name='sendMessageTf']").val();
	$("input[name='sendMessageTf']").val("");
	if (message == "") {
		return;
	}
	sendMessage(message);
}

function generateImageQueryUrl(imageId) {
	return IMAGE_URL + "?id=" + imageId;
}

function addMessageToList(pane, name, imageId) {
	// alert(generateImageQueryUrl(imageId));
	$img = $(pane + ".demoPane").clone().appendTo($(".douMsgList"))
			.removeClass("demoPane").find(".headIcon").html(name[0]).siblings(
					".messageBody").find(".namePane").html(name).siblings(
					".messageContentPane").find("img").attr("src",
					generateImageQueryUrl(imageId));
	$(".douMsgList").scrollTop($(".douMsgList")[0].scrollHeight);
}

function initWebSocket() {
	ws = new WebSocket("ws://localhost:8080/DouChatServer/");
	ws.onopen = function() {
		alert("ws open");
	};
	ws.onmessage = function(event) {
		var feedback = JSON.parse(event.data);
		if (feedback.status != "ok") {
			alert("Error occured: " + feedback.message);
			return;
		}
		switch (feedback.action) {
		case "login":
			accessKey = feedback.accessKey;
			hideLoginFrame();
			break;
		case "send":
			addMessageToList(".myMessagePane", feedback.username, feedback.imageId);
			break;
		case "boardcast":
			addMessageToList(".messagePane", feedback.username, feedback.imageId);
			break;
		}
	};
	ws.onclose = function(event) {
		alert("ws close");
	};
	ws.onerror = function(event) {
		alert("ws error");
	};
}

$(document).ready(function() {
	initWebSocket();

	$("#loginBtn").bind("click", loginAction);
	$("input[name='usernameTf']").bind("keypress", function(keyEvent) {
		if (keyEvent.keyCode == 13) {
			loginAction();
		}
	});
	$("#loginReturnBtn").bind("click", hideLoginFrame);
	$("#sendMessageBtn").bind("click", sendMessageAction);
	$("input[name='sendMessageTf']").bind("keypress", function(keyEvent) {
		if (keyEvent.keyCode == 13) {
			sendMessageAction();
		}
	});
});