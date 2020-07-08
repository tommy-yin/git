import xlrd
import xlwt

'''导入Excel'''
source = xlrd.open_workbook('21-27.xls')  # 导入Excel文件
table = source.sheet_by_index(1)  # 导入按序号索引表格1

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

'''导入原表数据'''
date = table.col_values(0)  # 导入第1列所有数据-日期
name = table.col_values(1)  # 导入第2列所有数据-姓名
account = table.col_values(2)  # 导入第3列所有数据-账号
department = table.col_values(3)  # 导入第4列所有数据-部门
rule = table.col_values(4)  # 导入第5列所有数据-打卡规则
kind = table.col_values(5)  # 导入第6列所有数据-打卡类型
time = table.col_values(6)  # 导入第7列所有数据-打卡时间

stuff = {}  # 名字 工号 部门 打卡时间 打卡规则


def getin():
    day = "2020/06/"
    for i in range(3, table.nrows):
        stuff[name[i]] = [name[i], account[i], department[i], list(), rule[i]]  # 需要什么数据
        for k in range(1, 32):
            stuff[name[i]][3].insert(2*k, str(k))
            stuff[name[i]][3].insert((2*k)+1, str(k))
    for i in range(3, table.nrows):
        for k in range(1, 32):
            dayy = day + str(k)
            if date[i] == dayy and stuff[name[i]][0] == name[i]:
                if kind[i] == "上班打卡":
                    del stuff[name[i]][3][2*(k-1)]
                    stuff[name[i]][3].insert(2*(k-1), time[i])
                else:
                    del stuff[name[i]][3][2*(k-1)+1]
                    stuff[name[i]][3].insert(2*(k-1)+1, time[i])

    # print(stuff[name[15]])


def inputname():
    s = list()
    k = 0
    for i in range(3, table.nrows):
        if name[i] not in s:
            s.insert(k, name[i])
            k = k + 1
    return s


def newexcel():
    sheet1.write_merge(0, 1, 0, 35, "打卡记录", s3)  # 合并单元格
    for i in range(0, 5):
        col_0 = sheet1.col(i)
        col_0.width = 256 * 10
    for i in range(5, 36):
        col_0 = sheet1.col(i)
        col_0.width = 256 * 6  # 日期单元格宽度
        sheet1.write(2, i, str(i-4))

    col_0 = sheet1.col(0)  # 工号
    col_0.width = 256 * 13
    col_0 = sheet1.col(2)   # 部门
    col_0.width = 256 * 40
    col_0 = sheet1.col(4)  # 打卡规则
    col_0.width = 256 * 25

    sheet1.write(2, 0, "工号")
    sheet1.write(2, 1, "姓名")
    sheet1.write(2, 2, "部门")
    sheet1.write(2, 3, "职位")
    sheet1.write(2, 4, "打卡规则")


def getout():
    newexcel()
    namelist = inputname()
    # print(namelist)
    for i in range(0, len(namelist)):
        sheet1.write(i + 3, 0, stuff[namelist[i]][1])   # 逐个类型输出到表格
        sheet1.write(i + 3, 1, stuff[namelist[i]][0])
        sheet1.write(i + 3, 2, stuff[namelist[i]][2])
        sheet1.write(i + 3, 4, stuff[namelist[i]][4])
        '''录入打卡时间数据'''
        for k in range(5, 35):
            '''上下班时间都有，不为--'''
            if stuff[namelist[i]][3][2*(k-4-1)] != str(k-4) and stuff[namelist[i]][3][2*(k-4-1)+1] != str(k-4) and \
                    stuff[namelist[i]][3][2*(k-4-1)] != "--" and stuff[namelist[i]][3][2*(k-4-1)+1] != "--":
                '''迟到判断'''
                sb = list()
                xb = list()
                sb.extend(stuff[namelist[i]][3][2 * (k - 4 - 1)])
                sbh = int(sb[0]) * 10 + int(sb[1])
                sbm = int(sb[3]) * 10 + int(sb[4])
                xb.extend(stuff[namelist[i]][3][2 * (k - 4 - 1) + 1])
                xbh = int(xb[0]) * 10 + int(xb[1])
                xbm = int(xb[3]) * 10 + int(xb[4])
                t = stuff[namelist[i]][3][2*(k-4-1)] + stuff[namelist[i]][3][2*(k-4-1)+1]
                if (sbh > 8 or (sbh == 8 and sbm > 30)) or (xbh < 17 or (xbh == 17 and xbm < 30)):
                    sheet1.write(i + 3, k, t, s4)
                else:
                    sheet1.write(i + 3, k, t, s2)

                '''只有上班或下班，不为--'''
            elif stuff[namelist[i]][3][2*(k-4-1)] != str(k-4) or stuff[namelist[i]][3][2*(k-4-1)+1] != str(k-4):
                if stuff[namelist[i]][3][2*(k-4-1)] != str(k-4) or stuff[namelist[i]][3][2*(k-4-1)] != "--" and \
                        stuff[namelist[i]][3][2*(k-4-1)] != str(k-4):

                    sheet1.write(i + 3, k, stuff[namelist[i]][3][2*(k-4-1)], style)
                else:

                    sheet1.write(i + 3, k, stuff[namelist[i]][3][2*(k-4-1)+1], style)

    new.save('new.xls')


if __name__ == '__main__':
    print("111")
    getin()
    getout()
else:
    print("from structure")

