import requests
import sys
import re
url=sys.argv[1]
html=requests.get(url).text
title=re.findall('<title data-vue-meta="true">(.*?)_哔哩哔哩 \(゜-゜\)つロ 干杯~-bilibili</title>',html)[0]
publishTime=re.findall('<meta data-vue-meta="true" itemprop="datePublished" content="(.*?)">',html)[0]
describe=re.findall('<meta data-vue-meta="true" itemprop="description" name="description" content="(.*?)">',html)[0]
up=re.findall('<meta data-vue-meta="true" itemprop="author" name="author" content="(.*?)">',html)[0]
url=re.findall('<meta data-vue-meta="true" itemprop="url" content="(.*?)">',html)[0]
aid=re.findall("av(\d+)",url)[0]
image=re.findall('<meta data-vue-meta="true" property="og:image" content="(.*?)">',html)[0]
videoInfoJson=requests.get(f"http://api.bilibili.com/archive_stat/stat?aid={aid}").json()["data"]
view=videoInfoJson["view"]
like=videoInfoJson["like"]
favorite=videoInfoJson["favorite"]
coin=videoInfoJson["coin"]
share=videoInfoJson["share"]
print(f"{title}\n{publishTime}\n{describe}\n{up}\n{url}\n{aid}\n{image}\n{view}\n{like}\n{favorite}\n{coin}\n{share}")