package com.Daniel.CSSA;

public class WeChatReplyTextMessage {

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private String content;
	private String funcFlag;
	
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFuncFlag() {
		return funcFlag;
	}
	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}
	@Override
	public String toString() {
		return "WeChatReplyTextMessage [toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", messageType=" + messageType + ", content="
				+ content + ", funcFlag=" + funcFlag + "]";
	}
	
}
