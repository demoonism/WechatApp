package com.Daniel.CSSA;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaiduGeoCodeResponse {
	private String lat;
	private String lng;

	public static BaiduGeoCodeResponse getBaiduGeoCode(String xml) throws Exception{
		BaiduGeoCodeResponse geo = new BaiduGeoCodeResponse();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document  doc = builder.parse(IOUtils.toInputStream(xml,"utf-8"));
		Element root=doc.getDocumentElement();
		for(Node node = root.getFirstChild();node!= null;node = node.getNextSibling()){
			if(node.getNodeName().equalsIgnoreCase("result")){
				NodeList resNodes = node.getChildNodes();
				for(int i = 0; i< resNodes.getLength();i ++){
					Node locNode = resNodes.item(i);
					if(locNode.getNodeName().equalsIgnoreCase("location")){
						NodeList lastNodes = locNode.getChildNodes();
						for(int j = 0;j< lastNodes.getLength();j++){
							Node latlng = lastNodes.item(j);
							if(latlng.getNodeName().equalsIgnoreCase("lat")){
								geo.setLat(latlng.getTextContent());
							}
							else if(latlng.getNodeName().equalsIgnoreCase("lng")){
								geo.setLng(latlng.getTextContent());
							}
						}
					}
				}
			}
		}
		
		return geo;
	}
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "BaiduGeoCodeResponse [lat=" + lat + ", lng=" + lng + "]";
	}
	
}
