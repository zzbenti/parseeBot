import requests
import re
import random
import sys
headers={'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9','Accept-Encoding':'gzip, deflate, br','Accept-Language':'zh-CN,zh;q=0.9','Cache-Control':'no-cache','Connection':'keep-alive','Cookie':'BIDUPSID=1025F651E8A203814960422D017315E0; PSTM=1595493625; BAIDUID=1025F651E8A2038179BB4FCAA3A83EF5:FG=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; PSINO=1; H_PS_PSSID=7541_32617_1422_7567_31253_7551_32677_7622_32116_26350; BDRCVFR[dG2JNJb_ajR]=mk3SLVN4HKm; userFrom=www.baidu.com','Host':'image.baidu.com','Pragma':'no-cache','Referer':'https://www.baidu.com/s?ie=UTF-8&wd=%E6%88%91%E7%88%B1%E4%BD%A0','Sec-Fetch-Dest':'document','Sec-Fetch-Mode':'navigate','Sec-Fetch-Site':'same-origin','Sec-Fetch-User':'?1','Upgrade-Insecure-Requests':'1','User-Agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36',}
html=requests.get(f"https://image.baidu.com/search/index?tn=baiduimage&ps=1&ct=201326592&lm=-1&cl=2&nc=1&ie=utf-8&word={sys.argv[1]}",headers=headers).text
rex=re.compile('"objURL":"(.*?)"',re.S)
print(random.choice(rex.findall(html)))
