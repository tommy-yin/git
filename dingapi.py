# -*- coding: utf-8 -*-
import requests
import time
import xlwt
import structure
import sys

appkey = "dingjwjj6ptwmzq0bind"
appsecret = "oJDQf6HPPMsmOHFiHyU6wm2Omz_xztne3Bo2_kay2NvRyBH6_YBpjnCMRV2LkAtJ"
'''获取access_TOKEN'''
response = requests.get("https://oapi.dingtalk.com/gettoken?appkey={}&appsecret={}".format(appkey, appsecret))
accesstoken = response.json()
accesstoken = accesstoken['access_token']
print(accesstoken)
#
#
#
# depid = requests.get("https://oapi.dingtalk.com/department/list?"
#                      "access_token=" + accesstoken + "&fetch_child=True")  # 获取部门列表
# id = depid.json()
# print(id['department'][0]['name'])
#
#
# usersid = requests.get("https://oapi.dingtalk.com/user/getDeptMember?"
#                        "access_token=" + accesstoken + "&deptId=1")    # 获取该部门用户ID
# usersid = usersid.json()
# print(usersid['userIds'])


ids = ['215856085836727316', '204723524033812229', '675366695322921069', '085929405820725644',
       '010060485937811226', '0417310304647551564', '181828345321920655', '1111521552860954704',
       '01091731692123532655', '032060285832551634', '250716551935589287', '086214094324071525',
       '04596454011220641', '083443561137802986', '250401241634072721']
department = "珠海科创电力电子有限公司深圳分公司"

stuff = {}  # 名字 工号 部门 打卡时间

'''创建新Excel'''
new = xlwt.Workbook()
sheet1 = new.add_sheet("打卡记录", cell_overwrite_ok=True)

pattern = xlwt.Pattern()
pattern.pattern = xlwt.Pattern.SOLID_PATTERN
pattern.pattern_fore_colour = 5
style = xlwt.XFStyle()
style.pattern = pattern

s2 = xlwt.easyxf('align: wrap on')

s3 = xlwt.easyxf('font: bold on; align: wrap on, vert centre, horiz center')

pattern = xlwt.Pattern()
pattern.pattern = xlwt.Pattern.SOLID_PATTERN
pattern.pattern_fore_colour = 4
s4 = xlwt.XFStyle()
s4 = xlwt.easyxf('align: wrap on')
s4.pattern = pattern


'''获取员工名字列表'''
def name_id():
    nameid = {}
    for i in range(0, 15):
        idn = ids[i]
        username = requests.get("https://oapi.dingtalk.com/user/get?"
                                "access_token=" + accesstoken + "&userid=" + idn)
        username = username.json()
        nameid[idn] = username['name']
        # print(nameid)
    return nameid


'''按日期搜索考勤数据'''
def daka_data(start, end):
    daka = {"workDateFrom": start,
            "workDateTo": end,
            "userIdList": ids,
            "offset": 0,
            "limit": 50,
            }
    result = requests.post("https://oapi.dingtalk.com/attendance/list?"
                           "access_token=" + accesstoken, json=daka)  # 获取考勤数据
    res = result.json()
    print(start, "打卡次数:", len(res['recordresult']))
    return res


def stuffinfo():
    for i in range(0, 15):
        stuff[nameid[ids[i]]] = [nameid[ids[i]], ids[i], department, list()]
        for k in range(1, 32):
            stuff[nameid[ids[i]]][3].insert(2*k, str(k))
            stuff[nameid[ids[i]]][3].insert((2*k)+1, str(k))
    print(stuff)
    return stuff


'''整理考勤数据'''
def arrange(res):
    for i in range(0, len(res['recordresult'])):    # 某天所有打卡数据
        tt = int(res['recordresult'][i]['userCheckTime'])
        timeArray = time.localtime(tt/1000)
        day = time.strftime("%m-%d", timeArray)
        date = time.strftime("%d", timeArray)
        dakatime = time.strftime("%H:%M", timeArray)
        # print(nameid[res['recordresult'][i]['userId']] + " "
        #       + res['recordresult'][i]['userId'] + " "
        #       + department + " "
        #       + day + " "
        #       + res['recordresult'][i]['checkType'] + " "
        #       + dakatime)

        if res['recordresult'][i]['checkType'] == "OnDuty":
            del stuff[nameid[res['recordresult'][i]['userId']]][3][2*(int(date)-1)]
            stuff[nameid[res['recordresult'][i]['userId']]][3].insert(2*(int(date)-1), dakatime)
        else:
            del stuff[nameid[res['recordresult'][i]['userId']]][3][2*(int(date)-1)+1]
            stuff[nameid[res['recordresult'][i]['userId']]][3].insert(2*(int(date)-1)+1, dakatime)


'''给时间戳取考勤数据'''
def get_checkin_data(time_start, time_end):
    ts = time_start.split('-')
    te = time_end.split('-')
    ts = [int(ts[0]), int(ts[1])]
    te = [int(te[0]), int(te[1])]
    if ts[0] == te[0] and te[1] > ts[1]:  # 头尾时间需同月
        gap = te[1] - ts[1]
        for i in range(0, gap+1):   # 按日期
            start = "2020-" + str(ts[0]) + "-" + str(ts[1]+i) + " 00:00:00"
            end = "2020-" + str(ts[0]) + "-" + str(ts[1]+i) + " 23:59:59"
            res = daka_data(start, end)
            arrange(res)
    else:
        print("头尾时间需同月, 日期输入错误")


# def newexcel():
#     sheet1.write_merge(0, 1, 0, 35, "打卡记录", s3)  # 合并单元格
#     for i in range(0, 5):
#         col_0 = sheet1.col(i)
#         col_0.width = 256 * 10
#     for i in range(5, 36):
#         col_0 = sheet1.col(i)
#         col_0.width = 256 * 6  # 日期单元格宽度
#         sheet1.write(2, i, str(i-4))
#
#     col_0 = sheet1.col(0)  # 工号
#     col_0.width = 256 * 13
#     col_0 = sheet1.col(2)   # 部门
#     col_0.width = 256 * 40
#     col_0 = sheet1.col(4)  # 打卡规则
#     col_0.width = 256 * 25
#
#     sheet1.write(2, 0, "工号")
#     sheet1.write(2, 1, "姓名")
#     sheet1.write(2, 2, "部门")
#     sheet1.write(2, 3, "职位")
#     sheet1.write(2, 4, "打卡规则")


def getout():
    structure.newexcel()
    # print(namelist)
    for i in range(0, len(ids)):
        sheet1.write(i + 3, 0, stuff[nameid[ids[i]]][1], s2)   # 逐个类型输出到表格
        sheet1.write(i + 3, 1, stuff[nameid[ids[i]]][0])
        sheet1.write(i + 3, 2, stuff[nameid[ids[i]]][2])
        # sheet1.write(i + 3, 4, stuff[nameid[ids[i]]][1])
        '''录入打卡时间数据'''
        for k in range(5, 35):
            '''上下班时间都有，不为--'''
            if stuff[nameid[ids[i]]][3][2*(k-4-1)] != str(k-4) and stuff[nameid[ids[i]]][3][2*(k-4-1)+1] != str(k-4):
                '''迟到判断'''
                sb = list()
                xb = list()
                sb.extend(stuff[nameid[ids[i]]][3][2 * (k - 4 - 1)])
                sbh = int(sb[0]) * 10 + int(sb[1])
                sbm = int(sb[3]) * 10 + int(sb[4])
                xb.extend(stuff[nameid[ids[i]]][3][2 * (k - 4 - 1) + 1])
                xbh = int(xb[0]) * 10 + int(xb[1])
                xbm = int(xb[3]) * 10 + int(xb[4])
                t = stuff[nameid[ids[i]]][3][2*(k-4-1)] + stuff[nameid[ids[i]]][3][2*(k-4-1)+1]
                if (sbh > 9 or (sbh == 9 and sbm > 00)) or (xbh < 18 or (xbh == 18 and xbm < 00)):
                    sheet1.write(i + 3, k, t, s4)
                else:
                    sheet1.write(i + 3, k, t, s2)

                '''只有上班或下班'''
            elif stuff[nameid[ids[i]]][3][2*(k-4-1)] != str(k-4) or stuff[nameid[ids[i]]][3][2*(k-4-1)+1] != str(k-4):
                if stuff[nameid[ids[i]]][3][2*(k-4-1)] != str(k-4):
                    sheet1.write(i + 3, k, stuff[nameid[ids[i]]][3][2*(k-4-1)], style)
                else:
                    sheet1.write(i + 3, k, stuff[nameid[ids[i]]][3][2*(k-4-1)+1], style)

    new.save('dingtalk_data.xls')


if __name__ == '__main__':
    nameid = name_id()
    stuff = stuffinfo()
    time_start = "04-01"
    time_end = "04-11"
    get_checkin_data(time_start, time_end)
    getout()
    print(stuff)

