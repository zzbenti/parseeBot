import requests
import sys
import random
json=requests.get(f"http://127.0.0.1:4001/api/biu/search/works?kt={sys.argv[2]}&mode=tag&totalPage=5&isCache=1&groupIndex=0&sortMode=0").json()
r18Lst=[]
noR18Lst=[]
for i in json["msg"]["rst"]["data"]:
    if("R-18" in i[tags"]):
        r18Lst.append(i)
    else:
        noR18Lst.append(i)
if sys.argv[1]=="yes":
    print(random.choice(r18Lst)["image_urls"]["medium"].replace("i.pximg.net","i.pixiv.cat"))
elif sys.argv[1]=="no":
    print(random.choice(noR18Lst)["image_urls"]["medium"].replace("i.pximg.net","i.pixiv.cat"))