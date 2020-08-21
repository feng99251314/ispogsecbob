package proof.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proof.ProofUtils;
import proof.util.Node;
import proof.util.R;

import java.util.Map;

@RestController
@RequestMapping("fabric")
public class ProofController {


    /**
     * 注册用户
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("/register")
    public R register(@RequestParam Map<String, Object> params) throws Exception {

        Object node = params.get("node");

        Object username = params.get("username");

        ProofUtils proofUtils = new ProofUtils();

        proofUtils.registerUser(Node.CA1,"222");

        return R.ok();
    }

    /**
     * 验证凭证
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("/verify")
    public R verify(@RequestParam Map<String, Object> params) throws Exception {

        ProofUtils proofUtils = new ProofUtils();

        proofUtils.verifyProof(Node.CA1,"222","");

        return R.ok();
    }

    /**
     * 保存凭证
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public R save(@RequestParam Map<String, Object> params) throws Exception {

        ProofUtils proofUtils = new ProofUtils();

        proofUtils.saveProof(Node.CA1,
                "222",
                "222",
                "222",
                "222",
                "222"
                );

        return R.ok();
    }



}


