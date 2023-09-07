package io.arcotech.quizAcademy.models

import jakarta.persistence.*

@Embeddable
class Fonte private constructor(
    @Column(name = "fonte_id")
    val id: Long,
    @Column(name = "fonte_descricao")
    val descricao: String,
    @Column(name = "fonte_arquivo")
    val arquivo: String? = null,
    @Column(name = "fonte_url")
    val url: String? = null,
) {

    companion object{
        fun create(id: Long, descricao: String, asset: String? = null, url:String? = null): Fonte{
            return Fonte(id, descricao, asset, url)
        }
    }
}