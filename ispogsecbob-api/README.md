# 区块链API服务

## run 

````jshelllanguage
bee run -gendoc=true -downdoc=true
````

http://localhost:8080/swagger/

# \[注意]

1.出现报错 not enough arguments in call to s.statsd.SendLoop

```jshelllanguage
在s.statsd.SendLoop添加context.Background()如下所示
s.statsd.SendLoop(context.Background(), s.sendTicker.C, network, address)
在第227行的fabric-sdk-go/internal/github.com/hyperledger/fabric/core/operations/system.go文件中。
```

2.采用GO SDK提交交易失败　(需要先进行查询)


3.生成的接口文档实体没有字段
````
請在g_docs.go的第84行底下加入

curPath, _ := os.Getwd()
parsePackagesFromDir(curPath)

再重新編譯安裝
$ go build;go install
````

# 测试数据

```json
{
	"id": 100,
	"fileName": "install_kubelet.sh",
	"filePath": "/home/mikey/Downloads/tmp/159788481288739200384/install_kubelet.sh",
	"fileHash": "0dacee6571e7b8e3dd0cf3f8940ab484a9807ade5a7655ea049d33c15bb8d5fb",
	"fileTime": "2020-11-09T21:21:46+00:00",
	"userId": 1101,
	"userName": "user1",
	"status": 1,
	"allowUser": "1"
}
```