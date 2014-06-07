package com.Daniel.CSSA;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BaiduPlaceResponse {

	private String name;
	private String telephone;
	private String address;
	private String lat;
	private String lng;
	private String tag;
	private String detailUrl;

	public static List<BaiduPlaceResponse> getBaiduPlace(String xml) throws Exception{
		List<BaiduPlaceResponse> resList = new ArrayList<BaiduPlaceResponse>();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document  doc = builder.parse(IOUtils.toInputStream(xml,"utf-8"));
		Element root=doc.getDocumentElement();
		for(Node node = root.getFirstChild();node!= null;node = node.getNextSibling()){
			if(node.getNodeName().equalsIgnoreCase("result")){
				BaiduPlaceResponse res = new BaiduPlaceResponse();
				NodeList subList = node.getChildNodes();
				for(int j = 0;j<subList.getLength();j++){
					Node mNode = subList.item(j);
					if(mNode.getNodeName().equalsIgnoreCase("name")){
						res.setName(mNode.getTextContent());
					}
					else if(mNode.getNodeName().equalsIgnoreCase("formatted_address")){
						res.setAddress(mNode.getTextContent());
					}
					else if (mNode.getNodeName().equalsIgnoreCase("geometry")){
						NodeList gNodes = mNode.getChildNodes();
						for(int k=0;k<gNodes.getLength();k++){
							Node gnode = gNodes.item(k);
							if(gnode.getNodeName().equalsIgnoreCase("location")){
								NodeList lNodes = gnode.getChildNodes();

								for(int p=0;p<lNodes.getLength();p++){
									Node lnode = lNodes.item(p);
									if(lnode.getNodeName().equalsIgnoreCase("lat")){
										res.setLat(lnode.getTextContent());
									}
									else if(lnode.getNodeName().equalsIgnoreCase("lng")){
										res.setLng(lnode.getTextContent());
									}
								}
								
							}
						}
						
					}
				}
				resList.add(res);
				
			}
			
			
			
			
			
//			if(node.getNodeName().equalsIgnoreCase("results")){
//				NodeList nodeList =node.getChildNodes();//results-result
//				for(int i = 0;i<nodeList.getLength();i++){
//					Node rNode = nodeList.item(i);
//					if(rNode.getNodeName().equalsIgnoreCase("result")){
//						BaiduPlaceResponse res = new BaiduPlaceResponse();
//						NodeList subList = rNode.getChildNodes();
//						
//						for(int j = 0;j<subList.getLength();j++){
//							Node mNode = subList.item(j);
//							if(mNode.getNodeName().equalsIgnoreCase("name")){
//								res.setName(mNode.getTextContent());
//							}
//							else if(mNode.getNodeName().equalsIgnoreCase("address")){
//								res.setAddress(mNode.getTextContent());
//							}
//							else if(mNode.getNodeName().equalsIgnoreCase("telephone")){
//								res.setTelephone(mNode.getTextContent());
//							}
//							else if(mNode.getNodeName().equalsIgnoreCase("detail_url")){
//								res.setDetailUrl(mNode.getTextContent());
//							}
//							else if (mNode.getNodeName().equalsIgnoreCase("location")){
//								NodeList lNodes = mNode.getChildNodes();
//								for(int k=0;k<lNodes.getLength();k++){
//									Node lnode = lNodes.item(k);
//									if(lnode.getNodeName().equalsIgnoreCase("lat")){
//										res.setLat(lnode.getTextContent());
//									}
//									else if(lnode.getNodeName().equalsIgnoreCase("lng")){
//										res.setLng(lnode.getTextContent());
//									}
//								}
//								
//							}
//						}
//						resList.add(res);
//					}
//				}
//			}
		}
		return resList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
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
		return "BaiduPlaceResponse [name=" + name + ", telephone=" + telephone
				+ ", address=" + address + ", lat=" + lat + ", lng=" + lng
				+ ", tag=" + tag + ", detailUrl=" + detailUrl + "]";
	}
}
