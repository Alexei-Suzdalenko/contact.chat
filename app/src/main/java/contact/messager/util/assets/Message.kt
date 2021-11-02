package contact.messager.util.assets

data class Message(
    var time: String = "",
    var text: String = "",
    var url: String = "",
    var sender: String = "",
    var receiver: String = ""
)