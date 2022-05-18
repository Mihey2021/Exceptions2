package data

data class Complain(
    val ownerId: Int,
    val commentId: Int,
    val reason: ReasonsComplain? = null
)

enum class ReasonsComplain(
    val id: Int,
    val text: String
) {
    SPAM(0, "спам"),
    CHLDPRN(1, "детская порнография"),
    EXTREMISM(2, "экстремизм"),
    VIOLENCE(3, "насилие"),
    DRUGS(4, "пропаганда наркотиков"),
    ADULT(5, "материал для взрослых"),
    ABUSE(6, "оскорбление"),
    SUICIDE(8, "призывы к суициду");
}