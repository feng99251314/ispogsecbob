# 区块链浏览器

### 配置

```json

{
  "adminPrivateKey": 
    {
		"path": "/tmp/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore/75188db466c6261b636c9fe76d4fa89a1091eda0d3c6023c4811d0d4870898e8_sk"
	}}
```


### 启动

``
docker-compose up -d
``

run and then open
 
 [访问链接](http://localhost:8080/explorer)
  
to view the fabric explorer.

[参考链接](https://ecsoya.github.io/fabric/pages/demo.html)

### 关闭

```jshelllanguage
docker-compose down
```