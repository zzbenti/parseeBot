import requests
req=requests.get("https://v1.hitokoto.cn").json()["hitokoto"]
print(req,end="")