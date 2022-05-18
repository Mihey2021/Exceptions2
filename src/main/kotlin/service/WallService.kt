package service

import data.*
import exceptions.*
import kotlin.jvm.Throws

object WallService {

    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var complains = emptyArray<Complain>()

    fun add(post: Post): Post {
        posts += post.copy(id = getLastPostId() + 1)
        println("Post added: ${posts.last()}")
        print("Attachments: ")
        if (post.attachments != null) {
            println()
            for (attachment in post.attachments) {
                when (attachment) {
                    is AttachmentPhoto -> {
                        println("✎ Фото | ${attachment.photo}")
                    }
                    is AttachmentDoc -> {
                        println("✎ Документ | ${attachment.doc}")
                    }
                    is AttachmentVideo -> {
                        println("✎ Видео | ${attachment.video}")
                    }
                    is AttachmentAudio -> {
                        println("♫ Аудио | ${attachment.audio}")
                    }
                    is AttachmentNote -> {
                        println("✎ Заметка | ${attachment.note}")
                    }
                }
            }

        } else println("<Вложений нет>")
        return posts.last()
    }

    fun update(post: Post): Boolean {
        var postUpdating = false
        for ((idx, currentPost) in posts.withIndex()) {
            if (post.id == currentPost.id) {
                posts[idx] = currentPost.copy(
                    id = post.id,
                    authorName = post.authorName,
                    content = post.content,
                    likes = post.likes
                )
                postUpdating = true
                println("Updating post with id ${post.id}: ${posts[idx]}")
            }
        }
        return postUpdating
    }

    @Throws(PostNotFoundException::class)
    fun createComment(comment: Comment): Boolean {
        var postFound = false
        for (post in posts) {
            if (post.id == comment.postId) {
                postFound = true
                comments += comment
                println("✔ Комментарий успешно добавлен к посту id: ${comment.postId} (${comment.text})")
            }
        }
        if (!postFound) {
            throw PostNotFoundException("✖ Невозможно добавить комментарий к посту: Поста с id ${comment.postId} не существует!")
        }

        return true
    }

    @Throws(ReasonNotFoundException::class, CommentNotFoundException::class)
    fun makeComplain(complain: Complain): Int {
        var commentIdFound = false;
        for (comment in comments) {
            if (comment.id == complain.commentId) {
                commentIdFound = true
            }
        }

        if (complain.reason == null) throw ReasonNotFoundException("✖ Невозможно подать жалобу: укажите причину!")

        if (!commentIdFound) throw CommentNotFoundException("✖ Невозможно подать жалобу (${complain.reason.text}, код причины: ${complain.reason.id}): Комменария с id ${complain.commentId} не существует!")

        complains += complain
        println("✔ Жалоба успешно подана (Комменарий id: ${complain.commentId}. Причина: ${complain.reason.text}, код причины: ${complain.reason.id})")

        return 1
    }

    fun getLastPostId(): Int {
        return if (posts.isEmpty()) 0 else posts.last().id
    }

    fun clearWall() {
        posts = emptyArray<Post>()
    }
}