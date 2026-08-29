package com.example.data.model

data class TimelineStoryItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val emoji: String,
    val tag: String
)

data class FriendshipQuoteItem(
    val id: Int,
    val quote: String,
    val authorTag: String = "Sani & Arafat Special",
    val emoji: String = "✨"
)

data class FunPollItem(
    val id: String,
    val question: String,
    val option1: String = "Sani",
    val option2: String = "Arafat",
    val emoji: String
)

data class FriendshipPromiseItem(
    val text: String,
    val emoji: String
)

object FriendshipDataDefaults {

    val timelineStories = listOf(
        TimelineStoryItem(
            id = "start",
            title = "Amader bondhutter shuru",
            subtitle = "2023–2024 er majhamaji somoy",
            description = "Ekta sadharan din theke shuru hoyechilo amader ei oshadharan bondhutto. Keu bhabini je ekdin ei porichoy amader jiboner sobcheye boro shomporko hoye darabe.",
            emoji = "🌱",
            tag = "Shuruat"
        ),
        TimelineStoryItem(
            id = "intro",
            title = "Prothom porichoy",
            subtitle = "Kichu obak muhurto ar heshe kotha bola",
            description = "Prothom jokhoni kotha hoyechilo, Arafat-er simple ar friendly shobhab dekhe mon chuye gechilo. Kono show-off nai, ekdom shohoj-shorol moner manush.",
            emoji = "👋",
            tag = "First Meet"
        ),
        TimelineStoryItem(
            id = "first_fun",
            title = "Prothom moja",
            subtitle = "Pani-r moto hashi ar anondo",
            description = "Duijon mile eto beshi moja ar hasha-hashi korlam je ashepasher manush obak hoye takiye chilo! Sheidin bujhechilam, ei bondhu ta ekdom amr type-er pagol.",
            emoji = "🤣",
            tag = "Masti"
        ),
        TimelineStoryItem(
            id = "big_fight",
            title = "Prothom boro jhogra",
            subtitle = "Maan-oviman ar chotto durutto",
            description = "Ektukhani vul bujhabujhi te amader moddhe boro jhogra hoye gechilo. Koyekdin kotha bondho chilo, kintu mone mone ekjon arekjon k khub miss korchilam.",
            emoji = "⚡",
            tag = "Jhogra"
        ),
        TimelineStoryItem(
            id = "reunion",
            title = "Abar mile jawa",
            subtitle = "Rag bhule abar pashe asha",
            description = "Ego fele diye jokhoni abar kotha shuru holo, mone holo bondhutto ta age thekeo double shoktishali hoye geche. Karur sathe karur rag beshidin thakeni.",
            emoji = "🫂",
            tag = "Bondhutto"
        ),
        TimelineStoryItem(
            id = "today",
            title = "Ajker amader bondhutto",
            subtitle = "Beshkoyek bochorer biswash ar bhalobasha",
            description = "Aj amra bujhi je jotoi jhor-tufan ashuk, Sani ar Arafat-er bondhutto kokhono bhangar noy. Ekta bondhutto, hajar ta golpo, ar shamne aro onek poth baki!",
            emoji = "💎",
            tag = "Forever"
        )
    )

    val banglishQuotes = listOf(
        FriendshipQuoteItem(
            1,
            "Bondhu mane sudhu pashe thaka na, jhogra-r por abar pashe fire asha.",
            emoji = "❤️"
        ),
        FriendshipQuoteItem(
            2,
            "Amader golpo te jhogra ache, rag ache, kintu sesh page-e sobsomoy bondhuttoi thake.",
            emoji = "📖"
        ),
        FriendshipQuoteItem(
            3,
            "Dunia jotoi bodle jak na keno, Sani ar Arafat-er bondhutto kokhono purono hobe na.",
            emoji = "✨"
        ),
        FriendshipQuoteItem(
            4,
            "Bhalo bondhu pawa bhagyer bapar, ar tor moto pagal bondhu pawa ekdom jackpot!",
            emoji = "🎯"
        ),
        FriendshipQuoteItem(
            5,
            "Koto manush ashe jay, kintu ashol bondhu shei je jotoi bipod ashuk pashe daray.",
            emoji = "🛡️"
        ),
        FriendshipQuoteItem(
            6,
            "Amader bondhutto perfect na hote pare, kintu eta 100% pure ar real.",
            emoji = "💎"
        ),
        FriendshipQuoteItem(
            7,
            "Jotoi dure thaki na keno, moner connect sob somoy same thakbe.",
            emoji = "🕊️"
        )
    )

    val friendshipPromises = listOf(
        FriendshipPromiseItem("Jotoi jhogra hok, bondhutto chere dibo na.", "🤝"),
        FriendshipPromiseItem("Jotoi dure jai, jogajog rakhar chesta korbo.", "📞"),
        FriendshipPromiseItem("Purono memories kokhono vulbo na.", "📸"),
        FriendshipPromiseItem("Ekjon arekjoner bhalo somoy-eo thakbo, kharap somoy-eo thakbo.", "🌈"),
        FriendshipPromiseItem("Jei kono bipode shobar age dhowre ashbo.", "⚡"),
        FriendshipPromiseItem("Future-e jotoi boro hoi, ager motoi adda dibo.", "☕")
    )

    val funnyMessages = listOf(
        "Arafat: Bhai tor moto pagol ami duniyay second ta dekhini! 😂",
        "Sani: Tor sathe kotha na bolle din tai incomplete mone hoy, pagla! 🤪",
        "Jhogra korar por 5 minute o thaka jay na, abar kotha bola shuru hoye jay! 🤭",
        "Bondhu emon howa chai, je jhogra-r poreo bole—'Oi, cha er bill ta kintu tor!' ☕",
        "Amader bondhutto holo: 10% gyan, 20% jhogra, ar 70% shudhu moja ar faizlami! 🤣",
        "Arafat-er shobcheye boro superpower holo: ghum theke uthe abar ghumate jawa! 😴",
        "Sani jokhon phone dhore na, bujhte hobe kono serious game khelche! 🎮"
    )

    val randomQuestions = listOf(
        "Amader bondhutter shobcheye funny moment konta chilo?",
        "Jodi amra duijon kono trip-e jai, shobar age ke hariye jabe?",
        "Amader moddhe ke beshi emotional ar ke beshi practical?",
        "Tor mone ache sei diner kotha jokhon amra prothom cha kheyechilam?",
        "Future-e 10 bochor por amra nijeder kothay dekhte chai?",
        "Konta amader bondhutter shobcheye boro shokti bole mone hoy?"
    )
}
