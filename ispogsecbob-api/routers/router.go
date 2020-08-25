// @APIVersion 1.0.0
// @Title 区块链存证系统服务调用 API
// @Description 区块链存证系统服务调用 API
// @Contact biaogejiushibiao@gmail.com
// @TermsOfServiceUrl https://mikeygithub.github.io/
// @License Apache 2.0
// @LicenseUrl http://www.apache.org/licenses/LICENSE-2.0.html
package routers

import (
	"ispogsecbob-api/controllers"

	"github.com/astaxie/beego"
)

func init() {
	ns := beego.NewNamespace("/api",

		beego.NSNamespace("/fabric",
			beego.NSInclude(
				&controllers.FabricFileController{},
			),
		),
	)
	beego.AddNamespace(ns)
}
