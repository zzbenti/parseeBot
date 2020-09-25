import requests
import sys
headers={'authority':'bangumi.bilibili.com','method':'GET','path':'/web_api/timeline_global','scheme':'https','accept':'application/json, text/plain, */*','accept-encoding':'gzip, deflate, br','accept-language':'zh-CN,zh;q=0.9','cookie':'_uuid=862DC32A-054F-5779-80B5-6CD50DA98AA485769infoc; buvid3=CEEC8631-C8D2-4348-A165-DB8D66AC9DE6138400infoc; PVID=1; CURRENT_FNVAL=16','origin':'https://www.bilibili.com','referer':'https://www.bilibili.com/anime/timeline','sec-fetch-dest':'empty','sec-fetch-mode':'cors','sec-fetch-site':'same-site','user-agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36',}
json=requests.get("https://bangumi.bilibili.com/web_api/timeline_global").json()
day=int(sys.argv[1])
if(json["message"]=="success"):
    print(f"日期:{json['result'][6+day]['date']}")
    for i in json["result"][6+day]["seasons"]:
        if "僅限" in i["title"]:
            continue
        print(f"{i['title']}/{i['pub_index']}/{i['pub_time']}\n")
    print("已过滤港澳台地区新番")
else:
    print("错误")