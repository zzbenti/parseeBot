import requests
import sys
musicName=sys.argv[1]
json=requests.get(f"http://musicapi.leanapp.cn/search?keywords={musicName}").json()
songCount=json["result"]["songCount"]
if int(songCount)!=0:
    musicId=json["result"]["songs"][0]["id"]
    print(f"https://music.163.com/#/song?id={musicId}")
else:
    print("错误")