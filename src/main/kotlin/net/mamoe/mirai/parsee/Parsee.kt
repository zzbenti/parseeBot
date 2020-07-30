package net.mamoe.mirai.parsee

import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.join
import net.mamoe.mirai.message.data.*
import java.lang.Exception
import java.net.URL

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
            123123123L,
            "123123123"
    ) {
        fileBasedDeviceInfo("device.json") // 使用 "device.json" 保存设备信息
    }.alsoLogin()

    val sleep = mutableMapOf<String, Long>()
    val history = mutableMapOf<String, String>()
    val choice = mutableMapOf<String, Boolean>()
    var tip = true
    val sleepSpeak = mutableMapOf<Long, Int>();
    val blockList = mutableListOf<Long>();
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
    bot.subscribeFriendMessages {
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
                    ".stg->自动抽取东方正作(th06~th17)和难度\n" +
                    "完全销毁->不要尝试这个选项\n" +
                    "复读<内容>->帕露西复读机\n" +
                    ".算卦<事件>->正如其名\n" +
                    ".抉择<事件>->正如其名\n" +
                    "睡觉->开启睡觉计时\n" +
                    "起床->关闭睡觉计时\n‍" +
                    "睡觉榜->查看谁正在睡觉\n" +
                    "🆕屏蔽榜->查看谁正在被屏蔽" +
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
                    "睡觉不超过30分钟会被禁言10分钟\n" +
                    "睡觉后水群超过9条消息后会被帕露西屏蔽\n" +
                    "睡觉超过13小时会被帕露西屏蔽\n" +
                    "🆕睡觉少于90秒会被帕露西屏蔽\n" +
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
            val stgLst = listOf<String>(
                    "th06东方红魔乡",
                    "th07东方妖妖梦",
                    "th08东方永夜抄",
                    "th10东方风神录",
                    "th11东方地灵殿",
                    "th12东方星莲船",
                    "th13东方神灵庙",
                    "th14东方辉针城",
                    "th15东方绀珠传",
                    "th16东方天空璋",
                    "th17东方鬼形兽"
            )
            val difficultyLst = listOf<String>("EASY", "NORMAL", "HARD", "LUNATIC")
            reply(At(sender as Member) + "\n作品:${stgLst.shuffled().take(1)[0]}\n难度:${difficultyLst.shuffled().take(1)[0]}")
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
            history.keys.forEach() {
                history.remove(it)
            }
            reply("算卦历史已清空帕露帕露~")
        }
        contains("机器人") {
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
                        if((t[1]*60+t[0])<=90){
                            blockList.add(sender.id)
                            reply(At(sender as Member)+"\n睡觉不超过90s,帕露自动屏蔽了")
                        }
                        if (sender.isOperator() == false) {
                            sender.mute(600)
                        }
                    } else {
                        reply("还要更多睡觉哦帕露帕露")
                    }
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
                    blockList.forEach {
                        blockList.remove(it)
                    }
                }
            }else{
                reply("无权限")
            }
        }
        case("屏蔽榜"){
            val sb=StringBuilder("屏蔽榜")
            var time=0
            blockList.forEach {
                sb.append("\n[${time}]${it}")
                time+=1
            }
            reply(sb.toString())
        }
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
            sleep.keys.forEach {
                val sleepTime = sleep.get(it)
                val nowTime = (System.currentTimeMillis() / 1000).toInt()
                if (sleepTime != null) {
                    if (getTime(nowTime - sleepTime.toInt())[0] >= 13) {
                        airing(bot,"${it}睡觉时间太长被帕露自动屏蔽了")
                        sleep.remove(it)
                        try {
                            val id = nickId.get(it)
                            if (id != null) {
                                blockList.add(id)
                            }
                        } catch (e: Exception) {
                        }
                    }
                }
            }

            if (senderName in sleep.keys && tip) {
                quoteReply("睡觉了之后就不要水群了哦帕露帕露~")
            }

            if (sender.id in sleepSpeak.keys && senderName in sleep.keys) {
                val nowNum = sleepSpeak.get(sender.id)
                if (nowNum != null) {
                    sleepSpeak[sender.id] = nowNum + 1
                    if (sleepSpeak[sender.id] == 10) {
                        blockList.add(sender.id)
                        sleep.remove(senderName)
                        reply(At(sender as Member) + "\n睡觉说话次数过多,被帕露西加入屏蔽了")
                        if (sender.isOperator() == false) {
                            sender.mute(600)
                        }
                    } else {}
                } else {}
            } else {
                sleepSpeak.put(sender.id, 1)
            }

            lastLastMessage = lastMessage
            lastMessage = message.contentToString()
            if (lastLastMessage == lastMessage && lastMessage != "[图片]" && lastMessage != ".hso"){
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
        (contains("早安") or contains("早上好") or contains("早")){
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
    }
    bot.subscribeAlways<MemberJoinEvent> {
        it.group.sendMessage(PlainText("欢迎${it.member.nameCardOrNick}加入本群帕露帕露"))
    }
    bot.join()//等到直到断开连接
}
