package controllers

import (
	"encoding/json"
	"errors"
	"fmt"
	"github.com/astaxie/beego"
	"github.com/hyperledger/fabric-sdk-go/pkg/core/config"
	"github.com/hyperledger/fabric-sdk-go/pkg/gateway"
	"io/ioutil"
	"ispogsecbob-api/models"
	"path/filepath"
)

// FabricFileController operations for FabricFile
type FabricFileController struct {
	beego.Controller
}

// URLMapping ...
func (c *FabricFileController) URLMapping() {
	c.Mapping("Save", c.Save)
	c.Mapping("Verify", c.Verify)
}

// Save ...
// @Title Save
// @Description Save FabricFile to BlockChain
// @Param	body	body	models.FabricFile	true	"body for FabricFile content"
// @Success 201 {int} models.FabricFile
// @Failure 403 body is empty
// @router /save [post]
func (c *FabricFileController) Save() {
	var v models.FabricFile
	if err := json.Unmarshal(c.Ctx.Input.RequestBody, &v); err == nil {

		fmt.Println("接收数据", v)

		//获取钱包
		wallet, err := gateway.NewFileSystemWallet("wallet")
		if err != nil {
			fmt.Printf("Failed to create wallet: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}
		//检查用户
		if !wallet.Exists(v.UserName) {
			err = populateWallet(wallet, v)
			if err != nil {
				fmt.Printf("Failed to populate wallet contents: %s\n", err)
				//os.Exit(1)
				c.Data["json"] = err
				return
			}
		}
		//获取连接文件
		ccpPath := filepath.Join(
			"..",
			"ispogsecbob-fabric",
			"network",
			"connection",
			"connection-org"+v.AllowUser+".yaml",
		)

		gw, err := gateway.Connect(
			gateway.WithConfig(config.FromFile(filepath.Clean(ccpPath))),
			gateway.WithIdentity(wallet, v.UserName),
		)
		if err != nil {
			fmt.Printf("Failed to connect to gateway: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}
		defer gw.Close()

		network, err := gw.GetNetwork("mychannel")
		if err != nil {
			fmt.Printf("Failed to get network: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}

		contract := network.GetContract("mycc")
		result, err := contract.EvaluateTransaction("verify", "init")

		//	//结构体
		//	var proof = SimpleChaincode{
		//		Time:     args[0],
		//		FilePath: args[1],
		//		HashCode: args[2],
		//		Owner:    args[3],
		//		Overdue:  false,
		//	}

		result, err = contract.SubmitTransaction("deposit", v.FileTime.String(), v.FilePath, v.FileHash, string(v.UserId), "false")
		if err != nil {
			fmt.Printf("Failed to evaluate transaction: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}
		fmt.Printf("写入成功:%s", string(result))

		c.Data["json"] = string(result)
	} else {
		c.Data["json"] = err
	}

	c.ServeJSON()
}

// Verify ...
// @Title Verify
// @Description verify FabricFile from BlockChain by hashcode
// @Param	body		body 	models.FabricFile	true		"body for FabricFile content"
// @Success 201 {int} models.FabricFile
// @Failure 403 body is empty
// @router /verify [post]
func (c *FabricFileController) Verify() {

	var v models.FabricFile
	if err := json.Unmarshal(c.Ctx.Input.RequestBody, &v); err == nil {

		fmt.Println("接收数据", v)

		wallet, err := gateway.NewFileSystemWallet("wallet")
		if err != nil {
			fmt.Printf("Failed to create wallet: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}

		if !wallet.Exists(v.UserName) {
			err = populateWallet(wallet, v)
			if err != nil {
				fmt.Printf("Failed to populate wallet contents: %s\n", err)
				//os.Exit(1)
				c.Data["json"] = err
				return
			}
		}

		ccpPath := filepath.Join(
			"..",
			"ispogsecbob-fabric",
			"network",
			"connection",
			"connection-org"+v.AllowUser+".yaml",
		)

		gw, err := gateway.Connect(
			gateway.WithConfig(config.FromFile(filepath.Clean(ccpPath))),
			gateway.WithIdentity(wallet, v.UserName),
		)
		if err != nil {
			fmt.Printf("Failed to connect to gateway: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}
		defer gw.Close()

		network, err := gw.GetNetwork("mychannel")
		if err != nil {
			fmt.Printf("Failed to get network: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}

		contract := network.GetContract("mycc")

		result, err := contract.EvaluateTransaction("verify", v.FileHash)
		if err != nil {
			fmt.Printf("Failed to evaluate transaction: %s\n", err)
			//os.Exit(1)
			c.Data["json"] = err
			return
		}
		fmt.Printf("验证结果:%s", string(result))

		if err != nil {
			c.Data["json"] = err
		} else {
			c.Data["json"] = string(result)
		}
	} else {
		c.Data["json"] = err
	}

	c.ServeJSON()
}

func populateWallet(wallet *gateway.Wallet, fabricFile models.FabricFile) error {
	credPath := filepath.Join(
		"..",
		"ispogsecbob-fabric",
		"network",
		"crypto-config",
		"peerOrganizations",
		"org"+fabricFile.AllowUser+".example.com",
		"users",
		"User1@org"+fabricFile.AllowUser+".example.com",
		"msp",
	)

	certPath := filepath.Join(credPath, "signcerts", "User1@org"+fabricFile.AllowUser+".example.com-cert.pem")
	// read the certificate pem
	cert, err := ioutil.ReadFile(filepath.Clean(certPath))
	if err != nil {
		return err
	}

	keyDir := filepath.Join(credPath, "keystore")
	// there's a single file in this dir containing the private key
	files, err := ioutil.ReadDir(keyDir)
	if err != nil {
		return err
	}
	if len(files) != 1 {
		return errors.New("keystore folder should have contain one file")
	}
	keyPath := filepath.Join(keyDir, files[0].Name())
	key, err := ioutil.ReadFile(filepath.Clean(keyPath))
	if err != nil {
		return err
	}

	identity := gateway.NewX509Identity("Org"+fabricFile.AllowUser+"MSP", string(cert), string(key))

	err = wallet.Put(fabricFile.UserName, identity)
	if err != nil {
		return err
	}
	return nil
}
