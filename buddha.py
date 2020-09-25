import requests
import sys

mode = sys.argv[1]
txt = sys.argv[2]
headers = {'Accept': '*/*', 'Accept-Encoding': 'gzip, deflate', 'Accept-Language': 'zh-CN,zh;q=0.9',
           'Connection': 'keep-alive', 'Content-Length': '31', 'Content-type': 'application/x-www-form-urlencoded',
           'Cookie': 'pgv_pvi=2299099136; pgv_si=s180898816', 'Host': 'hi.pcmoe.net', 'Origin': 'http://hi.pcmoe.net',
           'Referer': 'http://hi.pcmoe.net/buddha.html',
           'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36',
           'X-Requested-With': 'XMLHttpRequest', 'X-Token': 'B28D0492ADAC', }
if mode == "encode":
    req=requests.post("http://hi.pcmoe.net/bear.php", headers=headers,
                        data={"mode": "Buddha", "code": "Encode", "txt": txt})
    req.encoding="utf-8"
    print(req.text)
if mode == "decode":
    req=requests.post("http://hi.pcmoe.net/bear.php", headers=headers,
                        data={"mode": "Buddha", "code": "Decode", "txt": txt})
    req.encoding="utf-8"
    print(req.text)
