package com.lob.appdenoticiasadm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.lob.appdenoticiasadm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.btPublicarNoticia.setOnClickListener {

            val titulo = binding.editTituloNoticia.text.toString()
            val noticia = binding.editNoticia.text.toString()
            val data = binding.editDataNoticia.text.toString()
            val autor = binding.editAutorNoticia.text.toString()

            if (titulo.isEmpty() || noticia.isEmpty() || data.isEmpty() || autor.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                salvarNoticia(titulo, noticia, data, autor)

            }
        }
    }

    private fun salvarNoticia(titulo: String, noticia: String, data: String, autor: String) {

        val mapNoticias = hashMapOf(
            "titulo" to titulo,
            "noticia" to noticia,
            "data" to data,
            "autor" to autor
        )
        db.collection("noticias").document("noticia")
            .set(mapNoticias).addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful) {
                    Toast.makeText(this, "Notícia publicada com sucesso!", Toast.LENGTH_SHORT).show()
                    limparCampos()
                }
            }.addOnFailureListener {

            }
    }
    //Limpar código apos execução
    private fun limparCampos() {
        binding.editTituloNoticia.setText("")
        binding.editNoticia.setText("")
        binding.editDataNoticia.setText("")
        binding.editAutorNoticia.setText("")
    }
}