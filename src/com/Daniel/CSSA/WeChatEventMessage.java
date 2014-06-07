package com.Daniel.CSSA;

public class WeChatEventMessage {
	private String toUserName;
	private String fromUserName;
	private String Time;
	private String Msg;//text
	private String event;
	private String Key;
	
	
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
		return Time;
	}
	public void setCreateTime(String createTime) {
		this.Time = createTime;
	}
	public String getMessageType() {
		return Msg;
	}
	public void setMessageType(String messageType) {
		this.Msg = messageType;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String content) {
		this.event = content;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String content) {
		this.Key = content;
	}
	
	
	@Override
	public String toString() {
		return "WeChatTextMessage [toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ Time + ", messageType=" + Msg + ", event="
				+ event + ", msgId=" + Key + "]";
	}

}
