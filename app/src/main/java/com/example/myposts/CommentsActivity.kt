package com.example.myposts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myposts.databinding.ActivityCommentsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsActivity : AppCompatActivity() {
    var postId = 0
    lateinit var binding: ActivityCommentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        obtainPostId()
        fetchPostById()
        fetchComments()
    }

    fun obtainPostId() {
        postId = intent.extras?.getInt("POST_ID") ?: 0
    }

    fun fetchPostById() {
        val apiClient = ApiClient.buildApiClient((ApiInterface::class.java))
        val request = apiClient.getPostById(postId)
        request.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    var post = response.body()
                    binding.tvPostId.text = post?.title
                    binding.tvPostsBody.text = post?.body
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(baseContext,t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    fun fetchComments() {
        var apiClient = ApiClient.buildApiClient(ApiInterface::class.java)
        var request = apiClient.getComments()

        request.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                var comment = response.body()
                if (response.isSuccessful) {
                    Log.d("TAG", comment.toString())
                    comment?.let { displayComment(it) }
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Toast.makeText(baseContext,t.message,Toast.LENGTH_LONG).show()
            }
        })

    }
    fun displayComment(comment: List<Comment>) {
        var adapter = CommentsRvAdapter(comment)
        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.adapter = adapter
    }
}
