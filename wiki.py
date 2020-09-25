import requests,sys,re
rex=re.compile('【条目】<a href="[\s\S]*?" target=\'_blank\'><b>([\s\S]*?)</b></a></h4>')
headers={'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9','Accept-Encoding':'gzip, deflate, br','Accept-Language':'zh-CN,zh;q=0.9','Cache-Control':'no-cache','Connection':'keep-alive','Content-Length':'17','Content-Type':'application/x-www-form-urlencoded','Cookie':'PHPSESSID=2r27u0rve30om4k2l905oo8250; UM_distinctid=174c5dabeb1bf9-07691088aad438-31697304-13c680-174c5dabeb28ed; CNZZDATA1278192212=1090480608-1601043829-%7C1601043829; __gads=ID=b5bd838b5ebd06d2:T=1601047282:S=ALNI_MZPpNGuGwUgnOFGVZrCIQYSJxdQvw','Host':'wanweibaike.com','Origin':'https://wanweibaike.com','Pragma':'no-cache','Referer':'https://wanweibaike.com/','Sec-Fetch-Dest':'document','Sec-Fetch-Mode':'navigate','Sec-Fetch-Site':'same-origin','Sec-Fetch-User':'?1','Upgrade-Insecure-Requests':'1','User-Agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36',}
data={'kw':" ".join(sys.argv[1:])}
html=requests.post("https://wanweibaike.com/Index/search",headers=headers,data=data).text
result=rex.findall(html)
try:
    result.remove("")
except:
    pass
for i in result:
    print(i)