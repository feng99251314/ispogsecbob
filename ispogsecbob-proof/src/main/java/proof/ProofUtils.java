package proof;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proof.util.Constant;
import proof.util.Node;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * 凭证保存/验证工具
 */
public class ProofUtils {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static Logger logger = LoggerFactory.getLogger(ProofUtils.class);


    /**
     * 验证凭证
     */
    public String verifyProof(Node node ,String username ,String hashCode) throws Exception {

        String res = null;

        // Load an existing wallet holding identities used to access the network.
        Path walletDirectory = Paths.get("/home/mikey/DATA/MIKEY/IDEAWorkSpace/ispogsecbob/wallet");

        logger.info("加载钱包 ["+username+"]");

        Wallet wallet = Wallet.createFileSystemWallet(walletDirectory);

        boolean userExists = wallet.exists(username);

        if (!userExists) {
            System.out.println("An identity for the user ["+username+"] not exists in the wallet Now register");
            registerUser(node,username);
        }

        Path networkConfigFile = Paths.get(Constant.getConnectionFile(node));

        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, username).networkConfig(networkConfigFile);

        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork(Constant.CHAIN_NODE_NAME);

            Contract contract = network.getContract(Constant.CONTRACT_NAME);

            byte[] createCarResult = contract.submitTransaction("verify", hashCode);

            res = new String(createCarResult, StandardCharsets.UTF_8);

            logger.info(res);

        } catch (ContractException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(res);

        return res;
    }

    /**
     * 保存凭证
     */
    public String saveProof(Node node, String username , String time, String filePath, String hashCode, String owner) throws Exception {

        String res = null;

        logger.info("加载钱包");

        // Load an existing wallet holding identities used to access the network.
//        Path walletDirectory = Paths.get("wallet");
        Path walletDirectory = Paths.get("/home/mikey/DATA/MIKEY/IDEAWorkSpace/ispogsecbob/wallet");

        Wallet wallet = Wallet.createFileSystemWallet(walletDirectory);

        Path networkConfigFile = Paths.get(Constant.getConnectionFile(node));

        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, username).networkConfig(networkConfigFile);

        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork(Constant.CHAIN_NODE_NAME);

            Contract contract = network.getContract(Constant.CONTRACT_NAME);

            byte[] createCarResult = contract.submitTransaction("deposit", time, filePath, hashCode, owner,"false");

            res = new String(createCarResult, StandardCharsets.UTF_8);

            logger.info(res);

        } catch (ContractException | TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(res);

        return res;
    }

    /**
     * 注册用户
     */
    public void registerUser(Node node,String username) throws Exception {


        // Create a CA client for interacting with the CA.

        HFCAClient caClient = HFCAClient.createNewInstance(Constant.getCAUrl(node), Constant.getCAProperties(node));

        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();

        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

        // Check to see if we've already enrolled the user.
        boolean userExists = wallet.exists(username);

        if (userExists) {
            System.out.println("An identity for the user ["+username+"] already exists in the wallet");
            return;
        }

        userExists = wallet.exists("admin"+node);

        if (!userExists) {

            System.out.println("\"admin\" needs to be enrolled and added to the wallet first");

//            enroolAdmin(node);

        }

        Wallet.Identity adminIdentity = wallet.get("admin"+node);

        User admin = new User() {

            @Override
            public String getName() {
                return "admin"+node;
            }

            @Override
            public Set<String> getRoles() {
                return null;
            }

            @Override
            public String getAccount() {
                return null;
            }

            @Override
            public String getAffiliation() {
                return Constant.getAffiliation(node);
            }

            @Override
            public Enrollment getEnrollment() {
                return new Enrollment() {

                    @Override
                    public PrivateKey getKey() {
                        return adminIdentity.getPrivateKey();
                    }

                    @Override
                    public String getCert() {
                        return adminIdentity.getCertificate();
                    }
                };
            }

            @Override
            public String getMspId() {
                return Constant.getMspId(node);
            }

        };

        // Register the user, enroll the user, and import the new identity into the wallet.
        RegistrationRequest registrationRequest = new RegistrationRequest(username);
        registrationRequest.setAffiliation(Constant.getAffiliation(node));
        registrationRequest.setEnrollmentID(username);
        String enrollmentSecret = caClient.register(registrationRequest, admin);
        Enrollment enrollment = caClient.enroll(username, enrollmentSecret);
        Wallet.Identity user = Wallet.Identity.createIdentity(Constant.getMspId(node), enrollment.getCert(), enrollment.getKey());
        wallet.put(username, user);
        System.out.println("Successfully enrolled user ["+username+"] and imported it into the wallet");

    }


    /**
     * 注册管理员
     * @throws Exception
     */
    public void enroolAdmin(Node node) throws Exception {
        // Create a CA client for interacting with the CA.

        HFCAClient caClient = HFCAClient.createNewInstance(Constant.getCAUrl(node), Constant.getCAProperties(node));

        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

        // Check to see if we've already enrolled the admin user.
        boolean adminExists = wallet.exists("admin"+node);
        if (adminExists) {
            System.out.println("An identity for the admin user [ admin"+node+" ] already exists in the wallet");
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
        System.out.println(enrollment.getKey());
        System.out.println(enrollment.getCert());
        Wallet.Identity user = Wallet.Identity.createIdentity(Constant.getMspId(node), enrollment.getCert(), enrollment.getKey());
        wallet.put("admin"+node, user);
        System.out.println("Successfully enrolled user [ admin"+node+" ] and imported it into the wallet");
    }



    public static void main(String[] args) throws Exception{

        ProofUtils proofUtils = new ProofUtils();

//      //ca1
//
//        proofUtils.enroolAdmin(Node.CA1);
//        proofUtils.registerUser(Node.CA1,"222");
//        proofUtils.saveProof(Node.CA1,"user5","user1","user1","user1","user1");
//        proofUtils.verifyProof(Node.CA1,"user5","user1");
//
//      //ca2
//
//        proofUtils.enroolAdmin(proof.util.Node.CA2);
//        proofUtils.registerUser(Node.CA2,"user2");
//        proofUtils.saveProof(Node.CA2,"user2","user2","user2","0dacee6571e7b8e3dd0cf3f8940ab484a9807ade5a7655ea049d33c15bb8d5fb","user2");
        proofUtils.verifyProof(Node.CA2,"user2","0dacee6571e7b8e3dd0cf3f8940ab484a9807ade5a7655ea049d33c15bb8d5fb");

//      //ca3

//        proofUtils.enroolAdmin(proof.util.Node.CA3);
//        proofUtils.registerUser(proof.util.Node.CA3,"user3");
//        proofUtils.saveProof(Node.CA3,"user3","user3","user3","user3","user3");
//        proofUtils.verifyProof(Node.CA3,"user3","user3");

//      //ca4

//        proofUtils.enroolAdmin(proof.util.Node.CA4);
//        proofUtils.registerUser(proof.util.Node.CA4,"user4");
//        proofUtils.saveProof(Node.CA4,"user4","user4","user4","user4","user4");
//        proofUtils.verifyProof(Node.CA4,"user4","user4");
    }
}
