import requests
import re
import sys
def getGoogleToken(a, TKK):
    def RL(a, b):
        for d in range(0, len(b)-2, 3):
            c = b[d + 2]
            c = ord(c[0]) - 87 if 'a' <= c else int(c)
            c = a >> c if '+' == b[d + 1] else a << c
            a = a + c & 4294967295 if '+' == b[d] else a ^ c
        return a
    g = []
    f = 0
    while f < len(a):
        c = ord(a[f])
        if 128 > c:
            g.append(c)
        else:
            if 2048 > c:
                g.append((c >> 6) | 192)
            else:
                if (55296 == (c & 64512)) and (f + 1 < len(a)) and (56320 == (ord(a[f+1]) & 64512)):
                    f += 1
                    c = 65536 + ((c & 1023) << 10) + (ord(a[f]) & 1023)
                    g.append((c >> 18) | 240)
                    g.append((c >> 12) & 63 | 128)
                else:
                    g.append((c >> 12) | 224)
                    g.append((c >> 6) & 63 | 128)
            g.append((c & 63) | 128)
        f += 1
    e = TKK.split('.')
    h = int(e[0]) or 0
    t = h
    for item in g:
        t += item
        t = RL(t, '+-a^+6')
    t = RL(t, '+-3^+b+-f')
    t ^= int(e[1]) or 0
    if 0 > t:
        t = (t & 2147483647) + 2147483648
    result = t % 1000000
    return str(result) + '.' + str(result ^ h)
def getVoice():
    text=sys.argv[1]
    headers={'authority':'translate.google.cn','method':'GET','path':'/translate_tts?ie=UTF-8&q=123&tl=zh-CN&total=1&idx=0&textlen=3&tk=719756.802724&client=webapp&prev=input','scheme':'https','accept':'*/*','accept-encoding':'identity;q=1, *;q=0','accept-language':'zh-CN,zh;q=0.9','cookie':'NID=204=aMtbWoxcXhxyZ6yyv3CmXlucmBXW76jiTaIogT_mGXVC-TuRxpfvOuie0DXYM8uJs9E4PN1V9Z-1Zii-zlozt4mlDT816N8SsVesy_o5Gsz1xf6nn2BUljqxrFwdoUwPl2mhyUrjUo1yFS7sGtgaSeg5O-ZNA0He8nqacAsNYQY; _ga=GA1.3.1646810872.1596357357; _gid=GA1.3.1778963367.1596357357; 1P_JAR=2020-8-2-8','range':'bytes=0-','referer':'https://translate.google.cn/','sec-fetch-dest':'audio','sec-fetch-mode':'no-cors','sec-fetch-site':'same-origin','user-agent':'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36','x-client-data':'CKC1yQEIhbbJAQiitskBCMS2yQEIqZ3KAQiKocoBCIa1ygEImLXKAQiKv8oBCOfIygE=',}
    res = requests.get('https://translate.google.cn', timeout = 3)
    res.raise_for_status()
    tokenKey = re.search(r'tkk\:\'(\d+\.\d+)?\'', res.text).group(1)
    googleToken=getGoogleToken(text,tokenKey)
    f=open(f"voice.mp3","wb")
    f.write(requests.get(f"https://translate.google.cn/translate_tts?ie=UTF-8&q={text}&tl=zh-CN&total=1&idx=0&textlen={len(text)}&tk={googleToken}&client=webapp&prev=input",headers=headers).content)
    f.close()
if __name__=="__main__":
    getVoice()