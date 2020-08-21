package proof.util;

import java.util.Properties;

public class Constant {

    /**
     * 通道名称
     */
    public static final String CHAIN_NODE_NAME = "mychannel";
    /**
     * 链码名称
     */
    public static final String CONTRACT_NAME = "mycc";

    /**
     * 获取证书发布机构链接URL
     * @param node
     * @return
     */
    public static String getCAUrl(Node node) {

        switch (node){
            case CA1:
                return "https://localhost:7054";
            case CA2:
                return "https://localhost:8054";
            case CA3:
                return "https://localhost:9054";
            case CA4:
                return "https://localhost:10054";
            default:
                return "Not Exist This CA URL";
        }
    }

    /**
     * 获取证书发布机构加密证书
     * @param node
     * @return
     */
    public static Properties getCAProperties(Node node) {

        Properties props = new Properties();

        switch (node){
            case CA1:
                props.put("pemFile", "ispogsecbob-fabric/network/crypto-config/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
                break;
            case CA2:
                props.put("pemFile", "ispogsecbob-fabric/network/crypto-config/peerOrganizations/org2.example.com/ca/ca.org2.example.com-cert.pem");
                break;
            case CA3:
                props.put("pemFile", "ispogsecbob-fabric/network/crypto-config/peerOrganizations/org3.example.com/ca/ca.org3.example.com-cert.pem");
                break;
            case CA4:
                props.put("pemFile", "ispogsecbob-fabric/network/crypto-config/peerOrganizations/org4.example.com/ca/ca.org4.example.com-cert.pem");
                break;
        }
        props.put("allowAllHostNames", "true");

        return props;
    }


    public static String getConnectionFile(Node node) {

        switch (node){
            case CA1:
                return "ispogsecbob-fabric/network/connection/connection-org1.yaml";
            case CA2:
                return "ispogsecbob-fabric/network/connection/connection-org2.yaml";
            case CA3:
                return "ispogsecbob-fabric/network/connection/connection-org3.yaml";
            case CA4:
                return "ispogsecbob-fabric/network/connection/connection-org4.yaml";
            default:
                return "Not Exist This Connection File";
        }

    }

    public static String getMspId(Node node) {


                switch (node){
                    case CA1:
                        return "Org1MSP";
                    case CA2:
                        return "Org2MSP";
                    case CA3:
                        return "Org3MSP";
                    case CA4:
                        return "Org4MSP";
                    default:
                        return "Not Find MspId";
                }
    }

    //


    public static String getAffiliation(Node node) {

       return "org1.department1";

    }


    public Node getNode(int i) {
        switch (i){
            case 0: return Node.CA1;
            case 1: return Node.CA2;
            case 2: return Node.CA3;
            case 3: return Node.CA4;
        }
        return Node.CA1;
    }
}
