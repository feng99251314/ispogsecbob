//package proof.controller;
//
//import org.hyperledger.fabric.gateway.*;
//import org.hyperledger.fabric.sdk.Enrollment;
//import org.hyperledger.fabric.sdk.User;
//import org.hyperledger.fabric.sdk.security.CryptoSuite;
//import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
//import org.hyperledger.fabric_ca.sdk.HFCAClient;
//import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import proof.ProofUtils;
//import proof.util.Constant;
//import proof.util.Node;
//import proof.util.R;
//
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.PrivateKey;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeoutException;
//
//@RestController
//@RequestMapping("fabric")
//public class ProofController {
//
//    private static Logger logger = LoggerFactory.getLogger(ProofUtils.class);
//
//    /**
//     * 注册用户
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/register")
//    public R register(@RequestParam Map<String, Object> params) throws Exception {
//
//        Node node = Node.valueOf("CA"+params.get("node").toString());
//
//        Object username = params.get("username");
//
//        HFCAClient caClient = HFCAClient.createNewInstance(Constant.getCAUrl(node), Constant.getCAProperties(node));
//
//        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
//
//        caClient.setCryptoSuite(cryptoSuite);
//
//        Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));
//
//        boolean userExists = wallet.exists(username.toString());
//
//        if (userExists) {
//            return R.error("An identity for the user ["+username+"] already exists in the wallet");
//        }
//
//        userExists = wallet.exists("admin"+node);
//
//        if (!userExists) {
//
//            System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
//
//        }
//
//        Wallet.Identity adminIdentity = wallet.get("admin"+node);
//
//        User admin = new User() {
//
//            @Override
//            public String getName() {
//                return "admin"+node;
//            }
//
//            @Override
//            public Set<String> getRoles() {
//                return null;
//            }
//
//            @Override
//            public String getAccount() {
//                return null;
//            }
//
//            @Override
//            public String getAffiliation() {
//                return Constant.getAffiliation(node);
//            }
//
//            @Override
//            public Enrollment getEnrollment() {
//                return new Enrollment() {
//
//                    @Override
//                    public PrivateKey getKey() {
//                        return adminIdentity.getPrivateKey();
//                    }
//
//                    @Override
//                    public String getCert() {
//                        return adminIdentity.getCertificate();
//                    }
//                };
//            }
//
//            @Override
//            public String getMspId() {
//                return Constant.getMspId(node);
//            }
//
//        };
//
//        RegistrationRequest registrationRequest = new RegistrationRequest(username.toString());
//        registrationRequest.setAffiliation(Constant.getAffiliation(node));
//        registrationRequest.setEnrollmentID(username.toString());
//        String enrollmentSecret = caClient.register(registrationRequest, admin);
//        Enrollment enrollment = caClient.enroll(username.toString(), enrollmentSecret);
//
//        System.out.println("cert = "+enrollment.getCert());
//        System.out.println("key = "+enrollment.getKey());
//
//        Wallet.Identity user = Wallet.Identity.createIdentity(Constant.getMspId(node), enrollment.getCert(), enrollment.getKey());
//        wallet.put(username.toString(), user);
//        System.out.println("Successfully enrolled user ["+username+"] and imported it into the wallet");
//
//        return R.ok();
//    }
//
//    /**
//     * 验证凭证
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/verify")
//    public R verify(@RequestParam Map<String, Object> params) throws Exception {
//
////        String username = params.get("username").toString();
//
////        String hashcode = params.get("hashcode").toString();
//
//        String res = null;
//
//        Path walletDirectory = Paths.get("wallet");
//
//        logger.info("加载钱包 [user1]");
//
//        Wallet wallet = Wallet.createFileSystemWallet(walletDirectory);
//
//        Path networkConfigFile = Paths.get("/home/mikey/DATA/MIKEY/IDEAWorkSpace/ispogsecbob/ispogsecbob-fabric/network/connection/connection-org1.yaml");
//
////        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, username).networkConfig(networkConfigFile);
//        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "user1").networkConfig(networkConfigFile);
//
//        try (Gateway gateway = builder.connect()) {
//
//            Network network = gateway.getNetwork(Constant.CHAIN_NODE_NAME);
//
//            Contract contract = network.getContract(Constant.CONTRACT_NAME);
//
//            byte[] createCarResult = contract.submitTransaction("verify", "user1");
//
//            res = new String(createCarResult, StandardCharsets.UTF_8);
//
//            logger.info(res);
//
//        } catch (ContractException | TimeoutException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(res);
//
//        return R.ok(res);
//    }
//
//    /**
//     * 保存凭证
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/save")
//    public R save(@RequestParam Map<String, Object> params) throws Exception {
//
//        Node node = Node.valueOf("CA"+params.get("node").toString());
//
//        String username = params.get("username").toString();
//
//        Path walletDirectory = Paths.get("wallet");
//
//        Wallet wallet = Wallet.createFileSystemWallet(walletDirectory);
//
//        Path networkConfigFile = Paths.get(Constant.getConnectionFile(node));
//
//        Gateway.Builder builder = Gateway.createBuilder().identity(wallet, username).networkConfig(networkConfigFile);
//
//        try (Gateway gateway = builder.connect()) {
//
//            Network network = gateway.getNetwork(Constant.CHAIN_NODE_NAME);
//
//            Contract contract = network.getContract(Constant.CONTRACT_NAME);
//
//            byte[] createCarResult = contract.submitTransaction("deposit",
//                    params.get("time").toString()
//                    , params.get("filePath").toString()
//                    , params.get("hashCode").toString()
//                    , params.get("owner").toString()
//                    ,"false");
//
//            System.out.println(new String(createCarResult, StandardCharsets.UTF_8));
//
//
//        } catch (ContractException | TimeoutException e) {
//            e.printStackTrace();
//        }
//        return R.ok();
//    }
//
//
//
//}
//
//
