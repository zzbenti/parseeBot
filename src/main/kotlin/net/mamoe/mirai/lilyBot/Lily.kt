@file:Suppress("NAME_SHADOWING", "NAME_SHADOWING")

package net.mamoe.mirai.lilyBot

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.join
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.nextMessage
import net.mamoe.mirai.message.recallIn
import net.mamoe.mirai.message.sendImage
import java.io.*
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.sql.*

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
fun saveSettingsL(list: List<Long>,fileName:String){
    val file=File(fileName)
    val fw=FileWriter(file)
    list.forEach{
        fw.write("${it}\n")
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
        } catch (e: Exception) {}
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
        } catch (e: Exception) {}
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
fun readSettingsL(fileName:String):List<Long>{
    val file = File(fileName)
    val fr = FileReader(file).readText()
    val list= mutableListOf<Long>()
    fr.split("\n").forEach {
        try {
            list.add(it.toLong())
        } catch (e: Exception) {
        }
    }
    return list
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
        "输入命令即可,不用艾特哦",
        "呜呜呜别艾特了,直接输入命令就好了",
        "查看命令大全请输入\".help\""
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
fun printChess(ground:List<Int>):String{
    val sb=StringBuilder("莉莉杯下棋大赛\nABCDEFGHIJKLM")
    var time=0
    val column= arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    for(i in 1..ground.size){
        time+=1
        if(time==14)time=0
        if(time==0) sb.append("\n${column[(i-1)/13]}\t")
        if(ground[i-1]==0) sb.append("🈳️")
        if(ground[i-1]==1) sb.append("⚫")
        if(ground[i-1]==2) sb.append("⚪")
    }
    return sb.toString()
}
fun getBiliVideoInfo(url:String): List<String> {
    val result=python(arrayOf("python3","biliVideo.py",url))
    val info=result.split("\n")
    val strInfoBeforeImage="${info[0]}\n${info[1]}\n"
    val picUrl=info[6]
    val strInfoAfterImage="\nup:${info[3]}\n地址:${info[4]}\n简介:${info[2]}\n播放:${info[7]}\t点赞:${info[8]}\t投币:${info[9]}\t收藏:${info[10]}\t转发:${info[11]}"
    return listOf(strInfoBeforeImage,picUrl,strInfoAfterImage)
}
suspend fun main() {
    Class.forName("org.sqlite.JDBC")
    val bot = Bot(
            123123123L,
        "123123123"
    ) {
        fileBasedDeviceInfo("device.json") // 使用 "device.json" 保存设备信息
    }.alsoLogin()
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
//    val blockWords= listOf("台湾","香港","中国政府","习近平","独立","台独","港独","共产党","你国","政治敏感","邓小平","毛泽东","法轮")
    val blockWords=listOf("","")
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
    val stgResult= listOf("NO MISS","NO BOMB","NO MISS NO BOMB","9961","满身疮痍","混关","一面道中曲播放器","低封","高速封印","左右封印","上下封印","打分","避弹","LNN")
    /**
     * @key     qq号
     * @value   签到时的时间戳
     */
    var signMap= mutableMapOf<Long,Long>();
    try{
        signMap= readSettingsLL("signMap.txt") as MutableMap<Long, Long>
    }catch (e:Exception){}
    /**
     * @value   qq号
     */
    var signLst= mutableListOf<Long>()
    try{
        signLst= readSettingsL("signLst.txt") as MutableList<Long>
    }catch(e:Exception){}
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
    /**
     * @key     qq号
     * @value   昵称
     */
    val nickMap= mutableMapOf<Long,String>()
    val historyConn=DriverManager.getConnection("jdbc:sqlite:history.db")
    historyConn.autoCommit=false
    val s=historyConn.createStatement()
    val sql="select * from initedGroup;"
    val rs=s.executeQuery(sql)
    val initedGroupsId= mutableListOf<Long>()
    var chess=false
    while(rs.next()){
        initedGroupsId.add(rs.getString("groupID").toLong())
    }
    val bottles=arrayListOf<Message>()
    bot.groups.forEach {
        if(it.id in initedGroupsId==false){
            s.executeUpdate("create table Group${it.id}(`time` text,`qqId` text,`qqName` text,`message` text);")
            s.executeUpdate("insert into initedGroup values (\"${it.id}\");")
            historyConn.commit()
        }
    }
    rs.close()

    GlobalScope.launch {
        while(true){
            val nowTime=SimpleDateFormat("HHmmss").format(System.currentTimeMillis())
//            if(nowTime.equals("114514")){
//                airing(bot,"莉莉为您报时\n现在时间是11时45分14秒")
//            }
//            if(nowTime.equals("234514")){
//                airing(bot,"莉莉为您报时\n现在时间是11时45分14秒")
//            }
            if(nowTime.equals("000000")){
                bot.groups.forEach {
                    it.members.forEach {
                        groupMessageNum.put(it.id,0)
                    }
                }
                signLst= mutableListOf()
            }
            if(nowTime.equals("070000")){
                airing(bot,"春天来了哦~\n是时候起床了哦")
            }
            if(nowTime.equals("080000")){
                airing(bot,"春天来了哦~\n时间不早了快起床吧~")
            }
            if(nowTime.equals("230000")){
                airing(bot,"这么快就晚上了呢\n今天也做个好梦吧")
            }
            saveSettingsLI(coin,"coin.txt")
            saveSettingsLI(groupMessageNum,"groupMessageNum.txt")
            saveSettingsLI(signDays,"signDays.txt")
            saveSettingsLL(signMap,"signMap.txt")
            saveSettingsSL(sleep,"sleep.txt")
            saveSettingsLI(like,"like.txt")
            saveSettingsL(signLst,"signLst.txt")
            bot.groups.forEach {
                val groupId = it.id
                val group_=it
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
                    if(it.id==3137029792L) group_.quit()
                }
                if(it.id in initedGroupsId==false){
                    s.executeUpdate("create table Group${it.id}(`time` text,`qqId` text,`qqName` text,`message` text);")
                    s.executeUpdate("insert into initedGroup values (\"${it.id}\");")
                    historyConn.commit()
                }
            }
        }
    }
    bot.subscribeAlways<MemberJoinRequestEvent> {
        val nick=fromNick
        val id=fromId
        val req=message
        group.sendMessage("${nick}(${id})想加入本群\n申请消息:${req}")
    }
    bot.subscribeAlways<MemberLeaveEvent.Kick> {
        group.sendMessage("${member.nameCardOrNick}(${member.id})被${operatorOrBot.nameCard}橄榄了(悲")
    }
    bot.subscribeAlways<MemberLeaveEvent.Quit> {
        group.sendMessage("${member.nameCardOrNick}(${member.id})离开了(悲")
    }
    bot.subscribeAlways<BotInvitedJoinGroupRequestEvent> {
        bot.getGroup(1126808041).sendMessage("${invitorNick}(${invitorId})想把莉莉黑拉进${groupName}(${groupId})")
    }
//    bot.subscribeAlways<NewFriendRequestEvent> {
//        accept()
//    }
    bot.subscribeAlways<MemberPermissionChangeEvent> {
        if(new.isAdministrator()){
            bot.getGroup(group.id).sendMessage("@${member.nameCardOrNick}\n你管理有了")
        }else{
            bot.getGroup(group.id).sendMessage("@${member.nameCardOrNick}\n你管理无了")
        }
    }
    bot.subscribeFriendMessages {
        startsWith("#保存",removePrefix = true){
            val senderId=sender.id
            val thingName=it
            reply("请问要保存什么内容?\n(回复\"取消\"以取消保存)")
            val thingInfo=nextMessage().contentToString()
            if(thingInfo!="取消") {
                val c = DriverManager.getConnection("jdbc:sqlite:data.db")
                val s = c.createStatement()
                val sql = "insert into thing values(\"${senderId}\",\"${thingName}\",\"${thingInfo}\")"
                s.executeUpdate(sql)
                s.close()
                c.close()
                reply("保存成功")
            }else{
                reply("取消成功")
            }
        }
        case("#查询"){
            val c=DriverManager.getConnection("jdbc:sqlite:data.db")
            val s=c.createStatement()
            val sql="select * from thing where a=\"${sender.id}\""
            val rs=s.executeQuery(sql)
            val thingMap= mutableMapOf<String,String>();
            while (rs.next()){
                thingMap.put(rs.getString("b"),rs.getString("c"))
            }
            var time=0
            val sb=StringBuilder("你保存的信息")
            thingMap.keys.forEach{
                sb.append("\n[${time}]${it}")
                time+=1
            }
            sb.append("\n请问要选择哪个?")
            if(thingMap.keys.size>0) {
                reply(sb.toString())
                try {
                    val choice = nextMessage().contentToString().toInt()
                    val choiceThingName=thingMap.keys.toList().get(choice)
                    reply("\"${choiceThingName}\"的内容是:\n${thingMap.get(choiceThingName)!!}")
                } catch (e: Exception) {
                    reply("错误")
                }
            }else{
                reply("还没有保存的内容")
            }
        }
        startsWith("#pr18",removePrefix = true){
            try{
                val result=python(arrayOf("python3","pixiv.py","yes",it)).trimIndent()
                reply("莉莉帮你找到了这个")
                reply(result)
                sender.sendImage(URL(result))
            }catch (e:Exception){}
        }
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
            if(sender.id==1791355024L||sender.id==2248186038L) {
                val info = it
                airing(bot,info)
                reply("广播成功")
            }else{
                reply("无权限")
            }
        }
    }
    bot.subscribeGroupMessages {
        startsWith("/search",removePrefix = true){
            if(it.toString().split(" ")[0]=="东方"){
                reply("https://thwiki.cc/index.php?search=${java.net.URLEncoder.encode(it.replace("东方 ",""),"UTF-8")}")
            }else{
                var result=python(arrayOf("python3","wiki.py",it)).trimIndent().split("\n")
                result-=""
                if(result.size>0) {
                    val sb = StringBuilder("请问要访问哪个条目?")
                    var time = 0
                    result.forEach {
                        sb.append("\n[${time}]${it}")
                        time += 1
                    }
                    reply(At(sender as Member) + "\n${sb.toString()}")
                    try {
                        val info = result[nextMessage().contentToString().toInt()]
                        reply("莉莉帮你找到啦~\nhttps://wanweibaike.com/wiki-${java.net.URLEncoder.encode(info.replace(" ", "%20"),"UTF-8")}")
                    } catch (e: Exception) {
                        reply("错误")
                    }
                }else{
                    reply("莉莉好像并没有找到相关条目...")
                }
            }
        }
        startsWith("#wiki",removePrefix = true){
            var result=python(arrayOf("python3","wiki.py",it)).trimIndent().split("\n")
            result-=""
            if(result.size>0) {
                val sb = StringBuilder("请问要访问哪个条目?")
                var time = 0
                result.forEach {
                    sb.append("\n[${time}]${it}")
                    time += 1
                }
                reply(At(sender as Member) + "\n${sb.toString()}")
                try {
                    val info = result[nextMessage().contentToString().toInt()]
                    reply("莉莉帮你找到啦~\nhttps://wanweibaike.com/wiki-${java.net.URLEncoder.encode(info.replace(" ", "%20"),"UTF-8")}")
                } catch (e: Exception) {
                    reply("错误")
                }
            }else{
                reply("莉莉好像并没有找到相关条目...")
            }
        }
        (case("#bottle fly") or case("#bottle throw")){
            val coinNum=coin[sender.id]?:0
            if(coinNum>=3) {
                reply(At(sender as Member) + "\n莉莉黑帮你准备好了瓶子哟~\n请把要写在瓶子中的内容告诉莉莉")
                val info = nextMessage()
                bottles.add(info)
                reply(At(sender as Member) + "\n莉莉黑已经帮你原封不动地装进瓶子了!\n现在你的瓶子正在天空的花之都中飞翔!")
                coin[sender.id]=coinNum-3
            }else{
                reply("啊...\n你可能需要3个春点的力量才能让瓶子飞起来...")
            }
        }
        (case("#bottle num") or case("#bottle number")){
            reply("天上有多少瓶子呢...")
            reply("现在天上有${bottles.size}个瓶子等待您去探索!")
        }
        (case("#bottle get") or case("#bottle look")){
            if(bottles.size>0) {
                reply("(莉莉黑飞上了天空)\n要--接--住--哦--")
                reply("瓶子中记载着以下信息:")
                reply(bottles.removeAt((0..bottles.size-1).random()))
            }else{
                reply("天空中没有瓶子了...")
            }
        }
//        case("#结束下棋"){
//            if(chess){
//                chess=false
//                reply("已结束下棋")
//            }else{
//                reply("还没有开始下棋")
//            }
//        }
//        case("#下棋"){
//            if(chess){
//                reply("请等待下棋结束")
//            }else{
//                reply("请艾特出你的对手")
//                val friend=nextMessage { message.any(At) }[At]!!.asMember()
//                reply("已选择${friend.nameCard}作为对手")
//                chess=true
//                val ground=mutableListOf<Int>()
//                for(i in 1..13*13){
//                    ground.add(0)
//                }
//
//            }
//        }
        startsWith("#语音",removePrefix = true){
            python(arrayOf("python3","voice.py",it))

        }
        startsWith(".r",removePrefix = true){
            val pattern="\\d+d\\d+".toRegex()
            if(pattern.matches(it)){
                val num=it.split("d")[0].toInt()
                val men=it.split("d")[1].toInt()
                if (num>100){
                    reply("莉莉被你扔出的骰子淹没了")
                }else if(men>1000){
                    reply("哇!让我数数骰子有多少面先~1...2...")
                }else if(num<0||men<0){
                    reply("错误")
                }else{
                    var result=(1..men).random()
                    val sb=StringBuilder("${senderName}掷骰:\n${num}D${men}=${result}")
                    if(num!=1) {
                        for (i in 1..num - 1) {
                            val randoms = (1..men).random()
                            result += randoms
                            sb.append("+${randoms}")
                        }
                        sb.append("=${result}")
                    }
                    reply(sb.toString())
                }
            }
        }
        startsWith("#答案",removePrefix = true){
            val result= python(arrayOf("python3","answerBook.py")).trimIndent()
            reply(At(sender as Member)+"\n事件:${it}\n结果:${result}")
        }
        Regex("(http|https)://www\\.bilibili\\.com/video/.*")matching{
            val r= getBiliVideoInfo(it.split("?")[0])
            try {
                reply(r[0].toMessage().plus(uploadImage(URL(r[1]))).plus(r[2].toMessage()))
            }catch (e:Exception){
                reply("错误")
            }
        }
        Regex("[av|Av|aV|AV|bv|Bv|bV|bv].*?") matching {
            val url="https://www.bilibili.com/video/${it}"
            val r= getBiliVideoInfo(url)
            try {
                reply(r[0].toMessage().plus(uploadImage(URL(r[1]))).plus(r[2].toMessage()))
            }catch (e:Exception){
                reply("错误")
            }
        }
        (case("求则")){
//            reply("请输入你的ip地址")
//            var rightIp=true
//            val ip=nextMessage().contentToString()
//            val pattern="\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+".toRegex()
//            if(pattern.matches(ip)){
//                ip.split(":")[0].split(".").forEach{
//                    if((it.toInt()>=0&&it.toInt()<=255)==false){
//                        rightIp=false
//                    }
//                }
//                if((ip.split(":")[1].toInt()>=0&&ip.split(":")[1].toInt()<=65535)==false){
//                    rightIp=false
//                }
//
//                if(rightIp){
//                    airing(bot,"[求则]\n来自${senderName}(${sender.id})的ip地址\n${ip}")
//                }else{
//                    reply("ip格式错误")
//                }
//            }else{
//                reply("这不是一个ipv4地址")
//            }
            reply("求则功能已删除")
        }
        case("#一言"){
            val result=python(arrayOf("python3","hitokoto.py"))
            reply(At(sender as Member)+"\n${result.trimIndent()}")
        }
        Regex("\\d+分钟后提醒我.*?") matching {
            try {
                val time = it.split("分钟后提醒我")[0].toInt()
                val thing=it.split("分钟后提醒我")[1]
                reply(At(sender as Member)+"提醒创建成功")
                GlobalScope.launch {
                    delay((60*time*1000).toLong())
                    reply("[提醒]\n@${sender.nameCard}}\n现在该\"${thing}\"了哦")
                }
            }catch (e:Exception){
                reply("错误")
            }
        }
        Regex("\\d+小时后提醒我.*?") matching {
            try {
                val time = it.split("小时后提醒我")[0].toInt()
                val thing=it.split("小时后提醒我")[1]
                reply(At(sender as Member)+"提醒创建成功")
                GlobalScope.launch {
                    delay((60*time*1000*3600).toLong())
                    reply("[提醒]\n@${sender.nameCard}}\n现在该\"${thing}\"了哦")
                }
            }catch (e:Exception){
                reply("错误")
            }
        }
        case(".test"){
            val a:Message="1312313".toMessage()
            reply(a.plus(uploadImage(File("doremi.jpeg"))))
        }
        Regex("#.{6}")matching {
            val input=it.replace("#","").toCharArray()
            val rgb= listOf(String.format("0x%s%s",input[0],input[1]),String.format("0x%s%s",input[2],input[3]),String.format("0x%s%s",input[4],input[5]))
            python(arrayOf("python3","setu16.py",rgb[0],rgb[1],rgb[2]))
            group.sendImage(File("setu.png"))
        }
        startsWith("#setu",removePrefix = true){
            val rgb=it.split(";")
            if(rgb.size==3){
                try{
                    rgb.forEach {
                        it.toInt()
                    }
                    python(arrayOf("python3","setu.py",rgb[0],rgb[1],rgb[2]))
                    group.sendImage(File("setu.png"))
                }catch(e:Exception){
                    reply("错误")
                }
            }else{
                reply("请输入3个值")
            }
        }
        startsWith("#佛曰",removePrefix = true){
            reply("少女听佛诉说宇宙的奥秘中...")
            val result=python(arrayOf("python3","buddha.py","encode",it)).trimIndent()
            reply(result)
        }
        startsWith("新佛曰",removePrefix = false){
            reply("少女参悟佛所言的真谛中...")
            val result=python(arrayOf("python3","buddha.py","decode",it)).trimIndent()
            reply(result)
        }
        startsWith("#p",removePrefix = true){
            try{
                val result=python(arrayOf("python3","pixiv.py","no",it)).trimIndent()
                reply("莉莉帮你找到了这个")
                reply(result)
                group.sendImage(URL(result))
            }catch (e:Exception){}
        }
        startsWith("#莉莉搜图",removePrefix = true){
            val result= python(arrayOf("python3","pic.py",it)).trimIndent()
            reply(result)
            try{
                reply("莉莉帮你找到了这个\n".toMessage().plus(result+"\n").plus(uploadImage(URL(result))))
            }catch (e:Exception){
                reply("发送图片错误")
//                reply(e.printStackTrace().toString())
            }
        }
        case(".ping"){
            reply("我在线!并且有小白陪着我!")
        }
        case("exit"){
            if(sender.isOperator()) {
                group.quit()
            }else{
                reply("无权限")
            }
        }
        startsWith("#留言",removePrefix = true) {
            bot.getFriend(1791355024L).sendMessage("${senderName}(${sender.id}):\n${it}")
            reply("留言成功")
        }
        case("#好感"){
            reply(At(sender as Member)+"\n好感:${like[sender.id]}")
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
            reply(  "1- 10春点 -> 10好感\n" +
                    "2- 20春点 -> 30好感\n" +
                    "3- 40春点 -> 80好感")
        }
        startsWith("#购买",removePrefix = true){
            val nowCoin=coin[sender.id]!!
            if(it=="1"){
                if(nowCoin>=10){
                    coin[sender.id]=coin[sender.id]!!-10
                    like[sender.id]=like[sender.id]!!+10
                    reply(At(sender as Member)+"\n购买成功\n春点:\t${coin[sender.id]}\n好感度:\t${like[sender.id]}")
                }else{
                    reply("春点不足")
                }
            }else if(it=="2"){
                if(nowCoin>=20){
                    coin[sender.id]=coin[sender.id]!!-20
                    like[sender.id]=like[sender.id]!!+30
                    reply(At(sender as Member)+"\n购买成功\n春点:${coin[sender.id]}\n好感度:${like[sender.id]}")
                }else{
                    reply("春点不足")
                }
            }else if(it=="3"){
                if(nowCoin>=40){
                    coin[sender.id]=coin[sender.id]!!-40
                    like[sender.id]=like[sender.id]!!+80
                    reply(At(sender as Member)+"\n购买成功\n春点:${coin[sender.id]}\n好感度:${like[sender.id]}")
                }else{
                    reply("春点不足")
                }
            }
        }
        case("#跨群状态"){
            val nowStatus=jumpGroupBlock.get(group.id)!!
            if(nowStatus){
                jumpGroupBlock[group.id]=false
                reply("已屏蔽接受跨群消息")
            }else{
                jumpGroupBlock[group.id]=true
                reply("已开启接受跨群消息")
            }
        }
        case("exit"){
            if(sender.isOperator()){
                group.quit()
            }else{
                reply("群管理才能移除莉莉黑")
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
            val greenCoin=(10..30).random()
            if(sender.id in signLst==false){
                reply(At(sender as Member)+"\n签到天数:${signDays[sender.id]}(+1)\n春点:${coin[sender.id]}(+${greenCoin})\n群签到人数:${signLst.size+1}")
                groupSignNum[group.id]=groupSignNum[group.id]!!+1
                signDays[sender.id]=signDays[sender.id]!!+1
                coin[sender.id]=coin[sender.id]!!+greenCoin
                signLst.add(sender.id)
            }else{
                reply(At(sender as Member)+"现在还无法签到")
            }
        }
        case("#春点"){

            reply(At(sender as Member)+"\n春点:${coin[sender.id]}个")
        }
        case("#水"){

            reply(At(sender as Member)+"\n你今天水了:${groupMessageNum[sender.id]}条消息")
        }
        startsWith("#新番",removePrefix = true){
            try {
                val day = it.toInt()
                if(day>=-6&&day<=6) {
                    val result = python(arrayOf("python3", "getBangumi.py", it)).trimIndent()
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

            reply("莉莉Bot具体功能请查询:\nhttp://175.24.40.13/black.html\n如果将莉莉黑为管理(群主也行)则可以发送新成员入群申请等消息哦\n主人:莉莉黑\nQQ:2248186038\n莉莉的小群:1126808041")
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
            if (pChoice == it) reply(At(sender as Member) + "\n您猜的:${it}\n莉莉的:${pChoice}\n结果:平局")
            else if ((pChoice == "剪刀" && it == "布") || (pChoice == "布" && it == "石头") || (pChoice == "石头" && it == "剪刀")) reply(At(sender as Member) + "\n您猜的:${it}\n莉莉的:${pChoice}\n结果:莉莉黑赢了")
            else if ((pChoice == "布" && it == "剪刀") || (pChoice == "石头" && it == "布") || (pChoice == "剪刀" && it == "石头")) reply(At(sender as Member) + "\n您猜的:${it}\n莉莉的:${pChoice}\n结果:莉莉黑输了")
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
                reply("莉莉黑---满身疮痍")
                System.exit(0)
            }else{
                reply(At(sender)+"\n无权限销毁机器人")
                if(sender.isOperator()==false){
                    sender.mute(60*60*24)
                }
            }
        }
        startsWith("复读 ", removePrefix = true) {
            reply(it).recallIn(5*1000)
        }
//        (contains("我爱你") or contains("爱你") or contains("爱爱")) {
//
//            reply("不要男妈妈不要男妈妈")
//        }
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
            reply("算卦历史已清空")
        }
        startsWith("#囧仙算卦",removePrefix = true){
            val keisenLst=listOf("ao","还行","谔谔")
            reply(At(sender as Member)+"\n事件:${it}\n结果:${keisenLst.shuffled().take(1)[0]}")
        }
        (contains("莉莉黑") or contains("lily")){
            reply("莉莉黑爱你哟")
            sender.nudge()
            reply("无聊的时候听一会花之都吧~\nhttps://music.163.com/song?id=22636703")
        }
//        (contains("机器人") or contains("bot")) {
//
//            reply("帕露帕露才不是机器人")
////            sendImage(URL("http://175.24.40.13/thPics/19.png"))
//        }
//        case("运势") {
//            reply(At(sender as Member) + "\n你今天的运势是我爱你")
//        }
        case("睡觉") {

            nickId.put(senderName, sender.id)
            if (sender.id in blockList) {
                reply(At(sender as Member) + "\n您已经被屏蔽,无法执行本操作")
            } else {
                if (senderName in sleep) {
                    reply(At(sender as Member) + "\n您还没起床呢~先对莉莉黑说\"起床\"吧~")
                } else {
                    reply(At(sender as Member) + "\n莉莉晚安~")
                    reply("听着花都入睡吧~\nhttps://music.163.com/song?id=836396")
                    sleep[senderName] = System.currentTimeMillis() / 1000
                    group.sendImage(File(listOf("satori1.jpg","satori2.jpg","doremi.jpeg").shuffled().take(1)[0]))
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
                    reply(At(sender as Member) + "\n莉莉早安~\n您睡了${t[0]}小时${t[1]}分钟${t[2]}秒~")
                    reply("听会花都精神一下吧~\nhttps://music.163.com/song?id=28977339")
                    sleepSpeak.remove(sender.id)
                    if (t[0] >= 6) {
                        reply("真是美好的一觉啊")
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
                        reply("还要更多睡觉哦") }
                } else {
                    reply(At(sender as Member) + "\n您还没睡觉呢~先对莉莉黑说\"睡觉\"吧~")
                }
            } else {
                reply(At(sender as Member) + "\n您还没睡觉呢~先对莉莉黑说\"睡觉\"吧~")
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
        Regex("[\\s\\S]*?") matching {
            val nowTime=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
            val senderId=sender.id
            val senderName=sender.nameCard
            val info=message.contentToString()
            try {
                s.executeUpdate("insert into Group${group.id} values(\"${nowTime}\",\"${senderId}\",\"${senderName}\",\"${info}\");")
            }catch(e:Exception){
            }
            historyConn.commit()

//            val messageString=message.contentToString()
//            blockWords.forEach{
//                if(it in messageString){
//                    group.quit()
//                }
//            }

            groupMessageNum[sender.id]=groupMessageNum[sender.id]!!+1

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
                quoteReply("睡觉了之后就不要水群了哦")
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

//            lastLastMessage = lastMessage
//            lastMessage = message.contentToString()
//            if (lastLastMessage == lastMessage && lastMessage != "[图片]" && lastMessage != ".hso"&& lastMessage!=".给粉群"){
//                reply(lastMessage)
//                lastMessage=""
//                lastLastMessage=""
//            }
        }
        atBot {

            reply(getConversation())
        }
        contains("author") {

            reply("莉莉黑Bot\n主人:莉莉黑")
        }
//        (contains("早安") or contains("早上好") or case("早")){
//
//            reply(At(sender as Member) + "\n早上好~")
//            if (senderName in sleep) {
//                reply("对莉莉白说\"起床\"结束睡眠计时吧~")
//            }
//        }
//        (contains("晚安") or contains("睡觉了") or contains("睡了")){
//
//            reply(At(sender as Member) + "\n晚安~")
//            if (senderName in sleep) {
//            } else {
//                reply("对莉莉白说\"睡觉\"开始睡眠计时吧~")
//            }
//        }
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
                    reply(At(sender as Member)+"赛马要至少2个人哦")
                }
            }
        }
        case(".下一回合"){

            while(true) {
                if(turn>=70){
                    inHorseGame=false
                    reply("超过70回合被莉莉黑强制结束比赛")
                    break
                }
                if (inHorseGame) {
                    if (playerNum == winList.size) {
                        inHorseGame = false
                        val sb = java.lang.StringBuilder("LilyBlack杯赛马比赛结果")
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
        case(".结果"){
            if(inHorseGame) {
                inHorseGame = false
                val result = nameCountMap.keys.shuffled().take(nameCountMap.size)
                val sb = java.lang.StringBuilder("伊吹瓢杯赛马比赛结果")
                var time = 1
                result.forEach {
                    sb.append("\n第${time}名 -> ${it}")
                    time += 1
                }
                reply(sb.toString())
            }else{
                reply("赛马比赛还未开始")
            }
        }
        case("赛马结束"){

            inHorseGame=false
            reply(At(sender as Member)+"赛马比赛已结束")
        }
        case("#莉莉跨群") {
//            reply("跨群卡死了")
//            reply("等你群有人愿意给我赞助服务器再开跨群功能")
//            reply("qq:1791355024")
            val nowTime=System.currentTimeMillis()/1000
            val coolDownTime=nowTime-jumpGroupCD.get(group.id)!!
            if (coolDownTime>=60) {
                jumpGroupCD.put(group.id,nowTime)
                val groups = bot.groups
                val sb = java.lang.StringBuilder("Lily's Group List")
                sb.append("\n${getErrorCode()}")
                var time = 0
                groups.forEach {
                    sb.append("\nGroup${time}${it.name} ${getErrorCode()}")
                    time += 1
                }
                sb.append("\n${getErrorCode()}")
                sb.append("\nWhich Would You Like to Choose?")
                reply(sb.toString())
                try {
                    val chosedGroup = groups.toList()[nextMessage().contentToString().toInt()]
                    if(jumpGroupBlock.get(chosedGroup.id)!!) {
                        reply("You Chosed${chosedGroup.name}\nWhat Would You Like to Send\n(If You Want to Cancel Please Send \"cancel\")")
                        val wouldSendMessage = nextMessage()
                        if (wouldSendMessage.contentToString() != "Cancel") {
                            chosedGroup.sendMessage("来自${group.name}(${group.id})的跨群聊天\n${senderName}(${sender.id}):")
                            chosedGroup.sendMessage(wouldSendMessage)
                            reply("发送成功")
                        } else {
                            reply("取消成功")
                        }
                    }else{
                        reply("该群已屏蔽跨群功能")
                    }
                } catch (e: Exception) {
                    reply("错误")
                    reply(e.toString())
                }
            }else{
                reply("跨群功能60秒才能使用1次\n剩余时间:${60-coolDownTime}s")
            }
        }
    }
    bot.subscribeAlways<MemberJoinEvent> {
        it.group.sendMessage(PlainText("欢迎${it.member.nameCardOrNick}加入本群"))
    }
    bot.join()//等到直到断开连接
}
