package models

import (
	"errors"
	"fmt"
	"reflect"
	"strings"
	"time"

	"github.com/astaxie/beego/orm"
)

type FabricFile struct {
	Id        int       `orm:"column(id);auto" description:"id";json:"id"example:"1"`
	FileName  string    `orm:"column(file_name);size(255);null" description:"文件名" json:"fileName" example:"fileName"`
	FilePath  string    `orm:"column(file_path);size(255);null" description:"保存路径" json:"filePath" example:"fileName"`
	FileHash  string    `orm:"column(file_hash);size(255);null" description:"文件哈希" json:"fileHash"example:"fileName"`
	FileTime  time.Time `orm:"column(file_time);type(timestamp);auto_now" description:"保存时间" json:"fileTime" example:"2020-11-09T21:21:46+00:00"`
	UserId    int64     `orm:"column(user_id);null" description:"拥有者id" json:"userId" example:"1"`
	UserName  string    `orm:"column(user_name);null" description:"拥有者用户名" json:"userName" example:"userName"`
	Status    int       `orm:"column(status);null" description:"状态" json:"status" example:"1"`
	AllowUser string    `orm:"column(allow_user);size(255);null" json:"allowUser" example:"1"`
}

func (t *FabricFile) TableName() string {
	return "ent_fabric_file"
}

func init() {
	orm.RegisterModel(new(FabricFile))
}

// AddFabricFile insert a new FabricFile into database and returns
// last inserted Id on success.
func AddFabricFile(m *FabricFile) (id int64, err error) {
	o := orm.NewOrm()
	id, err = o.Insert(m)
	return
}

// GetFabricFileById retrieves FabricFile by Id. Returns error if
// Id doesn't exist
func GetFabricFileById(id int) (v *FabricFile, err error) {
	o := orm.NewOrm()
	v = &FabricFile{Id: id}
	if err = o.Read(v); err == nil {
		return v, nil
	}
	return nil, err
}

// GetAllFabricFile retrieves all FabricFile matches certain condition. Returns empty list if
// no records exist
func GetAllFabricFile(query map[string]string, fields []string, sortby []string, order []string,
	offset int64, limit int64) (ml []interface{}, err error) {
	o := orm.NewOrm()
	qs := o.QueryTable(new(FabricFile))
	// query k=v
	for k, v := range query {
		// rewrite dot-notation to Object__Attribute
		k = strings.Replace(k, ".", "__", -1)
		if strings.Contains(k, "isnull") {
			qs = qs.Filter(k, (v == "true" || v == "1"))
		} else {
			qs = qs.Filter(k, v)
		}
	}
	// order by:
	var sortFields []string
	if len(sortby) != 0 {
		if len(sortby) == len(order) {
			// 1) for each sort field, there is an associated order
			for i, v := range sortby {
				orderby := ""
				if order[i] == "desc" {
					orderby = "-" + v
				} else if order[i] == "asc" {
					orderby = v
				} else {
					return nil, errors.New("Error: Invalid order. Must be either [asc|desc]")
				}
				sortFields = append(sortFields, orderby)
			}
			qs = qs.OrderBy(sortFields...)
		} else if len(sortby) != len(order) && len(order) == 1 {
			// 2) there is exactly one order, all the sorted fields will be sorted by this order
			for _, v := range sortby {
				orderby := ""
				if order[0] == "desc" {
					orderby = "-" + v
				} else if order[0] == "asc" {
					orderby = v
				} else {
					return nil, errors.New("Error: Invalid order. Must be either [asc|desc]")
				}
				sortFields = append(sortFields, orderby)
			}
		} else if len(sortby) != len(order) && len(order) != 1 {
			return nil, errors.New("Error: 'sortby', 'order' sizes mismatch or 'order' size is not 1")
		}
	} else {
		if len(order) != 0 {
			return nil, errors.New("Error: unused 'order' fields")
		}
	}

	var l []FabricFile
	qs = qs.OrderBy(sortFields...)
	if _, err = qs.Limit(limit, offset).All(&l, fields...); err == nil {
		if len(fields) == 0 {
			for _, v := range l {
				ml = append(ml, v)
			}
		} else {
			// trim unused fields
			for _, v := range l {
				m := make(map[string]interface{})
				val := reflect.ValueOf(v)
				for _, fname := range fields {
					m[fname] = val.FieldByName(fname).Interface()
				}
				ml = append(ml, m)
			}
		}
		return ml, nil
	}
	return nil, err
}

// UpdateFabricFile updates FabricFile by Id and returns error if
// the record to be updated doesn't exist
func UpdateFabricFileById(m *FabricFile) (err error) {
	o := orm.NewOrm()
	v := FabricFile{Id: m.Id}
	// ascertain id exists in the database
	if err = o.Read(&v); err == nil {
		var num int64
		if num, err = o.Update(m); err == nil {
			fmt.Println("Number of records updated in database:", num)
		}
	}
	return
}

// DeleteFabricFile deletes FabricFile by Id and returns error if
// the record to be deleted doesn't exist
func DeleteFabricFile(id int) (err error) {
	o := orm.NewOrm()
	v := FabricFile{Id: id}
	// ascertain id exists in the database
	if err = o.Read(&v); err == nil {
		var num int64
		if num, err = o.Delete(&FabricFile{Id: id}); err == nil {
			fmt.Println("Number of records deleted in database:", num)
		}
	}
	return
}
