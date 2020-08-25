package routers

import (
	"github.com/astaxie/beego"
	"github.com/astaxie/beego/context/param"
)

func init() {

	beego.GlobalControllerRouter["ispogsecbob-api/controllers:FabricFileController"] = append(beego.GlobalControllerRouter["ispogsecbob-api/controllers:FabricFileController"],
		beego.ControllerComments{
			Method:           "Save",
			Router:           "/save",
			AllowHTTPMethods: []string{"post"},
			MethodParams:     param.Make(),
			Filters:          nil,
			Params:           nil})

	beego.GlobalControllerRouter["ispogsecbob-api/controllers:FabricFileController"] = append(beego.GlobalControllerRouter["ispogsecbob-api/controllers:FabricFileController"],
		beego.ControllerComments{
			Method:           "Verify",
			Router:           "/verify",
			AllowHTTPMethods: []string{"post"},
			MethodParams:     param.Make(),
			Filters:          nil,
			Params:           nil})

}
