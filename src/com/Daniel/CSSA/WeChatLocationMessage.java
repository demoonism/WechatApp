package com.Daniel.CSSA;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class WeChatLocationMessage {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String locationx;
	private String localtiony;
	private String scale;
	private String label;
	private String msgId;
	
	public static WeChatLocationMessage getWeChatLocationMessage(String xml){
		XStream xstream = new XStream(new DomDriver());
		WeChatLocationMessage  message = null;
		xstream.alias("xml", WeChatLocationMessage.class);
		xstream.aliasField("ToUserName", WeChatLocationMessage.class, "toUserName");
		xstream.aliasField("FromUserName", WeChatLocationMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", WeChatLocationMessage.class, "createTime");
		xstream.aliasField("MsgType", WeChatLocationMessage.class, "msgType");
		xstream.aliasField("Location_X", WeChatLocationMessage.class, "locationx");
		xstream.aliasField("Location_Y", WeChatLocationMessage.class, "localtiony");
		xstream.aliasField("Scale", WeChatLocationMessage.class, "scale");
		xstream.aliasField("Label", WeChatLocationMessage.class, "label");
		xstream.aliasField("MsgId", WeChatLocationMessage.class, "msgId");
		message = (WeChatLocationMessage)xstream.fromXML(xml);
		return message;
	}
	
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
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getLocationx() {
		return locationx;
	}
	public void setLocationx(String locationx) {
		this.locationx = locationx;
	}
	public String getLocaltiony() {
		return localtiony;
	}
	public void setLocaltiony(String localtiony) {
		this.localtiony = localtiony;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	@Override
	public String toString() {
		return "WeChatLocationMessage [toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + ", locationx="
				+ locationx + ", localtiony=" + localtiony + ", scale=" + scale
				+ ", label=" + label + ", msgId=" + msgId + "]";
	}
	
}
