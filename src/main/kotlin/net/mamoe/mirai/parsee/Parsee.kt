package net.mamoe.mirai.parsee

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.join
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.nextMessage
import java.io.*
import java.lang.Exception
import java.net.URL
import java.nio.file.FileVisitResult
import java.text.SimpleDateFormat
fun saveSettingsLI(map: Map<Long,Int>,fileName:String){
    val file=File(fileName)
    val fw=FileWriter(file)
    map.forEach{
        fw.write("${it.key}|${it.value}\n")
    }
    fw.close()
}
fun saveSettingsLL(map: Map<Long,Long>,fileName:String){
    val file=File(fileName)
    val fw=FileWriter(file)
    map.forEach{
        fw.write("${it.key}|${it.value}\n")
    }
    fw.close()
}
fun saveSettingsSL(map: Map<String,Long>,fileName:String){
    val file=File(fileName)
    val fw=FileWriter(file)
    map.forEach{
        fw.write("${it.key}|${it.value}\n")
    }
    fw.close()
}
fun readSettingsLI(fileName:String):Map<Long,Int>{
    val file = File(fileName)
    val fr = FileReader(file).readText()
    val map = mutableMapOf<Long, Int>()
    fr.split("\n").forEach {
        try {
            val key = it.split("|")[0].toLong()
            val value = it.split("|")[1].toInt()
            map.put(key, value)
        } catch (e: Exception) {
        }
    }
    return map
}
fun readSettingsLL(fileName:String):Map<Long,Long>{
    val file = File(fileName)
    val fr = FileReader(file).readText()
    val map = mutableMapOf<Long, Long>()
    fr.split("\n").forEach {
        try {
            val key = it.split("|")[0].toLong()
            val value = it.split("|")[1].toLong()
            map.put(key, value)
        } catch (e: Exception) {
        }
    }
    return map
}
fun readSettingsSL(fileName:String):Map<String,Long>{
    val file = File(fileName)
    val fr = FileReader(file).readText()
    val map = mutableMapOf<String, Long>()
    fr.split("\n").forEach {
        try {
            val key = it.split("|")[0].toString()
            val value = it.split("|")[1].toLong()
            map.put(key, value)
        } catch (e: Exception) {
        }
    }
    return map
}
fun python(args: Array<String>): String {
    val proc: Process
    val sb= java.lang.StringBuilder()
    try {
        proc = Runtime.getRuntime().exec(args)
        val `in` = BufferedReader(InputStreamReader(proc.inputStream))
        var line: String? = null
        while (`in`.readLine().also { line = it } != null) {
            sb.append("${line}\n")
        }
        `in`.close()
        proc.waitFor()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    return sb.toString()
}
fun getTime(t: Int): MutableList<Int> {
    val s = t % 60
    val min = ((t - s) % 3600) / 60
    val h = (t - 60 * min - s) / 3600
    val list = mutableListOf<Int>(h, min, s)
    return list
}

fun getLuck(): String {
    val luck = listOf<String>("大吉", "吉", "小吉", "吉大于凶", "中", "凶大于吉", "小凶", "凶", "大凶").shuffled().take(1)
    return luck[0]
}

fun getConversation(): String {
    val conversation = listOf<String>(
            "操纵嫉妒心程度的能力...",
            "帕露帕露是桥姬哦~",
            "在?开花爷爷",
            "少女丑时参拜中...",
            "人类?难道是看上了我们这被诅咒的力量?"
    ).shuffled().take(1);
    return conversation[0]
}

fun getErrorCode(): String {
    val list =
            "a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z".split(
                    "|"
            )
    val sb = StringBuilder()
    for (index in 1..13) {
        sb.append(list.shuffled().take(1)[0])
    }
    return sb.toString()
}

fun getChoice(): Boolean {
    val choice = listOf(true, false).shuffled().take(1)
    return choice[0]
}

fun getPSS(): String {
    val PSS = listOf<String>("剪刀", "石头", "布").shuffled().take(1)
    return PSS[0]
}
fun drawHorse(map: Map<String,Int>,turn:Int):String{
    val sb= java.lang.StringBuilder("伊吹瓢杯赛马比赛-第${turn}回合")
    var time=1
    map.keys.forEach{
        sb.append("\n${time}🏆")
        for(i in 1..map[it]!!){
            sb.append("O")
        }
        sb.append("🐎")
        sb.append(map[it]!!)
        sb.append(it)
        time+=1
    }
    return sb.toString()
}
suspend fun airing(bot:Bot,info:String){
    bot.groups.forEach {
        try {
            bot.getGroup(it.id).sendMessage(info)
        }catch (e:Exception){}
    }
}
suspend fun main() {
    val bot = Bot(
            12312313L,
            "123123123"
    ) {
        fileBasedDeviceInfo("device.json") // 使用 "device.json" 保存设备信息
    }.alsoLogin()
    val getBangumi="""import requests
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
    """.trimIndent()
    val fw1= FileWriter("getBangumi.py")
    fw1.write(getBangumi)
    fw1.close()
    var sleep = mutableMapOf<String, Long>()
    try{
       sleep= readSettingsSL("sleep.txt") as MutableMap<String, Long>
    }catch(e:Exception){}
    var history = mutableMapOf<String, String>()
    val choice = mutableMapOf<String, Boolean>()
    var tip = true
    val sleepSpeak = mutableMapOf<Long, Int>();
    var blockList = mutableListOf<Long>();
    var lastMessage: String = ""
    var lastLastMessage: String = ""
    val pics = "https://konachan.net/sample/e7c977cfce53e656acbae6ff78e2ab11/Konachan.com%20-%20300757%20sample.jpg,https://konachan.net/image/6318d55300cb4bbd9cb1d2bbb7be1d40/Konachan.com%20-%20300716%20fire%20fujiwara_no_mokou%20mage%20magic%20maru-pen%20touhou%20zoom_layer.jpg,https://konachan.net/sample/27c6554ec9f7cda1778f2fafeff02d82/Konachan.com%20-%20300681%20sample.jpg,https://konachan.net/jpeg/6fa448210d5405b054e5d28e93aaadc4/Konachan.com%20-%20300607%202girls%20blue_hair%20bow%20candy%20chocolate%20dress%20eichi_yuu%20food%20hat%20long_hair%20purple_eyes%20red_eyes%20short_hair%20shoujo_ai%20touhou%20valentine%20vampire%20wristwear.jpg,https://konachan.net/sample/d040e0423b7912b008649e79262fb3e2/Konachan.com%20-%20300589%20sample.jpg,https://konachan.net/sample/47246b44e3e71d8552e7436576c10266/Konachan.com%20-%20300498%20sample.jpg,https://konachan.net/sample/93eb550c0622355d13786afd9103b015/Konachan.com%20-%20300445%20sample.jpg,https://konachan.net/image/fb0204ce0f0f47ad2ad9da82a8a9d44d/Konachan.com%20-%20300441%20ass%20green_eyes%20green_hair%20komeiji_koishi%20maiwetea%20school_swimsuit%20short_hair%20swimsuit%20touhou%20underwater%20water.jpg,https://konachan.net/jpeg/9f9bed8b36c8fcc121c64176edaf5704/Konachan.com%20-%20300428%20bow%20cape%20nnyara%20red_eyes%20red_hair%20sekibanki%20short_hair%20skirt%20touhou%20watermark%20white.jpg,https://konachan.net/sample/d29f36f9fcf4d5c3c148a4e69b19193d/Konachan.com%20-%20300427%20sample.jpg,https://konachan.net/image/44698a09bc14c227548761af0351a413/Konachan.com%20-%20300311%20blonde_hair%20blush%20breasts%20cleavage%20close%20hat%20long_hair%20red_eyes%20roh_nam_kyung%20touhou%20yakumo_yukari.jpg,https://konachan.net/sample/e7c977cfce53e656acbae6ff78e2ab11/Konachan.com%20-%20300757%20sample.jpg,https://konachan.net/image/6318d55300cb4bbd9cb1d2bbb7be1d40/Konachan.com%20-%20300716%20fire%20fujiwara_no_mokou%20mage%20magic%20maru-pen%20touhou%20zoom_layer.jpg,https://konachan.net/sample/27c6554ec9f7cda1778f2fafeff02d82/Konachan.com%20-%20300681%20sample.jpg,https://konachan.net/jpeg/6fa448210d5405b054e5d28e93aaadc4/Konachan.com%20-%20300607%202girls%20blue_hair%20bow%20candy%20chocolate%20dress%20eichi_yuu%20food%20hat%20long_hair%20purple_eyes%20red_eyes%20short_hair%20shoujo_ai%20touhou%20valentine%20vampire%20wristwear.jpg,https://konachan.net/sample/d040e0423b7912b008649e79262fb3e2/Konachan.com%20-%20300589%20sample.jpg,https://konachan.net/sample/47246b44e3e71d8552e7436576c10266/Konachan.com%20-%20300498%20sample.jpg,https://konachan.net/sample/e7c977cfce53e656acbae6ff78e2ab11/Konachan.com%20-%20300757%20sample.jpg,https://konachan.net/image/6318d55300cb4bbd9cb1d2bbb7be1d40/Konachan.com%20-%20300716%20fire%20fujiwara_no_mokou%20mage%20magic%20maru-pen%20touhou%20zoom_layer.jpg,https://konachan.net/sample/27c6554ec9f7cda1778f2fafeff02d82/Konachan.com%20-%20300681%20sample.jpg,https://konachan.net/jpeg/6fa448210d5405b054e5d28e93aaadc4/Konachan.com%20-%20300607%202girls%20blue_hair%20bow%20candy%20chocolate%20dress%20eichi_yuu%20food%20hat%20long_hair%20purple_eyes%20red_eyes%20short_hair%20shoujo_ai%20touhou%20valentine%20vampire%20wristwear.jpg,".split(",")
    var hsoCd: Long = System.currentTimeMillis() / 1000
    val nickId = mutableMapOf<String, Long>()
    var inHorseGame=false
    var turn=0
    var distanceMap = mutableMapOf<String, Int>()
    var nameCountMap= mutableMapOf<String,Int>()
    var winList= mutableListOf<String>()
    var playerNum=0
    var distance=20
    val plane= mutableMapOf<Int,List<String>>()
    plane.put(6, listOf("ReimuA","ReimuB","MarisaA","MarisaB"))
    plane.put(7, listOf("ReimuA","ReimuB","MarisaA","MarisaB"))
    plane.put(8, listOf("Reimu&Yukari","Marisa&Alice","Remilia&Sakuya","Yuyuko&Youmu"))
    plane.put(10, listOf("ReimuA","ReimuB","ReimuC","MarisaA","MarisaB","MarisaC"))
    plane.put(11, listOf("ReimuA","ReimuB","ReimuC","MarisaA","MarisaB","MarisaC"))
    plane.put(12, listOf("ReimuA","ReimuB","MarisaA","MarisaB","SanaeA","SanaeB"))
    plane.put(13, listOf("Reimu","Marisa","Youmu","Sanae"))
    plane.put(14, listOf("ReimuA","ReimuB","MarisaA","MarisaB","SakuyaA","SakuyaB"))
    plane.put(15, listOf("Reimu","Marisa","Sanae","Reisen"))
    plane.put(16, listOf("ReimuSpring","ReimuSummer","ReimuAutumn","ReimuWinter","CirnoSpring","CirnoSummer","CirnoAutumn","CirnoWinter","AyaSpring","AyaSummer","AyaAutumn","AyaWinter","MarisaSpring","MarisaSummer","MarisaAutumn","MarisaWinter"))
    plane.put(17, listOf("ReimuA","ReimuB","ReimuC","MarisaA","MarisaB","MarisaC","YoumuA","YoumuB","YoumuC"))
    /**
     * @key     qq号
     * @value   签到时的时间戳
     */
    var signMap= mutableMapOf<Long,Long>();
    try{
       signMap= readSettingsLL("signMap.txt") as MutableMap<Long, Long>
    }catch (e:Exception){}
    /**
     * @key     qq号
     * @value   签到天数
     */
    var signDays= mutableMapOf<Long,Int>()
    try{
        signDays= readSettingsLI("signDays.txt") as MutableMap<Long, Int>
    }catch(e:Exception){}
    /**
     * @key     qq号
     * @value   西瓜数量
     */
    var coin= mutableMapOf<Long,Int>()
    try {
        coin = readSettingsLI("coin.txt") as MutableMap<Long, Int>
    }catch (e:Exception){}
    /**
     * @key     群号
     * @value   当前签到人数
     */
    val groupSignNum= mutableMapOf<Long,Int>()

    /**
     * @key     qq号
     * @value   水群数
     */
    var groupMessageNum= mutableMapOf<Long,Int>()
    try{
       groupMessageNum= readSettingsLI("groupMessageNum.txt") as MutableMap<Long, Int>
    }catch(e:Exception){}
    /**好感度map
     * @key     qq号
     * @value   好感度
     */
    var like= mutableMapOf<Long,Int>()
    try{
        like= readSettingsLI("like.txt") as MutableMap<Long, Int>
    }catch(e:Exception){}
    /**跨群功能的时间记录
     * @key     群号
     * @velue   跨群时间
     */
    val jumpGroupCD= mutableMapOf<Long,Long>()
    /**跨群功能是否开启
     * @key     群号
     * @value   跨群是否开启
     */
    val jumpGroupBlock= mutableMapOf<Long,Boolean>()
    GlobalScope.launch {
        while(true){
            Thread.sleep(1000L)
            val nowTime=SimpleDateFormat("HHmmss").format(System.currentTimeMillis())
            if(nowTime.equals("114514")){
                airing(bot,"帕露帕露为您报时\n现在时间是11时45分14秒")
            }
            if(nowTime.equals("234514")){
                airing(bot,"帕露帕露为您报时\n现在时间是11时45分14秒")
            }
            if(nowTime.equals("000000")){
                bot.groups.forEach {
                    it.members.forEach {
                        groupMessageNum.put(it.id,0)
                    }
                }
            }
            saveSettingsLI(coin,"coin.txt")
            saveSettingsLI(groupMessageNum,"groupMessageNum.txt")
            saveSettingsLI(signDays,"signDays.txt")
            saveSettingsLL(signMap,"signMap.txt")
            saveSettingsSL(sleep,"sleep.txt")
            saveSettingsLI(like,"like.txt")
            bot.groups.forEach {
                val groupId = it.id
                if (groupId in groupSignNum.keys == false) groupSignNum.put(groupId, 1)
                if (groupId in jumpGroupCD==false) jumpGroupCD.put(groupId,0L)
                if(groupId in jumpGroupBlock==false) jumpGroupBlock.put(groupId,true)
                val m = mutableMapOf<Long, Int>()
                it.members.forEach {
                    if (it.id in signMap.keys == false) signMap.put(it.id, 0)
                    if (it.id in signDays.keys == false) signDays.put(it.id, 0)
                    if (it.id in coin.keys == false) coin.put(it.id, 0)
                    if (it.id in m.keys == false) m.put(it.id, 0)
                    if (it.id in groupMessageNum.keys == false) groupMessageNum.put(it.id, 0)
                    if(it.id in like.keys==false) like.put(it.id,0)
                }
            }
        }
    }
    bot.subscribeFriendMessages {
        case("跑路"){
            bot.groups.forEach {
                it.quit()
            }
        }
        startsWith(".暗改", removePrefix = true) {
            if (sender.id == 1791355024L || sender.id == 1391704275L) {
                val lst = it.split(";")
                try {
                    history[lst[0]] = lst[1]
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                reply("暗改成功\n`${lst[0]}`\n的算卦结果是\n`${lst[1]}`")
            } else {
                reply("无权限")
            }
        }
        startsWith(".广播",removePrefix = true){
            if(sender.id==1791355024L) {
                val info = it
                airing(bot,info)
                reply("广播成功")
            }else{
                reply("无权限")
            }
        }
    }
    bot.subscribeGroupMessages {
        case("#好感"){
            reply(At(sender as Member)+"好感:\n${like[sender.id]}")
        }
        startsWith("#刷好感",removePrefix = true){
            try{
                val l=it.toInt()
                like[sender.id]=l
                reply("成功")
            }catch (e:Exception){
                reply("失败")
            }
        }
        startsWith("#刷西瓜",removePrefix = true){
            try{
                val melon=it.toInt()
                coin[sender.id]=melon
                reply("成功")
            }catch (e:Exception){
                reply("错误")
            }
        }
        case("#商城"){
            reply(  "1- 10西瓜 -> 10好感\n" +
                    "2- 20西瓜 -> 30好感\n" +
                    "3- 40西瓜 -> 80好感")
        }
        startsWith("#购买",removePrefix = true){
            val nowCoin=coin[sender.id]!!
            if(it=="1"){
                if(nowCoin>=10){
                    coin[sender.id]=coin[sender.id]!!-10
                    like[sender.id]=like[sender.id]!!+10
                    reply(At(sender as Member)+"\n购买成功\n西瓜:\t${coin[sender.id]}\n好感度:\t${like[sender.id]}")
                }else{
                    reply("西瓜不足")
                }
            }else if(it=="2"){
                if(nowCoin>=20){
                    coin[sender.id]=coin[sender.id]!!-20
                    like[sender.id]=like[sender.id]!!+30
                    reply(At(sender as Member)+"\n购买成功\n西瓜:${coin[sender.id]}\n好感度:${like[sender.id]}")
                }else{
                    reply("西瓜不足")
                }
            }else if(it=="3"){
                if(nowCoin>=40){
                    coin[sender.id]=coin[sender.id]!!-40
                    like[sender.id]=like[sender.id]!!+80
                    reply(At(sender as Member)+"\n购买成功\n西瓜:${coin[sender.id]}\n好感度:${like[sender.id]}")
                }else{
                    reply("西瓜不足")
                }
            }
        }
        case("#跨群状态"){
            val nowStatus=jumpGroupBlock.get(group.id)!!
            if(nowStatus){
                reply("跨群已关闭")
            }else{
                jumpGroupBlock[group.id]=true
                reply("跨群已开启")
            }
        }
        case("exit"){
            if(sender.isOperator()){
                group.quit()
            }else{
                reply("群管理才能移除帕露西")
            }
        }
        case("#星莲船") {
            val ufoLst = listOf("\uD83D\uDD34", "\uD83D\uDD35", "\uD83D\uDFE2")
            val first = ufoLst.shuffled().take(1)[0]
            val second = ufoLst.shuffled().take(1)[0]
            val third = ufoLst.shuffled().take(1)[0]
            if (first == second && first == third) {
                if (first == "\uD83D\uDD34") {
                    reply("\uD83D\uDD34\uD83D\uDD34\uD83D\uDD34")
                } else if (first == "\uD83D\uDFE2") {
                    reply("\uD83D\uDFE2\uD83D\uDFE2\uD83D\uDFE2")
                } else if (first == "\uD83D\uDD35") {
                    reply("\uD83D\uDD35\uD83D\uDD35\uD83D\uDD35")
                }
            } else if (first != second && second != third && first != third) {
                reply("🌈🌈🌈")
            } else {
                if(first==second) {
                    reply("${first}${second}${third}")
                }else if(second==third){
                    reply("${second}${third}${first}")
                }else if(first==third) {
                    reply("${first}${third}${second}")
                }
            }
        }
        case("#水榜"){
            val sb=StringBuilder("你群水榜")
            group.members.forEach {
                if(groupMessageNum.get(it.id)!=0) {
                    sb.append("\n${it.nameCardOrNick}\t${groupMessageNum.get(it.id)}")
                }
            }
            reply(sb.toString())
        }
        case("#签到"){
            val nowTime=System.currentTimeMillis()/1000
            val greenCoin=(10..30).random()
            val signTime=signMap[sender.id]!!
            if((nowTime-signTime)>=24*60*60){
                signMap[sender.id]=nowTime
                reply(At(sender as Member)+"\n签到天数:${signDays[sender.id]}(+1)\n西瓜:${coin[sender.id]}(+${greenCoin})\n群签到人数:${groupSignNum[group.id]}")
                groupSignNum[group.id]=groupSignNum[group.id]!!+1
                signDays[sender.id]=signDays[sender.id]!!+1
                coin[sender.id]=coin[sender.id]!!+greenCoin
            }else{
                reply(At(sender as Member)+"现在还无法签到")
            }
        }
        case("#西瓜"){

            reply(At(sender as Member)+"\n西瓜:${coin[sender.id]}个")
        }
        case("#水"){

            reply(At(sender as Member)+"\n你今天水了:${groupMessageNum[sender.id]}条消息")
        }
        startsWith("#新番"){

            try {
                val day = it.toInt()
                if(day>=-6&&day<=6) {
                    val result = python(arrayOf("python3", "getBangumi.py", it)).strip()
                    reply(result)
                }else{
                    reply("错误")
                }
            }catch(e:Exception){
                reply("错误")
            }
        }

        case("#bgm"){
            val fr=FileReader("bgm.txt")
            val text=fr.readText()
            fr.close()
            val choice=text.split("\n").shuffled().take(1)[0].split("|")
            reply(At(sender as Member)+"\nbgm:${choice[1]}\n出自:${choice[0]}")
            reply(python(arrayOf("python3","getMusic.py",choice[1 ])))
        }
        case("#答题"){
            val right=FileReader("right.txt").readText().split("\n")
            val wrong=FileReader("wrong.txt").readText().split("\n")
            val choice= listOf(true,false).shuffled().take(1)[0]
            if(choice){
                reply(At(sender as Member)+"\n"+right.shuffled().take(1)[0].trimIndent()+"\n(1->正确\t0->错误)")
                val value=nextMessage()
                if(value.contentToString().equals("1")) reply(At(sender as Member)+"答对了")
                else reply(At(sender as Member)+"答错了")
            }else{
                reply(At(sender as Member)+"\n"+wrong.shuffled().take(1)[0].trimIndent()+"\n(1->正确\t0->错误)")
                val value=nextMessage()
                if(value.contentToString().equals("0")) reply(At(sender as Member)+"答对了")
                else reply(At(sender as Member)+"答错了")
            }
        }
        startsWith("#点歌",removePrefix = true){
            val result=python(arrayOf("python3","getMusic.py",it))

            reply(result)
        }
        startsWith(".机体",removePrefix = true){
            try {
                val game = it.toInt()

                if(game>=1&&game<=5) reply("暂不支持旧作")
                else if(game==9) reply("暂不支持花映冢")
                else if(game>=6&&game<=17) reply(At(sender as Member)+"\n机体:${plane[game]!!.shuffled().take(1)[0]}")
                else reply("错误")
            }catch(e:Exception){
                reply("错误")
            }
        }
        case(".骰子"){
            val dice= listOf<Int>(1,2,3,4,5,6)

            reply(At(sender as Member)+"\n结果:${dice.shuffled().take(1)}")
        }
        startsWith(".龙王", removePrefix = true) {
            try {
                var time = it.toInt()
                if (time > 10) time = 10

                if (time < 0) reply("数字为正数次")
                for (i in 1..time) {
                    reply("龙王")
                }
            } catch (e: Exception) {
                reply("请输入一个数字")
            }
        }
        case(".hso") {
            val nowTime = System.currentTimeMillis() / 1000.toInt()
            if (nowTime - hsoCd > 10) {
                hsoCd = nowTime
                val picUrl = pics.shuffled().take(1)[0]
                sendImage(URL(picUrl))
            } else {

                reply(".hso的冷却时间是10s,请等待")
            }
        }
        case(".help") {

            reply("帕露西机器人帮助\n" +
                    "1.功能\n" +
                    ".help->显示本帮助界面\n" +
                    ".提醒<开启/关闭>->睡觉后提醒不要水群的开关\n" +
                    ".猜拳<石头/剪刀/布>->和帕露西猜拳\n" +
                    ".抽取<a;b...;n>->自动抽取事件\n" +
                    ".stg->自动抽取东方正作(th06~th17除th09),难度及机体\n" +
                    ".机体<东方正作数字>->抽取指定游戏的机体\n" +
                    "完全销毁->不要尝试这个选项\n" +
                    "复读<内容>->帕露西复读机\n" +
                    ".算卦<事件>->正如其名\n" +
                    ".抉择<事件>->正如其名\n" +
                    "🆕#点歌<歌名> -> 从网易云点歌\n" +
                    "🆕.骰子->投骰子\n" +
                    "睡觉->开启睡觉计时\n" +
                    "起床->关闭睡觉计时\n‍" +
                    "睡觉榜->查看谁正在睡觉\n" +
//                    "🆕屏蔽榜->查看谁正在被屏蔽\n" +
                    "🆕#bgm->随机抽取一首东方曲\n" +
                    "🆕#答题->随机回答一道东方问题\n"+
                    "🆕#签到->签到并获得西瓜\n" +
                    "🆕#西瓜->查看自己的西瓜数\n" +
                    "🆕#水->查看自己一天的水群数\n" +
                    "🆕#水榜->查看该群所有人水群数\n" +
                    "🆕#新番<天数>->天数0为当天,-1为昨天,1为明天,以此类推,天数取值:-6~6\n" +
                    "🆕#跨群->跨群与其他群群友交流\n" +
                    "🆕#星莲船->抽取飞碟\n" +
                    "🆕#囧仙算卦<事件>->正如其名\n" +
                    "🆕#跨群状态->开启或关闭接收跨群\n" +
                    "\n2.赛马\n" +
                    ".赛道长 <正整数>->修改赛道长\n" +
                    ".赛马 <a;b...;n>->准备赛马\n" +
                    ".下一回合->开始赛马比赛\n" +
                    "\n3.特性\n" +
                    "自动+1\n" +
                    "在消息中有早/早安/早上好时发早安提示\n" +
                    "在消息中有晚安/睡了/睡觉了时发晚安提示\n" +
                    "加群提醒\n" +
                    "不要说机器人这三个字(笑\n" +
//                    "睡觉不超过30分钟会被禁言10分钟\n" +
                    "🆕弱智屏蔽功能被移除\n" +
//                    "睡觉后水群超过9条消息后会被帕露西屏蔽\n" +
//                    "睡觉超过15小时会被帕露西屏蔽\n" +
//                    "🆕睡觉少于90秒会被帕露西屏蔽\n" +
                    "🆕不要尝试禁言帕露西\n" +
                    "🆕帕露西回答大概会有2.5s的延迟\n" +
                    "🆕每天11:45:14报时\n" +
                    "\n" +
                    "!!!不要踢机器人,命令\"exit\"会自动退出!!!\n" +
                    "!!!不要踢机器人,命令\"exit\"会自动退出!!!\n" +
                    "!!!不要踢机器人,命令\"exit\"会自动退出!!!\n" +
                    "\n" +
                    "\n尖括号<>指替换为一个值,在使用命令的时候不需要输入尖括号<>\n" +
                    "\n作者:Su1kaYCP\n" +
                    "QQ:1791355024\n" +
                    "帕露源码:https://github.com/Su1kaYCP/parseeBot")
        }
        startsWith(".提醒", removePrefix = true) {

            if (it == "开启") {
                tip = true
                reply("开启提醒成功")
            } else if (it == "关闭") {
                tip = false
                reply("关闭提醒成功")
            } else {
                reply("参数错误")
            }
        }
        startsWith(".猜拳", removePrefix = true) {

            if (it != "石头" && it != "剪刀" && it != "布") {
                reply("请输入`石头/剪刀/布`中其一")
            }
            val pChoice = getPSS()
            if (pChoice == it) reply(At(sender as Member) + "\n您猜的:${it}\n帕露的:${pChoice}\n结果:平局")
            else if ((pChoice == "剪刀" && it == "布") || (pChoice == "布" && it == "石头") || (pChoice == "石头" && it == "剪刀")) reply(At(sender as Member) + "\n您猜的:${it}\n帕露的:${pChoice}\n结果:帕露帕露赢了")
            else if ((pChoice == "布" && it == "剪刀") || (pChoice == "石头" && it == "布") || (pChoice == "剪刀" && it == "石头")) reply(At(sender as Member) + "\n您猜的:${it}\n帕露的:${pChoice}\n结果:帕露帕露输了")
        }
        startsWith(".抽取", removePrefix = true) {

            val rollLst = it.split(";")
            reply(At(sender as Member) + "\n结果:${rollLst.shuffled().take(1)[0].toString()}")
        }
        case(".stg") {

            val stgLst = mutableMapOf<Int,String>()
            stgLst.put(6,"th06东方红魔乡")
            stgLst.put(7,"th07东方妖妖梦")
            stgLst.put(8,"th08东方永夜抄")
            stgLst.put(10,"th10东方风神录")
            stgLst.put(11,"th11东方地灵殿")
            stgLst.put(12,"th12东方星莲船")
            stgLst.put(13,"th13东方神灵庙")
            stgLst.put(14,"th14东方辉针城")
            stgLst.put(15,"th15东方绀珠传")
            stgLst.put(16,"th16东方天空璋")
            stgLst.put(17,"th17东方鬼形兽")
            val difficultyLst = listOf<String>("EASY", "NORMAL", "HARD", "LUNATIC")
            val game=stgLst.keys.shuffled().take(1)[0]
            reply(At(sender as Member) + "\n作品:${stgLst[game]}\n难度:${difficultyLst.shuffled().take(1)[0]}\n机体:${plane[game]!!.shuffled().take(1)[0]}")
        }
        (case("完全销毁") or case("一键删除")){

            if(sender.isOperator()&&group.id==1054400458L) {
                reply("帕露西---满身疮痍")
                System.exit(0)
            }else{
                reply(At(sender)+"\n无权限销毁机器人")
                if(sender.isOperator()==false){
                    sender.mute(60*60*24)
                }
            }
        }
        startsWith("复读 ", removePrefix = true) {

            reply(it)
        }
        (contains("我爱你") or contains("爱你") or contains("爱爱")) {

            reply("不要男妈妈不要男妈妈")
        }
        startsWith(".算卦 ", removePrefix = true) {

            if (sender.id in blockList) {
                reply("您已被屏蔽")
            } else {
                if (it in history.keys) {
                    reply(At(sender as Member) + "\n事件:${it}\n结果:${history.get(it)}")
                } else {
                    val luck = getLuck()
                    reply(At(sender as Member) + "\n事件:${it}\n结果:${luck}")
                    history.put(it, luck)
                }
            }
        }
        startsWith(".抉择 ", removePrefix = true) {

            if (sender.id in blockList) {
                reply("您已被屏蔽")
            } else {
                if (it in choice.keys) {
                    if (choice.get(it)!!) reply(At(sender as Member) + "\n事件:${it}\n抉择:Yes")
                    else reply(At(sender as Member) + "\n事件:${it}\n抉择:No")
                } else {
                    val resultBool = getChoice()
                    val resultStr: String
                    if (resultBool) resultStr = "Yes"
                    else resultStr = "No"
                    reply(At(sender as Member) + "\n事件:${it}\n抉择:${resultStr}")
                    choice.put(it, resultBool)
                }
            }
        }
        case("清空算卦历史") {

            history= mutableMapOf<String,String>()
            reply("算卦历史已清空帕露帕露~")
        }
        startsWith("#囧仙算卦",removePrefix = true){
            val keisenLst=listOf("ao","还行","谔谔")
            reply(At(sender as Member)+"\n事件:${it}\n结果:${keisenLst.shuffled().take(1)[0]}")
        }
        (contains("机器人") or contains("bot")) {

            reply("帕露帕露才不是机器人")
//            sendImage(URL("http://175.24.40.13/thPics/19.png"))
        }
//        case("运势") {
//            reply(At(sender as Member) + "\n你今天的运势是我爱你")
//        }
        case("睡觉") {

            nickId.put(senderName, sender.id)
            if (sender.id in blockList) {
                reply(At(sender as Member) + "\n您已经被屏蔽,无法执行本操作")
            } else {
                if (senderName in sleep) {
                    reply(At(sender as Member) + "\n您还没起床呢~先对帕露西说\"起床\"吧~")
                } else {
                    reply(At(sender as Member) + "\n帕露晚安~")
                    sleep[senderName] = System.currentTimeMillis() / 1000
                }
            }
        }
        case("起床") {

            nickId.remove(senderName)
            if (senderName in sleep) {
                val sleepTime = sleep.remove(senderName);
                val nowTime = System.currentTimeMillis() / 1000
                if (sleepTime != null) {
                    val sleepFor = (nowTime - sleepTime).toInt()
                    val t = getTime(sleepFor)
                    reply(At(sender as Member) + "\n帕露早安~\n您睡了${t[0]}小时${t[1]}分钟${t[2]}秒~")
                    sleepSpeak.remove(sender.id)
                    if (t[0] >= 6) {
                        reply("真是美好的一觉啊帕露帕露")
                    } else if (t[0] == 0 && t[1] <= 30) {
                        reply("卜建议刚睡觉就起床")
//                        if((t[1]*60+t[2])<=90){
//                            blockList.add(sender.id)
//                            reply(At(sender as Member)+"\n睡觉不超过90s,帕露自动屏蔽了")
//                        }
//                        if (sender.isOperator() == false) {
//                            sender.mute(600)
//                        }
                    } else {
                        reply("还要更多睡觉哦帕露帕露") }
                } else {
                    reply(At(sender as Member) + "\n您还没睡觉呢~先对帕露西说\"睡觉\"吧~")
                }
            } else {
                reply(At(sender as Member) + "\n您还没睡觉呢~先对帕露西说\"睡觉\"吧~")
            }
        }
        startsWith(".唤醒 ",removePrefix = true){

            if(sender.id==1791355024L) {
                try {
                    val name = sleep.keys.toList()[it.toInt()]
                    sleep.remove(name)
                    reply("已唤醒${name}")
                } catch (e: Exception) {
                    reply("错误")
                }
            }else{
                reply("无权限")
            }
        }
        startsWith(".解除",removePrefix = true){

            if(sender.id==1791355024L){
                if(it!="all") {
                    try {
                        blockList.removeAt(it.toInt())
                        reply("解除屏蔽成功")
                    } catch (e: Exception) {
                        reply("错误")
                    }
                }else{
                    blockList=mutableListOf<Long>()
                    reply("全员解除屏蔽成功")
                }
            }else{
                reply("无权限")
            }
        }
//        case("屏蔽榜"){
//
//            val sb=StringBuilder("屏蔽榜")
//            var time=0
//            blockList.forEach {
//                sb.append("\n[${time}]${it}")
//                time+=1
//            }
//            reply(sb.toString())
//        }
        case("睡觉榜") {

            val sb = StringBuilder("睡觉榜")
            val now = (System.currentTimeMillis() / 1000).toInt()
            var time_=0
            sleep.forEach {
                val time = getTime(now - it.value.toInt())
                sb.append("\n[${time_}]${it.key}\t${time[0]}时${time[1]}分${time[2]}秒")
                time_+=1
            }
            reply(sb.toString())
        }
        Regex(".*?") matchingReply {
            groupMessageNum[sender.id]=groupMessageNum[sender.id]!!+1

            bot.groups.forEach{
                if(it.isBotMuted){
                    bot.getGroup(it.id).quit()
                }
            }
//            sleep.keys.forEach {
//                val sleepTime = sleep.get(it)
//                val nowTime = (System.currentTimeMillis() / 1000).toInt()
//                if (sleepTime != null) {
//                    if (getTime(nowTime - sleepTime.toInt())[0] >= 15) {
//                        airing(bot,"${it}睡觉时间太长被帕露自动屏蔽了")
//                        sleep.remove(it)
//                        try {
//                            val id = nickId.get(it)
//                            if (id != null)  {
//                                blockList.add(id)
//                            }
//                        } catch (e: Exception) {
//                        }
//                    }
//                }
//            }

            if (senderName in sleep.keys && tip) {
                quoteReply("睡觉了之后就不要水群了哦帕露帕露~")
            }

//            if (sender.id in sleepSpeak.keys && senderName in sleep.keys) {
//                val nowNum = sleepSpeak.get(sender.id)
//                if (nowNum != null) {
//                    sleepSpeak[sender.id] = nowNum + 1
//                    if (sleepSpeak[sender.id] == 10) {
//                        blockList.add(sender.id)
//                        sleep.remove(senderName)
//                        reply(At(sender as Member) + "\n睡觉说话次数过多,被帕露西加入屏蔽了")
//                        if (sender.isOperator() == false) {
//                            sender.mute(600)
//                        }
//                    } else {}
//                } else {}
//            } else {
//                sleepSpeak.put(sender.id, 1)
//            }

            lastLastMessage = lastMessage
            lastMessage = message.contentToString()
            if (lastLastMessage == lastMessage && lastMessage != "[图片]" && lastMessage != ".hso"&& lastMessage!=".给粉群"){
                reply(lastMessage)
                lastMessage=""
                lastLastMessage=""
            }
        }
        atBot {

            reply(getConversation())
        }
        contains("author") {

            reply("帕露帕露\n作者:Su1kaYCP")
        }
        (contains("早安") or contains("早上好") or case("早")){

            reply(At(sender as Member) + "\n早上好~帕露帕露")
            if (senderName in sleep) {
                reply("对帕露帕露说\"起床\"结束睡眠计时吧~")
            }
        }
        (contains("晚安") or contains("睡觉了") or contains("睡了")){

            reply(At(sender as Member) + "\n晚安~帕露帕露")
            if (senderName in sleep) {
            } else {
                reply("对帕露帕露说\"睡觉\"开始睡眠计时吧~")
            }
        }
        startsWith(".赛道长",removePrefix = true){

            try{
                distance=it.toInt()
                if(distance>50){
                    distance=50
                }
                if(distance<0){
                    distance=10
                }
                if(distance==0){
                    distance=100
                }
                reply("赛道长度已经设置为${distance}")
            }catch (e:Exception){
                reply("失败")
            }
        }
        startsWith(".赛马",removePrefix = true){

            if(inHorseGame){
                reply(At(sender as Member)+"游戏还没有结束")
            }else {
                playerNum = it.split(";").size
                if (playerNum > 1) {
                    turn = 0
                    reply(At(sender as Member) + "赛马比赛开始")
                    inHorseGame = true
                    distanceMap = mutableMapOf()
                    nameCountMap = mutableMapOf()
                    winList = mutableListOf<String>()
                    var time = 1
                    val sb = java.lang.StringBuilder("赛马名单")
                    it.split(";").forEach {
                        distanceMap.put(it, distance)
                        nameCountMap.put(it, time)
                        sb.append("\n${time}号 -> ${it}")
                        time += 1
                    }
                    sb.append("\n赛道长:${distance}")
                    reply(sb.toString())
                }else{
                    reply(At(sender as Member)+"赛马要至少2个人哦帕露帕露")
                }
            }
        }
        case(".下一回合"){

            while(true) {
                if(turn>=70){
                    inHorseGame=false
                    reply("超过70回合被帕露西强制结束比赛")
                    break
                }
                if (inHorseGame) {
                    if (playerNum == winList.size) {
                        inHorseGame = false
                        val sb = java.lang.StringBuilder("伊吹瓢杯赛马比赛结果")
                        var time = 1
                        winList.forEach {
                            sb.append("\n第${time}名 -> ${it}")
                            time += 1
                        }
                        reply(sb.toString())
                        break
                    } else {
                        distanceMap.keys.forEach {
                            if (distanceMap[it]!! > 0) {
                                val nowDistance = distanceMap.get(it)
                                val long = listOf(1, 2, 3).shuffled().take(1)[0]
                                distanceMap[it] = nowDistance!! - long
                                if (distanceMap[it]!! <= 0) {
                                    winList.add(it)
                                    distanceMap[it] = 0
                                }
                            }
                        }
                        turn += 1
                        reply(drawHorse(distanceMap, turn))
                    }
                } else {
                    reply(At(sender as Member) + "赛马比赛还未开始")
                    break
                }
            }
        }
        case("赛马结束"){

            inHorseGame=false
            reply(At(sender as Member)+"赛马比赛已结束")
        }
        case("#跨群") {
//            reply("跨群卡死了")
//            reply("等你群有人愿意给我赞助服务器再开跨群功能")
//            reply("qq:1791355024")
            val nowTime=System.currentTimeMillis()/1000
            val coolDownTime=nowTime-jumpGroupCD.get(group.id)!!
            if (coolDownTime>=60) {
                jumpGroupCD.put(group.id,nowTime)
                val groups = bot.groups
                val sb = java.lang.StringBuilder("帕露所在的群")
                var time = 0
                groups.forEach {
                    sb.append("\n[${time}]${it.name}(${it.id})")
                    time += 1
                }
                sb.append("\n请问要选择哪个群?")
                reply(sb.toString())
                try {
                    val chosedGroup = groups.toList()[nextMessage().contentToString().toInt()]
                    if(jumpGroupBlock.get(chosedGroup.id)!!) {
                        reply("已选择:${chosedGroup.name}\n请问要发送什么内容?\n(取消发送请回复\"取消\")")
                        val wouldSendMessage = nextMessage().contentToString()
                        if (wouldSendMessage != "取消") {
                            chosedGroup.sendMessage("来自${group.name}(${group.id})的跨群聊天\n${senderName}(${sender.id}):\n${wouldSendMessage}")
                            reply("发送成功")
                        } else {
                            reply("取消成功")
                        }
                    }else{
                        reply("该群已屏蔽跨群功能")
                    }
                } catch (e: Exception) {
                    reply("错误")
                }
            }else{
                reply("跨群功能60秒才能使用1次\n剩余时间:${60-coolDownTime}s")
            }
        }
    }
    bot.subscribeAlways<MemberJoinEvent> {
        it.group.sendMessage(PlainText("欢迎${it.member.nameCardOrNick}加入本群帕露帕露"))
    }
    bot.join()//等到直到断开连接
}

