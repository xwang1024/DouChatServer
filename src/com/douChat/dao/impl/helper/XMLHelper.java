package com.douChat.dao.impl.helper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHelper {
	private final DocumentBuilderFactory dbf = DocumentBuilderFactory
			.newInstance();
	private final XPathFactory xpf = XPathFactory.newInstance();

	private DocumentBuilder db;
	private Document doc;
	private XPath xpath;
	private Node node;
	private NodeList nodeList;

	public XMLHelper(String filePath) throws SAXException, IOException,
			ParserConfigurationException {
		db = dbf.newDocumentBuilder();
		doc = db.parse(filePath);
		xpath = xpf.newXPath();
	}

	public XMLHelper(File file) throws SAXException, IOException,
			ParserConfigurationException {
		db = dbf.newDocumentBuilder();
		doc = db.parse(file);
		xpath = xpf.newXPath();
	}

	public String getString(String xpathExpression)
			throws XPathExpressionException {
		node = (Node) xpath.evaluate(xpathExpression, doc, XPathConstants.NODE);
		return node.getTextContent().trim();
	}

	public int getInteger(String xpathExpression)
			throws XPathExpressionException {
		node = (Node) xpath.evaluate(xpathExpression, doc, XPathConstants.NODE);
		return Integer.parseInt(node.getTextContent());
	}

	public String[] getStringList(String xpathExpression)
			throws XPathExpressionException {
		nodeList = (NodeList) xpath.evaluate(xpathExpression, doc,
				XPathConstants.NODESET);
		List<String> l = new LinkedList<String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node pNode = nodeList.item(i);
			l.add(pNode.getTextContent().trim());
		}
		return l.toArray(new String[l.size()]);
	}

	public Integer[] getIntegerList(String xpathExpression)
			throws XPathExpressionException {
		nodeList = (NodeList) xpath.evaluate(xpathExpression, doc,
				XPathConstants.NODESET);
		List<Integer> l = new LinkedList<Integer>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node pNode = nodeList.item(i);
			l.add(Integer.parseInt(pNode.getTextContent()));
		}
		return l.toArray(new Integer[l.size()]);
	}
}
