package exceptions

class PostNotFoundException(msg: String) : RuntimeException(msg)

class CommentNotFoundException(msg: String) : RuntimeException(msg)

class ReasonNotFoundException(msg: String) : RuntimeException(msg)

