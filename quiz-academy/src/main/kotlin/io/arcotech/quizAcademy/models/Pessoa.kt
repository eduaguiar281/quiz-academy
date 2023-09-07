package io.arcotech.quizAcademy.models

import jakarta.persistence.*

@Embeddable
class Pessoa private constructor(
    @Column(name = "pessoa_id")
    val id: Long,
    @Column(name = "pessoa_name")
    val name: String,
    @Column(name = "pessoa_email")
    val email: String?){
    companion object{
        fun create (id: Long, name: String, email: String?):Pessoa{
            return Pessoa(id, name, email)
        }
    }
}