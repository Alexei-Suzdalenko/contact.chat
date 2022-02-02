package contact.messager.util.assets

data class User(
    var id: String = "",
    var name: String = "",
    var image: String = "",
    var age: Int = 0,
    var latitude: Double = 0.0,
    val longitude: Double = 0.0
    )