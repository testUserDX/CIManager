package com.utilities;


import com.sforce.soap.metadata.*;
import com.sforce.ws.ConnectionException;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MetadataUtils {

    public enum MetadataType {
        Dashboard,
        Document,
        EmailTemplate,
        Report;
    }

    public enum MetadataTypeFolder {
        DashboardFolder,
        DocumentFolder,
        EmailFolder,
        ReportFolder;
    }

    public static final String ELEMENT_PACKAGE = "Package";
    public static final String ELEMENT_XMLNS = "xmlns";
    public static final String ELEMENT_TYPES = "types";
    public static final String ELEMENT_MEMBERS = "members";
    public static final String ELEMENT_NAME = "name";
    public static final String ELEMENT_VERSION = "version";
    public static final String PROPERTY_INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";
    public static final String VALUE_XMLNS = "http://soap.sforce.com/2006/04/metadata";
    public static final String VALUE_YES = "Yes";
    public static final String VALUE_XML = "xml";
    public static final String VALUE_INDENT_AMOUNT = "4";
    public static final String MANIFEST_FILE_TEMP = "package_temp.xml";
    public static final String MANIFEST_FILE = "package.xml";

    private String path;

    public MetadataUtils(String path){
        System.out.println(path);
        this.path = path;
        System.out.println(this.path);
    }

    public  void listMetadata(final MetadataConnection metadataConnection, final Double apiVersion)
            throws ConnectionException, IOException, ParserConfigurationException, TransformerConfigurationException
            , TransformerException {


        final List<String> metadataTypeList = getMetadataTypeList(metadataConnection, apiVersion);
        final Map<String, String> metadataTypeFolderMap = new HashMap<>();
        metadataTypeFolderMap.put(MetadataType.Dashboard.toString(),     MetadataTypeFolder.DashboardFolder.toString());
        metadataTypeFolderMap.put(MetadataType.Document.toString(),      MetadataTypeFolder.DocumentFolder.toString());
        metadataTypeFolderMap.put(MetadataType.EmailTemplate.toString(), MetadataTypeFolder.EmailFolder.toString());
        metadataTypeFolderMap.put(MetadataType.Report.toString(),        MetadataTypeFolder.ReportFolder.toString());

        final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        final Document doc = docBuilder.newDocument();
        doc.setXmlStandalone(true);

        final Element rootElement = doc.createElement(ELEMENT_PACKAGE);
        doc.appendChild(rootElement);

        final Attr attr = doc.createAttribute(ELEMENT_XMLNS);
        attr.setValue(VALUE_XMLNS);
        rootElement.setAttributeNode(attr);

        for (String mt : metadataTypeList) {
            String metadataTypeLv1;
            String metadataTypeLv2;
            final Boolean hasKey = metadataTypeFolderMap.containsKey(mt.toString());

            if (hasKey) {
                metadataTypeLv1 = metadataTypeFolderMap.get(mt.toString());
            } else {
                metadataTypeLv1 = mt.toString();
            }
            metadataTypeLv2 = mt.toString();

            final ListMetadataQuery queryLv1 = new ListMetadataQuery();
            queryLv1.setType(metadataTypeLv1);

            final List<String> folderList = new ArrayList<>();
            final ListMetadataQuery[] queriesLv1 = new ListMetadataQuery[]{queryLv1};
            final FileProperties[] fileProperties = metadataConnection.listMetadata(queriesLv1, apiVersion);

            if (fileProperties.length > 0) {
                final Element types = doc.createElement(ELEMENT_TYPES);
                final List<String> fullNameList = new ArrayList<>();

                for (FileProperties fp : fileProperties) {
                    fullNameList.add(fp.getFullName());
                    if (hasKey) {
                        folderList.add(fp.getFullName());
                    }
                }

                for (String folder : folderList) {
                    final ListMetadataQuery queryLv2 = new ListMetadataQuery();
                    queryLv2.setType(metadataTypeLv2);
                    queryLv2.setFolder(folder);


                    final ListMetadataQuery[] queriesLv2 = new ListMetadataQuery[]{queryLv2};
                    final FileProperties[] fileProperties2 = metadataConnection.listMetadata(queriesLv2, apiVersion);

                    if (fileProperties2 != null) {
                        for (FileProperties fp2 : fileProperties2) {
                            fullNameList.add(fp2.getFullName());
                        }
                    }
                }

                final Set<String> fullNameSet = new HashSet<>(fullNameList);
                final List<String> fullNameListNoDuplication = new ArrayList<>(fullNameSet);
                Collections.sort(fullNameListNoDuplication);

                for (String fullName : fullNameListNoDuplication) {
                    final Element members = doc.createElement(ELEMENT_MEMBERS);
                    members.appendChild(doc.createTextNode(fullName));
                    types.appendChild(members);
                }

                final Element name = doc.createElement(ELEMENT_NAME);
                name.appendChild(doc.createTextNode(metadataTypeLv2));
                types.appendChild(name);
                rootElement.appendChild(types);
            }
        }

        final Element version = doc.createElement(ELEMENT_VERSION);
        version.appendChild(doc.createTextNode(String.valueOf(apiVersion)));
        rootElement.appendChild(version);

        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, VALUE_YES);
        transformer.setOutputProperty(OutputKeys.METHOD, VALUE_XML);
        transformer.setOutputProperty(PROPERTY_INDENT_AMOUNT, VALUE_INDENT_AMOUNT);

        final DOMSource source = new DOMSource(doc);
        final File tempManifestFile = new File(path+"zip\\"+MANIFEST_FILE_TEMP);
        final StreamResult result = new StreamResult(tempManifestFile);
        transformer.transform(source, result);

        final File newManifestFile = new File(path+"zip\\"+MANIFEST_FILE);
        final FileWriter filewriter = new FileWriter(newManifestFile);
        final String allLine = CommonUtils.readAllLine(path+"zip\\"+MANIFEST_FILE_TEMP);
        filewriter.write(allLine.replace("><", ">\n<"));
        filewriter.close();
        tempManifestFile.delete();

        System.out.println("successfully generated.");
    }

    private List<String> getMetadataTypeList(final MetadataConnection metadataConnection, final Double apiVersion)
            throws ConnectionException {
        final List<String> dmoStringList = new ArrayList<>();
        final DescribeMetadataObject[] dmo = metadataConnection.describeMetadata(apiVersion).getMetadataObjects();

        for (DescribeMetadataObject obj : dmo) {
            dmoStringList.add(obj.getXmlName());
        }
        Collections.sort(dmoStringList);

        return dmoStringList;
    }

}